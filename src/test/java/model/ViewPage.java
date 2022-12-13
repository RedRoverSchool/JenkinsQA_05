package model;

import model.base.MyViewSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ViewPage extends MyViewSideMenuFrame {

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(id = "jenkins-name-icon")
    private WebElement dashboard;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editView;

    @FindBy(css = ".tab a[href*='/my-views/']")
    private WebElement allButton;

    @FindBy(xpath = "//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div")
    private WebElement descriptionText;

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

    public String getTextDescription() {

        return descriptionText.getText();
    }
}
