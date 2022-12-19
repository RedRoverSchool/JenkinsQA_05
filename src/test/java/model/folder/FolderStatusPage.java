package model.folder;

import model.base.BaseStatusPage;
import model.MovePage;
import model.freestyle.FreestyleProjectStatusPage;
import model.multibranch_pipeline.MultibranchPipelineStatusPage;
import model.NewItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class FolderStatusPage extends BaseStatusPage {

    @FindBy(css = "#breadcrumbs li a")
    private List<WebElement> topMenuList;

    @FindBy(xpath = "//li[@class='item'][last()]//button")
    private WebElement breadcrumbsThisFolderToggleDropdown;

    @FindBy(css = "li.yuimenuitem[index='1']")
    private WebElement newItemInDropDown;

    @FindBy(xpath = "//tr/td[3]/a/span[1]")
    private List<WebElement> jobList;

    @FindBy(linkText = "Delete Folder")
    private WebElement deleteFolder;

    @FindBy(linkText = "Move")
    private WebElement moveFolder;

    @FindBy(linkText = "Create a job")
    private WebElement createJob;

    @FindBy(className = "empty-state-block")
    private WebElement emptyStateBlock;

    @FindBy(linkText = "New Item")
    private WebElement folderNewItem;

    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement folderNewName;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(linkText = "Add description")
    private WebElement addDescription;

    @FindBy(css = ".jenkins-input")
    private WebElement inputFieldDescription;

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItemDropdownThisFolderInBreadcrumbs() {
        getWait(5).until(ExpectedConditions.visibilityOf(breadcrumbsThisFolderToggleDropdown)).click();
        getWait(5).until(ExpectedConditions.visibilityOf(newItemInDropDown)).click();

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

    public MultibranchPipelineStatusPage clickMultibranchPipeline(String multibranchPipelineName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + multibranchPipelineName + "/']")).click();

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

    public FolderStatusPage clickSubmitButton() {
        submitButton.click();

        return new FolderStatusPage(getDriver());
    }

    public NewItemPage clickFolderNewItem() {
        folderNewItem.click();

        return new NewItemPage(getDriver());
    }

    public FolderStatusPage clickDeleteFolder() {
        deleteFolder.click();

        return new FolderStatusPage(getDriver());
    }

    public MovePage clickMoveFolder() {
        moveFolder.click();

        return new MovePage(getDriver());
    }


    public List<String> getTopMenueLinkText() {
        return topMenuList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getFolderName() {
        String[] namesBlock = getDriver().findElement(By.id("main-panel")).getText().split("\n");

        return namesBlock[1];
    }

    public FolderStatusPage clickAddDescription() {
        addDescription.click();

        return this;
    }

    public FolderStatusPage setDescription(String description) {
        inputFieldDescription.sendKeys(description);

        return this;
    }

    public FreestyleProjectStatusPage clickProject(String name) {
        getDriver().findElement(By.linkText(name)).click();

        return new FreestyleProjectStatusPage(getDriver());
    }
}
