package tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.ExcelUtils;
import utilities.HttpConnection;
import utilities.listeners.Listener;
import utilities.listeners.Retry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Listeners({ Listener.class })
@Epic("Regression Tests")
@Feature("Login Tests")
public class LoginTest extends BaseTest {
    List<Integer> acceptedStatusCodeList = new ArrayList<>();
    Predicate<String> isStatusCodeOk = link -> {
        try {
            return acceptedStatusCodeList.contains(HttpConnection.getResponseCode(link));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }};



    @Step("Test test test")
    @Attachment
    @Description("TC01_Verify Login using invalid user and valid password")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 0, description = "Test case 1")
    public void invalidLoginTest_InvalidUserValidPassword() throws IOException {
        LoginPage loginPage = new LoginPage(webDriver);
       loginPage.goToLogin()
        .login(ExcelUtils.getCell(0,0),ExcelUtils.getCell(0,1))
               .verifyLoginPassword(ExcelUtils.getCell(0,2))
               .verifyLoginUser(ExcelUtils.getCell(0,2));
    }
    @Step("Test test test")
    @Description("TC02_Verify Login using valid user and invalid password")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1, description = "Test case 2")
    public void invalidLoginTest_ValidUserInvalidPassword() throws IOException {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.goToLogin()
                .login(ExcelUtils.getCell(1,0),ExcelUtils.getCell(1,1))
                .verifyLoginPassword(ExcelUtils.getCell(1,2))
                .verifyLoginUser(ExcelUtils.getCell(1,2));
    }
    @Description("TC03_Verify Login using null user and valid password")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2, description = "Test case 3")
    public void invalidLoginTest_NullUserValidPassword() throws IOException {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.goToLogin()
                .login(ExcelUtils.getCell(2,0),ExcelUtils.getCell(2,1))
                .verifyLoginPassword(ExcelUtils.getCell(2,2))
                .verifyLoginUser(ExcelUtils.getCell(2,2));
    }
    @Description("TC04_Verify Login using valid user and null password")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 3)
    public void invalidLoginTest_ValidUserNullPassword() throws IOException {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.goToLogin()
                .login(ExcelUtils.getCell(3,0),ExcelUtils.getCell(3,1))
                .verifyLoginPassword(ExcelUtils.getCell(3,2))
                .verifyLoginUser(ExcelUtils.getCell(3,2));
    }
    @Description("TC05_Verify broken links")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 4)
    public void brokenLinkCheck() {
        Collections.addAll(acceptedStatusCodeList, 200, 301, 302, 403);
        long count = webDriver.findElements(By.tagName("a"))
                .stream()
                .parallel()
                .map(element -> element.getAttribute("href"))
                .filter(Objects::nonNull) ////filter the not null links.
                .filter(link -> !link.isEmpty()) //filter the non-empty links.
                .filter(link -> !link.contains("javascript") && !link.contains("*&")) //Filter other link related patterns.
                .filter(link -> link.startsWith("http") || link.startsWith("https")) //Filter links started http and https.
                .distinct() //remove duplicate links
                .filter(isStatusCodeOk.negate()) //Filter the Not Ok status codes
                .peek(link -> {
                    try {
                        System.out.println("Failed Link: " + link + " Response Code: " + HttpConnection.getResponseCode(link));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .count();
        System.out.println("Count: " + count);
        Assert.assertFalse(count > 0);
    }
    @Description("TC06_Verify broken links check 2")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 5)
    public void brokenLinkCheck2() {
        Collections.addAll(acceptedStatusCodeList, 200, 301, 302, 403);
        boolean result = webDriver.findElements(By.tagName("a"))
                .stream()
                .parallel()
                .map(element -> element.getAttribute("href"))
                .filter(Objects::nonNull) ////filter the not null links.
                .filter(link -> !link.isEmpty()) //filter the non-empty links.
                .filter(link -> !link.contains("javascript") && !link.contains("*&")) //Filter other link related patterns.
                .filter(link -> link.startsWith("http") || link.startsWith("https")) //Filter links started http and https.
                .distinct() //remove duplicate links
                .peek(link -> {
                    try {
                        System.out.println("Link: " + link + " Response Code: " + HttpConnection.getResponseCode(link));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .anyMatch(isStatusCodeOk.negate());
        Assert.assertFalse(result);
    }
}
