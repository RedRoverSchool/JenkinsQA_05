package model.base;

import org.openqa.selenium.WebDriver;

public abstract class MainBasePage<FooterFrame extends BaseFooterFrame> extends BasePage{
    public MainBasePage(WebDriver driver) {
        super(driver);
    }

//    @Override
//    protected MainFooterFrame createFooterFrame() {
//        return new MainFooterFrame(getDriver()) {
//        };
//    }

    protected abstract FooterFrame createFooterFrame();

    public FooterFrame getFooter(){
        return createFooterFrame();
    }
}
