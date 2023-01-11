package tests;

import static runner.TestUtils.getRandomStr;

import model.CreateItemErrorPage;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CopyItemTest extends BaseTest {

    @Test
    public void testCopyFromNotExistItemName() {
        final String nameItem = getRandomStr();
        final String nameNotExistItem = getRandomStr();
        final String nameExistItem = getRandomStr();
        final String expectedErrorMessage = "Error No such job: " + nameNotExistItem;

        createFreestyleProject(nameExistItem);

        CreateItemErrorPage createItemErrorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameItem)
                .selectFreestyleProject()
                .setCopyFrom(nameNotExistItem)
                .clickOKCreateItemErrorPage();

        String actualErrorMessage = createItemErrorPage.getErrorHeader() +
                " " + createItemErrorPage.getErrorMessage();
        boolean isItemAtTheDashboard = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobListAsString()
                .contains(nameItem);

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        Assert.assertFalse(isItemAtTheDashboard, "Item " + nameItem + " at the Dashboard");
    }

    @Test
    public void testFieldCopyFromDoNotDisplayIfDoNotHaveAnyItems() {
        boolean isDisplayedFieldFrom = new HomePage(getDriver())
                .clickNewItem()
                .isDisplayedFieldCopyFrom();

        Assert.assertFalse(isDisplayedFieldFrom, "Field Copy from is displayed");
    }

    private HomePage createFreestyleProject(String nameFreestyleProject) {
        return new HomePage(getDriver())
                .getHeader()
                .clickJenkinsHeadIcon()
                .clickNewItem()
                .setItemName(nameFreestyleProject)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();
    }
}
