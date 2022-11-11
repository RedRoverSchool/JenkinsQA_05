import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GroupJavaStartTest extends BaseTest {

    private final String FARM_PROJECT_URL = "https://formy-project.herokuapp.com/";
    private final String OMAYO_PAGE_URL = "http://omayo.blogspot.com/";
    private final String WEATHER_PAGE_URL = "https://openweathermap.org/";


    @Test
    public void tes_tHerokuApp() {
        getDriver().get(FARM_PROJECT_URL);

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));

        Assert.assertEquals(link.getText(), "Autocomplete");
    }

    @Test
    public void test_HerokuAppSearchCheckboxMenu() {
        getDriver().get(FARM_PROJECT_URL);

        WebElement link1 = getDriver().findElement(By.xpath("//li/a[@href='/checkbox']"));

        Assert.assertEquals(link1.getText(), "Checkbox");
    }

    @Test
    public void test_HerokuAppComponentsList(){
        getDriver().get(FARM_PROJECT_URL);

        WebElement buttons = getDriver().findElement(By.xpath("//li/a[text()='Buttons']"));

        Assert.assertEquals(buttons.getText(), "Buttons");
    }

    @Test
    public void test_HerokuApp_QuantityOfDropDownMenuLinks(){
        getDriver().get(FARM_PROJECT_URL);

        int expectedResult = 14;

        WebElement dropDownMenuLink = getDriver().findElement(By.xpath("//a[@id='navbarDropdownMenuLink']"));
        dropDownMenuLink.click();

        List<WebElement> aHrefs =
                getDriver().findElements(By.xpath("//div[@class='dropdown-menu show']/a"));

        List<String> listDropDownMenuShow = new ArrayList<>();

        for(WebElement a : aHrefs) {
            listDropDownMenuShow.add(a.getText());
        }

        int actualResult = listDropDownMenuShow.size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test_GoToPopUpWindow() {
        getDriver().get(OMAYO_PAGE_URL);

        String mainWindow = getDriver().getWindowHandle();

        getDriver().findElement(
                By.xpath("//a[text()= 'Open a popup window']")).click();

        Set<String> windows = getDriver().getWindowHandles();
        String popUpWindow = "";

        for (String changeWindow : windows) {
            if (!mainWindow.equals(changeWindow)) {
                popUpWindow = changeWindow;
                break;
            }
        }

        getDriver().switchTo().window(popUpWindow);

        String text = getDriver().findElement(By.id("para1")).getText();
        String text2 = getDriver().findElement(By.id("para2")).getText();

        Assert.assertEquals(text, "A paragraph of text");
        Assert.assertEquals(text2, "Another paragraph of text");
    }

    @Test
    public void test_EnableUnavailableButton() {
        getDriver().get(OMAYO_PAGE_URL);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(25));

        WebElement checkbox = getDriver().findElement(By.id("dte"));

        if (getDriver().findElement(By.id("dte")).getAttribute("disabled").equals("true")) {
            getDriver().findElement(By.xpath("//div[@class='widget-content']/button[text() = 'Check this']")).click();
            wait.until(ExpectedConditions.elementToBeClickable(checkbox));
            checkbox.click();
        } else {
            checkbox.click();
        }

        Assert.assertTrue(checkbox.isSelected());
    }

    @Test
    public void testCheckCheckboxStatus() {
    final List expectedResult = Arrays.asList("Kishore 22 Delhi", "Manish 25 Pune", "Praveen 29 Bangalore", "Dheepthi 31 Mumbai");
    
        getDriver().get(OMAYO_PAGE_URL);

        List<WebElement> elements = getDriver().findElements(By.xpath("//table[@id='table1']/tbody/tr"));

        List<String> actualResult = new ArrayList<>();

        for (WebElement temp : elements) {
            actualResult.add(temp.getText());
        }

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test_TittleAndUrlWhenGuideMenu_CordedWare() {

        String expectedResultUrl = "https://openweathermap.org/guide";
        String expectedResultTitle = "OpenWeatherMap API guide - OpenWeatherMap";

        getDriver().get(WEATHER_PAGE_URL);

        getDriver().findElement(By.xpath("//a[@href = '/guide']")).click();

        String actualResultUrl = getDriver().getCurrentUrl();
        String actualResultTitle = getDriver().getTitle();

        Assert.assertEquals(actualResultUrl, expectedResultUrl);
        Assert.assertEquals(actualResultTitle, expectedResultTitle);
    }
    @Ignore
    @Test
    public void test_TemperatureForCity_CordedWare() {

        String fSymbol = "°F";
        String expectedResult = "°F";

        getDriver().get(WEATHER_PAGE_URL);

        getDriver().findElement(By.xpath("//div[3][@class = 'option']")).click();

        WebElement checkTemperatureHeader = getDriver().findElement(By.xpath("//div[@class = 'current-container mobile-padding']/div[2]/div/span[@class = 'heading']"));
        checkTemperatureHeader.click();

        String actualResult = checkTemperatureHeader.getText().substring(checkTemperatureHeader.getText().length()-2);

        Assert.assertEquals(actualResult, expectedResult);
    }
}
