import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CreateUserVerifyCreateTest extends BaseTest {

    private String randUserName = getRandomDigitAndLetterString();


    public static String getRandomDigitAndLetterString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Test
    public void testCreateUser() throws InterruptedException {

        String randFullName = getRandomDigitAndLetterString();
        String randEmail = getRandomDigitAndLetterString();
        String randPass = getRandomDigitAndLetterString();
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//a[@href='securityRealm/']"))).click().perform();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
        getDriver().findElement(By.id("username")).sendKeys(randUserName);
        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys(randPass);
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys(randPass);
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys(randFullName);
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys(randEmail + "@gmail.com");
        getDriver().findElement(By.cssSelector("#yui-gen1")).click();

        List<WebElement> listWithTd = getDriver().findElements(By.xpath("//table[@id='people']//tbody//tr//td"));
        List<String> lst = listWithTd.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(lst.contains(randUserName));
        Assert.assertTrue(lst.contains(randFullName));

        getDriver().findElement(By.xpath("//div[@id='tasks']/div/span/a[1]")).click();
        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        List<WebElement> listWithIdsOfPerson = getDriver().findElements(By.xpath("//table[@id='people']//tr//td"));
        List<String> lstStringWithNamesOnPeoplePage = listWithIdsOfPerson.stream().map(e -> e.getAttribute("innerText")).collect(Collectors.toList());

        Assert.assertTrue(lstStringWithNamesOnPeoplePage.contains(randUserName));

    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testToDeleteUser() {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//a[@href='securityRealm/']"))).click().perform();
        WebElement deleteButton = getDriver().findElement(By.xpath("//a[@href='user/"+randUserName.toLowerCase()+"/delete']"));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        List<WebElement> listWithUsersIncludingAdmin = getDriver().findElements(By.xpath("//table[@id='people']/tbody/tr/td[2]/a"));
        List<String> listStringov = listWithUsersIncludingAdmin.stream().map(WebElement::getText).collect(Collectors.toList());
        if (listStringov.contains(randUserName)) {
            deleteButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@action='doDelete']")));
            getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='Users']")));
        }
        List<WebElement> listWithUsersIncludingAdmin2 = getDriver().findElements(By.xpath("//table[@id='people']/tbody/tr/td[2]/a"));
        List<String> listStringovFinal = listWithUsersIncludingAdmin2.stream().map(WebElement::getText).collect(Collectors.toList());


        Assert.assertFalse(listStringovFinal.contains(randUserName));
    }

}
