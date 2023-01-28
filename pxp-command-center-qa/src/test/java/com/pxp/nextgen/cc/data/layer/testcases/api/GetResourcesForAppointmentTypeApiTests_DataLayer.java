//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.PracticeAppointmentTypeInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetResourcesForAppointmentTypeApiTests_DataLayer extends BaseRest {
	private PracticeAppointmentTypeInfo practiceAppointmentTypeInfo;
	private Validations validations;
	private SharedInfo sharedInfo;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;
	Response fetchResourcesForAppointmentTypeResponse;
	private ResourceInfo resourceInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	Response getAppointmentTypeApiResponse, createProviderApiResponse, createResourceApiResponse,
			updateAppointmentTypesForResourcesApiResponse, createAppointmentTypeApiResponse,
			addOrRemoveResourcesForAppointmentTypeApiResponse;
	private CreateClientHierarchy_DL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		practiceAppointmentTypeInfo = new PracticeAppointmentTypeInfo();
		validations = new Validations();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
		resourceInfo = new ResourceInfo();
		appointmentTypeInfo = new AppointmentTypeInfo();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
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
		String name = testData.getProperty("api.appointment.name");
		String externalIdApp = testData.getProperty("api.appointment.externalId");
		String duration = testData.getProperty("api.appointment.duration");
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
		List<ResourceDetails> resourceList = Arrays.asList(new ResourceDetails(resourceId, false));
		addOrRemoveResourcesForAppointmentTypeApiResponse = sharedInfo.addOrRemoveResourcesForAppoinmentType(practiceId,
				appointmentTypeId, resourceList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the resources of appointment type of a practice")
	public void testGetResourceForAppointmentType() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getResourcesForAppointmentType_DL(resourceId, appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 200);
		practiceAppointmentTypeValidations
				.verifyNoOfResourcesFetchedResourceNameandId(fetchResourcesForAppointmentTypeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of existing practice Id and appointment Type Id but no resource is associated")
	public void testResponseCodeForExistingPracticeIdandAppointmentTypeOfNoAssociatedResource() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("no.resources.practice.id")),
				Integer.parseInt(testData.getProperty("no.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of existing practice Id and non-existing appointment Type Id")
	public void testErrorMessageForExistingPracticeIdandNonexistingAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("get.resources.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of non-existing practice Id and existing appointment Type Id")
	public void testErrorMessageForNonExistingPracticeIdandExistingAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("non.existing.resources.practice.id")),
				Integer.parseInt(testData.getProperty("get.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of both non-existing practice Id and appointment Type Id")
	public void testErrorMessageForBothNonExistingPracticeIdandAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo.getResourcesForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("non.existing.resources.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching resources with incorrect HTTP method")
	public void testErrorMessageForInvalidMethodCallWhenFetchingResourcesWithHTTPMethodPOST() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getResourcesForAppointmentType_DL_InvalidMethodCall(
						Integer.parseInt(testData.getProperty("get.resources.practice.id")),
						Integer.parseInt(testData.getProperty("get.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidMethodCall(fetchResourcesForAppointmentTypeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching resources with invalid url")
	public void testErrorMessageForInvalidUrlWhenFetchingResources() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getResourcesForAppointmentType_DL_InvalidUrl(
						Integer.parseInt(testData.getProperty("get.resources.practice.id")),
						Integer.parseInt(testData.getProperty("get.resources.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(fetchResourcesForAppointmentTypeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching resources with practice id and null and appointment Type Id as null")
	public void testErrorMessageForNullValuesInPracticeIdAndAppointmentTypeId() throws IOException {
		fetchResourcesForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getResourcesForAppointmentTypeDL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourcesForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchResourcesForAppointmentTypeResponse.getStatusCode(), 400);
		validations.verifyErrorMessageIfClientIdIsNull(fetchResourcesForAppointmentTypeResponse);
	}
}