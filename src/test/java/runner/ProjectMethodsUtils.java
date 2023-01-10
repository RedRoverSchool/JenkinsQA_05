package runner;

import model.HomePage;
import org.openqa.selenium.WebDriver;

public class ProjectMethodsUtils {
    public static void createNewPipelineProject(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(name)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewFreestyleProject(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMultiConfigurationProject(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(name)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(name)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMultibranchPipeline(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(name)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewOrganizationFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .setItemName(name)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewGlobalViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setGlobalViewType()
                .clickCreateButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewListViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setListViewType()
                .clickCreateButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMyViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setMyViewType()
                .clickCreateButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewListViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .setListViewType()
                .clickCreateButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMyViewViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .setMyViewType()
                .clickCreateButton()
                .getHeader().clickJenkinsNameIcon();
    }
}
