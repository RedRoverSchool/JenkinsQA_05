import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class BuildHistoryTest extends BaseTest {

    private static final By BUILD_NOW_BTN = By.xpath("//body[1]/div[3]/div[1]/div[1]/div[5]/span[1]");
    private static final By ICON_SIZE = By.xpath("//a[@class='jenkins-table__button']//*[name()='svg']");
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By INPUT_NAME = By.name("name");
    private static final String BASE_URL = "http://localhost:8080/";
    private static final By FREESTYLE_PROJECT = By.xpath("//li[@class='hudson_model_FreeStyleProject']");
    private static final By OK_BUTTON = By.xpath("//span[@class='yui-button primary large-button']");
    private static final By DESCRIPTION_FIELD = By.name("description");
    private static final By SAVE_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final By A_UL_BREADCRUMBS = By.xpath("//ul[@id='breadcrumbs']//a");
    private static final By BUILD_HISTORY = By.linkText("Build History");
    private static final By H1_HEADER_BULD_HISTORY = By.xpath("//div[@class='jenkins-app-bar__content']/h1");

    private void inputName(By by) {
        getDriver().findElement(by).sendKeys(TestUtils.getRandomStr(8));
    }

    private void clickElement(By by) {
        getDriver().findElement(by).click();
    }

    private String getText(By by) {

        return getDriver().findElement(by).getText();
    }



    @Test
    public void testVerifyRedirectToMainPage() {
        getDriver().findElement(
                By.linkText("Build History")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description-link")).getText(),
                "Add description");
    }

    @Test
    public void testVerifyDefaultIconSize() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(TestUtils.getRandomStr(8));
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(BUILD_NOW_BTN).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.linkText("Build History")).click();

        int width = getDriver().findElement(ICON_SIZE).getSize().getWidth();
        int height = getDriver().findElement(ICON_SIZE).getSize().getHeight();
        String size = getDriver().findElement(By.className("jenkins-icon-size__items-item")).getText();

        switch (size) {
            case "S\nmall":
                Assert.assertEquals(width, 16);
                Assert.assertEquals(height, 16);
                break;
            case "M\nedium":
                Assert.assertEquals(width, 20);
                Assert.assertEquals(height, 20);
                break;
            case "L\narge":
                Assert.assertEquals(width, 24);
                Assert.assertEquals(height, 24);
                break;
        }
    }

    @Test
    public void testH1Header_BuildHistory() {
        clickElement(NEW_ITEM);
        clickElement(INPUT_NAME);
        inputName(INPUT_NAME);
        clickElement(FREESTYLE_PROJECT);
        clickElement(OK_BUTTON);
        inputName(DESCRIPTION_FIELD);
        clickElement(SAVE_BUTTON);
        clickElement(A_UL_BREADCRUMBS);
        clickElement(BUILD_HISTORY);

        Assert.assertEquals(getText(H1_HEADER_BULD_HISTORY), "Build History of Jenkins");
    }


}
