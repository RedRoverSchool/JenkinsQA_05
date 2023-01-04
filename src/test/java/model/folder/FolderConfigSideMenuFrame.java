package model.folder;

import model.base.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class FolderConfigSideMenuFrame extends BaseConfigSideMenuFrame<FolderConfigPage> {

    public FolderConfigSideMenuFrame(WebDriver driver, FolderConfigPage configPage) {
        super(driver, configPage);
    }
}
