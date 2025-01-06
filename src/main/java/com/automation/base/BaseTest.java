package com.automation.base;



import com.automation.reports.ExtentTestNGListener;
import com.automation.utils.ConfigReader;
import com.automation.utils.WebDriverUtils;



import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

public class BaseTest extends ExtentTestNGListener {

    protected static WebDriver driver;
    
     
    public WebDriver getDriver() {
        return this.driver;
    }
    
    public static WebDriver setupTest() {
    	String browserName = ConfigReader.getProperty("browser");
    	String executeLambda = ConfigReader.getProperty("executeInLambda");
    	log.info("Performing browser setup");
    	if(executeLambda.equalsIgnoreCase("true")) {
    		 String USERNAME = ConfigReader.getProperty("LTUserName");
    	        String ACCESS_KEY = ConfigReader.getProperty("LTAccessKey");
    	        String URL = "https://"+ USERNAME + ":" + ACCESS_KEY + "@hub.lambdatest.com/wd/hub";
    	        String OSVersion = ConfigReader.getProperty("OSVersion");
    	        
    	        // Use MutableCapabilities to support W3C capabilities format
    	        MutableCapabilities capabilities = new MutableCapabilities();
    	        capabilities.setCapability("browserName", browserName);
    	        capabilities.setCapability("platformName", OSVersion);
    	        capabilities.setCapability("browserVersion", "latest");

    	        // LambdaTest specific capabilities
    	        MutableCapabilities lambdaTestOptions = new MutableCapabilities();
    	        lambdaTestOptions.setCapability("build", "LambdaTest Java Sample");
    	        lambdaTestOptions.setCapability("name", "TestNG Integration Test");

    	        // Add LambdaTest options as a nested capability
    	        capabilities.setCapability("LT:Options", lambdaTestOptions);
    	        try {
    	        	 driver = new RemoteWebDriver(new URL(URL), capabilities);
    	        }
    	        catch(MalformedURLException e){
    	        	log.error(e);
    	        }
    	       
    	}else {
    		 // Initialize the driver based on browser name
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    log.info("Iniatializing Chrome driver"); 
//                    ChromeOptions chromeOptions = new ChromeOptions();
//                    chromeOptions.addArguments("--headless"); // Run browser in headless mode
//                    chromeOptions.addArguments("--start-maximized");
                    
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
//                    firefoxOptions.addArguments("--headless"); // Run browser in headless mode
                    firefoxOptions.addArguments("--start-maximized");
                    driver = new FirefoxDriver();
                    log.info("Iniatializing firefox driver");
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
//                    edgeOptions.addArguments("--headless"); // Run browser in headless mode
                    edgeOptions.addArguments("--start-maximized");
                    driver = new EdgeDriver();
                    log.info("Iniatializing edge driver");
                    break;
                default:
                    throw new RuntimeException("Invalid browser name specified in config file");
            }
            log.info("Iniatialized driver");
            driver.manage().window().maximize();
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    	}
        return driver;
    }

    
    
    public static WebDriver executeLambdaTest() throws MalformedURLException {
               
        return driver;
    }
    
    @AfterSuite
    public void tearDown(ITestResult result) {
        // Close the browser after each test
        WebDriverUtils.closeBrowser(driver);
    }


    
    

	
}
