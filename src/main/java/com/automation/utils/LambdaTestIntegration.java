package com.automation.utils;

import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class LambdaTestIntegration {
    public static final String USERNAME = ConfigReader.getProperty("LTUserName");
    public static final String ACCESS_KEY = ConfigReader.getProperty("LTAccessKey");
    public static final String URL = "https://"+ USERNAME + ":" + ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    public static void main(String[] args) throws Exception {
        // Use MutableCapabilities to support W3C capabilities format
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("platformName", "Windows 10");
        capabilities.setCapability("browserVersion", "latest");

        // LambdaTest specific capabilities
        MutableCapabilities lambdaTestOptions = new MutableCapabilities();
        lambdaTestOptions.setCapability("build", "LambdaTest Java Sample");
        lambdaTestOptions.setCapability("name", "TestNG Integration Test");

        // Add LambdaTest options as a nested capability
        capabilities.setCapability("LT:Options", lambdaTestOptions);

        // Initialize the remote WebDriver with the capabilities
        WebDriver driver = new RemoteWebDriver(new URL(URL), capabilities);

        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
