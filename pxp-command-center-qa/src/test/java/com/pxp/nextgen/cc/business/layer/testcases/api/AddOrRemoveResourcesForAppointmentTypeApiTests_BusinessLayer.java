//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.AppointmentTypeDetails;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddOrRemoveResourcesForAppointmentTypeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private AppointmentTypeInfo appointmentTypeInfo;
	List<AppointmentTypeDetails> appointmentTypeList;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, addOrRemoveResForAppTypeApiResponse,
			createAppointmentTypeApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId, resourceIdone,
			resourceIdTwo;
	String externalId, firstName, lastName, resourceExternalId;

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
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceIdone = createResourceApiResponse.jsonPath().getInt("resourceId");
		resourceIdTwo = createResourceApiResponse.jsonPath().getInt("resourceId");
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add or Remove single resource id for appointment type and verify response body")
	public void testAddOrRemoveResourcesFunctionalityUsingSingleResourceId() throws IOException {
		logStep("Add valid resource id for appointment type");
		List<ResourceDetails> resourceList = Arrays.asList(new ResourceDetails(resourceId, true));
		addOrRemoveResForAppTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(practiceId,
				appointmentTypeId, resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResForAppTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResForAppTypeApiResponse.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		validation.verifyAddOrRemoveResourcesApiResponseBody(addOrRemoveResForAppTypeApiResponse, resourceId);
		logStep("Remove valid resource id for appointment type");
		List<ResourceDetails> resourceListone = Arrays.asList(new ResourceDetails(resourceId, false));
		addOrRemoveResForAppTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(practiceId,
				appointmentTypeId, resourceListone);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResForAppTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResForAppTypeApiResponse.getStatusCode(), 207);
		validation.verifyAddOrRemoveResourcesApiResponseBody(addOrRemoveResForAppTypeApiResponse, resourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resource id in ResourceId field and verify response body")
	public void testResponseBodyForInvalidResourceId() throws IOException {
		logStep("Enter non-existing resource id in ResourceId field");
		List<ResourceDetails> resourceList = Arrays
				.asList(new ResourceDetails(Integer.parseInt(testData.getProperty("non.existing.resource.id")), true));
		addOrRemoveResForAppTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(practiceId,
				appointmentTypeId, resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResForAppTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResForAppTypeApiResponse.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("non.existing.resource.id")));
		validation.verifyAddOrRemoveResourcesApiResponseBody(addOrRemoveResForAppTypeApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing appointment type id in appointmentTypeId field and verify response body")
	public void testResponseBodyForInvalidAppointmentTypeId() throws IOException {
		logStep("Enter non-existing appointment type id in appointmentTypeId field");
		List<ResourceDetails> resourceList = Arrays.asList(new ResourceDetails(resourceIdone, true),
				new ResourceDetails(resourceIdTwo, false));
		addOrRemoveResForAppTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(practiceId,
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResForAppTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResForAppTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(addOrRemoveResForAppTypeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practice id in practiceId field and verify response body")
	public void testResponseBodyForInvalidPracticeId() throws IOException {
		logStep("Enter non-existing practice id in practiceId field");
		List<ResourceDetails> resourceList = Arrays.asList(new ResourceDetails(resourceIdone, true),
				new ResourceDetails(resourceIdTwo, false));
		addOrRemoveResForAppTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), appointmentTypeId, resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResForAppTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResForAppTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(addOrRemoveResForAppTypeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceId, non-existing appointmentTypeId and verify response body")
	public void testResponseBodyForInvalidPracticeIdAppointmentTypeId() throws IOException {
		logStep("Enter non-existing practiceId, non-existing appointmentTypeId");
		List<ResourceDetails> resourceList = Arrays.asList(new ResourceDetails(resourceIdone, true),
				new ResourceDetails(resourceIdTwo, false));
		addOrRemoveResForAppTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResForAppTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResForAppTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(addOrRemoveResForAppTypeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank list in request payload and verify response body")
	public void testErrorMessageForBlankPayload() throws IOException {
		logStep("Enter blank list in request payload");
		List<ResourceDetails> resourceList = Arrays.asList();
		addOrRemoveResForAppTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResForAppTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResForAppTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(addOrRemoveResForAppTypeApiResponse);
	}
}
