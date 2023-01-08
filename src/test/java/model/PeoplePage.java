package model;

import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class PeoplePage extends MainBasePage {

    @FindBy(className = "jenkins-table__link")
    private List<WebElement> usersIdList;

    @FindBy(id = "jenkins-home-link")
    private WebElement rootMenuDashboardLink;

    @FindBy(xpath = "//h1")
    private WebElement header;

    @FindBy(className = "jenkins-description")
    private WebElement description;

    @FindBy(id = "side-panel")
    private WebElement sidePanel;

    @FindBy(xpath = "//table[@id='people']/thead//th")
    private List<WebElement> listPeopleTableColumns;

    @FindBy(xpath = "//ol/li/../../span")
    private WebElement iconLabel;

    @FindBy(xpath = "//ol/li")
    private List<WebElement> listIconSizeButtons;

    public PeoplePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getListOfUsers() {
        List<String> listOfUsers = new ArrayList<>();
        getWait(5).until(ExpectedConditions.visibilityOfAllElements(usersIdList));
        for (int i = 0; i < usersIdList.size(); i++) {
            listOfUsers.add(i, usersIdList.get(i).getText());
        }
        return listOfUsers;
    }

    public HomePage rootMenuDashboardLinkClick() {
        rootMenuDashboardLink.click();
        return new HomePage(getDriver());
    }

    public String getNameOfHeader() {
        return header.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public boolean isDisplayedSidePanel() {
        return sidePanel.isDisplayed();
    }

    public String getPeopleTableColumnsAsString() {
        StringBuilder listPeopleTableColumnsNames = new StringBuilder();
        for (WebElement columnName : listPeopleTableColumns) {
            listPeopleTableColumnsNames.append(columnName.getText()).append(" ");
        }

        return listPeopleTableColumnsNames.toString().replaceAll("  ↑", "")
                .replaceAll("  ↓", "").replaceAll("\n", "").trim();
    }

    public int getPeopleTableColumnsAmount() {

        return listPeopleTableColumns.size();
    }

    public String getIconLabel() {

        return iconLabel.getText();
    }

    public String getListIconSizeButtonsAsString() {
        StringBuilder listIconSizeButtonsNames = new StringBuilder();
        for (WebElement iconSizeButton : listIconSizeButtons) {
            listIconSizeButtonsNames.append(iconSizeButton.getText()).append(" ");
        }

        return listIconSizeButtonsNames.toString().replaceAll("\n", "").trim();
    }
}
