package model.organization_folder;

import model.base.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class OrgFolderConfigSideMenuFrame extends BaseConfigSideMenuFrame<OrgFolderConfigPage> {

    public OrgFolderConfigSideMenuFrame(WebDriver driver, OrgFolderConfigPage configPage) {
        super(driver, configPage);
    }
}
