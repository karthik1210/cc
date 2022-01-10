// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class ClientUserDeleteUserRolesAndPermissionPopupPage extends CCMainPage {

	@FindBy(className = "delete user role-page-title")
	private WebElement labelConfirmationQuestion;

	@FindBy(id = "cancelBtn")
	private WebElement buttonCancel;

	@FindBy(id = "YesBtn")
	private WebElement buttonYes;

	@FindBy(id = "applyCrossIcon")
	private WebElement buttonCrossIcon;

	public ClientUserDeleteUserRolesAndPermissionPopupPage(WebDriver driver) {
		super(driver);
	}

	public void verifyConfirmationQuestion() {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed()) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(), "Would you like to delete selected role ");
			log("Confirmation Question " + labelConfirmationQuestion.getText().trim());
		} else {
			log("confirmation Question is not displayed");
		}
	}

	public  void clickOnButtonYes() {
		CCUtils.verifyAndWaitForElement(buttonYes, driver);
		log("Click on Yes button");
		buttonYes.click();
		
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		buttonCrossIcon.click();
	}

	public void verifyYesButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonYes, driver);
		if(buttonYes.isDisplayed()) {
			CCUtils.assertEquals(buttonYes.getText().trim(), "Yes");
			CCUtils.assertEquals(buttonYes.isEnabled(), true);
		} else {
			log("Yes button is not displayed on delete user roles & permissions ");
		}
	
	}
	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		if (buttonCancel.isDisplayed()) {
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
			CCUtils.assertEquals(buttonCancel.isEnabled(), true);
		} else {
			log("Cancel button is not displayed on delete user roles & permissions popup");
		}
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		if (buttonCrossIcon.isDisplayed()) {
			CCUtils.assertEquals(buttonCrossIcon.getText().trim(), "clear");
			CCUtils.assertEquals(buttonCrossIcon.isEnabled(), true);
		} else {
			log("Cross icon is not displayed on delete user roles & permissions popup");
		}

	}
}