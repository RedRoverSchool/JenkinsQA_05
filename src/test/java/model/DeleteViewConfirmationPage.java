package model;

import model.base.MyViewSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteViewConfirmationPage extends MyViewSideMenuFrame {

    @FindBy(id = "yui-gen1-button")
    private WebElement yesButtonDeleteView;

    public DeleteViewConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public MyViewsPage clickYesButtonDeleteView() {
        yesButtonDeleteView.click();

        return new MyViewsPage(getDriver());
    }

}
