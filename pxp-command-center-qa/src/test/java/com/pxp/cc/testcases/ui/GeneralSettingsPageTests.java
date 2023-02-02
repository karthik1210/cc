// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

import java.util.ArrayList;
import java.util.List;

public class GeneralSettingsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	public static String systemName, clientName, enterpriseName, practiceName;
	public ClientDirectoryPage clientDirectorPage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			clientDirectorPage = ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			getClientSystementErpriseandPractice();
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and verify error mesaages- 'Maximum characters reached' and 'Client name required' ")
	public void testEditClientCancelButtonAndClientRequiredAndMaxCharacterReachedError() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13069, PXPP-13071, PXPP-26024, PXPP-26025 Verify functionality client name edit error and cancel button functionality");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickEditButtonOfClient();
		String newClientName = CCUtils.generateAlphaNumericString(101);
		ccui.getGeneralSettingsPage().enterClientName(newClientName);
		String actualNameError = ccui.getGeneralSettingsPage().getErrorMessage();
		logStep("Verify Maximum charater reached error message");
		CCUtils.assertEquals(actualNameError, "Maximum characters reached");
		ccui.getGeneralSettingsPage().clearClientNameField();
		String errorMessage = ccui.getGeneralSettingsPage().getClientNameErrorMessage();
		logStep("Verify Client name required error message");
		CCUtils.assertEquals(errorMessage, "Client name is required");
		ccui.getGeneralSettingsPage().clickCancelButtonOfEditClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().verifyEditButtonIsVisible();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new system, enterprise, practice page web elements and max character reached error")
	public void testAddNewSystemEnterprisePracticePageWebElementsAndMaxCharacterReachedErrorMessage() throws Exception {
		logStep("PXPP-12565, PXPP-15268, PXPP-12569, PXPP-12571, PXPP-12572, PXPP-12573, PXPP-12746, PXPP-12749, PXPP-12755, PXPP-12769, PXPP-12756, PXPP-12757, PXPP-12765, PXPP-12763, PXPP-12758  verify adding orgs within a client in manage client page");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickClientDirectory();
		clientName = ccui.getClient();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		logStep("click to add new system button");
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Verify add new system page");
		ccui.getAddOrgNodePage().verifyAddNewSystemPage();
		logStep("Verify maximum character reached error message");
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
		ccui.getAddOrgNodePage().clickOnOrgCloseButton();
		systemName = ccui.getSystem();
		ccui.getAddOrgNodePage().clickOnExpandArrow(systemName);
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddEnterpriseButton();
		ccui.getAddOrgNodePage().clickOnAddEnterprise();
		ccui.getAddOrgNodePage().verifyAddNewEnterprisePage();
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
		ccui.getAddOrgNodePage().clickOnOrgCloseButton();
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		enterpriseName = ccui.getEnterprise();
		ccui.getAddOrgNodePage().clickOnExpandArrow(systemName);
		ccui.getAddOrgNodePage().clickOnExpandArrow(enterpriseName);
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		ccui.getAddOrgNodePage().verifyAddNewPracticePage();
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
		ccui.getAddOrgNodePage().clickOnOrgCloseButton();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create inactive and active client and all inactive orgs")
	public void testCreateInactiveAndActiveClientAndItsOrgs() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickClientDirectory();
		logStep("ceate inactive client");
		ccui.getInactiveClient();
		logStep("create inactive system");
		ccui.getInactiveSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("create active client and inactive orgs");
		ccui.getClient();
		logStep("create inactive system");
		ccui.getInactiveSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("create active client and system and inactive enterprise and practice");
		ccui.getClient();
		logStep("create active system");
		ccui.getSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("create active client,system and enterprise and inactive practice");
		ccui.getClient();
		logStep("create active system");
		ccui.getSystem();
		logStep("create active enterprise");
		ccui.getEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button at add org page and when user click on 'CLOSE' button User redirected to general setting page")
	public void testSearchCancelAndCloseButtonFunctionalityOfManageClientPage() throws Exception {
		logStep("PXPP-12548, PXPP-12561, PXPP-12565, PXPP-12772 verify cancel and close button of manage client page");
		ngHierarchyClientCreate();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifySearchIconAndSearchTextBox();
		ccui.getAddOrgNodePage().enterSearchValue(clientSystementErpriseandPractice.get("systemName"));
		ccui.getAddOrgNodePage().clickFirstInAutoSuggestion();
		ccui.getAddOrgNodePage().verifySearchOrgResult(clientSystementErpriseandPractice.get("systemName"));
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Click on cancel button");
		ccui.getAddOrgNodePage().clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Click on close button");
		ccui.getAddOrgNodePage().clickOnCloseButton();
		logStep("Verify after clicking on close button user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of edit client, system, enterprise, practice name and status")
	public void testEditClientSystemEnterprisePracticeNameAndStatus() throws Exception {
		logStep("PXPP-13064, PXPP-13063, PXPP-13061, PXPP-13059, PXPP-12739, PXPP-12741, PXPP-12742, PXPP-12744 edit client, system, enterprise, practice name and status");
		ngHierarchyClientCreate();
		logStep("Edit and save client name and status");
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickEditButtonOfClient();
		String newClientName = CCUtils.generateAlphaNumericString(10);
		ccui.getGeneralSettingsPage().enterClientName(newClientName);
		ccui.getGeneralSettingsPage().changeStatusToInactive();
		logStep("Verify success toaster message");
		ccui.getGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		logStep("Edit and save System name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		logStep("Edit and save Enterprise name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String entName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(entName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		logStep("Edit and save Practice name and status");
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String pracName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(pracName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify locations tab count value at general settings page for client, system, enterprise, practice should match with no of location in corresponding locations details page")
	public void tabCountforLocationsTabAtClientSystemEnterprisePracticeLevel() throws Exception {
		logStep("PXPP-28739, PXPP-28745, PXPP-28752, PXPP-28753, PXPP-28797, PXPP-28811, PXPP-28825, PXPP-28826 verify location tab count should match with no of locations in location search page");
		ngHierarchyClientCreate();
		logStep("create multiple locations at client level");
		addLocationPopUp();
		testAddNewLocationFunctionalityWithAllValidDetails();
		testAddNewLocationFunctionalityWithAllValidDetails();
		testAddNewLocationFunctionalityWithAllValidDetails();
		testAddNewLocationFunctionalityWithAllValidDetails();
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		String clientLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocations = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at client level");
		CCUtils.assertEquals(clientLocationTabCount, noOfLocations.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String systemLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocationsSystemFilter = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at system level");
		CCUtils.assertEquals(systemLocationTabCount, noOfLocationsSystemFilter.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching an enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String enterpriseLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocationsEnterpriseFilter = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at enterprise level");
		CCUtils.assertEquals(enterpriseLocationTabCount, noOfLocationsEnterpriseFilter.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String practiceLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocationsPracticeFilter = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at practice level");
		CCUtils.assertEquals(practiceLocationTabCount, noOfLocationsPracticeFilter.trim());
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify providers & resources tab count value at general settings page for client, system, enterprise, practice should match with no of providers and resources in corresponding providers/resources search page")
	public void tabCountforProvidersAndResourcesTabAtClientSystemEnterprisePracticeLevel() throws Exception {
		logStep("PXPP-28746, PXPP-28747, PXPP-28754, PXPP-28756, PXPP-28813, PXPP-28814, PXPP-28827, PXPP-28828 verify providers and resources tab count should match with no of providers and resources in providers/resources search page");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching an practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("create provider and resource at client level");
		String providerOne = testAddNewProviderFunctionalityUsingValidData();
		testAddNewResourcesFunctionalityUsingValidData(providerOne);
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		String clientProviderAndResourcesTabCount = ccui.getGeneralSettingsPage().getProviderAndResourcesCount();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTile();
		int noOfProviders = Integer.parseInt(ccui.getSearchProviderPage().getTotalNoOfProviders());
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		int noOfResources = Integer.parseInt(ccui.getAddNewResourcePage().getTotalNoOfResources());
		String noOfProviderAndResources = String.valueOf(noOfProviders + noOfResources);
		logStep("verifying the no of providers and resources at providers and resources tab in general settings and providers/resource details page at client level");
		CCUtils.assertEquals(clientProviderAndResourcesTabCount, noOfProviderAndResources.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String systemProviderAndResourcesTabCount = ccui.getGeneralSettingsPage().getProviderAndResourcesCount();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTile();
		int noOfProvidersSys = Integer.parseInt(ccui.getSearchProviderPage().getTotalNoOfProviders());
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		int noOfResourcesSys = Integer.parseInt(ccui.getAddNewResourcePage().getTotalNoOfResources());
		String noOfProviderAndResourcesSys = String.valueOf(noOfProvidersSys + noOfResourcesSys);
		logStep("verifying the no of providers and resources on providers and resources tab in general settings and providers and resources search page at system level");
		CCUtils.assertEquals(systemProviderAndResourcesTabCount, noOfProviderAndResourcesSys.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching an enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String entProviderAndResourcesTabCount = ccui.getGeneralSettingsPage().getProviderAndResourcesCount();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTile();
		int noOfProvidersEnt = Integer.parseInt(ccui.getSearchProviderPage().getTotalNoOfProviders());
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		int noOfResourcesEnt = Integer.parseInt(ccui.getAddNewResourcePage().getTotalNoOfResources());
		String noOfProviderAndResourcesEnt = String.valueOf(noOfProvidersEnt + noOfResourcesEnt);
		logStep("verifying the no of providers and resources on providers and resources tab in general settings and providers and resources search page at enterprise level");
		CCUtils.assertEquals(entProviderAndResourcesTabCount, noOfProviderAndResourcesEnt.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String pracProviderAndResourcesTabCount = ccui.getGeneralSettingsPage().getProviderAndResourcesCount();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTile();
		int noOfProvidersPrac = Integer.parseInt(ccui.getSearchProviderPage().getTotalNoOfProviders());
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		int noOfResourcesPrac = Integer.parseInt(ccui.getAddNewResourcePage().getTotalNoOfResources());
		String noOfProviderAndResourcesPrac = String.valueOf(noOfProvidersPrac + noOfResourcesPrac);
		logStep("verifying the no of providers and resources on providers and resources tab in general settings and providers and resources search page at practice level");
		CCUtils.assertEquals(pracProviderAndResourcesTabCount, noOfProviderAndResourcesPrac.trim());
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify Appointment type tab count value at general settings page for client, system, enterprise, practice should match with no of appointment types in appointment type search page")
	public void tabCountforAppointmenTypeTabAtClientSystemEnterprisePracticeLevel() throws Exception {
		logStep("PXPP-28750, PXPP-28734, PXPP-28757, PXPP-28812, PXPP-28824, PXPP-28829 Verify appointment type tab count should match with no of appointment type in appointment type search page");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientDirectorPage.verifyClientDirectioryPageTitle();
		clientDirectorPage.searchAndSelectClientNameInClientTable("Qa_test_client_01m4%kcs6t7$>0");
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		String clientAppointmentTypeTabCount = ccui.getGeneralSettingsPage().getAppointmenTypeCount();
		ccui.getGeneralSettingsPage().clickToAppointmentTypeTile();
		String noOfAppointmentTypes = ccui.getAppointmentTypePage().getTotalNoOfAppointmentTypes();
		logStep("verifying the no of appointment types at appointment types tab in general settings and appointment types details page at client level");
		CCUtils.assertEquals(clientAppointmentTypeTabCount, noOfAppointmentTypes.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText("Qa_automation_system_01");
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String systAppointmentTypeTabCount = ccui.getGeneralSettingsPage().getAppointmenTypeCount();
		ccui.getGeneralSettingsPage().clickToAppointmentTypeTile();
		String noOfAppointmentTypesSyst = ccui.getAppointmentTypePage().getTotalNoOfAppointmentTypes();
		logStep("verifying the no of appointment types at appointment types tab in general settings and appointment types details page at system level");
		CCUtils.assertEquals(systAppointmentTypeTabCount, noOfAppointmentTypesSyst.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching an enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText("Qa_automation_enterprise_01");
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String entAppointmentTypeTabCount = ccui.getGeneralSettingsPage().getAppointmenTypeCount();
		ccui.getGeneralSettingsPage().clickToAppointmentTypeTile();
		String noOfAppointmentTypesEnt = ccui.getAppointmentTypePage().getTotalNoOfAppointmentTypes();
		logStep("verifying the no of appointment types at appointment types tab in general settings and appointment types details page at enterprise level");
		CCUtils.assertEquals(entAppointmentTypeTabCount, noOfAppointmentTypesEnt.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText("Qa_automation_practice_01");
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String pracAppointmentTypeTabCount = ccui.getGeneralSettingsPage().getAppointmenTypeCount();
		ccui.getGeneralSettingsPage().clickToAppointmentTypeTile();
		String noOfAppointmentTypesPrac = ccui.getAppointmentTypePage().getTotalNoOfAppointmentTypes();
		logStep("verifying the no of appointment types at appointment types tab in general settings and appointment types details page at practice level");
		CCUtils.assertEquals(pracAppointmentTypeTabCount, noOfAppointmentTypesPrac.trim());
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify default text, search icon and search node functionality and test to select and verify Auto Suggestion list for symbol and number search in node name")
	public void testGeneralSettingScreenAndAutoSuggestionForSingleCharacterSearchAndNumberSearch() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-12909, PXPP-12910, PXPP-12911, PXPP-12921 Verify default text, autosuggestions ,search icon and search node functionality ");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Verifying default text");
		String actualDefaultText = ccui.getGeneralSettingsPage().defaultTextInSearch();
		CCUtils.assertEquals(actualDefaultText, "Search");
		ccui.getGeneralSettingsPage().verifyStatusIcon();
		logStep("Verifying Auto-suggestion list is displayed");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().verifyAutoSuggestionList(clientSystementErpriseandPractice.get("systemName"));
		logStep("PXPP-12967 Verify Auto suggestion list is displayed when searched text is entered - Command Center Admin");
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		createNewSystemForNumberSymbolSearch();
		logStep("Searching for nodes with single character name in General settings search");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		runOnce = false;
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to search system, enterprise, practice node and non-existing node functionality in Client's General settings page")
	public void testSystemEnterprisePracticeNodeSearchAndNonExistingNodeSearch() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-12979 Verify full & partial search can be performed on general setting screen - Command Center Admin");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verified general settings page title");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion(clientSystementErpriseandPractice.get("systemName"));
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage()
				.clickFirstInAutoSuggestion(clientSystementErpriseandPractice.get("enterpriseName"));
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion(clientSystementErpriseandPractice.get("practiceName"));
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		logStep("Searching invalid node under client in general settings page, to check client details are highlighted");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(ccuser.searchInvalidNodeInGeneralSetting);
		CCUtils.pressEnterKey();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Client, System, Enterprise, Practice level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtClientSystemEnterprisePracticeLevel()
			throws Exception {
		logStep("PXPP-28739, PXPP-28745 Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Client, System, Enterprise, Practice level");
		ngHierarchyClientCreate();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles is presents At Enterprise Level");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Enterprise Level");
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at practice Level");
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify activate all the solutions within practice from available to purchase section")
	public void testAddPurchaseSolutionAtPracticeLevel() throws Exception {
		logStep("PXPP-28231, PXPP-28232, PXPP-28233, PXPP-28242, PXPP-28243, PXPP-28244 verify activating solutions functionality");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPortalSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPSSSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPayMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToEngageMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPatientPortalMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPSSMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToVirtualVisitsAddOnsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify activate all the solutions within practice from available to purchase section add ons")
	public void testandVerifyAddPurchaseSolutionAddOnsAtPracticeLevel() throws Exception {
		logStep("PXPP-28279, PXPP-28280, PXPP-28281, PXPP-28282, PXPP-28294, PXPP-28295, PXPP-28297, PXPP-28297, PXPP-28297,PXPP-28298, PXPP-28308 verify add on activate and terminate functionality");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsAddOnsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutionsAddOns();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPatientChatAddOnsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify activate pop up web elements")
	public void testActivatePopupVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		logStep("PXPP-28231, PXPP-28232, PXPP-28233, PXPP-28242, PXPP-28243, PXPP-28244, PXPP-28245 Verify the fnctionality and web elements of activate pop up");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		String pracName = clientSystementErpriseandPractice.get("practiceName");
		log("name of practice : " + pracName);
		ccui.getGeneralSettingsPage().verifyActivatePopupTitle();
		ccui.getGeneralSettingsPage().verifyActivateTerminatePopupCancelButtonAndCrossIcon();
		ccui.getGeneralSettingsPage().cautionActivateMessage(pracName);
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate pop up web elements")
	public void testTerminatePopupVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		logStep("PXPP-28244, PXPP-28245, PXPP-28246, PXPP-28247, PXPP-28248, PXPP-28297, PXPP-28297, PXPP-28308 verify terminate functionality");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		String pracName = clientSystementErpriseandPractice.get("practiceName");
		log("name of practice : " + pracName);
		ccui.getGeneralSettingsPage().clickToVirtualVisitsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().verifyTerminatePopupTitle();
		ccui.getGeneralSettingsPage().verifyActivateTerminatePopupCancelButtonAndCrossIcon();
		ccui.getGeneralSettingsPage().cautionTerminateMessage(pracName);
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit pop up web elements and edit functionality")
	public void testEditPopupVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		logStep("PXPP-28231, PXPP-28233, PXPP-28243, PXPP-28244, PXPP-28245, PXPP-28249, PXPP-28275, PXPP-28309, PXPP-28310 verify edit pop up functionality");
		ngHierarchyClientCreate();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToVirtualVisitsMoreOptions();
		ccui.getGeneralSettingsPage().clickToEditOption();
		ccui.getGeneralSettingsPage().verifyEditPopupTitleAndStatusLabel();
		ccui.getGeneralSettingsPage().verifyEditPopupCancelAndCrossButton();
		ccui.getGeneralSettingsPage().verifySingleSelectDropdownAndItsOptions(listOfEditStatus());
		ccui.getGeneralSettingsPage().clickToSingleSelectDropdown();
		ccui.getGeneralSettingsPage().selectAStatusInEditPopup(testData.getProperty("status.implementation"));
		ccui.getGeneralSettingsPage().clickToSaveButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	public List<String> listOfEditStatus() {
		List<String> listOfStatusOptions = new ArrayList<String>();
		listOfStatusOptions.add(testData.getProperty("status.newly.purchased"));
		listOfStatusOptions.add(testData.getProperty("status.implementation"));
		listOfStatusOptions.add(testData.getProperty("status.on.hold"));
		listOfStatusOptions.add(testData.getProperty("status.live"));
		return listOfStatusOptions;
	}

	@Test(enabled = false)
	public void providerNavigation() throws Exception {
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
	}

	@Test(enabled = false)
	public String testAddNewProviderFunctionalityUsingValidData() throws Exception {
		providerNavigation();
		String firstName = "ProvFirst "
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = "ProvLast " + CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String credentials = testData.getProperty("provider.credentials");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		logStep("Enter valid details in mandatory fields to Create new Male provider and verify success toaster message");
		ccui.getAddNewProviderPage().enterFirstName(firstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Male");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
		return firstName;
	}

	@Test(enabled = false)
	public String testAddNewResourcesFunctionalityUsingValidData(String firstName) throws Exception {
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
		String resourceName = "New"
				+ CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.getAddNewResourcePage().enterResourceName(resourceName);
		ccui.getAddNewResourcePage().enterExternalId(externalIds);
		ccui.getAddNewResourcePage().selectResourceType("Person");
		ccui.getAddNewResourcePage().enterConnectedProvider(firstName);
		ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
		ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
		logStep(" PXPP-27259 Verify on Add a Resource dialog box Save & Add button is displayed");
		ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Resource has been successfully created");
		return resourceName;
	}

	@Test(enabled = false)
	public void addLocationPopUp() throws Exception {
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Click on Add button");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
	}

	@Test(enabled = false)
	public String testAddNewLocationFunctionalityWithAllValidDetails() throws Exception {
		logStep("Enter data in all input fields");
		ccui.getAddLocationPage().verifyAddLocationButton();
		ccui.getAddLocationPage().clickOnAddLocationButton();
		String locationName = "Loc".concat(CCUtils.generateAlphaNumericString(10));
		ccui.getAddLocationPage().enterLocationName(locationName);
		ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressTwo(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.getAddLocationPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddLocationPage().verifyToasterMessage("Success! New location has been successfully created");
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickToBackButton();
		ccui.getSearchLocationDetailsPage().verifyLocationTitle();
		return locationName;
	}

	private void createNewSystemForNumberSymbolSearch() throws NullPointerException, InterruptedException {
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Enter data in all input fields");
		systemName = "System" + CCUtils.generateAlphaNumericString(18).concat("12").concat("@");
		ccui.getAddOrgNodePage().enterSystemName(systemName);
		ccui.getAddOrgNodePage().addPmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		ccui.getAddOrgNodePage().selectInactiveRadiobutton();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
		ccui.getAddOrgNodePage().clickOnCloseButton();
	}

	@Test(enabled = false)
	public void ngHierarchyClientCreate() throws Exception {
		if (!runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			LoginPage loginPage = new LoginPage(driver, testData.getProperty("base.url"));
			loginPage.login(testData.getProperty("user.name"), testData.getProperty("user.password"));
			logStep("Select create client button from new dropdown");
			logStep("Searching a client in client directory");
			ccui.getNgHierarchyClientCreationPage().searchNode(clientSystementErpriseandPractice.get("clientNam"));
			ccui.getNgHierarchyClientCreationPage().clickSearchButton();
			ccui.getNgHierarchyClientCreationPage()
					.clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		}
	}
}
