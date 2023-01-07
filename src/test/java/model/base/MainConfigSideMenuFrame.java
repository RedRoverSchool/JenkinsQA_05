package model.base;

import org.openqa.selenium.WebDriver;

public class MainConfigSideMenuFrame<ConfigPage extends BaseConfigPage<?, ?, ?>> extends BaseConfigSideMenuFrame<ConfigPage>{

    public MainConfigSideMenuFrame(WebDriver driver, ConfigPage configPage) {
        super(driver, configPage);
    }
}
