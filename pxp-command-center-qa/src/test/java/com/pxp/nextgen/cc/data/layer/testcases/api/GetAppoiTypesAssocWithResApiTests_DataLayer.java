//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetAppoiTypesAssocWithResApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	List<AppointmentTypeDetails> appointmentTypeList;
	private ResourceInfo resourceInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, getAppointmentTypesAscWithResourcesApiResponse,
			updateAppointmentTypesForResourcesApiResponse, createAppointmentTypeApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId, name;

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
		name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId, name, externalIdApp,
				duration, categoryId, name, externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
		logStep("Add existing resourceId for existing resourceId and practiceId");
		appointmentTypeList = Arrays.asList(new AppointmentTypeDetails(appointmentTypeId, true));
		updateAppointmentTypesForResourcesApiResponse = sharedInfo
				.addOrRemoveAppointmentTypesForResources_DL(resourceId, practiceId, appointmentTypeList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateAppointmentTypesForResourcesApiResponse);
		CCUtils.assertEquals(updateAppointmentTypesForResourcesApiResponse.getStatusCode(), 207);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId, practiceId to get Appointment Types For Resource and Verify response body")
	public void testToGetAppoinmentTypesAssociatedToExistingResourceId() throws IOException {
		logStep("Enter existing resourceId, practiceId to get Appointment Types For Resource");
		getAppointmentTypesAscWithResourcesApiResponse = sharedInfo
				.getAppointmentTypesAssociatedToResource_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesAscWithResourcesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesAscWithResourcesApiResponse.getStatusCode(), 200);
		validation.verifyAppointmentTypesForResourcesApiResponseBody(getAppointmentTypesAscWithResourcesApiResponse,
				appointmentTypeId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceId, existing practiceId to get Appointment Types For Resource and Verify response body")
	public void testToGetAppoinmentTypesAssociatedToNonExistingResourceId() throws IOException {
		logStep("Enter non-existing resourceId, existing practiceId to get Appointment Types For Resource");
		getAppointmentTypesAscWithResourcesApiResponse = sharedInfo.getAppointmentTypesAssociatedToResource_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")), practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesAscWithResourcesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesAscWithResourcesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId, non-existing practiceId to get Appointment Types For Resource and Verify response body")
	public void testToGetAppoinmentTypesAssociatedToNonExistingPracticeId() throws IOException {
		logStep("Enter existing resourceId, non-existing practiceId to get Appointment Types For Resource");
		getAppointmentTypesAscWithResourcesApiResponse = sharedInfo.getAppointmentTypesAssociatedToResource_DL(
				resourceId, Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesAscWithResourcesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesAscWithResourcesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceId, non-existing practiceId to get Appointment Types For Resource and Verify response body")
	public void testToGetAppoinmentTypesAssociatedToNonExistingResourceIdPracticeId() throws IOException {
		logStep("Enter non-existing resourceId, non-existing practiceId to get Appointment Types For Resource");
		getAppointmentTypesAscWithResourcesApiResponse = sharedInfo.getAppointmentTypesAssociatedToResource_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesAscWithResourcesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesAscWithResourcesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter resourceId, practiceId as null to get Appointment Types For Resource and Verify response body")
	public void testToGetAppoinmentTypesAssociatedWithResourceIdPracticeIdAsNull() throws IOException {
		logStep("Enter resourceId, practiceId as null to get Appointment Types For Resource");
		getAppointmentTypesAscWithResourcesApiResponse = sharedInfo
				.appointmentTypesAssociatedToResource_NullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesAscWithResourcesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesAscWithResourcesApiResponse.getStatusCode(), 400);
	}
}
