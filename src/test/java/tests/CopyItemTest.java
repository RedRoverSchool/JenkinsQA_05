package tests;

import static runner.TestUtils.getRandomStr;

import java.time.Duration;

import model.CreateItemErrorPage;
import model.HomePage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

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
                .clickJenkinsHeadIcon()
                .clickNewItem()
                .setItemName(nameFreestyleProject)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard();
    }
}
