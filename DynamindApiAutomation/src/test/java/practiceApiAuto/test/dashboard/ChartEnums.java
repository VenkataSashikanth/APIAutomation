package practiceApiAuto.test.dashboard;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.test.Base;

import static org.hamcrest.Matchers.lessThan;

public class ChartEnums extends Base {
    Response response;
    int chartsLength;

    @Test
    public void testChartEnums(){
        response= EndPoints.getAllChartEnums();
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("All Charts Found Successfully for ChartEnums on dashboard....");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in ChartEnums Class: Unable to fetch all charts for default dashboard");
        }
        response.then().time(lessThan(500L));
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.chartsFoundMsg());
        Assert.assertNotNull(response.jsonPath().get("data"));

        JSONObject jsonObject=new JSONObject(response.asString());
        chartsLength=jsonObject.getJSONArray("data").length();
        if (chartsLength>0){
            Assert.assertTrue(true,"Getting Charts length for dashboard");
        }else {
            Assert.fail("Unable to Get Charts length for dashboard");
        }
    }

    @Test(dependsOnMethods = "testChartEnums")
    public void testChartName(){
        JSONObject jsonObject=new JSONObject(response.asString());
        chartsLength=jsonObject.getJSONArray("data").length();
        if (chartsLength>0){
            for (int i=0; i<chartsLength; i++){
                boolean isChartNameExisting=jsonObject.getJSONArray("data").getJSONObject(i).has("chartName");
                if (isChartNameExisting){
                    Assert.assertTrue(true,"chartName field is getting in response for chartEnums");
                }else {
                    Assert.fail("chartName field is not getting in response for chartEnums");
                }
            }
        }else {
            Assert.fail("Unable to Get Charts length for dashboard");
        }
    }

    @Test(dependsOnMethods = "testChartEnums")
    public void testChartLabel(){
        JSONObject jsonObject=new JSONObject(response.asString());
        chartsLength=jsonObject.getJSONArray("data").length();
        if (chartsLength>0){
            for (int i=0; i<chartsLength; i++){
                boolean isChartNameExisting=jsonObject.getJSONArray("data").getJSONObject(i).has("chartLabel");
                if (isChartNameExisting){
                    Assert.assertTrue(true,"chartLabel field is getting in response for chartEnums");
                }else {
                    Assert.fail("chartLabel field is not getting in response for chartEnums");
                }
            }
        }else {
            Assert.fail("Unable to Get Charts length for dashboard");
        }
    }

    @Test(dependsOnMethods = "testChartEnums")
    public void testChartType(){
        JSONObject jsonObject=new JSONObject(response.asString());
        chartsLength=jsonObject.getJSONArray("data").length();
        if (chartsLength>0){
            for (int i=0; i<chartsLength; i++){
                boolean isChartNameExisting=jsonObject.getJSONArray("data").getJSONObject(i).has("chartType");
                if (isChartNameExisting){
                    Assert.assertTrue(true,"chartType field is getting in response for chartEnums");
                }else {
                    Assert.fail("chartType field is not getting in response for chartEnums");
                }
            }
        }else {
            Assert.fail("Unable to Get Charts length for dashboard");
        }
    }
}
