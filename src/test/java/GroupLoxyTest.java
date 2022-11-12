import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.ArrayList;
import java.util.List;

public class GroupLoxyTest extends BaseTest {
    @Test
    public void test_customerServiceResponseText_WhenContactFormSubmitted() {
        final String url = "https://parabank.parasoft.com/parabank/index.htm";
        final String name = "Mary Johnson";
        final String email = "mj@test.com";
        final String phone = "1234567";
        final String message = "test text";

        final List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Customer Care");
        expectedResult.add("Thank you " + name);
        expectedResult.add("A Customer Care Representative will be contacting you.");

        getDriver().get(url);
        getDriver().findElement(By.xpath("//div[@id = 'headerPanel']//li[@class = 'contact']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.id("email")).sendKeys(email);
        getDriver().findElement(By.id("phone")).sendKeys(phone);
        getDriver().findElement(By.id("message")).sendKeys(message);
        getDriver().findElement(By.xpath("//form[@id = 'contactForm']//input[@type = 'submit']")).click();

        List<String> actualResult = new ArrayList<>();
        actualResult.add(getDriver().findElement(By.xpath("//div[@id = 'rightPanel']/h1")).getText());
        actualResult.add(getDriver().findElement(By.xpath(".//div[@id = 'rightPanel']//p[1]")).getText());
        actualResult.add(getDriver().findElement(By.xpath(".//div[@id = 'rightPanel']//p[2]")).getText());

        Assert.assertEquals(actualResult, expectedResult);
    }

}
