import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpiritMastersTest extends BaseTest {

    @Test
    public void testSwitchToSecondWindow_OlPolezhaeva() {

        getDriver().get("https://www.toolsqa.com/selenium-training/");

        getDriver().findElement(By.xpath("//div[@class='col-auto']//li[3]")).click();

        for(String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.xpath("//div[@class='card-body']/h5")).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Elements");
    }

    @Test
    public void testFillRegistrationForm_OlPolezhaeva() {

        getDriver().get("https://demoqa.com/automation-practice-form");

        Map<String, String> expectedTableResult = new HashMap<>();
        expectedTableResult.put("Student Name", "Peter Ivanov");
        expectedTableResult.put("Student Email", "a@a.ru");
        expectedTableResult.put("Gender", "Male");
        expectedTableResult.put ("Mobile", "1234567890");
        expectedTableResult.put("Date of Birth", "15 November,1985");
        expectedTableResult.put ("Subjects", "Maths");
        expectedTableResult.put("Hobbies", "Sports");
        expectedTableResult.put("Picture", "");
        expectedTableResult.put("Address","CA, San Francisco, 17 avn, 1");
        expectedTableResult.put("State and City", "NCR Delhi");

        WebElement firstNameField = getDriver().findElement(By.id("firstName"));
        firstNameField.click();
        firstNameField.sendKeys("Peter");

        WebElement lastNameField = getDriver().findElement(By.id("lastName"));
        lastNameField.click();
        lastNameField.sendKeys("Ivanov");


        WebElement emailField = getDriver().findElement(By.id("userEmail"));
        emailField.click();
        emailField.sendKeys("a@a.ru");

        getDriver().findElement(By.cssSelector("[for='gender-radio-1']")).click();

        WebElement userNumberField = getDriver().findElement(By.id("userNumber"));
        userNumberField.click();
        userNumberField.sendKeys("1234567890");

        getDriver().findElement(By.id("dateOfBirthInput")).click();
        new Select(getDriver().findElement(By.xpath("//div[@class='react-datepicker__header']//select[@class='react-datepicker__month-select']"))).selectByVisibleText("November");
        new Select(getDriver().findElement(By.xpath("//div[@class='react-datepicker__header']//select[@class='react-datepicker__year-select']"))).selectByVisibleText("1985");
        getDriver().findElement(By.xpath("//div[@aria-label='Choose Friday, November 15th, 1985']")).click();

        WebElement subjectMenu = getDriver().findElement(By.id("subjectsInput"));
        subjectMenu.click();
        subjectMenu.sendKeys("Maths");

        getDriver().findElement(By.id("react-select-2-option-0")).click();

        getDriver().findElement(By.cssSelector("[for=hobbies-checkbox-1]")).click();

        WebElement currentAddressField = getDriver().findElement(By.id("currentAddress"));
        currentAddressField.click();
        currentAddressField.sendKeys("CA, San Francisco, 17 avn, 1");

        WebElement nameStateMenu = getDriver().findElement(By.id("react-select-3-input"));
        nameStateMenu.sendKeys("NCR");

        getDriver().findElement(By.id("react-select-3-option-0")).click();

        WebElement nameCityMenu = getDriver().findElement(By.id("react-select-4-input"));
        nameCityMenu.sendKeys("Delhi");

        getDriver().findElement(By.id("react-select-4-option-0")).click();

        WebElement submitBtn = getDriver().findElement(By.id("submit"));
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("arguments[0].click();", submitBtn);

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        Map<String, String> actualTableResult = new HashMap<>();
        for (WebElement row : rows) {
            actualTableResult.put(row.findElements(By.tagName("td")).get(0).getText(),row.findElements(By.tagName("td")).get(1).getText());
        }

        Assert.assertEquals(actualTableResult, expectedTableResult);
    }
}
