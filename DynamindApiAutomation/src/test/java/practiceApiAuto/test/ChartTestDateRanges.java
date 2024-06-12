/*
package practiceApiAuto.test;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.ChartNames;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChartTestDateRanges extends Base{
    ChartNames chartNames=new ChartNames();
    Response response;
    LocalDate currentDate=LocalDate.now();
    LocalDate sevenDaysBackDate=currentDate.minusDays(8);

    @Test
    public void chartTests(){
        chartNames.chartNamesList().add("FILE_STATS");
        response=EndPoints.getChart(chartNames);
    }

    @Test
    public void testDates(){
        JSONObject jsonObject=new JSONObject(response.asString());
        int length=jsonObject.getJSONObject("data").getJSONObject("FILE_STATS").getJSONArray("graphsResponses").length();
        Assert.assertTrue(length>0,"Getting graph responses for FileStats");

        for (int i=0; i<length; i++){
            boolean isDatExisting=jsonObject.getJSONObject("data").getJSONObject("FILE_STATS").getJSONArray("graphsResponses").getJSONObject(i).has("date");
            Assert.assertTrue(isDatExisting,"Date is present in the response of FILE_STATS");
            String dateValue = jsonObject.getJSONObject("data").getJSONObject("FILE_STATS").getJSONArray("graphsResponses").getJSONObject(i).getString("date");
            Assert.assertNotNull(dateValue);

            LocalDate date=LocalDate.parse(dateValue, DateTimeFormatter.ISO_DATE);
            System.out.println(currentDate);
            System.out.println(date);
            System.out.println(sevenDaysBackDate);
            Assert.assertTrue(date.isAfter(sevenDaysBackDate) && date.isBefore(currentDate), "Date is not within the specified range");
        }
    }
}
*/
