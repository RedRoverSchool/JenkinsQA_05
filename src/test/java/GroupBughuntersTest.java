import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.Random;

public class GroupBughuntersTest extends BaseTest {

    private final String AUTO_PRACTICE_URL = "http://automationpractice.com/index.php";

    @Ignore
    @Test
    public void testTicketonSearch(){
        getDriver().get("https://ticketon.kz/");

        WebElement search = getDriver().findElement(By.name("q"));
        WebElement button = getDriver().findElement(By.xpath("//button[@class='button postfix secondary search__postfix']"));
        search.sendKeys("Maneskin");
        button.click();

        Assert.assertEquals(getDriver().getTitle(), "Поиск - Система онлайн-покупки билетов в кино и на концерты Ticketon.kz");
    }

    @Test
    public void testBbcHeading(){
        getDriver().get("https://www.bbc.co.uk/learningenglish/english/");

        String text = getDriver().findElement(By.id("heading-things-you-cant-miss")).getText();

        Assert.assertEquals(text, "THINGS YOU CAN'T MISS");
    }

    @Test
    public void testBbcSearch(){
        getDriver().get("https://www.bbc.co.uk/learningenglish/english/");

        WebElement search = getDriver().findElement(By.xpath("//div/div/form/input[@name='q']"));
        WebElement button = getDriver().findElement(By.xpath("//div/div/form/input[@name ='submit']"));
        search.sendKeys("newspaper");
        button.click();

        Assert.assertEquals(getDriver().getTitle(), "BBC Learning English - Search");
    }

