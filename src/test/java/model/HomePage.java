package model;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItem;

    @FindBy(xpath = "//tr/td[3]/a/span[1]")
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
}
