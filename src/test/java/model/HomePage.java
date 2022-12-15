package model;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends Header {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;
    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItem;
    @FindBy(id = "yui-gen6")
    private WebElement dropdownRenameButton;
    @FindBy(className = "icon-edit-delete")
    private WebElement deleteItem;
    @FindBy(xpath = "//tr/td[3]/a/span[1]")
    private List<WebElement> jobList;
    @FindBy(xpath = "//h2[@class='h4']")
    private WebElement homePageWelcomeText;
    @FindBy(className = "jenkins-table__link")
    private WebElement getItemName;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public String getItemName() {
        return getItemName.getText();
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public HomePage clickJobExists(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return this;
    }

    public FreeStyleProjectMenuPage clickDropdownRenameButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(dropdownRenameButton));
        dropdownRenameButton.click();

        return new FreeStyleProjectMenuPage(getDriver());
    }

    public HomePage clickDeleteItem() {
        deleteItem.click();

        return this;
    }

    public void clickAcceptAlert() {
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public String getHomePageWelcomeText() {
        return homePageWelcomeText.getText();
    }

}
