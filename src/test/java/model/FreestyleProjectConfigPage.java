package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static runner.TestUtils.scrollToElement;

public class FreestyleProjectConfigPage extends BasePage {

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

    public FreestyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectStatusPage clickSaveBtn() {
        saveBtn.click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public FreestyleProjectConfigPage clickDiscardOldBuildsCheckbox() {
        discardOldBuildsCheckbox.click();

        return this;
    }

    public FreestyleProjectConfigPage typeDaysToKeepBuilds(String numberOfDays) {
        daysToKeepBuilds.sendKeys(numberOfDays);

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

    public String getFreestyleProjectName(String name){

        return projectButton.getText();
        }

    public FreestyleProjectConfigPage clickCheckBoxThisProjectIsParametrized(){
        checkBoxProjectIsParametrized.click();

        return this;
    }

    public FreestyleProjectConfigPage clickButtonAddParameter(){
        buttonAddParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage selectStringParameter(){
        stringParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage inputStringParameterName(String stringParameterName){
        getWait(5).until(ExpectedConditions.visibilityOf(fieldInputStringParameterName)).sendKeys(stringParameterName);

        return this;
    }

    public FreestyleProjectConfigPage inputStringParameterDefaultValue(String stringParameterDefaultValue){
        fieldInputStringParameterDefaultValue.sendKeys(stringParameterDefaultValue);

        return this;
    }

    public FreestyleProjectConfigPage selectChoiceParameter(){
        choiceParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage inputChoiceParameterName(String choiceParameterName){
        getWait(5).until(ExpectedConditions.visibilityOf(fieldInputChoiceParameterName)).sendKeys(choiceParameterName);

        return this;
    }

    public FreestyleProjectConfigPage inputChoiceParameterValue(String choiceParameterValue){
        fieldInputChoiceParameterValue.sendKeys(choiceParameterValue);

        return this;
    }

    public FreestyleProjectConfigPage selectBooleanParameter(){
        booleanParameter.click();

        return this;
    }

    public FreestyleProjectConfigPage inputBooleanParameterName(String booleanParameterName){
        getWait(10).until(ExpectedConditions.visibilityOf(fieldInputBooleanParameterName)).sendKeys(booleanParameterName);

        return this;
    }

    public FreestyleProjectConfigPage switchONBooleanParameterAsDefault(){
        setByDefault.click();

        return this;
    }

    public FreestyleProjectConfigPage scrollAndClickAddParameterButton() throws InterruptedException {
        scrollToElement(getDriver(), buttonAddParameter);
        Thread.sleep(300);
        buttonAddParameter.click();

        return this;
    }

    public BuildWithParametersPage clickSaveButton() {
        saveBtn.click();

        return new BuildWithParametersPage(getDriver());
    }
}
