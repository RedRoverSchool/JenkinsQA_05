package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageJenkinsPage extends BasePage{

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement manageUsers;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public UsersPage clickManageUsers(){
        manageUsers.click();

        return new UsersPage(getDriver());
    }
}
