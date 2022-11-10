import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class MariJernTest extends BaseTest {

    @Test
    public void testHerokuApp() {

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));

        Assert.assertEquals(link.getText(), "Buttons");
    }

    @Test
    public void testH2PopUpText_WhenClickingCookiesButton() throws InterruptedException {
        getDriver().get("https://rus.delfi.lv/");
        Thread.sleep(1000);

        WebElement otherCookiesButton = getDriver().findElement(
                By.xpath("//div[@id='qc-cmp2-ui'] //button[@mode='secondary']")
        );

        otherCookiesButton.click();

        WebElement popupH2Text = getDriver().findElement(
                By.xpath("//div[@id ='qc-cmp2-ui']//h2")
        );

        Assert.assertEquals(popupH2Text
                .getTagName(),"h2");
        Assert.assertEquals(popupH2Text
                .getText(),"Мы с уважением относимся к вашей конфиденциальности");
    }
}