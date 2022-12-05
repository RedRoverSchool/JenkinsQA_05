import java.time.Duration;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class CopyItemTest extends BaseTest {

    private static final By BY_TEXT_ERROR_MESSAGE_HEADER = By.xpath("//div[@id='main-panel']/h1");
    private static final By BY_TEXT_ERROR_MESSAGE_DESCRIPTION = By.xpath("//div[@id='main-panel']/p");
    private static final By BY_JENKINS_HOME_LINK = By.xpath("//a[@id='jenkins-home-link']");
    private static final By BY_LINK_NEW_ITEM = By.linkText("New Item");
    private static final By BY_FIELD_NAME = By.xpath("//input[@id='name']");
    private static final By BY_FIELD_FROM = By.xpath("//input[@id='from']");
    private static final By BY_BUTTON_OK = By.xpath("//button[@id='ok-button']");
    private static final By BY_BUTTON_SAVE = By.xpath("//button[@type='submit']");
    private static final By BY_RADIO_BUTTON_FREE_STYLE_PROJECT =
            By.xpath("//li[contains(@class, 'FreeStyleProject')]");

    @Test
    public void testCopyFromNotExistItemName() {
        final String nameItem = RandomStringUtils.randomAlphanumeric(10);
        final String nameNotExistItem = RandomStringUtils.randomAlphanumeric(10);
        final String nameExistItem = RandomStringUtils.randomAlphanumeric(10);
        final String expectedURL = "/view/all/createItem";
        final String expectedErrorMessage = "Error No such job: " + nameNotExistItem;
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        createItem(BY_RADIO_BUTTON_FREE_STYLE_PROJECT, nameExistItem);

        getDriver().findElement(BY_LINK_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_NAME).sendKeys(nameItem);
        wait.until(ExpectedConditions.attributeToBe(BY_FIELD_NAME, "value", nameItem));
        getDriver().findElement(BY_RADIO_BUTTON_FREE_STYLE_PROJECT).click();
        getDriver().findElement(BY_FIELD_FROM).sendKeys(nameNotExistItem);
        wait.until(ExpectedConditions.attributeToBe(BY_FIELD_FROM, "value", nameNotExistItem));
        wait.until(ExpectedConditions.elementToBeClickable(BY_BUTTON_OK));
        getDriver().findElement(BY_BUTTON_OK).click();
        wait.until(ExpectedConditions.urlContains(expectedURL));

        String actualErrorMessage =
                getDriver().findElement(BY_TEXT_ERROR_MESSAGE_HEADER).getText() +
                        " " + getDriver().findElement(BY_TEXT_ERROR_MESSAGE_DESCRIPTION).getText();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        Assert.assertFalse(isItemAtTheDashboard(nameItem), "Item " + nameItem + " at the Dashboard");
    }

    @Test
    public void testFieldCopyFromDoNotDisplayIfDoNotHaveAnyItems() {
        getDriver().findElement(BY_LINK_NEW_ITEM).click();
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(ExpectedConditions.visibilityOfElementLocated(BY_FIELD_NAME));

        Assert.assertFalse(isElementExistInDOM("id=\"from\""));
    }

    private boolean isItemAtTheDashboard(String nameItem) {
        getDriver().findElement(BY_JENKINS_HOME_LINK).click();
        return getDriver().getPageSource().contains("id=\"job_" + nameItem + "\"");
    }

    private boolean isElementExistInDOM(String htmlElement) {
        return getDriver().getPageSource().contains(htmlElement);
    }

    private void createItem(By radioButtonTypeItemInTheCreatePage, String nameItem) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        getDriver().findElement(BY_JENKINS_HOME_LINK).click();
        getDriver().findElement(BY_LINK_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_NAME).sendKeys(nameItem);
        getDriver().findElement(radioButtonTypeItemInTheCreatePage).click();
        wait.until(ExpectedConditions.elementToBeClickable(BY_BUTTON_OK));
        getDriver().findElement(BY_BUTTON_OK).click();
        wait.until(ExpectedConditions.elementToBeClickable(BY_BUTTON_SAVE));
        getDriver().findElement(BY_BUTTON_SAVE).click();
        getDriver().findElement(BY_JENKINS_HOME_LINK).click();
    }
}
