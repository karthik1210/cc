// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class UnsavedChangesPage extends CCMainPage {

	@FindBy(id = "unsavedCancelBtn")
	private WebElement buttonDisregardChanges;

	@FindBy(id = "unsavedConfirmBtn")
	private WebElement buttonRetutnToPage;

	@FindBy(id = "unsavedClearIcon")
	private WebElement buttonCloseWindow;

	@FindBy(xpath = "//span[text()='Unsaved changes']")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[@class='content']")
	private WebElement labelConfirmationQuestion;

	public UnsavedChangesPage(WebDriver driver) {
		super(driver);
	}

	public void verifyUnsavedChangesPopupPageTitle() {
		log("Verifying unsaved changes page title");
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertTrue(labelPageTitle.isDisplayed(), "Unsaved Changes page title is not displayed");
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Unsaved changes");
	}

	public void verifyConfirmationQuestionText() {
		log("Verifying confirmation question text is displayed on the unsaved changes popup");
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		CCUtils.assertTrue(labelConfirmationQuestion.isDisplayed(),
				"Confirmation question is not displayed on the unsaved changes popup");
		CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(), "Would you like to save your changes?");
	}

	public void verifyReturnToPageButtonIsDisplayed() {
		log("Verifying return to page button is displayed on the unsaved changes popup");
		CCUtils.verifyAndWaitForElement(buttonRetutnToPage, driver);
		CCUtils.assertTrue(buttonRetutnToPage.isDisplayed(),
				"Return to page button is not displayed on the unsaved changes popup");
		CCUtils.assertTrue(buttonRetutnToPage.isEnabled(), "Return to page button is disabled");
		CCUtils.assertEquals(buttonRetutnToPage.getText().trim(), "Return to Page");
	}

	public void verifyDisregardChangesButtonIsDisplayed() {
		log("Verifying disregard changes button is displayed on the unsaved changes popup");
		CCUtils.verifyAndWaitForElement(buttonDisregardChanges, driver);
		CCUtils.assertTrue(buttonDisregardChanges.isDisplayed(),
				"Disregard changes button is not displayed on the unsaved changes popup");
		CCUtils.assertTrue(buttonDisregardChanges.isEnabled(), "Disregard changes button is disabled");
		CCUtils.assertEquals(buttonDisregardChanges.getText().trim(), "Disregard Changes");
	}

	public void verifyCrossIconIsDisplayed() {
		log("Verifying cross icon is displayed on the unsaved changes popup");
		CCUtils.verifyAndWaitForElement(buttonCloseWindow, driver);
		CCUtils.assertTrue(buttonCloseWindow.isDisplayed(), "Cross icon is not displayed on unsaved changes popup");
		CCUtils.assertEquals(buttonCloseWindow.isEnabled(), true);
		CCUtils.assertEquals(buttonCloseWindow.getText().trim(), "clear");
	}

	public void clickOnCloseWindow() {
		CCUtils.verifyAndWaitForElement(buttonCloseWindow, driver);
		buttonCloseWindow.click();
		log("Clicked on close window button on the unsaved changes popup");
	}

	public void clickOnReturnToPageButton() {
		CCUtils.verifyAndWaitForElement(buttonRetutnToPage, driver);
		buttonRetutnToPage.click();
		log("Clicked on return to page button on the unsaved changes popup");
	}

	public void clickOnDisregardChangesButton() {
		CCUtils.verifyAndWaitForElement(buttonDisregardChanges, driver);
		buttonDisregardChanges.click();
		log("Clicked on disregard changes button on the unsaved changes popup");
	}
}
