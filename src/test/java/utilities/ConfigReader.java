package utilities;

import org.openqa.selenium.Platform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {


    public static ConfigReader instance ;
    public static Object lock = new Object();
    public static String currentDir = System.getProperty("user.dir");
    public static final String confFileName = "configuration.properties";
    public static String filepath;
    public String url;
    public String browser;

    public static ConfigReader getInstance() throws IOException {

        if(instance == null){
            synchronized (lock){
                instance = new ConfigReader();
                instance.loadData();

            }

        }
        return instance;


    }

    private void loadData() throws IOException {
        Properties properties = new Properties();
        if(Platform.getCurrent().toString().equalsIgnoreCase("MAC")){
            filepath = currentDir + "/src/test/resources/";
        } else if (Platform.getCurrent().toString().contains("Win")) {
            filepath = currentDir + "\\src\\test\\resources\\";

        }
        properties.load(new FileInputStream(filepath + confFileName));
        url = properties.getProperty("url");
        browser = properties.getProperty("browser");


    }
    public String getUrl(){
        return url;
    }
    public String getBrowser(){
        return browser;
    }




}
