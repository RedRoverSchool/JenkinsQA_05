import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CreateOrganizationFolderTest extends BaseTest {

    @Test
    public void createOrgFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Organization Folder");
        getDriver().findElement(By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        Assert.assertEquals(getDriver().
                        findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                "Organization Folder");
    }

    @Test
    public void createOrgFolderEmptyName() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']")).click();
        Assert.assertEquals(getDriver().
                        findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void createOrgFolderExistName() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Organization Folder");
        Assert.assertEquals(getDriver().
                        findElement(By.id("itemname-invalid")).getText(),
                "» A job already exists with the name ‘Organization Folder’");
    }
}

