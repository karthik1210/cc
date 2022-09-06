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

public class DeleteBrandTemplateTests extends CommandCenterGenericTest {

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
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid details in mandatory fields");
		String clientName = testData.getProperty("new.client.name")
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

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Delete brand for Inactive status at Client level")
	public void testDeleteBrandForInactiveStatusAtClientLevel() throws Exception {
		
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
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		brandingTemplatesPage.clickOnYesDelete();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Brand has been successfully deleted");
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to When the branding template status is active, Delete option is not displayed in the Kebab menu")
	public void testForEditObtionOnActiveStatus() throws Exception {
		logStep("Verify Branding templates displayed");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is active");
		brandingTemplatesPage.VerifyBrandingNameIsActive();
		brandingTemplatesPage.clickOnkebabMenuForActiveStatus();
		logStep("Verify edit option when branding status is active");
		brandingTemplatesPage.verifyEditOptionForActiveStatus();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Delete confirmation Popup")
	public void testForDeleteConfirmationPopup() throws Exception {
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
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button on the delete confirmation Popup")
	public void testForToCheckFunctionalityOfCancelOnDeleteConfirmationPopup() throws Exception {

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
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		logStep("Click on Cancel button");
		brandingTemplatesPage.clickOnCancel();
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cross-icon on the delete confirmation Popup")
	public void testForToCheckFunctionalityOfCrossIconDeleteConfirmationPopup() throws Exception {

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
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		logStep("Click on Cancel button");
		brandingTemplatesPage.clickOnCancel();
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

}