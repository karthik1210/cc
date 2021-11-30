// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.MoveAgnosticPracticePage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class MoveNgePracticeTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public MoveAgnosticPracticePage movePracticeNGEPage;
	public AddOrgNodePage addOrgNodePage;
	public CommonMethods commonMethods;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify move practice popup under NGE client")
	public void testMoveNGEPracticePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search practice node");
		generalSettingsPage.searchNode(testData.getProperty("source.practice.node.name"));
		logStep("Click on move practice button");
		movePracticeNGEPage = new MoveAgnosticPracticePage(driver);
		movePracticeNGEPage.clickMoveButtonOfPractice();
		movePracticeNGEPage.clickOnNewEnterpriseOption();
		logStep("Verify move practice popup");
		movePracticeNGEPage.verifyMovePracticePopupForEnterprise(testData.getProperty("source.practice.node.name"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active practice node to active enterprise with do not inherit for NGE client")
	public void testMoveActivePracticeToActiveEnterpriseWithDoNotInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Add new active agnostic practice node below client node");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = testData.getProperty("new.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.createNewOrgNode(practiceName, testData.getProperty("practice.sapid"),
				testData.getProperty("practice.timezone"), testData.getProperty("practice.solutiondisplayname"));
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Move active practice node to active client with do not inherit");
		movePracticeNGEPage = new MoveAgnosticPracticePage(driver);
		movePracticeNGEPage.clickMoveButtonOfPractice();
		movePracticeNGEPage.clickOnNewEnterpriseOption();
		movePracticeNGEPage.movePracticeNodeToEnterpriseWithDoNotInherit(testData.getProperty("search.nge.client"),
				testData.getProperty("search.nge.enterprsie"));
		commonMethods = new CommonMethods(driver);
		commonMethods.clickToasterlink();
		String clientType = generalSettingsPage.getClientType();
		logStep("Verify that when not inherited it does not takes the parent settings while transition.");
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, testData.getProperty("practice.sapid"),
				testData.getProperty("practice.timezone"), clientType, true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "verify error messages for non existing client name in client  & enterprise search textbox")
	public void testErrorMessageForNonExistingClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search practice node");
		generalSettingsPage.searchNode(testData.getProperty("source.practice.node.name"));
		movePracticeNGEPage = new MoveAgnosticPracticePage(driver);
		logStep("Click on move practice button");
		movePracticeNGEPage.clickMoveButtonOfPractice();
		movePracticeNGEPage.clickOnNewEnterpriseOption();
		logStep("Enter non existing client name in client search textbox and verify error message");
		movePracticeNGEPage.verifyNoClientsFoundErrorMessage("abcdef@0101");
		movePracticeNGEPage.verifyNoEnterpriseFoundErrorMessage("Koa9920*");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active practice to inactive NGE client and verify error message")
	public void testErrorMessageForInactiveTargetClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search practice node");
		generalSettingsPage.searchNode(testData.getProperty("source.practice.node.name"));
		movePracticeNGEPage = new MoveAgnosticPracticePage(driver);
		logStep("Click on move practice button");
		movePracticeNGEPage.clickMoveButtonOfPractice();
		movePracticeNGEPage.clickOnNewEnterpriseOption();
		logStep("Move active practice node to inactive client with do not inhetit and verify error message");
		movePracticeNGEPage.movePracticeNodeToEnterpriseWithDoNotInherit(
				testData.getProperty("inactive.target.nge.client.name"),
				testData.getProperty("search.inactive.nge.enterprsie"));
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Business unit cannot be moved to an inactive Target");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "test for moving active practice to active Enterprise with inherit")
	public void testMoveActivePracticeToActiveClientWithInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.nge.client"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new active practice in Nge client");
		String practiceName = testData.getProperty("new.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(testData.getProperty("search.nge.enterprsie"), true, practiceName,
				testData.getProperty("practice.sapid"), testData.getProperty("practice.timezone"),
				testData.getProperty("practice.solutiondisplayname"));
		logStep("Move active practice node to active enterprise with inherit");
		movePracticeNGEPage = new MoveAgnosticPracticePage(driver);
		movePracticeNGEPage.clickMoveButtonOfPractice();
		movePracticeNGEPage.clickOnNewEnterpriseOption();
		movePracticeNGEPage.movePracticeNodeToEnterpriseWithInherit(
				testData.getProperty("move.enterprise.target.client.name.active"),
				testData.getProperty("search.nge.enterprsie"));
		commonMethods = new CommonMethods(driver);
		commonMethods.clickToasterlink();
		generalSettingsPage.searchNode(testData.getProperty("search.nge.enterprsie"));
		String targetEnterpriseSapId = generalSettingsPage.getOrgSapId();
		String targetEnterpriseTimezone = generalSettingsPage.getOrgTimezone();
		String targetEnterpriseClientType = generalSettingsPage.getOrgClientType();
		boolean targetEnterpriseStatus = generalSettingsPage.returnOrgStatus();
		logStep("verify practice node inherits sap id, timezone, client type and status from target client node");
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, targetEnterpriseSapId, targetEnterpriseTimezone,
				targetEnterpriseClientType, targetEnterpriseStatus);
	}
}
