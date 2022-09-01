//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

public class DuplicateBrandingTemplateTests extends CommandCenterGenericTest {
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

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Duplicate option is present in kebab menu for active branding template status at Client level")
	public void testDuplicateOptionForActiveStatusAtClientLevel() throws Exception {
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
		logStep("Verify branding status is active");
		brandingTemplatesPage.VerifyBrandingNameIsActive();
		logStep("Create Duplicate Branding");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Duplicate Branding confirmation Popup")
	public void testForDuplicateBrandingTemplateConfirmationPopup() throws Exception {
		logStep("Verify Branding templates displayed");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Add New Brand");
		brandingTemplatesPage.VerifyAndClickVersionButton();
		String newVersion = testData.getProperty("new.client.name");
		brandingTemplatesPage.createNewBrand(newVersion);
		brandingTemplatesPage.uploadSolutionLogoImage();
		brandingTemplatesPage.verifyRemoveButton();
		String altText = testData.getProperty("new.client.name");
		brandingTemplatesPage.enterAltText(altText);
		brandingTemplatesPage.verifyAndClickSaveButton();
		logStep("Verify branding status is active");
		brandingTemplatesPage.VerifyBrandingNameIsActive();
		logStep("Create Duplicate Branding");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Duplicate option is present in kebab menu for INACTIVE branding template status at Client level")
	public void testDuplicateOptionForInactiveStatusAtClientLevel() throws Exception {
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
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
	}
}