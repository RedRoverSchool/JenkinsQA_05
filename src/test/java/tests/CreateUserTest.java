package tests;

import model.HomePage;
import model.ManageUsersPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;
import static runner.TestUtils.getRandomStr;

public class CreateUserTest extends BaseTest {
    private static final String USERNAME = getRandomStr(10);
    private static final String PASSWORD = getRandomStr(7);
    private static final String FULLNAME = getRandomStr(5);
    private static final String EMAIL = getRandomStr(5) + "@gmail.com";

    @Test
    public void testCreateUser() {
        ManageUsersPage homePage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(FULLNAME)
                .setEmail(EMAIL)
                .clickCreateUserButton();

        Assert.assertTrue(homePage.getListOfUsers().contains(USERNAME), USERNAME + " username not found");
        Assert.assertTrue(homePage.getListOfUsers().contains(FULLNAME), FULLNAME + " fullname not found");
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        List<String> listOfUsers = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickDeleteUser(USERNAME)
                .clickYesToManageUsersPage().getListOfUsers();

        Assert.assertFalse(listOfUsers.contains(USERNAME));
    }
}
