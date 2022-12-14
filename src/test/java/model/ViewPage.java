package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewPage extends BasePage {

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(id = "jenkins-name-icon")
    private WebElement dashboard;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editView;

    @FindBy(xpath = "//div[@class='tab']//a[@href='/user/admin/my-views/']")
    private WebElement allButton;

    @FindBy(xpath = "//a[@href='delete']")
    private WebElement deleteViewItem;

    @FindBy(id = "yui-gen1-button")
    private WebElement yesButtonDeleteView;

    @FindBy(xpath = "//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div")
    private WebElement descriptionText;

    @FindBys({
            @FindBy(css = ".task")
    })
    private List<WebElement> sideMenuList;

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

    public ViewPage clickDeleteViewItem() {
        deleteViewItem.click();

        return this;
    }

    public MyViewsPage clickYesButtonDeleteView() {
        yesButtonDeleteView.click();

        return new MyViewsPage(getDriver());
    }

    public String getTextDescription() {

        return descriptionText.getText();
    }

    public String getBreadcrumbsItemName(String name) {
        return getDriver()
                .findElement(By.xpath("//ul[@id='breadcrumbs']//a[@href='/user/admin/my-views/view/" + name + "/']"))
                .getText();
    }

    public ArrayList<String> getSideMenuTextList() {
        ArrayList<String> sideMenuText = new ArrayList<>();
        List<WebElement> sideMenu = sideMenuList;
        for (WebElement element : sideMenu) {
            sideMenuText.add(element.getText());
        }
        return sideMenuText;
    }

    public ArrayList<String> getActualSideMenu() {
        ArrayList<String> actualSideMenu = new ArrayList<>();
        actualSideMenu.add("New Item");
        actualSideMenu.add("People");
        actualSideMenu.add("Build History");
        actualSideMenu.add("Edit View");
        actualSideMenu.add("Delete View");

        return actualSideMenu;
    }
}
