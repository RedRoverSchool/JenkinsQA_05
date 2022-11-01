import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupSqaeTest extends BaseTest {

    @Test
    public void testNameFormInsurance() {
        getDriver().get("https://insurance.experian.com/sign-up/name");

        WebElement firstName = getDriver().findElement(By.cssSelector("input[name='first_name']"));
        firstName.sendKeys("Ivan");

        WebElement lastName = getDriver().findElement(By.cssSelector("input[name='last_name']"));
        lastName.sendKeys("Smith");

        WebElement buttonNextStep = getDriver().findElement(By.cssSelector("button[data-title='Next Step']"));
        buttonNextStep.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://insurance.experian.com/sign-up/birthdate");
    }

    @Test
    public void testBirthdayFormInsurance() {
        getDriver().get("https://insurance.experian.com/sign-up/birthdate");

        WebElement birthday = getDriver().findElement(By.cssSelector("input[name='date_of_birth']"));
        birthday.sendKeys("12031988");

        WebElement buttonNextStep = getDriver().findElement(By.cssSelector("button[data-title='Next Step']"));
        buttonNextStep.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://insurance.experian.com/sign-up/address");

    }

}
