package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.HttpConnection;
import utilities.logs.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class BaseTest {

    public  static WebDriver webDriver;



    @BeforeClass
    public void setup() throws IOException {

        Log.info("************************* Test is stating ****************************");
        System.out.printf("************* Set Test Data Sheet ****************************");
        ExcelUtils.setSheenName("Sheet1");
        if(ConfigReader.getInstance().getBrowser().equalsIgnoreCase("chrome")){
            webDriver = new ChromeDriver();

        } else if (ConfigReader.getInstance().getBrowser().equalsIgnoreCase("Edge")) {
            webDriver = new EdgeDriver();

        }
        else if (ConfigReader.getInstance().getBrowser().equalsIgnoreCase("Firefox")){
            webDriver = new FirefoxDriver();
        }
        else if (ConfigReader.getInstance().getBrowser().equalsIgnoreCase("Remote")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            URL gridUrl = new URL("http://192.168.80.148:4444/");
            webDriver = new RemoteWebDriver(gridUrl, chromeOptions);
        }else System.out.printf("******************************No Driver Found ***********************");

        webDriver.manage().window().maximize();


    }
    public WebDriver getDriver(){
        return webDriver;
    }
    @AfterClass
    public static void tearDown(){
        Log.info("********************* The test is finishin **********************");
        webDriver.quit();
    }



}