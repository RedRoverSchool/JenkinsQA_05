import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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
    public void testFormyProject() {
        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/dropdown']"));

        Assert.assertEquals(link.getText(), "Dropdown"); 
    }

    @Test
    public void testTextContactsIsPresent() {
        getDriver().get("https://heropark.by/");

        WebElement text = getDriver().findElement(By.xpath("//span[text()='КОНТАКТЫ']"));

        Assert.assertEquals(text.getText(), "КОНТАКТЫ");
    }

    @Test
    public void testWeb(){
        getDriver().get("https://formy-project.herokuapp.com");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));
        Assert.assertEquals(link.getText(),"Buttons");
    }
    
       @Test
        public void testAstraCom() {
            getDriver().get("https://www.astracom.ru/");
            getDriver().findElement(By.className("button")).click();
            getDriver().findElement(By.partialLinkText("АРСО P25")).click();
            WebElement text = getDriver().findElement(By.partialLinkText("Ретрансляторы"));
            Assert.assertEquals(text.getText(), "Ретрансляторы");
        }

    @Test
    public void testBox24Language() {
        final String URL = "https://box24.com.ua/";
        String expectedLanguage = "Укр";

        getDriver().get(URL);
        String actualLanguage = getDriver().findElement(
                By.xpath("//div[@class='lang-menu__button']")).getText();

        Assert.assertEquals(actualLanguage, expectedLanguage);
    }

    @Test
    public void testBox24SearchForm() {
        final String URL = "https://box24.com.ua/";

        String expectedH1 = "Результати пошуку «plane»";
        String input = "plane";

        getDriver().get(URL);
        getDriver().findElement(By.cssSelector(".search__input")).sendKeys(input);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".search__button")));

        getDriver().findElement(By.cssSelector(".search__button")).click();

        String actualH1 = getDriver().findElement(
                By.cssSelector("#j-catalog-header")).getText();

        Assert.assertEquals(actualH1, expectedH1);
    }
    @Test
    public void testFlower(){
        getDriver().get("https://www.flowerchimp.co.id/");
        WebElement SingIn = getDriver().findElement(By.xpath("//span[text()='Login']"));
        SingIn.click();
        WebElement Email = getDriver().findElement(By.xpath("//div[@id='login_form']/form/input[@type='email']"));
        Email.sendKeys("Heli@dmail.com");
        WebElement Password = getDriver().findElement(By.xpath("//input[@type='password']"));
        Password.sendKeys("12634");
        WebElement LOGIN = getDriver().findElement(By.xpath("//form/input[@class='btn action_button']"));
        LOGIN.click();
        WebElement actual = getDriver().findElement(By.xpath("//p[@class='shopify-challenge__message']"));
        Assert.assertEquals(actual.getText(), "To continue, let us know you're not a robot.");
    }
}
