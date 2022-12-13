package model;

import model.base.MyViewSideMenuFrame;
import model.base.SideMenuFrame;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

import static runner.TestUtils.scrollToElement;

public class HomePage extends MyViewSideMenuFrame {

    public boolean getProjectNameFromProjectTabl;

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(linkText = "Configure")
    private WebElement configureDropDownMenu;

    @FindBy(linkText = "Rename")
    private WebElement renameDropDownMenu;

    @FindBy(xpath = "//td[3]/a/button")
    private WebElement dropDownMenuOfJob;

    @FindBy(xpath = "//li[@index='2']")
    private WebElement deleteButtonInDropDownMenu;

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(linkText = "Manage Jenkins")
    private WebElement menuManageJenkins;

    @FindBy(xpath = "//a[@href='/manage']")
    private WebElement manageJenkins;

    @FindBy(xpath = "//span/a[@href='/asynchPeople/']")
    private WebElement people;

    @FindBy(css = ".tabBar>.tab>a[class='']")
    private WebElement openViewLink;

    @FindBy(xpath = "//span[text()='Move']")
    private WebElement moveButtonDropdown;

    @FindBy(xpath = "//div[@class='tabBar']/div/a")
    private List<WebElement> viewList;

    @FindBy(xpath = "//a[@href='api/']")
    private WebElement restApiLink;

    @FindBy(xpath = "//div/a[@class='model-link']")
    private WebElement iconUserName;

    @FindBy(css = "#page-header .jenkins-menu-dropdown-chevron")
    private WebElement userDropdownMenu;

    @FindBy(css = ".first-of-type > .yuimenuitem")
    private List<WebElement> userDropdownMenuItems;

    @FindBy(linkText = "Builds")
    private WebElement buildsItemInUserDropdownMenu;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(xpath = "//*[@id=\"job_Pipeline1\"]/td[4]")
    private WebElement lastSuccessStatus;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickViewLink() {
        openViewLink.click();

        return this;
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getViewList() {
        return viewList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public FreestyleProjectStatusPage clickFreestyleProjectName() {
        jobList.get(0).click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public FreestyleProjectStatusPage clickFreestyleProjectName(String name) {
        getDriver().findElement(By.linkText(name)).click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public RenameItemPage clickRenameDropDownMenu() {
        getWait(6).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage(getDriver());
    }

    public ConfigurationGeneralPage clickConfigDropDownMenu() {
        getWait(6).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new ConfigurationGeneralPage(getDriver());
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

    public JobPage clickJob(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new JobPage(getDriver());
    }

    public PipelineConfigPage clickConfigureDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new PipelineConfigPage(getDriver());
    }

    public String getHeaderText() {

        return getWait(5).until(ExpectedConditions.visibilityOf(header)).getText();
    }

    public HomePage clickFolderDropdownMenu(String folderName) {
        getWait(5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/" + folderName + "/']/button"))).click();

        return this;
    }

    public FolderStatusPage clickFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']")).sendKeys(Keys.RETURN);

        return new FolderStatusPage(getDriver());
    }

    public FolderConfigPage clickConfigureDropDownMenuForFolder() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new FolderConfigPage(getDriver());
    }

    public ManageJenkinsPage clickMenuManageJenkins() {
        menuManageJenkins.click();

        return new ManageJenkinsPage(getDriver());
    }

    public ManageJenkinsPage clickManageJenkins() {
        manageJenkins.click();

        return new ManageJenkinsPage(getDriver());
    }

    public PeoplePage clickPeople() {
        people.click();

        return new PeoplePage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickMultConfJobName(String name) {
        jobList.get(0).click();
        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MovePage clickMoveButtonDropdown() {
        getWait(5).until(ExpectedConditions.visibilityOf(moveButtonDropdown));
        scrollToElement(getDriver(), moveButtonDropdown);
        moveButtonDropdown.click();
        return new MovePage(getDriver());
    }

    public String getJobBuildStatus(String name) {
        return getDriver().findElement(By.id(String.format("job_%s", name)))
                .findElement(By.xpath(".//*[name()='svg']")).getAttribute("tooltip");
    }

    public FooterPage clickRestApiLink() {
        restApiLink.click();

        return new FooterPage(getDriver());
    }

    public StatusUserPage clickUserIcon() {
        iconUserName.click();

        return new StatusUserPage(getDriver());
    }

    public HomePage clickUserDropdownMenu() {
        userDropdownMenu.click();

        return this;
    }

    public int getItemsCountInUserDropdownMenu() {
        int itemsCount = 0;
        for (WebElement item : getWait(5).until(
                ExpectedConditions.visibilityOfAllElements(
                        userDropdownMenuItems))) {
            itemsCount++;
        }

        return itemsCount;
    }

    public String getItemsNamesInUserDropdownMenu() {
        StringBuilder itemsNames = new StringBuilder();
        for (WebElement item : getWait(5).until(
                ExpectedConditions.visibilityOfAllElements(
                        userDropdownMenuItems))) {
            itemsNames.append(item.getText()).append(" ");
        }

        return itemsNames.toString().trim();
    }

    public BuildsUserPage clickBuildsItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                buildsItemInUserDropdownMenu)).click();

        return new BuildsUserPage(getDriver());
    }

    public String getBuildDurationTime() {
        if (getJobBuildStatus().equals("Success")) {

            return getDriver().findElement(By.xpath("//tr[contains(@class, 'job-status')]/td[4]")).getText();
        } else if (getJobBuildStatus().equals("Failed")) {

            return getDriver().findElement(By.xpath("//tr[contains(@class, 'job-status')]/td[5]")).getText();
        }

        return null;
    }

    public String getJobBuildStatus() {

        return buildStatusIcon.getAttribute("tooltip");
    }

    public ViewPage clickView(String name) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/view/%s/']", name))).click();

        return new ViewPage(getDriver());
    }

    public String getLastSuccessText() {

        return lastSuccessStatus.getText();
    }

    public String getJobName(String name) {

        return getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", name))).getText();
    }
}
