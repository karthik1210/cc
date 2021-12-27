// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class LockUserConfirmationPage extends CCMainPage {

	public LockUserConfirmationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class='cid']")
	private WebElement labelConfirmationQuestion;

	@FindBy(id = "applyCancel")
	private WebElement buttonCancel;

	@FindBy(id = "applyClearIcon")
	private WebElement buttonCrossIcon;

	@FindBy(id = "applyBtn")
	private WebElement buttonYesSend;

	public void verifyConfirmationTextForSingleUser(String userName) {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed()) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(),
					"Are you sure you would like to Lock User the selected user " + userName + "?");
			log("Lock User confirmation text is " + labelConfirmationQuestion.getText().trim());
		} else {
			log("Lock User confirmation text is not displayed");
		}
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		buttonCrossIcon.click();
	}

	public void clickOnYesSendButton() {
		CCUtils.verifyAndWaitForElement(buttonYesSend, driver);
		log("Click on yes send button");
		buttonYesSend.click();
	}
}
