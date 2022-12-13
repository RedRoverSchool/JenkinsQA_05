package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobPage extends HomePage {

    @FindBy(xpath = "//span//*[@class='icon-edit-delete icon-md']")
    private WebElement delete;

    @FindBy(id="yui-gen1-button")
    private WebElement submit;

    public JobPage(WebDriver driver) {
        super(driver);
    }

    public JobPage clickDelete() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(delete));
        delete.click();

        return new JobPage(getDriver());
    }
    public String clickMainPanel(){
        String[] namesBlock = getDriver().findElement(By.id("main-panel")).getText().split("\n");

        return namesBlock[1];
    }

    public HomePage clickSubmit() {
        submit.click();

        return new HomePage(getDriver());
    }
}
