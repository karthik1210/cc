// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class VarResellerUserDetailsPage extends CCMainPage {

	@FindBy(id = "detailHeader")
	private WebElement labelPageTitle;

	@FindBy(id = "backBtn")
	private WebElement backButton;

	@FindBy(id = "editDetailsBtn")
	private WebElement buttonEditVarResellerUser;

	@FindBy(id = "cancelEdit")
	private WebElement buttonCancel;

	@FindBy(id = "saveDetail")
	private WebElement buttonSave;

	public VarResellerUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	public void verifyVarResellerUserDetailsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertTrue(labelPageTitle.isDisplayed(),
				"VAR/Reseller user details page title is not displayed on screen");
		String title = labelPageTitle.getText().trim().replace("arrow_back", "");
		CCUtils.assertEquals(title.trim(), "VAR/Reseller User Details");
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
		buttonCancel.click();
		log("Clicked on cancel button");
	}

	public void clickSave() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		buttonSave.click();
		logStep("Clicked on save button");
	}

}
