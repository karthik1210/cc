// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

	@FindBy(id = "applyCrossIcon")
	private WebElement buttonCloseWindow;

	public SendEnrollmentConfirmationPage(WebDriver driver) {
		super(driver);
	}

	public void verifyConfirmationPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed() == true) {
			CCUtils.assertEquals(labelPageTitle.getText().trim(), "Send Enrollment?");
			log("Send bulk enrollments confirmation page title is " + labelPageTitle.getText().trim());
		} else {
			log("Send bulk enrollments confirmation page title is not displayed");
		}
	}

	public void verifyConfirmationTextForBulkEnrollment(int numberOfUser) {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed() == true) {
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
		buttonYesSend.click();
		log("Clicked on yes,send button");
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		logStep("Clicked on cancel button");
	}

	public void clickOnCloseWindow() {
		CCUtils.verifyAndWaitForElement(buttonCloseWindow, driver);
		buttonCloseWindow.click();
		logStep("Clicked on close window button");
	}

	public void verifyConfirmationTextForSingleUser(String userName) {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		CCUtils.assertTrue(labelConfirmationQuestion.isDisplayed(),
				"Send enrollment confirmation text is not displayed");
		CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(),
				"Are you sure you would like to send an enrollment email to the selected user " + userName + "?");
		log("Send enrollment confirmation text is " + labelConfirmationQuestion.getText().trim());
	}

	public void verifyYesSendButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonYesSend, driver);
		CCUtils.assertTrue(buttonYesSend.isDisplayed(),
				"Yes, send button is not displayed on send enrollment confirmation popup");
		CCUtils.assertEquals(buttonYesSend.getText().trim(), "Yes, send");
		CCUtils.assertEquals(buttonYesSend.isEnabled(), true);
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		CCUtils.assertTrue(buttonCancel.isDisplayed(),
				"Cancel button is not displayed on send enrollment confirmation popup");
		CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		CCUtils.assertEquals(buttonCancel.isEnabled(), true);
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCloseWindow, driver);
		CCUtils.assertTrue(buttonCloseWindow.isDisplayed(),
				"Cross icon is not displayed on send enrollment confirmation popup");
		CCUtils.assertEquals(buttonCloseWindow.getText().trim(), "clear");
		CCUtils.assertEquals(buttonCloseWindow.isEnabled(), true);
	}

	public void VerifyConfirmationPopupForSingleUser(String userName) {
		verifyConfirmationPageTitle();
		verifyConfirmationTextForSingleUser(userName);
		verifyCancelButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
		verifyYesSendButtonIsDisplayed();
	}
}
