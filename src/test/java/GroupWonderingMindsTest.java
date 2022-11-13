import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupWonderingMindsTest extends BaseTest {

    private static final String URL_DEMOQA_ELEMENTS = "https://demoqa.com/elements";
    private static final String URL_99BOTTLES = "http://www.99-bottles-of-beer.net/";
    private static final String URL_DEMOQA = "https://demoqa.com";

    @Test
    public void testGetGorodTula_HappyStrawberry() {
        getDriver().get("https://rp5.ru");

        WebElement search = getDriver().findElement(By.name("searchStr"));
        search.sendKeys("Тула\n");

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Search result");
    }

    @Test
    public void testCheckNamePaeoniaBoutique_TaniaKuno() {
        getDriver().get("https://paeonia-boutique.ca/");

        WebElement link = getDriver().findElement(By.xpath("//span[text() = "
                + "'Paeonia Fleuristerie Boutique']"));

        Assert.assertEquals(link.getText(), "Paeonia Fleuristerie Boutique");
    }

    @Test
    public void testDemodaBookGuide_HappyStrawberry() {
        getDriver().get("https://demoqa.com/books");

        WebElement searchBook = getDriver().findElement(By.xpath("//input[@id='searchBox']"));
        searchBook.sendKeys("Guide");

        Assert.assertTrue(getDriver().findElement(By.id("see-book-Git Pocket Guide")).isDisplayed());
    }

    @Test
    public void testFindWebTables_YuliyaShershen() {
        getDriver().get(URL_DEMOQA);

        Assert.assertTrue(getDriver().findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']"))
                .isDisplayed());
    }

    @Test
    public void testStartMenu_EkaterinaLizina() {
        getDriver().get(URL_99BOTTLES);

       String actualResult = getDriver().findElement(
                By.xpath("//div[@id = 'navigation']//a[text() = 'Start']")).getText();

        Assert.assertEquals(actualResult, "START");
    }

    @Test
    public void testFindSubMenuTeam_TsetskL() {
        getDriver().get(URL_99BOTTLES);

        String actualResult  = getDriver().findElement(
                By.xpath("//ul[@id='submenu']//a[@href='team.html']")).getText();

        Assert.assertEquals(actualResult, "Team");
    }

    @Test
    public void testSendForm_HappyStrawberry() {
        getDriver().get(URL_DEMOQA_ELEMENTS);

        getDriver().findElement(By.className("text")).click();

        WebElement fullName = getDriver().findElement(By.id("userName"));
        fullName.sendKeys("Ashur Smith");

        WebElement email = getDriver().findElement(By.id("userEmail"));
        email.sendKeys("ashur@smith.com");

        WebElement currentAddress = getDriver().findElement(By.id("currentAddress"));
        currentAddress.sendKeys("2500 west End A, Cooksville, AZ, 67490");

        WebElement permanentAddress = getDriver().findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys("2500 west End A, Cooksville, AZ, 67490");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.cssSelector("#submit")).click();
        String actualResult = getDriver().findElement(By.xpath("//p[@id='email']")).getText();

        Assert.assertEquals(actualResult, "Email:ashur@smith.com");
    }

    @Test
    public void testFindEnglishLanguageInBrowseLanguages_YuliyaShershen() {
        getDriver().get(URL_99BOTTLES);

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
    public void testTextIsDisplayedOnMainPage_EkaterinaLizina() {
        getDriver().get(URL_99BOTTLES);

        String expectedResult = "Welcome to 99 Bottles of Beer";
        WebElement text = getDriver().findElement(
                By.xpath("//div[@id='main']/h2[text() ='Welcome to 99 Bottles of Beer']"));
        String actualResult = text.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCheckForm_HappyStrawberry() {
        getDriver().get(URL_DEMOQA_ELEMENTS);

        getDriver().findElement(By.xpath("//span[text()='Check Box']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='Home']")).isDisplayed());
    }

    @Test
    public void testFindLanguageCobra_HappyStrawberry() {
        getDriver().get(URL_99BOTTLES);

        WebElement findTopList = getDriver().findElement(
                By.xpath("//a[text()='Top Lists']"));
        findTopList.click();

        WebElement findCobra = getDriver().findElement(By.xpath("//a[text()='Cobra']"));
        findCobra.click();

        WebElement findLanguageCobra = getDriver().findElement(By.xpath("//h2[text()='Language Cobra']"));

        Assert.assertEquals(findLanguageCobra.getText(), "Language Cobra");
    }
}
