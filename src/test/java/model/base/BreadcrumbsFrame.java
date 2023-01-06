package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public class BreadcrumbsFrame extends BaseModel {
    @FindBy(id = "breadcrumbs")
    public WebElement breadcrumbs;

    public BreadcrumbsFrame(WebDriver driver) {
        super(driver);
    }

    public String getTextBreadcrumbs() {
        return breadcrumbs.getAttribute("textContent");
    }
}
