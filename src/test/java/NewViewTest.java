import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewViewTest extends BaseTest {

    private static final String PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final By NEW_ITEM = By.linkText("New Item");
    private static final By ITEM_NAME = By.id("name");
    private static final By PIPELINE = By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]/label/span");
    private static final By BUTTON_OK = By.id("ok-button");
    private static final By BUTTON_SAVE = By.id("yui-gen6-button");
    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By MY_VIEWS = By.xpath("//*[@id='tasks']/div[5]/span/a/span[2]");
    private static final By ADD_TAB = By.className("addTab");
    private static final By VIEW_NAME_FIELD = By.id("name");
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final By RADIO_BUTTON_MY_VIEW = By.xpath("//*[@id='createItemForm']/div[1]/div[2]/fieldset/div[3]/label");
    private static final By BUTTON_CREATE = By.id("ok");


    private void createPipelineProject() {
        getDriver().findElement(NEW_ITEM);
        getDriver().findElement(ITEM_NAME).sendKeys(PIPELINE_NAME);
        getDriver().findElement((PIPELINE)).click();
        getDriver().findElement(BUTTON_OK);
        getDriver().findElement(BUTTON_SAVE).click();

        getDriver().findElement(JENKINS_ICON).click();
    }

    private void deletePipelineProject(){

    }

    private void deleteNewView(){

    }

    @Test
    public void testCreateNewView(){

    }
}
