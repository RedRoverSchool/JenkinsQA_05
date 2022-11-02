import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class SpiritMastersTest extends BaseTest {

    @Test
    public void switchToSecondWindow_OlPolezhaeva_Test() {

        getDriver().get("https://www.toolsqa.com/selenium-training/");

        WebElement toolsQAHeader= getDriver().findElement(By.xpath("//div[@class='col-auto']//li[3]"));
        toolsQAHeader.click();

        for(String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.xpath("//div[@class='card-body']/h5")).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Elements");
    }
    private WebElement findCard_PK(int index) {
        getDriver().get("https://demoqa.com/");
        List<WebElement> category = getDriver().findElements(By.className("card"));
        return category.get(index);
    }

    @Test
    public void test_PK_RedirectToElementsTab(){
        findCard_PK(0).click();
        String actualTextElements = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextElements, "Elements");
    }

    @Test
    public void test_PK_RedirectToFormsTab() {
        findCard_PK(1).click();
        String actualTextForms = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextForms, "Forms");
    }

    @Test
    public void test_PK_RedirectToAlertsTab(){
        findCard_PK(2).click();
        String actualTextAlerts = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextAlerts, "Alerts, Frame & Windows");
    }

    @Test
    public void test_PK_RedirectToWidgetsTab(){
        findCard_PK(3).click();
        String actualTextWidgets = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextWidgets, "Widgets");
    }

    @Test
    public void test_PK_RedirectToInteractionsTab(){
        findCard_PK(4).click();
        String actualTextInteractions = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextInteractions, "Interactions");
    }

    @Test
    public void test_PK_RedirectToBooksTab(){
        findCard_PK(5).click();
        String actualTextBooks = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(actualTextBooks, "Book Store");
    }

    @Test
    public void testHerokuapp_gdiksanov() {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));

        Assert.assertEquals(link.getText(), "Autocomplete");
    }
}
