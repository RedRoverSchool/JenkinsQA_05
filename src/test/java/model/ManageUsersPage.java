package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersPage extends BasePage {

    @FindBy(css = ".jenkins-table__button")
    private WebElement ConfigureUser;

    @FindBy (xpath = "//a[@href='addUser']")
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

    public CreateUserPage clickCreateUser(){
        createUser.click();

        return new CreateUserPage (getDriver());
    }

    public List<String> getListOfUsers(){
        List<String> listOfUsers = new ArrayList<>();
        for (int i = 0; i < usersList.size(); i++){
            listOfUsers.add(i, usersList.get(i).getText());
        }
        return listOfUsers;
    }

    public HomePage rootMenuDashboardLinkClick(){
        rootMenuDashboardLink.click();

        return new HomePage(getDriver());
    }
}