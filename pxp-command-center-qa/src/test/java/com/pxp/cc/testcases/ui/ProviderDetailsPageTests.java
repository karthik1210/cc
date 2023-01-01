// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class ProviderDetailsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);

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

	String providerFirstName, locationName, resourceName;

	// AddProviderImageTests

	@Test(priority = 0, retryAnalyzer = RetryAnalyzer.class, description = "Verify provider image is successfully added and removed and Verify invalid file type error is displayed when uploading invalid format image file")
	public void testAddRemoveProviderImageFunctionalityAndInvalidFileTypeMessage() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13890, PXPP-13889 Verify Command Center users is able to upload a provider image - Command Center Admin");
		providerNavigation();
		createProvider();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		ccui.getProviderDetailsPage().uploadProviderImage();
		logStep("PXPP-13892 Verify Command Center users is able to remove the uploaded provider image through remove image button- Command Center Admin");
		ccui.getProviderDetailsPage().removeProviderImage();
		//ccui.getProviderDetailsPage().getSuccessToasterMessage();
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		logStep("PXPP-27618 Verify invalid file type error message is displayed when invalid image file types is used to upload image for provider - Command Center Admin");
		ccui.getProviderDetailsPage().uploadProviderInvalidImage();
		ccui.getProviderDetailsPage().verifyInvalidFileTypeErrorMessage();
		runOnce = false;
	}

	// ProvidersDetailsSolutionsDisplayInfoTests
	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify Provider Info section and Solutions Display Info Section")
	public void testProviderInfoSolutionsDisplayInfoSectionWebElements() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		logStep("PXPP-13894 Verify Provider Info section will display common/constant provider data is |Provider ID, Command Center ID, Staff ID| - Command Center Admin");
		ccui.getProviderDetailsPage().verifyProviderInfoSection();
		logStep("PXPP-14348, PXPP-14361, PXPP-14346, PXPP-14344 Verify details of Solutions Display Info section displayed on Provider Details page");
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		ccui.getProviderDetailsPage().verifySolutionsDisplayInfoSection();
		runOnce = false;
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify Save Functionality With Valid Details in input fields")
	public void testSaveFunctionalityWithValidDetails() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		logStep("PXPP-14400, PXPP-14354 Verify changes are displayed under Solutions Display Info after changes are saved for the Provider");
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		ccui.getProviderDetailsPage().verifyEditButtonLabel();
		ccui.getProviderDetailsPage().clickOnEditButton();
		ccui.getProviderDetailsPage().enterProviderDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		ccui.getProviderDetailsPage().enterProviderDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		ccui.getProviderDetailsPage().enterPhoneNumber(String.valueOf(CCUtils.generateMobileNumber()));
		ccui.getProviderDetailsPage().enterEmailAddress(CCUtils.generateNewEmail(12));
		ccui.getProviderDetailsPage().clickOnSaveButton();
		ccui.getProviderDetailsPage().verifyUpdateToasterMessage("Provider Details updated successfully!");
		logStep("PXPP-14349, PXPP-14351, PXPP-14353, PXPP-14352 Verify field limit for Provider Display Name (EN) field, Provider Display Name (ES) field, Phone Number field, Email Address field");
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		ccui.getProviderDetailsPage().verifyEditButtonLabel();
		ccui.getProviderDetailsPage().clickOnEditButton();
		ccui.getProviderDetailsPage().verifyMaximumCharactersReachedErrorMessage(
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(10), CCUtils.generateAlphaNumericString(10));
		logStep("Verify cancel button functionality");
		ccui.getProviderDetailsPage().clickOnCancel();
		runOnce = false;
	}

	// AssociateDeassociatePracticesOnProviderDetailsPageTests

	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Verify associate and deassociate practice for created provider within practice")
	public void testToAssociateDeassociatePracticeForProvider() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-14178 Verify User is able to enable or disable the Provider at the single Practice.");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		logStep("PXPP-14084 Verify User is able click on Providers, Resources & Staff tab on the top menu  OR Providers & Resources tile on Client's General Settings page.");
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("PXPP-14091 Verify User is able to search Provider on the Providers, Resources & Staff page");
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		logStep("PXPP-14100 Verify functionality of Toggle button displayed in PRACTICE SPECIFIC SETTINGS section ");
		ccui.getProviderDetailsPage().clickOnToggleButton();
		logStep("Verify success toaster message to associate practice");
		ccui.getProviderDetailsPage().verifyToasterMessage("Success! New practice has been successfully unassigned");
		ccui.getProviderDetailsPage().clickToBackButton();
		ccui.getSearchProviderPage().enterProviderInSearchTextBox(providerFirstName);
		List<String> listOfProviderName = new ArrayList<String>();
		listOfProviderName.add(providerFirstName);
		ccui.getSearchProviderPage().clickToEnterpriseFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getSearchProviderPage().clickToPracticeFilter();
		ccui.getSearchProviderPage().selectPracticeFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getSearchProviderPage().clickToFirstPracticeOption();
		ccui.getSearchProviderPage().clickToApplyFilterButton();
		ccui.getSearchProviderPage().verifySearchProviderFound(providerFirstName);
		ccui.getSearchProviderPage().verifyActiveStatus();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		ccui.getProviderDetailsPage().clickToEntepriseArrow();
		ccui.getProviderDetailsPage().clickOnToggleButton();
		logStep("Verify success toaster message to deassociate practice");
		ccui.getProviderDetailsPage().verifyToasterMessage("Success! New practice has been successfully assigned");
		ccui.getProviderDetailsPage().clickToBackButton();
		ccui.getSearchProviderPage().enterProviderInSearchTextBox(providerFirstName);
		List<String> listOfProvidersName = new ArrayList<String>();
		listOfProvidersName.add(providerFirstName);
		ccui.getSearchProviderPage().clickToEnterpriseFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getSearchProviderPage().clickToPracticeFilter();
		ccui.getSearchProviderPage().selectPracticeFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getSearchProviderPage().clickToFirstPracticeOption();
		ccui.getSearchProviderPage().clickToApplyFilterButton();
		ccui.getSearchProviderPage().verifyInactiveStatus();
		runOnce = false;
	}

	// AddRemoveConnectedResourcesInProvidersDetailsPageTests

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Verify connected scheduling resources section, Add Resources popup")
	public void testConnectedSchedulingResourcesSectionAndAddLocationsPopupWebElements() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		logStep("PXPP-25946, PXPP-25948, PXPP-25947, PXPP-25944 Verify that Connected Scheduling Resources container is displayed on Provider Details page  - Command Center Admin");
		ccui.getProviderDetailsPage().verifyLabelConnectedSchedulingResources();
		ccui.getProviderDetailsPage().verifyConnectedSchedulingResourcesSection();
		ccui.getProviderDetailsPage().clickToResourceAddButton();
		ccui.getProviderDetailsPage().verifyAddResourcesPopupDetails();
		ccui.getProviderDetailsPage().verifyLabelConnectedSchedulingResources();
		runOnce = false;
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "verify add and remove connected scheduling resources functionality on provider details page")
	public void testAddRemoveConnectedSchedulingResources() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		createResource();
		ccui.getResourceAndStaffSearchPage().clickToProvidersTab();
		ccui.getResourceAndStaffSearchPage().enterProviderInSearchTextbox(providerFirstName);
		ccui.getResourceAndStaffSearchPage().clickToSearchButton();
		ccui.getResourceAndStaffSearchPage().verifySearchProviderFound(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		ccui.getProviderDetailsPage().verifyLabelConnectedSchedulingResources();
		ccui.getProviderDetailsPage().verifyConnectedResourceInWebTable();
		ccui.getProviderDetailsPage().clickToRemoveConnectionButton();
		ccui.getCommonMethods().verifyToasterMessage("Successfully removed resources");
		logStep("PXPP-25953 Verify newly added Connected Scheduling Resource name is reflected when the user has added via +ADD button - Command Center Admin");
		ccui.getProviderDetailsPage().clickToResourceAddButton();
		ccui.getProviderDetailsPage().verifyAddResourceTitle();
		ccui.getProviderDetailsPage().enterResourceNameinInputText(resourceName);
		ccui.getProviderDetailsPage().clickToResourceCheckBox();
		ccui.getProviderDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
		ccui.getProviderDetailsPage().verifyLabelConnectedSchedulingResources();
		logStep("PXPP-25954 PXPP-25955 Verify existing Connected Resource name should not display when user has removed using cross(x) button infront of Connected Resource- Command Center Admin");
		ccui.getProviderDetailsPage().verifyConnectedResourceInWebTable();
		ccui.getProviderDetailsPage().clickToRemoveConnectionButton();
		ccui.getCommonMethods().verifyToasterMessage("Successfully removed resources");
		ccui.getProviderDetailsPage().verifyMessageWithNoConnectedResource();
		logStep("PXPP-25950 Verify all Resource names associated with selected Provider are listed out under Resource Name label in Connected Scheduling Resources container - Command Center Admin");
		ccui.getProviderDetailsPage().clickToResourceAddButton();
		ccui.getProviderDetailsPage().verifyAddResourceTitle();
		logStep("PXPP-27619 Verify no items found error message when entered non existing resource in Add resource pop up - Command Center Admin");
		ccui.getProviderDetailsPage().enterResourceNameinInputText("nonexistingresource");
		ccui.getProviderDetailsPage().verifyNoItemsFound();
		runOnce = false;
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Verify Connected Locations section, Add Locations popup")
	public void testConnectedLocationsSectionAndAddLocationsPopupWebElements() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyProviderDetailsTitle();
		logStep("PXPP-26013, PXPP-26014 Verify the Connected Locations section details on Provider details page");
		ccui.getProviderDetailsPage().verifyConnectedLocationsSection();
		ccui.getProviderDetailsPage().clickToAddLocationButton();
		ccui.getProviderDetailsPage().verifyConnectedLocationsPopupDetails();
		runOnce = false;
	}

	// AddRemoveConnectedLocationsInProvidersDetailsPageTests //passed

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Verify add and remove locations functionality on provider details page")
	public void testAndAddRemoveLocationsOnProviderDetailsPage() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		createLocation();
		ccui.getSearchLocationDetailsPage().clickToLocation(locationName);
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getProviderDetailsPage().clickToEntepriseArrow();
		ccui.getProviderDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickToBackButton();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getResourceAndStaffSearchPage().verifyProvidersAndResourcesPageTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().verifyConnectedLocationsSectionLabel();
		logStep("PXPP-26016, PXPP-26018 Verify on  Connected Location section, user able to remove  the Location Name using the cross button associated with the Location Name - Command center user.");
		ccui.getProviderDetailsPage().clickToAddLocationButton();
		ccui.getProviderDetailsPage().enterTextInLocationTextBox(locationName);
		ccui.getProviderDetailsPage().clickToLocationCheckBox(locationName);
		ccui.getProviderDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
		ccui.getProviderDetailsPage().verifyConnectedLocationInWebTable();
		ccui.getProviderDetailsPage().clickToRemoveConnectionBtn();
		ccui.getProviderDetailsPage().verifyNoDataDisplayConnectedLocationsMessage();
		runOnce = false;
	}

	// AssociateDeassociateSolutionsOnProvidersDetailsPageTests

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = " Associate Deassociate Solutions On Providers Details Page")
	public void testAssociateDeassociateSolutions() throws Exception {
		ngHierarchyClientCreate();
		addPurchaseSolutionAtPracticeLevel();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		logStep("PXPP-15209 Verify Connected Solutions Section is displayed on Provider Details page");
		ccui.getProviderDetailsPage().verifyConnectedSolutionsTitle();
		logStep("PXPP-15212, PXPP-15214, PXPP-15215, PXPP-15213 Verify functionality of Toggle button for solution list in connected solutions section.");
		ccui.getProviderDetailsPage().clickOnChatToggle();
		ccui.getProviderDetailsPage().clickOffChatToggle();
		ccui.getProviderDetailsPage().clickOnEngageToggle();
		ccui.getProviderDetailsPage().clickOffEngageToggle();
		ccui.getProviderDetailsPage().clickOnPayToggle();
		ccui.getProviderDetailsPage().clickOffPayToggle();
		ccui.getProviderDetailsPage().clickOnPortalToggle();
		ccui.getProviderDetailsPage().clickOffPortalToggle();
		ccui.getProviderDetailsPage().clickOnPSSToggle();
		ccui.getProviderDetailsPage().clickOffPSSToggle();
		ccui.getProviderDetailsPage().clickOnVirtualVisitToggle();
		ccui.getProviderDetailsPage().clickOffVirtualVisitToggle();
		runOnce = false;
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
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().verifyConnectedSolutionsLabel();
		runOnce = false;
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

	public void createLocation() throws Exception {
		ccui.getGeneralSettingsPage().clickToClientNode();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Click on Add button");
		ccui.addLocationsPage().verifyLocationsPageTitle();
		ccui.addLocationsPage().verifyAddLocationButton();
		ccui.addLocationsPage().clickOnAddLocationButton();
		locationName = "Location".concat(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterLocationName(locationName);
		ccui.addLocationsPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterAddressOne(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterAddressTwo(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterCity(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterZip(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.addLocationsPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.addLocationsPage().verifyToasterMessage("Success! New location has been successfully created");
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
}
