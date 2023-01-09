package model;

import model.base.BaseConfigPage;
import model.base.side_menu.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class BlankConfigSideMenuFrame<ConfigPage extends BaseConfigPage<?, ?, ?>> extends BaseConfigSideMenuFrame<ConfigPage> {

    public BlankConfigSideMenuFrame(WebDriver driver, ConfigPage configPage) {
        super(driver, configPage);
    }
}
