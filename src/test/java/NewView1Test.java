import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
    private static final String GLOBAL_VIEW_NAME_FREESTYLE = "Freestyle projects";
    private static final String LIST_VIEW_NAME_PIPLINES = "Piplines";

    private static final String LIST_VIEW_RENAME_PIPLINES = "NewPiplines";
    private static final String MY_VIEW_NAME_MULTI_CONFIGURATION = "Multi-configuration projects";

    public List<WebElement> getListViews() {

        return getDriver().findElements(
                By.cssSelector(".tabBar .tab a[href]"));
    }

    public String getStringListViewsNames() {
        StringBuilder listViewsNames = new StringBuilder();
        for (WebElement view : getListViews()) {
            listViewsNames.append(view.getText()).append(" ");
        }

        return listViewsNames.toString().trim();
    }

    public List<WebElement> getListButtonsForJobsDropdownMenu() {

        return getDriver().findElements(
                By.cssSelector(".job-status-nobuilt button"));
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
        createAnyJob("Freestyle project-1",
                By.xpath("//span[text() = 'Freestyle project']"));
        createAnyJob("Pipeline-1",
                By.xpath("//span[text() = 'Freestyle project']"));
        createAnyJob("Multi-configuration project-1",
                By.xpath("//span[text() = 'Multi-configuration project']"));
        createAnyView(GLOBAL_VIEW_NAME_FREESTYLE,
                By.cssSelector("label[for='hudson.model.ProxyView']"));
        createAnyView(LIST_VIEW_NAME_PIPLINES,
                By.cssSelector("label[for='hudson.model.ListView']"));
        createAnyView(MY_VIEW_NAME_MULTI_CONFIGURATION,
                By.cssSelector("label[for='hudson.model.MyView']"));

        getDriver().findElement(MY_VIEWS).click();
        List<String> viewsList = getListViews()
                .stream()
                .map((WebElement::getText))
                .collect(Collectors.toList());

        Assert.assertTrue(viewsList.contains(GLOBAL_VIEW_NAME_FREESTYLE));
        Assert.assertTrue(viewsList.contains(LIST_VIEW_NAME_PIPLINES));
        Assert.assertTrue(viewsList.contains(MY_VIEW_NAME_MULTI_CONFIGURATION));
    }

    @Test(dependsOnMethods = "testCreateMyViews")
    public void testRenameMyView() {
        final By ButtonOkEditView = By.id("yui-gen6-button");

        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(
                By.cssSelector(".tabBar .tab a[href='/user/admin/my-views/view/"
                        + LIST_VIEW_NAME_PIPLINES + "/']")).click();
        getDriver().findElement(By.xpath("//span[text()='Edit View']/..")).click();
        getDriver().findElement(By.name("name")).clear();
        getDriver().findElement(By.name("name")).sendKeys(LIST_VIEW_RENAME_PIPLINES);
        getDriver().findElement(ButtonOkEditView).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//a[@href='/user/admin/my-views/view/" + LIST_VIEW_RENAME_PIPLINES + "/']")).getText(),
                LIST_VIEW_RENAME_PIPLINES);
    }

    @Test(dependsOnMethods = "testRenameMyView")
    public void testDeleteMyView() {
        final By ButtonYesDeleteView = By.id("yui-gen1-button");

        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(
                By.cssSelector(".tabBar .tab a[href='/user/admin/my-views/view/"
                        + LIST_VIEW_RENAME_PIPLINES + "/']")).click();
        getDriver().findElement(DELETE_VIEW).click();
        getDriver().findElement(ButtonYesDeleteView).click();

        Assert.assertFalse(getStringListViewsNames().contains(LIST_VIEW_NAME_PIPLINES));
    }

    @Test(dependsOnMethods = "testDeleteMyView")
    public void testDeleteAllMyViews() {
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

        Assert.assertEquals(getStringListViewsNames(), "All");

        deleteAllJobsByDropdownMenus();
    }
}
