import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class GroupPLTest extends BaseTest {

    private final String BASE_URL = "https://www.selenium.dev/";

    @Test
    public void testH2HeaderSeleniumInteractionsWindows() {
        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//a[@href='/documentation']/span")).click();
        getDriver().
                findElement(
                        By.xpath
                                ("//a[@class='align-left pl-0 td-sidebar-link td-sidebar-link__section']/span[contains(text(),'Web')]"))
                .click();
        getDriver().findElement(
                By.xpath("//a[@href='/documentation/webdriver/interactions/']/span[contains(text(),'Inter')]"))
                .click();
        getDriver().findElement(
                By.xpath("//a[@href='/documentation/webdriver/interactions/windows/']/span[contains(text(),'Win')]"))
                .click();

        String expectedResult = "Working with windows and tabs";

        Assert.assertEquals(getDriver().findElement(By.xpath("//main/div/h1")).getText(), expectedResult);
    }

    @Test
    void testNavTest_Main() {
        getDriver().get(BASE_URL);

        getDriver().findElement(By.xpath("//a[@href='/documentation']/span")).click();
        getDriver().
                findElement(
                        By.xpath
                                ("//a[@class='align-left pl-0 td-sidebar-link td-sidebar-link__section']/span[contains(text(),'Web')]"))
                .click();
        getDriver().findElement(
                        By.xpath("//a[@href='/documentation/webdriver/interactions/']/span[contains(text(),'Inter')]"))
                .click();
        getDriver().findElement(
                        By.xpath("//a[@href='/documentation/webdriver/interactions/windows/']/span[contains(text(),'Win')]"))
                .click();

        String expectedResult = "Java".concat("Python");

        List<WebElement> countTableColumns = getDriver().findElements(By.xpath("//main//ul[@id='tabs-0']/li"));

        StringBuilder columnNames = new StringBuilder();
        for (WebElement name : countTableColumns) {
            columnNames.append(name.getText());
        }

        Assert.assertTrue(columnNames.toString().contains(expectedResult));
        Assert.assertEquals(countTableColumns.size(), 6);
    }

    @Test
    public void testParagraph() {
        getDriver().get(BASE_URL);

        WebElement link = getDriver().findElement(
                By.xpath("//div//p[text()='What you do with that power is entirely up to you.']")
        );

        Assert.assertEquals(link.getText(), "What you do with that power is entirely up to you.");
    }
}

