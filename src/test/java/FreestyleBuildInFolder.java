import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class FreestyleBuildInFolder extends BaseTest {
    private static final By DASHBOARD = By.xpath("//a[normalize-space()='Dashboard']");
    private static final String DESCRIPTION = "This folder contains job's documentation";
    private static String x = UUID.randomUUID().toString().substring(0, 2);
    private static String name = "project-" + x;
    @Test
    public void createFolderTest() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("Docs");
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("input[name='_.displayNameOrNull']")).sendKeys("Docs");
        getDriver().findElement(By.cssSelector("textarea[name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.cssSelector("#yui-gen6-button")).click();

        Assert.assertTrue(getDriver().findElement(
                By.cssSelector("#view-message")).getText().contains(DESCRIPTION));
    }
    @Test(dependsOnMethods = {"createFolderTest"})
    public void testCreateFreestyleProject() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.linkText("Docs")).click();
        getDriver().findElement(By.xpath("//*[contains(@class,'icon-new-package icon-md')]")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[normalize-space()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.linkText(name)).getText(), name);
    }

    @Test(dependsOnMethods = {"testCreateFreestyleProject"})
    public void testBuildFreestyleProject(){
        Actions actions = new Actions(getDriver());
        actions.doubleClick(getDriver().findElement(By.xpath("//a[@href='job/Docs/']"))).perform();
        actions.moveToElement(getDriver().findElement(By.xpath("//td/a[@href='job/" + name + "/']"))).perform();
        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside'] button[class='jenkins-menu-dropdown-chevron']")).click();
        getDriver().findElement(By.xpath("//span[normalize-space()='Build Now']")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/" + name + "/']")).click();
        getDriver().findElement(By.xpath("//a[normalize-space()='#1']")).click();
        getDriver().findElement(By.linkText("Console Output")).click();
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(3000));

        Assert.assertTrue(getDriver().findElement(
                By.cssSelector(".console-output")).getText().toUpperCase().contains("SUCCESS"));
    }
}


