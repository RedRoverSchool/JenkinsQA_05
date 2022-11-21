package old;

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

@Ignore
public class GroupBughuntersTest extends BaseTest {

    private final String AUTO_PRACTICE_URL = "http://automationpractice.com/index.php";
    private final String BBC_URL = "https://www.bbc.co.uk/learningenglish/english/";


    @Ignore
    @Test
    public void testTicketonSearch_NL(){
        getDriver().get("https://ticketon.kz/");

        getDriver().findElement(By.name("q")).sendKeys("Maneskin");
        getDriver().findElement(
                By.xpath("//button[@class='button postfix secondary search__postfix']")).click();

        Assert.assertEquals(getDriver().getTitle(), "Поиск - Система онлайн-покупки билетов в кино и на концерты Ticketon.kz");
    }

    @Test
    public void testBbcHeading_NL(){
        getDriver().get(BBC_URL);

        Assert.assertEquals(getDriver().findElement(By.id("heading-things-you-cant-miss")).getText(), "THINGS YOU CAN'T MISS");
    }

    @Test
    public void testBbcSearch_NL(){
        getDriver().get(BBC_URL);

        getDriver().findElement(By.xpath("//div/div/form/input[@name='q']")).sendKeys("newspaper");
        getDriver().findElement(By.xpath("//div/div/form/input[@name ='submit']")).click();

        Assert.assertEquals(getDriver().getTitle(), "BBC Learning English - Search");
    }

    @Test
    public void testGreatSchoolSearchByPostcode_NL(){
        getDriver().get("https://www.greatschools.org");

        getDriver().findElement(By.xpath("//div//form//input")).sendKeys("06032");
        getDriver().findElement(By.xpath("//div//button[@type = 'submit']")).click();
    
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
    public void testBbcLoginNegative_NL(){
        getDriver().get(BBC_URL);

        getDriver().findElement(By.id("idcta-link")).click();
        getDriver().findElement(By.id("user-identifier-input")).sendKeys("Test");
        getDriver().findElement(By.id("password-input")).sendKeys("test");
        getDriver().findElement(By.id("submit-button")).click();

        Assert.assertEquals(getDriver().findElement(By.className("form-message__text")).getText(),
                "Sorry, that password is too short. It needs to be eight characters or more.");
    }


    @Test
    public void testBbcChangeLanguagePersian_NL(){
        getDriver().get(BBC_URL);

        getDriver().findElement(By.id("floating-dropdown-toggle")).click();
        getDriver().findElement(By.xpath("//a[@lang ='fa']")).click();

        Assert.assertEquals(getDriver().getTitle(), "BBC BBC Learning English - Persian Home Page (Dari)");
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
    @Test
    public void testInsuranceCompanyQuote() throws InterruptedException {
        getDriver().get("https://demo.guru99.com/insurance/v1/register.php");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String randomDig = getRandomDigitAndLetterString();
        String registration = getRandomDigitAndLetterString();
        toSelectByVisibleText("Mr",getDriver().findElement(By.id("user_title")));
        getDriver().findElement(By.id("user_firstname")).sendKeys("Baha");
        getDriver().findElement(By.id("user_surname")).sendKeys("Python");
        getDriver().findElement(By.id("user_phone")).sendKeys("1234567890");
        toSelectByVisibleText("1980",getDriver().findElement(By.id("user_dateofbirth_1i")));
        toSelectByVisibleText("August",getDriver().findElement(By.id("user_dateofbirth_2i")));
        toSelectByVisibleText("20",getDriver().findElement(By.id("user_dateofbirth_3i")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("licencetype_f")));
        //getDriver().findElement(By.id("licencetype_f")).click();
        toSelectByVisibleText("2",getDriver().findElement(By.id("user_licenceperiod")));
        toSelectByVisibleText("Student",getDriver().findElement(By.id("user_occupation_id")));
        getDriver().findElement(By.id("user_address_attributes_street")).sendKeys("100 main street");
        getDriver().findElement(By.id("user_address_attributes_city")).sendKeys("Jersey city");
        getDriver().findElement(By.id("user_address_attributes_county")).sendKeys("United States");
        getDriver().findElement(By.id("user_address_attributes_postcode")).sendKeys("19125");
        getDriver().findElement(By.id("user_user_detail_attributes_email")).sendKeys(randomDig+"@gmail.com");
        getDriver().findElement(By.id("user_user_detail_attributes_password")).sendKeys(randomDig);
        getDriver().findElement(By.id("user_user_detail_attributes_password_confirmation")).sendKeys(randomDig);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='submit']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));

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
    @Test
    public void testCheckErrorMessage (){
        String expectedResult = "Either the username or password you entered seems to be wrong.";
        getDriver().get("https://www.bargainmoose.ca/");
        getDriver().findElement(By.xpath("//a[@class='mainmenu--signin-item border-right pr-5 m-0']")).click();
        getDriver().findElement(By.xpath("//span[@class='input-block mb-5 ']/input[@name='identity']"))
                .sendKeys("analama");
        getDriver().findElement(By.xpath("//span[@class='input-block mb-5 ']/input[@name='password']"))
                .sendKeys("1234");
        getDriver().findElement(By.xpath("//button[@class='w-100 btn btn-secondary']")).click();
        String actualResult = getDriver().findElement(By.xpath("//div[@class='text-danger border border-danger p-3 mb-3']")).getText();

        Assert.assertEquals(expectedResult, actualResult);
    }

}
