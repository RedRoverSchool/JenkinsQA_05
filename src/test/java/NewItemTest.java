import model.HomePage;
import model.NewItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class NewItemTest extends BaseTest {

    private static final String PROJECT_NAME = TestUtils.getRandomStr(7);

    private void setJobTypePipeline(String jobName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.id("name"))).sendKeys(jobName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
    }

    @Ignore
    @Test
    public void testNewItemsPageContainsItemsWithoutCreatedProject() {
        final List<String> expectedResult = List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        getDriver().findElement(By.linkText("New Item")).click();

        List<WebElement> newItemsElements = getDriver().findElements(By.xpath("//label/span[@class='label']"));
        List<String> newItemsName = newItemsElements.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(newItemsName, expectedResult);
    }

    @Test
    @Ignore
    public void testGoToNewItemPage() {
        final String expectedResult = "Enter an item name";

        getDriver().findElement(By.linkText("New Item")).click();

        Assert.assertEquals(getDriver().findElement(By.className("h3")).getText(), expectedResult);
    }

    @Test
    public void testCreateFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen4-button")).click();
        getDriver().findElement(By.xpath("//a[@href='/' and  @class= 'model-link']")).click();

        Assert.assertEquals(getDriver().findElement
                (By.xpath("//table[@id='projectstatus']//a[@href='job/"+ PROJECT_NAME + "/']")).getText(), PROJECT_NAME);
    }

    @Test
    public void testCreateAnyItemWithSpacesOnlyNameError() {

        int itemsListSize =  new HomePage(getDriver())
                .clickNewItem()
                .getItemsListSize();

        for (int i = 0; i < itemsListSize; i++) {

            String actualErrorMessage =  new NewItemPage(getDriver())
                    .rootMenuDashboardLinkClick()
                    .clickNewItem()
                    .setProjectName("      ")
                    .setItemAndClickOk(i)
                    .getErrorMessage();

            Assert.assertEquals(actualErrorMessage, "No name is specified");
        }
    }

    @Test
    public void testCreateItemsWithoutName() {
        int itemsListSize = new HomePage((getDriver()))
                .clickNewItem()
                .getItemsListSize();

        for (int i = 0; i < itemsListSize; i++) {

            String actualErrorMessage = new NewItemPage((getDriver()))
                    .rootMenuDashboardLinkClick()
                    .clickNewItem()
                    .setItem(i)
                    .getEmptyNameErrorMessage();

            Assert.assertEquals(actualErrorMessage,"» This field cannot be empty, please enter a valid name");
        }
    }

    @Test
    public void testCreateNewItemWithUnsafeCharacterName(){
        final String nameNewItem = "5%^PiPl$^Ne)";
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(nameNewItem)
                .getNameErrorMessageText();

        Assert.assertEquals(errorMessage,"» ‘%’ is an unsafe character");
    }

    @Test
    public void testCreateNewItemWithoutChooseAnyFolder(){
        setJobTypePipeline("");

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Ignore
    @Test(dependsOnMethods = "testWarningMessageIsDisappeared")
    public void testCreateNewItemFromOtherNonExistingName() {
        final String jobName = TestUtils.getRandomStr(7);

        setJobTypePipeline(jobName);
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.id("from"))).click()
                .sendKeys(jobName).perform();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "No such job: " + jobName);
    }
}
