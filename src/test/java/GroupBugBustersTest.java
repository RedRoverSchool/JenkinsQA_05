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
        final String expectedResultHospitalName = "Hongkong CURA Healthcare Center";
        final String expectedApplayHospitalReadmission = "Yes";
        final String expectedResultHealthProgram = "Medicaid";
        final String expectedResultVisitDate = "23/11/2022";
        final String expectedResultComment = "";

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");

        getDriver().findElement(By.id("btn-make-appointment")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("combo_facility")).click();
        getDriver().findElement(By.xpath("//option[@value='Hongkong CURA Healthcare Center']")).click();
        getDriver().findElement(By.id("chk_hospotal_readmission")).click();
        getDriver().findElement(By.id("radio_program_medicaid")).click();
        getDriver().findElement(By.id("txt_visit_date")).click();
        getDriver().findElement(By.xpath("//table//td[@class='day'][text()='23']")).click();
        getDriver().findElement(By.id("btn-book-appointment")).click();

        Assert.assertEquals(getDriver().findElement(By.id("facility")).getText(), expectedResultHospitalName);
        Assert.assertEquals(getDriver().findElement(By.id("hospital_readmission")).getText(), expectedApplayHospitalReadmission);
        Assert.assertEquals(getDriver().findElement(By.id("program")).getText(), expectedResultHealthProgram);
        Assert.assertEquals(getDriver().findElement(By.id("visit_date")).getText(), expectedResultVisitDate);
        Assert.assertEquals(getDriver().findElement(By.id("comment")).getText(), expectedResultComment);
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

        final String addressLogOut = getDriver().findElement
                (By.xpath("//body/footer/div/div/div/p[1]")).getText();

        getDriver().findElement(By.id("btn-make-appointment")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();

        String addressLogIn = getDriver().findElement(By.xpath("//body/footer/div/div/div/p[1]")).getText();

        Assert.assertEquals(addressLogOut, addressLogIn);
    }

    @Test
    public void testArailymScrollDownByPixel() {
        final String expectedResult = "Find out more";

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        getDriver().get("https://learnenglish.britishcouncil.org/");

        js.executeScript("window.scrollBy(0,1000)", "");
        String actualResult = getDriver().findElement(
                By.xpath("//a[@href='/online-courses/live-online-classes?promo_id=lc&promo_name=" +
                        "live-classes&promo_creative=cards&promo_position=homepage']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testScrollDownToBottom() {
        final String expectedResult = "© British Council\n" +
                "The United Kingdom's international organisation for cultural relations and educational opportunities.\n" +
                "A registered charity: 209131 (England and Wales) SC037733 (Scotland).";

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        getDriver().get("https://learnenglish.britishcouncil.org/");

        js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
        String actualResult = getDriver().findElement(
                By.xpath("//footer/div/section/section/div/p")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testGetUrlDataAndMoveToNewUrl() {
        final String domain = "katalon-demo-cura.herokuapp.com";
        final String url = "https://katalon-demo-cura.herokuapp.com/";
        final String title = "CURA Healthcare Service";

        getDriver().get(url);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        Assert.assertEquals(js.executeScript("return document.domain;").toString(), domain);
        Assert.assertEquals(js.executeScript("return document.title;").toString(), title);
        Assert.assertEquals(js.executeScript("return document.URL;"), url);

        js.executeScript("window.location = 'https://learnenglish.britishcouncil.org/'");
    }
}