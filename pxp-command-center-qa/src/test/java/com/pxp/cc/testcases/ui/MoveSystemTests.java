// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.MoveSystemPage;
import com.pxp.cc.util.CCUtils;

public class MoveSystemTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public AddOrgNodePage addOrgNodePage;
	public GeneralSettingsPage generalSettingsPage;
	public MoveSystemPage moveSystemPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify move system popup")
	public void testMoveSystemPopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search system node");
		generalSettingsPage.searchNode(testData.getProperty("source.system.node.name"));
		logStep("Click on move system button");
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		logStep("Verify move system popup");
		moveSystemPage.verifyMoveSystemPopup(testData.getProperty("source.system.node.name"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client name in client search textbox and verify error message")
	public void testErrorMessageForNonExistingClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search system node");
		generalSettingsPage.searchNode(testData.getProperty("source.system.node.name"));
		logStep("Click on move system button");
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		logStep("Enter non existing client name in client search textbox and verify error message");
		moveSystemPage.verifyNoClientsFoundErrorMessage("abcdef@0101");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active/inactive system node to inactive target client and verify error message")
	public void testErrorMessageForInactiveTargetClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Move active system node to inactive client with do not inhetit and verify error message");
		generalSettingsPage.searchNode(testData.getProperty("source.system.node.name"));
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		moveSystemPage.moveSystemNodeToClientWithDoNotInherit(testData.getProperty("inactive.target.client.name"));
		generalSettingsPage.verifyErrorMessageInactiveTargetClient();
		generalSettingsPage.closeToasterMessage();
		logStep("Move inactive system node to inactive client with do not inhetit and verify error message");
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(testData.getProperty("source.inactive.system.node.name"));
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage.moveSystemNodeToClientWithInherit(testData.getProperty("inactive.target.client.name"));
		generalSettingsPage.verifyErrorMessageInactiveTargetClient();
		generalSettingsPage.closeToasterMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel & cross button in move system popup")
	public void testCancelButtonAndCrossIconAtMoveSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search system node");
		generalSettingsPage.searchNode(testData.getProperty("source.system.node.name"));
		logStep("Click on move system button");
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		logStep("Click on cancel button and verify user redirected to general settings page");
		moveSystemPage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on move system button");
		generalSettingsPage.clickOnMoveSystem();
		logStep("Click on cross icon and verify user redirected to general settings page");
		moveSystemPage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move inactive system node to active client with do not inherit")
	public void testMoveInactiveSystemToActiveClientWithDoNotInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Add inactive system node below client");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemName = testData.getProperty("new.system.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewSystemNode(systemName, testData.getProperty("new.system.sapid"),
				testData.getProperty("new.system.timezone"), testData.getProperty("new.system.solutiondisplayname"),
				false);
		logStep("Move inactive system node to active client with do not inherit");
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		moveSystemPage.moveSystemNodeToClientWithDoNotInherit(testData.getProperty("active.target.client.name"));
		generalSettingsPage.verifySuccessToasterMessage(systemName, testData.getProperty("active.target.client.name"));
		logStep("Verify that sap id, timezone and status attribute values ​​are not taken from the target client node during the transition");
		String clientType = generalSettingsPage.getClientType();
		generalSettingsPage.verifySystemNodeData(systemName, testData.getProperty("new.system.sapid"),
				testData.getProperty("new.system.timezone"), clientType, false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active system node to active client with do not inherit")
	public void testMoveActiveSystemToActiveClientWithDoNotInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Add active system node below client along with child nodes enterprise and practice");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemName = testData.getProperty("new.system.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewSystemNode(systemName, testData.getProperty("new.system.sapid"),
				testData.getProperty("new.system.timezone"), testData.getProperty("new.system.solutiondisplayname"),
				true);
		generalSettingsPage.clickOnAddEnterprise();
		String enterpriseName = testData.getProperty("new.enterprise.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(systemName, false, enterpriseName, testData.getProperty("new.enterprise.sapid"),
				testData.getProperty("new.enterprise.timezone"),
				testData.getProperty("new.enterprise.solutiondisplayname"));
		generalSettingsPage.clickOnAddPractice();
		String practiceName = testData.getProperty("new.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(enterpriseName, false, practiceName, testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		logStep("Search system node");
		generalSettingsPage.searchNode(systemName);
		logStep("Move active system node to active client with do not inherit");
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		moveSystemPage.moveSystemNodeToClientWithDoNotInherit(testData.getProperty("active.target.client.name"));
		generalSettingsPage.verifySuccessToasterMessage(systemName, testData.getProperty("active.target.client.name"));
		String clientType = generalSettingsPage.getClientType();
		logStep("Verify that when not inherited it does not takes the parent settings while transition.");
		generalSettingsPage.verifySystemNodeData(systemName, testData.getProperty("new.system.sapid"),
				testData.getProperty("new.system.timezone"), clientType, true);
		generalSettingsPage.verifyEnterpriseNodeData(enterpriseName, testData.getProperty("new.enterprise.sapid"),
				testData.getProperty("new.enterprise.timezone"), clientType, false);
		generalSettingsPage.verifyPracticeNodeData(practiceName, testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.timezone"), clientType, false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move inactive system node to active client with inherit and verify system node inherits sap id, timezone, client type and status from target client node")
	public void testMoveInactiveSystemToActiveClientWithInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Add inactive system node below client");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemName = testData.getProperty("new.system.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewSystemNode(systemName, testData.getProperty("new.system.sapid"),
				testData.getProperty("new.system.timezone"), testData.getProperty("new.system.solutiondisplayname"),
				false);
		logStep("Move inactive system node to active client with inherit");
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		moveSystemPage.moveSystemNodeToClientWithInherit(testData.getProperty("active.target.client.name"));
		generalSettingsPage.verifySuccessToasterMessage(systemName, testData.getProperty("active.target.client.name"));
		String clientSapId = generalSettingsPage.getClientSapId();
		String clientTimezone = generalSettingsPage.getClientTimezone();
		String clientType = generalSettingsPage.getClientType();
		boolean clientStatus = generalSettingsPage.returnClientNodeStatusValue();
		logStep("verify system node inherits sap id, timezone, client type and status from target client node");
		generalSettingsPage.verifySystemNodeData(systemName, clientSapId, clientTimezone, clientType, clientStatus);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active system node to active client with inherit and verify that the system and its child nodes inherits sap id, timezone, client type and status from target client node")
	public void testMoveActiveSystemToActiveClientWithInherit() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Add active system node below client with the child nodes enterprise and practice");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemName = testData.getProperty("new.system.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewSystemNode(systemName, testData.getProperty("new.system.sapid"),
				testData.getProperty("new.system.timezone"), testData.getProperty("new.system.solutiondisplayname"),
				true);
		generalSettingsPage.clickOnAddEnterprise();
		String enterpriseName = testData.getProperty("new.enterprise.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(systemName, false, enterpriseName, testData.getProperty("new.enterprise.sapid"),
				testData.getProperty("new.enterprise.timezone"),
				testData.getProperty("new.enterprise.solutiondisplayname"));
		generalSettingsPage.clickOnAddPractice();
		String practiceName = testData.getProperty("new.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewNode(enterpriseName, false, practiceName, testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		logStep("Search system node");
		generalSettingsPage.searchNode(systemName);
		logStep("Move active system node to active client node with do not inherit");
		generalSettingsPage.clickOnMoveSystem();
		moveSystemPage = new MoveSystemPage(driver);
		moveSystemPage.moveSystemNodeToClientWithInherit(testData.getProperty("active.target.client.name"));
		generalSettingsPage.verifySuccessToasterMessage(systemName, testData.getProperty("active.target.client.name"));
		String clientSapId = generalSettingsPage.getClientSapId();
		String clientTimezone = generalSettingsPage.getClientTimezone();
		String clientType = generalSettingsPage.getClientType();
		boolean clientStatus = generalSettingsPage.returnClientNodeStatusValue();
		logStep("verify system node and its child node inherits sap id, timezone, client type and status from target client node");
		generalSettingsPage.verifySystemNodeData(systemName, clientSapId, clientTimezone, clientType, clientStatus);
		generalSettingsPage.verifyEnterpriseNodeData(enterpriseName, clientSapId, clientTimezone, clientType,
				clientStatus);
		generalSettingsPage.verifyPracticeNodeData(practiceName, clientSapId, clientTimezone, clientType, clientStatus);
	}

}
