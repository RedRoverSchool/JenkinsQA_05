package model.base;

import model.base.side_menu.BaseSideMenuFrame;
import org.openqa.selenium.WebDriver;

public abstract class BasePageWithSideMenu<SideMenu extends BaseSideMenuFrame> extends BasePage {

    protected abstract SideMenu createSideMenuFrame();

    public BasePageWithSideMenu(WebDriver driver) {
        super(driver);
    }

    public SideMenu getSideMenu() {
        return createSideMenuFrame();
    }
}
