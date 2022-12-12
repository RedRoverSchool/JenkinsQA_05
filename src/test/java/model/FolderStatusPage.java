package model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.stream.Collectors;

public class FolderStatusPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(xpath = "//li[@class='item'][last()]//button")
    private WebElement breadcrumbsThisFolderToggleDropdown;

    @FindBy(css = "li.yuimenuitem[index='1']")
    private WebElement newItemInDropDown;

    @FindBy(xpath = "//tr/td[3]/a/span[1]")
    private List<WebElement> jobList;

    @FindBy(linkText = "Create a job")
    private WebElement createJob;

    @FindBy(className = "empty-state-block")
    private WebElement emptyStateBlock;

    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement folderNewName;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement renameSubmitButton;

    @FindBy(xpath = "//h1")
    private WebElement folderHeader;

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public NewItemPage clickNewItemDropdownThisFolderInBreadcrumbs(){
        breadcrumbsThisFolderToggleDropdown.click();
        newItemInDropDown.click();

        return new NewItemPage(getDriver());
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public NewItemPage clickCreateJob() {
        createJob.click();

        return new NewItemPage(getDriver());
    }

    public MultibranchPipelineStatusPage clickMultibranchPipeline(String MultibranchPipelineName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + MultibranchPipelineName + "/']")).click();

        return new MultibranchPipelineStatusPage(getDriver());
    }

    public WebElement getEmptyStateBlock() {

        return emptyStateBlock;
    }

    public FolderStatusPage clickRename(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName + "/confirm-rename']")).click();

        return new FolderStatusPage(getDriver());
    }

    public FolderStatusPage clearAndSetNewName(String folderName) {
        folderNewName.clear();
        folderNewName.sendKeys(folderName);

        return new FolderStatusPage(getDriver());
    }

    public FolderStatusPage clickRenameSubmitButton() {
        renameSubmitButton.click();

        return new FolderStatusPage(getDriver());
    }

    public String getHeaderFolderText() {

        return folderHeader.getText();
    }
}
