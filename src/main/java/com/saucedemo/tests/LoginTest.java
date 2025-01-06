package com.saucedemo.tests;

import com.automation.base.BaseTest;
import com.automation.reports.ExtentTestNGListener;
import com.automation.utils.AssertionUtils;
import com.automation.utils.WebDriverUtils;
import com.saucedemo.pages.HomePage;
import com.saucedemo.pages.LoginPage;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    public static WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    public  ExtentTestNGListener report = new ExtentTestNGListener();

    @BeforeMethod
    public void setUp() throws MalformedURLException {
    	
        // Initialize WebDriver
        driver = BaseTest.setupTest();

        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void validLoginTest() {
        // Perform login with valid credentials
    	report.logInfo("Performing Login validation with valid credential"); 
        homePage = loginPage.login(driver, "standard_user", "secret_sauce");

        // Verify the product page title
        String pageTitle = homePage.getProductTitle();
        AssertionUtils.assertEquals(pageTitle, "Products", "Login failed or incorrect page title!");
    }

    @Test
    public void invalidLoginTest() {
        // Perform login with invalid credentials
    	report.logInfo("Performing Login validation with Invalid Credential");
        loginPage.login(driver,"invalid_user", "invalid_password");

        // Verify the error message
        String error = loginPage.getErrorMessage(driver);
        AssertionUtils.assertTrue(error.contains("Epic sadface"), "Error message not displayed for invalid login.");
    }
}
