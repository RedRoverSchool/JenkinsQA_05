import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupJavanistyTest extends BaseTest {

    @Test
    public void test_CheckMessage() {

        final String expectedResult = "24.69";

        getDriver().get("https://healthunify.com/bmicalculator/");

        getDriver().findElement(By.name("wg")).sendKeys("80");
        getDriver().findElement(By.name("ht")).sendKeys("180");
        getDriver().findElement(By.name("cc")).click();

        Assert.assertEquals(getDriver().findElement(By.name("si")).getAttribute("value"), expectedResult);
    }

    @Test
    public void test_Registration_IriSamo() {
        getDriver().get("https://www.sharelane.com/cgi-bin/register.py");

        getDriver().findElement(By.name("zip_code")).sendKeys("196240");
        getDriver().findElement(
                By.xpath("//input[@value='Continue']")).click();

        getDriver().findElement(By.name("first_name")).sendKeys("Imya");
        getDriver().findElement(By.name("last_name")).sendKeys("Familiya");
        getDriver().findElement(
                By.name("email")).sendKeys("Familiya@gmail.com");
        getDriver().findElement(By.name("password1")).sendKeys("777555333");
        getDriver().findElement(By.name("password2")).sendKeys("777555333");
        getDriver().findElement(
                By.xpath("//input[@value='Register']")).click();

        String actualConfirmationMessage = getDriver().findElement(
                By.xpath("//span[@class='confirmation_message']")).getText();

        Assert.assertEquals(actualConfirmationMessage, "Account is created!");
    }

    @Ignore
    @Test
    public void testBdoWarrior2() throws InterruptedException {
        getDriver().get("https://bdocodex.com/ru/skillbuilder/");
        Thread.sleep(2000);
        WebElement buttobWarrior = getDriver().findElement(By.xpath("//div[@class='class_cell'][1]/*"));
        buttobWarrior.click();
        Thread.sleep(2000);
        WebElement buttonSkillAbsolute = getDriver().findElement(By.xpath
                ("//div[@data-gid=\"618\"]"));
        buttonSkillAbsolute.click();
        Thread.sleep(2000);
        WebElement counter = getDriver().findElement(By.xpath
                ("//tr[4]//descendant::div[@class='level_cell current_level']"));
        String counterValue = counter.getText();
        Assert.assertEquals(counterValue, "10");
    }

    @Ignore
    @Test
    public void testZipCodeInputField() {
        getDriver().get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement zipInputField = getDriver().findElement(By.name("zip_code"));
        zipInputField.sendKeys("12345", Keys.ENTER);
        zipInputField = getDriver().findElement(By.name("zip_code"));
        WebElement registerButton = getDriver().findElement(By.xpath("//*[@value='Register']"));
        Assert.assertEquals(zipInputField.getAttribute("type"), "hidden");
        Assert.assertTrue(registerButton.isDisplayed());
    }

    @Test
    public void testIlyaFirstTest() {
        getDriver().get("https://karkas.k3-cottage.ru/");
        WebElement text = getDriver().findElement(By.xpath("//li/a[@href='#config']"));
        Assert.assertEquals(text.getText(), "НАСТРОЙКИ");
    }

    @Ignore
    @Test
    public void testTextHlebnica() {
        getDriver().get("http://hlebnitca.ru/");
        getDriver().findElement(By.xpath("//a[@class= 'tn-atom']")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "http://hlebnitca.ru/about");
    }

    @Ignore
    @Test
    public void testAboutHlebnica() {
        getDriver().get("http://hlebnitca.ru/about");
        String aboutHlebnica = getDriver().findElement(By.xpath("//div[@class = 't396__elem tn-elem tn-elem__3963063211640603855210']")).getText();
        Assert.assertEquals(aboutHlebnica, "Вкус настоящей домашней выпечки");
    }

    @Test
    public void testIlyaSecondTest() {
        getDriver().get("https://karkas.k3-cottage.ru/");
        WebElement text = getDriver().findElement(By.xpath("//li/a[@href='#features']"));
        Assert.assertEquals(text.getText(), "ВОЗМОЖНОСТИ");
    }

    @Test
    public void testAratinveMainMenuTitle() {
        String titleExpected = "Главная страница\nО программе\nНовости\nПродукты\nОбучение" +
                "\nСкачать\nКупить\nFAQ\nО нас";

        getDriver().get("https://k3-mebel.ru/");
        String titleActual = getDriver().findElement(By.id("menu-verhnee-menyu")).getText();
        Assert.assertEquals(titleActual, titleExpected);
    }

    @Test
    public void testWeightPoundsHeight2() {
        getDriver().get("https://healthunify.com/bmicalculator/");
        Select weight = new Select(getDriver().findElement(By.name("opt1")));
        weight.selectByValue("pounds");
        getDriver().findElement(By.name("wg")).sendKeys("88");
        Select height = new Select(getDriver().findElement(By.name("opt2")));
        height.selectByValue("5");
        Select height2 = new Select(getDriver().findElement(By.name("opt3")));
        height2.selectByValue("4");
        getDriver().findElement(By.name("cc")).click();
        Assert.assertEquals(getDriver().findElement(By.name("si")).getAttribute("value"), "15.05");
    }

    @Test
    public void testH2TagText_WhenSearchingCityCountry() throws InterruptedException {

        String url = "https://openweathermap.org/";
        String cityName = "Paris";
        String expectedResult = "Paris, FR";

        getDriver().get(url);
        Thread.sleep(5000);

        WebElement searchCityField = getDriver().findElement(
                By.xpath("//div[@id='weather-widget']//input[@placeholder='Search city']"));
        searchCityField.click();
        searchCityField.sendKeys(cityName);

        WebElement searchButton = getDriver().findElement(
                By.xpath("//div[@id='weather-widget']//button[@type='submit']"));
        searchButton.click();
        Thread.sleep(1000);

        WebElement parisFRChoiceInDropdownMenu = getDriver().findElement(
                By.xpath("//ul[@class='search-dropdown-menu']/li/span[text()='Paris, FR ']"));
        parisFRChoiceInDropdownMenu.click();

        WebElement h2CityCountryHeader = getDriver().findElement(
                By.xpath("//div[@id='weather-widget']//h2"));

        Thread.sleep(2000);
        String actualResult = h2CityCountryHeader.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }


    @Test
    public void testCalculateThePercentageOfFat() {
        getDriver().get("https://healthunify.com/bmicalculator/");

        WebElement element_ofSelectPounds = getDriver().findElement(By.name("opt1"));
        Select selectPounds = new Select(element_ofSelectPounds);
        WebElement element_ofSelectHeight1 = getDriver().findElement(By.name("opt2"));
        Select selectHeight1 = new Select(element_ofSelectHeight1);
        WebElement element_ofSelectHeight2 = getDriver().findElement(By.name("opt3"));
        Select selectHeight2 = new Select(element_ofSelectHeight2);
        WebElement textAboutYourWeight = getDriver().findElement(By.xpath("//input[@class='content']"));

        element_ofSelectPounds.click();
        selectPounds.selectByIndex(0);
        getDriver().findElement(By.xpath("//input[@name='wg']")).clear();
        getDriver().findElement(By.xpath("//input[@name='wg']")).sendKeys("55");
        element_ofSelectHeight1.click();
        selectHeight1.selectByIndex(4);
        element_ofSelectHeight2.click();
        selectHeight2.selectByIndex(3);
        getDriver().findElement(By.xpath("//input[@value='Calculate']")).click();

        Assert.assertTrue(textAboutYourWeight.isDisplayed());
    }
}
