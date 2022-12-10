package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class MyViewsPage extends HomePage{

    @FindBy(css = "a[title='New View']")
    private WebElement newView;

    @FindBy(css = ".tabBar .tab a[href]")
    private List <WebElement> listViews;

    @FindBy(css = ".pane-header-title")
    private List <WebElement> listViewActiveFilters;

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    public NewViewPage clickNewView() {
        newView.click();

        return new NewViewPage(getDriver());
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

    public List<String> getActiveFiltersList() {

        return listViewActiveFilters.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
