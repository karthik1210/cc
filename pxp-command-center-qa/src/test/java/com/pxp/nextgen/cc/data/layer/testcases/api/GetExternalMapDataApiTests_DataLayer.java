//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetExternalMapDataApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_DL clientHierObj;
	private Validations validation;
	Response createProviderApiResponse, createResourceApiResponse, createAppointmentTypeApiResponse,
			createNewLocationApiResponse, getExternalMapDataApiResponse, createNewStaffApiResponse;
	int clientId, systemId, enterpriseId, practiceId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as location to check externalId validation for locations and verify response body")
	public void testGetExternalMapDataForLocation() throws Exception {
		logStep("Create new location");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalIdLoc = testData.getProperty("location.external.id")
				.concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalIdLoc,
				testData.getProperty("location.timezonecode"), externalIdLoc);
		logStep("Enter existing orgId, existing externalId with sharedType as location");
		int locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(clientId, externalIdLoc, "location");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForExternalIdValidation(getExternalMapDataApiResponse, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as staff for provider to check externalId validation for provider and verify response body")
	public void testGetExternalMapDataForProvider() throws Exception {
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdPro = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalIdPro, testData.getProperty("provider.credentials"));
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Enter existing orgId, existing externalId with sharedType as staff for provider");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(practiceId, externalIdPro, "staff");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForExternalIdValidation(getExternalMapDataApiResponse, providerId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as resource to check externalId validation for Resources and verify response body")
	public void testGetExternalMapDataForResource() throws Exception {
		logStep("Create new provider for resource");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdPro = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalIdPro, testData.getProperty("provider.credentials"));
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String externalIdRes = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), externalIdRes, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		logStep("Enter existing orgId, existing externalId with sharedType as resource");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(practiceId, externalIdRes, "resource");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForExternalIdValidation(getExternalMapDataApiResponse, resourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as staff for staffs to check externalId validation for staffs and verify response body")
	public void testGetExternalMapDataForStaff() throws Exception {
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdStaff = CCUtils.generateAlphaNumericString(10);
		createNewStaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalIdStaff);
		int staffId = createNewStaffApiResponse.jsonPath().getInt("id");
		logStep("Enter existing orgId, existing externalId with sharedType as staff for staffs");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(clientId, externalIdStaff, "staff");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForExternalIdValidation(getExternalMapDataApiResponse, staffId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as appointmentType for appointmentTypes to check externalId validation for AppointmentTypes and verify response body")
	public void testGetExternalMapDataForAppointmentType() throws Exception {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		int appTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
		logStep("Enter existing orgId, existing externalId with sharedType as appointmentType for appointmentTypes");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(practiceId, externalIdApp, "appointmentType");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForExternalIdValidation(getExternalMapDataApiResponse, appTypeId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, externalId not associated with location with sharedType as location and verify response body")
	public void testGetExternalMapDataForLocationNotAssWithExtId() throws Exception {
		String externalId = CCUtils.generateAlphaNumericString(3);
		logStep("Enter existing orgId, externalId not associated with location with sharedType as location");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(clientId, externalId, "location");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForLocationSharedType(getExternalMapDataApiResponse, externalId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, externalId with not associated with sharedType as staff for provider and verify response body")
	public void testGetExternalMapDataForProviderNotAssWithExtId() throws Exception {
		String externalId = CCUtils.generateAlphaNumericString(3);
		logStep("Enter existing orgId, externalId with not associated with sharedType as staff for provider");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(practiceId, externalId, "staff");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForStaffSharedType(getExternalMapDataApiResponse, externalId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, externalId not associated with sharedType as resource and verify response body")
	public void testGetExternalMapDataForResourceNotAssWithExtId() throws Exception {
		String externalId = CCUtils.generateAlphaNumericString(3);
		logStep("Enter existing orgId, externalId not associated with sharedType as resource");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(practiceId, externalId, "resource");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForResourceSharedType(getExternalMapDataApiResponse, externalId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, externalId not associated with sharedType as staff for staffs and verify response body")
	public void testGetExternalMapDataForStaffNotAssWithExtId() throws Exception {
		String externalId = CCUtils.generateAlphaNumericString(3);
		logStep("Enter existing orgId, existing externalId with sharedType as staff for staffs");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(clientId, externalId, "staff");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForStaffSharedType(getExternalMapDataApiResponse, externalId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, externalId not associated with sharedType as AppointmentType for AppointmentTypes and verify response body")
	public void testGetExternalMapDataForAppointmentTypeNotAssWithExtId() throws Exception {
		String externalId = CCUtils.generateAlphaNumericString(3);
		logStep("Enter existing orgId, externalId not associated with sharedType as AppointmentType for AppointmentTypes");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(clientId, externalId, "appointmentType");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForAppTypeSharedType(getExternalMapDataApiResponse, externalId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, externalId with invalid sharedType and verify response code")
	public void testGetExternalMapDataForInvalidSharedType() throws Exception {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		logStep("Enter existing orgId, existing externalId with invalid sharedType");
		String invalidSharedType = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(4);
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_DL(practiceId, externalIdApp, invalidSharedType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter orgId, externalId, sharedType as null and verify response code")
	public void testGetExternalMapDataForOrgIdExtIdTypeAsNull() throws Exception {
		logStep("Enter orgId, externalId, sharedType as null");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_Null_Values_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
	}

}