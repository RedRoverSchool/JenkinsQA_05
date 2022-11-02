import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class SpiritMastersTest extends BaseTest {

    @Test
    public void switchToSecondWindow_OlPolezhaeva_Test() {

        getDriver().get("https://www.toolsqa.com/selenium-training/");

        WebElement toolsQAHeader = getDriver().findElement(By.xpath("//div[@class='col-auto']//li[3]"));
        toolsQAHeader.click();

        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.xpath("//div[@class='card-body']/h5")).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Elements");
    }

    @Test
    public void zyzBank_MW_Test()  {

        getDriver().get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        WebElement createUser = getDriver().findElement(By.xpath("//button[normalize-space()='Bank Manager Login']"));

        createUser.click();

        WebElement addCastomer = getDriver().findElement(By.xpath("//button[normalize-space()='Add Customer']"));

        addCastomer.click();

        WebElement firstName = getDriver().findElement(By.xpath("//input[@placeholder='First Name']"));

        firstName.click();

        firstName.sendKeys("John");

        WebElement lastName = getDriver().findElement(By.xpath("//input[@placeholder='Last Name']"));

        lastName.click();

        lastName.sendKeys("NeJonh");

        WebElement postcode = getDriver().findElement(By.xpath("//input[@placeholder='Post Code']"));

        postcode.click();

        postcode.sendKeys("12334");

        WebElement conf = getDriver().findElement(By.xpath("//button[@type='submit']"));

        conf.click();

        Alert confallert = getDriver().switchTo().alert();

        confallert.accept();

        WebElement verification = getDriver().findElement(By.xpath("//button[@class='btn btn-lg tab btn-primary']"));

        verification.click();

        WebElement home = getDriver().findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/button[1]"));

        home.click();

        WebElement login = getDriver().findElement(By.xpath("//button[normalize-space()='Customer Login']"));

        Assert.assertEquals(login.getText(), "Customer Login");

        login.click();

        WebElement selectNameVariant = getDriver().findElement(By.id("userSelect"));

        Select dropdown = new Select(selectNameVariant);

        dropdown.selectByValue("6");

        WebElement loginn = getDriver().findElement(By.xpath("//button[@type='submit']"));

        loginn.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/strong/span")).getText(), "John NeJonh");

    }




}
