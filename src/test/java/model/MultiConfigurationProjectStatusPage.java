package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiConfigurationProjectStatusPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement dashboard;

    @FindBy(id = "description-link")
    private WebElement descriptionLink;

    @FindBy(name = "description")
    private WebElement description;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement saveDescriptionButton;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement fieldDescription;

    @FindBy(xpath = "//span[text()='Delete Multi-configuration project']")
    private WebElement deleteOption;

    @FindBy(xpath = "//li[@class='item'][last()-1]")
    private WebElement breadcrumbsParentFolderLink;

    @FindBy(xpath = "//button[@id='yui-gen1-button']")
    private WebElement disableButton;

    @FindBy(xpath = "//button[@id='yui-gen1-button']")
    private WebElement enableButton;

    @FindBy(linkText = "Build Now")
    private WebElement buildNowButton;

    @FindBy(css = ".model-link.inside.build-link.display-name")
    private WebElement dropDownBuildIcon;

    @FindBy(xpath = "//li[@id='yui-gen3']/a/*[name()='svg']")
    private WebElement  consoleOutputDropDownBuildIcon;

    public MultiConfigurationProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickAddDescription() {
        descriptionLink.click();

        return this;
    }

    public MultiConfigurationProjectStatusPage fillDescription(String desc) {
        getWait(5).until(ExpectedConditions.visibilityOf(description));
        description.sendKeys(desc);

        return this;
    }

    public MultiConfigurationProjectStatusPage clickSave() {
        saveDescriptionButton.click();

        return this;
    }

    public String getDescriptionText() {

        return fieldDescription.getText();
    }

    public String getNameMultiConfigProject(String name) {

        return getDriver().findElement(By.xpath("//li[@class='item']//a[@href='/job/" + name + "/']")).getText();
    }

    public MulticonfigurationProjectConfigPage deleteMultiConfigProject() {
        deleteOption.click();
        getDriver().switchTo().alert().accept();

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public FolderStatusPage clickParentFolderInBreadcrumbs() {
        breadcrumbsParentFolderLink.click();

        return new FolderStatusPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickDisableButton() {
        disableButton.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickEnableButton(){
        enableButton.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MulticonfigurationProjectConfigPage clickCongiguration(String projectName) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/configure']", projectName))).click();

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public MultiConfigurationProjectStatusPage clickDropDownBuildIcon() {
        getWait(20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='build-status-icon__outer']"
                + "/*[name()='svg'][@tooltip='Success &gt; Console Output']")));
        dropDownBuildIcon.click();

        return this;
    }

    public ConsoleOutputMulticonfigProgectPage selectAndClickConsoleOutput() {
        getWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='yui-gen3']/a/*[name()='svg']")));
        consoleOutputDropDownBuildIcon.click();

        return new ConsoleOutputMulticonfigProgectPage(getDriver());
    }

    public void multiConfigurationProjectBuildNow (WebDriver driver) {
        driver.findElement(By.xpath("//a[@onclick='return build_id386(this)']")).click();
    }

    public void multiConfigurationProjectNewestBuilds (WebDriver driver) {
        driver.findElement(By.xpath("//*[@id='buildHistoryPageNav']/div[1]/div")).click();
    }
}
