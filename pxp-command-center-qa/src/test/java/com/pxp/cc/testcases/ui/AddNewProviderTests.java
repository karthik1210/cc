// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddNewProviderTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	String clientName, clientSapNumber, externalId;
	public AddOrgNodePage addOrgNodePage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClient();
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(clientName);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		createAgnosticPracticeNode();
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
	}

	public void createClient() throws NullPointerException, InterruptedException {
		logStep("Select create client button from new dropdown");
		createClientPage = new CreateClientPage(driver);
		commonMethods = new CommonMethods(driver);
		createClientPage.clickCreateClient();
		logStep("Enter valid details in mandatory fields");
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20)).trim();
		clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20)).trim();
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
	}

	public void createAgnosticPracticeNode() throws Exception {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.addNewSystem("SystemDemo");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		addOrgNodePage.addNewPractice("PracticeForDemo");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify Add New Provider page UI")
	public void testAddNewProviderPageUi() throws Exception {
		logStep("Verify Add a provider page UI");
		addNewProviderPage.VerifyAddNewProviderPageUi();
		logStep("Verify Maximum characters functionality for input fields");
		addNewProviderPage.enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(addNewProviderPage.getMaximumCharacterErrorForFirstNameField(),
				"Maximum characters reached");
		addNewProviderPage.enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(addNewProviderPage.getMaximumCharacterErrorForLastNameField(),
				"Maximum characters reached");
		addNewProviderPage.enterExternalId(CCUtils.generateAlphaNumericString(51));
		CCUtils.assertEquals(addNewProviderPage.getMaximumCharacterErrorForExternalIdField(),
				"Maximum characters reached");
		addNewProviderPage.enterCredentials(CCUtils.generateAlphaNumericString(51));
		CCUtils.assertEquals(addNewProviderPage.getMaximumCharacterErrorForCredentialField(),
				"Maximum characters reached");
		logStep("Enter invalid value gender field and verify error message");
		addNewProviderPage.clickOnGenderDropdown();
		addNewProviderPage.enterGender("abcdef");
		CCUtils.pressEnterKey();
		addNewProviderPage.verifyNoItemsFound();
		logStep("Verify Cancel button functionality on add a provider page");
		addNewProviderPage.clickOnCancelButton();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Verify Cross button functionality on add a provider page");
		addNewProviderPage.clickOnAddProviderButton();
		addNewProviderPage.clickOnCrossIcon();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Enter invalid first name and last name in firestName and lastName field");
		addNewProviderPage.clickOnAddProviderButton();
		addNewProviderPage.enterFirstName(testData.getProperty("invalid.user.first.name.one"));
		CCUtils.assertEquals(addNewProviderPage.getValidErrorForFirstNameField(), "Please enter valid first name");
		addNewProviderPage.enterLastName(testData.getProperty("invalid.user.last.name.one"));
		CCUtils.assertEquals(addNewProviderPage.getValidErrorForLastNameField(), "Please enter valid last name");
		
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create new provider using valid test data")
	public void testAddNewProviderFunctionalityUsingValidData() throws Exception {
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
		commonMethods.verifyToasterMessage("Success! New Provider has been successfully created");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, enabled = false, description = "Verify error message is displayed for existing external id")
	public void testErrorMessageForExistingExternalId() throws Exception {
		logStep("Enter existing external id in externalId field and verify error message");
		addNewProviderPage.enterExternalId(externalId);
		CCUtils.assertEquals(addNewProviderPage.getUniqueErrorMessageForExternalIdField(),
				"External Id already in use");
	}
}