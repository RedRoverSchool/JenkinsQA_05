import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDonSimonTutankhamonTest extends BaseTest {

    @Test
    public void testFormSubm_AutomationinTestingOnline() {

        String testName = "John Cena";
        String actualConfirmationTitle = String.format("Thanks for getting in touch %s!", testName);

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

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

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

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

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
    public void testFirstNameInputField_SelectorsHubCom() throws InterruptedException {

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

        WebElement firstNameInputField = getDriver()
                .findElement(By.xpath("//input[@placeholder='First Enter name']"));

        Assert.assertFalse(firstNameInputField.isEnabled());

        WebElement enableEditFirstNameInputFiledButton = getDriver()
                .findElement(By.xpath("//label[text()='Can you enter name here through automation ']"));

        enableEditFirstNameInputFiledButton.click();

        Thread.sleep(2500);

        Assert.assertTrue(firstNameInputField.isEnabled());
    }

    @Test
    public void testIfPageSourceContainsHtmlTag_SelectorsHubCom() {

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

        String pageSource = getDriver().getPageSource();

        Assert.assertTrue(pageSource.contains("</html>"));
    }

    @Test
    public void testSessionId_SelectorsHubCom() {

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

        SessionId sessionId = ((RemoteWebDriver) getDriver()).getSessionId();

        Assert.assertNotNull(sessionId);
    }

    @Test
    public void testFooter_SelectorsHubCom() {

        String expectedEmail = "support@selectorshub.com";

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

        WebElement footer = getDriver().findElement(By.tagName("footer"));

        Assert.assertTrue(footer.isDisplayed());
        Assert.assertEquals(footer.getDomAttribute("id"), "colophon");

        WebElement emailSpan = getDriver().findElement(By.xpath("//footer//li[last()]//span[last()]"));

        Assert.assertEquals(emailSpan.getText(), expectedEmail);
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

    @Test
    public void testDoingSmthDontKnowWhatExactly_Tchernomor() {
        String url = "https://demoqa.com/";
        getDriver().get("https://www.toolsqa.com/selenium-training/");

        WebElement findDemoSiteLink = getDriver().findElement(By.xpath(
                "//div[@class='col-auto']//li[3]/a"
        ));
        findDemoSiteLink.click();

        for (String pages : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(pages);
        }

        Assert.assertEquals(getDriver().getCurrentUrl(), url);
    }

    @Test
    public void testChangeCategoryInSidebarWhenChoosingWomenCategory() {
        getDriver().get("http://automationpractice.com/");

        WebElement womenCategoryButton = getDriver().findElement(By.xpath("//li/a[@title='Women']"));
        womenCategoryButton.click();

        WebElement sidebarCategoryName = getDriver().findElement(
                By.xpath("//div[@id='categories_block_left']/h2"));


        Assert.assertEquals(sidebarCategoryName.getText(), "WOMEN");
    }
}