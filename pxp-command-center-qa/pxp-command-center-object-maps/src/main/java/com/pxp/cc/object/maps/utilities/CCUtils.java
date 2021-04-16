package com.pxp.cc.object.maps.utilities;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;

public class CCUtils {

	public static void verifyAndWaitForElement(WebElement we, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new WaitForWEIsDisplayed(we));
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

	public static boolean verifyTextNotPresent(WebDriver driver, String value) {
		return !driver.getPageSource().contains(value);
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
}
