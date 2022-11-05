import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupFremenTest extends BaseTest {

    public final String URL = "https://formy-project.herokuapp.com/";

    @Test
    public void testMenuComponentsContainsListOfAllTheComponents() {
        List<String> expectedMenu = getExpectedListItemsMenuComponents();
        getDriver().get(URL);
        WebElement menuNavBarButtonComponents = getDriver().findElement(
                By.xpath("//a[@id = 'navbarDropdownMenuLink']"));
        menuNavBarButtonComponents.click();
        List<String> actualMenu = getActualListItemsByXpath("//a[@class = 'dropdown-item']");

        Assert.assertEquals(actualMenu, expectedMenu);
    }

    @Test
    public void testMainPageContainsListOfAllTheComponents() {
        List<String> expectedList = getExpectedListItemsMenuComponents();
        getDriver().get(URL);
        List<String> actualList = getActualListItemsByXpath("//li/a[@class = 'btn btn-lg']");

        Assert.assertEquals(actualList, expectedList);
    }

    private List<String> getExpectedListItemsMenuComponents() {
        List<String> expectedMenu = new ArrayList<>();
        expectedMenu.add("Autocomplete");
        expectedMenu.add("Buttons");
        expectedMenu.add("Checkbox");
        expectedMenu.add("Datepicker");
        expectedMenu.add("Drag and Drop");
        expectedMenu.add("Dropdown");
        expectedMenu.add("Enabled and disabled elements");
        expectedMenu.add("File Upload");
        expectedMenu.add("Key and Mouse Press");
        expectedMenu.add("Modal");
        expectedMenu.add("Page Scroll");
        expectedMenu.add("Radio Button");
        expectedMenu.add("Switch Window");
        expectedMenu.add("Complete Web Form");
        return expectedMenu;
    }

    private List<String> getActualListItemsByXpath(String xpathOfWebElementsList) {
        List<String> actualListItems = new ArrayList<>();
        List<WebElement> listMenuNavBarComponents = getDriver().findElements(
                By.xpath(xpathOfWebElementsList));
        for (WebElement itemMenuComponents : listMenuNavBarComponents) {
            actualListItems.add(itemMenuComponents.getText());
        }
        return actualListItems;
    }
}
