// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.BrandingTemplatesPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.PracticeLinksPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class CreateBrandTemplateTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	private GeneralSettingsPage generalSettingsPage;
	private BrandingTemplatesPage brandingTemplatesPage;
	public CommonMethods commonMethods;
	public CreateClientPage createClientPage;
	public ClientDirectoryPage cchomepage;
	public AddOrgNodePage addOrgNodePage;
	String systemName, enterpriseName, clientName, practiceName;
	public PracticeLinksPage practiceLinksPage;
	public NGHierarchyClientCreation ngHierarchyClientCreation;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to command center portal");
		cchomepage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		commonMethods = new CommonMethods(driver);
		ngHierarchyClientCreation = new NGHierarchyClientCreation(driver);
		String clientNam = testData.getProperty("new.client.name2")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSap = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientType = testData.getProperty("new.client.type");
		String timeZone = testData.getProperty("new.client.timezone");
		ngHierarchyClientCreation.createNewActiveClient(clientNam, clientSap, clientType, timeZone);
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level")
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

	@Test(priority = 1, enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level with all logos Favicon,Solutions,Patient Communication logo gets uploaded")
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

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level with Display selected as Logo")
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

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Version name required' error message when click to save without entering version name")
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

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Invalid file type' error message while uploading image at Solutions Logo")
	public void testErrorInvalidFileTypeAtSolutionsFaviconPatientCommunicationsLogoWhileCreateBrandAtClientLevel()
			throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadSolutionLogoInvalidImage();
		brandingTemplatesPage.verifyInvalidSolutionsLogoImageErrorGotDisplayed();
		brandingTemplatesPage.uploadFaviconLogoInvalidImage();
		brandingTemplatesPage.verifyInvalidFaviconImageErrorGotDisplayed();
		brandingTemplatesPage.uploadPatientCommunicationsLogoInvalidImage();
		brandingTemplatesPage.verifyInvalidPatientCommunicationsLogoImageErrorGotDisplayed();
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check error message when mandatory alt text is not entered while uploading Favicon/Solutions/Patient Communication logo")
	public void testErrorAltTextRequiredAtFaviconSolutionsPatientCommunicationsLogo() throws Exception {
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
		log("upload image at Solutions Logo section");
		brandingTemplatesPage.uploadSolutionLogoImage();
		brandingTemplatesPage.verifyAndClickSaveButton();
		brandingTemplatesPage.verifyAltTextRequiredErrorGotDisplayed();
		log("upload image at Patient Communications section");
		brandingTemplatesPage.uploadPatientCommunicationsLogoValidImage();
		brandingTemplatesPage.verifyAndClickSaveButton();
		brandingTemplatesPage.verifyAltTextRequiredErrorGotDisplayed();
	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Maximum character reached error while entering more than 50 character in Version name")
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

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Test to check remove button is working in Fvaicon Solutions and Patient Communication Logo Section")
	public void testRemoveFaviconSolutionsPatientCommunicationLogoImage() throws Exception {
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
		brandingTemplatesPage.uploadSolutionLogoImage();
		brandingTemplatesPage.clickToRemoveButtonOfFaviconLogo();
		brandingTemplatesPage.verifyNoFileUploadedFaviconIsDisplayed();
		brandingTemplatesPage.uploadPatientCommunicationsLogoValidImage();
		brandingTemplatesPage.clickToRemoveButtonOfPatientCommunicationsLogo();
		brandingTemplatesPage.verifyNoFileUploadedPatientCommunicationIsDisplayed();
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to Max Characters Reached Error at all Favicon,Solutions,Patient Communication logo gets uploaded")
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
