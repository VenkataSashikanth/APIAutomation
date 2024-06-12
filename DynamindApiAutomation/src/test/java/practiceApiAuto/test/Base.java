package practiceApiAuto.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import practiceApiAuto.test.login.LoginTest;
import practiceApiAuto.utilities.AuthorizationContext;
import practiceApiAuto.utilities.ReadConfig;


public class Base{
    public ReadConfig readConfig=new ReadConfig();
    public Logger logger;
    LoginTest loginTest;

    @BeforeSuite
    public void setUp(){
        logger= LogManager.getLogger(this.getClass());
        logger.info("*****Login Test Started*****");
        loginTest=new LoginTest();
        loginTest.testLogin();
        logger.info("*****Login Test Completed*****");
        logger.info(AuthorizationContext.getAuthorizationToken());
    }
}
