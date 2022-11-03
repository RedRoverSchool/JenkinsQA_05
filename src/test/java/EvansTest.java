import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class EvansTest extends BaseTest {
    @Test
    public void testIgnore() {
        getDriver().get("https://www.interglot.com/dictionary/en/es/translate/ignore");
        Assert.assertEquals(getDriver().findElement(By.className("defTitle")).getText(), "ignore:");
    }
    @Ignore
    @Test
    public void testCatchThird() {
        getDriver().get("https://www.ebay.com/");
        getDriver().findElement(By.id("gh-ac")).sendKeys("iphone se 3rd gen");
        getDriver().findElement(By.id("gh-btn")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.ebay.com/sch/i.html?_from=R40&_trksid=p2380057.m570.l1313&_nkw=iphone+se+3rd+gen&_sacat=0");
        String currentWindow = getDriver().getWindowHandle();
        List<WebElement> listOfElements = getDriver().findElements(By.className("s-item__image-img"));
        WebElement element = listOfElements.get(10);
        Actions action = new Actions(getDriver());
        action.moveToElement(element).perform();
        element.click();
        for (String winHandle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(winHandle);
        }
        Assert.assertNotEquals(getDriver().findElement(By.id("prcIsum")).getAttribute("content"), "269.95");
        getDriver().close();
        getDriver().switchTo().window(currentWindow);
        Assert.assertEquals(getDriver().getTitle(), "iphone se 3rd gen | eBay");
    }
}