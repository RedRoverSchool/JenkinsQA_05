import model.HomePage;
import model.StatusUserPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class UserProfileTest extends BaseTest {

    private static final By USER_ICON = By.xpath("//a[contains(@href, 'user')]");
    private static final By ADD_DES = By.id("description-link");
    private static final By INPUT_FIELD = By.tagName("textarea");
    private static final By SAVE_BUTTON = By.id("yui-gen1-button");
    private static final String TEXT = RandomStringUtils.randomAlphanumeric(50);
    private static final String TEXT_EDIT = RandomStringUtils.randomAlphanumeric(10);

    private WebDriverWait wait;

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return wait;
    }

    public void deleteDescription() {
        getWait().until(ExpectedConditions.elementToBeClickable(ADD_DES)).click();
        getDriver().findElement(INPUT_FIELD).clear();
        getDriver().findElement(SAVE_BUTTON).click();
    }

    @Test
    public void testUserProfileAddDescription() {
        String actualResult = new HomePage(getDriver())
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .inputTextInDescriptionField(TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, TEXT);
    }

    @Test
    public void testUserProfileHidePreviewDescription() {
        StatusUserPage statusUserPage = new HomePage(getDriver())
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .inputTextInDescriptionField(TEXT)
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(statusUserPage.isDisplayedPreviewField());
    }

    @Test
    public void testUserProfilePreviewDescription() {

        StatusUserPage statusUserPage = new HomePage(getDriver())
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .inputTextInDescriptionField(TEXT)
                .clickPreviewLink();

        Assert.assertEquals(statusUserPage.getPreviewText(), TEXT);
    }

    @Test(dependsOnMethods = "testUserProfileAddDescription")
    public void testUserProfileEditDescription() {

        getDriver().findElement(USER_ICON).click();
        getDriver().findElement(ADD_DES).click();
        getDriver().findElement(INPUT_FIELD).clear();
        getDriver().findElement(INPUT_FIELD).sendKeys(TEXT_EDIT);
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertTrue(getDriver().
                findElement(By.xpath("//div[contains(text(),'" + TEXT_EDIT + "')]")).isDisplayed());

        deleteDescription();
    }
}
