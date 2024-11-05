package com.automation.reports;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.automation.base.BaseTest;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent;
//    public static ExtentTest test;
    public static ThreadLocal<ExtentTest> testLog = new ThreadLocal<>(); // Use ThreadLocal

    protected static final Logger log = LogManager.getLogger(ExtentTestNGListener.class); // Logger initialized

    @Override
    public void onStart(ITestContext context) {
    	
        ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReportTestNG.html");
        extent = new ExtentReports();
        extent.setSystemInfo("Host Name", "Sakthi");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Sakthipratheesh");
        extent.attachReporter(spark);
        Reporter.log("Host Name, Sakthi");
        Reporter.log("Environment , QA");
        Reporter.log("User name :  Sakthipratheesh");

        log.info("Extent Reports initialized for the test suite: " + context.getName());
        Reporter.log("Extent Reports initialized for the test suite: " + context.getName());
        
    }

    @Override
    public void onTestStart(ITestResult result) {
    	ExtentTest  test = extent.createTest(result.getMethod().getMethodName());
    	Reporter.log("Test started: " +result.getMethod().getMethodName());
        log.info("Test started: " + result.getMethod().getMethodName());
        testLog.set(test);  // Set test in ThreadLocal
        testLog.get().info("Test started: " + result.getMethod().getMethodName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testLog.get().pass("Test Passed: " + result.getMethod().getMethodName());
        log.info("Test Passed: " + result.getMethod().getMethodName());
        Reporter.log("Test Passed: " + result.getMethod().getMethodName());
        try {
            captureScreenshot(result);
        } catch (IOException e) {
            log.error("Error capturing screenshot on test success: " + e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testLog.get().fail("Test Failed: " + result.getMethod().getMethodName());
        testLog.get().fail(result.getThrowable());
        log.error("Test Failed: " + result.getMethod().getMethodName(), result.getThrowable());
        Reporter.log("Test Failed: " + result.getMethod().getMethodName() +"  "+result.getThrowable());
        try {
            captureScreenshot(result);
        } catch (IOException e) {
            log.error("Error capturing screenshot on test failure: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testLog.get().skip("Test Skipped: " + result.getMethod().getMethodName());
        log.warn("Test Skipped: " + result.getMethod().getMethodName());
        Reporter.log("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("All tests completed for the test suite: " + context.getName());
        Reporter.log("All tests completed for the test suite: " + context.getName());
    }

    public void captureScreenshot(ITestResult result) throws IOException {
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + result.getMethod().getMethodName() + ".png";
        File dest = new File(screenshotPath);
        FileUtils.copyFile(src, dest);

        testLog.get().addScreenCaptureFromPath(screenshotPath);
        log.info("Screenshot captured for test: " + result.getMethod().getMethodName());
        Reporter.log("Screenshot captured for test: " + result.getMethod().getMethodName());
    }
    
    public void logInfo(String stepDescription) {
	    testLog.get().info(stepDescription);
	    Reporter.log(stepDescription);
	}
    public void logStepWithScreenshot(WebDriver driver, String stepDescription) {
	    String screenshotPath = ScreenshotUtils.captureScreenshot(driver, stepDescription);
	    testLog.get().info(stepDescription+" : "+"<a href='" + screenshotPath + "' target='_blank'>Screenshot</a>");
	    Reporter.log(stepDescription+" : "+"<a href='" + screenshotPath + "' target='_blank'>Screenshot</a>");
	}
}
