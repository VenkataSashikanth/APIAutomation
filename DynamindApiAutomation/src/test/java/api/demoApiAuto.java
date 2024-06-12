package api;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class demoApiAuto {

    int id;
    @Test(priority = 1)
    void getCountries(){
        given()
                .when()
                .get("http://localhost:1234/api/v1/get-countries")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test(priority = 2)
    void addCountry(){

        HashMap map=new HashMap();
        map.put("countryName","Sashi");
        map.put("countryCapital","QA");


        id=given()
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .post("http://localhost:1234/api/v1/add-country")
                .jsonPath().getInt("id");
        System.out.println(id);
    }

    @Test(priority = 3)
    void getCountry(){
        System.out.println(id);
        given()
                .queryParam("id",id)
                .when()
                .get("http://localhost:1234/api/v1/get-country-id")
                .then()
                .statusCode(200)
                .log().body();
        System.out.println(id);
    }

    @Test(priority = 4, dependsOnMethods = "addCountry")
    void updateCountry(){
        System.out.println(id);
        HashMap map=new HashMap();
        map.put("countryName","Sashikanth");
        map.put("countryCapital","QA Analyst");
        given()
                .contentType("application/json")
                .body(map)
                .when()
                .put("http://localhost:1234/api/v1/update-country/"+id)
                .then()
                .statusCode(200)
                .log().body();
        System.out.println(id);
    }
}
