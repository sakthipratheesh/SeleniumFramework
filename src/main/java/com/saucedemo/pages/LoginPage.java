package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.automation.utils.WebDriverUtils;

public class LoginPage {

    WebDriver driver;

    // Locators
    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Page Actions
    public void enterUsername(WebDriver driver,String username) {
        WebDriverUtils.sendKeysToElement(driver, usernameInput, username);
    }

    public void enterPassword(WebDriver driver,String password) {
    	WebDriverUtils.sendKeysToElement(driver, passwordInput, password);
    }

    public void clickLoginButton(WebDriver driver) {
       WebDriverUtils.clickElement(driver, loginButton,"loginBtn");
    }

    public String getErrorMessage(WebDriver driver) {
        return driver.findElement(errorMessage).getText();
    }

    // Login method for test case reuse
    public HomePage login(WebDriver driver,String username, String password) {
        enterUsername(driver, username);
        enterPassword(driver, password);
        clickLoginButton(driver);
        return new HomePage(driver);
    }
}
