import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class ListViewTest extends BaseTest {

    private static final By DASHBOARD = By.id("jenkins-name-icon");
    private static final By OK_BUTTON = By.cssSelector("#yui-gen6-button");
    private static final By DESCRIPTION_AREA = By.xpath("//textarea[@name='description']");
    private static final By DESCRIPTION = By.xpath(
            "//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div");
    private static final By EDIT_VIEW_MENU = By.linkText("Edit View");
    private static final String RANDOM_LIST_VIEW_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final By CREATED_LIST_VIEW = By.xpath("//a[@href='/view/" + RANDOM_LIST_VIEW_NAME + "/']");


    private String getRandomString() {

        return RandomStringUtils.randomAlphanumeric(10);
    }

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
        final String projectOne = getRandomString();
        final String projectTwo = getRandomString();

        createFreestyleProject(projectOne);
        createFreestyleProject(projectTwo);

        getDriver().findElement(By.xpath("//a[@tooltip='New View']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(RANDOM_LIST_VIEW_NAME);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.cssSelector("#ok")).click();

        WebElement elementJob = getDriver().findElement(By.xpath("//label[@title='" + projectOne + "']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", elementJob);

        getDriver().findElement(OK_BUTTON).click();

        int quantityProjectsInListView = getDriver().findElements(
                By.xpath("//table[@id='projectstatus']/tbody/tr")).size();

        getDriver().findElement(DASHBOARD).click();

        int quantityProjectsAll = getDriver().findElements(
                By.xpath("//table[@id='projectstatus']/tbody/tr")).size();

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(quantityProjectsAll > 1);
        Assert.assertTrue(getDriver().findElement(CREATED_LIST_VIEW).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithExistingJob")
    public void testEditViewAddDescription() {
        final String descriptionRandom = getRandomString();

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