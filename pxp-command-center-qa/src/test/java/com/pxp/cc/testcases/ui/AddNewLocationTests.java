// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

import java.util.HashMap;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class AddNewLocationTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			getClientSystementErpriseandPractice();
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new location popup")
	public void testAddNewLocationPopup() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify add new location popup");
		ccui.getAddLocationPage().verifyAddNewLocationPopup();
		runOnce = false;
	}

	@Test(enabled = false)
	public void ngHierarchyClientCreate() throws Exception {
		if (!runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			LoginPage loginPage = new LoginPage(driver, testData.getProperty("base.url"));
			loginPage.login(testData.getProperty("user.name"), testData.getProperty("user.password"));
			logStep("Select create client button from new dropdown");
			logStep("Searching a client in client directory");
			ccui.getNgHierarchyClientCreationPage().searchNode(clientSystementErpriseandPractice.get("clientNam"));
			ccui.getNgHierarchyClientCreationPage().clickSearchButton();
			ccui.getNgHierarchyClientCreationPage().clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		}
	}
}
