import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CreateOrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = RandomStringUtils.randomAlphanumeric(6);
    private static final String NEW_ITEM = "New Item";
    private static final String DASHBOARD = "Dashboard";
    private static final By BY_INPUT_NAME = By.id("name");
    private static final By ORGANIZATION_FOLDER = By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By SAVE_BUTTON = By.id("yui-gen15-button");

    public void click(By button) {
        getDriver().findElement(button).click();
    }

    public void clickByLinkText(String text) {
        getDriver().findElement(By.linkText(text)).click();
    }

    public void createNewOrganizationFolder() {
        clickByLinkText(NEW_ITEM);
        getDriver().findElement(BY_INPUT_NAME).sendKeys(ORGANIZATION_FOLDER_NAME);
        click(ORGANIZATION_FOLDER);
        click(OK_BUTTON);
        click(SAVE_BUTTON);
        clickByLinkText(DASHBOARD);

        Assert.assertEquals(getDriver().findElement(By.linkText(ORGANIZATION_FOLDER_NAME)).getText(), ORGANIZATION_FOLDER_NAME);
    }

    @Test
    public void newOrganizationFolderTest() {
        createNewOrganizationFolder();
    }

}