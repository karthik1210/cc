//Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
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
			//getClientSystementErpriseandPractice();
			 logStep("Searching a client in client directory");
		        ccui.getClientDirectoryPage().searchAndSelectClientNameInClientTable("Client For Staff Testingnew");
		        logStep("Navigating To General Settings page");
		        ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		}
	}
	
		@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new Staff using valid test data")
		public void testAddNewStaffFunctionalityUsingValidData() throws Exception {
			navigatetoStaff();
			logStep("Verify add new Staff popup");
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
			logStep("Verify navigation to Staff page with Staff tab and Staff tile on General Settings page");
			ccui.getStaffPage().clickOnStaffTab();
			logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
			ccui.getStaffPage().verifyAddButtonIsEnabled();
			logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
			ccui.getStaffPage().clickOnAddStaffButton();
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
			logStep("Enter more than 100 characters in StaffName, more than 50 characters in ExternalID fields ,verify error message and invalid value StaffGender");
			ccui.getStaffPage().clickOnStaffTab();
			logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
			ccui.getStaffPage().verifyAddButtonIsEnabled();
			logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
			ccui.getStaffPage().clickOnAddStaffButton();
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
			CCUtils.assertEquals(ccui.getStaffPage().getValidErrorForFirstNameField(),
					"Please enter valid first name");
			ccui.getStaffPage().enterLastName(testData.getProperty("invalid.user.last.name.one"));
			CCUtils.assertEquals(ccui.getStaffPage().getValidErrorForLastNameField(),
					"Please enter valid last name"); 
			ccui.getStaffPage().clickOnCancelButton();
			logStep("Verify navigation to Staff page with Staff tab and Staff tile on General Settins page");
			ccui.getStaffPage().clickOnStaffTab();
			logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
			ccui.getStaffPage().verifyAddButtonIsEnabled();
			logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
			ccui.getStaffPage().clickOnAddStaffButton();
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
			logStep("Verify after clicking hide button filter section should not get displayed");
			ccui.getStaffPage().clickOnStaffTab();
			logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
			ccui.getStaffPage().verifyAddButtonIsEnabled();
			logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
			ccui.getStaffPage().clickOnAddStaffButton();
			ccui.getStaffPage().clickOnCancelButton();
			ccui.getStaffPage().verifyFilterLabel();
			ccui.getStaffPage().verifySystemFilterLabelTextAndPlaceholder();
			ccui.getStaffPage().verifyEnterpriseFilterLabelTextAndPlaceholder();
			ccui.getStaffPage().verifyPracticeFilterLabelTextAndPlaceholder();
			ccui.getStaffPage().verifySolutionFilterLabelTextAndPlaceholder();
			ccui.getStaffPage().clearButtonDisplayed();
			ccui.getStaffPage().clearApplyDisplayed();
			ccui.getStaffPage().clickToHideFiltersButton();
			ccui.getStaffPage().clickToShowFiltersButton();
			ccui.getStaffPage().verifyAllElementsOfFilterNotVisible();
			logStep("Verify search result of a staff");
			ccui.getStaffPage().clickOnStaffTab();	
			logStep("PXPP-13256 Verify User is able to SEARCH for Staff member by NAME  on Providers, Resources & Staff page ");
			ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
			logStep("Navigated to staff details page");
			ccui.getStaffPage().clickOnStaffTab();	
			ccui.getStaffPage().enterNonExistingStaffAndVerifyResult("NonExistingResources");
			ccui.getStaffPage().verifyNoDataDisplayErrorMessage();
			runOnce = false;
			logStep("Verify search result when apply system, enterprise, practice filter in search Staff screen");
			ccui.getStaffPage().clickOnStaffTab();
			logStep("PXPP-13297 Verify HIDE FILTERS, CLEAR, APPLY, +ADD, IMPORT buttons are displayed and enabled on the Providers, Resources & Staff page");
			ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
			logStep("Navigated to staff details page");
			ccui.getStaffPage().clickOnStaffTab();
			ccui.getStaffPage().selectStaff();
			ccui.getStaffPage().verifyStaffDetailsTitle();
			ccui.getStaffPage().clickToBackButton();	
			logStep("Apply filter for System");
			logStep("PXPP-13257 Verify Users can FILTER Staff  by System, Enterprise, Practice and Solutions they are associated within CC");
			ccui.getStaffPage().clickToSystemFilter();
			ccui.getStaffPage().clickFirstInAutoSuggestion();
			ccui.getStaffPage().clickToApplyFilterButton();
			ccui.getStaffPage().clickToClearFilterButton();
			logStep("Apply filter for enterprise");
			ccui.getStaffPage().clickToEnterpriseFilter();
			ccui.getStaffPage().clickFirstInAutoSuggestion();
			ccui.getStaffPage().clickToApplyFilterButton();
			ccui.getStaffPage().clickToClearFilterButton();
			logStep("Apply filter for Practice");
			ccui.getStaffPage().clickToPracticeFilter();
			ccui.getStaffPage().clickFirstInAutoSuggestion();
			ccui.getStaffPage().clickToApplyFilterButton();
			logStep("Check Status, Staff firstname, lastname, sorting from ascending to descending and from ascending to descending");
			ccui.getStaffPage().clickOnStaffTab();
			logStep(" Verify staff first name column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
			ccui.getStaffPage().clickAscStaffTypeSortToDsc();
			ccui.getStaffPage().verifyStaffTypeDescendingSortIsVisible();
			ccui.getStaffPage().clickDscStaffTypeSortToAsc();
			ccui.getStaffPage().verifyStaffTypeAscendingSortIsVisible();
			logStep(" Verify staff last name column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
			ccui.getStaffPage().clickAscStaffLastNameSortToDsc();
			ccui.getStaffPage().verifyStaffLastNameDescendingSortIsVisible();
			ccui.getStaffPage().clickDscStaffLasttNameSortToAsc();
			ccui.getStaffPage().verifyStafflastNameAscendingSortIsVisible();
			logStep("Verify staff status column can be arranged in descending order by clicking to Ascending/descending filter pointing downwards");
			ccui.getStaffPage().clickStaffStatusSortToDsc();
			ccui.getStaffPage().verifyStaffStatusDescendingSortIsVisible();
			ccui.getStaffPage().clickDscStaffStatusSortToAsc();
			ccui.getStaffPage().verifyStaffStatusAscendingSortIsVisible();
			logStep("Verify pagination and Items per page dropdown should be present in Staff web table");
			String defaultItemsPerPage = ccui.getStaffPage().getDefaultItemsPerPage();
			CCUtils.assertEquals(defaultItemsPerPage, "10");
			ccui.getStaffPage()
					.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
			ccui.getStaffPage().clickItemsPerPageDropdown();
			String itemsPerPageTwenty = ccui.getStaffPage().clickTwentyItemsPerPage();
			CCUtils.assertEquals(itemsPerPageTwenty, "20");
			ccui.getStaffPage()
					.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
			ccui.getStaffPage().clickItemsPerPageDropdown();
			String itemsPerPageFifty = ccui.getStaffPage().clickFiftyItemsPerPage();
			CCUtils.assertEquals(itemsPerPageFifty, "50");
			ccui.getStaffPage()
					.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
			ccui.getStaffPage().clickItemsPerPageDropdown();
			String itemsPerPageHundred = ccui.getStaffPage().clickHundredItemsPerPage();
			CCUtils.assertEquals(itemsPerPageHundred, "100");
			ccui.getStaffPage()
					.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
			ccui.getStaffPage().clickItemsPerPageDropdown();
			String itemsPerPageTwoHundred = ccui.getStaffPage().clickTwoHundredItemsPerPage();
			CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
			ccui.getStaffPage()
					.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));		
		}
		
		@Test(enabled = false)
		public void navigatetoStaff() throws Exception {
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
	}
