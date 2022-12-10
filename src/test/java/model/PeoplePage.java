package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeoplePage extends BasePage{

    @FindBy(xpath = "//tbody/tr/td")
    private List<WebElement> usersListInPeople;
    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public List <String> getListOfUSersInPeople (){
        List<String> listOfUsersInPeople = new ArrayList<>();
        for (int i = 0; i < usersListInPeople.size(); i++) {
            listOfUsersInPeople.add(i, usersListInPeople.get(i).getText());
        }
        return listOfUsersInPeople;
    }
}
