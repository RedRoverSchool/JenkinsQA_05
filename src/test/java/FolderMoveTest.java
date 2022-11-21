import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class FolderMoveTest extends BaseTest {
    private static final By NEW_FOLDER = By.linkText("New Item");
    private static final By INPUT_NAME = By.cssSelector(".jenkins-input");
    private static final By FOLDER = By.xpath("//span[text()='Folder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By SAVE_BUTTON = By.xpath("//button[text()='Save']");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");

    private static final String folderName1 = RandomStringUtils.randomAlphanumeric(5);
    private static final String folderName2 = RandomStringUtils.randomAlphanumeric(5);

    private static final By FOLDER1 = By.xpath("//a[@href='job/" + folderName1 + "/']");
    private static final By FOLDER2 = By.xpath("//a[@href='job/" + folderName2 + "/']");

    private void createFolder(String folderName) {
        getDriver().findElement(NEW_FOLDER).click();
        getDriver().findElement(INPUT_NAME).sendKeys(folderName);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
    }

    private void deleteFolder() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(FOLDER2).sendKeys(Keys.RETURN);
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName2 + "/delete']")).click();
        getDriver().findElement(By.cssSelector(".first-child")).click();
    }

    @Test
    public void testMoveFolderToFolder() {
        createFolder(folderName1);
        createFolder(folderName2);

        getDriver().findElement(FOLDER1).click();
        WebElement element = getDriver().findElement(By.xpath("//span[text()='Move']"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);

        Select dropdown = new Select(getDriver().findElement(By.cssSelector(".select.setting-input")));
        dropdown.selectByVisibleText("Jenkins » " + folderName2);
        getDriver().findElement(By.xpath("//button[text()='Move']")).click();

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(FOLDER2).sendKeys(Keys.RETURN);

        String folderCheck = getDriver().findElement(By.xpath("//a[@href='job/" + folderName1 + "/']")).getText();
        Assert.assertEquals(folderCheck, folderName1);

        getDriver().findElement(DASHBOARD).click();

        List<WebElement> foldersList = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside"));
        Assert.assertTrue(foldersList.size() > 0);
        for (WebElement folder : foldersList) {
            Assert.assertFalse(folder.getText().contains(folderName1));
        }

        deleteFolder();
    }
}