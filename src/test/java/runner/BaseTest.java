package runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.AfterMethod;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        BaseUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());

        driver = BaseUtils.createDriver();
        ProjectUtils.login(driver);
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        ProjectUtils.logout(driver);
        driver.quit();

        BaseUtils.logf("Execution time is %o sec\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait(int sec) {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        }
        return wait;
    }

    protected List<String> getListNames(By by) {
        List<WebElement> existingJobs =
                getDriver().findElements(by);
        List<String> existingJobsNames = existingJobs.stream().map(WebElement::getText).collect(Collectors.toList());

        return existingJobsNames;
    }
}
