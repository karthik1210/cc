// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewClientUserPage;
import com.pxp.cc.objectmaps.AddNewInternalUsersPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
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
	String firstName,firstname1;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
	}
	private String createActiveInternalUser() throws Exception {
		logStep("Click on new button");
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		internalUsersPage.clickOnKebabMenuButton();
		internalUsersPage.clickOnNewBtn();		
		logStep("Create a new active internal user with user roles and verify success toaster");
		commonMethods = new CommonMethods(driver);
		firstName =CCUtils.generateAlphaNumericString(12).concat("'s");
		commonMethods.enterFirstName(firstName);
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(12));
		commonMethods.enterEmail(CCUtils.generateNewEmail(12));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
		addNewInternalUsersPage.addUserRoles();
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
		return firstName;		
		}

	@Test(enabled = true, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Test to check status changed to deactivate for multiple user")
	public void testBulkUser() throws Exception {
		createActiveInternalUser();
		createActiveInternalUser();
		 firstname1=createActiveInternalUser();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		internalUsersPage.clickOnBulkDeactivate();
		deactivateUserPage = new DeactivateUserPage(driver);
		deactivateUserPage.clickOnSearchOption();
		deactivateUserPage.clickOnActiveCheckbox();
		deactivateUserPage.clickOnSearchButton();
		deactivateUserPage.verifyClearAllButtonIsDisplayed();
		deactivateUserPage.clickOncheckBox();
		deactivateUserPage.clickOndeactivateBtn();
		deactivateUserPage.clickOnApplyBtn();		
	}
}
