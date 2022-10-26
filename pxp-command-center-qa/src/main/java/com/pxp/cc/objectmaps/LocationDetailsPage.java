// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.util.CCUtils;

public class LocationDetailsPage extends CCMainPage {

	public LocationDetailsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[text()='Location Details']")
	private WebElement titleLocationDetails;

	@FindBy(xpath = "//div[text()='Resource']")
	private WebElement labelResource;

	@FindBy(xpath = "//div[@role='combobox']")
	private WebElement textboxResource;

	@FindBy(xpath = "(//div[@role='option'])[1]")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(id = "cancel-icon")
	private WebElement buttonCancel;

	@FindBy(xpath = "//span[contains(text(),'clear')]")
	private WebElement crossIcon;

	@FindBy(xpath = "(//td[@role='gridcell'])[1]")
	private WebElement newResource;

	@FindBy(id = "removeResourceBtn")
	private WebElement removeConnection;

	@FindBy(xpath = "//div[contains(text(),'Connected Scheduling Resources')]")
	private WebElement labelSection;

	@FindBy(xpath = "(//button[contains(text(),'+ ADD')])[2]")
	private WebElement btnAdd;

	@FindBy(xpath = "//div[contains(text(),'Resource Name')]")
	private WebElement labelResourceNameColumn;

	@FindBy(xpath = "//div[contains(text(),'Type')]")
	private WebElement labelTypeColumn;

	@FindBy(id = "save-icon")
	private WebElement clickAdd;

	@FindBy(xpath = "//div[@class='title']")
	private WebElement popupTitle;

	@FindBy(id = "cancelBtn")
	private WebElement btnCancel;

	@FindBy(id = "maxdisplayNameENError")
	private WebElement errorMessageDisplayNameEn;

	@FindBy(id = "maxdisplayNameESError")
	private WebElement errorMessageDisplayNameEs;

	@FindBy(id = "maxaddressExtensionError")
	private WebElement errorMessageAddress2;

	@FindBy(xpath = "//input[@formcontrolname='en']")
	private WebElement textboxAddress2;

	@FindBy(id = "maxdirectionENError")
	private WebElement errorMessageDirectionsEn;

	@FindBy(id = "maxdirectionESError")
	private WebElement errorMessageDirectionsEs;

	@FindBy(id = "validMobileNumberError")
	private WebElement errorMessagePhoneNumber;

	@FindBy(xpath = "//span[contains(text(),'Invalid range selected')]")
	private WebElement errorMessageOfficeTime;

	@FindBy(id = "copySettingsBtn")
	private WebElement labelCopySettings;

	@FindBy(id = "saveDetailsBtn")
	private WebElement saveSolutionInfo;

	@FindBy(xpath = "//span[contains(text(),'Address 2')]")
	private WebElement labelAddress2;

	@FindBy(xpath = "//mat-icon[contains(text(),'edit')]")
	private WebElement labelEditButton;

	@FindBy(xpath = "//span[contains(text(),'Location Display Name (EN)')]")
	private WebElement labelLocationDisplayNameEn;

	@FindBy(xpath = "//span[contains(text(),'Location Display Name (ES)')]")
	private WebElement labelLocationDisplayNameEs;

	@FindBy(xpath = "//span[contains(text(),'Office Hours')]")
	private WebElement labelOfficeHours;

	@FindBy(xpath = "//span[contains(text(),'Phone Number')]")
	private WebElement labelPhoneNumber;

	@FindBy(xpath = "//span[contains(text(),'Directions (EN)')]")
	private WebElement labelDirectionsEn;

	@FindBy(xpath = "//span[contains(text(),'Directions (ES)')]")
	private WebElement labelDirectionsEs;

	@FindBy(xpath = "//textarea[@formcontrolname='en']")
	private WebElement textboxDirectionsEn;

	@FindBy(xpath = "//textarea[@formcontrolname='es']")
	private WebElement textboxDirectionsEs;

	@FindBy(id = "editDetailsBtn")
	private WebElement btnEdit;

	@FindBy(xpath = "//input[@formcontrolname='en']")
	private WebElement textboxLocationDisplayNameEn;

	@FindBy(xpath = "//input[@formcontrolname='es']")
	private WebElement textboxLocationDisplayNameEs;

	@FindBy(xpath = "//input[@formcontrolname='addressExtension']")
	private WebElement textboxAddress2Name;

	@FindBy(xpath = "(//input[@type='text'])[6]")
	private WebElement textboxAddress;

	@FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
	private WebElement textboxPhoneNumber;

	@FindBy(xpath = "//input[@formcontrolname='startTime']")
	private WebElement textboxStartOfficeHours;

	@FindBy(xpath = "//input[@formcontrolname='endTime']")
	private WebElement textboxEndOfficeHours;

