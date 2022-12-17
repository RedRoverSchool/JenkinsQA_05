package model.base;

import model.RestApiPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class FooterPage extends BasePage {
        @FindBy(xpath = "//div/a[@href = 'api/']")
        private WebElement restApi;

        public FooterPage(WebDriver driver) {
            super(driver);
        }
        public RestApiPage clickRestApiLink() {
            restApi.click();
            return new RestApiPage(getDriver());
        }
}
