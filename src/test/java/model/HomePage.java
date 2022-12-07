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

    @FindBy(xpath = "//tr/td[3]/a/span[1]")
    private List<WebElement> jobList;

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

    public DropdownMenu clickCreatedFolderDropdownMenu(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']/button")).click();

        return new DropdownMenu(getDriver());
    }

    public StatusPage clickCreatedFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']")).sendKeys(Keys.RETURN);

        return new StatusPage(getDriver());
    }
}
