package model;

import model.base.Breadcrumbs;
import model.base.Header;
import model.folder.FolderConfigPage;
import model.folder.FolderStatusPage;
import model.freestyle.FreestyleProjectConfigPage;
import model.freestyle.FreestyleProjectStatusPage;
import model.multibranch_pipeline.DeleteMultibranchPipelinePage;
import model.multibranch_pipeline.MultibranchPipelineStatusPage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import model.organization_folder.OrgFolderStatusPage;
import model.pipeline.PipelineConfigPage;
import model.pipeline.PipelineStatusPage;
import model.views.EditViewPage;
import model.views.MyViewsPage;
import model.views.NewViewPage;
import model.views.ViewPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

import static runner.TestUtils.scrollToElement;

public class HomePage extends Breadcrumbs {

    @FindBy(linkText = "Build History")
    private WebElement buildHistory;

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(linkText = "New Item")
    private WebElement newItem;

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

    @FindBy(xpath = "//li[@index='3']")
    private WebElement deleteMbPipelineButtonInDropDownMenu;

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(linkText = "Manage Jenkins")
    private WebElement menuManageJenkins;

    @FindBy(css = "a[href='/me/my-views']")
    private WebElement myViewsSideMenuLink;

    @FindBy(css = ".item a[class=''][href$='/my-views/']")
    private WebElement myViewsTopMenuLink;

    @FindBy(xpath = "//a[@href='/manage']")
    private WebElement manageJenkins;

    @FindBy(xpath = "//span/a[@href='/asynchPeople/']")
    private WebElement people;

    @FindBy(css = ".tabBar>.tab>a[class='']")
    private WebElement openViewLink;

    @FindBy(css = ".tabBar>.tab>a.addTab")
    private WebElement addViewLink;

    @FindBy(xpath = "//span[text()='Move']")
    private WebElement moveButtonDropdown;

    @FindBy(xpath = "//div[@class='tabBar']/div/a")
    private List<WebElement> viewList;

    @FindBy(xpath = "//div/a[@class='model-link']")
    private WebElement iconUserName;

    @FindBy(css = "#page-header .jenkins-menu-dropdown-chevron")
    private WebElement userDropdownMenu;

    @FindBy(css = ".first-of-type > .yuimenuitem")
    private List<WebElement> userDropdownMenuItems;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editView;

    @FindBy(linkText = "Builds")
    private WebElement buildsItemInUserDropdownMenu;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(xpath = "(//*[local-name()='svg' and @tooltip='Disabled'])[2]")
    private WebElement projectDisabledIcon;

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

    @FindBy(xpath = "//div[@id='description']//textarea")
    private WebElement descriptionTextarea;

    @FindBy(linkText = "Configure")
    private WebElement configureItemInUserDropdownMenu;

    @FindBy(linkText = "My Views")
    private WebElement myViewItemInUserDropdownMenu;

    @FindBy(linkText = "Credentials")
    private WebElement credentialsItemInUserDropdownMenu;

    @FindBy(css = "#projectstatus th")
    private List<WebElement> jobTableColumnList;

    @FindBy(xpath = "(//a[@class='yuimenuitemlabel'])[3]/span")
    private WebElement buildNowButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public HomePage clickViewLink() {
        openViewLink.click();

        return this;
    }

    public NewViewPage clickAddViewLink() {
        addViewLink.click();

        return new NewViewPage(getDriver());
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

    public PipelineStatusPage clickPipelineProjectName() {
        jobList.get(0).click();

        return new PipelineStatusPage(getDriver());
    }

    public FolderConfigPage clickDeleteDropDownMenu() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(deleteButtonInDropDownMenu));
        deleteButtonInDropDownMenu.click();

