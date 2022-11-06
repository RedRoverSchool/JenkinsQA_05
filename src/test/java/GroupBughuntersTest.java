import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupBughuntersTest extends BaseTest {

    private final String AUTO_PRACTICE_URL = "http://automationpractice.com/index.php";

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
    @Test
    public void testW3Resource() {
        getDriver().get("https://www.w3resource.com/index.php");

        WebElement link = getDriver().findElement(By.xpath("//a[@href='https://www.w3resource.com/java-tutorial/index.php']"));

        Assert.assertEquals(link.getText(), "Java");
    }

    @Test
    public void testPythonOrg() {
        getDriver().get("https://www.python.org/");
        WebElement talks = getDriver().findElement(By.xpath("//*[@id='container']/li[3]/ul/li[2]/a"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true);",talks);
        talks.click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//dd[contains(text(),'A podcast on Python and related technologies.')]")).isDisplayed());
    }

    @Ignore
    @Test
    public void testLoginSuccess() {
        getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        String username = "Admin";
        String password = "admin123";

        getDriver().findElement(By.name("username")).sendKeys(username);
        getDriver().findElement(By.name("password")).sendKeys(password);
        getDriver().findElement(By.className("orangehrm-login-button")).click();

        Assert.assertEquals(getDriver().findElement(By
                .xpath("//span[@class='oxd-topbar-header-breadcrumb']")).getText(), "PIM");
    }

    @Test
    public void testChooseCurrency() {
        getDriver().get("https://rahulshettyacademy.com/dropdownsPractise/");

        WebElement staticDropdown = getDriver().findElement(By.id("ctl00_mainContent_DropDownListCurrency"));

        Select dropdown = new Select(staticDropdown);
        dropdown.selectByIndex(3);

        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "USD");

    getDriver().get("https://developers.mts.ru");
    }

    @Test
    public void testСhoosingСlothes(){

        String[] expectedResult = {"TOPS", "DRESSES"};

        getDriver().get(AUTO_PRACTICE_URL);
        getDriver().findElement(By.xpath("//div[@id='block_top_menu']/ul/li/a[@title='Women']")).click();
        WebElement tops = getDriver().findElement(
                By.xpath("//div[@id='subcategories']/ul/li/h5/a[text()='Tops']"));
        WebElement dresses = getDriver().findElement(
                By.xpath("//div[@id='subcategories']/ul/li/h5/a[text()='Dresses']"));

        String[] actualResult = {tops.getText(), dresses.getText()};

        Assert.assertEquals(actualResult,expectedResult);
    }
    @Test
    public void testFillingInContactInformation(){

        getDriver().get(AUTO_PRACTICE_URL);
        getDriver().findElement(By.xpath("//div[@id='contact-link']/a[@title='Contact Us']")).click();
        getDriver().findElement(By.xpath("//div[@class='submit']/button/span[text()='Send']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//div[@class='alert alert-danger']/p")).getText(), "There is 1 error");
    }
}