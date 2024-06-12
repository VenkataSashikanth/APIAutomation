package practiceApiAuto.test.createUser;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.ChartNames;
import practiceApiAuto.payload.UserPayload;
import practiceApiAuto.test.Base;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class TestGetUser extends Base {
    int appUserId;
    ChartNames chartNames;
    @Test(priority = 1)
    public void getUserTest(){
        System.out.println(UserPayload.getAppUserId());
        if (UserPayload.getAppUserId()!=0){
            appUserId=UserPayload.getAppUserId();
        }else {
            appUserId=2;
        }
        System.out.println(appUserId);
        Response response=EndPoints.getUserById(appUserId);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("User Found Successfully.....");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in TestGetUserClass: Unable to find the user with provided id");
        }
        response.then().time(lessThan(100L));
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.userFoundMsg());
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().getString("data.name"));
        Assert.assertNotNull(response.jsonPath().getString("data.email"));
        Assert.assertEquals(response.jsonPath().getString("data.roleType"),"ADMIN");
    }

    @Test(priority = 2)
    public void testGetUserWithoutId(){
        int id = 0;
        Response response=EndPoints.getUserById(id);
        response.then().time(lessThan(100L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.provideIdMsg());
        System.out.println("Assertion pass: Cannot get user without id.....");
    }

    @Test(priority = 3)
    public void testGetNonExistingUserWithId(){
        if (UserPayload.getAppUserId()!=0){
            appUserId=UserPayload.getAppUserId()+5;
        }else {
            appUserId=50000;
        }
        Response response=EndPoints.getUserById(appUserId);
        System.out.println(appUserId);
        response.then().time(lessThan(100L));
//        response.then().statusCode(400);
        response.then().body("status", equalTo("NOT_FOUND"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.userNotFoundMsg());
        System.out.println("Assertion pass: User does not exist with provided id.....");
    }

    @Test(priority = 4)
    public void getUsersTest(){
        chartNames=new ChartNames();
        chartNames.setPageNumber(0);
        chartNames.setPageSize(100);
        Response response=EndPoints.getUserDetails(chartNames);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("All Admins Found Successfully....");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in TestGetUserClass: ");
        }
        response.then().time(lessThan(100L));
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.adminsFoundSuccessMsg());
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().get("data.content"));

        JSONObject jsonObject=new JSONObject(response.asString());
        int length=jsonObject.getJSONObject("data").getJSONArray("content").length();
        if (jsonObject.getJSONObject("data").getJSONArray("content")!=null){
            for (int i=0; i<length;i++){
                boolean isIdExists=jsonObject.getJSONObject("data").getJSONArray("content").getJSONObject(i).has("id");
                Assert.assertTrue(isIdExists,"Id filed exists in response");
                int id=jsonObject.getJSONObject("data").getJSONArray("content").getJSONObject(i).getInt("id");
                Assert.assertNotNull(id);

                String roleType=jsonObject.getJSONObject("data").getJSONArray("content").getJSONObject(i).getString("roleType");
                Assert.assertNotEquals(roleType,"SUPER_ADMIN");

                boolean isNameExists=jsonObject.getJSONObject("data").getJSONArray("content").getJSONObject(i).has("name");
                Assert.assertTrue(isNameExists,"Id filed exists in response");
                String name=jsonObject.getJSONObject("data").getJSONArray("content").getJSONObject(i).getString("name");
                Assert.assertNotNull(name);

                boolean isEmailExists=jsonObject.getJSONObject("data").getJSONArray("content").getJSONObject(i).has("email");
                Assert.assertTrue(isEmailExists,"Email filed exists in response");
                String email=jsonObject.getJSONObject("data").getJSONArray("content").getJSONObject(i).getString("email");
                Assert.assertNotNull(email);
            }
        }
    }
}
