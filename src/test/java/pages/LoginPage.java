package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ConfigReader;

import java.io.IOException;

public class LoginPage extends BasePage {
    //************** Page Constructor ********************************
    public LoginPage(WebDriver webDriver) throws IOException {
        super(webDriver);
    }
    //************** Page Variables ********************************
    String url = ConfigReader.getInstance().getUrl();

    //************** Page Elements ********************************
    By usernamefield = By.id("user-name");
    By passwordfield = By.id("password");
    By loginBtn = By.id("login-button");
    By error = By.xpath("//h3[@data-test='error']");
    //************** Page Functions ********************************
    public LoginPage goToLogin(){
        webDriver.get(url);
        return this;
    }
    public LoginPage login(String username, String password){
        setText(usernamefield,username);
        setText(passwordfield,password);
        click(loginBtn);
        return this;
    }
    public LoginPage verifyLoginUser(String expectedmessage){
        assertEquals(expectedmessage,error);
        return this;
    }

    public LoginPage verifyLoginPassword(String expectedmessage){
        assertEquals(expectedmessage,error);
        return this;
    }
}
