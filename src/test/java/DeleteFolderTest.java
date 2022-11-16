import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteFolderTest extends BaseTest {
    final private static String uniqueFolderName = "folder" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    private void createNewFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.PAGE_DOWN).build().perform();
        getDriver().findElement(By.id("name")).sendKeys(uniqueFolderName);
        getDriver().findElement(By.xpath("//span[text() = 'Folder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testDeleteFolder() {
        createNewFolder();

        getDriver().findElement(By.xpath("//span[text() = '"+ uniqueFolderName +"']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/"+ uniqueFolderName +"/delete']")).click();
        getDriver().findElement(By.xpath("//button[@type= 'submit']")).click();

        List<String> foldersList = getDriver()
                .findElements(By.xpath("//tr/td[3]/a/span"))
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertFalse(foldersList.contains(uniqueFolderName));
    }
}
