package model.base;

import org.openqa.selenium.WebDriver;

public class MainBasePage extends BasePage<MainFooterFrame>{
    public MainBasePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MainFooterFrame createFooterFrame() {
        return new MainFooterFrame(getDriver()) {
        };
    }
}
