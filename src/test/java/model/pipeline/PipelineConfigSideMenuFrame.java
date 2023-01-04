package model.pipeline;

import model.base.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class PipelineConfigSideMenuFrame extends BaseConfigSideMenuFrame<PipelineConfigPage> {

    public PipelineConfigSideMenuFrame(WebDriver driver, PipelineConfigPage configPage) {
        super(driver, configPage);
    }
}
