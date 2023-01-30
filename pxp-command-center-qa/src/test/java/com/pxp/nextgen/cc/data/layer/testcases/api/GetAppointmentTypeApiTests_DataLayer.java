//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetAppointmentTypeApiTests_DataLayer extends BaseRest {
	private CreateClientHierarchy_DL clientHierObj;
	private SharedInfo sharedInfo;
	private Validations validation;
	private ResourceInfo resourceInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	Response getAppointmentTypeApiResponse, createProviderApiResponse, createResourceApiResponse, updateAppointmentTypesForResourcesApiResponse,
	createAppointmentTypeApiResponse;
	List<ResourceDetails> resourceList;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String externalId, firstName, lastName, resourceExternalId;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		resourceInfo = new ResourceInfo();
		appointmentTypeInfo = new AppointmentTypeInfo();
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
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId, testData.getProperty("default.solution.id"), name, externalIdApp,
				duration, categoryId, name, externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get the Appointment type details with the valid AppointmentypeId and verify response body")
	public void testGetAppointmentTypeFunctionalityUsingAppointmentTypeId() throws IOException {
		logStep("Add valid resource id for appointment type");
		getAppointmentTypeApiResponse = sharedInfo
				.getAppointmentType_DL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypeApiResponse);
		CCUtils.assertEquals(getAppointmentTypeApiResponse.getStatusCode(), 200);
		validation.verifyGetAppointmentResponseBody(getAppointmentTypeApiResponse,
				testData.getProperty("api.appointment.name"), testData.getProperty("api.appointment.externalId"),
				(Integer.parseInt(testData.getProperty("api.appointment.duration"))),
				testData.getProperty("api.appointment.durationUnit"), testData.getProperty("api.appointment.status"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get the Appointment type details with the Invalid AppointmentypeId and verify response body")
	public void testGetAppointmentTypeFunctionalityUsingInvalidAppointmentTypeId() throws IOException {
		getAppointmentTypeApiResponse = sharedInfo
				.getAppointmentType_DL(Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypeApiResponse);
		CCUtils.assertEquals(getAppointmentTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidAppointmentTypeId(getAppointmentTypeApiResponse,
				testData.getProperty("non.existing.appointment.type.id"));

	}

}
