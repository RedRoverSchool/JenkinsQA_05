package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteUserPage extends BasePage {
    @FindBy(id = "yui-gen1-button")
    private WebElement yesButton;

    public DeleteUserPage(WebDriver driver) {
        super(driver);
    }

    public DeleteUserPage clickYes() {
        yesButton.click();

        return this;
    }

    public List<String> getListOfUsers() {

        return getWait(1).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("jenkins-table__link")))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
