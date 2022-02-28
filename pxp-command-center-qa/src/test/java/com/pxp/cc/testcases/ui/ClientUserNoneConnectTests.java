// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUserNoneConnectPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.util.CommonMethods;

public class ClientUserNoneConnectTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public ClientUserNoneConnectPage clientUserNoneConnectPage;
	public String clientName;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to Command Center portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Select client users tab in command center users page");
		clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		clientUsersPage.verifyClientUsersPageTitle();
		logStep("Select client user");
		clientUserDetailsPage = clientUsersPage.selectUser();
		logStep("Verify in Client User's profile connected resource field value is 'None-Connect'");
		clientUserDetailsPage.verifyConnectedResourceValueIsNoneConnect();
		clientName = clientUserDetailsPage.getClientName();
		clientUserNoneConnectPage = clientUserDetailsPage.clickNoneConnectButton();

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify connect existing resource popup elements in client users details page")
	public void testNoneConnectPopupElements() throws Exception {
		logStep("Verify all elements of 'None-Connect' popup after clicking 'None-Connect' button");
		clientUserNoneConnectPage = clientUserDetailsPage.clickNoneConnectButton();
		clientUserNoneConnectPage.verifyClientUserNoneConnectPopupTitle();
		clientUserNoneConnectPage.verifyClientUserNoneConnectInfoMessage(clientName);
		clientUserNoneConnectPage.verifySearchTextbox();
		clientUserNoneConnectPage.verifyCancelButtonPresentOnNoneConnectPopup();
		clientUserNoneConnectPage.verifyConnectButtonPresentOnNoneConnectPopup();
		clientUserNoneConnectPage.verifyCloseWindowIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search functionality in connect existing resource popup")
	public void testSearchFunctionality() throws Exception {
		logStep("Searching for staff/provider in search textbox in connect existing resource popup");
		String searchData = clientUserNoneConnectPage.searchProvider(testData.getProperty("search.provider"));
		logStep("Verify selected staff/provider from search list and clear button are displayed in search textbox");
		clientUserNoneConnectPage.verifySearchTextboxDataAfterSelectingProvider(searchData);
		clientUserNoneConnectPage.verifyClearButtonPresentInSearchTextbox();
		logStep("verify after selecting data from search text box mandatory override message with two radio buttons should appear");
		clientUserNoneConnectPage.verifyOverrideDemographicDetailsMessage();
		clientUserNoneConnectPage.verifyYesOverrideRadioButton();
		clientUserNoneConnectPage.verifyNoDoNotOverrideRadioButton();
		logStep("verify after selecting data from search text box connect button get enabled");
		clientUserNoneConnectPage.verifyConnectButtonIsEnabled();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify connecting a resource with overriding demographic details")
	public void testConnectingResourceWithOverrideDemographicDetails() throws Exception {
		clientUserNoneConnectPage.verifyClientUserNoneConnectPopupTitle();
		logStep("Searching for staff/provider in search textbox in connect existing resource popup");
		String searchData = clientUserNoneConnectPage.searchProvider(testData.getProperty("search.provider"));
		logStep("verify after selecting data from search text box mandatory override message with two radio buttons should appear");
		clientUserNoneConnectPage.verifyOverrideDemographicDetailsMessage();
		logStep("click to Yes, override radio option");
		clientUserNoneConnectPage.verifyYesOverrideRadioButton();
		logStep("verify after selecting data from search text box connect button get enabled and click to connect button");
		clientUserNoneConnectPage.verifyConnectButtonIsEnabled();
		clientUserNoneConnectPage.clickOnConnectButton();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("User has been successfully connected by provider");
		logStep("Verify user redirected to client user details page");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verifying connected resource first name, last name, email id is overriden in client uer's demographic deatils on client user details page");
		clientUserDetailsPage.verifyClientFirstNameOverridden(searchData);
		clientUserDetailsPage.verifyClientLastNameOverridden(searchData);
		clientUserDetailsPage.verifyClientEmailIdOverridden(searchData);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify connecting a resource without overriding demographic details")
	public void testConnectingResourceWithoutOverrideDemographicDetails() throws Exception {
		clientUserNoneConnectPage.verifyClientUserNoneConnectPopupTitle();
		logStep("Searching for staff/provider in search textbox in connect existing resource popup");
		String searchData = clientUserNoneConnectPage.searchProvider(testData.getProperty("search.provider"));
		logStep("verify after selecting data from search text box mandatory override message with two radio buttons should appear");
		clientUserNoneConnectPage.verifyOverrideDemographicDetailsMessage();
		logStep("click to No,do not override radio option");
		clientUserNoneConnectPage.verifyNoDoNotOverrideRadioButton();
		logStep("verify after selecting data from search text box connect button get enabled and click to connect button");
		clientUserNoneConnectPage.verifyConnectButtonIsEnabled();
		clientUserNoneConnectPage.clickOnConnectButton();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("User has been successfully connected by provider");
		logStep("Verify user redirected to client user details page");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verifying connected resource first name, last name, email id is not overriden in client uer's demographic deatils on client user details page");
		clientUserDetailsPage.verifyClientFirstNameNotOverridden(searchData);
		clientUserDetailsPage.verifyClientLastNameNotOverridden(searchData);
		clientUserDetailsPage.verifyClientEmailIdNotOverridden(searchData);
	}
	
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cross icon and cancel button functionality on connect existing resource popup")
	public void testCloseWindowAndCancelButtonFunctionality() throws Exception {
		logStep("Search/Select any provider from search textbox");
		clientUserNoneConnectPage.searchProvider(testData.getProperty("search.provider"));
		logStep("Click the cancel button on connect existing resource pop up");
		clientUserDetailsPage = clientUserNoneConnectPage.clickOnCancelButton();
		logStep("Verify user redirected to client user details page after clicking on cancel button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Click on none-connect on the client users details page.");
		clientUserNoneConnectPage = clientUserDetailsPage.clickNoneConnectButton();
		logStep("Click the cross icon on connect existing resource pop up");
		clientUserDetailsPage = clientUserNoneConnectPage.clickOnCloseWindow();
		logStep("Verify user redirected to client user details page after clicking on cross icon");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}
}
