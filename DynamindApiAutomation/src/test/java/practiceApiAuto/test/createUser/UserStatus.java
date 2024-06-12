package practiceApiAuto.test.createUser;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.UserPayload;
import practiceApiAuto.test.Base;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class UserStatus extends Base {
    Response response;
    int appUserId;
    @Test
    public void testChangeUserStatus(){
        if (UserPayload.getAppUserId()!=0){
            appUserId=UserPayload.getAppUserId();
        }else {
            appUserId=3;
        }
        System.out.println(appUserId);
        response= EndPoints.changeUserStatus(appUserId);
        JSONObject jsonObject=new JSONObject(response.asString());
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("Changed User status of: "+jsonObject.getJSONObject("data").getString("name")+" to: "+jsonObject.getJSONObject("data").getBoolean("active")+ " Successfully.....");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in UserStatus: Unable to find the user with provided id");
        }
        response.then().time(lessThan(100L));
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().getString("message"),"Success");
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().getString("data.name"));
        Assert.assertNotNull(response.jsonPath().getString("data.email"));
        Assert.assertEquals(response.jsonPath().getString("data.roleType"),"ADMIN");
    }

    @Test(priority = 2)
    public void testChangeUserStatusWithoutId(){
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
    public void testChangeUserStatusNonExistingId(){
        if (UserPayload.getAppUserId()!=0){
            appUserId=UserPayload.getAppUserId()+5;
        }else {
            appUserId=5000000;
        }
        Response response=EndPoints.getUserById(appUserId);
        System.out.println(appUserId);
        response.then().time(lessThan(500L));
//        response.then().statusCode(400);
        response.then().body("status", equalTo("NOT_FOUND"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.userNotFoundMsg());
        System.out.println("Assertion pass: User does not exist with provided id.....");
    }
}
