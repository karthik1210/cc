// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.StaleElementReferenceException;

public class CCUtils {
    public static int repeat = 0;
    private static final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#";
    private static final SecureRandom random = new SecureRandom();
    private static String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";
    private static String alphaNumericStringIncludingSpecialCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
            + "abcdefghijklmnopqrstuvxyz" + ".,!@#$%&*_-";
    private static String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
    public static SoftAssert softAssert = new SoftAssert();
    private static String numericString =  "0123456789";
    private static String alphaNumericStringWithoutNumbersAndSpecialCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";

    public static void verifyAndWaitForElement(WebElement we, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(new WaitForWEIsDisplayed(we));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void verifyElementToBeClickable(WebElement we, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.invisibilityOf(we));
            wait.until(ExpectedConditions.elementToBeClickable(we));
        } catch (Exception exception) {
            verifyAndWaitForElement(we, driver);
        }
    }


    public static void verifyAndWaitForElementPresence(WebElement we, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(we));
            wait.until(ExpectedConditions.stalenessOf(we));
        } catch (Exception exception) {
            verifyAndWaitForElement(we, driver);
        }
    }

    public static void verifyAndWaitForElementPresenceForInterfaceSetup(String externalSystemName, WebElement we, WebDriver driver) {
        while (repeat <= 5) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+externalSystemName+"']/../../following-sibling::td//mat-icon[@mattooltip='More Options']"))));
                wait.until(ExpectedConditions.stalenessOf(we));
                wait.until(ExpectedConditions.visibilityOf(we));
                break;
            } catch (StaleElementReferenceException exception) {
				//verifyAndWaitForElementPresenceForInterfaceSetup(externalSystemName, we, driver);
            }
            repeat++;
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

    public static void verifyAndWaitForTableList(WebElement we, WebDriver driver) {
        try {
            Log4jUtil.log("webdriverwait initiated");
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='table']"))));
            Log4jUtil.log("webdriverwait Completed and tableClientTypeInSearchResult validation");
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(we)));
        } catch (StaleElementReferenceException e) {
            verifyAndWaitForTableList(we, driver);
        }
    }

    public static void verifyAndWaitForUserTableList(WebElement we, WebDriver driver) {
        try {
            Log4jUtil.log("webdriverwait initiated");
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@role='grid']"))));
            Log4jUtil.log("webdriverwait Completed and tableClientTypeInSearchResult validation");
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(we)));
        } catch (StaleElementReferenceException e) {
            verifyAndWaitForUserTableList(we, driver);
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

    public static void assertEqualsNull(Object actual, Object expected) throws RuntimeException {
        try {
            Assert.assertEquals(actual, expected);
            Log4jUtil.log("Expected value: " + (expected == null ? null : expected.toString()) + " Actual value: "
                    + (actual == null ? null : actual.toString()) + " - PASSED");
        } catch (Exception e) {
            Log4jUtil.log("Expected value: " + (expected == null ? null : expected.toString()) + " Actual value: "
                    + (actual == null ? null : actual.toString()) + " - FAILED");
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

    public static String generateRandomStringWithoutSymbolsOrNumbers(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (alphaNumericStringWithoutNumbersAndSpecialCharacters.length() * Math.random());
            sb.append(alphaNumericStringWithoutNumbersAndSpecialCharacters.charAt(index));
        }
        return sb.toString();
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
        String mob = generateNumericString(10);
        mob = mob.toString().replace('0','9');
        return Long.parseLong(mob);
    }

    public static String generateNewEmail(int n) {
        String email = generateAlphaNumericString(n).concat("@nextgen.com");
        String[] emailList = email.split("@");
        int domainNameSize = emailList[1].length();
        if (emailList[0].length() <= 64 && domainNameSize <= 98) {
            if (email.length() <= 100) {
                return email;
            } else {
                int difference = email.length() - 100;
                return email.replaceAll(email.substring(0, difference), "").trim();
            }
        } else {
            int difference = emailList[0].length() - 64;
            String finalEmail = email.replaceAll(email.substring(0, difference), "").trim();
            if (finalEmail.length() <= 100) {
                return finalEmail;
            } else {
                int diff = finalEmail.length() - 100;
                return finalEmail.replaceAll(finalEmail.substring(0, diff), "").trim();
            }
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

    public static String verifyCharacterIsNumberOrDigit(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (Character.isDigit(data.charAt(i))) {
                return "number";
            } else if (Character.isLetter(data.charAt(i))) {
                return "letter";
            } else if (Character.isLetter(data.charAt(i)) && Character.isDigit(data.charAt(i))) {
                return "numberletter";
            } else if (specialCharactersString.contains(Character.toString(data.charAt(i)))) {
                return "alphanumeric";
            }
        }
        return "InvalidData";
    }

    public static String dateformatter() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        Log4jUtil.log(formatter.format(date));
        return formatter.format(date);
    }

    public static void clearTextboxField(WebElement element) {
        element.sendKeys(Keys.CONTROL, "a");
        element.sendKeys(Keys.BACK_SPACE);
    }

    public static void pageRefresh(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static void mouseHoverOverTheElement(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        long end = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < end) {
            action.moveToElement(element).pause(3000).build().perform();
        }
    }

    public static void assertNotEquals(Object actual, Object expected) throws RuntimeException {
        try {
            Assert.assertNotEquals(actual, expected);
            Log4jUtil.log(
                    "Expected value: " + expected.toString() + " Actual value: " + actual.toString() + " - PASSED");
        } catch (Exception e) {
            Log4jUtil.log(
                    "Expected value: " + expected.toString() + " Actual value: " + actual.toString() + " - FAILED");
            throw new RuntimeException(e);
        }
    }

    public static String reverseCase(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                chars[i] = Character.toLowerCase(c);
            } else if (Character.isLowerCase(c)) {
                chars[i] = Character.toUpperCase(c);
            }
        }
        return new String(chars);
    }

    public static String generateStringWithSpace(String text) {
        String stringWithSpace = text.replaceAll("................................(?!$)", "$0 ");
        return stringWithSpace;
    }

    public static String getName(String name) {
        int size = name.trim().length();
        if (size <= 100) {
            return name.trim();
        } else {
            int difference = size - 100;
            return name.replaceAll(name.substring(size - difference), "").trim();
        }
    }

    public static String getSapNumber(String sapNumber) {
        int size = sapNumber.trim().length();
        if (size <= 50) {
            return sapNumber.trim();
        } else {
            int difference = size - 50;
            return sapNumber.replaceAll(sapNumber.substring(size - difference), "").trim();
        }
    }

    public static void performDoubleClick(WebElement element, WebDriver driver) {
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();
    }

    public static void performMoveCursor(WebElement element, WebDriver driver) {
        Actions action = new Actions(driver);
        action.moveByOffset(20, 150).click().build().perform();
    }

    public static void hover(WebElement element, WebDriver driver) throws AWTException {
        long end = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < end) {
            Point coordinates = element.getLocation();
            int xcord = coordinates.getX();
            int ycord = coordinates.getY();
            Robot robot = new Robot();
            robot.mouseMove(xcord, ycord + 100);
            robot.setAutoDelay(2000);
            robot.mouseMove(xcord + 6, ycord + 135);
            robot.setAutoDelay(2000);
            robot.mouseMove(xcord + 6, ycord + 135);

        }
    }

    public static Long generateinvalidMobileNumber() {
        return (long) (Math.random() * 100000 + 33330000000L);
    }

    public static String generateAuthId() {

        String authId = UUID.randomUUID().toString();
        return authId;
    }

    public static void focusOnElement(WebElement element, WebDriver driver) {
        new Actions(driver).moveToElement(element).click().perform();
    }

    public String getDefaultItemsPerPageOfWebPage(WebElement ele, WebDriver driver) throws InterruptedException {
        CCUtils.verifyAndWaitForElement(ele, driver);
        return ele.getText().toString();
    }

    public static String getDefaultPaginationOfWebPage(WebElement ele, WebDriver driver) {
        String s1 = ele.getText().toString();
        String[] words = s1.split("of", 0);
        String data = null;
        for (String w : words) {
            data = w.trim();
            Log4jUtil.log("Print the value of default Pagination : " + w.trim());
            break;
        }
        return data.trim();
    }

    public static String getTotalNoOfClientsFromPaginationSection(WebElement ele, WebDriver driver) {
        String s1 = ele.getText().toString();
        String[] words = s1.split("of", 0);
        String data = null;
        for (String w : words) {
            data = w.trim();
            Log4jUtil.log("Print the value of default Pagination : " + w.trim());
        }
        return data.trim();
    }

    public String splitingPagination(WebElement ele, WebDriver driver) {
        CCUtils.verifyAndWaitForElement(ele, driver);
        String s1 = ele.getText().toString();
        String[] words = s1.split("\\s", 3);
        String data = null;
        for (String w : words) {
            data = w.trim();
        }
        Log4jUtil.log("Print the value of default Pagination : " + data.trim());
        return data.trim();
    }

    public String getNoOfRowsPagination(WebElement ele, WebDriver driver) {
        String data = splitingPagination(ele, driver);
        String noOfRowsPagination = data.substring(0, 2).trim();
        return noOfRowsPagination.trim();
    }

    public String getLHSPaginationOfWebPage(WebElement ele, WebDriver driver) throws AWTException {
        String data = getDefaultPaginationOfWebPage(ele, driver);
        String[] words = data.split("–", 2);
        for (String w : words) {
            data = w.trim();
            break;
        }
        Log4jUtil.log("Print the value of LHS Pagination : " + data.trim());
        return data.trim();
    }

    public static String getRHSPaginationOfWebPage(WebElement ele, WebDriver driver) throws AWTException {
        String data = getDefaultPaginationOfWebPage(ele, driver);
        String[] words = data.split("–", 0);
        for (String w : words) {
            data = w.trim();
        }
        Log4jUtil.log("Print the value of RHS Pagination : " + data.trim());
        return data.trim();
    }

    public static boolean isPaginationButtonDisabled(WebElement ele, WebDriver driver) {
        CCUtils.verifyAndWaitForElement(ele, driver);
        Log4jUtil.log("Verifying the visiblity of " + ele.getAttribute("aria-label") + " Button.");
        boolean buttonStatus = ele.isEnabled();
        Log4jUtil.log(
                "Print the value of " + ele.getAttribute("aria-label") + "Button visibility status : " + buttonStatus);
        return buttonStatus;
    }

    public static void scrollPageViaElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void verifyForElementToBeClickable(WebElement we, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(we));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void verifyAndWaitForPresenceOfListOfElement(List<WebElement> web, WebDriver driver) {
        for (int i = 0; i < web.size(); i++) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//tbody[@role='rowgroup']//tr[@id='locationList']//td[1]")));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void scrollPageToStart() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_PAGE_UP);
        robot.keyRelease(KeyEvent.VK_PAGE_UP);
    }

    public static String generateNumericString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (numericString.length() * Math.random());
            sb.append(numericString.charAt(index));
        }
        return sb.toString();
    }

}