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

public class ChartTest_URLBySource extends Base {
    ChartNames chartNames;
    Response response;
    int length;
    Long totalValue=0L;

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
    public void chartTests(){
        chartNames=new ChartNames();
        chartNames.chartNamesList().add("URL_BY_SOURCE");
        List<String> charts=chartNames.getChartNames();
        for (String chartName:charts){
            if (chartName.equals("URL_BY_SOURCE"))
                testUrlByClassification();
        }
    }

    private void testUrlByClassification() {
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("URL_BY_SOURCE Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for URLBySource");
            boolean isURLBySourceExisting = jsonObject.getJSONObject("data").has("URL_BY_SOURCE");

            if (isURLBySourceExisting) {
                Assert.assertTrue(true,"URL_BY_SOURCE field is present in response");
                boolean isGraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").has("donutChartResponses");
                if (isGraphsResponsesExisting){
                    Assert.assertTrue(true,"donutChartResponses field is present in response of URL_BY_SOURCE");
                }else {
                    Assert.fail("donutChartResponses field is not present in response of URL_BY_SOURCE");
                }
            }else {
                Assert.fail("URL_BY_SOURCE field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for URL_BY_SOURCE is 0");
        }
    }


    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testValuesField(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for URLBySource");
            for (int i = 0; i < length; i++) {
                boolean isValueExisting=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").getJSONObject(i).has("value");
                if (isValueExisting){
                    Assert.assertTrue(true,"value is present in the response of URL_BY_SOURCE");
                    Long value = jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").getJSONObject(i).getLong("value");
                    Assert.assertNotNull(value);

                    totalValue=totalValue+value;
                }else {
                    Assert.fail("value is not present in the response of URL_BY_SOURCE");
                }
            }
        }else {
            Assert.fail("Length of Graph response for URL_BY_SOURCE is 0");
        }
    }

    @Test(priority = 7,dependsOnMethods = {"chartTests", "testValuesField"})
    public void testTotalCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        boolean isTotalCountExisting=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").has("totalCount");
        if (isTotalCountExisting){
            Assert.assertTrue(true,"TotalCount exists in response");
            Long totalCount=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getLong("totalCount");
            Assert.assertNotNull(totalCount);

            Assert.assertEquals(totalCount,totalValue);
        }else {
            Assert.fail("TotalCount is not exists in response");
        }
    }

    @Test(priority = 8,dependsOnMethods = "chartTests")
    public void testPreviousDayCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        boolean isPreviousDayTotalCountExisting=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").has("previousDayTotalCount");
        if (isPreviousDayTotalCountExisting){
            Assert.assertTrue(true,"PreviousDayTotalCount exists in response");
            Long previousDayTotalCount=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getLong("previousDayTotalCount");
            Assert.assertNotNull(previousDayTotalCount);
        }else {
            Assert.fail("PreviousDayTotalCount is not exists in response");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testColorValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for URLBySource");
            for (int i=0; i<length; i++){
                boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").getJSONObject(i).has("color");
                if (isColorExisting){
                    Assert.assertTrue(true,"color is present in the response of URL_BY_SOURCE");
                    String color = jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").getJSONObject(i).getString("color");
                    Assert.assertNotNull(color);
                }else {
                    Assert.fail("color is not present in the response of URL_BY_SOURCE");
                }
            }
        }else {
            Assert.fail("Length of Graph response for URL_BY_SOURCE is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testNames(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for URLBySource");
            for (int i=0; i<length; i++){
                boolean isNameExisting=jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").getJSONObject(i).has("name");
                if (isNameExisting){
                    Assert.assertTrue(true,"name is present in the response of URL_BY_SOURCE");
                    String name = jsonObject.getJSONObject("data").getJSONObject("URL_BY_SOURCE").getJSONArray("donutChartResponses").getJSONObject(i).getString("name");
                    Assert.assertNotNull(name);
                }else {
                    Assert.fail("name is not present in the response of URL_BY_SOURCE");
                }
            }
        }else {
            Assert.fail("Length of Graph response for URL_BY_SOURCE is 0");
        }
    }
}
