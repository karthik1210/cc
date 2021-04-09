// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.object.maps.loginpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.object.maps.ccmainpage.CCMainPage;
import com.pxp.cc.object.maps.homepage.CommandCenterHomePage;

public class CommandCenterLoginPage extends CCMainPage {

	@FindBy(name = "PXPCommandCenter")
	private WebElement pxpCommandCenter;

	@FindBy(xpath = "//input[@type='text'][@id='email']")
	private WebElement username;

	@FindBy(xpath = "//input[@id='pwd']")
	private WebElement password;

	@FindBy(xpath = "//p[normalize-space()='Invalid Username or password']")
	private WebElement errorMsgInvalidUsernameOrPassword;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement loginButton;

	@FindBy(xpath = "//span[contains(text(),'SHOW')]")
	private WebElement showButton;

	@FindBy(xpath = "//span[contains(text(),'HIDE')]")
	private WebElement hideButton;

	@FindBy(xpath = "//p[@id='signin'][@name='signin']")
	private WebElement lockoutErrMsg;

	public CommandCenterLoginPage(WebDriver driver, String url) {
		super(driver, url);
	}

	public final String lockoutErrorMsg = "Password attempts exceeded, please contact your Command Center administrator.";

	public CommandCenterHomePage login(String emailID, String Password) throws InterruptedException {
		highlightElement(username);
		username.sendKeys(emailID.trim());
		log("Entered email in Email Input field");
		Thread.sleep(1000);
		highlightElement(password);
		password.sendKeys(Password.trim());
		log("Entered password in Password Input field");
		highlightElement(loginButton);
		loginButton.click();
		return PageFactory.initElements(super.driver, CommandCenterHomePage.class);
	}

	public void sixAttemptsLoginViaInvalidPwd(String userName, String InvalidPassword, String loginattempts)
			throws InterruptedException {
		highlightElement(username);
		username.sendKeys(userName.trim());
		log("Entered email in Username Input field");
		highlightElement(password);
		password.sendKeys(InvalidPassword.trim());
		log("Entered invalid password in Password Input field");
		int attempts = Integer.parseInt(loginattempts);
		for (int i = 0; i <= attempts; i++) {
			highlightElement(loginButton);
			Thread.sleep(1000);
			javascriptClick(loginButton);
			Thread.sleep(500);
			log("clicked on login button: " + i);
		}
		Thread.sleep(1000);
	}

	public String getLockOutErrMsg() {
		String msg = lockoutErrMsg.getText();
		return msg;
	}

	public void enterUsername(String Username) {
		highlightElement(username);
		username.sendKeys(Username.trim());
		log("Entered Username in Username Input field");
	}

	public void enterPassword(String Password) {
		highlightElement(password);
		password.sendKeys(Password.trim());
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
		if ((password.getAttribute("type")).equals("text")) {
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
