import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class HeaderTest extends BaseTest {

    private static final By USER_ACCOUNT_LINK = By.xpath("//a[@class='model-link']//span");

    @Test
    public void testSeeNameIcon() {

        boolean actualResult = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"))
                .isDisplayed();

        Assert.assertTrue(actualResult);
    }

    @Test
    public void testUserIdInUserAccountLinkAndInUserPage() {
        String usernameInUserAccountLink = getDriver().findElement(USER_ACCOUNT_LINK).getText();

        getDriver().findElement(USER_ACCOUNT_LINK).click();
        String usernameInUserPage = getDriver().findElement(
                By.xpath("//div[@id='main-panel']/div[contains(text(), 'ID')]")).getText();

        Assert.assertEquals(usernameInUserPage, String.format("Jenkins User ID: %s", usernameInUserAccountLink));
    }

    @Test
    public void testUserVerifyDescriptionIsCreated() {
        final String inputText = "This is my Jenkins description";

        getDriver().findElement(USER_ACCOUNT_LINK).click();

        if (getDriver().findElement(By.id("description")).getText().length() != 0) {
            getDriver().findElement(By.id("description-link")).click();
            getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
            getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        }

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(inputText);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[text()='" + inputText + "']")).getText(), inputText);

    }
}
