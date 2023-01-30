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
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddRemoveAppointmentTypesWithResourceApiTests_BusinessLayer extends BaseRest {
	List<AppointmentTypeDetails> appointmentTypeList;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId, appointmentTypeId;
	Response createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse,
			createAppointmentTypeApiResponse, AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse;
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
		String name = CCUtils.generateAlphaNumericString(10);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid appointmentType id in appointmentTypeId field and verify response body")
	public void testResponseBodyForInvalidResourceId() throws IOException {
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), true));
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 207);
		ArrayList<Integer> appointmentTypeIds = new ArrayList<Integer>();
		appointmentTypeIds.add(appointmentTypeId);
		validation.verifyAddOrRemoveAppointmentIdApiResponseBody(
				AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse, appointmentTypeId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid practice id in practiceId field and verify response body")
	public void testResponseBodyForInvalidPracticeId() throws IOException {
		appointmentTypeList = Arrays.asList(
				new AppointmentTypeDetails(Integer.parseInt(testData.getProperty("valid.appointment.type.id")), true));
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(
						Integer.parseInt(testData.getProperty("non.existing.practice.id")), resourceId,
						appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid Resource id in resourceId field and verify response body")
	public void testResponseBodyForInvalidAppointmentTypeId() throws IOException {
		appointmentTypeList = Arrays.asList(
				new AppointmentTypeDetails(Integer.parseInt(testData.getProperty("valid.appointment.type.id")), true));
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(practiceId,
						Integer.parseInt(testData.getProperty("non.existing.resource.id")), appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add/Remove single appointment type id for resource and verify response body")
	public void testAddOrRemoveResourcesFunctionalityUsingSingleResourceId() throws IOException {
		logStep("Add valid appointment type id for resource");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, true));
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 207);
		ArrayList<Integer> appointmentTypeIds = new ArrayList<Integer>();
		appointmentTypeIds.add(appointmentTypeId);
		validation.verifyAddOrRemoveAppointmentIdApiResponseBody(
				AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse, appointmentTypeId);
		logStep("Remove valid appointment type id for resource");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, false));
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 207);
		validation.verifyAddOrRemoveAppointmentIdApiResponseBody(
				AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse, appointmentTypeId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank list in payload and verify response body")
	public void testErrorMessageForBlankPayload() throws IOException {
		logStep("Add valid resource ids for appointment type");
		appointmentTypeList = Arrays.asList();
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
	}
}
