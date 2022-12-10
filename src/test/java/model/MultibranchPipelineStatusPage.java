package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(linkText = "Delete Multibranch Pipeline")
    private WebElement deleteLeftSideMenu;

    public MultibranchPipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public DeleteMultibranchPipelinePage clickDeleteMultibranchPipeline() {
        deleteLeftSideMenu.click();

        return new DeleteMultibranchPipelinePage(getDriver());
    }
}
