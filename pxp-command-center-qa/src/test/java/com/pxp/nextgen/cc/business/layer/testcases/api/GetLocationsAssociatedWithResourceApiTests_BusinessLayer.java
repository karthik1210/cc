// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetLocationsAssociatedWithResourceApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, addResourceForLocationApiResponse,
			getLocationsForResourseResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		locationsInfo = new LocationsInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();

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
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter resourceId and practiceId to get locations associated with resource")
	public void testGetLocationsUsingExistingResourceIdAndPracticeId() throws Exception {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add one Resource for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_BL(locationId, practiceId,
				resourceIds);
		logStep("Enter resourceId and practiceId");
		getLocationsForResourseResponse = locationsInfo.getLocationsForResource_BL(resourceId, practiceId);
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 200);
		String locationID = getLocationsForResourseResponse.jsonPath().getString("locationId");
		locationsValidations.verifyResponseBodyForLocationAssociatedWithResourses(getLocationsForResourseResponse,
				locationID);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceId and existing practiceId and verify response code")
	public void testGetLocationUsingNonExistingResourceIdAndExistingPracticeId() throws Exception {
		logStep("Enter non-existing resourceId and existing practiceId");
		int resourceId = Integer.parseInt(testData.getProperty("non.exting.resource.id"));
		getLocationsForResourseResponse = locationsInfo.getLocationsForResource_BL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId and non-existing practiceId and verify response code")
	public void testGetLocationUsingExistingResourceIdAndNonExistingPracticeId() throws Exception {
		logStep("Enter existing resourceId and non-existing practiceId");
		int practiceId = Integer.parseInt(testData.getProperty("nonexiting.practice.id"));
		getLocationsForResourseResponse = locationsInfo.getLocationsForResource_BL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceId and practiceId and verify response code")
	public void testGetLocationUsingNonExistingResourceIdAndPracticeId() throws Exception {
		logStep("Enter non-existing resourceId and practiceId");
		int resourceId = Integer.parseInt(testData.getProperty("non.exting.resource.id"));
		int practiceId = Integer.parseInt(testData.getProperty("nonexiting.practice.id"));
		getLocationsForResourseResponse = locationsInfo.getLocationsForResource_BL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter resourceId and practiceId as null value and verify response code")
	public void testGetLocationUsingNullValues() throws Exception {
		logStep("Enter resourceId and practice as null value");
		getLocationsForResourseResponse = locationsInfo.getLocationsForResourceUsingNullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(getLocationsForResourseResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId and practiceId and verify error message for invalid URL")
	public void testErrorResponseCodeForGetLocationsForInvalidURL() throws Exception {
		logStep("Enter resourceId, practiceId and hit invalid URL");
		getLocationsForResourseResponse = locationsInfo.getLocationsForResourceForInvalidUrl_BL(resourceId, practiceId);
		logStep("Validating response validation");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getLocationsForResourseResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId and practiceId and verify error message for invalid HTTP Method call")
	public void testErrorResponseCodeForGetLocationsForInvalidMethodCall() throws Exception {
		logStep("Enter resourceId and practiceId and make invalid method call");
		getLocationsForResourseResponse = locationsInfo.getLocationsForResourceForInvalidMethod_BL(resourceId,
				practiceId);
		logStep("Valiadting response validations");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(getLocationsForResourseResponse);
	}
}
