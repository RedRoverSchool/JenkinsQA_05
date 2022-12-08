package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FolderConfigPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButtonForDeleteFolder;

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public HomePage clickSubmitButtonForDeleteFolder(){
        submitButtonForDeleteFolder.click();

        return new HomePage(getDriver());
    }

}
