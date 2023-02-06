// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewInternalUsersPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.DeactivateUserConfirmationPage;
import com.pxp.cc.objectmaps.DeactivateUserPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class InternalUserMultipleDeactivationTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public DeactivateUserConfirmationPage deactivateUserConfirmationPage;
	public List<String> userStatusList = null;
	public CommonMethods commonMethods;
	public AddNewInternalUsersPage addNewInternalUsersPage;
	public DeactivateUserPage deactivateUserPage;
	String firstName, firstname1;
	public CCUI ccui;

	@DataProvider(name = "invalidTestData")
	public Object[] getInvalidTestData() {
		Object[][] obj = new Object[][] { { testData.getProperty("invalid.user.first.name.one"),
				testData.getProperty("invalid.user.last.name.one"), testData.getProperty("invalid.user.email.one"),
				testData.getProperty("invalid.username.one"), testData.getProperty("invalid.user.mobile.number.one") },
				{ testData.getProperty("invalid.user.first.name.two"),
						testData.getProperty("invalid.user.last.name.two"),
						testData.getProperty("invalid.user.email.two"), testData.getProperty("invalid.username.two"),
						testData.getProperty("invalid.user.mobile.number.two") },
				{ testData.getProperty("invalid.user.first.name.three"),
						testData.getProperty("invalid.user.last.name.three"),
						testData.getProperty("invalid.user.email.three"),
						testData.getProperty("invalid.username.three"),
						testData.getProperty("invalid.user.mobile.number.three") } };
		return obj;
	}

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		ccui = new CCUI(testData, driver);
		logStep("Login to command center portal");
		ccui.getClientDirectoryPage();
		navigatetoInternalUser();

	}

	@Test(enabled = false)
	public void navigatetoInternalUser() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		ccui.getClientDirectoryPage().clickOnCommandCenterUsersIcon();
		logStep("Verify user redirected to command center users page");
		ccui.getCommandCenterUsersPage().verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		ccui.getCommandCenterUsersPage().verifyInternalUsersTabByDefaultSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify CCInternaluser status before Deactivate User.")
	public void testBulkUser() throws Exception {
		logStep("PXPP-2602 Test to verify CCInternaluser status before Deactivate User.");
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getDeactivateUserPage().clickOnActiveCheckbox();
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().verifyClearAllButtonIsDisplayed();
		ccui.getDeactivateUserPage().clickOncheckBox();
		ccui.getDeactivateUserPage().clickOndeactivateBtn();
		ccui.getDeactivateUserPage().clickOnApplyBtn();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify user is able to cancel selected multiple users for deactivation ")
	public void testToCancleButtonOnSelectUsersToDectivatePage() throws Exception {
		logStep("PXPP-2602 Test to verify CCInternaluser have permission to Deactivate.");
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getDeactivateUserPage().clickOnActiveCheckbox();
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().verifyClearAllButtonIsDisplayed();
		ccui.getDeactivateUserPage().clickOncheckBox();
		ccui.getDeactivateUserPage().clickOnApplyCancel();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify confirmation popup")
	public void testSelectedMultipleDeactivateUsersPopup() throws Exception {
		logStep("PXPP-2672 Test to verify popup is appear to CCInternaluser asking them if they would like to proceed.");
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getDeactivateUserPage().clickOnActiveCheckbox();
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().verifyClearAllButtonIsDisplayed();
		ccui.getDeactivateUserPage().clickOncheckBox();
		ccui.getDeactivateUserPage().clickOndeactivateBtn();
		logStep("verify selected multiple deactivate users  confirmation popup");
		ccui.getDeactivateUserPage().verifySelectedMultipleUsersDeactivatePopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel button on confirmation popup ")
	public void testCancleButtonOnConfirmationPopup() throws Exception {
		logStep("PXPP-2674 Verify cancel button on confirmation popup");
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getDeactivateUserPage().clickOnActiveCheckbox();
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().clickOncheckBox();
		ccui.getDeactivateUserPage().clickOndeactivateBtn();
		logStep("Verify Cancel button on Confirmationpopup");
		ccui.getDeactivateUserPage().clickOnCancelbuttonConfirmationPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Cross icon on Confirmation popup , user redirected to deactivate user page ")
	public void testCrossIconOnConfirmationPopup() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getDeactivateUserPage().clickOnActiveCheckbox();
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().verifyClearAllButtonIsDisplayed();
		ccui.getDeactivateUserPage().clickOncheckBox();
		ccui.getDeactivateUserPage().clickOndeactivateBtn();
		logStep("Verify cross icon on Confirmationpopup");
		ccui.getDeactivateUserPage().verifyCrossIconDisplayed();
		ccui.getDeactivateUserPage().clickOnCrossIcon();
		ccui.getDeactivateUserPage().verifyInternalUsersSelectUsersToDectivatePageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify back arrow on select users to deactivate page , user redirected to internal user page ")
	public void testBackArrowOnSelectUserToDeactivatePage() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnBackArrow();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search options popup")
	public void testSearchOptionsPopup() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		logStep("Verify search popup");
		ccui.getDeactivateUserPage().verifySearchInternalUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed when all fields of search options are blank")
	public void testErrorMessageWhenAllFieldsOfSearchOptionsAreBlank() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		logStep("Verify required error message is displayed for mandatory fields");
		ccui.getDeactivateUserPage().verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, lastname, username, email fields and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		logStep("Verify maximum characters reached error message for firstname, lastname, username, email fields");
		ccui.getSearchOptionsPage().verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in firstname, lastname, email, username and mobile number fields and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		logStep("Enter invalid data in firstname, lastname, email, username and mobile number fields and verify error message");
		ccui.getSearchOptionsPage().verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1],
				getTestData[2], getTestData[3], getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button on search options popup")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getSearchOptionsPage().verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		ccui.getDeactivateUserPage().verifyClearButtonIsDisplayed();
		ccui.getDeactivateUserPage().clickOnClearButton();
		logStep("Verify after clicking on cancel button data from all textbox fields gets cleared");
		ccui.getDeactivateUserPage().verifyTextboxIsEmptyWhenClickOnClearButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of Search Filter Cross Icon after getting the search result")
	public void testClearButtonFunctionalityWhenSearchResultDisplayed() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getSearchOptionsPage().verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		logStep("Verify after clicking on search button user redirected to internal users page");
		ccui.getDeactivateUserPage().clickOnClearButton();
		ccui.getDeactivateUserPage().verifyInternalUsersSelectUsersToDectivatePageTitle();
		ccui.getDeactivateUserPage().verifySearchFilterCrossIcon();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields are filled & 'CLEAR All' button clicked after search result")
	public void testSearchOptionsWithAllTheFieldsAndMultipleCheckboxes() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getSearchOptionsPage().verifyClearAllButtonFunctionalityWithAllFields(
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.client.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"), testData.getProperty("valid.user.mobile.number"));
		ccui.getDeactivateUserPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getDeactivateUserPage().selectEnrollmentStatus("Confirmed");
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().verifyInternalUsersSelectUsersToDectivatePageTitle();
		logStep("Verify clear all button is displayed");
		ccui.getDeactivateUserPage().verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		ccui.getDeactivateUserPage().clickOnClearAllButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify no results found message is displayed if searched text is not found")
	public void testNoResultsFoundMessageIfSearchedTextIsNotFound() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getSearchOptionsPage().verifyClearAllButtonFunctionalityWithAllFields(
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.client.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"), testData.getProperty("valid.user.mobile.number"));
		ccui.getDeactivateUserPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getDeactivateUserPage().selectEnrollmentStatus("Confirmed");
		logStep("Verify after clicking on search button user redirected to Select Users To Dectivate  page");
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().verifyInternalUsersSelectUsersToDectivatePageTitle();
		ccui.getDeactivateUserPage().verifyNoResultsFoundErrorMessageIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify toaster message if more than 50 matches return for the searched text")
	public void testToasterMessageIfMoreThanFiftyMatchesReturnForSearchedText() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		ccui.getDeactivateUserPage().clickOnSearchOption();
		ccui.getInternalUsersPage().verifyErrorMessageWhenNonExistingDataIsEntered(
				testData.getProperty("existing.user.common.first.name"));
		logStep("Verify after clicking on search button user redirected to internal users page");
		ccui.getDeactivateUserPage().clickOnSearchButton();
		ccui.getDeactivateUserPage().verifyInternalUsersSelectUsersToDectivatePageTitle();
		ccui.getDeactivateUserPage().verifyToasterMessageIsDisplayedIfMoreThanFiftyMatchesReturn();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 23, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndPaginationInInternalUsersWebTable() throws Exception {
		logStep("Now comparing the default Items per Page .Ideally it should be 10.");
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		CCUtils.scrollPageToEnd(driver);
		CCUtils.assertEquals(ccui.getDeactivateUserPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getDeactivateUserPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getDeactivateUserPage().getTotalNoOfInternalUsers();
		logStep("Get the Total No of Clients : " + noOfClients);
		int getNoOfRowsPagination = Integer.parseInt(ccui.getDeactivateUserPage().getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(ccui.getDeactivateUserPage().isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getDeactivateUserPage().isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getDeactivateUserPage().isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(ccui.getDeactivateUserPage().isLastPageButtonDisabled(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 24, description = "Verify numbers of rows and Pagination should be change as per 'Items Per Page value")
	public void testAllPaginationButtonsClickable() throws Exception {
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		CCUtils.assertEquals(ccui.getDeactivateUserPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getDeactivateUserPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getDeactivateUserPage().getTotalNoOfInternalUsers();
		logStep("Get the Total No of Clients : " + noOfClients);
		ccui.getDeactivateUserPage().clickNextPageButton();
		ccui.getDeactivateUserPage().clickNextPageButton();
		ccui.getDeactivateUserPage().clickNextPageButton();
		ccui.getDeactivateUserPage().clickPreviousPageButton();
		ccui.getDeactivateUserPage().clickLastPageButton();
		ccui.getDeactivateUserPage().clickFirstPageButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 25, description = "Check Next Button functionality")
	public void testNextPageButtonFunctionality() throws NumberFormatException, Exception {
		logStep("Check Next Button functionality when Items Per Page value is = 20.");
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		String defaultItemsPerPage = ccui.getDeactivateUserPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getDeactivateUserPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Next Button functionality when Items Per Page value is = 20.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getDeactivateUserPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getDeactivateUserPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Next Button functionality when Items Per Page value is = 50.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getDeactivateUserPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getDeactivateUserPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Next Button functionality when Items Per Page value is = 100.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getDeactivateUserPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getDeactivateUserPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Next Button functionality when Items Per Page value is = 200.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getDeactivateUserPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getDeactivateUserPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 26, description = "Check Last Button functionality")
	public void testLastPageButtonFunctionality() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		String defaultItemsPerPage = ccui.getDeactivateUserPage().getDefaultItemsPerPage();
		logStep("Check Last Button functionality when Items Per Page value is = 10.");
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getDeactivateUserPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 20.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getDeactivateUserPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + ccui.getDeactivateUserPage().getDefaultPagination());
		ccui.getDeactivateUserPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 50.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getDeactivateUserPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getDeactivateUserPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Last Button functionality when Items Per Page value is = 100.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getDeactivateUserPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getDeactivateUserPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Last Button functionality when Items Per Page value is = 200.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageTwohundred = ccui.getDeactivateUserPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwohundred, "200");
		ccui.getDeactivateUserPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwohundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 27, description = "Check Previous Button functionality")
	public void testPreviousPageButtonFunctionality() throws NumberFormatException, Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		String defaultItemsPerPage = ccui.getDeactivateUserPage().getDefaultItemsPerPage();
		logStep("Check Previous Button functionality when Items Per Page value is = 10.");
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getDeactivateUserPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 20.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getDeactivateUserPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		ccui.getDeactivateUserPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 50.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getDeactivateUserPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getDeactivateUserPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Previous Button functionality when Items Per Page value is = 100.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageOnehundred = ccui.getDeactivateUserPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageOnehundred, "100");
		logStep("Check Previous Button functionality when Items Per Page value is = 200.");
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		String itemsPerPageTwohundred = ccui.getDeactivateUserPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwohundred, "200");
		ccui.getDeactivateUserPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwohundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 28, description = "Check First Button functionality")
	public void testFirstPageButtonFunctionality() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		String defaultItemsPerPage = ccui.getDeactivateUserPage().getDefaultItemsPerPage();
		logStep("Check First Button functionality when Items Per Page value is = 10.");
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getDeactivateUserPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 20.");
		String itemsPerPageTwenty = ccui.getDeactivateUserPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getDeactivateUserPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 50.");
		String itemsPerPageFifty = ccui.getDeactivateUserPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getDeactivateUserPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 100.");
		String itemsPerPageOneHundred = ccui.getDeactivateUserPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageOneHundred, "100");
		ccui.getDeactivateUserPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageOneHundred));
		ccui.getDeactivateUserPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 200.");
		String itemsPerPageTwoHundred = ccui.getDeactivateUserPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getDeactivateUserPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 29, description = "Check Sorting functionality for Firstname, Lastname, Email, Enrollment status, UserStatus columns")
	public void testSortingFunctionality() throws Exception {
		ccui.getInternalUsersPage().clickOnBulkDeactivate();
		logStep("Check firstName sorting");
		ccui.getInternalUsersPage().clickAscFirstNameSortToDsc();
		ccui.getInternalUsersPage().clickDscFirstNameSortToAsc();
		logStep("Check flastName sorting");
		ccui.getInternalUsersPage().clickAscLastNameSortToDsc();
		ccui.getInternalUsersPage().clickDscLastNameSortToAsc();
		logStep("Check email sorting");
		ccui.getInternalUsersPage().clickAscEmailSortToDsc();
		ccui.getInternalUsersPage().clickDscEmailSortToAsc();
		logStep("Check enrollment status sorting");
		ccui.getInternalUsersPage().clickAscEnrollmentStatusSortToDsc();
		ccui.getInternalUsersPage().clickDscEnrollmentStatusSortToAsc();
		logStep("Check UserStatus sorting");
		ccui.getInternalUsersPage().clickAscUserStatusSortToDsc();
		ccui.getInternalUsersPage().clickDscUserStatusSortToAsc();
	}
}