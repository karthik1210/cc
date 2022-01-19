// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.objectmaps.VarResellerUsersPage;
import com.pxp.cc.util.CCUtils;

public class VarResellerUsersSearchOptionsTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public VarResellerUsersPage varResellerUsersPage;
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
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select VAR/Reseller users tab in command center users page");
		varResellerUsersPage = commandCenterUsersPage.clickOnVarResellerUsersTab();
		varResellerUsersPage.verifyVarResellerUsersPageTitle();
		logStep("Click on search options button");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search VAR/Reseller user popup")
	public void testSearchVarResellerUserPopup() throws Exception {
		logStep("Verify search VAR/Reseller user popup");
		searchOptionsPage.verifySearchVarResellerUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed for search options page")
	public void testErrorMessageWhenAllFieldsOfSearchOptionsIsBlank() throws Exception {
		logStep("Click on search  button when all fields of search options are blank");
		searchOptionsPage.verifySearchButtonIsDisplayed();
		searchOptionsPage.clickToSearchButton();
		searchOptionsPage.verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, last name,username, and email field and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum character reached error message for firstname, last name,username, email field");
		searchOptionsPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateNewEmail(101));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		logStep("Enter invalid data in first name, last name, email, username and mobile number field and verify error message");
		searchOptionsPage.verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1], getTestData[2],
				getTestData[3], getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button on search option popup")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {
		logStep("Search user using first name, last name, user name, email, mobile number and enrollment status");
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.Confirmed"));
		enrollmentStatusList.add(testData.getProperty("enrollment.status.pending"));
		searchOptionsPage.searchVarResellerUser(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.var.reseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"), enrollmentStatusList);
		logStep("Verify clear button is displayed on search options popup");
		searchOptionsPage.verifyClearButtonIsDisplayed();
		logStep("Click on clear button");
		searchOptionsPage.clickOnClearButton();
		logStep("Verify after clicking on clear button, all text-box and checkbox got empty");
		searchOptionsPage.verifyTextboxIsEmptyWhenClickOnClearButton();
		searchOptionsPage.verifyVarResellerTextboxIsEmptyWhenClickOnClearButton();
		searchOptionsPage.verifyStatusCheckboxesAreNotByDefaultSelectedOnVarResellerSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search options when only single status checkbox is selected")
	public void testSearchOptionsWithSingleCheckboxOfStatus() throws Exception {
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.Active"));
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.Confirmed"));
		varResellerUsersPage.verifySelectedSearchOptionsWithSingleCheckboxOfStatus(userStatusList,
				enrollmentStatusList);
		logStep("Verify clear all button is displayed");
		varResellerUsersPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		varResellerUsersPage.clickOnClearAllButton();
		logStep("Verify all search options are cleared after clicking the Clear All button.");
		varResellerUsersPage.verifyAllOptionsGotCleared();

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields without  checkboxes are selected")
	public void testSearchOptionsWithAllTheFieldsAndWithoutCheckboxes() throws Exception {
		searchOptionsPage.verifyClearAllButtonFunctionalityWithAllFields(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.VARReseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		logStep("Click on search button on the search options popup");
		searchOptionsPage.clickToSearchButton();
		logStep("Verify that the selected search options are displayed on the VAR/Reseller page.");
		varResellerUsersPage.verifySelectedSearchOptionsWithoutStatus(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.var.reseller.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"));
		logStep("Verify clear all button is displayed");
		varResellerUsersPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		varResellerUsersPage.clickOnClearAllButton();
		logStep("Verify all search options are cleared after clicking the Clear All button.");
		varResellerUsersPage.verifyAllOptionsGotCleared();

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields with multiple checkboxes are filled ")
	public void testSearchOptionsWithAllTheFieldsAndMultipleCheckboxes() throws Exception {
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.pending"));
		enrollmentStatusList.add(testData.getProperty("enrollment.status.notsent"));
		searchOptionsPage.searchVarResellerUser(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.var.reseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"), enrollmentStatusList);
		logStep("Click on search button on the search options popup");
		searchOptionsPage.clickOnSearchButton();
		logStep("Verify after clicking on search button on the each options popup, user redirected to Var/Reseller users page");
		varResellerUsersPage.verifyVarResellerUsersPageTitle();
		logStep("Verify selected search options are displayed on VAR/Reseller users page.");
		varResellerUsersPage.verifySelectedSearchOptions(enrollmentStatusList,
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.var.reseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"));
		logStep("Verify clear all button is displayed");
		varResellerUsersPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		varResellerUsersPage.clickOnClearAllButton();
		logStep("Verify all search options are cleared after clicking the Clear All button.");
		varResellerUsersPage.verifyAllOptionsGotCleared();

	}

}
