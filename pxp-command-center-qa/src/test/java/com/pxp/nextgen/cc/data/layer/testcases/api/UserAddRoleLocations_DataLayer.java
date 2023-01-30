// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UserAddRoleLocations_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private SharedInfo sharedInfo;
	Response createUserApiResponse, fetchUserDetailsApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, createProviderApiResponse,
			addRoleLocationResponse;
	String locname;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId, userId;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		userId = testCreateNewClientUser();
		locationId = createLocation(clientId);
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
