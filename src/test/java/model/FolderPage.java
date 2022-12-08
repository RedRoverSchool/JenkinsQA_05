package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class FolderPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(xpath = "//tr/td[3]/a/span[1]")
    private List<WebElement> jobList;

    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement folderNewName;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement renameSubmitButton;

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public List<String> getJobList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public FolderPage clickRename(String folderName){
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName + "/confirm-rename']")).click();

        return new FolderPage(getDriver());
    }

    public FolderPage clearAndSetNewName(String folderName){
        folderNewName.clear();
        folderNewName.sendKeys(folderName);

        return new FolderPage(getDriver());
    }

    public FolderPage clickRenameSubmitButton(){
        renameSubmitButton.click();

        return new FolderPage(getDriver());
    }
}
