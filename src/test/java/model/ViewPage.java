package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewPage extends BasePage{
    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editView;

    @FindBy(xpath = "//div[@class='tab']//a[@href='/user/admin/my-views/']")
    private WebElement allButton;

    public ViewPage(WebDriver driver) {
        super(driver);
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
