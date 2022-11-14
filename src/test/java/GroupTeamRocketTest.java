import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class GroupTeamRocketTest extends BaseTest {

    final List<String> EXPECTED_ITEMS = List.of(
            "What's New",
            "Women",
            "Men",
            "Gear",
            "Training",
            "Sale");
    private static final String URL = "https://www.saucedemo.com";
    private static final String USER_NAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String URL_99 = "http://www.99-bottles-of-beer.net/";
    private static final String URL_PICKNPULL = "https://www.picknpull.com/check-inventory/vehicle-search?make=182&model=3611&distance=25&zip=95123&year=";
    private static final String URL_ELCATS = "http://www.elcats.ru/mercedes/";
    private static final String URL_DEMOBLAZE = "https://www.demoblaze.com/";

    private static final String URL_JENKINS = "https://www.jenkins.io/";

    private static final String URL_UI_TESTING_PLAYGROUND = "http://uitestingplayground.com";

    private static final String URL_MINTHOUSE = "https://minthouse.com/";

    private WebDriverWait webDriverWait20;

    private WebDriverWait wait20() {
        if (webDriverWait20 == null) {
            webDriverWait20 = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }

        return webDriverWait20;
    }

    private Actions doAction(){
        return new Actions(getDriver());
    }


    @Test
    public void testAddElementHerokuapp() {
        getDriver().get("https://the-internet.herokuapp.com/");

        getDriver().findElement(By.xpath("//a[@href='/add_remove_elements/']")).click();
        getDriver().findElement(By.xpath("//button[@onclick='addElement()']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//button[@class='added-manually']")).isDisplayed());
    }

    @Test
    public void testSwagLabs_LogIn() {
        getDriver().get(URL);

        getDriver().findElement(By.id("user-name")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("password")).sendKeys(PASSWORD);
        getDriver().findElement(By.id("login-button")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void testFindTitleGuide_NataliiaOliver() {
        getDriver().get("https://openweathermap.org/");

        wait20().until(ExpectedConditions.invisibilityOfElementLocated(By.className("owm-loader-container")));

        WebElement guide = getDriver().findElement(By.xpath("//div[@id='desktop-menu']/ul/li/a[@href='/guide']"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(guide));
        doAction().click(guide).perform();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='col-sm-7']/h1[text()='Guide']")).getText(),
                "Guide");
    }

    @Test
    public void testCart() {
        getDriver().get(URL);

        getDriver().findElement(By.id("user-name")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("password")).sendKeys(PASSWORD);
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        getDriver().findElement(By.id("shopping_cart_container")).click();

        Assert.assertTrue(getDriver().findElement(By.id("item_4_title_link")).isDisplayed());
    }

    @Test
    public void testAboutLinkRedirect() {
        getDriver().get(URL);

        getDriver().findElement(By.id("user-name")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("password")).sendKeys(PASSWORD);
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("react-burger-menu-btn")).click();
        getDriver().findElement(By.id("about_sidebar_link")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://saucelabs.com/");
    }

    @Ignore
    @Test
    public void testAtt_RS() {
        getDriver().get("https://www.att.com/");

        getDriver().findElement(By.xpath(" //input[@id='z1-searchfield']")).sendKeys("Bundles");
        getDriver().findElement(By.xpath("//div/form/button")).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'Bundle & save')]")).click();
        WebElement checkAvailBtn = getDriver().findElement(
                By.xpath("(//a[@class='btn-primary-2 btn-full-width '])[1]"));

        Assert.assertTrue(checkAvailBtn.isDisplayed());
        Assert.assertEquals(checkAvailBtn.getText(), "Check availability");
    }

    @Ignore
    @Test
    public void testAboutUs() {
        getDriver().get("http://automationpractice.com/index.php");
        getDriver().findElement(
                        By.xpath("//a[@href='http://automationpractice.com/index.php?id_cms=4&controller=cms']"))
                .click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "http://automationpractice.com/index.php?id_cms=4&controller=cms");
    }

    @Test
    public void testLoginForm_EZ() {
        getDriver().get("https://www.grubhub.com/");
        getDriver().findElement(By.cssSelector("[data-testid='prettyhomepagesignin']")).click();
        getDriver().findElement(By.cssSelector(".ghs-goToCreateAccount")).click();
        getDriver().findElement(By.id("firstName")).sendKeys("Vasya");
        getDriver().findElement(By.id("lastName")).sendKeys("Piterskiy");
        getDriver().findElement(By.id("email")).sendKeys("vasiliy@gmail.com");
        getDriver().findElement(By.id("password")).sendKeys("Ababgalamaga1!");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//img[@class='captchaMediaImage']")).isDisplayed());
    }

    @Ignore
    @Test
    public void testInformationDelivery() {
        getDriver().get("http://automationpractice.com/index.php");
        getDriver().findElement(By.cssSelector(".sf-with-ul[title=\"Women\"]")).click();
        getDriver().findElement(By.cssSelector("[title=\"Delivery\"]")).click();

        Assert.assertEquals(getDriver().getTitle(), "Delivery - My Store");
    }

    @Ignore
    @Test
    public void testGoToTermsAndConditionsPage_AnastasiaYakimova() {
        getDriver().get("http://automationpractice.com");
        getDriver().findElement(By.xpath("//section[@id='block_various_links_footer']/ul/li[6]/a")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(),
                "http://automationpractice.com/index.php?id_cms=3&controller=cms");
    }

    @Test
    public void testCheckOut() {
        String name = "John";
        String lastName = "Smith";
        getDriver().get(URL);

        getDriver().findElement(By.id("user-name")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("password")).sendKeys(PASSWORD);
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        getDriver().findElement(By.id("shopping_cart_container")).click();
        getDriver().findElement(By.id("checkout")).click();
        getDriver().findElement(By.id("first-name")).sendKeys(name);
        getDriver().findElement(By.id("last-name")).sendKeys(lastName);
        getDriver().findElement(By.id("postal-code")).sendKeys("28277");
        getDriver().findElement(By.id("continue")).click();
        getDriver().findElement(By.id("finish")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")).getText(), "THANK YOU FOR YOUR ORDER");
    }

    @Ignore
    @Test
    public void testAddToCartButton() {
        getDriver().get("https://www.demoblaze.com");

        getDriver().findElement(By.xpath("//div[@class='list-group']/a[4]")).click();
        getDriver().findElement(By.xpath("//div[@class='card-block']/h4[1]/a[@href='prod.html?idp_=10']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@class='btn btn-success btn-lg']")).isDisplayed());
    }

    @Ignore
    @Test
    public void testContactUs() {
        getDriver().get("http://automationpractice.com/index.php");

        getDriver().findElement(By.xpath("//a[@title='Contact Us']")).click();
        Select dropdown = new Select(getDriver().findElement(By.id("id_contact")));
        dropdown.selectByVisibleText("Customer service");
        getDriver().findElement(By.id("email")).sendKeys("test@mailinator.com");
        getDriver().findElement(By.id("id_order")).sendKeys("super order");
        getDriver().findElement(By.id("message")).sendKeys("super message test");
        getDriver().findElement(By.id("submitMessage")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p[@class='alert alert-success']"))
                .getText(), "Your message has been successfully sent to our team.");
    }


    @Ignore
    @Test
    public void testFindBook_VZ() {
        getDriver().get("https://www.abebooks.com/");

        getDriver().findElement(By.id("rare-books")).click();
        getDriver().findElement(By.xpath("//input[@placeholder='Enter author']")).sendKeys("Tolstoy");
        getDriver().findElement(By.name("prl")).sendKeys("400");
        getDriver().findElement(By.xpath("//button[@class='button-primary']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[@data-cy='listing-title']"))
                .getText(), "The Tragedy of Tolstoy");
    }

    @Ignore
    @Test
    public void testSaucedemo_EZ() {
        getDriver().get(URL);

        getDriver().findElement(By.id("user-name")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("password")).sendKeys(PASSWORD);
        getDriver().findElement(By.id("login-button")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector(
                "#login_button_container > div > form > div.error-message-container.error")).isDisplayed());
    }

    @Test
    public void testBrowseLanguages_NO() {
        getDriver().get(URL_99);

        getDriver().findElement(By.xpath("//div[@id='navigation']/ul[@id='menu']/li/a[@href='/abc.html']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//table[@id='category']/tbody/tr/th[text()='Language']")).getText(),
                "Language");
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//table[@id='category']/tbody/tr/th[text()='Author']")).getText(),
                "Author");
    }

    @Test
    public void testFilterIconSortingPriceFromLowToHigh_AnastasiaYakimova() {
        getDriver().get(URL);

        getDriver().findElement(By.id("user-name")).sendKeys(USER_NAME);
        getDriver().findElement(By.id("password")).sendKeys(PASSWORD);
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.xpath("//div[@id='header_container']/div[2]/div[2]/span/select")).click();
        getDriver().findElement(By.xpath("//div[@id='header_container']/div[2]/div[2]/span/select/option[3]")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

    @Ignore
    @Test
    public void testSaleSticker_ET() {
        getDriver().get("http://automationpractice.com/index.php");

        WebElement searchInput = getDriver().findElement(By.cssSelector(".search_query.form-control.ac_input"));
        searchInput.sendKeys("Printed Chiffon Dress");
        getDriver().findElement(By.cssSelector(".btn.btn-default.button-search")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@itemprop='name']/*[@title='Printed Chiffon Dress']"))
                .getText(), "Printed Chiffon Dress");
        Assert.assertEquals(getDriver().findElement(By.xpath("(//span[@class='price-percent-reduction'])[3]"))
                .getText(), "-20%");
    }

    @Test
    public void testCheckForBrokenIMGs() throws IOException {
        getDriver().get("https://www.rifle.com");

        List<WebElement> listOfElementsWithIMGtag = getDriver().findElements(By.tagName("img"));
        if (listOfElementsWithIMGtag.size() != 0) {
            for (WebElement tempElement : listOfElementsWithIMGtag) {
                String imgSource = tempElement.getAttribute("src");

                HttpURLConnection connection = (HttpURLConnection) new URL(imgSource).openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();

                Assert.assertTrue(responseCode >= 200 && responseCode < 400);
            }
        }
    }

    @Test
    public void testPriceNokia_ZoiaBut() {
        getDriver().get(URL_DEMOBLAZE);

        getDriver().findElement(By.xpath("//div/a[@href='prod.html?idp_=2']")).click();

        Assert.assertTrue((getDriver().findElement(By.xpath("//h3[text()='$820']"))
                .getText().contains("$820")));
    }

    @Test
    public void testSignGuestbookTest_NO() {
        final String random = String.valueOf((int) (Math.random() * 900) + 100);

        getDriver().get("http://www.99-bottles-of-beer.net/");
        getDriver().findElement(By.xpath("//a[@href='/guestbookv2.html']")).click();
        getDriver().findElement(By.xpath("//a[@href='./signv2.html']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("Nataliia");
        getDriver().findElement(By.xpath("//input[@name='location']")).sendKeys("Louisiana");
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("psvnatali@gmail.com");
        getDriver().findElement(By.xpath("//input[@name='homepage']")).sendKeys("lagoldgymnastics.com");
        getDriver().findElement(By.xpath("//input[@name='captcha']")).sendKeys(random);
        getDriver().findElement(By.xpath("//textarea[@name='comment']")).sendKeys("test message");
        getDriver().findElement(By.xpath("//input[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//div[@id='main']/p[contains(text(),' Error: Invalid security code.')]")).getText(),
                "Error: Error: Invalid security code.");
    }

    @Test
    public void testTenLanguageStartNumbers_NO() {
        getDriver().get(URL_99);

        getDriver().findElement(By.xpath("//ul[@id='menu']/li/a[@href='/abc.html']")).click();
        getDriver().findElement(By.xpath("//a[@href='0.html']")).click();

        Assert.assertEquals(getDriver().findElements(By.xpath("//tbody/tr/td/a")).size(), 10);
    }

    @Test
    public void testBrowseLanguagesAlternativeVersions_NO() {
        getDriver().get(URL_99);

        getDriver().findElement(By.xpath("//ul[@id='menu']/li/a[@href='/abc.html']")).click();
        getDriver().findElement(By.xpath("//a[@href='a.html']")).click();
        getDriver().findElement(By.xpath("//a[@href='language-autoit-657.html']")).click();
        getDriver().findElement(By.xpath("//a[@href='language-autoit-663.html']")).click();
        getDriver().findElement(By.xpath("//a[@title='reddit']/img")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[@class='Title m-no-margin']")).isDisplayed());
    }

    @Test
    public void testGoToStepTwoForGetQuote_VadimTref() {
        getDriver().get("https://commercialinsurance.net/");

        getDriver().findElement(By.xpath("//div[@id='cobranding-steps']//input[@name='zipcode']"))
                .sendKeys("11230");
        getDriver().findElement(By.xpath("//div[@id='cobranding-steps']//a[@class='btn next-step']"))
                .click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//button[@type='submit'][@class='btn cobranding-form-submit']"))
                .getText(), "Go To Final Step");
    }

    @Test
    public void testSamsungGalaxyS7Price_ZB() {
        getDriver().get(URL_DEMOBLAZE);

        getDriver().findElement(By.xpath("//div[@id='tbodyid']//div[4]//h4/a[@href='prod.html?idp_=4']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h3[@class='price-container']")).getText(),
                "$800 *includes tax");
    }

    @Test
    public void testSwitchToPageCompareInsuranceQuote_VadimTref() {
        getDriver().get("https://www.statewidedealerinsurance.com/");

        final String expectedNoteText = "An insurance quote does not impact your "
                + "credit score. Quote will take approximately 3-5 minutes to complete.";

        getDriver().findElement(By.id("ZipCode")).sendKeys("11230");
        getDriver().findElement(By.id("Type")).click();
        getDriver().findElement(By.xpath("//select[@id='Type']/option[@value='Home']")).click();
        getDriver().findElement(By.xpath("//form[@id='miniQuote']//button[@type='submit']")).click();
        getDriver().switchTo().frame(getDriver().findElement(By.id("cpIframe")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='body']//p[@class='cpInstructions cpPage0']")).getText(),
                expectedNoteText);
    }

    @Test
    public void testCheckTheMostRelevantBook_AnastasiaKuz() {
        getDriver().get("https://www.powells.com/");

        getDriver().findElement(By.id("keyword")).sendKeys("Software Testing\n");

        Assert.assertEquals(getDriver().getTitle(), "Search Results - Powell's Books");
        Assert.assertTrue(getDriver().findElement(By.xpath("//img[contains(@alt,'Software Testing and Quality Assurance: Theory and Practice')]")).isDisplayed());
    }

    @Test
    public void testRemoveElementHerokuapp() {
        getDriver().get("https://the-internet.herokuapp.com/");

        getDriver().findElement(By.xpath("//a[@href='/add_remove_elements/']")).click();
        getDriver().findElement(By.xpath("//button[@onclick='addElement()']")).click();
        getDriver().findElement(By.xpath("//button[@class='added-manually']")).click();

        Assert.assertTrue(getDriver().findElements(By.xpath("//button[@class='added-manually']")).isEmpty());
    }

    @Test
    public void testAddElementsHerokuapp() {
        getDriver().get("https://the-internet.herokuapp.com/");

        getDriver().findElement(By.xpath("//a[@href='/add_remove_elements/']")).click();
        getDriver().findElement(By.xpath("//button[@onclick='addElement()']")).click();
        getDriver().findElement(By.xpath("//button[@onclick='addElement()']")).click();
        getDriver().findElement(By.xpath("//button[@onclick='addElement()']")).click();

        Assert.assertEquals(getDriver().findElements(By.xpath("//button[@class='added-manually']"))
                .size(), 3);
    }

    @Ignore
    @Test
    public void testContactUs_ThankYouMessagePopsUp_AnastasiaY() {

        getDriver().get("https://www.demoblaze.com/");

        getDriver().findElement(
                By.xpath("//div[@id = 'navbarExample']//a[@class = 'nav-link'][contains(text(),'Contact')]")).click();
        getDriver().findElement(By.id("recipient-email")).sendKeys("anatestova123@gmail.com");
        getDriver().findElement(By.id("recipient-name")).sendKeys("Ana");
        getDriver().findElement(By.id("message-text")).sendKeys("Hi");
        getDriver().findElement(By.xpath("//button[@type = 'button'][@onclick = 'send()']")).click();

        Assert.assertEquals(getDriver().switchTo().alert().getText(), "Thanks for the message!!");
    }

    @Test
    public void testContextMenu_ET() {
        getDriver().get("https://the-internet.herokuapp.com/");

        getDriver().findElement(By.xpath("//a[text()='Context Menu']")).click();
        Actions actions = new Actions(getDriver());
        WebElement rectangle = getDriver().findElement(By.id("hot-spot"));
        actions.contextClick(rectangle).perform();

        Assert.assertEquals(getDriver().switchTo().alert().getText(), "You selected a context menu");
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
    public void testLumaTabPanel() {

        getDriver().get("https://magento.softwaretestingboard.com");

        List<WebElement> elementList = getDriver().findElements(By.xpath("//ul[@class='ui-menu ui-widget ui-widget-content ui-corner-all']/li"));
        List<String> strlist = WebelementToString(elementList);

        Assert.assertEquals(strlist, EXPECTED_ITEMS);
    }

    public static List<String> WebelementToString(List<WebElement> elementList) {
        List<String> stringList = new ArrayList<>();
        for (WebElement element : elementList) {
            stringList.add(element.getText());
        }
        return stringList;
    }

    @Test
    public void testCategoriesPanel_ZB() {
        getDriver().get(URL_DEMOBLAZE);

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='list-group']/a[text()='CATEGORIES']")).isDisplayed());
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='list-group']/a[text()='Phones']")).isDisplayed());
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='list-group']/a[text()='Laptops']")).isDisplayed());
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='list-group']/a[text()='Monitors']")).isDisplayed());
    }

    @Test
    public void testSergeDotMenuStartTitle() {
        getDriver().get(URL_99);

        getDriver().findElement(By.cssSelector("#menu a[href='/abc.html']")).click();
        getDriver().findElement(By.cssSelector("#menu a[href='/'")).click();
        getDriver().findElement(By.cssSelector("#menu a[href='/']")).getText();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main h2"))
                .getText(), "Welcome to 99 Bottles of Beer");
    }

    @Test
    public void testBROWSE_LANGUAGES_M_NO() {
        final String expectedResult = "MySQL";

        getDriver().get(URL_99);

        getDriver().findElement(By.xpath("//ul[@id='menu']/li/a[@href='/abc.html']")).click();
        getDriver().findElement(By.xpath("//a[@href='m.html']")).click();

        String actualResult = getDriver().findElement(By.xpath("//tr[last()]/td/a")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testSergeDotMintHouseDateSelectionYesterdayIsNotClickable() {
        final long dayInMillis = 86400000;
        getDriver().get(URL_MINTHOUSE);

        WebElement propertyList = getDriver().findElement(By.cssSelector(".hero .value .text"));
        wait20().until(ExpectedConditions.elementToBeClickable(propertyList));
        doAction().click(propertyList).perform();
        getDriver().findElement(By.cssSelector(".dropdown.active a")).click();

        WebElement dates = getDriver().findElement(By.cssSelector("#mainContent .booker-calendar .t-check-in"));
        wait20().until(ExpectedConditions.elementToBeClickable(dates));
        doAction().click(dates).perform();

        long todayInMillis = Long.parseLong(getDriver()
                .findElement(By.cssSelector(".hero .month-item .container__days .is-today"))
                .getAttribute("data-time"));

        WebElement yesterdayDate = getDriver()
                .findElement(By.cssSelector(".hero .month-item .container__days div[data-time='" +
                        Math.subtractExact(todayInMillis, dayInMillis) + "']"));
        wait20().until(ExpectedConditions.elementToBeClickable(yesterdayDate));
        new Actions(getDriver()).click(dates).perform();

        boolean actualResult = yesterdayDate.getAttribute("class").contains("is-start-date") ||
                yesterdayDate.getAttribute("class").contains("is-end-date") ||
                yesterdayDate.getAttribute("class").contains("is-in-range");

        Assert.assertFalse(actualResult);
    }

    @Test
    public void testCountAndNamesOfRadioButtons_VadimTref() {
        getDriver().get("https://demoqa.com/");

        getDriver().findElement(
                By.cssSelector("div:nth-of-type(1) > div > .avatar.mx-auto.white > svg")).click();
        getDriver().findElement(By.id("item-2")).click();
        List<WebElement> radioButtons = getDriver().findElements(
                By.xpath("//div[contains(@class,'custom-control')]"));

        Assert.assertEquals(radioButtons.size(), 3);
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[contains(@class,'custom-control')][1]/label")).getText(), "Yes");
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[contains(@class,'custom-control')][2]/label")).getText(), "Impressive");
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[contains(@class,'custom-control')][3]/label")).getText(), "No");
    }

    @Test
    public void testHeaderIsDisplayedInEach() {
        boolean actualResult = false;
        final String expectedHeaderText = "Smart By Design";
        final String carouselContainerPath = ".swiper-slide .image .d-none";

        getDriver().get(URL_MINTHOUSE);
        List<WebElement> carousel = getDriver().findElements(By.cssSelector(carouselContainerPath));
        for (WebElement element : carousel) {
            wait20().until(ExpectedConditions.visibilityOf(element));
            if (!element.findElement(By.xpath("parent::div/following-sibling::div//h2"))
                    .getText().equals(expectedHeaderText)) {
                System.out.println("Error report. Missing text in " + element.getAttribute("src"));
                actualResult = true;
            }
        }

        Assert.assertFalse(actualResult);
    }

    @Test
    public void testSearchDuckDuckGo_AnastasiaY() {
        getDriver().get("https://duckduckgo.com/");

        WebElement searchInput = getDriver().findElement(By.id("search_form_input_homepage"));
        searchInput.sendKeys("siberian tiger");
        getDriver().findElement(By.id("search_button_homepage")).click();
        List<WebElement> resultLinks = getDriver().findElements(
                By.xpath("//div[@class = 'nrn-react-div']/article//div/h2"));
        for (WebElement link : resultLinks) {

            Assert.assertTrue(link.getText().matches("(?i).*tiger.*"));
        }
    }

    @Test
    public void testFindMercedesVinOnJunkYardAndDecodeIt() {
        getDriver().get(URL_PICKNPULL);

        WebElement firstSearchResult = new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*/div[@class='fixed-table-body']/table//img[contains(@alt, 'Mercedes')][1]")));
        firstSearchResult.click();

        String vinNumber = getDriver().getCurrentUrl().substring(getDriver().getCurrentUrl().length() - 17);
        getDriver().get(URL_ELCATS);

        getDriver().findElement(By.id("cphMasterPage_txbVIN")).sendKeys(vinNumber);
        getDriver().findElement(By.id("cphMasterPage_btnFindByVIN")).click();

        List<WebElement> vehicleInfoRow = getDriver().findElements(By.xpath("//table[@id='cphMasterPage_tblComplectation']//tr[2]/td"));

        boolean isMercedesCLKfound = false;
        for (WebElement webElement : vehicleInfoRow) {
            if (webElement.getText().contains("CLK")) {
                isMercedesCLKfound = true;
                break;
            }
        }

        Assert.assertTrue(isMercedesCLKfound);
    }

    @Test
    public void testStaticDropDown_VZ() throws InterruptedException {
        getDriver().get("https://rahulshettyacademy.com/dropdownsPractise/");

        WebElement staticDropdown = getDriver().findElement(By.id("ctl00_mainContent_DropDownListCurrency"));
        Select dropdown = new Select(staticDropdown);
        dropdown.selectByIndex(3);

        getDriver().findElement(By.id("divpaxinfo")).click();
        Thread.sleep(2000);
        for (int i = 1; i < 5; i++) {
            getDriver().findElement(By.id("hrefIncAdt")).click();
        }
        getDriver().findElement(By.id("btnclosepaxoption")).click();

        Assert.assertEquals(getDriver().findElement(By.id("divpaxinfo")).getText(), "5 Adult");
    }

    @Test
    public void test_WixWebSiteAnnaPav() {
        getDriver().get("https://www.wix.com/");
        WebElement actualResult = getDriver().findElement(By.xpath("//span[contains(text(), 'Create a website without limits')]"));

        Assert.assertEquals(actualResult.getText(), "Create a website without limits");
    }

    @Ignore
    @Test
    public void test_WixGetStartedAnnaPav() {
        getDriver().get("https://www.wix.com/");
        getDriver().findElement(By.xpath("//*[@id='main-menu-item-creation']"));

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-menu-item-creation']")).getText(), "Creation");
    }

    @Ignore
    @Test
    public void test_WixCreationAnnaPav() throws InterruptedException {
        getDriver().get("https://www.wix.com/");
        getDriver().findElement(By.xpath("//a[@class='bOL8nw yOYgO_']")).click();
        Thread.sleep(5000);
        WebElement actualResult = getDriver().findElement(By.xpath("//h1[@class='log-in-title title ng-binding']"));

        Assert.assertEquals(actualResult.getText(), "Log In");
    }

    @Ignore
    @Test
    public void test_WixLoginAnnaPav() throws InterruptedException {
        getDriver().get("https://www.wix.com/");
        getDriver().findElement(By.xpath("//a[@class='bOL8nw yOYgO_']")).click();
        Thread.sleep(5000);
        WebElement actualResult = getDriver().findElement(By.xpath("//button[@name='submit']"));

        Assert.assertEquals(actualResult.getText(), "Continue with Email");
    }

    @Test
    public void testJenkinsUnstartedDemoWatchMode_EZ() {
        getDriver().get(URL_JENKINS);

        getDriver().findElement(By.xpath("//div[@id='CollapsingNavbar']/ul[1]/li/button")).click();
        getDriver().findElement(By.xpath("//a[@href='https://jenkins-x.io/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/v3/develop/developing/#demo']")).click();
        getDriver().switchTo().frame(getDriver()
                .findElement(By.xpath("//iframe[@title='Demo of developing with Jenkins X']")));

        String playerState = getDriver().findElement(By.id("movie_player")).getAttribute("class");

        Assert.assertTrue(playerState.contains("unstarted-mode"));
    }

    @Test
    public void testJenkinsPlayingDemoWatchMode_EZ() {
        getDriver().get(URL_JENKINS);

        getDriver().findElement(By.xpath("//div[@id='CollapsingNavbar']/ul[1]/li/button")).click();
        getDriver().findElement(By.xpath("//a[@href='https://jenkins-x.io/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/v3/develop/developing/#demo']")).click();
        getDriver().switchTo().frame(getDriver()
                .findElement(By.xpath("//iframe[@title='Demo of developing with Jenkins X']")));

        getDriver().findElement(By.id("player")).click();

        String playerState = getDriver().findElement(By.id("movie_player")).getAttribute("class");

        Assert.assertTrue(playerState.contains("playing-mode"));
    }

    @Test
    public void testJenkinsPausedDemoWatchMode_EZ() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().get(URL_JENKINS);

        getDriver().findElement(By.xpath("//div[@id='CollapsingNavbar']/ul[1]/li/button")).click();
        getDriver().findElement(By.xpath("//a[@href='https://jenkins-x.io/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/v3/develop/developing/#demo']")).click();
        getDriver().switchTo().frame(getDriver()
                .findElement(By.xpath("//iframe[@title='Demo of developing with Jenkins X']")));

        getDriver().findElement(By.id("player")).click();
        wait.until(ExpectedConditions.attributeContains(getDriver()
                .findElement(By.id("movie_player")),"class", "playing-mode"));

        getDriver().findElement(By.id("player")).click();
        wait.until(ExpectedConditions.attributeContains(getDriver()
                .findElement(By.id("movie_player")),"class", "paused-mode"));

        String playerState = getDriver().findElement(By.id("movie_player")).getAttribute("class");

        Assert.assertTrue(playerState.contains("paused-mode"));
    }
    @Test
    public void test_UiTestingPlaygroundScrollbars_ArtCh() {
        getDriver().get(URL_UI_TESTING_PLAYGROUND);

        getDriver().findElement(By.xpath ("//a[@href = '/scrollbars']")).click ();
        WebElement element = getDriver().findElement(By.xpath("//button[@id='hidingButton']"));
        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
        jse.executeScript("arguments[0].scrollIntoView();", element);

        Assert.assertEquals (element.getText (), "Hiding Button");
    }

    @Test
    public void testMintHouseSlideOutImageCountsNegative_SergeDot() {
        getDriver().get(URL_MINTHOUSE);

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.cssSelector("#destination-plus"))).click().pause(500).perform();
        Map<String,Integer> slideOutImages = new HashMap<>();
        List<WebElement> destList = getDriver().findElements(By.cssSelector("#destination-list .menu__item"));
        Iterator<WebElement> it = destList.iterator();
        while (it.hasNext()) {
            WebElement temp = it.next();
            new Actions(getDriver()).moveToElement(temp).pause(1000).perform();
            Integer numberOfImages = getDriver().findElements(By.cssSelector(".destination-item.active img")).size();
            slideOutImages.put(temp.findElement(By.cssSelector("a")).getText(),numberOfImages);
        }

        Assert.assertEquals(slideOutImages.size(),destList.size());

        int missingImages = 0;
        for (String i : slideOutImages.keySet()) {
            if(slideOutImages.get(i) < 2) {
                missingImages++;
            }
        }
        Assert.assertNotEquals(missingImages, 0);
    }
}

