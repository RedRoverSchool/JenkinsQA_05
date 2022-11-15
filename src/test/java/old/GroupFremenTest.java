package old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Ignore
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
    public void testVera_completeWebForm() {
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

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".alert.alert-success")).getText(),
                "The form was successfully submitted!");
    }

    @Test
    public void testMainPageAutocompleteLink() {
        getDriver().get(URL);
        String expectedResult = "https://formy-project.herokuapp.com/autocomplete";

        getDriver().findElement(
                By.xpath("//div[@class = 'jumbotron-fluid']/li/a[@href = '/autocomplete']")).click();

        Assert.assertEquals(expectedResult, getDriver().getCurrentUrl());
    }

    @Test
    public void testKristina_SwitchWindow() {
        getDriver().get(URL);

        String expectedResultTitle = "Switch Window";
        String expectedResultAlert = "This is a test alert!";

        getDriver().findElement(By.xpath("//a[@class='btn btn-lg'][text()='Switch Window']")).click();
        String actualResultTitle = getDriver().findElement(By.xpath("//h1[text()='Switch Window']")).getText();

        getDriver().findElement(By.xpath("//button[@id='alert-button']")).click();
        String actualResultAlert = getDriver().switchTo().alert().getText();
        getDriver().switchTo().alert().accept();

        getDriver().findElement(By.xpath("//button[@id='new-tab-button']")).click();

        Assert.assertEquals(actualResultTitle, expectedResultTitle);
        Assert.assertEquals(actualResultAlert, expectedResultAlert);
    }

    @Test
    public void testDZAmountElementsOfLinksInTabComponents() {
        final int expectedResult = 14;

        getDriver().get(URL);

        getDriver().findElement(By.id("navbarDropdownMenuLink")).click();

        int actualResult = getDriver().findElements(
                By.xpath("//div[@class='dropdown-menu show']/a")).size();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testDZSubmitAnEmptyForm() {
        final String expectedResult = "The form was successfully submitted!";

        getDriver().get(URL);

        getDriver().findElement(By.xpath("//a[text()='Form']")).click();
        getDriver().findElement(By.xpath("//a[@class='btn btn-lg btn-primary']")).click();

        String actualResult = getDriver().findElement(
                By.xpath("//div[@class='alert alert-success']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCloseModalIO() {
        getDriver().get(URL);
        getDriver().findElement(By.xpath("//li/a[@href='/modal']")).click();
        getDriver().findElement(By.xpath("//form/button[@type='button']")).click();
        getDriver().findElement(By.id("close-button")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//h1[text()='Modal']")).getText(), "Modal");
    }

    @Test
    public void testHerokuappButtonsContainWarningIO() {
        getDriver().get(URL);
        getDriver().findElement(By.xpath("//li/a[@href='/buttons']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//button[@class='btn btn-lg btn-warning']")).getText(), "Warning");
    }

    @Test
    public void testDZSwitchToNewWindowCheckLogoName() {
        final String expectedResult = "FORMY";

        getDriver().get(URL);

        getDriver().findElement(By.xpath("//a[@class='btn btn-lg' and text()='Switch Window']")).click();

        String originalWindow = getDriver().getWindowHandle();
        getDriver().findElement(By.id("new-tab-button")).click();

        for (String windowNew : getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowNew)) {
                getDriver().switchTo().window(windowNew);
                break;
            }
        }
        String actualResult = getDriver().findElement(By.xpath("//a[@id='logo']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testHerokuAppButtonsName() {
        final String expectedButton = "Danger";

        getDriver().get(URL);
        getDriver().findElement(
                By.xpath("//li/a[@href='/buttons']")).click();

        String actualButton = getDriver().findElement(
                By.xpath("//button[@class ='btn btn-lg btn-danger']")).getText();

        Assert.assertEquals(actualButton, expectedButton);
    }

    @Test
    public void testMICheckModalTitleMessage() throws InterruptedException {
        final String expectedText = "Some text here";

        getDriver().get(URL);
        getDriver().findElement(
                By.xpath("//li/a[@href = '/modal']")).click();
        getDriver().findElement(
                By.xpath("//button[@id = 'modal-button']")).click();

        Thread.sleep(1000);
        String actualText = getDriver().findElement(
                By.xpath("//div[@class = 'modal-body']")).getText();

        Assert.assertEquals(actualText, expectedText);
    }
}
