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
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(testData.getProperty("search.client.in.client.directory.qa"));
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		commonMethods = new CommonMethods(driver);

	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Delete brand for Inactive status at Client level")
	public void testDeleteBrandForInactiveStatusAtClientLevel() throws Exception {
		logStep("Verify Branding templates displayed at client level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		logStep("Create Duplicate Branding");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		logStep("Delete Brand");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		brandingTemplatesPage.clickOnYesDelete();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Brand has been successfully deleted");
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Delete brand for Inactive status at System level")
	public void testDeleteBrandForInactiveStatusAtSystemLevel() throws Exception {
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		logStep("Searching System node");
		generalSettingsPage.enterSearchText((testData.getProperty("search.system.in.genral.setting")));
		logStep("Verify Branding templates displayed at system level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		logStep("Create Duplicate Branding");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		logStep("Delete Brand");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		brandingTemplatesPage.clickOnYesDelete();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Brand has been successfully deleted");
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Delete brand for Inactive status at Entepeise level")
	public void testDeleteBrandForInactiveStatusAtEnterpriseLevel() throws Exception {
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		logStep("Searching enterprise node");
		generalSettingsPage.enterSearchText((testData.getProperty("search.enteprise.in.genral.setting")));
		logStep("Verify Branding templates displayed at enterprise level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		logStep("Create Duplicate Branding");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		logStep("Delete Brand");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		brandingTemplatesPage.clickOnYesDelete();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Brand has been successfully deleted");
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Delete brand for Inactive status at Practice level")
	public void testDeleteBrandForInactiveStatusAtPracticerLevel() throws Exception {
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		logStep("Searching practice node");
		generalSettingsPage.enterSearchText((testData.getProperty("search.practice.in.genral.setting")));
		logStep("Verify Branding templates displayed at practice level");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is Inactive");
		logStep("Create Duplicate Branding");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on Duplicate option");
		brandingTemplatesPage.clickOnDuplicate();
		logStep("Delete Brand");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
		brandingTemplatesPage.clickOnYesDelete();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Brand has been successfully deleted");
		logStep("Verify user redirected to client information page and branding template is displayed");
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to When the branding template status is active Edit option is available instead of Delete option  in the Kebab menu")
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
		logStep("Verify Branding templates displayed");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
		logStep("Click on kebab menu");
		brandingTemplatesPage.clickOnKebabMenu();
		logStep("Click on  delete option");
		brandingTemplatesPage.clickOnDelete();
		logStep("Verify Popup for delete");
		brandingTemplatesPage.verifyConfirmationPopupForDelete();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button on the delete confirmation Popup")
	public void testForToCheckFunctionalityOfCancelOnDeleteConfirmationPopup() throws Exception {
		logStep("Verify Branding templates displayed");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cross-icon on the delete confirmation Popup")
	public void testForToCheckFunctionalityOfCrossIconDeleteConfirmationPopup() throws Exception {
		logStep("Verify Branding templates displayed");
		brandingTemplatesPage = new BrandingTemplatesPage(driver);
		brandingTemplatesPage.VerifyBrandingTemplatesIsDisplayed();
		logStep("Verify branding status is inactive");
		brandingTemplatesPage.VerifyBrandingNameIsInactive();
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