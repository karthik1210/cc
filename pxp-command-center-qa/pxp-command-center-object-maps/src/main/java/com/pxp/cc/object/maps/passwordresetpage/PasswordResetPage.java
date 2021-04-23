// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.object.maps.passwordresetpage;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.medfusion.common.utils.Mailinator;
import com.pxp.cc.object.maps.ccmainpage.CCMainPage;
import com.pxp.cc.object.maps.pojo.CCUser;
import com.pxp.cc.object.maps.utilities.CCUtils;

public class PasswordResetPage extends CCMainPage {

	@FindBy(id = "forgetLink")
	private WebElement forgotPassword;

	@FindBy(id = "email")
	private WebElement usernameField;

	@FindBy(xpath = "//*[@id=\"loginButton\"]/button")
	private WebElement emailMeTheLink;

	@FindBy(id = "errormsg")
	private WebElement errorMsgUnregisteredUser;

	@FindBy(xpath = "//*[@id=\"signInLable\"]")
	private WebElement errorMsgExternalUser;

	@FindBy(xpath = "//*[@id=\"resetButton\"]/button")
	private WebElement resetButton;

	@FindBy(xpath = "//*[@id=\"contact\"]")
	private WebElement changePasswordMsgContactAdmin;

	@FindBy(id = "message")
	private WebElement userNotificationMsg;

	@FindBy(id = "emailid")
	private WebElement emailSentNotification;

	@FindBy(id = "userRequire")
	private WebElement blankEmail;

	@FindBy(id = "confirmemail")
	private WebElement receivedEmailAddress;

	@FindBy(xpath = "//*[@id=\"resetButton\"]/button")
	private WebElement resetLogin;

	@FindBy(xpath = "//*[@id=\"returnButton\"]/button")
	private WebElement returnToLogin;

	@FindBy(xpath = "//input[@ng-reflect-name='password']")
	private WebElement newPassword;

	@FindBy(id = "confirmPassword")
	private WebElement confirmPassword;

	@FindBy(id = "create")
	private WebElement setNewPassword;

	@FindBy(xpath = "/html/body/app-root/app-resetconfirmation/div/div[2]/div/div")
	private WebElement newPaswwordCreated;

	@FindBy(xpath = "/html/body/app-root/app-password-change/div/div/div[3]/form/div/div/div[1]/div/div/div/span")
	private WebElement hideNewPass;

	@FindBy(xpath = "/html/body/app-root/app-password-change/div/div/div[3]/form/div/div/div[2]/div/div/div/span")
	private WebElement hideConfirmPass;

	@FindBy(xpath = "//*[@id=\"create\"]")
	private WebElement updatePassword;

	@FindBy(xpath = "//*[@id=\"loginButton\"]/button")
	private WebElement returnToLoginButton;

	@FindBy(xpath = "//*[@id=\"email\"]")
	private WebElement username;

	@FindBy(xpath = "//*[@id=\"pwd\"]")
	private WebElement password;

	@FindBy(xpath = "/html/body/app-root/app-login/div[2]/div/div/form/div[2]/div/div/div/span")
	private WebElement showButton;

	@FindBy(className = "errormsg")
	private WebElement alreadyUsedPassword;

	@FindBy(xpath = "/html/body/app-root/app-password-change/div/div/p")
	private WebElement spaceValidation;

	@FindBy(xpath = "/html/body/app-root/app-password-change/div/div/p")
	private WebElement invalidToken;

	@FindBy(xpath = "/html/body/app-root/app-password-change/div/div/p")
	private WebElement expiredToken;

	@FindBy(xpath = "/html/body/app-root/app-password-change/div/div/p")
	private WebElement maximumCharacter;

	public PasswordResetPage(WebDriver driver, String url) {
		super(driver, url);
	}

	public Mailinator mailinator = new Mailinator();

