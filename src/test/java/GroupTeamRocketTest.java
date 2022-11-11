import org.openqa.selenium.By;
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
import java.util.ArrayList;
import java.util.List;

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
    public void testFindTitleGuide_NataliiaOliver() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");

        Thread.sleep(8000);

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']/ul/li/a[@href='/guide']")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/guide");
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
        getDriver().get("https://www.demoblaze.com/");

        getDriver().findElement(By.xpath("//div/a[@href='prod.html?idp_=2']")).click();

        Assert.assertFalse((getDriver().findElement(By.xpath("//h3[text()='$820']"))
                .getText().contains("$620")));
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

    @Ignore
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

    @Ignore
    @Test
    public void testSamsungGalaxyS7Price_ZB() {
        getDriver().get("https://www.demoblaze.com/");

        getDriver().findElement(By.xpath("//h4/a[@href='prod.html?idp_=4']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h3[@class='price-container']")).getText(),
                "$800 *includes tax");
    }

    @Ignore
    @Test
    public void testSwitchToPageCompareInsuranceQuote_VadimTref() {
        getDriver().get("https://www.statewidedealerinsurance.com/");

        final String EXPECTED_NOTE_TEXT = "An insurance quote does not impact your "
                + "credit score. Quote will take approximately 3-5 minutes to complete.";

        getDriver().findElement(By.id("ZipCode")).sendKeys("11230");
        getDriver().findElement(By.id("Type")).click();
        getDriver().findElement(By.xpath("//select[@id='Type']/option[@value='Home']")).click();
        getDriver().findElement(By.xpath("//form[@id='miniQuote']//button[@type='submit']")).click();
        getDriver().switchTo().frame(getDriver().findElement(By.id("cpIframe")));

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='body']//p[@class='cpInstructions cpPage0']")).getText(),
                EXPECTED_NOTE_TEXT);
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
        getDriver().get("https://www.demoblaze.com/");

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@class='list-group']/a[text()='CATEGORIES']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@class='list-group']/a[text()='Phones']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@class='list-group']/a[text()='Laptops']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@class='list-group']/a[text()='Monitors']")).isDisplayed());
    }

    @Test
    public void testSergeDotMenuStartTitle() {
        getDriver().get(URL_99);

        getDriver().findElement(By.xpath("//body/div[@id='wrap']/div[@id='navigation']/ul[@id='menu']/li/a[@href='/abc.html']")).click();
        getDriver().findElement(By.xpath("//body/div[@id='wrap']/div[@id='navigation']/ul[@id='menu']/li/a[@href='/']")).click();
        getDriver().findElement(By.xpath("//body/div[@id='wrap']/div[@id='main']/h2")).getText();

        Assert.assertEquals(getDriver().findElement(By.xpath("//body/div[@id='wrap']/div[@id='main']/h2")).getText(), "Welcome to 99 Bottles of Beer");
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
        getDriver().get("https://minthouse.com/");

        WebElement propertyList = getDriver().findElement(By
                .xpath("//div[@class='hero hero-home']//span[@class='text'][contains(text(),'Where to next')]"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(propertyList));
        new Actions(getDriver()).click(propertyList).perform();
        getDriver().findElement(By
                .xpath("//div[@class='dropdown active']//a[@tabindex='0'][contains(text(),'Mint House Austin – South Congress')]")).click();

        WebElement dates = getDriver().findElement(By
                .xpath("//div[@class='hero hero-home']//span[@class='value']//span[@class='t-check-in'][contains(text(),'Select date')]"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(dates));
        new Actions(getDriver()).click(dates).perform();

        long todayInMillis = Long.parseLong(getDriver()
                .findElement(By
                        .xpath("//div[@class='hero hero-home']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@class='day-item is-today']"))
                .getAttribute("data-time"));
        WebElement yesterdayDate = getDriver()
                .findElement(By
                        .xpath("//div[@class='hero hero-home']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@data-time=" + Math.subtractExact(todayInMillis, dayInMillis) + "]"));
        System.out.println(Math.subtractExact(todayInMillis, dayInMillis));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(yesterdayDate));
        new Actions(getDriver()).click(dates).perform();

        String actualResult = yesterdayDate.getAttribute("class");

        Assert.assertFalse(actualResult.contains("is-start-date") || actualResult.contains("is-end-date") || actualResult.contains("is-in-range"));
    }

    @Test
    public void testCountAndNamesOfRadioButtons_VadimTref() {
        getDriver().get("https://demoqa.com/");

        getDriver().findElement(
                By.xpath("//*[name()='svg'][@stroke='currentColor']/*[local-name() = 'path'][1]")).click();
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
    public void  testHeaderIsDisplayedInEach (){
        final String expectedHeaderText = "Smart By Design";
        final String carouselContainerPath = "//div[contains(@class, 'swiper-slide')]//div[@class='image']//img[contains(@class, 'd-none')]";

        getDriver().get("https://minthouse.com/");
        List<WebElement> carousel = getDriver().findElements(By.xpath(carouselContainerPath));
        for(WebElement element : carousel) {
            new WebDriverWait(getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.visibilityOf(element));
            if (!element.findElement(By.xpath("parent::div/following-sibling::div//h2")).getText().equals(expectedHeaderText)) {
                System.out.println("Missing text in " + element.getAttribute("src"));
            }

            Assert.assertEquals(element.findElement(By.xpath("parent::div/following-sibling::div//h2")).getText(), expectedHeaderText);
        }
    }
    @Test
    public void testSearchDuckDuckGo_AnastasiaY() {
        getDriver().get("https://duckduckgo.com/");

        WebElement searchInput = getDriver().findElement(By.id("search_form_input_homepage"));
        searchInput.sendKeys("siberian tiger");
        getDriver().findElement(By.id("search_button_homepage")).click();
        List<WebElement> resultLinks = getDriver().findElements(
                By.xpath("//div[@class = 'nrn-react-div']/article//div/h2"));
        for (WebElement link: resultLinks) {

            Assert.assertTrue(link.getText().matches("(?i).*tiger.*"));
        }
    }
}
