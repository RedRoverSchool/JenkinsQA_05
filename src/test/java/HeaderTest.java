import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class HeaderTest extends BaseTest {

    private static final By USER_ACCOUNT_LINK = By.xpath("//a[@class='model-link']//span");

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
}

