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

public class ChartTest_DetectionByIndustry extends Base {
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
        Response response=EndPoints.getChart(chartNames);
        response.then().statusCode(400);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("status", equalTo("BAD_REQUEST"));
        response.then().body("message", equalTo(readConfig.chartsNotFoundMsg()));
        Assert.assertNull(response.jsonPath().get("data"));
    }

    @Test(priority = 3)
    public void chartTests(){
        chartNames=new ChartNames();
        chartNames.chartNamesList().add("DETECTION_BY_INDUSTRY");
        List<String> charts=chartNames.getChartNames();
        for (String chartName:charts){
            if (chartName.equals("DETECTION_BY_INDUSTRY")){
                testDetectionByIndustry();
            }else {
                System.out.println("Please provide correct chart name");
            }
        }
    }

    private void testDetectionByIndustry() {
        response=EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("DETECTION_BY_INDUSTRY Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(700L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for DETECTION_BY_INDUSTRY");
            boolean isDetectionByIndustryExisting = jsonObject.getJSONObject("data").has("DETECTION_BY_INDUSTRY");
            if (isDetectionByIndustryExisting){
                Assert.assertTrue(true,"DETECTION_BY_INDUSTRY field is present in response");
                boolean isGraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").has("responseForGraphs");
                if (isGraphsResponsesExisting){
                    Assert.assertTrue(true,"graphsResponses field is present in response of DETECTION_BY_INDUSTRY");
                }else {
                    Assert.fail("graphsResponses field is not present in response of DETECTION_BY_INDUSTRY");
                }
            }else {
                Assert.fail("graphsResponses field is not present in response of DETECTION_BY_INDUSTRY");
            }
        }else {
            Assert.fail("Length of Graph response for DETECTION_BY_INDUSTRY is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testValuesField(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for DETECTION_BY_INDUSTRY");
            for (int i = 0; i < length; i++) {
                boolean isValueExisting=jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").getJSONObject(i).has("value");
                if (isValueExisting){
                    Assert.assertTrue(true,"value is present in the response of DETECTION_BY_INDUSTRY");
                    Long value = jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").getJSONObject(i).getLong("value");
//                    Assert.assertNotNull(value);
                    if (value!=null){
                        Assert.assertTrue(true,"Getting value");
                    }else {
                        Assert.fail("value is getting null in response for DETECTION_BY_INDUSTRY");
                    }
                }else {
                    Assert.fail("value is not present in the response of DETECTION_BY_INDUSTRY");
                }
            }
        }else {
            Assert.fail("Length of Graph response for DETECTION_BY_INDUSTRY is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testColorValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for DETECTION_BY_INDUSTRY");
            for (int i=0; i<length; i++){
                boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").getJSONObject(i).has("color");
                if (isColorExisting){
                    Assert.assertTrue(true,"color is present in the response of DETECTION_BY_INDUSTRY");
                    String color = jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").getJSONObject(i).getString("color");
//                    Assert.assertNotNull(color);
                    if (color!=null){
                        Assert.assertTrue(true);
                    }else {
                        Assert.fail("Colour is getting null for DETECTION_BY_INDUSTRY");
                    }
                }else {
                    Assert.fail("color is not present in the response of DETECTION_BY_INDUSTRY");
                }
            }
        }else {
            Assert.fail("Length of Graph response for DETECTION_BY_INDUSTRY is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testNames(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for DETECTION_BY_INDUSTRY");
            for (int i=0; i<length; i++){
                boolean isNameExisting=jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").getJSONObject(i).has("threatName");
                if (isNameExisting){
                    Assert.assertTrue(true,"name is present in the response of DETECTION_BY_INDUSTRY");
                    String threatName = jsonObject.getJSONObject("data").getJSONObject("DETECTION_BY_INDUSTRY").getJSONArray("responseForGraphs").getJSONObject(i).getString("threatName");
//                    Assert.assertNotNull(threatName);
                    if (threatName!=null){
                        Assert.assertTrue(true);
                    }else {
                        Assert.fail("threatName is getting null for DETECTION_BY_INDUSTRY");
                    }
                }else {
                    Assert.fail("name is not present in the response of DETECTION_BY_INDUSTRY");
                }
            }
        }else {
            Assert.fail("Length of Graph response for DETECTION_BY_INDUSTRY is 0");
        }
    }
}
