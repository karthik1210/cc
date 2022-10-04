//Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddLocationPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import com.pxp.cc.objectmaps.LocationDetailsPage;

public class LocationDetailsSolutionsDisplayInfoTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private LocationDetailsPage locationDetailsPage;
	String clientName, locationName;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid details in mandatory fields");
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		logStep("Verify active radio button is by default selected on the create client popup");
		createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on create button on the create client popup");
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchNode(clientName);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		commonMethods = new CommonMethods(driver);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
	}

	public void createLocation() throws Exception {
		generalSettingsPage.clickOnGeneralSettingsTab();
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(clientName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify Locations tab is displayed");
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		addLocationPage = new AddLocationPage(driver);
		logStep("Click on Add button");
		addLocationPage.verifyLocationsPageTitle();
		addLocationPage.verifyAddLocationButton();
		addLocationPage.verifyAddButtonIsEnabled();
		addLocationPage.clickOnAddLocationButton();
		logStep("Enter data in all input fields");
		locationName = CCUtils.generateAlphaNumericString(18);
		addLocationPage.enterLocationName(locationName);
		addLocationPage.enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterAddressOne(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterAddressTwo(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterCity(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterZip(CCUtils.generateAlphaNumericString(18));
		addLocationPage.addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		addLocationPage.clickOnSaveAndAddButton();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Solutions Display Info Section")
	public void testSolutionsDisplayInfoSection() throws Exception {
		logStep("Verify Solutions Display Info Section");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifySolutionsDisplayInfoSection();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all input fields and verify save functionality")
	public void testSaveFunctionalityWithValidDetailsInAllInputFields() throws Exception {
		logStep("Edit Solutions Display Info and verify success toaster");
		createLocation();
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyEditButtonLabel();
		locationDetailsPage.clickOnEditButton();
		locationDetailsPage.verifySolutionsDisplayInfoSectionLabel();
		locationDetailsPage.enterLocationDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterLocationDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterAddress2(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterPhoneNumber(String.valueOf(CCUtils.generateMobileNumber()));
		locationDetailsPage.enterDirectionsEn(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterDirectionsEs(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterStartOfficeHours("10:00");
		locationDetailsPage.enterEndOfficeHours("20:00");
		locationDetailsPage.clickOnSaveButton();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify  error message for LocationDisplayNameEn, LocationDisplayNameEs, address2, directionsEn, directionsEs, phoneNumber fields and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum character reached error message for LocationDisplayNameEn, LocationDisplayNameEs, address2, directionsEn, directionsEs, phoneNumber");
		createLocation();
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyEditButtonLabel();
		locationDetailsPage.clickOnEditButton();
		locationDetailsPage.verifySolutionsDisplayInfoSectionLabel();
		locationDetailsPage.verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericString(102),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		locationDetailsPage.verifyErrorMessageForInvalidPhoneNumber(CCUtils.generateAlphaNumericString(10));
		locationDetailsPage.verifyErrorMessageForInvalidOfficeHours("20:00", "10:00");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel button functionality")
	public void testCancelFunctionality() throws Exception {
		createLocation();
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		logStep("Verify cancel button functionality");
		locationDetailsPage.verifyEditButtonLabel();
		locationDetailsPage.clickOnEditButton();
		locationDetailsPage.clickOnCancel();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in mandatory input fields and verify save functionality")
	public void testSaveFunctionalityWithValidDetailsInMandatoryInputFields() throws Exception {
		logStep("Edit Solutions Display Info and verify success toaster");
		createLocation();
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyEditButtonLabel();
		locationDetailsPage.clickOnEditButton();
		locationDetailsPage.verifySolutionsDisplayInfoSectionLabel();
		locationDetailsPage.enterPhoneNumber(String.valueOf(CCUtils.generateMobileNumber()));
		locationDetailsPage.enterStartOfficeHours("10:00");
		locationDetailsPage.enterEndOfficeHours("20:00");
		locationDetailsPage.clickOnSaveButton();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in Non manadory input fields and verify save button is disabled")
	public void testSaveFunctionalityWithValidDetailsInAllInputNonMandatoryFields() throws Exception {
		logStep(" For valid details in Non manadory input fields and verify save button is disabled");
		createLocation();
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyEditButtonLabel();
		locationDetailsPage.clickOnEditButton();
		locationDetailsPage.verifySolutionsDisplayInfoSectionLabel();
		locationDetailsPage.enterLocationDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterLocationDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterAddress2(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterDirectionsEn(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.enterDirectionsEs(CCUtils.generateAlphaNumericString(8));
		locationDetailsPage.verifySaveButtonIsDisabled();
	}
}
