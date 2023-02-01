//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
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

public class GetStatisticsDataApiTests_DataLayer extends BaseRest {

	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private AppointmentTypeInfo appointmentTypeInfo;
	private CreateClientHierarchy_DL clientHierObj;
	Response createResourceApiResponse, createNewLocationApiResponse, createProviderApiResponse,
			createAppointmentTypeApiResponse, getStatisticsDataResponse, associatePracticesForLocationApiResponse;
	public int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, locationId, appointmentTypeId;
	String externalId;
	public static int countLocationClient = 0;
	public static int countResourcesClient = 0;
	public static int countAppointmentTypeClient = 0;
	public static int countLocationSystem = 0;
	public static int countResourcesSystem = 0;
	public static int countAppointmentTypeSystem = 0;
	public static int countLocationEnt = 0;
	public static int countResourcesEnt = 0;
	public static int countAppointmentTypeEnt = 0;
	public static int countLocationPrac = 0;
	public static int countResourcesPrac = 0;
	public static int countAppointmentTypePrac = 0;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		resourceInfo = new ResourceInfo();
		appointmentTypeInfo =  new AppointmentTypeInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProviderForPractice(practiceId);
		resourceId = createResource(providerId);
		appointmentTypeId = createAppointmentType(practiceId);
		locationId = createLocation();
		associateLocationToPractice(practiceId);
	}

	public int createLocation() throws Exception {
		logStep("create new location");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		return createNewLocationApiResponse.jsonPath().getInt("id");
		
	}

	public int createProviderForPractice(int practiceId) throws IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	public int createResource(int providerId) {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		return resourceId;
	}

	public int createAppointmentType(int practiceId) {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		return createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	public void associateLocationToPractice(int practiceId) {
		logStep("Associate NGE practice with location");
		associatePracticesForLocationApiResponse = sharedInfo.associateDeassociatePracticeForLocation_DL(locationId,
				practiceId, true);
		CCUtils.assertEquals(associatePracticesForLocationApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify can get correct statistics data of org type client")
	public void testToGetStatisticsDataOfOrgClient() throws IOException {
		logStep("get statistics data for client");
		getStatisticsDataResponse = sharedInfo.getStatisticsData_DL(clientId);
		logStep("Validate response code is 200");
		CCUtils.assertEquals(getStatisticsDataResponse.getStatusCode(), 200);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStatisticsDataResponse);
		validation.verifyResponseBodyOfStatisticsData(getStatisticsDataResponse,
				countLocationClient = ((locationId != 0) ? ++countLocationClient : 0),
				countResourcesClient = ((providerId != 0) ? ++countResourcesClient : 0)
						+ ((resourceId != 0) ? countResourcesClient++ : 0),
				countAppointmentTypeClient = (appointmentTypeId != 0) ? ++countAppointmentTypeClient : 0);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify can get correct statistics data of org type system")
	public void testToGetStatisticsDataOfOrgSystem() throws IOException {
		logStep("get statistics data for system");
		getStatisticsDataResponse = sharedInfo.getStatisticsData_DL(systemId);
		logStep("Validate response code is 200");
		CCUtils.assertEquals(getStatisticsDataResponse.getStatusCode(), 200);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStatisticsDataResponse);
		validation.verifyResponseBodyOfStatisticsData(getStatisticsDataResponse,
				countLocationSystem = ((locationId != 0) ? ++countLocationSystem : 0),
				countResourcesSystem = ((providerId != 0) ? ++countResourcesSystem : 0)
						+ ((resourceId != 0) ? countResourcesSystem++ : 0),
				countAppointmentTypeSystem = (appointmentTypeId != 0) ? ++countAppointmentTypeSystem : 0);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify can get correct statistics data of org type enterprise")
	public void testToGetStatisticsDataOfOrgEnterprise() throws IOException {
		logStep("get statistics data for enterprise");
		getStatisticsDataResponse = sharedInfo.getStatisticsData_DL(enterpriseId);
		logStep("Validate response code is 200");
		CCUtils.assertEquals(getStatisticsDataResponse.getStatusCode(), 200);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStatisticsDataResponse);
		validation.verifyResponseBodyOfStatisticsData(getStatisticsDataResponse,
				countLocationEnt = ((locationId != 0) ? ++countLocationEnt : 0),
				countResourcesEnt = ((providerId != 0) ? ++countResourcesEnt : 0)
						+ ((resourceId != 0) ? countResourcesEnt++ : 0),
				countAppointmentTypeEnt = (appointmentTypeId != 0) ? ++countAppointmentTypeEnt : 0);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify can get correct statistics data of org type practice")
	public void testToGetStatisticsDataOfOrgPractice() throws IOException {
		logStep("get statistics data for practice");
		getStatisticsDataResponse = sharedInfo.getStatisticsData_DL(practiceId);
		logStep("Validate response code is 200");
		CCUtils.assertEquals(getStatisticsDataResponse.getStatusCode(), 200);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStatisticsDataResponse);
		validation.verifyResponseBodyOfStatisticsData(getStatisticsDataResponse,
				countLocationPrac = ((locationId != 0) ? ++countLocationPrac : 0),
				countResourcesPrac = ((providerId != 0) ? ++countResourcesPrac : 0)
						+ ((resourceId != 0) ? countResourcesPrac++ : 0),
				countAppointmentTypePrac = (appointmentTypeId != 0) ? ++countAppointmentTypePrac : 0);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify can get correct statistics data of non existing data")
	public void testToGetStatisticsDataOfNonExistingOrg() throws IOException {
		logStep("get statistics data for practice");
		getStatisticsDataResponse = sharedInfo
				.getStatisticsData_DL(Integer.parseInt(testData.getProperty("nonexisting.location.id")));
		logStep("Validate response code is 200");
		CCUtils.assertEquals(getStatisticsDataResponse.getStatusCode(), 200);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStatisticsDataResponse);
		validation.verifyResponseBodyOfStatisticsData(getStatisticsDataResponse, 0, 0, 0);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify can statistics data of null data")
	public void testToGetStatisticsDataOfNullOrg() throws IOException {
		logStep("get statistics data for practice");
		getStatisticsDataResponse = sharedInfo
				.getStatisticsData_NULL_DL();
		logStep("Validate response code is 400");
		CCUtils.assertEquals(getStatisticsDataResponse.getStatusCode(), 400);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStatisticsDataResponse);
	}
}
