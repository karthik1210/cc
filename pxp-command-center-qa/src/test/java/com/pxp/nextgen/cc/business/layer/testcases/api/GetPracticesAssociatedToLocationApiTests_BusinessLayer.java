//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetPracticesAssociatedToLocationApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private LocationsValidations locationValidation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createNewLocationApiResponse, fetchLocationDetailsApiResponse,
			createOrgNodeApiResponse, associateDeassociatePracticesForLocationApiResponse,
			getPracticesAssociateToLocationApiResponse;
	String externalId, locationName;
	int clientId, systemId, enterpriseId, ngePracticeId, locationId, locationIdOne;
	ArrayList<Integer> associatePracticeIds;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationValidation = new LocationsValidations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		ngePracticeId = clientHierObj.createPractice_BL(enterpriseId);
		locationId = createLocation(clientId);
		locationIdOne = createLocation(clientId);
	}

	public int createLocation(int clientId) throws Exception {
		logStep("Enter valid data in mandatory field");
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 204 status code is displayed in response when practices are not associated with location")
	public void testResponseBodyWhenPracticesNotAssociatedWithLocation() throws IOException {
		logStep("Enter valid location id in locationId field and verify 204 status code is displayed in response if  practices are not associated with location");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(locationIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociateToLocationApiResponse);
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate and deassociate practice for location and verify get location for practice functionality")
	public void testGetLocationForPracticeFunctionality() throws IOException {
		logStep("Associate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, ngePracticeId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Fetch practice associated to location");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(locationId);
		logStep("Validating response information");
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 200);
		associatePracticeIds = new ArrayList<Integer>();
		associatePracticeIds.add(ngePracticeId);
		locationValidation.verifyGetPracticesAssociatedToLocationApiResponseBody(
				getPracticesAssociateToLocationApiResponse, associatePracticeIds);
		logStep("Deassociate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, ngePracticeId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Fetch deassociated practice id from location and verify 204 status code is displayed in response if practices are not associated with location");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(locationId);
		logStep("Validating response information");
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing location id in locationId field and verify response body")
	public void testErrorResponseForNonExistingLocationId() throws IOException {
		logStep("Enter non existing location id in location id field");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociateToLocationApiResponse);
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getPracticesAssociateToLocationApiResponse = sharedInfo
				.getPracticesAssociatedToLocationUsingInvalidUrl_BL(locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociateToLocationApiResponse);
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getPracticesAssociateToLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		getPracticesAssociateToLocationApiResponse = sharedInfo
				.getPracticesAssociatedToLocationUsingInvalidMethod_BL(locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociateToLocationApiResponse);
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(getPracticesAssociateToLocationApiResponse);
	}

}
