package runner;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class CucumberDriver {

    private static WebDriver driver;

    @Before
    public static void before(Scenario scenario){
        driver = BaseUtils.createDriver();

        ProjectUtils.get(driver);
        ProjectUtils.login(driver);
        clearData();
    }
    
    @After
    public static void after(Scenario scenario){
        driver.quit();
    }

    public static WebDriver getDriver(){
        return driver;
    }

    private static void clearData(){
        BaseUtils.log("Clear data");
        JenkinsUtils.clearData();
    }
}
