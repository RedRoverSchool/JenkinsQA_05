package tests;

import static runner.TestUtils.getRandomStr;

import model.HomePage;
import model.StatusUserPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class UserProfileTest extends BaseTest {

    private static final String TEXT = getRandomStr(50);
    private static final String TEXT_EDIT = getRandomStr(10);

    @Test
    public void testUserProfileAddDescription() {
        String actualUserDescription = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualUserDescription, TEXT);
    }

    @Test
    public void testUserProfileHidePreviewDescription() {
        StatusUserPage statusUserPage = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(TEXT)
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(statusUserPage.isDisplayedPreviewField());
    }

    @Test
    public void testUserProfilePreviewDescription() {
        String actualPreviewText = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(TEXT)
                .clickPreviewLink()
                .getPreviewText();

        Assert.assertEquals(actualPreviewText, TEXT);
    }

    @Test(dependsOnMethods = "testUserProfileAddDescription")
    public void testUserProfileEditDescription() {
        String actualUserDescription = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(TEXT_EDIT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualUserDescription, TEXT_EDIT);
    }
}
