package model.freestyle;

import model.BuildWithParametersPage;
import model.base.BaseConfigPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static runner.TestUtils.scrollToElement_PlaceInCenter;

public class FreestyleProjectConfigPage extends BaseConfigPage<FreestyleProjectStatusPage, FreestyleProjectConfigPage> {

    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(css = "#main-panel > p")
    private WebElement errorMsg;

    @FindBy(name = "Submit")
    private WebElement saveBtn;

    @FindBy(xpath = "//span/label[text()='Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuilds;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement maxNumberOfBuildsToKeep;

    @FindBy(xpath = "//li[@class='item'][2]")
    private WebElement projectButton;

    @FindBy(xpath = "//label[text() = 'This project is parameterized']")
    private WebElement checkBoxProjectIsParametrized;

    @FindBy(xpath = "//button[text() = 'Add Parameter']")
    private WebElement buttonAddParameter;

    @FindBy(xpath = "//a[text() = 'String Parameter']")
    private WebElement stringParameter;

    @FindBy(xpath = "//input[@name = 'parameter.name']")
    private WebElement fieldInputStringParameterName;

    @FindBy(xpath = "//input[@name = 'parameter.defaultValue']")
    private WebElement fieldInputStringParameterDefaultValue;

    @FindBy(xpath = "//a[text() = 'Choice Parameter']")
    private WebElement choiceParameter;

    @FindBy(xpath = "//div[@class = 'jenkins-form-item hetero-list-container with-drag-drop  ']/div[2]//input[@name = 'parameter.name']")
    private WebElement fieldInputChoiceParameterName;

    @FindBy(xpath = "//textarea[@name= 'parameter.choices']")
    private WebElement fieldInputChoiceParameterValue;

    @FindBy(xpath = "//a[text() = 'Boolean Parameter']")
    private WebElement booleanParameter;

    @FindBy(xpath = "//div[@class = 'jenkins-form-item hetero-list-container with-drag-drop  ']/div[3]//input[@name = 'parameter.name']")
    private WebElement fieldInputBooleanParameterName;

    @FindBy(xpath = "//label[text() = 'Set by Default']")
    private WebElement setByDefault;

    @FindBy(xpath = "//label[text() = 'Git']")
    private WebElement radioGitButton;

    @FindBy(xpath = "//div[text() = 'Repository URL']/following-sibling::div/input")
    private WebElement fieldInputRepositoryURL;

    @FindBy(xpath = "//button[@data-section-id='build-steps']")
    private WebElement buildStepsSideMenuOption;

    @FindBy(xpath = "//button[text()='Add build step']")
    private WebElement buildStepsButton;

    @FindBy(xpath = "//button[text()='Add build step']/../../..//a[@href='#']")
    private List<WebElement> listOfElementsInBuildStepsDropDown;

    @FindBy(linkText = "Build Now")
    private WebElement buildNowButton;

    @FindBy(xpath = "//button[@data-section-id='build-triggers']")
    private WebElement buildTriggersSideMenuOption;

    @FindBy(xpath = "//label[text()='Build periodically']")
    private WebElement buildPeriodicallyOption;

    @FindBy(name = "hudson-triggers-TimerTrigger")
    private WebElement buildPeriodicallyCheckbox;

    @FindBy(xpath = "//div[contains(text(), 'Branch Specifier')]/following-sibling::div/input")
    private WebElement BranchSpecifierInputField;

    @Override
    protected FreestyleProjectStatusPage createStatusPage() {
        return new FreestyleProjectStatusPage(getDriver());
    }

    public FreestyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadlineText() {
        return headline.getText();
    }

    public String getErrorMsg() {
        return errorMsg.getText();
    }

    public FreestyleProjectConfigPage clickDiscardOldBuildsCheckbox() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(discardOldBuildsCheckbox)).click();

