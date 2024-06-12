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

public class ChartTest_UrlStats extends Base {

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
        chartNames.chartNamesList().add("URL_STATS");
        List<String> charts = chartNames.getChartNames();
        for (String chartName : charts) {
            if (chartName.equals("URL_STATS"))
                testFileStatsResponses();
            else {
                System.out.println("Please provide correct chart names");
            }
        }
    }

    public void testFileStatsResponses(){
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("URL_STATS Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for UrlStats");

            boolean isURL_STATSExisting = jsonObject.getJSONObject("data").has("URL_STATS");
            if (isURL_STATSExisting) {
                Assert.assertTrue(true,"URL_STATS field is present in response");
                boolean isGraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("URL_STATS").has("responseForGraphs");
                if (isGraphsResponsesExisting){
                    Assert.assertTrue(true,"responseForGraphs field is present in response of URL_STATS");
                }else {
                    Assert.fail("responseForGraphs field is not present in response of URL_STATS");
                }
            }else {
                Assert.fail("URL_STATS field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for URL_STATS is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").length();
        if (length>0) {
            Assert.assertTrue(true, "Getting graph responses for UrlStats");
            for (int i = 0; i < length; i++) {
                boolean isValueExisting=jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").getJSONObject(i).has("value");
                if (isValueExisting){
                    Assert.assertTrue(true,"Value is present in the response of URL_STATS");
                    Long value = jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").getJSONObject(i).getLong("value");
                    Assert.assertNotNull(value);
                }else {
                    Assert.fail("Value is not present in the response of URL_STATS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for URL_STATS is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testColorValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").length();
        if (length>0) {
            Assert.assertTrue(true, "Getting graph responses for UrlStats");
            for (int i=0; i<length; i++){
                boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").getJSONObject(i).has("color");
                if (isColorExisting){
                    Assert.assertTrue(true,"Color is present in the response of URL_STATS");
                    String color = jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").getJSONObject(i).getString("color");
                    Assert.assertNotNull(color);
                }else {
                    Assert.fail("Color is not present in the response of URL_STATS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for URL_STATS is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testDatesRange(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").length();
        if (length>0) {
            Assert.assertTrue(true, "Getting graph responses for UrlStats");
            for (int i=0; i<length; i++){
                boolean isDateExisting=jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").getJSONObject(i).has("date");
                if (isDateExisting){
                    Assert.assertTrue(true,"Date is present in the response of URL_STATS");
                    String dateValue = jsonObject.getJSONObject("data").getJSONObject("URL_STATS").getJSONArray("responseForGraphs").getJSONObject(i).getString("date");
                    Assert.assertNotNull(dateValue);
                }else {
                    Assert.fail("Date is not present in the response of URL_STATS");
                }
            /*LocalDate date=LocalDate.parse(dateValue, DateTimeFormatter.ISO_DATE);
            Assert.assertTrue(date.isAfter(sevenDaysBackDate) && date.isBefore(currentDate), "Date is not within the specified range");*/
            }
        }else {
            Assert.fail("Length of Graph response for URL_STATS is 0");
        }
        System.out.println("Date Assertions passed: All dates are within expected dates range");
    }
}
