package api.test;

import api.endpoints.EndPoint;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import api.payload.Country;
import practiceApiAuto.utilities.DataProviders;

public class DDTests {
    int id;
    Country countryPayload;

    @Test(dataProvider = "countryData", dataProviderClass = DataProviders.class)
    public void testAddCountry(String countryName, String countryCapital){
        countryPayload=new Country();

        countryPayload.setCountryName(countryName);
        countryPayload.setCountryCapital(countryCapital);

        Response response= EndPoint.addCountry(countryPayload);
        response.then().log().body();
//        String countryNameResponse=response.jsonPath().getString("countryCapital");
        id=response.jsonPath().getInt("id");
    }

    @Test
    public void testGetCountry(){
        Response response= EndPoint.getCountry(id);
        response.then().log().body();
        if (response.jsonPath().getInt("id")==id){
            System.out.println("Data created successfully");
        }
    }
}
