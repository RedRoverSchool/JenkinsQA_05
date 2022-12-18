package tests;

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

    private static final String TEXT = RandomStringUtils.randomAlphanumeric(50);
    private static final String TEXT_EDIT = RandomStringUtils.randomAlphanumeric(10);
    
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
        String actualResult = new HomePage(getDriver())
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .inputTextInDescriptionField(TEXT_EDIT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, TEXT_EDIT);
    }
}
