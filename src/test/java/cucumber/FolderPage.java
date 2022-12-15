package cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.FolderConfigPage;
import model.HomePage;
import model.NewItemPage;
import org.testng.Assert;
import runner.CucumberDriver;
import runner.TestUtils;

import java.util.List;

public class FolderPage {

    private HomePage homePage;
    private FolderConfigPage folderConfigPage;
    private NewItemPage newItemPage;
    private final String folderName = TestUtils.getRandomStr();

    @Given("Go to home page")
    public void goToHomePage() {
        homePage = new HomePage(CucumberDriver.getDriver());
    }
    @When("Click on link New Item")
    public void clickOnLink(){
        newItemPage = homePage.clickNewItem();
    }

    @And("Enter an item name")
    public void enterAnItemName() {
        newItemPage= newItemPage.setProjectName(folderName);
    }

    @And("Select Folder from list and click Ok")
    public void selectFolderFromListAndClickOk() {
        folderConfigPage = newItemPage.selectFolderAndClickOk();
    }

    @And("click Save")
    public void clickSave() {
        folderConfigPage.clickSaveButton();
    }

    @Then("Result: folder was created")
    public void resultFolderWasCreated() {
        List<String> allJobsAfterCreate = new HomePage(CucumberDriver.getDriver())
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(allJobsAfterCreate.contains(folderName));
    }
}
