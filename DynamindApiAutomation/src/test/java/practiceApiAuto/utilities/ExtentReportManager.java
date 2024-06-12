package practiceApiAuto.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter extentSparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;
    String repName;


    public void onStart(ITestContext testContext){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName="QH_CC_Test_Report-"+timeStamp+".html";

        extentSparkReporter=new ExtentSparkReporter("./reports/"+repName);
        System.out.println(repName);
        extentSparkReporter.config().setDocumentTitle("RestAssured_QH_CCProject");
        extentSparkReporter.config().setReportName("QH_CC_Charts_Test");
        extentSparkReporter.config().setTheme(Theme.STANDARD);

        extentReports=new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Application","QH_CC_Charts_API");
        extentReports.setSystemInfo("OperatingSystem",System.getProperty("os.name"));
        extentReports.setSystemInfo("Username",System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment","QA");
        extentReports.setSystemInfo("user","Sashikanth");
    }

    public void onTestSuccess(ITestResult result){
        extentTest=extentReports.createTest(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        extentTest.log(Status.PASS,"Test Passed");
    }

    public void onTestFailure(ITestResult result){
        extentTest=extentReports.createTest(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        extentTest.log(Status.FAIL,"Test Failed");
        extentTest.log(Status.FAIL,result.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult result){
        extentTest=extentReports.createTest(result.getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        extentTest.log(Status.SKIP,"Test Skipped");
        extentTest.log(Status.SKIP,result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext){
        extentReports.flush();
    }
}
