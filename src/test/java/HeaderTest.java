import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.Locale;

public class HeaderTest extends BaseTest {

    private static final By USER_ACCOUNT_LINK = By.xpath("//a[@class='model-link']//span");

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
}

