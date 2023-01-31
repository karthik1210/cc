//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class AppointmentTypesDetailsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
	String firstName = "New"
			+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
	String lastName = CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6))
			.concat("'s");
	String resourceName, AppointmentTypeName, providerFirstName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.getClientDirectoryPage().searchAndSelectClientNameInClientTable("Qa_test_client_01m4%kcs6t7$>0");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Appointment Types Info section, Display Info Section, Connected Solutions section and Connected Scheduling Resources section")
	public void testAppTypeInfoSectionDisplayInfoConnectedSolutionsAndConnectedResourcesSection() throws Exception {
		addPurchaseSolutionAtPracticeLevel();
		ccui.getAppointmentTypePage().verifyNavigationAppointmentTypeTile();
		ccui.getAppointmentTypePage().verifyAppointmentTypesTitle();
		ccui.getAppointmentTypePage().searchAppointmentTypesAndVerifyResult("S Appointment");
		ccui.getAppointmentTypePage().selectSearchedAppType();
		ccui.getAppointmentTypesDetailsPage().verifyAllAppointmentTypesDetailsTitle();
		logStep("PXPP-13373 Verify Connected Solutions Section is displayed on Appointment Type Details page");
		ccui.getAppointmentTypesDetailsPage().verifyConnectedSolutionsTitle();
		logStep("PXPP-13376, PXPP-13377 Verify functionality of Toggle button for solution list in connected solutions section");
		ccui.getAppointmentTypesDetailsPage().clickOnEngageToggle();
		ccui.getAppointmentTypesDetailsPage().clickOffEngageToggle();
		ccui.getAppointmentTypesDetailsPage().clickOnPayToggle();
		ccui.getAppointmentTypesDetailsPage().clickOffPayToggle();
		ccui.getAppointmentTypesDetailsPage().clickOnPortalToggle();
		ccui.getAppointmentTypesDetailsPage().clickOffPortalToggle();
		ccui.getAppointmentTypesDetailsPage().clickOnPSSToggle();
		ccui.getAppointmentTypesDetailsPage().clickOffPSSToggle();
		ccui.getAppointmentTypesDetailsPage().clickOnVirtualVisitToggle();
		ccui.getAppointmentTypesDetailsPage().clickOffVirtualVisitToggle();
		ccui.getAppointmentTypesDetailsPage().verifyAllAppointmentTypesDetailsTitle();
		logStep("PXPP-13029 Verify Appointment Type ID, Appointment Categories, command center Id, Visit duration are view only fields in Appointment Type Info Section");
		ccui.getAppointmentTypesDetailsPage().verifyAppointmentTypeInfoSection();
		ccui.getAppointmentTypesDetailsPage().verifyAllAppointmentTypesDetailsTitle();
		ccui.getAppointmentTypesDetailsPage().verifyAppointmentTypeDisplayInfoSection();
		ccui.getAppointmentTypesDetailsPage().verifyAllAppointmentTypesDetailsTitle();
		logStep("PXPP-13047, PXPP-13051, PXPP-13025 Verify Edit button is displayed on Appointment Type Details page");
		ccui.getAppointmentTypesDetailsPage().verifyEditButtonLabel();
		ccui.getAppointmentTypesDetailsPage().clickOnEditButton();
		ccui.getAppointmentTypesDetailsPage().enterDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		ccui.getAppointmentTypesDetailsPage().enterDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		logStep("PXPP-13041 Verify changes are displayed in bottom pane under Appointment Type Display Info after changes are saved for selected practice");
		ccui.getAppointmentTypesDetailsPage().clickOnSaveButton();
		ccui.getAppointmentTypesDetailsPage().verifyAllAppointmentTypesDetailsTitle();
		ccui.getAppointmentTypesDetailsPage().verifyEditButtonLabel();
		ccui.getAppointmentTypesDetailsPage().clickOnEditButton();
		logStep("PXPP-13030, PXPP-13040 Verify field limit for Appointment Type Display Name (EN) field, (ES) field");
		ccui.getAppointmentTypesDetailsPage().verifyMaximumCharactersReachedErrorMessage(
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericString(101));
		logStep("PXPP-13026, PXPP-13045 Verify after clicking on CANCEL button , changes are not saved in solutions display info");
		ccui.getAppointmentTypesDetailsPage().clickOnCancel();
		ccui.getAppointmentTypesDetailsPage().verifyAllAppointmentTypesDetailsTitle();
		logStep("PXPP-13027 Verify after clicking on back arrow on appointment type details page user redirected to appointment type list page.");
		ccui.getAppointmentTypesDetailsPage().clickToBackButton();
		ccui.getAppointmentTypePage().verifyAppointmentTypesTitle();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		providerNavigation();
		createProvider();
		createResource();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getAppointmentTypePage().verifyNavigationAppointmentTypeTile();
		ccui.getAppointmentTypePage().verifyAppointmentTypesTitle();
		ccui.getAppointmentTypePage().searchAppointmentTypesAndVerifyResult("S Appointment");
		ccui.getAppointmentTypePage().selectSearchedAppType();
		ccui.getAppointmentTypesDetailsPage().verifyAllAppointmentTypesDetailsTitle();
		logStep("PXPP-13205 Verify Connected Scheduling Resources section is displayed on the Appointment Types page");
		ccui.getAppointmentTypesDetailsPage().verifyLabelConnectedSchedulingResources();
		logStep("PXPP-13206 Verify details of the Connected Scheduling Resources section displayed on the Appointment Types page");
		ccui.getAppointmentTypesDetailsPage().verifyConnectedSchedulingResourcesSection();
		ccui.getAppointmentTypesDetailsPage().clickToResourceAddButton();
		logStep("PXPP-13222 Verify details on the dialog box which opens after clicking on +ADD button");
		ccui.getAppointmentTypesDetailsPage().verifyAddResourcesPopupDetails();
		ccui.getAppointmentTypesDetailsPage().verifyAddResourceTitle();
		ccui.getAppointmentTypesDetailsPage().enterResourceNameinInputText(resourceName);
		ccui.getAppointmentTypesDetailsPage().clickToResourceCheckBox();
		logStep("PXPP-13208, PXPP-13207 Verify User is able to view all scheduling resources that have been associated or are using the appointment type");
		ccui.getAppointmentTypesDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
		ccui.getAppointmentTypesDetailsPage().verifyLabelConnectedSchedulingResources();
		logStep("PXPP-13209 Verify User have the ability to remove the scheduling resource from the Connected Scheduling Resources list on the appointment type page");
		ccui.getAppointmentTypesDetailsPage().clickToRemoveConnectionButton();
		ccui.getCommonMethods().verifyToasterMessage("Successfully removed resource");
		ccui.getAppointmentTypesDetailsPage().verifyMessageWithNoConnectedResource();
		ccui.getAppointmentTypesDetailsPage().clickToResourceAddButton();
		ccui.getAppointmentTypesDetailsPage().verifyAddResourceTitle();
		ccui.getAppointmentTypesDetailsPage().enterResourceNameinInputText("nonexistingresource");
		ccui.getAppointmentTypesDetailsPage().verifyNoItemsFound();
		ccui.getAppointmentTypesDetailsPage().clickOnCrossIcon();
		runOnce = false;
	}

	public void addPurchaseSolutionAtPracticeLevel() throws Exception {
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getGeneralSettingsPage().clickSystemOrgArrow();
		ccui.getGeneralSettingsPage().clickEnterpriseOrgArrow();
		ccui.getGeneralSettingsPage().clickPracticeOrg();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPortalSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPSSSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		runOnce = false;
	}

	@Test(enabled = false)
	public void providerNavigation() throws Exception {
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getGeneralSettingsPage().clickSystemOrgArrow();
		ccui.getGeneralSettingsPage().clickEnterpriseOrgArrow();
		ccui.getGeneralSettingsPage().clickPracticeOrg();
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
	}

	public String createProvider() throws Exception {
		providerFirstName = "Prov"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		ccui.getAddNewProviderPage().enterFirstName(providerFirstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Male");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		return providerFirstName;
	}

	public String createResource() throws Exception {
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
		logStep("Create new Resources using valid test data");
		resourceName = "New" + CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.getAddNewResourcePage().enterResourceName(resourceName);
		ccui.getAddNewResourcePage().enterExternalId(externalIds);
		ccui.getAddNewResourcePage().selectResourceType("Person");
		ccui.getAddNewResourcePage().enterConnectedProvider(providerFirstName);
		ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
		ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Resource has been successfully created");
		return resourceName;
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
			ccui.getNgHierarchyClientCreationPage().searchNode("Script_automation_client");
			ccui.getNgHierarchyClientCreationPage().clickSearchButton();
			ccui.getNgHierarchyClientCreationPage().clickSearchResult("Qa_test_client_01m4%kcs6t7$>0");
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
			ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow("Qa_automation_system_01");
			ccui.getPrivacyPolicySettingsPage().clickOnEnterpriseArrow("Qa_automation_enterprise_01");
			ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel("Qa_automation_practice_01");
		}
	}
}