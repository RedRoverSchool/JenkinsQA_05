package tests;

import model.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    @Test
    public void testRenameFullUserName() {
        final String newUsersFullName = TestUtils.getRandomStr(6);
        StatusUserPage userStatusPage = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickManageUsers()
                .clickConfigureUser()
                .clearInputFieldFullUserName()
                .inputNameInFieldFullUserName(newUsersFullName)
                .clickSaveButton()
                .refreshPage();

        Assert.assertEquals(userStatusPage.getPageHeaderUserName(), newUsersFullName);
        Assert.assertEquals(userStatusPage.getBreadcrumbsUserName(), newUsersFullName);
        Assert.assertEquals(userStatusPage.getH1Title(), newUsersFullName);
    }

    @Test
    public void testManageOldData() {
        ManageOldDataPage page = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickLinkManageOldData();

        Assert.assertEquals(page.getMainPanelNoticeText(), "No old data was found.");
    }

    @Test
    public void testPluginManagerInstallPlugin() {
        final String pluginName = "TestNG Results";

        String notice = new HomePage(getDriver())
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkAvailable()
                .inputValueToSearchRow(pluginName)
                .clickCheckBoxTestNGResults()
                .clickButtonInstallWithoutRestart()
                .clickButtonGoBackToTopPage()
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkInstalled()
                .inputValueToSearchRow(pluginName)
                .getResultFieldText();

        Assert.assertTrue(notice.contains("Failed to load: TestNG Results Plugin"));
    }

    @Test
    public void testCreateUserWithEmptyName() {
        String password = TestUtils.getRandomStr(10);

        String errorMessageWhenEmptyUserName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setPassword(password)
                .confirmPassword(password)
                .setFullName(TestUtils.getRandomStr(10))
                .setEmail(TestUtils.getRandomStr(10) + "@gmail.com")
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenEmptyUserName, "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testCreateUserWithIncorrectCharactersInName() {
        List<Character> incorrectNameCharacters =
                List.of('!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>', '.', ',');
        String password = TestUtils.getRandomStr(10);

        CreateUserPage createUserPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser();

        for (Character character : incorrectNameCharacters) {
            String errorMessageWhenIncorrectCharacters = createUserPage.clearUserName()
                    .setUsername(String.valueOf(character))
                    .setPassword(password)
                    .confirmPassword(password)
                    .setFullName(TestUtils.getRandomStr(10))
                    .setEmail(TestUtils.getRandomStr(10) + "@gmail.com")
                    .clickCreateUserAndGetErrorMessage();

            Assert.assertEquals(errorMessageWhenIncorrectCharacters, "User name must only contain alphanumeric characters, underscore and dash");
        }
    }
}
