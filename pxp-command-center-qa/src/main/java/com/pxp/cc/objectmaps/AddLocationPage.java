// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddLocationPage extends CCMainPage {

	public CommonMethods commonMethods;

	public AddLocationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "generalLabel")
	private WebElement labelLocationsPageTitle;

	@FindBy(id = "openDialog")
	private WebElement addButton;

	@FindBy(id = "addNewTitle")
	private WebElement labelPageTitle;

	@FindBy(id = "locationName")
	private WebElement labelLocationName;

	@FindBy(id = "LocationNameInput")
	private WebElement textboxLocationName;

	@FindBy(id = "locationDisplayENName")
	private WebElement labelLocationDisplayName;

	@FindBy(id = "LocationDisplayNameInput")
	private WebElement textboxLocationDisplayName;

	@FindBy(id = "address1")
	private WebElement labelAddressOne;

	@FindBy(id = "Address1Input")
	private WebElement textboxAddressOne;

	@FindBy(id = "address2")
	private WebElement labelAddressTwo;

	@FindBy(id = "Address2Input")
	private WebElement textboxAddressTwo;

	@FindBy(id = "city")
	private WebElement labelCity;

	@FindBy(id = "CityInput")
	private WebElement textboxCity;

	@FindBy(id = "state")
	private WebElement labelState;

	@FindBy(id = "zipCode")
	private WebElement labelZip;

	@FindBy(id = "ZipCodeInput")
	private WebElement textboxZip;

	@FindBy(id = "timezone-label")
	private WebElement labelTimezone;

	@FindBy(id = "locationIdExternal")
	private WebElement labelLocationId;

	@FindBy(id = "LocationExternalInput")
	private WebElement textboxLocationId;

	@FindBy(id = "stateDropdown")
	private WebElement dropdownState;

	@FindBy(id = "addNewLocationSave")
	private WebElement saveLocation;

	@FindBy(id = "addNewCancel")
	private WebElement buttonCancel;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//span[contains(text(),'Success! New location has been successfully created')]")
	private WebElement toasterMessage;

	@FindBy(id = "maxlocationNameError")
	private WebElement errorMessage;

	@FindBy(id = "LocationExternalInput")
	private WebElement textboxLocationExternal;
	
	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectLocation;
	
	@FindBy(id = "searchOrgInput")
	private WebElement Searchicon;
	
	@FindBy(id = "searchOrgInput")
	private WebElement textboxSearch;
	
	public void verifyLocationsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelLocationsPageTitle, driver);
		CCUtils.assertEquals(labelLocationsPageTitle.getText().trim(), "Locations");
		log("Locations page title is displayed");
	}

	public void verifyAddLocationButton() {
		CCUtils.verifyAndWaitForElement(addButton, driver);
		log("Locations Add Button is displayed");
	}

	public void clickOnAddLocationButton() {
		CCUtils.verifyAndWaitForElement(addButton, driver);
		addButton.click();
		log("Verifying add new location Button");
	}

	public void verifyAddLocationPopupTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		log("Verifying create Location page title");
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Add a Location");
	}

	public void verifyLocationNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLocationName, driver);
		log("Verifying Location name label text");
		CCUtils.assertEquals(labelLocationName.getText().trim(), "Location Name *");
		log("Location name label text is " + labelLocationName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxLocationName, driver);
		log("Verifying Input text here placeholder is displayed for Location name textbox field");
		CCUtils.assertEquals(textboxLocationName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for Location name textbox field is " + textboxLocationName.getAttribute("placeholder").trim());
	}

	public void verifyLocationDisplayNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLocationDisplayName, driver);
		log("Verifying Location Display Name label text");
		CCUtils.assertEquals(labelLocationDisplayName.getText().trim(), "Location Display Name (EN)");
		log("Location Display Name label text is " + labelLocationDisplayName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayName, driver);
		log("Verifying Input Text placeholder is displayed for Location Display Name textbox field");
		CCUtils.assertEquals(textboxLocationDisplayName.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for Location Display Name textbox field is "
				+ textboxLocationDisplayName.getAttribute("placeholder").trim());
	}

	public void verifyAddressOneLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelAddressOne, driver);
		log("Verifying AddressOne label text");
		CCUtils.assertEquals(labelAddressOne.getText().trim(), "Address 1 *");
		log("Address1 * label text is " + labelAddressOne.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxAddressOne, driver);
		log("Verifying Input Text placeholder is displayed for AddressOne textbox field");
		CCUtils.assertEquals(textboxAddressOne.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for AddressOne textbox field is " + textboxAddressOne.getAttribute("placeholder").trim());
	}

	public void verifyAddressTwoLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelAddressTwo, driver);
		log("Verifying AddressTwo label text");
		CCUtils.assertEquals(labelAddressTwo.getText().trim(), "Address 2");
		log("Address2 label text is " + labelAddressTwo.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxAddressTwo, driver);
		log("Verifying Input Text placeholder is displayed for AddressTwo textbox field");
		CCUtils.assertEquals(textboxAddressTwo.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for AddressTwo textbox field is " + textboxAddressTwo.getAttribute("placeholder").trim());
	}

	public void verifyCityLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelCity, driver);
		log("Verifying City label text");
		CCUtils.assertEquals(labelCity.getText().trim(), "City *");
		log("City label text is " + labelCity.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxCity, driver);
		log("Verifying Input Text placeholder is displayed for City textbox field");
		CCUtils.assertEquals(textboxCity.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for City textbox field is " + textboxCity.getAttribute("placeholder").trim());
	}

	public void verifyStateLabelText() {
		CCUtils.verifyAndWaitForElement(labelState, driver);
		log("Verifying State label text");
		CCUtils.assertEquals(labelState.getText().trim(), "State *");
		log("State label text is " + labelState.getText().trim());
	}

	public void verifyZipLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelZip, driver);
		log("Verifying Zip label text");
		CCUtils.assertEquals(labelZip.getText().trim(), "Zip *");
		log("Zip label text is " + labelZip.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxZip, driver);
		log("Verifying Input Text placeholder is displayed for Zip textbox field");
		CCUtils.assertEquals(textboxCity.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for Zip textbox field is " + textboxCity.getAttribute("placeholder").trim());
	}

	public void verifyTimezoneLabelText() {
		CCUtils.verifyAndWaitForElement(labelTimezone, driver);
		log("Verifying Timezone label text");
		CCUtils.assertEquals(labelTimezone.getText().trim(), "Timezone *");
		log("Timezone label text is " + labelTimezone.getText().trim());
	}

	public void verifyLocationIdLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelZip, driver);
		log("Verifying Zip label text");
		CCUtils.assertEquals(labelZip.getText().trim(), "Zip *");
		log("Zip label text is " + labelZip.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxZip, driver);
		log("Verifying Input Text placeholder is displayed for Zip textbox field");
		CCUtils.assertEquals(textboxCity.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for Zip textbox field is " + textboxCity.getAttribute("placeholder").trim());
	}

	public void verifyAddNewLocationPopup() throws InterruptedException, AWTException {
		log("Verifying add a location pop-up");
		verifyAddLocationPopupTitle();
		verifyLocationNameLabelTextAndTextboxDefaultValue();
		verifyLocationDisplayNameLabelTextAndTextboxDefaultValue();
		verifyAddressOneLabelTextAndTextboxDefaultValue();
		verifyAddressTwoLabelTextAndTextboxDefaultValue();
		verifyCityLabelTextAndTextboxDefaultValue();
		verifyStateLabelText();
		verifyZipLabelTextAndTextboxDefaultValue();
		verifyTimezoneLabelText();
		verifyLocationIdLabelTextAndTextboxDefaultValue();
	}

	private String xpathRequiredState = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private String xpathRequiredNewClientTimezone = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForState(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectState(String stateName) {
		WebElement state = prepareDynamicXpathForState(xpathRequiredState, stateName);
		CCUtils.verifyAndWaitForElement(state, driver);
		state.click();
		log("Clicked on : " + stateName);
	}

	public void addStateAndTimezone(String stateName, String newTimezone) throws InterruptedException {
		log("click to dropdown of client type");
		dropdownState.click();
		selectState(stateName);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		log("click to dropdown of Timezone");
		dropdownTimezone.click();
		selectTimeZone(newTimezone);
	}

	public void selectTimeZone(String newTimezone) {
		WebElement newClientTimezone = prepareDynamicXpathForState(xpathRequiredNewClientTimezone, newTimezone);
		newClientTimezone.click();
		log("Clicked on : " + newTimezone);
	}

	public void clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(saveLocation, driver);
		saveLocation.click();
		log("Clicked on Save & Add Button");
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel Button");
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on cross Icon");
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void verifyHundredMaximumCharactersReachedErrorMessage(String locationName, String locationDisplayName,
			String address1) {
		log("Enter more than 100 characters in location Name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLocationName, driver);
		textboxLocationName.sendKeys(locationName);
		log("Entered text in location Name field is " + locationName);
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertEquals(errorMessage.getText().trim(), "Maximum characters reached");
		log("Error message displayed for location Name field is " + errorMessage.getText().trim());

		log("Enter more than 100 characters in location Display Name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayName, driver);
		textboxLocationDisplayName.sendKeys(locationDisplayName);
		log("Entered text in location Display Name field is " + locationDisplayName);
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertEquals(errorMessage.getText().trim(), "Maximum characters reached");
		log("Error message displayed for location Display Name field is " + errorMessage.getText().trim());

		log("Enter more than 100 characters in address1 textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxAddressOne, driver);
		textboxAddressOne.clear();
		textboxAddressOne.sendKeys(address1);
		log("Entered text in address1 field is " + address1);
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertEquals(errorMessage.getText().trim(), "Maximum characters reached");
		log("Error message displayed for address1 field is " + errorMessage.getText().trim());
	}

	public void verifyFiftyMaximumCharactersReachedErrorMessage(String city, String zip, String locationId) {
		log("Enter more than 50 characters in city textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxCity, driver);
		textboxCity.sendKeys(city);
		log("Entered text in city field is " + city);
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertEquals(errorMessage.getText().trim(), "Maximum characters reached");
		log("Error message displayed for city field is " + errorMessage.getText().trim());

		log("Enter more than 50 characters in zip textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxZip, driver);
		textboxZip.sendKeys(zip);
		log("Entered text in zip field is " + zip);
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertEquals(errorMessage.getText().trim(), "Maximum characters reached");
		log("Error message displayed for city field is " + errorMessage.getText().trim());

		log("Enter more than 50 characters in locationId textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLocationId, driver);
		textboxLocationId.sendKeys(locationId);
		log("Entered text in locationId field is " + locationId);
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertEquals(errorMessage.getText().trim(), "Maximum characters reached");
		log("Error message displayed for city field is " + errorMessage.getText().trim());
	}

	public void enterLocationName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLocationName, driver);
		textboxLocationName.clear();
		CCUtils.verifyAndWaitForElement(textboxLocationName, driver);
		textboxLocationName.sendKeys(input);
		log("Entered input for Location Name : " + input);
	}

	public void enterLocationDisplayName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayName, driver);
		textboxLocationDisplayName.clear();
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayName, driver);
		textboxLocationDisplayName.sendKeys(input);
		log("Entered input for Location Display Name : " + input);
	}

	public void enterAddressOne(String input) {
		CCUtils.verifyAndWaitForElement(textboxAddressOne, driver);
		textboxAddressOne.clear();
		textboxAddressOne.sendKeys(input);
		log("Entered input for AddressOne : " + input);
	}

	public void enterAddressTwo(String input) {
		CCUtils.verifyAndWaitForElement(textboxAddressTwo, driver);
		textboxAddressTwo.clear();
		textboxAddressTwo.sendKeys(input);
		log("Entered input for AddressTwo : " + input);
	}

	public void enterLocationIdExternal(String input) {
		CCUtils.verifyAndWaitForElement(textboxLocationExternal, driver);
		textboxLocationExternal.clear();
		textboxLocationExternal.sendKeys(input);
		CCUtils.verifyAndWaitForElement(textboxLocationExternal, driver);
		textboxLocationExternal.click();
		log("Entered input for Location External : " + input);
	}

	public void enterZip(String input) {
		CCUtils.verifyAndWaitForElement(textboxZip, driver);
		textboxZip.clear();
		textboxZip.sendKeys(input);
		log("Entered input for Zip : " + input);
	}

	public void enterCity(String input) {
		CCUtils.verifyAndWaitForElement(textboxCity, driver);
		textboxCity.clear();
		textboxCity.sendKeys(input);
		log("Entered input for city : " + input);
	}

	public void selectLocation() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectLocation, driver);
		selectLocation.click();
		log("Clicked on first Location");
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

	public void verifyAddButtonIsEnabled() {
		logStep("Verifying add button is enabled or not");
		CCUtils.verifyAndWaitForElement(addButton, driver);
		CCUtils.assertEquals(addButton.isEnabled(), true);
	}
}
