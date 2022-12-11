package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ViewPage extends BasePage{

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(id = "jenkins-name-icon")
    private WebElement dashboard;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editView;

    @FindBy(xpath = "//div[@class='tab']//a[@href='/user/admin/my-views/']")
    private WebElement allButton;


    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }
    public EditViewPage clickEditViewButton() {
        editView.click();

        return new EditViewPage(getDriver());
    }

    public MyViewsPage clickMyViews() {
        allButton.click();

        return new MyViewsPage(getDriver());
    }
}
