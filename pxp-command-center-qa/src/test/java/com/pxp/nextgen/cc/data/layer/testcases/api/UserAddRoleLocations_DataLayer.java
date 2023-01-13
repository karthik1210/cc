// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UserAddRoleLocations_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private SharedInfo sharedInfo;
	Response createUserApiResponse, fetchUserDetailsApiResponse, createSystemApiResponse, createEnterpriseApiResponse,
			createNewLocationApiResponse, associateDeassociatePracticesForLocationApiResponse, createClientApiResponse,
			createPracticeApiResponse, createProviderApiResponse, addRoleLocationResponse;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, locname;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId,userId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		sharedInfo = new SharedInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		providerId = createProvider(practiceId);
		userId=testCreateNewClientUser();
		locationId=createLocation(clientId);
	}

	public int createClient(String clientType) throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createProvider(int practiceId) throws Exception {
		logStep("Create provider for providerId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		return createProviderApiResponse.jsonPath().get("providerId");

	}

	public int testCreateNewClientUser() throws Exception {
		logStep("Create client user using valid details ");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");

	}

	public int createLocation(int clientId) throws Exception {
		locname = "Pune";
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locname,
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(5),
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add location for existing userId,orgId  and validate response code")
	public void testToAddLocationForExistingUserIdAndOrgId() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		logStep("Add location for existing userId and validate response code");
		addRoleLocationResponse = userInfo.addRoleLocation_DL(userId, practiceId, 3010, "Client Super Admin",
				locationId, locname);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addRoleLocationResponse);
		CCUtils.assertEquals(addRoleLocationResponse.getStatusCode(), 200);
	}
	

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add location for existing userId and non existing orgId and validate response code")
	public void testToAddLocationForExistingUserIdAndNonExistingOrgId() throws IOException {
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		logStep("Add location for existing userId and non existing orgId and validate response code");
		addRoleLocationResponse = userInfo.addRoleLocation_DL(userId, practiceId, 3010, "Client Super Admin",
				locationId, locname);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addRoleLocationResponse);
		CCUtils.assertEquals(addRoleLocationResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyWhenUserEnterClientIdInOrgIdField(addRoleLocationResponse, practiceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add location for non existing userId and  existing orgId and validate response code")
	public void testToAddLocationForNonExistingUserIdAndExistingOrgId() throws IOException {
		int userId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		logStep("Add location for non existing userId and  existing orgId and validate response code");
		addRoleLocationResponse = userInfo.addRoleLocation_DL(userId, practiceId, 3010, "Client Super Admin",
				locationId, locname);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addRoleLocationResponse);
		CCUtils.assertEquals(addRoleLocationResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(addRoleLocationResponse,
				testData.getProperty("api.nonexisting.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add non existing location for existing userId,orgId  and validate response code")
	public void testToAddNonExistingLocationForExistingUserIdAndOrgId() throws IOException {
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		logStep("Add non existing location for existing userId,orgId  and validate response code");
		addRoleLocationResponse = userInfo.addRoleLocation_DL(userId, practiceId, 3010, "Client Super Admin",
				locationId, locname);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addRoleLocationResponse);
		CCUtils.assertEquals(addRoleLocationResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForLocationField(addRoleLocationResponse,
				testData.getProperty("api.nonexisting.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add non existing location for non existing userId,orgId  and validate response code")
	public void testToAddNonExistingLocationForNonExistingUserIdAndOrgId() throws IOException {
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		logStep("Add non existing location for non existing userId,orgId  and validate response code");
		addRoleLocationResponse = userInfo.addRoleLocation_DL(userId, practiceId, 3010, "Client Super Admin",
				locationId, locname);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addRoleLocationResponse);
		CCUtils.assertEquals(addRoleLocationResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForLocationField(addRoleLocationResponse,
				testData.getProperty("api.nonexisting.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add non existing location name for existing userId,orgId  and validate response code")
	public void testToAddExistingLocationIdNonExistingnameNonForExistingUserIdAndOrgId() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		logStep("Validating response information");
		logStep("Add non existing location name for existing userId,orgId  and validate response code");
		addRoleLocationResponse = userInfo.addRoleLocation_DL(userId, practiceId, 3010, "Client Super Admin",
				locationId, CCUtils.generateAlphaNumericString(5));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addRoleLocationResponse);
		CCUtils.assertEquals(addRoleLocationResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in each field and validate response code")
	public void testErrorMassegeForNullValues() throws IOException {
		logStep("Enter null values in each field and validate response code");
		addRoleLocationResponse = userInfo.addRoleLocation_DL(0, 0, 0, null, 0, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addRoleLocationResponse);
		CCUtils.assertEquals(addRoleLocationResponse.getStatusCode(), 400);
	}
}


