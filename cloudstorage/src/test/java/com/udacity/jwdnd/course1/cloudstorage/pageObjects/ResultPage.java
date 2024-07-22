package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

public class ResultPage {

    @FindBy(css = "span a[th:href='@{/home}']") // Targets anchor tag with specific href value
    private WebElement hereLink;
    private JavascriptExecutor jE;
    private Wait wait;


    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        jE = (JavascriptExecutor) driver;

    }
    public void clickHere(){
        jE.executeScript("arguments[0].click();", hereLink);
    }
}
