package group_a_team_test;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SauceDemoInventoryPageTest extends SauceDemoBaseTest {

    @BeforeMethod
    private void navigateToPage() {
        loginIn(SauceDemoConsts.STANDARD_USER, SauceDemoConsts.CORRECT_PASSWORD);
    }

    @Test
    public void testSidebarMenuForItems() {
        clickOnSidebarMenuBtn();

        List<String> expectedMenuItemNames = List.of("ALL ITEMS", "ABOUT", "LOGOUT", "RESET APP STATE");
        List<WebElement> actualMenuItems = new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(getDriver().findElements(By.xpath("//nav[@class='bm-item-list']/a"))));
        List<String> actualMenuItemNames = new ArrayList<>();
        actualMenuItems.stream().forEach(webElement -> actualMenuItemNames.add(webElement.getText()));

        Assert.assertEquals(actualMenuItemNames, expectedMenuItemNames);
    }

    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testAllItemsLinkFromSidebarMenu() {
        goThrowLinkOfSidebarMenu("inventory_sidebar_link");
        Assert.assertEquals(getDriver().getCurrentUrl(), SauceDemoConsts.INVENTORY_PAGE_URL);
    }

    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testAboutUsLinkFromSideBarMenu() {
        goThrowLinkOfSidebarMenu("about_sidebar_link");
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://saucelabs.com/");
    }
    @Ignore
    @Test(dependsOnMethods = "testSidebarMenuForItems")
    public void testLogOutFromSideBarMenu() {
        goThrowLinkOfSidebarMenu("logout_sidebar_link");
        Assert.assertEquals(getDriver().getCurrentUrl(), SauceDemoConsts.URL);
    }

    private void goThrowLinkOfSidebarMenu(String locator) {
        clickOnSidebarMenuBtn();
        WebElement link = new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id(locator))));

        getAction().moveToElement(link).click().perform();
    }

    private void clickOnSidebarMenuBtn() {
        getAction().moveToElement(getDriver().findElement(By.id("react-burger-menu-btn"))).click().perform();
    }
}