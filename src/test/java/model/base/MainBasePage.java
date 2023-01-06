package model.base;

import org.openqa.selenium.WebDriver;

public abstract class MainBasePage<FooterFrame extends BaseFooterFrame> extends BasePage{
    public MainBasePage(WebDriver driver) {
        super(driver);

    }

    protected abstract FooterFrame createFooterFrame();

    protected HeaderFrame createHeaderFrame(){
        return new HeaderFrame(getDriver());
    }

    protected BreadcrumbsFrame createBreadcrumbs(){
        return new BreadcrumbsFrame(getDriver());
    }

    public FooterFrame getFooter(){
        return createFooterFrame();
    }

    public HeaderFrame getHeader(){
        return createHeaderFrame();
    }

    public BreadcrumbsFrame getBreadcrumbs(){
        return createBreadcrumbs();
    }
}
