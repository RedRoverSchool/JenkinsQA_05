import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class GroupCubsTest extends BaseTest {

    private static final String URL_HABR = "https://habr.com/ru/all/";

    @Test
    public void testFelix_IX() {
        getDriver().get(URL_HABR);

        getDriver().findElement(By.xpath("//a[@data-test-id='search-button']")).click();
        getDriver().findElement(By.className("tm-input-text-decorated__input"))
                .sendKeys("приоритет тест-кейса в TestNG" + "\n");

        getDriver().findElement(By.xpath("//article[@id='588466']/div[1]/h2")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1/span")).getText(),
                "Как установить приоритет тест-кейса в TestNG с помощью Selenium");
    }

    @Test
    public void testRp5SearchLocation() {
        getDriver().get("https://rp5.ru/Weather_in_the_world");

        WebElement search = getDriver().findElement(By.name("searchStr"));
        search.sendKeys("Tanger\n");

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),
                "Search result");
    }

    @Test
    public void testAnastasiiaApp() {
        getDriver().get("https://koma.lux.pl/");
        WebElement link = getDriver().findElement(By.xpath("//a[@href='https://koma.lux.pl/Wszystkie-produkty,pid,9.html']"));
        Assert.assertEquals(link.getText(), "Wyszukiwanie zaawansowane");
    }

    @Test
    public void testSmetankina() {
        getDriver().get("https://demoqa.com/");
        WebElement link = getDriver().findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[4]/div/div[3]/h5"));
        Assert.assertEquals(link.getText(), "Widgets");
    }

    @Ignore
    @Test
    public void testJudmi() {
        getDriver().get("http://automationpractice.com/");

        String query = "dress";
        getDriver().findElement(By.xpath("//*[@id='search_query_top']")).sendKeys(query + "\n");
        WebElement searchResult = getDriver().findElement(By.xpath("//ul[@class = 'product_list grid row']/li[1]/div/div/h5/a"));
        Assert.assertTrue(searchResult.getText().toLowerCase(Locale.ROOT).contains(query));
    }

    @Test
    public void testAsh() {
        getDriver().get("https://www.saucedemo.com/");

        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");

        getDriver().findElement(By.id("login-button")).click();

        getDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//button[@id='remove-sauce-labs-backpack']")).getText(),
                "REMOVE");
    }

    @Test
    public void testLiza() {
        getDriver().get("https://petstore.octoperf.com/actions/Catalog.action");

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='MenuContent']/a[3]")).getText(),
                "?");
    }

    @Test
    public void testPochekirya() {
        getDriver().get("https://louna.ru/");
        getDriver().findElement(By.xpath("//div[@id='menu']/a[2]/img")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='content']/p[2]/b")).getText(),
                "23.05.09");
    }

    @Test
    public void testCompanySearch_KirillShumakov() {
        getDriver().get(URL_HABR);

        getDriver().findElement(By.xpath("//a[contains(text(),'Компании')]")).click();
        getDriver().findElement(By.xpath("//input[@name='searchQuery']")).sendKeys("Selectel");

        Assert.assertEquals(getDriver().findElement(By.xpath("//em[contains(text(),'Selectel')]")).getText(),
                "Selectel");
    }
    
    @Test
    public void testSearchItemName_MariaOrlova() {
        getDriver().get("https://www.saucedemo.com/");

        getDriver().findElement(By.id("user-name")).sendKeys("problem_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        WebElement actualResult= getDriver().findElement(
                By.xpath("//a[@id='item_2_title_link']/div[contains(text(),'Sauce Labs Onesie')]"));

        Assert.assertEquals(actualResult.getText(), "Sauce Labs Onesie");
    }
        
    @Test
    public void testLoginAndPassword() {
        getDriver().get("https://www.saucedemo.com/");

        getDriver().findElement(By.xpath("//div[@class='form_group']/input")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.name("login-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div/span[@class='title']")).getText(),"PRODUCTS");
    }
    @Test
    public void testNegativeLoginAsh() {
        getDriver().get("https://www.mirror.co/");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.xpath("//button[@data-testid='footer-sign-in']")).click();
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("woman@flower.is");
        getDriver().findElement(By.xpath("//button[contains(@class,'Input__Button')]")).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//span[contains(@class,'Input__StyledError')]"),
                "This email is not recognized. Try again?"));

        Assert.assertEquals(getDriver().findElement(By.xpath
                        ("//span[contains(@class,'Input__StyledError')]")).getText(),
                "This email is not recognized. Try again?");
    }
    @Test
    public void testFooterHeadersPropertiesAsh() {
        getDriver().get("https://www.mirror.co/shop/packages");
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        final String FONT_FAMILY = "moderat-extended-bold-italic, sans-serif";

            js.executeScript(("window.scrollBy(0,document.body.scrollHeight)"));
            List<WebElement> footerElements = getDriver().findElements(By.xpath(
                    "//span[contains(@id,'footer-heading')]"));

            Assert.assertEquals(footerElements.get(0).getCssValue("font-family"),FONT_FAMILY);
            Assert.assertEquals(footerElements.get(1).getCssValue("font-family"),FONT_FAMILY);
            Assert.assertEquals(footerElements.get(2).getCssValue("font-family"),FONT_FAMILY);

    }

}