package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class JobPage extends HomePage {

    @FindBy(xpath = "//span//*[@class='icon-edit-delete icon-md']")
    private WebElement delete;

    @FindBy(id = "yui-gen1-button")
    private WebElement submit;

    @FindBy(xpath = "//span[text()='Move']/..")
    private WebElement move;

    @FindBy(xpath = "//select[@name='destination']")
    private WebElement destination;

    @FindBy(xpath = "//button[text()='Move']")
    private WebElement moveAfterSelectDestination;

    public JobPage(WebDriver driver) {
        super(driver);
    }

    public JobPage clickDelete() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(delete));
        delete.click();

        return new JobPage(getDriver());
    }

    public String clickMainPanel() {
        String[] namesBlock = getDriver().findElement(By.id("main-panel")).getText().split("\n");

        return namesBlock[1];
    }

    public HomePage clickSubmit() {
        submit.click();

        return new HomePage(getDriver());
    }

    public JobPage clickMove() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(move));
        move.click();

        return new JobPage(getDriver());
    }

    public JobPage selectDestination(String jobName) {
        Select select = new Select(destination);
        select.selectByValue("/" + jobName);

        return this;
    }

    public JobPage clickMoveAfterSelectDestination() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(moveAfterSelectDestination));
        moveAfterSelectDestination.click();

        return new JobPage(getDriver());
    }
}
