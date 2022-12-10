package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static runner.TestUtils.scrollToElement;

public class DropdownMenu extends BasePage {

    @FindBy(xpath = "//span[text()='Move']")
    private WebElement moveButtonDropdown;

    public DropdownMenu(WebDriver driver) {
        super(driver);
    }

    public MovePage clickMoveButtonDropdown() {
        getWait(5).until(ExpectedConditions.visibilityOf(moveButtonDropdown));
        scrollToElement(getDriver(), moveButtonDropdown);
        moveButtonDropdown.click();
        return new MovePage(getDriver());
    }
}
