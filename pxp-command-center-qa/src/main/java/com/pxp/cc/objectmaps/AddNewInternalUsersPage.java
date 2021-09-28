// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class AddNewInternalUsersPage extends CCMainPage {

	@FindBy(id = "addNewTitle")
	private WebElement labelTitleAddNewInternalUsersPage;

	@FindBy(xpath = "//span[text()='First Name *']")
	private WebElement labelFirstName;

	@FindBy(xpath = "//span[text()='Last Name *']")
	private WebElement labelLastName;

	@FindBy(xpath = "//span[text()='Username *']")
	private WebElement labelUsername;

	@FindBy(xpath = "//span[text()='Email *']")
	private WebElement labelEmail;

	@FindBy(xpath = "//span[text()='Mobile Number']")
	private WebElement labelMobileNumber;

	@FindBy(xpath = "//span[text()='Status *']")
	private WebElement labelStatus;

	@FindBy(xpath = "//label[text()='User Roles']")
	private WebElement labelUserRoles;

	@FindBy(id = "addFirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "addLastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "addUserNameInput")
	private WebElement textboxUsername;

	@FindBy(id = "addEmailInput")
	private WebElement textboxEmail;

	@FindBy(id = "addMobileInput")
	private WebElement textboxMobileNumber;

	@FindBy(id = "addNewCancel")
	private WebElement buttonCancel;

	@FindBy(id = "addNewInternalSave")
	private WebElement buttonSaveAndAdd;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(xpath = "//input[@id='createUserActiveRadio-input']")
	private WebElement radioButtonActive;

	@FindBy(xpath = "//input[@id='createUserInactiveRadio-input']")
	private WebElement radioButtonInactive;

	@FindBy(xpath = "//label[text()='Active']")
	private WebElement labelActive;

	@FindBy(xpath = "//label[text()='Inactive']")
	private WebElement labelInactive;

	@FindBy(xpath = "//div[text()='First name is required']")
	private WebElement errorFirstNameRequired;

	@FindBy(xpath = "//div[text()='Last name is required']")
	private WebElement errorLastNameRequired;

	@FindBy(xpath = "//div[text()='Username is required']")
	private WebElement errorUsernameRequired;

	@FindBy(xpath = "//div[text()='Email is required']")
	private WebElement errorEmailRequired;

	@FindBy(xpath = "//div[text()='Please enter valid first name']")
	private WebElement errorValidFirstName;

	@FindBy(xpath = "//div[text()='Please enter valid last name']")
	private WebElement errorValidLastName;

	@FindBy(xpath = "//div[text()='Please enter valid username']")
	private WebElement errorValidUsername;

	@FindBy(xpath = "//input[@id='addFirstNameInput']//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterFirstName;

	@FindBy(xpath = "//input[@id='addLastNameInput']//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterLastName;

	@FindBy(xpath = "//input[@id='addUserNameInput']//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterUsername;

	@FindBy(xpath = "//input[@id='addEmailInput']//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterEmail;

	@FindBy(xpath = "//div[text()=' Please enter valid mobile number ']")
	private WebElement errorValidMobileNumber;

	@FindBy(xpath = "//div[text()='Please enter valid email address']")
	private WebElement errorValidEmail;

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(xpath = "//div[text()='Username already in use']")
	private WebElement errorUniqueUsername;

	@FindBy(xpath = "//div[text()='Email already in use']")
	private WebElement errorUniqueEmail;

	@FindBy(xpath = "//div[text()='Mobile number already in use']")
	private WebElement errorUniqueMobileNumber;

	@FindBy(xpath = "//label[@id='rolesLabel']/span")
	private List<WebElement> labelUserRolesList;

	@FindBy(xpath = "//label[@id='rolesLabel']/mat-checkbox/label/div/input")
	private List<WebElement> checkboxUserRoles;

	List<String> actualUserRoleValues = new ArrayList<String>();

	public final List<String> expectedUserRoleValues = Arrays.asList("Super Admin", "Support T2", "Implementation",
			"Support T3", "Demo/Sales", "Support T1");

	public AddNewInternalUsersPage(WebDriver driver) {
		super(driver);
	}

	public void verifyAddNewInternalUserPopup() throws InterruptedException {
		log("Verifying add new internal user pop-up");
		verifyAddNewInternalUserPageTitle();
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		verifyStatusLabelTextAndRadioButtons();
		verifyActiveRadioButtonByDefaultSelected();
		verifyUserRoles();
		verifyCrossIconIsDisplayed();
		verifyCancelButtonIsDisplayed();
		verifySaveAndAddButtonIsDisplayed();
	}

	public void verifyAddNewInternalUserPageTitle() {
		CCUtils.verifyAndWaitForElement(labelTitleAddNewInternalUsersPage, driver);
		if (labelTitleAddNewInternalUsersPage.isDisplayed()) {
			CCUtils.assertEquals(labelTitleAddNewInternalUsersPage.getText().trim(), "Add New Internal User");
			log("Add new internal user page title is " + labelTitleAddNewInternalUsersPage.getText().trim());
		} else {
			log("Add new internal user page title is not displayed");
		}
	}

	public void verifyFirstNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelFirstName, driver);
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		if (labelFirstName.isDisplayed() && textboxFirstName.isDisplayed()) {
			log("Verifying first name label text");
			CCUtils.assertEquals(labelFirstName.getText().trim(), "First Name *");
			log("First name label text is " + labelFirstName.getText().trim());
			log("Verifying input text placeholder is displayed for first name textbox field");
			CCUtils.assertEquals(textboxFirstName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for first name textbox field is " + textboxFirstName.getAttribute("placeholder").trim());
		} else {
			log("First name label and input textbox are not displayed on add new internal user page");
		}
	}

	public void verifyLastNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLastName, driver);
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		if (labelLastName.isDisplayed() && textboxLastName.isDisplayed()) {
			log("Verifying last name label text");
			CCUtils.assertEquals(labelLastName.getText().trim(), "Last Name *");
			log("Last name label text is " + labelLastName.getText().trim());
			log("Verifying input text placeholder is displayed for last name textbox field");
			CCUtils.assertEquals(textboxLastName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for last name textbox field is " + textboxLastName.getAttribute("placeholder").trim());
		} else {
			log("Last name label and input textbox are not displayed on add new internal user page");
		}
	}

	public void verifyUsernameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUsername, driver);
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		if (labelUsername.isDisplayed() && textboxUsername.isDisplayed()) {
			log("Verifying username label text");
			CCUtils.assertEquals(labelUsername.getText().trim(), "Username *");
			log("Username label text is " + labelUsername.getText().trim());
			log("Verifying input text placeholder is displayed for username textbox field ");
			CCUtils.assertEquals(textboxUsername.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for username textbox field is " + textboxUsername.getAttribute("placeholder").trim());
		} else {
			log("Username label and input textbox are not displayed on add new internal user page");
		}
	}

	public void verifyEmailLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		if (labelEmail.isDisplayed() && textboxEmail.isDisplayed()) {
			log("Verifying email label text");
			CCUtils.assertEquals(labelEmail.getText().trim(), "Email *");
			log("Email label text is " + labelEmail.getText().trim());
			log("Verifying input text placeholder is displayed for email textbox field ");
			CCUtils.assertEquals(textboxEmail.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for email textbox field is " + textboxEmail.getAttribute("placeholder").trim());
		} else {
			log("Email label and input textbox are not displayed on add new internal user page");
		}
	}

	public void verifyMobileNumberLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelMobileNumber, driver);
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		if (labelMobileNumber.isDisplayed() && textboxMobileNumber.isDisplayed()) {
			log("Verifying mobile number label text");
			CCUtils.assertEquals(labelMobileNumber.getText().trim(), "Mobile Number");
			log("Mobile number label text is " + labelMobileNumber.getText().trim());
			log("Verifying input text placeholder is displayed for mobile number textbox field ");
			CCUtils.assertEquals(textboxMobileNumber.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for mobile number textbox field is "
					+ textboxMobileNumber.getAttribute("placeholder").trim());
		} else {
			log("Mobile number label and input textbox are not displayed on add new internal user page");
		}
	}

	public void verifyStatusLabelTextAndRadioButtons() {
		CCUtils.verifyAndWaitForElement(labelStatus, driver);
		if (labelStatus.isDisplayed()) {
			log("Verifying status label text");
			CCUtils.assertEquals(labelStatus.getText().trim(), "Status *");
			log("Status label text is " + labelStatus.getText().trim());
			log("Verifying active label and radio button are displayed on add new internal user page");
			CCUtils.assertEquals(labelActive.getText().trim(), "Active");
			CCUtils.assertTrue(radioButtonActive.isDisplayed(),
					"Active radiobutton is not present on add new internal user page");
			log("Verifying inactive label and radio button are displayed on add new internal user page");
			CCUtils.assertEquals(labelInactive.getText().trim(), "Inactive");
			CCUtils.assertTrue(radioButtonInactive.isDisplayed(),
					"Inactive radiobutton is not present on add new internal user page");
		} else {
			log("Status label is not displayed on add new internal user page");
		}
	}

	public void verifyActiveRadioButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		log("Verifying active button is by default selected");
		if (radioButtonActive.isDisplayed() == true && radioButtonActive.isSelected() == true) {
			log("Active button is displayed on add new internal user page and selected");
		} else {
			log("Active button is not displayed on add new internal user page and selected");
		}
	}

	public void verifyUserRoles() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(labelUserRoles, driver);
		if (labelUserRoles.isDisplayed()) {
			log("Verifying user roles label text");
			CCUtils.assertEquals(labelUserRoles.getText().trim(), "User Roles");
			log("User roles label text is " + labelUserRoles.getText().trim());
			log("Verifying user roles available for internal user");
			verifyUserRoleValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserRoleCheckboxesAreNotByDefaultSelected();
		} else {
			log("User roles label is not displayed on add new internal user page");
		}
	}

	public void getUserRoleValues() throws InterruptedException {
		for (int i = 0; i < labelUserRolesList.size(); i++) {
			actualUserRoleValues.add(labelUserRolesList.get(i).getText().trim());
		}
		log("User roles available for internal user are :" + actualUserRoleValues);
	}

	public void verifyUserRoleValues() throws InterruptedException {
		getUserRoleValues();
		CCUtils.assertEquals(actualUserRoleValues, expectedUserRoleValues);
	}

	public void verifyUserRoleCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxUserRoles.size(); i++) {
			CCUtils.assertTrue(!checkboxUserRoles.get(i).isSelected(), "User role checkboxes are already selected");
		}
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		log("Verifying cross icon is displayed on add new internal user page");
		if (crossIcon.isDisplayed() == true) {
			log("Verifying cross icon is enabled");
			CCUtils.assertTrue(crossIcon.isEnabled(), "Cross icon is disabled");
		} else {
			log("cross icon is not displayed on add new internal user page");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on add new internal user page");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button is enabled");
			CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on add new internal user page");
		}
	}

	public void verifySaveAndAddButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		log("Verifying save & add button is displayed on add new internal user page");
		if (buttonSaveAndAdd.isDisplayed() == true) {
			log("Verifying save & add button is enabled");
			CCUtils.assertTrue(buttonSaveAndAdd.isEnabled(), "Save & Add button is disabled");
			log("Verifying save & add button text");
			CCUtils.assertEquals(buttonSaveAndAdd.getText().trim(), "Save & Add");
		} else {
			log("Save & Add button is not displayed on add new internal user page");
		}
	}

	public InternalUsersPage clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		buttonSaveAndAdd.click();
		log("Clicked on Save&Add button");
		return PageFactory.initElements(driver, InternalUsersPage.class);
	}

	public InternalUsersPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel button");
		return PageFactory.initElements(driver, InternalUsersPage.class);
	}

	public InternalUsersPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on X icon");
		return PageFactory.initElements(driver, InternalUsersPage.class);
	}

	public void verifyRequiredErrorMessageForMandatoryFields() {
		log("Verifying first name required error message is displayed on add new internal user page");
		CCUtils.verifyAndWaitForElement(errorFirstNameRequired, driver);
		CCUtils.assertEquals(errorFirstNameRequired.getText().trim(), "First name is required");
		log("Error message displayed for first name field is " + errorFirstNameRequired.getText().trim());
		log("Verifying last name required error message is displayed on add new internal user page");
		CCUtils.verifyAndWaitForElement(errorLastNameRequired, driver);
		CCUtils.assertEquals(errorLastNameRequired.getText().trim(), "Last name is required");
		log("Error message displayed for last name field is " + errorLastNameRequired.getText().trim());
		log("Verifying username required error message is displayed on add new internal user page");
		CCUtils.verifyAndWaitForElement(errorUsernameRequired, driver);
		CCUtils.assertEquals(errorUsernameRequired.getText().trim(), "Username is required");
		log("Error message displayed for username field is " + errorUsernameRequired.getText().trim());
		log("Verifying email required error message is displayed on add new internal user page");
		CCUtils.verifyAndWaitForElement(errorEmailRequired, driver);
		CCUtils.assertEquals(errorEmailRequired.getText().trim(), "Email is required");
		log("Error message displayed for email field is " + errorEmailRequired.getText().trim());

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

	public void verifyUniqueMessageForUsernameEmailAndMobileNumberField(String existingUsername, String existingEmail,
			String existigMobileNumber) {
		log("Enter already existing username in username textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(existingUsername);
		log("Entered text in username field is " + existingUsername);
		CCUtils.verifyAndWaitForElement(errorUniqueUsername, driver);
		CCUtils.assertEquals(errorUniqueUsername.getText().trim(), "Username already in use");
		log("Error message displayed for username field is " + errorUniqueUsername.getText().trim());

		log("Enter already existing email in email textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(existingEmail);
		log("Entered text in email field is " + existingEmail);
		CCUtils.verifyAndWaitForElement(errorUniqueEmail, driver);
		CCUtils.assertEquals(errorUniqueEmail.getText().trim(), "Email already in use");
		log("Error message displayed for email field is " + errorUniqueEmail.getText().trim());

		log("Enter already existing mobile in mobile number textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxEmail.sendKeys(existigMobileNumber);
		log("Entered text in mobile number field is " + existigMobileNumber);
		CCUtils.verifyAndWaitForElement(errorUniqueMobileNumber, driver);
		CCUtils.assertEquals(errorUniqueMobileNumber.getText().trim(), "Mobile number already in use");
		log("Error message displayed for mobile number field is " + errorUniqueMobileNumber.getText().trim());
	}

	public void verifyValidErrorMessage(String invalidFirstName, String invalidLastName, String invalidEmail,
			String invalidUsername, String invalidMobileNumber) {
		log("Enter invalid first name in first name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(invalidFirstName);
		log("Entered text in first name field is " + invalidFirstName);
		CCUtils.verifyAndWaitForElement(errorValidFirstName, driver);
		CCUtils.assertEquals(errorValidFirstName.getText().trim(), "Please enter valid first name");
		log("Error message displayed for first name field is " + errorValidFirstName.getText().trim());

		log("Enter invalid last name in first name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(invalidLastName);
		log("Entered text in last name field is " + invalidLastName);
		CCUtils.verifyAndWaitForElement(errorValidLastName, driver);
		CCUtils.assertEquals(errorValidLastName.getText().trim(), "Please enter valid last name");
		log("Error message displayed for last name field is " + errorValidLastName.getText().trim());

		log("Enter invalid email in email textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(invalidEmail);
		log("Entered text in email field is " + invalidEmail);
		textboxUsername.click();
		CCUtils.verifyAndWaitForElement(errorValidEmail, driver);
		CCUtils.assertEquals(errorValidEmail.getText().trim(), "Please enter valid email address");
		log("Error message displayed for email field is " + errorValidEmail.getText().trim());

		log("Enter invalid username in username textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(invalidUsername);
		log("Entered text in username field is " + invalidUsername);
		CCUtils.verifyAndWaitForElement(errorValidUsername, driver);
		CCUtils.assertEquals(errorValidUsername.getText().trim(), "Please enter valid username");
		log("Error message displayed for username field is " + errorValidUsername.getText().trim());

		log("Enter invalid mobile in mobile number textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(invalidMobileNumber);
		log("Entered text in mobile number field is " + invalidMobileNumber);
		CCUtils.verifyAndWaitForElement(errorValidMobileNumber, driver);
		CCUtils.assertEquals(errorValidMobileNumber.getText().trim(), "Please enter valid mobile number");
		log("Error message displayed for mobile number field is " + errorValidMobileNumber.getText().trim());
	}

	public void verifyUsernameGeneratedUsingBeginningOfEmail(String email) {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.sendKeys(email);
		log("Entered text in email field is " + email);
		String[] strList = email.split("@");
		String username = strList[0];
		log("Verifying username is generated using beginning of email address");
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.click();
		CCUtils.assertEquals(textboxUsername.getAttribute("value").trim(), username);
		log("Usename text is " + textboxUsername.getAttribute("value").trim());
	}

	public void createInternalUser(String firstName, String lastName, String userName, String email,
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
		textboxUsername.click();
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		textboxUsername.clear();
		textboxUsername.sendKeys(userName);
		log("Entered text in username field field is " + userName);
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(mobileNumber);
		log("Entered mobile number is " + mobileNumber);
	}

	public void selectInactiveRadiobutton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(radioButtonInactive, driver);
		javascriptClick(radioButtonInactive);
		log("Clicked on Inactive radiobutton in popup");
	}

	public void addUserRoles() throws InterruptedException {
		log("Select all user role");
		for (int i = 0; i < checkboxUserRoles.size(); i++) {
			javascriptClick(checkboxUserRoles.get(i));
		}
	}

}
