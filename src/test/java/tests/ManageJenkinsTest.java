package tests;

import model.HomePage;
import model.ManageOldDataPage;
import model.StatusUserPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class ManageJenkinsTest extends BaseTest {

    private static final String NEW_USERS_FULLNAME = TestUtils.getRandomStr(6);

    @Test
    public void testRenameFullUserName() {
        final String newUsersFullName = TestUtils.getRandomStr(6);
        StatusUserPage userStatusPage = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickManageUsers()
                .clickConfigureUser()
                .clearInputFieldFullUserName()
                .inputNameInFieldFullUserName(NEW_USERS_FULLNAME)
                .clickSaveButton()
                .refreshPage();

        Assert.assertEquals(userStatusPage.getPageHeaderUserName(), NEW_USERS_FULLNAME);
        Assert.assertEquals(userStatusPage.getBreadcrumbsUserName(), NEW_USERS_FULLNAME);
        Assert.assertEquals(userStatusPage.getH1Title(), NEW_USERS_FULLNAME);
    }

    @Test(dependsOnMethods = "testRenameFullUserName")
    public void testNewFullnameAfterReLogging() {
         new HomePage(getDriver())
                .getHeader()
                .clickLogOut();
         loginWeb();

        Assert.assertEquals(new HomePage(getDriver()).getUserName(), NEW_USERS_FULLNAME);
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

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'&'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'<'}, {'>'}, {'.'}, {','}};
    }

    @Test(dataProvider = "specialCharacters")
    public void testCreateUserWithIncorrectCharactersInName(Character specialCharacter) {
        String password = TestUtils.getRandomStr(10);

        String errorMessageWhenIncorrectCharacters = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(String.valueOf(specialCharacter))
                .setPassword(password)
                .confirmPassword(password)
                .setFullName(TestUtils.getRandomStr(10))
                .setEmail(TestUtils.getRandomStr(10) + "@gmail.com")
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenIncorrectCharacters, "User name must only contain alphanumeric characters, underscore and dash");
    }
}
