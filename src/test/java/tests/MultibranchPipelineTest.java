package tests;

import model.*;
import model.multibranch_pipeline.MultibranchPipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;
import java.util.List;

public class MultibranchPipelineTest extends BaseTest {
    private static final String MULTIBRANCH_PIPELINE_NAME = TestUtils.getRandomStr();
    private static final String MULTIBRANCH_PIPELINE_NAME_RENAMED = TestUtils.getRandomStr();

    @Test
    public void testCreateMultibranchPipeline() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveBtn(MultibranchPipelineStatusPage.class)
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testCreateMbPipelineEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultibranchPipeline();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateMultibranchPipelineInvalidName() {
        NewItemPage newItemPage = new NewItemPage(getDriver())
                .clickNewItem()
                .setItemName("MultibranchPipeline@")
                .selectMultibranchPipeline();

        Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), "» ‘@’ is an unsafe character");

        CreateItemErrorPage createItemErrorPage = newItemPage.clickOkButton();

        Assert.assertEquals(createItemErrorPage.getCurrentURL(), "http://localhost:8080/view/all/createItem");
        Assert.assertEquals(createItemErrorPage.getErrorHeader(),
                "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "‘@’ is an unsafe character");
    }

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'!'},{'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'$'}, {'<'}, {'>'},};
    }

    @Test(dataProvider = "specialCharacters")
    public void testCreateMultibranchPipelineUnsafeCharacter(Character unsafeCharacter) {
        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(unsafeCharacter.toString())
                .selectMultibranchPipeline()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualErrorMessage, String.format("» ‘%s’ is an unsafe character",unsafeCharacter));
    }

    @Test
    public void testCreateMultibranchPipelineWithExistingName() {
        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveBtn(MultibranchPipelineStatusPage.class)
                .clickDashboard()
                .clickNewItem()
                .setItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualErrorMessage, String.format("» A job already exists with the name ‘%s’", MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testRenameMultiBranchPipelineFromDropdown() {
        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveBtn(MultibranchPipelineStatusPage.class)
                .clickDashboard()
                .clickJobDropDownMenu(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameMultibranchPipelineDropDownMenu()
                .clearFieldAndInputNewName(MULTIBRANCH_PIPELINE_NAME_RENAMED)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(MULTIBRANCH_PIPELINE_NAME_RENAMED));
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testRenameMultiBranchPipelineFromLeftSideMenu() {
        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobMBPipeline(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(MULTIBRANCH_PIPELINE_NAME_RENAMED)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(MULTIBRANCH_PIPELINE_NAME_RENAMED));

                List <String> jobsList = new HomePage(getDriver())
                        .clickDashboard()
                        .getJobNamesList();

        Assert.assertTrue(jobsList.contains(MULTIBRANCH_PIPELINE_NAME_RENAMED));
    }
}
