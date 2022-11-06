import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SpiritMastersTest extends BaseTest {

    private static final String URL_DEMOQA = "https://demoqa.com/";

    private WebDriverWait webDriverWait20;

    private WebDriverWait getWait20() {
        if (webDriverWait20 == null) {
            webDriverWait20 = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return webDriverWait20;
    }

    private Actions getActions() {
        return new Actions(getDriver());
    }

    private Select getSelect(WebElement element) {
        return new Select(element);
    }

    private void additionEmoji(String elementById, String emoji) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();
        javascriptExecutor.executeScript("document.getElementById('" + elementById + "').value='" + emoji + "';");
    }


    private WebElement findCard_PK(int index) {
        getDriver().get(URL_DEMOQA);
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
    public void test_PK_RedirectToElementsTab() {
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
    public void test_PK_RedirectToAlertsTab() {
        findCard_PK(2).click();
        String actualTextAlerts = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextAlerts, "Alerts, Frame & Windows");
    }

    @Test
    public void test_PK_RedirectToWidgetsTab() {
        findCard_PK(3).click();
        String actualTextWidgets = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextWidgets, "Widgets");
    }

    @Test
    public void test_PK_RedirectToInteractionsTab() {
        findCard_PK(4).click();
        String actualTextInteractions = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextInteractions, "Interactions");
    }

    @Ignore
    @Test
    public void test_PK_RedirectToBooksTab() {
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

    @Ignore
    @Test
    public void testCheckButtonLink_AFedorova() {
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
        expectedTableResult.put("Mobile", "1234567890");
        expectedTableResult.put("Date of Birth", "15 November,1985");
        expectedTableResult.put("Subjects", "Maths");
        expectedTableResult.put("Hobbies", "Sports");
        expectedTableResult.put("Picture", "");
        expectedTableResult.put("Address", "CA, San Francisco, 17 avn, 1");
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
        getSelect(getDriver().findElement(By.xpath("//select[@class='react-datepicker__month-select']"))).selectByVisibleText("November");
        getSelect(getDriver().findElement(By.xpath("//select[@class='react-datepicker__year-select']"))).selectByVisibleText("1985");

        WebElement month = getWait20().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='react-datepicker__month-select']")));
        getSelect(month).selectByVisibleText("November");

        WebElement year = getWait20().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='react-datepicker__year-select']")));
        getSelect(year).selectByVisibleText("1985");

        getDriver().findElement(By.xpath("//div[@aria-label='Choose Friday, November 15th, 1985']")).click();

        WebElement subjectMenu = getDriver().findElement(By.id("subjectsInput"));
        getActions().moveToElement(subjectMenu).click().sendKeys("Maths").pause(500).sendKeys(Keys.TAB)
                    .scrollToElement(getDriver().findElement(By.id("submit"))).build().perform();

        getDriver().findElement((By.cssSelector("[for=hobbies-checkbox-1]"))).click();

        WebElement currentAddressField = getDriver().findElement(By.id("currentAddress"));
        currentAddressField.click();
        currentAddressField.sendKeys("CA, San Francisco, 17 avn, 1");

        getActions().scrollToElement(getDriver().findElement(By.id("submit")));

        WebElement nameStateMenu = getDriver().findElement(By.id("react-select-3-input"));
        nameStateMenu.sendKeys("NCR");
        getActions().moveToElement(getDriver().findElement(By.id("react-select-3-option-0"))).sendKeys(Keys.TAB).perform();

        WebElement nameCityMenu = getDriver().findElement(By.id("react-select-4-input"));
        nameCityMenu.sendKeys("Delhi");
        getActions().moveToElement(getDriver().findElement(By.id("react-select-4-option-0"))).sendKeys(Keys.TAB).perform();

        getDriver().findElement(By.id("submit")).click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));

        getWait20().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));
        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        Map<String, String> actualTableResult = new HashMap<>();
        for (WebElement row : rows) {
            actualTableResult.put(row.findElements(By.tagName("td")).get(0).getText(), row.findElements(By.tagName("td")).get(1).getText());
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
    public void testRedirectToSeleniumTrainingTab_PK() {
        getDriver().get(URL_DEMOQA);
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
    public void zyzBankRegisterLogin_MW_Test() {
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
    public void testSwitchFrames_OlPolezhaeva() {
        getDriver().get("https://demoqa.com/frames");

        getDriver().switchTo().frame(getDriver().findElement(By.id("frame1")));
        Assert.assertEquals(getDriver().findElement(By.xpath("//body/h1[@id='sampleHeading']")).getText(), "This is a sample page");

        getDriver().switchTo().defaultContent();

        getDriver().switchTo().frame(getDriver().findElement(By.id("frame2")));
        Assert.assertEquals(getDriver().findElement(By.xpath("//body/h1[@id='sampleHeading']")).getText(), "This is a sample page");

        getDriver().switchTo().defaultContent();
        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Frames");
    }

    @Test
    public void testStyleFrame1_OlPolezhaeva() {
        getDriver().get("https://demoqa.com/frames");

        getDriver().switchTo().frame(getDriver().findElement(By.id("frame1")));
        WebElement headerFrame = getDriver().findElement(By.xpath("//body/h1[@id='sampleHeading']"));

        Assert.assertEquals(headerFrame.getRect().getWidth(), 480.0);
        Assert.assertEquals(headerFrame.getRect().getHeight(), 37.0);
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

    @Test
    public void testCheckButtonTutotials_LPlucci() throws InterruptedException {
        getDriver().get("https://www.toolsqa.com/");
        Thread.sleep(1000);
        WebElement openButton = getDriver().findElement(By.xpath("//span[@class='navbar__tutorial-menu--text']"));
        Thread.sleep(1000);
        Assert.assertEquals(openButton.getText(), "TUTORIALS");
    }

    @Test
    public void testModalDialogs_OlPolezhaeva() {
        getDriver().get("https://demoqa.com/modal-dialogs");

        getDriver().findElement(By.id("showSmallModal")).click();

        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.id("closeSmallModal")).click();
        Assert.assertTrue(getDriver().findElement(By.id("showLargeModal")).isDisplayed());
    }

    @Ignore
    @Test
    public void testToolTips_OlPolezhaeva() {
        getDriver().get("https://demoqa.com/tool-tips");

        getActions().moveToElement(getDriver().findElement(By.xpath("//a[text()='Contrary']"))).perform();
        String actualToolTip = new WebDriverWait(getDriver(), Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tooltip-inner']"))).getText();

        Assert.assertEquals(actualToolTip, "You hovered over the Contrary");
    }

    @Test
    public void testTextBoxFields_AFedorova() {
        getDriver().get(URL_DEMOQA);

        String name = "Anna Fedorova";
        String email = "test@gmail.com";
        String cAddress = "40 S Rengstorff Ave, Mountain View, CA 94040";
        String pAddress = "851 Manor Way, Los Altos, CA 94024";

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Name:" + name);
        expectedResult.add("Email:" + email);
        expectedResult.add("Current Address :" + cAddress);
        expectedResult.add("Permananet Address :" + pAddress);

        WebElement elementBtn = getDriver().findElement(By.cssSelector("div.category-cards>div:first-of-type"));
        elementBtn.click();

        WebElement textBoxBtn = getDriver().findElement(By.xpath("//*[@id='item-0']/span"));
        textBoxBtn.click();

        WebElement tbName = getDriver().findElement(By.id("userName"));
        WebElement tbEmail = getDriver().findElement(By.id("userEmail"));
        WebElement tbAddress = getDriver().findElement(By.id("currentAddress"));
        WebElement tbPermAddress = getDriver().findElement(By.id(
                "permanentAddress"));

        tbName.sendKeys(name);
        tbEmail.sendKeys(email);
        tbAddress.sendKeys(cAddress);
        tbPermAddress.sendKeys(pAddress);

        WebElement submitBtn = getDriver().findElement(By.id("submit"));
        getActions().scrollToElement(submitBtn);
        submitBtn.click();


        List<String> actualResult = new ArrayList<>();
        actualResult.add(getDriver().findElement(By.id("name")).getText());
        actualResult.add(getDriver().findElement(By.id("email")).getText());
        actualResult.add(getDriver().findElement(By.xpath("//p[@id='currentAddress']")).getText());
        actualResult.add(getDriver().findElement(By.xpath("//p[@id='permanentAddress']")).getText());

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Ignore
    @Test
    public void testCheckBoxes_AFedorova() {
        List<String> expectedResult = new ArrayList<>(List.of("You have " +
                "selected :", "desktop", "notes", "commands"));

        getDriver().get(URL_DEMOQA);

        getDriver().findElement(By.cssSelector("div.category-cards>div:first" +
                "-of-type")).click();
        getDriver().findElement(By.xpath("//*[@id" +
                "='item-1']/span")).click();
        getDriver().findElement(By.cssSelector(".rct" +
                "-option.rct-option-expand-all")).click();

        List<WebElement> listOfCheckBoxes =
                getDriver().findElements(By.cssSelector("span" +
                        ".rct-checkbox"));
        listOfCheckBoxes.get(3).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("span" +
                ".text-success")).getText(), "commands");

        listOfCheckBoxes.get(2).click();

        List<WebElement> listOfSelectedCheckBoxesDesktop =
                getDriver().findElements(By.cssSelector(".display-result" +
                        ".mt-4>span"));
        List<String> actualResult = new ArrayList<>();
        for (WebElement element : listOfSelectedCheckBoxesDesktop) {
            actualResult.add(element.getText());
        }

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testSlider_KI() {
        getDriver().get(URL_DEMOQA);

        getDriver().findElement(By.xpath("//div[@class='category-cards']/div[4]")).click();
        getActions().scrollToElement(getDriver().findElement(By.xpath("//span[text()='Slider']")));
        getActions().moveToElement(getDriver().findElement(By.xpath("//span[text()='Slider']")))
                .click().pause(500).perform();

        WebElement slider = getDriver().findElement(By.xpath("//input[@type='range']"));
        getActions().dragAndDropBy(slider, 350, 0).pause(500).perform();
        String actualSliderValue = getDriver().findElement(By.id("sliderValue")).getAttribute("value");

        Assert.assertEquals(actualSliderValue, "100");
    }

    @Test
    public void testOpenweathermap_justGoToGuide_gdiksanov() {

        String url = "https://openweathermap.org/";

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        getDriver().get(url);
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@class='owm-loader-container']/div")));

        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[@href='/guide']")).click();

        String currentUrl = getDriver().getCurrentUrl();
        String currentTitle = getDriver().getTitle();

        Assert.assertEquals(currentUrl, "https://openweathermap.org/guide");
        Assert.assertEquals(currentTitle, "OpenWeatherMap API guide - OpenWeatherMap");
    }

    @Test
    public void testWebTables_KI() {
        getDriver().get(URL_DEMOQA);

        getDriver().findElement(By.xpath("//div[@class='category-cards']/div[1]")).click();
        getActions().moveToElement(getDriver().findElement(By.xpath("//span[text()='Web Tables']")))
                    .click().pause(250).perform();
        getDriver().findElement(By.id("addNewRecordButton")).click();

        additionEmoji("firstName", "\uD83D\uDCA9\uD83D\uDCA9\uD83D\uDCA9");
        additionEmoji("lastName", "(ノಠ益ಠ)ノ彡┻━┻");

        getActions().pause(250)
                .moveToElement(getDriver().findElement(By.id("firstName"))).click().sendKeys(" ")
                .moveToElement(getDriver().findElement(By.id("lastName"))).click().sendKeys(" ")
                .moveToElement(getDriver().findElement(By.id("userEmail"))).click().sendKeys("email...@...domain...com")
                .moveToElement(getDriver().findElement(By.id("age"))).click().sendKeys("0")
                .moveToElement(getDriver().findElement(By.id("salary"))).click().sendKeys("0000999999")
                .moveToElement(getDriver().findElement(By.id("department"))).click().sendKeys("ООО \"3,14-3-ДАТА\"")
                .moveToElement(getDriver().findElement(By.id("submit"))).click().perform();

        List<WebElement> actualNumberWorkers = getDriver().
                findElements(By.xpath("//div[@class='action-buttons']"));

        Assert.assertEquals(actualNumberWorkers.size(), 4);
    }

}
