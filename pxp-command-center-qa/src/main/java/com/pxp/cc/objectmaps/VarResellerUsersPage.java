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

	@FindBy(xpath = "//button[text()=' SEARCH OPTIONS ']")
	private WebElement buttonSearchOption;
	
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
	
	public void verifySearchOptionsButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSearchOption, driver);
		CCUtils.assertTrue(buttonSearchOption.isDisplayed(),
				"Search Option button is not displayed on var/reseller users page");
		CCUtils.assertEquals(buttonSearchOption.getText().trim(), "SEARCH OPTIONS");
		CCUtils.assertTrue(buttonSearchOption.isEnabled(),
				"Search Option button is not enabled on var/reseller users page");
	}
	
	public SearchOptionsPage clickOnSearchOptionsButton(){
		CCUtils.verifyAndWaitForElement(buttonSearchOption, driver);
		buttonSearchOption.click();
		log("Clicked on Search Option button in VAR Reseller");
		return PageFactory.initElements(driver, SearchOptionsPage.class);
	}
}
