package pages;

import org.openqa.selenium.WebDriver;
import utilities.ExcelUtils;

import java.io.IOException;

public class HomePage extends BasePage{
    LoginPage loginPage = new LoginPage(this.webDriver);
    public HomePage(WebDriver webDriver) throws IOException {
        super(webDriver);
    }
    public  HomePage goToHomePage(){
        loginPage.login(ExcelUtils.getCell(4,0), ExcelUtils.getCell(4,1));
        return this;
    }

}
