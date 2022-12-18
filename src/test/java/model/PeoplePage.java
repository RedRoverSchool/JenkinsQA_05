package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends BasePage {

    @FindBy(xpath = "//tbody/tr/td")
    private List<WebElement> usersListInPeople;

    @FindBy(id = "jenkins-home-link")
    private WebElement rootMenuDashboardLink;

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getListOfUSersInPeople() {
        List<String> listOfUsersInPeople = new ArrayList<>();
        for (int i = 0; i < usersListInPeople.size(); i++) {
            listOfUsersInPeople.add(i, usersListInPeople.get(i).getText());
        }
        return listOfUsersInPeople;
    }

    public HomePage rootMenuDashboardLinkClick() {
        rootMenuDashboardLink.click();
        return new HomePage(getDriver());
    }
}
