// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class EditClientDetailsTests extends CommandCenterGenericTest {
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	public NGHierarchyClientCreation ngHierarchyClientCreation;
	public GeneralSettingsPage generalSettingsPage;
	public ClientDirectoryPage clientDirectoryPage;
	public AddOrgNodePage addOrgNodePage;
	String clientName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to command center portal");
		cchomepage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));

		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		commonMethods = new CommonMethods(driver);
		ngHierarchyClientCreation = new NGHierarchyClientCreation(driver);
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSap = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientType = testData.getProperty("new.client.type");
		String timeZone = testData.getProperty("new.client.timezone");
		ngHierarchyClientCreation.createNewActiveClient(clientName, clientSap, clientType, timeZone);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify Edit button is present for client node and cancel button functionality")
	public void testCanelFunctionality() throws Exception {
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Setting page");
		String expText = generalSettingsPage.getEditButtonText();
		CCUtils.assertEquals(expText, "EDIT");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("change details in client name & sap id");
		String newClientName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(10);
		generalSettingsPage.enterNewClientName(newClientName);
		String newClientSap = CCUtils.generateAlphaNumericStringIncludingSpecialChar(10);
		generalSettingsPage.enterNewClientSap(newClientSap);
		generalSettingsPage.clickCancel();
		logStep("Verifying old values are retained after changes are cancelled ");
		String oldClientName = generalSettingsPage.getDuplicateClientName();
		String oldClientSap = generalSettingsPage.getDuplicateSap();
		generalSettingsPage.clickEditButtonOfClient();
		CCUtils.assertTrue(!oldClientName.equals(newClientName), "Old and new value for client name are same");
		CCUtils.assertTrue(!oldClientSap.equals(newClientSap), "Old and new value for client sap id are same");

	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify maximum character limit for client name & sap id")
	public void testMaximumCharcterError() throws Exception {

		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Verifying Edit button is displayed ");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("Verifying maximum charcter error for client name ");
		generalSettingsPage.enterNewClientName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		String actualNameError = generalSettingsPage.getErrorMessage();
		CCUtils.assertEquals(actualNameError, "Maximum characters reached");
		generalSettingsPage.clearClientNameField();
		generalSettingsPage.enterNewClientName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(100));
		logStep("Verifying error message is not displayed when input is of 100 charcters  for client name");
		generalSettingsPage.verifyNoErrorPresent();
		logStep("Verifying maximum charcter error for SAP Id ");
		generalSettingsPage.enterNewClientSap(CCUtils.generateAlphaNumericStringIncludingSpecialChar(51));
		String actualSapError = generalSettingsPage.getErrorMessage();
		CCUtils.assertEquals(actualSapError, "Maximum characters reached");
		generalSettingsPage.clearSapField();
		logStep("Verifying error message is not displayed when input is of 50 charcters for SAP id ");
		generalSettingsPage.enterNewClientName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50));
		generalSettingsPage.verifyNoErrorPresent();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify unique value accepted for client name & sap id")
	public void tesUniqueNameAndSapError() throws Exception {

		clientDirectoryPage.searchNode(ccuser.duplicateClientName);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Setting page");
		String duplicateClientName = generalSettingsPage.getDuplicateClientName();
		String duplicateClientSap = generalSettingsPage.getDuplicateSap();
		generalSettingsPage.clickClientDirectory();
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage.clickEditButtonOfClient();
		logStep("Verifying duplicate error message is displayed error for client name ");
		generalSettingsPage.clearClientNameField();
		generalSettingsPage.enterNewClientName(duplicateClientName.trim());
		CCUtils.pressEnterKey();
		String actualNameError = generalSettingsPage.getErrorMessage();
		CCUtils.assertEquals(actualNameError, "Client name already in use");
		generalSettingsPage.enterNewClientName("new");
		generalSettingsPage.verifyNoErrorPresent();
		logStep("Verifying duplicate error message is displayed error for SAP Id ");
		generalSettingsPage.clearSapField();
		generalSettingsPage.enterNewClientSap(duplicateClientSap.trim());
		String actualSapError = generalSettingsPage.getErrorMessage();
		CCUtils.assertEquals(actualSapError, "SAP ID already in use");
		generalSettingsPage.enterNewClientSap("new");
		logStep("Verifying error message is not displayed  ");
		generalSettingsPage.verifyNoErrorPresent();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify client name & sap id are mandatory values")
	public void testClientNameAndSapAreMandatory() throws Exception {

		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Setting page");
		logStep("Verifying Edit button is displayed ");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("Verifying client name is mandatory");
		generalSettingsPage.clearClientNameField();
		String actualNameError = generalSettingsPage.getErrorMessage();
		CCUtils.assertEquals(actualNameError, "Client Name is required");
		generalSettingsPage.enterNewClientName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		logStep("Verifying error message is not displayed when input is provided for client name");
		generalSettingsPage.verifyNoErrorPresent();
		logStep("Verifying SAP Id is mandatory");
		generalSettingsPage.clearSapField();
		String actualSapError = generalSettingsPage.getErrorMessage();
		CCUtils.assertEquals(actualSapError, "SAP ID is required");
		generalSettingsPage.enterNewClientSap(CCUtils.generateAlphaNumericStringIncludingSpecialChar(11));
		logStep("Verifying error message is not displayed when input is provided for SAP id ");
		generalSettingsPage.verifyNoErrorPresent();
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify client edited details are reflected")
	public void testToVerifyEditedChanges() throws Exception {
		logStep("Change above details of client ");
		generalSettingsPage.clickEditButtonOfClient();
		String newClientName = CCUtils.generateAlphaNumericString(10);
		generalSettingsPage.enterClientName(newClientName);
		generalSettingsPage.changeStatus();
		logStep("Verify success toaster message");
		generalSettingsPage.verifyToasterMessage("Changes updated successfully");

	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify cltimezone is not mandatory while editing & saving client detail")
	public void testTimeZoneIsNotMandatory() throws Exception {

		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Setting page");
		logStep("Change timezone value to Select");
		String previousTimeZone = generalSettingsPage.getClientTimezone();
		logStep("Timezone for client ' " + ccuser.searchClientInClientDirectory + " ' is :" + previousTimeZone);
		generalSettingsPage.clickEditButtonOfClient();
		createClientPage = new CreateClientPage(driver);
		generalSettingsPage.clickTimeZoneDropdown();
		createClientPage.selectTimeZone("Select");
		logStep("Save timezone changed made");
		generalSettingsPage.clickSave();
		logStep("Changing client timezone to old values");
		generalSettingsPage.clickEditButtonOfClient();
		generalSettingsPage.clickTimeZoneDropdown();
		createClientPage.selectTimeZone(previousTimeZone);
		generalSettingsPage.clickSave();
	}
}
