package model.base;

import org.openqa.selenium.WebDriver;
import runner.BaseModel;

public class BaseStatusSideMenuFrame<StatusPage extends BaseStatusPage<?, ?>> extends BaseModel {

    protected final StatusPage statusPage;

    public BaseStatusSideMenuFrame(WebDriver driver, StatusPage statusPage) {
        super(driver);
        this.statusPage = statusPage;
    }
}
