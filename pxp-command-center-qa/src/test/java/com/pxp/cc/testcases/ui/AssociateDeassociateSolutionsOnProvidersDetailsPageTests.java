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
import com.pxp.cc.objectmaps.ProviderDetailsPage;
import com.pxp.cc.objectmaps.ProviderResourceAndStaffSearchPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AssociateDeassociateSolutionsOnProvidersDetailsPageTests extends CommandCenterGenericTest {

	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	public ProviderResourceAndStaffSearchPage providerResourceStaffSearchPage;
	public ProviderDetailsPage providerDetailsPage;
	String clientName, systemName, agPracticeName, providerName;
	public AddOrgNodePage addOrgNodePage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(clientName);
		generalSettingsPage = new GeneralSettingsPage(driver);
		providerDetailsPage = new ProviderDetailsPage(driver);
	}

	public String createSystem() throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyAddSystemButton();
		addOrgNodePage.clickOnAddSystem();
		String sysName = "SystemDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.enterNewSystemDetails(sysName);
		return sysName;
	}

	public String createPracticeUnderSystem(String systemName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		addOrgNodePage.clickOnManageClient();
		logStep("Clicked on manage client button");
		addOrgNodePage.createPracticeWithinSystem(systemName);
		String practiceName = "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewPractice(practiceName);
		return practiceName;
	}

	public String createProvider() throws InterruptedException {
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
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
		return firstName;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify particular solutions activated in a practice is associated in providers details page")
	public void testAssociateSolutionWithProvider() throws Exception {
		systemName = createSystem();
		agPracticeName = createPracticeUnderSystem(systemName);
		generalSettingsPage.enterSearchText(agPracticeName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyPurchaseSolutionsSection();
		generalSettingsPage.clickToExpandPurchaseSolutions();
		generalSettingsPage.verifyAvailableToPurchaseSection();
		generalSettingsPage.clickToActivateButtonOfChatSolution();
		generalSettingsPage.clickToYesActivateButtonInActivateSolutions();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		providerName = createProvider();
		providerResourceStaffSearchPage = new ProviderResourceAndStaffSearchPage(driver);
		providerResourceStaffSearchPage.verifySearchProviderFound(providerName);
		providerDetailsPage = new ProviderDetailsPage(driver);
		providerDetailsPage.clickOnChatToggle();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify particular solutions activated in a practice is dis-associated in providers details page")
	public void testDisassociateSolutionWithProvider() throws Exception {
		systemName = createSystem();
		agPracticeName = createPracticeUnderSystem(systemName);
		generalSettingsPage.enterSearchText(agPracticeName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyPurchaseSolutionsSection();
		generalSettingsPage.clickToExpandPurchaseSolutions();
		generalSettingsPage.verifyAvailableToPurchaseSection();
		generalSettingsPage.clickToActivateButtonOfChatSolution();
		generalSettingsPage.clickToYesActivateButtonInActivateSolutions();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		providerName = createProvider();
		providerResourceStaffSearchPage = new ProviderResourceAndStaffSearchPage(driver);
		providerResourceStaffSearchPage.verifySearchProviderFound(providerName);
		providerDetailsPage = new ProviderDetailsPage(driver);
		providerDetailsPage.clickOnChatToggle();
		providerDetailsPage.clickOffChatToggle();
	}

}
