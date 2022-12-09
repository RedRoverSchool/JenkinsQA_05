import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreestyleProjectSecondTest extends BaseTest {
    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String DESCRIPTION_TEXT = RandomStringUtils.randomAlphanumeric(20);
    private static final String VALID_NAME = "New project(1.1)";

    @Test
    public void testCreateFreestyleProject(){

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getWait(10).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@class='jenkins-input']")))
                .sendKeys(FREESTYLE_NAME);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//li/a[@href='/job/" + FREESTYLE_NAME + "/']"))
                .getText(), FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testCreateAndRenameFreestyleProject(){

        getDriver().findElement(By.xpath("//td/a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + FREESTYLE_NAME + "/confirm-rename']")).click();
        WebElement newName = getDriver().findElement(By.xpath("//div/input[@checkdependson='newName']"));
        newName.clear();
        newName.sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(By.xpath("//span/button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//li/a[@href='/job/" + NEW_FREESTYLE_NAME + "/']"))
                .getText(), NEW_FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateAndRenameFreestyleProject")
    public void testCreateWithDescriptionFreestyleProject(){

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getWait(10).until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div/textarea[@name='description']")))
                .sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.xpath("//span/button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                DESCRIPTION_TEXT);
    }

    @Test
    public void testCreateFreestyleProjectWithValidName(){

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(VALID_NAME);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                "Project " + VALID_NAME);
    }

    @Test(dependsOnMethods = "testCreateWithDescriptionFreestyleProject")
    public void testConfigurationProvideDiscardOldBuildsWithDaysToKeepBuilds() {
        final String expectedDaysToKeepBuilds = Integer.toString((int)(Math.random() * 20 + 1));
        String actualDaysToKeepBuilds;

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"))
                .click();
        getDriver().findElement(By.xpath("//span/label[text()='Discard old builds']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.daysToKeepStr']"))
                .sendKeys(expectedDaysToKeepBuilds);
        getDriver().findElement(By.xpath("//span/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"))
                .click();
        actualDaysToKeepBuilds = getDriver().findElement(By.xpath("//input[@name='_.daysToKeepStr']"))
                .getAttribute("value");

        Assert.assertEquals(actualDaysToKeepBuilds,expectedDaysToKeepBuilds);
    }

    @Test(dependsOnMethods = "testConfigurationProvideDiscardOldBuildsWithDaysToKeepBuilds")
    public void testConfigurationProvideKeepMaxNumberOfOldBuilds() {
        final String expectedMaxNumberToKeepBuilds = Integer.toString((int)(Math.random() * 20 + 1));
        String actualMaxNumberToKeepBuilds;

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"))
                .click();
        getDriver().findElement(By.xpath("//input[@name='_.numToKeepStr']"))
                .sendKeys(expectedMaxNumberToKeepBuilds);
        getDriver().findElement(By.xpath("//span/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"))
                .click();
        actualMaxNumberToKeepBuilds = getDriver().findElement(By.xpath("//input[@name='_.numToKeepStr']"))
                .getAttribute("value");

        Assert.assertEquals(actualMaxNumberToKeepBuilds,expectedMaxNumberToKeepBuilds);
    }

    @Test(dependsOnMethods = "testConfigurationProvideKeepMaxNumberOfOldBuilds")
    public void testVerifyOptionsInBuildStepsSection() throws InterruptedException {

        final Set<String> expectedOptions = new HashSet<>(List.of("Execute Windows batch command", "Execute shell",
                "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets", "Run with timeout",
                "Set build status to \"pending\" on GitHub commit"));
        Set<String> actualOptions = new HashSet<>();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"))
                .click();
        getDriver().findElement(By.xpath("//button[@data-section-id='build-steps']")).click();
        getWait(10).until(TestUtils
                .ExpectedConditions.elementIsNotMoving(By.xpath("//button[text()='Add build step']")));
        getDriver().findElement(By.xpath("//button[text()='Add build step']")).click();
        List<WebElement> listOfOptions = getDriver()
                .findElements(By.xpath("//button[text()='Add build step']/../../..//a[@href='#']"));

        for (WebElement element : listOfOptions) {
            actualOptions.add(element.getText());
        }

        getWait(10).until(TestUtils
                .ExpectedConditions.elementIsNotMoving(By.xpath("//button[text()='Add build step']")));
        getDriver().findElement(By.xpath("//button[text()='Add build step']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(actualOptions, expectedOptions);
    }

    @Ignore
    @Test(dependsOnMethods = "testVerifyOptionsInBuildStepsSection")
    public void testSelectBuildPeriodicallyCheckbox() {
        boolean selectedCheckbox;

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"))
                .click();
        getDriver().findElement(By.xpath("//button[@data-section-id='build-triggers']")).click();
        getWait(10).until(TestUtils.
                ExpectedConditions.elementIsNotMoving(By.xpath("//label[text()='Build periodically']")));
        getDriver().findElement(By.xpath("//label[text()='Build periodically']")).click();

        selectedCheckbox = getDriver().findElement(By.name("hudson-triggers-TimerTrigger")).isSelected();

        getWait(10).until(TestUtils.
                ExpectedConditions.elementIsNotMoving(By.xpath("//label[text()='Build periodically']")));
        getDriver().findElement(By.xpath("//label[text()='Build periodically']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(selectedCheckbox);
    }
}
