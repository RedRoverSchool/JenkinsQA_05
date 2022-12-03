import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class NewView1Test extends BaseTest {

    private static final By PROJECT_OR_VIEW_NAME = By.id("name");
    private static final By DASHBOARD_LINK = By.xpath("//ul[@id='breadcrumbs']//a[@href='/']");
    private static final By MY_VIEWS = By.cssSelector("a[href='/me/my-views']");
    private static final By ADD_VIEW = By.cssSelector("a[title='New View']");
    private static final By DELETE_VIEW = By.xpath("//a[@href='delete']");
    private static final String GLOBAL_VIEW_NAME = "Global_View";
    private static final String LIST_VIEW_NAME = "List_View";
    private static final String MY_VIEW_NAME = "My_View";

    public List<WebElement> getListViews() {

        return getDriver().findElements(
                By.cssSelector(".tabBar .tab a[href]"));
    }

    public String getListViewsNames() {
        StringBuilder listViewsNames = new StringBuilder();
        for (WebElement view : getListViews()) {
            listViewsNames.append(view.getText()).append(" ");
        }

        return listViewsNames.toString().trim();
    }

    public List<WebElement> getListButtonsForJobsDropdownMenu() {

        return getWait(10)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector(".job-status-nobuilt button")));
    }

    private void createAnyJob(String name, By projectType) {
        final By ButtonOKCreateJob =
                By.cssSelector(".large-button.primary.yui-button");
        final By ButtonSaveJob = By.cssSelector("[type='submit']");

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(PROJECT_OR_VIEW_NAME).sendKeys(name);
        getDriver().findElement(projectType).click();
        getDriver().findElement(ButtonOKCreateJob).click();
        getDriver().findElement(ButtonSaveJob).click();
        getDriver().findElement(DASHBOARD_LINK).click();
    }

    public void createAnyView(String name, By radioButton) {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(ADD_VIEW).click();
        getDriver().findElement(PROJECT_OR_VIEW_NAME).sendKeys(name);
        getDriver().findElement(radioButton).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(DASHBOARD_LINK).click();
    }

    public void deleteAllJobsByDropdownMenus() {
        getDriver().findElement(DASHBOARD_LINK).click();
        for (int i = getListButtonsForJobsDropdownMenu().size() - 1; i >= 0; i--) {
            getListButtonsForJobsDropdownMenu().get(i).click();
            getDriver().findElement(
                    By.partialLinkText("Delete")).click();
            getDriver().switchTo().alert().accept();
        }
    }

    @Test
    public void testCreateMyViews() {
        createAnyJob("Freestyle project",
                By.xpath("//span[text() = 'Freestyle project']"));
        createAnyJob("Pipeline",
                By.xpath("//span[text() = 'Freestyle project']"));
        createAnyJob("Multi-configuration project",
                By.xpath("//span[text() = 'Multi-configuration project']"));
        createAnyView(GLOBAL_VIEW_NAME,
                By.cssSelector("label[for='hudson.model.ProxyView']"));
        createAnyView(LIST_VIEW_NAME,
                By.cssSelector("label[for='hudson.model.ListView']"));
        createAnyView(MY_VIEW_NAME,
                By.cssSelector("label[for='hudson.model.MyView']"));
        getDriver().findElement(MY_VIEWS).click();

        Assert.assertTrue(getListViewsNames().contains(GLOBAL_VIEW_NAME));
        Assert.assertTrue(getListViewsNames().contains(LIST_VIEW_NAME));
        Assert.assertTrue(getListViewsNames().contains(MY_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateMyViews")
    public void testViewHasSelectedTypeGlobalView() {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.linkText(GLOBAL_VIEW_NAME)).click();
        getDriver().findElement(
                By.cssSelector("a[href='/user/admin/my-views/view/"
                        + GLOBAL_VIEW_NAME + "/configure']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.cssSelector(".jenkins-form-description")).getText(),
                "The name of a global view that will be shown.");
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeGlobalView")
    public void testViewHasSelectedTypeListView() {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.linkText(LIST_VIEW_NAME)).click();
        getDriver().findElement(
                By.cssSelector("a[href='/user/admin/my-views/view/"
                        + LIST_VIEW_NAME + "/configure']")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("div:nth-of-type(5) > .jenkins-section__title")).getText(),
                "Job Filters");
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeListView")
    public void testDeleteView() {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(
                By.cssSelector(".tabBar .tab a[href='/user/admin/my-views/view/"
                        + LIST_VIEW_NAME + "/']")).click();
        getDriver().findElement(DELETE_VIEW).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();

        Assert.assertFalse(getListViewsNames().contains(LIST_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testDeleteView")
    public void testDeleteAllViews() {
        getDriver().findElement(DASHBOARD_LINK).click();
        getDriver().findElement(MY_VIEWS).click();
        for (int i = getListViews().size() - 1; i >= 0; i--) {
            if (!getListViews().get(i).getText().equals("All")
                    && !getListViews().get(i).equals(getDriver().findElement(ADD_VIEW))) {
                getListViews().get(i).click();
                getDriver().findElement(DELETE_VIEW).click();
                getDriver().findElement(By.id("yui-gen1-button")).click();
            }
        }

        Assert.assertEquals(getListViewsNames(), "All");

        deleteAllJobsByDropdownMenus();
    }
}
