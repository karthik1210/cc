// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.PracticeInterfaces;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAnInterfaceApiTests_BusinessLayer extends BaseRest {
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private Validations validations;
	Response addPracticeInterfaceApiResponse, getPracticeInterfaceApiResponse, createClientApiResponse,
			createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
	HashMap<String, Object> params = new HashMap<String, Object>();
	HashMap<String, Object> params1 = new HashMap<String, Object>();
	HashMap<String, Object> params2 = new HashMap<String, Object>();
	PracticeInterfaces practiceInterfacesObj, practiceInterfacesObj1, practiceInterfacesObj2;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
	}

	@DataProvider(name = "invalidPracticeId")
	public Object[][] invalidPracticeId() throws Exception {
		return new Object[][] { { testData.getProperty("Non.existing.practice.id").trim() },
				{ testData.getProperty("api.client.id").trim() }, { testData.getProperty("api.system.id").trim() },
				{ testData.getProperty("api.enterprise.id").trim() }, { null } };
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23452_Add Single Interface to given practiceId")
	public void addSinglePracticeInterfaceApiSuccess() throws IOException {
		logStep("TC_01_Verify the Response code as 200, when user try to Add an Interface by using POST Request [BL]");
		ArrayList<Integer> orgIds = createPractice();
		logStep("Create a payload to Add an InterfaceId for a given practiceId");
		int practiceId = orgIds.get(2);
		String nextGenApiId = "NextGenApiId" + orgIds.get(0);
		String nextGenEnterpriseId = "NextGenEnterpriseId" + orgIds.get(1);
		String nextGenPracticeId = "NextGenPracticeId" + orgIds.get(2);
		params.put("5", nextGenEnterpriseId);
		params.put("6", nextGenPracticeId);
		params.put("7", nextGenApiId);
		int expectedExternalSystemId = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
		String expectedName = testData.getProperty("api.practice.interface.name");
		boolean active = true;
		practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "BL", practiceId,
				practiceInterfacesObj);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
		CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 200);
		logStep("Getting InterfaceId information for a given practiceId");
		getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
		List<String> interfaceIds = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId");
		for (int i = 0; i < interfaceIds.size(); i++) {
			int actualExternalSystemId = getPracticeInterfaceApiResponse.jsonPath().get("externalSystemId[" + i + "]");
			String actualName = getPracticeInterfaceApiResponse.jsonPath().get("name[" + i + "]");
			if (actualExternalSystemId == expectedExternalSystemId && actualName.equalsIgnoreCase(expectedName)) {
				logStep("Interface is added successfully for " + expectedName + " as " + actualName);
			} else {
				logStep("Interface is not added successfully for " + expectedName + " as " + actualName);
			}
		}

	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23452_Add Multiple Interface to given practiceId")
	public void addMultiplePracticeInterfaceApiSuccess() throws IOException {
		logStep("TC_01_Verify the Response code as 200, when user try to Add an Interface by using POST Request [BL]");
		ArrayList<Integer> orgIds = createPractice();
		logStep("Create a payload to Add an InterfaceId for a given practiceId : 1");
		int practiceId = orgIds.get(2);
		String nextGenApiId = "NextGenApiId" + orgIds.get(0);
		String nextGenEnterpriseId = "NextGenEnterpriseId" + orgIds.get(1);
		String nextGenPracticeId = "NextGenPracticeId" + orgIds.get(2);
		params.put("5", nextGenEnterpriseId);
		params.put("6", nextGenPracticeId);
		params.put("7", nextGenApiId);
		int expectedExternalSystemId = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
		String expectedName = testData.getProperty("api.practice.interface.name");
		boolean active = true;
		practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "BL", practiceId,
				practiceInterfacesObj);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
		CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 200);
		logStep("Create a another payload to Add an InterfaceId for a given practiceId : 2");
		params1.put("13", nextGenPracticeId);
		int expectedExternalSystemId1 = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId82"));
		String expectedName1 = testData.getProperty("api.practice.interface.name82");
		practiceInterfacesObj1 = new PracticeInterfaces(expectedExternalSystemId1, params1, active);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "BL", practiceId,
				practiceInterfacesObj1);
		logStep("Create a another payload to Add an InterfaceId for a given practiceId : 3");
		params2.put("18", nextGenPracticeId);
		int expectedExternalSystemId2 = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId84"));
		String expectedName2 = testData.getProperty("api.practice.interface.name84");
		practiceInterfacesObj2 = new PracticeInterfaces(expectedExternalSystemId2, params2, false);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "BL", practiceId,
				practiceInterfacesObj2);
		logStep("Getting InterfaceId information for a given practiceId");
		getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
		List<String> interfaceIds = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId");
		ArrayList<Integer> expectedExternalSystemIds = new ArrayList<Integer>();
		expectedExternalSystemIds.clear();
		expectedExternalSystemIds.add(expectedExternalSystemId);
		expectedExternalSystemIds.add(expectedExternalSystemId1);
		expectedExternalSystemIds.add(expectedExternalSystemId2);
		ArrayList<String> expectedExternalSystemNames = new ArrayList<String>();
		expectedExternalSystemNames.clear();
		expectedExternalSystemNames.add(expectedName);
		expectedExternalSystemNames.add(expectedName1);
		expectedExternalSystemNames.add(expectedName2);
		for (int i = 0; i < interfaceIds.size(); i++) {
			int actualExternalSystemId = getPracticeInterfaceApiResponse.jsonPath().get("externalSystemId[" + i + "]");
			int expectedExternalId = expectedExternalSystemIds.get(i);
			String actualName = getPracticeInterfaceApiResponse.jsonPath().get("name[" + i + "]");
			String expectedExternalName = expectedExternalSystemNames.get(i);
			if (actualName.contains(expectedExternalName)) {
				CCUtils.assertEquals(actualExternalSystemId, expectedExternalId);
				logStep("Interface is added successfully for " + expectedExternalName + " as " + actualName);
			} else {
				logStep("Interface is not added successfully for " + expectedExternalName + " as " + actualName);
			}
		}
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23453_Add Interface to given practiceId with Invalid URI")
	public void addPracticeInterfaceWithInvalidUriApi() throws IOException {
		logStep("TC_02_Verify the Response code as 404, when user try to give invalid URI for An Interface by using POST Request [BL]");
		int orgId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
		logStep("Create a payload to Add an InterfaceId for a given practiceId");
		params.put("5", "nextGenEnterpriseId");
		params.put("6", "nextGenPracticeId");
		params.put("7", "nextGenApiId");
		int expectedExternalSystemId = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
		String expectedName = testData.getProperty("api.practice.interface.name");
		boolean active = true;
		practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("InValid", "BL", orgId,
				practiceInterfacesObj);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
		CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 404);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23454_Add Interface to Invalid Datatype PracticeId")
	public void addPracticeInterfaceWithInvalidDatatypePracticeApi() throws IOException {
		logStep("TC_03_Verify the Response code as 400, when user try to give alphanumeric practice Id for an interface by using POST Request [BL]");
		String orgId = testData.getProperty("api.practice.interface.practiceid") + "Sample";
		logStep("Create a payload to Add an InterfaceId for a given practiceId");
		params.put("5", "nextGenEnterpriseId");
		params.put("6", "nextGenPracticeId");
		params.put("7", "nextGenApiId");
		int expectedExternalSystemId = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
		String expectedName = testData.getProperty("api.practice.interface.name");
		boolean active = true;
		practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesInvaliDatatypeResponse("BL", orgId,
				practiceInterfacesObj);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
		CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23455_Add Interface to given practiceId with Invalid Mathod Type")
	public void addSinglePracticeInterfaceWithInvalidMethodApi() throws IOException {
		logStep("TC_04_Verify the Response code as 405, when user try to call Patch method instead of POST Request for An Interface [BL]");
		int orgId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
		logStep("Create a payload to Add an InterfaceId for a given practiceId");
		params.put("5", "nextGenEnterpriseId");
		params.put("6", "nextGenPracticeId");
		params.put("7", "nextGenApiId");
		int expectedExternalSystemId = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
		String expectedName = testData.getProperty("api.practice.interface.name");
		boolean active = true;
		practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("InValidMethodType", "BL", orgId,
				practiceInterfacesObj);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
		CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 405);
	}

	@Test(priority = 6, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23456_PXPP-23457_add a Practice Interface with invalid or null practice id")
	public void addPracticeInterfaceWithInvalidAndNullPracticeId(String practiceId) throws IOException {
		logStep("TC_05,TC_06 add a Practice Interface with invalid and different orgtypes(0,1,2) and null practice id [BL]");
		logStep("Create a payload to Add an InterfaceId for a given practiceId");
		params.put("5", "nextGenEnterpriseId");
		params.put("6", "nextGenPracticeId");
		params.put("7", "nextGenApiId");
		int expectedExternalSystemId = Integer
				.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
		String expectedName = testData.getProperty("api.practice.interface.name");
		boolean active = true;
		practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesInvaliDatatypeResponse("BL", practiceId,
				practiceInterfacesObj);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
		CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23458_Add Interface to given practiceId with Invalid Payload")
	public void addPracticeInterfaceWithInvalidPayloadApi() throws IOException {
		logStep("TC_07_Verify the Response code as 400, when user try to give Valid practice Id and invalid payload for an interface by using POST Request [BL]");
		int orgId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
		logStep("Create a payload to Add an InterfaceId for a given practiceId");
		params.put("5", "nextGenEnterpriseId");
		params.put("6", "nextGenPracticeId");
		params.put("7", "nextGenApiId");
		practiceInterfacesObj = new PracticeInterfaces(0, params, true);
		logStep("Add an InterfaceId for a given practiceId");
		addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "BL", orgId,
				practiceInterfacesObj);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
		CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 400);
	}

	@Test(enabled = false)
	public ArrayList<Integer> createPractice() throws IOException {
		logStep("Create nge practice below the client with valid details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.system.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validations.verifyCreatePracticeResponseBody(createPracticeApiResponse,
				testData.getProperty("api.practice.name"));
		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		ArrayList<Integer> allOrgIds = new ArrayList<Integer>();
		allOrgIds.clear();
		allOrgIds.add(clientId);
		allOrgIds.add(enterpriseId);
		allOrgIds.add(orgId);
		return allOrgIds;
	}
}
