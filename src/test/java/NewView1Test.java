import model.HomePage;
import model.MyViewsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

public class NewView1Test extends BaseTest {

    private static final By PROJECT_OR_VIEW_NAME = By.id("name");
    private static final By DASHBOARD_LINK = By.xpath("//ul[@id='breadcrumbs']//a[@href='/']");
    private static final By MY_VIEWS = By.cssSelector("a[href='/me/my-views']");
    private static final By ADD_VIEW = By.cssSelector("a[title='New View']");
    private static final By DELETE_VIEW = By.xpath("//a[@href='delete']");
    private static final String LIST_VIEW_RENAME = "New_List_View";
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

    public List<String> getListJobs() {

        return getDriver().findElements(
                        By.cssSelector("a[class='jenkins-table__link model-link inside'] span"))
                .stream()
                .map((WebElement::getText))
                .collect(Collectors.toList());
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

    public void goToEditView(String viewName) {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.linkText(viewName)).click();
        getDriver().findElement(
                By.cssSelector("a[href='/user/admin/my-views/view/"
                        + viewName + "/configure']")).click();
    }

    @Test
    public void testCreateMyViews() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName("Freestyle project")
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()

                .clickNewItem()
                .setProjectName("Pipeline")
                .selectPipelineAndClickOk()
                .saveConfigAndGoToProject()
                .clickDashboard()

                .clickNewItem()
                .setProjectName("Multi-configuration project")
                .selectMultiConfigurationProjectAndClickOk()
                .clickSave()
                .goToDashboard()

                .clickMyViews()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViews()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViews()
                .clickNewView()
                .setViewName(MY_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViews();

        Assert.assertTrue(myViewsPage.getListViewsNames().contains(GLOBAL_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(LIST_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(MY_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateMyViews")
    public void testRenameMyView() {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(
                By.cssSelector(".tabBar .tab a[href='/user/admin/my-views/view/"
                        + LIST_VIEW_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span[text()='Edit View']/..")).click();
        getDriver().findElement(By.name("name")).clear();
        getDriver().findElement(By.name("name")).sendKeys(LIST_VIEW_RENAME);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//a[@href='/user/admin/my-views/view/" + LIST_VIEW_RENAME + "/']")).getText(),
                LIST_VIEW_RENAME);
    }

    @Test(dependsOnMethods = "testRenameMyView")
    public void testViewHasSelectedTypeGlobalView() {
        goToEditView(GLOBAL_VIEW_NAME);

        Assert.assertEquals(getDriver()
                        .findElement(By.cssSelector(".jenkins-form-description")).getText(),
                "The name of a global view that will be shown.");
    }

    @Ignore
    @Test(dependsOnMethods = "testViewHasSelectedTypeGlobalView")
    public void testViewHasSelectedTypeListView() {
        goToEditView(LIST_VIEW_RENAME);

        Assert.assertEquals(getDriver().findElement(
                        By.cssSelector("div:nth-of-type(5) > .jenkins-section__title")).getText(),
                "Job Filters");
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeGlobalView")
    public void testViewHasSelectedTypeMyView() {
        final List<String> expectedListJobs = getListJobs();

        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.linkText(MY_VIEW_NAME)).click();

        Assert.assertEquals(getListJobs(), expectedListJobs);
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeMyView")
    public void testDeleteView() {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(
                By.cssSelector(".tabBar .tab a[href='/user/admin/my-views/view/"
                        + LIST_VIEW_RENAME + "/']")).click();
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
    }
}