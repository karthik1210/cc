// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CCUtils;

public class ClientUsersSearchOptionsTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUserPage;
	public SearchOptionsPage searchOptionsPage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon in left side navigation panel");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		clientUserPage = commandCenterUsersPage.clickOnClientUsersTab();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search options for client user popup")
	public void testSearchOptionsClientUserPopup() throws Exception {
		logStep("Verify search client user popup");
		searchOptionsPage.verifySearchClientUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed in search option page")
	public void testErrorMessageWhenAllFieldsOfSearchOptionsIsBlank() throws Exception {
		logStep("Click on search  button when all fields of search options are blank");
		searchOptionsPage.verifySearchButtonIsDisplayed();
		searchOptionsPage.clickToSearchButton();
		searchOptionsPage.verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, last name,username and email field and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum character reached error message for firstname, last name,username, email and mobile field");
		searchOptionsPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
	}

	@DataProvider(name = "invalidTestData")
	public Object[] getInvalidTestData() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("invalid.user.first.name.one"),
						testData.getProperty("invalid.user.last.name.one"),
						testData.getProperty("invalid.user.email.one"), testData.getProperty("invalid.username.one"),
						testData.getProperty("invalid.user.mobile.number.three") },
				{ testData.getProperty("invalid.user.first.name.two"),
						testData.getProperty("invalid.user.last.name.two"),
						testData.getProperty("invalid.user.email.two"), testData.getProperty("invalid.username.two"),
						testData.getProperty("invalid.user.mobile.number.three") },
				{ testData.getProperty("invalid.user.first.name.three"),
						testData.getProperty("invalid.user.last.name.three"),
						testData.getProperty("invalid.user.email.three"),
						testData.getProperty("invalid.username.three"),
						testData.getProperty("invalid.user.mobile.number.three") } };
		return obj;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		logStep("Enter invalid data in first name, last name, client, email, username and mobile number field and verify error message");
		searchOptionsPage.verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1], getTestData[2],
				getTestData[3], getTestData[4]);
		searchOptionsPage.clickOnSearchButton();
		clientUserPage = new ClientUsersPage(driver);
		logStep("Verify after clicking on search button  popup, user redirected to Var/Reseller users page");
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.verifyErrorMessageOnClientUserPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search options when only single status checkbox is selected")
	public void testSearchOptionsWithSingleChecboxOfStatus() throws Exception {
		searchOptionsPage.selectUserStatus(Arrays.asList("Active"));
		searchOptionsPage.selectEnrollmentStatusValues(Arrays.asList("Confirmed"));
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		searchOptionsPage.verifyClearAllButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button on search option page")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {

		logStep("Search user using first name, last name, user name, email, mobile number,user status and enrollment status");
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.pending"));
		enrollmentStatusList.add(testData.getProperty("enrollment.status.notsent"));
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.enterSearchTextInSearchFields(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"), testData.getProperty("valid.user.mobile.number"));
		searchOptionsPage.selectEnrollmentStatusValues(enrollmentStatusList);
		searchOptionsPage.selectUserStatus(userStatusList);
		logStep("Verify clear button is displayed on search options popup");
		searchOptionsPage.verifyClearButtonIsDisplayed();
		logStep("Click on clear button");
		searchOptionsPage.clickOnClearButton();
		logStep("Verify after clicking on clear button, all text-box and checkbox got empty");
		searchOptionsPage.verifyTextboxIsEmptyWhenClickOnClearButton();
		searchOptionsPage.verifyEnrollmentStatusCheckboxesAreNotByDefaultSelected();
		searchOptionsPage.verifyUserStatusCheckboxesAreNotByDefaultSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of CLEAR ALL button after getting the search result")
	public void testClearButtonFunctionalityWhenSearchResultDisplayed() throws Exception {
		searchOptionsPage.verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.client.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"), testData.getProperty("valid.user.mobile.number"));
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Verify clear all button is displayed");
		clientUserPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		clientUserPage.clickOnClearAllButton();
		clientUserPage.verifyClientUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields with single checkbox are filled & 'CLEAR All' button clicked after search result")
	public void testSearchOptionsWithAllTheFieldsAndSingleCheckbox() throws Exception {
		searchOptionsPage.verifyClearAllButtonFunctionalityWithAllFields(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.client.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		searchOptionsPage.selectUserStatus(Arrays.asList("Active"));
		searchOptionsPage.selectEnrollmentStatusValues(Arrays.asList("Confirmed"));
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Verify clear all button is displayed");
		clientUserPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		clientUserPage.clickOnClearAllButton();
	}

	@Test(enabled = true, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of search button when all the fields with multiple checkboxes are filled & 'CLEAR All' button clicked after search result")
	public void testSearchOptionsWithAllTheFieldsAndMultipleCheckboxes() throws Exception {
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.notsent"));
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.enterSearchTextInSearchFields(testData.getProperty("client.user.first.name"),
				testData.getProperty("client.user.last.name"), testData.getProperty("client.user.email"),
				testData.getProperty("client.username"), testData.getProperty("client.user.mobile.number"));
		searchOptionsPage.enterSeachTextInClientNameField(testData.getProperty("valid.client.name"));
		searchOptionsPage.selectEnrollment(enrollmentStatusList);
		searchOptionsPage.clickOnNoRolesAssignedCheckbox();
		logStep("Click on search button on the search options popup");
		searchOptionsPage.clickOnSearchButton();
		clientUserPage = new ClientUsersPage(driver);
		logStep("Verify after clicking on search button on the each options popup, user redirected to Var/Reseller users page");
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Verify selected search options are displayed on VAR/Reseller users page.");
		clientUserPage.verifySelectedSearchOptions(enrollmentStatusList, userStatusList,
				testData.getProperty("client.user.first.name"), testData.getProperty("client.user.last.name"),
				testData.getProperty("client.user.email"), testData.getProperty("client.username"),
				testData.getProperty("client.user.mobile.number"));
		logStep("Verify clear all button is displayed");
		clientUserPage.verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		clientUserPage.clickOnClearAllButton();
	}
}
