package model.multibranch_pipeline;

import model.base.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class MultibranchPipelineConfigSideMenuFrame extends BaseConfigSideMenuFrame<MultibranchPipelineConfigPage> {

    public MultibranchPipelineConfigSideMenuFrame(WebDriver driver, MultibranchPipelineConfigPage configPage) {
        super(driver, configPage);
    }
}
