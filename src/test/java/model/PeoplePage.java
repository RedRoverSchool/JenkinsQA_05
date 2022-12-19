package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends BasePage {

    @FindBy(className = "jenkins-table__link")
    private List<WebElement> usersIdList;

    @FindBy(id = "jenkins-home-link")
    private WebElement rootMenuDashboardLink;

    @FindBy(xpath = "//h1")
    private WebElement peopleTitle;

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getListOfUsers() {
        List<String> listOfUsers = new ArrayList<>();
        getWait(5).until(ExpectedConditions.visibilityOfAllElements(usersIdList));
        for (int i = 0; i < usersIdList.size(); i++) {
            listOfUsers.add(i, usersIdList.get(i).getText());
        }
        return listOfUsers;
    }

    public HomePage rootMenuDashboardLinkClick() {
        rootMenuDashboardLink.click();
        return new HomePage(getDriver());
    }

    public String getTitle() {
        return peopleTitle.getText();
    }
}
