package model.base;

import model.RestApiPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainFooterFrame extends BaseFooterFrame{

    @FindBy(xpath = "//div/a[@href = 'api/']")
    private WebElement restApi;

    public MainFooterFrame(WebDriver driver) {
        super(driver);
    }

    public RestApiPage clickRestApiLink() {
        restApi.click();
        return new RestApiPage(getDriver());
    }
}
