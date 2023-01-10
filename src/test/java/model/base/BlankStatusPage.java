package model.base;

import model.BlankStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;

public abstract class BlankStatusPage<Self extends BlankStatusPage<?>> extends BaseStatusPage<Self, BlankStatusSideMenuFrame<Self>>{

    @Override
    protected BlankStatusSideMenuFrame<Self> createSideMenuFrame() {
        return new BlankStatusSideMenuFrame<>(getDriver(), (Self)this);
    }

    public BlankStatusPage(WebDriver driver) {
        super(driver);
    }
}
