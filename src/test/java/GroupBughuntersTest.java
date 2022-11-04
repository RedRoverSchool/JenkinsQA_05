import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupBughuntersTest extends BaseTest {

    @Test
    public void ticketonSearch(){
        getDriver().get("https://ticketon.kz/");
        WebElement search = getDriver().findElement(By.name("q"));
        WebElement button = getDriver().findElement(By.xpath("//button[@class='button postfix secondary search__postfix']"));
        search.sendKeys("Maneskin");
        button.click();
        Assert.assertEquals("Поиск - Система онлайн-покупки билетов в кино и на концерты Ticketon.kz", getDriver().getTitle());

    }

    @Test
    public void bbcHeading(){
        getDriver().get("https://www.bbc.co.uk/learningenglish/english/");
        String text = getDriver().findElement(By.id("heading-things-you-cant-miss")).getText();
        String actualResult = "THINGS YOU CAN'T MISS";
        Assert.assertEquals(actualResult, text);

    }

    @Test
    public void bbcSearch(){
        getDriver().get("https://www.bbc.co.uk/learningenglish/english/");
        WebElement search = getDriver().findElement(By.xpath("//div/div/form/input[@name='q']"));
        WebElement button = getDriver().findElement(By.xpath("//div/div/form/input[@name ='submit']"));
        search.sendKeys("newspaper");
        button.click();
        Assert.assertEquals("BBC Learning English - Search", getDriver().getTitle());

    }

    @Test
    public void greatSchoolMainPage(){
        getDriver().get("https://www.greatschools.org");
        WebElement searchBox = getDriver().findElement(By.xpath("//*[@class=\"full-width pam search_form_field\"]"));
        WebElement searchButton = getDriver().findElement(By.xpath("//*[@class=\"search-label\"]"));
        searchBox.sendKeys("06032");
        searchButton.click();
        Assert.assertEquals("Schools in 06032, 1-20 | GreatSchools", getDriver().getTitle());


    }
}
