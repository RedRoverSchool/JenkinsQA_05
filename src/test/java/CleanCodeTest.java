import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CleanCodeTest extends BaseTest {

    @Test
    public void testFolkInstruments() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/en/instruments/show/instrument/4653");
        WebElement link = getDriver().findElement(By.xpath("/html/body/div[2]/div[4]/div/div/div/div/div[5]/h2"));
//        Thread.sleep(3000);
        Assert.assertEquals(link.getText(), "ritual scepter");
    }

    @Test
    public void testFolkInstrumentsEn() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/pl/o-projekcie");
        WebElement plEn = getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div/ul/li[3]/a"));
        plEn.click();

        WebElement languageChange = getDriver().findElement(By.xpath("//*[@id=\"main\"]/div[4]/div/div/div/div/div[1]/h2"));
        Assert.assertEquals(languageChange.getText(), "About");
    }


    @Test
    public void testBox24Menu() {
        final String URL = "https://box24.com.ua/";
        int expectedNumbersMenu = 11;

        getDriver().get(URL);

        int actualNumbersMenu = getDriver().findElements(
                By.cssSelector(".products-menu__title-link")).size();

        Assert.assertEquals(actualNumbersMenu, expectedNumbersMenu);
    }

    @Test
    public void testPageSales() {
        getDriver().get("https://klinik.by/");
        WebElement bottomSales = getDriver().findElement(By.xpath("//*[@id=\"menu-item-2570\"]/a[text() = 'Акции']"));

        bottomSales.click();

        WebElement pageSales = getDriver().findElement(By.xpath("//*[@id=\"page\"]//h1[text() ='Специальные предложения']"));

        Assert.assertEquals(pageSales.getText(), "Специальные предложения");
    }

    @Test
    public void testTextContactsIsPresent() {
        getDriver().get("https://heropark.by/");

        WebElement text = getDriver().findElement(By.xpath("//span[text()='КОНТАКТЫ']"));

        Assert.assertEquals(text.getText(), "КОНТАКТЫ");
    }

}