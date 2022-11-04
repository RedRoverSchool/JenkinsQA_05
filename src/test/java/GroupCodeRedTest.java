import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;


public class GroupCodeRedTest extends BaseTest {
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
        Assert.assertEquals(enteredAddress.getAttribute("value"),"555 Open road");
    }

    @Test
    public void testCompleteWebForm(){
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
    public void testButton() {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));
        link.click();
        String actualResult = getDriver().getCurrentUrl();
        Assert.assertEquals(actualResult, "https://formy-project.herokuapp.com/buttons");
    }

    @Test
    public void testDatepicker()  {
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
                ("/html/body/div[2]/div[1]/table/tbody/tr[1]/td[@class='today day']"));
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
        Assert.assertEquals(actualModalHeader,"Modal");
    }
}
