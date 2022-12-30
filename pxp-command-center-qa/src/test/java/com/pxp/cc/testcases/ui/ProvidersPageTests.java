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

public class ProvidersPageTests extends CommandCenterGenericTest {
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

	String proOne, proTwo, proThree, proFour, proFive, proSix, proSeven, proEight, proNine, proTen, proEleven,
			providerFirstName, lastName;

	// AddNewProviderTests

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new provider using valid test data")
	public void testAddNewProviderFunctionalityUsingValidData() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-25961, PXPP-13289, PXPP-13292, PXPP-25997- Verify new provider is created after entering values for First Name, Last Name, External ID, Credentials, Gender fields");
		providerNavigation();
		String firstName = "ProvFirst "
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = "ProvLast " + CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory fields to Create new Male provider and verify success toaster message");
		ccui.getAddNewProviderPage().enterFirstName(firstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Male");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
		logStep("Enter valid details in mandatory fields to Create new Female provider and verify success toaster message");
		ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		ccui.getAddNewProviderPage().enterFirstName(firstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Female");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Add New Provider page UI")
	public void testAddNewProviderPageUi() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-25964, PXPP-25963,PXPP-25962, PXPP-25965, PXPP-25967, PXPP-25978, PXPP-25986, PXPP-25993, PXPP-25995- Verify all the textfields (First Name, Last Name, External ID, Credentials, Gender) present on the \"Add a Provider\" dialog box are empty and editable");
		providerNavigation();
		logStep("Verify Add a provider page UI");
		ccui.getAddNewProviderPage().VerifyAddNewProviderPageUi();
		logStep("PXPP-25995- Verify all the textfields (First Name, Last Name, External ID, Credentials, Gender) present on the Add a Provider dialog box are empty and editable");
		ccui.getAddNewProviderPage().enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForFirstNameField(),
				"Maximum characters reached");
		ccui.getAddNewProviderPage().enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForLastNameField(),
				"Maximum characters reached");
		ccui.getAddNewProviderPage().enterExternalId(CCUtils.generateAlphaNumericString(51));
		CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForExternalIdField(),
				"Maximum characters reached");
		ccui.getAddNewProviderPage().enterCredentials(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForCredentialField(),
				"Maximum characters reached");
		logStep("Enter invalid value gender field and verify error message");
		ccui.getAddNewProviderPage().clickOnGenderDropdown();
		ccui.getAddNewProviderPage().enterGender("abcdef");
		CCUtils.pressEnterKey();
		ccui.getAddNewProviderPage().verifyNoItemsFound();
		logStep("Verify Cancel button functionality on add a provider page");
		ccui.getAddNewProviderPage().clickOnCancelButton();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify Cross button functionality on add a provider page");
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		ccui.getAddNewProviderPage().clickOnCrossIcon();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Enter invalid first name and last name in firestName and lastName field");
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		ccui.getAddNewProviderPage().enterFirstName(testData.getProperty("invalid.user.first.name.one"));
		CCUtils.assertEquals(ccui.getAddNewProviderPage().getValidErrorForFirstNameField(),
				"Please enter valid first name");
		ccui.getAddNewProviderPage().enterLastName(testData.getProperty("invalid.user.last.name.one"));
		CCUtils.assertEquals(ccui.getAddNewProviderPage().getValidErrorForLastNameField(),
				"Please enter valid last name"); 
		// These lines are commented due to external id is not saved in DB .// hence we cann't do validation. //
		//logStep("Enter existing external id in externalId field and verify error message"); // ccui.getAddNewProviderPage().enterExternalId(externalId);
		//CCUtils.assertEquals(ccui.getAddNewProviderPage().getUniqueErrorMessageForExternalIdField(), "External Id already in use");
		runOnce = false;
	}

	// SearchAndFilterProviderTests

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify the visibility and presence of web elements of search and filter provider screen")
	public void testSearchFilterProviderScreenWebElements() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13325- Verify search textbox with search button is present in provider search page - Command Center Admin");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getSearchProviderPage().verifySearchTextBoxAndPlaceholderIsDisplayed();
		ccui.getSearchProviderPage().verifySearchButtonIsDisplayed();
		logStep("PXPP-13332- Verify elements of Filter container in provider search page - Command Center Admin");
		ccui.getSearchProviderPage().verifyFilterLabel();
		ccui.getSearchProviderPage().verifySystemFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().verifyEnterpriseFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().verifyPracticeFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().verifySolutionFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().clearButtonDisplayed();
		ccui.getSearchProviderPage().clearApplyDisplayed();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a provider")
	public void testSearchExistingNonExistingProviderOnProviderScreen() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13345, PXPP-13330 Verify user can search by provider name  in search textbox in provider search page - Command Center Admin");
		providerNavigation();
		createProvider();
		ccui.getSearchProviderPage().searchProviderAndVerifyResult(providerFirstName);
		logStep("PXPP-13346 Verify when user can search by provider name in search textbox and it is not found in provider web table should display message 'No data to display' - Command Center Admin");
		ccui.getSearchProviderPage().searchNonExistingProviderAndVerifyResult("NonExistingProvider");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify after clicking hide button filter section should not get displayed")
	public void testFunctionalityOfHideFiltersButtonOnProviderScreen() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13344, PXPP-13334- Verify Hide filters button should be present in providers page - Command Center Admin");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getSearchProviderPage().verifyFilterLabel();
		ccui.getSearchProviderPage().verifySystemFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().verifyEnterpriseFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().verifyPracticeFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().verifySolutionFilterLabelTextAndPlaceholder();
		ccui.getSearchProviderPage().clearButtonDisplayed();
		ccui.getSearchProviderPage().clearApplyDisplayed();
		ccui.getSearchProviderPage().clickToHideFiltersButton();
		ccui.getSearchProviderPage().verifyAllElementsOfFilterNotVisible();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply system, enterprise, practice filter in search provider screen")
	public void testSearchExistingProviderByFilterSystemEnterprisePractice() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13343 Verify user can apply filter by clicking apply button in Filter container on provider search page - Command Center Admin");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Apply filter for System");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getSearchProviderPage().clickToSystemFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("systemName"));
		ccui.getSearchProviderPage().clickToApplyFilterButton();
		ccui.getSearchProviderPage().verifySearchProviderFound(providerFirstName);
		ccui.getSearchProviderPage().clickToClearFilterButton();
		logStep("Apply filter for enterprise");
		ccui.getSearchProviderPage().clickToEnterpriseFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getSearchProviderPage().clickToApplyFilterButton();
		ccui.getSearchProviderPage().verifySearchProviderFound(providerFirstName);
		logStep("Apply filter for Practice");
		ccui.getSearchProviderPage().clickToPracticeFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getSearchProviderPage().clickToApplyFilterButton();
		ccui.getSearchProviderPage().verifySearchProviderFound(providerFirstName);
		logStep("Apply filter for System, enterprise, practice at a time");
		ccui.getSearchProviderPage().clickToSystemFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("systemName"));
		ccui.getSearchProviderPage().clickToEnterpriseFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getSearchProviderPage().clickToPracticeFilter();
		ccui.getSearchProviderPage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getSearchProviderPage().clickToApplyFilterButton();
		ccui.getSearchProviderPage().verifySearchProviderFound(providerFirstName);
		runOnce = false;
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply solution filter in search provider screen")
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
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickToProvider(providerFirstName);
		ccui.getProviderDetailsPage().clickOnChatToggle();
		ccui.getProviderDetailsPage().clickToBackButton();
		ccui.getSearchProviderPage().clickToSolutionFilter();
		ccui.getSearchProviderPage().selectFilter(testData.getProperty("solution.chat"));
		ccui.getSearchProviderPage().clickToApplyFilterButton();
		List<String> listOfProviders = new ArrayList<String>();
		listOfProviders.add(providerFirstName);
		ccui.getSearchProviderPage().verifyListOfProviderMatchedSearchResult(listOfProviders);
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Providers FirstName, LastName, Status column sorting from ascending to descending and from ascending to descending")
	public void testProviderFirstNameLastNameStatusColumnSorting() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13335, PXPP-13338, PXPP-13340 Verify provider column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards - Command Center Admin");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getSearchProviderPage().clickAscProvidersFirstNameSortToDsc();
		ccui.getSearchProviderPage().verifyProvidersFirstNameDescendingSortIsVisible();
		ccui.getSearchProviderPage().clickDscProvidersFirstNameSortToAsc();
		ccui.getSearchProviderPage().verifyProvidersFirstNameAscendingSortIsVisible();
		logStep("PXPP-13340 Verify provider last name column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards - Command Center Admin");
		ccui.getSearchProviderPage().clickAscProvidersLastNameSortToDsc();
		ccui.getSearchProviderPage().verifyProvidersLastNameDescendingSortIsVisible();
		ccui.getSearchProviderPage().clickDscProvidersLastNameSortToAsc();
		ccui.getSearchProviderPage().verifyProvidersLastNameAscendingSortIsVisible();
		logStep("PXPP-13341 Verify provider status column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards - Command Center Admin");
		ccui.getSearchProviderPage().clickAscProvidersStatusSortToDsc();
		ccui.getSearchProviderPage().verifyProvidersStatusDescendingSortIsVisible();
		ccui.getSearchProviderPage().clickDscProvidersStatusSortToAsc();
		ccui.getSearchProviderPage().verifyProvidersStatusAscendingSortIsVisible();
		runOnce = false;
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultProviderPaginationInProviderWebTable() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-13342 Verify pagination and Items per page dropdown should be present in provider web table - Command Center Admin");
		providerNavigation();
		createMultipleProviders();
		logStep("Now comparing the default Items per Page. Ideally it should be 10.");
		CCUtils.scrollPageToEnd();
		CCUtils.assertEquals(ccui.getSearchProviderPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getSearchProviderPage().getDefaultPagination(), "1 – 10");
		String noOfProviders = ccui.getSearchProviderPage().getTotalNoOfProviders();
		logStep("Get the Total No of Providers : " + noOfProviders);
		int getNoOfRowsPagination = Integer.parseInt(ccui.getSearchProviderPage().getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(ccui.getSearchProviderPage().isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getSearchProviderPage().isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getSearchProviderPage().isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(ccui.getSearchProviderPage().isLastPageButtonDisabled(), true);
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality")
	public void testNextPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		logStep("Check Next Button functionality when Items Per Page value is = 10.");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		String defaultItemsPerPage = ccui.getSearchProviderPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getSearchProviderPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Next Button functionality when Items Per Page value is = 20");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getSearchProviderPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getSearchProviderPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Next Button functionality when Items Per Page value is = 50");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getSearchProviderPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getSearchProviderPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Next Button functionality when Items Per Page value is = 100");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchProviderPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getSearchProviderPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Next Button functionality when Items Per Page value is = 200");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getSearchProviderPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getSearchProviderPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check Last Button functionality")
	public void testLastPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		logStep("Check Last Button functionality when Items Per Page value is = 10.");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		String defaultItemsPerPage = ccui.getSearchProviderPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + ccui.getSearchProviderPage().getRHSPagination());
		ccui.getSearchProviderPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 20.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getSearchProviderPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		log("get default pagination : " + ccui.getSearchProviderPage().getDefaultPagination());
		log("Print the RHS Pagination : " + ccui.getSearchProviderPage().getRHSPagination());
		ccui.getSearchProviderPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Last Button functionality when Items Per Page value is = 50.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getSearchProviderPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		log("Print the RHS Pagination : " + ccui.getSearchProviderPage().getRHSPagination());
		ccui.getSearchProviderPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Last Button functionality when Items Per Page value is = 100.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchProviderPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		log("Print the RHS Pagination : " + ccui.getSearchProviderPage().getRHSPagination());
		ccui.getSearchProviderPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Last Button functionality when Items Per Page value is = 200.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getSearchProviderPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		log("Print the RHS Pagination : " + ccui.getSearchProviderPage().getRHSPagination());
		ccui.getSearchProviderPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality")
	public void testPreviousPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		logStep("Check Previous Button functionality when Items Per Page value is = 10");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		String defaultItemsPerPage = ccui.getSearchProviderPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getSearchProviderPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 20");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getSearchProviderPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getSearchProviderPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Previous Button functionality when Items Per Page value is = 50");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getSearchProviderPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getSearchProviderPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Previous Button functionality when Items Per Page value is = 100");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchProviderPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getSearchProviderPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Previous Button functionality when Items Per Page value is = 200");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getSearchProviderPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getSearchProviderPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality")
	public void testFirstPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		logStep("Check First Button functionality when Items Per Page value is = 10.");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		String defaultItemsPerPage = ccui.getSearchProviderPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getSearchProviderPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check First Button functionality when Items Per Page value is = 20.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getSearchProviderPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		ccui.getSearchProviderPage().checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check First Button functionality when Items Per Page value is = 50.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getSearchProviderPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getSearchProviderPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check First Button functionality when Items Per Page value is = 100.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchProviderPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getSearchProviderPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check First Button functionality when Items Per Page value is = 200.");
		ccui.getSearchProviderPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getSearchProviderPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getSearchProviderPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	public String createProvider() throws Exception {
		providerFirstName = "NewPro"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		lastName = CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6))
				.concat("'s");
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

	public void createMultipleProviders() throws Exception {
		proOne = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proTwo = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proThree = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proFour = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proFive = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proSix = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proSeven = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proEight = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proNine = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proTen = createProvider();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		proEleven = createProvider();
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
