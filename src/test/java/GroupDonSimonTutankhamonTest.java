import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDonSimonTutankhamonTest extends BaseTest {

    private static final String SELECTROSHUB_URL = "https://selectorshub.com/xpath-practice-page/";
    private static final String WEBDRIVERUNI_DROPDOWN_URL = "https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html";
    private static final String HEROKUAPP_URL = "https://formy-project.herokuapp.com/";

    @Test
    public void testFormSubm_AutomationinTestingOnline_IKrlkv() {

        final String testName = "John Cena";
        final String actualConfirmationTitle = String.format("Thanks for getting in touch %s!", testName);

        getDriver().get("https://automationintesting.online/");

        WebElement nameField = getDriver().findElement(By.id("name"));
        Actions action = new Actions(getDriver());
        action.moveToElement(nameField).build().perform();
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

        WebElement confirmationFormTitle = getDriver().findElement(By.xpath("//div[@class='row contact']//h2"));

        Assert.assertEquals(confirmationFormTitle.getText(), actualConfirmationTitle);
    }

    @Test
    public void testDropDown_SelectorsHubCom_IKrlkv() {

        final int expectedRowsCount = 99;

        getDriver().get(SELECTROSHUB_URL);

        WebElement dropDownMenu = getDriver().findElement(By.xpath("//select[@name='tablepress-1_length']"));

        Actions actions = new Actions(getDriver());
        actions.scrollToElement(dropDownMenu).build().perform();

        Select select = new Select(dropDownMenu);
        select.selectByVisibleText("100");

        List<WebElement> actualRowsCount = getDriver().findElements(By.xpath("//tbody[@class='row-hover']//tr"));

        Assert.assertEquals(actualRowsCount.size(), expectedRowsCount);
    }

    @Test
    public void testFilterTable_SelectorsHubCom_IKrlkv() {

        final int expectedRowsCount = 3;
        final String expectedCountry = "United States";

        getDriver().get(SELECTROSHUB_URL);

        WebElement tableFilterTextBox = getDriver().findElement(By.xpath("//input[@aria-controls='tablepress-1']"));
        tableFilterTextBox.sendKeys("United States");

        List<String> actualValues = getDriver().findElements(By.xpath("//td[@class='column-5']"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(actualValues.size(), expectedRowsCount);
        for (String actualValue : actualValues) {
            Assert.assertEquals(actualValue, expectedCountry);
        }
    }

    @Test
    public void testFirstNameInputField_SelectorsHubCom_IKrlkv() {

        getDriver().get(SELECTROSHUB_URL);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement firstNameInputField = getDriver()
                .findElement(By.xpath("//input[@placeholder='First Enter name']"));

        Assert.assertFalse(firstNameInputField.isEnabled());

        getDriver().findElement(By.xpath("//label[text()='Can you enter name here through automation ']"))
                .click();

        Assert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(firstNameInputField)).isEnabled());
    }

    @Test
    public void testIfPageSourceContainsHtmlTag_SelectorsHubCom_IKrlkv() {

        getDriver().get(SELECTROSHUB_URL);

        String pageSource = getDriver().getPageSource();

        Assert.assertTrue(pageSource.contains("</html>"));
    }

    @Test
    public void testSessionId_SelectorsHubCom_IKrlkv() {

        getDriver().get(SELECTROSHUB_URL);

        SessionId sessionId = ((RemoteWebDriver) getDriver()).getSessionId();

        Assert.assertNotNull(sessionId);
    }

    @Test
    public void testFooterEmailAddress_SelectorsHubCom_IKrlkv() {

        final String expectedEmail = "support@selectorshub.com";

        getDriver().get(SELECTROSHUB_URL);

        WebElement footer = getDriver().findElement(By.tagName("footer"));

        Assert.assertTrue(footer.isDisplayed());
        Assert.assertEquals(footer.getDomAttribute("id"), "colophon");
        Assert.assertEquals(getDriver().findElement(By.xpath("//footer//li[last()]//span[last()]")).getText(), expectedEmail);
    }

    @Test
    public void testTextByLink_SelectorsHubCom_IKrlkv() {

        final String textByLink = "A tool to generate manual test case automatically, click to learn more";

        getDriver().get(SELECTROSHUB_URL);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions
                .attributeContains(By.xpath("//div[@data-id='aa151c7']"), "transform", "none"));

        WebElement linkByText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(textByLink)));
        WebElement linkByPartialText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("generate")));

        Assert.assertEquals(linkByText.getTagName(), "a");
        Assert.assertEquals(linkByText.getCssValue("box-sizing"), "border-box");
        Assert.assertEquals(linkByPartialText.getRect(), linkByText.getRect());
        Assert.assertEquals(linkByPartialText.getLocation(), linkByPartialText.getLocation());
    }

    @Test
    public void testCheckBoxes_WebdDiverUniversityCom_iKrlkv() {

        getDriver().get(WEBDRIVERUNI_DROPDOWN_URL);

        WebElement checkedCheckbox = getDriver().findElement(By.cssSelector("[type=checkbox]:checked"));
        WebElement unCheckedCheckbox = getDriver().findElement(By.cssSelector("[type=checkbox]:not(:checked)"));

        Assert.assertEquals(checkedCheckbox.getAttribute("value"), "option-3");
        Assert.assertTrue(checkedCheckbox.isSelected());
        Assert.assertEquals(unCheckedCheckbox.getAttribute("value"), "option-1");
        Assert.assertFalse(unCheckedCheckbox.isSelected());

        checkedCheckbox.click();
        unCheckedCheckbox.click();

        Assert.assertFalse(checkedCheckbox.isSelected());
        Assert.assertTrue(unCheckedCheckbox.isSelected());
    }

    @Test
    public void testRadioButtons_WebdDiverUniversityCom_iKrlkv() {

        getDriver().get(WEBDRIVERUNI_DROPDOWN_URL);

        WebElement checkedRadioButton = getDriver().findElement(By.xpath("//input[@type='radio' and @checked]"));
        WebElement unCheckedRadioButton = getDriver().findElement(By.xpath("//input[@type='radio' and not(@checked) and @value='lettuce']"));
        WebElement disabledRadioButton = getDriver().findElement(By.xpath("//input[@type='radio' and @disabled]"));

        Assert.assertEquals(checkedRadioButton.getAttribute("value"), "pumpkin");
        Assert.assertTrue(checkedRadioButton.isSelected());
        Assert.assertEquals(unCheckedRadioButton.getAttribute("name"), "vegetable");
        Assert.assertFalse(unCheckedRadioButton.isSelected());
        Assert.assertEquals(disabledRadioButton.getAttribute("type"), "radio");
        Assert.assertFalse(disabledRadioButton.isEnabled());

        unCheckedRadioButton.click();

        Assert.assertFalse(checkedRadioButton.isSelected());
        Assert.assertTrue(unCheckedRadioButton.isSelected());
    }

    @Test
    public void testRelativeLocator_WebdDiverUniversityCom_iKrlkv() {

        getDriver().get("https://webdriveruniversity.com/Data-Table/index.html");

        WebElement blockQuote = getDriver().findElement(By.xpath("//blockquote/p"));
        RelativeLocator.RelativeBy relativeBy = RelativeLocator.with(By.tagName("mark"));
        WebElement fieldWithRandomText = getDriver().findElement(relativeBy.above(blockQuote));

        Assert.assertEquals(fieldWithRandomText.getText(), "sed do eiusmod tempor incididunt ut labore");
    }

    @Test
    public void testHiddenElements_WebdDiverUniversityCom_iKrlkv() {

        getDriver().get("https://webdriveruniversity.com/Hidden-Elements/index.html");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement notDisplayedButton = getDriver().findElement(By.id("button1"));
        WebElement hiddenButton = getDriver().findElement(By.xpath("//span[@id='button2']"));
        WebElement zeroOpacityButton = getDriver().findElement(By.id("button3"));

        Assert.assertTrue(notDisplayedButton.isEnabled());
        Assert.assertFalse(notDisplayedButton.isDisplayed());
        Assert.assertTrue(hiddenButton.isEnabled());
        Assert.assertFalse(hiddenButton.isDisplayed());
        Assert.assertTrue(zeroOpacityButton.isEnabled());
        Assert.assertFalse(zeroOpacityButton.isDisplayed());

        zeroOpacityButton.click();

        WebElement modalWindow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModalMoveClick")));

        Assert.assertTrue(modalWindow.isDisplayed());
    }

    @Test
    @Ignore
    public void testDatePicker_WebdDiverUniversityCom_iKrlkv() {

        final LocalDate today = LocalDate.now();
        final LocalDate todayOneYearAgo = today.minusYears(1);
        final int currentYear = today.getYear();
        final int currentMonth = today.getMonthValue();
        final int currentDay = today.getDayOfMonth();
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        final String expectedDate = todayOneYearAgo.format(dateFormat);

        getDriver().get("https://webdriveruniversity.com/Datepicker/index.html");

        WebElement datePicker = getDriver().findElement(By.xpath("//div[@id='datepicker']/input"));
        datePicker.click();

        WebElement monthAndYearButton = getDriver().findElement(By.xpath(String.format("//th[contains (text(), %s)]", currentYear)));
        monthAndYearButton.click();

        WebElement leftArrow = getDriver().findElement(RelativeLocator.with(By.tagName("th")).toRightOf(monthAndYearButton));
        leftArrow.click();

        WebElement monthToClick = getDriver().findElements(By.xpath("//div[@class='datepicker-months']//span")).get(currentMonth - 1);
        monthToClick.click();

        WebElement dayToClick = getDriver().findElements(By.xpath("//div[@class='datepicker-days']//td[@class='day']")).get(currentDay - 1);
        dayToClick.click();

        final String actualDate = datePicker.getAttribute("value");
        Assert.assertEquals(actualDate, expectedDate);
    }

    @Test
    public void testFileUpload_WebdDiverUniversityCom_iKrlkv() throws IOException {

        final String url ="https://webdriveruniversity.com/File-Upload/index.html";
        final Path tempFile = Files.createTempFile("tempfiles", ".tmp");
        final String fileName = tempFile.toAbsolutePath().toString();

        getDriver().get(url);

        WebElement uploadFileField = getDriver().findElement(By.id("myFile"));
        uploadFileField.sendKeys(fileName);

        getDriver().findElement(By.id("submit-button")).submit();

        Assert.assertEquals(getDriver().getCurrentUrl(), url + "?filename=" + tempFile.getFileName());
    }

    @Test
    public void testSlider_DemoqaCom_iKrlkv() {

        final String minSliderValue = "0";
        final String maxSliderValue = "100";
        final String defaultSliderValue = "25";
        final int stepsToMove = 55;

        getDriver().get("https://demoqa.com/slider");

        WebElement slider = getDriver().findElement(By.xpath("//input[@type='range']"));

        Assert.assertEquals(slider.getAttribute("min"), minSliderValue);
        Assert.assertEquals(slider.getAttribute("max"), maxSliderValue);
        Assert.assertEquals(slider.getAttribute("value"), defaultSliderValue);

        for (int i = 0; i < stepsToMove; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        final int resultSliderValue = Integer.parseInt(defaultSliderValue) + stepsToMove;

        Assert.assertEquals(slider.getAttribute("value"), String.valueOf(resultSliderValue));

        WebElement sliderValueWindow = getDriver().findElement(By.id("sliderValue"));
        Assert.assertEquals(sliderValueWindow.getAttribute("value"), String.valueOf(resultSliderValue));
    }

    @Test
    public void testButtonsClicks_DemoqaCom_iKrlkv() {

        getDriver().get("https://demoqa.com/buttons");
        Actions actions = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement dropDown1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("doubleClickBtn")));
        actions.doubleClick(dropDown1).build().perform();
        WebElement contextMenu1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("doubleClickMessage")));

        WebElement dropDown2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rightClickBtn")));
        actions.contextClick(dropDown2).build().perform();
        WebElement contextMenu2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rightClickMessage")));

        WebElement dropDown3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Click Me']")));
        actions.click(dropDown3).build().perform();
        WebElement contextMenu3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicClickMessage")));

        Assert.assertTrue(contextMenu1.isDisplayed());
        Assert.assertTrue(contextMenu2.isDisplayed());
        Assert.assertTrue(contextMenu3.isDisplayed());
    }

    @Test
    public void testDragAndDrop_WebdDiverUniversityCom_iKrlkv() {

        getDriver().get("https://webdriveruniversity.com/Actions/index.html");
        Actions actions = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement draggable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("draggable")));
        WebElement target = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("droppable")));

        actions.dragAndDrop(draggable, target).build().perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='droppable']//b")).getText(), "Dropped!");
    }

    @Test
    public void testCopyPaste_WebdDiverUniversityCom_iKrlkv() {

        getDriver().get("https://webdriveruniversity.com/Contact-Us/contactus.html");
        Actions actions = new Actions(getDriver());

        WebElement firstNameField = getDriver().findElement(By.name("first_name"));
        WebElement lastNameField = getDriver().findElement(By.name("last_name"));

        actions.sendKeys(firstNameField, "John Cena").keyDown(Keys.CONTROL).sendKeys(firstNameField, "a")
                .sendKeys(firstNameField, "c").sendKeys(lastNameField, "v").build().perform();

        Assert.assertEquals(firstNameField.getText(), lastNameField.getText());
    }

    @Test
    public void testButtonsLinkText_HerokuApp_iKrlkv() {

        getDriver().get(HEROKUAPP_URL);

        WebElement buttonsLink = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));

        Assert.assertEquals(buttonsLink.getText(), "Buttons");
    }

    @Test
    public void testButtonsPageURL_HerokuApp_iKrlkv() {

        getDriver().get(HEROKUAPP_URL);

        getDriver().findElement(By.linkText("Buttons")).click();

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
    public void testSuccessfulLoginAndLogout() {
        getDriver().get("http://the-internet.herokuapp.com/login");

        WebElement usernameInput = getDriver().findElement(By.id("username"));
        String usernameText = getDriver().findElement(By.xpath("//h4/em")).getText();
        usernameInput.sendKeys(usernameText);
        WebElement usernamePassword = getDriver().findElement(By.id("password"));
        String usernamePasswordText = getDriver().findElement(By.xpath("//h4/em[2]")).getText();
        usernamePassword.sendKeys(usernamePasswordText);
        WebElement loginButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://the-internet.herokuapp.com/secure");

        WebElement logoutButton = getDriver().findElement(
                By.xpath("//div[@id='content']//a[@href='/logout']"));
        logoutButton.click();
        WebElement loginPage = getDriver().findElement(By.xpath("//h2"));

        Assert.assertEquals(loginPage.getText(), "Login Page");
    }

    @Test
    public void testEnteringNameInAlertAndConfirmation_Snegafal() {
        final String name = "Emma";

        getDriver().get("https://demoqa.com/alerts");
        String resultAlertText = String.format("You entered %s", name);
        getDriver().findElement(By.id("promtButton")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys(name);
        alert.accept();

        Assert.assertEquals(getDriver().findElement(By.id("promptResult")).getText(), resultAlertText);
    }

    @Test
    public void test_CheckItemsPriceSortedFromLowToHigh_Snegafal() throws InterruptedException {
        final String username = "standard_user";
        final String password = "secret_sauce";

        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys(username);
        getDriver().findElement(By.id("password")).sendKeys(password);
        getDriver().findElement(By.id("login-button")).click();
        Select dropdown = new Select(getDriver().findElement(By.xpath("//select[@class='product_sort_container']")));
        dropdown.selectByValue("lohi");
        Thread.sleep(1000);
        List<WebElement> itemPrice = getDriver().findElements(By.xpath("//div[@class='inventory_item_price']"));
        List itemsPrices = new ArrayList();
        for (WebElement web : itemPrice) {
            Double price = Double.valueOf(web.getText().substring(1));
            itemsPrices.add(price);
        }
        List tempList = new ArrayList(itemsPrices);
        Collections.sort(tempList);

        Assert.assertEquals(itemsPrices, tempList);
    }
}