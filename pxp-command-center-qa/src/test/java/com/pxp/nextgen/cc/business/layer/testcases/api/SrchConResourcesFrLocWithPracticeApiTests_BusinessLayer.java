// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SrchConResourcesFrLocWithPracticeApiTests_BusinessLayer extends BaseRest {

	private SharedInfo sharedInfo;
	private Validations validation;
	private ResourceInfo resourceInfo;
	Response createProviderApiResponse, createResourceApiResponse, createSystemApiResponse, createClientApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, searchResourceApiResponse;
	public static int clientId, practiceId, locationId, resourceId;
	public int providerId;
	private OrgInfo orgInfo;
	private Validations validations;
	private ClientInfo clientInfo;
	private ResourceValidations resourceValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
		resourceInfo = new ResourceInfo();
		resourceValidations = new ResourceValidations();
	}

	public int createProviderForPractice() throws IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	public int createResource(int providerId) {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		return resourceId;
	}

	public int createLocation() {
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		int locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		return locationId;
	}

	@Test(enabled = false)
	public int createPractice(String status, String clientName, String clientSapNumber, String testFlag, int clientType,
			String clientTimezoneId, String systemName, String sysOrgType, String sysParentType, int ehrIdentifier,
			int pmIdentifier, String enterpriseName, String enterpriseType, String enterpriseParentType,
			String enterpriseTimeZone, String practiceDisplayName, String practiceName, String practiceOrgType,
			String practiceParentType, String practiceTimeZone) throws IOException {
		logStep("Create nge practice below the client with valid details");
		createClientApiResponse = clientInfo.createNewClient(status, clientName, clientSapNumber, testFlag, clientType,
				clientTimezoneId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg(status, "", systemName, sysOrgType, clientId, sysParentType, "",
				ehrIdentifier, pmIdentifier);
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg(status, "", enterpriseName, enterpriseType, systemId,
				enterpriseParentType, enterpriseTimeZone, ehrIdentifier, pmIdentifier);
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg(status, practiceDisplayName, practiceName, practiceOrgType,
				enterpriseId, practiceParentType, practiceTimeZone, ehrIdentifier, pmIdentifier);
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validations.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		return orgId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get list of resource for a practice associated with a location and verify response body")
	public void testGetConnectedResource() throws IOException {
		practiceId = createPractice(ccUser.getActive(), ccUser.getClientName().concat(CCUtils.generateRandomString()),
				ccUser.getClientSapNumber().concat(CCUtils.generateRandomString()), "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype"),
				testData.getProperty("api.enterprise.timezoneid"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"));
		locationId = createLocation();
		providerId = createProviderForPractice();
		resourceId = createResource(providerId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(practiceId,
				testData.getProperty("resource.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 200);
		resourceValidations.verifyGetResourcesApiResponseBody(searchResourceApiResponse, resourceId,
				testData.getProperty("resource.name"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 204 status code when providing non-existing practice id associated with a location")
	public void testNotFoundResourceWhenEnterNonExistingPracticeId() throws IOException {
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(
				Integer.parseInt(testData.getProperty("api.nonexisting.id")), testData.getProperty("resource.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 204 status code when providing non-existing resource name associated with a location")
	public void testNotFoundResourceWhenEnterNonExistingResourceName() throws IOException {
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(practiceId,
				testData.getProperty("non.existing.user.first.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 204 status code when providing non-existing practice id and resource name associated with a location")
	public void testNotFoundResourceWhenEnterNonExistingResourceNameAndPracticeId() throws IOException {
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(
				Integer.parseInt(testData.getProperty("api.nonexisting.id")),
				testData.getProperty("non.existing.user.first.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 400 status code when providing null practice id associated with a location")
	public void testNotFoundResourceWhenEnterNullPracticeId() throws IOException {
		searchResourceApiResponse = sharedInfo
				.getConnectedResource_Null_BL(testData.getProperty("non.existing.user.first.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 400);
	}
}
