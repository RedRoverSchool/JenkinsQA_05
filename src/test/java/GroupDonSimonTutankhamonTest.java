import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDonSimonTutankhamonTest extends BaseTest {

    @Test
    public void test_FormSubmAutomationinTestingOnline_IKrlkv() {

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
    public void testDropDown_SelectorsHubCom() {

        int expectedRowsCount = 99;

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

        WebElement dropDownMenu = getDriver().findElement(By.xpath("//select[@name='tablepress-1_length']"));

        Actions actions = new Actions(getDriver());
        actions.scrollToElement(dropDownMenu).build().perform();

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
    public void testTextByLink_SelectorsHubCom() throws InterruptedException {

        String textByLink = "A tool to generate manual test case automatically, click to learn more";

        getDriver().get("https://selectorshub.com/xpath-practice-page/");

        WebElement linkByText = getDriver().findElement(By.linkText(textByLink));

        Assert.assertEquals(linkByText.getTagName(), "a");
        Assert.assertEquals(linkByText.getCssValue("box-sizing"), "border-box");

        WebElement linkByPartialText = getDriver().findElement(By.partialLinkText("generate"));

        Thread.sleep(1500);

        Assert.assertEquals(linkByPartialText.getRect(), linkByText.getRect());
        Assert.assertEquals(linkByPartialText.getLocation(), linkByPartialText.getLocation());
    }

    @Test
    public void testCheckBoxes_WebdDiverUniversityCom() {

        getDriver().get("https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");

        WebElement checkedCheckbox = getDriver().findElement(By.cssSelector("[type=checkbox]:checked"));
        Assert.assertEquals(checkedCheckbox.getAttribute("value"), "option-3");
        Assert.assertTrue(checkedCheckbox.isSelected());

        WebElement unCheckedCheckbox = getDriver().findElement(By.cssSelector("[type=checkbox]:not(:checked)"));
        Assert.assertEquals(unCheckedCheckbox.getAttribute("value"), "option-1");
        Assert.assertFalse(unCheckedCheckbox.isSelected());

        checkedCheckbox.click();
        Assert.assertFalse(checkedCheckbox.isSelected());

        unCheckedCheckbox.click();
        Assert.assertTrue(unCheckedCheckbox.isSelected());
    }

    @Test
    public void testRadioButtons_WebdDiverUniversityCom() {

        getDriver().get("https://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");

        WebElement checkedRadioButton = getDriver().findElement(By.xpath("//input[@type='radio' and @checked]"));
        Assert.assertEquals(checkedRadioButton.getAttribute("value"), "pumpkin");
        Assert.assertTrue(checkedRadioButton.isSelected());

        WebElement unCheckedRadioButton = getDriver().findElement(By.xpath("//input[@type='radio' and not(@checked) and @value='lettuce']"));
        Assert.assertEquals(unCheckedRadioButton.getAttribute("name"), "vegetable");
        Assert.assertFalse(unCheckedRadioButton.isSelected());

        WebElement disabledRadioButton = getDriver().findElement(By.xpath("//input[@type='radio' and @disabled]"));
        Assert.assertEquals(disabledRadioButton.getAttribute("type"), "radio");
        Assert.assertFalse(disabledRadioButton.isEnabled());

        unCheckedRadioButton.click();
        Assert.assertFalse(checkedRadioButton.isSelected());
        Assert.assertTrue(unCheckedRadioButton.isSelected());
    }

    @Test
    public void testRelativeLocator_WebdDiverUniversityCom() {

        getDriver().get("https://webdriveruniversity.com/Data-Table/index.html");

        WebElement blockQuote = getDriver().findElement(By.xpath("//blockquote/p"));
        RelativeLocator.RelativeBy relativeBy = RelativeLocator.with(By.tagName("mark"));

        WebElement fieldWithRandomText = getDriver().findElement(relativeBy.above(blockQuote));
        Assert.assertEquals(fieldWithRandomText.getText(), "sed do eiusmod tempor incididunt ut labore");
    }

    @Test
    public void testHiddenElements_WebdDiverUniversityCom() throws InterruptedException {

        getDriver().get("https://webdriveruniversity.com/Hidden-Elements/index.html");

        WebElement notDisplayedButton = getDriver().findElement(By.id("button1"));
        Assert.assertTrue(notDisplayedButton.isEnabled());
        Assert.assertFalse(notDisplayedButton.isDisplayed());

        WebElement hiddenButton = getDriver().findElement(By.xpath("//span[@id='button2']"));
        Assert.assertTrue(hiddenButton.isEnabled());
        Assert.assertFalse(hiddenButton.isDisplayed());

        WebElement zeroOpacityButton = getDriver().findElement(By.id("button3"));
        Assert.assertTrue(zeroOpacityButton.isEnabled());
        Assert.assertFalse(zeroOpacityButton.isDisplayed());

        zeroOpacityButton.click();
        Thread.sleep(300);

        WebElement modalWindow = getDriver().findElement(By.id("myModalMoveClick"));
        Assert.assertTrue(modalWindow.isDisplayed());
    }

    @Test
    public void testDatePicker_WebdDiverUniversityCom() {

        LocalDate today = LocalDate.now();
        LocalDate todayOneYearAgo = today.minusYears(1);
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String expectedDate = todayOneYearAgo.format(dateFormat);

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

        String actualDate = datePicker.getAttribute("value");

        Assert.assertEquals(actualDate, expectedDate);
    }

    @Test
    public void testFileUpload_WebdDiverUniversityCom() throws IOException {

        String url ="https://webdriveruniversity.com/File-Upload/index.html";
        Path tempFile = Files.createTempFile("tempfiles", ".tmp");
        String fileName = tempFile.toAbsolutePath().toString();

        getDriver().get(url);

        WebElement uploadFileField = getDriver().findElement(By.id("myFile"));
        uploadFileField.sendKeys(fileName);

        WebElement submitButton = getDriver().findElement(By.id("submit-button"));
        submitButton.submit();

        Assert.assertEquals(getDriver().getCurrentUrl(), url + "?filename=" + tempFile.getFileName());
    }

    @Test
    public void testSlider_DemoqaCom() {

        String minSliderValue = "0";
        String maxSliderValue = "100";
        String defaultSliderValue = "25";
        int stepsToMove = 55;

        getDriver().get("https://demoqa.com/slider");

        WebElement slider = getDriver().findElement(By.xpath("//input[@type='range']"));

        Assert.assertEquals(slider.getAttribute("min"), minSliderValue);
        Assert.assertEquals(slider.getAttribute("max"), maxSliderValue);
        Assert.assertEquals(slider.getAttribute("value"), defaultSliderValue);

        for (int i = 0; i < stepsToMove; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        int resultSliderValue = Integer.parseInt(defaultSliderValue) + stepsToMove;

        Assert.assertEquals(slider.getAttribute("value"), String.valueOf(resultSliderValue));

        WebElement sliderValueWindow = getDriver().findElement(By.id("sliderValue"));
        Assert.assertEquals(sliderValueWindow.getAttribute("value"), String.valueOf(resultSliderValue));
    }
    @Ignore
    @Test
    public void testButtonsClicks_DemoqaCom() {

        getDriver().get("https://demoqa.com/buttons");
        Actions actions = new Actions(getDriver());

        WebElement dropDown1 = getDriver().findElement(By.id("doubleClickBtn"));
        actions.doubleClick(dropDown1).build().perform();
        WebElement contextMenu1 = getDriver().findElement(By.id("doubleClickMessage"));
        Assert.assertTrue(contextMenu1.isDisplayed());

        WebElement dropDown2 = getDriver().findElement(By.id("rightClickBtn"));
        actions.contextClick(dropDown2).build().perform();
        WebElement contextMenu2 = getDriver().findElement(By.id("rightClickMessage"));
        Assert.assertTrue(contextMenu2.isDisplayed());

        WebElement dropDown3 = getDriver().findElement(By.xpath("//button[text()='Click Me']"));
        actions.click(dropDown3).build().perform();
        WebElement contextMenu3 = getDriver().findElement(By.id("dynamicClickMessage"));
        Assert.assertTrue(contextMenu3.isDisplayed());
    }

    @Test
    public void testDragAndDrop_WebdDiverUniversityCom() {

        getDriver().get("https://webdriveruniversity.com/Actions/index.html");
        Actions actions = new Actions(getDriver());

        WebElement draggable = getDriver().findElement(By.id("draggable"));
        WebElement target = getDriver().findElement(By.id("droppable"));

        actions.dragAndDrop(draggable, target).build().perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='droppable']//b")).getText(), "Dropped!");
    }

    @Test
    public void testCopyPaste_WebdDiverUniversityCom() throws InterruptedException {

        getDriver().get("https://webdriveruniversity.com/Contact-Us/contactus.html");
        Actions actions = new Actions(getDriver());

        WebElement firstNameField = getDriver().findElement(By.name("first_name"));
        WebElement lastNameField = getDriver().findElement(By.name("last_name"));

        actions.sendKeys(firstNameField, "John Cena").keyDown(Keys.CONTROL).sendKeys(firstNameField, "a")
                .sendKeys(firstNameField, "c").sendKeys(lastNameField, "v").build().perform();

        Assert.assertEquals(firstNameField.getText(), lastNameField.getText());

        Thread.sleep(5000);
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

    @Ignore
    @Test
    public void testChangeCategoryInSidebarWhenChoosingWomenCategory() {
        getDriver().get("http://automationpractice.com/");

        WebElement womenCategoryButton = getDriver().findElement(By.xpath("//li/a[@title='Women']"));
        womenCategoryButton.click();

        WebElement sidebarCategoryName = getDriver().findElement(
                By.xpath("//div[@id='categories_block_left']/h2"));


        Assert.assertEquals(sidebarCategoryName.getText(), "WOMEN");
    }

    @Test
    public void testSuccessfulLoginAndLogout() {
        getDriver().get("http://the-internet.herokuapp.com/login");

        WebElement usernameInput = getDriver().findElement(By.id("username"));
        String usernameText = getDriver().findElement(By.xpath("//h4/em")).getText();
        usernameInput.sendKeys(usernameText);

        WebElement usernamePassword = getDriver().findElement(By.id("password"));
        String usernamePasswordText= getDriver().findElement(By.xpath("//h4/em[2]")).getText();
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
    public void testEnteringNameInAlertAndConfirmation () {
        getDriver().get("https://demoqa.com/alerts");

        String name = "Emma";
        String resultAlertText = "You entered " + name;
        getDriver().findElement(By.id("promtButton")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys(name);
        alert.accept();

        Assert.assertEquals(getDriver().findElement(By.id("promptResult")).getText(), resultAlertText);
    }

}

