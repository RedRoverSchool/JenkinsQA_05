package model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItem;

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(xpath = "//td[3]/a/button")
    private WebElement dropDownMenuOfJob;

    @FindBy(xpath = "//li[@index='2']")
    private WebElement deleteButtonInDropDownMenu;

    public HomePage(WebDriver driver) {
        super(driver);
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

    public FreestyleProjectPage clickProjectName() {
        jobList.get(0).click();

        return new FreestyleProjectPage(getDriver());
    }

    public HomePage clickDropDownMenu(){
        dropDownMenuOfJob.click();

        return new HomePage(getDriver());
    }

    public FolderConfigPage clickDeleteButtonInDropDownMenu(){
        deleteButtonInDropDownMenu.click();

        return new FolderConfigPage(getDriver());
    }

    public WebElement getDeleteButtonInDropDownMenu() {
        return deleteButtonInDropDownMenu;
    }

    public DropdownMenu clickCreatedFolderDropdownMenu(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']/button")).click();

        return new DropdownMenu(getDriver());
    }

    public StatusPage clickCreatedFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']")).sendKeys(Keys.RETURN);

        return new StatusPage(getDriver());
    }
}
