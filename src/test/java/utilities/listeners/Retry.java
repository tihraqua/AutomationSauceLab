package utilities.listeners;

//import org.apache.logging.log4j.core.util.internal.Status;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utilities.ConfigReader;

public class Retry implements IRetryAnalyzer {
    public int count = 0;
    public int maxRetry = 3;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(!iTestResult.isSuccess()){
            if(count<maxRetry){
                count++;
                iTestResult.setStatus(ITestResult.FAILURE);
                return true;
            }else {
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        }else {
        iTestResult.setStatus(ITestResult.SUCCESS);}
        return false;
    }
}
