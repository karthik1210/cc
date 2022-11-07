// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

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
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class ProvidersDetailsSolutionsDisplayInfoTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	private AddOrgNodePage addOrgNodePage;
	private ProviderDetailsPage providerDetailsPage;
	public String systemNameOne, agPracticeNameOne, providerFirstName, clientName;

	@BeforeMethod()
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
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
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

	public String createProvider() throws Exception {
		systemNameOne = createSystem();
		agPracticeNameOne = createAgnosticPractice(systemNameOne);
		generalSettingsPage.enterSearchText(agPracticeNameOne);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewProviderPage.clickOnAddProviderButton();
		providerFirstName = "New"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		addNewProviderPage.enterFirstName(providerFirstName);
		addNewProviderPage.enterLastName(lastName);
		addNewProviderPage.enterCredentials(credentials);
		addNewProviderPage.enterExternalId(externalId);
		addNewProviderPage.selectGenderValue("Male");
		addNewProviderPage.verifySaveAndAddButtonIsEnabled();
		addNewProviderPage.clickOnSaveAndAddButton();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		return providerFirstName;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Solutions Display Info Section")
	public void testSolutionsDisplayInfoSection() throws Exception {
		logStep("Verify Solutions Display Info Section");
		String providerName = createProvider();
		providerDetailsPage = new ProviderDetailsPage(driver);
		providerDetailsPage.clickSearchIcon();
		providerDetailsPage.enterSearchText(providerName);
		CCUtils.pressEnterKey();
		providerDetailsPage.selectProvider();
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.verifySolutionsDisplayInfoSection();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all input fields and verify save functionality")
	public void testSaveFunctionalityWithValidDetailsInAllInputFields() throws Exception {
		logStep("Enter valid details in all input fields and verify save functionality");
		String providerName = createProvider();
		providerDetailsPage = new ProviderDetailsPage(driver);
		providerDetailsPage.clickSearchIcon();
		providerDetailsPage.enterSearchText(providerName);
		CCUtils.pressEnterKey();
		providerDetailsPage.selectProvider();
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.verifyEditButtonLabel();
		providerDetailsPage.clickOnEditButton();
		providerDetailsPage.enterProviderDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		providerDetailsPage.enterProviderDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		providerDetailsPage.enterPhoneNumber(String.valueOf(CCUtils.generateMobileNumber()));
		providerDetailsPage.enterEmailAddress(CCUtils.generateNewEmail(12));
		providerDetailsPage.clickOnSaveButton();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for providerDisplayNameEn, providerDisplayNameEs, emailaddress, phoneNumber  fields and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify error message for providerDisplayNameEn, providerDisplayNameEs, emailaddress, phoneNumber");
		String providerName = createProvider();
		providerDetailsPage = new ProviderDetailsPage(driver);
		providerDetailsPage.clickSearchIcon();
		providerDetailsPage.enterSearchText(providerName);
		CCUtils.pressEnterKey();
		providerDetailsPage.selectProvider();
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.verifyEditButtonLabel();
		providerDetailsPage.clickOnEditButton();
		providerDetailsPage.verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericString(10),
				CCUtils.generateAlphaNumericString(10));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel button functionality")
	public void testCancelFunctionality() throws Exception {
		logStep("Verify cancel button functionality");
		String providerName = createProvider();
		providerDetailsPage = new ProviderDetailsPage(driver);
		providerDetailsPage.clickSearchIcon();
		providerDetailsPage.enterSearchText(providerName);
		CCUtils.pressEnterKey();
		providerDetailsPage.selectProvider();
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.verifyEditButtonLabel();
		providerDetailsPage.clickOnEditButton();
		providerDetailsPage.clickOnCancel();
	}
}
