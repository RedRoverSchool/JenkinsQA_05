import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GroupTeamRocketTest extends BaseTest {

    @Test
    public void testAddElementHerokuapp() {
        getDriver().get("https://the-internet.herokuapp.com/");
        getDriver().findElement(By.xpath("//a[@href='/add_remove_elements/']")).click();
        getDriver().findElement(By.xpath("//button[@onclick='addElement()']")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[@class='added-manually']")).isDisplayed());
    }

    @Test
    public void testSwagLabs_LogIn() {
        getDriver().get("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void testFindTitleGuide_NataliiaOliver() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");
        Thread.sleep(6000);
        getDriver().findElement(By.xpath("//div[@id='desktop-menu']/ul/li/a[@href='/guide']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/guide");
        Assert.assertEquals(
                getDriver()
                        .findElement(By.xpath("//div[@class='col-sm-7']/h1[text()='Guide']"))
                        .getText(), "Guide");
    }

    @Test
    public void testCart() {
        getDriver().get ("https://www.saucedemo.com");
        getDriver().findElement (By.id ("user-name")).sendKeys ("standard_user");
        getDriver().findElement (By.id ("password")).sendKeys ("secret_sauce");
        getDriver().findElement (By.id ("login-button")).click ();
        getDriver().findElement (By.id ("add-to-cart-sauce-labs-backpack")).click ();
        getDriver().findElement (By.id ("shopping_cart_container")).click ();
        Assert.assertTrue (getDriver().findElement (By.id ("item_4_title_link")).isDisplayed ());
     }

     @Test
     public void testAboutLinkRedirect() {
        getDriver().get ("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("react-burger-menu-btn")).click();
        getDriver().findElement(By.id("about_sidebar_link")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(),"https://saucelabs.com/");
    }

    @Ignore
    @Test
    public void testLAtimes_RomanS() throws InterruptedException{
        getDriver().get("https://www.latimes.com/");
        getDriver().findElement(By.xpath("//body[@class='page-body']/ps-header[@class='page-header-custom-element sticky-top']//span[@class='label']")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//ps-header/div[1]/div[1]/div[1]/nav[1]/ul[1]/li[6]/div[1]/div[1]/a[1]")).click();
        Assert.assertEquals(getDriver().getTitle(), "Food - Los Angeles Times");
    }

    @Ignore
    @Test
    public void testAboutUs(){
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
        getDriver().get ("http://automationpractice.com");
        getDriver().findElement (By.xpath("//section[@id='block_various_links_footer']/ul/li[6]/a")).click();

        Assert.assertEquals (getDriver().getCurrentUrl (),
                "http://automationpractice.com/index.php?id_cms=3&controller=cms");
    }

    @Test
    public void testCheckOut() {
        String name = "John";
        String lastName = "Smith";
        getDriver ().get ("https://www.saucedemo.com");
        getDriver ().findElement (By.id ("user-name")).sendKeys ("standard_user");
        getDriver ().findElement (By.id ("password")).sendKeys ("secret_sauce");
        getDriver ().findElement (By.id ("login-button")).click ();
        getDriver ().findElement (By.id ("add-to-cart-sauce-labs-backpack")).click ();
        getDriver ().findElement (By.id ("shopping_cart_container")).click ();
        getDriver ().findElement (By.id ("checkout")).click ();
        getDriver ().findElement (By.id ("first-name")).sendKeys (name);
        getDriver ().findElement (By.id ("last-name")).sendKeys (lastName);
        getDriver ().findElement (By.id ("postal-code")).sendKeys ("28277");
        getDriver ().findElement (By.id ("continue")).click ();
        getDriver ().findElement (By.id ("finish")).click ();
        Assert.assertEquals (getDriver ().findElement (By.xpath ("//*[@id=\"checkout_complete_container\"]/h2")).getText (), "THANK YOU FOR YOUR ORDER");
    }


    @Test
    public void testAddToCartButton() {
        getDriver().get("https://www.demoblaze.com");
        getDriver().findElement(By.xpath("//div[@class='list-group']/a[4]")).click();
        getDriver().findElement(By.xpath("//div[@class='card-block']/h4[1]/a[@href=\"prod.html?idp_=10\"]")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@class='btn btn-success btn-lg']")).isDisplayed());
    }

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
                .getText(),"Your message has been successfully sent to our team.");
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
                .getText(),"The Tragedy of Tolstoy");
   }

    @Test
    public void testSaucedemo_EZ() {
        getDriver().get("https://www.saucedemo.com/");
        getDriver().findElement(By.id("user-name")).sendKeys("locked_out_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector(
                "#login_button_container > div > form > div.error-message-container.error")).isDisplayed());
    }

    @Test
    public void testBrowseLanguages_NO() {
        getDriver().get ("http://www.99-bottles-of-beer.net/");
        getDriver().findElement (By.xpath("//div[@id='navigation']/ul[@id='menu']/li/a[@href='/abc.html']")).click();
        Assert.assertEquals(getDriver().findElement (By.xpath("//table[@id='category']/tbody/tr/th[text()='Language']"))
                .getText(), "Language");
        Assert.assertEquals(getDriver().findElement (By.xpath("//table[@id='category']/tbody/tr/th[text()='Author']"))
                .getText(), "Author");
    }

    @Test
    public void testFilterIconSortingPriceFromLowToHigh_AnastasiaYakimova() {
        getDriver().get("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
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

                    HttpURLConnection connection = (HttpURLConnection)new URL(imgSource).openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    Assert.assertTrue(responseCode >= 200 && responseCode < 400);
            }
        }
    }

    @Test
    public void testPriceNokia_ZoiaBut(){
        getDriver().get("https://www.demoblaze.com/");
        getDriver().findElement(By.xpath("//div/a[@href='prod.html?idp_=2']")).click();

        Assert.assertFalse((getDriver().findElement(By.xpath("//h3[text()='$820']"))
                        .getText().contains("$620")));
    }
    
    @Test
    public void testSignGuestbookTest_NO() {

        String random = "" + ((int) (Math.random() * 900) + 100);
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
                By.xpath("//div[@id='main']/p[contains(text(),' Error: Invalid security code.')]"))
                .getText(), "Error: Error: Invalid security code.");
    }

    @Test
    public void testTenLanguageStartNumbers_NO() {

        getDriver().get("http://www.99-bottles-of-beer.net/");
        getDriver().findElement(By.xpath("//ul[@id='menu']/li/a[@href='/abc.html']")).click();
        getDriver().findElement(By.xpath("//a[@href='0.html']")).click();
        Assert.assertEquals(getDriver().findElements(By.xpath("//tbody/tr/td/a")).size(), 10);
    }

    @Test
    public void testBrowseLanguagesAlternativeVersions_NO() {

        getDriver().get("http://www.99-bottles-of-beer.net/");
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
        getDriver().findElement(By.xpath("//div[@id='cobranding-steps']//input[@name='zipcode']")).sendKeys("11230");
        getDriver().findElement(By.xpath("//div[@id='cobranding-steps']//a[@class='btn next-step']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//button[@type='submit'][@class='btn cobranding-form-submit']"))
                .getText(), "Go To Final Step");
    }

    @Test
    public void testSwitchToPageCompareInsuranceQuote_VadimTref() {
        getDriver().get("https://www.statewidedealerinsurance.com/");
        getDriver().findElement(By.id("ZipCode")).sendKeys("11230");
        getDriver().findElement(By.id("Type")).click();
        getDriver().findElement(By.xpath("//select[@id='Type']/option[@value='Home']")).click();
        getDriver().findElement(By.xpath("//form[@id='miniQuote']//button[@type='submit']")).click();
        WebElement iframeSwitch = getDriver().findElement(By.id("cpIframe"));
        getDriver().switchTo().frame(iframeSwitch);

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@id='body']//p[@class='cpInstructions cpPage0']"))
                .getText(), "An insurance quote does not impact your credit score. "
                + "Quote will take approximately 3-5 minutes to complete.");

        getDriver().switchTo().defaultContent();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//h1")).getText(), "Compare Quotes");
    }
}