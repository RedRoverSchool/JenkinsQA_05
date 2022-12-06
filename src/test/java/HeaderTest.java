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

    private void openUserDropdownMenu() {
        getDriver().findElement(
                By.cssSelector("header#page-header .jenkins-menu-dropdown-chevron")).click();
    }

    private void createOrganizationFolder() {
        for(int i = 1; i <= 4; i++) {
            String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 1000);

            getDriver().findElement(By.linkText("New Item")).click();
            getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
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
        getDriver().findElement(
                By.cssSelector("header#page-header .jenkins-menu-dropdown-chevron")).click();
        List<WebElement> userDropdownItems = getDriver().findElements(
                By.cssSelector(".first-of-type > .yuimenuitem"));
        int actualItemsCount = 0;
        StringBuilder actualNamesItems = new StringBuilder();
        for (WebElement item : userDropdownItems) {
            actualItemsCount++;
            actualNamesItems.append(item.getText());
        }

        Assert.assertEquals(actualItemsCount, 4);
        Assert.assertEquals(actualNamesItems.toString(),
                "BuildsConfigureMy ViewsCredentials");
    }

    @Test
    public void testUserDropdownMenuToOpenPageAdminBuilds() {
        openUserDropdownMenu();
        getDriver().findElement(
                By.cssSelector("ul > li:nth-of-type(1) span")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("div#main-panel > h1")).getText(),
                "Builds for admin");
    }

    @Test
    public void test_Logo_HeadIconIsSeen() {

        Assert.assertTrue(getDriver().findElement(
                By.id("jenkins-head-icon")).isEnabled());

        Assert.assertTrue(getDriver().findElement(
                By.id("jenkins-head-icon")).isDisplayed());
    }

    @Test
    public void test_Manage_Jenkins_ClickNameIconToReturnToTheMainPage() {
        getDriver().findElement(
                        By.xpath("//div[@id='tasks']//a[@href='/manage']")).
                click();

        Assert.assertEquals(getDriver().getCurrentUrl(),
                "http://localhost:8080/manage/");

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(),
                "http://localhost:8080/");
    }

    @Test
    public void testUserDropdownMenuToOpenPageAdminConfigure() {
        openUserDropdownMenu();
        getDriver().findElement(
                By.cssSelector("ul > li:nth-of-type(2) span")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("div:nth-of-type(3) > .jenkins-section__title")).getText(),
                "API Token");
    }

    @Test
    public void testUserDropdownMenuToOpenPageAdminMyViews() {
        openUserDropdownMenu();
        getDriver().findElement(
                By.cssSelector("ul > li:nth-of-type(3) span")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//ul[@id='breadcrumbs']//a[@href='/user/admin/my-views/']")).getText(),
                "My Views");
    }

    @Test
    public void testUserDropdownMenuToOpenPageAdminCredentials() {
        openUserDropdownMenu();
        getDriver().findElement(
                By.cssSelector("li:nth-of-type(4) span")).click();

        Assert.assertEquals(
                getDriver().findElement(By.tagName("h1")).getText(),
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

    @Ignore
    @Test
    public void testCheckTheAppropriateSearchResult(){
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
    public void test_Logo_HeadIcon_ReloadMainPage(){
        getDriver().findElement(By.id("description-link")).click();

        Assert.assertTrue(getDriver().findElement(
                        By.xpath("//div[@id='description']//textarea")).
                isDisplayed());

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertTrue(getDriver().findElement(
                By.id("description-link")).isDisplayed());
    }
}

