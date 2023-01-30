// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class CreateNewResourceApiTests_DataLayer extends BaseRest {
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse;
	private Validations validation;
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private ResourceValidations resourceValidation;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		resourceValidation = new ResourceValidations();
		sharedInfo = new SharedInfo();
		resourceInfo = new ResourceInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);	
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid with valid details in payload to create new resource and verify response code")
	public void testResponseCodeToCreateNewResource() throws IOException {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		logStep("Fetch Newly Created resource details using resource id");
		getResourceDetailsApiResponse = sharedInfo.getResourceDetails_DL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		resourceValidation.verifyResponseBodyForExistingResourceId(getResourceDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid with invalid details to create new resource and verify response code")
	public void testResponseCodeToCreateNewResourceForInvalidPayload() throws NullPointerException, IOException {
		logStep("Create new resource using null value in payload");
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, "", "", "", "", "");
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorResponseForNullPayload_DL(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid with valid details to create new resource and verify response code")
	public void testResponseCodeToCreateNewResourceForInvalidPracticeId() throws NullPointerException, IOException {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test maximum character functionality for mandatory field")
	public void testAddNewResourceFunctionalityUsingMaximumFieldSize() throws IOException {
		logStep("Enter 101 characters in name field and 51 characters in external id fields");
		String resourceName = CCUtils.generateAlphaNumericString(101);
		String resourceExternalId = CCUtils.generateAlphaNumericString(51);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorResponseForMaxCharacterLimit_DL(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid linktype in request payload and verify error response body")
	public void testErrorResponseForInvalidLinkType() throws IOException {
		logStep("Enter valid data in mandatory field and non existing timezone in timezone field");
		String resourceName = testData.getProperty("resource.name");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String invalidLinkType = testData.getProperty("resource.link.type")
				.concat(CCUtils.generateAlphaNumericString(3));
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId), invalidLinkType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createResourceApiResponse);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorInResponseForNullValuesForOrgIdField(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in name field and more than 50 characters in externalId field and verify error response body")
	public void testMaximumCharacterErrorFunctionality() throws IOException {
		logStep("Enter more than 100 characters in name field and more than 50 characters in externalId field");
		String invalidName = CCUtils.generateAlphaNumericString(101);
		String invalidExternalId = CCUtils.generateAlphaNumericString(51);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, invalidName,
				testData.getProperty("resource.type"), invalidExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		logStep("Validating response information");
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value for practiceid with valid details to create new resource and verify response code")
	public void testResponseCodeToCreateNewResourceForNullPracticeId() throws NullPointerException, IOException {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResourceNullValue_DL(testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response body for invalid url")
	public void testErrorResponseForInvalidUrl() throws IOException {
		logStep("Verify error response body for invalid url");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResourceInvalidUrl_DL(practiceId,
				testData.getProperty("resource.name"), testData.getProperty("resource.type"), resourceExternalId,
				String.valueOf(providerId), testData.getProperty("resource.link.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createResourceApiResponse);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make PUT method call instead of POST method call and verify response body")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		logStep("Verify error response for invalid method call");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResourceInvalidPutMethodCall_DL(practiceId,
				testData.getProperty("resource.name"), testData.getProperty("resource.type"), resourceExternalId,
				String.valueOf(providerId), testData.getProperty("resource.link.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createResourceApiResponse);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(createResourceApiResponse);
	}
}
