package runner;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.AfterMethod;

import java.lang.reflect.Method;

@Listeners({FilterForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        BaseUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());

        driver = BaseUtils.createDriver();
        ProjectUtils.login(driver);
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess() && BaseUtils.isServerRun()) {
            BaseUtils.captureScreenFile(driver, method.getName(), this.getClass().getName());
        }

        ProjectUtils.logout(driver);
        driver.quit();

        BaseUtils.logf("Execution time is %o sec\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
