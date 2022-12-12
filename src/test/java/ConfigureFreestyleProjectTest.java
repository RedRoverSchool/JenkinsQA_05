import model.BuildWithParametersPage;
import model.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class ConfigureFreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = TestUtils.getRandomStr();
    private static final By SAVE_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final By CONFIGURE_BUTTON = By.linkText("Configure");
    private static final By GO_TO_DASHBOARD_BUTTON = By.linkText("Dashboard");

    private List<WebElement> getJobSpecifications(String nameJob) {
        return getDriver().findElements(By.xpath("//tr[@id = 'job_" + nameJob + "']/td"));
    }

    private String getBuildStatus(){
        return getDriver().findElement(By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']"))
                .getAttribute("tooltip");
    }

    @Test
    public void testConfigureJobAsParameterized() throws InterruptedException {
        final String freestyleName = FREESTYLE_NAME;
        final String stringParameterName = "Held post";
        final String stringParameterDefaultValue = "Manager";
        final String choiceParameterName = "Employee_name";
        final String choiceParameterValues = "John Smith\nJane Dow";
        final String booleanParameterName = "Employed";
        final String pageNotification = "This build requires parameters:";

        BuildWithParametersPage page = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(freestyleName)
                .selectFreestyleProjectAndClickOk()
                .clickCheckBoxThisProjectIsParametrized()
                .clickButtonAddParameter()
                .selectStringParameter()
                .inputStringParameterName(stringParameterName)
                .inputStringParameterDefaultValue(stringParameterDefaultValue)
                .scrollAndClickAddParameterButton()
                .selectChoiceParameter()
                .inputChoiceParameterName(choiceParameterName)
                .inputChoiceParameterValue(choiceParameterValues)
                .scrollAndClickAddParameterButton()
                .selectBooleanParameter()
                .inputBooleanParameterName(booleanParameterName)
                .switchONBooleanParameterAsDefault()
                .clickSaveButton()
                .clickButtonBuildWithParameters();

        Assert.assertEquals(page.getProjectName(), freestyleName);
        Assert.assertEquals(page.getPageNotificationText(), pageNotification);
        Assert.assertEquals(page.getFirstParamName(), stringParameterName);
        Assert.assertEquals(page.getFirstParamValue(), stringParameterDefaultValue);
        Assert.assertEquals(page.getSecondParamName(), choiceParameterName);
        Assert.assertEquals(page.selectedParametersValues(), choiceParameterValues);
        Assert.assertEquals(page.getThirdParamName(), booleanParameterName);
        Assert.assertTrue(page.isBooleanParameterDefaultOn());
    }

    @Ignore
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
