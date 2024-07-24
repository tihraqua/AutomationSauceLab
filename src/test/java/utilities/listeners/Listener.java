package utilities.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import tests.BaseTest;
import utilities.logs.Log;

import java.io.File;
import java.util.Arrays;

public class Listener extends BaseTest implements ITestListener {
    private static int count =0;
    private static final int maxRetry = 1;


    @Attachment(value = "Page Screenshots", type = "image/bmp", fileExtension = ".bmp")
    public byte[] saveScreenShotPNG(WebDriver driver){
         byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        return screenshot;
    }

    @Attachment(value = "{0}", type = "text/plain")
    static String saveTextLog(String message){
        return message;
    }
    public static String getMethodName(ITestResult iTestResult){
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
public void onStart(ITestContext iTestContext){
    Log.info("I am on onStart method: " + iTestContext.getName());
  //  iTestContext.setAttribute("webDriver", this.webDriver);
}

public void onFinish(ITestContext iTestContext){
        Log.info("I am on onFinish method: " + iTestContext.getName());

}
    public void onTestSuccess(ITestResult iTestResult){

        Log.info("I am on onTestSuccess method: " + iTestResult.getName());




    }

    public void onTestFailure(ITestResult iTestResult){
        Object testclass = iTestResult.getInstance();
        WebDriver driver = ((BaseTest) testclass).getDriver();
        Log.info("I am on onTestFailure method: " + iTestResult.getName());
        System.out.printf("Screeshot captured for test cases: " + getMethodName(iTestResult));
        saveScreenShotPNG(driver);
        saveTextLog(getMethodName(iTestResult) + "Failed and screenshot taken");
        Log.info("I am on onTestStat method " + iTestResult.getMethod().getConstructorOrMethod().getName());
        //saveTextLog(getMethodName(iTestResult) + "Test case failed and screenshot taken");
//       if(count < maxRetry) {
//           count++;
//           TestNG testNG = new TestNG();
//           Class[] classes = {iTestResult.getClass()};
//           testNG.setTestClasses(classes);
//           testNG.addListener(new Listener());
//           testNG.run();




    }
    public void onTestSkipped(ITestResult iTestResult){
        Log.info("I am on onTestSkipped method: " + getMethodName(iTestResult));
       // iTestResult.setAttribute("webDriver", this.webDriver);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult){
        Log.info("I am on onFinish method: " + iTestResult.getName());

    }
    public void onTestStart(ITestResult iTestResult){

    }
    public void afterInvocation(ITestResult iTestResult){

    }


}
