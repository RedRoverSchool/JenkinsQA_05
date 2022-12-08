import model.HomePage;
import model.ViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class ListViewTest extends BaseTest {

    private static final By DASHBOARD = By.id("jenkins-name-icon");
    private static final By OK_BUTTON = By.cssSelector("#yui-gen6-button");
    private static final By DESCRIPTION_AREA = By.xpath("//textarea[@name='description']");
    private static final By DESCRIPTION = By.xpath(
            "//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div");
    private static final By EDIT_VIEW_MENU = By.linkText("Edit View");
    private static final String RANDOM_LIST_VIEW_NAME = TestUtils.getRandomStr();
    private static final By CREATED_LIST_VIEW = By.xpath("//a[@href='/view/" + RANDOM_LIST_VIEW_NAME + "/']");


    private void createFreestyleProject(String name) {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(name);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(DASHBOARD).click();
    }

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
        final String projectTwo = TestUtils.getRandomStr();

        createFreestyleProject(projectOne);
        createFreestyleProject(projectTwo);

        int quantityProjectsInListView =
                new HomePage(getDriver())
                        .clickNewView()
                        .setViewName(RANDOM_LIST_VIEW_NAME)
                        .selectListView()
                        .clickCreate()
                        .addJobToView(projectOne)
                        .clickOk()
                        .getJobList().size();

        int quantityProjectsAll =
                new ViewPage(getDriver())
                        .goToDashboard()
                        .getJobList().size();

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(quantityProjectsAll > 1);
        Assert.assertTrue(new HomePage(getDriver()).getListView(RANDOM_LIST_VIEW_NAME).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithExistingJob")
    public void testEditViewAddDescription() {
        final String descriptionRandom = TestUtils.getRandomStr();

        getDriver().findElement(CREATED_LIST_VIEW).click();
        getDriver().findElement(EDIT_VIEW_MENU).click();
        getDriver().findElement(DESCRIPTION_AREA).sendKeys(descriptionRandom);
        getDriver().findElement(OK_BUTTON).click();

        WebElement actualDescription = getDriver().findElement(DESCRIPTION);

        Assert.assertTrue(actualDescription.isDisplayed());
        Assert.assertEquals(actualDescription.getText(), descriptionRandom);
    }

    @Test(dependsOnMethods = "testEditViewAddDescription")
    public void testEditViewDeleteDescription() {

        getDriver().findElement(CREATED_LIST_VIEW).click();
        getDriver().findElement(By.cssSelector("#description-link")).click();
        getDriver().findElement(DESCRIPTION_AREA).clear();
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