package tests;

import model.HomePage;
import model.PeoplePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

import static runner.TestUtils.getRandomStr;

public class PeoplePageTest extends BaseTest {
    private static final String USER_NAME = runner.TestUtils.getRandomStr();
    private static final String PASSWORD = getRandomStr(7);
    private static final String EMAIL = getRandomStr(5) + "@gmail.com";

    @Test
    public void testPeoplePageContent() {
        PeoplePage peoplePage = new HomePage(getDriver())
                .clickPeople();
        Assert.assertEquals(peoplePage.getNameOfHeader(), "People");
        Assert.assertEquals(peoplePage.getDescription(), "Includes all known “users”," +
                " including login identities which the current security realm can enumerate," +
                " as well as people mentioned in commit messages in recorded changelogs.");
        Assert.assertTrue(peoplePage.isDisplayedSidePanel(), "Side Panel is not displayed");
        Assert.assertTrue(peoplePage.getFooter().isDisplayedFooter(), "Footer is not displayed");
        Assert.assertEquals(peoplePage.getPeopleTableColumnsAmount(), 5);
        Assert.assertEquals(peoplePage.getPeopleTableColumnsAsString(), "User ID Name Last Commit Activity On");
        Assert.assertEquals(peoplePage.getIconLabel(), "Icon:");
        Assert.assertEquals(peoplePage.getListIconSizeButtonsAsString(), "Small Medium Large");

    }

    @Test
    public void testFindUserInThePeopleSection() {
        PeoplePage peoplePage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(USER_NAME)
                .setEmail(EMAIL)
                .clickCreateUserButton()
                .rootMenuDashboardLinkClick()
                .clickPeople();

        Assert.assertTrue(peoplePage.getListOfUsers().contains(USER_NAME), USER_NAME + " not found");
    }

    @Test(dependsOnMethods = "testFindUserInThePeopleSection")
    public void testPeopleDeleteUser() {
        PeoplePage peoplePage = new PeoplePage(getDriver())
                .rootMenuDashboardLinkClick()
                .clickManageJenkins()
                .clickManageUsers()
                .clickDeleteUser(USER_NAME)
                .clickYes()
                .rootMenuDashboardLinkClick()
                .clickPeople();

        Assert.assertFalse(peoplePage.getListOfUsers().contains(USER_NAME),USER_NAME + " wasn't deleted");
    }

    @Test
    public void testCreateUser() {
        List<String> userList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(USER_NAME)
                .setEmail(EMAIL)
                .clickCreateUserButton()
                .rootMenuDashboardLinkClick()
                .clickPeople()
                .getListOfUsers();

        Assert.assertTrue(userList.contains(USER_NAME), USER_NAME + " not found");
    }

    @Test
    public void testViewPeoplePage() {
        var peoplePage = new HomePage(getDriver())
                .clickPeople();

        Assert.assertEquals(peoplePage.getNameOfHeader(), "People");
    }
}
