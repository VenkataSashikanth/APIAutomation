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

public class ChartTest_OsWiseEndpointsProtected extends Base {
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
        chartNames.chartNamesList().add("OS_WISE_ENDPOINTS_PROTECTED");
        List<String> charts = chartNames.getChartNames();
        for (String chartName : charts) {
            if (chartName.equals("OS_WISE_ENDPOINTS_PROTECTED"))
                testOsWiseEndpointsProtected();
            else {
                System.out.println("Please provide correct chart names");
            }
        }
    }

    public void testOsWiseEndpointsProtected() {
        response=EndPoints.getChart(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("OS_WISE_ENDPOINTS_PROTECTED Chart Response Found Successfully");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().assertThat().time(lessThan(200L));
        response.then().body("message", equalTo(readConfig.chartsFoundMsg()));

        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for OsWiseEndPointsProtected");
            boolean isOsWiseEndpointsProtectedExisting = jsonObject.getJSONObject("data").has("OS_WISE_ENDPOINTS_PROTECTED");
            if (isOsWiseEndpointsProtectedExisting) {
                Assert.assertTrue(true,"OS_WISE_ENDPOINTS_PROTECTED field is present in response");
                boolean isGraphsResponsesExisting = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").has("cardResponses");
                if (isGraphsResponsesExisting){
                    Assert.assertTrue(true,"cardResponses field is present in response of OS_WISE_ENDPOINTS_PROTECTED");
                }else {
                    Assert.fail("cardResponses field is not present in response of OS_WISE_ENDPOINTS_PROTECTED");
                }
            }else {
                Assert.fail("OS_WISE_ENDPOINTS_PROTECTED field is not present in response");
            }
        }else {
            Assert.fail("Length of Graph response for OS_WISE_ENDPOINTS_PROTECTED is 0");
        }
    }

    @Test(priority = 4,dependsOnMethods = "chartTests")
    public void testValueOfLatestCount(){
        JSONObject jsonObject=new JSONObject(response.asString());
        length=jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for OsWiseEndPointsProtected");
            for (int i=0; i<length; i++){
                boolean isValueOfLatestCountExisting=jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).has("valueOfLatestCount");
                if (isValueOfLatestCountExisting){
                    Assert.assertTrue(true,"latestCount is present in the response of OS_WISE_ENDPOINTS_PROTECTED");
                    Long latestCount = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).getLong("valueOfLatestCount");
                    Assert.assertNotNull(latestCount);
                }else {
                    Assert.fail("latestCount is not present in the response of OS_WISE_ENDPOINTS_PROTECTED");
                }
            }
        }else {
            Assert.fail("Length of Graph response for OS_WISE_ENDPOINTS_PROTECTED is 0");
        }
    }

    @Test(priority = 5,dependsOnMethods = "chartTests")
    public void testValueOfPreviousCount() {
        JSONObject jsonObject = new JSONObject(response.asString());
        length = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for OsWiseEndPointsProtected");
            for (int i = 0; i < length; i++) {
                boolean isValueOfPreviousCountExisting = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).has("valueOfPreviousCount");
                if (isValueOfPreviousCountExisting){
                    Assert.assertTrue(true, "previousCount is present in the response of OsWiseEndPointsProtected");
                    Long previousCount = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).getLong("valueOfPreviousCount");
                    Assert.assertNotNull(previousCount);
                }else {
                    Assert.fail("previousCount is not present in the response of OsWiseEndPointsProtected");
                }
            }
        }else {
            Assert.fail("Length of Graph response for OS_WISE_ENDPOINTS_PROTECTED is 0");
        }
    }

    @Test(priority = 6,dependsOnMethods = "chartTests")
    public void testColor() {
        JSONObject jsonObject = new JSONObject(response.asString());
        length = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for OsWiseEndPointsProtected");
            for (int i = 0; i < length; i++) {
                boolean isColorExisting=jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).has("color");
                if (isColorExisting){
                    Assert.assertTrue(true,"color is present in the response of OS_WISE_ENDPOINTS_PROTECTED");
                    String color = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).getString("color");
//                    Assert.assertNotNull(color);
                    if (color!=null){
                        Assert.assertTrue(true,"Colour is not getting null in response");
                    }else {
                        Assert.fail("Color is getting null in response");
                    }
                }else {
                    Assert.fail("color is not present in the response of OS_WISE_ENDPOINTS_PROTECTED");
                }
            }
        }else {
            Assert.fail("Length of Graph response for OS_WISE_ENDPOINTS_PROTECTED is 0");
        }
    }

    @Test(priority = 7,dependsOnMethods = "chartTests")
    public void testNames() {
        JSONObject jsonObject = new JSONObject(response.asString());
        length = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").length();
        if (length>0){
            Assert.assertTrue(true,"Getting graph responses for OsWiseEndPointsProtected");
            for (int i = 0; i < length; i++) {
                boolean isNameExisting=jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).has("name");
                if (isNameExisting){
                    Assert.assertTrue(true,"name is present in the response of OS_WISE_ENDPOINTS_PROTECTED");
                    String name = jsonObject.getJSONObject("data").getJSONObject("OS_WISE_ENDPOINTS_PROTECTED").getJSONArray("cardResponses").getJSONObject(i).getString("name");
//                    Assert.assertNotNull(name);
                    if (name!=null){
                        Assert.assertTrue(true,"name is not getting null in response");
                    }else {
                        Assert.fail("Name is getting null in response");
                    }
                }else {
                    Assert.fail("name is not present in the response of OS_WISE_ENDPOINTS_PROTECTED");
                }
            }
        }else {
            Assert.fail("Length of Graph response for OS_WISE_ENDPOINTS_PROTECTED is 0");
        }
    }
}
