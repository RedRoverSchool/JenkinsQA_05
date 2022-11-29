import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class ConfigureFreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = TestUtils.getRandomStr();
    private static final By CREATE_NEW_ITEM_BUTTON = By.linkText("New Item");
    private static final By INPUT_NEW_ITEM_NAME_FIELD = By.id("name");
    private static final By SELECT_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By OK_BUTTON = By.cssSelector("#ok-button");
    private static final By ADD_PARAMETER_BUTTON = By.xpath("//button[text() = 'Add Parameter']");
    private static final By SAVE_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final By CONFIGURE_BUTTON = By.linkText("Configure");
    private static final By GO_TO_DASHBOARD_BUTTON = By.linkText("Dashboard");

    private void scrollAndClickAddParameterButton() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(ADD_PARAMETER_BUTTON));
        Thread.sleep(300);
        getDriver().findElement(ADD_PARAMETER_BUTTON).click();
    }

    private List<WebElement> getJobSpecifications(String nameJob) {
        return getDriver().findElements(By.xpath("//tr[@id = 'job_" + nameJob + "']/td"));
    }

    private String getBuildStatus(){
        return getDriver().findElement(By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']"))
                .getAttribute("tooltip");
    }

    @Test
    public void testConfigureJobAsParameterized() throws InterruptedException {
        final String stringParameterName = "Held post";
        final String stringParameterDefaultValue = "Manager";
        final String choiceParameterName = "Employee_name";
        final String choiceParameterValues = "John Smith\nJane Dow";
        final String booleanParameterName = "Employed";

        getDriver().findElement(CREATE_NEW_ITEM_BUTTON).click();
        getDriver().findElement(INPUT_NEW_ITEM_NAME_FIELD).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(By.xpath("//label[text() = 'This project is parameterized']")).click();

        getDriver().findElement(ADD_PARAMETER_BUTTON).click();
        getDriver().findElement(By.xpath("//a[text() = 'String Parameter']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'parameter.name']")).sendKeys(stringParameterName);
        getDriver().findElement(By.xpath("//input[@name = 'parameter.defaultValue']"))
                .sendKeys(stringParameterDefaultValue);

        scrollAndClickAddParameterButton();
        getDriver().findElement(By.xpath("//a[text() = 'Choice Parameter']")).click();
        getDriver().findElement(By
            .xpath("//div[@class = 'jenkins-form-item hetero-list-container with-drag-drop  ']/div[2]//input[@name = 'parameter.name']"))
            .sendKeys(choiceParameterName);
        getDriver().findElement(By.xpath("//textarea[@name= 'parameter.choices']")).sendKeys(choiceParameterValues);

        scrollAndClickAddParameterButton();
        getDriver().findElement(By.xpath("//a[text() = 'Boolean Parameter']")).click();
        getDriver().findElement(By
                        .xpath("//div[@class = 'jenkins-form-item hetero-list-container with-drag-drop  ']/div[3]//input[@name = 'parameter.name']"))
                .sendKeys(booleanParameterName);
        getDriver().findElement(By.xpath("//label[text() = 'Set by Default']")).click();

        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(By.linkText("Build with Parameters")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'main-panel']/p")).getText(),
                "This build requires parameters:");

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class= 'parameters']/div[1]//input[1]"))
                        .getAttribute("value"),
                stringParameterName);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class= 'parameters']/div[1]//input[2]"))
                        .getAttribute("value"),
                stringParameterDefaultValue);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class= 'parameters']/div[2]//input"))
                        .getAttribute("value"),
                choiceParameterName);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class= 'parameters']/div[3]//input[@name = 'name']"))
                        .getAttribute("value"),
                booleanParameterName);

        Assert.assertEquals(getDriver().findElement(By.xpath("//select[@name= 'value']")).getText(),
                choiceParameterValues);

        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@type= 'checkbox']")).isSelected());
    }

    @Test(dependsOnMethods = "testConfigureJobAsParameterized")
    public void testConfigureSourceCodeGIT() {
        final String repositoryURL = "https://github.com/AlekseiChapaev/TestingJenkinsRepo.git";
        final By fieldInputRepositoryURL =
                By.xpath("//div[@name= 'userRemoteConfigs']/div[@style]/div[1][@class = 'jenkins-form-item tr  has-help']/div[2]/input");

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(CONFIGURE_BUTTON).click();
        getDriver().findElement(By.xpath("//label[text() = 'This project is parameterized']")).click();
        getDriver().findElement(By.xpath("//button[@data-section-id= 'source-code-management']")).click();
        getDriver().findElement(By.xpath("//label[text() = 'Git']")).click();

        getDriver().findElement(fieldInputRepositoryURL).sendKeys(repositoryURL);
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();
        getJobSpecifications(FREESTYLE_NAME).get(6).click();

        while(getJobSpecifications(FREESTYLE_NAME).get(5).getText().equals("N/A")){
            getDriver().navigate().refresh();
        }

        Assert.assertEquals(getBuildStatus(), "Success");
        Assert.assertTrue(getJobSpecifications(FREESTYLE_NAME).get(3).getText().contains("#1"));
    }
}
