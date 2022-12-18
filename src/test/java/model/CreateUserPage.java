package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateUserPage extends BasePage {

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(name = "password1")
    private WebElement password;

    @FindBy(name = "password2")
    private WebElement confirmPassword;

    @FindBy(name = "fullname")
    private WebElement fullName;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(id = "yui-gen1-button")
    private WebElement createUserButton;

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage setUsername(String name) {
        username.sendKeys(name);

        return this;
    }

    public CreateUserPage setPassword(String name) {
        password.sendKeys(name);

        return this;
    }

    public CreateUserPage confirmPassword(String name) {
        confirmPassword.sendKeys(name);

        return this;
    }

    public CreateUserPage setFullName(String name) {
        fullName.sendKeys(name);

        return this;
    }

    public CreateUserPage setEmail(String name) {
        email.sendKeys(name);

        return this;
    }

    public ManageUsersPage clickCreateUserButton() {
        createUserButton.click();

        return new ManageUsersPage(getDriver());
    }
}
