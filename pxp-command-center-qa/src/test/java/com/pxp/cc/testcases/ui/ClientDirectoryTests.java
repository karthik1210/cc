// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.awt.AWTException;
import java.util.List;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.util.CCUtils;

public class ClientDirectoryTests extends CommandCenterGenericTest {

	ClientDirectoryPage clientDirectoryPage;

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to Search Client name in Client Directory")
	public void testClientNameSearch() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		logStep("Verifying results for searched client");
		clientDirectoryPage.verfiySearchResult(ccuser.searchClientInClientDirectory);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check No results found is displayed if client name not present")
	public void testToCheckNoResultsFound() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.searchNode(ccuser.getNonExistingClientId());
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Default text of Search bar & functionality of Search Button")
	public void testDefaultTextInSearchBar() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("checking search button functionality");
		clientDirectoryPage.verfiySearchButtonFunctionality(ccuser.searchClientInClientDirectory);

	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check results after ClientType filter is applied")
	public void testClientTypeFilters() throws Exception {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Apply Client Type Filter in Client Directory.");
		clientDirectoryPage.selectClientTypeDropDown();
		clientDirectoryPage.selectClientType(ccuser.getClientThreeType());
		clientDirectoryPage.applyClientTypeFilter();
		logStep("Verify client type filter");
		clientDirectoryPage.verfiySearchResultForClientType(ccuser.getClientThreeType());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check nodes can be searched in Client Directory screen")
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultClientPaginationInClientWebTable()
			throws InterruptedException, RuntimeException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Now comparing the default Items per Page .Ideally it should be 10.");
		CCUtils.scrollPageToEnd();
		CCUtils.assertEquals(clientDirectoryPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(clientDirectoryPage.getDefaultPagination(), "1 – 10");
		String noOfClients = clientDirectoryPage.getTotalNoOfClients();
		logStep("Get the Total No of Clients : " + noOfClients);
		int getNoOfRowsPagination = Integer.parseInt(clientDirectoryPage.getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(clientDirectoryPage.isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(clientDirectoryPage.isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(clientDirectoryPage.isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(clientDirectoryPage.isLastPageButtonDisabled(), true);
		clientDirectoryPage.clickUserIcon();
		clientDirectoryPage.clickLogout();

	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testItemsPerPageDropdownChangesEffectClientWebTableRows() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		int noOfClients = Integer.valueOf(clientDirectoryPage.getTotalNoOfClients());
		clientDirectoryPage.clickItemsPerPageDropdown();
		logStep("Get the Total No of Clients : " + noOfClients);
		clientDirectoryPage.getListOfItemsPerPagee();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		CCUtils.assertEquals(clientDirectoryPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(clientDirectoryPage.getDefaultPagination(), "1 – 10");
		String noOfClients = clientDirectoryPage.getTotalNoOfClients();
		logStep("Get the Total No of Clients : " + noOfClients);
		clientDirectoryPage.clickNextPageButton();
		clientDirectoryPage.clickNextPageButton();
		clientDirectoryPage.clickNextPageButton();
		clientDirectoryPage.clickPreviousPageButton();
		clientDirectoryPage.clickLastPageButton();
		clientDirectoryPage.clickFirstPageButton();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testNextPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		String defaultItemsPerPage = clientDirectoryPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")
	public void testNextPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPageTwenty = clientDirectoryPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		clientDirectoryPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testNextPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPageFifty = clientDirectoryPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		clientDirectoryPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testNextPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPageHundred = clientDirectoryPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		clientDirectoryPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testNextPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = clientDirectoryPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		clientDirectoryPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testLastPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		String defaultItemsPerPage = clientDirectoryPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + clientDirectoryPage.getRHSPagination());
		clientDirectoryPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")

	public void testLastPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("Print the RHS Pagination : " + clientDirectoryPage.getRHSPagination());
		clientDirectoryPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testLastPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		log("Print the RHS Pagination : " + clientDirectoryPage.getRHSPagination());
		clientDirectoryPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testLastPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		log("Print the RHS Pagination : " + clientDirectoryPage.getRHSPagination());
		clientDirectoryPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testLastPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		log("Print the RHS Pagination : " + clientDirectoryPage.getRHSPagination());
		clientDirectoryPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 10.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		String defaultItemsPerPage = clientDirectoryPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 20.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		clientDirectoryPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 50.")
	public void testPreviousPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		clientDirectoryPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 100.")
	public void testPreviousPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		clientDirectoryPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 200.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		clientDirectoryPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 10.")
	public void testFirstPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		String defaultItemsPerPage = clientDirectoryPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 20.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		clientDirectoryPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 50.")
	public void testFirstPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		clientDirectoryPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 100.")
	public void testFirstPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		clientDirectoryPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 200.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		logStep("Login to Command Center");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		clientDirectoryPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}
}
