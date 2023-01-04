package model;

import model.base.BasePage;
import model.folder.FolderStatusPage;
import model.views.MyViewsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteItemPage extends BasePage {

    @FindBy(id = "yui-gen1-button")
    private WebElement yesButton;

    public DeleteItemPage(WebDriver driver) {
        super(driver);
    }

    public ManageUsersPage clickYesToManageUsersPage() {
        yesButton.click();

        return new ManageUsersPage(getDriver());
    }

    public HomePage clickYesButtonDeleteListView() {
        yesButton.click();

        return new HomePage(getDriver());
    }

    public MyViewsPage clickYesButtonDeleteView() {
        yesButton.click();

        return new MyViewsPage(getDriver());
    }

    public FolderStatusPage clickYesButton() {
        yesButton.click();

        return new FolderStatusPage(getDriver());
    }
    public HomePage clickYesToDashboard() {
        yesButton.click();

        return new HomePage(getDriver());
    }
}
