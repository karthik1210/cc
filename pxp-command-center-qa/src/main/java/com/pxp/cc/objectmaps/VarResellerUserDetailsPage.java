// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class VarResellerUserDetailsPage extends CCMainPage {

	@FindBy(id = "backBtn")
	private WebElement backButton;

	@FindBy(id = "editUser")
	private WebElement buttonEditVarResellerUser;

	@FindBy(id = "cancelUser")
	private WebElement buttonCancel;

	@FindBy(id = "saveUser")
	private WebElement buttonSave;

	@FindBy(xpath = "//button[@class='mat-menu-trigger menu-button']")
	private WebElement buttonKebabMenu;

	@FindBy(xpath = "//button[contains(text(),'Lock User')]")
	private WebElement buttonLockUser;

	@FindBy(id = "detailSaveBtn")
	private WebElement buttonUnlockUser;

	@FindBy(xpath = "//div[@class='ml-auto']")
	private WebElement labelSelectedUserStatus;

	@FindBy(xpath = "//span[@class='pl-3']")
	private WebElement inactiveStatus;

	@FindBy(xpath = "//span[@class='m-2']")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//button[contains(text(),' Deactivate User ')]")
	private WebElement buttonDeactivateUser;

	@FindBy(xpath = "//mat-card-title[@class='mat-card-title pt-1 text-oneline']")
	private WebElement labelUsernameValue;

	@FindBy(xpath = "//div[@class='ml-auto']")
	private WebElement labelVarResellerUserStatusValue;

	@FindBy(xpath = "//button[contains(text(),'Activate User')]")
	private WebElement kebabActiveUserOption;

	public VarResellerUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	public void verifyVarResellerUserDetailsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertTrue(labelPageTitle.isDisplayed(),
				"VAR/Reseller user details page title is not displayed on screen");
		String title = labelPageTitle.getText().trim();
		CCUtils.assertEquals(title.trim(), "User Details");
		CCUtils.assertEquals(labelPageTitle.isDisplayed(), true);
		log("VarReseller user details page title is displayed on screen");
	}

	public void clickEditInVarResellerUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditVarResellerUser, driver);
		javascriptClick(buttonEditVarResellerUser);
		log("Clicked on edit button");
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		CCUtils.assertTrue(buttonCancel.isDisplayed(),
				"Cancel button is not present on VAR/Reseller user details page");
		CCUtils.assertEquals(buttonCancel.getText().trim(), "CANCEL");
	}

	public void verifySaveButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		CCUtils.assertTrue(buttonSave.isDisplayed(), "Save button is not present on VAR/Reseller user details page");
		CCUtils.assertEquals(buttonSave.getText().trim(), "SAVE");
	}

	public void clickCancel() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		javascriptClick(buttonCancel);
		log("Clicked on cancel button");
	}

	public void clickSave() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		javascriptClick(buttonSave);
		logStep("Clicked on save button");
	}

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		log("Click on kebab menu icon on VarReseller user details page");
		javascriptClick(buttonKebabMenu);
	}

	public void verifyLockUserButtonIsDisplayed() {
		log("Verifying lock user button is displayed in kebab menu");
		CCUtils.assertEquals(buttonLockUser.isDisplayed(), true);
	}

	public void verifyLockUserButtonIsNotDisplayed() {
		log("Verifying lock user button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonLockUser.isDisplayed(), false);
	}

	public LockUserConfirmationPage clickOnLockUserButton() {
		CCUtils.verifyAndWaitForElement(buttonLockUser, driver);
		javascriptClick(buttonLockUser);
		return PageFactory.initElements(driver, LockUserConfirmationPage.class);
	}

	public void verifyUnlockButtonIsDispalyed() {
		CCUtils.verifyAndWaitForElement(buttonUnlockUser, driver);
		CCUtils.assertTrue(buttonUnlockUser.isDisplayed(),
				"Unlock button is not displayed on VarReseller user details page");
	}

	public void clickOnUnlockButton() {
		CCUtils.verifyAndWaitForElement(buttonUnlockUser, driver);
		javascriptClick(buttonUnlockUser);
		log("Clicked on unlock user button on the VarReseller user details page");
	}

	public void verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(String userStatus) {
		CCUtils.verifyAndWaitForElement(labelSelectedUserStatus, driver);
		if (labelSelectedUserStatus.isDisplayed() == true) {
			CCUtils.assertEquals(labelSelectedUserStatus.getText().trim(), userStatus);
		} else {
			log("User status value is not present on  user details page");
		}
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(labelUsernameValue, driver);
		return labelUsernameValue.getText().trim();
	}

	public DeactivateUserConfirmationPage clickOnDeactivateUserButton() {
		CCUtils.verifyAndWaitForElement(buttonDeactivateUser, driver);
		log("Click on deactivate user button");
		javascriptClick(buttonDeactivateUser);
		return PageFactory.initElements(driver, DeactivateUserConfirmationPage.class);
	}

	public void verifyDeactivateUsertButtonIsNotDisplayed() {
		log("Verifying deactivate user button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonDeactivateUser.isDisplayed(), true);
	}

	public void verifyDeactivateUserButtonIsDisplayed() {
		log("Verifying deactivate user button is displayed in kebab menu");
		CCUtils.assertEquals(buttonDeactivateUser.isDisplayed(), true);
	}

	public void checkInactiveStatus() {
		CCUtils.assertEquals(inactiveStatus.isDisplayed(), true);
	}

	public void verifyVarResellerUsersPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.isDisplayed(), true);
		log("VarReseller user details page title is displayed on screen");
	}

	public void verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage() {
		CCUtils.verifyAndWaitForElement(labelVarResellerUserStatusValue, driver);
		CCUtils.assertEquals(labelVarResellerUserStatusValue.isDisplayed(), true);
		log("User status value is present on VarReseller user details page");
	}

	public void verifyDeactivateUsertButtonIsNotEnabled() {
		log("Verifying deactivate user button is enabled in kebab menu");
		CCUtils.assertEquals(!buttonDeactivateUser.isEnabled(), false);
	}

	public void verifyActivateUserButtonIsDisplayed() {
		log("Verifying activate user button is displayed in kebab menu");
		CCUtils.assertEquals(kebabActiveUserOption.isDisplayed(), true);
	}
}
