package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteUserPage extends BasePage {
    @FindBy(id = "yui-gen1-button")
    private WebElement yesButton;

    public DeleteUserPage(WebDriver driver) {
        super(driver);
    }

    public ManageUsersPage clickYesToManageUsersPage() {
        yesButton.click();

        return new ManageUsersPage(getDriver());
    }

    public HomePage clickYesToDashboard() {
        yesButton.click();

        return new HomePage(getDriver());
    }
}
