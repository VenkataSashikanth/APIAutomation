package practiceApiAuto.test.charts;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.ChartNames;
import practiceApiAuto.test.Base;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class ChartTest_FilesByClassification extends Base {
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
        chartNames.chartNamesList().add("FILES_BY_CLASSIFICATION");
        List<String> charts=chartNames.getChartNames();
        for (String chartName: charts){
            if (chartName.equals("FILES_BY_CLASSIFICATION"))
                testFilesByClassification();
            else {
                System.out.println("Please provide correct chart names");
            }
        }
    }

    private void testFilesByClassification() {
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("FILES_BY_CLASSIFICATION Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for FILES_BY_CLASSIFICATION");
            boolean isFilesByClassificationExisting = jsonObject.getJSONObject("data").has("FILES_BY_CLASSIFICATION");
            if (isFilesByClassificationExisting) {
                Assert.assertTrue(true,"FILES_BY_CLASSIFICATION field is present in response");
                boolean isGraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").has("donutChartResponses");
                if (isGraphsResponsesExisting) {
                    Assert.assertTrue(true, "donutChartResponses field is present in response of FILES_BY_CLASSIFICATION");
                }else {
                    Assert.fail("donutChartResponses field is not present in response of FILES_BY_CLASSIFICATION");
                }
            }else {
                Assert.fail("FILES_BY_CLASSIFICATION field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for FILES_BY_CLASSIFICATION is 0");
        }
    }


    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testValuesField(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for FILES_BY_CLASSIFICATION");
            for (int i = 0; i < length; i++) {
                boolean isValueExisting=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").getJSONObject(i).has("value");
                if (isValueExisting){
                    Assert.assertTrue(true,"value is present in the response of FILES_BY_CLASSIFICATION");
                    Long value = jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").getJSONObject(i).getLong("value");
                    Assert.assertNotNull(value);

                    totalValue=totalValue+value;
                }else {
                    Assert.fail("value is not present in the response of FILES_BY_CLASSIFICATION");
                }
            }
        }else {
            Assert.fail("Length of Graph response for FILES_BY_CLASSIFICATION is 0");
        }
    }

    @Test(priority = 7,dependsOnMethods = {"chartTests", "testValuesField"})
    public void testTotalCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        boolean isTotalCountExisting=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").has("totalCount");
        if (isTotalCountExisting){
            Assert.assertTrue(true,"TotalCount exists in response");
            Long totalCount=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getLong("totalCount");
//            Assert.assertNotNull(totalCount);
            if (totalCount!=null){
                Assert.assertTrue(true,"Getting TotalCount in response");
            }else {
                Assert.fail("TotalCount is not getting in response of FILES_BY_CLASSIFICATION");
            }
            Assert.assertEquals(totalCount,totalValue);
        }else {
            Assert.fail("TotalCount is not present in response");
        }
    }

    @Test(priority = 8,dependsOnMethods = "chartTests")
    public void testPreviousDayCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        boolean isPreviousTotalCountExisting=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").has("previousDayTotalCount");
        if (isPreviousTotalCountExisting){
            Assert.assertTrue(true,"PreviousTotalCountExisting exists in response");
            Long previousDayTotalCount=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getLong("previousDayTotalCount");
//            Assert.assertNotNull(previousDayTotalCount);
            if (previousDayTotalCount!=null){
                Assert.assertTrue(true,"PreviousDayTotalCount is not null in response");
            }else {
                Assert.fail("PreviousDayTotalCount is getting null in response");
            }
        }else {
            Assert.fail("PreviousTotalCountExisting is not present in response");
        }

    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testColorValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for FILES_BY_CLASSIFICATION");
            for (int i=0; i<length; i++){
                boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").getJSONObject(i).has("color");
                if (isColorExisting){
                    Assert.assertTrue(true,"color is present in the response of FILES_BY_CLASSIFICATION");
                    String color = jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").getJSONObject(i).getString("color");
//                    Assert.assertNotNull(color);
                    if (color!=null){
                        Assert.assertTrue(true,"color is not null in response");
                    }else {
                        Assert.fail("Color is getting null in response");
                    }
                }else {
                    Assert.fail("color is not present in the response of FILES_BY_CLASSIFICATION");
                }
            }
        }else {
            Assert.fail("Length of Graph response for FILES_BY_CLASSIFICATION is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testNames(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for FILES_BY_CLASSIFICATION");
            for (int i=0; i<length; i++){
                boolean isNameExisting=jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").getJSONObject(i).has("name");
                if (isNameExisting){
                    Assert.assertTrue(true,"name is present in the response of FILES_BY_CLASSIFICATION");
                    String name = jsonObject.getJSONObject("data").getJSONObject("FILES_BY_CLASSIFICATION").getJSONArray("donutChartResponses").getJSONObject(i).getString("name");
//                    Assert.assertNotNull(name);
                    if (name!=null){
                        Assert.assertTrue(true,"Name is not getting null in response");
                    }else {
                        Assert.fail("name is getting null in response");
                    }
                }else {
                    Assert.fail("name is not present in the response of FILES_BY_CLASSIFICATION");
                }
            }
        }else {
            Assert.fail("Length of Graph response for FILES_BY_CLASSIFICATION is 0");
        }
    }
}
