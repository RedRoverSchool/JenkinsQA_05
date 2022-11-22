import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class HeaderTest extends BaseTest {

    private static final By USER_ACCOUNT_LINK = By.xpath("//a[@class='model-link']//span");

    private void openUserDropdownMenu() {
        getDriver().findElement(
                By.cssSelector("header#page-header .jenkins-menu-dropdown-chevron")).click();
    }

    @Test
    public void testSeeNameIcon() {

        boolean actualResult = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"))
                .isDisplayed();

        Assert.assertTrue(actualResult);
    }

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
        openUserDropdownMenu();
        List<WebElement> userDropdownItems = getDriver().findElements(
                By.cssSelector(".first-of-type > .yuimenuitem"));
        int actualItemsCount = 0;
        StringBuilder actualNamesItems = new StringBuilder();
        for (WebElement item : userDropdownItems) {
            actualItemsCount++;
            actualNamesItems.append(item.getText()).append(" ");
        }

        Assert.assertEquals(actualItemsCount, 4);
        Assert.assertEquals(actualNamesItems.toString().trim(),
                "Builds Configure My Views Credentials");
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
    public void test_Logo_Head_icon_is_Seen(){

        Assert.assertTrue(getDriver().findElement(
                By.id("jenkins-head-icon")).isEnabled());

        Assert.assertTrue(getDriver().findElement(
                By.id("jenkins-head-icon")).isDisplayed());
    }

    @Test
    public void testUserDropdownMenuToOpenPageAdminConfigure() {
        openUserDropdownMenu();
        getDriver().findElement(
                By.cssSelector("ul > li:nth-of-type(2) span")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.cssSelector("div:nth-of-type(3) > .jenkins-section__title")).getText(),
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
}

