package model;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MulticonfigurationProjectConfigPage extends BaseConfigPage<MulticonfigurationProjectConfigPage> {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement dashboard;

    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(className = "textarea-show-preview")
    private WebElement textareaShowPreview;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewArea;

    public MulticonfigurationProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }

    public MulticonfigurationProjectConfigPage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public MulticonfigurationProjectConfigPage showPreview() {
        getWait(5).until(ExpectedConditions.visibilityOf(textareaShowPreview)).click();

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage getPreview() {
        getWait(5).until(ExpectedConditions.visibilityOf(previewArea)).getText();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }
}
