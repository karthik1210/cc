//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.AppointmentTypeDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddOrRemoveAppTypesForResApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private OrgInfo orgInfo;
	List<AppointmentTypeDetails> appointmentTypeList;
	private ResourceInfo resourceInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, updateAppointmentTypesForResourcesApiResponse,
			createAppointmentTypeApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().get("clientId");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createSystemApiResponse.jsonPath().get("orgId");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().get("orgId");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId, name, externalIdApp,
				duration, categoryId, name, externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add Appointment Types for existing resourceId and practiceId and verify response body")
	public void testToAddExistingAppointmentTypesForExistingResourceId() throws IOException {
		logStep("Add existing Appointment Types for existing resourceId and practiceId");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, true));
		updateAppointmentTypesForResourcesApiResponse = sharedInfo
				.addOrRemoveAppointmentTypesForResources_DL(resourceId, practiceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		CCUtils.assertEquals(updateAppointmentTypesForResourcesApiResponse.getStatusCode(), 207);
		ArrayList<Integer> appointmentTypeIds = new ArrayList<Integer>();
		appointmentTypeIds.add(appointmentTypeId);
		validation.verifyAppointmentTypesForResourcesApiResponseBody(updateAppointmentTypesForResourcesApiResponse,
				appointmentTypeId);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Remove Appointment Types for existing resourceId and practiceId and verify response body")
	public void testToRemoveExistingAppointmentTypesForExistingResourceId() throws IOException {
		logStep("Remove existing Appointment Types for existing providerId and practiceId");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, false));
		updateAppointmentTypesForResourcesApiResponse = sharedInfo
				.addOrRemoveAppointmentTypesForResources_DL(resourceId, practiceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		CCUtils.assertEquals(updateAppointmentTypesForResourcesApiResponse.getStatusCode(), 207);
		ArrayList<Integer> appointmentTypeIds = new ArrayList<Integer>();
		appointmentTypeIds.add(appointmentTypeId);
		validation.verifyAppointmentTypesForResourcesApiResponseBody(updateAppointmentTypesForResourcesApiResponse,
				appointmentTypeId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting resource For Existing Appointment TypeId and practiceId  and verify error message")
	public void testToAddNonExistingResourceIdForExistingAppointmentTypeId() throws IOException {
		logStep("Add existing Appointment Types for nonexisting resourceId");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, true));
		updateAppointmentTypesForResourcesApiResponse = sharedInfo.addOrRemoveAppointmentTypesForResources_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")), practiceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		CCUtils.assertEquals(updateAppointmentTypesForResourcesApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateAppointmentTypesForResourcesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resource for nonexisting Appointment TypeId and existing practiceId and verify error message")
	public void testExistingResourceIdForNonExistingAppointmentTypeId() throws IOException {
		logStep("Enter existing resource for nonexisting Appointment TypeId and existing practiceId");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), true));
		updateAppointmentTypesForResourcesApiResponse = sharedInfo
				.addOrRemoveAppointmentTypesForResources_DL(resourceId, practiceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		CCUtils.assertEquals(updateAppointmentTypesForResourcesApiResponse.getStatusCode(), 207);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resource for existing Appointment TypeId and nonexisting practiceId and verify error message")
	public void testExistingResourceIdForNonExistingProviderIdAndPracticeId() throws IOException {
		logStep("Enter existing resource for existing Appointment TypeId and nonexisting practiceId");
		logStep("Add existing Appointment Types for existing resourceId and practiceId");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, true));
		updateAppointmentTypesForResourcesApiResponse = sharedInfo.addOrRemoveAppointmentTypesForResources_DL(
				resourceId, Integer.parseInt(testData.getProperty("non.existing.practice.id")), appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		validation.verifyErrorMessageForInvalidInput(updateAppointmentTypesForResourcesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Enter blank input in request payload");
		appointmentTypeList = Arrays.asList();
		updateAppointmentTypesForResourcesApiResponse = sharedInfo
				.addOrRemoveAppointmentTypesForResources_DL(resourceId, practiceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		CCUtils.assertEquals(updateAppointmentTypesForResourcesApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateAppointmentTypesForResourcesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for resourceId and practiceId and verify error message")
	public void testAddToResourcesForNullValues() throws IOException {
		logStep("Enter null values for resourceId and practiceId");
		updateAppointmentTypesForResourcesApiResponse = sharedInfo.appointmentTypesAssociatedToResource_NullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		CCUtils.assertEquals(updateAppointmentTypesForResourcesApiResponse.getStatusCode(), 400);
	}
}
