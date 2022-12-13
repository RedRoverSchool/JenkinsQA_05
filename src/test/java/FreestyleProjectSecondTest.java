import model.HomePage;
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

import static runner.TestUtils.scrollToElement_PlaceInCenter;

public class FreestyleProjectSecondTest extends BaseTest {
    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String DESCRIPTION_TEXT = RandomStringUtils.randomAlphanumeric(20);
    private static final String VALID_NAME = "New project(1.1)";

    @Test
    public void testCreateFreestyleProject(){

       String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .getFreestyleProjectName(FREESTYLE_NAME);

        Assert.assertEquals(projectName, FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testCreateAndRenameFreestyleProject(){

        String projectName = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickRenameButton()
                .clearFieldAndInputNewName(NEW_FREESTYLE_NAME)
                .clickSubmitButton()
                .getFreestyleProjectName(NEW_FREESTYLE_NAME);

        Assert.assertEquals(projectName, NEW_FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateAndRenameFreestyleProject")
    public void testCreateWithDescriptionFreestyleProject(){

        String description = new HomePage(getDriver())
                .clickFreestyleProjectName(NEW_FREESTYLE_NAME)
                .clickButtonAddDescription()
                .inputAndSaveDescriptionText(DESCRIPTION_TEXT)
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION_TEXT);
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

        String actualDaysToKeepBuilds = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigureLink()
                .clickDiscardOldBuildsCheckbox()
                .typeDaysToKeepBuilds(expectedDaysToKeepBuilds)
                .clickSaveBtn()
                .clickSideMenuConfigureLink()
                .getNumberOfDaysToKeepBuilds();

        Assert.assertEquals(actualDaysToKeepBuilds,expectedDaysToKeepBuilds);
    }

    @Test(dependsOnMethods = "testConfigurationProvideDiscardOldBuildsWithDaysToKeepBuilds")
    public void testConfigurationProvideKeepMaxNumberOfOldBuilds() {
        final String expectedMaxNumberOfBuildsToKeep= Integer.toString((int)(Math.random() * 20 + 1));

        String actualMaxNumberOfBuildsToKeep = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigureLink()
                .typeMaxNumberOfBuildsToKeep(expectedMaxNumberOfBuildsToKeep)
                .clickSaveBtn()
                .clickSideMenuConfigureLink()
                .getMaxNumberOfBuildsToKeep();

        Assert.assertEquals(actualMaxNumberOfBuildsToKeep,expectedMaxNumberOfBuildsToKeep);
    }

    @Ignore
    @Test(dependsOnMethods = "testConfigurationProvideKeepMaxNumberOfOldBuilds")
    public void testVerifyOptionsInBuildStepsSection() {

        final Set<String> expectedOptions = new HashSet<>(List.of("Execute Windows batch command", "Execute shell",
                "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets", "Run with timeout",
                "Set build status to \"pending\" on GitHub commit"));
        Set<String> actualOptions = new HashSet<>();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"))
                .click();
        getDriver().findElement(By.xpath("//button[@data-section-id='build-steps']")).click();

        scrollToElement_PlaceInCenter(getDriver(),
                getDriver().findElement(By.xpath("//button[text()='Add build step']")));
        getWait(10).until(TestUtils
                .ExpectedConditions.elementIsNotMoving(By.xpath("//button[text()='Add build step']"))).click();
        List<WebElement> listOfOptions = getDriver()
                .findElements(By.xpath("//button[text()='Add build step']/../../..//a[@href='#']"));

        for (WebElement element : listOfOptions) {
            actualOptions.add(element.getText());
        }

        getWait(10).until(TestUtils
                .ExpectedConditions.elementIsNotMoving(By.xpath("//button[text()='Add build step']"))).click();
        getWait(10).until(TestUtils
                .ExpectedConditions.elementIsNotMoving(By.xpath("//button[@type='submit']"))).click();

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

        scrollToElement_PlaceInCenter(getDriver(),
                getDriver().findElement(By.xpath("//label[text()='Build periodically']")));
        getWait(10).until(TestUtils.
                ExpectedConditions.elementIsNotMoving(By.xpath("//label[text()='Build periodically']"))).click();

        selectedCheckbox = getWait(10).until(ExpectedConditions
                .elementSelectionStateToBe(By.name("hudson-triggers-TimerTrigger"),true));

        getWait(10).until(TestUtils.
                ExpectedConditions.elementIsNotMoving(By.xpath("//label[text()='Build periodically']"))).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(selectedCheckbox);
    }
}
