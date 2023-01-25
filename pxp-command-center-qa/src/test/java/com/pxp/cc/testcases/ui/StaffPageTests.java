//Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class StaffPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
	String staffName;
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

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create new Staff using valid test data")
	public void testAddNewStaffFunctionalityUsingValidData() throws Exception {
		navigatetoStaff();
		ccui.getStaffPage().verifyAddStaffPopupTitle();
		logStep("PXPP-26546 Verify ADD button is displayed on Providers, Resources, & Staff tab Staff page for the client ");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		logStep("PXPP-26550 Verify Add Staff dialog box contains First Name, Last Name, External ID, Gender fields");
		ccui.getStaffPage().enterFirstName(firstName);
		ccui.getStaffPage().enterLastName(lastName);
		ccui.getStaffPage().enterExternalId(externalId);
		ccui.getStaffPage().selectGenderValue("Male");
		ccui.getStaffPage().verifySaveAndAddButtonIsEnabled();
		logStep("PXPP-26551 Verify Add Staff dialog box contains Save & Add button ");
		ccui.getStaffPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Staff has been successfully created");
		runOnce = false;  
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify navigation to Staff page with Staff tab and Staff tile on General Settins page")
	public void testNavigationToStaffPageWithStaffTile() throws Exception {
		navigatetoStaff();
		logStep(" Verify the cancel and cross Icon functionality while adding New Staff");
		ccui.getStaffPage().clickOnCancelButton();
		logStep(" Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getStaffPage().verifyProvidersAndResourcesAndStaffTitle();
		logStep("Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getStaffPage().clickOnAddStaffButton();
		logStep("Verify the cancel and cross Icon functionality while adding New Resources");
		ccui.getStaffPage().clickOnCrossIcon();
		logStep(" Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getStaffPage().verifyProvidersAndResourcesAndStaffTitle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Enter more than 100 characters in StaffName, more than 50 characters in ExternalID fields ,verify error message and invalid value StaffGender")
	public void testHundredMaximumCharactersErrorMessageForInputFields() throws Exception {
		navigatetoStaff();
		logStep("PXPP-26553 Verify field limit for all mandatory fields - First Name, Last Name, External ID");
		ccui.getStaffPage().enterFirstName((CCUtils.generateAlphaNumericString(101)));
		CCUtils.assertEquals(ccui.getStaffPage().getMaximumCharacterErrorForFirstNameField(),
				"Maximum characters reached");
		ccui.getStaffPage().enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getStaffPage().getMaximumCharacterErrorForLastNameField(),
				"Maximum characters reached");
		ccui.getStaffPage().enterExternalId(CCUtils.generateAlphaNumericString(51));
		CCUtils.assertEquals(ccui.getStaffPage().getMaximumCharacterErrorForExternalIdField(),
				"Maximum characters reached");
		logStep("Enter invalid value gender field and verify error message");
		ccui.getStaffPage().clickOnGenderDropdown();
		ccui.getStaffPage().enterGender("abcdef");
		CCUtils.pressEnterKey();
		ccui.getStaffPage().verifyNoItemsFound();
		logStep("Verify Cancel button functionality on add a provider page");
		ccui.getStaffPage().clickOnCancelButton();
		logStep(" Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getStaffPage().verifyProvidersAndResourcesAndStaffTitle();
		ccui.getStaffPage().clickOnAddStaffButton();
		ccui.getStaffPage().clickOnCrossIcon();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is displayed on Providers, Resources, & Staff page");
		ccui.getStaffPage().clickOnAddStaffButton();
		ccui.getStaffPage().enterFirstName(testData.getProperty("invalid.user.first.name.one"));
		CCUtils.assertEquals(ccui.getStaffPage().getValidErrorForFirstNameField(), "Please enter valid first name");
		ccui.getStaffPage().enterLastName(testData.getProperty("invalid.user.last.name.one"));
		CCUtils.assertEquals(ccui.getStaffPage().getValidErrorForLastNameField(), "Please enter valid last name");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 5, description = "Verify after clicking hide button filter section should not get displayed")
	public void testFunctionalityOfHideFiltersButtonOnStaffScreen() throws Exception {
		navigatetoStaff();
		ccui.getStaffPage().clickOnCancelButton();
		ccui.getStaffPage().verifyFilterLabel();
		ccui.getStaffPage().verifySystemFilterLabelTextAndPlaceholder();
		ccui.getStaffPage().verifyEnterpriseFilterLabelTextAndPlaceholder();
		ccui.getStaffPage().verifyPracticeFilterLabelTextAndPlaceholder();
		ccui.getStaffPage().verifySolutionFilterLabelTextAndPlaceholder();
		ccui.getStaffPage().clearButtonDisplayed();
		ccui.getStaffPage().clearApplyDisplayed();
		ccui.getStaffPage().clickToHideFiltersButton();
		ccui.getStaffPage().verifyAllElementsOfFilterNotVisible();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 6, description = "Check Next Button functionality when Items Per Page value ")
	public void testNextPageButtonWhenItemsPerPageValue() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getStaffPage().clickOnStaffTab();
		String defaultItemsPerPage = ccui.getStaffPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getStaffPage().checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getStaffPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getStaffPage().checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getStaffPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getStaffPage().checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getStaffPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getStaffPage().checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getStaffPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getStaffPage().checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 11, description = "Check Last Button functionality")
	public void testLastPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("Check Last Button functionality when Items Per Page value is = 10.");
		String defaultItemsPerPage = ccui.getStaffPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + ccui.getStaffPage().getRHSPagination());
		ccui.getStaffPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 20.");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getStaffPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		log("get default pagination : " + ccui.getStaffPage().getDefaultPagination());
		log("Print the RHS Pagination : " + ccui.getStaffPage().getRHSPagination());
		ccui.getStaffPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Last Button functionality when Items Per Page value is = 50.");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getStaffPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		log("Print the RHS Pagination : " + ccui.getStaffPage().getRHSPagination());
		ccui.getStaffPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Last Button functionality when Items Per Page value is = 100.");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getStaffPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		log("Print the RHS Pagination : " + ccui.getAddNewResourcePage().getRHSPagination());
		ccui.getStaffPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Last Button functionality when Items Per Page value is = 200.");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getStaffPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		log("Print the RHS Pagination : " + ccui.getStaffPage().getRHSPagination());
		ccui.getStaffPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 12, description = "Check Previous Button functionality")
	public void testPreviousPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("Check Previous Button functionality when Items Per Page value is = 10");
		String defaultItemsPerPage = ccui.getStaffPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getStaffPage().checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 20");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getStaffPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getStaffPage().checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Previous Button functionality when Items Per Page value is = 50");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getStaffPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getStaffPage().checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Previous Button functionality when Items Per Page value is = 100");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getStaffPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getStaffPage().checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Previous Button functionality when Items Per Page value is = 200");
		ccui.getStaffPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getStaffPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getStaffPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		runOnce = false;
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a staff")
	public void testSearchExistingStaffAndNonExistingStaffOnStaffScreen() throws Exception {
		testAddNewStaffFunctionalityUsingValidData();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("PXPP-13256 Verify User is able to SEARCH for Staff member by NAME  on Providers, Resources & Staff page ");
		ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
		logStep("Navigated to staff details page");
		ccui.getStaffPage().clickOnStaffTab();
		ccui.getStaffPage().enterNonExistingStaffAndVerifyResult("NonExistingResources");
		ccui.getStaffPage().verifyNoDataDisplayErrorMessage();
		runOnce = false;
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply system, enterprise, practice filter in search Staff screen")
	public void testSearchExistingStaffByFilterSystemEnterprisePractice() throws Exception {
		testAddNewStaffFunctionalityUsingValidData();
		logStep("PXPP-13297 Verify HIDE FILTERS, CLEAR, APPLY, +ADD, IMPORT buttons are displayed and enabled on the Providers, Resources & Staff page");
		ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
		logStep("Navigated to staff details page");
		ccui.getStaffPage().clickOnStaffTab();
		ccui.getStaffPage().selectStaff();
		ccui.getStaffPage().verifyStaffDetailsTitle();
		ccui.getStaffPage().clickToEntepriseArrow();
		ccui.getStaffPage().clickToBackButton();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("PXPP-13257 Verify Users can FILTER Staff  by System, Enterprise, Practice and Solutions they are associated within CC");
		ccui.getStaffPage().clickToSystemFilter();
		logStep("Apply filter for System");
		ccui.getStaffPage().selectFilter(clientSystementErpriseandPractice.get("systemName"));
		ccui.getStaffPage().clickToApplyFilterButton();
		ccui.getStaffPage().clickToClearFilterButton();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("Apply filter for enterprise");
		ccui.getStaffPage().clickToEnterpriseFilter();
		ccui.getStaffPage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getStaffPage().clickToApplyFilterButton();
		ccui.getStaffPage().clickToClearFilterButton();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("Apply filter for Practice");
		ccui.getStaffPage().clickToPracticeFilter();
		ccui.getStaffPage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getStaffPage().clickToApplyFilterButton();
		ccui.getStaffPage().clickOnStaffTab();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 10, description = "Check Status, Staff name, Type, sorting from ascending to descending and from ascending to descending")
	public void testStaffSortingToDescendingAndAscending() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		createStaff();
		ccui.getStaffPage().clickOnStaffTab();
		logStep(" Verify staff first name column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
		ccui.getStaffPage().clickAscStaffTypeSortToDsc();
		ccui.getStaffPage().verifyStaffTypeDescendingSortIsVisible();
		ccui.getStaffPage().clickDscStaffTypeSortToAsc();
		ccui.getStaffPage().verifyStaffTypeAscendingSortIsVisible();
		logStep(" Verify staff last name column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
		ccui.getStaffPage().clickAscStaffLastNameSortToDsc();
		ccui.getStaffPage().verifyStaffLastNameDescendingSortIsVisible();
		ccui.getStaffPage().clickDscStaffLastNameSortToAsc();
		ccui.getStaffPage().verifyStaffLastNameAscendingSortIsVisible();
		logStep("Verify staff status column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
		ccui.getStaffPage().clickStaffStatusSortToDsc();
		ccui.getStaffPage().verifyStaffStatusDescendingSortIsVisible();
		ccui.getStaffPage().clickDscStaffStatusSortToAsc();
		ccui.getStaffPage().verifyStaffStatusAscendingSortIsVisible();
		runOnce = false;
	}
	
	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testDefaultItemsPerPageAndDefaultStaffPaginationInStaffWebTable() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		createStaff();
		createStaff();
		createStaff();
		createStaff();
		createStaff();
		createStaff();
		createStaff();
		createStaff();
		createStaff();
		createStaff();
		createStaff();	
		logStep("Now comparing the default Items per Page. Ideally it should be 10.");
		CCUtils.scrollPageToEnd();
		CCUtils.assertEquals(ccui.getStaffPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getStaffPage().getDefaultPagination(), "1 – 10");
		String noOfStaff = ccui.getStaffPage().getTotalNoOfStaff();
		logStep("Get the Total No of Staff : " +  noOfStaff);
		int getNoOfRowsPagination = Integer.parseInt(ccui.getStaffPage().getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(ccui.getStaffPage().isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getStaffPage().isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getStaffPage().isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(ccui.getStaffPage().isLastPageButtonDisabled(), true);
		runOnce = false;
	}

	public void createStaff() throws Exception {
		ccui.getStaffPage().clickOnStaffTab();
		logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
		ccui.getStaffPage().verifyAddButtonIsEnabled();
		logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
		ccui.getStaffPage().clickOnAddStaffButton();
		logStep("Verify add new Resource popup");
		ccui.getStaffPage().verifyAddStaffPopupTitle();
		logStep("PXPP-26546 Verify ADD button is displayed on Providers, Resources, & Staff tab Staff page for the client ");
		logStep("Enter data in mandatory fields");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		logStep("PXPP-26550 Verify Add Staff dialog box contains First Name, Last Name, External ID, Gender fields");
		ccui.getStaffPage().enterFirstName(firstName);
		ccui.getStaffPage().enterLastName(lastName);
		ccui.getStaffPage().enterExternalId(externalId);
		ccui.getStaffPage().selectGenderValue("Male");
		ccui.getStaffPage().verifySaveAndAddButtonIsEnabled();
		logStep("PXPP-26551 Verify Add Staff dialog box contains Save & Add button ");
		ccui.getStaffPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Staff has been successfully created");

	}
	@Test(enabled = false)
	public void navigatetoStaff() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
		ccui.getStaffPage().verifyAddButtonIsEnabled();
		logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
		ccui.getStaffPage().clickOnAddStaffButton();
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
