package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    // Locators
    private By productTitle = By.className("title");
    private By cartIcon = By.className("shopping_cart_link");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Page Actions
    public String getProductTitle() {
        return driver.findElement(productTitle).getText();
    }

    public void clickCartIcon() {
        driver.findElement(cartIcon).click();
    }
}
