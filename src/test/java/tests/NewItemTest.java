package tests;

import model.CreateItemErrorPage;
import model.HomePage;
import model.NewItemPage;
import model.pipeline.PipelineConfigPage;
import model.pipeline.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static runner.ProjectMethodsUtils.createNewFolder;

public class NewItemTest extends BaseTest {

    private static final String PROJECT_NAME = TestUtils.getRandomStr(7);
    private static final String ITEM_NEW_DESCRIPTION = "New description";
    private static final String SECOND_PROJECT_NAME = TestUtils.getRandomStr(7);

    @Test
    public void testNewItemsPageContainsItemsWithoutCreatedProject() {
        final List<String> expectedResult = List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        NewItemPage newItemPage = new HomePage(getDriver()).clickNewItem();

        Assert.assertEquals(newItemPage.newItemsNameList(), expectedResult);
    }

    @Test
    public void testCheckNavigationToNewItemPage() {
        NewItemPage newItemPage = new HomePage(getDriver()).clickNewItem();

        Assert.assertEquals(newItemPage.getH3HeaderText(), "Enter an item name");
    }

    @Test
    public void testCreateFolder() {

        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFolderAndClickOk()
                .clickApplyButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertEquals(homePage.getJobName(PROJECT_NAME), PROJECT_NAME);
    }

    @DataProvider(name = "typeProjectList")
    private Object[][] getTypeProjectList() {
        return Stream.<Consumer<NewItemPage>>of(
                NewItemPage::selectFreestyleProjectAndClickOk,
                NewItemPage::selectPipelineAndClickOk,
                NewItemPage::selectMultiConfigurationProjectAndClickOk,
                NewItemPage::selectFolderAndClickOk,
                NewItemPage::selectMultibranchPipelineAndClickOk,
                NewItemPage::selectOrgFolderAndClickOk
        ).map(func -> new Object[]{func}).toArray(Object[][]::new);
    }

    @Test(dataProvider = "typeProjectList")
    public void testCreateAnyItemWithSpacesOnlyNameError(Consumer<NewItemPage> typeProjectMethod) {
        typeProjectMethod.accept(
                new HomePage(getDriver())
                        .clickNewItem()
                        .setItemName("     "));

        Assert.assertEquals(
                new CreateItemErrorPage(getDriver()).getErrorMessage(),
                "No name is specified");
    }

    @Test
    public void testCreatePipelineAssertInsideJob() {
        String actualPipelineName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getNameText();

        Assert.assertEquals(actualPipelineName, "Pipeline " + PROJECT_NAME);
    }

    @Test
    public void testCreatePipelineAssertOnDashboard() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PROJECT_NAME);
        String actualPipelineName = new HomePage(getDriver())
                .getJobName(PROJECT_NAME);

        Assert.assertEquals(actualPipelineName, PROJECT_NAME);
    }

    @Test
    public void testCreatePipelineAssertOnBreadcrumbs() {
        String actualTextOnBreadcrumbs = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .getTextBreadcrumbs();

        Assert.assertTrue(actualTextOnBreadcrumbs.contains(PROJECT_NAME), PROJECT_NAME + " Pipeline Not Found On Breadcrumbs");
    }

    @Test
    public void testCreateNewPipelineWithDescription() {
        String actualPipelineDescription = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(ITEM_NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualPipelineDescription, ITEM_NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testCreateNewPipelineFromExisting() {
        String actualJobName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(SECOND_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .clickOk()
                .clickSaveButton()
                .getPipelineName();

        String actualDescription = new PipelineStatusPage(getDriver()).getDescriptionText();

        Assert.assertEquals(actualJobName, SECOND_PROJECT_NAME);
        Assert.assertEquals(actualDescription, ITEM_NEW_DESCRIPTION);
    }

    @Test
    public void testCreateItemsWithoutName() {
        int itemsListSize = new HomePage((getDriver()))
                .clickNewItem()
                .getItemsListSize();

        for (int i = 0; i < itemsListSize; i++) {
            String actualErrorMessage = new NewItemPage((getDriver()))
                    .rootMenuDashboardLinkClick()
                    .clickNewItem()
                    .setItem(i)
                    .getItemNameRequiredMsg();

            Assert.assertEquals(actualErrorMessage, "» This field cannot be empty, please enter a valid name");
        }
    }

    @Test
    public void testCreateNewItemWithUnsafeCharacterName() {
        final String nameNewItem = "5%^PiPl$^Ne)";
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameNewItem)
                .getItemNameInvalidMsg();

        Assert.assertEquals(errorMessage, "» ‘%’ is an unsafe character");
    }

    @Test
    public void testCreateNewItemTypePipelineWithEmptyName() {
        final String nameNewItemTypePipeline = "";
        PipelineConfigPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameNewItemTypePipeline)
                .selectPipelineAndClickOk();

        Assert.assertEquals(new NewItemPage(getDriver()).getItemNameRequiredMsg(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineExistingNameError() {
        String newItemPageErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .clickDashboard()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipeline()
                .getItemNameInvalidMsg();

        Assert.assertEquals(newItemPageErrorMessage, (String.format("» A job already exists with the name ‘%s’", PROJECT_NAME)));
    }

    @Test
    public void testCreateNewItemFromOtherNonExistingName() {
        createNewFolder(getDriver(), PROJECT_NAME);
        final String jobName = TestUtils.getRandomStr(7);

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(jobName)
                .selectPipeline()
                .setCopyFrom(jobName)
                .clickOkButton()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "No such job: " + jobName);
    }
}
