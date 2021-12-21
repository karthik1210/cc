package com.pxp.cc.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.intuit.ifs.csscat.core.pojo.ExpectedEmail;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class YopMailUtility {

	private static final int TIME_TO_WAIT_MS = 10000;

	protected WebDriver driver;
	protected JavascriptExecutor jse;

	// Code starts
	public YopMailUtility(WebDriver driver) {
		this.driver = driver;
		jse = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public String getLinkFromEmail(String username, String emailSubject, String findInEmail, int retries)
			throws InterruptedException {

		for (int j = 1; j <= retries; j++) {
			WebElement links = getEmailElements(username, emailSubject, findInEmail);
			if (links != null) {
				String href = links.getAttribute("href");
				if (!href.isEmpty()) {
					Log4jUtil.log("URL found: " + href);
					return href;
				}
			}
			logAttemptAndSleep(j, retries);
		}
		return null;
	}

	private WebElement getEmailElements(String username, String emailSubject, String findInEmail) {

		driver.get("https://yopmail.com/en/");
		driver.manage().window().maximize();
		String email, emailContent;
		try {
			WebElement d = driver.findElement(By.xpath("//input[@id='login']"));
			Thread.sleep(1000);
			d.clear();
			Thread.sleep(1000);
			d.sendKeys(username);
			Thread.sleep(1000);

			WebElement goInboxButton = driver.findElement(By.xpath("//button[@title='Check Inbox @yopmail.com']"));
			goInboxButton.click();
			Thread.sleep(1000);
			driver.switchTo().frame("ifinbox");
			List<WebElement> mailList = driver.findElements(By.xpath("//div[@class='m']/button/div[2]"));
			int l = mailList.size();
			if (l > 0) {
				Thread.sleep(2000);
				for (int i = 0; i < l; i++) {
					jse.executeScript("arguments[0].scrollIntoView(true);", mailList.get(i));
					email = mailList.get(i).getText();

					if (email.contains(emailSubject)) {
						mailList.get(i).click();
						Thread.sleep(2000);
						driver.switchTo().defaultContent();
						Thread.sleep(2000);
						driver.switchTo().frame("ifmail");
						List<WebElement> mailLinks = driver
								.findElements(By.xpath(" //*[contains(@id,'mail')]//tr//a"));
						for (int j = 0; j < mailLinks.size(); j++) {
							emailContent = mailLinks.get(j).getText();
							if (emailContent.contains(findInEmail)) {
								return mailLinks.get(j);
							}
						}

					}

				}
			}

		} catch (Exception e) {
			Log4jUtil.log("unable to find links in yopmail" + e);
		}

		return null;
	}

	public boolean isMessageInInbox(String username, String emailSubject, String findInEmail, int retries)
			throws Exception {

		for (int j = 1; j <= retries; j++) {
			if (getEmailElements(username, emailSubject, findInEmail) != null) {
				return true;
			}
			logAttemptAndSleep(j, retries);
		}
		return false;
	}

	public boolean isMessageInInbox(ExpectedEmail email, int retries) throws Exception {
		if (email == null) {
			throw new IllegalArgumentException("Email is null");
		}
		return isMessageInInbox(email.getRecipient(), email.getSubject(), email.getEmailContentPart(), retries);
	}

	/**
	 * This method waits $retries times for all emails, not for everyone separately.
	 * After every retry, there is sleep for couple of seconds.
	 * 
	 * @param emails
	 * @param retries
	 * @return true if all emails were received, otherwise false
	 * @throws Exception
	 */
	public boolean areAllMessagesInInbox(List<ExpectedEmail> emails, int retries) throws Exception {

		if (emails == null) {
			throw new IllegalArgumentException("Emails are null");
		}

		int expectedEmails = emails.size();
		List<ExpectedEmail> received = new ArrayList<ExpectedEmail>();
		for (int i = 1; i <= retries; i++) {
			Iterator<ExpectedEmail> iterator = emails.iterator();
			while (iterator.hasNext()) {
				ExpectedEmail e = iterator.next();
				if (isMessageInInbox(e, 1)) {
					received.add(e);
					iterator.remove();
				}
			}
			if (received.size() == expectedEmails) {
				Log4jUtil.log("All emails were received");
				return true;
			}
			logAttemptAndSleep(i, retries, true);
		}
		Log4jUtil
				.log("Emails received: " + received.size() + "/" + expectedEmails + ". Not received emails: " + emails);
		return false;
	}

	public boolean getEmailContent(String username, String emailSubject, String findInEmailContent, int retries)
			throws Exception {

		driver.get("https://yopmail.com/en/");
		driver.manage().window().maximize();
		String email, emailContent;
		boolean ExpectedContentExist = false;
		try {
			WebElement d = driver.findElement(By.xpath("//input[@id='login']"));
			Thread.sleep(1000);
			d.clear();
			Thread.sleep(1000);
			d.sendKeys(username);
			Thread.sleep(1000);

			WebElement goInboxButton = driver.findElement(By.xpath("//button[@title='Check Inbox @yopmail.com']"));
			goInboxButton.click();
			Thread.sleep(1000);
			driver.switchTo().frame("ifinbox");
			List<WebElement> mailList = driver.findElements(By.xpath("//div[@class='m']/button/div[2]"));
			int l = mailList.size();

			Thread.sleep(2000);
			for (int i = 0; i < l; i++) {
				email = mailList.get(i).getText();
				jse.executeScript("arguments[0].scrollIntoView(true);", mailList.get(i));

				if (email.contains(emailSubject)) {
					mailList.get(i).click();
					Thread.sleep(2000);
					driver.switchTo().defaultContent();
					Thread.sleep(2000);
					driver.switchTo().frame("ifmail");
					List<WebElement> mailcontent = driver
							.findElements(By.xpath("//*[@id=\"mail\"]/div/table/tbody/tr[2]/td/p[2]/a"));
					for (int j = 0; j < mailcontent.size(); j++) {
						emailContent = mailcontent.get(j).getText();
						if (emailContent.contains(findInEmailContent)) {
							ExpectedContentExist = true;
							return ExpectedContentExist;
						}
					}

				}
			}

		} catch (Exception e) {
			Log4jUtil.log("unable to find links in yopmail" + e);
		}

		return ExpectedContentExist;
	}

	private void logAttemptAndSleep(int currentAttempt, int maxAttempts) throws InterruptedException {
		logAttemptAndSleep(currentAttempt, maxAttempts, false);
	}

	private void logAttemptAndSleep(int currentAttempt, int maxAttempts, boolean plural) throws InterruptedException {
		Log4jUtil.log((plural ? "Emails were" : "Email was") + " not retrieved. Trial number " + currentAttempt + "/"
				+ maxAttempts + "."
				+ (currentAttempt != maxAttempts
						? " Waiting for e-mail" + (plural ? "s" : "") + " to arrive for " + TIME_TO_WAIT_MS / 1000
								+ " s."
						: ""));
		// we don't need to sleep after the last retry
		if (currentAttempt != maxAttempts) {
			Thread.sleep(TIME_TO_WAIT_MS);
		}
	}

}
