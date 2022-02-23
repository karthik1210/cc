// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.objectmaps.CCMainPage;

public class CommonMethods extends CCMainPage {

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(id = "loginUsernameInput")
	private WebElement loginUserName;

	@FindBy(id = "snackCloseBtn")
	private WebElement buttonCloseToaster;

	@FindBy(xpath = "//div[@class='flex']/span//following::a")
	private WebElement linkClientName;

	@FindBy(id = "addFirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "addLastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "addEmailInput")
	private WebElement textboxEmail;

	@FindBy(id = "addUserNameInput")
	private WebElement textboxUserName;

	@FindBy(xpath = "//input[@id='addMobileInput'] | //input[@id='mobileInput']")
	private WebElement textboxMobileNumber;

	@FindBy(id = "maxFirstNameError")
	private WebElement errorMaximumCharacterFirstName;

	@FindBy(id = "maxLastNameError")
	private WebElement errorMaximumCharacterLastName;

	@FindBy(id = "maxUsernameError")
	private WebElement errorMaximumCharacterUsername;

	@FindBy(id = "maxEmailError")
	private WebElement errorMaximumCharacterEmail;

	@FindBy(id = "firstNameError")
	private WebElement errorFirstNameRequired;

	@FindBy(id = "lastNameError")
	private WebElement errorLastNameRequired;

	@FindBy(id = "usernameError")
	private WebElement errorUsernameRequired;

	@FindBy(id = "emailError")
	private WebElement errorEmailRequired;

	@FindBy(xpath = "//div[normalize-space()='Username already in use']")
	private WebElement errorUniqueUsername;

	@FindBy(xpath = "//div[normalize-space()='Email already in use']")
	private WebElement errorUniqueEmail;

	@FindBy(xpath = "//div[normalize-space()='Mobile number already in use']")
	private WebElement errorUniqueMobileNumber;

	@FindBy(id = "validMobileNumberError")
	private WebElement errorValidMobileNumber;

	@FindBy(id = "validEmailError")
	private WebElement errorValidEmail;

	@FindBy(id = "validFirstNameError")
	private WebElement errorValidFirstName;

	@FindBy(id = "validLastNameError")
	private WebElement errorValidLastName;

	@FindBy(id = "validUsernameError")
	private WebElement errorValidUsername;

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

	@FindBy(xpath = "//label[text()='Active']")
	private WebElement labelActive;

	@FindBy(xpath = "//label[text()='Inactive']")
	private WebElement labelInactive;

	@FindBy(xpath = "//input[@id='createUserActiveRadio-input']")
	private WebElement radioButtonActive;

	@FindBy(xpath = "//input[@id='createUserInactiveRadio-input']")
	private WebElement radioButtonInactive;

	@FindBy(id = "FirstNameInput")
	private WebElement labelFirstNameValue;

	@FindBy(id = "LastNameInput")
	private WebElement labelLastNameValue;

	@FindBy(id = "EmailInput")
	private WebElement labelEmailValue;

	@FindBy(id = "MobileNumberInput")
	private WebElement labelMobileNumberValue;

	@FindBy(id = "usernameValidationMsg")
	private WebElement iconUsernameValidationError;

	public CommonMethods(WebDriver driver) {
		super(driver);
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public String getToasterMessage() {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		return toasterMessage.getText().trim();

	}

	public void closeToasterMessage() {
		CCUtils.verifyAndWaitForElement(buttonCloseToaster, driver);
		log("Click on cross icon");
		buttonCloseToaster.click();
	}

	public void clickToasterlink() {
		CCUtils.verifyAndWaitForElement(linkClientName, driver);
		linkClientName.click();
	}

	public void enterFirstName(String input) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.clear();
		textboxFirstName.sendKeys(input);
		log("Entered input for Firstname : " + input);
	}

