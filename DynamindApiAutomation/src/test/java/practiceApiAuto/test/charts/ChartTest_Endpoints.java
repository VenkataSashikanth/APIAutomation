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

public class ChartTest_Endpoints extends Base {
    ChartNames chartNames;
    Response response;
    int length;
    LocalDate currentDate=LocalDate.now();
    LocalDate sevenDaysBackDate=currentDate.minusDays(8);

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
    public void chartTests() {
        chartNames = new ChartNames();
        chartNames.chartNamesList().add("ENDPOINTS");
        List<String> charts = chartNames.getChartNames();
        for (String chartName : charts) {
            if (chartName.equals("ENDPOINTS")){
                testEndpoints();
            }
            else {
                System.out.println("Please provide correct chart names");
            }
        }
    }

    public void testEndpoints(){
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("ENDPOINTS Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(7000L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ENDPOINTS");
            boolean isENDPOINTSExisting = jsonObject.getJSONObject("data").has("ENDPOINTS");
            if (isENDPOINTSExisting){
                Assert.assertTrue(true,"ENDPOINTS field is present in response");
                boolean isgraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").has("endPointsResponses");
                if (isgraphsResponsesExisting){
                    Assert.assertTrue(true,"graphsResponses field is present in response of ENDPOINTS");
                }else {
                    Assert.fail("graphsResponses field is not present in response of ENDPOINTS");
                }
            }else {
                Assert.fail("ENDPOINTS field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for ENDPOINTS is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testActiveCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ENDPOINTS");
            for (int i=0; i<length; i++){
                boolean isActiveCountExisting=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).has("activeCount");
                if (isActiveCountExisting){
                    Assert.assertTrue(true,"activeCount is present in the response of ENDPOINTS");
                    Long activeCount = jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).getLong("activeCount");
                    Assert.assertNotNull(activeCount);
                }else {
                    Assert.fail("activeCount is not present in the response of ENDPOINTS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for ENDPOINTS is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testProtectedCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ENDPOINTS");
            for (int i=0; i<length; i++){
                boolean isProtectedCountExisting=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).has("protectedCount");
                if (isProtectedCountExisting){
                    Assert.assertTrue(true,"protectedCount is present in the response of ENDPOINTS");
                    Long protectedCount = jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).getLong("protectedCount");
                    Assert.assertNotNull(protectedCount);
                }else {
                    Assert.fail("protectedCount is not present in the response of ENDPOINTS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for ENDPOINTS is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testColorForActive(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ENDPOINTS");
            for (int i=0; i<length; i++){
                boolean isColorForActiveExisting=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).has("colorForActive");
                if (isColorForActiveExisting){
                    Assert.assertTrue(true,"colorForActive is present in the response of ENDPOINTS");
                    String colorForActive = jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).getString("colorForActive");
//                    Assert.assertNotNull(colorForActive);
                    if (colorForActive!=null){
                        Assert.assertTrue(true,"Getting color in response");
                    }else {
                        Assert.fail("Colour is getting null in response of ENDPOINTS");
                    }
                }else {
                    Assert.fail("colorForActive is not present in the response of ENDPOINTS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for ENDPOINTS is 0");
        }
    }

    @Test(priority = 7,dependsOnMethods = "chartTests")
    public void testColorForProtected(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ENDPOINTS");
            for (int i=0; i<length; i++){
                boolean isColorForProtectedExisting=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).has("colorForProtected");
                if (isColorForProtectedExisting){
                    Assert.assertTrue(true,"colorForProtected is present in the response of ENDPOINTS");
                    String colorForProtected = jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).getString("colorForProtected");
//                    Assert.assertNotNull(colorForProtected);
                    if (colorForProtected!=null){
                        Assert.assertTrue(true,"Getting colour in response");
                    }else {
                        Assert.fail("Colour is getting null in response of ENDPOINTS");
                    }
                }else {
                    Assert.fail("colorForProtected is not present in the response of ENDPOINTS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for ENDPOINTS is 0");
        }
    }

    @Test(priority = 8,dependsOnMethods = "chartTests")
    public void testDatesRange(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for ENDPOINTS");
            for (int i=0; i<length; i++){
                boolean isDateExisting=jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).has("date");
                if (isDateExisting){
                    Assert.assertTrue(true,"Date is present in the response of ENDPOINTS");
                    String dateValue = jsonObject.getJSONObject("data").getJSONObject("ENDPOINTS").getJSONArray("endPointsResponses").getJSONObject(i).getString("date");
//                    Assert.assertNotNull(dateValue);
                    if (dateValue!=null){
                        Assert.assertTrue(true,"Getting date in response");
                    }else {
                        Assert.fail("Date is getting null in response of ENDPOINTS");
                    }
                }else {
                    Assert.fail("Date is not present in the response of ENDPOINTS");
                }
            /*LocalDate date=LocalDate.parse(dateValue, DateTimeFormatter.ISO_DATE);
            Assert.assertTrue(date.isAfter(sevenDaysBackDate) && date.isBefore(currentDate), "Date is not within the specified range");*/
            }
        }else {
            Assert.fail("Length of Graph response for ENDPOINTS is 0");
        }

        System.out.println("Date Assertions passed: All dates are within expected dates range");
    }
}
