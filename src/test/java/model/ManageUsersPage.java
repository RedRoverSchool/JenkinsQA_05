package model;

import model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersPage extends BasePage {

    @FindBy(css = ".jenkins-table__button")
    private WebElement ConfigureUser;

    @FindBy(xpath = "//a[@href='addUser']")
    private WebElement createUser;

    @FindBy(xpath = "//table[@id='people']//tbody//tr//td")
    private List<WebElement> usersList;

    @FindBy(className = "item")
    private WebElement rootMenuDashboardLink;

    public ManageUsersPage(WebDriver driver) {
        super(driver);
    }

    public ConfigureUserPage clickConfigureUser() {
        ConfigureUser.click();

        return new ConfigureUserPage(getDriver());
    }

    public CreateUserPage clickCreateUser() {
        createUser.click();

        return new CreateUserPage(getDriver());
    }

    public List<String> getListOfUsers() {
        List<String> listOfUsers = new ArrayList<>();
        getWait(5).until(ExpectedConditions.visibilityOfAllElements(usersList));
        for (int i = 0; i < usersList.size(); i++) {
            listOfUsers.add(i, usersList.get(i).getText());
        }
        return listOfUsers;
    }

    public HomePage rootMenuDashboardLinkClick() {
        rootMenuDashboardLink.click();

        return new HomePage(getDriver());
    }

    public DeleteUserPage clickDeleteUser(String name) {
        getWait(3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='user/" + name.toLowerCase() + "/delete']"))).click();

        return new DeleteUserPage(getDriver());
    }
}
