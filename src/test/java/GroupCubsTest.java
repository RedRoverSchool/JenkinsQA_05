import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

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
    public void testAnastasiiaApp(){
        getDriver().get("https://koma.lux.pl/");
        WebElement link = getDriver().findElement(By.xpath("//a[@href='https://koma.lux.pl/Wszystkie-produkty,pid,9.html']"));
        Assert.assertEquals(link.getText(), "Wyszukiwanie zaawansowane");
    }
}
