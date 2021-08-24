// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class LoginPage extends CCMainPage {

	@FindBy(id = "loginUsernameInput")
	private WebElement usernameField;

	@FindBy(id = "loginPwdInput")
	private WebElement passwordField;

	@FindBy(xpath = "//p[@class='sign']")
	private WebElement errorMsgInvalidUsernameOrPassword;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement loginButton;

	@FindBy(xpath = "//span[contains(text(),'SHOW')]")
	private WebElement showButton;

	@FindBy(xpath = "//span[contains(text(),'HIDE')]")
	private WebElement hideButton;

	@FindBy(xpath = "//p[@id='CC-1vLd']")
	private WebElement lockoutErrMsg;

	public LoginPage(WebDriver driver, String url) {
		super(driver, url);
	}

	public final String lockoutErrorMsg = "Password attempts exceeded, please contact your Command Center administrator.";

	public ClientDirectoryPage login(String emailID, String password) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(usernameField, driver);
		highlightElement(usernameField);
		usernameField.sendKeys(emailID.trim());
		log("Entered email in Email Input field");
		CCUtils.verifyAndWaitForElement(passwordField, driver);
		highlightElement(passwordField);
		passwordField.sendKeys(password.trim());
		log("Entered password in Password Input field");
		CCUtils.verifyAndWaitForElement(loginButton, driver);
		highlightElement(loginButton);
		loginButton.click();
		return PageFactory.initElements(super.driver, ClientDirectoryPage.class);
	}

	public void sixAttemptsLoginViaInvalidPwd(String userName, String invalidPassword, String loginAttempts)
			throws InterruptedException {
		CCUtils.verifyAndWaitForElement(usernameField, driver);
		highlightElement(usernameField);
		usernameField.sendKeys(userName.trim());
		log("Entered email in Username Input field");
		highlightElement(passwordField);
		passwordField.sendKeys(invalidPassword.trim());
		log("Entered invalid password in Password Input field");
		int attempts = Integer.parseInt(loginAttempts);
		for (int i = 0; i <= attempts; i++) {
			CCUtils.verifyAndWaitForElement(loginButton, driver);
			highlightElement(loginButton);
			javascriptClick(loginButton);
			log("clicked on login button: " + i);
		}
	}

	public String getLockOutErrMsg() {
		String msg = lockoutErrMsg.getText();
		return msg;
	}

	public void enterUsername(String username) {
		highlightElement(usernameField);
		usernameField.sendKeys(username.trim());
		log("Entered Username in Username Input field");
	}

	public void enterPassword(String password) {
		highlightElement(passwordField);
		passwordField.sendKeys(password.trim());
		log("Entered password in Password Input field");
	}

	public void clickLogin() {
		highlightElement(loginButton);
		javascriptClick(loginButton);
		log("clicked on Login button.");
	}

	public void clickShowButton() {
		highlightElement(showButton);
		showButton.click();
		log("clicked on 'SHOW' button in Password Input Field.");
	}

	public void clickHideButton() {
		highlightElement(hideButton);
		hideButton.click();
		log("Clicked on 'HIDE' button in Password Input Field.");
	}

	public boolean verifyPwdTypeAttribute() {
		if ((passwordField.getAttribute("type")).equals("text")) {
			log("Password input field || Type attribute value is : 'text' ");
			return true;
		}
		log("Password input field || Type attribute value is : 'password' ");
		return false;
	}

	public String getInvalidErrorColor() {
		String value = errorMsgInvalidUsernameOrPassword.getCssValue("color");
		return value;
	}

	public boolean verifyInvalidUsernameOrPasswordError() {
		if ((errorMsgInvalidUsernameOrPassword.isDisplayed())
				&& errorMsgInvalidUsernameOrPassword.getCssValue("color").equals("rgba(175, 14, 14, 1)")) {
			log("error displayed due to 'invalid Username' or 'invalid Password' or BOTH.");
			return true;
		}
		return false;
	}

}
