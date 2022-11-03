import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupCubsTest extends BaseTest {

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
        getDriver().findElement(By.xpath("//span/a[@href='/Погода_в_Танжере_(аэропорт)']")).click();
        String actualText = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualText, "Погода в Танжере (аэропорт)");
    }
}

