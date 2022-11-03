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
import com.pxp.cc.objectmaps.SearchProviderPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class SearchProviderTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddNewProviderPage addNewProviderPage;
	private AddOrgNodePage addOrgNodePage;
	private SearchProviderPage searchProviderPage;
	public String systemNameOne, systemNameTwo, agPracticeNameOne, practiceNameTwo, enterpriseOne, practiceNameThree,
			providerFirstName;
	String agProOne, agProTwo, proOne, proTwo, proThree, proFour;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {

		logStep("Login to CC portal with url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);

		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid details in mandatory fields");
		String clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
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
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();

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
		 systemNameOne = createSystem(); agPracticeNameOne =
		 createAgnosticPractice(systemNameOne);
		 generalSettingsPage.enterSearchText(agPracticeNameOne);
		 generalSettingsPage.clickFirstInAutoSuggestion(); CCUtils.pressEnterKey();
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify the visibility and presence of web elements of search provider screen")
	public void testSearchProviderScreenWebElements() throws Exception {
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.verifySearchTextBoxAndPlaceholderIsDisplayed();
		searchProviderPage.verifySearchButtonIsDisplayed();
		searchProviderPage.verifyFilterLabel();
		searchProviderPage.verifySystemFilterLabelTextAndPlaceholder();
		searchProviderPage.verifyEnterpriseFilterLabelTextAndPlaceholder();
		searchProviderPage.verifyPracticeFilterLabelTextAndPlaceholder();
		searchProviderPage.verifySolutionFilterLabelTextAndPlaceholder();
		searchProviderPage.clearButtonDisplayed();
		searchProviderPage.clearApplyDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify after clicking hide button filter section should not get displayed")
	public void testFunctionalityOfHideFiltersButtonOnProviderScreen() throws Exception {
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.verifyFilterLabel();
		searchProviderPage.verifySystemFilterLabelTextAndPlaceholder();
		searchProviderPage.verifyEnterpriseFilterLabelTextAndPlaceholder();
		searchProviderPage.verifyPracticeFilterLabelTextAndPlaceholder();
		searchProviderPage.verifySolutionFilterLabelTextAndPlaceholder();
		searchProviderPage.clearButtonDisplayed();
		searchProviderPage.clearApplyDisplayed();
		searchProviderPage.clickToHideFiltersButton();
		searchProviderPage.verifyAllElementsOfFilterNotVisible();
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a provider")
	public void testSearchExistingProviderOnProviderScreen() throws Exception {
		String createProviderOne = createProvider();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.searchProviderAndVerifyResult(createProviderOne);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error message when searching non-existing provider")
	public void testErrorMessageWhenSearchingNonExistingProvider() throws Exception {
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.searchNonExistingProviderAndVerifyResult("NonExistingProvider");
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply system filter in search provider screen")
	public void testSearchExistingProviderByFilterSystem() throws Exception {
		 systemNameOne = createSystem();
		 agPracticeNameOne = createAgnosticPractice(systemNameOne);
		 generalSettingsPage.enterSearchText(agPracticeNameOne);
		 generalSettingsPage.clickFirstInAutoSuggestion();
		 CCUtils.pressEnterKey();
		 String createProviderOne = createProvider();
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.clickToSystemFilter();
		searchProviderPage.selectFilter(systemNameOne);
		searchProviderPage.clickToApplyFilterButton();
		searchProviderPage.verifySearchProviderFound(createProviderOne);
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply enterprise filter in search provider screen")
	public void testSearchExistingProviderByFilterEnterprise() throws Exception {
		 systemNameTwo = createSystem();
		 enterpriseOne=createEnterprise(systemNameTwo);
		 generalSettingsPage.enterSearchText(practiceNameTwo);
		 generalSettingsPage.clickFirstInAutoSuggestion();
		 CCUtils.pressEnterKey();
		 generalSettingsPage.enterSearchText(practiceNameThree);
		 generalSettingsPage.clickFirstInAutoSuggestion();
		 CCUtils.pressEnterKey();
		 String createProviderOne = createProvider();
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.clickToEnterpriseFilter();
		searchProviderPage.selectFilter(enterpriseOne);
		searchProviderPage.clickToApplyFilterButton();
		searchProviderPage.verifySearchProviderFound(createProviderOne);
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply practice filter in search provider screen")
	public void testSearchExistingProviderByFilterPractice() throws Exception {
		systemNameTwo = createSystem();
		enterpriseOne = createEnterprise(systemNameTwo);
		 generalSettingsPage.enterSearchText(practiceNameTwo);
	    generalSettingsPage.clickFirstInAutoSuggestion();
		 CCUtils.pressEnterKey();
		 generalSettingsPage.enterSearchText(practiceNameThree);
		 generalSettingsPage.clickFirstInAutoSuggestion();
		 CCUtils.pressEnterKey();
		 String createProviderOne = createProvider();
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		searchProviderPage = new SearchProviderPage(driver);
		searchProviderPage.clickToPracticeFilter();
		searchProviderPage.selectFilter(practiceNameTwo);
		searchProviderPage.clickToApplyFilterButton();
		searchProviderPage.verifySearchProviderFound(createProviderOne);
	}
}