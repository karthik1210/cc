// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.testcases.api;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.GeneralSettingsClientDropdown;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ClientDirectoryValidations;
import com.pxp.cc.util.ClientTopNavDropdownValidations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ClientDropdownGeneralSettingApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private GeneralSettingsClientDropdown generalSettingsDropdown;
	private ClientTopNavDropdownValidations clientTopNavDropdownValidations = new ClientTopNavDropdownValidations();
	private ClientDirectoryValidations clientDirectoryValidations;
	JsonPath jsonPath;
	List<String> list = null;

	@BeforeMethod()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		generalSettingsDropdown = new GeneralSettingsClientDropdown();
		clientTopNavDropdownValidations = new ClientTopNavDropdownValidations();
		clientDirectoryValidations = new ClientDirectoryValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating searched client name should be displayed in .")
	public void testClickedClientNameVisibleInGeneralSettingsDropdown() {
		Response viewClient = clientInfo.getClientDetails(testData.getProperty("favoriting.client.one"));
		logStep("Validating Response Code");
		CCUtils.assertEquals(viewClient.getStatusCode(), 200);
		String getClientName = clientDirectoryValidations.fetchViewedClientName(viewClient);
		Response searchedClientVisibleInDropdownAndRecentVisits = generalSettingsDropdown
				.getClientFavRecentVisitsDropdown(testData.getProperty("favoriting.client.one"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(searchedClientVisibleInDropdownAndRecentVisits);
		CCUtils.assertEquals(searchedClientVisibleInDropdownAndRecentVisits.getStatusCode(), 200);
		clientTopNavDropdownValidations.verifyClickedClientPresentInDropdownHeaderRecentVisitsSection(
				searchedClientVisibleInDropdownAndRecentVisits, getClientName);
	}

	public List<String> viewSixClients() {
		List<String> listOfSixViewedClients = new ArrayList<String>();
		listOfSixViewedClients.add(testData.getProperty("favoriting.client.one"));
		listOfSixViewedClients.add(testData.getProperty("favoriting.client.two"));
		listOfSixViewedClients.add(testData.getProperty("favoriting.client.three"));
		listOfSixViewedClients.add(testData.getProperty("favoriting.client.four"));
		listOfSixViewedClients.add(testData.getProperty("favoriting.client.five"));
		listOfSixViewedClients.add(testData.getProperty("favoriting.client.six"));
		return listOfSixViewedClients;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating searched client name should be displayed in .")
	public void testOnlyFiveRecentlyClickedClientNameVisibleInGeneralSettingsDropdown() {
		List<String> viewingSixClients = viewSixClients();
		List<String> viewingSixClientsNames = new ArrayList<String>();
		for (String viewClient : viewingSixClients) {
			Response viewClientOneByOne = clientInfo.getClientDetails(viewClient);
			logStep("Validating Response Code");
			CCUtils.assertEquals(viewClientOneByOne.getStatusCode(), 200);
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(viewClientOneByOne);
			viewingSixClientsNames.add(clientDirectoryValidations.fetchViewedClientName(viewClientOneByOne));
		}
		Response searchedClientVisibleInDropdownAndRecentVisits = generalSettingsDropdown
				.getClientFavRecentVisitsDropdown(testData.getProperty("favoriting.client.one"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(searchedClientVisibleInDropdownAndRecentVisits);
		CCUtils.assertEquals(searchedClientVisibleInDropdownAndRecentVisits.getStatusCode(), 200);
		clientTopNavDropdownValidations.verifyOnlyFiveRecentVisitsClients(
				searchedClientVisibleInDropdownAndRecentVisits, viewingSixClientsNames);
	}
}
