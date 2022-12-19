package tests;

import model.HomePage;
import model.ManageOldDataPage;
import model.StatusUserPage;

import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

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
}
