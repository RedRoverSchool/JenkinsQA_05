import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupSqaeTest extends BaseTest {

    @Ignore
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

    @Test
    public void testPageTitleOfMagazine() {
        getDriver().get("https://www.theatlantic.com/");
        String actualTitle = getDriver().getTitle();
        String expectedTitle = "The Atlantic";

        Assert.assertEquals(actualTitle, expectedTitle);
    }
    @Ignore
    @Test
    public void testSubscribePageUrlOfMagazine() {
        getDriver().get("https://www.theatlantic.com/");
        WebElement subscribeButton = getDriver().findElement(By.xpath("//*[@id='__next']/nav/div/div[2]/ul/li[2]/a"));
        subscribeButton.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://accounts.theatlantic.com/products/?source=nav");
    }

    @Test
    public void testPageTitleOfSite() {
        getDriver().get("http://www.seleniumframework.com/Practiceform/");

        String actualSiteTitle = getDriver().getTitle();
        String expectedSiteTitle = "Selenium Framework | Practiceform";

        Assert.assertEquals(actualSiteTitle, expectedSiteTitle);
    }

    @Test
    public void testUrlOfSite() {
        getDriver().get("http://www.seleniumframework.com/Practiceform/");
        getDriver().findElement(By.xpath("//*[@id=\"main-nav\"]/li[8]/a/span")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://www.seleniumframework.com/about-2/");
    }

    @Test
    public void testUrlTitleHeading() {
        getDriver().get("http://www.seleniumframework.com/about-2/");
        getDriver().findElement(By.xpath("//*[@id=\"main-nav\"]/li[1]/a/span")).click();

        String actualUrl = getDriver().getCurrentUrl();
        String expectedUrl = "http://www.seleniumframework.com/";

        Assert.assertEquals(actualUrl, expectedUrl);

        String actualTitle = getDriver().getTitle();
        String expectedTitle = "Selenium Framework | Selenium, Cucumber, Ruby, Java et al.";

        Assert.assertEquals(actualTitle, expectedTitle);

        WebElement actualH2 = getDriver().findElement(By.cssSelector("h2"));

        Assert.assertEquals(actualH2.getText(), "Agile Testing and ATDD Automation –  Free Tutorials");
    }


}
