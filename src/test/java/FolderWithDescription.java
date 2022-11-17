import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FolderWithDescription extends BaseTest {
    private final String DESCRIPTION = "This folder contains job's documentation";
    @Test
    public void createFolderDescriptionTest() {

        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("Docs");
        getDriver().findElement(By.cssSelector(".icon-folder.icon-xlg")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.cssSelector("input[name='_.displayNameOrNull']")).sendKeys("Docs");
        getDriver().findElement(By.cssSelector("textarea[name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.cssSelector("#yui-gen6-button")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#view-message")).getText().contains(DESCRIPTION));

        getDriver().findElement(By.xpath("//a[normalize-space()='Dashboard']")).click();
        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside'] button[class='jenkins-menu-dropdown-chevron']")).click();
        getDriver().findElement(By.xpath("(//span[normalize-space()='Delete Folder'])[1]")).click();
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();
    }
}