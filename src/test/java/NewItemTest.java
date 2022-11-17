import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

public class NewItemTest extends BaseTest {

    @Test
    public void testContainsItemsWithoutCreatedProject() {
        final List<String> expectedResult = List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        getDriver().findElement(By.linkText("New Item")).click();

        List<WebElement> newItemsElements = getDriver().findElements(By.xpath("//label/span[@class='label']"));
        List<String> newItemsName = newItemsElements.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(newItemsName, expectedResult);
    }
}
