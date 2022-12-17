package model;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineConfigPage> {

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }
}
