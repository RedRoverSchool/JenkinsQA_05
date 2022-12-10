package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateUserPage extends BasePage{

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(xpath = "//div/input[@name='password1']")
    private WebElement password;

    @FindBy(xpath = "//div/input[@name='password2']")
    private WebElement confirmPassword;

    @FindBy(xpath = "//div/input[@name='fullname']")
    private WebElement fullName;

    @FindBy(xpath = "//div/input[@name='email']")
    private WebElement email;

    @FindBy(xpath = "//span/button[@type='submit']")
    private WebElement createUserButton;

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage setUsername(String name){
        username.sendKeys(name);

        return this;
    }

    public CreateUserPage setPassword(String name){
        password.sendKeys(name);

        return this;
    }

    public CreateUserPage confirmPassword(String name){
        confirmPassword.sendKeys(name);

        return this;
    }

    public CreateUserPage setFullName(String name){
        fullName.sendKeys(name);

        return this;
    }

    public CreateUserPage setEmail(String name){
        email.sendKeys(name);

        return this;
    }

    public ManageUsersPage clickCreateUserButton(){
        createUserButton.click();

        return new ManageUsersPage(getDriver());
    }
}
