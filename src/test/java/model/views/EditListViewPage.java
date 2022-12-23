package model.views;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.BaseUtils;
import runner.TestUtils;

import java.util.List;

public class EditListViewPage extends EditViewPage {

    @FindBy(css = "#yui-gen3-button")
    private WebElement addColumnButton;

    @FindBy(css = ".repeated-chunk__header")
    private List<WebElement> columns;

    @FindBy(css = "input[json='true']+label")
    private WebElement jobToAddListView;

    @FindBy(css = "input[json='true']+label")
    private List<WebElement> listJobsToAddListView;

    @FindBy(css = ".bottom-sticker-inner--stuck")
    private WebElement bottomStickerDynamic;

    public EditListViewPage(WebDriver driver) {
        super(driver);
    }

    public EditListViewPage addJobsToListView(int numberOfJobs) {
        if(listJobsToAddListView.size()<numberOfJobs) {
            BaseUtils.log("Create more items");
            return null;
        }
        for(int i = 0; i < numberOfJobs; i++) {
            listJobsToAddListView.get(i).click();
        }

        return this;
    }

    public EditListViewPage addAllJobsToListView() {
        try {
            listJobsToAddListView
                    .stream()
                    .forEach(WebElement::click);
        } catch (NoSuchElementException exception) {
            BaseUtils.log(String.format("Jobs not found at " + getDriver().getTitle()));
        }

        return this;
    }

    public int getCountColumns() {
        return columns.size();
    }

    public EditListViewPage addColumn(String type) {
        TestUtils.scrollToEnd(getDriver());
        getWait(10).until(ExpectedConditions.invisibilityOf(bottomStickerDynamic));
        addColumnButton.click();
        getDriver().findElement(By.linkText(type)).click();

        return this;
    }

    public EditListViewPage addJobToView(String name) {
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath(String.format("//label[@title='%s']", name)))).click();

        return this;
    }
}