	@FindBy(xpath = "//span[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(xpath = "//div[contains(text(),'Solutions Display Info')]")
	private WebElement labelSolutionsSection;

	@FindBy(xpath = "//div[contains(text(),'Location Info')]")
	private WebElement labelLocationInfoSection;

	@FindBy(xpath = "//span[contains(text(),'External ID')]")
	private WebElement labelExternalIDSection;

	@FindBy(xpath = "(//span[contains(text(),'Address')])[1]")
	private WebElement labelAddress;

	@FindBy(xpath = "//span[contains(text(),'Command Center ID')]")
	private WebElement labelCommandCenterId;

	@FindBy(xpath = "//span[contains(text(),'Latitude')]")
	private WebElement labelLatitude;

	@FindBy(xpath = "//span[contains(text(),'Timezone')]")
	private WebElement labelTimezone;

	@FindBy(xpath = "//span[contains(text(),'Longitude')]")
	private WebElement labelLongitude;

	@FindBy(xpath = "//span[contains(text(),'Google Maps Link')]")
	private WebElement labelGoogleMapsLink;

	@FindBy(xpath = "//span[contains(text(),'LOCATION')]")
	private WebElement labelLocation;

	@FindBy(xpath = "//div[@role= 'region']")
	private WebElement locationMap;

	@FindBy(xpath = "//mat-icon[@svgicon= 'Location_Icon']")
	private WebElement locationIcon;

	@FindBy(xpath = "//mat-icon[contains(text(),'content_copy')]")
	private WebElement copyIcon;

	@FindBy(xpath = "//mat-icon[contains(text(),'check_circle')]")
	private WebElement messageCopied;

	@FindBy(xpath = "//div[@class='col header-small ellipses']/span")
	private WebElement locationName;

	@FindBy(xpath = "(//div[@class='row'])[4]")
	private WebElement address;

	@FindBy(xpath = "(//div[@class='row'])[8]")
	private WebElement timeZone;

	@FindBy(xpath = "//div[@role='tab']//div")
	private List<WebElement> listOfSystemTabs;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button/following-sibling::p")
	private List<WebElement> listOfEntTabs;

	@FindBy(id = "treeNodeName")
	private List<WebElement> listOfPracTabs;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button//span//mat-icon")
	private WebElement arrowEntPractice;

	@FindBy(xpath = "//mat-icon[@title='Back']")
	private WebElement backIcon;

	public String xpathRequiredClientType(String practiceName) {
		return "//mat-tree-node//p[contains(text(),'" + practiceName
				+ "')]//following::div[1]//label[@class='switch']//span";
	}

	@FindBy(xpath = "(//input[@id='directorySearchInput'])[2]")
	private WebElement textboxSearchResources;

	@FindBy(xpath = "(//span[@id='directoryToggleIcon'])[2]")
	private WebElement buttonSearchResources;

	@FindBy(xpath = "//span[contains(text(),'CLEAR')]")
	private WebElement clearButton;

	@FindBy(xpath = "(//div[contains(text(),'No data to display')])[2]")
	private WebElement noResultFoundInResources;

	@FindBy(xpath = "(//td[@role='gridcell'])[1]")
	private WebElement tableResourceNameInSearchResult;

	@FindBy(xpath = "(//span[contains(text(),'10')])[2]")
	private WebElement defaultItemsPerPage;

	@FindBy(xpath = "(//div[@class='mat-paginator-range-label'])[2]")
	private WebElement displayPagination;

	@FindBy(xpath = "(//button[@aria-label='First page'])[2]")
	private WebElement buttonFirstPage;

	@FindBy(xpath = "(//button[@aria-label='Next page'])[2]")
	private WebElement buttonNextPage;

	@FindBy(xpath = "(//button[@aria-label='Last page'])[2]")
	private WebElement buttonLastPage;

	@FindBy(xpath = "(//button[@aria-label='Previous page'])[2]")
	private WebElement buttonPreviousPage;

	@FindBy(xpath = "(//input[@id='directorySearchInput'])[2]")
	private WebElement searchTextBox;

	@FindBy(xpath = "(//span[@id='directoryToggleIcon'])[2]")
	private WebElement searchButton;

	@FindBy(xpath = "//div[contains(text(),'Connected Providers')]")
	private WebElement labelProvidersSection;

	@FindBy(xpath = "//div[contains(text(),'Provider Name')]")
	private WebElement labelProviderNameColumn;

	@FindBy(xpath = "(//button[contains(text(),'+ ADD')])[1]")
	private WebElement buttonAdd;

	@FindBy(xpath = "//div[text()='Provider']")
	private WebElement labelProvider;

	@FindBy(xpath = "//input[@type='text']")
	private WebElement textboxProvider;

	@FindBy(xpath = "(//div[contains(text(),'No data to display')])[1]")
	private WebElement noResultFoundInProviders;

	@FindBy(xpath = "(//button[@aria-label='First page'])[1]")
	private WebElement buttonProvidersFirstPage;

	@FindBy(xpath = "(//button[@aria-label='Next page'])[2]")
	private WebElement buttonProvidersNextPage;

	@FindBy(xpath = "(//button[@aria-label='Last page'])[3]")
	private WebElement buttonProvidersLastPage;

	@FindBy(xpath = "(//button[@aria-label='Previous page'])[4]")
	private WebElement buttonProvidersPreviousPage;

	@FindBy(xpath = "//label[@id='resourceName']")
	private WebElement clickOnFirstOption;

	@FindBy(xpath = "(//td[@role='gridcell'])[1]")
	private WebElement addedProvider;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement providerInList;

	@FindBy(xpath = "(//mat-icon[@mattooltip='Remove Connection'])[1]")
	private WebElement btnRemoveConnection;

	@FindBy(xpath = "(//tbody[@role='rowgroup']//tr[@role='row']//td[1])[1]")
	private List<WebElement> listOfProviderName;

	@FindBy(xpath = "(//input[@id='directorySearchInput'])[1]")
	private WebElement textboxSearchProviders;

	@FindBy(xpath = "(//span[@id='directoryToggleIcon'])[1]")
	private WebElement buttonSearchProviders;

	@FindBy(id = "treeNodeName")
	private WebElement nodePractice;

	@FindBy(xpath = "//span[contains(text(),'Successfully added provider')]")
	private WebElement successToasterMessage;

	public void verifyLocationDetailsTitle() {
		CCUtils.verifyAndWaitForElement(titleLocationDetails, driver);
		log("Verifying Location Details Title");
		CCUtils.assertEquals(titleLocationDetails.getText().trim(), "Location Details");
	}

	public void verifyConnectedSchedulingResourcesSection() throws InterruptedException, AWTException {
		log("Verifying Connected Scheduling Resources Section");
		verifyConnectedSchedulingResourcesLabel();
		verifyResourceNameColumnLabel();
		verifyTypeColumnLabel();
		verifyAddButtonLabel();
	}

	public void verifyConnectedSchedulingResourcesLabel() {
		CCUtils.verifyAndWaitForElement(labelSection, driver);
		log("Verifying Connected Scheduling Resources Label");
		CCUtils.assertEquals(labelSection.getText().trim(), "Connected Scheduling Resources");
	}

	public void verifyResourceNameColumnLabel() {
		CCUtils.verifyAndWaitForElement(labelResourceNameColumn, driver);
		log("Verifying Resource Name Column Label");
		CCUtils.assertEquals(labelResourceNameColumn.getText().trim(), "Resource Name");
	}

	public void verifyTypeColumnLabel() {
		CCUtils.verifyAndWaitForElement(labelTypeColumn, driver);
		log("Verifying Type Column Label");
		CCUtils.assertEquals(labelTypeColumn.getText().trim(), "Type");
	}

	public void verifyAddButtonLabel() {
		CCUtils.verifyAndWaitForElement(btnAdd, driver);
		log("Verifying Add Button Label");
		CCUtils.assertEquals(btnAdd.getText().trim(), "+ ADD");
	}

	public void clickOnAddButton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(btnAdd, driver);
		btnAdd.click();
	}

