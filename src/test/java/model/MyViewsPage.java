package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MyViewsPage extends BasePage{

    @FindBy(css = "a[title='New View']")
    private WebElement newView;

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(css = ".tabBar .tab a[href]")
    private List <WebElement> listViews;

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    public NewViewPage clickNewView() {
        newView.click();

        return new NewViewPage(getDriver());
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public List<WebElement> getListViews() {

        return listViews;
    }

    public String getListViewsNames() {
        StringBuilder listViewsNames = new StringBuilder();
        for (WebElement view : getListViews()) {
            listViewsNames.append(view.getText()).append(" ");
        }

        return listViewsNames.toString().trim();
    }
}
