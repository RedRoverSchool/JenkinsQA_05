import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;

public class GroupCodeRedTest extends BaseTest {
    private final String BASE_URL_HEROKUAPP = "https://formy-project.herokuapp.com";
    private final String WEATHER_URL = "https://openweathermap.org/";

    @Test
    public void testAutocomplete() throws InterruptedException {
        getDriver().get(BASE_URL_HEROKUAPP);

        getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']")).click();
        Thread.sleep(500);
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(actualResult, "Autocomplete");
    }

    @Test
    public void testAutocompleteAddress() throws InterruptedException {
        getDriver().get(BASE_URL_HEROKUAPP);

        getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Autocomplete");

        getDriver().findElement(By.xpath
                ("//div/input[@placeholder='Enter address']")).sendKeys("555 Open road");
        getDriver().findElement(By.xpath("//button[@class='dismissButton']")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getDriver().findElement(By.xpath
                ("//input[@id='autocomplete']")).getAttribute("value"), "555 Open road");
    }

    @Test
    public void testCompleteWebForm() {
        getDriver().get("https://formy-project.herokuapp.com/");
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

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='alert alert-success']"))
                .getText().contains("The form was successfully submitted!"));
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
    public void testFormyButton() {
        getDriver().get(BASE_URL_HEROKUAPP);

        getDriver().findElement(By.xpath("//li/a[@href='/buttons']")).click();
        String actualResult = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult, "https://formy-project.herokuapp.com/buttons");
    }


    @Test
    public void testFormyDatepicker() {
        getDriver().get(BASE_URL_HEROKUAPP);

        getDriver().findElement(By.xpath("//li/a[@href='/datepicker']")).click();
        getDriver().findElement(By.xpath("//div[@class='row']//input[@id='datepicker']")).click();
        getDriver().findElement(By.xpath
                ("/html/body/div[2]/div[1]/table/tbody/tr/td[@class='today day']")).click();
        String actualTitle = getDriver().findElement(By.xpath("/html/body/div/h1")).getText();
        String actualAddress = getDriver().getCurrentUrl();

        Assert.assertEquals(actualTitle, "Datepicker");
        Assert.assertEquals(actualAddress, "https://formy-project.herokuapp.com/datepicker");
    }

    @Test
    public void testFormyDropdown()  {
        String dropdownMenu = "Dropdown";
        getDriver().get(BASE_URL_HEROKUAPP);

        getDriver().findElement(By.linkText(dropdownMenu)).click();
        getDriver().findElement(By.id("dropdownMenuButton")).click();

        String actualResult = getDriver().getCurrentUrl();
        String actualTitle = getDriver().findElement(By.xpath("/html/body/div/h1")).getText();

        Assert.assertEquals(actualTitle, dropdownMenu);
        Assert.assertEquals(actualResult, "https://formy-project.herokuapp.com/dropdown");
        Assert.assertEquals(getDriver().findElements(By.xpath(
                "/html/body/div/div/div/a[@class='dropdown-item']")).size(),15);
    }

    @Test
    public void testToggleMenuGuide() throws InterruptedException {
        getDriver().get(WEATHER_URL);
        Thread.sleep(7000);

        WebElement guideLink = getDriver().findElement(By.xpath
                ("//div[@id='desktop-menu']//a[text()='Guide']"));

        Assert.assertEquals(guideLink.getText(), "Guide");

        guideLink.click();
        Thread.sleep(500);
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/guide");

        WebElement homeLink = getDriver().findElement(By.xpath
                ("//ol[@class='breadcrumb pull-right hidden-xs']//a"));
        Assert.assertEquals(homeLink.getText(), "Home");
    }

    @Test
    public void testToggleMenuAPI() throws InterruptedException {
        getDriver().get(WEATHER_URL);
        Thread.sleep(7000);

        WebElement apiLink = getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[text()='API']"));

        Assert.assertEquals(apiLink.getText(), "API");
        apiLink.click();
        Thread.sleep(500);

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/api");

        WebElement homeLink = getDriver().findElement(By.xpath
                ("//ol[@class='breadcrumb pull-right hidden-xs']//a"));
        Assert.assertEquals(homeLink.getText(), "Home");
    }

    @Test
    public void testToggleMenuDashboard() throws InterruptedException {
        getDriver().get(WEATHER_URL);
        Thread.sleep(10000);

        WebElement dashboardLink = getDriver().findElement(By.xpath
                ("//div[@id='desktop-menu']//a[text()='Dashboard']"));
        Assert.assertEquals(dashboardLink.getText(), "Dashboard");
        dashboardLink.click();
        Thread.sleep(1000);

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/weather-dashboard");

        WebElement homeLink = getDriver().findElement(By.xpath
                ("//ol[@class='breadcrumb pull-right hidden-xs']//a"));
        Assert.assertEquals(homeLink.getText(), "Home");
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
    @Ignore
    @Test
    public void testYourAdress() throws InterruptedException {
        getDriver().get("https://insurance.experian.com/sign-up/address");

        Thread.sleep(2000);
        getDriver().findElement(By.cssSelector("input[name='address_field_input']")).sendKeys
                ("input[name='address_field_input']");
    }

    @Test
    public void testEmail() throws InterruptedException {
        getDriver().get("https://insurance.experian.com/sign-up/email");

        Thread.sleep(2000);
        getDriver().findElement(By.cssSelector
                ("input[name='email']")).sendKeys("MinnieMouse@cheese.com");
        getDriver().findElement(By.cssSelector("button[data-title='Next Step']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(),"https://insurance.experian.com/sign-up/email");
    }
    @Test
    public void testTermsOfCondition() throws InterruptedException {
        getDriver().get("https://insurance.experian.com/sign-up/phone");

        Thread.sleep(500);
        getDriver().findElement(By.xpath("//input[@name='phone_number']")).sendKeys("831888888");
        WebElement enteredPhone = getDriver().findElement(By.xpath("//input[@name='phone_number']"));

        Assert.assertEquals(enteredPhone.getAttribute("value"), "(831) 888 888");
    }

    @Test
    public void test_FindRome() throws InterruptedException {

        String city = "Rome";

        getDriver().get(WEATHER_URL);
        Thread.sleep(8000);

        getDriver().findElement(
                By.xpath("//div[@id='desktop-menu']//input[@placeholder='Weather in your city']")
        ).sendKeys(city + "\n");
        Thread.sleep(700);

        Assert.assertTrue(getDriver().getCurrentUrl().contains("find") && getDriver().getCurrentUrl().contains(city));
    }

    @Test
    public void testOpenWeatherMapGuidePageTitle() throws InterruptedException {
        String expectedResult_1 = "https://openweathermap.org/guide";
        String expectedResult_2 = "OpenWeatherMap API guide - OpenWeatherMap";

        getDriver().get(WEATHER_URL);
        Thread.sleep(10000);
        getDriver().findElement(By.xpath("//div[@id = 'desktop-menu']//li/a [@href='/guide']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), expectedResult_1);
        Assert.assertEquals(getDriver().getTitle(), expectedResult_2);
    }

    @Test
    public void testAutoCompleteFieldsWebForm() {


        getDriver().get(BASE_URL_HEROKUAPP + "/form");

        getDriver().findElement(By.xpath("//input[@id ='first-name']")).sendKeys("Don");
        getDriver().findElement(By.xpath("//input[@id ='last-name']")).sendKeys("Red");
        getDriver().findElement(By.xpath("//input[@id ='job-title']")).sendKeys("QA");
        getDriver().findElement(By.xpath("//div/input[@id='radio-button-2']")).click();
        getDriver().findElement(By.xpath
                ("//div[@class ='input-group']/div/input[@type='checkbox' and @value='checkbox-1']")).click();
        getDriver().findElement(By.xpath("//select[@id='select-menu']")).click();
        getDriver().findElement(By.xpath("//select/option[@value ='2']")).click();
        getDriver().findElement(By.xpath("//input[@id='datepicker']")).click();
        getDriver().findElement(By.xpath("//div/div/table/tbody/tr/td[@class='today day']")).click();
        getDriver().findElement(By.xpath("//a[@href='/thanks']")).click();
        String actualResult = getDriver().findElement(By.xpath("//div[@class='alert alert-success']")).getText();

        Assert.assertEquals(actualResult, "The form was successfully submitted!");
    }

    @Test
    public void testModalWindow() throws InterruptedException{
        getDriver().get(BASE_URL_HEROKUAPP);

        getDriver().findElement(By.xpath("//li/a[@href='/modal']")).click();
        getDriver().findElement(By.xpath("//button[@id='modal-button']")).click();
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
    }

    @Test
    public void testCheckbox() throws InterruptedException {

        getDriver().get(BASE_URL_HEROKUAPP);

        Assert.assertEquals(getDriver().getTitle(), "Formy");
        getDriver().findElement(By.xpath("//li[3]/a[@class = 'btn btn-lg']")).click();
        WebElement name=getDriver().findElement(By.xpath( "//h1[contains(text(),'Checkboxes')]"));
        Assert.assertEquals(name.getText(), "Checkboxes");
        WebElement name1=getDriver().findElement(By.xpath( "//div[@class = 'col-sm-8']"));
        Assert.assertEquals(name1.getText(), "Checkbox1");
        WebElement name2=getDriver().findElement(By.xpath( "//div[2]/div/div"));
        Assert.assertEquals(name2.getText(), "Checkbox2");
        WebElement name3=getDriver().findElement(By.xpath( "//div[3]/div/div"));
        Assert.assertEquals(name3.getText(), "Checkbox3");

        WebElement checkBox1 =getDriver().findElement(By.id( "checkbox-1"));
        checkBox1.click();
        Assert.assertTrue(checkBox1.isSelected());
        WebElement checkBox2 =getDriver().findElement(By.id( "checkbox-2"));
        checkBox2.click();
        Assert.assertTrue(checkBox2.isSelected());
        WebElement checkBox3 =getDriver().findElement(By.id( "checkbox-3"));
        checkBox3.click();
        Thread.sleep(2000);
        Assert.assertTrue(checkBox3.isSelected());
    }

    @Test
    public void test_scrollPageDownAndFillingFields() {
        final String  fullName ="Don Sanches";
        final String date = "11/07/2022";

        getDriver().get(BASE_URL_HEROKUAPP);

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

    @Test
    public void test_dragAndDropElement(){
        final String EXPECTED_TEXT_DROPBOX= "Dropped!";

        getDriver().get("http://jqueryui.com/droppable");

        getDriver().switchTo().frame(0);
        WebElement sourceElement=  getDriver().findElement(By.id("draggable"));
        WebElement distinationElement=  getDriver().findElement(By.id("droppable"));
        Actions action = new Actions( getDriver());
        action.dragAndDrop(sourceElement, distinationElement).build().perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id ='droppable']/p")).getText(),
                EXPECTED_TEXT_DROPBOX);
    }

    @Test
    public void positiveLoginTest()  {

        getDriver().get("http://qa.jtalks.org/jcommune/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.id("userName")).sendKeys("test-user");
        getDriver().findElement(By.id("password")).sendKeys("test-user");
        getDriver().findElement(By.id("signin-submit-button")).click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        Assert.assertTrue(getDriver().findElement(By.id("user-dropdown-menu-link")).getText().contains("test-user"));
    }

    @Test
    public void negativeLoginTest()  {
        getDriver().get("http://qa.jtalks.org/jcommune/");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.id("userName")).sendKeys("test-user");
        getDriver().findElement(By.id("password")).sendKeys("test");
        getDriver().findElement(By.id("signin-submit-button")).click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[@class = 'help-inline _error']"))
                .getText().contains("Неверное имя пользователя или пароль"));
    }

    @Test
    public void testButtonHerokuApp() {

        getDriver().get(BASE_URL_HEROKUAPP);

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));

        Assert.assertEquals(link.getText(), "Buttons");
    }

    @Test
    public void testH2PopUpText_WhenClickingCookiesButton() throws InterruptedException {
        getDriver().get("https://rus.delfi.lv/");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//div[@id='qc-cmp2-ui'] //button[@mode='secondary']")).click();
        WebElement popupH2Text = getDriver().findElement(By.xpath("//div[@id ='qc-cmp2-ui']//h2"));

        Assert.assertEquals(popupH2Text.getTagName(),"h2");
        Assert.assertEquals(popupH2Text.getText(),"Мы с уважением относимся к вашей конфиденциальности");
    }
}
