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
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class EditBrandingTemplateTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	private GeneralSettingsPage generalSettingsPage;
	private BrandingTemplatesPage brandingTemplatesPage;
	public CommonMethods commonMethods;
	public CreateClientPage createClientPage;
	public AddOrgNodePage addOrgNodePage;
	String newVersionName, clientName, systemName;

	@BeforeMethod
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.clickConfirmPopup();
		clientDirectoryPage.searchAndSelectClientNameInClientTable(clientName);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
	}

	public void createBrandingTemplate() throws Exception {
		logStep("Verify Branding templates label displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		newVersionName = "Version" + CCUtils.generateAlphaNumericString(5);
		brandingTemplatesPage.createNewBrand(newVersionName);
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

	public void editBrandingTemplate() throws Exception {
		String newVersionName = "NewVersion" + CCUtils.generateAlphaNumericString(5);
		brandingTemplatesPage.editBrandVersionName(newVersionName);
		brandingTemplatesPage.editFaviconLogoValidImage();
		String editAltTextFavicon = "FavAltText" + CCUtils.generateAlphaNumericString(3);
		brandingTemplatesPage.editAltTextForFaviconLogo(editAltTextFavicon);
		brandingTemplatesPage.editSolutionLogoImage();
		String editAltText = "SolAltText" + CCUtils.generateAlphaNumericString(3);
		brandingTemplatesPage.editAltTextForSolutionsLogo(editAltText);
		brandingTemplatesPage.editPatientCommunicationsLogoValidImage();
		String editAltTextPatientCommunications = "PComAltText" + CCUtils.generateAlphaNumericString(3);
		brandingTemplatesPage.editAltTextForPatientCommunicationsLogo(editAltTextPatientCommunications);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to edit active brand at Client level")
	public void testEditActiveBrandAtClientLevel() throws Exception {
		createBrandingTemplate();
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		brandingTemplatesPage.clickOnExpandArrow();
		brandingTemplatesPage.VerifyNewlyCreatedBrandingTemplate(newVersionName);
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  Make Active option");
		brandingTemplatesPage.clickOnMakeActive();
		logStep("Verify branding status is active");
		brandingTemplatesPage.VerifyBrandingNameIsActive();
		brandingTemplatesPage.clickOnKebabMenu();
		brandingTemplatesPage.clickOnEdit();
		logStep("Edit active branding template");
		editBrandingTemplate();
		brandingTemplatesPage.verifyAndClickEditSaveButton();
		brandingTemplatesPage.clickSaveOnConfirmationDialogBox();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Updated Brand");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to edit inactive brand at Client level")
	public void testEditInactiveBrandAtClientLevel() throws Exception {
		createBrandingTemplate();
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		brandingTemplatesPage.clickOnExpandArrow();
		brandingTemplatesPage.VerifyNewlyCreatedBrandingTemplate(newVersionName);
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		brandingTemplatesPage.clickOnEdit();
		logStep("Edit inactive branding template");
		editBrandingTemplate();
		brandingTemplatesPage.verifyAndClickEditSaveButton();
		brandingTemplatesPage.clickSaveOnConfirmationDialogBox();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Successfully Updated Brand");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify details on Confirmation dialog box which appears after clicking on SAVE")
	public void testConfirmationDialogBoxDetails() throws Exception {
		createBrandingTemplate();
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		brandingTemplatesPage.clickOnExpandArrow();
		brandingTemplatesPage.VerifyNewlyCreatedBrandingTemplate(newVersionName);
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		brandingTemplatesPage.clickOnEdit();
		logStep("Edit active branding template");
		editBrandingTemplate();
		brandingTemplatesPage.verifyAndClickEditSaveButton();
		logStep("Verify details on Confirmation dialog box which appears after clicking on SAVE");
		brandingTemplatesPage.verifyConfirmationDialogBoxTitleLabel();
		brandingTemplatesPage.verifyConfirmationDialogBoxSubText();
		brandingTemplatesPage.verifyConfirmationDialogBoxSaveButton();
		brandingTemplatesPage.verifyConfirmationDialogBoxCancelButton();
		brandingTemplatesPage.verifyConfirmationDialogBoxCrossIcon();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Cancel button, cross icon functionality on Confirmation dialog box")
	public void testConfirmationDialogBoxCancelCrossButtonFunctionality() throws Exception {
		createBrandingTemplate();
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		brandingTemplatesPage.clickOnExpandArrow();
		brandingTemplatesPage.VerifyNewlyCreatedBrandingTemplate(newVersionName);
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		brandingTemplatesPage.clickOnEdit();
		logStep("Edit active branding template");
		editBrandingTemplate();
		logStep("Verify Cancel button, cross icon functionality on Confirmation dialog box");
		brandingTemplatesPage.verifyAndClickEditSaveButton();
		brandingTemplatesPage.verifyConfirmationDialogBoxTitleLabel();
		brandingTemplatesPage.verifyConfirmationDialogBoxSubText();
		brandingTemplatesPage.verifyConfirmationDialogBoxCancelButton();
		brandingTemplatesPage.clickCancelOnConfirmationDialogBox();
		brandingTemplatesPage.verifyAndClickEditSaveButton();
		brandingTemplatesPage.verifyConfirmationDialogBoxCrossIcon();
		brandingTemplatesPage.clickCrossIconOnConfirmationDialogBox();
	}
}
