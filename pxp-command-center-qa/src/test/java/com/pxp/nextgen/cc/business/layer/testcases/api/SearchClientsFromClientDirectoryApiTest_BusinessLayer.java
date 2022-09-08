// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientDirectory;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.GeneralSettingsClientDropdown;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ClientDirectoryValidations;
import com.pxp.cc.util.ClientTopNavDropdownValidations;
import com.pxp.cc.util.Validations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SearchClientsFromClientDirectoryApiTest_BusinessLayer extends BaseRest {
	private ClientDirectory clientDirectory;
	private ClientDirectoryValidations clientDirectoryValidations;
	JsonPath jsonPath;
	List<String> list = null;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse;
	private Validations validation;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientDirectory = new ClientDirectory();
		new GeneralSettingsClientDropdown();
		new ClientTopNavDropdownValidations();
		clientDirectoryValidations = new ClientDirectoryValidations();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
	}

	public int createClient(String clientType) throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		systemName = testData.getProperty("api.system.name").concat(CCUtils.generateAlphaNumericString(5));
		createSystemApiResponse = orgInfo.createOrgNode_BL(systemName, testData.getProperty("api.system.orgtype"), "",
				testData.getProperty("api.system.active"), "", clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		enterpriseName = testData.getProperty("api.enterprise.name").concat(CCUtils.generateAlphaNumericString(5));
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(enterpriseName,
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		practiceName = testData.getProperty("api.practice.name").concat(CCUtils.generateAlphaNumericString(5));
		createPracticeApiResponse = orgInfo.createOrgNode_BL(practiceName, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.timezoneid"), enterpriseId,
				testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	@DataProvider(name = "clientdatawithoutfilter")
	public Object[] searchClientDetailsWithoutFilter() {
		Object[][] obj = new Object[][] {
				{ clientName, testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ clientName.substring(3), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ String.valueOf(clientId), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ String.valueOf(clientId).substring(2), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ systemName, testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ systemName.substring(4), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ enterpriseName, testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ enterpriseName.substring(4), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ practiceName, testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ practiceName.substring(4), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithoutfilter", description = "search client nodes in client directory without select Client Type in client directory by keeping page 0 and page size 10")
	public void testSearchClientOutputWithoutFilter(String[] getSearchClientDetails) throws IOException {
		Response searchClientDirResult = clientDirectory.getClientFromClientDirectoryWithoutFilter(
				getSearchClientDetails[0], getSearchClientDetails[1], getSearchClientDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientDirResult);
		CCUtils.assertEquals(searchClientDirResult.getStatusCode(), 200);
		clientDirectoryValidations.verifyClientOutputWithoutFilter(searchClientDirResult, getSearchClientDetails[0]);
	}

	@DataProvider(name = "clientdatanonexisting")
	public Object[] searchClientDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.nonexisting.full.client.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.command.center.id").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.system.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.enterprise.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.practice.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatanonexisting", description = "search non existing client/client nodes in client directory by keeping page 0 and page size 10")
	public void testSearchNonExistingClient(Object[] clientDataNonExisting) {
		Response nonexistingClient = clientDirectory.getClientFromClientDirectoryWithoutFilter(
				clientDataNonExisting[0].toString(), clientDataNonExisting[1].toString(),
				clientDataNonExisting[2].toString());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(nonexistingClient);
		CCUtils.assertEquals(nonexistingClient.getStatusCode(), 200);
		jsonPath = new JsonPath(nonexistingClient.asString());
		logStep("Print data[] in response body : " + jsonPath.get("data"));
		int clientDataSize = jsonPath.getInt("data.size()");
		logStep("Get the size of data[] in response body : " + clientDataSize);
		if (clientDataSize == 0) {
			logStep("Client Data array in Response body is empty,so expected searched data : "
					+ clientDataNonExisting[0].toString() + " does not found in response body.");
		}
	}

	@DataProvider(name = "clientdatawithfilter")
	public Object[] searchClientDetailsWithFilter() throws Exception {
		ArrayList<String> filterType = new ArrayList<String>();
		filterType.add(ccUser.getClientOneType());
		filterType.add(ccUser.getClientElevenType());
		filterType.add(ccUser.getClientTwoType());
		filterType.add(ccUser.getClientEightType());
		Object[][] obj = new Object[][] {
				{ clientName, testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType },
				{ clientName.substring(3), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ String.valueOf(clientId), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ String.valueOf(clientId), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ systemName, testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType },
				{ systemName.substring(3), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ enterpriseName, testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType },
				{ enterpriseName.substring(3), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ practiceName, testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType },
				{ practiceName.substring(3), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithfilter", description = "search client nodes by applying multiselect Client Type Filter in client directory by keeping page 0 and page size 10")
	public void testSearchClientOutputWithFilter(Object[] clientDataWithFilter) throws IOException {
		System.out.println("Type :" + clientDataWithFilter[3]);
		Response searchClientDirResultWithFilter = clientDirectory.getClientFromClientDirectoryWithFilter(
				clientDataWithFilter[0].toString(), clientDataWithFilter[1].toString(),
				clientDataWithFilter[2].toString(), clientDataWithFilter[3]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientDirResultWithFilter);
		CCUtils.assertEquals(searchClientDirResultWithFilter.getStatusCode(), 200);
		clientDirectoryValidations.verifyClientOutputWithFilter(searchClientDirResultWithFilter,
				clientDataWithFilter[0], clientDataWithFilter[3]);
	}
}