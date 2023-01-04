package model.multibranch_pipeline;

import model.DeleteItemPage;
import model.base.BaseStatusPage;
import model.RenameItemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusPage extends BaseStatusPage<MultibranchPipelineStatusPage> {

    @FindBy(linkText = "Delete Multibranch Pipeline")
    private WebElement deleteLeftSideMenu;

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    public MultibranchPipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public DeleteItemPage clickDeleteMultibranchPipeline() {
        deleteLeftSideMenu.click();

        return new DeleteItemPage(getDriver());
    }

    public RenameItemPage<MultibranchPipelineStatusPage> clickRenameSideMenu() {
        renameButton.click();

        return new RenameItemPage<>(getDriver(), new MultibranchPipelineStatusPage(getDriver()));
    }
}
