package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(id="logoutButton")
    private WebElement logoutButton;
    //todo: delete if not used
    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;
    @FindBy(id="nav-credentials-tab")
    private WebElement navCredentialsTab;
    @FindBy(id = "addNewNoteButton")
    private WebElement addNewNoteButton;
    @FindBy(id = "note-title")
    private WebElement noteTitleTextBox;
    @FindBy(id = "note-description")
    private WebElement noteDescriptionTextBox;
    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;
    @FindBy(id ="addNewCredButton" )
    private WebElement addNewCredButton;
    @FindBy(id="credential-url")
    private WebElement credUrlTextbox;
    @FindBy(id="credential-username" )
    private WebElement credUsernameTextbox;
    @FindBy(id="credential-password")
    private WebElement credPasswordTextbox;
    @FindBy(id="credentialSubmit")
    private WebElement credSubmitButton;
    @FindBy(id="noteTitle" )
    private WebElement noteTitle;
    @FindBy(id="noteDescription" )
    private WebElement noteDescription;
    private final JavascriptExecutor jE;
    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;
    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;
    @FindBy(id="deleteCredLink" )
    private WebElement deleteCredLink;
    @FindBy(id = "credUrl")
    private WebElement credUrlDisplayed;
    @FindBy(id="credUsername")
    private WebElement credUsernameDisplayed;
    @FindBy(id="credPass")
    private WebElement credPassDisplayed;
    @FindBy(id = "editViewCredButton")
    private WebElement editViewCredButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        jE = (JavascriptExecutor) driver;

    }

    public void logout(){
        logoutButton.click();
    }

    public void createNewNote(String title, String description){
        jE.executeScript("arguments[0].click();", addNewNoteButton);
        jE.executeScript("arguments[0].value='" + title + "';", noteTitleTextBox);
        jE.executeScript("arguments[0].value='" + description + "';", noteDescriptionTextBox);
        jE.executeScript("arguments[0].click();", noteSubmitButton);

    }

    public String getNoteTitleWithJS() {
        return (String) jE.executeScript("return arguments[0].innerText;", noteTitle);
    }
    public String getNoteDescWithJS() {
        return (String) jE.executeScript("return arguments[0].innerText;", noteDescription);
    }
    public String getCredUrlWithJE(){
        return (String) jE.executeScript("return arguments[0].innerText;", credUrlDisplayed);

    }
    public String getCredUsernameWithJE(){
        return (String) jE.executeScript("return arguments[0].innerText;", credUsernameDisplayed);

    }
    public String getCredPasswordWithJE(){
        return (String) jE.executeScript("return arguments[0].innerText;", credPassDisplayed);

    }
    public String getPasswordValueAfterClickingEditButton(){
        jE.executeScript("arguments[0].click();", editViewCredButton);
        return (String) jE.executeScript("return arguments[0].value;", credPasswordTextbox);
    }

    public void editNote(String title, String description){
        jE.executeScript("arguments[0].click();", editNoteButton);
        jE.executeScript("arguments[0].value='" + title + "';", noteTitleTextBox);
        jE.executeScript("arguments[0].value='" + description + "';", noteDescriptionTextBox);
        jE.executeScript("arguments[0].click();", noteSubmitButton);

    }
    public void deleteNote(){
        jE.executeScript("arguments[0].click();", deleteNoteButton);

    }
    public void createNewCred(String url, String username, String password){
        jE.executeScript("arguments[0].click();", addNewCredButton);
        jE.executeScript("arguments[0].value='" + url + "';", credUrlTextbox);
        jE.executeScript("arguments[0].value='" + username + "';", credUsernameTextbox);
        jE.executeScript("arguments[0].value='" + password + "';", credPasswordTextbox);
        jE.executeScript("arguments[0].click();", credSubmitButton);
    }
    public void deleteCred(){
        jE.executeScript("arguments[0].click();", deleteCredLink);

    }




}

