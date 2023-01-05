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
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.PracticeAppointmentTypeInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetResourcesForAppointmentTypeApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId, appointmentTypeId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse,
			createAppointmentTypeApiResponse, addOrRemoveResourcesForAppointmentTypeApiResponse,
			fetchResourcesForAppointmentTypeResponse;
	private Validations validation;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private PracticeAppointmentTypeInfo practiceAppointmentTypeInfo;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		practiceAppointmentTypeInfo = new PracticeAppointmentTypeInfo();
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
		appointmentTypeId = createNewAppointmentType();
		resourceId = createNewResource();
	}

	public int createNewAppointmentType() {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalId = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId, name, externalId,
				duration, categoryId, name, externalId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the resources of appointment type of a practice")
	public void testGetResourceForAppointmentType() throws IOException {

		logStep("Add valid resource id for appointment type");
		List<ResourceDetails> resourceList = Arrays.asList(new ResourceDetails(resourceId, true));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(practiceId,
				appointmentTypeId, resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);

		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getResourcesForAppointmentType_BL(practiceId, appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 200);
		practiceAppointmentTypeValidations
				.verifyNoOfResourcesFetchedResourceNameandId(fetchResourcesForAppointmentTypeResponse);

		logStep("Remove valid resource id for appointment type");
		List<ResourceDetails> resourceListone = Arrays.asList(new ResourceDetails(resourceId, false));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(practiceId,
				appointmentTypeId, resourceListone);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addOrRemoveResourcesForAppointmentTypeApiResponse);
		CCUtils.assertEquals(addOrRemoveResourcesForAppointmentTypeApiResponse.getStatusCode(), 207);

		validation.verifyAddOrRemoveResourcesApiResponseBody(addOrRemoveResourcesForAppointmentTypeApiResponse,
				resourceId);

		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getResourcesForAppointmentType_BL(practiceId, appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 200);
		practiceAppointmentTypeValidations
				.verifyNoOfResourcesFetchedResourceNameandId(fetchResourcesForAppointmentTypeResponse);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of existing practice Id and appointment Type Id but no resource is associated")
	public void testResponseCodeForExistingPracticeIdAndAppointmentTypeOfNoAssociatedResource() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_BL(
				Integer.parseInt(testData.getProperty("no.resources.practice.id")),
				Integer.parseInt(testData.getProperty("no.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of existing practice Id and non-existing appointment Type Id")
	public void testErrorMessageForExistingPracticeIdAndNonexistingAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_BL(
				Integer.parseInt(testData.getProperty("get.resources.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of non-existing practice Id and existing appointment Type Id")
	public void testErrorMessageForNonExistingPracticeIdAndExistingAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_BL(
				Integer.parseInt(testData.getProperty("non.existing.resources.practice.id")),
				Integer.parseInt(testData.getProperty("get.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of both non-existing practice Id and appointment Type Id")
	public void testErrorMessageForBothNonExistingPracticeIdAndAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_BL(
				Integer.parseInt(testData.getProperty("non.existing.resources.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching resources with practice id and null and appointment Type Id as null")
	public void testErrorMessageForNullValuesInPracticeIdAndAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getResourcesForAppointmentTypeBL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 400);
	}
}
