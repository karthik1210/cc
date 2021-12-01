// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class SendBulkEnrollmentPage extends CCMainPage {

	@FindBy(className = "cc-page-title")
	private WebElement labelPageTitle;

	@FindBy(id = "applyCancel")
	private WebElement buttonCancel;

	@FindBy(id = "enrollBtn")
	private WebElement buttonSendEnrollment;

	@FindBy(xpath = "//th[@role='columnheader'][1]/mat-checkbox")
	private WebElement indeterminateCheckbox;

	@FindBy(xpath = "//td[@role='gridcell']/span/mat-icon")
	private List<WebElement> userStatusList;

	@FindBy(xpath = "//td[@role='gridcell']/mat-checkbox/label/div/input")
	private List<WebElement> checkboxList;

	@FindBy(id = "mat-button-toggle-4-button")
	private WebElement buttonSearchOptions;

	@FindBy(xpath = "//mat-icon[text()='arrow_back']")
	private WebElement iconBackArrow;

	@FindBy(id = "enrollStatus")
	private List<WebElement> labelSelectedEnrollmentStatus;

	@FindBy(xpath = "//span[@class='label']")
	private List<WebElement> labelSelectedSearchOptions;

	@FindBy(id = "firstName")
	private WebElement labelSelectedFirstName;

	@FindBy(id = "lastName")
	private WebElement labelSelectedLastName;

	@FindBy(id = "email")
	private WebElement labelSelectedEmail;

	@FindBy(id = "userName")
	private WebElement labelSelectedUserName;

	@FindBy(id = "mobileNumber")
	private WebElement labelSelectedMobileNumber;

	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement buttonClearAll;

	public SendBulkEnrollmentPage(WebDriver driver) {
		super(driver);
	}

	public void verifySendBulkEnrollmentPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed()) {
			CCUtils.assertEquals(labelPageTitle.getText().trim(), "Select Users to Send Enrollment");
			log("Send bulk enrollments page title is " + labelPageTitle.getText().trim());
		} else {
			log("Send bulk enrollments page title is not displayed");
		}
	}

	public void selectUsers(int numberOfUsers) {
		log("Select " + numberOfUsers + " user");
		if (numberOfUsers > 0 && numberOfUsers < checkboxList.size()) {
			for (int i = 0; i < numberOfUsers; i++) {
				javascriptClick(checkboxList.get(i));
			}
		} else {
			log("Enter Valid Number");
		}
	}

	public SendEnrollmentConfirmationPage clickOnSendEnrollmentButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollment, driver);
		buttonSendEnrollment.click();
		return PageFactory.initElements(driver, SendEnrollmentConfirmationPage.class);
	}

	public void verifySendEnrollmentButtonIsDisabled() {
		log("Verifying send enrollment button is disabled.");
		CCUtils.assertEquals(buttonSendEnrollment.getAttribute("disabled"), "true");
	}

	public void verifySendEnrollmentButtonIsEnabled() {
		log("Verifying send enrollment button is disabled.");
		CCUtils.verifyAndWaitForElement(buttonSendEnrollment, driver);
		CCUtils.assertTrue(buttonSendEnrollment.isEnabled(), "Send Enrollment button is disabled");
	}

	public void VerifyOnlyActiveUsersDisplayedInSendBulkEnrollmentList() {
		log("Verifying only active users are displayed in send bulk enrollment list on send bulk enrollment page");
		for (int i = 0; i < userStatusList.size(); i++) {
			CCUtils.assertEquals(userStatusList.get(i).getText().trim(), "check_circle");
		}
	}

	public void clickOnIndeterminateCheckbox() {
		CCUtils.verifyAndWaitForElement(indeterminateCheckbox, driver);
		indeterminateCheckbox.click();
	}

	public void verifyAllCheckboxesAreSelected() {
		log("Verifying all checkboxes are selected");
		for (int i = 0; i < checkboxList.size(); i++) {
			CCUtils.assertTrue(checkboxList.get(i).isSelected(), "Checkox is not selected");
		}
	}

	public void verifyAllCheckboxesAreNotSelected() {
		log("Verifying all checkboxes are not selected");
		for (int i = 0; i < checkboxList.size(); i++) {
			CCUtils.assertTrue(checkboxList.get(i).getAttribute("aria-checked").equals("false"), "Checkox is selected");
		}
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public SearchOptionsPage clickOnSearchOptionsButton() {
		CCUtils.verifyAndWaitForElement(buttonSearchOptions, driver);
		buttonSearchOptions.click();
		return PageFactory.initElements(driver, SearchOptionsPage.class);
	}

	public void verifySelectedSearchOptions(List<String> enrollmentStatus, String firstName, String lastName,
			String email, String username, String mobileNumber) {

		for (int i = 0; i < labelSelectedSearchOptions.size(); i++) {
			String fieldId = labelSelectedSearchOptions.get(i).getAttribute("id");
			String fieldValue = labelSelectedSearchOptions.get(i).getText().trim();
			if (fieldId.equals("firstName")) {
				CCUtils.assertEquals(labelSelectedFirstName.getText().trim(), firstName);
			} else if (fieldId.equals("enrollStatus")) {
				CCUtils.assertTrue(enrollmentStatus.contains(fieldValue), "Enrollment status is not present");
			} else if (fieldId.equals("lastName")) {
				CCUtils.assertEquals(labelSelectedLastName.getText().trim(), lastName);
			} else if (fieldId.equals("userName")) {
				CCUtils.assertEquals(labelSelectedUserName.getText().trim(), username);
			} else if (fieldId.equals("email")) {
				CCUtils.assertEquals(labelSelectedEmail.getText().trim(), email);
			} else if (fieldId.equals("mobileNumber")) {
				CCUtils.assertEquals(labelSelectedMobileNumber.getText().trim(), mobileNumber);
			}
		}

	}

	public void clickOnClearAllButton() {
		CCUtils.verifyAndWaitForElement(buttonClearAll, driver);
		javascriptClick(buttonClearAll);
	}

	public void verifyAllOptionsGotCleared() {
		log("Verifying search labels are not displayed on screen.");
		CCUtils.assertTrue(labelSelectedSearchOptions.isEmpty(), "Search labels are displayed on screen");
	}

}
