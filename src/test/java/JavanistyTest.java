import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class JavanistyTest extends BaseTest {

    @Test
    public void testBMICalculator(){
        getDriver().get("https://healthunify.com/bmicalculator/");
        getDriver().findElement(By.name("wg")).sendKeys("80");
        getDriver().findElement(By.name("ht")).sendKeys("180");
        getDriver().findElement(By.name("cc")).click();
        String expectedResult = "24.69";
        System.out.println(getDriver().findElement(By.name("si")).getAttribute("value"));
        Assert.assertEquals(getDriver().findElement(By.name("si")).getAttribute("value"), expectedResult);
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
    @Test
    public void testBdoWarrior2() throws InterruptedException {
        getDriver().get("https://bdocodex.com/ru/skillbuilder/");
        Thread.sleep(1000);
        WebElement buttobWarrior = getDriver().findElement(By.xpath("//div[@class='class_cell'][1]/*"));
        buttobWarrior.click();
        Thread.sleep(1000);
        WebElement buttonSkillAbsolute = getDriver().findElement(By.xpath
                ("//div[@data-gid=\"618\"]"));
        buttonSkillAbsolute.click();
        Thread.sleep(1000);
        WebElement counter = getDriver().findElement(By.xpath
                ("//tr[4]//descendant::div[@class='level_cell current_level']"));
        String counterValue = counter.getText();
        Assert.assertEquals(counterValue, "10");
    }
}
