import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PipelineTest extends BaseTest {
    @DataProvider(name = "new-item-unsafe-names")
    public Object[][] dpMethod() {
        return new Object[][]{{"!Pipeline1"}, {"pipel@ne2"}, {"PipeLine#3"},
                {"PIPL$N@4"}, {"5%^PiPl$^Ne)"}, {"6pipe^;line"}};
    }

    @Test(dataProvider = "new-item-unsafe-names")
    public void test_CreateNewItemWithUnsafeCharactersName(String name) {
        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getDriver().findElement(By.cssSelector("input#name")).sendKeys(
                name);

        Matcher matcher =
                Pattern.compile("[!@#$%^&*|:?></.']").matcher(name);
        matcher.find();

        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("» ‘").append(name.substring(matcher.start(),
                matcher.start() + 1)).append("’ is an unsafe character");

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div" +
                        "#itemname-invalid")).getAttribute("textContent"),
                expectedResult.toString());
    }
}
