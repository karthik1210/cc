// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.pxp.cc.util.YopMailUtility;

public class PasswordResetPage extends CCMainPage {

	@FindBy(id = "loginForgetLink")
	private WebElement forgotPassword;

	@FindBy(id = "passresetUsernameInput")
	private WebElement usernameField;

	@FindBy(xpath = "//button[contains(text(),'Email me the Link')]")
	private WebElement emailMeTheLink;

	@FindBy(id = "passresetErrorNessage")
	private WebElement errorMsgUnregisteredUser;

	@FindBy(id = "passresetCredentailLabel")
	private WebElement errorMsgExternalUser;

	@FindBy(id = "passresetContactLabel")
	private WebElement changePasswordMsgContactAdmin;

	@FindBy(id = "passresetVerifiction")
	private WebElement userNotificationMsg;

	@FindBy(id = "passresetUserEmail")
	private WebElement emailSentNotification;

	@FindBy(id = "passresetUserRequired")
	private WebElement blankEmail;

	@FindBy(id = "passresetEmailMessage")
	private WebElement receivedEmailAddress;

	@FindBy(xpath = "//button[contains(text(), 'Return to login')]")
	private WebElement returnToLoginFromOops;

	@FindBy(id = "passwordInput")
	private WebElement newPassword;

	@FindBy(id = "passwordConfirmInput")
	private WebElement confirmPassword;

	@FindBy(id = "passwordCreate")
	private WebElement setNewPassword;

	@FindBy(id = "resetSuccessMessage")
	private WebElement newPaswwordCreated;

	@FindBy(xpath = "//span[@id='passwordShowHide']")
	private WebElement hideNewPass;

	@FindBy(xpath = "//span[@id='passwordShow']")
	private WebElement hideConfirmPass;

	@FindBy(id = "resetLoginButton")
	private WebElement updatePassword;

	@FindBy(id = "loginButton")
	private WebElement returnToLoginButton;

	@FindBy(id = "loginUsernameInput")
	private WebElement username;

	@FindBy(id = "loginPwdInput")
	private WebElement password;

	@FindBy(id = "loginShowHide")
	private WebElement showButton;

	@FindBy(xpath = "//div/p[@class='errormsg']")
	private WebElement alreadyUsedPassword;

	@FindBy(id = "passwordErrMessage")
	private WebElement errorMessage;

	public PasswordResetPage(WebDriver driver, String url) {
		super(driver, url);
	}

	public YopMailUtility mailinator = new YopMailUtility(driver);

	public void clickForgotPasswordAndEnterEmailAddress(String resetEmail, String autogeneratePwd)
			throws InterruptedException {
		log("Updated password login functionality email :" + resetEmail + " and auto generated password :"
				+ autogeneratePwd);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Clicked on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(resetEmail);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("Entering the Email id");
		emailMeTheLink.click();
		log("It will send the link for password reset on entered email Id");
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
	}

	public void urlInEmail(String resetEmail, String autogeneratePwd) throws InterruptedException {
		String url = mailinator.getLinkFromEmail(resetEmail, "Password reset request", "pwd-change", 1);
		log("Fetching the reset password url received in email:" + url);
		driver.navigate().to(url);
		log("Navigate to choose a new Password Page ");
		highlightElement(hideNewPass);
		hideNewPass.click();
		log("Click on hide button of new password field");
		wait.until(ExpectedConditions.visibilityOf(newPassword));
		newPassword.sendKeys(autogeneratePwd);
		highlightElement(confirmPassword);
		confirmPassword.sendKeys(autogeneratePwd);
		highlightElement(hideConfirmPass);
		log("Click on hide button of confirm password field");
		hideConfirmPass.click();
		wait.until(ExpectedConditions.visibilityOf(hideConfirmPass));
		scrollAndWait(0, 250, 3000);
		wait.until(ExpectedConditions.visibilityOf(setNewPassword));
		highlightElement(setNewPassword);
		setNewPassword.click();
		log("Click on set the new password button");
		wait.until(ExpectedConditions.visibilityOf(setNewPassword));

	}

	public void oldPasswordLinkInEmail(String mailinatorOldPassword, String autogeneratePwd)
			throws InterruptedException {
		String url = mailinator.getLinkFromEmail(mailinatorOldPassword, "Password reset request", "pwd-change", 1);
		log("Fetching the reset password url received in email:" + url);
		driver.navigate().to(url);
		log("Navigate to Choose a new password Page ");
		highlightElement(hideNewPass);
		hideNewPass.click();
		newPassword.sendKeys(autogeneratePwd);
		highlightElement(confirmPassword);
		confirmPassword.sendKeys(autogeneratePwd);
		highlightElement(hideConfirmPass);
		hideConfirmPass.click();
		scrollAndWait(0, 250, 3000);
		wait.until(ExpectedConditions.visibilityOf(setNewPassword));
		highlightElement(setNewPassword);
		setNewPassword.click();
		log("Choose new password page throw error for already used password.");
		wait.until(ExpectedConditions.textToBePresentInElement(alreadyUsedPassword,
				"New password must not match with last 10 old passwords"));

	}

