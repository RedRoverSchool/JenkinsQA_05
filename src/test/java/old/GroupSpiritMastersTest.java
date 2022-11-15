package old;

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

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Ignore
public class GroupSpiritMastersTest extends BaseTest {

    private static final String URL_DEMOQA = "https://demoqa.com/";
    private static final String URL_DEMOQA_FRAMES = "https://demoqa.com/frames";
    private static final String URL_HEROKU_FORMY = "https://formy-project.herokuapp.com/";
    private static final String URL_OPENWEATHER = "https://openweathermap.org/";

    private WebDriverWait webDriverWait20;

    private WebDriverWait getWait20() {
        webDriverWait20 = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

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
    public void test_SwitchToSecondWindow_OlPolezhaeva() {
        getDriver().get("https://www.toolsqa.com/selenium-training/");

        getDriver().findElement(By.xpath("//div[@class='col-auto']//li[3]")).click();

        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.xpath("//div[@class='card-body']/h5")).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Elements");
    }

    @Test
    public void test_RedirectToElementsTab_PK() {
        findCard_PK(0).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Elements");
    }

    @Test
    public void test_RedirectToFormsTab_PK() {
        findCard_PK(1).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Forms");
    }

    @Test
    public void test_RedirectToAlertsTab_PK() {
        findCard_PK(2).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Alerts, Frame & Windows");
    }

    @Test
    public void test_RedirectToWidgetsTab_PK() {
        findCard_PK(3).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Widgets");
    }

    @Test
    public void test_RedirectToInteractionsTab_PK() {
        findCard_PK(4).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Interactions");
    }

    @Ignore
    @Test
    public void test_RedirectToBooksTab_PK() {
        getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
        findCard_PK(5).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Book Store");
    }

    @Test
    public void test_Herokuapp_gdiksanov() {
        getDriver().get(URL_HEROKU_FORMY);

        Assert.assertEquals(getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']")).getText(), "Autocomplete");
    }

    @Ignore
    @Test
    public void test_CheckButtonLink_AFedorova() {
        getDriver().get(URL_HEROKU_FORMY);

        Assert.assertEquals(getDriver().findElement(By.cssSelector("a.btn-lg" + "[href^='/butt']")).getText(), "Buttons");
    }

    @Ignore
    @Test
    public void test_FillRegistrationForm_OlPolezhaeva() {

        final Map<String, String> expectedTableResult = new TreeMap<>();
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

        getDriver().get("https://demoqa.com/automation-practice-form");

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

        getWait20().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));
        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Map<String, String> actualTableResult = new TreeMap<>();
        for (WebElement row : rows) {
            actualTableResult.put(row.findElements(By.tagName("td")).get(0).getText(), row.findElements(By.tagName("td")).get(1).getText());
        }

