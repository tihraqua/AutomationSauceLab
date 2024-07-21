package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePage {

    public WebDriver webDriver;
    public WebDriverWait webDriverWait;

    public BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

    }
    public void waitVisibility(By elementBy){
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
    }
    public String getText(By elemntBy){
        waitVisibility(elemntBy);
        return webDriver.findElement(elemntBy).getText();
    }
    public void click(By elementBy){
        waitVisibility(elementBy);
        webDriver.findElement(elementBy).click();
    }
    public void setText(By elementBy, String value){
        waitVisibility(elementBy);
        webDriver.findElement(elementBy).sendKeys(value);
    }
    public void assertEquals(String expectedValue,By elementBy){
        waitVisibility(elementBy);
        String actualText = getText(elementBy);
        Assert.assertEquals(actualText,expectedValue);
    }
}
