package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class BrokenLinks {
    private WebDriver webDriver;
    List<Integer> acceptedStatusCodeList = new ArrayList<>();
    Predicate<String> isStatusCodeOk = link -> {
        try {
            return acceptedStatusCodeList.contains(HttpConnection.getResponseCode(link));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

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

}
