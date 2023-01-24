// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.pxp.cc.util.CCUtils;

public class SearchOptionsPage extends CCMainPage {

	public SearchOptionsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[contains(text(),'First Name')]")
	private WebElement labelFirstName;

	@FindBy(xpath = "//div[contains(text(),'Last Name')]")
	private WebElement labelLastName;

	@FindBy(xpath = "//div[contains(text(),'Client')]")
	private WebElement labelClient;

	@FindBy(xpath = "//div[contains(text(),'Email')]")
	private WebElement labelEmail;

	@FindBy(xpath = "//span[contains(text(),'Username')]")
	private WebElement labelUsername;

	@FindBy(xpath = "//div[contains(text(),' Mobile Number ')]")
	private WebElement labelMobileNumber;

	@FindBy(id = "searchFirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "searchLastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "searchUserNameInput")
	private WebElement textboxUsername;

	@FindBy(id = "searchclientNameInput")
	private WebElement clientName;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(id = "searchEmailInput")
	private WebElement textboxEmail;

	@FindBy(id = "searchMobileInput")
	private WebElement textboxMobileNumber;

	@FindBy(xpath = "//div[contains(@class, 'userStatus')]")
	private WebElement labelUserStatus;

	@FindBy(id = "searchUserStatus")
	private WebElement labelUserStatusList;

	@FindBy(xpath = "//div[contains(@class, 'userStatus')]//following::input[@type='checkbox']")
	private List<WebElement> checkboxUserStatus;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//following::div[@class='mat-checkbox-inner-container']")
	private WebElement checkboxUserStatusSingle;

	@FindBy(xpath = "//span[contains(text(),'Active')]")
	private WebElement userStatusActive;

	@FindBy(xpath = "//span[contains(text(),'Inactive')]")
	private WebElement userStatusInactive;

	@FindBy(xpath = "//span[contains(text(),'Locked out')]")
	private WebElement userStatusLockedOut;

	@FindBy(xpath = "//span[contains(text(),'No roles assigned')]")
	private WebElement userStatusNoRolesAssigned;

	@FindBy(xpath = "//div[contains(@class, 'enrStatus')]")
	private WebElement labelEnrollmentStatus;

	@FindBy(id = "searchEnrollmentStatus")
	private List<WebElement> labelEnrollmentStatusList;

	@FindBy(xpath = "//div[@class='mat-checkbox-inner-container']")
	private List<WebElement> checkboxEnrollmentAndUserStatusList;

	@FindBy(xpath = "//div[contains(text(),'Enrollment Status')]//following::input[@type='checkbox']")
	private List<WebElement> checkboxEnrollmentStatus;

	@FindBy(xpath = "//div[contains(text(),'EnrollmentStatus')]//following::div[@class='mat-checkbox-inner-container']")
	private WebElement checkboxEnrollmentStatusSingle;

	@FindBy(xpath = "//span[contains(text(),'Confirmed')]")
	private WebElement enrollmentStatusConfirmed;

	@FindBy(xpath = "//span[contains(text(),'Delivered')]")
	private WebElement enrollmentStatusDelivered;

	@FindBy(xpath = "//span[contains(text(),'Pending')]")
	private WebElement enrollmentStatusPending;

	@FindBy(xpath = "//span[contains(text(),'Email Issue')]")
	private WebElement enrollmentStatusEmailIssue;

	@FindBy(xpath = "//span[contains(text(),'Expired')]")
	private WebElement enrollmentStatusExpired;

	@FindBy(xpath = "//span[contains(text(),'Not Sent')]")
	private WebElement enrollmentStatusNotSent;

	@FindBy(id = "filterClearBtn")
	private WebElement buttonClear;

	@FindBy(id = "filterSearchBtn")
	private WebElement buttonSearch;

	@FindBy(id = "searchFilterBtn")
	private WebElement buttonHideSearchOptions;

	@FindBy(xpath = "//label[contains(text(),'Please enter or select to search')]")
	private WebElement errorSearchOptionsPage;

	@FindBy(id = "maxFirstNameError")
	private WebElement errorMaximumCharacterFirstName;

	@FindBy(id = "maxLastNameError")
	private WebElement errorMaximumCharacterLastName;

	@FindBy(id = "maxUserNameError")
	private WebElement errorMaximumCharacterUsername;

