package group_a_team;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SauceDemoInventoryPageTest extends SauceDemoBaseTest {

    @BeforeMethod
    private void navigateToPage() {
        loginIn(STANDARD_USER, CORRECT_PASSWORD);
    }

    @Test
    public void testSidebarMenuForItems() {
        getAction().moveToElement(getDriver().findElement(By.id("react-burger-menu-btn"))).click().perform();
        Assert.assertTrue(getDriver().findElement(By.cssSelector("div.bm-menu")).isDisplayed());

        List<String> expectedMenuItemNames = Arrays.asList("ALL ITEMS", "ABOUT", "LOGOUT", "RESET APP STATE");
        List<WebElement> actualMenuItems = new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(getDriver().findElements(By.xpath("//nav[@class='bm-item-list']/a"))));
        List<String> actualMenuItemNames = new ArrayList<>();
        actualMenuItems.stream().forEach(webElement -> actualMenuItemNames.add(webElement.getText()));

        Assert.assertEquals(actualMenuItemNames, expectedMenuItemNames);
    }
}