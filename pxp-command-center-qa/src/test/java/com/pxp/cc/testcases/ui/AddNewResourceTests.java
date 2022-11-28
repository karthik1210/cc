// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.objectmaps.AddNewResourcePage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddNewResourceTests extends CommandCenterGenericTest {
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	private AddNewResourcePage addNewResourcePage;
	String clientName, clientSapNumber, externalId, providerFirstName, resourceName, firstName;
	public AddOrgNodePage addOrgNodePage;
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
		String systemOrEnterprise = testData.getProperty("new.system");
		String timeZoneDisplayName = testData.getProperty("new.timezone.display.name");
		String solution = testData.getProperty("solution.forms");
		ngHierarchyClientCreation.createNewActiveClient(clientNam, clientSap, clientType, timeZone);
		ngHierarchyClientCreation.createNewActiveSystem(systemOrEnterprise, systemOrEnterprise);
		ngHierarchyClientCreation.createNewActiveEnterprise(systemOrEnterprise, systemOrEnterprise,
				timeZoneDisplayName);
		ngHierarchyClientCreation.createNewActivePractice(systemOrEnterprise, systemOrEnterprise, timeZoneDisplayName,
				solution);
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
	}

	public void createProvider() throws InterruptedException {
		logStep("Create new Provider using valid test data");
		firstName = "New"
				+ CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		addNewProviderPage.enterFirstName(firstName);
		addNewProviderPage.enterLastName(lastName);
		addNewProviderPage.enterCredentials(credentials);
		addNewProviderPage.enterExternalId(externalId);
		addNewProviderPage.selectGenderValue("Male");
		addNewProviderPage.verifySaveAndAddButtonIsEnabled();
		addNewProviderPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New Provider has been successfully created");
		logStep("Verify Providers And Resources tab is displayed");
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewResourcePage = new AddNewResourcePage(driver);
		addNewResourcePage.clickOnResourcesTab();
		addNewResourcePage.verifyAddButtonIsEnabled();
		addNewResourcePage.clickOnAddResourceButton();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create new Resource using valid test data")
	public void testAddNewResourcesFunctionalityUsingValidData() throws Exception {
		createProvider();
		logStep("Create new Resources using valid test data");
		resourceName = "New"
				+ CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		addNewResourcePage.enterResourceName(resourceName);
		addNewResourcePage.enterExternalId(externalIds);
		addNewResourcePage.selectResourceType("Person");
		addNewResourcePage.enterConnectedProvider(firstName);
		addNewResourcePage.clickFirstInAutoSuggestion();
		addNewResourcePage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New Resource has been successfully created");	
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new Resource popup")
	public void testAddNewResourcePopup() throws Exception {
		createProvider();
		logStep("Verify add new Resource popup");
		addNewResourcePage.verifyAddResourcePopupTitle();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify navigation to Resource page with Resource tab and Resource tile on General Settins page")
	public void testNavigationToResourcePageWithResourceTile() throws Exception {
		createProvider();
		logStep("Verify Cancel button functionality on add a resources page");
		addNewResourcePage.clickOnCancelButton();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Verify Cross button functionality on add a resources page");
		addNewResourcePage.clickOnAddResourceButton();
		addNewResourcePage.clickOnCrossIcon();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in ResourceName and verify error message")
	public void testHundredMaximumCharactersErrorMessageForInputFields() throws Exception {
		createProvider();
		logStep("Verify maximum character reached error message for ResourceName");
		addNewResourcePage.verifyHundredMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 50 characters in ExternalID fields and verify error message")
	public void testFiftyMaximumCharactersErrorMessageForInputFields() throws Exception {
		createProvider();
		logStep("Verify maximum character reached error message for EXternalId fields");
		addNewResourcePage.verifyFiftyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(51));
		logStep("Enter invalid value Resource type and verify error message");
		addNewResourcePage.clickOnResourceType();
		addNewResourcePage.enterResourceType("abcdef");
		CCUtils.pressEnterKey();
		addNewResourcePage.verifyNoItemsFound();
	}
}