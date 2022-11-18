import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class OrganizationFolderTest extends BaseTest {

    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");
    private static final By ORGANIZATION_FOLDER = By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");
    private static final By APPLY_BUTTON = By.id("yui-gen13-button");

    public WebElement getInputName() {
        return getDriver().findElement(INPUT_NAME);
    }
    public WebElement getOrganizationFolder() {
        return getDriver().findElement(ORGANIZATION_FOLDER);
    }
    public WebElement getOkButton() {
        return getDriver().findElement(OK_BUTTON);
    }
    public WebElement getDashboard() {
        return getDriver().findElement(DASHBOARD);
    }
    public WebElement getApplyButton() {
        return getDriver().findElement(APPLY_BUTTON);
    }

    @Test
    public void testCreateOrganizationFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("First Organization Folder");
        getOrganizationFolder().click();
        getOkButton().click();
        getApplyButton().click();
        getDashboard().click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href ='job/First%20Organization%20Folder/']"))
                .getText(), "First Organization Folder");
    }

    @Test
    public void testCreateOrganizationFolderWithCheckOnDashbord() {
        final String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 100);
        boolean actualResult = false;

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getInputName().sendKeys(organizationFolderName);
        getOkButton().click();
        getDriver().findElement(By.xpath("//li[@class='item']/a[@href='/']")).click();
        List<WebElement> list = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));

        Assert.assertTrue(list.size()>0);

        for (WebElement a : list){
           if(a.getText().equals(organizationFolderName)){
                actualResult = true;
                break;
           }
        }

        Assert.assertTrue(actualResult);
    }
}
