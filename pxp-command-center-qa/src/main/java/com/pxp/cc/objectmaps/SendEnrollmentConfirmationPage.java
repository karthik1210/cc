// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

	public SendBulkEnrollmentPage clickOnYesSendButton() {
		CCUtils.verifyAndWaitForElement(buttonYesSend, driver);
		log("Click on yes send button");
		buttonYesSend.click();
		return PageFactory.initElements(driver, SendBulkEnrollmentPage.class);
	}

	public SendBulkEnrollmentPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		return PageFactory.initElements(driver, SendBulkEnrollmentPage.class);
	}

	public SendBulkEnrollmentPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		buttonCrossIcon.click();
		return PageFactory.initElements(driver, SendBulkEnrollmentPage.class);
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

}
