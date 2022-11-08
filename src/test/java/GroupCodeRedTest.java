import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

import static java.sql.DriverManager.getDriver;


public class GroupCodeRedTest extends BaseTest {
    private final String baseUrlHerokuapp = "https://formy-project.herokuapp.com";

    public static void get(WebDriver driver, String url) {
        driver.get(url);
    }

    public static WebElement findElementBy(WebDriver driver, String cssSelector) {

        return driver.findElement(By.cssSelector(cssSelector));
    }



    public static void sleep(int sec) throws InterruptedException {
        Thread.sleep(1000);
    }
    @Test
    public void testAutocomplete() throws InterruptedException {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));
        link.click();
        Thread.sleep(500);
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualResult, "Autocomplete");
    }

    @Test
    public void testAutocompleteAddress() throws InterruptedException {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));
        link.click();
        Thread.sleep(500);
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualResult, "Autocomplete");
//        getDriver().findElement(By.id("autocomplete")).clear();
//        getDriver().findElement(By.id("autocomplete")).sendKeys("Sample text");
        WebElement address = getDriver().findElement(By.xpath("//div/input[@placeholder='Enter address']"));
        address.sendKeys("555 Open road");
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//button[@class='dismissButton']")).click();
        WebElement enteredAddress = getDriver().findElement(By.xpath("//input[@id='autocomplete']"));