	@FindBy(id = "maxEmailError")
	private WebElement errorMaximumCharacterEmail;

	@FindBy(xpath = "//input[@formcontrolname='mobileNumber']//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterMobileNumber;

	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement butonClearAll;

	@FindBy(xpath = "//mat-icon[contains(text(),'clear')]")
	private List<WebElement> buttonFilterSearchCrossIcon;

	@FindBy(xpath = "//div[contains(text(),'Please enter valid email address')]")
	private WebElement errorValidEmail;

	@FindBy(xpath = "//div[text()='Please enter valid first name']")
	private WebElement errorValidFirstName;

	@FindBy(xpath = "//div[text()='Please enter valid last name']")
	private WebElement errorValidLastName;

	@FindBy(xpath = "//div[text()='Please enter valid username']")
	private WebElement errorValidUsername;

	@FindBy(xpath = "//div[contains(text(),'Please enter valid mobile number')]")
	private WebElement errorValidMobileNumber;

	@FindBy(xpath = "//div[text()=' Enrollment Status ']//following::input[@type='checkbox']")
	private List<WebElement> enrollmentStatusCheckboxList;

	@FindBy(xpath = "//div[text()=' Enrollment Status ']//following::span[@class='mat-checkbox-label']")
	private List<WebElement> listEnrollmentStatusLabel;

	@FindBy(xpath = "//span[text()='VAR/Reseller']")
	private WebElement labelVarReseller;

	@FindBy(xpath = "//input[@formcontrolname='resellerName']")
	private WebElement dropdownSearchVarReseller;

	@FindBy(xpath = "//span[contains(text(),'No roles assigned')]")
	private WebElement noRolesAssignedCheckbox;

	@FindBy(xpath = "//span[contains(text(),'Active')]")
	private WebElement activeCheckbox;

	@FindBy(xpath = "//span[normalize-space(text())='Confirmed']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement confirmedStatusCheckBox;

	@FindBy(xpath = "//span[normalize-space(text())='Pending']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement pendingStatusCheckBox;

	@FindBy(xpath = "//span[normalize-space(text())='Expired']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement expiredStatusCheckBox;

	@FindBy(xpath = "//span[@class='mat-checkbox-label']/span[normalize-space(text())='Not Sent']/../preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement notSentStatusCheckBox;

	public final List<String> expectedListOfEnrollmentStatusValues = Arrays.asList("Delivered", "Pending",
			"Email Issue", "Expired", "Not Sent");

	public void verifySearchInternalUserPopup() throws InterruptedException, AWTException {
		log("Verifying search internal user pop-up");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		verifyUserStatusLabelTextAndCheckboxes();
		verifyEnrollmentStatusLabelTextAndCheckboxes();
		verifyClearButtonIsDisplayed();
		verifySearchButtonIsDisplayed();
	}

	public void verifySearchClientUserPopup() throws InterruptedException, AWTException {
		log("Verifying search client user pop-up");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyClientLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		verifyUserStatusLabelTextAndCheckboxes();
		verifyEnrollmentStatusLabelTextAndCheckboxes();
		verifyClearButtonIsDisplayed();
		verifySearchButtonIsDisplayed();
	}

	public void verifySearchVarResellerUserPopup() throws InterruptedException, AWTException {
		log("Verifying Search Options pop up on the VAR/Reseller users page");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyVarResellerLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		verifyEnrollmentStatusLabelTextAndCheckboxes();
		verifyUserStatusLabelTextAndCheckboxes();
		verifyClearButtonIsDisplayed();
		verifySearchButtonIsDisplayed();
	}

	public void verifyFirstNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelFirstName, driver);
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		if (labelFirstName.isDisplayed() == true && textboxFirstName.isDisplayed() == true) {
			log("Verifying first name label text");
			CCUtils.assertEquals(labelFirstName.getText().trim(), "First Name");
			log("First name label text is " + labelFirstName.getText().trim());
			log("Verifying input text placeholder is displayed for first name textbox field");
			CCUtils.assertEquals(textboxFirstName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for first name textbox field is " + textboxFirstName.getAttribute("placeholder").trim());
		} else {
			log("First name label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyLastNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLastName, driver);
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		if (labelLastName.isDisplayed() == true && textboxLastName.isDisplayed() == true) {
			log("Verifying last name label text");
			CCUtils.assertEquals(labelLastName.getText().trim(), "Last Name");
			log("Last name label text is " + labelLastName.getText().trim());
			log("Verifying input text placeholder is displayed for last name textbox field");
			CCUtils.assertEquals(textboxLastName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for last name textbox field is " + textboxLastName.getAttribute("placeholder").trim());
		} else {
			log("Last name label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyClientLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelClient, driver);
		CCUtils.verifyAndWaitForElement(clientName, driver);
		log("Verifying client label text");
		CCUtils.assertEquals(labelClient.getText().trim(), "Client");
		log("Client label text is " + labelClient.getText().trim());
		log("Verifying Input Text placeholder is displayed for client textbox field");
		CCUtils.assertEquals(clientName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for client textbox field is " + clientName.getAttribute("placeholder").trim());
	}

	public void verifyUsernameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUsername, driver);
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		if (labelUsername.isDisplayed() == true && textboxUsername.isDisplayed() == true) {
			log("Verifying username label text");
			CCUtils.assertEquals(labelUsername.getText().trim(), "Username");
			log("Username label text is " + labelUsername.getText().trim());
			log("Verifying input text placeholder is displayed for username textbox field ");
			CCUtils.assertEquals(textboxUsername.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for username textbox field is " + textboxUsername.getAttribute("placeholder").trim());
		} else {
			log("Username label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyEmailLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		if (labelEmail.isDisplayed() == true && textboxEmail.isDisplayed() == true) {
			log("Verifying email label text");
			CCUtils.assertEquals(labelEmail.getText().trim(), "Email");
			log("Email label text is " + labelEmail.getText().trim());
			log("Verifying input text placeholder is displayed for email textbox field ");
			CCUtils.assertEquals(textboxEmail.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for email textbox field is " + textboxEmail.getAttribute("placeholder").trim());
		} else {
			log("Email label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyMobileNumberLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelMobileNumber, driver);
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		if (labelMobileNumber.isDisplayed() == true && textboxMobileNumber.isDisplayed() == true) {
			log("Verifying mobile number label text");
			CCUtils.assertEquals(labelMobileNumber.getText().trim(), "Mobile Number");
			log("Mobile number label text is " + labelMobileNumber.getText().trim());
			log("Verifying input text placeholder is displayed for mobile number textbox field ");
			CCUtils.assertEquals(textboxMobileNumber.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for mobile number textbox field is "
					+ textboxMobileNumber.getAttribute("placeholder").trim());
		} else {
			log("Mobile number label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyUserStatusLabelTextAndCheckboxes() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelUserStatus, driver);
		if (labelUserStatus.isDisplayed() == true) {
			log("Verifying status label text");
			CCUtils.assertEquals(labelUserStatus.getText().trim(), "User Status");
			log("User Status label text is " + labelUserStatus.getText().trim());
			log("Verifying User status label are displayed on search options popup");
			verifyUserStatusValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserStatusCheckboxesAreNotByDefaultSelected();
		} else {
			log("User status label is not displayed on search options popup");
		}
	}

	public void verifyUserStatusValues() throws InterruptedException {
		log("Verifying userStatus Active");
		CCUtils.verifyAndWaitForElement(userStatusActive, driver);
		CCUtils.assertEquals(userStatusActive.getText().trim(), "Active");
		log("Active user status label text is " + userStatusActive.getText().trim());

		log("Verifying userStatus Inactive");
		CCUtils.verifyAndWaitForElement(userStatusInactive, driver);
		CCUtils.assertEquals(userStatusInactive.getText().trim(), "Inactive");
		log("Active user status label text is " + userStatusInactive.getText().trim());

		log("Verifying userStatus Locked out");
		CCUtils.verifyAndWaitForElement(userStatusLockedOut, driver);
		CCUtils.assertEquals(userStatusLockedOut.getText().trim(), "Locked out");
		log("Active user status label text is " + userStatusLockedOut.getText().trim());

		log("Verifying userStatus No roles assigned");
		CCUtils.verifyAndWaitForElement(userStatusNoRolesAssigned, driver);
		CCUtils.assertEquals(userStatusNoRolesAssigned.getText().trim(), "No roles assigned");
		log("Active user status label text is " + userStatusNoRolesAssigned.getText().trim());

	}

	public void verifyUserStatusCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxUserStatus.size(); i++) {
			CCUtils.assertTrue(!checkboxUserStatus.get(i).isSelected(), "User role checkboxes are not selected");
		}
	}

	public void verifySearchFilterCrossIcon() throws InterruptedException {
		for (int i = 0; i < buttonFilterSearchCrossIcon.size(); i++) {
			buttonFilterSearchCrossIcon.get(i).click();
		}
	}

	public void verifyClearButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		CCUtils.assertEquals(buttonClear.getText().trim(), "CLEAR");
	}

	public void verifyClearAllButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(butonClearAll, driver);
		CCUtils.assertEquals(butonClearAll.getText().trim(), "CLEAR ALL");
	}

	public void clickOnClearButton() throws InterruptedException {
		log("Verifying clear button is displayed on search options popup");
		verifyClearButtonIsDisplayed();
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		buttonClear.click();
	}

	public void verifyClearAllButtonFunctionalityWithAllFields(String firstName, String lastName, String email,
			String username, String mobileNumber) throws AWTException, InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);

		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(lastName);
		log("Entered text in last name field is " + lastName);

		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);

		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(username);
		log("Entered text in username field is " + username);

		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(mobileNumber);
		log("Entered text in email field is " + mobileNumber);
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		clickEnrollmentStatusCheckboxes();
		clickOnSearchButton();
	}

	public void verifyClearAllButtonFunctionalityWithAllFields(String firstName, String lastName, String clientName,
			String email, String username, String mobileNumber) throws AWTException, InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);

		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(lastName);
		log("Entered text in last name field is " + lastName);

		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);

		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(username);
		log("Entered text in username field is " + username);

		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(mobileNumber);
		log("Entered text in email field is " + mobileNumber);
	}

	public void clickOnSearchButton() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		javascriptClick(buttonSearch);
	}

	public void clickOnClearAllSearchData() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(butonClearAll, driver);
		scrollAndWait(0, 500, 200);
		butonClearAll.click();
	}

	public void verifySearchButtonIsDisplayed() {
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		log("Verifying search button is displayed on search options popup");
		if (buttonSearch.isDisplayed() == true) {
			log("Verifying search button is enabled");
			CCUtils.assertTrue(buttonSearch.isEnabled(), "search button is disabled");
			log("Verifying search button text");
			CCUtils.assertEquals(buttonSearch.getText().trim(), "SEARCH");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public void verifyEnrollmentStatusLabelTextAndCheckboxes() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelEnrollmentStatus, driver);
		if (labelEnrollmentStatus.isDisplayed()) {
			log("Verifying status label text");
			CCUtils.assertEquals(labelEnrollmentStatus.getText().trim(), "Enrollment Email Status");
			log("Enrollment Status label text is " + labelEnrollmentStatus.getText().trim());
			log("Verifying Enrollment status label are displayed on search  user page");
			verifyEnrollmentStatusValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserStatusCheckboxesAreNotByDefaultSelected();
		} else {
			log("Enrollment status label is not displayed on search options popup");
		}
	}

	public void getEnrollmentStatusValues() throws InterruptedException {
		for (int i = 0; i < labelEnrollmentStatusList.size(); i++) {
			actualEnrollmentStatusValues.add(labelEnrollmentStatusList.get(i).getText());
		}
		log("Enrollment status available for internal user are :" + actualEnrollmentStatusValues);
	}

	public void clickEnrollmentStatusCheckboxes() throws InterruptedException {
		for (int i = 0; i < checkboxEnrollmentAndUserStatusList.size(); i++) {
			CCUtils.assertTrue(!checkboxEnrollmentAndUserStatusList.get(i).isSelected(),
					"User role checkboxes are already selected");
			checkboxEnrollmentAndUserStatusList.get(i).click();
		}
	}

	public void verifyEnrollmentStatusCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxEnrollmentStatus.size(); i++) {
			CCUtils.assertTrue(!checkboxEnrollmentStatus.get(i).isSelected(),
					"User role checkboxes are already selected");
		}
	}

	public void clickToSearchButton() {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		buttonSearch.click();
		log("search button clicked");

	}

	public void verifyErrorMessageOnSearchOptionsPage() throws InterruptedException, AWTException {
		verifySearchButtonIsDisplayed();
		clickToSearchButton();
		CCUtils.assertEquals(errorSearchOptionsPage.getText().trim(), "Please enter or select to search");
		log("Error message displayed for empty search field " + errorSearchOptionsPage.getText().trim());

	}

	public void verifyMaximumCharactersErrorMessage(String firstName, String lastName, String username, String email) {
		log("Enter more than 100 characters in first name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterFirstName, driver);
		CCUtils.assertEquals(errorMaximumCharacterFirstName.getText().trim(), "Maximum characters reached");
		log("Error message displayed for first name field is " + errorMaximumCharacterFirstName.getText().trim());

		log("Enter more than 100 characters in last name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(lastName);
		log("Entered text in last name field is " + lastName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterLastName, driver);
		CCUtils.assertEquals(errorMaximumCharacterLastName.getText().trim(), "Maximum characters reached");
		log("Error message displayed for last name field is " + errorMaximumCharacterLastName.getText().trim());

		log("Enter more than 100 characters in username textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(username);
		log("Entered text in username field is " + username);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterUsername, driver);
		CCUtils.assertEquals(errorMaximumCharacterUsername.getText().trim(), "Maximum characters reached");
		log("Error message displayed for username field is " + errorMaximumCharacterUsername.getText().trim());

		log("Enter more than 100 characters in email textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterEmail, driver);
		CCUtils.assertEquals(errorMaximumCharacterEmail.getText().trim(), "Maximum characters reached");
		log("Error message displayed for email field is " + errorMaximumCharacterEmail.getText().trim());
	}

	public void verifyWebElement(String webElement) {
		log("Enter more than 100 characters in first name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(webElement);
		log("Entered text in first name field is " + webElement);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterFirstName, driver);
		CCUtils.assertEquals(errorMaximumCharacterFirstName.getText().trim(), "Maximum characters reached");
		log("Error message displayed for first name field is " + errorMaximumCharacterFirstName.getText().trim());
	}

	public void verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(String firstName, String lastName,
			String email, String username, String mobileNumber) throws AWTException, InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);

		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(lastName);
		log("Entered text in last name field is " + lastName);

		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);

		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(username);
		log("Entered text in username field is " + username);

		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(mobileNumber);
		log("Entered text in email field is " + mobileNumber);
	}

	public void verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(String firstName, String lastName,
			String clientName, String email, String username, String mobileNumber)
			throws AWTException, InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);

		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(lastName);
		log("Entered text in last name field is " + lastName);

		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);

		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(username);
		log("Entered text in username field is " + username);

		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(mobileNumber);
		log("Entered text in email field is " + mobileNumber);
	}

	public void verifyFieldErrorMessageWithInvalidData(String invalidFirstName, String invalidLastName,
			String invalidEmail, String invalidUsername, String invalidMobileNumber) {
		log("Enter invalid first name in first name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(invalidFirstName);
		log("Entered text in first name field is " + invalidFirstName);

		log("Enter invalid last name in first name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(invalidLastName);
		log("Entered text in last name field is " + invalidLastName);

		log("Enter invalid email in email textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(invalidEmail);
		log("Entered text in email field is " + invalidEmail);

		log("Enter invalid username in username textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(invalidUsername);
		log("Entered text in username field is " + invalidUsername);

		log("Enter invalid mobile in mobile number textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(invalidMobileNumber);
		log("Entered text in mobile number field is " + invalidMobileNumber);

	}

	public void selectSingleCheckboxOfUserStatus() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkboxUserStatusSingle, driver);
		scrollAndWait(0, 500, 8000);
		log("User Status & Enrollment status checkbox is " + checkboxUserStatusSingle.getText().trim());
		checkboxUserStatusSingle.click();
	}

	public void selectSingleCheckboxOfEnrollmentStatus() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkboxEnrollmentStatusSingle, driver);
		scrollAndWait(0, 500, 8000);
		log("User Status & Enrollment status checkbox is " + checkboxEnrollmentStatusSingle.getText().trim());
		checkboxEnrollmentStatusSingle.click();
	}

	public void verifyTextboxIsEmptyWhenClickOnClearButton() {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		String firstName = textboxFirstName.getText().trim();
		String lastName = textboxLastName.getText().trim();
		String userName = textboxUsername.getText().trim();
		String emailName = textboxEmail.getText().trim();
		String mobileNumber = textboxMobileNumber.getText().trim();
		log("Verifying text is not present in firstname field");
		CCUtils.assertTrue(firstName.length() == 0, "Text is present in first field");
		log("Verifying text is not present in lastname field");
		CCUtils.assertTrue(lastName.length() == 0, "Text is present in last field");
		log("Verifying text is not present in username field");
		CCUtils.assertTrue(userName.length() == 0, "Text is present in username field");
		log("Verifying text is not present in email field");
		CCUtils.assertTrue(emailName.length() == 0, "Text is present in email field");
		log("Verifying text is not present in mobilenumber field");
		CCUtils.assertTrue(mobileNumber.length() == 0, "Text is present in mobilenumber field");
	}

	public void verifyEnrollmentSearchOptionsPopup() throws InterruptedException {
		log("Verifying search internal user pop-up");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		verifyEnrollmentStatusLabelTextAndCheckboxesAtEnrollmentSearchOptions();
		verifyClearButtonIsDisplayed();
		verifySearchButtonIsDisplayed();
	}

	public void verifyEnrollmentStatusLabelTextAndCheckboxesAtEnrollmentSearchOptions() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(labelEnrollmentStatus, driver);
		if (labelEnrollmentStatus.isDisplayed()) {
			log("Verifying enrollment status label text");
			CCUtils.assertEquals(labelEnrollmentStatus.getText().trim(), "Enrollment Status");
			log("User enrollment status label text is " + labelEnrollmentStatus.getText().trim());
			log("Verifying User enrollment status labels are displayed on search options page");
			verifyEnrollmentStatusValuesAtEnrollmentSearchOptions();
			log("Verifying enrollment status checkboxes are not selected");
			verifyStatusCheckboxesAreNotByDefaultSelectedOnEnrollmentSearchOptionsPage();
		} else {
			log("Enrollment status label is not displayed on search options popup");
		}
	}

	public void getLabelEnrollmentStatusValues() throws InterruptedException {
		for (int i = 0; i < listEnrollmentStatusLabel.size(); i++) {
			actualListOfEnrollmentStatusValues.add(listEnrollmentStatusLabel.get(i).getText());
		}
		log("Enrollment status available for internal user are :" + actualEnrollmentStatusValues);
	}

	public void verifyEnrollmentStatusValuesAtEnrollmentSearchOptions() throws InterruptedException {
		getLabelEnrollmentStatusValues();
		CCUtils.assertEquals(actualListOfEnrollmentStatusValues, expectedListOfEnrollmentStatusValues);
	}

	public void verifyStatusCheckboxesAreNotByDefaultSelectedOnEnrollmentSearchOptionsPage()
			throws InterruptedException {
		log("Verifying enrollment status checkboxes are not selected");
		for (int i = 0; i < enrollmentStatusCheckboxList.size(); i++) {
			CCUtils.assertTrue(!enrollmentStatusCheckboxList.get(i).isSelected(),
					"Enrollment checkboxes are already selected");
		}
	}

	public void selectEnrollmentStatusOnTheEnrollmentSearchPage(List<String> enrollmentStatusList) {
		for (int i = 0; i < enrollmentStatusList.size(); i++) {
			if (enrollmentStatusList.get(i).equals("Delivered")) {
				javascriptClick(enrollmentStatusCheckboxList.get(0));
			} else if (enrollmentStatusList.get(i).equals("Pending")) {
				javascriptClick(enrollmentStatusCheckboxList.get(1));
			} else if (enrollmentStatusList.get(i).equals("Email Issue")) {
				javascriptClick(enrollmentStatusCheckboxList.get(2));
			} else if (enrollmentStatusList.get(i).equals("Expired")) {
				javascriptClick(enrollmentStatusCheckboxList.get(3));
			} else if (enrollmentStatusList.get(i).equals("Not Sent")) {
				javascriptClick(enrollmentStatusCheckboxList.get(4));
			}
		}
	}

	public void enterSearchTextInSearchFields(String firstName, String lastName, String email, String username,
			String mobileNumber) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);

		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(lastName);
		log("Entered text in last name field is " + lastName);

		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);

		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(username);
		log("Entered text in username field is " + username);

		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(mobileNumber);
		log("Entered text in mobile number field is " + mobileNumber);
	}

	public void selectUserStatus(List<String> userStatusList) {
		for (int i = 0; i < userStatusList.size(); i++) {
			if (userStatusList.get(i).equals("Active")) {
				javascriptClick(checkboxUserStatus.get(0));
			} else if (userStatusList.get(i).equals("Inactive")) {
				javascriptClick(checkboxUserStatus.get(1));
			} else if (userStatusList.get(i).equals("Locked out")) {
				javascriptClick(checkboxUserStatus.get(2));
			} else if (userStatusList.get(i).equals("No roles assigned")) {
				javascriptClick(checkboxUserStatus.get(3));
			}
		}
	}

	public void selectEnrollmentStatusValues(List<String> enrollmentStatusList) {
		for (int i = 0; i < enrollmentStatusList.size(); i++) {
			if (enrollmentStatusList.get(i).equals("Confirmed")) {
				javascriptClick(checkboxEnrollmentStatus.get(0));
			} else if (enrollmentStatusList.get(i).equals("Delivered")) {
				javascriptClick(checkboxEnrollmentStatus.get(1));
			} else if (enrollmentStatusList.get(i).equals("Pending")) {
				javascriptClick(checkboxEnrollmentStatus.get(2));
			} else if (enrollmentStatusList.get(i).equals("Email Issue")) {
				javascriptClick(checkboxEnrollmentStatus.get(3));
			} else if (enrollmentStatusList.get(i).equals("Expired")) {
				javascriptClick(checkboxEnrollmentStatus.get(4));
			} else if (enrollmentStatusList.get(i).equals("Not Sent")) {
				javascriptClick(checkboxEnrollmentStatus.get(5));
			}
		}
	}

	public void searchUser(String firstName, String lastName, String email, String username, String mobileNumber,
			List<String> enrollmentStatusList) {
		enterSearchTextInSearchFields(firstName, lastName, email, username, mobileNumber);
		selectEnrollmentStatusOnTheEnrollmentSearchPage(enrollmentStatusList);
	}

	public void clearSearchTextInClientSearchBox() {
		CCUtils.verifyAndWaitForElement(clientName, driver);
		clientName.clear();
		log("text in client search box is cleared");
	}

	public void clickFirstInAutoSuggestion() {
		CCUtils.verifyAndWaitForElement(selectFirstInAutoSuggestion, driver);
		if (selectFirstInAutoSuggestion.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstInAutoSuggestion);

		} else {
			log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present in client");
		}
	}

	public void verifyVarResellerLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelVarReseller, driver);
		CCUtils.assertTrue(labelVarReseller.isDisplayed(),
				"VAR/Reseller label is not displayed on search options popup");
		log("Verifying var/reseller label text");
		CCUtils.assertEquals(labelVarReseller.getText().trim(), "VAR/Reseller");
		log("VAR/Reseller label text is " + labelVarReseller.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownSearchVarReseller, driver);
		CCUtils.assertTrue(dropdownSearchVarReseller.isDisplayed(),
				"VAR/Reseller search textbox is not displayed on search options popup");
		log("Verifying input text placeholder is displayed for var/reseller textbox field");
		CCUtils.assertEquals(dropdownSearchVarReseller.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for var/reseller textbox field is "
				+ dropdownSearchVarReseller.getAttribute("placeholder").trim());
	}

	public void verifyEnrollmentSearchOptionsPopupForVarResellerUser() throws InterruptedException {
		log("Verifying search VAR/Reseller user pop-up");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyVarResellerLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		verifyEnrollmentStatusLabelTextAndCheckboxesAtEnrollmentSearchOptions();
		verifyClearButtonIsDisplayed();
		verifySearchButtonIsDisplayed();
	}

	public void enterSeachTextInVarResellerField(String varReseller) {
		CCUtils.verifyAndWaitForElement(dropdownSearchVarReseller, driver);
		dropdownSearchVarReseller.sendKeys(varReseller);
		log("Entered text in var/reseller field is " + varReseller);
	}

	public void searchVarResellerUser(String firstName, String lastName, String varReseller, String email,
			String username, String mobileNumber, List<String> enrollmentStatusList) {
		enterSearchTextInSearchFields(firstName, lastName, email, username, mobileNumber);
		enterSeachTextInVarResellerField(varReseller);
		selectEnrollmentStatusOnTheEnrollmentSearchPage(enrollmentStatusList);
	}

	public void verifyVarResellerTextboxIsEmptyWhenClickOnClearButton() {
		CCUtils.verifyAndWaitForElement(dropdownSearchVarReseller, driver);
		log("Verifying text is not present in var/reseller field");
		CCUtils.assertTrue(dropdownSearchVarReseller.getText().trim().length() == 0,
				"Text is present in var/reseller textbox field");
	}

	public void enterSeachTextInClientNameField(String ClientName) {
		CCUtils.verifyAndWaitForElement(clientName, driver);
		clientName.sendKeys(ClientName);
		log("Entered text in clientName field is " + ClientName);
	}

	public void clickOnNoRolesAssignedCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		CCUtils.verifyAndWaitForElement(noRolesAssignedCheckbox, driver);
		noRolesAssignedCheckbox.click();
		log("Clicked on no roles assigned User Status checkbox on Search Options popup");
	}

	List<String> actualUserStatusValues = new ArrayList<String>();
	List<String> actualListOfEnrollmentStatusValues = new ArrayList<String>();

	public final List<String> expectedUserStatusValues = Arrays.asList("Active", "Inactive", "Locked out",
			"No roles assigned");

	List<String> actualEnrollmentStatusValues = new ArrayList<String>();

	public final List<String> expectedEnrollmentStatusValues = Arrays.asList("Confirmed", "Pending", "Expired",
			"Not Sent");
	public final List<String> expectedListEnrollmentStatusValues = Arrays.asList("Confirmed", "Pending", "Expired",
			"Not Sent");

	public void selectEnrollmentStatus(String status) {
		if (status.equalsIgnoreCase("Confirmed"))
			confirmedStatusCheckBox.click();
		else if (status.equalsIgnoreCase("Pending"))
			pendingStatusCheckBox.click();
		else if (status.equalsIgnoreCase("Expired"))
			expiredStatusCheckBox.click();
		else if (status.equalsIgnoreCase("Not Sent"))
			notSentStatusCheckBox.click();
	}

	public void selectEnrollment(List<String> enrollmentStatus) {
		for (String each : enrollmentStatus) {
			if (each.equalsIgnoreCase("Confirmed"))
				confirmedStatusCheckBox.click();
			else if (each.equalsIgnoreCase("Pending"))
				pendingStatusCheckBox.click();
			else if (each.equalsIgnoreCase("Expired"))
				expiredStatusCheckBox.click();
			else if (each.equalsIgnoreCase("Not Sent"))
				notSentStatusCheckBox.click();
		}
	}

	public void verifyEnrollmentStatusValues() throws InterruptedException {
		log("Verifying enrollmentStatus Confirmed");
		CCUtils.verifyAndWaitForElement(enrollmentStatusConfirmed, driver);
		CCUtils.assertEquals(enrollmentStatusConfirmed.getText().trim(), "Confirmed");
		log("Confirmed  enrollment status label text is " + enrollmentStatusConfirmed.getText().trim());

		log("Verifying enrollmentStatus Pending");
		CCUtils.verifyAndWaitForElement(enrollmentStatusPending, driver);
		CCUtils.assertEquals(enrollmentStatusPending.getText().trim(), "Pending");
		log("Pending  enrollment status label text is " + enrollmentStatusPending.getText().trim());

		log("Verifying enrollmentStatus Expired");
		CCUtils.verifyAndWaitForElement(enrollmentStatusExpired, driver);
		CCUtils.assertEquals(enrollmentStatusExpired.getText().trim(), "Expired");
		log("Expired enrollment status label text is " + enrollmentStatusExpired.getText().trim());

		log("Verifying enrollmentStatus Not Sent");
		CCUtils.verifyAndWaitForElement(enrollmentStatusNotSent, driver);
		CCUtils.assertEquals(enrollmentStatusNotSent.getText().trim(), "Not Sent");
		log("Not Sent  enrollment status label text is " + enrollmentStatusNotSent.getText().trim());

	}

}
