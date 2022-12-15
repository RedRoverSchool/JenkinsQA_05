import model.FreeStyleProjectMenuPage;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import static runner.TestUtils.getRandomStr;

public class NewFreeStyleProjectTest extends BaseTest {
    private String projectName;
    private String renameNewName;

    @Test
    public void createFreestyleProjectTest() throws InterruptedException {
        projectName = getRandomStr(5);
        final String descriptionText = "Some text to description area";
        final String timeTriggerText = "H H 1,15 1-11 *";

        new HomePage(getDriver())
                .clickNewItem()
                .typeName(projectName)
                .selectFreeStyleProjectAndClickOk()
                .sendTextToDescriptionField(descriptionText)
                .clickGithubCheckbox()
                .clickGithubCheckbox()
                .clickGitInfoQuestMark()
                .clickTimeTriggerCheckbox()
                .sendTextToTimeTriggerField(timeTriggerText)
                .clickAntCheckbox()
                .clickSaveButton()
                .clickDashboardButton();

        String actualName = new HomePage(getDriver()).getItemName();
        Assert.assertTrue(actualName.contains(projectName));
    }

    @Test(dependsOnMethods = {"createFreestyleProjectTest"})
    public void renameFreeStyleProjectTest() {
        renameNewName = getRandomStr(5);

        new HomePage(getDriver())
                .clickJobExists(projectName)
                .clickConfirmRename(projectName)
                .clearRenameField()
                .sendTextToRenameField(renameNewName)
                .clickConfirmButton()
                .clickDashboard();

        String actualName = new HomePage(getDriver()).getItemName();
        Assert.assertTrue(actualName.contains(renameNewName));
    }

    @Test(dependsOnMethods = {"renameFreeStyleProjectTest"})
    public void renameFreeStyleProjectNegativeTest() {
        final String expectedPhrase = "The new name is the same as the current name.";

        new HomePage(getDriver())
                .clickJobExists(renameNewName)
                .clickConfirmRename(renameNewName)
                .clickConfirmButton();

        String actualPhrase = new FreeStyleProjectMenuPage(getDriver()).getErrorText();
        Assert.assertEquals(actualPhrase, expectedPhrase);
    }

    @Test(dependsOnMethods = {"renameFreeStyleProjectNegativeTest"})
    public void deleteFreestyleProjectTest() {
        final String expectedText = "Start building your software project";

        new HomePage(getDriver())
                .clickJobExists(renameNewName)
                .clickDeleteItem()
                .clickAcceptAlert();

        String actualText = new HomePage(getDriver()).getHomePageWelcomeText();
        Assert.assertTrue(actualText.contains(expectedText));
    }
}
