// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.MoveAgnosticPracticePage;

public class MovePracticeAgnosticNoInheritTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public MoveAgnosticPracticePage moveAgnosticPracticePage;
	public AddOrgNodePage addOrgNodePage;

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
	public void testMoveActivePracticeToActiveClientWithDoNotInheritWithoutSolutions() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = testData.getProperty("new.practice.name");
		addOrgNodePage.createNewOrgNode(testData.getProperty("practice.client.name"),
				testData.getProperty("practice.sapid"), testData.getProperty("practice.timezone"),
				testData.getProperty("practice.solutiondisplayname"));
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Search practice node");
		generalSettingsPage.searchNode(practiceName);
		logStep("Move active practice node to active client with do not inherit");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		logStep("Click on practice node");
		moveAgnosticPracticePage.clickOnPracticeNode();
		logStep("Click on move practice button");
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		logStep("Enter client name in client search field");
		moveAgnosticPracticePage.clickOnSearchClientfield(testData.getProperty("practice.client.name"));
		moveAgnosticPracticePage.searchAndSelectClientNameInClientfield(testData.getProperty("practice.client.name"));
		logStep("Click on confirm button");
		moveAgnosticPracticePage.clickOnMoveConfirmButton();
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
		logStep("Enter client name in client search field");
		moveAgnosticPracticePage.clickOnSearchClientfield(testData.getProperty("practice.client.name"));
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
		generalSettingsPage.verifyErrorMessageInactiveTargetClient();
		generalSettingsPage.closeToasterMessage();
		logStep("Move inactive practice node to inactive client with inhetit and verify error message");
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(testData.getProperty("source.inactive.practice.node.name"));
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage
				.movePracticeNodeToClientWithInherit(testData.getProperty("inactive.target.client.name"));
		generalSettingsPage.verifyErrorMessageInactiveTargetClient();
		generalSettingsPage.closeToasterMessage();
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
	public void testMoveInactivePracticeToActiveClientWithDoNotInheritWithSolutions() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		logStep("Add inactive practice node below client");
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = testData.getProperty("inactive.practice.name");
		addOrgNodePage.createNewOrgNode(practiceName, testData.getProperty("inactive.practice.sapid"),
				testData.getProperty("inactive.practice.timezone"),
				testData.getProperty("inactive.practice.solutiondisplayname"));
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.addSolutions();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Move inactive prctice node to active client with do not inherit");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage
				.movePracticeNodeToClientWithDoNotInherit(testData.getProperty("active.target.client.name"));
		generalSettingsPage.verifySuccessToasterMessage(practiceName,
				testData.getProperty("active.target.client.name"));
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
		logStep("Add inactive practice node below client");
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = testData.getProperty("inactive.practice.name");
		addOrgNodePage.createNewOrgNode(practiceName, testData.getProperty("inactive.practice.sapid"),
				testData.getProperty("inactive.practice.timezone"),
				testData.getProperty("inactive.practice.solutiondisplayname"));
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Move inactive practice node to active client with inherit");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		moveAgnosticPracticePage.movePracticeNodeToClientWithInherit(testData.getProperty("active.target.client.name"));
		generalSettingsPage.verifySuccessToasterMessage(practiceName,
				testData.getProperty("active.target.client.name"));
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
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = testData.getProperty("practice.client.name");
		addOrgNodePage.createNewOrgNode(testData.getProperty("practice.client.name"),
				testData.getProperty("practice.sapid"), testData.getProperty("practice.timezone"),
				testData.getProperty("practice.solutiondisplayname"));
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Search practice node");
		generalSettingsPage.searchNode(practiceName);
		logStep("Move active practice node to active client with inherit");
		moveAgnosticPracticePage = new MoveAgnosticPracticePage(driver);
		logStep("Click on move practice button");
		moveAgnosticPracticePage.clickMoveButtonOfPractice();
		moveAgnosticPracticePage.clickOnNewClientOption();
		logStep("Enter client name in client search field");
		moveAgnosticPracticePage.movePracticeNodeToClientWithInherit(testData.getProperty("active.target.client.name"));
		generalSettingsPage.verifySuccessToasterMessage(practiceName,
				testData.getProperty("active.target.client.name"));
		String clientSapId = generalSettingsPage.getClientSapId();
		String clientTimezone = generalSettingsPage.getClientTimezone();
		String clientType = generalSettingsPage.getClientType();
		boolean clientStatus = generalSettingsPage.returnClientNodeStatusValue();
		logStep("verify practice node inherits sap id, timezone, client type and status from target client node");
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, clientSapId, clientTimezone, clientType,
				clientStatus);
	}

}
