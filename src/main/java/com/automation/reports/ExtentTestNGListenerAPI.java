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
import com.automation.base.BaseTest;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentTestNGListenerAPI implements ITestListener {

    private static ExtentReports extent;
//    public static ExtentTest test;
    public static ThreadLocal<ExtentTest> testLog = new ThreadLocal<>(); // Use ThreadLocal

    protected static final Logger log = LogManager.getLogger(ExtentTestNGListenerAPI.class); // Logger initialized

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReportTestNG.html");
        extent = new ExtentReports();
        extent.setSystemInfo("Host Name", "Sakthi");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Sakthipratheesh");
        extent.attachReporter(spark);

        log.info("Extent Reports initialized for the test suite: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
    	ExtentTest  test = extent.createTest(result.getMethod().getMethodName());
        log.info("Test started: " + result.getMethod().getMethodName());
        testLog.set(test);  // Set test in ThreadLocal
        testLog.get().info("Test started: " + result.getMethod().getMethodName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testLog.get().pass("Test Passed: " + result.getMethod().getMethodName());
        log.info("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testLog.get().fail("Test Failed: " + result.getMethod().getMethodName());
        testLog.get().fail(result.getThrowable());
        log.error("Test Failed: " + result.getMethod().getMethodName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testLog.get().skip("Test Skipped: " + result.getMethod().getMethodName());
        log.warn("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("All tests completed for the test suite: " + context.getName());
    }
    
    public void logInfo(String stepDescription) {
	    testLog.get().info(stepDescription);
	}
}
