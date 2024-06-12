package practiceApiAuto.test.charts;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.ChartNames;
import practiceApiAuto.test.Base;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class ChartTest_TopTrendingThreats extends Base {
    ChartNames chartNames;
    Response response;
    int length;

    @Test(priority = 1)
    public void testChartsWithEmptyChartNames(){
        chartNames=new ChartNames();
        Response response=EndPoints.getChart(chartNames);
        response.then().statusCode(400);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("status", equalTo("BAD_REQUEST"));
        response.then().body("message", equalTo(readConfig.provideChartsMsg()));
        Assert.assertNull(response.jsonPath().get("data"));
    }

    @Test(priority = 2)
    public void testChartsWithoutChartNames(){
        chartNames=new ChartNames();
        chartNames.chartNamesList().add("");
        Response response= EndPoints.getChart(chartNames);
        response.then().statusCode(400);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("status", equalTo("BAD_REQUEST"));
        response.then().body("message", equalTo(readConfig.chartsNotFoundMsg()));
        Assert.assertNull(response.jsonPath().get("data"));
    }

    @Test(priority = 3)
    public void chartTests() {
        chartNames = new ChartNames();
        chartNames.chartNamesList().add("TOP_TRENDING_THREATS");
//        chartNames.chartNamesList().add("ENDPOINTS");
        List<String> charts = chartNames.getChartNames();
        for (String chartName : charts) {
            if (chartName.equals("TOP_TRENDING_THREATS"))
                testTopTrendingThreats();
            else if (chartName.equals("ENDPOINTS")){
                testEndpoints();
            }
            else {
                System.out.println("Please provide correct chart names");
            }
        }
    }

    public void testTopTrendingThreats(){
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("TOP_TRENDING_THREATS Chart Response Found Successfully ");
        }else {
            response.then().log().body();
        }
        System.out.println(chartNames);
        response.then().statusCode(200);
        response.then().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        int length=jsonObject.getJSONObject("data").getJSONObject("TOP_TRENDING_THREATS").getJSONArray("topTrendingThreatsResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for TopTrendingThreats");
            boolean isTOP_TRENDING_THREATSExisting = jsonObject.getJSONObject("data").has("TOP_TRENDING_THREATS");
            if (isTOP_TRENDING_THREATSExisting) {
                Assert.assertTrue(true,"TOP_TRENDING_THREATS field is present in response");
                boolean isgraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("TOP_TRENDING_THREATS").has("topTrendingThreatsResponses");
                if (isgraphsResponsesExisting){
                    Assert.assertTrue(true,"topTrendingThreatsResponses field is present in response of TOP_TRENDING_THREATS");
                }else {
                    Assert.fail("topTrendingThreatsResponses field is not present in response of TOP_TRENDING_THREATS");
                }
            }else {
                Assert.fail("TOP_TRENDING_THREATS field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for topTrendingThreatsResponses is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testThreatNames(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("TOP_TRENDING_THREATS").getJSONArray("topTrendingThreatsResponses").length();
        if (length>0) {
            Assert.assertTrue(true,"Getting graph responses for TopTrendingThreats");
            for (int i = 0; i < length; i++) {
                boolean isThreatNameExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_TRENDING_THREATS").getJSONArray("topTrendingThreatsResponses").getJSONObject(i).has("threatName");
                if (isThreatNameExisting){
                    Assert.assertTrue(true,"threatName is present in the response of TOP_TRENDING_THREATS");
                    String threatName = jsonObject.getJSONObject("data").getJSONObject("TOP_TRENDING_THREATS").getJSONArray("topTrendingThreatsResponses").getJSONObject(i).getString("threatName");
//                    Assert.assertNotNull(threatName);
                    if (threatName!=null){
                        Assert.assertTrue(true,"ThreatName is not getting null in response");
                    }else {
                        Assert.fail("ThreatName is getting null in response of TOP_TRENDING_THREATS");
                    }
                }else {
                    Assert.fail("threatName is not present in the response of TOP_TRENDING_THREATS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for topTrendingThreatsResponses is 0");
        }
    }

    public void testEndpoints(){
        Response response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("ENDPOINTS Chart Response Found Successfully ");
        }else {
            response.then().log().body();
        }
    }
}
