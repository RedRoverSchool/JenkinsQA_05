package model.base;

import org.openqa.selenium.WebDriver;

public abstract class MainBasePage extends BasePage {
    public MainBasePage(WebDriver driver) {
        super(driver);
    }

    public FooterComponent getFooter() {
        return new FooterComponent(getDriver());
    }

    public HeaderComponent getHeader() {
        return new HeaderComponent(getDriver());
    }

    public BreadcrumbsComponent getBreadcrumbs() {
        return new BreadcrumbsComponent(getDriver());
    }
}
