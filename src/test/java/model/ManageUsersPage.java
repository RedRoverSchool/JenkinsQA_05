package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageUsersPage extends BasePage {

    @FindBy(css = ".jenkins-table__button")
    private WebElement ConfigureUser;

    public ManageUsersPage(WebDriver driver) {
       super(driver);
    }

    public ConfigureUserPage clickConfigureUser() {
        ConfigureUser.click();

        return new ConfigureUserPage(getDriver());
    }
}