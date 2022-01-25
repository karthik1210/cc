// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.List;

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

	@FindBy(xpath = "//span[@class='label']")
	private List<WebElement> labelSelectedSearchOptions;

	@FindBy(id = "firstName")
	private WebElement labelSelectedFirstName;

	@FindBy(id = "lastName")
	private WebElement labelSelectedLastName;

	@FindBy(id = "email")
	private WebElement labelSelectedEmail;

	@FindBy(id = "resellerName")
	private WebElement labelSelectedVarResellerName;

	@FindBy(id = "userName")
	private WebElement labelSelectedUserName;

	@FindBy(id = "mobileNumber")
	private WebElement labelSelectedMobileNumber;

	@FindBy(id = "enrollStatus")
	private List<WebElement> labelSelectedEnrollmentStatus;

	@FindBy(id = "userStatus")
	private List<WebElement> labelSelectedUserStatus;

	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement buttonClearAll;

	@FindBy(id = "firstName")
	private List<WebElement> selectUser;

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

	public SearchOptionsPage clickOnSearchOptionsButton() {
		CCUtils.verifyAndWaitForElement(buttonSearchOption, driver);
		buttonSearchOption.click();
		log("Clicked on Search Option button in VAR Reseller");
		return PageFactory.initElements(driver, SearchOptionsPage.class);
	}

	public void verifyClearAllButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonClearAll, driver);
		CCUtils.assertTrue(buttonClearAll.isDisplayed(),
				"Clear All button is not displayed on var/reseller users page");
		CCUtils.assertEquals(buttonSearchOption.getText().trim(), "Clear All");
		CCUtils.assertTrue(buttonSearchOption.isEnabled(),
				"Clear All button is not enabled on var/reseller users page");
	}

	public void clickOnClearAllButton() {
		CCUtils.verifyAndWaitForElement(buttonClearAll, driver);
		javascriptClick(buttonClearAll);
	}

	public void verifyAllOptionsGotCleared() {
		log("Verifying search labels are not displayed on screen.");
		CCUtils.assertTrue(labelSelectedSearchOptions.isEmpty(), "Search labels are displayed on screen");
	}

	public void verifySelectedSearchOptionsWithSingleCheckboxOfStatus(List<String> enrollmentStatus,
			List<String> userStatus) {

		for (int i = 0; i < labelSelectedSearchOptions.size(); i++) {
			String fieldId = labelSelectedSearchOptions.get(i).getAttribute("id");
			String fieldValue = labelSelectedSearchOptions.get(i).getText().trim();
			if (fieldId.equals("enrollStatus")) {
				CCUtils.assertTrue(enrollmentStatus.contains(fieldValue), "Enrollment status is not present");
			} else if (fieldId.equals("userStatus")) {
				CCUtils.assertTrue(userStatus.contains(fieldValue), "User status is not present");
			}
		}
	}

	public void verifySelectedSearchOptions(List<String> enrollmentStatus, List<String> userStatus, String firstName,
			String lastName, String varResellerName, String email, String username, String mobileNumber) {

		for (int i = 0; i < labelSelectedSearchOptions.size(); i++) {
			String fieldId = labelSelectedSearchOptions.get(i).getAttribute("id");
			String fieldValue = labelSelectedSearchOptions.get(i).getText().trim();
			if (fieldId.equals("firstName")) {
				CCUtils.assertEquals(labelSelectedFirstName.getText().trim(), firstName);
			} else if (fieldId.equals("VarResellerName")) {
				CCUtils.assertEquals(labelSelectedLastName.getText().trim(), varResellerName);
			} else if (fieldId.equals("lastName")) {
				CCUtils.assertEquals(labelSelectedLastName.getText().trim(), lastName);
			} else if (fieldId.equals("userName")) {
				CCUtils.assertEquals(labelSelectedUserName.getText().trim(), username);
			} else if (fieldId.equals("email")) {
				CCUtils.assertEquals(labelSelectedEmail.getText().trim(), email);
			} else if (fieldId.equals("mobileNumber")) {
				CCUtils.assertEquals(labelSelectedMobileNumber.getText().trim(), mobileNumber);
			} else if (fieldId.equals("enrollStatus")) {
				CCUtils.assertTrue(enrollmentStatus.contains(fieldValue), "Enrollment status is not present");
			} else if (fieldId.equals("userStatus")) {
				CCUtils.assertTrue(userStatus.contains(fieldValue), "User status is not present");
			}
		}
	}

	public VarResellerUserDetailsPage selectUser() {
		CCUtils.verifyAndWaitForElement(selectUser.get(0), driver);
		javascriptClick(selectUser.get(0));
		log("Clicked on first VAR/Reseller user");
		return PageFactory.initElements(driver, VarResellerUserDetailsPage.class);
	}

}