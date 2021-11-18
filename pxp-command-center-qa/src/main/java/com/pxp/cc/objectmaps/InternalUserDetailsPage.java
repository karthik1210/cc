// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class InternalUserDetailsPage extends CCMainPage {

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

	public InternalUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		javascriptClick(buttonKebabMenu);
	}

	public void verifySuccessToaster() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), "Enrollment sent to users successfully");
		else
			log("Success toaster is not displayed");
	}

	public void verifySendEnrollmentButtonIsNotDisplayed() {
		log("Verifying send enrollment email button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), false);
	}

	public SendEnrollmentConfirmationPage clickOnSendEnrollmentEmailButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollmentEmail, driver);
		javascriptClick(buttonSendEnrollmentEmail);
		return PageFactory.initElements(driver, SendEnrollmentConfirmationPage.class);
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(labelUsernameValue, driver);
		return labelUsernameValue.getText().trim();
	}
}
