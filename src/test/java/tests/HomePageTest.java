package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utilities.ExcelUtils;
import utilities.HttpConnection;
import utilities.listeners.Listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Listeners({Listener.class})
public class HomePageTest extends BaseTest{

    List<Integer> acceptedStatusCodeList = new ArrayList<>();
    Predicate<String> isStatusCodeOk = link -> {
        try {
            return acceptedStatusCodeList.contains(HttpConnection.getResponseCode(link));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }};


    @Description("Broken Link Check for the Home Page")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 4, description = "Check the broken links for the page HomePage")
    public void brokenLinkCheck() throws IOException {
        new LoginPage(webDriver).goToLogin()
                .login(ExcelUtils.getCell(4,0), ExcelUtils.getCell(4,1));
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
    @Description("Broken Link Check 2 for the Home Page")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 5)
    public void brokenLinkCheck2() throws IOException {
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
