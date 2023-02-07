// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class ResourcesDetailsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String externalId;
	String firstName = "New"
			+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
	String lastName = CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6))
			.concat("'s");
	String resourceName, locationName;

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

	// AddResourceImageTests
	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify resources image is successfully added and removed and Verify invalid file type error is displayed when uploading invalid format image file")
	public void testAddRemoveResourceImageFunctionalityAndInvalidFileTypeMessage() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-21556, Verify Command Center users able to upload Resource image ");
		createResources();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		logStep("PXPP-21558, Verify Image can be common image file types to upload image for Resource  ");
		ccui.resourceDetailsPage().uploadResourcesImage();
		logStep("PXPP-21560 Verify Command Center users is able to remove the uploaded Resource image through REMOVE IMAGE button");
		ccui.resourceDetailsPage().removeResourceImage();
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().uploadResourceInvalidImage();
		ccui.resourceDetailsPage().verifyInvalidFileTypeErrorMessage();
		runOnce = false;
	}

	// ResourcesDetailsResourcesDisplayInfoTests
	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify Resources Info section and Resources Display Info Section")
	public void testResourcesInfoResourcesDisplayInfoSectionWebElements() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().verifyResourcesInfoSection();
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		logStep("PXPP-14499 Verify details of Resource Display Info section displayed on Resource Details page");
		ccui.resourceDetailsPage().verifyResourceDisplayInfoSection();
		runOnce = false;
	}

	// AssociateDeassociateSolutionsOnResourcesDetailsPageTests
	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify  solutions activated in a practice are associated and deassociate in resource details page")
	public void testAssociateDeassociateSolutionsWithResource() throws Exception {
		ngHierarchyClientCreate();
		addPurchaseSolutionAtPracticeLevel();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		logStep("PXPP-14528 Verify Connected Solutions Section is displayed on Resource Details page");
		ccui.resourceDetailsPage().verifyConnectedSolutionsLabel();
		logStep("PXPP-14530 Verify the Toggle button is present for every solution in list of connected solutions");
		ccui.resourceDetailsPage().clickOnChatToggle();
		ccui.resourceDetailsPage().clickOffChatToggle();
		ccui.resourceDetailsPage().clickOnEngageToggle();
		ccui.resourceDetailsPage().clickOffEngageToggle();
		ccui.resourceDetailsPage().clickOnPortalToggle();
		ccui.resourceDetailsPage().clickOffPortalToggle();
		ccui.resourceDetailsPage().clickOnPayToggle();
		ccui.resourceDetailsPage().clickOffPayToggle();
		ccui.resourceDetailsPage().clickOnPSSToggle();
		ccui.resourceDetailsPage().clickOffPSSToggle();
		ccui.resourceDetailsPage().clickOnVirtualVisitToggle();
		ccui.resourceDetailsPage().clickOffVirtualVisitToggle();
		runOnce = false;
	}

	// Connected Location Pop-Up
	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify Connected Locations section, Add Locations popup")
	public void testConnectedLocationsSectionAndAddLocationsPopupWebElements() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		logStep("PXPP-27233,PXPP-27227 Verify the Connected Locations section is displayed on Resource details page");
		ccui.resourceDetailsPage().verifyConnectedLocationsSection();
		ccui.resourceDetailsPage().clickToAddLocationButton();
		ccui.resourceDetailsPage().verifyConnectedLocationsPopupDetails();
		runOnce = false;
	}

	// AssociateDeassociatePracticesOnResourceDetailsPageTests
	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Verify associate and deassociate practice for created resource within practice")
	public void testToAssociateDeassociatePracticeForResource() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-14441 Verify User is able click on Providers, Resources & Staff tab on the top menu  OR Providers & Resources tile on Client's General Settings page");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		logStep("PXPP-14475 Verify User is able to enable or disable the Resource at the single Practice.");
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		logStep("PXPP-14474 Verify functionality of Toggle button displayed on Resource Details page");
		ccui.resourceDetailsPage().clickOnToggleButton();
		logStep("Verify success toaster message to deassociate practice");
		ccui.resourceDetailsPage().verifyToasterMessage("Success! New practice has been successfully unassigned");
		ccui.resourceDetailsPage().clickToBackButton();
		ccui.getAddNewResourcePage().enterResourceInSearchTextBox(resourceName);
		logStep("Apply filter for enterprise");
		ccui.getAddNewResourcePage().clickToEnterpriseFilter();
		ccui.getAddNewResourcePage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		logStep("Apply filter for Practice");
		ccui.getAddNewResourcePage().clickToPracticeFilter();
		ccui.getAddNewResourcePage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getAddNewResourcePage().clickToApplyFilterButton();
		ccui.resourceDetailsPage().verifyInactiveStatus();
		ccui.getAddNewResourcePage().selectResource();
		ccui.getAddNewResourcePage().verifyResourceDetailsTitle();
		ccui.getAddNewResourcePage().clickToEntepriseArrow();
		ccui.resourceDetailsPage().clickOnToggleButton();
		logStep("Verify success toaster message to associate practice");
		ccui.resourceDetailsPage().verifyToasterMessage("Success! New practice has been successfully assigned");
		ccui.resourceDetailsPage().clickToBackButton();
		ccui.getAddNewResourcePage().enterResourceInSearchTextBox(resourceName);
		logStep("Apply filter for enterprise");
		ccui.getAddNewResourcePage().clickToEnterpriseFilter();
		ccui.getAddNewResourcePage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		logStep("Apply filter for Practice");
		ccui.getAddNewResourcePage().clickToPracticeFilter();
		ccui.getAddNewResourcePage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getAddNewResourcePage().clickToApplyFilterButton();
		ccui.resourceDetailsPage().verifyActiveStatus();
		runOnce = false;

	}

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Verify Save Functionality With Valid Details in input fields")
	public void testSaveFunctionalityWithValidDetails() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.resourceDetailsPage().clickToResource(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().verifyEditButtonLabel();
		ccui.resourceDetailsPage().clickOnEditButton();
		logStep("PXPP-14515 Verify field limit for Resource Display Name (EN) field");
		ccui.resourceDetailsPage().enterResourceDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		logStep("PXPP-14516 Verify field limit for Resource Display Name (ES) field");
		ccui.resourceDetailsPage().enterResourceDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		logStep("PXPP-14514 Verify CANCEL & SAVE buttons are present in EDIT mode of Solutions Display Info");
		ccui.resourceDetailsPage().clickOnSaveButton();
		ccui.resourceDetailsPage().verifyUpdateToasterMessage("Resource Details updated successfully!");
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		logStep("PXPP-14520 Verify EDIT button is enabled for enabled Resource");
		ccui.resourceDetailsPage().verifyEditButtonLabel();
		ccui.resourceDetailsPage().clickOnEditButton();
		ccui.resourceDetailsPage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101));
		logStep("Verify cancel button functionality");
		ccui.resourceDetailsPage().clickOnCancel();
		runOnce = false;
	}

	// AddRemoveConnectedLocationsInResourcessDetailsPageTests 
	@Test(priority = 6,enabled = true,retryAnalyzer = RetryAnalyzer.class, description ="AddRemoveConnectedLocationsInResourcessDetailsPageTests")
	public void testAndAddRemoveLocationsOnResourcesDetailsPage() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		createLocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.resourceDetailsPage().clickToEntepriseArrow();
		ccui.resourceDetailsPage().clickOnToggleButton();
		ccui.resourceDetailsPage().clickToLocationBackButton();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getAddNewResourcePage().enterResourceInSearchTextBox(resourceName);
		ccui.getAddNewResourcePage().selectResource();
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().verifyConnectedSolutionsLabel();
		ccui.resourceDetailsPage().clickToAddLocationButton();
		ccui.resourceDetailsPage().enterTextInLocationTextBox(locationName);
		ccui.resourceDetailsPage().clickToLocationCheckBox(locationName);
		ccui.resourceDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Successfully added location");
		logStep("PXPP-27238 Verify on Connected Location section, user able to remove  the Location Name using the cross button associated with the Location Name.");
		ccui.resourceDetailsPage().clickToRemoveConnectionButton();
		ccui.resourceDetailsPage().verifyNoDataDisplayConnectedLocationsMessage();
		runOnce = false;
	}

	@Test(enabled = false)
	public void createResources() throws Exception {
		createProvider();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
		logStep("PXPP-27251 Verify dialog box is displayed on clicking the ADD RESOURCE button");
		logStep("PXPP-27261 Verify all the text fields (Name, External ID, ResourceType,Connected Provider) present on the \"Add a Resource\" dialog box are empty and editable");
		resourceName = "New" + CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.getAddNewResourcePage().enterResourceName(resourceName);
		ccui.getAddNewResourcePage().enterExternalId(externalIds);
		ccui.getAddNewResourcePage().selectResourceType("Person");
		ccui.getAddNewResourcePage().enterConnectedProvider(firstName);
		ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
		ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Resource has been successfully created");
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

	@Test(enabled = false)
	public void createProvider() throws Exception {
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		logStep("Enter valid details in mandatory field and verify success toaster message");
		String credentials = testData.getProperty("provider.credentials");
		ccui.getAddNewProviderPage().enterFirstName(firstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		externalId = testData.getProperty("provider.external.id").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Male");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
	}

	@Test(enabled = false)
	public void navigatetoResources() throws Exception {
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
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

	public void createLocation() throws Exception {
		ccui.getGeneralSettingsPage().clickToClientNode();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Click on Add button");
		ccui.addLocationsPage().verifyLocationsPageTitle();
		ccui.addLocationsPage().verifyAddLocationButton();
		driver.navigate().refresh();
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
	}
}