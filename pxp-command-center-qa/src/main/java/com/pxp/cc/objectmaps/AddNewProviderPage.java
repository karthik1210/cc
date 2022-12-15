// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddNewProviderPage extends CCMainPage {
	public CommonMethods commonMethods;

	public AddNewProviderPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "openDialog")
	private WebElement addButton;

	@FindBy(id = "FirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "LastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "ExternalIdInput")
	private WebElement textboxExternalId;

	@FindBy(id = "CredentialsInput")
	private WebElement textboxCredentials;

	@FindBy(xpath = "//ng-select[@formcontrolname='gender']")
	private WebElement dropdownGender;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement saveButton;

	@FindBy(id = "searchOrgInput")
	private WebElement Searchicon;

	@FindBy(id = "searchOrgInput")
	private WebElement textboxSearch;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectProvider;

	@FindBy(id = "editDetailsBtn")
	private WebElement clickOnEdit;

	@FindBy(id = "providerEmailInput")
	private WebElement providerEmail;

	@FindBy(id = "saveDetailsBtn")
	private WebElement saveBtn;

	@FindBy(id = "selectStatus")
	private WebElement textboxSearchClientName;

	@FindBy(xpath = "//mat-icon[contains(text(),'arrow_back')]")
	private WebElement backArrow;

	@FindBy(xpath = "(//input[@id='searchFilterInput'])[1]")
	private WebElement textboxSearchProviderName;

	@FindBy(xpath = "//span[contains(text(),' Providers, Resources & Staff')]")
	private WebElement lableProvidersAndResourcesPageTitle;

	@FindBy(id = "addNewTitle")
	private WebElement labelAddNewProviderPageTitle;

	@FindBy(xpath = "(//mat-option[@id='searchList'])[1]")
	private WebElement selectFirstOption;

	@FindBy(xpath = "//span[text()='First Name * ']")
	private WebElement labelFirstName;

	@FindBy(xpath = "//span[text()='Last Name * ']")
	private WebElement labelLastName;

	@FindBy(xpath = "//span[text()='External Id * ']")
	private WebElement labelExternalId;

	@FindBy(xpath = "//span[text()='Credentials * ']")
	private WebElement labelCredentials;

	@FindBy(xpath = "//span[text()='Gender *']")
	private WebElement labelGender;

	@FindBy(xpath = "//div[@role='option']/span")
	private List<WebElement> listOfGenderValue;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(id = "addNewCancel")
	private WebElement buttonCancel;

	@FindBy(xpath = "//div[@id='maxExternalIdError']")
	private WebElement errorMaximumCharacterExternalId;

	@FindBy(xpath = "//div[@id='maxCredentialsError']")
	private WebElement errorMaximumCharacterCredential;

	@FindBy(xpath = "//div[@aria-expanded='true']/input")
	private WebElement textBoxGender;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;

	@FindBy(xpath = "//div[@id='maxfirstNameError']")
	private WebElement errorMaximumCharacterFirstName;

	@FindBy(xpath = "//div[@id='maxlastNameError']")
	private WebElement errorMaximumCharacterLastName;

	@FindBy(xpath = "//div[text()='External Id already in use']")
	private WebElement errorUniqueExternalId;
	
	@FindBy(id = "validFirstNameError")
	private WebElement errorValidFirstName;

	@FindBy(id = "validLastNameError")
	private WebElement errorValidLastName;

	List<String> actualGenderValues = new ArrayList<String>();

	public final List<String> expectedGenderValues = Arrays.asList("Male", "Female");

	public void verifyProvidersAndResourcesPageTitle() {
		CCUtils.verifyAndWaitForElement(lableProvidersAndResourcesPageTitle, driver);
		CCUtils.assertEquals(lableProvidersAndResourcesPageTitle.getText().trim(), "Providers, Resources & Staff");
		log("lableProvidersAndResourcesPageTitle page title is displayed");
	}

	public void verifyAddProvidersAndResourcesButton() {
		CCUtils.verifyAndWaitForElement(addButton, driver);
		log(" Add Button is displayed");
	}

	public void clickOnAddProviderButton() {
		CCUtils.verifyAndWaitForElement(addButton, driver);
		addButton.click();
		log("Verifying add new provider Button");
	}

	public void enterFirstName(String input) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.clear();
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(input);
		log("Entered input for First Name : " + input);
	}

	public void enterLastName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.clear();
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(input);
		log("Entered input for last Name : " + input);
	}

	public void enterCredentials(String input) {
		CCUtils.verifyAndWaitForElement(textboxCredentials, driver);
		textboxCredentials.clear();
		CCUtils.verifyAndWaitForElement(textboxCredentials, driver);
		textboxCredentials.sendKeys(input);
		log("Entered input for textboxCredentials : " + input);
	}

	public void enterExternalId(String input) {
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.clear();
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.sendKeys(input);
		log("Entered input for ExternalId : " + input);
	}

	private String xpathRequiredGender = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForGender(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectGender(String gender) {
		WebElement state = prepareDynamicXpathForGender(xpathRequiredGender, gender);
		CCUtils.verifyAndWaitForElement(state, driver);
		state.click();
		log("Clicked on : " + gender);
	}

	public void addGender(String gender) throws InterruptedException {
		log("click to dropdown of client type");
		dropdownGender.click();
		selectGender(gender);
	}

	public void clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
	}

	public void addProvider(String firstName) throws InterruptedException {
		verifyProvidersAndResourcesPageTitle();
		verifyAddProvidersAndResourcesButton();
		clickOnAddProviderButton();
		enterFirstName(firstName);
		enterLastName(CCUtils.generateAlphaNumericString(18));
		enterExternalId(CCUtils.generateAlphaNumericString(8));
		enterCredentials(CCUtils.generateAlphaNumericString(8));
		addGender("Female");
		clickOnSaveAndAddButton();
	}

	public void clickSearchIcon() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(Searchicon, driver);
		Searchicon.click();
	}

	public void enterSearchText(String firstName) {
		CCUtils.verifyAndWaitForElement(textboxSearch, driver);
		textboxSearch.sendKeys(firstName);
		log("Entered text in search :" + firstName);
	}

	public void selectProvider() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectProvider, driver);
		selectProvider.click();
		log("Clicked on first Provider");
	}

	public void clickOnEdit() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(clickOnEdit, driver);
		clickOnEdit.click();
	}

	public void enterProviderEmail(String email) {
		CCUtils.verifyAndWaitForElement(providerEmail, driver);
		providerEmail.sendKeys(email);
	}

	public void clickOnSave() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(saveBtn, driver);
		saveBtn.click();
	}

	public void clickOnBackArrow() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(backArrow, driver);
		backArrow.click();
	}

	public void editProvider() throws InterruptedException {
		clickOnEdit();
		enterProviderEmail(CCUtils.generateNewEmail(12));
		clickOnSave();
		clickOnBackArrow();
	}

	public void verifyProvidersAndResourcesTitle() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(lableProvidersAndResourcesPageTitle, driver);
		CCUtils.assertEquals(lableProvidersAndResourcesPageTitle.isDisplayed(), true);
	}

	public void clickFirstOption() {
		CCUtils.verifyAndWaitForElement(selectFirstOption, driver);
		if (selectFirstOption.isDisplayed()) {
			log("Clicked on " + selectFirstOption.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstOption);
		} else {
			log(selectFirstOption.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present in client");
		}
	}

	public void enterProviderName(String providerName) {
		CCUtils.verifyAndWaitForElement(textboxSearchProviderName, driver);
		textboxSearchProviderName.click();
		textboxSearchProviderName.sendKeys(providerName);
		log("Entered input for search provider field : " + providerName);
	}

	public void searchProvider(String providerName) throws NullPointerException, InterruptedException {
		enterProviderName(providerName);
		clickFirstOption();
		CCUtils.pressEnterKey();
	}

	public void verifyAddButtonIsEnabled() {
		logStep("Verifying add button is enabled or not");
		CCUtils.verifyAndWaitForElement(addButton, driver);
		CCUtils.assertTrue(addButton.isEnabled(), "Add button is disabled on provider page");
	}

	public void verifyAddNewProviderPageTitle() {
		CCUtils.verifyAndWaitForElement(labelAddNewProviderPageTitle, driver);
		log("Verifying add new provider page title");
		CCUtils.assertEquals(labelAddNewProviderPageTitle.getText().trim(), "Add a Provider");
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

	public void verifyExternalIdLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelExternalId, driver);
		log("Verifying external id label text");
		CCUtils.assertEquals(labelExternalId.getText().trim(), "External Id *");
		log("External id label text is " + labelExternalId.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		log("Verifying Input External Id placeholder is displayed for external id textbox field");
		CCUtils.assertEquals(textboxExternalId.getAttribute("placeholder").trim(), "Input External Id");
		log("Box text for external id textbox field is " + textboxExternalId.getAttribute("placeholder").trim());
	}

	public void verifyCredentialsLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelCredentials, driver);
		log("Verifying credentials label text");
		CCUtils.assertEquals(labelCredentials.getText().trim(), "Credentials *");
		log("Credentials label text is " + labelCredentials.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxCredentials, driver);
		log("Verifying input text placeholder is displayed for last name textbox field");
		CCUtils.assertEquals(textboxCredentials.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for credentials textbox field is " + textboxCredentials.getAttribute("placeholder").trim());
	}

	public void getGenderDropDownValues() throws InterruptedException {
		for (int i = 0; i < listOfGenderValue.size(); i++) {
			actualGenderValues.add(listOfGenderValue.get(i).getText().trim());
		}
		log("Gender dropdown values :" + actualGenderValues);
	}

	public void verifyGenderDropDownValues() throws InterruptedException {
		getGenderDropDownValues();
		CCUtils.assertEquals(actualGenderValues, expectedGenderValues);
	}

	public void verifyGenderLabelTextAndDropDownValue() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(labelGender, driver);
		log("Verifying gender label text");
		CCUtils.assertEquals(labelGender.getText().trim(), "Gender *");
		log("gender label text is " + labelGender.getText().trim());
		log("Verifying Select gender placeholder is displayed for gender field");
		CCUtils.assertEquals(dropdownGender.getAttribute("placeholder").trim(), "Select gender");
		log("Box text for gender field is " + dropdownGender.getAttribute("placeholder").trim());
		clickOnGenderDropdown();
		log("Verifying gender dropdown values");
		verifyGenderDropDownValues();
		clickOnGenderDropdown();
	}

	public void clickOnGenderDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownGender, driver);
		dropdownGender.click();
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		log("Verifying cross icon is displayed on add a provider page");
		if (crossIcon.isDisplayed() == true) {
			log("Verifying cross icon is enabled");
			CCUtils.assertTrue(crossIcon.isEnabled(), "Cross icon is disabled");
		} else {
			log("cross icon is not displayed on add a provider page");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on add a provider page");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button is enabled");
			CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on add a provider page");
		}
	}

	public void verifySaveAndAddButtonIsDisabled() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		logStep("Verifying Save and add button is disabled");
		CCUtils.assertEquals(saveButton.isEnabled(), false);
	}

	public void VerifyAddNewProviderPageUi() throws Exception {
		verifyAddNewProviderPageTitle();
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyExternalIdLabelTextAndTextboxDefaultValue();
		verifyCredentialsLabelTextAndTextboxDefaultValue();
		verifyGenderLabelTextAndDropDownValue();
		verifyCancelButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
		verifySaveAndAddButtonIsDisabled();
	}

	public String getMaximumCharacterErrorForExternalIdField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterExternalId, driver);
		return errorMaximumCharacterExternalId.getText().trim();
	}

	public String getMaximumCharacterErrorForCredentialField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterCredential, driver);
		return errorMaximumCharacterCredential.getText().trim();
	}

	public void enterGender(String input) {
		CCUtils.verifyAndWaitForElement(textBoxGender, driver);
		textBoxGender.sendKeys(input);
		log("Entered input for Gender : " + input);
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}

	public String getMaximumCharacterErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterFirstName, driver);
		return errorMaximumCharacterFirstName.getText().trim();
	}

	public String getMaximumCharacterErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterLastName, driver);
		return errorMaximumCharacterLastName.getText().trim();
	}

	public void selectGenderValue(String value) {
		clickOnGenderDropdown();
		if (listOfGenderValue.size() > 0) {
			if (value.equals("Male")) {
				javascriptClick(listOfGenderValue.get(0));
				log("Clicked on 'Male' option");
			} else if (value.equals("Female")) {
				javascriptClick(listOfGenderValue.get(1));
				log("Clicked on 'Female' option");

			} else {
				log("Gender value not displayed on add a provider screen");
			}
		}
	}

	public void verifySaveAndAddButtonIsEnabled() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		logStep("Verifying Save and add button is enabled");
		CCUtils.assertEquals(saveButton.isEnabled(), true);
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel button");
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on X icon");
	}

	public String getUniqueErrorMessageForExternalIdField() {
		CCUtils.verifyAndWaitForElement(errorUniqueExternalId, driver);
		return errorUniqueExternalId.getText().trim();
	}
	
	public String getValidErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorValidFirstName, driver);
		return errorValidFirstName.getText().trim();
	}

	public String getValidErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorValidLastName, driver);
		return errorValidLastName.getText().trim();
	}
}
