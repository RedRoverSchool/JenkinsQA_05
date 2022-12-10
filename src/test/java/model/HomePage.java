package model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItem;

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(linkText = "Configure")
    private WebElement configureDropDownMenu;

    @FindBy(xpath = "//td[3]/a/button")
    private WebElement dropDownMenuOfJob;

    @FindBy(xpath = "//li[@index='2']")
    private WebElement deleteButtonInDropDownMenu;

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(linkText = "Manage Jenkins")
    private WebElement menuManageJenkins;

    @FindBy(css = "a[href='/me/my-views']")
    private WebElement myViews;
    
      @FindBy(xpath = "//a[@href='/manage']")
    private WebElement manageJenkins;

    @FindBy(xpath = "//span/a[@href='/asynchPeople/']")
    private WebElement people;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public FreestyleProjectPage clickFreestyleProjectName() {
        jobList.get(0).click();

        return new FreestyleProjectPage(getDriver());
    }

    public PipelineProjectPage clickPipelineProjectName() {
        jobList.get(0).click();

        return new PipelineProjectPage(getDriver());
    }

    public FolderConfigPage clickDeleteDropDownMenu() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(deleteButtonInDropDownMenu));
        deleteButtonInDropDownMenu.click();

        return new FolderConfigPage(getDriver());
    }

    public HomePage clickJobDropDownMenu(String name) {
        getDriver().findElement((By.xpath(String.format(
                "//tr[@id='job_%s']//button[@class='jenkins-menu-dropdown-chevron']", name)))).click();

        return this;
    }

    public PipelineConfigPage clickConfigureDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new PipelineConfigPage(getDriver());
    }

    public String getTextHeader(){
        return header.getText();
    }

    public DropdownMenu clickFolderDropdownMenu(String folderName) {
        getWait(5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/" + folderName + "/']/button"))).click();

        return new DropdownMenu(getDriver());
    }

    public FolderStatusPage clickFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']")).sendKeys(Keys.RETURN);

        return new FolderStatusPage(getDriver());
    }

    public ManageJenkinsPage clickMenuManageJenkins() {
        menuManageJenkins.click();

        return new ManageJenkinsPage(getDriver());
    }
    
     public MyViewsPage clickMyViews() {
        myViews.click();

        return new MyViewsPage(getDriver());
    }

    public ManageJenkinsPage clickManageJenkins(){
        manageJenkins.click();

        return new ManageJenkinsPage(getDriver());
    }

    public PeoplePage clickPeople(){
        people.click();

        return new PeoplePage(getDriver());
    }
}
