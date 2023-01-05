package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BreadcrumbsComponent extends HeaderComponent {
    public BreadcrumbsComponent(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "breadcrumbs")
    public WebElement breadcrumbs;

    public String getTextBreadcrumbs() {
        return breadcrumbs.getAttribute("textContent");
    }

}
