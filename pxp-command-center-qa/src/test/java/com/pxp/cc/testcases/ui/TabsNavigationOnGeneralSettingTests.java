//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;

public class TabsNavigationOnGeneralSettingTests extends CommandCenterGenericTest {
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

	@Test(priority=1, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers & Resources,Appointments type tab & tiles is presents at Client level")
	public void verifyVisibilityOfTabsAndTilesAtClientLevel() throws Exception {
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles is presents at Client level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}

	@Test(priority=2, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers & Resources,Appointments type tab & tiles navigate respective pages at Client level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtClientLevel() throws Exception {
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles navigate respective pages at Client level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}

	@Test(priority=3, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers & Resources,Appointments type tab & tiles is presents at System level")
	public void verifyVisibilityOfTabsAndTilesAtSystemLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles is presents At System Level");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}

	@Test(priority=4, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers & Resources,Appointments type tab & tiles navigate respective pages at System Level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtSystemLevel() throws Exception {
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles navigate respective pages at System Level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}

	@Test(priority=5, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers & Resources,Appointments type tab & tiles is presents at Enterprise level")
	public void verifyVisibilityOfTabsAndTilesAtEnterpriseLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles is presents At Enterprise Level");
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}

	@Test(priority=6, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers& Resources,Appointments type tab & tiles navigate respective pages at Enterprise Level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtEnterpriseLevel() throws Exception {
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles navigate respective pages at Enterprise Level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}
	
	
	@Test(priority=7, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers& Resources,Appointments type tab & tiles is presents at Practice level")
	public void verifyVisibilityOfTabsAndTilesAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles is presents At Practice Level");
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}
	
	@Test(priority=8, retryAnalyzer = RetryAnalyzer.class, description = "Verify locatin,Providers& Resources,Appointments type tab & tiles navigate respective pages at Practice Level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtPracticeLevel() throws Exception {
		logStep("Verify locatin,Providers& Resources,Appointments type tab & tiles navigate respective pages at Enterprise Level");
		ngHierarchyClientCreate();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
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
			ccui.getNgHierarchyClientCreationPage().clickSearchResult();
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
			ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		}
	}

}
