//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AppointmentTypeSearchResourcesApiTests_BusinessLayer extends BaseRest {
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId, appointmentTypeId;
	String name;
	Response createProviderApiResponse, createResourceApiResponse, createAppointmentTypeApiResponse,
			SearchResourcesApiResponse;
	private Validations validation;
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private CreateClientHierarchy_BL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		clientHierObj = new CreateClientHierarchy_BL();
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		appointmentTypeId = createNewAppointmentType();
		resourceId = createNewResource();
	}

	public int createNewAppointmentType() {
		logStep("Create new AppointmentType");
		name = CCUtils.generateAlphaNumericString(10);
		String externalId = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId,
				testData.getProperty("default.solution.id"), name, externalId, duration, categoryId, name, externalId);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		return createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	public int createNewResource() {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId and existing resource name and verify response body")
	public void testResponseBodyForExistingPracticeIdAndResourceName() throws IOException {
		logStep("Enter existing practiceId and existing resource name and verify response body");
		SearchResourcesApiResponse = sharedInfo.searchResourceForAppointmentType_BL(practiceId,
				testData.getProperty("resource.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(SearchResourcesApiResponse);
		CCUtils.assertEquals(SearchResourcesApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyOfAppTypesResourses(SearchResourcesApiResponse, resourceId,
				testData.getProperty("resource.name"), testData.getProperty("resource.link.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing practiceId and existing resource name and verify response code")
	public void testResponseCodeForNonExistingPracticeIdAndExistingResourceName() throws IOException {
		logStep("Enter non-existing practiceId and non existing resource name and verify response body");
		SearchResourcesApiResponse = sharedInfo.searchResourceForAppointmentType_BL(
				Integer.parseInt(CCUtils.generateNumericString(7)), testData.getProperty("resource.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(SearchResourcesApiResponse);
		CCUtils.assertEquals(SearchResourcesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId and non existing resource name and verify response code")
	public void testResponseCodeForExistingPracticeIdAndNonExistingResourceName() throws IOException {
		logStep("Enter existing practiceId and non-existing resource name and verify response code");
		SearchResourcesApiResponse = sharedInfo.searchResourceForAppointmentType_BL(practiceId,
				CCUtils.generateAlphaNumericString(5));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(SearchResourcesApiResponse);
		CCUtils.assertEquals(SearchResourcesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceId and non-existing resource name and verify response body")
	public void testResponseCodeForNonExistingPracticeIdAndNonExistingResourceName() throws IOException {
		logStep("Enter non-existing practiceId and non existing resource name and verify response body");
		SearchResourcesApiResponse = sharedInfo.searchResourceForAppointmentType_BL(
				Integer.parseInt(CCUtils.generateNumericString(7)), CCUtils.generateAlphaNumericString(5));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(SearchResourcesApiResponse);
		CCUtils.assertEquals(SearchResourcesApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response for null values in practiceId and name fields")
	public void testResponseCodeForNullValues() throws IOException {
		logStep("Enter non-existing practiceId and non existing resource name and verify response body");
		SearchResourcesApiResponse = sharedInfo.searchResourceForAppointmentType_NullValue_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(SearchResourcesApiResponse);
		CCUtils.assertEquals(SearchResourcesApiResponse.getStatusCode(),400);
	}

}