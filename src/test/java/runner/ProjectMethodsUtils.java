package runner;

import model.HomePage;
import org.openqa.selenium.WebDriver;

public class ProjectMethodsUtils {
    public static void createNewPipelineProject(WebDriver driver, String projectName) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickDashboard();
    }
    public static void testCreateNewFreestyleProject(WebDriver driver, String projectName) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .clickDashboard();
    }

//    public static void createNewItemFromDashboard(WebDriver driver, String type, String name) {
//        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
//        driver.findElement(By.id("name")).sendKeys(name);
//        driver.findElement(By.xpath(type)).click();
//        driver.findElement(By.id("ok-button")).click();
//    }
//
//    public static void createNewViewFromDashboard(WebDriver driver, By type, String name) {
//        driver.findElement(By.xpath("//a[@href='/me/my-views']")).click();
//        driver.findElement(By.className("addTab")).click();
//        driver.findElement(By.id("name")).sendKeys(name);
//        driver.findElement(type).click();
//        driver.findElement(By.id("ok")).click();
//    }
}
