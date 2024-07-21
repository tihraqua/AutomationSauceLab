package utilities.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import tests.BaseTest;
import utilities.logs.Log;

public class Listener extends BaseTest implements ITestListener {
    private static int count =0;
    private static final int maxRetry = 1;
    private static String getMethodName(ITestResult iTestResult){
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

        Log.info("I am on onTestFailure method: " + iTestResult.getName());
        if(count < maxRetry){
            count ++;
            TestNG testNG = new TestNG();
            Class[] classes = {iTestResult.getClass()};
            testNG.setTestClasses(classes);
            testNG.addListener(new Listener());
            testNG.run();
        }


    }
    public void onTestSkipped(ITestResult iTestResult){
        Log.info("I am on onTestSkipped method: " + iTestResult.getName());
        iTestResult.setAttribute("webDriver", this.webDriver);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult){
        Log.info("I am on onFinish method: " + iTestResult.getName());

    }
    public void onTestStart(ITestResult iTestResult){
        Log.info("I am on onTestStat method " + iTestResult.getMethod().getConstructorOrMethod().getName());
    }

}
