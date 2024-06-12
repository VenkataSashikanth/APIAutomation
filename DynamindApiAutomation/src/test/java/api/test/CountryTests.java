package api.test;

import api.endpoints.EndPoint;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api.payload.Country;

public class CountryTests {

    int id;
    String countryName;
    String countryCapital;
    Faker faker;
    public Logger logger;
    Country countryPayload;
    @BeforeClass
    public void setupData(){
        faker = new Faker();
        countryPayload=new Country();

//        countryPayload.setId(faker.idNumber().hashCode());
        countryPayload.setCountryName(faker.country().name());
        countryPayload.setCountryCapital(faker.country().capital());

        logger= LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testAddCountry(){
        logger.info("******* Adding Country *******");
        Response response= EndPoint.addCountry(countryPayload);
        logger.info("***** Country Addedd Successfully *****");
        response.then().log().body();
        id=response.jsonPath().getInt("id");
        System.out.println(id);
        countryName=response.jsonPath().getString("countryName");
        System.out.println(countryName);
        countryCapital=response.jsonPath().getString("countryCapital");
        System.out.println(countryCapital);
        /*JSONObject jsonObject=new JSONObject(response.asString());
        int id=jsonObject.getJSONObject().get("id");*/
    }

    @Test(priority = 2, dependsOnMethods = "testAddCountry")
    public void testGetCountry(){
        Response response=EndPoint.getCountry(id);
        response.then().log().body();
        Assert.assertEquals(response.jsonPath().getString("countryName"),countryName);
        Assert.assertEquals(response.jsonPath().getString("countryCapital"),countryCapital);
    }
}
