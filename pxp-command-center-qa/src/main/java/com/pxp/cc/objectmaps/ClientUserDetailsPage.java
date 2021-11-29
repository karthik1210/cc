// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class ClientUserDetailsPage extends CCMainPage {

	@FindBy(xpath = "//div[@class='arrowDiv']")
	private WebElement labelPageTitle;

	@FindBy(id = "kebabMenu")
	private WebElement buttonKebabMenu;

	@FindBy(id = "enrollBtn")
	private WebElement buttonSendEnrollmentEmail;

	@FindBy(id = "enrollStatus")
	private WebElement labelEnrollmentStatusValue;

	@FindBy(id = "userStatus")
	private WebElement labelUserStatusValue;

	@FindBy(xpath = "//mat-card-title[@id='userName']")
	private WebElement labelUsernameValue;

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	public ClientUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		log("Click on kebab menu icon on client user details page");
		javascriptClick(buttonKebabMenu);
	}

	public void verifySendEnrollmentButtonIsNotDisplayed() {
		log("Verifying send enrollment email button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), false);
	}

	public void verifySelectedUserStatusIsSameOnClientUserDetailsPage(String userStatus) {
		CCUtils.verifyAndWaitForElement(labelUserStatusValue, driver);
		if (labelUserStatusValue.isDisplayed()) {
			CCUtils.assertEquals(labelUserStatusValue.getText().trim(), userStatus);
		} else {
			log("User status value is not present on client user details page");
		}
	}

	public void verifySendEnrollmentButtonIsDisplayed() {
		log("Verifying send enrollment email button is isplayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), true);
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(labelUsernameValue, driver);
		return labelUsernameValue.getText().trim();
	}

	public SendEnrollmentConfirmationPage clickOnSendEnrollmentEmailButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollmentEmail, driver);
		log("Click on send enrollment email button");
		javascriptClick(buttonSendEnrollmentEmail);
		return PageFactory.initElements(driver, SendEnrollmentConfirmationPage.class);
	}

	public void verifyClientUserDetailsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed()) {
			String title = labelPageTitle.getText().trim().replace("arrow_back", "");
			CCUtils.assertEquals(title.trim(), "Client User Details");
		} else {
			log("Client user details page title is not displayed on screen");
		}
	}

	public String getToasterMessage() {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		return toasterMessage.getText().trim();
	}
}
