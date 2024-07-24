package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    @FindBy(id="inputFirstName")
    private WebElement inputFirstName;
    @FindBy(id="inputLastName")
    private WebElement inputLastName;
    @FindBy(id="inputUsername")
    private WebElement inputUsername;
    @FindBy(id="inputPassword")
    private WebElement inputPassword;
    @FindBy(id="buttonSignUp")
    private WebElement signupButton;
    @FindBy(id="backToLoginLink")
    private WebElement backToLoginLink;


    private JavascriptExecutor jE;
    private WebDriverWait wait;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        jE = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 20);

    }



    public void siunup(String firstName, String lastName, String usernamem, String passsword){
        jE.executeScript("arguments[0].value='" + firstName + "';", inputFirstName);
        jE.executeScript("arguments[0].value='" + lastName + "';", inputLastName);
        jE.executeScript("arguments[0].value='" + usernamem + "';", inputUsername);
        jE.executeScript("arguments[0].value='" + passsword + "';", inputPassword);
        jE.executeScript("arguments[0].click();", signupButton);
    }

    public void login(){
        jE.executeScript("arguments[0].click();", backToLoginLink);

    }

}
