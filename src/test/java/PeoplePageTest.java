import model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

import static runner.TestUtils.getRandomStr;

public class PeoplePageTest extends BaseTest {


    private static final String user_name = runner.TestUtils.getRandomStr();
    private static final String password = getRandomStr(7);
    private static final String email = getRandomStr(5) + "@gmail.com";

    @Test
    public void testPeoplePage() {
        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        getDriver().findElement(By.xpath("//h1[contains(text(),'People')]")).isDisplayed();
        getDriver().findElement(By.xpath("//p[@class='jenkins-description']")).isDisplayed();

        List<WebElement> sizes = getDriver().findElements(By.tagName("//ol/li"));
        for (WebElement eachSize : sizes) {
            eachSize.click();

            List<WebElement> columns = getDriver().findElements(By.tagName("//th"));
            for (WebElement columnName : columns) {
                columnName.click();

                getDriver().findElement(By.id("side-panel")).isDisplayed();
                getDriver().findElement(By.id("footer")).isDisplayed();

                Assert.assertTrue(getDriver().findElement(By.id("main-panel")).isDisplayed());
            }
        }
    }

    @Test
    public void testFindUserInThePeopleSection() {
        PeoplePage peoplePage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(user_name)
                .setPassword(password)
                .confirmPassword(password)
                .setFullName(user_name)
                .setEmail(email)
                .clickCreateUserButton()
                .rootMenuDashboardLinkClick()
                .clickPeople();

        Assert.assertTrue(peoplePage.getListOfUSersInPeople().contains(user_name));
    }

    @Test(dependsOnMethods = "testFindUserInThePeopleSection")
    public void testPeopleDeleteUser() {
        DeleteUserPage deleteUserPage = new PeoplePage(getDriver())
                .rootMenuDashboardLinkClick()
                .clickManageJenkins()
                .clickManageUsers()
                .clickDeleteUser(user_name)
                .clickYes();

        Assert.assertFalse(deleteUserPage.getListOfUsers().contains(user_name));
    }
}