import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class PLGroupTest extends BaseTest {

    @Test
    public void testH2HeaderSeleniumInteractionsWindows() {
        getDriver().get("https://www.selenium.dev/documentation/webdriver/interactions/windows/");

        String expectedResult = "Working with windows and tabs";

        Assert.assertEquals(getDriver().findElement(By.xpath("//main/div/h1")).getText(), expectedResult);
    }

    @Test
    void testNavTest_Main() {
        getDriver().get("https://www.selenium.dev/documentation/webdriver/interactions/windows/");

        String expectedResult = "Java".concat("Python");

        List<WebElement> countTableColumns = getDriver().findElements(By.xpath("//main//ul[@id='tabs-0']/li"));

        String columnNames = "";
        for (WebElement name : countTableColumns) {
            columnNames += name.getText();
        }

        Assert.assertTrue(columnNames.contains(expectedResult));
        Assert.assertEquals(countTableColumns.size(), 6);
    }

    @Test
    public void testParagraph() {
        getDriver().get("https://www.selenium.dev/");

        WebElement link = getDriver().findElement(
                By.xpath("//div//p[text()='What you do with that power is entirely up to you.']")
        );

        Assert.assertEquals(link.getText(), "What you do with that power is entirely up to you.");
    }

    @Test
    public void testSubMenu() throws InterruptedException {

        getDriver().get("https://openweathermap.org/");

        getDriver().manage().window().maximize();
        WebElement support = getDriver().findElement(By.xpath(" //div[@id='support-dropdown']"));

        Thread.sleep(5000);
        support.click();

        List<WebElement> allSupportMenu = getDriver().findElements(
                By.xpath("//ul[@class='dropdown-menu dropdown-visible']/li/a")
        );
        String actualResult = "";
        for (WebElement supportMenu : allSupportMenu) {
            actualResult += supportMenu.getText() + " ";
        }

        Assert.assertEquals(actualResult, "FAQ How to start Ask a question ");

    }

    @Test
    public void testConfirmTemperatureFaringate() throws InterruptedException {

        getDriver().get("https://openweathermap.org/");

        WebElement imperialF = getDriver().findElement(
                By.xpath("//div[text()='Imperial: °F, mph']")
        );
        Thread.sleep(5000);
        imperialF.click();

        WebElement faringate = getDriver().findElement(
                By.xpath("//div[@class = 'current-temp']/span")
        );
        Thread.sleep(5000);
        String actualResult = faringate.getText();

        Assert.assertTrue(actualResult.contains("F"));

    }

    @Test
    public void testOpenPageCooker() {
        WebDriver driver = getDriver();

        String url = "https://www.russianfood.com/";
        String recipePie = "Быстрый пирог-шарлотка с яблоками\n";
        String expectedResult = "Быстрый пирог-шарлотка с яблоками";

        driver.get(url);

        WebElement searchFormField = driver.findElement(
                By.xpath("//form[@action='/search/simple/index.php#beforesearchform']/div/div/div/div/input[@class='search-form__input']")
        );
        searchFormField.click();
        searchFormField.sendKeys(recipePie);

        WebElement searchButton = driver.findElement(
                By.xpath("//button[@type='submit']")
        );
        searchButton.click();

        WebElement choiceFindPie = driver.findElement(
                By.xpath("//a[@name='el127921']")
        );
        choiceFindPie.click();

        WebElement h1RecipePieHeader = driver.findElement(
                By.xpath("//h1[@class ='title ']")
        );

        String actualResult = h1RecipePieHeader.getText();

        Assert.assertEquals(actualResult,expectedResult);
    }
}

