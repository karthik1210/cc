// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;

public class GeneralSettingsTests extends CommandCenterGenericTest {
	ClientDirectoryPage clientDirectoryPage;
	GeneralSettingsPage generalSettingsPage;

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test verify default text, searchicon & search node functionality")
	public void testGeneralSettingScreen() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Setting page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verifying default text & status icon");
		String actualDefaultText = generalSettingsPage.defaultTextInSearch();
		CCUtils.assertEquals(actualDefaultText, "Search");
		generalSettingsPage.verifyStatusIcon();
		logStep("Verifying Auto-suggestion list is displayed");
		generalSettingsPage.enterSearchText(ccuser.searchNodeInGeneralSetting);
		generalSettingsPage.verifyAutoSuggestionList(ccuser.searchNodeInGeneralSetting);
	}

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to select & verify Auto Suggestion list for symbol & numbers search in node name")
	public void testAutoSuggestionForSingleCharcaterSearch() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching for nodes with single character name in General setting seacrh");
		generalSettingsPage.enterSearchText(ccuser.searchSymbolInGeneralSetting);
		generalSettingsPage.clickFirstInAutoSuggestion();
		generalSettingsPage.keyboardEnter();
		String actualTitle = generalSettingsPage.returnGeneralSettingTitle();
		if (actualTitle.contains(ccuser.searchSymbolInGeneralSetting)) {
			log("Test Case pass");
			CCUtils.assertTrue(true, "node selected");
		} else {
			log("Test case Failed");
			CCUtils.assertTrue(false, "Test Failed");
		}
		generalSettingsPage.clearSearchText();
		logStep("Searching for nodes with number in General setting search");
		generalSettingsPage.enterSearchText(ccuser.searchNumberInGeneralSetting);
		generalSettingsPage.clickFirstInAutoSuggestion();
		generalSettingsPage.keyboardEnter();
		String actualTitleForNumSearch = generalSettingsPage.returnGeneralSettingTitle();
		if (actualTitleForNumSearch.contains(ccuser.searchNumberInGeneralSetting)) {
			log("Test Case pass, Numbers searched in general setting is succesfull");
			CCUtils.assertTrue(true, "node selected");
		} else {
			log("Test case Failed");
			CCUtils.assertTrue(false, "Test Failed");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to search node in Client's General setting page")
	public void testNodeSearch() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching a node under client in general setting page");
		generalSettingsPage.enterSearchText(ccuser.searchNodeInGeneralSetting);
		generalSettingsPage.clickFirstInAutoSuggestion();
		generalSettingsPage.keyboardEnter();
		List<String> nodesInClient = clientDirectoryPage.getClientNodeinGeneralSetting();
		if (nodesInClient.contains(ccuser.searchNodeInGeneralSetting)) {
			log("Node present in client's general setting page");
		} else {
			log("Test Case failed, node not present under client");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to search node which is not available in Client's General setting page")
	public void testNoResultFound() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching inavlid node under client in general setting page, to check client details are highlighted");
		generalSettingsPage.enterSearchText(ccuser.searchInvalidNodeInGeneralSetting);
		String actualTitle = generalSettingsPage.returnGeneralSettingTitle();
		if (actualTitle.equals(ccuser.searchClientInClientDirectory)) {
			log("Test Case pass");
			CCUtils.assertTrue(true, "No node present so client node selected");
		} else {
			log("Test case Failed");
			CCUtils.assertTrue(false, "Test Failed, node present under client. check with invalid node name");
		}
	}
}
