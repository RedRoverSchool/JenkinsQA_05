import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupBugBustersTest extends BaseTest {

    @Test
    public void testArailymSuccessLogIn(){
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        getDriver().findElement(By.xpath("//body/a/i")).click();
        getDriver().findElement(By.xpath("//body/nav/ul/li/a[@href = 'profile.php#login']")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(), "Make Appointment");
    }

    @Test
    public void testNadiaSucssesDoctorAppointment() throws InterruptedException {
        String expectedResultHospitalName = "Hongkong CURA Healthcare Center";
        String expectedApplayHospitalReadmission = "Yes";
        String expectedResultHealthProgram = "Medicaid";
        String expectedResultVisitDate = "23/11/2022";
        String expectedResultComment = "";

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        getDriver().findElement(By.xpath("//a[@id='btn-make-appointment']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("combo_facility")).click();

        getDriver().findElement(By.xpath("//select[@id='combo_facility']/option" +
                "[@value='Hongkong CURA Healthcare Center']")).click();
        //Thread.sleep(2000);
        getDriver().findElement(By.xpath("//input[@id='chk_hospotal_readmission']")).click();
        //Thread.sleep(1000);
        getDriver().findElement(By.xpath("//input[@id='radio_program_medicaid']")).click();
        getDriver().findElement(By.xpath("//section[@id=\"appointment\"]/div/div/form/div/div/div/div/span" +
                "[@class='glyphicon glyphicon-calendar']")).click();
        getDriver().findElement(By.xpath("//body/div/div/table/tbody/tr[4]/td[4]")).click();
        getDriver().findElement(By.id("btn-book-appointment")).click();

        String actualResulttHospitalName = getDriver().findElement(By.id("facility")).getText();
        String actualResulttApplayHospitalReadmission = getDriver().findElement(By.id("hospital_readmission")).getText();
        String actualResultHealthProgram = getDriver().findElement(By.id("program")).getText();
        String actualResultVisitDate = getDriver().findElement(By.id("visit_date")).getText();
        String actualResultComment = getDriver().findElement(By.id("comment")).getText();

        Assert.assertEquals(actualResulttHospitalName, expectedResultHospitalName);
        Assert.assertEquals(actualResulttApplayHospitalReadmission, expectedApplayHospitalReadmission);
        Assert.assertEquals(actualResultHealthProgram, expectedResultHealthProgram);
        Assert.assertEquals(actualResultVisitDate, expectedResultVisitDate);
        Assert.assertEquals(actualResultComment, expectedResultComment);
    }

    @Test
    public void testRadasSuccessLogIn() throws InterruptedException {
        getDriver().get("https://www.wunderground.com/");
        getDriver().findElement(By.xpath("//div[@id='global-header']/lib-login/div/p/span/a")).click();
        Thread.sleep(5000);
        getDriver().findElement(By.xpath("//input[@id='form-signin-email']")).sendKeys("motoxx68@gmail.com");
        getDriver().findElement(By.xpath("//input[@id='form-signin-password']")).sendKeys("Intsnewpassword");
        getDriver().findElement(By.id("signIn")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//div[@id = 'global-header']//button[@class = 'wu-account close-login ng-star-inserted']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@translatecontext='wu-header-user-login']")).getText(), "Welcome back!");
    }
    @Test
    public void testArailymCheckAddress(){
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        String addressLogOut = getDriver().findElement(By.xpath("//body/footer/div/div/div/p[1]")).getText();
        getDriver().findElement(By.id("btn-make-appointment")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();
        String addressLogIn = getDriver().findElement(By.xpath("//body/footer/div/div/div/p[1]")).getText();
        Assert.assertEquals(addressLogOut, addressLogIn);
    }
}
