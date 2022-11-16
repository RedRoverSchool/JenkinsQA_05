import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;
import java.util.stream.Collectors;

public class FolderTest extends BaseTest {

    private static final By OK_BUTTON = By.id("ok-button");

    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");

    private static final By SAVE_BUTTON = By.id("yui-gen6-button");

    private static final By FOLDER = By.xpath("//span[text()='Folder']");

    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");


    public WebElement getInputName(){
        return getDriver().findElement(INPUT_NAME);
    }
    public WebElement getFolder(){
        return getDriver().findElement(FOLDER);
    }

    public WebElement getOkButton(){
        return getDriver().findElement(OK_BUTTON);
    }

    public WebElement getSaveButton(){
        return getDriver().findElement(SAVE_BUTTON);
    }

    public WebElement getDashboard(){
        return getDriver().findElement(DASHBOARD);
    }

    @BeforeMethod
    public void delete() {
        List<String> folders = getDriver()
                .findElements(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span"))
                .stream().map(WebElement::getText)
                .collect(Collectors.toList());
        for (String folder : folders) {
            getDriver().get("http://localhost:8080/job/" + folder + "/delete");
            getDriver().findElement(By.id("yui-gen1-button")).click();
        }
    }
    @Test
    public void create(){
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("First job");
        getFolder().click();
        getOkButton().click();
        getSaveButton().click();
        getDashboard().click();
        String job = getDriver().findElement(By.xpath("//span[text()='First job']")).getText();

        Assert.assertEquals(job,"First job");
    }
}