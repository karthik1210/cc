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
import com.pxp.cc.objectmaps.ProviderDetailsPage;
import com.pxp.cc.objectmaps.ProviderResourceAndStaffSearchPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddRemoveConnectedResourcesInProvidersDetailsPageTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	public GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	private ProviderResourceAndStaffSearchPage providerResourceAndStaffPage;
	private AddNewResourcePage addNewResourcePage;
	private ProviderDetailsPage providerDetailsPage;
	private AddOrgNodePage addOrgNodePage;
	public String systemName, practiceName;

	@BeforeMethod
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		generalSettingsPage = new GeneralSettingsPage(driver);
		addNewProviderPage = new AddNewProviderPage(driver);
		providerResourceAndStaffPage = new ProviderResourceAndStaffSearchPage(driver);
		addNewResourcePage = new AddNewResourcePage(driver);
		providerDetailsPage = new ProviderDetailsPage(driver);
		commonMethods = new CommonMethods(driver);
	}

	public String createClient() throws NullPointerException, InterruptedException {
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
		return clientName;
	}

	public String createSystem() throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnAddSystem();
		String sysName = "SystemDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.enterNewSystemDetails(sysName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(sysName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return sysName;
	}

	public String createAgnosticPractice(String orgName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(orgName);
		String practiceName = "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewPractice(practiceName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return practiceName;
	}

	public String createProvider() {
		String firstName = "New"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
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
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New Provider has been successfully created");
		return firstName;
	}

	public String createResource(String providerName) throws Exception{
		addNewResourcePage = new AddNewResourcePage(driver);
		addNewResourcePage.verifyAddButtonIsEnabled();
		addNewResourcePage.clickOnAddResourceButton();
		String resourceName = "Resource"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(5));
		addNewResourcePage.enterResourceName(resourceName);
		String externalId = "externalId"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(5));
		addNewResourcePage.enterExternalId(externalId);
		addNewResourcePage.selectResourceType("Person");
		addNewResourcePage.enterConnectedProvider(providerName);
		addNewResourcePage.clickFirstInAutoSuggestion();
		addNewResourcePage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New Resource has been successfully created");
		return resourceName;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test can able to remove connected scheduling resources from providers details page")
	public void testRemoveResourceFromProviderDetailsPage() throws Exception {
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode("Testfavclient");
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Settings page");
		systemName = createSystem();
		practiceName = createAgnosticPractice(systemName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String actualData = generalSettingsPage.returnGeneralSettingTitle();
		CCUtils.assertEquals(actualData, practiceName);
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		providerResourceAndStaffPage = new ProviderResourceAndStaffSearchPage(driver);
		providerResourceAndStaffPage.verifyProvidersAndResourcesPageTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
		String providerName = createProvider();
		providerResourceAndStaffPage.clickToResourcesTab();
		String resourceName = createResource(providerName);
		providerResourceAndStaffPage.clickToProvidersTab();
		providerResourceAndStaffPage.enterProviderInSearchTextbox(providerName);
		providerResourceAndStaffPage.clickToSearchButton();
		providerResourceAndStaffPage.verifySearchProviderFound(providerName);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToSytemTab(systemName);
		providerDetailsPage.clickToPracticeTabAndToggleOn(practiceName);
		providerDetailsPage.verifyDesiredResourceInWebTable(resourceName);
		commonMethods.verifyToasterMessage("Successfully removed resources");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test can able to add connected scheduling resources from providers details page")
	public void testAddResourceFromProviderDetailsPage() throws Exception {
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode("Testfavclient");
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Settings page");
		systemName = createSystem();
		practiceName = createAgnosticPractice(systemName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String actualData = generalSettingsPage.returnGeneralSettingTitle();
		CCUtils.assertEquals(actualData, practiceName);
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		providerResourceAndStaffPage = new ProviderResourceAndStaffSearchPage(driver);
		providerResourceAndStaffPage.verifyProvidersAndResourcesPageTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
		String providerName = createProvider();
		providerResourceAndStaffPage.clickToResourcesTab();
		String resourceName = createResource(providerName);
		providerResourceAndStaffPage.clickToProvidersTab();
		providerResourceAndStaffPage.enterProviderInSearchTextbox(providerName);
		providerResourceAndStaffPage.clickToSearchButton();
		providerResourceAndStaffPage.verifySearchProviderFound(providerName);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToSytemTab(systemName);
		providerDetailsPage.clickToPracticeTabAndToggleOn(practiceName);
		providerDetailsPage.verifyDesiredResourceInWebTable(resourceName);
		commonMethods.verifyToasterMessage("Successfully removed resources");
		providerDetailsPage.clickToResourceAddButton();
		providerDetailsPage.verifyAddResourceTitle();
		providerDetailsPage.enterResourceNameinInputText(resourceName);
		providerDetailsPage.clickToResourceCheckBox();
		providerDetailsPage.clickToAddButtonInPopup();
		commonMethods.verifyToasterMessage("Changes updated successfully");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to validate add resources pop up elements")
	public void testAddResourcePopupWebElements() throws Exception {
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode("Testfavclient");
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Settings page");
		systemName = createSystem();
		practiceName = createAgnosticPractice(systemName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String actualData = generalSettingsPage.returnGeneralSettingTitle();
		CCUtils.assertEquals(actualData, practiceName);
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		providerResourceAndStaffPage = new ProviderResourceAndStaffSearchPage(driver);
		providerResourceAndStaffPage.verifyProvidersAndResourcesPageTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
		String providerName = createProvider();
		providerResourceAndStaffPage.clickToResourcesTab();
		String resourceName = createResource(providerName);
		providerResourceAndStaffPage.clickToProvidersTab();
		providerResourceAndStaffPage.enterProviderInSearchTextbox(providerName);
		providerResourceAndStaffPage.clickToSearchButton();
		providerResourceAndStaffPage.verifySearchProviderFound(providerName);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToResourceAddButton();
		providerDetailsPage.verifyAddResourceTitle();
		providerDetailsPage.verifyResourceLabel();
		providerDetailsPage.verifyCancelButton();
		providerDetailsPage.verifyAddButton();
		providerDetailsPage.verifyCrossIcon();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check no items found error message when enter non existing resource in Add resource pop up")
	public void testAddResourcePopupNoItemsFoundResourceListSuggestionMessage() throws Exception {
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode("Testfavclient");
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Settings page");
		systemName = createSystem();
		practiceName = createAgnosticPractice(systemName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String actualData = generalSettingsPage.returnGeneralSettingTitle();
		CCUtils.assertEquals(actualData, practiceName);
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		providerResourceAndStaffPage = new ProviderResourceAndStaffSearchPage(driver);
		providerResourceAndStaffPage.verifyProvidersAndResourcesPageTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
		String providerName = createProvider();
		providerResourceAndStaffPage.clickToResourcesTab();
		String resourceName = createResource(providerName);
		providerResourceAndStaffPage.clickToProvidersTab();
		providerResourceAndStaffPage.enterProviderInSearchTextbox(providerName);
		providerResourceAndStaffPage.clickToSearchButton();
		providerResourceAndStaffPage.verifySearchProviderFound(providerName);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToSytemTab(systemName);
		providerDetailsPage.clickToPracticeTabAndToggleOn(practiceName);
		providerDetailsPage.verifyDesiredResourceInWebTable(resourceName);
		commonMethods.verifyToasterMessage("Successfully removed resources");
		providerDetailsPage.clickToResourceAddButton();
		providerDetailsPage.verifyAddResourceTitle();
		providerDetailsPage.enterResourceNameinInputText("nonexistingresource");
		providerDetailsPage.verifyNoItemsFound();
		providerDetailsPage.verifyCancelButton();
		providerDetailsPage.verifyAddButton();
	}

}
