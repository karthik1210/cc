// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.BrandingTemplatesPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class CreateBrandTemplateTests extends CommandCenterGenericTest {

	private ClientDirectoryPage clientDirectoryPage;
	private GeneralSettingsPage generalSettingsPage;
	private BrandingTemplatesPage brandingTemplatesPage;
	public CommonMethods commonMethods;
	public CreateClientPage createClientPage;

	@BeforeMethod
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid details in mandatory fields");
		String clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
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

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level")
	public void testCreateBrandAtClientLevel() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		brandingTemplatesPage.enterAltText(altText);
		brandingTemplatesPage.verifyAndClickSaveButton();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level with all logos Favicon,Solutions,Patient Communication logo gets uploaded")
	public void testCreateBrandAtClientLevelUploadingAllLogoImages() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		brandingTemplatesPage.enterAltTextForFavicon(altTextFavicon);
		brandingTemplatesPage.uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		brandingTemplatesPage.enterAltText(altText);
		brandingTemplatesPage.uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(3));
		brandingTemplatesPage.enterAltTextForPatientCommunication(altTextPatientCommunications);
		brandingTemplatesPage.verifyAndClickSaveButton();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level with Display selected as Logo")
	public void testCreateBrandAtClientLevelUploadingAllLogoImagesWithDisplaySelectedLogo() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.clickToLogoRadioButton();
		brandingTemplatesPage.uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		brandingTemplatesPage.enterAltTextForFavicon(altTextFavicon);
		brandingTemplatesPage.uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		brandingTemplatesPage.enterAltText(altText);
		brandingTemplatesPage.uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(3));
		brandingTemplatesPage.enterAltTextForPatientCommunication(altTextPatientCommunications);
		brandingTemplatesPage.verifyAndClickSaveButton();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Version name required' error message when click to save without entering version name")
	public void testErrorMessageCreateBrandAtClientLevel() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		brandingTemplatesPage.createNewBrand("");
		brandingTemplatesPage.verifyAndClickSaveButton();
		brandingTemplatesPage.verifyVerionNameErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Invalid file type' error message while uploading image at Solutions Logo")
	public void testErrorInvalidFileTypeAtSolutionsLogoWhileCreateBrandAtClientLevel() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadSolutionLogoInvalidImage();
		brandingTemplatesPage.verifyInvalidSolutionsLogoImageErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Invalid file type' error message while uploading image at Favicon Logo")
	public void testErrorInvalidFileTypeAtFaviconLogoWhileCreateBrandAtClientLevel() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadFaviconLogoInvalidImage();
		brandingTemplatesPage.verifyInvalidFaviconImageErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Invalid file type' error message while uploading image at Patient Communications Logo")
	public void testErrorInvalidFileTypeAtPatientCommunicationsLogoWhileCreateBrandAtClientLevel() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadPatientCommunicationsLogoInvalidImage();
		brandingTemplatesPage.verifyInvalidPatientCommunicationsLogoImageErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check error message when mandatory alt text is not entered while uploading Patient Communtications logo")
	public void testErrorAltTextRequiredAtPatientCommunicationsLogo() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		log("upload image at Patient Communications section");
		brandingTemplatesPage.uploadPatientCommunicationsLogoValidImage();
		brandingTemplatesPage.verifyAndClickSaveButton();
		brandingTemplatesPage.verifyAltTextRequiredErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check error message when mandatory alt text is not entered while uploading Favicon logo")
	public void testErrorAltTextRequiredAtFaviconLogo() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		log("upload image at favicon section");
		brandingTemplatesPage.uploadFaviconLogoValidImage();
		brandingTemplatesPage.verifyAndClickSaveButton();
		brandingTemplatesPage.verifyAltTextRequiredErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check error message when mandatory alt text is not entered while uploading Solutions Logo")
	public void testErrorAltTextRequiredAtSolutionsLogo() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		log("upload image at Solutions Logo section");
		brandingTemplatesPage.uploadSolutionLogoImage();
		brandingTemplatesPage.verifyAndClickSaveButton();
		brandingTemplatesPage.verifyAltTextRequiredErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Maximum character reached error while entering more than 50 character in Version name")
	public void testErrorMaximumCharacterReachedAtVersionName() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(42));
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.verifyMaxCharacterReachedErrorGotDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Maximum character reached error while entering more than 50 character in Version name")
	public void testRemoveFaviconLogoImage() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(42));
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadFaviconLogoValidImage();
		brandingTemplatesPage.clickToRemoveButtonOfFaviconLogo();
		brandingTemplatesPage.verifyNoFileUploadedFaviconIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Maximum character reached error while entering more than 50 character in Version name")
	public void testRemovePatientCommunicationsLogoImage() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadPatientCommunicationsLogoValidImage();
		brandingTemplatesPage.clickToRemoveButtonOfPatientCommunicationsLogo();
		brandingTemplatesPage.verifyNoFileUploadedPatientCommunicationIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Maximum character reached error while entering more than 50 character in Version name")
	public void testRemoveSolutionsLogoImage() throws Exception {
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadSolutionLogoImage();
		brandingTemplatesPage.clickToRemoveButtonOfFaviconLogo();
		brandingTemplatesPage.verifyNoFileUploadedFaviconIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Max Characters Reached Error at all Favicon,Solutions,Patient Communication logo gets uploaded")
	public void testMaxCharactersReachedErrorAtAltText() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(92));
		brandingTemplatesPage.enterAltTextForFavicon(altTextFavicon);
		brandingTemplatesPage.verifyFaviconLogoMaxCharReachedErrorIsDisplayed();
		brandingTemplatesPage.uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(92));
		brandingTemplatesPage.enterAltText(altText);
		brandingTemplatesPage.verifySolutionsLogoMaxCharReachedErrorIsDisplayed();
		brandingTemplatesPage.uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(92));
		brandingTemplatesPage.enterAltTextForPatientCommunication(altTextPatientCommunications);
		brandingTemplatesPage.verifyPatientCommunicationMaxCharReachedErrorIsDisplayed();
	}

}
