package tests;

import model.CreateItemErrorPage;
import model.HomePage;
import model.NewItemPage;
import model.pipeline.PipelineConfigPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class NewItemTest extends BaseTest {

    private static final String PROJECT_NAME = TestUtils.getRandomStr(7);

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
    public void testCreateNewItemWithEmptyName() {
        final String nameNewItemTypePipeline = "";
        PipelineConfigPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameNewItemTypePipeline)
                .selectPipelineAndClickOk();

        Assert.assertEquals(new NewItemPage(getDriver()).getItemNameRequiredMsg(), "» This field cannot be empty, please enter a valid name");
    }


    @Test(dependsOnMethods = "testCreateFolder")
    public void testCreateNewItemFromOtherNonExistingName() {
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
