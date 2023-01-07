package model.multibranch_pipeline;

import model.base.BaseConfigPage;
import model.base.MainConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage, MainConfigSideMenuFrame<MultibranchPipelineConfigPage>> {

    @Override
    protected MultibranchPipelineStatusPage createStatusPage() {
        return new MultibranchPipelineStatusPage(getDriver());
    }

    @Override
    protected MainConfigSideMenuFrame<MultibranchPipelineConfigPage> createConfigSideMenuFrame() {
        return new MainConfigSideMenuFrame<>(getDriver(), this);
    }

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }
}
