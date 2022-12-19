package tests;

import model.*;
import model.organization_folder.OrgFolderStatusPage;
import model.views.MyViewsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class HeaderTest extends BaseTest {
    
    @Test
    public void testSeeNameIcon() {

        boolean actualResult = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"))
                .isDisplayed();

        Assert.assertTrue(actualResult);
    }

    @Test
    public void testUserIdInUserAccountLinkAndInUserPage() {

        String usernameInUserAccountLink = new HomePage(getDriver())
                .getUserNameText();

        String usernameInUserPage = new HomePage(getDriver())
                .clickUserIcon()
                .getUserIDText();

        Assert.assertEquals(usernameInUserPage,
                String.format("Jenkins User ID: %s", usernameInUserAccountLink));
    }

    @Test
    public void testCountAndNamesItemsInUserDropdownMenu() {
        int itemsCount = new HomePage(getDriver())
                .clickUserDropdownMenu()
                .getItemsCountInUserDropdownMenu();

        String itemsNames = new HomePage(getDriver())
                .clickUserDropdownMenu()
                .getItemsNamesInUserDropdownMenu();

        Assert.assertEquals(itemsCount, 4);
        Assert.assertEquals(itemsNames, "Builds Configure My Views Credentials");
    }

    @Test
    public void testUserDropdownMenuToOpenBuildsUserPage() {
        BuildsUserPage buildsUserPage = new HomePage(getDriver())
                .clickUserDropdownMenu()
                .clickBuildsItemInUserDropdownMenu();

        Assert.assertEquals(buildsUserPage.getHeaderH1Text(),
                "Builds for admin");
    }

    @Test
    public void testLogoHeadIconIsSeen() {

        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.getJenkinsHeadIcon().isDisplayed());

        Assert.assertTrue(homePage.getJenkinsHeadIcon().isEnabled());
    }

    @Test
    public void testManageJenkinsClickNameIconToReturnToTheMainPage() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver()).clickManageJenkins();

        Assert.assertEquals(manageJenkinsPage.getCurrentURL(), "http://localhost:8080/manage/");
        Assert.assertEquals(manageJenkinsPage.getTextHeader1ManageJenkins(), "Manage Jenkins");

        HomePage homePage = manageJenkinsPage.clickJenkinsNameIcon();

        Assert.assertEquals(homePage.getCurrentURL(), "http://localhost:8080/");
        Assert.assertEquals(homePage.getHeaderText(), "Welcome to Jenkins!");
    }

    @Test
    public void testUserDropdownMenuToOpenConfigureUserPage() {
        ConfigureUserPage configureUserPage = new HomePage(getDriver())
                .clickUserDropdownMenu()
                .clickConfigureItemInUserDropdownMenu();

        Assert.assertEquals(configureUserPage.getAddNewTokenButtonName(),
                "Add new Token");
    }

    @Test
    public void testUserDropdownMenuToOpenMyViewsUserPage() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickUserDropdownMenu()
                .clickMyViewItemInUserDropdownMenu();

        Assert.assertEquals(myViewsPage.getMyViewItemNameOnTopBar(),
                "My Views");
    }

    @Test
    public void testUserDropdownMenuToOpenCredentialsUserPage() {
        CredentialsPage credentialsPage = new HomePage(getDriver())
                .clickUserDropdownMenu()
                .clickCredentialsItemInUserDropdownMenu();

        Assert.assertEquals(credentialsPage.getHeaderH1Text(),
                "Credentials");
    }

    @Test
    public void testReturnFromNewItemPageToHomePageByClickingOnHeadIcon() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/");
    }

    @Test
    public void testClickOnTheIconNameTheUserIsReturnedToTheMainPage() {

        getDriver().findElement(By.xpath("//span[text()='New Item']"));
        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"));

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        boolean actualResultPage = wait.until(ExpectedConditions.urlContains("http://localhost:8080/"));

        Assert.assertTrue(actualResultPage);
    }

    @Test
    public void testCheckTheAppropriateSearchResult() {
        String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 1000);
        String searchRequest = "organiza";

        List<String> searchResults = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(organizationFolderName)
                .selectOrgFolderAndClickOk()
                .clickSaveBtn(OrgFolderStatusPage.class)
                .clickDashboard()
                .setSearchFieldAndClickEnter(searchRequest)
                .getSearchResultList();

        Assert.assertTrue(searchResults.size() > 0);

        for (String result: searchResults) {
            Assert.assertTrue(result.contains(searchRequest));
        }
    }

    @Test
    public void testLogoHeadIconReloadMainPage() {

        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.clickAddDescriptionButton().getDescriptionTextarea().isEnabled());

        Assert.assertFalse(homePage.isAddDescriptionButtonPresent());

        Assert.assertFalse(homePage.clickJenkinsHeadIcon()
                .waitForVisibilityOfAddDescriptionButton().isDescriptionTextareaPresent());

        Assert.assertTrue(homePage.clickJenkinsHeadIcon()
                .waitForVisibilityOfAddDescriptionButton().getAddDescriptionButton().isEnabled());
    }
}