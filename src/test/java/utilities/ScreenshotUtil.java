package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ScreenshotUtil implements ITestListener {
    public WebDriver webDriver;
    private String currentDir = System.getProperty("user.dir");
    private String screenShotsPath = "\\screenshots\\";

    public void takeScreenshots(WebDriver webDriver){
        File sreenshotFile = new File(currentDir + screenShotsPath);
    }




}
