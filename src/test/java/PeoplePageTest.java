import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

public class PeoplePageTest extends BaseTest {

    private static final String USER_NAME = runner.TestUtils.getRandomStr();

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
        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/manage']"))).click();
        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='securityRealm/']"))).click();
        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='addUser']"))).click();

        getWait(2).until(ExpectedConditions.presenceOfElementLocated(By.id("username"))).sendKeys(USER_NAME);
        getWait(2).until(ExpectedConditions.presenceOfElementLocated(By.name("password1"))).sendKeys("1234567890");
        getWait(2).until(ExpectedConditions.presenceOfElementLocated(By.name("password2"))).sendKeys("1234567890");
        getWait(2).until(ExpectedConditions.presenceOfElementLocated(By.name("fullname"))).sendKeys(USER_NAME);
        getWait(2).until(ExpectedConditions.presenceOfElementLocated(By.name("email"))).sendKeys("email@email.com");
        getWait(2).until(ExpectedConditions.elementToBeClickable(By.id("yui-gen1-button"))).click();

        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/'] "))).click();
        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/asynchPeople/'] "))).click();

        List<WebElement> list = getWait(2).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("jenkins-table__link")));
        List<String> lst = list.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(lst.contains(USER_NAME));
    }

    @Test(dependsOnMethods = "testFindUserInThePeopleSection")
    public void testPeopleDeleteUser() {
        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/'] "))).click();

        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/manage']"))).click();
        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='securityRealm/']"))).click();

        getWait(2).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='user/" + USER_NAME.toLowerCase() + "/delete']"))).click();

        getWait(2).until(ExpectedConditions.elementToBeClickable(By.id("yui-gen1-button"))).click();

        List<WebElement> list = getWait(2).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("jenkins-table__link")));
        List<String> lst = list.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertFalse(lst.contains(USER_NAME));
    }
}