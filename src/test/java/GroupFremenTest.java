import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testVera_completeWebForm() throws InterruptedException {
        getDriver().get(URL);
        getDriver().findElement(
                By.xpath("//div[@class='jumbotron-fluid']//a[text()='Complete Web Form']")).click();
        getDriver().findElement(By.id("first-name")).sendKeys("Vera");
        getDriver().findElement(By.id("last-name")).sendKeys("Dmitrenko");
        getDriver().findElement(By.id("job-title")).sendKeys("Software Tester");
        getDriver().findElement(By.id("radio-button-1")).click();
        getDriver().findElement(By.id("checkbox-2")).click();
        final Select select = new Select(getDriver().findElement(By.id("select-menu")));
        select.selectByVisibleText("0-1");
        WebElement datePicker = getDriver().findElement(By.id("datepicker"));
        datePicker.sendKeys(LocalDate.now().toString());
        datePicker.sendKeys(Keys.RETURN);
        getDriver().findElement(By.cssSelector(".btn.btn-lg.btn-primary")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1")).getText(),
                "Thanks for submitting your form");
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".alert.alert-success")).getText(),
                "The form was successfully submitted!");
    }

    @Test
    public void testVera_optionValuesForSelectedElementsInSelectMenu() {
        getDriver().get(URL);
        getDriver().findElement(
                By.xpath("//div[@class='jumbotron-fluid']//a[text()='Complete Web Form']")).click();
        final WebElement selectMenu = getDriver().findElement(By.id("select-menu"));
        final Select select = new Select(selectMenu);

        select.selectByVisibleText("Select an option");
        assertSelectedValueEquals(select, "0");

        select.selectByVisibleText("0-1");
        assertSelectedValueEquals(select, "1");

        select.selectByVisibleText("2-4");
        assertSelectedValueEquals(select, "2");

        select.selectByVisibleText("5-9");
        assertSelectedValueEquals(select, "3");

        select.selectByVisibleText("10+");
        assertSelectedValueEquals(select, "4");
    }

    private static void assertSelectedValueEquals(Select select, String expectedValue) {
        Assert.assertEquals(select.getFirstSelectedOption().getAttribute("value"), expectedValue);
    }

    @Test
    public void testMainPageAutocompleteLink() {
        getDriver().get(URL);
        String expectedResult = "https://formy-project.herokuapp.com/autocomplete";

        WebElement LinkAutocomplete = getDriver().findElement(
                By.xpath("//div[@class = 'jumbotron-fluid']/li/a[@href = '/autocomplete']"));
        LinkAutocomplete.click();

        String actualResult = getDriver().getCurrentUrl();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testKristina_SwitchWindow() {
        getDriver().get(URL);

        getDriver().findElement(By.xpath("//a[@class='btn btn-lg'][text()='Switch Window']")).click();
        getDriver().findElement(By.xpath("//div/button[@id='new-tab-button']")).click();
        String currentHandle = getDriver().getWindowHandle();
        getDriver().switchTo().window(currentHandle);
        getDriver().findElement(By.xpath("//div/button[@id='alert-button']")).click();

        Assert.assertEquals(getDriver().switchTo().alert().getText(), "This is a test alert!");
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1")).getText(),
                "Switch Window");
    }

    @Test
    public void testDZAmountElementsOfLinksInTabComponents() {
        getDriver().get(URL);
        int expectedResult = 14;
        WebElement linkComponents = getDriver().findElement(
                By.id("navbarDropdownMenuLink"));
        linkComponents.click();
        int actualResult = getDriver().findElements(
                By.xpath("//div[@class='dropdown-menu show']/a")).size();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testDZSubmitAnEmptyForm() {
        getDriver().get(URL);
        String expectedResult = "The form was successfully submitted!";
        WebElement linkForm = getDriver().findElement(By.xpath("//a[text()='Form']"));
        linkForm.click();
        WebElement pressSubmit = getDriver().findElement(By.xpath("//a[@class='btn btn-lg btn-primary']"));
        pressSubmit.click();
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@class='alert alert-success']")).getText(), expectedResult);
    }

}
