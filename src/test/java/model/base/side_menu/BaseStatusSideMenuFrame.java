package model.base.side_menu;

import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;

public class BaseStatusSideMenuFrame<StatusPage extends BaseStatusPage<?, ?>> extends BaseSideMenuFrame<StatusPage> {

    public BaseStatusSideMenuFrame(WebDriver driver, StatusPage statusPage) {
        super(driver, statusPage);
    }
}
