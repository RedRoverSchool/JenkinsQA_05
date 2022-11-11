import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;



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

    @Test
    public void testFindWebTables_YuliyaShershen() {
        getDriver().get("https://demoqa.com");
        Assert.assertTrue(getDriver().findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']"))
                .isDisplayed());

    }

    @Test
    public void testStartMenu_EkaterinaLizina() throws InterruptedException {
        getDriver().get("http://www.99-bottles-of-beer.net/");

        String expectedResult = "START";
        WebElement startMenu = getDriver().findElement(
                By.xpath("//div[@id = 'navigation']//a[text() = 'Start']"));
        String actualResult = startMenu.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testFindSubMenuTeam_TsetskL() {
        getDriver().get("http://www.99-bottles-of-beer.net/");
        String expectedResult = "Team";
        WebElement subMenuTeam = getDriver().findElement(By.xpath("//ul[@id='submenu']//a[@href='team.html']"));
        String actualResult = subMenuTeam.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testSendForm_HappyStrawberry() {
        getDriver().get("https://demoqa.com/elements");
                getDriver().findElement(By.className("text")).click();

        WebElement fullName = getDriver().findElement(By.id("userName"));
        fullName.sendKeys("Ashur Smith");

        WebElement email = getDriver().findElement(By.id("userEmail"));
        email.sendKeys("ashur@smith.com");

        WebElement currentAddress = getDriver().findElement(By.id("currentAddress"));
        currentAddress.sendKeys("2500 west End A, Cooksville, AZ, 67490");

        WebElement permanentAddress = getDriver().findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys("2500 west End A, Cooksville, AZ, 67490");

        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.cssSelector("#submit")).click();
        String actualResult = getDriver().findElement(By.xpath("//p[@id='email']")).getText();

        Assert.assertEquals(actualResult, "Email:ashur@smith.com");
    }

    @Test
    public void testFindEnglishLanguageInBrowseLanguages() {
        getDriver().get("https://www.99-bottles-of-beer.net/");
        WebElement browseLanguages = getDriver().findElement(
                By.xpath("//div/ul/li[2]/a[text()='Browse Languages']"));
        browseLanguages.click();

        WebElement findLetterE = getDriver().findElement(By.xpath("//li/a[text()='E']"));
        findLetterE.click();

        WebElement findEnglish = getDriver().findElement(By.xpath("//a[text()='English']"));
        findEnglish.click();

        WebElement findLanguageEnglish = getDriver().findElement(By.xpath("//*[@id=\"main\"]/h2"));

        Assert.assertEquals(findLanguageEnglish.getText(), "Language English");
    }

    @Test
    public void testTextIsDisplayedOnMainPage_EkaterinaLizina(){
        getDriver().get("http://www.99-bottles-of-beer.net/");

        String expectedResult = "Welcome to 99 Bottles of Beer";
        WebElement text = getDriver().findElement(
                By.xpath("//div[@id='main']/h2[text() ='Welcome to 99 Bottles of Beer']"));
        String actualResult = text.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
