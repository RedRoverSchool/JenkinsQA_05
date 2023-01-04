package model.multiconfiguration;

import model.base.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class MultiConfigurationProjectConfigSideMenuFrame extends BaseConfigSideMenuFrame<MultiConfigurationProjectConfigPage> {

    public MultiConfigurationProjectConfigSideMenuFrame(WebDriver driver, MultiConfigurationProjectConfigPage configPage) {
        super(driver, configPage);
    }
}
