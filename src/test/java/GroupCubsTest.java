import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

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
    public void testSmetankina(){
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
}