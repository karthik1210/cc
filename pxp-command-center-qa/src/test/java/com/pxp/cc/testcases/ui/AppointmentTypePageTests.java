// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;

public class AppointmentTypePageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
	String firstName = "New"
			+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
	String lastName = CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6))
			.concat("'s");
	String resourceName, AppointmentTypeName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.getClientDirectoryPage().searchAndSelectClientNameInClientTable("Qa_test_client_01m4%kcs6t7$>0");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
			logStep("PXPP-13163 ,PXPP-13164 Verify User is able click on Appointment Types tab in the top menu on Client's General Settings page.");
			ccui.getAppointmentTypePage().verifyNavigationAppointmentTypeTile();
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify the visibility and presence of web elements of search and filter Appointment Types screen")
	public void testSearchFilterAppointmentTypesScreenWebElements() throws Exception {
		logStep("PXPP-13325- Verify search textbox with search button is present in AppointmentTypes search page - Command Center Admin");
		ccui.getAppointmentTypePage().verifySearchTextBoxAndPlaceholderIsDisplayed();
		ccui.getAppointmentTypePage().verifySearchButtonIsDisplayed();
		logStep("PXPP-13332- Verify elements of Filter container in AppointmentTypes search page - Command Center Admin");
		ccui.getAppointmentTypePage().verifyFilterLabel();
		ccui.getAppointmentTypePage().verifySystemFilterLabelTextAndPlaceholder();
		ccui.getAppointmentTypePage().verifyEnterpriseFilterLabelTextAndPlaceholder();
		ccui.getAppointmentTypePage().verifyPracticeFilterLabelTextAndPlaceholder();
		ccui.getAppointmentTypePage().verifySolutionFilterLabelTextAndPlaceholder();
		ccui.getAppointmentTypePage().clearButtonDisplayed();
		ccui.getAppointmentTypePage().clearApplyDisplayed();
		logStep("PXPP-13181 Verify user is able to SEARCH for the appointment types by name or Appointment Type ID number on appointment types management page ");
		ccui.getAppointmentTypePage().searchAppointmentTypesAndVerifyResult("Myyc07LI2U");
		logStep("PXPP-13346 Verify when user can search by AppointmentTypes name in search textbox and it is not found in AppointmentTypes web table should display message 'No data to display' - Command Center Admin");
		ccui.getAppointmentTypePage().searchNonExistingAppointmentTypesAndVerifyResult("NonExistingAppointmentTypes");
		logStep("Verify filter button functionality");
		ccui.getSearchProviderPage().clickToHideFiltersButton();
		ccui.getSearchProviderPage().verifyAllElementsOfFilterNotVisible();
		ccui.getSearchProviderPage().clickToHideFiltersButton();
		logStep("PXPP-12737 Verify pagination and Items per page dropdown should be present in AppointmentType web table");
		String defaultItemsPerPage = ccui.getAppointmentTypePage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		logStep("Check Next Button functionality");
		ccui.getAppointmentTypePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Next Button functionality when Items Per Page value is = 20");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getAppointmentTypePage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getAppointmentTypePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Next Button functionality when Items Per Page value is = 50");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getAppointmentTypePage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getAppointmentTypePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Next Button functionality when Items Per Page value is = 100");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getAppointmentTypePage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getAppointmentTypePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Next Button functionality when Items Per Page value is = 200");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getAppointmentTypePage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getAppointmentTypePage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
		logStep("Check last Button functionality");
		log("Print the RHS Pagination : " + ccui.getAppointmentTypePage().getRHSPagination());
		ccui.getAppointmentTypePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 20.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getAppointmentTypePage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + ccui.getAppointmentTypePage().getDefaultPagination());
		log("Print the RHS Pagination : " + ccui.getAppointmentTypePage().getRHSPagination());
		ccui.getAppointmentTypePage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 50.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageFiftyL = ccui.getAppointmentTypePage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFiftyL, "50");
		log("Print the RHS Pagination : " + ccui.getAppointmentTypePage().getRHSPagination());
		ccui.getAppointmentTypePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFiftyL));
		logStep("Check Last Button functionality when Items Per Page value is = 100.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageHundredL = ccui.getAppointmentTypePage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundredL, "100");
		log("Print the RHS Pagination : " + ccui.getAppointmentTypePage().getRHSPagination());
		ccui.getAppointmentTypePage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundredL));
		logStep("Check Last Button functionality when Items Per Page value is = 200.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageTwohundred = ccui.getAppointmentTypePage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwohundred, "200");
		ccui.getAppointmentTypePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwohundred));
		logStep("Check Privious Button functionality");
		ccui.getAppointmentTypePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 20");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageP = ccui.getAppointmentTypePage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageP, "20");
		ccui.getAppointmentTypePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageP));
		logStep("Check Previous Button functionality when Items Per Page value is = 50");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPagFiftyP = ccui.getAppointmentTypePage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPagFiftyP, "50");
		ccui.getAppointmentTypePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPagFiftyP));
		logStep("Check Previous Button functionality when Items Per Page value is = 100");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageHundredP = ccui.getAppointmentTypePage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundredP, "100");
		ccui.getAppointmentTypePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundredP));
		logStep("Check Previous Button functionality when Items Per Page value is = 200");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundredP = ccui.getAppointmentTypePage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundredP, "200");
		ccui.getAppointmentTypePage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundredP));
		logStep("Check First Button functionality");
		ccui.getAppointmentTypePage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check First Button functionality when Items Per Page value is = 20.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageF = ccui.getAppointmentTypePage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageF, "20");
		ccui.getAppointmentTypePage().checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageF));
		logStep("Check First Button functionality when Items Per Page value is = 50.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageFiftyF = ccui.getAppointmentTypePage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFiftyF, "50");
		ccui.getAppointmentTypePage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFiftyF));
		logStep("Check First Button functionality when Items Per Page value is = 100.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageHundredF = ccui.getAppointmentTypePage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundredF, "100");
		ccui.getAppointmentTypePage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundredF));
		logStep("Check First Button functionality when Items Per Page value is = 200.");
		ccui.getAppointmentTypePage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundredF = ccui.getAppointmentTypePage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundredF, "200");
		ccui.getAppointmentTypePage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundredF));
		logStep("PXPP-13184 Verify User is able to SORT the Appointment Types,  Appointment Categories and Status Columns in ascending or descending order.");
		logStep("Check AppointmentType status sorting");
		ccui.getAppointmentTypePage().clickAscappointTypeNameSortToDsc();
		ccui.getAppointmentTypePage().clickDscappointTypeStatusSortToAsc();
		logStep("Check AppointmentType name sorting");
		ccui.getAppointmentTypePage().clickAscappointTypeNameSortToDsc();
		ccui.getAppointmentTypePage().clickDscappointTypeNameSortToAsc();
		logStep("Check AppointmentType categories sorting");
		// ccui.getAppointmentTypePage().clickAscappointTypeCategoriesSortToDsc();
		// ccui.getAppointmentTypePage().verifyDescendingCategoriesSortIsVisible();
		logStep("Apply filter for Practice");
		ccui.getAppointmentTypePage().clickToPracticeFilter();
		ccui.getAppointmentTypePage().selectFilter("Qa_automation_practice_01");
		ccui.getAppointmentTypePage().clickToApplyFilterButton();
		ccui.getAppointmentTypePage().clickToClearFilterButton();
		logStep("Apply filter for enterprise");
		ccui.getAppointmentTypePage().clickToEnterpriseFilter();
		ccui.getAppointmentTypePage().selectFilter("Qa_automation_enterprise_01");
		ccui.getAppointmentTypePage().clickToApplyFilterButton();
		logStep("PXPP-PXPP-13183 Verify user can clear applied filter by clicking to clear button in Appointment type search page");
		ccui.getAppointmentTypePage().clickToClearFilterButton();
		logStep("Apply filter for System");
		ccui.getAppointmentTypePage().clickToSystemFilter();
		ccui.getAppointmentTypePage().selectFilter("Qa_automation_system_01");
		ccui.getAppointmentTypePage().clickToApplyFilterButton();
		ccui.getAppointmentTypePage().clickToClearFilterButton();
		ccui.getAppointmentTypePage().clickToSolutionFilter();
		ccui.getAppointmentTypePage().selectFilter(testData.getProperty("solution.chat"));
		ccui.getAppointmentTypePage().clickToApplyFilterButton();
		ccui.getAppointmentTypePage().clickToClearFilterButton();
		logStep("Apply filter for System, enterprise, practice solution at a time");
		ccui.getAppointmentTypePage().clickToSystemFilter();
		ccui.getAppointmentTypePage().selectFilter("Qa_automation_system_01");
		ccui.getAppointmentTypePage().clickToEnterpriseFilter();
		ccui.getAppointmentTypePage().selectFilter("Qa_automation_enterprise_01");
		ccui.getAppointmentTypePage().clickToPracticeFilter();
		ccui.getAppointmentTypePage().selectFilter("Qa_automation_practice_01");
		ccui.getAppointmentTypePage().clickToSolutionFilter();
		ccui.getAppointmentTypePage().selectFilter(testData.getProperty("solution.chat"));
		ccui.getAppointmentTypePage().clickToApplyFilterButton();
		runOnce = false;	
	}
}