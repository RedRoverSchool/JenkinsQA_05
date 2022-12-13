package model.base;

import model.BuildHistoryPage;
import model.MyViewsPage;
import model.NewItemPage;
import model.NewViewPage;
import model.base.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class SideMenuFrame extends MainPage {

    @FindBy(linkText = "Build History")
    private WebElement buildHistory;

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItem;

    @FindBy(css = "a[href='/me/my-views']")
    private WebElement myViewsSideMenuLink;

    @FindBy(css = ".tabBar>.tab>a.addTab")
    private WebElement addViewLink;


    public SideMenuFrame(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }

    public BuildHistoryPage clickBuildHistory() {
        buildHistory.click();

        return new BuildHistoryPage(getDriver());
    }

    public MyViewsPage clickMyViewsSideMenuLink() {
        myViewsSideMenuLink.click();

        return new MyViewsPage(getDriver());
    }

    public NewViewPage clickAddViewLink() {
        addViewLink.click();

        return new NewViewPage(getDriver());
    }
}
