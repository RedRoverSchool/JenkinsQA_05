package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Ignore
public class NoGroupTest extends BaseTest {

    @Ignore
    @Test
    public void testVerifyTextBoxOutputResult() {
        getDriver().get("https://demoqa.com/");

        final String name = "TestUser";
        final String email = "userTest@gmail.com";
        final String currentAdr = "123 Main Street";
        final String permanentAdr = "123 Main Street";

        final List<String> expectedResult = new ArrayList<>();
        expectedResult.add(String.format("Name:%s", name));
        expectedResult.add(String.format("Email:%s", email));
        expectedResult.add(String.format("Current Address :%s", currentAdr));
        expectedResult.add(String.format("Permananet Address :%s", permanentAdr));

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

        getDriver().findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']")).click();
        getDriver().findElement(By.xpath("//div[@class='element-list collapse show']//li[@id='item-0']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userName")));
        getDriver().findElement(By.id("userName")).click();
        getDriver().findElement(By.id("userName")).sendKeys(name);
        getDriver().findElement(By.id("userEmail")).sendKeys(email);
        getDriver().findElement(By.id("currentAddress")).sendKeys(currentAdr);
        getDriver().findElement(By.id("permanentAddress")).sendKeys(permanentAdr);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        getDriver().findElement(By.id("submit")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='output']/div")));

        final List<String> actualResult = new ArrayList<>();
        actualResult.add(getDriver().findElement(By.id("name")).getText());
        actualResult.add(getDriver().findElement(By.id("email")).getText());
        actualResult.add(getDriver().findElement(By.cssSelector("#output #currentAddress")).getText());
        actualResult.add(getDriver().findElement(By.cssSelector("#output #permanentAddress")).getText());

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testSearchResults() {
        final String expectedResult = "en.wikipedia.org";

        getDriver().get("https://ya.ru/");

        getDriver().findElement(By.id("text")).sendKeys("Vilnius");
        getDriver().findElement(By.xpath("//*[@type='submit']")).click();

        String actualResult = getDriver().findElement(By.xpath("//b[contains(text(), 'en.wikipedia.org')]"))
                .getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testModaldialogs_MariaSh() {
        getDriver().get("https://demoqa.com/modal-dialogs");

        getDriver().findElement(By.id("showSmallModal")).click();

        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.id("closeSmallModal")).click();

        Assert.assertTrue(getDriver().findElement(By.id("showLargeModal")).isDisplayed());
    }

    @Test
    public void testGoFormHerokuApp() {
        final String expectedResult = "https://formy-project.herokuapp.com/form";

        getDriver().get("https://formy-project.herokuapp.com");

        WebElement navFormLink = getDriver().findElement(
                By.xpath("//div[@id='navbarNavDropdown']/ul/li[1]/a[@href='/form']"));
        navFormLink.click();

        Assert.assertEquals(navFormLink.getText(), "Form");

        String actualResult = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult,expectedResult);
    }
}

