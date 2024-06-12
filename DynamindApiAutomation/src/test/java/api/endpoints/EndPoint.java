package api.endpoints;

import api.payload.Country;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import practiceApiAuto.endpoints.Routes;
import practiceApiAuto.payload.ChartNames;
import practiceApiAuto.payload.LoginCreds;
import practiceApiAuto.utilities.AuthorizationContext;

import static io.restassured.RestAssured.given;

public class EndPoint {
    static String authorizationToken;
    public static Response addCountry (Country payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Route.add_country_url);
        return response;
    }
    public static Response getCountry(int id){
        Response response=given()
                .queryParam("id", id)
                .when()
                .get(Route.get_country_url);
        return response;
    }
}
