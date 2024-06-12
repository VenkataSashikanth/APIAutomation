package practiceApiAuto.endpoints;

public class Routes {

//    #BaseURl
//    public static String base_url="http://localhost:8089/api/v1";
    public static String base_url="http://175.101.24.203:8089/api/v1";
//    public static String base_url="http://172.18.197.12";    //Client environment: Dev



//    #TestingURLs: Endpoints
//    #AppUser
    public static String login_url=base_url+"/user/login";
    public static String create_user_url=base_url+"/user/create";
    public static String create_superAdmin_url=base_url+"/user/create-super-admin";
    public static String get_user_details_url=base_url+"/user/get-user-details";
    public static String get_userById_url=base_url+"/user/get/{appUserId}";
    public static String change_user_status_url=base_url+"/user/change-user-status/{appUserId}";


//    #Charts
    public static String get_charts_url=base_url+"/charts/get-charts";
//    public static String get_charts_url=base_url+"/charts/get-charts-with-type";
    public static String get_chart_settings_url=base_url+"/charts/get-charts-for-settings";
    public static String change_chart_status_url=base_url+"/charts/change-chart-status";
    public static String get_all_chart_enums_url=base_url+"/charts/get-all-chart-enums";


//    #Dashboard
    public static String create_dashboard_url=base_url+"/dashboard/create-dashboard";
    public static String get_dashboard_byId_url=base_url+"/dashboard/get-dashboard-by-id/{id}";
    public static String delete_dashboard_url=base_url+"/dashboard/delete-dashboard/{id}";
    public static String get_all_dashboards=base_url+"/dashboard/get-dashboard";
    public static String get_dashboard_charts=base_url+"/dashboard/get-dashboard-charts";
    public static String delete_dashboard_chart_url=base_url+"/dashboard/delete-dashboard-chart/{id}";
}
