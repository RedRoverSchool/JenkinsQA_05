import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupJavaStartTest extends BaseTest {

    private final String FARM_PROJECT_URL = "https://formy-project.herokuapp.com/";

    @Test
    public void tes_tHerokuApp() {
        getDriver().get(FARM_PROJECT_URL);

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));

        Assert.assertEquals(link.getText(), "Autocomplete");
    }

    @Test
    public void test_HerokuAppSearchCheckboxMenu() {
        getDriver().get(FARM_PROJECT_URL);

        //Thread.sleep(5000);
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
        getDriver().get("http://omayo.blogspot.com/");

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
}
