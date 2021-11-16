// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

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

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search options internal user popup")
	public void testSearchOptionsInternalUserPopup() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		logStep("Verify search internal user popup");
		searchOptionsPage.verifySearchInternalUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed for search option page")
	public void testErrorMessageWhenAllFieldsOfSearchOptionsIsBlank() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		logStep("Verify required error message is displayed for mandatory fields");
		searchOptionsPage.verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, last name,username email and mobile field and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		logStep("Verify maximum character reached error message for firstname, last name,username email and mobile field");
		searchOptionsPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateNewEmail(101), CCUtils.generateMobileNumber());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		logStep("Enter invalid data in first name, last name, email, username and mobile number field and verify error message");
		searchOptionsPage.verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1], getTestData[2],
				getTestData[3], getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button on search option page")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		searchOptionsPage.verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionsPage.clickOnClearButton();
		logStep("Verify after clicking on cancel button user redirected to internal users page");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button after getting the search result")
	public void testClearButtonFunctionalityWhenSearchResultDisplayed() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		searchOptionsPage.verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		logStep("Verify after clicking on cancel button user redirected to internal users page");
		searchOptionsPage.clickOnSearchButton();
		searchOptionsPage.verifySearchFilterCrossIcon();
		internalUsersPage.verifyInternalUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields are fielded & clear All button selected after search result")
	public void testSearchOptionsWithAllTheFieldsAndMultipleCheckboxes() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		searchOptionsPage.verifyClearAllButtonFunctionalityWithAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		internalUsersPage.verifyInternalUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search options when only single status checkbox is selected")
	public void testSearchOptionsWithSingleChecboxOfStatus() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on search button");
		internalUsersPage.clickOnSearchOptionPage();
		searchOptionsPage = new SearchOptionsPage(driver);
		searchOptionsPage.selectSingleCheckboxOfEnrollmentStatus();
		searchOptionsPage.selectSingleCheckboxOfUserStatus();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
	}

}
