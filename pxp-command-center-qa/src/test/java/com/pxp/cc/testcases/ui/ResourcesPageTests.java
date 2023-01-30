// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;
import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import java.util.ArrayList;
import java.util.List;

public class ResourcesPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
	String resourceName;
	String firstName = "New"
			+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
	String lastName = CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6))
			.concat("'s");

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

	String proOne, proTwo, proThree, proFour, proFive, proSix, proSeven, proEight, proNine, proTen, proEleven;

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify add new Resource popup")
	public void testAddNewResourcePopup() throws Exception {
		ngHierarchyClientCreate();
		navigatetoResources();
		logStep("Verify add new Resource popup");
		ccui.getAddNewResourcePage().verifyAddResourcePopupTitle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, groups = {
			"buildvalidation" }, priority = 2, description = "Create new Resource using valid test data")
	public void testAddNewResourcesFunctionalityUsingValidData() throws Exception {
		ngHierarchyClientCreate();
		createProvider();
		navigatetoResources();
		logStep("PXPP-27251,PXPP-27261 Verify all the text fields (Name, External ID, ResourceType,Connected Provider) present on the \"Add a Resource\" dialog box are empty and editable");
		resourceName = "New" + CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		logStep("PXPP-27254 Verify  Name label and textfield displayed on Add a Resources dialog box");
		ccui.getAddNewResourcePage().enterResourceName(resourceName);
		logStep("PXPP-27256 Verify External ID label and text field displayed on Add a Resources dialog box");
		ccui.getAddNewResourcePage().enterExternalId(externalIds);
		logStep(" PXPP-27258 Verify Connected Provider and Auto Suggestions displayed to select Connect Provider on \"Add a Resources\" dialog box");
		ccui.getAddNewResourcePage().selectResourceType("Person");
		logStep("PXPP-27257 Verify Resource Type  dropdown label and text field displayed on \"Add a Resources\" dialog box");
		ccui.getAddNewResourcePage().enterConnectedProvider(firstName);
		ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
		ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
		logStep(" PXPP-27259 Verify on Add a Resource dialog box Save & Add button is displayed");
		ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Resource has been successfully created");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify navigation to Resource page with Resource tab and Resource tile on General Settins page")
	public void testNavigationToResourcePageWithResourceTile() throws Exception {
		ngHierarchyClientCreate();
		navigatetoResources();
		logStep("PXPP-27614 Verify the cancel and cross Icon functionality while adding New Resources");
		ccui.getAddNewResourcePage().clickOnCancelButton();
		logStep(" PXPP-27250 Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep(" PXPP-27250 Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
		logStep("PXPP-27614 Verify the cancel and cross Icon functionality while adding New Resources");
		ccui.getAddNewResourcePage().clickOnCrossIcon();
		logStep(" PXPP-27250 Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 4, description = "Enter more than 100 characters in ResourceName, more than 50 characters in ExternalID fields ,verify error message and invalid value Resource type")
	public void testHundredMaximumCharactersErrorMessageForInputFields() throws Exception {
		ngHierarchyClientCreate();
		navigatetoResources();
		logStep("PXPP-27262 Verify field limit for (Name, External ID, Resource Type, Connected Provider) and Click on Save button");
		ccui.getAddNewResourcePage()
				.verifyHundredMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
		logStep("Verify maximum character reached error message for EXternalId fields");
		ccui.getAddNewResourcePage()
				.verifyFiftyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(51));
		logStep("Enter invalid value Resource type and verify error message");
		ccui.getAddNewResourcePage().clickOnResourceType();
		logStep(" PXPP-27258 Verify Connected Provider and Auto Suggestions displayed to select Connect Provider on \"Add a Resources\" dialog box");
		ccui.getAddNewResourcePage().enterResourceType(testData.getProperty("resource.name"));
		CCUtils.pressEnterKey();
		ccui.getAddNewResourcePage().verifyNoItemsFound();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 5, description = "Verify after clicking hide button filter section should not get displayed")
	public void testFunctionalityOfHideFiltersButtonOnResourceScreen() throws Exception {
		ngHierarchyClientCreate();
		navigatetoResources();
		logStep("PXPP-27614 Verify the cancel and cross Icon functionality while adding New Resources");
		ccui.getAddNewResourcePage().clickOnCancelButton();
		logStep("PXPP-14899 Verify user can apply filter by clicking apply button in Filter container on resource search page");
		ccui.getAddNewResourcePage().verifyFilterLabel();
		ccui.getAddNewResourcePage().verifySystemFilterLabelTextAndPlaceholder();
		ccui.getAddNewResourcePage().verifyEnterpriseFilterLabelTextAndPlaceholder();
		ccui.getAddNewResourcePage().verifyPracticeFilterLabelTextAndPlaceholder();
		ccui.getAddNewResourcePage().verifySolutionFilterLabelTextAndPlaceholder();
		logStep("PXPP-14900 Verify user can clear applied filter by clicking to clear button in resource search page - Command Center Admin");
		ccui.getAddNewResourcePage().clearButtonDisplayed();
		ccui.getAddNewResourcePage().clearApplyDisplayed();
		ccui.getAddNewResourcePage().clickToHideFiltersButton();
		ccui.getAddNewResourcePage().verifyAllElementsOfFilterNotVisible();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 6, description = "Check Next Button functionality when Items Per Page value ")
	public void testNextPageButtonWhenItemsPerPageValue() throws Exception {
		ngHierarchyClientCreate();
		createResources();
		logStep("PXPP-14898 Verify pagination and Items per page dropdown should be present in resource web table ");
		String defaultItemsPerPage = ccui.getAddNewResourcePage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getAddNewResourcePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getAddNewResourcePage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getAddNewResourcePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getAddNewResourcePage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getAddNewResourcePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getAddNewResourcePage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getAddNewResourcePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getAddNewResourcePage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getAddNewResourcePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 13, description = "Verify search result when apply solution filter in search provider screen")
	public void testSearchExistingProviderByFilterSolution() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Purchased Solutions section is displayed");
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		createResources();
		logStep("Navigated to Resource details page");
		ccui.getAddNewResourcePage().selectResource();
		ccui.getAddNewResourcePage().clickOnChatToggle();
		ccui.getAddNewResourcePage().clickToBackButton();
		ccui.getAddNewResourcePage().clickToSolutionFilter();
		ccui.getAddNewResourcePage().selectFilter(testData.getProperty("solution.chat"));
		ccui.getAddNewResourcePage().clickToApplyFilterButton();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 11, description = "Check Last Button functionality")
	public void testLastPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		createResources();
		logStep("Check Last Button functionality when Items Per Page value is = 10.");
		String defaultItemsPerPage = ccui.getAddNewResourcePage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + ccui.getAddNewResourcePage().getRHSPagination());
		ccui.getAddNewResourcePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 20.");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getAddNewResourcePage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		log("get default pagination : " + ccui.getAddNewResourcePage().getDefaultPagination());
		log("Print the RHS Pagination : " + ccui.getAddNewResourcePage().getRHSPagination());
		ccui.getAddNewResourcePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Last Button functionality when Items Per Page value is = 50.");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getAddNewResourcePage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		log("Print the RHS Pagination : " + ccui.getAddNewResourcePage().getRHSPagination());
		ccui.getAddNewResourcePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Last Button functionality when Items Per Page value is = 100.");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getAddNewResourcePage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		log("Print the RHS Pagination : " + ccui.getAddNewResourcePage().getRHSPagination());
		ccui.getAddNewResourcePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Last Button functionality when Items Per Page value is = 200.");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getAddNewResourcePage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		log("Print the RHS Pagination : " + ccui.getAddNewResourcePage().getRHSPagination());
		ccui.getAddNewResourcePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 12, description = "Check Previous Button functionality")
	public void testPreviousPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		createResources();
		logStep("Check Previous Button functionality when Items Per Page value is = 10");
		String defaultItemsPerPage = ccui.getAddNewResourcePage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getAddNewResourcePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 20");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getAddNewResourcePage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getAddNewResourcePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Previous Button functionality when Items Per Page value is = 50");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getAddNewResourcePage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getAddNewResourcePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Previous Button functionality when Items Per Page value is = 100");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getAddNewResourcePage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getAddNewResourcePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Previous Button functionality when Items Per Page value is = 200");
		ccui.getAddNewResourcePage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getAddNewResourcePage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getAddNewResourcePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a resource")
	public void testSearchExistingResourceAndNonExistingResourceOnResourceScreen() throws Exception {
		ngHierarchyClientCreate();
		createResources();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		logStep("PXPP-14732 Verify search textbox with search button is present in resource search page");
		ccui.getAddNewResourcePage().enterResourceInSearchTextBox(resourceName);
		logStep("Navigated to Resource details page");
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		logStep("PXPP-14904 Verify when user can search by resource name in search textbox and it is not found in resource web table should display message ' no data to display'");
		ccui.getAddNewResourcePage().enterNonExistingResourceAndVerifyResult("NonExistingResources");
		ccui.getAddNewResourcePage().verifyNoDataDisplayErrorMessage();
		runOnce = false;
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply system, enterprise, practice filter in search Resources screen")
	public void testSearchExistingResourceByFilterSystemEnterprisePractice() throws Exception {
		ngHierarchyClientCreate();
		createResources();
		ccui.getAddNewResourcePage().selectResource();
		ccui.getAddNewResourcePage().verifyResourceDetailsTitle();
		ccui.getAddNewResourcePage().clickToEntepriseArrow();
		ccui.getAddNewResourcePage().clickToBackButton();
		List<String> listOfResources = new ArrayList<String>();
		listOfResources.add(resourceName);
		logStep("PXPP-14899 Verify user can apply filter by clicking apply button in Filter container on resource search page");
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		logStep("Apply filter for System");
		ccui.getAddNewResourcePage().clickToSystemFilter();
		ccui.getAddNewResourcePage().selectFilter(clientSystementErpriseandPractice.get("systemName"));
		ccui.getAddNewResourcePage().clickToApplyFilterButton();
		ccui.getAddNewResourcePage().clickToClearFilterButton();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		logStep("Apply filter for enterprise");
		ccui.getAddNewResourcePage().clickToEnterpriseFilter();
		ccui.getAddNewResourcePage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getAddNewResourcePage().clickToApplyFilterButton();
		ccui.getAddNewResourcePage().clickToClearFilterButton();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		logStep("Apply filter for Practice");
		ccui.getAddNewResourcePage().clickToPracticeFilter();
		ccui.getAddNewResourcePage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getAddNewResourcePage().clickToApplyFilterButton();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 10, description = "Check Status, Resource name, Type, sorting from ascending to descending and from ascending to descending")
	public void testResourcesSortingToDescendingAndAscending() throws Exception {
		ngHierarchyClientCreate();
		createResources();
		logStep("PXPP-14897 Verify resource status column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
		ccui.getAddNewResourcePage().clickAscResourcesStatusSortToDsc();
		ccui.getAddNewResourcePage().verifyResourcesStatusDescendingSortIsVisible();
		ccui.getAddNewResourcePage().clickDscResourcesStatusSortToAsc();
		ccui.getAddNewResourcePage().verifyResourcesStatusAscendingSortIsVisible();
		logStep("PXPP-14894 Verify resource first name column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
		ccui.getAddNewResourcePage().clickAscResourcesFirstNameSortToDsc();
		ccui.getAddNewResourcePage().verifyResourcesFirstNameDescendingSortIsVisible();
		ccui.getAddNewResourcePage().clickDscResourcesFirstNameSortToAsc();
		ccui.getAddNewResourcePage().verifyResourcesFirstNameAscendingSortIsVisible();
		logStep("PXPP-14896 Verify resource type column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
		ccui.getAddNewResourcePage().clickAscResourceTypeSortToDsc();
		ccui.getAddNewResourcePage().verifyResourceTypeDescendingSortIsVisible();
		ccui.getAddNewResourcePage().clickDscResourceTypeSortToAsc();
		ccui.getAddNewResourcePage().verifyResourceTypeAscendingSortIsVisible();
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
		ccui.getAddNewResourcePage().enterResourceInSearchTextBox(resourceName);
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
		logStep("Enter valid details in mandatory field and verify success toaster message");
		String credentials = testData.getProperty("provider.credentials");
		ccui.getAddNewProviderPage().enterFirstName(firstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
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
}