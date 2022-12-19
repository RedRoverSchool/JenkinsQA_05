package cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.folder.FolderConfigPage;
import model.HomePage;
import model.NewItemPage;
import model.folder.FolderStatusPage;
import org.openqa.selenium.By;
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
        newItemPage= newItemPage.setItemName(folderName);
    }

    @And("Select Folder from list and click Ok")
    public void selectFolderFromListAndClickOk() {
        folderConfigPage = newItemPage.selectFolderAndClickOk();
    }

    @And("click Save")
    public void clickSave() {
        folderConfigPage.clickSaveBtn(FolderStatusPage.class);
    }

    @Then("Result: folder was created")
    public void resultFolderWasCreated() {
        List<String> allJobsAfterCreate = new HomePage(CucumberDriver.getDriver())
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(allJobsAfterCreate.contains(folderName));
    }

    @When("Folder was created")
    public void folderWasCreated() {
        HomePage homePage = new HomePage(CucumberDriver.getDriver());
        homePage.clickNewItem()
                .setItemName(folderName)
                .selectFolderAndClickOk()
                .clickSaveBtn(FolderStatusPage.class);
    }
    @And("Return to home page")
    public void returnToHomePage() {
        CucumberDriver.getDriver().findElement(By.linkText("Dashboard")).click();
    }

    @And("Click on the existing folder")
    public void clickOnTheExistingFolder() {
        homePage.clickFolder(folderName);
    }

    @And("Click on menu Delete Folder")
    public void clickOnMenuDeleteFolder() {
        CucumberDriver.getDriver()
                .findElement(By.xpath("//a[@href='/job/" + folderName +"/delete']")).click();
    }

    @And("Click on button Yes")
    public void clickOnButtonYes() {
        CucumberDriver.getDriver()
                .findElement(By.cssSelector("#yui-gen1-button")).click();
    }

    @Then("Result: Deleted Folder in not exist")
    public void resultDeletedFolderInNotExist() {
        Assert.assertFalse(homePage.getJobList().contains(folderName));
    }

    @Then("Result: All folder should be deleted")
    public void resultAllFolderShouldBeDeleted() {
        Assert.assertTrue(homePage.getJobList().isEmpty());
    }
}