        return this;
    }

    public FreestyleProjectConfigPage typeDaysToKeepBuilds(String numberOfDays) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(daysToKeepBuilds)).sendKeys(numberOfDays);

        return this;
    }

    public String getNumberOfDaysToKeepBuilds() {

        return daysToKeepBuilds.getAttribute("value");
    }

    public FreestyleProjectConfigPage typeMaxNumberOfBuildsToKeep(String numberOfBuilds) {
        maxNumberOfBuildsToKeep.sendKeys(numberOfBuilds);

        return this;
    }

    public String getMaxNumberOfBuildsToKeep() {

        return maxNumberOfBuildsToKeep.getAttribute("value");
    }

    public String getFreestyleProjectName(String name) {

        return projectButton.getText();
    }

    public FreestyleProjectConfigPage switchONCheckBoxThisProjectIsParametrized() {
        checkBoxProjectIsParametrized.click();

        return this;
    }

    public FreestyleProjectConfigPage clickButtonAddParameter() {
        buttonAddParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage selectStringParameter() {
        stringParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage inputStringParameterName(String stringParameterName) {
        getWait(5).until(ExpectedConditions.visibilityOf(fieldInputStringParameterName)).sendKeys(stringParameterName);

        return this;
    }

    public FreestyleProjectConfigPage inputStringParameterDefaultValue(String stringParameterDefaultValue) {
        fieldInputStringParameterDefaultValue.sendKeys(stringParameterDefaultValue);

        return this;
    }

    public FreestyleProjectConfigPage selectChoiceParameter() {
        choiceParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage inputChoiceParameterName(String choiceParameterName) {
        getWait(5).until(ExpectedConditions.visibilityOf(fieldInputChoiceParameterName)).sendKeys(choiceParameterName);

        return this;
    }

    public FreestyleProjectConfigPage inputChoiceParameterValue(String choiceParameterValue) {
        fieldInputChoiceParameterValue.sendKeys(choiceParameterValue);

        return this;
    }

    public FreestyleProjectConfigPage selectBooleanParameter() {
        booleanParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage inputBooleanParameterName(String booleanParameterName) {
        getWait(10).until(ExpectedConditions.visibilityOf(fieldInputBooleanParameterName)).sendKeys(booleanParameterName);

        return this;
    }

    public FreestyleProjectConfigPage switchONBooleanParameterAsDefault() {
        setByDefault.click();

        return this;
    }

    public FreestyleProjectConfigPage scrollAndClickAddParameterButton() {
        scrollToElement_PlaceInCenter(getDriver(), buttonAddParameter);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(buttonAddParameter)).click();

        return this;
    }

    public BuildWithParametersPage clickSaveButton_() {
        saveBtn.click();

        return new BuildWithParametersPage(getDriver());
    }

    public FreestyleProjectConfigPage selectSourceCodeManagementGIT() {
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(radioGitButton));
        getAction()
                .scrollToElement(radioGitButton)
                .moveToElement(radioGitButton)
                .click()
                .perform();

        return this;
    }

    public FreestyleProjectConfigPage inputGITRepositoryURL(String url) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldInputRepositoryURL)).sendKeys(url);

        return this;
    }

    public FreestyleConfigSideMenuPage switchOFFCheckBoxThisProjectIsParametrized() {
        checkBoxProjectIsParametrized.click();

        return new FreestyleConfigSideMenuPage(getDriver());
    }

    public FreestyleConfigSideMenuPage clickBuildNowButton() {
        buildNowButton.click();

        return new FreestyleConfigSideMenuPage(getDriver());
    }

    public FreestyleProjectConfigPage clickBuildStepsSideMenuOption() {
        buildStepsSideMenuOption.click();

        return this;
    }

    public FreestyleProjectConfigPage openAddBuildStepDropDown() {
        TestUtils.scrollToElement(getDriver(), buildStepsButton);
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".yui-button.yui-menu-button")));
        getWait(10).until(TestUtils.ExpectedConditions.elementIsNotMoving(buildStepsButton));
        new Actions(getDriver())
                .moveToElement(buildStepsButton)
                .perform();
        buildStepsButton.click();
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".yui-button-active.yui-menu-button-active")));

        return this;
    }

    public FreestyleProjectConfigPage closeAddBuildStepDropDown() {
        buildStepsButton.click();
        getWait(10).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".yui-button-active.yui-menu-button-active")));

        return this;
    }

    public Set<String> getOptionsInBuildStepsDropDown() {

        return listOfElementsInBuildStepsDropDown
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public FreestyleProjectConfigPage clickBuildTriggersSideMenuOption() {
        buildTriggersSideMenuOption.click();

        return this;
    }

    public FreestyleProjectConfigPage scrollAndClickBuildPeriodicallyCheckbox() {
        scrollToElement_PlaceInCenter(getDriver(), buildPeriodicallyOption);
        getWait(10).until(TestUtils.ExpectedConditions.elementIsNotMoving(buildPeriodicallyOption)).click();
        getWait(10).until(ExpectedConditions
                .elementSelectionStateToBe(By.name("hudson-triggers-TimerTrigger"), true));

        return this;
    }

    public boolean verifyThatBuildPeriodicallyCheckboxIsSelected() {

        return buildPeriodicallyCheckbox.isSelected();
    }

    public FreestyleProjectConfigPage uncheckBuildPeriodicallyCheckbox() {
        if (buildPeriodicallyCheckbox.isSelected()) {
            getWait(10).until(TestUtils.ExpectedConditions.elementIsNotMoving(buildPeriodicallyOption)).click();
        }

        return this;
    }

    public FreestyleProjectConfigPage inputBranchSpecifier(String branchSpecifier){
        scrollToElement_PlaceInCenter(getDriver(), BranchSpecifierInputField);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(BranchSpecifierInputField)).clear();
        //BranchSpecifierInputField.clear();
        BranchSpecifierInputField.sendKeys(branchSpecifier);

        return this;
    }
}