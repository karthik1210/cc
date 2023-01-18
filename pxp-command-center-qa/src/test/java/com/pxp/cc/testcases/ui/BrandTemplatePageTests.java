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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level")
	public void testCreateBrandAtClientLevel() throws Exception {
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
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Created Brand");
		runOnce = false;
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to create brand at Client level with all logos Favicon,Solutions,Patient Communication logo gets uploaded")
	public void testCreateBrandAtClientLevelUploadingAllLogoImages() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
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
	public void testCreateAndEditAndVerifyEditPageBrandAtClientLevelUploadingAllLogoImagesWithDisplaySelectedLogo()
			throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().clickToLogoRadioButton();
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
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		ccui.getBrandingTemplatesPage().clickOnExpandArrow();
		ccui.getBrandingTemplatesPage().clickOnShowOtherVersionsArrow();
		ccui.getBrandingTemplatesPage().VerifyNewlyCreatedBrandingTemplate(newVersion);
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenu();
		logStep("Click on  Make Active option");
		ccui.getBrandingTemplatesPage().clickOnEdit();
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
		String actualMsg = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMsg, "Success! Successfully Updated Brand");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Version name required' error message when click to save without entering version name")
	public void testErrorMessageCreateBrandAtClientLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		ccui.getBrandingTemplatesPage().createNewBrand("");
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getBrandingTemplatesPage().verifyVerionNameErrorGotDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Invalid file type' error message while uploading image at Solutions Logo")
	public void testErrorInvalidFileTypeAtSolutionsFaviconPatientCommunicationsLogoWhileCreateBrandAtClientLevel()
			throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
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
		ngHierarchyClientCreate();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		log("upload image at favicon section");
		ccui.getBrandingTemplatesPage().uploadFaviconLogoValidImage();
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getBrandingTemplatesPage().verifyAltTextRequiredErrorGotDisplayed();
		log("upload image at Solutions Logo section");
		ccui.getBrandingTemplatesPage().uploadSolutionLogoImage();
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getBrandingTemplatesPage().verifyAltTextRequiredErrorGotDisplayed();
		log("upload image at Patient Communications section");
		ccui.getBrandingTemplatesPage().uploadPatientCommunicationsLogoValidImage();
		ccui.getBrandingTemplatesPage().verifyAndClickSaveButton();
		ccui.getBrandingTemplatesPage().verifyAltTextRequiredErrorGotDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Maximum character reached error while entering more than 50 character in Version name")
	public void testErrorMaximumCharacterReachedAtVersionName() throws Exception {
		ngHierarchyClientCreate();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(42));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
		ccui.getBrandingTemplatesPage().verifyMaxCharacterReachedErrorGotDisplayed();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to check remove button is working in Fvaicon Solutions and Patient Communication Logo Section")
	public void testRemoveFaviconSolutionsPatientCommunicationLogoImage() throws Exception {
		ngHierarchyClientCreate();
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(42));
		ccui.getBrandingTemplatesPage().createNewBrand(newVersion);
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
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		ccui.getBrandingTemplatesPage().VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
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

	@Test(enabled = true,retryAnalyzer = RetryAnalyzer.class, description = "Test to Delete brand for Inactive status at Client level")
	public void testDeleteBrandForInactiveStatusAtClientLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Create Duplicate Branding");
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenu();
		logStep("Click on Duplicate option");
		ccui.getBrandingTemplatesPage().clickOnDuplicate();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenu();
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Click on  YesDelete option");
		ccui.getBrandingTemplatesPage().clickOnYesDelete();
		String actualMessageOne = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessageOne, "Success! Brand has been successfully deleted");
		logStep("Verify user redirected to client information page and branding template is displayed");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Delete confirmation Popup")
	public void testForDeleteConfirmationPopup() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenu();
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Verify Popup for delete");
		ccui.getBrandingTemplatesPage().verifyConfirmationPopupForDelete();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button on the delete confirmation Popup")
	public void testForToCheckFunctionalityOfCancelOnDeleteConfirmationPopup() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenu();
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Verify Popup for delete");
		ccui.getBrandingTemplatesPage().verifyConfirmationPopupForDelete();
		logStep("Verify Popup for delete");
		ccui.getBrandingTemplatesPage().verifyConfirmationPopupForDelete();
		logStep("Click on Cancel button");
		ccui.getBrandingTemplatesPage().clickOnCancel();
		logStep("Verify user redirected to client information page and branding template is displayed");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cross-icon on the delete confirmation Popup")
	public void testForToCheckFunctionalityOfCrossIconDeleteConfirmationPopup() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Branding templates displayed at client level");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		ccui.getBrandingTemplatesPage().VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		ccui.getBrandingTemplatesPage().clickOnKebabMenu();
		logStep("Click on  delete option");
		ccui.getBrandingTemplatesPage().clickOnDelete();
		logStep("Verify Popup for delete");
		ccui.getBrandingTemplatesPage().verifyConfirmationPopupForDelete();
		logStep("Click on Cancel button");
		ccui.getBrandingTemplatesPage().clickOnCancel();
		logStep("Verify user redirected to client information page and branding template is displayed");
		ccui.getBrandingTemplatesPage().VerifyBrandingTemplatesIsDisplayed();
		runOnce = false;
	}
}
