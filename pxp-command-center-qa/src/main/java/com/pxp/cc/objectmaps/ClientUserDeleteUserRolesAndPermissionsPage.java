// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class ClientUserDeleteUserRolesAndPermissionsPage extends CCMainPage {

	@FindBy(className = "delete user role-page-title")
	private WebElement labelConfirmationQuestion;

	@FindBy(id = "cancelBtn")
	private WebElement buttonCancel;

	@FindBy(id = "YesBtn")
	private WebElement buttonYes;

	@FindBy(id = "applyCrossIcon")
	private WebElement buttoncloseWindowIcon;

	public ClientUserDeleteUserRolesAndPermissionsPage(WebDriver driver) {
		super(driver);
	}

	public void verifyConfirmationQuestion() {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed() == true) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(), "Would you like to delete selected role ");
			log("Confirmation Question " + labelConfirmationQuestion.getText().trim());
		} else {
			log("confirmation Question is not displayed");
		}
	}

	public void clickOnYesButton() {
		CCUtils.verifyAndWaitForElement(buttonYes, driver);
		buttonYes.click();
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public void clickOncloseWindowIcon() {
		CCUtils.verifyAndWaitForElement(buttoncloseWindowIcon, driver);
		buttoncloseWindowIcon.click();
	}

	public void verifyYesButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonYes, driver);
		CCUtils.assertTrue(buttonYes.isDisplayed(),
				"Yes button is not displayed on delete user roles & permissions popup");
		CCUtils.assertEquals(buttonYes.getText().trim(), "Yes");
		CCUtils.assertEquals(buttonYes.isEnabled(), true);
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		CCUtils.assertTrue(buttonCancel.isDisplayed(),
				"Cancel button is not displayed on delete user roles & permissions popup");
		CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		CCUtils.assertEquals(buttonCancel.isEnabled(), true);
	}

	public void verifycloseWindowIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttoncloseWindowIcon, driver);
		CCUtils.assertTrue(buttoncloseWindowIcon.isDisplayed(),
				"Cross icon is not displayed on delete user roles & permissions popup");
		CCUtils.assertEquals(buttoncloseWindowIcon.getText().trim(), "clear");
		CCUtils.assertEquals(buttoncloseWindowIcon.isEnabled(), true);
	}
}