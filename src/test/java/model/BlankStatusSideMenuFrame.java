package model;

import model.base.BaseStatusPage;
import model.base.side_menu.BaseStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class BlankStatusSideMenuFrame<StatusPage extends BaseStatusPage<?, ?>> extends BaseStatusSideMenuFrame<StatusPage> {

    public BlankStatusSideMenuFrame(WebDriver driver, StatusPage statusPage) {
        super(driver, statusPage);
    }
}
