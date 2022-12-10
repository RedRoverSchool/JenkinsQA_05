package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FolderConfigPage extends HomePage {

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButtonForDeleteFolder;

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSubmitDeleteProject(){
        submitButtonForDeleteFolder.click();

        return new HomePage(getDriver());
    }

}
