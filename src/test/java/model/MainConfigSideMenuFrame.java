package model;

import model.base.BaseConfigPage;
import model.base.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class MainConfigSideMenuFrame<ConfigPage extends BaseConfigPage<?, ?, ?>> extends BaseConfigSideMenuFrame<ConfigPage> {

    public MainConfigSideMenuFrame(WebDriver driver, ConfigPage configPage) {
        super(driver, configPage);
    }
}
