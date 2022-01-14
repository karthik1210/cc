// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.Arrays;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsVARResellerUsersPage;
import com.pxp.cc.objectmaps.VARResellerUsersPage;
import com.pxp.cc.util.CCUtils;

public class SearchOptionsVARResellerUserTest extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public VARResellerUsersPage varResellerUsersPage;
	public SearchOptionsVARResellerUsersPage searchOptionVARResellerUsersPage;

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
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select VAR/Reseller users tab in command center users page");
		varResellerUsersPage = commandCenterUsersPage.clickOnVARResellerUsersTab();
		varResellerUsersPage.verifyVARResellerUsersPageTitle();
		logStep("Click on search options button");
		searchOptionVARResellerUsersPage = varResellerUsersPage.clickOnSearchOptionVARResellerUsersPage();
		logStep("Verify search VAR/Reseller user popup");
		searchOptionVARResellerUsersPage.verifySearchVARResellerUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search options button functionality")
	public void testSearchOptionsButtonFunctionality() throws Exception {
		logStep("Click on search options button");
		searchOptionVARResellerUsersPage = varResellerUsersPage.clickOnSearchOptionVARResellerUsersPage();
		logStep("Verify search VAR/Reseller user popup");
		searchOptionVARResellerUsersPage.verifySearchVARResellerUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed for search options page")
	public void testErrorMessageWhenAllFieldsOfSearchOptionsIsBlank() throws Exception {
			logStep("Click on search  button when all fields of search options are blank");
			searchOptionVARResellerUsersPage.verifySearchVARResellerUserPopup();
			searchOptionVARResellerUsersPage.verifySearchButtonIsDisplayed();
			searchOptionVARResellerUsersPage.clickonSearchButton();
		    searchOptionVARResellerUsersPage.verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, last name,username, email and mobile field and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum character reached error message for firstname, last name,username, email and mobile field");
		searchOptionVARResellerUsersPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateNewEmail(101));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		logStep("Enter invalid data in first name, last name, email, username and mobile number field and verify error message");
		searchOptionVARResellerUsersPage.verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1], getTestData[2],
				getTestData[3], getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button on search option popup")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {
		
		searchOptionVARResellerUsersPage.verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionVARResellerUsersPage.verifyClearButtonIsDisplayed();
		searchOptionVARResellerUsersPage.clickOnClearButton();
		logStep("Verify after clicking on clear button user redirected to VAR/Reseller users page");
		searchOptionVARResellerUsersPage.verifyTextboxIsEmptyWhenClickOnClearButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search options when only single status checkbox is selected")
	public void testSearchOptionsWithSingleChecboxOfStatus() throws Exception {
		
		searchOptionVARResellerUsersPage.selectSingleCheckboxOfEnrollmentStatus();
		searchOptionVARResellerUsersPage.selectSingleCheckboxOfUserStatus();
		searchOptionVARResellerUsersPage.clickOnSearchButton();
		varResellerUsersPage.verifyVARResellerUsersPageTitle();

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields with multiple checkboxes are filled ")
	public void testSearchOptionsWithAllTheFieldsAndMultipleCheckboxes() throws Exception {
		searchOptionVARResellerUsersPage.verifyClearAllButtonFunctionalityWithAllFields(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.VARReseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionVARResellerUsersPage.selectUserStatus(Arrays.asList("Active", "Inactive"));
		searchOptionVARResellerUsersPage.selectEnrollmentStatusValues(Arrays.asList("Confirmed", "Delivered", "Not Sent"));
		searchOptionVARResellerUsersPage.clickOnSearchButton();
		searchOptionVARResellerUsersPage.verifyClearAllButtonIsDisplayed();
		searchOptionVARResellerUsersPage.clickOnClearAllSearchData();
		varResellerUsersPage.verifyVARResellerUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields without  checkboxes are selected")
	public void testSearchOptionsWithAllTheFieldsAndWithoutCheckboxes() throws Exception {
		searchOptionVARResellerUsersPage.verifyClearAllButtonFunctionalityWithAllFields(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.VARReseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionVARResellerUsersPage.clickOnSearchButton();
		searchOptionVARResellerUsersPage.verifyClearAllButtonIsDisplayed();
		searchOptionVARResellerUsersPage.clickOnClearAllSearchData();
		varResellerUsersPage.verifyVARResellerUsersPageTitle();
	}

}



