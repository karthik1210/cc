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

public class EnableDisablePracticesOnResourceDetailsPageTests extends CommandCenterGenericTest {
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

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class,description = "Disable practices on resource details page Tests")

	public void testDisablePracticesOnResourceDetailsPage() throws Exception {
		ngHierarchyClientCreate();
		createProvider();
		createResource();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().clickOnToggleButton();
		ccui.resourceDetailsPage().verifyToasterMsgForDisable("Success! New practice has been successfully unassigned");
		runOnce = false;
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Enable practices on resource details page Tests")
	public void testEnablePracticesOnResourceDetailsPage() throws Exception {
		ngHierarchyClientCreate();
		createProvider();
		createResource();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().clickOnToggleButton();
		ccui.resourceDetailsPage().verifyToasterMsgForDisable("Success! New practice has been successfully unassigned");
		ccui.resourceDetailsPage().clickToEntepriseArrow();
		ccui.resourceDetailsPage().clickOnToggleButton();
		ccui.resourceDetailsPage().verifyToasterMsgForEnable("Success! New practice has been successfully assigned");
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
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Create new Provider using valid test data");
		firstName = "Provi" + CCUtils.generateAlphaNumericString(10);
		String lastName = CCUtils.generateAlphaNumericString(5);
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		ccui.getAddNewProviderPage().enterFirstName(firstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Male");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
	}

	public void createResource() throws Exception {
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
		logStep("Create new Resources using valid test data");
		resourceName = "Resourr" + CCUtils.generateAlphaNumericString(10);
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.getAddNewResourcePage().enterResourceName(resourceName);
		ccui.getAddNewResourcePage().enterExternalId(externalIds);
		ccui.getAddNewResourcePage().selectResourceType("Person");
		ccui.getAddNewResourcePage().enterConnectedProvider(firstName);
		ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
		ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
	}
}
