//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class SearchProviderInLocationsApiTests_DataLayer extends BaseRest {

	private SharedInfo sharedInfo;
	Response createProviderApiResponse, createSystemApiResponse, createClientApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, getProvidersApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, searchProvidersLocationResponse;
	public static int clientId, systemId, enterpriseId, practiceId;
	public static String locationName, providerFirstName, providerLastName;
	public int providerId, locationId;
	private OrgInfo orgInfo;
	private Validations validations;
	private ClientInfo clientInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
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
		providerId = createProviderForPractice();
		locationId = createLocation();
	}

	public int createProviderForPractice() throws IOException {
		providerFirstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		providerLastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, providerFirstName, providerLastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validations.verifyCreateProviderApiResponseBody(createProviderApiResponse, providerFirstName, providerLastName);
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	public int createLocation() {
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		createNewLocationApiResponse.jsonPath().getInt("id");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo.associateDeassociatePracticeForLocation_DL(
				createNewLocationApiResponse.jsonPath().getInt("id"), practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(enabled = false)
	public int createPractice(String status, String clientName, String clientSapNumber, String testFlag, int clientType,
			String clientTimezoneId, String systemName, String sysOrgType, String sysParentType, int ehrIdentifier,
			int pmIdentifier, String enterpriseName, String enterpriseType, String enterpriseParentType,
			String enterpriseTimeZone, String practiceDisplayName, String practiceName, String practiceOrgType,
			String practiceParentType, String practiceTimeZone) throws IOException {
		logStep("Create nge practice below the client with valid details");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber, testFlag,
				clientType, clientTimezoneId, testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(status, "", systemName, sysOrgType, clientId,
				sysParentType, "", ehrIdentifier, pmIdentifier);
		systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(status, "", enterpriseName, enterpriseType, systemId,
				enterpriseParentType, enterpriseTimeZone, ehrIdentifier, pmIdentifier);
		enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(status, practiceDisplayName, practiceName,
				practiceOrgType, enterpriseId, practiceParentType, practiceTimeZone, ehrIdentifier, pmIdentifier);
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		return orgId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body when searching providers by inputing correct practice id and provider name")
	public void testSerachProvidersLocation() throws IOException {
		searchProvidersLocationResponse = sharedInfo.searchProviderLocation_DL(practiceId, providerFirstName.trim());
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(searchProvidersLocationResponse);
		CCUtils.assertEquals(searchProvidersLocationResponse.getStatusCode(), 200);
		validations.verifyResponseBodyOfProvidersLocation(searchProvidersLocationResponse, providerId,
				providerFirstName, providerLastName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body when searching providers by inputing non-existing practice id and existing provider name")
	public void testErrorNonExistingPracticeIdWhenSerachProvidersLocation() throws IOException {
		searchProvidersLocationResponse = sharedInfo.searchProviderLocation_DL(
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), providerFirstName.trim());
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(searchProvidersLocationResponse);
		CCUtils.assertEquals(searchProvidersLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body when searching providers by inputing existing practice id and non-existing provider name")
	public void testErrorNonExistingProviderNameWhenSerachProvidersLocation() throws IOException {
		searchProvidersLocationResponse = sharedInfo.searchProviderLocation_DL(practiceId,
				testData.getProperty("nonexisting.name"));
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(searchProvidersLocationResponse);
		CCUtils.assertEquals(searchProvidersLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body when searching providers by inputing both non-existing practice id and provider name")
	public void testErrorBothNonExistingProviderNameAndPracticeIdWhenSerachProvidersLocation() throws IOException {
		searchProvidersLocationResponse = sharedInfo.searchProviderLocation_DL(
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")),
				testData.getProperty("nonexisting.name"));
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(searchProvidersLocationResponse);
		CCUtils.assertEquals(searchProvidersLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body when searching providers by inputing null practice id and existing provider name")
	public void testErrorNullPracticeIdWhenSerachProvidersLocation() throws IOException {
		searchProvidersLocationResponse = sharedInfo.searchProviderLocation_NULL_DL(providerFirstName.trim());
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(searchProvidersLocationResponse);
		CCUtils.assertEquals(searchProvidersLocationResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body when searching providers by inputing existing practice id and null provider name")
	public void testErrorNullProviderNameWhenSerachProvidersLocation() throws IOException {
		searchProvidersLocationResponse = sharedInfo.searchProviderLocation_DL(practiceId, "");
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(searchProvidersLocationResponse);
		CCUtils.assertEquals(searchProvidersLocationResponse.getStatusCode(), 400);
		validations.verifyErrorMessageForBlankField(searchProvidersLocationResponse);
	}
}