//        enteredAddress.clear();
        Thread.sleep(500);
        Assert.assertEquals(enteredAddress.getAttribute("value"), "555 Open road");
    }

    @Test
    public void testCompleteWebForm() {
        getDriver().get("https://formy-project.herokuapp.com/");
        getDriver().manage().window().maximize();
        String actualTitle = getDriver().getTitle();
        Assert.assertEquals(actualTitle, "Formy");
        getDriver().findElement(By.xpath("//div/li/a[@href='/form']")).click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        getDriver().findElement(By.id("first-name")).sendKeys("John");
        getDriver().findElement(By.id("last-name")).sendKeys("Doe");
        getDriver().findElement(By.id("job-title")).sendKeys("Unemployed");
        getDriver().findElement(By.id("radio-button-3")).click();
        getDriver().findElement(By.id("checkbox-3")).click();
        Select dropdown = new Select(getDriver().findElement(By.id("select-menu")));
        dropdown.selectByValue("1");
        getDriver().findElement(By.xpath("//div/a[@href='/thanks']")).click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='alert alert-success']")).getText().contains("The form was successfully submitted!"));
    }

    @Test
    public void testGetPage() {

        getDriver().get("https://www.demoblaze.com/");
        WebElement link = getDriver().findElement(By.cssSelector("div.col-sm-4 p"));
        Assert.assertEquals(link.getText(), "We believe performance needs to be validated at every stage of " +
                "the software development cycle and our open source compatible," +
                " massively scalable platform makes that a reality.");
    }

    @Test
    public void testverifyAPI() throws InterruptedException {
        getDriver().get("https://openweathermap.org");
        Thread.sleep(5000);
        getDriver().findElement(By.cssSelector("#desktop-menu > ul > li:nth-child(2) > a")).click();
        getDriver().findElement(By.cssSelector("h2 > a")).click();
        WebElement onecallAPI = getDriver().findElement(By.xpath("//section[@id='how']/div/code"));

        Assert.assertEquals(onecallAPI.getText(), "https://api.openweathermap.org/data/3.0/onecall?lat={lat}" +
                "&lon={lon}&exclude={part}&appid={API key}");
    }

    @Test
    public void testButton() {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));
        link.click();
        String actualResult = getDriver().getCurrentUrl();
        Assert.assertEquals(actualResult, "https://formy-project.herokuapp.com/buttons");
    }


    @Test
    public void testDatepicker() {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/datepicker']"));
        link.click();
        String actualTitle = getDriver().findElement(By.xpath("/html/body/div/h1")).getText();
        Assert.assertEquals(actualTitle, "Datepicker");
        String actualAddress = getDriver().getCurrentUrl();
        Assert.assertEquals(actualAddress, "https://formy-project.herokuapp.com/datepicker");
        WebElement dateInput = getDriver().findElement(By.xpath("//div[@class='row']//input[@id='datepicker']"));
        dateInput.click();
        WebElement todayDate = getDriver().findElement(By.xpath
                ("/html/body/div[2]/div[1]/table/tbody/tr/td[@class='today day']"));
        todayDate.click();
    }

    @Test
    public void testDropdown() throws InterruptedException {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/dropdown']"));
        link.click();
        String actualResult = getDriver().getCurrentUrl();
        Assert.assertEquals(actualResult, "https://formy-project.herokuapp.com/dropdown");
        String actualTitle = getDriver().findElement(By.xpath("/html/body/div/h1")).getText();
        Assert.assertEquals(actualTitle, "Dropdown");
        WebElement dropDown = getDriver().findElement(By.xpath("//div[@class='dropdown']" +
                "//button[@id=\"dropdownMenuButton\"]"));
        dropDown.click();
        WebElement modal = getDriver().findElement(By.xpath("/html/body/div/div/div/a[11]"));
        modal.click();
        String actualModalResult = getDriver().getCurrentUrl();
        Assert.assertEquals(actualModalResult, "https://formy-project.herokuapp.com/modal");
        Thread.sleep(100);
        String actualModalHeader = getDriver().findElement(By.xpath("/html/body/div/h1")).getText();
        Assert.assertEquals(actualModalHeader, "Modal");
    }

    @Test
    public void testToggleMenuGuide() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");
        Thread.sleep(5000);
        WebElement guideLink = getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[text()='Guide']"));
        Assert.assertEquals(guideLink.getText(), "Guide");
        guideLink.click();
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://openweathermap.org/guide");

        WebElement homeLink = getDriver().findElement(By.xpath("//ol[@class='breadcrumb pull-right hidden-xs']//a"));
        Assert.assertEquals(homeLink.getText(), "Home");
        homeLink.click();
    }

    @Test
    public void testToggleMenuAPI() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");
        Thread.sleep(5000);
        WebElement apiLink = getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[text()='API']"));
        Assert.assertEquals(apiLink.getText(), "API");
        apiLink.click();
        Thread.sleep(2000);
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/api");

        WebElement homeLink = getDriver().findElement(By.xpath("//ol[@class='breadcrumb pull-right hidden-xs']//a"));
        Assert.assertEquals(homeLink.getText(), "Home");
        homeLink.click();
    }

    @Ignore
    @Test
    public void testToggleMenuDashboard() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");
        Thread.sleep(5000);
        WebElement dashboardLink = getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[text()='Dashboard']"));
        Assert.assertEquals(dashboardLink.getText(), "Dashboard");
        dashboardLink.click();
        Thread.sleep(2000);
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/weather-dashboard");

        WebElement homeLink = getDriver().findElement(By.xpath("//ol[@class='breadcrumb pull-right hidden-xs']//a"));
        Assert.assertEquals(homeLink.getText(), "Home");
        homeLink.click();
    }

    @Test

    public void testWhenYouWereBornPage() throws InterruptedException {
        getDriver().get("https://insurance.experian.com/sign-up/birthdate");
        Thread.sleep(2000);
        WebElement dateBirthday = getDriver().findElement(
                By.cssSelector("input[name='date_of_birth']"));
        Thread.sleep(2000);
        dateBirthday.sendKeys("05051994");
        Thread.sleep(2000);
        WebElement buttonNext = getDriver().findElement(By.cssSelector("button[data-title='Next Step']"));
        buttonNext.click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://insurance.experian.com/sign-up/address");
    }

    @Test
    public void testYourAdress() throws InterruptedException {
        get(getDriver(), "https://insurance.experian.com/sign-up/address");
        sleep(2);
        WebElement  inputTest = findElementBy(getDriver(),"input[name='address_field_input']");sleep(2);
        inputTest.sendKeys("142 1/2 E Broadway St, Shelbyville, IN 46176");sleep(2);

    }

    @Ignore
    @Test
    public void testEmail() throws InterruptedException {
        get(getDriver(),"https://insurance.experian.com/sign-up/email");sleep(4);

        WebElement inputEmailAddress = getDriver().findElement(
                By.cssSelector("input[name='email']"));sleep(3);
        inputEmailAddress.sendKeys("MinnieMouse@cheese.com");
        WebElement buttonNext = getDriver().findElement(By.cssSelector("button[data-title='Next Step']"));sleep(2);
        buttonNext.click();sleep(2);
        Assert.assertEquals(getDriver().getCurrentUrl(),"https://insurance.experian.com/sign-up/phone");
    }
    @Test
    public void testTermsOfCondition() throws InterruptedException {
        get(getDriver(),"https://insurance.experian.com/sign-up/phone");sleep(4);
        WebElement enterPhoNumber = getDriver().findElement(
                By.xpath("//input[@name='phone_number']"));
        enterPhoNumber.sendKeys("8318888888");
        WebElement buttonNext = getDriver().findElement(By.cssSelector("button[data-title='Next Step']"));sleep(4);
        Assert.assertEquals(getDriver().getCurrentUrl(),"https://insurance.experian.com/sign-up/phone");
        buttonNext.click();sleep(4);

    }

    @Test
    public void testFindRome() throws InterruptedException {

        String url = "https://openweathermap.org/";
        String city = "Rome";
        boolean expectedResult = true;

        getDriver().get(url);

        Thread.sleep(5000);

        WebElement searchFieldByCity = getDriver().findElement(
                By.xpath("//div[@id='desktop-menu']//input[@placeholder='Weather in your city']")
        );

        searchFieldByCity.click();
        searchFieldByCity.sendKeys(city);
        Thread.sleep(700);
        searchFieldByCity.sendKeys(Keys.ENTER);

        boolean actualResult = getDriver().getCurrentUrl().contains("find") && getDriver().getCurrentUrl().contains(city);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testOpenWeatherMapGuidePageTitle() throws InterruptedException {
        String baseUrl = "https://openweathermap.org/";
        String expectedResult_1 = "https://openweathermap.org/guide";
        String expectedResult_2 = "OpenWeatherMap API guide - OpenWeatherMap";

        getDriver().get(baseUrl);
        Thread.sleep(5000);
        WebElement guideButton = getDriver().findElement(
                By.xpath("//div[@id = 'desktop-menu']//li/a [@href='/guide']")
        );
        guideButton.click();
        String actualResult_1 = getDriver().getCurrentUrl();
        String actualResult_2 = getDriver().getTitle();

        Assert.assertEquals(actualResult_1, expectedResult_1);
        Assert.assertEquals(actualResult_2, expectedResult_2);
    }

    @Test
    public void testAutoCompleteFieldsWebForm() {


        getDriver().get(baseUrlHerokuapp + "/form");

        WebElement firstName = getDriver().findElement(By.xpath("//input[@id ='first-name']"));
        firstName.sendKeys("Don");
        WebElement lastName = getDriver().findElement(By.xpath("//input[@id ='last-name']"));
        lastName.sendKeys("Red");
        WebElement jobTitle = getDriver().findElement(By.xpath("//input[@id ='job-title']"));
        jobTitle.sendKeys("QA");
        WebElement educationCollege = getDriver().findElement(By.xpath("//div/input[@id='radio-button-2']"));
        educationCollege.click();
        WebElement selectSexMale = getDriver()
                .findElement(By
                        .xpath("//div[@class ='input-group']/div/input[@type='checkbox' and @value='checkbox-1']"));
        selectSexMale.click();
        getDriver().findElement(By.xpath("//select[@id='select-menu']")).click();
        WebElement selectExperience =getDriver().findElement(By.xpath("//select/option[@value ='2']"));
        selectExperience.click();
        getDriver().findElement(By.xpath("//input[@id='datepicker']")).click();
        WebElement dateToday = getDriver().findElement(By.xpath("//div/div/table/tbody/tr/td[@class='today day']"));
        dateToday.click();
        WebElement submitButton = getDriver().findElement(By.xpath("//a[@href='/thanks']"));
        submitButton.click();
        String actualResult = getDriver().findElement(By.xpath("//div[@class='alert alert-success']")).getText();

        Assert.assertEquals(actualResult, "The form was successfully submitted!");
    }

    @Test
    public void testModalWindow() throws InterruptedException{
        getDriver().get(baseUrlHerokuapp);

        WebElement modalLink = getDriver().findElement(By.xpath("//li/a[@href='/modal']"));
        modalLink.click();
        WebElement buttonOpenModal = getDriver().findElement(By.xpath("//button[@id='modal-button']"));
        buttonOpenModal.click();
        Thread.sleep(2000);
        String actualResult = getDriver().findElement(By.xpath("//h5")).getText();

        Assert.assertEquals(actualResult,"Modal title");
    }

    @Test
    public void testCorrectTitlePresence() throws InterruptedException {

        String baseUrl = "https://portal.311.nyc.gov/";
        String expectedResult = "Home  · NYC311";
        String actualResult;

        getDriver().get(baseUrl);
        Thread.sleep(2000);
        actualResult = getDriver().getTitle();

        Assert.assertEquals(actualResult, expectedResult);

        getDriver().quit();
    }

    @Test
    public void testCheckbox() {

        getDriver().get("https://formy-project.herokuapp.com/");

        String actualTitle = getDriver().getTitle();
        Assert.assertEquals(actualTitle, "Formy");
        getDriver().findElement(By.xpath("//li[3]/a[@class = 'btn btn-lg']")).click();
        WebElement name=getDriver().findElement(By.xpath( "//h1[contains(text(),'Checkboxes')]"));
        Assert.assertEquals(name.getText(), "Checkboxes");
        WebElement name1=getDriver().findElement(By.xpath( "//div[@class = 'col-sm-8']"));
        Assert.assertEquals(name1.getText(), "Checkbox1");
        getDriver().findElement(By.xpath("//input[@id='checkbox-2']")).click();
        WebElement name2=getDriver().findElement(By.xpath( "//div[2]/div/div"));
        Assert.assertEquals(name2.getText(), "Checkbox2");
        WebElement name3=getDriver().findElement(By.xpath( "//div[3]/div/div"));

        Assert.assertEquals(name3.getText(), "Checkbox3");
    }
    @Test
    public void test_scrollPageDownAndFillingFields() {
        final String  fullName ="Don Sanches";
        final String date = "11/07/2022";

        getDriver().get(baseUrlHerokuapp);

        getDriver().findElement(By.xpath("//a[@class ='btn btn-lg' and @href='/scroll']")).click();
        JavascriptExecutor jsScroll = (JavascriptExecutor) getDriver();
        WebElement elementFullName = getDriver().findElement(By.xpath("//input[@placeholder='Full name']"));
        WebElement elementDate = getDriver().findElement(By.xpath("//input[@placeholder='MM/DD/YYYY']"));
        jsScroll.executeScript("arguments[0].scrollIntoView();",elementFullName);
        elementFullName.sendKeys(fullName);
        elementDate.sendKeys(date);

        Assert.assertEquals(elementFullName.getAttribute("value"), fullName);
        Assert.assertEquals(elementDate.getAttribute("value"), date);


    }



}
