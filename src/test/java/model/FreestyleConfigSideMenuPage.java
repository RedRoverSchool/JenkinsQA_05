package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FreestyleConfigSideMenuPage extends BasePage {

    @FindBy(css = "button.task-link")
    List<WebElement> freestyleConfigSideMenu;

    public FreestyleConfigSideMenuPage(WebDriver driver) {
        super(driver);
    }

    public Set<String> collectFreestyleConfigSideMenu() {
        return freestyleConfigSideMenu.stream().map(WebElement::getText).collect(Collectors.toCollection(TreeSet::new));
    }

    public FreestyleProjectConfigPage clickLinkSourceCodeManagement(){
        freestyleConfigSideMenu.get(1).click();

        return new FreestyleProjectConfigPage(getDriver());
    }
}