    @Test
    public void testGreatSchoolMainPage(){
        getDriver().get("https://www.greatschools.org");

        WebElement searchBox = getDriver().findElement(By.xpath("//*[@class=\"full-width pam search_form_field\"]"));
        WebElement searchButton = getDriver().findElement(By.xpath("//*[@class=\"search-label\"]"));
        searchBox.sendKeys("06032");
        searchButton.click();

        Assert.assertEquals( getDriver().getTitle(), "Schools in 06032, 1-20 | GreatSchools");
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

    @Ignore
    @Test
    public void testChoosingClothes(){

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
    @Ignore
    @Test
    public void testFillingInContactInformation() {

        getDriver().get(AUTO_PRACTICE_URL);
        getDriver().findElement(By.xpath("//div[@id='contact-link']/a[@title='Contact Us']")).click();
        getDriver().findElement(By.xpath("//div[@class='submit']/button/span[text()='Send']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//div[@class='alert alert-danger']/p")).getText(), "There is 1 error");
    }

    @Test
    public void testBbcLogin(){
        getDriver().get("https://www.bbc.co.uk/learningenglish/english/");

        WebElement signInButton = getDriver().findElement(By.id("idcta-link"));
        signInButton.click();
        WebElement account = getDriver().findElement(By.id("user-identifier-input"));
        account.sendKeys("Test");
        WebElement password = getDriver().findElement(By.id("password-input"));
        password.sendKeys("test");
        WebElement button = getDriver().findElement(By.id("submit-button"));
        button.click();
        WebElement errorMessage = getDriver().findElement(By.className("form-message__text"));

        Assert.assertEquals(errorMessage.getText(), "Sorry, that password is too short. It needs to be eight characters or more.");
    }

    @Test
    public void testBbcChangeLanguage(){
        getDriver().get("https://www.bbc.co.uk/learningenglish/english/");

        WebElement changeLanguageButton = getDriver().findElement(By.id("floating-dropdown-toggle"));
        changeLanguageButton.click();
        WebElement persianLanguage = getDriver().findElement(By.xpath("//*[@id=\"language-selections\"]/li[2]/a"));
        persianLanguage.click();

        Assert.assertEquals(getDriver().getTitle(), "BBC BBC Learning English - Persian Home Page (Dari)");
    }

    @Ignore
    @Test
    public void testBbcStoriesForChildren(){
        getDriver().get("https://www.bbc.co.uk/learningenglish/english/");

        Actions action = new Actions(getDriver());
        WebElement storiesForChildren = getDriver().findElement(By.xpath("//*[@id=\"bbcle-content\"]/div/div[4]/div[5]/div/div[1]/a"));
        action.moveToElement(storiesForChildren).perform();
        storiesForChildren.click();

        Assert.assertEquals(getDriver().getTitle(), "BBC Learning English - Stories for Children / Camping");
    }

    public static void toSelectByVisibleText(String text, WebElement webelement){
        Select select = new Select(webelement);
        select.selectByVisibleText(text);
    }
    public static String getRandomDigitAndLetterString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    @Ignore
    @Test
    public void testInsuranceCompanyQuote() throws InterruptedException {
        getDriver().get("https://demo.guru99.com/insurance/v1/register.php");
        String randomDig = getRandomDigitAndLetterString();
        String registration = getRandomDigitAndLetterString();
        toSelectByVisibleText("Mr",getDriver().findElement(By.id("user_title")));
        getDriver().findElement(By.id("user_firstname")).sendKeys("Baha");
        getDriver().findElement(By.id("user_surname")).sendKeys("Python");
        getDriver().findElement(By.id("user_phone")).sendKeys("1234567890");
        toSelectByVisibleText("1980",getDriver().findElement(By.id("user_dateofbirth_1i")));
        toSelectByVisibleText("August",getDriver().findElement(By.id("user_dateofbirth_2i")));
        toSelectByVisibleText("20",getDriver().findElement(By.id("user_dateofbirth_3i")));
        getDriver().findElement(By.id("licencetype_f")).click();
        toSelectByVisibleText("2",getDriver().findElement(By.id("user_licenceperiod")));
        toSelectByVisibleText("Student",getDriver().findElement(By.id("user_occupation_id")));
        getDriver().findElement(By.id("user_address_attributes_street")).sendKeys("100 main street");
        getDriver().findElement(By.id("user_address_attributes_city")).sendKeys("Jersey city");
        getDriver().findElement(By.id("user_address_attributes_county")).sendKeys("United States");
        getDriver().findElement(By.id("user_address_attributes_postcode")).sendKeys("19125");
        getDriver().findElement(By.id("user_user_detail_attributes_email")).sendKeys(randomDig+"@gmail.com");
        getDriver().findElement(By.id("user_user_detail_attributes_password")).sendKeys(randomDig);
        getDriver().findElement(By.id("user_user_detail_attributes_password_confirmation")).sendKeys(randomDig);
        getDriver().findElement(By.xpath("//input[@name='submit']")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Register']")));

        getDriver().findElement(By.id("email")).sendKeys(randomDig+"@gmail.com");
        getDriver().findElement(By.id("password")).sendKeys(randomDig);
        getDriver().findElement(By.xpath("//input[@value='Log in']")).click();
        getDriver().findElement(By.id("newquote")).click();
        toSelectByVisibleText("European", getDriver().findElement(By.id("quotation_breakdowncover")));
        getDriver().findElement(By.id("quotation_incidents")).sendKeys("1");
        getDriver().findElement(By.id("quotation_vehicle_attributes_registration")).sendKeys(registration);
        getDriver().findElement(By.id("quotation_vehicle_attributes_mileage")).sendKeys("50000");
        getDriver().findElement(By.id("quotation_vehicle_attributes_value")).sendKeys("7000");
        toSelectByVisibleText("Locked Garage", getDriver().findElement(By.id("quotation_vehicle_attributes_parkinglocation")));
        toSelectByVisibleText("2022", getDriver().findElement(By.id("quotation_vehicle_attributes_policystart_1i")));
        toSelectByVisibleText("February", getDriver().findElement(By.id("quotation_vehicle_attributes_policystart_2i")));
        toSelectByVisibleText("1", getDriver().findElement(By.id("quotation_vehicle_attributes_policystart_3i")));
        getDriver().findElement(By.xpath("//input[@value='Calculate Premium']")).click();
        String premiumAmount = getDriver().findElement(By.id("calculatedpremium")).getText();
        getDriver().findElement(By.xpath("//input[@value='Save Quotation']")).click();
        String identificNumber = getDriver().findElement(By.xpath("/html/body")).getText();
        String idNumerReal = "";
        for(int i = 0; i < identificNumber.length();i++){
            if(Character.isDigit(identificNumber.charAt(i))){
                idNumerReal += String.valueOf(identificNumber.charAt(i));
            }
        }
        getDriver().navigate().back();
        getDriver().findElement(By.id("retrieve")).click();
        getDriver().findElement(By.xpath("//input[@placeholder='identification number']")).sendKeys(idNumerReal);
        getDriver().findElement(By.id("getquote")).click();
        String registrationValue = getDriver().findElement(By.xpath("/html/body/table/tbody/tr[6]/td[2]")).getText();
        Assert.assertEquals(registrationValue,registration);
    }

}
