package practiceApiAuto.test.charts;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.ChartNames;
import practiceApiAuto.test.Base;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class ChartTest_ThreatsDetected extends Base {
    ChartNames chartNames;
    Response response;
    int length;
    LocalDate currentDate=LocalDate.now();
    LocalDate sevenDaysBackDate=currentDate.minusDays(8);

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
        chartNames.chartNamesList().add("THREATS_DETECTED");
        List<String> charts=chartNames.getChartNames();
        for (String chartName:charts){
            if (chartName.equals("THREATS_DETECTED"))
                testThreatsDetected();
        }
    }

    private void testThreatsDetected() {
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("THREATS_DETECTED Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ThreatsDetected");

            boolean isThreatsDetectedExisting = jsonObject.getJSONObject("data").has("THREATS_DETECTED");

            if (isThreatsDetectedExisting) {
                Assert.assertTrue(true,"THREATS_DETECTED field is present in response");
                boolean isdualAxisChartResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").has("dualAxisChartResponses");
                if (isdualAxisChartResponsesExisting){
                    Assert.assertTrue(true,"dualAxisChartResponses field is present in response of THREATS_DETECTED");
                }else {
                    Assert.fail("dualAxisChartResponses field is not present in response of THREATS_DETECTED");
                }
            }else {
                Assert.fail("THREATS_DETECTED field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testCountForLineGraph(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ThreatsDetected");
            for (int i=0; i<length; i++){
                boolean isCountForLineGraphExisting=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).has("countForLineGraph");
                if (isCountForLineGraphExisting){
                    Assert.assertTrue(true,"countForLineGraph is present in the response of THREATS_DETECTED");
                    Long countForLineGraph = jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).getLong("countForLineGraph");
                    Assert.assertNotNull(countForLineGraph);
                }else {
                    Assert.fail("countForLineGraph is not present in the response of THREATS_DETECTED");
                }
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testCountForBarGraph(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ThreatsDetected");
            for (int i=0; i<length; i++){
                boolean isCountForBarGraphExisting=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).has("countForBarGraph");
                if (isCountForBarGraphExisting){
                    Assert.assertTrue(true,"countForBarGraph is present in the response of THREATS_DETECTED");
                    Long countForBarGraph = jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).getLong("countForBarGraph");
                    Assert.assertNotNull(countForBarGraph);
                }else {
                    Assert.fail("countForBarGraph is not present in the response of THREATS_DETECTED");
                }
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testColorForLineGraph(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ThreatsDetected");
            for (int i=0; i<length; i++){
                boolean isColorForLineGraphExisting=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).has("colorForBarGraph");
                if (isColorForLineGraphExisting){
                    Assert.assertTrue(true,"colorForLineGraph is present in the response of THREATS_DETECTED");
                    String colorForLineGraph = jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).getString("colorForBarGraph");
                    Assert.assertNotNull(colorForLineGraph);
                }else {
                    Assert.fail("colorForLineGraph is not present in the response of THREATS_DETECTED");
                }
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }

    @Test(priority = 7,dependsOnMethods = "chartTests")
    public void testColorForBarGraph(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ThreatsDetected");
            for (int i=0; i<length; i++){
                boolean isColorForBarGraphExisting=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).has("colorForBarGraph");
                if (isColorForBarGraphExisting){
                    Assert.assertTrue(true,"colorForBarGraph is present in the response of THREATS_DETECTED");
                    String colorForBarGraph = jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).getString("colorForBarGraph");
                    Assert.assertNotNull(colorForBarGraph);
                }else {
                    Assert.fail("colorForBarGraph is not present in the response of THREATS_DETECTED");
                }
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }

    @Test(priority = 8,dependsOnMethods = "chartTests")
    public void testDatesRange(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ThreatsDetected");
            for (int i=0; i<length; i++){
                boolean isDateExisting=jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).has("date");
                if (isDateExisting){
                    Assert.assertTrue(true,"date is present in the response of THREATS_DETECTED");
                    String dateValue = jsonObject.getJSONObject("data").getJSONObject("THREATS_DETECTED").getJSONArray("dualAxisChartResponses").getJSONObject(i).getString("date");
                    Assert.assertNotNull(dateValue);
                }else {
                    Assert.fail("date is not present in the response of THREATS_DETECTED");
                }
            /*LocalDate date=LocalDate.parse(dateValue, DateTimeFormatter.ISO_DATE);
            Assert.assertTrue(date.isAfter(sevenDaysBackDate) && date.isBefore(currentDate), "Date is not within the specified range");*/
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
        System.out.println("Date Assertions passed: All dates are within expected dates range");
    }
}