	public void verifyUpdatedPasswordSuccessfullyLoginFunctionality(String resetEmail, String autogeneratePwd)
			throws InterruptedException {
		log("Updated password login functionality email id :" + resetEmail + " and auto generated password :"
				+ autogeneratePwd);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(resetEmail);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("Entering the Email id");
		emailMeTheLink.click();
		log("It will send the link for password reset on entered email Id");
		wait.until(ExpectedConditions.visibilityOf(userNotificationMsg));
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				userNotificationMsg.getText());
		wait.until(ExpectedConditions.visibilityOf(emailSentNotification));
		CCUtils.assertEquals("r*****@m*****.com", emailSentNotification.getText());
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
		CCUtils.assertEquals("confirm@nextgen.com.", receivedEmailAddress.getText());
		urlInEmail(resetEmail, autogeneratePwd);
	}

	private void urlInEmail(String resetEmail, String autogeneratePwd) throws InterruptedException {
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
		wait.until(ExpectedConditions.visibilityOf(newPaswwordCreated));
		CCUtils.assertEquals(newPaswwordCreated.getText(), "Success! Your password has been reset.");
		wait.until(ExpectedConditions.visibilityOf(updatePassword));
		highlightElement(updatePassword);
		updatePassword.click();
		log("Click on updated password button");
		wait.until(ExpectedConditions.visibilityOf(username));
		username.sendKeys("ruchi.singh@mailinator.com");
		wait.until(ExpectedConditions.visibilityOf(password));
		password.sendKeys(autogeneratePwd);
		highlightElement(showButton);
		showButton.click();
		wait.until(ExpectedConditions.visibilityOf(returnToLoginButton));
		highlightElement(returnToLoginButton);
		returnToLoginButton.click();
		log("Navigate to login page");
		wait.until(ExpectedConditions.visibilityOf(returnToLoginButton));
		deleteEmail(resetEmail);
		log("It will delete the already used link of email");
		driver.navigate().refresh();
		driver.close();
		log("It will close the current browser window");

	}

	public void verifySetNewPasswordFunctionalityWithLastTenOldPassword(String mailinatorOldPassword,
			String autogeneratePwd) throws InterruptedException {
		log("Updated password login functionality email id :" + mailinatorOldPassword + " and password :"
				+ autogeneratePwd);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(mailinatorOldPassword);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("Entering the Emailid.....");
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(userNotificationMsg));
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				userNotificationMsg.getText());
		wait.until(ExpectedConditions.visibilityOf(emailSentNotification));
		CCUtils.assertEquals("r*****@m*****.com", emailSentNotification.getText());
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
		CCUtils.assertEquals("confirm@nextgen.com.", receivedEmailAddress.getText());
		Thread.sleep(4000);
		oldPasswordLinkInEmail(mailinatorOldPassword, autogeneratePwd);
	}

	private void oldPasswordLinkInEmail(String mailinatorOldPassword, String autogeneratePwd)
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
		Thread.sleep(4000);
		log("Choose new password page throw error for already used password.");
		wait.until(ExpectedConditions.visibilityOf(alreadyUsedPassword));
		CCUtils.assertEquals("New password must not match with last 10 old passwords", alreadyUsedPassword.getText());
		deleteEmail(mailinatorOldPassword);
		log("It will delete the already used link of email");
		driver.navigate().refresh();
		driver.close();
		log("It will close the current browser window");
	}

	public void verifyFunctionalityForUnregisteredUser(String email) throws InterruptedException {
		log("Unregistered user email :" + email);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(email);
		log("After entering the Email validating the email address.....");
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(errorMsgUnregisteredUser));
		CCUtils.assertEquals(errorMsgUnregisteredUser.getText(), "You do not have access to this system.");
	}

	public void verifyPasswordResetFunctionalityWithBlankUsername() throws InterruptedException {
		log("Blank EmailID Verification :");
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(blankEmail));
		CCUtils.assertEquals(blankEmail.getText(), "Username is required");
		log("click on EmailMeTheLink button without email id....");
	}

	public void verifyPasswordResetValidationForExternalUser(String externalUser) throws InterruptedException {
		log("External User email :" + externalUser);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(externalUser);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("After entering the Email validating externalUser.....");
		emailMeTheLink.click();
		log("click on EmailMeTheLink button");
		wait.until(ExpectedConditions.visibilityOf(resetLogin));
		wait.until(ExpectedConditions.visibilityOf(errorMsgExternalUser));
		log("click on EmailMeTheLink button errorMsgExternalUser");
		CCUtils.assertEquals(errorMsgExternalUser.getText(),
				"Your credentials are managed through an external system and the Command Center cannot change your password on your behalf.");
		wait.until(ExpectedConditions.visibilityOf(changePasswordMsgContactAdmin));
		CCUtils.assertEquals(changePasswordMsgContactAdmin.getText(),
				"If you would like to change your password, please contact _______________.");
		log("Navigate to welcome Page.....");
		highlightElement(resetLogin);
		resetLogin.click();
		log("click on return to login");
	}

	public void verifySetNewPasswordFunctionalityWithSpaceEnteredInPasswordField(String mailinatorSpaceInPassword)
			throws InterruptedException {
		log("Functionality of Space entered in password field :" + mailinatorSpaceInPassword);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		log("click on ForgotPassword link.....");
		usernameField.sendKeys(mailinatorSpaceInPassword);
		highlightElement(emailMeTheLink);
		log("Entering the Emailid.....");
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(userNotificationMsg));
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				userNotificationMsg.getText());
		wait.until(ExpectedConditions.visibilityOf(emailSentNotification));
		CCUtils.assertEquals("p*****@m*****.com", emailSentNotification.getText());
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
		CCUtils.assertEquals("confirm@nextgen.com.", receivedEmailAddress.getText());
		Thread.sleep(4000);
		linkInEmail(mailinatorSpaceInPassword);
	}

	private void linkInEmail(String mailinatorSpaceInPassword) throws InterruptedException {
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
		setNewPassword.click();
		wait.until(ExpectedConditions.visibilityOf(spaceValidation));
		CCUtils.assertEquals("Space not allowed in password", spaceValidation.getText());
		log("Set New Password will throw error message when entered space in password field.");
	}

	public void verifySetNewPasswordFunctionalityWithRepetitiveSequenceEnteredInPasswordField(
			String mailinatorRepetitiveSequence) throws InterruptedException {
		log("Repetitive sequence entered in password field :" + mailinatorRepetitiveSequence);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		log("click on ForgotPassword link.....");
		usernameField.sendKeys(mailinatorRepetitiveSequence);
		highlightElement(emailMeTheLink);
		log("Entering the Emailid.....");
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(userNotificationMsg));
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				userNotificationMsg.getText());
		wait.until(ExpectedConditions.visibilityOf(emailSentNotification));
		CCUtils.assertEquals("r*****@m*****.com", emailSentNotification.getText());
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
		CCUtils.assertEquals("confirm@nextgen.com.", receivedEmailAddress.getText());
		linkUnderEmail(mailinatorRepetitiveSequence);
	}

	private void linkUnderEmail(String mailinatorRepetitiveSequence) throws InterruptedException {
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
		jse.executeScript("window.scrollBy(0,250)");
		scrollAndWait(0, 250, 3000);
		log("Set New Password button will be disabled when used repetitive sequence in password field.");
	}

	public void verifyPasswordResetForInvalidToken(String mailinatorInvalidToken, String tokenURL)
			throws InterruptedException {
		log("Invalid Token :" + mailinatorInvalidToken);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(mailinatorInvalidToken);
		highlightElement(emailMeTheLink);
		log("Entering the Emailid.....");
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(userNotificationMsg));
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				userNotificationMsg.getText());
		wait.until(ExpectedConditions.visibilityOf(emailSentNotification));
		CCUtils.assertEquals("i*****@m*****.com", emailSentNotification.getText());
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
		CCUtils.assertEquals("confirm@nextgen.com.", receivedEmailAddress.getText());
		invalidTokenUrlInEmail(mailinatorInvalidToken, tokenURL);
	}

	private void invalidTokenUrlInEmail(String mailinatorInvalidToken, String tokenURL) throws InterruptedException {
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
		jse.executeScript("window.scrollBy(0,250)");
		scrollAndWait(0, 250, 3000);
		wait.until(ExpectedConditions.visibilityOf(setNewPassword));
		highlightElement(setNewPassword);
		setNewPassword.click();
		wait.until(ExpectedConditions.visibilityOf(invalidToken));
		CCUtils.assertEquals("This password reset token is invalid", invalidToken.getText());
		log("Already used token for password reset.");
	}

	public void verifySetNewPasswordFunctionalityWithCommonPassword(String mailinatorCommonPassword)
			throws InterruptedException {
		log("Common Password :" + mailinatorCommonPassword);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(mailinatorCommonPassword);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("Entering the Emailid.....");
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(userNotificationMsg));
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				userNotificationMsg.getText());
		wait.until(ExpectedConditions.visibilityOf(emailSentNotification));
		CCUtils.assertEquals("c*****@m*****.com", emailSentNotification.getText());
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
		CCUtils.assertEquals("confirm@nextgen.com.", receivedEmailAddress.getText());
		Thread.sleep(4000);
		commonPasswordUrlInEmail(mailinatorCommonPassword);
	}

	private void commonPasswordUrlInEmail(String mailinatorCommonPassword) throws InterruptedException {
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

	public void verifySetNewPasswordFunctionalityWithMaximumOfhunderedCharacters(String mailinatorMaximumCharacters)
			throws InterruptedException {
		log("Maximum of hundered Characters :" + mailinatorMaximumCharacters);
		highlightElement(forgotPassword);
		forgotPassword.click();
		log("Click on forgot Password link and rendered to password reset page");
		highlightElement(usernameField);
		usernameField.sendKeys(mailinatorMaximumCharacters);
		wait.until(ExpectedConditions.visibilityOf(emailMeTheLink));
		highlightElement(emailMeTheLink);
		log("Entering the Emailid.....");
		emailMeTheLink.click();
		wait.until(ExpectedConditions.visibilityOf(userNotificationMsg));
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				userNotificationMsg.getText());
		wait.until(ExpectedConditions.visibilityOf(emailSentNotification));
		CCUtils.assertEquals("M*****@m*****.com", emailSentNotification.getText());
		wait.until(ExpectedConditions.visibilityOf(receivedEmailAddress));
		CCUtils.assertEquals("confirm@nextgen.com.", receivedEmailAddress.getText());
		Thread.sleep(4000);
		maximumCharacterValidationlinkInEmail(mailinatorMaximumCharacters);
	}

	private void maximumCharacterValidationlinkInEmail(String mailinatorMaximumCharacters) throws InterruptedException {
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
		wait.until(ExpectedConditions.visibilityOf(maximumCharacter));
		CCUtils.assertEquals("Password should have less than 100 characters", maximumCharacter.getText());
	}

	public void deleteEmail(String mailinatorEmail) {
		log("Blank EmailID Verification :");
		driver.navigate().to("https://www.mailinator.com/v4/public/inboxes.jsp?to=" + mailinatorEmail);
		driver.manage().window().maximize();
		int rowCount = driver.findElements(By.xpath("//*[@id=\"inbox_pane\"]/div[3]/div/div[4]/div/div/table/tbody/tr"))
				.size();
		log("print: " + rowCount);
		for (int i = 1; i <= rowCount; i++) {
			driver.findElement(By.xpath("//*[@id=\"inbox_pane\"]/div[3]/div/div[4]/div/div/table/tbody/tr[" + i + "]"))
					.click();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pills-html-tab\"]")));
		driver.findElement(By.className("btn-delete")).click();
		driver.navigate().to("https://www.mailinator.com/v4/public/inboxes.jsp?to=" + mailinatorEmail);
		driver.navigate().refresh();
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

	public PasswordResetPage clickOnforgotPasswordLink(WebDriver driver, CCUser ccuser, String autogeneratePassword)
			throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail=" + ccuser.getResetEmail());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifyUpdatedPasswordSuccessfullyLoginFunctionality(ccuser.getResetEmail(),
				autogeneratePassword);
		return commandcenterforgotpage;
	}

	public PasswordResetPage emailAddressValidation(WebDriver driver, CCUser ccuser) throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail=" + ccuser.getUnregisteredEmail());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifyFunctionalityForUnregisteredUser(ccuser.getUnregisteredEmail());
		return commandcenterforgotpage;
	}

	public PasswordResetPage emailWithoutData(WebDriver driver, CCUser ccuser) throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail=" + ccuser);
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifyPasswordResetFunctionalityWithBlankUsername();
		return commandcenterforgotpage;
	}

	public PasswordResetPage externalUserLogin(WebDriver driver, CCUser ccuser) throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and external user email=" + ccuser);
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifyPasswordResetValidationForExternalUser(ccuser.getExternalUser());
		return commandcenterforgotpage;
	}

	public PasswordResetPage resetPasswordSpaceValidations(WebDriver driver, CCUser ccuser)
			throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail=" + ccuser.getMailinatorSpaceInPassword());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifySetNewPasswordFunctionalityWithSpaceEnteredInPasswordField(
				ccuser.getMailinatorSpaceInPassword());
		return commandcenterforgotpage;
	}

	public PasswordResetPage resetPasswordRepetitiveSequenceValidation(WebDriver driver, CCUser ccuser)
			throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail="
				+ ccuser.getMailinatorRepetitiveSequence());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifySetNewPasswordFunctionalityWithRepetitiveSequenceEnteredInPasswordField(
				ccuser.getMailinatorRepetitiveSequence());
		return commandcenterforgotpage;
	}

	public PasswordResetPage resetPasswordAlreadyUsedPasswordValidation(WebDriver driver, CCUser ccuser,
			String autogeneratePassword) throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail=" + ccuser.getMailinatorOldPassword());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifySetNewPasswordFunctionalityWithLastTenOldPassword(
				ccuser.getMailinatorOldPassword(), autogeneratePassword);
		return commandcenterforgotpage;
	}

	public PasswordResetPage resetPasswordAlreadyUsedTokenValidation(WebDriver driver, CCUser ccuser)
			throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail=" + ccuser.getMailinatorInvalidToken());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifyPasswordResetForInvalidToken(ccuser.getMailinatorInvalidToken(),
				ccuser.getInvalidTokenUrl());
		return commandcenterforgotpage;
	}

	public PasswordResetPage resetPasswordCommonlyUsedValidation(WebDriver driver, CCUser ccuser)
			throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail=" + ccuser.getMailinatorCommonPassword());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage
				.verifySetNewPasswordFunctionalityWithCommonPassword(ccuser.getMailinatorCommonPassword());
		return commandcenterforgotpage;
	}

	public PasswordResetPage resetPasswordMaximumCharactersValidation(WebDriver driver, CCUser ccuser)
			throws InterruptedException {
		log("Click on FORGOT PASSWORD" + ccuser.getUrl() + " and ResetEmail="
				+ ccuser.getMailinatorMaximumCharacters());
		PasswordResetPage commandcenterforgotpage = new PasswordResetPage(driver, ccuser.getUrl());
		commandcenterforgotpage.verifySetNewPasswordFunctionalityWithMaximumOfhunderedCharacters(
				ccuser.getMailinatorMaximumCharacters());
		return commandcenterforgotpage;
	}

}
