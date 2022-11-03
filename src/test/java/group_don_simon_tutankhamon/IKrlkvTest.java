package group_don_simon_tutankhamon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;
import java.util.stream.Collectors;

public class IKrlkvTest extends BaseTest {

    public void getToMainPage_AutomationPracticeCom() {
        getDriver().get("http://automationpractice.com/index.php");
    }

    public void getToContactUsPage_AutomationPracticeCom() {
        getToMainPage_AutomationPracticeCom();
        getDriver().findElement(By.linkText("Contact us")).click();
    }

    public void getToMainPage_SelectorsHubCom() {
        getDriver().get("https://selectorshub.com/xpath-practice-page/");
    }

    @Test
    public void testContactUsPageAddress_AutomationPracticeCom() {

        String expectedResult = "http://automationpractice.com/index.php?controller=contact";

        getToContactUsPage_AutomationPracticeCom();

        Assert.assertEquals(expectedResult, getDriver().getCurrentUrl());
    }

    @Test
    public void testSendMessageFromContactUsPage_AutomationPracticeCom() throws InterruptedException {

        getToContactUsPage_AutomationPracticeCom();

        Select subjectHeading = new Select(getDriver().findElement(By.id("id_contact")));

        subjectHeading.selectByVisibleText("Webmaster");

        Thread.sleep(3000);

        Assert.assertEquals(getDriver().findElement(By.id("desc_contact1")).getText(), "If a technical problem occurs on this website");

        getDriver().findElement(By.id("email")).sendKeys("johncena@123.com");

        getDriver().findElement(By.id("id_order")).sendKeys("1235813");

        getDriver().findElement(By.id("message")).sendKeys("Pack my box with five dozen liquor jugs.");

        getDriver().findElement(By.id("submitMessage")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p[@class='alert alert-success']")).getText(),
                "Your message has been successfully sent to our team.");
    }

    @Test
    public void testFormSubm_AutomationinTestingOnline() {

        String testName = "John Cena";
        String actualConfirmationTitle = String.format("Thanks for getting in touch %s!",testName);

        getDriver().get("https://automationintesting.online/");

        WebElement nameField = getDriver().findElement(By.id("name"));
        Actions action = new Actions(getDriver());
        action.moveToElement(nameField);
        nameField.sendKeys("John Cena");

        WebElement emailField = getDriver().findElement(By.xpath("//input[@data-testid='ContactEmail']"));
        emailField.sendKeys("johncena@123.com");

        WebElement phoneFiled = getDriver().findElement(By.xpath("//input[@placeholder='Phone']"));
        phoneFiled.sendKeys(getDriver().findElement(By.xpath("//p/span[@class='fa fa-phone']/parent::p")).getText());

        WebElement subjectField = getDriver().findElement(By.id("subject"));
        subjectField.sendKeys("Booking request");

        WebElement textAreaField = getDriver().findElement(By.id("description"));
        textAreaField.sendKeys("Pack my box with five dozen liquor jugs.");

        WebElement submitButton = getDriver().findElement(By.id("submitContact"));
        submitButton.click();

        WebElement confirmationTitle = getDriver().findElement(By.xpath("//div[@class='row contact']//h2"));

        Assert.assertEquals(confirmationTitle.getText(), actualConfirmationTitle);
    }

    @Test
    public void testDropDown_SelectorsHubCom() {

        int expectedRowsCount = 99;

        getToMainPage_SelectorsHubCom();

        WebElement dropDownMenu = getDriver().findElement(By.xpath("//select[@name='tablepress-1_length']"));

        Actions actions = new Actions(getDriver());
        actions.scrollToElement(dropDownMenu);

        Select select = new Select(dropDownMenu);
        select.selectByVisibleText("100");

        List<WebElement> actualRowsCount = getDriver().findElements(By.xpath("//tbody[@class='row-hover']//tr"));

        Assert.assertEquals(expectedRowsCount, actualRowsCount.size());
    }

    @Test
    public void testFilterTable_SelectorsHubCom() {

        int expectedRowsCount = 3;
        String expectedCountry = "United States";

        getToMainPage_SelectorsHubCom();

        WebElement tableFilterTextBox = getDriver().findElement(By.xpath("//input[@aria-controls='tablepress-1']"));
        tableFilterTextBox.sendKeys("United States");

        List<String> actualValues = getDriver().findElements(By.xpath("//td[@class='column-5']"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(expectedRowsCount, actualValues.size());
        for (String actualValue : actualValues) {
            Assert.assertEquals(expectedCountry, actualValue);
        }
    }

    @Test
    public void testButtonsLinkText_HerokuApp() {

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement buttonsLink = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));

        Assert.assertEquals(buttonsLink.getText(), "Buttons");
    }

    @Test
    public void testButtonsPageURL_HerokuApp() {

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement buttonsLink = getDriver().findElement(By.linkText("Buttons"));

        buttonsLink.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://formy-project.herokuapp.com/buttons");
    }
}