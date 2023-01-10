package model.base.side_menu;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import runner.BaseModel;

public abstract class BaseSideMenuFrame<Page extends BasePage> extends BaseModel {

    protected final Page page;

    public BaseSideMenuFrame(WebDriver driver, Page page) {
        super(driver);
        this.page = page;
    }
}
