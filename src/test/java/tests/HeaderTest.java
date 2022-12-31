package tests;

import model.*;
import model.views.MyViewsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class HeaderTest extends BaseTest {

    @Test
    public void testIsJenkinsNameIconExist() {

        boolean actualResult = new HomePage(getDriver()).isJenkinsNameIconDisplayed();

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

        Assert.assertTrue(homePage.isJenkinsHeadIconDisplayed());
        Assert.assertTrue(homePage.isJenkinsHeadIconEnabled());
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

        Assert.assertEquals(myViewsPage.getMyViewsTopMenuLinkText(),
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

        String actualURL = new HomePage(getDriver())
                .clickNewItem()
                .clickJenkinsNameIcon()
                .getCurrentURL();

        Assert.assertEquals(actualURL, "http://localhost:8080/");
    }

    @Test
    public void testCheckTheAppropriateSearchResult() {
        String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 1000);
        String searchRequest = "organiza";

        List<String> searchResults = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(organizationFolderName)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
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

        Assert.assertTrue(homePage.clickAddDescriptionButton().isDescriptionTextareaEnabled());
        Assert.assertFalse(homePage.isAddDescriptionButtonPresent());
        Assert.assertFalse(homePage.clickJenkinsHeadIcon()
                .waitForVisibilityOfAddDescriptionButton().isDescriptionTextareaPresent());
        Assert.assertTrue(homePage.clickJenkinsHeadIcon()
                .waitForVisibilityOfAddDescriptionButton().isAddDescriptionButtonEnabled());
    }
}