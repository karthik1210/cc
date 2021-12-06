// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class ClientUserDetailsPage extends CCMainPage {

	@FindBy(xpath = "//div[@class='arrowDiv']")
	private WebElement labelPageTitle;

	@FindBy(id = "kebabMenu")
	private WebElement buttonKebabMenu;

	@FindBy(id = "enrollBtn")
	private WebElement buttonSendEnrollmentEmail;

	@FindBy(id = "enrollStatus")
	private WebElement labelEnrollmentStatusValue;

	@FindBy(id = "userStatus")
	private WebElement labelUserStatusValue;

	@FindBy(xpath = "//mat-card-title[@id='userName']")
	private WebElement labelUsernameValue;

	@FindBy(id = "clientEdit")
	private WebElement buttonEditClientUser;

	@FindBy(id = "editCancel")
	private WebElement buttonCancel;

	@FindBy(id = "saveClient")
	private WebElement buttonSave;

	@FindBy(id = "addFirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "addLastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "addEmailInput")
	private WebElement textboxEmail;

	@FindBy(id = "addUserNameInput")
	private WebElement textboxUserName;

	@FindBy(id = "mobileInput")
	private WebElement textboxMobileNumber;

	@FindBy(id = "password")
	private WebElement labelPassword;

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

	@FindBy(xpath = "//div[text()='Username already in use']")
	private WebElement errorUniqueUsername;

	@FindBy(xpath = "//div[text()='Email already in use']")
	private WebElement errorUniqueEmail;

	@FindBy(xpath = "//div[text()='Mobile number already in use']")
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

	public ClientUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		log("Click on kebab menu icon on client user details page");
		javascriptClick(buttonKebabMenu);
	}

	public void verifySendEnrollmentButtonIsNotDisplayed() {
		log("Verifying send enrollment email button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), false);
	}

	public void verifySelectedUserStatusIsSameOnClientUserDetailsPage(String userStatus) {
		CCUtils.verifyAndWaitForElement(labelUserStatusValue, driver);
		if (labelUserStatusValue.isDisplayed()) {
			CCUtils.assertEquals(labelUserStatusValue.getText().trim(), userStatus);
		} else {
			log("User status value is not present on client user details page");
		}
	}

	public void verifySendEnrollmentButtonIsDisplayed() {
		log("Verifying send enrollment email button is isplayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), true);
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(labelUsernameValue, driver);
		return labelUsernameValue.getText().trim();
	}

	public SendEnrollmentConfirmationPage clickOnSendEnrollmentEmailButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollmentEmail, driver);
		log("Click on send enrollment email button");
		javascriptClick(buttonSendEnrollmentEmail);
		return PageFactory.initElements(driver, SendEnrollmentConfirmationPage.class);
	}

	public void verifyClientUserDetailsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed()) {
			String title = labelPageTitle.getText().trim().replace("arrow_back", "");
			CCUtils.assertEquals(title.trim(), "Client User Details");
		} else {
			log("Client user details page title is not displayed on screen");
		}
	}

	public void clickEditInClientUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditClientUser, driver);
		javascriptClick(buttonEditClientUser);
	}

	public void verifyPasswordUneditable() {
		CCUtils.verifyAndWaitForElement(labelPassword, driver);
		CCUtils.assertEquals(labelPassword.getAttribute("disabled"), "true");
		log("Password is not editable");
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

	public void clickCancel() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public void clickSave() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		buttonSave.click();
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

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		if (buttonCancel.isDisplayed()) {
			CCUtils.assertEquals(buttonCancel.getText().trim(), "CANCEL");
		} else {
			logStep("Cancel button is not present on client user details page");
		}
	}

	public void verifySaveButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		if (buttonSave.isDisplayed()) {
			CCUtils.assertEquals(buttonSave.getText().trim(), "SAVE");
		} else {
			logStep("Save button is not present on client user details page");
		}
	}

}
