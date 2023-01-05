package model.views;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditViewPage extends ViewPage {

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(css = "input[name=filterExecutors]")
    private WebElement filterBuildExecutorsOptionCheckBox;

    @FindBy(name = "name")
    private WebElement viewName;

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    @FindBy(xpath = "//button[text() = 'Apply']")
    private WebElement applyButton;

    @FindBy(css = ".jenkins-form-description")
    private WebElement uniqueTextOnGlobalViewEditPage;

    @FindBy(css = "div:nth-of-type(5) > .jenkins-section__title")
    private WebElement uniqueSectionOnListViewEditPage;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement description;

    @FindBy(css = "#notification-bar")
    private WebElement confirmAfterClickingApply;

    @FindBy(css = "input[checked='true']")
    private WebElement markedCheckboxNameJob;

    @FindBy(css = ".hetero-list-add[suffix='columns']")
    private WebElement addColumnDropDownMenu;

    @FindBy(xpath = "//a[@class='yuimenuitemlabel' and text()='Git Branches']")
    private WebElement gitBranchesColumnMenuOption;

    @FindBy(css = "input[name='useincluderegex']+label")
    private WebElement regexFilterCheckbox;

    @FindBy(css = "input[name='includeRegex']")
    private WebElement regexFilterTextArea;

    @FindBy(xpath = "//div[@descriptorid='hudson.views.StatusColumn']//div[@class='dd-handle']")
    private WebElement statusColumnDragHandle;

    @FindBy(css = "div[descriptorid='hudson.views.StatusColumn'] button.repeatable-delete")
    private WebElement deleteStatusColumnButton;

    @FindBy(css = "#main-panel p")
    private WebElement errorPageDetailsText;

    @FindBy(css = "a.yuimenuitemlabel")
    private List<WebElement> listAddColumnDropDownMenuItemsText;

    @FindBy(css = ".bd ul")
    private WebElement listAddColumnDropDownMenuItems;

    @FindBy(css = "#main-panel h1")
    private WebElement errorPageHeader;

    @FindBy(xpath = "//div[contains(@class, 'hetero-list-container')]/div[@class='repeated-chunk'][last()]//button")
    private WebElement lastExistingColumnDeleteButton;

    public EditViewPage(WebDriver driver) {
        super(driver);
    }

    public MyViewsPage clickListOrMyViewOkButton() {
        okButton.click();

        return new MyViewsPage(getDriver());
    }

    public EditViewPage renameView(String name) {
        viewName.clear();
        viewName.sendKeys(name);

        return this;
    }

    public ViewPage clickGlobalViewOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    public String getUniqueTextOnGlobalViewEditPage() {

        return uniqueTextOnGlobalViewEditPage.getText();
    }

    public String getUniqueSectionOnListViewEditPage() {

        return uniqueSectionOnListViewEditPage.getText();
    }

    public EditViewPage addDescription(String desc) {
        description.sendKeys(desc);

        return new EditViewPage(getDriver());
    }

    public EditViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public EditViewPage selectFilterBuildExecutorsOptionCheckBox() {
        filterBuildExecutorsOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public boolean isFilterBuildQueueOptionCheckBoxSelected() {

        return filterBuildQueueOptionCheckBox.isSelected();
    }

    public boolean isFilterBuildExecutorsOptionCheckBoxSelected() {

        return filterBuildExecutorsOptionCheckBox.isSelected();
    }

    public EditViewPage clickApplyButton() {
        applyButton.click();

        return this;
    }

    public String getTextConfirmAfterClickingApply() {

        return getWait(15).until(ExpectedConditions.visibilityOf(
                confirmAfterClickingApply)).getText();
    }

    public String getSelectedJobName() {

        return markedCheckboxNameJob.getAttribute("name");
    }

    public EditViewPage clickJobsCheckBoxForAddRemoveToListView(String jobName) {
        getDriver().findElement(By.cssSelector("label[title='" + jobName + "']")).click();

        return this;
    }

    public EditViewPage clickAddColumnDropDownMenu() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), addColumnDropDownMenu);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(addColumnDropDownMenu));
        addColumnDropDownMenu.click();

        return this;
    }

    public EditViewPage clickGitBranchesColumnMenuOption() {
        gitBranchesColumnMenuOption.click();

        return this;
    }

    public EditViewPage scrollToRegexFilterCheckboxPlaceInCenterWaitTillNotMoving() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), regexFilterCheckbox);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(regexFilterCheckbox));

        return this;
    }

    public boolean isRegexCheckboxChecked() {

        return regexFilterCheckbox.isSelected();
    }

    public EditViewPage clickRegexCheckbox() {
        regexFilterCheckbox.click();

        return this;
    }

    public EditViewPage clearAndSendKeysRegexTextArea(String regex) {
        regexFilterTextArea.clear();
        regexFilterTextArea.sendKeys(regex);

        return this;
    }

    public EditViewPage dragByYOffset(int offset) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(statusColumnDragHandle)
                .clickAndHold(statusColumnDragHandle)
                .moveByOffset(0, offset / 2)
                .moveByOffset(0, offset / 2)
                .release().perform();

        return this;
    }

    public EditViewPage scrollToStatusColumnDragHandlePlaceInCenterWaitTillNotMoving() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), statusColumnDragHandle);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(statusColumnDragHandle));

        return this;
    }

    public String getErrorPageHeader() {

        return errorPageHeader.getText();
    }

    public boolean isCorrectErrorPageDetailsText(char illegalCharacter) {

        return errorPageDetailsText.getText().equals(String.format("‘%c’ is an unsafe character", illegalCharacter));
    }

    public EditViewPage clickDeleteStatusColumnButton() {
        deleteStatusColumnButton.click();
        getWait(5).until(ExpectedConditions.invisibilityOf(deleteStatusColumnButton));

        return this;
    }

    public EditViewPage scrollToDeleteStatusColumnButtonPlaceInCenterWaitTillNotMoving() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), deleteStatusColumnButton);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(deleteStatusColumnButton));

        return this;
    }

    public Map<String, String> getMapMatchingColumnDropDownMenuItemsAndJobTableHeader() {
        final String[] listOrderedAllPossibleJobTableHeadersText = {
                "S", "W", "Name", "Last Success", "Last Failure", "Last Stable",
                "Last Duration", "", "Git Branches", "Name", "Description"};

        Map<String, String> tableMenuMap = new HashMap<>();
        for (int i = 0; i < listAddColumnDropDownMenuItemsText.size(); i++) {
            tableMenuMap.put(listAddColumnDropDownMenuItemsText.get(i).getText(), listOrderedAllPossibleJobTableHeadersText[i]);
        }

        return tableMenuMap;
    }

    public String getAddColumnDropDownMenuItemTextByOrder(int itemNumber){

        return listAddColumnDropDownMenuItems.findElement(By.cssSelector(String.format("li:nth-child(%d)", itemNumber))).getText();
    }


    public EditViewPage clickAddColumnDropDownMenuItemByOrder(int itemNumber){
        listAddColumnDropDownMenuItems.findElement(By.cssSelector(String.format("li:nth-child(%d)", itemNumber))).click();

        return new EditViewPage(getDriver());
    }

    public int getNumberOfAllAddColumnDropDownMenuItems(){

        return listAddColumnDropDownMenuItemsText.size();
    }

    public EditViewPage clickLastExistingColumnDeleteButton(){
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), lastExistingColumnDeleteButton);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(lastExistingColumnDeleteButton));
        lastExistingColumnDeleteButton.click();

        return this;
    }
}