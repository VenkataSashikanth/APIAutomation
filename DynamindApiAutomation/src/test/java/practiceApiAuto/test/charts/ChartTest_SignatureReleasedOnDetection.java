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

public class ChartTest_SignatureReleasedOnDetection extends Base {
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
        chartNames.chartNamesList().add("SIGNATURE_RELEASED_ON_DETECTION");
        List<String> charts=chartNames.getChartNames();
        for (String chartName:charts){
            if (chartName.equals("SIGNATURE_RELEASED_ON_DETECTION"))
                testSignatureReleasedOnDetection();
        }
    }

    private void testSignatureReleasedOnDetection() {
        response= EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("SIGNATURE_RELEASED_ON_DETECTION Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for SignatureReleasedOnDetection");
            boolean isSignatureReleasedOnDetectionExisting = jsonObject.getJSONObject("data").has("SIGNATURE_RELEASED_ON_DETECTION");
            if (isSignatureReleasedOnDetectionExisting) {
                Assert.assertTrue(true,"SIGNATURE_RELEASED_ON_DETECTION field is present in response");
                boolean isGraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").has("donutChartResponses");
                if (isGraphsResponsesExisting){
                    Assert.assertTrue(true,"donutChartResponses field is present in response of SIGNATURE_RELEASED_ON_DETECTION");
                }else {
                    Assert.fail("donutChartResponses field is not present in response of SIGNATURE_RELEASED_ON_DETECTION");
                }
            }else{
                Assert.fail("SIGNATURE_RELEASED_ON_DETECTION field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }


    @Test(priority = 3,dependsOnMethods = "chartTests")
    public void testValuesField(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for SignatureReleasedOnDetection");
            for (int i = 0; i < length; i++) {
                boolean isValueExisting=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").getJSONObject(i).has("value");
                if (isValueExisting){
                    Assert.assertTrue(true,"value is present in the response of SIGNATURE_RELEASED_ON_DETECTION");
                    Long value = jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").getJSONObject(i).getLong("value");
                    Assert.assertNotNull(value);

                    totalValue=totalValue+value;
                }else {
                    Assert.fail("value is not present in the response of SIGNATURE_RELEASED_ON_DETECTION");
                }
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = {"chartTests", "testValuesField"})
    public void testTotalCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        boolean isTotalCountExisting=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").has("totalCount");
        if (isTotalCountExisting){
            Assert.assertTrue(true,"TotalCount exists in response");
            Long totalCount=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getLong("totalCount");
            Assert.assertNotNull(totalCount);

            Assert.assertEquals(totalCount,totalValue);
        }else {
            Assert.fail("TotalCount is not exists in response");
        }
    }

    @Test(priority = 7,dependsOnMethods = "chartTests")
    public void testPreviousDayCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        boolean isPreviousDayTotalCountExisting=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").has("previousDayTotalCount");
        if (isPreviousDayTotalCountExisting){
            Assert.assertTrue(true,"PreviousDayTotalCount exists in response");
            Long previousDayTotalCount=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getLong("previousDayTotalCount");
            Assert.assertNotNull(previousDayTotalCount);
        }else {
            Assert.fail("PreviousDayTotalCount is not exists in response");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testColorValues(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for SignatureReleasedOnDetection");
            for (int i=0; i<length; i++){
                boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").getJSONObject(i).has("color");
                if (isColorExisting){
                    Assert.assertTrue(true,"color is present in the response of SIGNATURE_RELEASED_ON_DETECTION");
                    String color = jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").getJSONObject(i).getString("color");
//                    Assert.assertNotNull(color);
                    if (color!=null){
                        Assert.assertTrue(true,"Color is not getting null in response");
                    }else {
                        Assert.fail("Colour is getting null in response");
                    }
                }else {
                    Assert.fail("color is not present in the response of SIGNATURE_RELEASED_ON_DETECTION");
                }
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testNames(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for SignatureReleasedOnDetection");
            for (int i=0; i<length; i++){
                boolean isNameExisting=jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").getJSONObject(i).has("name");
                if (isNameExisting){
                    Assert.assertTrue(true,"name is present in the response of SIGNATURE_RELEASED_ON_DETECTION");
                    String name = jsonObject.getJSONObject("data").getJSONObject("SIGNATURE_RELEASED_ON_DETECTION").getJSONArray("donutChartResponses").getJSONObject(i).getString("name");
//                    Assert.assertNotNull(name);
                    if (name!=null){
                        Assert.assertTrue(true,"name is not getting null in response");
                    }else {
                        Assert.fail("Name is getting null in response");
                    }
                }else {
                    Assert.fail("name is not present in the response of SIGNATURE_RELEASED_ON_DETECTION");
                }
            }
        }else {
            Assert.fail("Length of Graph response for SIGNATURE_RELEASED_ON_DETECTION is 0");
        }
    }
}
