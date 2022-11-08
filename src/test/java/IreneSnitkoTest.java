import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class IreneSnitkoTest extends BaseTest {

    @Test
    public void testVerifyQuantityALinks() {
        getDriver().get("https://formy-project.herokuapp.com/");

        int expectedResult = 14;

        List<WebElement> aHrefs =
                getDriver().findElements(By.xpath("//div[@class='jumbotron-fluid']/li/a"));
        List<String> listBtnLg = new ArrayList<>();

        for(WebElement a : aHrefs) {
            listBtnLg.add(a.getText());
        }

        int actualResult = listBtnLg.size();

        Assert.assertTrue(listBtnLg.size() != 0);
        Assert.assertEquals(actualResult, expectedResult);
    }
}