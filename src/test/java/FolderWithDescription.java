import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

import static runner.TestUtils.scrollToElement;
import static runner.TestUtils.scrollToEnd;

public class FolderWithDescription extends BaseTest {
    private final String DESCRIPTION = "This folder contains job's documentation";



    @Test
    public void createFolderDescriptionTest() {

        getDriver().findElement(By.xpath("//span[normalize-space()='Create a job']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("Docs");
        getDriver().findElement(By.cssSelector(".icon-folder.icon-xlg")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.cssSelector("input[name='_.displayNameOrNull']")).sendKeys("Docs");
        getDriver().findElement(By.cssSelector("textarea[name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.cssSelector("#yui-gen6-button")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#view-message")).getText().contains(DESCRIPTION));

        getDriver().findElement(By.xpath("//a[normalize-space()='Dashboard']")).click();
        getDriver().findElement(By.cssSelector("a[class='jenkins-table__link model-link inside'] button[class='jenkins-menu-dropdown-chevron']")).click();
        getDriver().findElement(By.xpath("(//span[normalize-space()='Delete Folder'])[1]")).click();
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();
    }

    @Test
    public void createFolderWithDescriptionAndJobPipelineTest() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath(" //input[@id='name']")).sendKeys("Projects 2022");
        scrollToEnd(getDriver());
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.xpath("//span[@class='yui-button primary large-button']")).click();
        getDriver().findElement(By.name("_.description")).sendKeys("Panera LTD 2022");
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();
        getDriver().findElement(By.xpath(" //input[@class='jenkins-input']")).sendKeys("New items");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(By.xpath("//a[@href='/'][@class='model-link']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("projectstatus")).getText().contains("Projects 2022"));

        clearData();
    }
}

