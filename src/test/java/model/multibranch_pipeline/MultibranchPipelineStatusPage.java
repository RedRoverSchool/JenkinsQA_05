package model.multibranch_pipeline;

import model.base.BaseStatusPage;
import model.RenameItemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusPage extends BaseStatusPage {

    @FindBy(linkText = "Delete Multibranch Pipeline")
    private WebElement deleteLeftSideMenu;

    @FindBy(xpath = "//h1")
    private WebElement multibranchPipelineName;

    @FindBy(linkText = "Rename")
    private WebElement renameLeftMenu;

    public MultibranchPipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public DeleteMultibranchPipelinePage clickDeleteMultibranchPipeline() {
        deleteLeftSideMenu.click();

        return new DeleteMultibranchPipelinePage(getDriver());
    }

    public String getMultibranchPipelineName() {
        return multibranchPipelineName.getText();
    }

    public RenameItemPage clickRenameLeftMenu() {
        renameLeftMenu.click();

        return new RenameItemPage(getDriver());
    }
}
