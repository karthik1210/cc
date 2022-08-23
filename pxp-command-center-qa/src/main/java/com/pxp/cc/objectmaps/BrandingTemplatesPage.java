// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class BrandingTemplatesPage extends CCMainPage {

	@FindBy(xpath = "//div[contains(text(),'Branding Templates')]")
	private WebElement brandingTemplatesLabel;

	@FindBy(xpath = "//span[contains (text(),' bhupesh clinic ')]")
	private WebElement displayName;

	@FindBy(xpath = "(//span[contains(text(),' ACTIVE ')])[1]")
	private WebElement activeStatus;

	@FindBy(xpath = "(//span[contains(text(),' INACTIVE ')])[3]")
	private WebElement inactiveStatus;

	@FindBy(xpath = "(//mat-icon[@role='img'][contains(text(),'more_vert')])[1]")
	private WebElement kebabMenuForActiveStatus;

	@FindBy(xpath = "(//mat-icon[@role='img'][contains(text(),'more_vert')])[2]")
	private WebElement kebabMenu;

	@FindBy(id = "duplicate")
	private WebElement duplicateButton;

	@FindBy(id = "delete_forever")
	private WebElement deleteButton;

	@FindBy(id = "edit")
	private WebElement editButton;

	@FindBy(xpath = "//span[@class='mat-dialog-title title']")
	private WebElement labelConfirmationQuestion;

	@FindBy(id = "unsavedConfirmBtn")
	private WebElement yesDeleteButton;

	@FindBy(id = "unsavedCancelBtn")
	private WebElement CancelButton;

	@FindBy(xpath = "//span[@class='material-icons cross']")
	private WebElement CrossIcon;

	public BrandingTemplatesPage(WebDriver driver) {
		super(driver);
	}

	public void VerifyBrandingTemplatesIsDisplayed() throws InterruptedException, AWTException {

		CCUtils.scrollPageViaElement(driver, brandingTemplatesLabel);
		CCUtils.verifyAndWaitForElement(brandingTemplatesLabel, driver);
		log("Verifying Inactive status text");
		CCUtils.assertEquals(brandingTemplatesLabel.getText().trim(), "Branding Templates");
	}

	public void VerifyBrandingNameIsDisplayed() throws InterruptedException, AWTException {

		CCUtils.verifyAndWaitForElement(displayName, driver);
		log("Verifying Inactive status text");
		CCUtils.assertEquals(displayName.getText().trim(), "bhupesh clinic");
	}

	public void VerifyBrandingNameIsActive() throws InterruptedException, AWTException {

		CCUtils.verifyAndWaitForElement(activeStatus, driver);
		log("Verifying Inactive status text");
		CCUtils.assertEquals(activeStatus.getText().trim(), "ACTIVE");
	}

	public void VerifyBrandingNameIsInactive() throws InterruptedException, AWTException {

		CCUtils.verifyAndWaitForElement(inactiveStatus, driver);
		log("Verifying Inactive status text");
		CCUtils.assertEquals(inactiveStatus.getText().trim(), "INACTIVE");
	}

	public void clickOnKebabMenu() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(kebabMenu, driver);
		javascriptClick(kebabMenu);
		log("Clicked on kebabMenu");
	}

	public void clickOnkebabMenuForActiveStatus() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(kebabMenuForActiveStatus, driver);
		javascriptClick(kebabMenuForActiveStatus);
		log("Clicked on kebabMenu");
	}

	public void verifyEditOptionForActiveStatus() throws InterruptedException {

		CCUtils.verifyAndWaitForElement(editButton, driver);
		CCUtils.assertTrue(true, editButton.getText().trim());
		log("Edit option is  display");
	}

	public void clickOnDuplicate() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(duplicateButton, driver);
		javascriptClick(duplicateButton);
		log("Clicked on duplicate");
	}

	public void clickOnDelete() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(deleteButton, driver);
		javascriptClick(deleteButton);
		log("Clicked on delete");
	}

	public void verifyConfirmationPopupForDelete() {
		verifyConfirmationPageTitle();
		verifyCancelButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
		verifyYesDeleteIsDisplayed();
	}

	public void verifyConfirmationPageTitle() {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed() == true) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(),
					"Are you sure you want to delete this item?");
			log("Delete Inactive branding template confirmation text is = "
					+ labelConfirmationQuestion.getText().trim());
		} else {
			log("Delete Inactive branding template confirmation text is not displayed");
		}
	}

	public void verifyYesDeleteIsDisplayed() {
		CCUtils.verifyAndWaitForElement(yesDeleteButton, driver);
		CCUtils.assertTrue(yesDeleteButton.isDisplayed(),
				"Yes, Delete button is not displayed on delete confirmation popup");
		CCUtils.assertEquals(yesDeleteButton.getText().trim(), "Yes, Delete");
		CCUtils.assertEquals(yesDeleteButton.isEnabled(), true);
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(CancelButton, driver);
		CCUtils.assertTrue(CancelButton.isDisplayed(), "Cancel button is not displayed on delete confirmation popup");
		CCUtils.assertEquals(CancelButton.getText().trim(), "Cancel");
		CCUtils.assertEquals(CancelButton.isEnabled(), true);
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(CrossIcon, driver);
		CCUtils.assertTrue(CrossIcon.isDisplayed(), "Cross icon is not displayed on delete confirmation popup");
		CCUtils.assertEquals(CrossIcon.getText().trim(), "clear");
		CCUtils.assertEquals(CrossIcon.isEnabled(), true);
	}

	public void clickOnYesDelete() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(yesDeleteButton, driver);
		javascriptClick(yesDeleteButton);
		log("Clicked on Yes,Delete Button");
	}

	public void clickOnCancel() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(CancelButton, driver);
		javascriptClick(CancelButton);
		log("Clicked on Cancel");
	}

	public void clickOnCrossIcon() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(CrossIcon, driver);
		javascriptClick(CrossIcon);
		log("Clicked on CrossIcon");
	}
}