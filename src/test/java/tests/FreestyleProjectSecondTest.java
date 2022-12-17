package tests;

import model.freestyle.FreestyleProjectConfigPage;
import model.HomePage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreestyleProjectSecondTest extends BaseTest {
    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String DESCRIPTION_TEXT = RandomStringUtils.randomAlphanumeric(20);
    private static final String VALID_NAME = "New project(1.1)";

    @Test
    public void testCreateFreestyleProject() {

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .getFreestyleProjectName(FREESTYLE_NAME);

        Assert.assertEquals(projectName, FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testCreateAndRenameFreestyleProject() {

        String projectName = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickRenameButton()
                .clearFieldAndInputNewName(NEW_FREESTYLE_NAME)
                .clickSubmitButton()
                .getFreestyleProjectName(NEW_FREESTYLE_NAME);

        Assert.assertEquals(projectName, NEW_FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateAndRenameFreestyleProject")
    public void testCreateWithDescriptionFreestyleProject() {

        String description = new HomePage(getDriver())
                .clickFreestyleProjectName(NEW_FREESTYLE_NAME)
                .clickButtonAddDescription()
                .inputAndSaveDescriptionText(DESCRIPTION_TEXT)
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION_TEXT);
    }

    @Test
    public void testCreateFreestyleProjectWithValidName() {

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
        final String expectedDaysToKeepBuilds = Integer.toString((int) (Math.random() * 20 + 1));

        String actualDaysToKeepBuilds = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigureLink()
                .clickDiscardOldBuildsCheckbox()
                .typeDaysToKeepBuilds(expectedDaysToKeepBuilds)
                .clickSaveBtn()
                .clickSideMenuConfigureLink()
                .getNumberOfDaysToKeepBuilds();

        Assert.assertEquals(actualDaysToKeepBuilds, expectedDaysToKeepBuilds);
    }

    @Test(dependsOnMethods = "testConfigurationProvideDiscardOldBuildsWithDaysToKeepBuilds")
    public void testConfigurationProvideKeepMaxNumberOfOldBuilds() {
        final String expectedMaxNumberOfBuildsToKeep = Integer.toString((int) (Math.random() * 20 + 1));

        String actualMaxNumberOfBuildsToKeep = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigureLink()
                .typeMaxNumberOfBuildsToKeep(expectedMaxNumberOfBuildsToKeep)
                .clickSaveBtn()
                .clickSideMenuConfigureLink()
                .getMaxNumberOfBuildsToKeep();

        Assert.assertEquals(actualMaxNumberOfBuildsToKeep, expectedMaxNumberOfBuildsToKeep);
    }

    @Test(dependsOnMethods = "testConfigurationProvideKeepMaxNumberOfOldBuilds")
    public void testVerifyOptionsInBuildStepsSection() {
        final Set<String> expectedOptionsInBuildStepsSection = new HashSet<>(List.of("Execute Windows batch command",
                "Execute shell", "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets",
                "Run with timeout", "Set build status to \"pending\" on GitHub commit"));

        Set<String> actualOptionsInBuildStepsSection = new HomePage(getDriver())
                .clickFreestyleProjectName(NEW_FREESTYLE_NAME)
                .clickSideMenuConfigureLink()
                .clickBuildStepsSideMenuOption()
                .openAddBuildStepDropDown()
                .collectOptionsInBuildStepsDropDown();

        new FreestyleProjectConfigPage(getDriver())
                .closeAddBuildStepDropDown()
                .clickSaveBtn();

        Assert.assertEquals(actualOptionsInBuildStepsSection, expectedOptionsInBuildStepsSection);
    }

    @Test(dependsOnMethods = "testVerifyOptionsInBuildStepsSection")
    public void testSelectBuildPeriodicallyCheckbox() {

        boolean selectedCheckbox = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigureLink()
                .clickBuildTriggersSideMenuOption()
                .scrollAndClickBuildPeriodicallyCheckbox()
                .verifyThatBuildPeriodicallyCheckboxIsSelected();

        new FreestyleProjectConfigPage(getDriver())
                .uncheckBuildPeriodicallyCheckbox()
                .clickSaveBtn();

        Assert.assertTrue(selectedCheckbox);
    }
}
