package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class MyViewSideMenuFrame extends SideMenuFrame {

    @FindBy(css = "a[href*=configure]")
    private WebElement editViewMenuLink;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editView;

    @FindBy(xpath = "//a[@href='delete']")
    private WebElement deleteViewItem;


    public MyViewSideMenuFrame(WebDriver driver) {
        super(driver);
    }

    public EditViewPage goToEditView(String viewName) {
        clickMyViewsSideMenuLink();
        getDriver().findElement(By.linkText(viewName)).click();
        editView.click();

        return new EditViewPage(getDriver());
    }

    public EditViewPage clickEditViewLink() {
        editViewMenuLink.click();

        return new EditViewPage(getDriver());
    }

    public DeleteViewConfirmationPage clickDeleteViewItem() {
        deleteViewItem.click();

        return new DeleteViewConfirmationPage(getDriver());
    }
}
