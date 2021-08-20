package com.pxp.cc.testcases.ui;

import java.util.List;

import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;

public class ClientDirectoryTests extends CommandCenterGenericTest {

	ClientDirectoryPage clientDirectoryPage;

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Search Client name in Client Directory")
	public void testClientNameSearch() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		logStep("Verifying results for searched client");
		clientDirectoryPage.verfiySearchResult(ccuser.searchClientInClientDirectory);
	}

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check No results found is displayed if client name not present")
	public void testToCheckNoResultsFound() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.searchNode(ccuser.getNonexistingclient());
		clientDirectoryPage.clickSearchButton();
		logStep("Verifying no result found scenario");
		boolean result = clientDirectoryPage.testToCheckNoResultsFound();
		if (result == true) {
			logStep("Test Case Pass, No Result For search text is displayed ");
		} else {
			logStep("Matching Result for Searched string found, "
					+ "kindly provide client name that is not present in Commandcenter");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Default text of Search bar & functionality of Search Button")
	public void testDefaultTextInSearchBar() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("checking search button functionality");
		clientDirectoryPage.verfiySearchButtonFunctionality(ccuser.searchClientInClientDirectory);

	}

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check results after ClientType filter is applied")
	public void testClientTypeFilters() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Apply Client Type Filter");
		clientDirectoryPage.selectClientTypeDropDown();
		clientDirectoryPage.selectClientType(ccuser.getClientthreetype());
		clientDirectoryPage.applyClientTypeFilter();
		logStep("Verify client type filter");
		clientDirectoryPage.verfiySearchResultForClientType(ccuser.getClientthreetype());
	}

	@Test(enabled = true, groups = { "AcceptanceTests",
			"SmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check nodes can be searched in Client Directory screen")
	public void testClientNodeSearch() throws Exception {

		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Searching for node under a client in client directory page");
		clientDirectoryPage.searchNode(ccuser.searchSystemInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Verifying result in general setting screen");
		List<String> allNodesInClient = clientDirectoryPage.getClientNodeinGeneralSetting();
		for (int i = 0; i <= allNodesInClient.size(); i++) {

			String actualResultNode = allNodesInClient.get(i).toLowerCase();
			if (actualResultNode.contains(ccuser.searchSystemInClientDirectory.toLowerCase())) {
				logStep("Test case Pass, Node present under client is '" + allNodesInClient.get(i)
						+ "' & searched text is '" + ccuser.searchSystemInClientDirectory + "'");
				break;
			} else {
				log("Checking next node in Client, current node is " + allNodesInClient.get(i));
				continue;
			}
		}
	}

}
