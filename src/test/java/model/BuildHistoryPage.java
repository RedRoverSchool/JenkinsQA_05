package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;

public class BuildHistoryPage extends HomePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='/iconSize?16x16']")
    private WebElement smallSizeIcon;

    @FindBy(xpath = "//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li/following-sibling::li[2]")
    private WebElement middleSizeIcon;

    @FindBy(xpath = "//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li[last()]")
    private WebElement largeSizeIcon;

    @FindBy(xpath = "//table[@id='projectStatus']/thead/tr/th")
    private List<WebElement> columns;

    @FindBy(xpath = "//a[@href='/legend']")
    private WebElement iconLegend;

    @FindBy(xpath = "//div[contains(@id,'label-tl')]")
    private List<WebElement> labelsOnTimelineBuildHistory;

    @FindBy(css = "#icon-tl-0-1-e1")
    private WebElement iconOfLabelsOnTime;

    @FindBy(xpath = "//a/span[contains(text(), 'Atom feed for all')]")
    private WebElement iconAtomFeedForAll;

    @FindBy(xpath = "//a/span[contains(text(), 'Atom feed for failures')]")
    private WebElement iconAtomFeedForFailures;

    @FindBy(xpath = "//a/span[contains(text(), 'Atom feed for just latest builds')]")
    private WebElement iconAtomFeedFoJustLatestBuilds;


    public boolean smallSizeIconIsDisplayed() {

        return smallSizeIcon.isDisplayed();
    }

    public boolean middleSizeIconIsDisplayed() {

        return middleSizeIcon.isDisplayed();
    }

    public boolean largeSizeIconIsDisplayed() {

        return largeSizeIcon.isDisplayed();
    }

    public int getSize() {

        return columns.size();
    }

    public boolean isIconDisplayed() {

        return iconLegend.isDisplayed();
    }

    public List<String> getNameOfLabelsOnTimeLineBuildHistory(){
        getWait(5).until(ExpectedConditions.visibilityOf(iconOfLabelsOnTime));
        return labelsOnTimelineBuildHistory
                .stream()
                .map(WebElement :: getText)
                .collect(Collectors.toList());
    }

    public boolean iconAtomFeedForAllIsDisplayed() {

        return iconAtomFeedForAll.isDisplayed();
    }

    public boolean iconAtomFeedForFailuresIsDisplayed() {

        return iconAtomFeedForFailures.isDisplayed();
    }

    public boolean iconAtomFeedForFoJustLatestBuildsIsDisplayed() {

        return iconAtomFeedFoJustLatestBuilds.isDisplayed();
    }

}
