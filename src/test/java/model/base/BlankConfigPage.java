package model.base;

import model.BlankConfigSideMenuFrame;
import model.base.BaseConfigPage;
import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;

public abstract class BlankConfigPage<StatusPage extends BaseStatusPage<?>, Self extends BaseConfigPage<?, ?, ?>> extends BaseConfigPage<StatusPage, Self, BlankConfigSideMenuFrame<Self>> {

    @Override
    protected BlankConfigSideMenuFrame<Self> createSideMenuFrame() {
        return new BlankConfigSideMenuFrame<>(getDriver(), (Self)this);
    }

    public BlankConfigPage(WebDriver driver) {
        super(driver);
    }
}
