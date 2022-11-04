import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.Locale;

public class GroupCubsTest extends BaseTest {
    @Ignore
    @Test
    public void testFelix_IX() {
        getDriver().get("https://habr.com/ru/all/");

        String query ="приоритет тест-кейса в TestNG";
        getDriver().findElement(By.xpath("//a[@data-test-id='search-button']")).click();
        getDriver().findElement(By.className("tm-input-text-decorated__input")).sendKeys(query + "\n");
        getDriver().findElement(By.xpath("//article[@id='588466']/div[1]/h2")).click();
        WebElement actualRes = getDriver().findElement(By.xpath("//h1[@data-test-id='articleTitle']"));
        Assert.assertEquals(actualRes.getText(), "Как установить приоритет тест-кейса в TestNG с помощью Selenium");
    }

    @Test
    public void testRp5(){
        getDriver().get("https://rp5.ru");
        WebElement search = getDriver().findElement(By.name("searchStr"));
        search.sendKeys("Танжер\n");
        String actualText = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualText, "Search result");
    }

    @Test
    public void testAnastasiiaApp(){
        getDriver().get("https://koma.lux.pl/");
        WebElement link = getDriver().findElement(By.xpath("//a[@href='https://koma.lux.pl/Wszystkie-produkty,pid,9.html']"));
        Assert.assertEquals(link.getText(), "Wyszukiwanie zaawansowane");
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

            WebElement text = getDriver().findElement(By.id("user-name"));
            text.sendKeys("standard_user");
            WebElement text2 = getDriver().findElement(By.id("password"));
            text2.sendKeys("secret_sauce");
            WebElement link = getDriver().findElement(By.id("login-button"));
            getDriver().findElement(By.id("login-button")).click();
            getDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            String link2 = getDriver().findElement(By.xpath("//*[contains(@name, 'remove-sauce-labs-backpack')]")).getText();
            Assert.assertEquals(link2, "REMOVE");
    }
}

