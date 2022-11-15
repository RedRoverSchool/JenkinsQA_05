package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

@Ignore
public class GroupCleanCodeTest extends BaseTest {

    @Test
    public void testFolkInstruments() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/en/instruments/show/instrument/4653");

        WebElement link = getDriver().findElement(By.xpath("//div//h2[@class='hidden-xs'][text()='ritual scepter']"));

        Assert.assertEquals(link.getText(), "ritual scepter");
    }

    @Test
    public void testFolkInstrumentsPolishEnglish() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/pl/o-projekcie");

        WebElement polishEnglish = getDriver().findElement(By.xpath("//a[text()='en']"));
        polishEnglish.click();

        WebElement languageChange = getDriver().findElement(By.xpath("//h2[text()='About']"));

        Assert.assertEquals(languageChange.getText(), "About");
    }

    @Test
    public void testBox24Menu() {
        getDriver().get("https://box24.com.ua/");

        int actualNumbersMenu = getDriver().findElements(
                By.cssSelector(".products-menu__title-link")).size();

        Assert.assertEquals(actualNumbersMenu, 11);
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
    public void testBox24BaseLanguage() {
        final String expectedLanguage = "Укр";

        getDriver().get("https://box24.com.ua/");

        String actualLanguage = getDriver().findElement(
                By.xpath("//div[@class='lang-menu__button']")).getText();

        Assert.assertEquals(actualLanguage, expectedLanguage);
    }

    @Test
    public void testBox24SearchForm() {
        final String SEARCH_TEXT = "plane";
        final String EXPECTED_H1 = "Результати пошуку «" + SEARCH_TEXT + "»";

        getDriver().get("https://box24.com.ua/");
        getDriver().findElement(By.cssSelector(".search__input")).sendKeys(SEARCH_TEXT);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".search__button")));

        getDriver().findElement(By.cssSelector(".search__button")).click();

        String actualH1 = getDriver().findElement(By.cssSelector("#j-catalog-header")).getText();

        Assert.assertEquals(actualH1, EXPECTED_H1);
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
    public void testWebElements() {
        getDriver().get("http://json.parser.online.fr/");

        WebElement text = getDriver().findElement(By.xpath("//div[2][@class='n b']/span[@class='l']"));

        Assert.assertEquals(text.getText(), "Options");
        text.click();
        WebElement text2 = getDriver().findElement(By.xpath("//div[@class='e d']"));
        text2.click();

        Assert.assertEquals(text2.getText(), "Top-bottom");
    }

    @Test
    public void testFolkInstrumentsOpsGroup() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/pl");

        getDriver().findElement(By.xpath("//li/a[text()='Instrumenty']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//h2[text()='Opis grup']")).getText(), "Opis grup");


    }
}
