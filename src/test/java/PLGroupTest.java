import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class PLGroupTest extends BaseTest {

    @Test
    public void testH2HeaderSeleniumInteractionsWindows(){
        getDriver().get("https://www.selenium.dev/documentation/webdriver/interactions/windows/");

        String expectedResult = "Working with windows and tabs";

        Assert.assertEquals(getDriver().findElement(By.xpath("//main/div/h1")).getText(),expectedResult);
    }

    @Test void testNavTest_Main(){
        getDriver().get("https://www.selenium.dev/documentation/webdriver/interactions/windows/");

        String expectedResult = "Java".concat("Python");

        List<WebElement> countTableColumns = getDriver().findElements(By.xpath("//main//ul[@id='tabs-0']/li"));

        String columnNames = "";
        for (WebElement name : countTableColumns) {
            columnNames += name.getText();
        }

        Assert.assertTrue(columnNames.contains(expectedResult));
        Assert.assertEquals(countTableColumns.size(), 6);
        }

    public void testParagraph(){
        getDriver().get("https://www.selenium.dev/");

        WebElement link = getDriver().findElement(
                By.xpath("//div//p[text()='What you do with that power is entirely up to you.']")
        );

        Assert.assertEquals(link.getText(), "What you do with that power is entirely up to you.");
    }
}
