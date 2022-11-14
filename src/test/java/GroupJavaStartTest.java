import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GroupJavaStartTest extends BaseTest {

    private final String FARM_PROJECT_URL = "https://formy-project.herokuapp.com/";
    private final String OMAYO_PAGE_URL= "http://omayo.blogspot.com/";
    private final String JENKINS_URL_CW = "http://192.168.201.154:8099/";
    private final String LOGIN_CW = "CordedWare";
    private final String PASSWORD_CW = "ОутлштыЕуые";

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
    public void test_CreateJobInJenkins_CordedWare() {

        String expectedResult = "Freestyle";

        getDriver().get(JENKINS_URL_CW);

        getDriver().findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(LOGIN_CW);

        getDriver().findElement(By.xpath("//div[2]/input[@placeholder='Пароль']")).sendKeys(PASSWORD_CW);

        getDriver().findElement(By.xpath("//div[4]/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div[1]/span[@class='task-link-wrapper ']")).click();

        getDriver().findElement(By.xpath("//div/input[@name='name']")).sendKeys("Freestyle");

        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();

        getDriver().findElement(By.xpath("//span/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div[10]/div/span[1]/span/button")).click();

        getDriver().findElement(By.xpath("//div[1]/div[1]/div[1]/span")).click();

        WebElement JobFreestyle = getDriver().findElement(By.xpath("//td[3]/a[@href='job/Test/Freestyle']"));

        String actualResult = JobFreestyle.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
