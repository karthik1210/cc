//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class AssociateDeassociateSolutionsOnResourceDetailsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String resourceName, firstName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			getClientSystementErpriseandPractice();
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Verify chat solutions activated in a practice are associated and deasociate in resource details page")
	public void testAssociateDeassociateChatSolutionsWithResource() throws Exception {
		ngHierarchyClientCreate();
		addPurchaseSolutionAtPracticeLevel();
		createProvider();
		createResource();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().verifyConnectedSolutionsLabel();
		ccui.resourceDetailsPage().clickOnChatToggle();
		ccui.resourceDetailsPage().clickOffChatToggle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, dependsOnMethods = "testAssociateDeassociateChatSolutionsWithResource", description = "Verify Engage solutions activated in a practice are associated and deasociate in resource details page")
	public void testAssociateDeassociateEngageSolutionsWithResource() throws Exception {
		ngHierarchyClientCreate();
		searchResource();
		ccui.resourceDetailsPage().clickOnEngageToggle();
		ccui.resourceDetailsPage().clickOffEngageToggle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, dependsOnMethods = "testAssociateDeassociateChatSolutionsWithResource", description = "Verify Portal solutions activated in a practice are associated and deasociate in resource details page")
	public void testAssociateDeassociatePortalSolutionsWithResource() throws Exception {
		ngHierarchyClientCreate();
		searchResource();
		ccui.resourceDetailsPage().clickOnPortalToggle();
		ccui.resourceDetailsPage().clickOffPortalToggle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, dependsOnMethods = "testAssociateDeassociateChatSolutionsWithResource", description = "Verify Pay solutions activated in a practice are associated and deasociate in resource details page")
	public void testAssociateDeassociatePaySolutionsWithResource() throws Exception {
		ngHierarchyClientCreate();
		searchResource();
		ccui.resourceDetailsPage().clickOnPayToggle();
		ccui.resourceDetailsPage().clickOffPayToggle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 4, dependsOnMethods = "testAssociateDeassociateChatSolutionsWithResource", description = "Verify PSS solutions activated in a practice are associated and deasociate in resource details page")
	public void testAssociateDeassociatePssSolutionsWithResource() throws Exception {
		ngHierarchyClientCreate();
		searchResource();
		ccui.resourceDetailsPage().clickOnPSSToggle();
		ccui.resourceDetailsPage().clickOffPSSToggle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 5, dependsOnMethods = "testAssociateDeassociateChatSolutionsWithResource", description = "Verify VertualVisit solutions activated in a practice are associated and deasociate in resource details page")
	public void testAssociateDeassociateVirtualVisitSolutionsWithResource() throws Exception {
		ngHierarchyClientCreate();
		searchResource();
		ccui.resourceDetailsPage().clickOnVirtualVisitToggle();
		ccui.resourceDetailsPage().clickOffVirtualVisitToggle();
		runOnce = false;
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
			ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		}
	}

	public void createProvider() throws Exception {
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.addNewProviderPage = new AddNewProviderPage(driver);
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Create new Provider using valid test data");
		firstName = "Provi" + CCUtils.generateAlphaNumericString(10);
		String lastName = CCUtils.generateAlphaNumericString(5);
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		ccui.addNewProviderPage().verifyAddButtonIsEnabled();
		ccui.addNewProviderPage().clickOnAddProviderButton();
		ccui.addNewProviderPage().enterFirstName(firstName);
		ccui.addNewProviderPage().enterLastName(lastName);
		ccui.addNewProviderPage().enterCredentials(credentials);
		ccui.addNewProviderPage().enterExternalId(externalId);
		ccui.addNewProviderPage().selectGenderValue("Male");
		ccui.addNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.addNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
		logStep("Verify Providers And Resources tab is displayed");
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
	}

	public void createResource() throws Exception {
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.addNewResourcePage().clickOnResourcesTab();
		ccui.addNewResourcePage().verifyAddButtonIsEnabled();
		ccui.addNewResourcePage().clickOnAddResourceButton();
		logStep("Create new Resources using valid test data");
		resourceName = "Resourr" + CCUtils.generateAlphaNumericString(10);
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.addNewResourcePage().enterResourceName(resourceName);
		ccui.addNewResourcePage().enterExternalId(externalIds);
		ccui.addNewResourcePage().selectResourceType("Person");
		ccui.addNewResourcePage().enterConnectedProvider(firstName);
		ccui.addNewResourcePage().clickFirstInAutoSuggestion();
		ccui.addNewResourcePage().verifySaveAndAddButtonIsEnabled();
		ccui.addNewResourcePage().clickOnSaveAndAddButton();
	}

	public void addPurchaseSolutionAtPracticeLevel() throws Exception {
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPortalSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPSSSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		runOnce = false;
	}

	public void searchResource() throws Exception {
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.addNewProviderPage = new AddNewProviderPage(driver);
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.addNewResourcePage().clickOnResourcesTab();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().verifyConnectedSolutionsLabel();
		runOnce = false;
	}

}