	public void enterLastName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.clear();
		textboxLastName.sendKeys(input);
		log("Entered input for Last name : " + input);
	}

	public void enterUserName(String input) {
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		textboxUserName.clear();
		textboxUserName.sendKeys(input);
		log("Entered input for username : " + input);
	}

	public void enterEmail(String input) {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.clear();
		textboxEmail.sendKeys(input);
		textboxUserName.click();
		log("Entered input for email : " + input);
	}

	public void enterMobileNumber(String input) {
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.clear();
		textboxMobileNumber.sendKeys(input);
		log("Entered input for mobile number : " + input);
	}

	public String getMaximumCharacterErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterFirstName, driver);
		return errorMaximumCharacterFirstName.getText().trim();
	}

	public String getMaximumCharacterErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterLastName, driver);
		return errorMaximumCharacterLastName.getText().trim();
	}

	public String getMaximumCharacterErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterUsername, driver);
		return errorMaximumCharacterUsername.getText().trim();
	}

	public String getMaximumCharacterErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterEmail, driver);
		return errorMaximumCharacterEmail.getText().trim();
	}

	public String getRequiredErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorFirstNameRequired, driver);
		return errorFirstNameRequired.getText().trim();
	}

	public String getRequiredErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorLastNameRequired, driver);
		return errorLastNameRequired.getText().trim();
	}

	public String getRequiredErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorUsernameRequired, driver);
		return errorUsernameRequired.getText().trim();
	}

	public String getRequiredErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorEmailRequired, driver);
		return errorEmailRequired.getText().trim();
	}

	public String getValidErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorValidFirstName, driver);
		return errorValidFirstName.getText().trim();
	}

	public String getValidErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorValidLastName, driver);
		return errorValidLastName.getText().trim();
	}

	public String getValidErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorValidUsername, driver);
		return errorValidUsername.getText().trim();
	}

	public String getValidErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorValidEmail, driver);
		return errorValidEmail.getText().trim();
	}

	public String getValidErrorForMobileNumberField() {
		CCUtils.verifyAndWaitForElement(errorValidMobileNumber, driver);
		return errorValidMobileNumber.getText().trim();
	}

	public String getDuplicateErrorForUsernameField() {
		CCUtils.verifyAndWaitForElement(errorUniqueUsername, driver);
		return errorUniqueUsername.getText().trim();
	}

	public String getDuplicateErrorForEmailField() {
		CCUtils.verifyAndWaitForElement(errorUniqueEmail, driver);
		return errorUniqueEmail.getText().trim();
	}

	public String getDuplicateErrorForMobileNumberField() {
		CCUtils.verifyAndWaitForElement(errorUniqueMobileNumber, driver);
		return errorUniqueMobileNumber.getText().trim();
	}

	public void clearFirstNameField() {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		CCUtils.clearTextboxField(textboxFirstName);
		log("deleted input in first name field");
	}

	public void clearLastNameField() {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		CCUtils.clearTextboxField(textboxLastName);
		log("deleted input in last name field");
	}

	public void clearUsernameField() {
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		CCUtils.clearTextboxField(textboxUserName);
		log("deleted input in username field");
	}

	public void clearEmailField() {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		CCUtils.clearTextboxField(textboxEmail);
		log("deleted input in email field");
	}

	public void verifyFirstNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelFirstName, driver);
		log("Verifying first name label text");
		CCUtils.assertEquals(labelFirstName.getText().trim(), "First Name *");
		log("First name label text is " + labelFirstName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		log("Verifying input text placeholder is displayed for first name textbox field");
		CCUtils.assertEquals(textboxFirstName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for first name textbox field is " + textboxFirstName.getAttribute("placeholder").trim());
	}

	public void verifyLastNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLastName, driver);
		log("Verifying last name label text");
		CCUtils.assertEquals(labelLastName.getText().trim(), "Last Name *");
		log("Last name label text is " + labelLastName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		log("Verifying input text placeholder is displayed for last name textbox field");
		CCUtils.assertEquals(textboxLastName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for last name textbox field is " + textboxLastName.getAttribute("placeholder").trim());
	}

	public void verifyUsernameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUsername, driver);
		log("Verifying username label text");
		CCUtils.assertEquals(labelUsername.getText().trim(), "Username *");
		log("Username label text is " + labelUsername.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		log("Verifying input text placeholder is displayed for username textbox field ");
		CCUtils.assertEquals(textboxUserName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for username textbox field is " + textboxUserName.getAttribute("placeholder").trim());
	}

	public void verifyEmailLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		log("Verifying email label text");
		CCUtils.assertEquals(labelEmail.getText().trim(), "Email *");
		log("Email label text is " + labelEmail.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		log("Verifying input text placeholder is displayed for email textbox field ");
		CCUtils.assertEquals(textboxEmail.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for email textbox field is " + textboxEmail.getAttribute("placeholder").trim());
	}

	public void verifyMobileNumberLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelMobileNumber, driver);
		log("Verifying mobile number label text");
		CCUtils.assertEquals(labelMobileNumber.getText().trim(), "Mobile Number");
		log("Mobile number label text is " + labelMobileNumber.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		log("Verifying input text placeholder is displayed for mobile number textbox field ");
		CCUtils.assertEquals(textboxMobileNumber.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for mobile number textbox field is " + textboxMobileNumber.getAttribute("placeholder").trim());

	}

	public void verifyStatusLabelTextAndRadioButtons() {
		CCUtils.verifyAndWaitForElement(labelStatus, driver);
		log("Verifying status label text");
		CCUtils.assertEquals(labelStatus.getText().trim(), "Status *");
		log("Status label text is " + labelStatus.getText().trim());
		log("Verifying active label and radio button are displayed on add new user page");
		CCUtils.assertEquals(labelActive.getText().trim(), "Active");
		CCUtils.assertTrue(radioButtonActive.isDisplayed(), "Active radiobutton is not present on add new user page");
		log("Verifying inactive label and radio button are displayed on add new user page");
		CCUtils.assertEquals(labelInactive.getText().trim(), "Inactive");
		CCUtils.assertTrue(radioButtonInactive.isDisplayed(),
				"Inactive radiobutton is not present on add new user page");
	}

	public void verifyActiveRadioButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		log("Verifying active button is by default selected");
		CCUtils.assertTrue(radioButtonActive.isSelected(),
				"Active button is not displayed on add new user page and selected");
	}

	public String getFirstNameValue() {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		return textboxFirstName.getAttribute("value").trim();
	}

	public String getLastNameValue() {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		return textboxLastName.getAttribute("value").trim();
	}

	public String getEmailValue() {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		return textboxEmail.getAttribute("value").trim();
	}

	public String getUserNameValue() {
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		return textboxUserName.getAttribute("value").trim();
	}

	public String getMobileNumberValue() {
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		return textboxMobileNumber.getAttribute("value").replace("-", "").trim();
	}

	public void selectInactiveRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonInactive, driver);
		javascriptClick(radioButtonInactive);
		log("Clicked on inactive radiobutton in popup");
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(loginUserName, driver);
		return loginUserName.getAttribute("value").trim();
	}

	public String getFirstName() {
		CCUtils.verifyAndWaitForElement(labelFirstNameValue, driver);
		return labelFirstNameValue.getAttribute("value").trim();
	}

	public String getLastName() {
		CCUtils.verifyAndWaitForElement(labelLastNameValue, driver);
		return labelLastNameValue.getAttribute("value").trim();
	}

	public String getEmailAddress() {
		CCUtils.verifyAndWaitForElement(labelEmailValue, driver);
		return labelEmailValue.getAttribute("value").trim();
	}

	public String getMobileNumber() {
		CCUtils.verifyAndWaitForElement(labelMobileNumberValue, driver);
		return labelMobileNumberValue.getAttribute("value").trim();
	}

	public String getTooltipTextForUsernameField() {
		CCUtils.verifyAndWaitForElement(iconUsernameValidationError, driver);
		log("Hovering the mouse over the username field error icon");
		CCUtils.mouseHoverOverTheElement(driver, iconUsernameValidationError);
		return iconUsernameValidationError.getAttribute("title").trim();
	}
}
