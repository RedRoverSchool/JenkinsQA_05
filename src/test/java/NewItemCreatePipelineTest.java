import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewItemCreatePipelineTest extends BaseTest {

    private void click(By by) {
        getDriver().findElement(by).click();
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
}
