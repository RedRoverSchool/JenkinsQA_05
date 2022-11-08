import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupWonderingMindsTest extends BaseTest {

    @Test
    public void testGorodTulaTheBest() {
        getDriver().get("https://rp5.ru");
        WebElement search = getDriver().findElement(By.name("searchStr"));
        search.sendKeys("Тула\n");
        String actualText = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualText, "Search result");
    }

    @Test
    public void testAmazingBouqets() {

        String url = "https://paeonia-boutique.ca/";
        String expectedResult = "Paeonia Fleuristerie Boutique";

        getDriver().get(url);

        WebElement link = getDriver().findElement(By.xpath("//span[text() = "
                + "'Paeonia Fleuristerie Boutique']"));

        String actualResult = link.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }


    @Test
    public void testDemodaBookGuide_HappyStrawberry() {
        getDriver().get("https://demoqa.com/books");
        WebElement searchBook = getDriver().findElement(By.xpath("//*[@id='searchBox']"));
        searchBook.sendKeys("Guide");

        Assert.assertTrue(getDriver().findElement(By.id("see-book-Git Pocket Guide")).isDisplayed());
    }

}
