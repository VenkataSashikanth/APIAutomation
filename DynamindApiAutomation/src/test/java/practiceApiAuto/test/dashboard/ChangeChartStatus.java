package practiceApiAuto.test.dashboard;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.DashboardCharts;
import practiceApiAuto.test.Base;

import java.util.ArrayList;
import java.util.List;

public class ChangeChartStatus extends Base {

    @Test
    public void testChangeChartStatus(){
        DashboardCharts chart1=new DashboardCharts(1, true);
        DashboardCharts chart2=new DashboardCharts(3, false);
        List<DashboardCharts> dashboardCharts=new ArrayList<>();
        dashboardCharts.add(chart1);
        dashboardCharts.add(chart2);
        System.out.println(dashboardCharts);
        Response response= EndPoints.changeChartStatus(dashboardCharts);
        response.then().log().body();
    }
}
