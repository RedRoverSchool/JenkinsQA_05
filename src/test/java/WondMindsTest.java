import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class WondMindsTest extends BaseTest {

    @Test
    public void testGorodTulaTheBest() {
        getDriver().get("https://rp5.ru");
        WebElement search = getDriver().findElement(By.name("searchStr"));
        search.sendKeys("Тула\n");
        String actualText = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualText, "Search result");
    }

    @Test
    public void testGuide() throws InterruptedException {
        String url = "https://openweathermap.org/";
        String expectedResultTitle = "OpenWeatherMap API guide - OpenWeatherMap";
        String expectedResultUrl = "https://openweathermap.org/guide";

        getDriver().get(url);
        getDriver().manage().window().maximize();
        Thread.sleep(5000);

        WebElement searchButton = getDriver().findElement(By.xpath("//a[@href='/guide']"));
        searchButton.click();
        Thread.sleep(2000);

        String actualResultTitle = getDriver().getTitle();
        Assert.assertEquals(actualResultTitle, expectedResultTitle);

        String actualResultUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(actualResultUrl, expectedResultUrl);
        }

public void testAmazingBouqets() {

        String url = "https://paeonia-boutique.ca/";
        String expectedResult = "Paeonia Fleuristerie Boutique";

        getDriver().get(url);

        WebElement link = getDriver().findElement(By.xpath("//span[text() = "
                + "'Paeonia Fleuristerie Boutique']"));

        String actualResult = link.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
