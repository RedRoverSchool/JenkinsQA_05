import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupBugBustersTest extends BaseTest {

    @Test
    public void testArailymSuccessLogIn() {

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");

        getDriver().findElement(By.id("menu-toggle")).click();
        getDriver().findElement(By.xpath("//a[@href = 'profile.php#login']")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(), "Make Appointment");
    }

    @Test
    public void testNadiaSucssesDoctorAppointment() throws InterruptedException {

        final String EXPECTED_RESULT_HOSPITAL_NAME = "Hongkong CURA Healthcare Center";
        final String EXPECTED_APPLAY_HOSPITAL_READMISSION = "Yes";
        final String EXPECTED_RESULT_HEALTH_PROGRAM = "Medicaid";
        final String EXPECTED_RESULT_COMMENT = "";

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");

        getDriver().findElement(By.xpath("//a[@id='btn-make-appointment']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();
        getDriver().findElement(By.id("combo_facility")).click();

        getDriver().findElement(By.xpath("//select[@id='combo_facility']/option" +
                "[@value='Hongkong CURA Healthcare Center']")).click();
        getDriver().findElement(By.xpath("//input[@id='chk_hospotal_readmission']")).click();
        getDriver().findElement(By.xpath("//input[@id='radio_program_medicaid']")).click();
        getDriver().findElement(By.xpath("//span[@class='glyphicon glyphicon-calendar']")).click();
        getDriver().findElement(By.xpath("//body/div/div/table/tbody/tr[4]/td[4]")).click();
        getDriver().findElement(By.id("btn-book-appointment")).click();

        Assert.assertEquals(getDriver().findElement(By.id("facility")).getText(), EXPECTED_RESULT_HOSPITAL_NAME);
        Assert.assertEquals(getDriver().findElement(By.id("hospital_readmission")).getText(), EXPECTED_APPLAY_HOSPITAL_READMISSION);
        Assert.assertEquals(getDriver().findElement(By.id("program")).getText(), EXPECTED_RESULT_HEALTH_PROGRAM);
        Assert.assertTrue(getDriver().findElement(By.id("visit_date")).getText() != null);
        Assert.assertEquals(getDriver().findElement(By.id("comment")).getText(), EXPECTED_RESULT_COMMENT);
    }

    @Test
    public void testRadasSuccessLogIn() throws InterruptedException {

        getDriver().get("https://www.wunderground.com/");

        getDriver().findElement(By.xpath("//a[@href='/login']")).click();
        getDriver().findElement(By.id("form-signin-email")).sendKeys("motoxx68@gmail.com");
        getDriver().findElement(By.id("form-signin-password")).sendKeys("Intsnewpassword");
        Thread.sleep(5000);
        getDriver().findElement(By.id("signIn")).click();
        getDriver().findElement(By.xpath
                ("//button[@class='wu-account close-login ng-star-inserted'][text()=' My Profile ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath
                ("//li[@translatecontext='wu-header-user-login']")).getText(), "Welcome back!");
    }


    @Test
    public void testArailymCheckAddress() {

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");

        final String ADDRESS_LOG_OUT = getDriver().findElement(By.xpath("//body/footer/div/div/div/p[1]")).getText();

        getDriver().findElement(By.id("btn-make-appointment")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();

        String addressLogIn = getDriver().findElement(By.xpath("//body/footer/div/div/div/p[1]")).getText();

        Assert.assertEquals(ADDRESS_LOG_OUT, addressLogIn);
    }

    @Test
    public void testArailymScrollDownByPixel() {

        final String EXPECTED_RESULT = "Find out more";

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        getDriver().get("https://learnenglish.britishcouncil.org/");

        js.executeScript("window.scrollBy(0,1000)", "");
        String actualResult = getDriver().findElement(
                By.xpath(
                        "//a[@href='/online-courses/live-online-classes?promo_id=lc&promo_name=" +
                                "live-classes&promo_creative=cards&promo_position=homepage']")
        ).getText();

        Assert.assertEquals(actualResult, EXPECTED_RESULT);
    }

    @Test
    public void testScrollDownToBottom() {

        final String EXPECTED_RESULT = "© British Council\n" +
                "The United Kingdom's international organisation for cultural relations and educational opportunities.\n" +
                "A registered charity: 209131 (England and Wales) SC037733 (Scotland).";

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        getDriver().get("https://learnenglish.britishcouncil.org/");

        js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
        String actualResult = getDriver().findElement(
                By.xpath("//footer/div/section/section/div/p")
        ).getText();

        Assert.assertEquals(actualResult, EXPECTED_RESULT);
    }

    @Test
    public void testGetUrlDataAndMoveToNewUrl() {

        final String DOMAIN = "katalon-demo-cura.herokuapp.com";
        final String URL = "https://katalon-demo-cura.herokuapp.com/";
        final String TITLE = "CURA Healthcare Service";

        getDriver().get(URL);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        Assert.assertEquals(js.executeScript("return document.domain;").toString(), DOMAIN);
        Assert.assertEquals(js.executeScript("return document.title;").toString(), TITLE);
        Assert.assertEquals(js.executeScript("return document.URL;"), URL);

        js.executeScript("window.location = 'https://learnenglish.britishcouncil.org/'");
    }
}
