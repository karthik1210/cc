//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.AppointmentTypeDetails;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetAppointmentTypesForResourceApiTests_BusinessLayer extends BaseRest {
	List<AppointmentTypeDetails> appointmentTypeList;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, createResourceApiResponse, createAppointmentTypeApiResponse,
			AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse,
			GetAppointmentTypesAssociatedWithResourceApiResponse;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private AppointmentTypeInfo appointmentTypeInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		appointmentTypeInfo = new AppointmentTypeInfo();
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
		resourceId = createNewResource();
		appointmentTypeId = createNewAppointmentType();
	}

	public int createNewAppointmentType() {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalId = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId, testData.getProperty("default.solution.id"), name, externalId,
				duration, categoryId, name, externalId);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enter existing resourceId and existing practiceId to get appointment type for resourses and verify response code")
	public void testResponseBodyForExistingResourceIdAndExistingPracticeId() throws IOException {
		logStep("Add valid appointment type id for resource");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, true));
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 207);
		logStep("Get appointment type id for existing resourceId and existing practiceId");
		GetAppointmentTypesAssociatedWithResourceApiResponse = appointmentTypeInfo
				.getAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(GetAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(GetAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 200);
		logStep("Remove valid appointment type id for resource");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, false));
		AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse = sharedInfo
				.addOrRemoveAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(AddOrRemoveAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 207);
		logStep("Get appointment type id for existing resourceId and existing practiceId");
		GetAppointmentTypesAssociatedWithResourceApiResponse = appointmentTypeInfo
				.getAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(GetAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(GetAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enter existing resourceId and nonexisting practiceId to get appointment type for resourses and verify response code")
	public void testResponseBodyForExistingResourceIdAndNonExistingPracticeId() throws IOException {
		logStep("Get appointment type id for existing resourceId and nonexisting practiceId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		GetAppointmentTypesAssociatedWithResourceApiResponse = appointmentTypeInfo
				.getAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(GetAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(GetAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enter nonexisting resourceId and existing practiceId to get appointment type for resourses and verify response code")
	public void testResponseBodyForNonExistingResourceIdAndExistingPracticeId() throws IOException {
		logStep("Get appointment type id for nonexisting resourceId and existing practiceId");
		int resourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		GetAppointmentTypesAssociatedWithResourceApiResponse = appointmentTypeInfo
				.getAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(GetAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(GetAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enter nonexisting resourceId and nonexisting practiceId to get appointment type for resourses and verify response code")
	public void testResponseBodyForNonExistingResourceIdAndNonExistingPracticeId() throws IOException {
		logStep("Get appointment type id for nonexisting resourceId and nonexisting practiceId");
		int resourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		GetAppointmentTypesAssociatedWithResourceApiResponse = appointmentTypeInfo
				.getAppoinmentTypesAssociatedWithResources_BL(practiceId, resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(GetAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(GetAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enter null values in resourceId and practiceId to get appointment type for resourses and verify response code")
	public void testResponseBodyForNullResourceIdAndPracticeIdField() throws IOException {
		logStep("Get appointment type id for nonexisting resourceId and nonexisting practiceId");
		GetAppointmentTypesAssociatedWithResourceApiResponse = appointmentTypeInfo
				.getAppoinmentTypesAssociatedWithResourcesNullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(GetAppointmentTypesAssociatedWithResourceApiResponse);
		CCUtils.assertEquals(GetAppointmentTypesAssociatedWithResourceApiResponse.getStatusCode(), 400);
	}
}