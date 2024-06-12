package api;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.json.JSONObject;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class AddCountry {
    @Test(priority = 1)
    void addCountry(){
        Faker faker=new Faker();
        JSONObject data=new JSONObject();

        data.put("countryName",faker.country().name());
        data.put("countryCapital",faker.country().capital());

        given()
                .contentType(ContentType.JSON)
                .body(data.toString())
                .when()
                .post("http://localhost:1234/api/v1/add-country")
                .then()
                .statusCode(201)
                .log().body();

        /*JSONObject jsonObject=new JSONObject(response.toString());

        boolean isCountryExist=false;
        for (int i=0; i<=jsonObject.getJSONArray("countriesList").length(); i++) {
            String countryName=jsonObject.getJSONArray("countriesList").getJSONObject(i).get("countryName").toString();
            if (countryName.equalsIgnoreCase("india")){
                isCountryExist = true;
                break;
            }
        }
        Assert.assertEquals(isCountryExist,true);*/
    }

    @Test(priority = 2)
    void getCountries(){
        Response res=given()
                .when()
                .get("http://localhost:1234/api/v1/get-countries");
        System.out.println(res.asString());

        JSONObject jsonObject=new JSONObject(res.asString());

        boolean isCountryExist=false;
        for (int i=0; i<=jsonObject.getJSONArray("countriesList").length(); i++) {
            String countryName=jsonObject.getJSONArray("countriesList").getJSONObject(i).get("countryName").toString();
            if (countryName.equalsIgnoreCase("india")){
                isCountryExist = true;
                break;
            }
        }
        Assert.assertEquals(isCountryExist,true);
    }
}
