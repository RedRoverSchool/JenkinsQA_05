package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class BuildHistoryPage extends HomePage {

    @FindBy(id = "map")
    private WebElement mapOfBuild;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean getMap() {
        mapOfBuild.isDisplayed();

        return false;
    }


}
