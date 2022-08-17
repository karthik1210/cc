// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.awt.AWTException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.util.CCUtils;

public class InternalUsersSortingPaginationTests extends CommandCenterGenericTest {

	private ClientDirectoryPage clientDirectoryPage;
	private CommandCenterUsersPage commandCenterUsersPage;
	private InternalUsersPage internalUsersPage;

	@BeforeMethod
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon in left side navigation panel");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		internalUsersPage = commandCenterUsersPage.verifyInternalUsersTabByDefaultSelectedInCCUsers();
		internalUsersPage = new InternalUsersPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndPaginationInInternalUsersWebTable()
			throws InterruptedException, RuntimeException, AWTException {
		logStep("Now comparing the default Items per Page .Ideally it should be 10.");
		CCUtils.scrollPageToEnd();
		CCUtils.assertEquals(internalUsersPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(internalUsersPage.getDefaultPagination(), "1 – 10");
		String noOfClients = internalUsersPage.getTotalNoOfInternalUsers();
		logStep("Get the Total No of Clients : " + noOfClients);
		int getNoOfRowsPagination = Integer.parseInt(internalUsersPage.getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(internalUsersPage.isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(internalUsersPage.isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(internalUsersPage.isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(internalUsersPage.isLastPageButtonDisabled(), true);
		clientDirectoryPage.clickUserIcon();
		clientDirectoryPage.clickLogout();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws InterruptedException, AWTException {
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		CCUtils.assertEquals(internalUsersPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(internalUsersPage.getDefaultPagination(), "1 – 10");
		String noOfClients = internalUsersPage.getTotalNoOfInternalUsers();
		logStep("Get the Total No of Clients : " + noOfClients);
		internalUsersPage.clickNextPageButton();
		internalUsersPage.clickNextPageButton();
		internalUsersPage.clickNextPageButton();
		internalUsersPage.clickPreviousPageButton();
		internalUsersPage.clickLastPageButton();
		internalUsersPage.clickFirstPageButton();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testNextPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = internalUsersPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		internalUsersPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")
	public void testNextPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPageTwenty = internalUsersPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		internalUsersPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testNextPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPageFifty = internalUsersPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		internalUsersPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testNextPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPageHundred = internalUsersPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		internalUsersPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testNextPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = internalUsersPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		internalUsersPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testLastPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = internalUsersPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		internalUsersPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")

	public void testLastPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + internalUsersPage.getDefaultPagination());
		internalUsersPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testLastPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		internalUsersPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testLastPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		internalUsersPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testLastPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		internalUsersPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 10.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = internalUsersPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		internalUsersPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 20.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		internalUsersPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 50.")
	public void testPreviousPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		clientDirectoryPage.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		clientDirectoryPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 100.")
	public void testPreviousPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		internalUsersPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 200.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		clientDirectoryPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 10.")
	public void testFirstPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = internalUsersPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		internalUsersPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 20.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		internalUsersPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 50.")
	public void testFirstPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		internalUsersPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 100.")
	public void testFirstPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		internalUsersPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 200.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		internalUsersPage.clickItemsPerPageDropdown();
		String itemsPerPage = internalUsersPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		internalUsersPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}
}
