package practiceApiAuto.test.login;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.LoginCreds;
import practiceApiAuto.utilities.AuthorizationContext;
import practiceApiAuto.utilities.ReadConfig;

import static org.hamcrest.Matchers.*;

public class LoginTest {
    ReadConfig readConfig=new ReadConfig();
    String authorizationToken;
    LoginCreds loginCreds;

    @Test(priority = 1)
    public void testLoginEmptyCredentials(){
        loginCreds=new LoginCreds();
        loginCreds.setEmail("");
        loginCreds.setPassword("");
        Response response= EndPoints.adminLogin(loginCreds);
        response.then().statusCode(400);
        response.then().time(lessThan(500L));
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
//        Assert.assertEquals(response.jsonPath().getString("message"),"null");
        Assert.assertNotEquals(response.jsonPath().getString("data.email"),loginCreds.getEmail());
    }

    @Test(priority = 2)
    public void testLoginEmptyPassword(){
        loginCreds=new LoginCreds();
        loginCreds.setEmail(readConfig.getEmail());
        loginCreds.setPassword("");
        Response response= EndPoints.adminLogin(loginCreds);
        response.then().time(lessThan(200L));
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.emptyPasswordMsg());
        Assert.assertNotEquals(response.jsonPath().getString("message"),readConfig.loginSuccessMsg());
    }

    @Test(priority = 3)
    public void testLoginInvalidCredentials(){
        loginCreds=new LoginCreds();
        loginCreds.setEmail(readConfig.getEmail());
        loginCreds.setPassword(readConfig.getIncorrectPassword());
        Response response= EndPoints.adminLogin(loginCreds);
        response.then().time(lessThan(100L));
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.incorrectPasswordMsg());
        Assert.assertNotEquals(response.jsonPath().getString("data.email"),loginCreds.getEmail());
        Assert.assertNotEquals(response.jsonPath().getString("message"),readConfig.loginSuccessMsg());
    }

    @Test(priority = 4)
    public void testLoginWIthInvalidEmail(){
        loginCreds=new LoginCreds();
        loginCreds.setEmail(readConfig.getInvalidEmail());
        loginCreds.setPassword(readConfig.getPassword());
        Response response= EndPoints.adminLogin(loginCreds);
        response.then().time(lessThan(500L));
        response.then().body("status", equalTo("BAD_REQUEST"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.invalidEmailPatternMsg());
        Assert.assertNotEquals(response.jsonPath().getString("data.email"),loginCreds.getEmail());
        Assert.assertNotEquals(response.jsonPath().getString("message"),readConfig.loginSuccessMsg());
    }

    @Test(priority = 5)
    public void testLoginWithIncorrectEmail(){
        loginCreds=new LoginCreds();
        loginCreds.setEmail(readConfig.getIncorrectEmail());
        loginCreds.setPassword(readConfig.getPassword());
        Response response= EndPoints.adminLogin(loginCreds);
        response.then().time(lessThan(500L));
        response.then().body("status", equalTo("NOT_FOUND"));
        Assert.assertNull(response.jsonPath().get("data"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.userNotFoundMsg());
        Assert.assertNotEquals(response.jsonPath().getString("data.email"),loginCreds.getEmail());
        Assert.assertNotEquals(response.jsonPath().getString("message"),readConfig.loginSuccessMsg());
    }

    @Test(priority = 6)
    public void testLogin(){
        loginCreds=new LoginCreds();
        loginCreds.setEmail(readConfig.getEmail());
        loginCreds.setPassword(readConfig.getPassword());
        Response response= EndPoints.adminLogin(loginCreds);
        if(response.jsonPath().getInt("statusCode")==200 && response.jsonPath().getString("status").equals("OK")){
            System.out.println("USER LOGIN SUCCESSFULLY with Status OK and statusCode: 200");
        }else {
            response.then().log().body();
        }
        response.then().statusCode(200);
        response.then().time(lessThan(500L));
        response.then().body("data",notNullValue());
        Assert.assertNotNull(response.jsonPath().get("data"));
        Assert.assertNotNull(response.jsonPath().getString("data.auth_token"));
        Assert.assertEquals(response.jsonPath().getString("message"),readConfig.loginSuccessMsg());
        Assert.assertEquals(response.jsonPath().getString("data.roleType"),"SUPER_ADMIN");
        Assert.assertEquals(response.jsonPath().getString("data.email"), loginCreds.getEmail());
        authorizationToken=response.jsonPath().getString("data.auth_token");
        AuthorizationContext.setAuthorizationToken(authorizationToken);
    }
}

