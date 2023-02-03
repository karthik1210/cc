//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetExternalMapDataApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, createAppointmentTypeApiResponse,
			createNewLocationApiResponse, getExternalMapDataApiResponse, createNewStaffApiResponse;
	int clientId, systemId, enterpriseId, practiceId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		appointmentTypeInfo = new AppointmentTypeInfo();
		clientHierObj = new CreateClientHierarchy_BL();
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as location to verify externalId validation for locations and verify response code")
	public void testGetExternalMapDataForLocation() throws Exception {
		logStep("Create new location");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalIdLoc = testData.getProperty("location.external.id")
				.concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalIdLoc,
				testData.getProperty("location.timezonecode"), externalIdLoc);
		logStep("Enter existing orgId, existing externalId with sharedType as location");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(clientId, externalIdLoc, "location");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as staff for provider to verify externalId validation for provider and verify response code")
	public void testGetExternalMapDataForProvider() throws Exception {
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdPro = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalIdPro, testData.getProperty("provider.credentials"));
		logStep("Enter existing orgId, existing externalId with sharedType as staff for provider");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(practiceId, externalIdPro, "staff");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as resource to verify externalId validation for Resources and verify response code")
	public void testGetExternalMapDataForResource() throws Exception {
		logStep("Create new provider for resource");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdPro = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalIdPro, testData.getProperty("provider.credentials"));
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String externalIdRes = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), externalIdRes, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));

		logStep("Enter existing orgId, existing externalId with sharedType as resource");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(practiceId, externalIdRes, "resource");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as staff for staffs to verify externalId validation for staffs and verify response code")
	public void testGetExternalMapDataForStaff() throws Exception {
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdStaff = CCUtils.generateAlphaNumericString(10);
		createNewStaffApiResponse = sharedInfo.createStaff_BL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalIdStaff);
		logStep("Enter existing orgId, existing externalId with sharedType as staff for staffs");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(clientId, externalIdStaff, "staff");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with sharedType as appointmentType for appointmentTypes to verify externalId validation for AppointmentTypes and verify response code")
	public void testGetExternalMapDataForAppointmentType() throws Exception {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		logStep("Enter existing orgId, existing externalId with sharedType as appointmentType for appointmentTypes");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(practiceId, externalIdApp, "appointmentType");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing orgId, existing externalId with sharedType as appointmentType and verify response code")
	public void testGetExternalMapDataForNonExistingOrgId() throws Exception {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		logStep("Enter non-existing orgId, existing externalId with sharedType as appointmentType");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(practiceId, externalIdApp, "appointmentType");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, non-existing externalId with sharedType as appointmentType and verify response code")
	public void testGetExternalMapDataForNonExistingExternalId() throws Exception {
		logStep("Enter existing orgId, non-existing externalId with sharedType as appointmentType");
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(practiceId, externalIdApp, "appointmentType");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, existing externalId with invalid sharedType and verify response code")
	public void testGetExternalMapDataForInvalidSharedType() throws Exception {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		logStep("Enter existing orgId, existing externalId with invalid sharedType");
		String invalidSharedType = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(4);
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_BL(practiceId, externalIdApp, invalidSharedType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter orgId, externalId, sharedType as null and verify response code")
	public void testGetExternalMapDataForOrgIdExtIdTypeAsNull() throws Exception {
		logStep("Enter orgId, externalId, sharedType as null");
		getExternalMapDataApiResponse = sharedInfo.getExternalMapData_Null_Values_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalMapDataApiResponse);
		CCUtils.assertEquals(getExternalMapDataApiResponse.getStatusCode(), 400);
	}

}