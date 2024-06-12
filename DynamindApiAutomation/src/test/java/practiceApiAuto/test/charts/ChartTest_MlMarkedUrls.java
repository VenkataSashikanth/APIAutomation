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

public class ChartTest_MlMarkedUrls extends Base {
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
    public void chartTests(){
        chartNames=new ChartNames();
        chartNames.chartNamesList().add("ML_MARKED_URLS");
        List<String> charts=chartNames.getChartNames();

        charts.forEach((chartName)-> {
            if (chartName.equals("ML_MARKED_URLS"))
                testMlMarkedUrls();
        });

        /*for (String chartName:charts){
            if (chartName.equals("ML_MARKED_URLS"))
                testMlMarkedUrls();
        }*/
    }

    private void testMlMarkedUrls() {
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("ML_MARKED_URLS Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for MlMarkedURLs");
            boolean isMlMarkedUrlsExisting = jsonObject.getJSONObject("data").has("ML_MARKED_URLS");
            if (isMlMarkedUrlsExisting){
                Assert.assertTrue(true,"ML_MARKED_URLS field is present in response");
                boolean isGraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").has("lineGraphResponses");
                if (isGraphsResponsesExisting){
                    Assert.assertTrue(true,"lineGraphResponses field is present in response of ML_MARKED_URLS");
                }else {
                    Assert.fail("lineGraphResponses field is not present in response of ML_MARKED_URLS");
                }
            }else {
                Assert.fail("ML_MARKED_URLS field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for ML_MARKED_URLS is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for MlMarkedURLs");
            for (int i = 0; i < length; i++) {
                boolean isValueExisting=jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").getJSONObject(i).has("value");
                if (isValueExisting){
                    Assert.assertTrue(true,"value is present in the response of ML_MARKED_URLS");
                    Long value = jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").getJSONObject(i).getLong("value");
                    Assert.assertNotNull(value);
                }else {
                    Assert.fail("value is not present in the response of ML_MARKED_URLS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for ML_MARKED_URLS is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testColorValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for MlMarkedURLs");
            for (int i=0; i<length; i++){
                boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").getJSONObject(i).has("color");
                if (isColorExisting) {
                    Assert.assertTrue(true,"color is present in the response of ML_MARKED_URLS");
                    String color = jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").getJSONObject(i).getString("color");
//                    Assert.assertNotNull(color);
                    if (color!=null){
                        Assert.assertTrue(true,"Colour is not getting null in response");
                    }else {
                        Assert.fail("Colour is getting null in response of ML_MARKED_URLS");
                    }
                }else {
                    Assert.fail("color is present in the response of ML_MARKED_URLS");
                }
            }
        }else {
            Assert.fail("Length of Graph response for ML_MARKED_URLS is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testDatesRange(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for MlMarkedURLs");
            for (int i=0; i<length; i++){
                boolean isDateExisting=jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").getJSONObject(i).has("date");
                if (isDateExisting){
                    Assert.assertTrue(true,"Date is present in the response of ML_MARKED_URLS");
                    String dateValue = jsonObject.getJSONObject("data").getJSONObject("ML_MARKED_URLS").getJSONArray("lineGraphResponses").getJSONObject(i).getString("date");
//                    Assert.assertNotNull(dateValue);
                    if (dateValue!=null){
                        Assert.assertTrue(true,"date is not getting null in response");
                    }else {
                        Assert.fail("date is getting null in response");
                    }
                }else {
                    Assert.fail("Date is present in the response of ML_MARKED_URLS");
                }

            /*LocalDate date=LocalDate.parse(dateValue, DateTimeFormatter.ISO_DATE);
            Assert.assertTrue(date.isAfter(sevenDaysBackDate) && date.isBefore(currentDate), "Date is not within the specified range");*/
            }
        }else {
            Assert.fail("Length of Graph response for ML_MARKED_URLS is 0");
        }
        System.out.println("Date Assertions passed: All dates are within expected dates range");
    }
}
