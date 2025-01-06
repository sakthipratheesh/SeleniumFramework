package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    WebDriver driver;

    // Locators
    private By cartTitle = By.className("title");

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Page Actions
    public String getCartTitle(WebDriver driver) {
        return driver.findElement(cartTitle).getText();
    }
}
