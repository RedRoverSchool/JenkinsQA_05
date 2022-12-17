package cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.HomePage;
import model.MultibranchPipelineConfigPage;
import model.MultibranchPipelineStatusPage;
import model.NewItemPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import runner.CucumberDriver;
import java.util.List;


import static runner.TestUtils.getRandomStr;


public class MultiBranchPipelinePage {

    private HomePage homePage;
    private NewItemPage newItemPage;
    private MultibranchPipelineConfigPage multibranchPipelineConfigPage;
    private MultibranchPipelineStatusPage multibranchPipelineStatusPage;

    private final String multiBranchPipelineItemName = getRandomStr();

    @Given("Go to the home page")
    public void goToTheHomePage() {
        homePage = new HomePage(CucumberDriver.getDriver());
    }

    @When("Click on New Item")
    public void clickOnNewItem() {
        homePage.clickNewItem();
    }

    @And("Enter valid name")
    public void enterValidName() {
        CucumberDriver.getDriver().findElement(By.id("name")).sendKeys(multiBranchPipelineItemName);
    }

    @And("Select Multibranch Pipeline")
    public void selectMultibranchPipeline() {
        CucumberDriver.getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject']")).click();
    }

    @And("Click OK Button")
    public void clickOKButton() {
        CucumberDriver.getDriver().findElement(By.id("ok-button")).click();
    }

    @And("Click Save Button on Configure page without any changes")
    public void clickSaveButtonOnConfigurePageWithoutAnyChanges() {
        CucumberDriver.getDriver().findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("Pipeline page is displayed")
    public void pipelinePageIsDisplayed() {
        Assert.assertEquals(CucumberDriver.getDriver().findElement(By.xpath("//h1")).getText(), multiBranchPipelineItemName);
    }

    @Then("Multibranch Pipeline with name exists")
    public void multibranchPipelineWithNameExists() {
        List<String> allJobsAfterCreate = new HomePage(CucumberDriver.getDriver())
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(allJobsAfterCreate.contains(multiBranchPipelineItemName));
    }
}
