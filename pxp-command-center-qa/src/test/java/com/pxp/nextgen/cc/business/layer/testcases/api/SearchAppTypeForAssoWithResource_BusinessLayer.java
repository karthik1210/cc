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

public class SearchAppTypeForAssoWithResource_BusinessLayer extends BaseRest {
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String name;
	Response createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse,
			createAppointmentTypeApiResponse, searchAppTypeForAssociatingWithResourceResponse;
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
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, existing resourceId and existing name and verify response body")
	public void testResponseBodyForExistingPracticeIdResourceIdAndName() throws IOException {
		logStep("Enter existing practiceId, existing resourceId and existing name and verify response body");
		searchAppTypeForAssociatingWithResourceResponse = sharedInfo
				.searchAppointmentTypeForAssociatingWithResource_BL(practiceId, resourceId, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypeForAssociatingWithResourceResponse);
		CCUtils.assertEquals(searchAppTypeForAssociatingWithResourceResponse.getStatusCode(), 200);
		validation.verifyAppointmentTypesForResourcesApiResponseBody(searchAppTypeForAssociatingWithResourceResponse,
				appointmentTypeId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, non-existing resourceId and existing name and verify response body")
	public void testResponseBodyForExistingPracticeIdNonExistingResourceId() throws IOException {
		logStep("Enter existing practiceId, non-existing resourceId and existing name and verify response body");
		int resourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		searchAppTypeForAssociatingWithResourceResponse = sharedInfo
				.searchAppointmentTypeForAssociatingWithResource_BL(practiceId, resourceId, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypeForAssociatingWithResourceResponse);
		CCUtils.assertEquals(searchAppTypeForAssociatingWithResourceResponse.getStatusCode(), 200);
		validation.verifyAppointmentTypesForResourcesApiResponseBody(searchAppTypeForAssociatingWithResourceResponse,
				appointmentTypeId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, existing resourceId and non-existing name and verify response code")
	public void testResponseForExistingPracticeIdExistingResourceIdNonExistingName() throws IOException {
		logStep("Enter existing practiceId, existing resourceId and non-existing name and verify response body");

		searchAppTypeForAssociatingWithResourceResponse = sharedInfo
				.searchAppointmentTypeForAssociatingWithResource_BL(practiceId, resourceId, "non existing name");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypeForAssociatingWithResourceResponse);
		CCUtils.assertEquals(searchAppTypeForAssociatingWithResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing practiceId, existing resourceId and existing name and verify response code")
	public void testResponseForNonExistingPracticeIdExistingResourceIdExistingName() throws IOException {
		logStep("Enter existing practiceId, existing resourceId and non-existing name and verify response body");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		searchAppTypeForAssociatingWithResourceResponse = sharedInfo
				.searchAppointmentTypeForAssociatingWithResource_BL(practiceId, resourceId, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypeForAssociatingWithResourceResponse);
		CCUtils.assertEquals(searchAppTypeForAssociatingWithResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing practiceId, non existing resourceId and non existing name and verify response code")
	public void testResponseForNonExistingPracticeIdNonExistingResourceIdNonExistingName() throws IOException {
		logStep("Enter non-existing practiceId, non-existing resourceId and non-existing name and verify response body");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int resourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		searchAppTypeForAssociatingWithResourceResponse = sharedInfo
				.searchAppointmentTypeForAssociatingWithResource_BL(practiceId, resourceId, "non existing name");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypeForAssociatingWithResourceResponse);
		CCUtils.assertEquals(searchAppTypeForAssociatingWithResourceResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Response code For NullValues PracticeId ResourceId And Name field")
	public void testResponseForNullValueForPracticeIdResourceIdAndName() throws IOException {
		logStep("Verify Response code For NullValue PracticeId ResourceId And Name field");
		searchAppTypeForAssociatingWithResourceResponse = sharedInfo
				.searchAppointmentTypeForAssociatingWithResource_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypeForAssociatingWithResourceResponse);
		CCUtils.assertEquals(searchAppTypeForAssociatingWithResourceResponse.getStatusCode(), 400);
	}
}