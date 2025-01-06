package com.automation.utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.automation.reports.ExtentTestNGListener;
import com.automation.reports.ExtentTestNGListenerAPI;


public class AssertionUtils extends Assert {

	/**
	 * Asserts that two Strings are equal. If they are not, an AssertionError, with
	 * the given message, is thrown.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @param message  the assertion error message
	 */
	public static void assertEquals(String actual, String expected, String message, WebDriver driver) {
		assertEquals((Object) actual, (Object) expected, expected+" Value is not same as "+actual+" Value"+message);
		ExtentTestNGListener.testLog.get().info(expected+" Value is same as "+actual+" Value"+message);
		ScreenshotUtils.captureScreenshot(driver, expected+" Value is same as "+actual+" Value"+message);
	}

	public static void assertNotEquals(String actual, String expected) {
		ExtentTestNGListener.testLog.get().info(expected+" Value is not same as "+actual+" Value");
		assertEquals(actual, expected, null);
	}

	public static void assertNotEquals(String expected, String actual, String message,WebDriver driver) {
		assertNotEquals((Object) expected, (Object) actual, expected+" Value is not same as "+actual+" Value"+message);
		ExtentTestNGListener.testLog.get().info(expected+" Value is not same as "+actual+" Value"+message);
		ScreenshotUtils.captureScreenshot(driver, expected+" Value is not same as "+actual+" Value"+message);
	}

	/**
	 * Asserts that two Strings are equal. If they are not, an AssertionError is
	 * thrown.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 */
	public static void assertEquals(String actual, String expected) {
		ExtentTestNGListener.testLog.get().info(expected+" Value is  same as "+actual+" Value");
		assertEquals(actual, expected, null);
	}
	
	/**
	 * Asserts that given value is true or not. 
	 * thrown.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 */
	public static void assertEquals(boolean value, String message) {
		if(value == true) {
			ExtentTestNGListenerAPI.testLog.get().info("Successfull"+message);
		}else {
			ExtentTestNGListenerAPI.testLog.get().info("Failed for procession"+message);
		}
	}
}
