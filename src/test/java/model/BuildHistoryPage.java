package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;


public class BuildHistoryPage extends HomePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "jenkins-icon-size__items-item")
    private WebElement sizeIcon;

    @FindBy(linkText = "Build Now")
    private WebElement buildNowButton;

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newJob;

    @FindBy(id = "name")
    private WebElement inputBuildName;

    @FindBy(xpath = "//li[@class='hudson_model_FreeStyleProject']")
    private WebElement newFreeStyleProjectButton;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//a[@href='/iconSize?16x16']")
    private WebElement smallSizeIcon;

    @FindBy(xpath = "//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li/following-sibling::li[2]")
    private WebElement middleSizeIcon;

    @FindBy(xpath = "//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li[last()]")
    private WebElement largeSizeIcon;

    @FindBy(xpath = "//table[@id='projectStatus']/thead/tr/th")
    private List<WebElement> columns;

    @FindBy(xpath = "//a[@href='/legend']")
    private WebElement iconLegend;

    @FindBy(xpath = "//div[contains(@id,'label-tl')]")
    private List<WebElement> labelsOnTimelineBuildHistory;

    @FindBy(css = "#icon-tl-0-1-e1")
    private WebElement iconOfLabelsOnTime;

    public String getSizeText() {

        return sizeIcon.getText();
    }

    public BuildHistoryPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public BuildHistoryPage clickCreateNewJob() {
        newJob.click();

        return this;
    }

    public BuildHistoryPage enterNewBuildName(String newName) {
        inputBuildName.sendKeys(newName);

        return this;
    }

    public BuildHistoryPage clickNewFreestyleProjectButton() {
        newFreeStyleProjectButton.click();

        return this;
    }

    public BuildHistoryPage clickOkButton() {
        okButton.click();

        return this;
    }

    public BuildHistoryPage enterDescriptionField(String description) {
        if (!(description.equals("empty"))) {
            descriptionField.sendKeys(description);
        }

        return this;
    }

    public BuildHistoryPage clickSubmitButton() {
        submitButton.click();

        return this;
    }

    public boolean smallSizeIconIsDisplayed() {

        return smallSizeIcon.isDisplayed();
    }

    public boolean middleSizeIconIsDisplayed() {

        return middleSizeIcon.isDisplayed();
    }

    public boolean largeSizeIconIsDisplayed() {

        return largeSizeIcon.isDisplayed();
    }

    public int getSize() {

        return columns.size();
    }

    public boolean isIconDisplayed() {

        return iconLegend.isDisplayed();
    }

    public List<String> getNameOfLabelsOnTimeLineBuildHistory(){
        getWait(5).until(ExpectedConditions.visibilityOf(iconOfLabelsOnTime));
        return labelsOnTimelineBuildHistory
                .stream()
                .map(WebElement :: getText)
                .collect(Collectors.toList());
    }
}
