//Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.ProviderDetailsPage;
import com.pxp.cc.objectmaps.SearchProviderPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AssociateDeassociatePracticesOnProviderDetailsPageTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	private AddOrgNodePage addOrgNodePage;
	private ProviderDetailsPage providerDetailsPage;
	private SearchProviderPage searchProviderPage;
	String systemNameOne, systemNameTwo, systemThree, systemFour, agPracticeNameOne, agPracticeNameTwo, practiceNameOne,
			practiceNameTwo, enterpriseOne, enterpriseTwo, agProviderOne, agProviderTwo, providerOne, providerTwo,
			clientName, sysName, providerFirstName;

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
	}

	public String createSystem() throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnAddSystem();
		sysName = "SystemDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.enterNewSystemDetails(sysName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(sysName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return sysName;
	}

	public String createEnterprise(String orgName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(orgName);
		String enterpriseName = "EnterpriseForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewEnterprise(enterpriseName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return enterpriseName;
	}

	public String createPractice(String orgSystemName, String orgEnterpriseName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(orgSystemName);
		addOrgNodePage.clickOnExpandArrow(orgEnterpriseName);
		String practiceName = "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewPractice(practiceName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return practiceName;
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
		createAgnosticPractice(sysName);
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching a practice");
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText("PracticeForDemo");
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewProviderPage.clickOnAddProviderButton();
		providerFirstName = "Pro"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		addNewProviderPage.enterFirstName(providerFirstName);
		addNewProviderPage.enterLastName(lastName);
		addNewProviderPage.enterCredentials(credentials);
		addNewProviderPage.enterExternalId(externalId);
		addNewProviderPage.selectGenderValue("Male");
		addNewProviderPage.verifySaveAndAddButtonIsEnabled();
		addNewProviderPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New Provider has been successfully created");
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		return providerFirstName;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify associate NG practice for created provider within practice")
	public void testToAssociateProviderForNGPractice() throws Exception {
		systemNameOne = createSystem();
		enterpriseOne = createEnterprise(systemNameOne);
		practiceNameOne = createPractice(systemNameOne, enterpriseOne);
		providerOne = createProvider();
		providerDetailsPage = searchProviderPage.clickToProvider(providerOne);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToSytemTab(systemNameOne);
		providerDetailsPage.clickToEnterpriseTabPracticeAndToggleOn(enterpriseOne, practiceNameOne);
		logStep("Verify success toaster message");
		providerDetailsPage.verifyToasterMessage("Success! New practice has been successfully assigned");
		providerDetailsPage.clickToBackButton();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.enterProviderInSearchTextBox(providerOne);
		List<String> listOfProviderName = new ArrayList<String>();
		listOfProviderName.add(providerOne);
		searchProviderPage.clickToEnterpriseFilter();
		searchProviderPage.selectFilter(enterpriseOne);
		searchProviderPage.clickToPracticeFilter();
		searchProviderPage.selectPracticeFilter(practiceNameOne);
		searchProviderPage.clickToFirstPracticeOption();
		searchProviderPage.clickToApplyFilterButton();
		searchProviderPage.verifySearchProviderFound(providerOne);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deassociate NG practice for created provider within practice")
	public void testToDeassociateProviderForNGPractice() throws Exception {
		systemNameTwo = createSystem();
		enterpriseTwo = createEnterprise(systemNameTwo);
		practiceNameTwo = createPractice(systemNameTwo, enterpriseTwo);
		providerTwo = createProvider();
		providerDetailsPage = searchProviderPage.clickToProvider(providerTwo);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToSytemTab(systemNameTwo);
		providerDetailsPage.clickToEnterpriseTabPracticeAndToggleOn(enterpriseTwo, practiceNameTwo);
		logStep("Verify success toaster message");
		providerDetailsPage.verifyToasterMessage("Success! New practice has been successfully unassigned");
		providerDetailsPage.clickToBackButton();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.enterProviderInSearchTextBox(providerTwo);
		List<String> listOfProviderName = new ArrayList<String>();
		listOfProviderName.add(providerTwo);
		searchProviderPage.clickToEnterpriseFilter();
		searchProviderPage.selectFilter(enterpriseTwo);
		searchProviderPage.clickToPracticeFilter();
		searchProviderPage.selectPracticeFilter(practiceNameTwo);
		searchProviderPage.clickToFirstPracticeOption();
		searchProviderPage.clickToApplyFilterButton();
		searchProviderPage.verifyNoDataDisplayErrorMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify associate agnostic practice for created provider within practice")
	public void testToAssociateProviderForAgnosticPractice() throws Exception {
		systemThree = createSystem();
		agPracticeNameOne = createAgnosticPractice(systemThree);
		agProviderOne = createProvider();
		providerDetailsPage = searchProviderPage.clickToProvider(providerOne);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToSytemTab(systemThree);
		providerDetailsPage.clickToPracticeTabAndToggleOn(agPracticeNameOne);
		logStep("Verify success toaster message");
		providerDetailsPage.verifyToasterMessage("Success! New practice has been successfully assigned");
		providerDetailsPage.clickToBackButton();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.enterProviderInSearchTextBox(agProviderOne);
		List<String> listOfProviderName = new ArrayList<String>();
		listOfProviderName.add(agProviderOne);
		searchProviderPage.clickToPracticeFilter();
		searchProviderPage.selectPracticeFilter(agPracticeNameOne);
		searchProviderPage.clickToFirstPracticeOption();
		searchProviderPage.clickToApplyFilterButton();
		searchProviderPage.verifySearchProviderFound(agProviderOne);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deassociate agnostic practice for created provider within practice")
	public void testToDeassociateProviderForAgnosticPractice() throws Exception {
		systemFour = createSystem();
		agPracticeNameTwo = createAgnosticPractice(systemFour);
		agProviderTwo = createProvider();
		providerDetailsPage = searchProviderPage.clickToProvider(agProviderTwo);
		providerDetailsPage.verifyProviderDetailsTitle();
		providerDetailsPage.clickToSytemTab(systemFour);
		providerDetailsPage.clickToPracticeTabAndToggleOff(agPracticeNameTwo);
		logStep("Verify success toaster message");
		providerDetailsPage.verifyToasterMessage("Success! New practice has been successfully unassigned");
		providerDetailsPage.clickToBackButton();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.enterProviderInSearchTextBox(agProviderTwo);
		List<String> listOfProviderName = new ArrayList<String>();
		listOfProviderName.add(agProviderTwo);
		searchProviderPage.clickToPracticeFilter();
		searchProviderPage.selectPracticeFilter(agPracticeNameTwo);
		searchProviderPage.clickToFirstPracticeOption();
		searchProviderPage.clickToApplyFilterButton();
		searchProviderPage.verifyNoDataDisplayErrorMessage();
	}
}
