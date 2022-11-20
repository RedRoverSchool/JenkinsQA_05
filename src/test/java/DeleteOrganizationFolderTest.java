import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteOrganizationFolderTest extends BaseTest {
    final private static String nameFolder = "New Organization Folder3";

    private void createNewOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input [@name = 'name']")).sendKeys("New Organization Folder3");
        getDriver().findElement(By.xpath("//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
    }


    @Test
    public void testDeleteOrganizationFolder() {

        createNewOrganizationFolder();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//span[text()='New Organization Folder3']")).click();
        getDriver().findElement(By.xpath("//span//*[@class='icon-edit-delete icon-md']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        List<String> foldersList = getDriver()
                .findElements(By.xpath("//tr/td[3]/a/span"))
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertFalse(foldersList.contains(nameFolder));
    }
}