        Assert.assertEquals(actualTableResult, expectedTableResult);
    }

    @Test
    public void test_OpenQABible_DS() {
        getDriver().get("https://vladislaveremeev.gitbook.io/qa_bible/");
        getWait20().until(ExpectedConditions.visibilityOfElementLocated(By.linkText("QA_Bible")));

        Assert.assertEquals(getDriver().findElement(By.linkText("QA_Bible")).getText(), "QA_Bible");
    }

    @Test
    public void test_RedirectToSeleniumTrainingTab_PK() {
        getDriver().get(URL_DEMOQA);

        getDriver().findElement(By.xpath("//div[@class='home-banner']/a")).click();

        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }

        Assert.assertEquals(getDriver().getTitle(), "Tools QA - Selenium Training");
    }

    @Test
    public void test_BankRegisterLogin_MW() {
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

        Alert confAlert = getDriver().switchTo().alert();
        String userId = confAlert.getText().substring(confAlert.getText().indexOf(":") + 1);
        confAlert.accept();

        getDriver().findElement(By.xpath("//button[@class='btn btn-lg tab btn-primary']")).click();
        getDriver().findElement(By.cssSelector(".btn.home")).click();
        getDriver().findElement(By.xpath("//button[normalize-space()='Customer Login']")).click();

        getSelect(getDriver().findElement(By.id("userSelect"))).selectByValue(userId);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//strong/span[@class='fontBig ng-binding']")).getText(),
                "John NeJonh");
    }

    @Test
    public void test_SwitchFrames_OlPolezhaeva() {
        getDriver().get(URL_DEMOQA_FRAMES);

        getDriver().switchTo().frame(getDriver().findElement(By.id("frame1")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//body/h1[@id='sampleHeading']")).getText(), "This is a sample page");

        getDriver().switchTo().defaultContent();
        getDriver().switchTo().frame(getDriver().findElement(By.id("frame2")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//body/h1[@id='sampleHeading']")).getText(), "This is a sample page");

        getDriver().switchTo().defaultContent();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Frames");
    }

    @Test
    public void test_StyleFrame_OlPolezhaeva() {
        getDriver().get(URL_DEMOQA_FRAMES);

        getDriver().switchTo().frame(getDriver().findElement(By.id("frame1")));
        WebElement headerFrame = getDriver().findElement(By.xpath("//body/h1[@id='sampleHeading']"));

        Assert.assertEquals(headerFrame.getRect().getWidth(), 480.0);
        Assert.assertEquals(headerFrame.getRect().getHeight(), 37.0);
    }

    @Test
    public void testFindTitle_DS() throws InterruptedException {
        getDriver().get("https://vladislaveremeev.gitbook.io/qa_bible/%22");
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//a[@href = '/qa_bible/obshee']/div[1]")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//a[@href = '/qa_bible/obshee/principy-testirovaniya']/div[contains(@class, 'css-901oao')]")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class = 'r-1nf4jbm r-crgep1 r-1xnzce8']")).getText(), "Принципы тестирования");
    }

    @Test
    public void test_CheckButtonTutorials_LPlucci() {
        getDriver().get("https://www.toolsqa.com/");
        WebElement openButton = getDriver().findElement(By.xpath("//span[@class='navbar__tutorial-menu--text']"));

        Assert.assertEquals(openButton.getText(), "TUTORIALS");
    }

    @Ignore
    @Test
    public void test_ModalDialogs_OlPolezhaeva() {
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
    public void test_ToolTips_OlPolezhaeva() {
        getDriver().get("https://demoqa.com/tool-tips");

        getActions().moveToElement(getDriver().findElement(By.xpath("//div[@id='texToolTopContainer']/a[contains(text(),'Contrary')]"))).pause(5000).perform();
        getWait20().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tooltip-inner']")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='tooltip-inner']")).getText(), "You hovered over the Contrary");
    }

    @Ignore
    @Test
    public void test_TextBoxFields_AFedorova() {

        final String name = "Anna Fedorova";
        final String email = "test@gmail.com";
        final String cAddress = "40 S Rengstorff Ave, Mountain View, CA 94040";
        final String pAddress = "851 Manor Way, Los Altos, CA 94024";
        final List<String> expectedResult = new ArrayList<>();
        expectedResult.add(String.format("Name:%s", name));
        expectedResult.add(String.format("Email:%s", email));
        expectedResult.add(String.format("Current Address :%s", cAddress));
        expectedResult.add(String.format("Permananet Address :%s", pAddress));

        getDriver().get(URL_DEMOQA);

        getDriver().findElement(By.cssSelector("div.category-cards>div:first-of-type")).click();
        getDriver().findElement(By.xpath("//*[@id='item-0']/span")).click();

        getDriver().findElement(By.id("userName")).sendKeys(name);
        getDriver().findElement(By.id("userEmail")).sendKeys(email);
        getDriver().findElement(By.id("currentAddress")).sendKeys(cAddress);
        getDriver().findElement(By.id("permanentAddress")).sendKeys(pAddress);

        WebElement submitBtn = getDriver().findElement(By.id("submit"));
        getActions().scrollToElement(submitBtn).build().perform();
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
    public void test_CheckBoxes_AFedorova() {
        final List<String> expectedResult = new ArrayList<>(List.of("You have selected :", "desktop", "notes", "commands"));

        getDriver().get(URL_DEMOQA);

        getDriver().findElement(By.cssSelector("div.category-cards>div:first-of-type")).click();
        getDriver().findElement(By.xpath("//*[@id='item-1']/span")).click();
        getDriver().findElement(By.cssSelector(".rct-option.rct-option-expand-all")).click();

        List<WebElement> listOfCheckBoxes = getDriver().findElements(By.cssSelector("span.rct-checkbox"));
        listOfCheckBoxes.get(3).click();
        listOfCheckBoxes.get(2).click();
        List<WebElement> listOfSelectedCheckBoxesDesktop = getDriver().findElements(By.cssSelector(".display-result.mt-4>span"));
        List<String> actualResult = new ArrayList<>();

        for (WebElement element : listOfSelectedCheckBoxesDesktop) {
            actualResult.add(element.getText());
        }

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test_Slider_KI() {
        getDriver().get(URL_DEMOQA);

        getDriver().findElement(By.xpath("//div[@class='category-cards']/div[4]")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", getDriver().findElement(By.xpath("//span[text()='Slider']")));
        getActions().moveToElement(getDriver().findElement(By.xpath("//span[text()='Slider']"))).click().perform();

        WebElement slider = getDriver().findElement(By.xpath("//input[@type='range']"));
        getActions().moveToElement(slider).pause(1500).dragAndDropBy(slider, 350, 0).pause(1500).perform();

        Assert.assertEquals(getDriver().findElement(By.id("sliderValue")).getAttribute("value"), "100");
    }

    @Test
    public void test_FillingWebTables_KI() {
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

        Assert.assertEquals(getDriver().findElements(By.xpath("//div[@class='action-buttons']")).size(), 4);
    }

    @Test
    public void test_CheckValueCart_AKaz() {
        final String login = "standard_user";
        final String pass = "secret_sauce";

        getDriver().get("http://saucedemo.com/");

        getDriver().findElement(By.id("user-name")).sendKeys(login);
        getDriver().findElement(By.id("password")).sendKeys(pass);
        getDriver().findElement(By.id("login-button")).click();

        List<WebElement> productList = getDriver().findElements(By.cssSelector(".inventory_item"));
        for (int i = 1; i <= productList.size(); i++) {
            String path = "//div[@class='inventory_item'][" + i + "]//button";
            getDriver().findElement(By.xpath(path)).click();
        }

        Assert.assertEquals(getDriver().findElement(By.cssSelector("span.shopping_cart_badge")).getText(), "6");
    }

    @Test
    public void test_CountOrangeAPIButtons_gdiksanov() {
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        getDriver().get(URL_OPENWEATHER);

        getWait20().until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[@href='/api']")).click();

        Assert.assertEquals(getDriver().findElements(By.xpath("//a[contains(@class,'orange')]")).size(), 30);
    }

    @Test
    public void test_SearchRome_gdiksanov() {
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        getDriver().get(URL_OPENWEATHER);

        getWait20().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//input[@name='q']")).sendKeys("Rome\n");

        Assert.assertTrue(getDriver().getCurrentUrl().contains("find") && getDriver().getCurrentUrl().contains("Rome"));
        Assert.assertTrue(getDriver().findElement(By.id("search_str")).getAttribute("value").equals("Rome"));
    }


    @Test
    public void test_CaptchaError_gdiksanov() {
        final String email = "qwerty@qwerty.org";
        final String message = "Hello world";

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        getDriver().get(URL_OPENWEATHER);

        getWait20().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='support-dropdown']")).click();
        getDriver().findElement(By.xpath("//ul[@id='support-dropdown-menu']//a[contains (text(), 'Ask a question')]")).click();

        getWait20().until(numberOfWindowsToBe(2));

        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }

        getWait20().until(urlToBe("https://home.openweathermap.org/questions"));

        getDriver().findElement(By.id("question_form_email")).sendKeys(email);
        getSelect(getDriver().findElement(By.id("question_form_subject"))).selectByValue("Sales");
        getDriver().findElement(By.xpath("//textarea[@id='question_form_message']")).sendKeys(message);
        getDriver().findElement(By.xpath("//input[@type='submit'][@name='commit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='help-block']")).getText(),
                "reCAPTCHA verification failed, please try again.");
    }

    @Test
    public void test_CheckboxesPage_MRakhmanava() {
        getDriver().get("http://the-internet.herokuapp.com/checkboxes");

        List<WebElement> checkboxes = getDriver().findElements(By.cssSelector("[type=checkbox]"));
        checkboxes.get(1).click();

        Assert.assertFalse(checkboxes.get(1).isSelected());
    }

    @Test
    public void test_RadioButtonText_MAnna503() {
        getDriver().get(URL_HEROKU_FORMY);

        Assert.assertEquals(getDriver().findElement(By.xpath("//li/a[@href='/radiobutton']")).getText(),
                "Radio Button");
    }

    @Test
    public void test_OpenPageCooker_MAnna503() {
        getDriver().get("https://www.russianfood.com/");

        WebElement searchFormField = getDriver().findElement(By.id("sskw_title"));
        searchFormField.click();
        searchFormField.sendKeys("Быстрый пирог-шарлотка с яблоками");

        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//a[@name='el127921']")).click();
        getDriver().findElement(By.xpath("//h1[@class ='title ']")).getText();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class ='title ']")).getText(),
                "Быстрый пирог-шарлотка с яблоками");
    }
}
