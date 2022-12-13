  // Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.Arrays;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CCUtils;

public class SearchOptionsTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
	}

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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search options internal user popup")
	public void testSearchOptionsInternalUserPopup() throws Exception {
		logStep("Verify search internal user popup");
		searchOptionsPage.verifySearchInternalUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed when all fields of search options are blank")
	public void testErrorMessageWhenAllFieldsOfSearchOptionsAreBlank() throws Exception {
		logStep("Verify required error message is displayed for mandatory fields");
		searchOptionsPage.verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, lastname, username, email fields and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum characters reached error message for firstname, lastname, username, email fields");
		searchOptionsPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in firstname, lastname, email, username and mobile number fields and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		logStep("Enter invalid data in firstname, lastname, email, username and mobile number fields and verify error message");
		searchOptionsPage.verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1], getTestData[2],
				getTestData[3], getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button on search options popup")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {
		searchOptionsPage.verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionsPage.verifyClearButtonIsDisplayed();
		searchOptionsPage.clickOnClearButton();
		logStep("Verify after clicking on cancel button data from all textbox fields gets cleared");
		searchOptionsPage.verifyTextboxIsEmptyWhenClickOnClearButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of Search Filter Cross Icon after getting the search result")
	public void testClearButtonFunctionalityWhenSearchResultDisplayed() throws Exception {
		searchOptionsPage.verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		logStep("Verify after clicking on search button user redirected to internal users page");
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		searchOptionsPage.verifySearchFilterCrossIcon();
	}

	@Test(enabled = true, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields are filled & 'CLEAR All' button clicked after search result")
	public void testSearchOptionsWithAllTheFieldsAndMultipleCheckboxes() throws Exception {
		searchOptionsPage.verifyClearAllButtonFunctionalityWithAllFields(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.client.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionsPage.selectUserStatus(Arrays.asList("Active"));
		searchOptionsPage.selectEnrollmentStatus("Confirmed");
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		logStep("Verify clear all button is displayed");
		internalUsersPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		internalUsersPage.clickOnClearAllButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search options when only single status checkbox is selected")
	public void testSearchOptionsWithSingleCheckboxOfStatusIsSelected() throws Exception {
		searchOptionsPage.selectUserStatus(Arrays.asList("Active"));
		searchOptionsPage.selectEnrollmentStatusValues(Arrays.asList("Confirmed"));
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		logStep("Verify clear all button is displayed");
		internalUsersPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		internalUsersPage.clickOnClearAllButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields are filled with single checkbox selected & 'CLEAR All' button clicked after search result")
	public void testSearchOptionsWithAllTheFieldsAndSingleCheckbox() throws Exception {
		searchOptionsPage.verifyClearAllButtonFunctionalityWithAllFields(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.client.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionsPage.selectUserStatus(Arrays.asList("Active"));
		searchOptionsPage.selectEnrollmentStatus("Confirmed");
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		logStep("Verify clear all button is displayed");
		internalUsersPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		internalUsersPage.clickOnClearAllButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify no results found message is displayed if searched text is not found")
	public void testNoResultsFoundMessageIfSearchedTextIsNotFound() throws Exception {
		internalUsersPage.verifyErrorMessageWhenNonExistingDataIsEntered(
				testData.getProperty("non.existing.user.first.name").concat(CCUtils.generateAlphaNumericString(2)));
		logStep("Verify after clicking on search button user redirected to internal users page");
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.verifyNoResultsFoundErrorMessageIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify toaster message if more than 50 matches return for the searched text")
	public void testToasterMessageIfMoreThanFiftyMatchesReturnForSearchedText() throws Exception {
		internalUsersPage.verifyErrorMessageWhenNonExistingDataIsEntered(
				testData.getProperty("existing.user.common.first.name"));
		logStep("Verify after clicking on search button user redirected to internal users page");
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.verifyToasterMessageIsDisplayedIfMoreThanFiftyMatchesReturn();
	}
}
