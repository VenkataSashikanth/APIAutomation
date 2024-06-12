package api.endpoints;

public class Route {
//    public static String base_url="http://localhost:8089/api/v1";

    public static String base_url="http://localhost:1234/api/v1";

    public static String add_country_url=base_url+"/add-country";
    public static String get_country_url=base_url+"/get-country-id";
    public static String update_country_url=base_url+"/update-country/{id}";
    public static String delete_country_url=base_url+"/delete-country/{id}";


    public static String get_charts_url=base_url+"/charts/get-charts";

    public static String login_url=base_url+"/user/login";
}
