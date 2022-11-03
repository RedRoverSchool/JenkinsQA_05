import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class SpiritMastersTest extends BaseTest {

    private WebElement findCard_PK(int index) {
        getDriver().get("https://demoqa.com/");
        List<WebElement> category = getDriver().findElements(By.className("card"));
        return category.get(index);
    }

    @Test
    public void testSwitchToSecondWindow_OlPolezhaeva() {

        getDriver().get("https://www.toolsqa.com/selenium-training/");

        WebElement toolsQAHeader = getDriver().findElement(By.xpath("//div[@class='col-auto']//li[3]"));
        toolsQAHeader.click();
        getDriver().findElement(By.xpath("//div[@class='col-auto']//li[3]")).click();

        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.xpath("//div[@class='card-body']/h5")).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Elements");
    }

    @Test
    public void test_PK_RedirectToElementsTab(){
        findCard_PK(0).click();
        String actualTextElements = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextElements, "Elements");
    }

    @Test
    public void test_PK_RedirectToFormsTab() {
        findCard_PK(1).click();
        String actualTextForms = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextForms, "Forms");
    }

    @Test
    public void test_PK_RedirectToAlertsTab(){
        findCard_PK(2).click();
        String actualTextAlerts = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextAlerts, "Alerts, Frame & Windows");
    }

    @Test
    public void test_PK_RedirectToWidgetsTab(){
        findCard_PK(3).click();
        String actualTextWidgets = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextWidgets, "Widgets");
    }

    @Test
    public void test_PK_RedirectToInteractionsTab(){
        findCard_PK(4).click();
        String actualTextInteractions = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextInteractions, "Interactions");
    }
    @Ignore
    @Test
    public void test_PK_RedirectToBooksTab(){
        findCard_PK(5).click();
        String actualTextBooks = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextBooks, "Book Store");
    }

    @Test
    public void testHerokuapp_gdiksanov() {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));

        Assert.assertEquals(link.getText(), "Autocomplete");
    }

    @Test
    public void checkButtonsLink_AFedorova_Test() {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.cssSelector("a.btn-lg" +
                "[href^=\"/butt\"]"));
        Assert.assertEquals(link.getText(), "Buttons");
    }
    @Ignore
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
        new Select(getDriver().findElement(By.xpath("//select[@class='react-datepicker__month-select']"))).selectByVisibleText("November");
        new Select(getDriver().findElement(By.xpath("//select[@class='react-datepicker__year-select']"))).selectByVisibleText("1985");
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

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        Map<String, String> actualTableResult = new HashMap<>();
        for (WebElement row : rows) {
            actualTableResult.put(row.findElements(By.tagName("td")).get(0).getText(),row.findElements(By.tagName("td")).get(1).getText());
        }

        Assert.assertEquals(actualTableResult, expectedTableResult);
    }

    @Test
    public void testDSOpenQABible() throws InterruptedException {
        getDriver().get("https://vladislaveremeev.gitbook.io/qa_bible/");
        Thread.sleep(1500);
        WebElement firstTitle = getDriver().findElement(By.linkText("QA_Bible"));
        Assert.assertEquals(firstTitle.getText(), "QA_Bible");
    }

    @Test
    public void testRedirectToSeleniumTrainingTab_PK(){
        getDriver().get("https://demoqa.com/");
        String currentWindow = getDriver().getWindowHandle();
        WebElement newWindow = getDriver().findElement(By.xpath("//div[@class='home-banner']/a"));
        String link = newWindow.getAttribute("href");
        Assert.assertEquals(link, "https://www.toolsqa.com/selenium-training/");
        newWindow.click();
        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!currentWindow.contentEquals(windowHandle)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Tools QA - Selenium Training");
    }

    @Test
    public void zyzBankRegisterLogin_MW_Test()  {
        getDriver().get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        getDriver().findElement(By.xpath("//button[normalize-space()='Bank Manager Login']")).click();
        getDriver().findElement(By.xpath("//button[normalize-space()='Add Customer']")).click();
        WebElement firstName = getDriver().findElement(By.xpath("//input[@placeholder='First Name']"));
        firstName.click();
        firstName.sendKeys("John");
        WebElement lastName = getDriver().findElement(By.xpath("//input[@placeholder='Last Name']"));
        lastName.click();
        lastName.sendKeys("NeJonh");
        WebElement postcode = getDriver().findElement(By.xpath("//input[@placeholder='Post Code']"));
        postcode.click();
        postcode.sendKeys("12334");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        Alert confAllert = getDriver().switchTo().alert();
        confAllert.accept();
        getDriver().findElement(By.xpath("//button[@class='btn btn-lg tab btn-primary']")).click();
        getDriver().findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/button[1]")).click();
        WebElement login = getDriver().findElement(By.xpath("//button[normalize-space()='Customer Login']"));
        Assert.assertEquals(login.getText(), "Customer Login");
        login.click();
        WebElement selectNameVariant = getDriver().findElement(By.id("userSelect"));
        Select dropdown = new Select(selectNameVariant);
        dropdown.selectByValue("6");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/strong/span")).getText(), "John NeJonh");

    }

    @Test
    public void testDSFindTitle() throws InterruptedException {
        getDriver().get("https://vladislaveremeev.gitbook.io/qa_bible/");
        Thread.sleep(1500);
        getDriver().findElement(By.xpath("//div[4]//a[1]//div[1]")).click();
        WebElement title = getDriver().findElement(By.xpath(
                "//div[contains(@class, 'css-901oao r')][contains(text(),'Принципы тестирования')]"));
        title.click();
        Assert.assertEquals(title.getText(), "Принципы тестирования");
    }
}
