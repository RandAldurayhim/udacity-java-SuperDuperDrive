package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id="inputUsername")
    private WebElement inputUsername;
    @FindBy(id="inputPassword")
    private WebElement inputPassword;
    @FindBy(id="login-button")
    private WebElement loginButton;
    @FindBy(id="signup-link")
    private WebElement signupLink;
    @FindBy(id="success-msg")
    private WebElement successMsg;

    public String getSuccessMsg() {
        return  successMsg.getText();
    }
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }

    public void login(String username, String password){
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        loginButton.click();
    }

    public void signup(){
        signupLink.click();
    }


}
