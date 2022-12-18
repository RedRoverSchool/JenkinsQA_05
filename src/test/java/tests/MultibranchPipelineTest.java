package tests;

import model.*;
import model.freestyle.FreestyleProjectStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultibranchPipelineTest extends BaseTest {
    private final String NEW_ITEM_XPATH = "//div [@class='task '][1]";
    private final String ENTER_AN_ITEM_NAME_XPATH = "//input[@id='name']";
    private final String MULTIBRANCH_PIPELINE_XPATH =
            "//div[@id='j-add-item-type-nested-projects']/ul [@class='j-item-options']/li[2]";
    private final String BUTTON_OK_XPATH = "//span [@class='yui-button primary large-button']";
    private final String DASHBOARD_XPATH = "//li[@class='item'][1]/a [@class='model-link']";
    private static final By SUBMIT_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final String RANDOM_MULTIBRANCHPIPELINE_NAME = TestUtils.getRandomStr();
    private static final By MULTIBRANCH_PIPELINE_NAME_INPUT_FIELD = (By.xpath("//input[@type='text']"));
    private static final By MULTIBRANCH_PIPELINE_NAME = By.xpath("//a [@class='jenkins-table__link model-link inside']");

    private WebElement findElementXpath(String xpath) {
        return getDriver().findElement(By.xpath(xpath));
    }

    private void clickElement(By by) {
        getDriver().findElement(by).click();
    }

    private void clearElement(By by) {
        getDriver().findElement(by).clear();
    }

    private void redirectToDashboardPage() {
        clickElement(By.xpath("//a[text()='Dashboard']"));
    }

    private void buttonClickXpath(String locator) {
        getDriver().findElement(By.xpath(locator)).click();
    }

    private void inputTextByXPath(String locator, String text) {
        getDriver().findElement(By.xpath(locator)).sendKeys(text);
    }

    private void assertTextByXPath(String locator, String text) {
        Assert.assertEquals(getDriver().findElement(By.xpath(locator)).getText(), text);
    }

    private void assertTextById(String id, String text) {
        Assert.assertEquals(getDriver().findElement(By.id(id)).getText(), text);
    }

    private void deleteItem(String nameOfItem) {
        getDriver().get("http://localhost:8080/job/" + nameOfItem + "/delete");
        getDriver().findElement(By.id("yui-gen1-button")).click();
    }

    private void inputNewMbName(String itemName) {
        buttonClickXpath(NEW_ITEM_XPATH);
        inputTextByXPath(ENTER_AN_ITEM_NAME_XPATH, itemName);
        buttonClickXpath(MULTIBRANCH_PIPELINE_XPATH);
    }

    private void createMultibranchPipeline(String folderName) {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(folderName)
                .selectMultibranchPipeline()
                .clickOKButton()
                .clickSubmitButton()
                .clickDashboard();
    }

    @Test
    public void testCreateMultibranchPipeline() {
        createMultibranchPipeline(RANDOM_MULTIBRANCHPIPELINE_NAME);
        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.getJobList().contains(RANDOM_MULTIBRANCHPIPELINE_NAME));
    }

    @Test
    public void testCreateMbPipelineEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultibranchPipeline();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateMultibranchPipelineInvalidName() {
        NewItemPage newItemPage = new NewItemPage(getDriver())
                .clickNewItem()
                .setItemName("MultibranchPipeline@")
                .selectMultibranchPipeline();

        Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), "» ‘@’ is an unsafe character");

        CreateItemErrorPage createItemErrorPage = newItemPage.clickOkButton();

        Assert.assertEquals(createItemErrorPage.getCurrentURL(), "http://localhost:8080/view/all/createItem");
        Assert.assertEquals(createItemErrorPage.getErrorHeader(),
                "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "‘@’ is an unsafe character");
    }

    @Ignore
    @Test
    public void testCreateWithUnsafeCharsInName() {
        String itemName = "MultiBranch!Pipeline/000504";
        Matcher matcher = Pattern.compile("[»!@#$%^&*|:?></.'\\]\\[;]").matcher(itemName);
        matcher.find();
        String warnMessage = String.format("» ‘%s’ is an unsafe character", itemName.charAt(matcher.start()));

        buttonClickXpath(NEW_ITEM_XPATH);
        buttonClickXpath(MULTIBRANCH_PIPELINE_XPATH);
        inputTextByXPath(ENTER_AN_ITEM_NAME_XPATH, itemName);

        assertTextById("itemname-invalid", warnMessage);
    }

    @Test
    public void testCreateWithExistingName() {
        String itemName = "MultiBranchPipeline 000503";
        String warnMessage = String.format("» A job already exists with the name ‘%s’", itemName);

        inputNewMbName(itemName);
        buttonClickXpath(BUTTON_OK_XPATH);
        buttonClickXpath(DASHBOARD_XPATH);
        inputNewMbName(itemName);

        assertTextByXPath("//*[@id='itemname-invalid' and not(contains(@class, 'disabled'))]", warnMessage);

        deleteItem(itemName);
    }

    @Ignore
    @Test
    public void Rename_Multibranch_Pipeline_Test() {
        String nameOfItem = "NewMultibranchPipeline";
        buttonClickXpath(NEW_ITEM_XPATH);
        inputTextByXPath(ENTER_AN_ITEM_NAME_XPATH, "MultibranchPipeline");
        buttonClickXpath(MULTIBRANCH_PIPELINE_XPATH);
        buttonClickXpath(BUTTON_OK_XPATH);
        buttonClickXpath("//button [@id='yui-gen8-button']");
        buttonClickXpath(DASHBOARD_XPATH);
        getDriver().findElement
                (By.xpath("//a [@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//div [@class='task '][8]")).click();   //NewMultibranchPipeline
        findElementXpath("//input [@class='jenkins-input validated  ']").clear();
        findElementXpath("//input [@class='jenkins-input validated  ']").sendKeys(nameOfItem);
        buttonClickXpath("//button [@id='yui-gen1-button']");

        assertTextByXPath("//ul [@id='breadcrumbs']/li[3]/a[@class='model-link']", nameOfItem);
        buttonClickXpath(DASHBOARD_XPATH);

        assertTextByXPath("//span[text()='" + nameOfItem + "']", nameOfItem);

        deleteItem(nameOfItem);
    }


    @Test
    public void testRename_MultiBranch_Pipeline_From_Dropdown() {
        createMultibranchPipeline(RANDOM_MULTIBRANCHPIPELINE_NAME);
        FreestyleProjectStatusPage actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobDropDownMenu(RANDOM_MULTIBRANCHPIPELINE_NAME)
                .clickRenameDropDownMenu()
                .clearFieldAndInputNewName(RANDOM_MULTIBRANCHPIPELINE_NAME + "_Renamed")
                .clickSubmitButton();

        Assert.assertTrue(actualMultibranchPipeline.getHeadlineText().contains(RANDOM_MULTIBRANCHPIPELINE_NAME + "_Renamed"));
    }

    @Test
    public void testRenameMultiBranchPipelineFromLeftSideMenu() {
        String Renamed = "Renamed_Multibranch_Pipeline";

        createMultibranchPipeline(RANDOM_MULTIBRANCHPIPELINE_NAME);
        redirectToDashboardPage();
        clickElement(MULTIBRANCH_PIPELINE_NAME);
        clickElement(By.linkText("Rename"));
        clearElement(MULTIBRANCH_PIPELINE_NAME_INPUT_FIELD);
        getDriver().findElement(MULTIBRANCH_PIPELINE_NAME_INPUT_FIELD).sendKeys(Renamed);
        clickElement(SUBMIT_BUTTON);

        Assert.assertEquals((getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText()), Renamed);

        redirectToDashboardPage();

        Assert.assertEquals(getDriver().findElement(MULTIBRANCH_PIPELINE_NAME).getText(), Renamed);
    }

    @Test
    public void testDeleteMultibranchPipelineUsingDropDown() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(RANDOM_MULTIBRANCHPIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .clickJobDropdownMenu(RANDOM_MULTIBRANCHPIPELINE_NAME)
                .clickDeleteMbPipelineDropDownMenu()
                .clickSubmitButtonToHomePage();

        Assert.assertEquals(homePage.getHeaderText(), "Welcome to Jenkins!");
    }
}
