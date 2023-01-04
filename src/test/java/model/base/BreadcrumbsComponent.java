package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public class BreadcrumbsComponent extends BaseModel {
    public BreadcrumbsComponent(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "breadcrumbs")
    public WebElement breadcrumbs;

    public String getTextBreadcrumbs() {
        return breadcrumbs.getAttribute("textContent");
    }

}
