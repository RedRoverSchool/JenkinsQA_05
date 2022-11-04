import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class RustamKhRedCodeTest extends BaseTest {
    private String baseURL = "https://formy-project.herokuapp.com";
    @Test

    public void testLinkName(){

        getDriver().get("https://formy-project.herokuapp.com/");

        String Link =  getDriver().findElement(By.xpath("//li/a[text()='Autocomplete']")).getText();
        Assert.assertEquals(Link, "Autocomplete");
        

    }
    @Test
    public void testAutoCompleteFieldsWebForm() {


        getDriver().get(baseURL + "/form");
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
}
