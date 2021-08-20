// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.util;

import java.security.SecureRandom;
import java.util.List;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class CCUtils {

	private static final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#";
	private static final SecureRandom random = new SecureRandom();

	public static void verifyAndWaitForElement(WebElement we, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new WaitForWEIsDisplayed(we));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void assertEquals(Object actual, Object expected) throws RuntimeException {
		try {
			Assert.assertEquals(actual, expected);
			Log4jUtil.log(
					"Expected value: " + expected.toString() + " Actual value: " + actual.toString() + " - PASSED");
		} catch (Exception e) {
			Log4jUtil.log(
					"Expected value: " + expected.toString() + " Actual value: " + actual.toString() + " - FAILED");
			throw new RuntimeException(e);
		}
	}

	public static void assertTrue(boolean condition, String message) throws RuntimeException {
		try {
			Assert.assertTrue(condition);
			Log4jUtil.log("Expected value: true" + " Actual value: " + condition + " - PASSED", "\n" + message);
		} catch (Exception e) {
			Log4jUtil.log("Expected value: true" + " Actual value: " + condition + " - FAILED", "\n" + message);
			throw new RuntimeException(e);
		}
	}

	public static void assertNotNull(Object object, String message) throws RuntimeException {
		try {
			Assert.assertNotNull(object, message);
			Log4jUtil.log("Object Value is : {} - PASSED," + object.toString());
		} catch (Exception e) {
			Log4jUtil.log("FAILED {}", message);
			throw new RuntimeException(e);
		}
	}

	public static void selectFromDropDown(WebElement webElement, String containsText) {
		Select select = new Select(webElement);
		List<WebElement> allOptions = select.getOptions();
		for (WebElement option : allOptions) {
			if (option.getText().contains(containsText)) {
				select.selectByVisibleText(option.getText());
			}
		}
	}

	public static void responseTimeValidation(Response response) {
		long time = response.time();
		Log4jUtil.log("Response time " + time);
		ValidatableResponse valRes = response.then();
		valRes.time(Matchers.lessThan(6000L));
		Log4jUtil.log("Response Time in ms- " + time);
	}

	public static String generateRandomString() {

		while (true) {
			char[] newValue = new char[random.nextBoolean() ? 12 : 13];
			boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
			for (int i = 0; i < newValue.length; i++) {
				char ch = symbols.charAt(random.nextInt(symbols.length()));
				if (Character.isUpperCase(ch))
					hasUpper = true;
				else if (Character.isLowerCase(ch))
					hasLower = true;
				else if (Character.isDigit(ch))
					hasDigit = true;
				else
					hasSpecial = true;
				newValue[i] = ch;
			}
			if (hasUpper && hasLower && hasDigit && hasSpecial) {
				return new String(newValue);
			}
		}
	}

}
