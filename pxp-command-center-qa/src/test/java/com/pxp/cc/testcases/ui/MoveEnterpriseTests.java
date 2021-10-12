// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.MoveEnterprisePage;
import com.pxp.cc.objectmaps.MoveSystemPage;
import com.pxp.cc.util.CCUtils;

public class MoveEnterpriseTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public AddOrgNodePage addOrgNodePage;
	public GeneralSettingsPage generalSettingsPage;
	public MoveSystemPage moveSystemPage;
	public MoveEnterprisePage moveEnterprisePage;

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify move enterprise popup")
	public void testMoveEnterprisePopup() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("move.enterprise.source.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search enterprise node");
		generalSettingsPage.searchNode(testData.getProperty("move.enterprise.source.enterprise.name"));
		moveEnterprisePage = new MoveEnterprisePage(driver);
		String currentNodeName = moveEnterprisePage.getNodeName();
		moveEnterprisePage.clickMoveEnterprise();
		logStep("Verify move enterprise popup");
		moveEnterprisePage.verifyEnterprisePopup(currentNodeName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client name in client search textbox and verify error message")
	public void testErrorMessageForNonExistingClientEnterprisePopup() throws Exception {

		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("move.enterprise.source.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search enterprise node");
		generalSettingsPage.searchNode(testData.getProperty("move.enterprise.source.enterprise.name"));
		moveEnterprisePage = new MoveEnterprisePage(driver);
		moveEnterprisePage.clickMoveEnterprise();
		logStep("Enter non existing client name in client search textbox and verify error message");
		moveSystemPage = new MoveSystemPage(driver);
		moveSystemPage.verifyNoClientsFoundErrorMessage("abcdef@0101");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active/inactive enterprise node to inactive target client and verify error message")
	public void testErrorMessageForInactiveTargetClient() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("move.enterprise.source.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search enterprise node");
		generalSettingsPage.searchNode(testData.getProperty("move.enterprise.source.enterprise.name"));
		moveEnterprisePage = new MoveEnterprisePage(driver);
		moveEnterprisePage.clickMoveEnterprise();
		moveEnterprisePage.enterClientName((testData.getProperty("inactive.target.client.name")));
		moveEnterprisePage.selectClientFromAutoSugesstion();

		moveEnterprisePage.enterSystemName(testData.getProperty("move.enterprise.target.source.name.inactive"));
		moveEnterprisePage.selectSystemFromAutoSugesstion();
		moveEnterprisePage.clickConfirmInMoveEnterprise();
		String actualToastermessage = moveEnterprisePage.getToasterMessage();
		if (actualToastermessage.equalsIgnoreCase("Business unit cannot be moved to an inactive Target")) {
			log("Enterprise moved successfully ");
			CCUtils.assertTrue(true, "Test Pass");
		} else {
			log("Enterprise move failed ");
			CCUtils.assertTrue(false, "Test Failed");
		}
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel & cross button in move enterprise popup")
	public void testCancelButtonAndCrossIconAtMoveEnterprise() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("move.enterprise.source.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Search enterprise node");
		generalSettingsPage.searchNode(testData.getProperty("move.enterprise.source.enterprise.name"));
		moveEnterprisePage = new MoveEnterprisePage(driver);
		moveEnterprisePage.clickMoveEnterprise();
		moveSystemPage = new MoveSystemPage(driver);
		logStep("Click on cross icon and verify user redirected to general settings page");
		moveSystemPage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active enterprise node to active system with do not inherit")
	public void testMoveActiveEnterpriseToActiveSystemWithDoNotInherit() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
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
		generalSettingsPage.searchNode(enterpriseName);
		logStep("Move active enterprise node to active system node with system setting inherit");
		moveEnterprisePage = new MoveEnterprisePage(driver);
		moveEnterprisePage.clickMoveEnterprise();
		moveEnterprisePage.enterClientName((testData.getProperty("active.target.client.name")));
		moveEnterprisePage.selectClientFromAutoSugesstion();
		moveEnterprisePage.enterSystemName(testData.getProperty("move.enterprise.target.system.name.active"));
		moveEnterprisePage.selectSystemFromAutoSugesstion();
		moveEnterprisePage.clickConfirmInMoveEnterprise();
		String actualToastermessage = moveEnterprisePage.getToasterMessage();
		if (actualToastermessage.contains("SUCCESS")) {
			log("Enterprise moved successfully ");
		} else {
			log("Enterprise move failed ");
		}
		moveEnterprisePage.clickToasterlink();
		logStep("Search target system node where enterprise is moved & get it's details");
		String clientType = generalSettingsPage.getClientType();
		logStep("Verify that when not inherited it does not takes the parent settings while transition.");
		generalSettingsPage.searchNode(testData.getProperty("move.enterprise.target.system.name.active"));
		generalSettingsPage.verifyEnterpriseNodeFieldData(enterpriseName, testData.getProperty("new.enterprise.sapid"),
				testData.getProperty("new.enterprise.timezone"), clientType, false);
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.timezone"), clientType, false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Move active enterprise node to active system with inherit and verify that the enterprise and its child nodes inherits sap id, timezone, client type and status from target node")
	public void testMoveActiveEnterpriseToActiveSystemWithInherit() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
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
		generalSettingsPage.searchNode(enterpriseName);
		logStep("Move active enterprise node to active system node with system setting inherit");
		moveEnterprisePage = new MoveEnterprisePage(driver);
		moveEnterprisePage.clickMoveEnterprise();
		moveEnterprisePage.enterClientName((testData.getProperty("active.target.client.name")));
		moveEnterprisePage.selectClientFromAutoSugesstion();
		moveEnterprisePage.enterSystemName(testData.getProperty("move.enterprise.target.system.name.active"));
		moveEnterprisePage.selectSystemFromAutoSugesstion();
		moveEnterprisePage.clickInheritRadioButton();
		moveEnterprisePage.clickConfirmInMoveEnterprise();
		String actualToastermessage = moveEnterprisePage.getToasterMessage();
		if (actualToastermessage.contains("SUCCESS")) {
			log("Enterprise moved successfully ");
		} else {
			log("Enterprise move failed ");
		}
		moveEnterprisePage.clickToasterlink();
		logStep("Search target system node where enterprise is moved & get it's details");
		generalSettingsPage.searchNode(testData.getProperty("move.enterprise.target.system.name.active"));
		String targetSystemSapId = generalSettingsPage.getOrgSapId();
		String targetSystemTimezone = generalSettingsPage.getOrgTimezone();
		String targetSystemClientType = generalSettingsPage.getOrgClientType();
		boolean targetSystemClientStatus = generalSettingsPage.returnOrgStatus();
		logStep("verify enterprise node and its child node inherits sap id, timezone, client type and status from target system node");
		generalSettingsPage.verifyEnterpriseNodeFieldData(enterpriseName, targetSystemSapId, targetSystemTimezone,
				targetSystemClientType, targetSystemClientStatus);
		generalSettingsPage.verifyPracticeNodeFieldData(practiceName, targetSystemSapId, targetSystemTimezone,
				targetSystemClientType, targetSystemClientStatus);
	}
}
