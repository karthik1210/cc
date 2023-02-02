// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewInternalUsersPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.DeactivateUserConfirmationPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class InternalUsersDetailsPageTests extends CommandCenterGenericTest {
	public CCUI ccui;
	String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
	String lastName = CCUtils.generateAlphaNumericString(12);
	String email = CCUtils.generateNewEmail(12);
	String userName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(12), firstNameValueInEditMode,
			lastNameValueInEditMode, emailValueInEditMode, userNameValueInEditMode, mobileNumberValueInEditMode;
	long mobileNumber = CCUtils.generateMobileNumber();
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public DeactivateUserConfirmationPage deactivateUserConfirmationPage;
	public List<String> userStatusList = null;
	public CommonMethods commonMethods;
	public AddNewInternalUsersPage addNewInternalUsersPage;
	String firstNameUser;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		ccui = new CCUI(testData, driver);
		logStep("Login to command center portal");
		ccui.getClientDirectoryPage();
		navigatetoInternalUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fields available for editing")
	public void testEditMode() throws Exception {
		createInternalUser();
		ccui.getInternalUserDetailsPage().clickEditInInternalUser();
		logStep("PXPP-10651 Verify edit button is displayed & fields available for editing");
		ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(18));
		ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(18));
		ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(11));
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericString(12));
		ccui.getCommonMethods().enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		logStep("Click on cancel button");
		ccui.getInternalUserDetailsPage().clickCancel();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify changes are displayed in right section after changes are saved")
	public void testEditInternalUser() throws Exception {
		createInternalUser();
		logStep("PXPP-10653 Verify changes are displayed in right section after changes are saved");
		ccui.getInternalUserDetailsPage().clickEditInInternalUser();
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		logStep("Modifying editable fields detail");
		firstNameValueInEditMode = CCUtils.generateAlphaNumericString(18);
		log("print the value of first name : " + firstNameValueInEditMode);
		ccui.getCommonMethods().enterFirstName(firstNameValueInEditMode);
		lastNameValueInEditMode = CCUtils.generateAlphaNumericString(18);
		log("print the value of last name : " + lastNameValueInEditMode);
		ccui.getCommonMethods().enterLastName(lastNameValueInEditMode);
		emailValueInEditMode = CCUtils.generateNewEmail(11);
		log("print the value of email : " + emailValueInEditMode);
		ccui.getCommonMethods().enterEmail(emailValueInEditMode);
		userNameValueInEditMode = CCUtils.generateAlphaNumericStringIncludingSpecialChar(12);
		log("print the value of username : " + userNameValueInEditMode);
		ccui.getCommonMethods().enterUserName(userNameValueInEditMode);
		mobileNumberValueInEditMode = String.valueOf(CCUtils.generateMobileNumber());
		log("print the value of mobile number : " + mobileNumberValueInEditMode);
		ccui.getCommonMethods().enterMobileNumber(mobileNumberValueInEditMode);
		logStep("Click on save button");
		ccui.getInternalUserDetailsPage().clickSave();
		ccui.getInternalUserDetailsPage().verifyEditButtonVisible();
		logStep("Verify new updated value are displayed in first name, last name, username, email and mobile number fields");
		CCUtils.assertEquals(ccui.getCommonMethods().getFirstName(), firstNameValueInEditMode.trim());
		CCUtils.assertEquals(ccui.getCommonMethods().getLastName(), lastNameValueInEditMode.trim());
		CCUtils.assertEquals(ccui.getCommonMethods().getUserName(), userNameValueInEditMode.trim());
		CCUtils.assertEquals(ccui.getCommonMethods().getEmailAddress(), emailValueInEditMode.trim());
		CCUtils.assertEquals(ccui.getCommonMethods().getMobileNumber().replaceAll("-", "").trim(),
				mobileNumberValueInEditMode.trim());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message for mandatory fields in edit mode")

	public void testRequiredErrorMessageInEditMode() throws Exception {
		createInternalUser();
		ccui.getInternalUserDetailsPage().clickEditInInternalUser();
		logStep("PXPP-10664 Verify required error message for mandatory fields in edit mode");
		ccui.getCommonMethods().clearFirstNameField();
		CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForFirstNameField(), "First name is required");
		ccui.getCommonMethods().clearLastNameField();
		CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForLastNameField(), "Last name is required");
		ccui.getCommonMethods().clearEmailField();
		CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForEmailField(), "Email is required");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify maximum characters error message for editable fields")
	public void testMaximumCharacterErrorInEditMode() throws Exception {
		createInternalUser();
		ccui.getInternalUserDetailsPage().clickEditInInternalUser();
		logStep("PXPP-10655 Enter more than 100 characters in first name field and verify maximum characters error message");
		ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForFirstNameField(),
				"Maximum characters reached");
		logStep("PXPP-10656 Enter more than 100 characters in last name field and verify maximum characters error message");
		ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForLastNameField(),
				"Maximum characters reached");
		logStep("PXPP-10657 Enter more than 100 characters in username field and verify maximum characters error message");
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForUsernameField(),
				"Maximum characters reached");
		logStep("PXPP-10658 Enter more than 100 characters in email field and verify maximum characters error message");
		ccui.getCommonMethods().enterEmail(CCUtils.generateAlphaNumericString(101).concat("@gmail.com"));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForEmailField(),
				"Maximum characters reached");
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in editable fields and verify valid error message for editable fields")
	public void testEnterValidDetailErrorInEditMode(String[] getTestData) throws Exception {
		createInternalUser();
		ccui.getInternalUserDetailsPage().clickEditInInternalUser();
		logStep("PXPP-10661 Enter invalid data in first name, last name, email,username and mobile number fields and verify error message");
		ccui.getCommonMethods().enterFirstName(getTestData[0]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForFirstNameField(), "Please enter valid first name");
		ccui.getCommonMethods().enterLastName(getTestData[1]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForLastNameField(), "Please enter valid last name");
		ccui.getCommonMethods().enterEmail(getTestData[2]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForEmailField(), "Please enter valid email address");
		ccui.getCommonMethods().enterMobileNumber(getTestData[4]);
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForMobileNumberField(),
				"Please enter valid mobile number");
		ccui.getCommonMethods().focusOnUserNameField();
		ccui.getCommonMethods().enterUserName(getTestData[3]);
		CCUtils.assertEquals(ccui.commonMethods.getValidErrorForUsernameField(), "Please enter valid username");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing data in username, mobile number and email fields and verify error message")
	public void testUniqueErrorMssageInEditMode() throws Exception {
		logStep("PXPP-10664 Enter already existing data in username, mobile number and email fields and verify error message");
		ccui.getInternalUsersPage().clickToMoreOptions();
		logStep("Create new internal user");
		String firstname = CCUtils.generateAlphaNumericString(5);
		String lastname = CCUtils.generateAlphaNumericString(5);
		String emailOne = testData.getProperty("valid.user.first.name").concat(CCUtils.generateNewEmail(12));
		String username = testData.getProperty("new.client.name2").concat(CCUtils.generateAlphaNumericString(12));
		long mobilenumber = CCUtils.generateMobileNumber();
		ccui.getInternalUsersPage().clickOnNewButton();
		ccui.getCommonMethods().enterFirstName(firstname);
		ccui.getCommonMethods().enterLastName(lastname);
		ccui.getCommonMethods().enterEmail(emailOne);
		ccui.getCommonMethods().enterUserName(username);
		ccui.getCommonMethods().enterMobileNumber(String.valueOf(mobilenumber));
		ccui.getAddNewInternalUsersPage().addUserRoles();
		ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
		logStep("Search another internal user");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("No roles assigned"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().selectUser();
		logStep("Edit searched internal user And Enter already existing data in username, mobile number and email fields and verify error message");
		ccui.getInternalUserDetailsPage().clickEditInInternalUser();
		ccui.getCommonMethods().enterFirstName(firstname);
		ccui.getCommonMethods().enterLastName(lastname);
		ccui.getCommonMethods().enterEmail(emailOne);
		CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForEmailField(), "Email already in use");
		ccui.getCommonMethods().enterUserName(username);
		CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForUsernameField(), "Username already in use");

		ccui.getCommonMethods().enterMobileNumber(String.valueOf(mobilenumber));
		CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForMobileNumberField(),
				"Mobile number already in use");

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid data in username field and verify error and tooltip text for username field")
	public void testTooltipForUsernameField() throws Exception {
		createInternalUser();
		ccui.getInternalUserDetailsPage().clickEditInInternalUser();
		logStep("PXPP-10661 Enter invalid data in username field and verify error and tooltip text for username field");
		ccui.getCommonMethods().enterUserName(testData.getProperty("invalid.username.one"));
		CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForUsernameField(), "Please enter valid username");
		logStep("Hover over the error icon for the username field and verify the tooltip text for the username field");
		CCUtils.assertEquals(ccui.getCommonMethods().getTooltipTextForUsernameField(),
				"Only alphanumeric and special characters .,!@#$%&*_- allowed");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user confirmation popup")
	public void testDeactivateConfirmationPopupForInternalUser() throws Exception {
		logStep("PXPP-7506 Verify selected internal user status is same on internal user details page");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().selectUser();
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		ccui.getInternalUserDetailsPage().clickOnDeactivateUserButton();
		ccui.getDeactivateUserConfirmationPage().verifyDeactivatePopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for active user")
	public void testDeactivateUserOptionIsPresentForActiveInternalUser() throws Exception {
		createInternalUser();
		logStep("PXPP-7509 Verify selected internal user status is same on internal user details page");
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("Verify deactivate button is displayed in kebab menu for active user");
		ccui.getInternalUserDetailsPage().verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for no roles assigned user")
	public void testDeactivateUserOptionIsPresentForNoRolesAssignedInternalUser() throws Exception {
		logStep("PXPP-7502 Create a new active internal user with user roles and verify success toaster");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("No roles assigned"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().selectUser();
		logStep("Verify selected internal user status is same on internal user details page");
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("Verify deactivate user button is displayed in kebab menu for no roles assigned user");
		ccui.getInternalUserDetailsPage().verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify activate user button is displayed in kebab menu for inactive user")
	public void testDeactivateUserOptionIsNotPresentForInactiveInternalUser() throws Exception {
		logStep("PXPP-7501 Verify activate user button is displayed in kebab menu for inactive user");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("Inactive"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().selectUser();
		logStep("Verify selected internal user status is same on internal user details page");
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("Verify deactivate user button is not displayed in kebab menu for inactive user");
		ccui.getInternalUserDetailsPage().verifyActivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed but not enabled in kebab menu for locked user")
	public void testDeactivateUserOptionIsNotPresentForLockedInternalUser() throws Exception {
		createInternalUser();
		logStep("PXPP-7500 Verify selected internal user status is same on internal user details page");
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for active user");
		ccui.getInternalUserDetailsPage().verifyLockUserButtonIsDisplayed();
		ccui.getInternalUserDetailsPage().clickOnLockUserButton();
		logStep("verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Success! User status has been successfully locked");
		logStep("Verify user status after clicking on lock user button");
		ccui.getInternalUserDetailsPage().verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Verify lock button is not displayed in kebab menu for locked user");
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("Verify deactivate user button is displayed but not enabled in kebab menu for locked user");
		ccui.getInternalUserDetailsPage().verifyDeactivateUsertButtonIsNotEnabled();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel and cross button functionality for deactivate user button popup")
	public void testCancelAndCrossIconFunctionalityOnDeactivateInternalUserButton() throws Exception {
		logStep("PXPP-7507 Verify selected internal user status is same on internal user details page");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().selectUser();
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		ccui.getInternalUserDetailsPage().clickOnDeactivateUserButton();
		logStep("Verify after clicking on cancel button, user redirected to internal user details page");
		ccui.getDeactivateUserConfirmationPage().clickOnCancelButton();
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		ccui.getInternalUserDetailsPage().clickOnDeactivateUserButton();
		logStep("Verify after clicking on cross icon, user redirected to internal user details page");
		ccui.getDeactivateUserConfirmationPage().clickOnCloseWindow();
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user functionality")
	public void testInternalUserDeactivateFunctionality() throws Exception {
		createInternalUser();
		logStep("PXPP-7579 Verify deactivate user functionality");
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		ccui.getInternalUserDetailsPage().clickOnDeactivateUserButton();
		ccui.getDeactivateUserConfirmationPage().clickOnYesDeactivateButton();
		logStep("verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Success! User status has been successfully deactivated");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock active internal user and Verify lock button is not displayed in kebab menu for locked user")
	public void testInternalUserLockButtonIsPresentForActiveUserAndNotPresentForLockedUser() throws Exception {
		logStep("PXPP-7507 Verify selected internal user status is same on internal user details page");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("Active"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().selectUser();
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("PXPP-5079 Verify lock button is displayed in kebab menu for active user");
		ccui.getInternalUserDetailsPage().verifyLockUserButtonIsDisplayed();
		ccui.getInternalUserDetailsPage().clickOnLockUserButton();
		logStep("verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Success! User status has been successfully locked");
		logStep("Verify user status after clicking on lock user button");
		ccui.getInternalUserDetailsPage().verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Verify lock button is not displayed in kebab menu for locked user");
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		ccui.getInternalUserDetailsPage().verifyLockUsertButtonIsNotDisplayed();
		logStep("Click on unlock user button in kebab menu");
		ccui.getInternalUserDetailsPage().clickOnUnlockUserButton();
		logStep("verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Success! User status has been successfully activated");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock for no roles assigned user")
	public void testInternalUserLockButtonIsPresentForNoRolesAssignedUser() throws Exception {
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getSearchOptionsPage().selectUserStatus(Arrays.asList("No roles assigned"));
		ccui.getSearchOptionsPage().clickOnSearchButton();
		ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
		ccui.getInternalUsersPage().selectUser();
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		logStep("PXPP-5072 Verify selected internal user status is same on internal user details page");
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for no roles assigned user");
		ccui.getInternalUserDetailsPage().verifyLockUserButtonIsDisplayed();
		logStep("Click on lock user button");
		ccui.getInternalUserDetailsPage().clickOnLockUserButton();
		logStep("verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Success! User status has been successfully locked");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for inactive user")
	public void testInternalUserLockButtonIsNotPresentForInactiveUser() throws Exception {
		logStep("PXPP-5077 Verify lock button is not displayed in kebab menu for inactive user");
		ccui.getInternalUsersPage().clickOnKebabMenuButton();
		ccui.getInternalUsersPage().clickOnNewBtn();
		logStep("Create New Inactive Internal User");
		String firstName = "InactiveUser" + CCUtils.generateAlphaNumericString(3).concat("'s");
		ccui.getCommonMethods().enterFirstName(firstName);
		String lastName = CCUtils.generateAlphaNumericString(9);
		ccui.getCommonMethods().enterLastName(lastName);
		ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(10));
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(9));
		ccui.getCommonMethods().selectInactiveRadiobutton();
		ccui.getAddNewInternalUsersPage().selectUserRole("Super Admin");
		ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
		searchOptionsPage = ccui.getInternalUsersPage().clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		userStatusList = new ArrayList<String>();
		ccui.getSearchOptionsPage().enterSearchTextInSearchFields(firstName, lastName, "", "", "");
		userStatusList.add(testData.getProperty("user.status.inactive"));
		ccui.getSearchOptionsPage().selectUserStatus(userStatusList);
		ccui.getSearchOptionsPage().clickOnSearchButton();
		String userStatus = ccui.getInternalUsersPage().getInternalUserStatus();
		ccui.getInternalUsersPage().selectUser();
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		logStep("Verify selected internal user status is same on internal user details page");
		ccui.getInternalUserDetailsPage().verifySelectedUserStatusIsSameOnInternalUserDetailsPage(userStatus);
		ccui.getInternalUserDetailsPage().clickOnKebabMenuButton();
		logStep("Verify lock button is not displayed in kebab menu for inactive user");
		ccui.getInternalUserDetailsPage().verifyLockUsertButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify User Roles Section, Edit, Save And Cancel Button Functionality ")
	public void testUserRolesSectionEditSaveAndCancelButtonFunctionality() throws Exception {
		createInternalUser();
		logStep("PXPP-4735 Test to verify when user redirected to Internal User Details Page then 'User Roles' displayed in user role area.");
		ccui.getInternalUserDetailsPage().verifyInternalUserDetailsPageTitle();
		ccui.getInternalUserDetailsPage().clickUserRolesEditbutton();
		ccui.getInternalUserDetailsPage().verifyUserRoleSaveButton();
		ccui.getInternalUserDetailsPage().verifyUserRoleCancelButton();
		ccui.getInternalUserDetailsPage().clickOnCancel();
		logStep("PXPP-4736 Test to verify edit & cancel button functionality ");
		ccui.getInternalUserDetailsPage().clickUserRolesEditbutton();
		ccui.getInternalUserDetailsPage().clickToRemoveUserRoles();
		ccui.getInternalUserDetailsPage().clickToAddUserRoles();
		ccui.getInternalUserDetailsPage().clickOnCancel();
		logStep("PXPP-4737 Test to verify admin user have access to 'delete' & 'save' the roles by clicking on edit button in the user role area using red trash i-con.");
		ccui.getInternalUserDetailsPage().clickUserRolesEditbutton();
		ccui.getInternalUserDetailsPage().clickToRemoveUserRoles();
		ccui.getInternalUserDetailsPage().clickOnSaveButton();
		ccui.getInternalUserDetailsPage().verifyEditToasterMessage();
		logStep("PXPP-4738 Test to verify admin user have access to 'add' & save the added roles by clicking on edit button in the user role area using  plus sign.");
		ccui.getInternalUserDetailsPage().clickUserRolesEditbutton();
		ccui.getInternalUserDetailsPage().clickToAddUserRoles();
		ccui.getInternalUserDetailsPage().verifyUserRoleSaveButton();
		ccui.getInternalUserDetailsPage().clickOnSaveButton();
		ccui.getInternalUserDetailsPage().verifyEditToasterMessage();
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

	@Test(enabled = false)
	public void createInternalUser() throws Exception {
		logStep("Create a new active internal user with user roles and verify success toaster");
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
		logStep("Search and select active user");
		ccui.getInternalUsersPage().clickOnSearchOptionPage();
		ccui.getInternalUsersPage().searchUser(firstName);
		ccui.getSearchOptionsPage().clickOnSearchButton();
		logStep("Clicked on user to edit");
		ccui.getInternalUsersPage().selectUser();
	}
}
