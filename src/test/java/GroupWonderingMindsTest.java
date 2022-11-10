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

        getDriver().manage().window().maximize();

        WebElement clickTextBooks = getDriver().findElement(By.xpath("//span[text()='Text Box']  "));
        clickTextBooks.click();
        WebElement fillFullName = getDriver().findElement(By.xpath("//input[@id='userName']"));
        fillFullName.sendKeys("Ashur Smith");
        WebElement fillEmail = getDriver().findElement(By.xpath("//input[@id='userEmail']"));
        fillEmail.sendKeys("ashur@smith.com");
        WebElement fillCurrentAddress = getDriver().findElement(By.xpath("//textarea[@id='currentAddress']"));
        fillCurrentAddress.sendKeys("2500 west End A, Cooksville, Az,67490");
        WebElement fillPermanentAddress = getDriver().findElement(By.xpath("//textarea[@id='permanentAddress']"));
        fillPermanentAddress.sendKeys("2500 west End A, Cooksville, Az,67490");
        WebElement clickButtonSubmit = getDriver().findElement(By.xpath("//button[@id='submit']"));
        clickButtonSubmit.click();
        WebElement printNameAndEmail = getDriver().findElement(By.xpath("//p[@id='email']"));
        String actualResult =printNameAndEmail.getText();

        Assert.assertEquals(actualResult, "Email:ashur@smith.com");

    }

}
