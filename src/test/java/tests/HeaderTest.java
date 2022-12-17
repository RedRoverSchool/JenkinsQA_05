package tests;

import model.*;
import model.views.MyViewsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class HeaderTest extends BaseTest {

    private static final By USER_ACCOUNT_LINK = By.xpath("//a[@class='model-link']//span");

    private void createOrganizationFolder() {
        for (int i = 1; i <= 4; i++) {
            String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 1000);

            getDriver().findElement(By.linkText("New Item")).click();
            getWait(5).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins_branch_OrganizationFolder"))).click();
            getDriver().findElement(By.xpath("//input [@name = 'name']")).sendKeys(organizationFolderName);
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.id("yui-gen15-button")).click();
            getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        }
    }

    @Test
    public void testSeeNameIcon() {

        boolean actualResult = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"))
                .isDisplayed();

        Assert.assertTrue(actualResult);
    }

    @Ignore
    @Test
    public void testUserIdInUserAccountLinkAndInUserPage() {
        String usernameInUserAccountLink = getDriver().findElement(USER_ACCOUNT_LINK).getText();

        getDriver().findElement(USER_ACCOUNT_LINK).click();
        String usernameInUserPage = getDriver().findElement(
                By.xpath("//div[@id='main-panel']/div[contains(text(), 'ID')]")).getText();

        Assert.assertEquals(usernameInUserPage, String.format("Jenkins User ID: %s", usernameInUserAccountLink));
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
        createOrganizationFolder();

        getDriver().findElement(By.id("search-box")).sendKeys("organiza");
        getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);
        List<WebElement> listSearchResult = getDriver().findElements(By.xpath("//div[@id='main-panel']/ol/li"));

        Assert.assertTrue(listSearchResult.size() > 0);

        for (WebElement a : listSearchResult) {
            Assert.assertTrue(a.getText().toLowerCase().contains("organiza"));
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

