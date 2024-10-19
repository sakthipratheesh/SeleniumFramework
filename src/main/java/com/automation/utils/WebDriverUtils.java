package com.automation.utils;

import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.reports.ExtentTestNGListener;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WebDriverUtils {

	
    private static WebDriver driver;
    private static final int TIMEOUT = 10;  // Default timeout for waits

    public static ExtentTestNGListener report = new ExtentTestNGListener();
    // Constructor to initialize the WebDriver
    public WebDriverUtils(WebDriver driver) {
    	this.driver = driver;
    }

    // Wait for element visibility
    public static WebElement waitForElementVisible(WebDriver driver,By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        try {
        	report.logStepWithScreenshot(driver, "Checking for the visibility of the element");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element: " + locator.toString());
            throw e;
        }
    }

    // Click an element with retries for stale element exception
    public static void clickElement(WebDriver driver,By locator, String buttonName) {
        int retries = 3;
        while (retries > 0) {
            try {
            	report.logStepWithScreenshot(driver, "Performing element click");
                waitForElementClickable(driver, locator).click();
                ScreenshotUtils.captureScreenshot(driver, "ClickElement_"+buttonName);
                break;
            } catch (StaleElementReferenceException e) {
            	report.logStepWithScreenshot(driver, "Performing retry element click for stale element reference");
                System.out.println("StaleElementReferenceException caught, retrying...");
                retries--;
            } catch (ElementClickInterceptedException e) {
                System.out.println("ElementClickInterceptedException: unable to click element. " + locator.toString());
                throw e;
            }
        }
    }

    // Wait for element to be clickable
    public static WebElement waitForElementClickable(WebDriver driver,By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        try {
        	report.logStepWithScreenshot(driver, "Checking the clickability of the element");
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element to be clickable: " + locator.toString());
            throw e;
        }
    }

    // Send text to input field with stale element handling
    public static void sendKeysToElement(WebDriver driver,By locator, String text) {
        int retries = 3;
        while (retries > 0) {
            try {
                WebElement element = waitForElementVisible(driver, locator);
                element.clear();
                element.sendKeys(text);
                report.logStepWithScreenshot(driver, "Performing Send keys");
                ScreenshotUtils.captureScreenshot(driver, "SendKeysToElement_"+text);
                
                break;
            } catch (StaleElementReferenceException e) {
            	report.logStepWithScreenshot(driver, "Performing retry element text entering for stale element reference");
                System.out.println("StaleElementReferenceException caught, retrying...");
                retries--;
            }
        }
    }

    // Get text from an element
    public static String getElementText(WebDriver driver,By locator) {
        try {
            return waitForElementVisible(driver, locator).getText();
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: Unable to find element: " + locator.toString());
            throw e;
        }
    }

    // Select from dropdown by visible text
    public static void selectDropdownByVisibleText(WebDriver driver,By locator, String visibleText) {
        try {
        	report.logStepWithScreenshot(driver, "Performing select dropdown value choosing");
            WebElement dropdownElement = waitForElementVisible(driver, locator);
            Select select = new Select(dropdownElement);
            select.selectByVisibleText(visibleText);
            ScreenshotUtils.captureScreenshot(driver, "SelectDropdown_");

        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: Unable to find element: " + locator.toString());
            throw e;
        }
    }
    
    // Select from dropdown by Index
    public static void selectDropdownByIndex(WebDriver driver,By locator, Integer index) {
        try {
        	report.logStepWithScreenshot(driver, "Performing select dropdown value choosing");
            WebElement dropdownElement = waitForElementVisible(driver, locator);
            Select select = new Select(dropdownElement);
            select.selectByIndex(index);
            ScreenshotUtils.captureScreenshot(driver, "SelectDropdown_");

        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: Unable to find element: " + locator.toString());
            throw e;
        }
    }

    
    // Select from dropdown by value
    public static void selectDropdownByValue(WebDriver driver,By locator, String value) {
        try {
        	report.logStepWithScreenshot(driver, "Performing select dropdown value choosing");
            WebElement dropdownElement = waitForElementVisible(driver, locator);
            Select select = new Select(dropdownElement);
            select.selectByValue(value);
            ScreenshotUtils.captureScreenshot(driver, "SelectDropdown_");

        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: Unable to find element: " + locator.toString());
            throw e;
        }
    }
    
    
    
    // Handle list of elements and return a list of WebElements
    public static List<WebElement> findElements(WebDriver driver,By locator) {
        try {
            return driver.findElements(locator);
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: Unable to find elements: " + locator.toString());
            throw e;
        }
    }

    // Check if an element exists
    public static boolean isElementPresent(WebDriver driver,By locator) {
        try {
            driver.findElement(locator);
            report.logStepWithScreenshot(driver, "Checking the element is present or not");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Scroll to an element
    public static void scrollToElement(WebDriver driver,By locator) {
        try {
        	report.logStepWithScreenshot(driver, "Performing scroll to element using JavaScript executor");
            WebElement element = waitForElementVisible(driver, locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (NoSuchElementException e) {
            System.out.println("Unable to scroll to element: " + locator.toString());
            throw e;
        }
    }

    // Close the browser
    public static void closeBrowser(WebDriver driver) {
        if (driver != null) {
        	report.logStepWithScreenshot(driver, "Quitting the browser");
        	driver.close();
            driver.quit();
        }
    }

    // Window Handling: Switch to a window by title
    public static void switchToWindowByTitle(WebDriver driver,String windowTitle) {
        String currentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            driver.switchTo().window(window);
            if (driver.getTitle().equals(windowTitle)) {
            	report.logStepWithScreenshot(driver, "Performing switch to window ");
            	 ScreenshotUtils.captureScreenshot(driver, "SwitchToWindow_");
                 
                return; // Found the window with the matching title
            }
        }
        driver.switchTo().window(currentWindow); // Switch back to original if not found
    }

    // Window Handling: Switch to a window by index
    public static void switchToWindowByIndex(WebDriver driver,int index) {
        try {
            Set<String> windows = driver.getWindowHandles();
            List<String> windowList = new ArrayList<>(windows);
            report.logStepWithScreenshot(driver, "Performing switch to window ");
            driver.switchTo().window(windowList.get(index));
            
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such window index exists: " + index);
            throw e;
        }
    }

    // Alert Handling: Accept alert
    public static void acceptAlert(WebDriver driver) {
        try {
        	report.logStepWithScreenshot(driver, "Performing alert accept ");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert found to accept");
            throw e;
        }
    }

    // Alert Handling: Dismiss alert
    public static void dismissAlert(WebDriver driver) {
        try {
        	report.logStepWithScreenshot(driver, "Performing alert dismiss ");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert found to dismiss");
            throw e;
        }
    }

    // Alert Handling: Get alert text
    public static String getAlertText(WebDriver driver) {
        try {
        	report.logStepWithScreenshot(driver, "Performing fetching text from alert ");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            wait.until(ExpectedConditions.alertIsPresent());
            return driver.switchTo().alert().getText();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert found");
            throw e;
        }
    }

    // Shadow DOM Handling: Get shadow root using JavaScript
    public static WebElement getShadowRoot(WebDriver driver,By locator) {
        WebElement shadowHost = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (WebElement) js.executeScript("return arguments[0].shadowRoot", shadowHost);
    }

    // Shadow DOM Handling: Find an element in shadow DOM
    public static WebElement findElementInShadowDom(WebDriver driver,By shadowHostLocator, By elementLocator) {
        WebElement shadowRoot = getShadowRoot(driver, shadowHostLocator);
        return shadowRoot.findElement(elementLocator);
    }

    // Shadow DOM Handling: Send keys to shadow DOM element
    public static void sendKeysToShadowDomElement(WebDriver driver,By shadowHostLocator, By elementLocator, String text) {
        WebElement element = findElementInShadowDom(driver, shadowHostLocator, elementLocator);
        element.clear();
        element.sendKeys(text);
    }

    // Frame Handling: Switch to a frame by index
    public static void switchToFrameByIndex(int index) {
        try {
        	report.logStepWithScreenshot(driver, "Performing frame switch");
            driver.switchTo().frame(index);
        } catch (NoSuchFrameException e) {
            System.out.println("No such frame found by index: " + index);
            throw e;
        }
    }

    // Frame Handling: Switch to a frame by name or ID
    public static void switchToFrameByNameOrId(String nameOrId) {
        try {
        	report.logStepWithScreenshot(driver, "Performing frame switch");
            driver.switchTo().frame(nameOrId);
        } catch (NoSuchFrameException e) {
            System.out.println("No such frame found by name or ID: " + nameOrId);
            throw e;
        }
    }

    // Frame Handling: Switch to a frame by WebElement
    public static void switchToFrameByElement(By locator) {
        try {
        	report.logStepWithScreenshot(driver, "Performing frame switch");
            WebElement frameElement = waitForElementVisible(driver, locator);
            driver.switchTo().frame(frameElement);
        } catch (NoSuchFrameException | NoSuchElementException e) {
            System.out.println("No such frame or element found: " + locator.toString());
            throw e;
        }
    }

    // Frame Handling: Switch back to the main document
    public static void switchToDefaultContent() {
    	report.logStepWithScreenshot(driver, "Performing frame switch to parent frame");
        driver.switchTo().defaultContent();
    }
}
