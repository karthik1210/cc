// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class BrandTemplatePageTests extends CommandCenterGenericTest {
	public static boolean runOnce = true;
	public CCUI ccui;
	public static String systemName, clientName, enterpriseName, practiceName;
	public ClientDirectoryPage clientDirectorPage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			clientDirectorPage = ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			getClientSystementErpriseandPractice();
		}
	}

	@Test(enabled = false)
	public void ngHierarchyClientCreate() throws Exception {
		if (!runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			LoginPage loginPage = new LoginPage(driver, testData.getProperty("base.url"));
			loginPage.login(testData.getProperty("user.name"), testData.getProperty("user.password"));
			logStep("Select create client button from new dropdown");
			logStep("Searching a client in client directory");
			ccui.getNgHierarchyClientCreationPage().searchNode(clientSystementErpriseandPractice.get("clientNam"));
			ccui.getNgHierarchyClientCreationPage().clickSearchButton();
			ccui.getNgHierarchyClientCreationPage()
					.clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		}
	}

	public void editBrandingTemplate() throws Exception {
		String newVersionName = "NewVersion" + CCUtils.generateAlphaNumericString(5);
		ccui.getBrandingTemplatesPage().editBrandVersionName(newVersionName);
		ccui.getBrandingTemplatesPage().editFaviconLogoValidImage();
		String editAltTextFavicon = "FavAltText" + CCUtils.generateAlphaNumericString(3);
		ccui.getBrandingTemplatesPage().editAltTextForFaviconLogo(editAltTextFavicon);
		ccui.getBrandingTemplatesPage().editSolutionLogoImage();
		String editAltText = "SolAltText" + CCUtils.generateAlphaNumericString(3);
		ccui.getBrandingTemplatesPage().editAltTextForSolutionsLogo(editAltText);
		ccui.getBrandingTemplatesPage().editPatientCommunicationsLogoValidImage();
		String editAltTextPatientCommunications = "PComAltText" + CCUtils.generateAlphaNumericString(3);
		ccui.getBrandingTemplatesPage().editAltTextForPatientCommunicationsLogo(editAltTextPatientCommunications);
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level")
	public void testCreateBrandAtClientLevel() throws Exception {
		logStep("PXPP-11639 PXPP-11649 PXPP-11663 PXPP-11720 creation of brand at client level");
		ngHierarchyClientCreate();
		clientDirectorPage.searchAndSelectClientNameInClientTable("Brandclient");
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(4));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
		// runOnce = false;
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level with all logos Favicon,Solutions,Patient Communication logo gets uploaded")
	public void testCreateBrandAtClientLevelUploadingAllLogoImages() throws Exception {
		logStep("PXPP-11651 PXPP-11664 PXPP-11663 - verify create brand at client level with all logo images upload");
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForFavicon(altTextFavicon);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForPatientCommunication(altTextPatientCommunications);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand with Display selected as Logo and edit and verify web elements while editing brand at Client level")
	public void testCreateAndEditAndVerifyEditPageActiveBrandAtClientLevelUploadingAllLogoImagesWithDisplaySelectedLogo()
			throws Exception {
		logStep("PXPP-11759 PXPP-11736 PXPP-12513 PXPP-12517 PXPP-12519 PXPP-12546 PXPP-12549 PXPP-12551 PXPP-12555 PXPP-12584 verify functionality of create and edit brand at client level");
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().clickToLogoRadioButton();
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForFavicon(altTextFavicon);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForPatientCommunication(altTextPatientCommunications);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  Make Active option");
		ccui.getBrandingTemplatesPage().clickOnMakeActive();
		String actualMsg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMsg, "Success! Successfully made the brand active");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on edit option");
		ccui.getBrandingTemplatesPage().clickOnEdit();
		logStep("update display to practice name");
		ccui.getBrandingTemplatesPage().clickToPracticeNameRadioButton();
		ccui.getBrandingTemplatesPage().createNewBrand(CCUtils.generateAlphaNumericString(4));
		ccui.getBrandingTemplatesPage().verifyAndClickEditSaveButton();
		logStep("Verify details on Confirmation dialog box which appears after clicking on SAVE");
		ccui.getBrandingTemplatesPage().verifyConfirmationDialogBoxTitleLabel();
		ccui.getBrandingTemplatesPage().verifyConfirmationDialogBoxSubText();
		ccui.getBrandingTemplatesPage().verifyConfirmationDialogBoxSaveButton();
		ccui.getBrandingTemplatesPage().verifyConfirmationDialogBoxCancelButton();
		ccui.getBrandingTemplatesPage().verifyConfirmationDialogBoxCrossIcon();
		logStep("Edit active branding template");
		editBrandingTemplate();
		ccui.getBrandingTemplatesPage().verifyAndClickEditSaveButton();
		ccui.getBrandingTemplatesPage().clickSaveOnConfirmationDialogBox();
		String actualMesg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMesg, "Success! Successfully Updated Brand");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "system level creation of brand and verify edit functionality")
	public void testCreateAndEditAndVerifyEditPageActiveBrandAtSystemLevelUploadingAllLogoImagesWithDisplaySelectedLogo()
			throws Exception {
		logStep("PXPP-11835 PXPP-11841 PXPP-11850 PXPP-11853 PXPP-11855 PXPP-11862 PXPP-11852 PXPP-11843 PXPP-11836 verify functionality of create and edit brand at system level");
		ngHierarchyClientCreate();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		ccui.getGeneralSettingsPage().pressEnterInSearchText();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().clickToLogoRadioButton();
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForFavicon(altTextFavicon);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForPatientCommunication(altTextPatientCommunications);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  Make Active option");
		ccui.getBrandingTemplatesPage().clickOnMakeActive();
		String actualMsg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMsg, "Success! Successfully made the brand active");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on edit option");
		ccui.getBrandingTemplatesPage().clickOnEdit();
		logStep("Edit active branding template");
		editBrandingTemplate();
		ccui.getBrandingTemplatesPage().verifyAndClickEditSaveButton();
		ccui.getBrandingTemplatesPage().clickSaveOnConfirmationDialogBox();
		String actualMesg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMesg, "Success! Successfully Updated Brand");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "enterprise level creation of brand and verify edit functionality")
	public void testCreateAndEditAndVerifyEditPageActiveBrandAtEnterpriseLevelUploadingAllLogoImagesWithDisplaySelectedLogo()
			throws Exception {
		logStep("PXPP-11935 PXPP-11989 PXPP-12387 PXPP-12388 PXPP-12390 PXPP-12457 PXPP-12460 PXPP-12461 PXPP-12480 PXPP-12482 verify functionality of create and edit brand at enterprise level");
		ngHierarchyClientCreate();
		logStep("Searching a enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		ccui.getGeneralSettingsPage().pressEnterInSearchText();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().clickToLogoRadioButton();
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForFavicon(altTextFavicon);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForPatientCommunication(altTextPatientCommunications);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  Make Active option");
		ccui.getBrandingTemplatesPage().clickOnMakeActive();
		String actualMsg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMsg, "Success! Successfully made the brand active");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on edit option");
		ccui.getBrandingTemplatesPage().clickOnEdit();
		logStep("Edit active branding template");
		editBrandingTemplate();
		ccui.getBrandingTemplatesPage().verifyAndClickEditSaveButton();
		ccui.getBrandingTemplatesPage().clickSaveOnConfirmationDialogBox();
		String actualMesg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMesg, "Success! Successfully Updated Brand");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "practice level creation of brand and verify edit functionality")
	public void testCreateAndEditAndVerifyEditPageActiveBrandAtPracticeLevelUploadingAllLogoImagesWithDisplaySelectedLogo()
			throws Exception {
		logStep("PXPP-12404	PXPP-12423 PXPP-12452 PXPP-12455 PXPP-12456 PXPP-12470 PXPP-12471 PXPP-14307 PXPP-14317 PXPP-14359 PXPP- 14347 PXPP-14341 PXPP-14342 PXPP-14343 verify functionality of create and edit brand at practice level");
		ngHierarchyClientCreate();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		ccui.getGeneralSettingsPage().pressEnterInSearchText();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().clickToLogoRadioButton();
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForFavicon(altTextFavicon);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().enterAltTextForPatientCommunication(altTextPatientCommunications);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  Make Active option");
		ccui.getBrandingTemplatesPage().clickOnMakeActive();
		String actualMsg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMsg, "Success! Successfully made the brand active");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on edit option");
		ccui.getBrandingTemplatesPage().clickOnEdit();
		logStep("update display to practice name");
		ccui.getBrandingTemplatesPage().clickToPracticeNameRadioButton();
		ccui.getBrandingTemplatesPage().createNewBrand(CCUtils.generateAlphaNumericString(4));
		logStep("Edit active branding template");
		editBrandingTemplate();
		ccui.getBrandingTemplatesPage().verifyAndClickEditSaveButton();
		ccui.getBrandingTemplatesPage().clickSaveOnConfirmationDialogBox();
		String actualMesg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMesg, "Success! Successfully Updated Brand");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Version name required' error message when click to save without entering version name and Invalid file type' error message while uploading image at Solutions Logo")
	public void testErrorMessageVersionNameRequiredAndInvalidFileTypeAtSolutionsFaviconPatientCommunicationsLogoWhileCreateBrandAtClientLevel()
			throws Exception {
		logStep("PXPP-11720 PXPP-11729 PXPP-11755 version Name Required and invalid file type at all logos verification");
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		ccui.getBrandingTemplatesPage().createNewBrand("");
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getBrandingTemplatesPage().verifyVerionNameErrorGotDisplayed();
		ccui.getBrandingTemplatesPage().uploadSolutionLogoInvalidImage();
		ccui.getBrandingTemplatesPage().verifyInvalidSolutionsLogoImageErrorGotDisplayed();
		ccui.getBrandingTemplatesPage().uploadFaviconLogoInvalidImage();
		ccui.getBrandingTemplatesPage().verifyInvalidFaviconImageErrorGotDisplayed();
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoInvalidImage();
		ccui.getBrandingTemplatesPage().verifyInvalidPatientCommunicationsLogoImageErrorGotDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check error message when mandatory alt text is not entered while uploading Favicon/Solutions/Patient Communication logo")
	public void testErrorAltTextRequiredAtFaviconSolutionsPatientCommunicationsLogo() throws Exception {
		logStep("PXPP-11720 PXPP-11729 alt text at all logos verification");
		ngHierarchyClientCreate();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		log("upload image at favicon section");
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(96));
		ccui.getBrandingTemplatesPage().enterAltTextForFavicon(altTextFavicon);
		ccui.getBrandingTemplatesPage().removeEnteredAltTextForFavicon();
		ccui.getBrandingTemplatesPage().verifyAltTextRequiredErrorGotDisplayed();
		log("upload image at Solutions Logo section");
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(96));
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().removeEnterAltText();
		ccui.getBrandingTemplatesPage().verifyAltTextRequiredErrorGotDisplayed();
		log("upload image at Patient Communications section");
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(96));
		ccui.getBrandingTemplatesPage().enterAltTextForPatientCommunication(altTextPatientCommunications);
		ccui.getBrandingTemplatesPage().removeEnterAltTextForPatientCommunication();
		ccui.getBrandingTemplatesPage().verifyAltTextRequiredErrorGotDisplayed();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to check Maximum character reached error in Version name and remove button is working in Favicon Solutions and Patient Communication Logo Section")
	public void testErrorMaximumCharacterReachedVersionNameAndRemoveFaviconSolutionsPatientCommunicationLogoImage()
			throws Exception {
		logStep("PXPP-11721 PXPP-12531 PXPP-12519 field errors and remove image in logos verification");
		ngHierarchyClientCreate();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(42));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().verifyMaxCharacterReachedErrorGotDisplayed();
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		ccui.getBrandingTemplatesPage().clickToRemoveButtonOfFaviconLogo();
		ccui.getBrandingTemplatesPage().verifyNoFileUploadedFaviconIsDisplayed();
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		ccui.getBrandingTemplatesPage().clickToRemoveButtonOfFaviconLogo();
		ccui.getBrandingTemplatesPage().verifyNoFileUploadedFaviconIsDisplayed();
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		ccui.getBrandingTemplatesPage().clickToRemoveButtonOfPatientCommunicationsLogo();
		ccui.getBrandingTemplatesPage().verifyNoFileUploadedPatientCommunicationIsDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to Max Characters Reached Error at all Favicon,Solutions,Patient Communication logo gets uploaded")
	public void testMaxCharactersReachedErrorAtAltText() throws Exception {
		logStep("PXPP-11729 PXPP-11755 field validation verification");
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		String altTextFavicon = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(92));
		ccui.getBrandingTemplatesPage().enterAltTextForFavicon(altTextFavicon);
		ccui.getBrandingTemplatesPage().verifyFaviconLogoMaxCharReachedErrorIsDisplayed();
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(92));
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().verifySolutionsLogoMaxCharReachedErrorIsDisplayed();
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		String altTextPatientCommunications = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericString(92));
		ccui.getBrandingTemplatesPage().enterAltTextForPatientCommunication(altTextPatientCommunications);
		ccui.getBrandingTemplatesPage().verifyPatientCommunicationMaxCharReachedErrorIsDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and cross-icon btton and verify all web elements on the delete confirmation Popup")
	public void testForToCheckFunctionalityOfCancelAndCrossIconButtonAndDeleteConfirmationPopup() throws Exception {
		logStep("PXPP-12356 PXPP-12236 PXPP-12356 cancel cross button functionalityy description");
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate("createBrand");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding("createBrand");
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Verify Popup for delete");
		ccui.getBrandingTemplatesPage().verifyConfirmationPopupForDelete();
		logStep("Click on cross icon button");
		ccui.getBrandingTemplatesPage().clickOnCrossIcon();
		logStep("Verify user redirected to client information page and branding template is displayed");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding("createBrand");
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Click on Cancel button");
		ccui.getBrandingTemplatesPage().clickOnCancel();
		logStep("Verify user redirected to client information page and branding template is displayed");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Duplicate and Delete functionality for branding template status at Client level")
	public void testDuplicateAndDeleteFunctionalityForBrandAtClientLevel() throws Exception {
		logStep("PXPP-21281 PXPP-21288 PXPP-21290 PXPP-12238 PXPP-12240 PXPP-12241 PXPP-12352 PXPP-12353 PXPP-12354 PXPP-12355 duplicate branding and delete brand functionality");
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("verify newly created brand visible in brands templates section");
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify newly created branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on Duplicate option");
		ccui.getBrandingTemplatesPage().clickOnDuplicate();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Click on Yes,Delete option");
		ccui.getBrandingTemplatesPage().clickOnYesDelete();
		String actualMessageOne = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessageOne, "Success! Brand has been successfully deleted");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Duplicate functionality for branding template status at system level")
	public void testDuplicateFunctionalityForBrandAtSystemLevel() throws Exception {
		logStep("PXPP-21281 PXPP-21288 PXPP-21290 PXPP-12238 PXPP-12240 PXPP-12241 PXPP-12352 PXPP-12353 PXPP-12354 PXPP-12355 duplicate branding and delete brand functionality");
		ngHierarchyClientCreate();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		ccui.getGeneralSettingsPage().pressEnterInSearchText();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("verify newly created brand visible in brands templates section");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify newly created branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on Duplicate option");
		ccui.getBrandingTemplatesPage().clickOnDuplicate();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Click on Yes,Delete option");
		ccui.getBrandingTemplatesPage().clickOnYesDelete();
		String actualMessageOne = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessageOne, "Success! Brand has been successfully deleted");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Duplicate and delete functionality for branding template status at enterprise level")
	public void testDuplicateAndDeleteFunctionalityForBrandAtEnterpriseLevel() throws Exception {
		logStep("PXPP-21281 PXPP-21288 PXPP-21290 verify duplicate branding functionality");
		ngHierarchyClientCreate();
		logStep("Searching a enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		ccui.getGeneralSettingsPage().pressEnterInSearchText();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("verify newly created brand visible in brands templates section");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify newly created branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on Duplicate option");
		ccui.getBrandingTemplatesPage().clickOnDuplicate();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Click on Yes,Delete option");
		ccui.getBrandingTemplatesPage().clickOnYesDelete();
		String actualMessageOne = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessageOne, "Success! Brand has been successfully deleted");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Duplicate and Delete functionality for branding template status at practice level")
	public void testDuplicateAndDeleteFunctionalityForBrandAtPracticeLevel() throws Exception {
		logStep("PXPP-21281 PXPP-21288 PXPP-21290 verify duplicate branding functionality");
		ngHierarchyClientCreate();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		ccui.getGeneralSettingsPage().pressEnterInSearchText();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		CCUtils.assertEquals(ccui.getGeneralSettingsPage().returnGeneralSettingTitle().trim(), "Brandpractice");
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		String altText = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().enterAltText(altText);
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("verify newly created brand visible in brands templates section");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrowForBrandingTemplates();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify newly created branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on Duplicate option");
		ccui.getBrandingTemplatesPage().clickOnDuplicate();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenuOfParticularBranding(newVersion);
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Click on Yes,Delete option");
		ccui.getBrandingTemplatesPage().clickOnYesDelete();
		String actualMessageOne = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessageOne, "Success! Brand has been successfully deleted");
		runOnce = false;
	}
}
