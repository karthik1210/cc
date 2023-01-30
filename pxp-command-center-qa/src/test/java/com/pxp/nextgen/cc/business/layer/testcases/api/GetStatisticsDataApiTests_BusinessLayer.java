//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetStatisticsDataApiTests_BusinessLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private AppointmentTypeInfo appointmentTypeInfo;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createResourceApiResponse, createNewLocationApiResponse, createProviderApiResponse,
			createAppointmentTypeApiResponse, getStatisticsDataResponse,
			associateDeassociatePracticesForLocationApiResponse;
	public int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, locationId, appointmentTypeId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;
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
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
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
		systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create new provider");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new location");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		logStep("Associate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		logStep("Create new AppointmentType");
		String aptName = CCUtils.generateAlphaNumericString(10);
		String aptExternalId = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId,
				testData.getProperty("default.solution.id"), aptName, externalId, duration, categoryId, aptName,
				aptExternalId);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify can get correct statistics data of org type client")
	public void testToGetStatisticsDataOfOrgClient() throws IOException {
		logStep("get statistics data for client");
		getStatisticsDataResponse = sharedInfo.getStatisticsData_BL(clientId);
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
		getStatisticsDataResponse = sharedInfo.getStatisticsData_BL(systemId);
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
		getStatisticsDataResponse = sharedInfo.getStatisticsData_BL(enterpriseId);
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
		getStatisticsDataResponse = sharedInfo.getStatisticsData_BL(practiceId);
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
				.getStatisticsData_BL(Integer.parseInt(testData.getProperty("nonexisting.location.id")));
		logStep("Validate response code is 200");
		CCUtils.assertEquals(getStatisticsDataResponse.getStatusCode(), 200);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStatisticsDataResponse);
		validation.verifyResponseBodyOfStatisticsData(getStatisticsDataResponse, 0, 0, 0);
	}
}
