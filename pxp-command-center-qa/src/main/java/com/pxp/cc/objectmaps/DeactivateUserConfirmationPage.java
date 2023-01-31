// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class DeactivateUserConfirmationPage extends CCMainPage {

	public DeactivateUserConfirmationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class='cid ng-star-inserted']")
	private WebElement labelConfirmationQuestion;

	@FindBy(id = "applyCancel")
	private WebElement buttonCancel;

	@FindBy(id = "applyClearIcon")
	private WebElement closeWindowIcon;

	@FindBy(id = "applyBtn")
	private WebElement buttonYesDeactivate;
	
	@FindBy(id = "applyDeactivate")
	private WebElement titleDeactivate;
	
	

	public void verifyConfirmationTextForSingleUser(String userName) {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed() == true) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(),
					"Are you sure you would like to deactivate the selected user " + userName + "?");
			log("Deactivate User confirmation text is = " + labelConfirmationQuestion.getText().trim());
		} else {
			log("Deactivate User confirmation text is not displayed");
		}
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public void clickOnCloseWindow() {
		CCUtils.verifyAndWaitForElement(closeWindowIcon, driver);
		closeWindowIcon.click();
	}

	public void clickOnYesDeactivateButton() {
		CCUtils.verifyAndWaitForElement(buttonYesDeactivate, driver);
		log("Click on yes deactivate button");
		buttonYesDeactivate.click();
	}
	
	public void verifyDeactivatePopup() {
		log("Verify 'Deactivate?' title");
			CCUtils.verifyAndWaitForElement(titleDeactivate, driver);
			CCUtils.assertEquals(titleDeactivate.getText().trim(), "Deactivate?");
			log("Verify Cancel button displayed");		
			CCUtils.verifyAndWaitForElement(buttonCancel, driver);
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
			log("Verify cross button displayed");		
			CCUtils.verifyAndWaitForElement(closeWindowIcon, driver);
			CCUtils.assertEquals(closeWindowIcon.getText().trim(), "clear");
			log("Verify button Yes, Deactivate displayed");		
			CCUtils.verifyAndWaitForElement(buttonYesDeactivate, driver);
			CCUtils.assertEquals(buttonYesDeactivate.getText().trim(), "Yes, deactivate");
		}
	}


