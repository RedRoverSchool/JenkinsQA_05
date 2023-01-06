package model.base;

import org.openqa.selenium.WebDriver;

public abstract class MainBasePage<FooterFrame extends BaseFooterFrame> extends BasePage{
    private final FooterFrame footerFrame;
    public MainBasePage(WebDriver driver, FooterFrame footerFrame) {
        super(driver);
        this.footerFrame = footerFrame;
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