	public void clickForgotPasswordAndEnterUnregisteredUserEmailAddress(String email) throws InterruptedException {
		log("Unregistered user email :" + email);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Clicked on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(email);
		log("After entering the Email validating the email address.....");
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		emailMeTheLink.click();
		wait.until(ExpectedConditions.textToBePresentInElement(errorMsgUnregisteredUser,
				"You do not have access to this system."));
	}

	public void verifyPasswordResetFunctionalityWithBlankUsername() throws InterruptedException {
		log("Blank EmailID Verification :");
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Clicked on forgot Password link and rendered to password reset page");
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(blankEmail));
		log("click on EmailMeTheLink button without email id....");
	}

	public void clickForgotPasswordAndEnterExternalUserEmailAddress(String externalUser) throws InterruptedException {
		log("External User email :" + externalUser);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Clicked on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(externalUser);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("After entering the Email validating externalUser.....");
		emailMeTheLink.click();
		log("click on EmailMeTheLink button");
		wait.until(ExpectedConditions.visibilityOf(returnToLoginFromOops));
	}

	public void clickForgotPasswordAndEnterEmailAddress(String email) throws InterruptedException {
		log("Common Password :" + email);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Clicked on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(email);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("Entering the Emailid.....");
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
	}

	public void linkInEmail(String mailinatorSpaceInPassword) throws InterruptedException {
		String url = mailinator.getLinkFromEmail(mailinatorSpaceInPassword, "Password reset request", "pwd-change", 1);
		log("Fetching the reset password url received in email:" + url);
		driver.navigate().to(url);
		log("Navigate to Choose a new password Page ");
		newPassword.click();
		highlightElement(hideNewPass);
		hideNewPass.click();
		highlightElement(newPassword);
		newPassword.sendKeys(" Ruchi#66");
		highlightElement(confirmPassword);
		confirmPassword.sendKeys(" Ruchi#66");
		highlightElement(hideConfirmPass);
		hideConfirmPass.click();
		scrollAndWait(0, 250, 3000);
		wait.until(ExpectedConditions.visibilityOf(setNewPassword));
		highlightElement(setNewPassword);
		log("Set New Password button will be disabled when used space in password field.");
	}

	public void linkUnderEmail(String mailinatorRepetitiveSequence) throws InterruptedException {
		String url = mailinator.getLinkFromEmail(mailinatorRepetitiveSequence, "Password reset request", "pwd-change",
				1);
		log("Fetching the reset password url received in email:" + url);
		driver.navigate().to(url);
		log("Navigate to Choose a new password Page ");
		driver.navigate().refresh();
		highlightElement(hideNewPass);
		hideNewPass.click();
		newPassword.sendKeys("RRR432k@");
		highlightElement(confirmPassword);
		confirmPassword.sendKeys("RRR432k@");
		highlightElement(hideConfirmPass);
		hideConfirmPass.click();
		scrollAndWait(0, 250, 3000);
		log("Set New Password button will be disabled when used repetitive sequence in password field.");
	}

	public void invalidTokenUrlInEmail(String mailinatorInvalidToken, String tokenURL) throws InterruptedException {
		log("Fetching the reset password url received in email:" + tokenURL);
		driver.navigate().to(tokenURL);
		log("Navigate to Choose a new password Page ");
		highlightElement(hideNewPass);
		hideNewPass.click();
		newPassword.sendKeys("Ruchi*29");
		highlightElement(confirmPassword);
		confirmPassword.sendKeys("Ruchi*29");
		highlightElement(hideConfirmPass);
		hideConfirmPass.click();
		scrollAndWait(0, 250, 3000);
		wait.until(ExpectedConditions.visibilityOf(setNewPassword));
		highlightElement(setNewPassword);
		setNewPassword.click();
		wait.until(ExpectedConditions.textToBePresentInElement(errorMessage, "This password reset token is invalid"));
		log("Already used token for password reset.");
	}

	public void commonPasswordUrlInEmail(String mailinatorCommonPassword) throws InterruptedException {
		String url = mailinator.getLinkFromEmail(mailinatorCommonPassword, "Password reset request", "pwd-change", 1);
		log("Fetching the reset password url received in email:" + url);
		driver.navigate().to(url);
		highlightElement(hideNewPass);
		hideNewPass.click();
		newPassword.sendKeys("p@ssw0rd");
		highlightElement(confirmPassword);
		confirmPassword.sendKeys("p@ssw0rd");
		highlightElement(hideConfirmPass);
		hideConfirmPass.click();
		scrollAndWait(0, 250, 3000);
		log("Set New Password button will be disabled when used commonly password.");
	}

	public void maximumCharacterValidationlinkInEmail(String mailinatorMaximumCharacters) throws InterruptedException {
		String url = mailinator.getLinkFromEmail(mailinatorMaximumCharacters, "Password reset request", "pwd-change",
				1);
		log("Fetching the reset password url received in email:" + url);
		driver.navigate().to(url);
		log("Navigate to Choose a new password Page ");
		highlightElement(hideNewPass);
		hideNewPass.click();
		newPassword.sendKeys(
				"qwertyuiop0987654321!@#$%&*^?\"ZXCVBNMLKJHGFDSAPOIUYTREWQ1234567890qazwsxedcrfvtgbyhnUJMIKOLP654321891");
		highlightElement(confirmPassword);
		confirmPassword.sendKeys(
				"qwertyuiop0987654321!@#$%&*^?\"ZXCVBNMLKJHGFDSAPOIUYTREWQ1234567890qazwsxedcrfvtgbyhnUJMIKOLP654321891");
		highlightElement(hideConfirmPass);
		hideConfirmPass.click();
		scrollAndWait(0, 250, 3000);
		wait.until(ExpectedConditions.visibilityOf(setNewPassword));
		highlightElement(setNewPassword);
		setNewPassword.click();
		log("Choose new password page throw error for maximum characters.");
		wait.until(ExpectedConditions.textToBePresentInElement(errorMessage,
				"Password should have less than 100 characters"));
	}

	public void deleteEmail(String mailinatorEmail) throws InterruptedException {
		log("Delete EmailID Verification :" + mailinatorEmail);
		driver.get("https://yopmail.com/en/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		WebElement d = driver.findElement(By.xpath("//input[@id='login']"));
		d.clear();
		d.sendKeys(mailinatorEmail);
		Thread.sleep(2000);
		WebElement goInboxButton = driver.findElement(By.xpath("//button[@title='Check Inbox @yopmail.com']"));
		goInboxButton.click();
		Thread.sleep(2000);
		driver.switchTo().frame("ifinbox");
		List<WebElement> subList = driver.findElements(By.xpath("//div[@class='m']/button"));
		List<WebElement> chkList = driver.findElements(By.xpath("//input[@class='mc']"));

		String subject_line = "Password reset request";
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getText().contains(subject_line)) {
				log(subList.get(i).getText() + "---Text");
				chkList.get(i).click();
			}
		}
		driver.switchTo().parentFrame();
		WebElement buttonDelete = driver.findElement(By.xpath("//button[@id='delsel']"));
		buttonDelete.click();
	}

	private static final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#";

	public static String genPassword(Random r) {
		while (true) {
			char[] password = new char[r.nextBoolean() ? 12 : 13];
			boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
			for (int i = 0; i < password.length; i++) {
				char ch = symbols.charAt(r.nextInt(symbols.length()));
				if (Character.isUpperCase(ch))
					hasUpper = true;
				else if (Character.isLowerCase(ch))
					hasLower = true;
				else if (Character.isDigit(ch))
					hasDigit = true;
				else
					hasSpecial = true;
				password[i] = ch;
			}
			if (hasUpper && hasLower && hasDigit && hasSpecial) {
				return new String(password);
			}
		}
	}

	public String getUserNotificationMsg() {
		String msg = userNotificationMsg.getText();
		return msg;
	}

	public String getEmailSentNotificationMsg() {
		String msg = emailSentNotification.getText();
		return msg;
	}

	public String getReceivedEmailAddressMsg() {
		String msg = receivedEmailAddress.getText();
		return msg;
	}

	public String getErrorMsgUnregisteredUserMsg() {
		String msg = errorMsgUnregisteredUser.getText();
		return msg;
	}

	public String getBlankEmailMsg() {
		String msg = blankEmail.getText();
		return msg;
	}

	public String getErrorMsgExternalUser() {
		String msg = errorMsgExternalUser.getText();
		return msg;
	}

	public String getChangePasswordMsgContactAdminMsg() {
		String msg = changePasswordMsgContactAdmin.getText();
		return msg;
	}

	public String getNewPaswwordCreatedMsg() {
		String msg = newPaswwordCreated.getText();
		return msg;
	}

	public String getAlreadyUsedPasswordMsg() {
		String msg = alreadyUsedPassword.getText();
		return msg;
	}

	public void callReturnToLogin() {
		returnToLoginFromOops.click();
		log("click on return to login");

	}

	public void callPasswordUpdated(String resetEmail, String autogeneratePwd) {
		wait.until(ExpectedConditions.visibilityOf(updatePassword));
		highlightElement(updatePassword);
		updatePassword.click();
		log("Click on updated password button");
		wait.until(ExpectedConditions.visibilityOf(username));
		username.sendKeys("ruchi.singh@yopmail.com");
		wait.until(ExpectedConditions.visibilityOf(password));
		password.sendKeys(autogeneratePwd);
		highlightElement(showButton);
		showButton.click();
		wait.until(ExpectedConditions.visibilityOf(returnToLoginButton));
		highlightElement(returnToLoginButton);
		returnToLoginButton.click();
		log("Navigate to login page");
		wait.until(ExpectedConditions.visibilityOf(returnToLoginButton));
	}

}
