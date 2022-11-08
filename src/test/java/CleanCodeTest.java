import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;


public class CleanCodeTest extends BaseTest {

    @Test
    public void testFolkInstruments() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/en/instruments/show/instrument/4653");

        WebElement link = getDriver().findElement(By.xpath("/html/body/div[2]/div[4]/div/div/div/div/div[5]/h2"));

        Assert.assertEquals(link.getText(), "ritual scepter");
    }

    @Test
    public void testFolkInstrumentsPolishEnglish() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/pl/o-projekcie");

        WebElement polishEnglish = getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div/ul/li[3]/a"));
        polishEnglish.click();

        WebElement languageChange = getDriver().findElement(By.xpath("//*[@id='main']/div[4]/div/div/div/div/div[1]/h2"));

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
        WebElement bottomSales = getDriver().findElement(By.xpath("//*[@id='menu-item-2570']/a[text() = 'Акции']"));

        bottomSales.click();

        WebElement pageSales = getDriver().findElement(By.xpath("//*[@id='page']//h1[text() ='Специальные предложения']"));

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
    public void testWeb() {
        getDriver().get("https://formy-project.herokuapp.com");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));

        Assert.assertEquals(link.getText(), "Buttons");
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
    public void testFlower() {
        getDriver().get("https://www.flowerchimp.co.id/");
        WebElement SingIn = getDriver().findElement(By.xpath("//span[text()='Login']"));
        SingIn.click();
        String LOGIN = getDriver().findElement(By.xpath("//div[@class='sixteen columns clearfix collection_nav']")).getText();

        Assert.assertEquals(LOGIN, "Customer Login");
    }

    @Test
    public void testSauceLabsEvgeniya() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("secret_sauce");
        getDriver().findElement(By.xpath("//input[@type='submit']")).click();
        String header = getDriver().findElement(By.xpath("//span[@class='title']")).getText();

        Assert.assertEquals(header, "PRODUCTS");
    }

    @Test
    public void testVitebskbiz() {
        String Vitebskbiz = "https://vitebsk.biz/";
        getDriver().get(Vitebskbiz);
        WebElement link = getDriver().findElement(By.xpath("//div/a[@href='https://vitebsk.biz/news/tc/']"));

        Assert.assertEquals(link.getText(), "Запостить");
    }

    @Test
    public void testFolkInstrumentsBow() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/en");

        WebElement instrumentSearch = getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div/ul/li[2]/a"));
        instrumentSearch.click();

        WebElement instrumentSearch1 = getDriver().findElement(By.xpath("/html/body/div[2]/div[4]/div/div/div/div/div[2]/div/div[2]/div[1]/div/div[3]/a/h4"));
        instrumentSearch1.click();

        WebElement instrumentSearch2 = getDriver().findElement(By.xpath("/html/body/div[2]/div[4]/div/div/div/div/div[2]/div[3]/a[2]/div/img"));
        instrumentSearch2.click();

        WebElement bow = getDriver().findElement(By.xpath("/html/body/div[2]/div[4]/div/div/div/div/div[5]/h2"));

        Assert.assertEquals(bow.getText(), "bow for mazanki");
    }

}
