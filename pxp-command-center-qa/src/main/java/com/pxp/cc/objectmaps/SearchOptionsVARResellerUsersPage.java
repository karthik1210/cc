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

public class SearchOptionsVARResellerUsersPage extends CCMainPage {

	public SearchOptionsVARResellerUsersPage(WebDriver driver) {
		super(driver);
	}


	@FindBy(xpath = "//div[contains(text(),'First Name')]")
	private WebElement labelFirstName;

	@FindBy(xpath = "//div[contains(text(),'Last Name')]")
	private WebElement labelLastName;

	@FindBy(xpath = "//div[contains(text(),'VARReseller')]")
	private WebElement labelVARReseller;

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

	@FindBy(xpath = "//input[@formcontrolname='VARReseller']")
	private WebElement dropdownSearchVARReseller;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

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
	
	List<String> actualListOfEnrollmentStatusValues = new ArrayList<String>();
    List<String> actualUserStatusValues = new ArrayList<String>();
	
	public final List<String> expectedUserStatusValues = Arrays.asList("Active", "Inactive", "Locked out",
			"No roles assigned");

	List<String> actualEnrollmentStatusValues = new ArrayList<String>();

	public final List<String> expectedEnrollmentStatusValues = Arrays.asList("Confirmed", "Delivered", "Pending",
			"Email Issue", "Expired", "Not Sent");

	public final List<String> expectedListOfEnrollmentStatusValues = Arrays.asList("Delivered", "Pending",
			"Email Issue", "Expired", "Not Sent");

	public void verifySearchVARResellerUserPopup() throws InterruptedException, AWTException {
		log("Verifying search VARReseller user pop-up");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyVARResellerLabelTextAndTextboxDefaultValue();
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
		CCUtils.assertTrue(labelFirstName.isDisplayed(),
				"First name label is not displayed on search options popup");
		CCUtils.assertTrue(labelFirstName.isDisplayed(),
				"First name input textbox are not displayed on search options popup");
		CCUtils.assertEquals(labelFirstName.getText().trim(), "First Name");
		CCUtils.assertEquals(labelFirstName.isEnabled(), true);
		CCUtils.assertEquals(textboxFirstName.getText().trim(), "Input Text");
		CCUtils.assertEquals(textboxFirstName.isEnabled(), true);
		
	}
	public void verifyLastNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLastName, driver);
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		CCUtils.assertTrue(labelLastName.isDisplayed(),
				"Last name label is not displayed on search options popup");
		CCUtils.assertTrue(labelLastName.isDisplayed(),
				"First name input textbox are not displayed on search options popup");
		CCUtils.assertEquals(labelLastName.getText().trim(), "Last Name");
		CCUtils.assertEquals(labelLastName.isEnabled(), true);
		CCUtils.assertEquals(textboxLastName.getText().trim(), "Input Text");
		CCUtils.assertEquals(textboxLastName.isEnabled(), true);
	}

	private void verifyVARResellerLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelVARReseller, driver);
		CCUtils.verifyAndWaitForElement(dropdownSearchVARReseller, driver);
		CCUtils.assertTrue(labelVARReseller.isDisplayed(),
				"VARReseller label are not displayed on search options popup");
		CCUtils.assertTrue(dropdownSearchVARReseller.isDisplayed(),
				"VARReseller Search/Select dropdown are not displayed on search options popup");
		CCUtils.assertEquals(labelVARReseller.getText().trim(), "VARReseller");
		CCUtils.assertEquals(labelVARReseller.isEnabled(), true);	
		CCUtils.assertEquals(dropdownSearchVARReseller.getAttribute("placeholder").trim(), "Search/Select");
		CCUtils.assertEquals(dropdownSearchVARReseller.isEnabled(), true);	
		
	}
	
	public void verifyUsernameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUsername, driver);
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		CCUtils.assertTrue(labelUsername.isDisplayed(),
				"Username label is not displayed on search options popup");
		CCUtils.assertTrue(textboxUsername.isDisplayed(),
				"Username input textbox are not displayed on search options popup");
		CCUtils.assertEquals(labelUsername.getText().trim(), "Username");
		CCUtils.assertEquals(labelUsername.isEnabled(), true);	
		CCUtils.assertEquals(textboxUsername.getText().trim(), "Input Text");
		CCUtils.assertEquals(textboxUsername.isEnabled(), true);	
	}

	public void verifyEmailLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		CCUtils.assertTrue(labelEmail.isDisplayed(),
				"Email label is not displayed on search options popup");
		CCUtils.assertTrue(textboxEmail.isDisplayed(),
				"Email input textbox are not displayed on search options popup");
		CCUtils.assertEquals(labelEmail.getText().trim(), "Email");
		CCUtils.assertEquals(labelEmail.isEnabled(), true);	
		CCUtils.assertEquals(textboxEmail.getText().trim(), "Input Text");
		CCUtils.assertEquals(textboxEmail.isEnabled(), true);	
	}

	public void verifyMobileNumberLabelTextAndTextboxDefaultValue() {
	        CCUtils.verifyAndWaitForElement(labelMobileNumber, driver);
			CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
			CCUtils.assertTrue(labelMobileNumber.isDisplayed(),
					"MobileNumber label is not displayed on search options popup");
			CCUtils.assertTrue(textboxMobileNumber.isDisplayed(),
					"MobileNumber input textbox are not displayed on search options popup");
			CCUtils.assertEquals(labelMobileNumber.getText().trim(), "MobileNumber");
			CCUtils.assertEquals(labelMobileNumber.isEnabled(), true);	
			CCUtils.assertEquals(textboxMobileNumber.getText().trim(), "Input Tex");
			CCUtils.assertEquals(textboxMobileNumber.isEnabled(), true);	
	}

	public void verifyUserStatusLabelTextAndCheckboxes() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelUserStatus, driver);
		CCUtils.scrollPageToEnd();
		if (labelUserStatus.isDisplayed()) {
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

	public void verifyClearButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		CCUtils.assertEquals(buttonClear.getText().trim(), "CLEAR");
	}

	public void clickOnClearButton() throws InterruptedException {
		log("Verifying clear button is displayed on search options popup");
		verifyClearButtonIsDisplayed();
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		buttonClear.click();
	}

	public void verifyClearAllButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(butonClearAll, driver);
		CCUtils.assertEquals(butonClearAll.getText().trim(), "CLEAR ALL");
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

