package practiceApiAuto.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
    Properties properties;

    public ReadConfig(){
        File src=new File("./Configuration/config.properties");
        try{
            FileInputStream fis=new FileInputStream(src);
            properties=new Properties();
            properties.load(fis);
        }
        catch (Exception e){
            System.out.println("Exception is "+ e.getMessage());
        }
    }

    public String getEmail(){
        return properties.getProperty("EMAIL");
    }

    public String getPassword(){
        return properties.getProperty("PASSWORD");
    }

    public String getInvalidEmail(){
        return properties.getProperty("INVALID_EMAIL");
    }

    public String getIncorrectEmail(){
        return properties.getProperty("INCORRECT_EMAIL");
    }

    public String getIncorrectPassword(){
        return properties.getProperty("INCORRECT_PASS");
    }

    public String getCmdcPassword(){
        return properties.getProperty("cmdc_Password");
    }

    public String emptyPasswordMsg(){
        return properties.getProperty("EMPTY_PASSWORD");
    }
    public String loginSuccessMsg(){
        return properties.getProperty("LOGIN_SUCCESS");
    }

    public String chartsFoundMsg(){
        return properties.getProperty("CHARTS_FOUND");
    }

    public String chartsNotFoundMsg(){
        return properties.getProperty("CHARTS_NOT_FOUND");
    }

    public String provideChartsMsg(){
        return properties.getProperty("PROVIDE_CHART_NAMES");
    }

    public String incorrectPasswordMsg(){
        return properties.getProperty("INCORRECT_PASSWORD");
    }

    public String invalidEmailPatternMsg(){
        return properties.getProperty("INVALID_EMAIL_PATTERN");
    }

    public String userFoundMsg(){
        return properties.getProperty("USER_FOUND");
    }

    public String userNotFoundMsg(){
        return properties.getProperty("USER_ID_NOT_FOUND");
    }

    public String existingUserEmail(){
        return properties.getProperty("EXISTING_USER_EMAIL");
    }

    public String duplicateEmailMsg(){
        return properties.getProperty("DUPLICATE_EMAIL");
    }

    public String invalidUserEmail(){
        return properties.getProperty("INVALID_USER_EMAIL");
    }

    public String createUserSuccessMsg(){
        return properties.getProperty("CREATE_SUCCESS");
    }

    public String userUpdateSuccessMsg(){
        return properties.getProperty("USER_UPDATE_SUCCESS");
    }

    public String provideIdMsg(){
        return properties.getProperty("PROVIDE_ID");
    }

    public String accessDeniedMsg(){
        return properties.getProperty("ACCESS_DENIED");
    }

    public String adminsFoundSuccessMsg(){
        return properties.getProperty("ADMIN_FOUND_SUCCESS");
    }

    public String fileStats(){
        return properties.getProperty("FileStats");
    }

    public String urlStats(){
        return properties.getProperty("URLStats");
    }

    public String topTrendingThreats(){
        return properties.getProperty("TopTrendingThreats");
    }

    public String top10DetectedThreats(){
        return properties.getProperty("Top10DetectedThreats");
    }

    public String Endpoints(){
        return properties.getProperty("Endpoints");
    }

    public String osWiseEndpointsProtected(){
        return properties.getProperty("OsWiseEndpointsProtected");
    }

    public String filesByClassification(){
        return properties.getProperty("FilesByClassification");
    }

    public String arwDetection(){
        return properties.getProperty("ArwDetection");
    }

    public String urlBySource(){
        return properties.getProperty("UrlBySource");
    }

    public String monthlyReleaseUpdate(){
        return properties.getProperty("MonthlyReleaseUpdate");
    }

    public String darkNetFeed(){
        return properties.getProperty("DarkNetFeed");
    }

    public String threatMap(){
        return properties.getProperty("ThreatMap");
    }
}
