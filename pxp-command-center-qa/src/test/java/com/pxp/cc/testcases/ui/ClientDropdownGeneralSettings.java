package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;

public class ClientDropdownGeneralSettings extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify Search Client name visible in Client Dropdown Header and Recently Visited Section")
	public void testSearchedClientNameShouldPresentInClientDropdownHeader() throws InterruptedException {
		logStep("Test to verify that the searched client is visible general setting's Client Dropdown Header and Recently Visited Section.");
		logStep("Searching for client : " + ccuser.searchClientInClientDirectory);
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Now searched client : " + ccuser.searchClientInClientDirectory
				+ " will be validated with Client Dropdown header value which is : "
				+ generalSettingsPage.getClientDropdownHeader());
		CCUtils.assertEquals(generalSettingsPage.getClientDropdownHeader(), ccuser.searchClientInClientDirectory);
		generalSettingsPage.clickClientDropdownGenSettings();
		logStep("Now searched client : " + ccuser.searchClientInClientDirectory
				+ " will be validated with Client Dropdown Recently Visited first value which is : "
				+ generalSettingsPage.getFirstClientDropdownRecentlyVisits());
		List<String> value = generalSettingsPage.getListOfRecentVisitsInClientDropdown();
		logStep("Print Recent Visits value : " + value);
		CCUtils.assertEquals(generalSettingsPage.getFirstClientDropdownRecentlyVisits(),
				ccuser.searchClientInClientDirectory);
	}

	public List<String> viewSixClients() {
		List<String> listOfSixViewedClients = new ArrayList<String>();
		listOfSixViewedClients.add(ccuser.searchClientInClientDirectory);
		listOfSixViewedClients.add(testData.getProperty("new.active.client.name"));
		listOfSixViewedClients.add(testData.getProperty("new.inactive.client.name"));
		listOfSixViewedClients.add(testData.getProperty("existing.client.name"));
		listOfSixViewedClients.add(testData.getProperty("api.query.full.client.name"));
		listOfSixViewedClients.add(testData.getProperty("favoriting.client.one.name"));
		return listOfSixViewedClients;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify only five latest viewed Client name visible in Client Dropdown Recently Visited Section")
	public void testOnlyFiveClientsShouldPresentInClientDropdownRecentlyVisitedSection() throws InterruptedException {
		List<String> listofClients = viewSixClients();
		log("print value : " + listofClients.size());
		List<String> reverselistofClients = new ArrayList<String>();
		List<String> actualRecentVisitsLists = new ArrayList<String>();
		logStep("Searching for clients in client Directory : " + listofClients.toString());
		for (String clientname : listofClients) {
			clientDirectoryPage.searchNode(clientname);
			clientDirectoryPage.clickSearchButton();
			generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
			generalSettingsPage.verifyGeneralSettingsPageTitle();
			logStep("Now searched client : " + clientname
					+ " will be validated with Client Dropdown header value which is : "
					+ generalSettingsPage.getClientDropdownHeader());
			CCUtils.assertEquals(generalSettingsPage.getClientDropdownHeader(), clientname);
			generalSettingsPage.clickClientDropdownGenSettings();
			actualRecentVisitsLists = generalSettingsPage.getListOfRecentVisitsInClientDropdown();
			logStep("Print Recently Visited clients in Client Dropdown : " + actualRecentVisitsLists.toString());
			clientDirectoryPage = generalSettingsPage.clickClientDirectoryIcon();
		}
		Collections.reverse(listofClients);
		for (int i = 0; i < listofClients.size() - 1; i++) {
			reverselistofClients.add(listofClients.get(i));
			log("print the value of expected list of serached clients : " + reverselistofClients.get(i).toString());
		}
		logStep("comparing list of recent viewed clients matches with actual list in Recently visited section and only upto 5 clientss");
		CCUtils.assertEquals(actualRecentVisitsLists, reverselistofClients);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify by clicking Client in Recent Visit Section Client Dropdown Header it's geenral settings page got displayed.")
	public void testClickedClientInClientDropdownRecentVisitsItsGeneralSettingsPageShouldDisplay()
			throws InterruptedException {
		logStep("Searching for client : " + ccuser.searchClientInClientDirectory);
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Now searched client : " + ccuser.searchClientInClientDirectory
				+ " will be validated with Client Dropdown header value which is : "
				+ generalSettingsPage.getClientDropdownHeader());
		generalSettingsPage.clickClientDropdownGenSettings();
		String recentVisitClient = generalSettingsPage.clickAnyRecentVisitsInClientDropdown();

		CCUtils.assertTrue(generalSettingsPage.getGeneralSettingsRightSectionClientName().contains(recentVisitClient),
				"Clicked client in dropdown general settings page is displayed.");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify by clicking Client in Fav section in Client Dropdown Header it's geenral settings page got displayed.")
	public void testClickedClientInClientDropdownFavoritesItsGeneralSettingsPageShouldDisplay()
			throws InterruptedException {
		int getFavClients = clientDirectoryPage.getSizeOfListOfFavClients();
		if (getFavClients == 0) {
			clientDirectoryPage.selectFavClientInClientDirectory();
		} else {
			log("There are few clients as Favorites in Client Dropdown");
		}
		logStep("Searching for client : " + testData.getProperty("new.active.client.name"));
		clientDirectoryPage.searchNode(testData.getProperty("new.active.client.name"));
		clientDirectoryPage.clickSearchButton();
		generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Now searched client : " + testData.getProperty("new.active.client.name")
				+ " will be validated with Client Dropdown header value which is : "
				+ generalSettingsPage.getClientDropdownHeader());
		generalSettingsPage.clickClientDropdownGenSettings();
		String favClient = generalSettingsPage.clickAnyFavClientInClientDropdown();
		logStep("Clicked Fav client : " + generalSettingsPage.getGeneralSettingsRightSectionClientName()
				+ " and General Settings Page of current client : " + favClient + " is getting verified.");
		CCUtils.assertTrue(generalSettingsPage.getGeneralSettingsRightSectionClientName().contains(favClient),
				"Clicked client in dropdown general settings page is displayed.");

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify if none of clients is favorited then no Favorites section is visible.")
	public void testDeFavoriteAllClientsInClientDirectoryAndNoFavInClientDropdown() {
		int getFavClients = clientDirectoryPage.getSizeOfListOfFavClients();
		if (getFavClients == 0) {
			logStep("Searching for client : " + testData.getProperty("new.active.client.name"));
			clientDirectoryPage.searchNode(testData.getProperty("new.active.client.name"));
			clientDirectoryPage.clickSearchButton();
			generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
			logStep("Now searched client : " + testData.getProperty("new.active.client.name")
					+ " will be validated with Client Dropdown header value which is : "
					+ generalSettingsPage.getClientDropdownHeader());
			CCUtils.assertEquals(generalSettingsPage.getClientDropdownHeader(),
					testData.getProperty("new.active.client.name"));
			generalSettingsPage.clickClientDropdownGenSettings();
			boolean assertData = generalSettingsPage.checkVisiblityOfFavTitle();
			CCUtils.assertTrue(assertData, "Favorites can be zero and not visible section in Client Dropdown.");
		} else {
			clientDirectoryPage.deselectFavClientInClientDirectory();
			logStep("Searching for client : " + testData.getProperty("new.active.client.name"));
			clientDirectoryPage.searchNode(testData.getProperty("new.active.client.name"));
			clientDirectoryPage.clickSearchButton();
			generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
			logStep("Now searched client : " + testData.getProperty("new.active.client.name")
					+ " will be validated with Client Dropdown header value which is : "
					+ generalSettingsPage.getClientDropdownHeader());
			CCUtils.assertEquals(generalSettingsPage.getClientDropdownHeader(),
					testData.getProperty("new.active.client.name"));
			generalSettingsPage.clickClientDropdownGenSettings();
			boolean assertData = generalSettingsPage.checkVisiblityOfFavTitle();
			CCUtils.assertTrue(assertData, "Favorites can be zero and not visible section in Client Dropdown.");
		}
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify if some clients favorited then Favorites section is visible in Client Dropdown.")
	public void testFavoriteFiveClientsInClientDirectoryAndFavSectionVisibleInClientDropdown() {
		int getFavClients = clientDirectoryPage.getSizeOfListOfFavClients();
		if (getFavClients == 0) {
			clientDirectoryPage.selectFavClientInClientDirectory();
			logStep("Searching for client : " + testData.getProperty("new.active.client.name"));
			clientDirectoryPage.searchNode(testData.getProperty("new.active.client.name"));
			clientDirectoryPage.clickSearchButton();
			generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
			logStep("Now searched client : " + testData.getProperty("new.active.client.name")
					+ " will be validated with Client Dropdown header value which is : "
					+ generalSettingsPage.getClientDropdownHeader());
			CCUtils.assertEquals(generalSettingsPage.getClientDropdownHeader(),
					testData.getProperty("new.active.client.name"));
			generalSettingsPage.clickClientDropdownGenSettings();
			boolean assertData = generalSettingsPage.checkVisiblityOfFavTitle();
			CCUtils.assertTrue(!assertData, "Favorites is visible section in Client Dropdown.");
		} else {
			logStep("Searching for client : " + testData.getProperty("new.active.client.name"));
			clientDirectoryPage.searchNode(testData.getProperty("new.active.client.name"));
			clientDirectoryPage.clickSearchButton();
			generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
			logStep("Now searched client : " + testData.getProperty("new.active.client.name")
					+ " will be validated with Client Dropdown header value which is : "
					+ generalSettingsPage.getClientDropdownHeader());
			CCUtils.assertEquals(generalSettingsPage.getClientDropdownHeader(),
					testData.getProperty("new.active.client.name"));
			generalSettingsPage.clickClientDropdownGenSettings();
			boolean assertData = generalSettingsPage.checkVisiblityOfFavTitle();
			CCUtils.assertTrue(!assertData, "Favorites is visible section in Client Dropdown.");
		}

	}
}
