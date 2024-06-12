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

public class ChartTest_Top10TargetedIndustries extends Base {
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
    public void chartTests(){
        chartNames=new ChartNames();
        chartNames.chartNamesList().add("TOP_10_TARGETED_INDUSTRIES");
        List<String> charts=chartNames.getChartNames();
        for (String chartName:charts){
            if (chartName.equals("TOP_10_TARGETED_INDUSTRIES")) {
                testTop10TargetedIndustries();
            }
            else {
                System.out.println("Please provide correct chart name");
            }
        }
    }

    private void testTop10TargetedIndustries() {
        response=EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("testTop10TargetedIndustries Chart Response Found Successfully ");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for TOP_10_TARGETED_INDUSTRIES");
            boolean isTop10ExploitedProcessesExisting = jsonObject.getJSONObject("data").has("TOP_10_TARGETED_INDUSTRIES");
            if (isTop10ExploitedProcessesExisting) {
                Assert.assertTrue(true,"TOP_10_TARGETED_INDUSTRIES field is present in response");
                boolean isRaceBarChartDtosExisting = jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").has("raceBarChartDtos");
                if (isRaceBarChartDtosExisting){
                    Assert.assertTrue(true,"raceBarChartDtos field is present in response of TOP_10_TARGETED_INDUSTRIES");
                }else {
                    Assert.fail("raceBarChartDtos field is not present in response of TOP_10_TARGETED_INDUSTRIES");
                }
            }else {
                Assert.fail("TOP_10_TARGETED_INDUSTRIES field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for TOP_10_TARGETED_INDUSTRIES is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testDetectionType(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for TOP_10_TARGETED_INDUSTRIES");
            for (int i=0; i<length; i++){
                boolean isDetectionTypeExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).has("detectionType");
                if (isDetectionTypeExisting){
                    Assert.assertTrue(true,"DetectionType is present in the response of TOP_10_TARGETED_INDUSTRIES");
                    String detectionType = jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getString("detectionType");
                    Assert.assertNotNull(detectionType);
                }
            }
        }else {
            Assert.fail("Length of Graph response for TOP_10_TARGETED_INDUSTRIES is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testDonutChartResponseList(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for TOP_10_TARGETED_INDUSTRIES");
            for (int i=0; i<length; i++){
                boolean isDonutChartResponseListExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).has("donutChartResponseList");
                if (isDonutChartResponseListExisting){
                    Assert.assertTrue(true,"donutChartResponseList is present in the response of TOP_10_TARGETED_INDUSTRIES");
                    int dailyLength=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").length();
                    if (dailyLength>0){
                        Assert.assertTrue(true,"Getting RaceBarCharts for TOP_10_TARGETED_INDUSTRIES");
                    }else {
                        Assert.fail("Length of RaceBarCharts for TOP_10_TARGETED_INDUSTRIES response is getting 0");
                    }
                }else {
                    Assert.fail("donutChartResponseList is not present in the response of TOP_10_TARGETED_INDUSTRIES");
                }
            }
        }else {
            Assert.fail("Length of Graph response for TOP_10_TARGETED_INDUSTRIES is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testValue(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for TOP_10_TARGETED_INDUSTRIES");
            for (int i=0; i<length; i++){
                boolean isDonutChartResponseListExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).has("donutChartResponseList");
                if (isDonutChartResponseListExisting) {
                    Assert.assertTrue(true,"donutChartResponseList is present in the response of TOP_10_TARGETED_INDUSTRIES");
                    int dailyLength=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").length();
                    if (dailyLength>0){
                        Assert.assertTrue(true,"Getting RaceBarCharts for TOP_10_TARGETED_INDUSTRIES");
                        for (int j=0; j<dailyLength; j++){
                            boolean isValueExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").getJSONObject(j).has("value");
                            if (isValueExisting){
                                Assert.assertTrue(true,"Value is present in DonutChartResponseList for TOP_10_TARGETED_INDUSTRIES");
                                Long value=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").getJSONObject(j).getLong("value");
                                Assert.assertNotNull(value,"Getting value in response");
                            }else {
                                Assert.fail("Value is not present in DonutChartResponseList for TOP_10_TARGETED_INDUSTRIES");
                            }
                        }
                    }else {
                        Assert.fail("Length of RaceBarCharts for TOP_10_TARGETED_INDUSTRIES response is getting 0");
                    }
                }else {
                    Assert.fail("donutChartResponseList is not present in the response of TOP_10_TARGETED_INDUSTRIES");
                }
            }
        }else {
            Assert.fail("Length of Graph response for TOP_10_TARGETED_INDUSTRIES is 0");
        }
    }

    @Test(priority = 7,dependsOnMethods = "chartTests")
    public void testName(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for TOP_10_TARGETED_INDUSTRIES");
            for (int i=0; i<length; i++){
                boolean isDonutChartResponseListExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).has("donutChartResponseList");
                if (isDonutChartResponseListExisting) {
                    Assert.assertTrue(true,"donutChartResponseList is present in the response of TOP_10_TARGETED_INDUSTRIES");
                    int dailyLength=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").length();
                    if (dailyLength>0){
                        Assert.assertTrue(true,"Getting RaceBarCharts for TOP_10_TARGETED_INDUSTRIES");
                        for (int j=0; j<dailyLength; j++){
                            boolean isNameExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").getJSONObject(j).has("name");
                            if (isNameExisting){
                                Assert.assertTrue(true,"Name is present in DonutChartResponseList for TOP_10_TARGETED_INDUSTRIES");
                                String name=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").getJSONObject(j).getString("name");
                                Assert.assertNotNull(name,"Getting name in response");
                            }else {
                                Assert.fail("Name is not present in DonutChartResponseList for TOP_10_TARGETED_INDUSTRIES");
                            }
                        }
                    }else {
                        Assert.fail("Length of RaceBarCharts for TOP_10_TARGETED_INDUSTRIES response is getting 0");
                    }
                }else {
                    Assert.fail("donutChartResponseList is not present in the response of TOP_10_TARGETED_INDUSTRIES");
                }
            }
        }else {
            Assert.fail("Length of Graph response for TOP_10_TARGETED_INDUSTRIES is 0");
        }
    }

    @Test(priority = 8,dependsOnMethods = "chartTests")
    public void testColor(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for TOP_10_TARGETED_INDUSTRIES");
            for (int i=0; i<length; i++){
                boolean isDonutChartResponseListExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).has("donutChartResponseList");
                if (isDonutChartResponseListExisting) {
                    Assert.assertTrue(true,"donutChartResponseList is present in the response of TOP_10_TARGETED_INDUSTRIES");
                    int dailyLength=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").length();
                    if (dailyLength>0){
                        Assert.assertTrue(true,"Getting RaceBarCharts for TOP_10_TARGETED_INDUSTRIES");
                        for (int j=0; j<dailyLength; j++){
                            boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").getJSONObject(j).has("color");
                            if (isColorExisting){
                                Assert.assertTrue(true,"Color is present in DonutChartResponseList for TOP_10_TARGETED_INDUSTRIES");
                                String color=jsonObject.getJSONObject("data").getJSONObject("TOP_10_TARGETED_INDUSTRIES").getJSONArray("raceBarChartDtos").getJSONObject(i).getJSONArray("donutChartResponseList").getJSONObject(j).getString("color");
                                Assert.assertNotNull(color,"Getting color in response");
                            }else {
                                Assert.fail("Color is not present in DonutChartResponseList for TOP_10_TARGETED_INDUSTRIES");
                            }
                        }
                    }else {
                        Assert.fail("Length of RaceBarCharts for TOP_10_TARGETED_INDUSTRIES response is getting 0");
                    }
                }else {
                    Assert.fail("donutChartResponseList is not present in the response of TOP_10_TARGETED_INDUSTRIES");
                }
            }
        }else {
            Assert.fail("Length of Graph response for TOP_10_TARGETED_INDUSTRIES is 0");
        }
    }
}
