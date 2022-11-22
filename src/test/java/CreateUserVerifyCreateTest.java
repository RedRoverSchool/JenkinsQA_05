import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CreateUserVerifyCreateTest extends BaseTest {

    private static final By BUTTON_MANAGE_JENKINS = By.xpath("//a[@href='/manage']");
    private static final By BUTTON_MANAGE_USERS = By.xpath("//a[@href='securityRealm/']");
    private static final By BUTTON_CREATE_USERS = By.xpath("//a[@href='addUser']");
    private static final By BUTTON_FINAL_CREATE_USER = By.id("yui-gen1-button");
    private final String randUserName = getRandomDigitAndLetterString();


    @Ignore
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

    @Ignore
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

    @Test
    public void testCreateUserOfJenkins() {

        String randomUsername = getRandomDigitAndLetterString();
        String randomPasswordAndConfirmPassword = getRandomDigitAndLetterString();
        String randomFullName = getRandomDigitAndLetterString();
        String randomEmail = getRandomDigitAndLetterString();

        getDriver().findElement(BUTTON_MANAGE_JENKINS).click();
        getDriver().findElement(BUTTON_MANAGE_USERS).click();
        getDriver().findElement(BUTTON_CREATE_USERS).click();
        getDriver().findElement(By.id("username")).sendKeys(randomUsername);
        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys(randomPasswordAndConfirmPassword);
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys(randomPasswordAndConfirmPassword);
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys(randomFullName);
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys(randomEmail + "@gmail.com");
        getDriver().findElement(BUTTON_FINAL_CREATE_USER).click();

        List<WebElement> tableListOfUsers = getDriver().findElements(By.xpath("//table[@id='people']//tbody//tr//td"));
        List<String> userNameFromListTable = tableListOfUsers.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(userNameFromListTable.contains(randomUsername));
    }
}
