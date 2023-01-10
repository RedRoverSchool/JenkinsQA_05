package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public class BreadcrumbsComponent extends BaseModel {
    @FindBy(id = "breadcrumbs")
    private WebElement breadcrumbs;

    public BreadcrumbsComponent(WebDriver driver) {
        super(driver);
    }

    public String getTextBreadcrumbs() {
        return breadcrumbs.getAttribute("textContent");
    }
}
