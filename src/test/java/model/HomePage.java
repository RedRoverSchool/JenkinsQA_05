package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItem;

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public FreestyleProjectPage clickProjectName(String name) {
        jobList.get(0).click();

        return new FreestyleProjectPage(getDriver());
    }
}
