// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	private static String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
			+ "abcdefghijklmnopqrstuvxyz";
	private static String alphaNumericStringIncludingSpecialCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
			+ "abcdefghijklmnopqrstuvxyz" + ".,!@#$%&*_-";

	public static void verifyAndWaitForElement(WebElement we, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new WaitForWEIsDisplayed(we));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void verifyAndWaitForListOfElement(List<WebElement> web, WebDriver driver) {
		for (int i = 0; i < web.size(); i++) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(new WaitForWEIsDisplayed(web.get(i)));
			try {
				Thread.sleep(2300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		valRes.time(Matchers.lessThan(21000L));
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

	public static void pressEnterKey() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Log4jUtil.log("Keyboard Action : Enter key ");
	}

	public static String generateAlphaNumericString(int n) {
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (alphaNumericString.length() * Math.random());
			sb.append(alphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	public static Long generateMobileNumber() {
		return (long) (Math.random() * 1000000 + 3333000000L);
	}

	public static String generateNewEmail(int n) {
		String email = generateAlphaNumericString(n).concat("@nextgen.com");
		String[] emailList = email.split("@");
		int domainNameSize = emailList[1].length();
		if (n <= 100 && n > domainNameSize) {
			int size = email.length();
			if (size <= n) {
				return email;
			} else {
				int difference = size - n;
				return email.replaceAll(email.substring(0, difference), "").trim();
			}
		} else {
			return email;
		}
	}

	public static String generateAlphaNumericStringIncludingSpecialChar(int n) {
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (alphaNumericStringIncludingSpecialCharacters.length() * Math.random());
			sb.append(alphaNumericStringIncludingSpecialCharacters.charAt(index));
		}
		return sb.toString();
	}

	public static void scrollPageToEnd() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}

	public static String dateformatter() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		Log4jUtil.log(formatter.format(date));
		return formatter.format(date);
	}
}
