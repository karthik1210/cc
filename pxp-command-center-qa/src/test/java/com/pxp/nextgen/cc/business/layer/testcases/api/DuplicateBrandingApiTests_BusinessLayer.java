// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.DuplicateBrandingValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DuplicateBrandingApiTests_BusinessLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	private DuplicateBrandingValidations duplicateBrandingValidations;
	Response fetchDuplicateBrandApiResponse, createClientApiResponse, createSystemNodeApiResponse,
			createEnterpriseNodeApiResponse, createNgPracticeApiResponse, createAgnosticPracticeApiResponse,
			createBrandApiResponse;
	public int clientID;
	public int systemId;
	public int enterpriseId;
	public int ngPracticeId;
	public int agnosticPracticeId;
	public int brandId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		brandInfo = new BrandsInfo();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
		duplicateBrandingValidations = new DuplicateBrandingValidations();
	}

	public int createClient() throws NullPointerException, IOException {
		String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(78))));
		String clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(45))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createSystem(int clientId) throws NumberFormatException, NullPointerException, IOException {
		createSystemNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", "false", "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemNodeApiResponse.jsonPath().getInt("orgId");
	}

	public int createEnterprise(int systemId) throws NumberFormatException, NullPointerException, IOException {
		createEnterpriseNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseNodeApiResponse.jsonPath().get("orgId");
	}

	public int createNGPractice(int enterpriseId) throws NumberFormatException, NullPointerException, IOException {
		createNgPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createNgPracticeApiResponse.jsonPath().getInt("orgId");
	}

	public int createAgnosticPractice(int systemId) throws NumberFormatException, NullPointerException, IOException {
		createAgnosticPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"), systemId,
				testData.getProperty("api.system.orgtype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createAgnosticPracticeApiResponse.jsonPath().getInt("orgId");
	}

	public int createBrand(int orgId) {
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(orgId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}

	@Test(priority = 0, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created client Org")
	public void testDuplicateBrandToClientOrg() throws IOException {
		clientID = createClient();
		brandId = createBrand(clientID);
		logStep("Duplicating Brand to newly created client org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(brandId, clientID);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created system Org")
	public void testDuplicateBrandToSystemOrg() throws IOException {
		systemId = createSystem(clientID);
		brandId = createBrand(clientID);
		logStep("Duplicating Brand to newly created system org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(brandId, systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created enterprise Org")
	public void testDuplicateBrandToEnterpriseOrg() throws IOException {
		enterpriseId = createEnterprise(systemId);
		logStep("Duplicating Brand to newly created enterprise org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(brandId, enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created ng practice Org")
	public void testDuplicateBrandToNgPracticeOrg() throws IOException {
		ngPracticeId = createNGPractice(enterpriseId);
		logStep("Duplicating Brand to newly created Ng practice org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(brandId, ngPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created ng practice Org")
	public void testDuplicateBrandToAgnosticPracticeOrg() throws IOException {
		agnosticPracticeId = createAgnosticPractice(systemId);
		logStep("Duplicating Brand to newly created agnostic practice org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(brandId, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter non-existing Brand Id when duplicating brand to org")
	public void testErrorMsgWhenEnterNonExistingBrandId() throws IOException {
		logStep("Duplicating non-existing brand to org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(fetchDuplicateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter non-existing owner id when duplicating brand to Org")
	public void testErrorMsgWhenEnterNonExistingOwnerIdOfOrg() throws IOException {
		logStep("Duplicating brand to non-existing org id");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(brandId,
				Integer.parseInt(testData.getProperty("non.existing.owner.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(
				fetchDuplicateBrandApiResponse, testData.getProperty("non.existing.owner.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter null owner Id while duplicating brand")
	public void testErrorMsgWhenEnterNullownerId() throws IOException {
		logStep("Duplicating brand to owner type value as either client or org but owner id as null");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandBL(brandId, 0);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNullOwnerId(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when duplicating brand with invalid url")
	public void testErrorMsgForInvalidUrl() throws IOException {
		logStep("Duplicating brand to org with invalid url");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandInvalidUrlBL(brandId, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when duplicating brand with incorrect HTTP method")
	public void testErrorMsgForInvalidMethodCall() throws IOException {
		logStep("Duplicating brand to org with invalid HTTP method call as PATCH");
		fetchDuplicateBrandApiResponse = brandInfo.duplicateBrandInvalidMethodCallBL(brandId, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethodCall(fetchDuplicateBrandApiResponse);
	}
}
