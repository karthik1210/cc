// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class SendEnrollmentConfirmationPage extends CCMainPage {

	@FindBy(id = "applyPracticeSetting")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[@class='cid']")
	private WebElement labelConfirmationQuestion;

	@FindBy(id = "applyCancel")
	private WebElement buttonCancel;

	@FindBy(id = "applyBtn")
	private WebElement buttonYesSend;

	@FindBy(id = "applyClearIcon")
	private WebElement buttonCrossIcon;

	public SendEnrollmentConfirmationPage(WebDriver driver) {
		super(driver);
	}

	public void verifyConfirmationPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed()) {
			CCUtils.assertEquals(labelPageTitle.getText().trim(), "Send Enrollment?");
			log("Send bulk enrollments confirmation page title is " + labelPageTitle.getText().trim());
		} else {
			log("Send bulk enrollments confirmation page title is not displayed");
		}
	}

	public void verifyConfirmationTextForBulkEnrollment(int numberOfUser) {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed()) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(),
					"Are you sure you would like to send an enrollment email to the " + numberOfUser
							+ " selected user(s).");
			log("Send bulk enrollments confirmation text is " + labelConfirmationQuestion.getText().trim());
		} else {
			log("Send bulk enrollments confirmation text is not displayed");
		}
	}

	public void clickOnYesSendButton() {
		CCUtils.verifyAndWaitForElement(buttonYesSend, driver);
		log("Click on yes send button");
		buttonYesSend.click();
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		buttonCrossIcon.click();
	}

	public void verifyConfirmationTextForSingleUser(String userName) {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed()) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(),
					"Are you sure you would like to send an enrollment email to the selected user " + userName + "?");
			log("Send enrollment confirmation text is " + labelConfirmationQuestion.getText().trim());
		} else {
			log("Send enrollment confirmation text is not displayed");
		}
	}

	public void verifyYesSendButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonYesSend, driver);
		if (buttonYesSend.isDisplayed()) {
			CCUtils.assertEquals(buttonYesSend.getText().trim(), "Yes, send");
			CCUtils.assertEquals(buttonYesSend.isEnabled(), true);
		} else {
			log("Yes, send button is not displayed on send enrollment confirmation popup");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		if (buttonCancel.isDisplayed()) {
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
			CCUtils.assertEquals(buttonCancel.isEnabled(), true);
		} else {
			log("Cancel button is not displayed on send enrollment confirmation popup");
		}
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		if (buttonCrossIcon.isDisplayed()) {
			CCUtils.assertEquals(buttonCrossIcon.getText().trim(), "clear");
			CCUtils.assertEquals(buttonCrossIcon.isEnabled(), true);
		} else {
			log("Cross icon is not displayed on send enrollment confirmation popup");
		}
	}

	public void VerifyConfirmationPopupForSingleUser(String userName) {
		verifyConfirmationPageTitle();
		verifyConfirmationTextForSingleUser(userName);
		verifyCancelButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
		verifyYesSendButtonIsDisplayed();
	}

}
