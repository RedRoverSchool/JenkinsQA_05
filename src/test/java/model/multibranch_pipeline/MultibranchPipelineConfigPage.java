package model.multibranch_pipeline;

import model.base.BlankConfigPage;
import org.openqa.selenium.WebDriver;

public class MultibranchPipelineConfigPage extends BlankConfigPage<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage> {

    @Override
    protected MultibranchPipelineStatusPage createStatusPage() {
        return new MultibranchPipelineStatusPage(getDriver());
    }

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }
}
