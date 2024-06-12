package practiceApiAuto.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import practiceApiAuto.payload.*;
import practiceApiAuto.utilities.AuthorizationContext;

import java.util.List;

import static io.restassured.RestAssured.*;

public class EndPoints {
    static String authorizationToken;

    public static Response adminLogin(LoginCreds payload){
        Response response=given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.login_url);
        return response;
    }

    public static Response createUser(UserPayload userPayload){
        authorizationToken = AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .body(userPayload)
                .when()
                .post(Routes.create_user_url);
        return response;
    }

    public static Response getUserById(int appUserId){
        authorizationToken=AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .pathParam("appUserId", appUserId)
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .when()
                .get(Routes.get_userById_url);
        return response;
    }

    public static Response getUserDetails(ChartNames chartNames){
        authorizationToken=AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .body(chartNames)
                .when()
                .post(Routes.get_user_details_url);
        return response;
    }

    public static Response changeUserStatus(int appUserId){
        authorizationToken=AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .pathParam("appUserId",appUserId)
                .when()
                .get(Routes.change_user_status_url);
        return response;
    }

    public static Response getAllChartEnums(){
        authorizationToken=AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .when()
                .get(Routes.get_all_chart_enums_url);
        return response;
    }

    public static Response getChart(ChartNames chartNames){
        authorizationToken = AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization", "Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .body(chartNames)
                .when()
                .post(Routes.get_charts_url);
        return  response;
    }

    public static Response chartSettings(){
        authorizationToken=AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .when()
                .get(Routes.get_chart_settings_url);
        return response;
    }

    public static Response changeChartStatus(List<DashboardCharts> dashboardCharts){
        authorizationToken=AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .body(dashboardCharts)
                .when()
                .post(Routes.change_chart_status_url);
        return response;
    }

    public static Response createDashboard(CreateDashboard createDashboard){
        authorizationToken=AuthorizationContext.getAuthorizationToken();
        Response response=given()
                .header("Authorization","Bearer "+authorizationToken)
                .contentType(ContentType.JSON)
                .body(createDashboard)
                .when()
                .post(Routes.create_dashboard_url);
        return response;
    }
}
