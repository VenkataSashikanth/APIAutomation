package practiceApiAuto.test.dashboard;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import practiceApiAuto.endpoints.EndPoints;
import practiceApiAuto.payload.CreateDashboard;
import practiceApiAuto.payload.DashboardCharts;
import practiceApiAuto.test.Base;

import java.util.ArrayList;
import java.util.List;

public class CreateDashboardTest extends Base {
    DashboardCharts dashboardCharts;
    CreateDashboard createDashboard;

    @Test
    public void testCreateDashboard(){
        DashboardCharts dashboardCharts1=new DashboardCharts(1,1,"FILE_STATS","File Stats","stacked-bar",true);
        DashboardCharts dashboardCharts2=new DashboardCharts(1,1,"URL_STATS","URL Stats","stacked-bar",true);

        createDashboard=new CreateDashboard();
        createDashboard.setDefaultDashboard(false);
        createDashboard.setName("Rest Dashboard New");
        /*createDashboard.dashboardChartNames().add(dashboardCharts1);
        createDashboard.dashboardChartNames().add(dashboardCharts2);*/

        List<DashboardCharts> dashboardCharts=new ArrayList<>();
        dashboardCharts.add(dashboardCharts1);
        dashboardCharts.add(dashboardCharts2);

        createDashboard.setCharts(dashboardCharts);

        Response response=EndPoints.createDashboard(createDashboard);
        response.then().log().body();
    }
}
