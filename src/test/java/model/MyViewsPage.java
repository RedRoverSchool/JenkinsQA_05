package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyViewsPage extends BasePage{

    @FindBy(css = "a[title='New View']")
    private WebElement newView;

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    public NewViewPage clickNewView() {
        newView.click();

        return new NewViewPage(getDriver());
    }
}
