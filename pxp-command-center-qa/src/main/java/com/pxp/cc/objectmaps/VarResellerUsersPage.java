// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class VarResellerUsersPage extends CCMainPage {

	@FindBy(className = "tab-name")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//button[text()=' SEND ENROLLMENT ']")
	private WebElement buttonSendEnrollment;

	public VarResellerUsersPage(WebDriver driver) {
		super(driver);
	}

	public void verifySendEnrollmentButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollment, driver);
		CCUtils.assertTrue(buttonSendEnrollment.isDisplayed(),
				"Send enrollment button is not displayed on var/reseller users page");
		CCUtils.assertEquals(buttonSendEnrollment.getText().trim(), "SEND ENROLLMENT");
		CCUtils.assertTrue(buttonSendEnrollment.isEnabled(),
				"Send enrollment button is not enabled on var/reseller users page");
	}

	public SendBulkEnrollmentPage clickOnSendEnrollmentButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollment, driver);
		buttonSendEnrollment.click();
		log("Clicked on send enrollment button");
		return PageFactory.initElements(driver, SendBulkEnrollmentPage.class);
	}

	public void verifyVarResellerUsersPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "VAR/Reseller Users");
		log("VAR/Reseller users page title is displayed");
	}

}
