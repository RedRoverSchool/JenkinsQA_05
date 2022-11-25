import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CreateListViewTest extends BaseTest {

    private static final By DASHBOARD = By.id("jenkins-name-icon");
    private static final String RANDOM_LIST_VIEW_NAME = RandomStringUtils.randomAlphanumeric(10);

    private String getRandomName() {

        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void createFreestyleProject(String name) {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(name);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(DASHBOARD).click();
    }

    @Test
    public void testCreateNewListViewWithExistingJob() {
        final String projectOne = getRandomName();
        final String projectTwo = getRandomName();

        createFreestyleProject(projectOne);
        createFreestyleProject(projectTwo);

        getDriver().findElement(By.xpath("//a[@tooltip='New View']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(RANDOM_LIST_VIEW_NAME);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.cssSelector("#ok")).click();

        WebElement elementJob = getDriver().findElement(By.xpath("//label[@title='" + projectOne + "']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", elementJob);

        getDriver().findElement(By.cssSelector("#yui-gen6-button")).click();

        int quantityProjectsInListView = getDriver().findElements(
                By.xpath("//table[@id='projectstatus']/tbody/tr")).size();

        getDriver().findElement(DASHBOARD).click();

        int quantityProjectsAll = getDriver().findElements(
                By.xpath("//table[@id='projectstatus']/tbody/tr")).size();

        WebElement newListView = getDriver().findElement(
                By.xpath("//a[@href='/view/" + RANDOM_LIST_VIEW_NAME + "/']"));

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(quantityProjectsAll > 1);
        Assert.assertTrue(newListView.isDisplayed());
    }
}
