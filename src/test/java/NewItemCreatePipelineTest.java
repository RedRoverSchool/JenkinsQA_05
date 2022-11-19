import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewItemCreatePipelineTest extends BaseTest {

    private void click(By by) {
        getDriver().findElement(by).click();
    }

    private static String getRandomStr() {
        return RandomStringUtils.random(7, true,true);
    }

    private void createPipeline(String jobName) {
        setJobPipeline(jobName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void setJobPipeline(String jobName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
    }

    private JavascriptExecutor scrollPageDown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        return js;
    }

    @Test
    public void testCreatePipelineExistingNameError() {

        final String jobname = "Job15";

        click(By.linkText("New Item"));
        getDriver().findElement(By.id("name")).sendKeys(jobname);
        click(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"));
        click(By.id("ok-button"));
        click(By.id("jenkins-home-link"));
        click(By.linkText("New Item"));

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.id("name"))).click()
                .sendKeys(jobname).build().perform();

        final WebElement notificationError = getDriver()
                .findElement(By.xpath("//div[@class='input-validation-message' and not(contains(@class, 'disabled')) and  @id='itemname-invalid']"));

        Assert.assertEquals(notificationError.getText(), String.format("» A job already exists with the name ‘%s’", jobname));
    }

    @DataProvider(name = "new-item-unsafe-names")
    public Object[][] dpMethod() {
        return new Object[][]{{"!Pipeline1"}, {"pipel@ne2"}, {"PipeLine3#"},
                {"PIPL$N@4"}, {"5%^PiPl$^Ne)"}};
    }

    @Test(dataProvider = "new-item-unsafe-names")
    public void testCreateNewItemWithUnsafeCharactersName(String name) {
        Matcher matcher = Pattern.compile("[!@#$%^&*|:?></.']").matcher(name);
        matcher.find();

        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getDriver().findElement(By.cssSelector("input#name")).sendKeys(name);

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div#itemname-invalid")).getAttribute("textContent"),
                String.format("» ‘%s’ is an unsafe character", name.charAt(matcher.start())));
    }

    @Test
    public void testCreatePipelineWithoutName() {
        click(By.linkText("New Item"));
        click(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"));

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateNewItemWithoutChooseAnyFolder(){
        click(By.linkText("New Item"));
        click(By.xpath("//span[@class = 'yui-button primary large-button']"));

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineOnBreadcrumbs () {
        final String itemName = "AFJenkins05";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.cssSelector("input#name")).sendKeys(
                itemName);
        click(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"));
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertTrue(getDriver().findElement(By.className("jenkins-breadcrumbs"))
                .getAttribute("textContent").contains(itemName));
    }

    @Test
    public void testCreateNewItemFromOtherNonExistingName() {
        final String jobName = getRandomStr();

        setJobPipeline(jobName);
        scrollPageDown();
        new Actions(getDriver()).pause(1500).moveToElement(getDriver().findElement(By.id("from"))).click()
                .sendKeys(jobName).perform();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "No such job: " + jobName);
    }
}
