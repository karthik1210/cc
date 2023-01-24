// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;
import java.awt.AWTException;
import java.util.Arrays;

public class InternalUserPageTests extends CommandCenterGenericTest {
	public CCUI ccui;
	String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
	String lastName = CCUtils.generateAlphaNumericString(12);
	String email = CCUtils.generateNewEmail(12);
	String userName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(12);
	long mobileNumber = CCUtils.generateMobileNumber();

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		ccui = new CCUI(testData, driver);
		logStep("Login to command center portal");
		ccui.getClientDirectoryPage();
		navigatetoInternalUser();
	}

	@DataProvider(name = "invalidTestData")
	public Object[] getInvalidTestData() {
		Object[][] obj = new Object[][] { { testData.getProperty("invalid.user.first.name.one"),
				testData.getProperty("invalid.user.last.name.one"), testData.getProperty("invalid.user.email.one"),
				testData.getProperty("invalid.username.one"), testData.getProperty("invalid.user.mobile.number.one") },
				{ testData.getProperty("invalid.user.first.name.two"),
						testData.getProperty("invalid.user.last.name.two"),
						testData.getProperty("invalid.user.email.two"), testData.getProperty("invalid.username.two"),
						testData.getProperty("invalid.user.mobile.number.two") } };
		return obj;
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify add new internal user popup")
	public void testAddNewInternalUserPopup() throws Exception {
		logStep("PXPP-3482 Verify Add New Internal Pop up UI");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		logStep("PXPP-2894 Verify Box Text displayed for Text box field");
		ccui.getAddNewInternalUsersPage().verifyAddNewInternalUserPageTitle();
		ccui.getCommonMethods().verifyFirstNameLabelTextAndTextboxDefaultValue();
		ccui.getCommonMethods().verifyLastNameLabelTextAndTextboxDefaultValue();
		ccui.getCommonMethods().verifyUsernameLabelTextAndTextboxDefaultValue();
		ccui.getCommonMethods().verifyEmailLabelTextAndTextboxDefaultValue();
		ccui.getCommonMethods().verifyMobileNumberLabelTextAndTextboxDefaultValue();
		ccui.getCommonMethods().verifyStatusLabelTextAndRadioButtons();
		ccui.getCommonMethods().verifyActiveRadioButtonByDefaultSelected();
		ccui.getAddNewInternalUsersPage().verifyUserRoles();
		ccui.getAddNewInternalUsersPage().verifyCrossIconIsDisplayed();
		ccui.getAddNewInternalUsersPage().verifyCancelButtonIsDisplayed();
		ccui.getAddNewInternalUsersPage().verifySaveAndAddButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Verify add new active internal user(without user role) functionality")
	public void testCreateActiveInternalUserWithoutRole() throws Exception {
		logStep("PXPP-3523 Create Active Internal User without role and verify newly created internal user details in internal user table");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
		ccui.getCommonMethods().enterFirstName(firstName);
		ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(12));
		ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(12));
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericString(20));
		ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
		ccui.getAddNewInternalUsersPage().clickOnCautionYes();
		ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify add new inactive internal user(without user role) functionality")
	public void testCreateInactiveInternalUserWithoutRole() throws Exception {
		logStep("PXPP-3525 Create Inactive Internal User without role and verify newly created internal user details in internal user table");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
		ccui.getCommonMethods().enterFirstName(firstName);
		ccui.getCommonMethods().enterLastName(lastName);
		ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(12));
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericString(20));
		ccui.getCommonMethods().selectInactiveRadiobutton();
		ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
		ccui.getAddNewInternalUsersPage().clickOnCautionYes();
		ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
	}

	@Test(enabled = true, groups = {
			"buildvalidation" }, priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active internal user(with user roles) functionality")
	public void testCreateActiveInternalUserWithRoles() throws Exception {
		logStep("PXPP-3522 Create Active Internal User with roles and verify newly created internal user details in internal user table");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getCommonMethods().enterFirstName(firstName);
		ccui.getCommonMethods().enterLastName(lastName);
		ccui.getCommonMethods()
				.enterEmail(testData.getProperty("valid.user.first.name").concat(CCUtils.generateNewEmail(12)));
		ccui.getCommonMethods()
				.enterUserName(testData.getProperty("new.client.name2").concat(CCUtils.generateAlphaNumericString(20)));
		ccui.getCommonMethods().enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		ccui.getAddNewInternalUsersPage().addUserRoles();
		ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 5, description = "Verify add new inactive internal user(with user roles) functionality")
	public void testCreateInactiveInternalUserWithRoles() throws Exception {
		logStep("PXPP-3521 Create Inactive Internal User with roles and verify newly created internal user details in internal user table");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(9));
		ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(3).concat("'s"));
		ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(10));
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		ccui.getCommonMethods().selectInactiveRadiobutton();
		ccui.getAddNewInternalUsersPage().addUserRoles();
		ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 6, description = "Verify required error message is displayed for mandatory fields(First Name, Last Name, Username and Email)")
	public void testErrorMessageForMandatoryFields() throws Exception {
		logStep("PXPP-3484 Verify that the required error message appears on the screen when the user tries to save the mandatory field without entering data");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
		logStep("Verify required error message is displayed for mandatory fields");
		CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForFirstNameField(), "First name is required");
		CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForLastNameField(), "Last name is required");
		CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForEmailField(), "Email is required");
		CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForUsernameField(), "Username is required");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 7, description = "Enter more than 100 characters in firstname, last name,username and email field and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields1() throws Exception {
		logStep("PXPP-3491 Maximum characters reached\" error message for First Name, Last Name, Username and email fields when user enters more than 100 characters");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForFirstNameField(),
				"Maximum characters reached");
		ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForLastNameField(),
				"Maximum characters reached");
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForUsernameField(),
				"Maximum characters reached");
		ccui.getCommonMethods().enterEmail(CCUtils.generateAlphaNumericString(101).concat("@gmail.com"));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForEmailField(),
				"Maximum characters reached");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 8, dependsOnMethods = {
			"testCreateActiveInternalUserWithRoles" }, description = "Enter already existing username, email and mobile number on add new internal user page and verify error message.")
	public void testUniqueErrorMessage() throws Exception {
		logStep("PXPP-3508 Enter existing data in Username, Email and mobile number field and verify error message");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getCommonMethods().enterFirstName(firstName);
		ccui.getCommonMethods().enterLastName(lastName);
		ccui.getCommonMethods().enterEmail(email);
		ccui.getCommonMethods().enterUserName(userName);
		ccui.getCommonMethods().enterMobileNumber(String.valueOf(mobileNumber));
		ccui.getCommonMethods().focusOnInactiveRadioButton();
		CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForMobileNumberField(),
				"Mobile number already in use");
		CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForEmailField(), "Email already in use");
		CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForUsernameField(), "Username already in use");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 9, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
	public void testEnterValidDataErrorMessage(String[] getTestData) throws Exception {
		logStep("PXPP-2894 Enter invalid data in first name, last name, email,username and mobile number fields and verify error message");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getCommonMethods().enterFirstName(getTestData[0]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForFirstNameField(), "Please enter valid first name");
		ccui.getCommonMethods().enterLastName(getTestData[1]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForLastNameField(), "Please enter valid last name");
		ccui.getCommonMethods().enterEmail(getTestData[2]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForEmailField(), "Please enter valid email address");
		ccui.getCommonMethods().enterUserName(getTestData[3]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForUsernameField(), "Please enter valid username");
		ccui.getCommonMethods().enterMobileNumber(getTestData[4]);
		ccui.getCommonMethods().focusOnInactiveRadioButton();
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForMobileNumberField(),
				"Please enter valid mobile number");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 10, description = "Verify username is generated using beginning of email")
	public void testUsernameGeneratedFromEmail() throws Exception {
		logStep("PXPP-3496 Verify username is generated using beginning of email");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getCommonMethods().verifyUsernameGeneratedUsingBeginningOfEmail(CCUtils.generateNewEmail(20));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 11, description = "Verify functionality of cancel and cross icon")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		logStep("PXPP-3486 Verify functionality of Cancel button");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getAddNewInternalUsersPage().clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to internal users page");
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		logStep("Click on more options button");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Click on new button");
		ccui.getInternalUsersPage().clickOnNewButton();
		logStep("Click on cross button");
		ccui.getAddNewInternalUsersPage().clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to internal users page");
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 12, description = "Verify search options internal user popup")
	public void testSearchOptionsInternalUserPopup() throws Exception {
		logStep("PXPP-2676 After clicking the Search Options button, verify that the search options popup is displayed");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifySearchInternalUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 13,description = "Verify required error message is displayed when all fields of search options are blank")
	public void testErrorMessageWhenAllFieldsOfSearchOptionsAreBlank() throws Exception {
		logStep("Verify required error message is displayed for mandatory fields");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 14, description = "Enter more than 100 characters in firstname, lastname, username, email fields and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("PXPP-2687 Verify maximum characters reached error message for firstname, lastname, username, email fields");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 15, dataProvider = "invalidTestData", description = "Enter invalid data in firstname, lastname, email, username and mobile number fields and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		logStep("Enter invalid data in firstname, lastname, email, username and mobile number fields and verify error message");
		logStep("Click on search button");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1],
				getTestData[2], getTestData[3], getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 16, description = "Verify functionality of clear button on search options popup")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {
		logStep("PXPP-2686 CVerify user able to clear search text and selected filter options using CLEAR button");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		ccui.getSearchOptionsPage().verifyClearButtonIsDisplayed();
		ccui.getSearchOptionsPage().clickOnClearButton();
		logStep("Verify after clicking on cancel button data from all textbox fields gets cleared");
		ccui.getSearchOptionsPage().verifyTextboxIsEmptyWhenClickOnClearButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 17, description = "Verify functionality of Search Filter Cross Icon after getting the search result")
	public void testClearButtonFunctionalityWhenSearchResultDisplayed() throws Exception {
		logStep("PXPP-2686 Click on search button");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifyClearButtonFunctionalityWhenDataIsAvailableForAllFields(
				testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username").concat(CCUtils.generateAlphaNumericString(2)),
				testData.getProperty("valid.user.mobile.number"));
		logStep("Verify after clicking on search button user redirected to internal users page");
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getSearchOptionsPage().verifySearchFilterCrossIcon();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,priority = 18,description = "Verify functionality of search button when all the fields are filled & 'CLEAR All' button clicked after search result")
	public void testSearchOptionsWithAllTheFieldsAndMultipleCheckboxes() throws Exception {
		logStep("PXPP-5027 Verify functionality of search button when all the fields are filled & 'CLEAR All' button clicked after search result");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifyClearAllButtonFunctionalityWithAllFields(
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.client.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"), testData.getProperty("valid.user.mobile.number"));
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getSearchOptionsPage().selectEnrollmentStatus("Confirmed");
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		logStep("Verify clear all button is displayed");
		ccui.getInternalUsersPage().verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		ccui.getInternalUsersPage().clickOnClearAllButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 19, description = "Verify functionality of search options when only single status checkbox is selected")
	public void testSearchOptionsWithSingleCheckboxOfStatusIsSelected() throws Exception {
		logStep("PXPP-5016 Verify functionality of search options when only single status checkbox is selected");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		logStep("Verify clear all button is displayed");
		ccui.getInternalUsersPage().verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		ccui.getInternalUsersPage().clickOnClearAllButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 20, description = "Verify functionality of search button when all the fields are filled with single checkbox selected & 'CLEAR All' button clicked after search result")
	public void testSearchOptionsWithAllTheFieldsAndSingleCheckbox() throws Exception {
		logStep("PXPP-5016 Verify functionality of search button when all the fields are filled with single checkbox selected & 'CLEAR All' button clicked after search result");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().verifyClearAllButtonFunctionalityWithAllFields(
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.client.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"), testData.getProperty("valid.user.mobile.number"));
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getSearchOptionsPage().selectEnrollmentStatus("Confirmed");
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		logStep("Verify clear all button is displayed");
		ccui.getInternalUsersPage().verifyClearAllButtonIsDisplayed();
		logStep("Click on clear all button");
		ccui.getInternalUsersPage().clickOnClearAllButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 21, description = "Verify no results found message is displayed if searched text is not found")
	public void testNoResultsFoundMessageIfSearchedTextIsNotFound() throws Exception {
		logStep("PXPP-2674 Verify 'no results found' message is displayed if search text not found");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getInternalUsersPage().verifyErrorMessageWhenNonExistingDataIsEntered(
				testData.getProperty("non.existing.user.first.name").concat(CCUtils.generateAlphaNumericString(2)));
		logStep("Verify after clicking on search button user redirected to internal users page");
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().verifyNoResultsFoundErrorMessageIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 22, description = "Verify toaster message if more than 50 matches return for the searched text")
	public void testToasterMessageIfMoreThanFiftyMatchesReturnForSearchedText() throws Exception {
		logStep("PXPP-3555 Verify toaster message if more than 50 matches return for the searched text");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getInternalUsersPage().verifyErrorMessageWhenNonExistingDataIsEntered(
				testData.getProperty("existing.user.common.first.name"));
		logStep("Verify after clicking on search button user redirected to internal users page");
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().verifyToasterMessageIsDisplayedIfMoreThanFiftyMatchesReturn();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,  priority = 23,description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndPaginationInInternalUsersWebTable()
			throws InterruptedException, RuntimeException, AWTException {
		logStep("Now comparing the default Items per Page .Ideally it should be 10.");
		CCUtils.scrollPageToEnd();
		CCUtils.assertEquals(ccui.getInternalUsersPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getInternalUsersPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getInternalUsersPage().getTotalNoOfInternalUsers();
		logStep("Get the Total No of Clients : " + noOfClients);
		int getNoOfRowsPagination = Integer.parseInt(ccui.getInternalUsersPage().getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(ccui.getInternalUsersPage().isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getInternalUsersPage().isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getInternalUsersPage().isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(ccui.getInternalUsersPage().isLastPageButtonDisabled(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,  priority = 24,description = "Verify numbers of rows and Pagination should be change as per 'Items Per Page value")
	public void testAllPaginationButtonsClickable() throws InterruptedException, AWTException {
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		CCUtils.assertEquals(ccui.getInternalUsersPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getInternalUsersPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getInternalUsersPage().getTotalNoOfInternalUsers();
		logStep("Get the Total No of Clients : " + noOfClients);
		ccui.getInternalUsersPage().clickNextPageButton();
		ccui.getInternalUsersPage().clickNextPageButton();
		ccui.getInternalUsersPage().clickNextPageButton();
		ccui.getInternalUsersPage().clickPreviousPageButton();
		ccui.getInternalUsersPage().clickLastPageButton();
		ccui.getInternalUsersPage().clickFirstPageButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 25, description = "Check Next Button functionality ")
	public void testNextPageButtonFunctionality() throws InterruptedException, AWTException {
		logStep("Check Next Button functionality when Items Per Page value is = 20.");
		String defaultItemsPerPage = ccui.getInternalUsersPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getInternalUsersPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Next Button functionality when Items Per Page value is = 20.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getInternalUsersPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getInternalUsersPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Next Button functionality when Items Per Page value is = 50.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getInternalUsersPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getInternalUsersPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Next Button functionality when Items Per Page value is = 100.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getInternalUsersPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getInternalUsersPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Next Button functionality when Items Per Page value is = 200.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getInternalUsersPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getInternalUsersPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,  priority = 26,description = "Check Last Button functionality")
	public void testLastPageButtonFunctionality() throws InterruptedException, AWTException {
		String defaultItemsPerPage = ccui.getInternalUsersPage().getDefaultItemsPerPage();
		logStep("Check Last Button functionality when Items Per Page value is = 10.");
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getInternalUsersPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 20.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getInternalUsersPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + ccui.getInternalUsersPage().getDefaultPagination());
		ccui.getInternalUsersPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 50.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getInternalUsersPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getInternalUsersPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Last Button functionality when Items Per Page value is = 100.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getInternalUsersPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getInternalUsersPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Last Button functionality when Items Per Page value is = 200.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageTwohundred = ccui.getInternalUsersPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwohundred, "200");
		ccui.getInternalUsersPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwohundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,  priority = 27,description = "Check Previous Button functionality")
	public void testPreviousPageButtonFunctionality() throws InterruptedException, AWTException {
		String defaultItemsPerPage = ccui.getInternalUsersPage().getDefaultItemsPerPage();
		logStep("Check Previous Button functionality when Items Per Page value is = 10.");
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getInternalUsersPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 20.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getInternalUsersPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		ccui.getInternalUsersPage().checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 50.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getInternalUsersPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getInternalUsersPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Previous Button functionality when Items Per Page value is = 100.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageOnehundred = ccui.getInternalUsersPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageOnehundred, "100");
		logStep("Check Previous Button functionality when Items Per Page value is = 200.");
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		String itemsPerPageTwohundred = ccui.getInternalUsersPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwohundred, "200");
		ccui.getInternalUsersPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwohundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,  priority = 28,description = "Check First Button functionality ")
	public void testFirstPageButtonFunctionality() throws InterruptedException, AWTException {
		String defaultItemsPerPage = ccui.getInternalUsersPage().getDefaultItemsPerPage();
		logStep("Check First Button functionality when Items Per Page value is = 10.");
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getInternalUsersPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 20.");
		String itemsPerPageTwenty = ccui.getInternalUsersPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getInternalUsersPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 50.");
		String itemsPerPageFifty = ccui.getInternalUsersPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getInternalUsersPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 100.");
		String itemsPerPageOneHundred = ccui.getInternalUsersPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageOneHundred, "100");
		ccui.getInternalUsersPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageOneHundred));
		ccui.getInternalUsersPage().clickItemsPerPageDropdown();
		logStep("Check First Button functionality when Items Per Page value is = 200.");
		String itemsPerPageTwoHundred = ccui.getInternalUsersPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getInternalUsersPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test( enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 29, description = "test Sorting Functionality")
	public void testSortingFunctionality() throws Exception {

		logStep("Check firstName  sorting");
		ccui.getInternalUsersPage().clickAscfirstNameSortToDsc();
		ccui.getInternalUsersPage().clickDscfirstNameSortToAsc();
		logStep("Check flastName  sorting");
		ccui.getInternalUsersPage().clickAscLastNameSortToDsc();
		ccui.getInternalUsersPage().clickDscLastNameSortToAsc();
		logStep("Check email  sorting");
		ccui.getInternalUsersPage().clickAscEmailSortToDsc();
		ccui.getInternalUsersPage().clickDscEmailSortToAsc();
		logStep("Check enrollment status  sorting");
		ccui.getInternalUsersPage().clickAscEnrollmentStatusSortToDsc();
		ccui.getInternalUsersPage().clickDscEnrollmentStatusSortToAsc();
		logStep("Check UserStatus sorting");
		ccui.getInternalUsersPage().clickAscUserStatusSortToDsc();
		ccui.getInternalUsersPage().clickDscUserStatusSortToAsc();
	}

}
