import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class JavanistyTest extends BaseTest {

    @Test
    public void test1(){
        open("https://calc.by/building-calculators/laminate.html");
        enter("ln_room_id", "820");
        enter("wd_room_id","820");
        enter("ln_lam_id", "1600");
        enter("wd_lam_id", "200");
        enter("n_packing","10");
        Select select = new Select(getDriver().findElement(By.id("laying_method_laminate")));
        select.selectByValue("2");
        enter("min_length_segment_id", "300");
        enter("indent_walls_id","5");
        getDriver().findElement(By.id("direction-laminate-id1")).click();
        getDriver().findElement(By.cssSelector("[class='calc-btn']")).click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        List<String> expectedResult = Arrays.asList(
                "Требуемое количество досок ламината: 226", "Количество упаковок ламината: 23"
        );
        Assert.assertEquals(getActualResult(),expectedResult , "expected equal actual");
    }

    public void open(String url){
        getDriver().get(url);
    }

    public void enter(String id, String value){
        getDriver().findElement(By.id(id)).clear();
        getDriver().findElement(By.id(id)).sendKeys(value);
    }

    public List<String> getActualResult(){
        String countLaminte = getDriver().findElement(By.xpath("(//div[@style='text-align:center; font-size:16px;'])[1]")).getText();
        String countPackage = getDriver().findElement(By.xpath("(//div[@style='text-align:center; font-size:16px;'])[2]")).getText();

        List<String> actualResult = Arrays.asList(
                countLaminte, countPackage
        );
        return actualResult;
    }

    @Test
    public void testIriSamoRegistration(){
        getDriver().get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement boxZIP = getDriver().findElement(By.name("zip_code"));
        boxZIP.sendKeys("196240");
        getDriver().findElement(By.xpath("//input[@value='Continue']")).click();
        WebElement boxFirstName = getDriver().findElement(By.name("first_name"));
        boxFirstName.sendKeys("Imya");
        WebElement boxLastName = getDriver().findElement(By.name("last_name"));
        boxLastName.sendKeys("Familiya");
        WebElement boxEmail = getDriver().findElement(By.name("email"));
        boxEmail.sendKeys("Familiya@gmail.com");
        WebElement boxPassword = getDriver().findElement(By.name("password1"));
        boxPassword.sendKeys("777555333");
        WebElement boxConfirmPassword = getDriver().findElement(By.name("password2"));
        boxConfirmPassword.sendKeys("777555333");
        getDriver().findElement(By.xpath("//input[@value='Register']")).click();
        WebElement confirmationMessage = getDriver().findElement(By.xpath("//span[@class='confirmation_message']"));
        String expectedConfirmationMessage = "Account is created!";
        String actualConfirmationMessage = confirmationMessage.getText();

        Assert.assertEquals(expectedConfirmationMessage, actualConfirmationMessage);
    }
}
