package practiceApiAuto.test.createUser;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.LoginCreds;
import practiceApiAuto.payload.UserPayload;
import practiceApiAuto.test.Base;
import practiceApiAuto.utilities.AuthorizationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CreateUserTest extends Base {
    String userAuthToken;
    int appUserId;
    String email;
    Faker faker;
    UserPayload userPayload;
    LoginCreds loginCreds;

    @Test(priority = 1)
    public void testCreateUserEmptyMail(){
        faker=new Faker();
        userPayload=new UserPayload();
        userPayload.setName(faker.name().firstName());
        Response response= EndPoints.createUser(userPayload);
        response.then().time(lessThan(100L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
    }

    @Test(priority = 2)
    public void testCreateUserEmptyName(){
        faker=new Faker();
        userPayload=new UserPayload();
        String name=faker.name().firstName();
        userPayload.setEmail(name.toLowerCase()+"@gmail.com");
        Response response= EndPoints.createUser(userPayload);
        response.then().time(lessThan(100L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
    }

    @Test(priority = 3)
    public void testCreateUserInvalidMail(){
        faker=new Faker();
        userPayload=new UserPayload();
        userPayload.setName(faker.name().firstName());
        userPayload.setEmail("userapi");
        Response response= EndPoints.createUser(userPayload);
        response.then().time(lessThan(100L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.invalidEmailPatternMsg());
    }

    @Test(priority = 4)
    public void testCreateUserExistingMail(){
        faker=new Faker();
        userPayload=new UserPayload();
        userPayload.setName(faker.name().firstName());
        userPayload.setEmail(readConfig.existingUserEmail());
        Response response= EndPoints.createUser(userPayload);
        response.then().time(lessThan(100L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.duplicateEmailMsg());
    }

    @Test(priority = 5)
    public void testCreateUserValidMailWithSpaces(){
        faker=new Faker();
        userPayload=new UserPayload();
        userPayload.setName(faker.name().firstName());
        userPayload.setEmail(readConfig.invalidUserEmail());
        Response response=EndPoints.createUser(userPayload);
        response.then().time(lessThan(100L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.invalidEmailPatternMsg());
    }

    @Test(priority = 6)
    public void testCreateUser(){
        faker=new Faker();
        userPayload=new UserPayload();
        userPayload.setName(faker.name().firstName());
        userPayload.setEmail(userPayload.getName().toLowerCase()+"@gmail.com");
        Response response= EndPoints.createUser(userPayload);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("Created User SUCCESSFULLY");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in CreateUserTestClass: Unable to create user with provided details");
        }
        appUserId=response.jsonPath().getInt("data.id");
        email=response.jsonPath().getString("data.email");
        UserPayload.setAppUserId(appUserId);
        System.out.println(appUserId);
        response.then().time(lessThan(500L));
        response.then().statusCode(200);
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().getString("data.name"));
        Assert.assertNotNull(response.jsonPath().getString("data.email"));
        Assert.assertNotNull(response.jsonPath().getString("data.roleType"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.createUserSuccessMsg());
        Assert.assertEquals(response.jsonPath().getString("data.roleType"),"ADMIN");
        Assert.assertEquals(response.jsonPath().getString("data.email"), userPayload.getEmail());
        testGetUserWithoutId();
    }

    @Test(priority = 7)
    public void testGetUserWithoutId(){
        int id = 0;
        Response response=EndPoints.getUserById(id);
        response.then().time(lessThan(1000L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.provideIdMsg());
        System.out.println("Assertion pass: Cannot get user without id.....Please provide valid id");
    }

    @Test(priority = 8,dependsOnMethods = "testCreateUser")
    public void testGetUserById(){
        Response response=EndPoints.getUserById(appUserId);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("User Found Successfully.....");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in CreateUserTestClass: Unable to get the userById in CreateUserTestClass");
        }
        response.then().time(lessThan(100L));
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.userFoundMsg());
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().getString("data.name"));
        Assert.assertNotNull(response.jsonPath().getString("data.email"));
        Assert.assertEquals(response.jsonPath().getString("data.roleType"),"ADMIN");
        Assert.assertEquals(response.jsonPath().getString("data.email"), userPayload.getEmail());
    }

    @Test(priority = 9,dependsOnMethods = "testCreateUser")
    public void testEditUser(){
        faker=new Faker();
        userPayload.setName(faker.name().firstName());
        userPayload.setId(UserPayload.getAppUserId());
        Response response=EndPoints.createUser(userPayload);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("User Details Updated SUCCESSFULLY");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in CreateUserTestClass: Unable to update user with provided details");
        }
        response.then().time(lessThan(100L));
        response.then().statusCode(200);
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().getString("data.name"));
        Assert.assertNotNull(response.jsonPath().getString("data.email"));
        Assert.assertNotNull(response.jsonPath().getString("data.roleType"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.userUpdateSuccessMsg());
        Assert.assertEquals(response.jsonPath().getString("data.roleType"),"ADMIN");
        Assert.assertEquals(response.jsonPath().getString("data.email"), userPayload.getEmail());
        Assert.assertEquals(response.jsonPath().getInt("data.id"), appUserId);
    }

    @Test(priority = 10)
    public void testGetNonExistingUserWithId(){
        if (appUserId!=0){
            appUserId=appUserId+2;
        }else {
            appUserId=200000;
        }
        Response response=EndPoints.getUserById(appUserId);
        response.then().time(lessThan(100L));
//        response.then().statusCode(400);
        response.then().body("status", equalTo("NOT_FOUND"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.userNotFoundMsg());
        System.out.println("Assertion pass: Cannot get user with id provided....."+appUserId);
    }

    @Test(priority = 11)
    public void testUserLogin(){
        loginCreds=new LoginCreds();
        loginCreds.setEmail(readConfig.existingUserEmail());
        loginCreds.setPassword(readConfig.getCmdcPassword());
        Response response=EndPoints.adminLogin(loginCreds);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("User logged in SUCCESSFULLY");
        }else {
            response.then().log().body();
            System.out.println("Test Failed in CreateUserTestClass: Unable to login with user details");
        }
        userAuthToken=response.jsonPath().getString("data.auth_token");
        response.then().time(lessThan(100L));
        response.then().statusCode(200);
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().getString("data.name"));
        Assert.assertNotNull(response.jsonPath().getString("data.email"));
        Assert.assertEquals(response.jsonPath().getString("data.email"),loginCreds.getEmail());
        Assert.assertNotNull(response.jsonPath().getString("data.roleType"));
        Assert.assertEquals(response.jsonPath().getString("data.roleType"),"ADMIN");
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.loginSuccessMsg());
    }

    @Test(priority = 12,dependsOnMethods = "testUserLogin")
    public void testUserShouldNotCreateUser(){
        String oldAuthorization = AuthorizationContext.getAuthorizationToken();
        AuthorizationContext.setAuthorizationToken(userAuthToken);
        userPayload.setName(faker.name().firstName());
        userPayload.setEmail(userPayload.getName().toLowerCase()+"@gmail.com");
        Response response=EndPoints.createUser(userPayload);
        response.then().log().body();
        AuthorizationContext.setAuthorizationToken(oldAuthorization);
        response.then().time(lessThan(100L));
        response.then().statusCode(400);
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.accessDeniedMsg());
        System.out.println("Assertion Pass: Access Denied For User Creation with Admin login. Only Super-Admin can create users.....");
    }
}