	public void clickOnAddToSave() throws InterruptedException {
		javascriptClick(clickAdd);
		log("Clicked on Add button");
	}

	public void VerifyAddToSaveIsDisplayed() throws InterruptedException {
		CCUtils.assertEquals(clickAdd.isDisplayed(), true);
		log("Add button is displayed");
	}

	public void verifyAddResourcePopupTitle() {
		CCUtils.verifyAndWaitForElement(popupTitle, driver);
		log("Verifying Add Resource Popup title");
		CCUtils.assertEquals(popupTitle.getText().trim(), "Add Resource(s)");
	}

	public void verifyResourceNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelResource, driver);
		log("Verifying Resource name label text");
		CCUtils.assertEquals(labelResource.getText().trim(), "Resource");
		log("Resource name label text is " + labelResource.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxResource, driver);
		log("Verifying Input text here placeholder is displayed for Resource name textbox field");
		CCUtils.assertEquals(textboxResource.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for Resource name textbox field is " + textboxResource.getAttribute("placeholder").trim());
	}

	public void verifyAddButtonIsDisabled() {
		CCUtils.verifyAndWaitForElement(clickAdd, driver);
		CCUtils.assertEquals(clickAdd.isEnabled(), false);
		log("Add button is disabled");
	}

	public void enterResourceName(String input) {
		CCUtils.verifyAndWaitForElement(textboxResource, driver);
		CCUtils.assertEquals(textboxResource.isEnabled(), true);
		textboxResource.click();
		log("Entered input for Resource Name : " + input);
	}

	public void clickFirstInAutoSuggestion() {
		CCUtils.verifyAndWaitForElement(selectFirstInAutoSuggestion, driver);
		if (selectFirstInAutoSuggestion.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstInAutoSuggestion);

		} else {
			log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present");
		}
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

	public void verifyNewlyAddedConnectedSchedulingResource() {
		CCUtils.verifyAndWaitForElement(newResource, driver);
		log("Verifying newly added Connected Scheduling Resource in list");
		CCUtils.assertEquals(newResource.isDisplayed(), true);
	}

	public void clickOnRemoveConnectionButton() {
		CCUtils.verifyAndWaitForElement(removeConnection, driver);
		removeConnection.click();
		log("Clicked on Remove Connection Button");
	}

	public void verifySolutionsDisplayInfoSection() throws InterruptedException, AWTException {
		log("Verifying Solutions Display Info Section");
		verifySolutionsDisplayInfoSectionLabel();
		verifyEditButtonLabel();
		verifyCopySettingsButtonLabel();
		verifyLocationDisplayNameEnLabel();
		verifyLocationDisplayNameEsLabel();
		verifyPhoneNumberLabel();
		verifyAddress2Label();
		verifyDirectionsEnNameLabelTextAndTextboxDefaultValue();
		verifyDirectionsEsNameLabelTextAndTextboxDefaultValue();
		verifyOfficeHoursLabel();
	}

	public void verifyCopySettingsButtonLabel() {
		CCUtils.verifyAndWaitForElement(labelCopySettings, driver);
		log("Verifying Copy Settings Button Label");
		CCUtils.assertEquals(labelCopySettings.getText().trim(), "COPY SETTINGS");
	}

	public void verifyEditButtonLabel() {
		CCUtils.verifyAndWaitForElement(labelEditButton, driver);
		log("Verifying Edit Button Label");
		CCUtils.assertEquals(labelEditButton.getText().trim(), "edit");
	}

	public void verifySolutionsDisplayInfoSectionLabel() {
		CCUtils.scrollPageViaElement(driver, labelSolutionsSection);
		CCUtils.verifyAndWaitForElement(labelSolutionsSection, driver);
		log("Verifying Solutions Display Info Label");
		CCUtils.assertEquals(labelSolutionsSection.getText().trim(), "Solutions Display Info");
	}

	public void verifyLocationDisplayNameEnLabel() {
		CCUtils.verifyAndWaitForElement(labelLocationDisplayNameEn, driver);
		log("Verifying Location Display Name (EN) Label");
		CCUtils.assertEquals(labelLocationDisplayNameEn.getText().trim(), "Location Display Name (EN)");
	}

	public void verifyLocationDisplayNameEsLabel() {
		CCUtils.verifyAndWaitForElement(labelLocationDisplayNameEs, driver);
		log("Verifying Location Display Name (ES) Label");
		CCUtils.assertEquals(labelLocationDisplayNameEs.getText().trim(), "Location Display Name (ES)");
	}

	public void verifyOfficeHoursLabel() {
		CCUtils.verifyAndWaitForElement(labelOfficeHours, driver);
		log("Verifying Office Hours Label");
		CCUtils.assertEquals(labelOfficeHours.getText().trim(), "Office Hours");
	}

	public void verifyPhoneNumberLabel() {
		CCUtils.verifyAndWaitForElement(labelPhoneNumber, driver);
		log("Verifying Phone Number Label");
		CCUtils.assertEquals(labelPhoneNumber.getText().trim(), "Phone Number");
	}

	public void verifyAddress2Label() {
		CCUtils.verifyAndWaitForElement(labelAddress2, driver);
		log("Verifying Address 2 (suite, floor etc) Label");
		CCUtils.assertEquals(labelAddress2.getText().trim(), "Address 2 (suite, floor etc)");
	}

	public void verifyDirectionsEnNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelDirectionsEn, driver);
		log("Verifying Directions En label text");
		CCUtils.assertEquals(labelDirectionsEn.getText().trim(), "Directions (EN)");
		log("Directions En label text is " + labelDirectionsEn.getText().trim());
	}

	public void verifyDirectionsEsNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelDirectionsEs, driver);
		log("Verifying Directions Es label text");
		CCUtils.assertEquals(labelDirectionsEs.getText().trim(), "Directions (ES)");
		log("Directions Es label text is " + labelDirectionsEs.getText().trim());
	}

	public void clickOnSaveButton() {
		CCUtils.verifyAndWaitForElement(saveSolutionInfo, driver);
		saveSolutionInfo.click();
		log("Clicked on Save Button");
	}

	public void clickOnEditButton() {
		CCUtils.verifyAndWaitForElement(btnEdit, driver);
		btnEdit.click();
		log("Clicked on Edit Button");
	}

	public void enterLocationDisplayNameEn(String input) {
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayNameEn, driver);
		textboxLocationDisplayNameEn.clear();
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayNameEn, driver);
		textboxLocationDisplayNameEn.sendKeys(input);
		log("Entered input for Location Display Name En : " + input);
	}

	public void enterLocationDisplayNameEs(String input) {
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayNameEs, driver);
		textboxLocationDisplayNameEs.clear();
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayNameEs, driver);
		textboxLocationDisplayNameEs.sendKeys(input);
		log("Entered input for Location Display Name Es : " + input);
	}

	public void enterAddress2(String input) {
		CCUtils.verifyAndWaitForElement(textboxAddress2Name, driver);
		textboxAddress2Name.clear();
		textboxAddress2Name.sendKeys(input);
		log("Entered input for Address2 name : " + input);
	}

	public void enterPhoneNumber(String input) {
		CCUtils.verifyAndWaitForElement(textboxPhoneNumber, driver);
		textboxPhoneNumber.clear();
		textboxPhoneNumber.sendKeys(input);
		log("Entered input for Phone number : " + input);
	}

	public void enterDirectionsEn(String input) {
		CCUtils.verifyAndWaitForElement(textboxDirectionsEn, driver);
		textboxDirectionsEn.clear();
		CCUtils.verifyAndWaitForElement(textboxDirectionsEn, driver);
		textboxDirectionsEn.sendKeys(input);
		log("Entered input for Directions EN : " + input);
	}

	public void enterDirectionsEs(String input) {
		CCUtils.verifyAndWaitForElement(textboxDirectionsEs, driver);
		textboxDirectionsEs.clear();
		CCUtils.verifyAndWaitForElement(textboxDirectionsEs, driver);
		textboxDirectionsEs.sendKeys(input);
		log("Entered input for Directions ES : " + input);
	}

	public void enterStartOfficeHours(String input) {
		CCUtils.verifyAndWaitForElement(textboxStartOfficeHours, driver);
		textboxStartOfficeHours.clear();
		CCUtils.verifyAndWaitForElement(textboxStartOfficeHours, driver);
		textboxStartOfficeHours.sendKeys(input);
		log("Entered input for Start Office Hours : " + input);
	}

	public void enterEndOfficeHours(String input) {
		CCUtils.verifyAndWaitForElement(textboxEndOfficeHours, driver);
		textboxEndOfficeHours.clear();
		CCUtils.verifyAndWaitForElement(textboxEndOfficeHours, driver);
		textboxEndOfficeHours.sendKeys(input);
		log("Entered input for End Office Hours : " + input);
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		CCUtils.assertEquals(toasterMessage.isDisplayed(), true);
	}

	public void clickOnCancel() {
		CCUtils.verifyAndWaitForElement(btnCancel, driver);
		btnCancel.click();
		log("Clicked on Cancel Button");
	}

	public void verifyMaximumCharactersReachedErrorMessage(String LocationDisplayNameEn, String LocationDisplayNameEs,
			String address2, String directionsEn, String directionsEs) {
		log("Enter more than 100 characters in LocationDisplayNameEn textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayNameEn, driver);
		textboxLocationDisplayNameEn.sendKeys(LocationDisplayNameEn);
		log("Entered text in LocationDisplayNameEn field is " + LocationDisplayNameEn);
		CCUtils.verifyAndWaitForElement(errorMessageDisplayNameEn, driver);
		CCUtils.assertEquals(errorMessageDisplayNameEn.getText().trim(), "Maximum characters reached");
		log("Error message displayed for LocationDisplayNameEn field is " + errorMessageDisplayNameEn.getText().trim());
		log("Enter more than 100 characters in LocationDisplayNameEs textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxLocationDisplayNameEs, driver);
		textboxLocationDisplayNameEs.sendKeys(LocationDisplayNameEs);
		log("Entered text in LocationDisplayNameEs field is " + LocationDisplayNameEs);
		CCUtils.verifyAndWaitForElement(errorMessageDisplayNameEs, driver);
		CCUtils.assertEquals(errorMessageDisplayNameEs.getText().trim(), "Maximum characters reached");
		log("Error message displayed for LocationDisplayNameEs field is " + errorMessageDisplayNameEs.getText().trim());
		log("Enter more than 100 characters in Address2 textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxAddress, driver);
		textboxAddress.clear();
		textboxAddress.sendKeys(address2);
		log("Entered input for Address2 name : " + address2);
		CCUtils.verifyAndWaitForElement(errorMessageAddress2, driver);
		CCUtils.assertEquals(errorMessageAddress2.getText().trim(), "Maximum characters reached");
		log("Error message displayed for Address2 field is " + errorMessageAddress2.getText().trim());
		log("Enter more than 100 characters in DirectionsEn textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxDirectionsEn, driver);
		textboxDirectionsEn.clear();
		textboxDirectionsEn.sendKeys(directionsEn);
		log("Entered text in DirectionsEn field is " + directionsEn);
		CCUtils.verifyAndWaitForElement(errorMessageDirectionsEn, driver);
		CCUtils.assertEquals(errorMessageDirectionsEn.getText().trim(), "Maximum characters reached");
		log("Error message displayed for DirectionsEn field is " + errorMessageDirectionsEn.getText().trim());
		log("Enter more than 100 characters in DirectionsEs textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxDirectionsEs, driver);
		textboxDirectionsEs.clear();
		textboxDirectionsEs.sendKeys(directionsEs);
		log("Entered text in DirectionsEs field is " + directionsEs);
		CCUtils.verifyAndWaitForElement(errorMessageDirectionsEs, driver);
		CCUtils.assertEquals(errorMessageDirectionsEs.getText().trim(), "Maximum characters reached");
		log("Error message displayed for DirectionsEs field is " + errorMessageDirectionsEs.getText().trim());
	}

	public void verifyErrorMessageForInvalidPhoneNumber(String phoneNumber) {
		log("Enter invalid phone number in textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxPhoneNumber, driver);
		textboxPhoneNumber.clear();
		textboxPhoneNumber.sendKeys(phoneNumber);
		log("Entered text in phone number field is " + phoneNumber);
		CCUtils.verifyAndWaitForElement(errorMessagePhoneNumber, driver);
		CCUtils.assertEquals(errorMessagePhoneNumber.getText().trim(), "Please enter valid phone number");
		log("Error message displayed for phone number field is " + errorMessagePhoneNumber.getText().trim());
	}

	public void verifyErrorMessageForInvalidOfficeHours(String startTime, String endTime) {
		CCUtils.verifyAndWaitForElement(textboxStartOfficeHours, driver);
		textboxStartOfficeHours.clear();
		CCUtils.verifyAndWaitForElement(textboxStartOfficeHours, driver);
		textboxStartOfficeHours.sendKeys(startTime);
		log("Entered input for Start Office Hours : " + startTime);
		CCUtils.verifyAndWaitForElement(textboxEndOfficeHours, driver);
		textboxEndOfficeHours.clear();
		CCUtils.verifyAndWaitForElement(textboxEndOfficeHours, driver);
		textboxEndOfficeHours.sendKeys(endTime);
		log("Entered input for End Office Hours : " + endTime);
		CCUtils.verifyAndWaitForElement(errorMessageOfficeTime, driver);
		CCUtils.assertEquals(errorMessageOfficeTime.getText().trim(),
				"Invalid range selected. Start time cannot be greater than End time");
	}

	public void verifySaveButtonIsDisabled() {
		CCUtils.verifyAndWaitForElement(saveSolutionInfo, driver);
		CCUtils.assertEquals(saveSolutionInfo.isEnabled(), false);
	}

	public void verifyLocationInfoSection() throws InterruptedException, AWTException {
		log("Verifying Location Info Section");
		verifyLocationLabel();
		verifyocationIcon();
		verifyLocationMap();
		verifyLocationInfoSectionLabel();
		verifyExternalIdLabel();
		verifyLocationAddressLabel();
		verifyLocationCommandCenterIdLabel();
		verifyLatitudeLabel();
		verifyTimezoneLabel();
		verifyLongitudeLabel();
		verifyGoogleMapsLink();
	}

	public void verifyLocationInfoSectionLabel() {
		CCUtils.verifyAndWaitForElement(labelLocationInfoSection, driver);
		log("Verifying Location Info Label");
		CCUtils.assertEquals(labelLocationInfoSection.getText().trim(), "Location Info");
	}

	public void verifyExternalIdLabel() {
		CCUtils.verifyAndWaitForElement(labelExternalIDSection, driver);
		log("Verifying Location External ID Label");
		CCUtils.assertEquals(labelExternalIDSection.getText().trim(), "External ID");
	}

	public void verifyLocationAddressLabel() {
		CCUtils.verifyAndWaitForElement(labelAddress, driver);
		log("Verifying Location Address Label");
		CCUtils.assertEquals(labelAddress.getText().trim(), "Address");
	}

	public void verifyLocationCommandCenterIdLabel() {
		CCUtils.verifyAndWaitForElement(labelCommandCenterId, driver);
		log("Verifying Command Center Id Label");
		CCUtils.assertEquals(labelCommandCenterId.getText().trim(), "Command Center ID");
	}

	public void verifyLatitudeLabel() {
		CCUtils.verifyAndWaitForElement(labelLatitude, driver);
		log("Verifying Latitude Label");
		CCUtils.assertEquals(labelLatitude.getText().trim(), "Latitude");
	}

	public void verifyTimezoneLabel() {
		CCUtils.verifyAndWaitForElement(labelTimezone, driver);
		log("Verifying Timezone Label");
		CCUtils.assertEquals(labelTimezone.getText().trim(), "Timezone");
	}

	public void verifyLongitudeLabel() {
		CCUtils.verifyAndWaitForElement(labelLongitude, driver);
		log("Verifying Longitude Label");
		CCUtils.assertEquals(labelLongitude.getText().trim(), "Longitude");
	}

	public void verifyGoogleMapsLink() {
		CCUtils.verifyAndWaitForElement(labelGoogleMapsLink, driver);
		log("Verifying Google Maps Link Label");
		CCUtils.assertEquals(labelGoogleMapsLink.getText().trim(), "Google Maps Link");
	}

	public void verifyLocationLabel() {
		CCUtils.verifyAndWaitForElement(labelLocation, driver);
		log("Verifying Location Label");
		CCUtils.assertEquals(labelLocation.getText().trim(), "LOCATION");
	}

	public void verifyLocationMap() {
		log("Verifying Location map");
		CCUtils.verifyAndWaitForElement(locationMap, driver);
		CCUtils.assertTrue(locationMap.isDisplayed(), "Location map is displayed");
	}

	public void verifyocationIcon() {
		log("Verifying Location icon");
		CCUtils.verifyAndWaitForElement(locationIcon, driver);
		CCUtils.assertTrue(locationIcon.isDisplayed(), "Location icon is displayed");
	}

	public void verifyCopiedText() {
		log("Verifying Copy icon");
		CCUtils.verifyAndWaitForElement(copyIcon, driver);
		CCUtils.assertTrue(copyIcon.isDisplayed(), "copy icon is displayed");
		javascriptClick(copyIcon);
		log("Clicked on Copy icon");
		log("Verifying Copied message");
		CCUtils.assertEquals(messageCopied.isDisplayed(), true);
	}

	public void verifyLocationNameAddressAndTimezone() {
		log("Verifying location Name");
		CCUtils.verifyAndWaitForElement(locationName, driver);
		CCUtils.assertTrue(locationName.isDisplayed(), "location Name is displayed");
		log("Verifying address");
		CCUtils.verifyAndWaitForElement(address, driver);
		CCUtils.assertTrue(address.isDisplayed(), "address is displayed");
		log("Verifying timeZone");
		CCUtils.verifyAndWaitForElement(timeZone, driver);
		CCUtils.assertTrue(timeZone.isDisplayed(), "timeZone is displayed");
	}

	public void clickToBackButton() {
		CCUtils.verifyAndWaitForElement(backIcon, driver);
		backIcon.click();
	}

	public void clickToSytemTab(String systemName) {
		for (WebElement element : listOfSystemTabs) {
			if (element.getText().toString().contains(systemName)) {
				CCUtils.verifyAndWaitForElement(element, driver);
				javascriptClick(element);
				log("clicked to system : " + systemName + " tab in location details page");
			}
		}
	}

	public void clickToEnterpriseTabPracticeAndToggleOn(String enterpriseName, String practiceName)
			throws AWTException {
		for (WebElement element : listOfEntTabs) {
			CCUtils.verifyAndWaitForElement(element, driver);
			CCUtils.scrollPageToEnd();
			if (element.getText().toString().trim().equals(enterpriseName.trim())) {
				if (arrowEntPractice.getText().contains("chevron_right")) {
					arrowEntPractice.click();
					log("clicked to arrow practice : " + arrowEntPractice.getText() + " tab");
				} else
					log("enterprise " + element.getText() + " is already expanded");
				for (WebElement ele : listOfPracTabs) {
					log("get the class of ele: " + ele.getAttribute("class").toString());
					if (ele.getText().toString().trim().equals(practiceName.trim())
							&& ele.getAttribute("class").toString().trim().equals("treenode no-event")) {
						WebElement toggleButtonSpecific = driver
								.findElement(By.xpath(xpathRequiredClientType(practiceName)));
						CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
						toggleButtonSpecific.click();
						log("clicked to toggle ON button of practice : " + ele.getText() + " tab");
					} else if (ele.getText().toString().contains(practiceName)
							&& ele.getAttribute("class").toString().contains("treenode cursor")) {
						log("toggle for this practice :" + ele.getText() + "is already ON");
					}
				}
			}
		}
	}

	public void clickToEnterpriseTabPracticeAndToggleOff(String enterpriseName, String practiceName)
			throws AWTException {
		log("entered to ent prac tab");
		for (WebElement element : listOfEntTabs) {
			log("entered to ent tab");
			CCUtils.verifyAndWaitForElement(element, driver);
			CCUtils.scrollPageToEnd();
			if (element.getText().toString().contains(enterpriseName)) {
				log("if ent tab contains desired ent");
				log("get the text of arrow : " + arrowEntPractice.getText());
				if (arrowEntPractice.getText().equals("chevron_right")) {
					arrowEntPractice.click();
					log("clicked to arrow practice : " + arrowEntPractice.getText() + " tab");
				} else
					log("enterprise " + element.getText() + " is already expanded");
				for (WebElement ele : listOfPracTabs) {
					log("entered to prac tab");
					log("get the class of ele: " + ele.getAttribute("class").toString());
					if (ele.getText().toString().contains(practiceName)
							&& ele.getAttribute("class").toString().contains("treenode cursor")) {
						WebElement toggleButtonSpecific = driver
								.findElement(By.xpath(xpathRequiredClientType(practiceName)));
						CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
						toggleButtonSpecific.click();
						log("clicked to toggle OFF button of practice : " + ele.getText() + " tab");
					} else if (ele.getText().toString().contains(practiceName)
							&& ele.getAttribute("class").toString().contains("treenode no-event")) {
						log("toggle for this practice :" + ele.getText() + "is already OFF");
					}
				}
			}
		}
	}

	public void clickToPracticeTabAndToggleOn(String practiceName) throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForListOfElement(listOfPracTabs, driver);
		for (WebElement ele : listOfPracTabs) {
			log(ele.getText().toString());
			if (ele.getText().toString().trim().equals(practiceName)) {
				log("actual practice : " + ele.getText().toString() + " is equal to " + practiceName);
				if (ele.getAttribute("class").toString().trim().equals("treenode no-event")) {
					WebElement toggleButtonSpecific = driver
							.findElement(By.xpath(xpathRequiredClientType(practiceName)));
					CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
					toggleButtonSpecific.click();
					log("clicked on toggle button");
					log("Toggle ON button of practice : " + ele.getText() + " tab");
					CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
				}
			} else
				log("either practice " + ele.getText() + " does not match with " + practiceName
						+ " or toggle button for " + ele.getText() + " is already ON");
		}
	}

	public void clickToPracticeTabAndToggleOff(String practiceName) throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForListOfElement(listOfPracTabs, driver);
		for (WebElement ele : listOfPracTabs) {
			if ((ele.getText().toString().equals(practiceName))
					&& (ele.getAttribute("class").toString().equals("treenode cursor"))) {
				WebElement toggleButtonSpecific = driver.findElement(By.xpath(xpathRequiredClientType(practiceName)));
				CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
				toggleButtonSpecific.click();
				log("clicked on toggle button");
				log("clicked to toggle OFF button of practice : " + ele.getText() + " tab");
				CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
			} else
				log("toggle button is already OFF for practice " + ele.getText());
		}
	}

	public void verifySearchButtonFunctionality(String searchResource) {
		CCUtils.verifyAndWaitForElement(textboxSearchResources, driver);
		log("Default text in Resources search is : " + textboxSearchResources.getAttribute("placeholder"));
		CCUtils.assertTrue(textboxSearchResources.getAttribute("placeholder").contains("Search"), "");
		CCUtils.assertEquals(buttonSearchResources.getText(), "SEARCH");
		log("Default text present in Search button in search textbox : " + buttonSearchResources.getText());
		searchResource(searchResource);
		clickSearchButton();
		CCUtils.assertEquals(buttonSearchResources.getText(), "CLEAR");
		log("Text present in Resources search button after it is clicked is : " + buttonSearchResources.getText());
	}

	public void verifyClearButtonFunctionality(String searchResource) {
		CCUtils.verifyAndWaitForElement(textboxSearchResources, driver);
		log("Default text in Resources search is : " + textboxSearchResources.getAttribute("placeholder"));
		CCUtils.assertTrue(textboxSearchResources.getAttribute("placeholder").contains("Search"), "");
		CCUtils.assertEquals(buttonSearchResources.getText(), "SEARCH");
		log("Default text present in Search button in search textbox : " + buttonSearchResources.getText());
		searchResource(searchResource);
		clickSearchButton();
		CCUtils.assertEquals(buttonSearchResources.getText(), "CLEAR");
		log("Text present in Resources search button after it is clicked is : " + buttonSearchResources.getText());
		clickClearButton();
		log("Clicked on CLEAR button");
	}

	public void searchResource(String searchResourceName) {
		CCUtils.verifyAndWaitForElement(textboxSearchResources, driver);
		textboxSearchResources.sendKeys(searchResourceName);
		log("Entered text in Resources Search is : " + searchResourceName);
	}

	public void clickSearchButton() {
		CCUtils.verifyAndWaitForElement(buttonSearchResources, driver);
		javascriptClick(buttonSearchResources);
		log("Clicked on Search button");
	}

	public void clickClearButton() {
		CCUtils.verifyAndWaitForElement(clearButton, driver);
		javascriptClick(clearButton);
		log("Clicked on Clear button");
	}

	public boolean testToCheckNoResultsFound() {
		CCUtils.verifyAndWaitForElement(noResultFoundInResources, driver);
		if (noResultFoundInResources.getText().equals("No data to display")) {
			return true;
		}
		log("Search result found " + noResultFoundInResources.getText());
		return false;
	}

	public void verifySearchResult(String searchResourceName) {
		log("Verifying searched Connected Scheduling Resource in list");
		CCUtils.verifyAndWaitForElement(tableResourceNameInSearchResult, driver);
		CCUtils.assertEquals(newResource.isDisplayed(), true);
	}

	public void verifySearchTextBoxAndPlaceholderIsDisplayed() {
		CCUtils.verifyAndWaitForElement(searchTextBox, driver);
		log("Verifying search textbox is present on locations deatils page");
		if (searchTextBox.isDisplayed() == true) {
			log("Verifying search textbox is enabled");
			CCUtils.assertTrue(searchTextBox.isEnabled(), "search button is disabled");
			CCUtils.assertEquals(searchTextBox.getAttribute("placeholder"), "Search");
			log("Verified search text and it's placeholder");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public void verifySearchButtonIsDisplayed() {
		log("Verifying search button is displayed on locations search page");
		CCUtils.verifyAndWaitForElement(searchTextBox, driver);
		searchTextBox.sendKeys("ResourcesSearchInput");
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		if (searchButton.isDisplayed() == true) {
			log("Verifying search button is enabled");
			CCUtils.assertTrue(searchButton.isEnabled(), "search button is disabled");
			log("Verifying search button text");
			CCUtils.assertEquals(searchButton.getText().trim(), "SEARCH");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public String getDefaultItemsPerPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		highlightElement(defaultItemsPerPage);
		return defaultItemsPerPage.getText().toString();
	}

	public String getDefaultPagination() {
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("of", 0);
		String data = null;
		for (String w : words) {
			data = w.trim();
			log("Print the value of default Pagination : " + w.trim());
			break;
		}
		return data.trim();
	}

	public void isFirstPageButtonDisabled() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		CCUtils.assertEquals(buttonFirstPage.isEnabled(), false);
	}

	public void isPreviousPageButtonDisabled() {
		CCUtils.verifyAndWaitForElement(buttonPreviousPage, driver);
		CCUtils.assertEquals(buttonPreviousPage.isEnabled(), false);
	}

	public void isNextPageButtonDisabled() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		CCUtils.assertEquals(buttonNextPage.isEnabled(), false);
	}

	public void isLastPageButtonDisabled() {
		CCUtils.verifyAndWaitForElement(buttonLastPage, driver);
		CCUtils.assertEquals(buttonLastPage.isEnabled(), false);
	}

	public void clickNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		log("click to Next Page button");
		buttonNextPage.click();
	}

	public void clickLastPageButton() {
		CCUtils.verifyAndWaitForElement(buttonLastPage, driver);
		log("click to Last Page button");
		buttonLastPage.click();
	}

	public void clickPreviousPageButton() {
		CCUtils.verifyAndWaitForElement(buttonPreviousPage, driver);
		log("click to Previous Page button");
		buttonPreviousPage.click();
	}

	public void clickFirstPageButton() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		log("click to First Page button");
		buttonFirstPage.click();
	}

	public void verifyConnectedProvidersSection() throws InterruptedException, AWTException {
		log("Verifying Connected Providers Section");
		verifyConnectedProvidersLabel();
		verifyProviderNameColumnLabel();
		verifyAddProviderButtonLabel();
	}

	public void verifyConnectedProvidersLabel() {
		CCUtils.verifyAndWaitForElement(labelProvidersSection, driver);
		log("Verifying Connected Providers Label");
		CCUtils.assertEquals(labelProvidersSection.getText().trim(), "Connected Providers");
	}

	public void verifyProviderNameColumnLabel() {
		CCUtils.verifyAndWaitForElement(labelProviderNameColumn, driver);
		log("Verifying Provider Name Column Label");
		CCUtils.assertEquals(labelProviderNameColumn.getText().trim(), "Provider Name");
	}

	public void verifyAddProviderButtonLabel() {
		CCUtils.verifyAndWaitForElement(buttonAdd, driver);
		log("Verifying Add Button Label");
		CCUtils.assertEquals(buttonAdd.getText().trim(), "+ ADD");
	}

	public void clickOnAdd() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonAdd, driver);
		buttonAdd.click();
	}

	public void verifyAddProviderPopupTitle() {
		CCUtils.verifyAndWaitForElement(popupTitle, driver);
		log("Verifying Add Resource Popup title");
		CCUtils.assertEquals(popupTitle.getText().trim(), "Add Provider(s)");
	}

	public void verifyProviderNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelProvider, driver);
		log("Verifying Provider name label text");
		CCUtils.assertEquals(labelProvider.getText().trim(), "Provider");
		log("Provider name label text is " + labelProvider.getText().trim());
	}

	public void enterProviderName(String input) {
		CCUtils.verifyAndWaitForElement(textboxProvider, driver);
		CCUtils.assertEquals(textboxProvider.isEnabled(), true);
		textboxProvider.sendKeys(input);
		selectFirstOption();
		log("Entered input for Provider Name : " + input);
	}

	public void selectFirstOption() {
		javascriptClick(clickOnFirstOption);
		log("Clicked on first option");
	}

	public void verifyRemoveConnectionButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(btnRemoveConnection, driver);
		CCUtils.assertEquals(btnRemoveConnection.isDisplayed(), true);
	}

	public void clickOnRemoveConnection() {
		CCUtils.verifyAndWaitForElement(btnRemoveConnection, driver);
		btnRemoveConnection.click();
		log("Clicked on Remove Connection Button");
	}

	public void enterSearchText(String firstName) {
		CCUtils.verifyAndWaitForElement(textboxSearchProviders, driver);
		textboxSearchProviders.sendKeys(firstName);
		log("Entered text in search :" + firstName);
	}

	public void clickOnPracticeNode() {
		CCUtils.verifyAndWaitForElement(nodePractice, driver);
		nodePractice.click();
		log("Clicked on practice node");
	}

	public void verifySuccessToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(successToasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (successToasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(successToasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void enterNonExistingProviderName(String input) {
		CCUtils.verifyAndWaitForElement(textboxProvider, driver);
		CCUtils.assertEquals(textboxProvider.isEnabled(), true);
		textboxProvider.sendKeys(input);
		log("Entered text in search :" + input);
	}

	public void verifyConnectedProvidersSearchButtonFunctionality(String searchProviders) {
		CCUtils.verifyAndWaitForElement(textboxSearchProviders, driver);
		log("Default text in Providers search is : " + textboxSearchProviders.getAttribute("placeholder"));
		CCUtils.assertTrue(textboxSearchProviders.getAttribute("placeholder").contains("Search"), "");
		CCUtils.assertEquals(buttonSearchProviders.getText(), "SEARCH");
		log("Default text present in Search button in search textbox : " + buttonSearchProviders.getText());
		searchProviders(searchProviders);
		clickProvidersSearchButton();
	}

	public void searchProviders(String searchProvidersName) {
		CCUtils.verifyAndWaitForElement(textboxSearchProviders, driver);
		textboxSearchProviders.sendKeys(searchProvidersName);
		log("Entered text in Providers Search is : " + searchProvidersName);
	}

	public void clickProvidersSearchButton() {
		CCUtils.verifyAndWaitForElement(buttonSearchProviders, driver);
		javascriptClick(buttonSearchProviders);
		log("Clicked on Search button");
	}

	public void verifySearchedResult(String searchProvidersName) {
		log("Verifying searched Connected Providers in list");
		CCUtils.verifyAndWaitForElement(tableResourceNameInSearchResult, driver);
		CCUtils.assertEquals(newResource.isDisplayed(), true);
		log("Verified Searched result");
	}

	public void verifyProvidersClearButtonFunctionality(String searchProviders) {
		CCUtils.verifyAndWaitForElement(textboxSearchProviders, driver);
		log("Default text in Providers search is : " + textboxSearchProviders.getAttribute("placeholder"));
		CCUtils.assertTrue(textboxSearchProviders.getAttribute("placeholder").contains("Search"), "");
		CCUtils.assertEquals(buttonSearchProviders.getText(), "SEARCH");
		log("Default text present in Search button in search textbox : " + buttonSearchProviders.getText());
		searchProviders(searchProviders);
		clickProvidersSearchButton();
		CCUtils.assertEquals(buttonSearchProviders.getText(), "CLEAR");
		log("Text present in Providers search button after it is clicked is : " + buttonSearchProviders.getText());
		clickClearButton();
		log("Clicked on CLEAR button");
	}

	public boolean testToCheckNoResultsFoundMessage() {
		CCUtils.verifyAndWaitForElement(noResultFoundInProviders, driver);
		if (noResultFoundInProviders.getText().equals("No data to display")) {
			return true;
		}
		log("Search result found " + noResultFoundInProviders.getText());
		return false;
	}

	public void verifyProvidersSearchTextBoxAndPlaceholderIsDisplayed() {
		CCUtils.verifyAndWaitForElement(textboxSearchProviders, driver);
		log("Verifying search textbox is present on locations deatils page");
		if (textboxSearchProviders.isDisplayed() == true) {
			log("Verifying search textbox is enabled");
			CCUtils.assertTrue(textboxSearchProviders.isEnabled(), "search button is disabled");
			CCUtils.assertEquals(textboxSearchProviders.getAttribute("placeholder"), "Search");
			log("Verified search text and it's placeholder");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public void verifyProvidersSearchButtonIsDisplayed() {
		log("Verifying search button is displayed on locations search page");
		CCUtils.verifyAndWaitForElement(textboxSearchProviders, driver);
		textboxSearchProviders.sendKeys("ProvidersSearchInput");
		CCUtils.verifyAndWaitForElement(buttonSearchProviders, driver);
		if (buttonSearchProviders.isDisplayed() == true) {
			log("Verifying search button is enabled");
			CCUtils.assertTrue(buttonSearchProviders.isEnabled(), "search button is disabled");
			log("Verifying search button text");
			CCUtils.assertEquals(buttonSearchProviders.getText().trim(), "SEARCH");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public void clickProvidersNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		log("click to Next Page button");
		buttonNextPage.click();
	}

	public void clickProvidersLastPageButton() {
		CCUtils.verifyAndWaitForElement(buttonLastPage, driver);
		log("click to Last Page button");
		buttonLastPage.click();
	}

	public void clickProvidersPreviousPageButton() {
		CCUtils.verifyAndWaitForElement(buttonPreviousPage, driver);
		log("click to Previous Page button");
		buttonPreviousPage.click();
	}

	public void clickProvidersFirstPageButton() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		log("click to First Page button");
		buttonFirstPage.click();
	}

	public void addConnectedProviders(String input) throws Exception {
		clickOnAdd();
		enterProviderName(input);
		clickOnAddToSave();
	}
}
