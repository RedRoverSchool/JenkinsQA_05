import model.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class ListViewTest extends BaseTest {

    private static final By OK_BUTTON = By.cssSelector("#yui-gen6-button");
    private static final By DESCRIPTION_AREA = By.xpath("//textarea[@name='description']");
    private static final By DESCRIPTION = By.xpath(
            "//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div");
    private static final By EDIT_VIEW_MENU = By.linkText("Edit View");
    private static final String RANDOM_LIST_VIEW_NAME = TestUtils.getRandomStr();
    private static final By CREATED_LIST_VIEW = By.xpath("//a[@href='/view/" + RANDOM_LIST_VIEW_NAME + "/']");


    private List<String> getListFromWebElements(List<WebElement> elements) {
        List<String> list = new ArrayList<>();
        for (WebElement element : elements) {
            list.add(element.getText());
        }

        return list;
    }

    @Test
    public void testCreateNewListViewWithExistingJob() {
        final String projectOne = TestUtils.getRandomStr();

        int quantityProjectsInListView = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(projectOne)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()
                .clickAddViewLink()
                .setViewName(RANDOM_LIST_VIEW_NAME)
                .setListViewType()
                .clickCreateListView()
                .addJobToView(projectOne)
                .clickOk()
                .getJobList().size();

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(new HomePage(getDriver()).getViewList().contains(RANDOM_LIST_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithExistingJob")
    public void testEditViewAddDescription() {
        final String descriptionRandom = TestUtils.getRandomStr();

        String actualDescription = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickEditViewButton()
                .addDescription(descriptionRandom)
                .clickOk()
                .getTextDescription();

        Assert.assertEquals(actualDescription, descriptionRandom);
    }

    @Test(dependsOnMethods = "testEditViewAddDescription")
    public void testEditViewDeleteDescription() {

        getDriver().findElement(CREATED_LIST_VIEW).click();
        getDriver().findElement(By.cssSelector("#description-link")).click();
        getWait(5)
                .until(TestUtils.ExpectedConditions.elementIsNotMoving(
                        getDriver().findElement(DESCRIPTION_AREA))).clear();
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), "");
    }

    @Test(dependsOnMethods = "testEditViewDeleteDescription")
    public void testDeleteListView() {

        getDriver().findElement(CREATED_LIST_VIEW).click();
        getDriver().findElement(By.linkText("Delete View")).click();
        getDriver().findElement(By.cssSelector("#yui-gen1")).click();

        List<String> listViews = getListFromWebElements(getDriver().findElements(
                By.xpath("//div[@class='tabBar']/div")));

        Assert.assertFalse(listViews.contains(RANDOM_LIST_VIEW_NAME));
    }
}