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

public class MoveAgnosticPracticeTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public MoveAgnosticPracticePage moveAgnosticPracticePage;
	public AddOrgNodePage addOrgNodePage;
	public CommonMethods commonMethods;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify move practice popup")
	public void testMovePracticePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search practice node");
		generalSettingsPage.searchNode(testData.getProperty("source.practice.node.name"));
		logStep("Click on move practice button");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		logStep("Verify move practice popup");
		moveAgnosticPracticePage.verifyMovePracticePopup(testData.getProperty("source.practice.node.name"));

	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active practice node to active client with do not inherit")
	public void testMoveActivePracticeToActiveClientWithDoNotInherit() throws Exception {
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
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage.movePracticeNodeToClientWithDoNotInherit(testData.getProperty("practice.client.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("SUCCESS " + practiceName + " has been successfully moved to client "
				+ testData.getProperty("practice.client.name"));
		commonMethods.clickToasterlink();
		generalSettingsPage.verifyClientNameValue(testData.getProperty("practice.client.name"));
		String clientType = generalSettingsPage.getClientType();
		logStep("Verify that when not inherited it does not takes the parent settings while transition.");
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, testData.getProperty("practice.sapid"),
				testData.getProperty("practice.timezone"), clientType, true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client name in client search textbox and verify error message")
	public void testErrorMessageForNonExistingClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search practice node");
		generalSettingsPage.searchNode(testData.getProperty("source.practice.node.name"));
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		logStep("Click on move practice button");
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		logStep("Enter non existing client name in client search textbox and verify error message");
		moveAgnosticPracticePage.verifyNoClientsFoundErrorMessage("abcdef@0101");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active/inactive practice node to inactive target client and verify error message")
	public void testErrorMessageForInactiveTargetClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search practice node");
		generalSettingsPage.searchNode(testData.getProperty("source.practice.node.name"));
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		logStep("Click on move practice button");
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		logStep("Move active practice node to inactive client with do not inhetit and verify error message");
		moveAgnosticPracticePage
				.movePracticeNodeToClientWithDoNotInherit(testData.getProperty("inactive.target.client.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Client selected is inactive. Activate client and try again.");
		commonMethods.closeToasterMessage();
		logStep("Move inactive practice node to inactive client with inhetit and verify error message");
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(testData.getProperty("source.inactive.practice.node.name"));
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage
				.movePracticeNodeToClientWithInherit(testData.getProperty("inactive.target.client.name"));
		commonMethods.verifyToasterMessage("Client selected is inactive. Activate client and try again.");
		commonMethods.closeToasterMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel & cross button in move practice popup")
	public void testCancelButtonAndCrossIconAtMovePractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search practice node");
		generalSettingsPage.searchNode(testData.getProperty("source.practice.node.name"));
		logStep("Click on move practice button");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		logStep("Click on cancel button and verify user redirected to general settings page");
		moveAgnosticPracticePage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on move practice button");
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		logStep("Click on cross icon and verify user redirected to general settings page");
		moveAgnosticPracticePage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move inactive practice node to active client with do not inherit")
	public void testMoveInactivePracticeToActiveClientWithDoNotInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Add inactive agnostic practice node below client");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		String practiceName = testData.getProperty("inactive.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(parentName, false, practiceName, testData.getProperty("inactive.practice.sapid"),
				testData.getProperty("inactive.practice.timezone"),
				testData.getProperty("inactive.practice.solutiondisplayname"));
		logStep("Move inactive practice node to active client with do not inherit");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage
				.movePracticeNodeToClientWithDoNotInherit(testData.getProperty("active.target.client.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("SUCCESS " + practiceName + " has been successfully moved to client "
				+ testData.getProperty("active.target.client.name"));
		commonMethods.clickToasterlink();
		generalSettingsPage.verifyClientNameValue(testData.getProperty("active.target.client.name"));
		logStep("Verify that sap id, timezone and status attribute values ​​are not taken from the target client node during the transition");
		String clientType = generalSettingsPage.getClientType();
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, testData.getProperty("inactive.practice.sapid"),
				testData.getProperty("inactive.practice.timezone"), clientType, false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move inactive practice node to active client with inherit and verify practice node inherits sap id, timezone, client type and status from target client node")
	public void testMoveInactivePracticeToActiveClientWithInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		logStep("Add new inactive agnostic practice node below client node");
		addOrgNodePage = new AddOrgNodePage(driver);
		String practiceName = testData.getProperty("inactive.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(parentName, false, practiceName, testData.getProperty("inactive.practice.sapid"),
				testData.getProperty("inactive.practice.timezone"),
				testData.getProperty("inactive.practice.solutiondisplayname"));
		logStep("Move inactive practice node to active client with inherit");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage.movePracticeNodeToClientWithInherit(testData.getProperty("active.target.client.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("SUCCESS " + practiceName + " has been successfully moved to client "
				+ testData.getProperty("active.target.client.name"));
		commonMethods.clickToasterlink();
		generalSettingsPage.verifyClientNameValue(testData.getProperty("active.target.client.name"));
		String clientSapId = generalSettingsPage.getClientSapId();
		String clientTimezone = generalSettingsPage.getClientTimezone();
		String clientType = generalSettingsPage.getClientType();
		boolean clientStatus = generalSettingsPage.returnClientNodeStatusValue();
		logStep("verify practice node inherits sap id, timezone, client type and status from target client node");
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, clientSapId, clientTimezone, clientType,
				clientStatus);
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active practice node to active client with inherit")
	public void testMoveActivePracticeToActiveClientWithInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new active agnostic practice node below client node");
		String practiceName = testData.getProperty("new.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(parentName, true, practiceName, testData.getProperty("practice.sapid"),
				testData.getProperty("practice.timezone"), testData.getProperty("practice.solutiondisplayname"));
		logStep("Move active practice node to active client with inherit");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage.movePracticeNodeToClientWithInherit(testData.getProperty("active.target.client.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("SUCCESS " + practiceName + " has been successfully moved to client "
				+ testData.getProperty("active.target.client.name"));
		commonMethods.clickToasterlink();
		generalSettingsPage.verifyClientNameValue(testData.getProperty("active.target.client.name"));
		String clientSapId = generalSettingsPage.getClientSapId();
		String clientTimezone = generalSettingsPage.getClientTimezone();
		String clientType = generalSettingsPage.getClientType();
		boolean clientStatus = generalSettingsPage.returnClientNodeStatusValue();
		logStep("verify practice node inherits sap id, timezone, client type and status from target client node");
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, clientSapId, clientTimezone, clientType,
				clientStatus);
	}
}