// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class CreateNewResourceApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private ResourceValidations resourceValidation;
	private Validations validation;
	Response getResourceDetailsApiResponse, createClientApiResponse, createProviderApiResponse, createSystemApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, createResourceApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		resourceValidation = new ResourceValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		resourceValidation = new ResourceValidations();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid with valid details in request body to create new resource and verify response code")
	public void testResponseCodeToCreateNewResource() throws NullPointerException, IOException {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		logStep("Fetch Newly Created resource details using resource id");
		getResourceDetailsApiResponse = resourceInfo.getResourceDetails_BL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		resourceValidation.verifyResponseBodyForExistingResourceId(getResourceDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid with invalid details to create new resource and verify response code")
	public void testResponseCodeToCreateNewResourceForInvalidPayload() throws NullPointerException, IOException {
		logStep("Create new resource using null value in payload");
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, "", "", "", "", "");
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorResponseForNullPayload(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid with valid details to create new resource and verify response code")
	public void testResponseCodeToCreateNewResourceForInvalidPracticeId() throws NullPointerException, IOException {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidPracticeId(createResourceApiResponse, practiceId, providerId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test maximum character functionality for mandatory field")
	public void testAddNewResourceFunctionalityUsingMaximumFieldSize() throws IOException {
		logStep("Enter 101 characters in name field and 51 characters in external id fields");
		String resourceName = CCUtils.generateAlphaNumericString(101);
		String resourceExternalId = CCUtils.generateAlphaNumericString(51);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorResponseForMaxCharacterLimit(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid linktype in request payload and verify error response body")
	public void testErrorResponseForInvalidLinkType() throws IOException {
		logStep("Enter valid data in mandatory field and non existing timezone in timezone field");
		String resourceName = CCUtils.getName(CCUtils.generateStringWithSpace(
				testData.getProperty("resource.name").concat(CCUtils.generateAlphaNumericString(95))));
		String resourceExternalId = CCUtils.generateAlphaNumericString(50);
		String invalidLinkType = testData.getProperty("resource.link.type")
				.concat(CCUtils.generateAlphaNumericString(3));
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId), invalidLinkType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createResourceApiResponse);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorInResponseForNullValuesForOrgIdField(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response body for invalid url")
	public void testErrorResponseForInvalidUrl() throws IOException {
		logStep("Verify error response body for invalid url");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResourceInvalidUrl_BL(practiceId,
				testData.getProperty("resource.name"), testData.getProperty("resource.type"), resourceExternalId,
				String.valueOf(providerId), testData.getProperty("resource.link.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createResourceApiResponse);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a PUT call instead of POST and verify response body")
	public void testErrorMessageForInvalidMethod() throws IOException {
		logStep("Verify error response for invalid method call");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResourceInvalidMethodCall_BL(practiceId,
				testData.getProperty("resource.name"), testData.getProperty("resource.type"), resourceExternalId,
				String.valueOf(providerId), testData.getProperty("resource.link.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createResourceApiResponse);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value for practiceid with valid details to create new resource and verify response code")
	public void testResponseCodeToCreateNewResourceForNullPracticeId() throws NullPointerException, IOException {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResourceNullValue_BL(testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
	}
}
