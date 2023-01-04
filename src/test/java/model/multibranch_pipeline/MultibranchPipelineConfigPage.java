package model.multibranch_pipeline;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage, MultibranchPipelineConfigSideMenuFrame> {

    @Override
    protected MultibranchPipelineStatusPage createStatusPage() {
        return new MultibranchPipelineStatusPage(getDriver());
    }

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
        setConfigSideMenuFrame(new MultibranchPipelineConfigSideMenuFrame(driver, this));
    }
}