        return new FolderConfigPage(getDriver());
    }

    public HomePage clickJobDropDownMenu(String name) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(
                "//tr[@id='job_%s']//button[@class='jenkins-menu-dropdown-chevron']", name)))).click();

        return this;
    }

    public FolderStatusPage clickJob(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new FolderStatusPage(getDriver());
    }

    public MultibranchPipelineStatusPage clickJobMBPipeline(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new MultibranchPipelineStatusPage(getDriver());
    }

    public PipelineConfigPage clickConfigureDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new PipelineConfigPage(getDriver());
    }

    public String getHeaderText() {

        return getWait(10).until(ExpectedConditions.visibilityOf(header)).getText();
    }

    public HomePage clickJobDropdownMenu(String folderName) {
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

    public MyViewsPage clickMyViewsSideMenuLink() {
        myViewsSideMenuLink.click();

        return new MyViewsPage(getDriver());
    }

    public String getJobBuildStatus(String name) {
        return getDriver().findElement(By.id(String.format("job_%s", name)))
                .findElement(By.xpath(".//*[name()='svg']")).getAttribute("tooltip");
    }

    public MyViewsPage clickMyViewsTopMenuLink() {
        myViewsTopMenuLink.click();

        return new MyViewsPage(getDriver());
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

    public BuildHistoryPage clickBuildHistory() {
        buildHistory.click();

        return new BuildHistoryPage(getDriver());
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

    public EditViewPage goToEditView(String viewName) {
        clickMyViewsSideMenuLink();
        getDriver().findElement(By.linkText(viewName)).click();
        editView.click();

        return new EditViewPage(getDriver());
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

    public String getLastSuccessText(String name) {

        return getDriver().findElement(By.xpath(String.format("//*[@id='job_%s']/td[4]", name))).getText();
    }

    public String getJobName(String name) {

        return getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", name))).getText();
    }

    public MultiConfigurationProjectStatusPage clickProject(String projectName) {
        getWait(5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/" + projectName + "/']"))).click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public Boolean getProjectIconText() {
        return projectDisabledIcon.isDisplayed();
    }

    public String getStatusBuildText() {

        return buildStatusIcon.getAttribute("tooltip");
    }

    public HomePage movePointToCheckBox() {
        getAction().moveToElement(buildStatusIcon).perform();

        return this;
    }

    public FreestyleProjectConfigPage clickConfigDropDownMenuFreestyle() {
        getWait(6).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new FreestyleProjectConfigPage(getDriver());
    }

    public HomePage clickAddDescriptionButton() {
        getWait(10).until(ExpectedConditions.elementToBeClickable(addDescriptionButton)).click();
        getWait(10).until(ExpectedConditions.visibilityOf(descriptionTextarea));

        return this;
    }

    public WebElement getDescriptionTextarea() {

        return descriptionTextarea;
    }

    public WebElement getAddDescriptionButton() {

        return addDescriptionButton;
    }

    public HomePage waitForVisibilityOfAddDescriptionButton(){
        getWait(10).until(ExpectedConditions.visibilityOf(addDescriptionButton));

        return this;
    }

    public boolean isAddDescriptionButtonPresent(){
        try{
            getDriver().findElement(By.id("description-link"));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    public boolean isDescriptionTextareaPresent(){
        try{
            getDriver().findElement(By.xpath("//div[@id='description']//textarea"));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    public ConfigureUserPage clickConfigureItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                configureItemInUserDropdownMenu)).click();

        return new ConfigureUserPage(getDriver());
    }

    public MyViewsPage clickMyViewItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                myViewItemInUserDropdownMenu)).click();

        return new MyViewsPage(getDriver());
    }

    public CredentialsPage clickCredentialsItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                credentialsItemInUserDropdownMenu)).click();

        return new CredentialsPage(getDriver());
    }

    public boolean clickProjectDropdownMenu(String projectName) {
        getWait(5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/" + projectName + "/']/button"))).click();

        return buildNowButton.isDisplayed();
    }

    public DeleteMultibranchPipelinePage clickDeleteMbPipelineDropDownMenu() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(deleteMbPipelineButtonInDropDownMenu));
        deleteMbPipelineButtonInDropDownMenu.click();

        return new DeleteMultibranchPipelinePage(getDriver());
    }

    public void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public OrgFolderStatusPage clickOrgFolder(String name) {
        getDriver().findElement(By.linkText(name)).click();

        return new OrgFolderStatusPage(getDriver());
    }
    public PipelineStatusPage clickPipelineJob(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new PipelineStatusPage(getDriver());
    }
    public String getJobListAsString() {
        StringBuilder listProjectsNames = new StringBuilder();
        for (WebElement projects : jobList) {
            listProjectsNames.append(projects.getText()).append(" ");
        }

        return listProjectsNames.toString().trim();
    }
}