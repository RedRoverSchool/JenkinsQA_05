package model.multibranch_pipeline;

import model.DeletePage;
import model.RenameItemPage;
import model.base.BasePage;
import model.base.BlankStatusPage;
import model.folder.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusPage extends BlankStatusPage<MultibranchPipelineStatusPage> {

    @FindBy(linkText = "Delete Multibranch Pipeline")
    private WebElement deleteLeftSideMenu;

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    private <T extends BasePage> DeletePage<T> clickDeleteMultibranchPipeline(T page) {
        deleteLeftSideMenu.click();

        return new DeletePage<>(getDriver(), page);
    }

    public MultibranchPipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public DeletePage<MultibranchPipelineStatusPage> clickDeleteMultibranchPipelineWithOutFolder() {
        return  clickDeleteMultibranchPipeline(this);
    }

    public DeletePage<FolderStatusPage> clickDeleteMultibranchPipelineWithFolder() {
        return  clickDeleteMultibranchPipeline(new FolderStatusPage(getDriver()));
    }

    public RenameItemPage<MultibranchPipelineStatusPage> clickRenameSideMenu() {
        renameButton.click();

        return new RenameItemPage<>(getDriver(), new MultibranchPipelineStatusPage(getDriver()));
    }
}
