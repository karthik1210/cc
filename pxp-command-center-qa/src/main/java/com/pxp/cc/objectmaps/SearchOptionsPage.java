// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

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

	@FindBy(id = "internal Users")
	private WebElement labelTitleInternalUsersPage;

	@FindBy(xpath = "//div[contains(text(),'First Name')]")
	private WebElement labelFirstName;

	@FindBy(xpath = "//div[contains(text(),'Last Name')]")
	private WebElement labelLastName;

	@FindBy(xpath = "//div[contains(text(),'Email')]")
	private WebElement labelEmail;

	@FindBy(xpath = "//div[contains(text(),'Username')]")
	private WebElement labelUsername;

	@FindBy(xpath = "//div[contains(text(),' Mobile Number ')]")
	private WebElement labelMobileNumber;

	@FindBy(xpath = "//input[@formcontrolname='firstName']")
	private WebElement textboxFirstName;

	@FindBy(xpath = "//input[@formcontrolname='lastName']")
	private WebElement textboxLastName;

	@FindBy(xpath = "//input[@formcontrolname='userName']")
	private WebElement textboxUsername;

	@FindBy(xpath = "//input[@formcontrolname='email']")
	private WebElement textboxEmail;

	@FindBy(xpath = "//input[@formcontrolname='mobileNumber']")
	private WebElement textboxMobileNumber;

	@FindBy(xpath = "//div[contains(text(),'User Status')]")
	private WebElement labelUserStatus;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//following::span[@class='mat-checkbox-label']")
	private List<WebElement> labelUserStatusList;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//following::input[@type='checkbox']")
	private List<WebElement> checkboxUserStatus;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//following::div[@class='mat-checkbox-inner-container']")
	private WebElement checkboxUserStatusSingle;

	@FindBy(xpath = "//div[contains(text(),'Enrollment Status')]")
	private WebElement labelEnrollmentStatus;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//preceding::span[@class='mat-checkbox-label']")
	private List<WebElement> labelEnrollmentStatusList;

	@FindBy(xpath = "//div[@class='mat-checkbox-inner-container']")
	private List<WebElement> checkboxEnrollmentAndUserStatusList;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//preceding::input[@type='checkbox']")
	private List<WebElement> checkboxEnrollmentStatus;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//preceding::div[@class='mat-checkbox-inner-container']")
	private WebElement checkboxEnrollmentStatusSingle;

	@FindBy(id = "filterClearBtn")
	private WebElement buttonClear;

	@FindBy(id = "filterSearchBtn")
	private WebElement buttonSearch;

	@FindBy(id = "searchFilterBtn")
	private WebElement buttonHideSearchOptions;

	@FindBy(xpath = "//label[contains(text(),'Please enter or select to search')]")
	private WebElement errorSearchOptionsPage;

	@FindBy(xpath = "//div[contains(text(),'First Name')]//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterFirstName;

	@FindBy(xpath = "//div[contains(text(),'Last Name')]//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterLastName;

	@FindBy(xpath = "//div[contains(text(),'Username')]//following::div[text()=' Maximum characters reached '][1]")
	private WebElement errorMaximumCharacterUsername;

	@FindBy(xpath = "//input[@formcontrolname='email']//following::div[text()=' Maximum characters reached '][1]")
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

	List<String> actualUserStatusValues = new ArrayList<String>();

	public final List<String> expectedUserStatusValues = Arrays.asList("Active", "Inactive", "Locked out",
			"No roles assigned");

	List<String> actualEnrollmentStatusValues = new ArrayList<String>();

	public final List<String> expectedEnrollmentStatusValues = Arrays.asList("Confirmed", "Delivered", "Pending",
			"Email Issue", "Expired", "Not Sent");

	public void verifySearchInternalUserPopup() throws InterruptedException, AWTException {
		log("Verifying search internal user pop-up");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		verifyUserStatusLabelTextAndCheckboxes();
		verifyEnrollmentStatusLabelTextAndCheckboxes();
		verifyClearButtonIsDisplayed();
		verifySearchButtonIsDisplayed();
	}

	public void verifyFirstNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelFirstName, driver);
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		if (labelFirstName.isDisplayed() && textboxFirstName.isDisplayed()) {
			log("Verifying first name label text");
			CCUtils.assertEquals(labelFirstName.getText().trim(), "First Name");
			log("First name label text is " + labelFirstName.getText().trim());
			log("Verifying input text placeholder is displayed for first name textbox field");
			CCUtils.assertEquals(textboxFirstName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for first name textbox field is " + textboxFirstName.getAttribute("placeholder").trim());
		} else {
			log("First name label and input textbox are not displayed on search internal user page");
		}
	}

	public void verifyLastNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLastName, driver);
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		if (labelLastName.isDisplayed() && textboxLastName.isDisplayed()) {
			log("Verifying last name label text");
			CCUtils.assertEquals(labelLastName.getText().trim(), "Last Name");
			log("Last name label text is " + labelLastName.getText().trim());
			log("Verifying input text placeholder is displayed for last name textbox field");
			CCUtils.assertEquals(textboxLastName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for last name textbox field is " + textboxLastName.getAttribute("placeholder").trim());
		} else {
			log("Last name label and input textbox are not displayed on search internal user page");
		}
	}

	public void verifyUsernameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUsername, driver);
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		if (labelUsername.isDisplayed() && textboxUsername.isDisplayed()) {
			log("Verifying username label text");
			CCUtils.assertEquals(labelUsername.getText().trim(), "Username");
			log("Username label text is " + labelUsername.getText().trim());
			log("Verifying input text placeholder is displayed for username textbox field ");
			CCUtils.assertEquals(textboxUsername.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for username textbox field is " + textboxUsername.getAttribute("placeholder").trim());
		} else {
			log("Username label and input textbox are not displayed on search internal user page");
		}
	}

	public void verifyEmailLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		if (labelEmail.isDisplayed() && textboxEmail.isDisplayed()) {
			log("Verifying email label text");
			CCUtils.assertEquals(labelEmail.getText().trim(), "Email");
			log("Email label text is " + labelEmail.getText().trim());
			log("Verifying input text placeholder is displayed for email textbox field ");
			CCUtils.assertEquals(textboxEmail.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for email textbox field is " + textboxEmail.getAttribute("placeholder").trim());
		} else {
			log("Email label and input textbox are not displayed on search internal user page");
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
			log("Mobile number label and input textbox are not displayed on search internal user page");
		}
	}

	public void verifyUserStatusLabelTextAndCheckboxes() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelUserStatus, driver);
		CCUtils.scrollPageToEnd();
		if (labelUserStatus.isDisplayed()) {
			log("Verifying status label text");
			CCUtils.assertEquals(labelUserStatus.getText().trim(), "User Status");
			log("User Status label text is " + labelUserStatus.getText().trim());
			log("Verifying User status label are displayed on search internal user page");
			verifyUserStatusValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserStatusCheckboxesAreNotByDefaultSelected();
		} else {
			log("User status label is not displayed on search internal user page");
		}
	}

	public void getUserStatusValues() throws InterruptedException {
		for (int i = 0; i < labelUserStatusList.size(); i++) {
			actualUserStatusValues.add(labelUserStatusList.get(i).getText().trim());
		}
		log("User status available for internal user are :" + actualUserStatusValues);
	}

	public void verifyUserStatusValues() throws InterruptedException {
		getUserStatusValues();
		CCUtils.assertEquals(actualUserStatusValues, expectedUserStatusValues);
	}

	public void verifyUserStatusCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxUserStatus.size(); i++) {
			CCUtils.assertTrue(!checkboxUserStatus.get(i).isSelected(), "User role checkboxes are already selected");
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
		log("Verifying clear button is displayed on search internal user page");
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

		clickEnrollmentStatusCheckboxes();

		clickOnSearchButton();
		scrollAndWait(0, 500, 8000);
		clickOnClearAllSearchData();
	}

	public void clickOnSearchButton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		scrollAndWait(0, 500, 200);
		buttonSearch.click();
	}

	public void clickOnClearAllSearchData() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(butonClearAll, driver);
		scrollAndWait(0, 500, 200);
		butonClearAll.click();
	}

	public void verifySearchButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		log("Verifying search button is displayed on search internal user page");
		if (buttonSearch.isDisplayed() == true) {
			log("Verifying search button is enabled");
			CCUtils.assertTrue(buttonSearch.isEnabled(), "search button is disabled");
			log("Verifying search button text");
			CCUtils.assertEquals(buttonSearch.getText().trim(), "SEARCH");
		} else {
			log("Search button is not displayed on search internal user page");
		}
	}

	public void verifyEnrollmentStatusLabelTextAndCheckboxes() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelEnrollmentStatus, driver);
		if (labelEnrollmentStatus.isDisplayed()) {
			log("Verifying status label text");
			CCUtils.assertEquals(labelEnrollmentStatus.getText().trim(), "Enrollment Status");
			log("User Status label text is " + labelEnrollmentStatus.getText().trim());
			log("Verifying User status label are displayed on search internal user page");
			verifyEnrollmentStatusValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserStatusCheckboxesAreNotByDefaultSelected();
		} else {
			log("Enrollment status label is not displayed on search internal user page");
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

	public void verifyEnrollmentStatusValues() throws InterruptedException {
		getEnrollmentStatusValues();
		CCUtils.assertEquals(actualEnrollmentStatusValues, expectedEnrollmentStatusValues);
	}

	public void verifyEnrollmentStatusCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxEnrollmentStatus.size(); i++) {
			CCUtils.assertTrue(!checkboxEnrollmentStatus.get(i).isSelected(),
					"User role checkboxes are already selected");
		}
	}

	public void verifyErrorMessageOnSearchOptionsPage() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		log("Verifying required error message is displayed on search internal user page");
		buttonSearch.click();
		scrollAndWait(0, 500, 200);
		CCUtils.verifyAndWaitForElement(buttonHideSearchOptions, driver);
		CCUtils.assertEquals(errorSearchOptionsPage.getText().trim(), "Please enter or select to search");
		log("Error message displayed for empty search field " + errorSearchOptionsPage.getText().trim());
		buttonHideSearchOptions.click();
		log("search button closed " + buttonHideSearchOptions);

	}

	public void verifyMaximumCharactersErrorMessage(String firstName, String lastName, String username, String email,
			Long mobileNumber) {
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

		log("Enter 10 numbers in mobile textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.sendKeys(String.valueOf(mobileNumber));
		log("Entered text in email field is " + mobileNumber);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterMobileNumber, driver);
		CCUtils.assertEquals(errorMaximumCharacterMobileNumber.getText().trim(), "Maximum characters reached");
		log("Error message displayed for email field is " + errorMaximumCharacterMobileNumber.getText().trim());
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

	public void verifyFieldErrorMessageWithInvalidData(String invalidFirstName, String invalidLastName,
			String invalidEmail, String invalidUsername, String invalidMobileNumber) {
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
			CCUtils.assertTrue(firstName.length() == 0, "Text is not present in first field");
			log("Verifying text is not present in firstname field");
			CCUtils.assertTrue(lastName.length() == 0, "Text is not present in last field");
			log("Verifying text is not present in lastname field");
			CCUtils.assertTrue(userName.length() == 0, "Text is not present in username field");
			log("Verifying text is not present in username field");
			CCUtils.assertTrue(emailName.length() == 0, "Text is not present in email field");
			log("Verifying text is not present in email field");
			CCUtils.assertTrue(mobileNumber.length() == 0, "Text is not present in mobilenumber field");
			log("Verifying text is not present in mobilenumber field");
	}

}
