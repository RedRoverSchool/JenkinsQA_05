import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class RomanTTest extends BaseTest {

    @Ignore
    @Test
    public void testVerifyTextBoxOutputResult() {
        getDriver().get("https://demoqa.com/");

        String name = "TestUser";
        String email = "userTest@gmail.com";
        String currentAdr = "123 Main Street";
        String permanentAdr = "123 Main Street";

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(String.format("Name:%s", name));
        expectedResult.add(String.format("Email:%s", email));
        expectedResult.add(String.format("Current Address :%s", currentAdr));
        expectedResult.add(String.format("Permananet Address :%s", permanentAdr));

        getDriver().findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']")).click();
        getDriver().findElement(By.xpath("//div[@class='element-list collapse show']//li[@id='item-0']")).click();
        getDriver().findElement(By.id("userName")).sendKeys(name);
        getDriver().findElement(By.id("userEmail")).sendKeys(email);
        getDriver().findElement(By.id("currentAddress")).sendKeys(currentAdr);
        getDriver().findElement(By.id("permanentAddress")).sendKeys(permanentAdr);
        getDriver().findElement(By.id("submit")).click();

        List<String> actualResult = new ArrayList<>();
        actualResult.add(getDriver().findElement(By.id("name")).getText());
        actualResult.add(getDriver().findElement(By.id("email")).getText());
        actualResult.add(getDriver().findElement(By.cssSelector("#output #currentAddress")).getText());
        actualResult.add(getDriver().findElement(By.cssSelector("#output #permanentAddress")).getText());

        Assert.assertEquals(actualResult, expectedResult);
    }

}
