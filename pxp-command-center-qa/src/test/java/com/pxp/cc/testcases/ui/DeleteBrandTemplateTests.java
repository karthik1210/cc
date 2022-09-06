// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.BrandingTemplatesPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class DeleteBrandTemplateTests extends CommandCenterGenericTest {

	private ClientDirectoryPage clientDirectoryPage;
	private GeneralSettingsPage generalSettingsPage;
	private BrandingTemplatesPage brandingTemplatesPage;
	public CommonMethods commonMethods;

	@BeforeMethod
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Login to CC portal");
		clientDirectoryPage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
		commonMethods = new CommonMethods(driver);
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
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
		logStep("Create Duplicate Branding");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Duplicate brand has been successfully created");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Create Duplicate Branding Template");
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Click on  YesDelete option");
		brandingTemplatesPage.clickOnYesDelete();
		String actualMessageOne = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessageOne, "Success! Brand has been successfully deleted");
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Delete confirmation Popup")
	public void testForDeleteConfirmationPopup() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
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