public void verifyClearAllButtonFunctionalityWithAllFields(String firstName, String lastName, String VARReseller,
		String email, String username, String mobileNumber) throws AWTException, InterruptedException {
	CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
	textboxFirstName.sendKeys(firstName);
	log("Entered text in first name field is " + firstName);

	CCUtils.verifyAndWaitForElement(textboxLastName, driver);
	textboxLastName.sendKeys(lastName);
	log("Entered text in last name field is " + lastName);

	clearSearchTextInVARResellerSearchBox();
	searchNode(VARReseller);
	log("Entered text in client field is " + VARReseller);

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
	CCUtils.verifyAndWaitForElement(buttonSearch, driver);
	scrollAndWait(0, 500, 200);
	scrollAndWait(0, 500, 8000);
	javascriptClick(buttonSearch);
}

public void clickOnClearAllSearchData() throws InterruptedException {
	CCUtils.verifyAndWaitForElement(butonClearAll, driver);
	scrollAndWait(0, 500, 200);
	butonClearAll.click();
}

public void verifySearchButtonIsDisplayed() {
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
		CCUtils.assertEquals(labelEnrollmentStatus.getText().trim(), "Enrollment Status");
		log("User Status label text is " + labelEnrollmentStatus.getText().trim());
		log("Verifying User status label are displayed on search VAR/Reseller user page");
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

public void clickonSearchButton() {
	CCUtils.verifyAndWaitForElement(buttonSearch, driver);
	buttonSearch.click();
	log("search button clicked");

}

public void verifyErrorMessageOnSearchOptionsPage() throws InterruptedException, AWTException {
	clickonSearchButton();
	scrollAndWait(0, 500, 200);
	CCUtils.verifyAndWaitForElement(buttonHideSearchOptions, driver);
	CCUtils.assertEquals(errorSearchOptionsPage.getText().trim(), "Please enter or select to search");
	log("Error message displayed for empty search field " + errorSearchOptionsPage.getText().trim());
	buttonHideSearchOptions.click();
	log("search button closed " + buttonHideSearchOptions);

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

	clearSearchTextInVARResellerSearchBox();
	searchNode(clientName);
	log("Entered text in client field is " + clientName);

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
	log("Entered text in email field is " + mobileNumber);
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

public void clearSearchTextInVARResellerSearchBox() {
	CCUtils.verifyAndWaitForElement(dropdownSearchVARReseller, driver);
	dropdownSearchVARReseller.clear();
	log("text in VARReseller search box is cleared");
}

public void searchNode(String clientName) {
	enterSearchText(clientName);
	clickFirstInAutoSuggestion();
	CCUtils.pressEnterKey();
}

public void enterSearchText(String nodeName) {
	CCUtils.verifyAndWaitForElement(dropdownSearchVARReseller, driver);
	dropdownSearchVARReseller.sendKeys(nodeName);
	log("Entered text in search :" + nodeName);
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
}