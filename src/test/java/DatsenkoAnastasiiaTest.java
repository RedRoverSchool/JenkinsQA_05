import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class DatsenkoAnastasiiaTest extends BaseTest {

    @Test
    public void testApp(){
        getDriver().get("https://koma.lux.pl/");
        WebElement link = getDriver().findElement(By.xpath("//a[@href='https://koma.lux.pl/Wszystkie-produkty,pid,9.html']"));
        Assert.assertEquals(link.getText(), "Wyszukiwanie zaawansowane");
    }
}
