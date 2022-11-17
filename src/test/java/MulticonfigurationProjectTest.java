import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class MulticonfigurationProjectTest extends BaseTest {

    private static final String RANDOM_PROJECT_NAME = RandomStringUtils.randomAlphanumeric(10);

    private void click(By by) {
        getDriver().findElement(by).click();
    }

    @Test
    public void testCreateMulticonfigurationProjectWithRandomName_HappyPath() {

        click(By.linkText("New Item"));
        getDriver().findElement(By.id("name")).sendKeys(RANDOM_PROJECT_NAME);
        click(By.className("hudson_matrix_MatrixProject"));
        click(By.id("ok-button"));
        click(By.id("yui-gen27-button"));
        click(By.xpath("//ul[@id = 'breadcrumbs']//a[@class= 'model-link'][contains(., 'Dashboard')]"));

        Assert.assertTrue(getProjectsNames().contains(RANDOM_PROJECT_NAME));
    }

    private List<String> getProjectsNames() {
        List<WebElement> projects = getDriver().findElements(By.xpath("//table[@id = 'projectstatus']//td/a/span"));
        List<String> projectList = new ArrayList<>();
        for (WebElement project : projects) {
            projectList.add(project.getText());
        }

        return projectList;
    }
}
