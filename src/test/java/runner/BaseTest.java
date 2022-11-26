package runner;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.AfterMethod;
import runner.order.OrderForTests;
import runner.order.OrderUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Listeners({FilterForTests.class, OrderForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    private OrderUtils.MethodsOrder<Method> methodsOrder;

    @BeforeClass
    protected void beforeClass() {
        methodsOrder = OrderUtils.createMethodsOrder(
                Arrays.stream(this.getClass().getMethods())
                        .filter(m -> m.getAnnotation(Test.class) != null && m.getAnnotation(Ignore.class) == null)
                        .collect(Collectors.toList()),
                m -> m.getName(),
                m -> m.getAnnotation(Test.class).dependsOnMethods());
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        BaseUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (!methodsOrder.isGroupStarted(method)) {
                clearData();
                startDriver();
                getWeb();
                loginWeb();
            } else {
                getWeb();
            }
        } catch (Exception e) {
            stopDriver();
            throw new RuntimeException(e);
        } finally {
            methodsOrder.markAsInvoked(method);
        }
    }

    protected void clearData() {
        BaseUtils.log("Clear data");
        JenkinsUtils.deleteJobs();
    }

    protected void loginWeb() {
        BaseUtils.log("Login");
        ProjectUtils.login(driver);
    }

    protected void getWeb() {
        BaseUtils.log("Get web page");
        ProjectUtils.get(driver);
    }

    protected void startDriver() {
        BaseUtils.log("Browser open");
        driver = BaseUtils.createDriver();
    }

    protected void stopDriver() {
        try {
            ProjectUtils.logout(driver);
        } catch (Exception ignore) {}

        driver.quit();
        BaseUtils.log("Browser closed");
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess() && BaseUtils.isServerRun()) {
            BaseUtils.captureScreenFile(driver, method.getName(), this.getClass().getName());
        }

        if (!testResult.isSuccess() || methodsOrder.isGroupFinished(method)) {
            stopDriver();
        }

        BaseUtils.logf("Execution time is %o sec\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
