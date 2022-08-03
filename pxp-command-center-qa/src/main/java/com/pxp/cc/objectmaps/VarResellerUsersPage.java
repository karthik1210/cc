// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class VarResellerUsersPage extends CCMainPage {

	@FindBy(xpath = "//span[@class='tab-name']")
	private WebElement labelPageTitle;
	
	@FindBy(xpath = "//button[text()=' SEND ENROLLMENT ']")
	private WebElement buttonSendEnrollment;

	@FindBy(className = "mat-button-toggle-label-content")
	private WebElement buttonSearchOption;

	@FindBy(xpath = "//span[@class='configure']")
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

	@FindBy(id = "newButton")
	private WebElement buttonNew;

	@FindBy(xpath = "//td[@id='no-result']")
	private WebElement errorMessage;

	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement buttonSearch;

	@FindBy(xpath = "//span[contains(text(),'Active')]")
	private WebElement activeCheckbox;

	@FindBy(id = "filterSearchBtn")
	private WebElement searchButton;

	@FindBy(xpath = "//span[contains(text(),'No roles assigned')]")
	private WebElement noRolesAssignedCheckbox;

	@FindBy(xpath = "//span[contains(text(),'Inactive')]")
	private WebElement inactiveCheckbox;

	@FindBy(xpath = "//span[contains(text(),'Locked out')]")
	private WebElement lockedCheckbox;

	@FindBy(xpath = "//td[@class='mat-cell cdk-cell cdk-column-firstName mat-column-firstName ng-star-inserted'][1]")
	private WebElement selectVarResellerUser;

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

	public void verifyErrorMessageOnVarResellerUsersPage() {
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertTrue(errorMessage.isDisplayed(), "No results found");
		log("No results found error message is displayed");
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
		CCUtils.assertEquals(buttonClearAll.getText().trim(), "CLEAR ALL");
		CCUtils.assertTrue(buttonClearAll.isEnabled(), "Clear All button is not enabled on var/reseller users page");
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

	public String getVarResellerUserStatus() {
		CCUtils.verifyAndWaitForElement(labelSelectedUserStatus.get(0), driver);
		return labelSelectedUserStatus.get(0).getAttribute("ng-reflect-message").toString().trim();
	}

	public AddNewVarResellerUserPage clickOnNewButton() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		buttonNew.click();
		log("Clicked on new button in VAR/Reseller users page");
		return PageFactory.initElements(driver, AddNewVarResellerUserPage.class);
	}

	public SearchOptionsPage clickOnSearchOptionPage() {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		buttonSearch.click();
		log("Clicked on search button in command center users page");
		return PageFactory.initElements(driver, SearchOptionsPage.class);
	}

	public void selectVarResellerUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectVarResellerUser, driver);
		selectVarResellerUser.click();
		log("Clicked on first VarReseller user");
	}

	public void clickOnActiveCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		CCUtils.verifyAndWaitForElement(activeCheckbox, driver);
		activeCheckbox.click();
		log("Clicked on active User Status checkbox on Search Options popup");
	}

	public void clickOnNoRolesAssignedCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		CCUtils.verifyAndWaitForElement(noRolesAssignedCheckbox, driver);
		noRolesAssignedCheckbox.click();
		log("Clicked on no roles assigned User Status checkbox on Search Options popup");
	}

	public void clickOnInactiveCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		CCUtils.verifyAndWaitForElement(inactiveCheckbox, driver);
		inactiveCheckbox.click();
		log("Clicked on inactive User Status checkbox on Search Options popup");
	}

	public void clickOnLockedCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		CCUtils.verifyAndWaitForElement(lockedCheckbox, driver);
		lockedCheckbox.click();
		log("Clicked on inactive User Status checkbox on Search Options popup");
	}
}
