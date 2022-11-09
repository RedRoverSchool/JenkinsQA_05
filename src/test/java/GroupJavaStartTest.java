import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class GroupJavaStartTest extends BaseTest {

    @Test
    public void testHerokuApp() {

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));

        Assert.assertEquals(link.getText(), "Autocomplete");
    }

    @Test
    public void testHerokuAppSearchCheckboxMenu() {
        getDriver().get("https://formy-project.herokuapp.com/");

        //Thread.sleep(5000);
        WebElement link1 = getDriver().findElement(By.xpath("//li/a[@href='/checkbox']"));

        Assert.assertEquals(link1.getText(), "Checkbox");
    }


    @Test
    public void testHerokuAppComponentsList(){
        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement buttons = getDriver().findElement(By.xpath("//li/a[text()='Buttons']"));

        Assert.assertEquals(buttons.getText(), "Buttons");
    }

    @Test
    public void testHerokuApp_QuantityOfDropDownMenuLinks(){

        getDriver().get("https://formy-project.herokuapp.com/");

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

}
