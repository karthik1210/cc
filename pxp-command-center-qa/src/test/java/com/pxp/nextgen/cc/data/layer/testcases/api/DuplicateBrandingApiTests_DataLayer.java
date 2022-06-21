package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.DuplicateBrandingValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DuplicateBrandingApiTests_DataLayer extends BaseRest {

	private BrandsInfo brandInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private DuplicateBrandingValidations duplicateBrandingValidations;
	Response fetchDuplicateBrandApiResponse, createClientApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandInfo = new BrandsInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		duplicateBrandingValidations = new DuplicateBrandingValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another existing client")
	public void testDuplicateBrandToClient() throws IOException {
		String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		String clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20))));
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("New client created with client id : " + clientId);
		logStep("Duplicating Brand to created client");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id.one")), clientId, testData.getProperty("owner.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another existing Org")
	public void testDuplicateBrandToOrg() throws IOException {
		logStep("Duplicating Brand to existing Org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id")),
				Integer.parseInt(testData.getProperty("owner.id.org")), testData.getProperty("owner.type.org"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter non-existing Brand Id when duplicating brand")
	public void testErrorMsgWhenEnterNonExistingBrandId() throws IOException {
		logStep("Duplicating non-existing brand to org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("non.existing.brand.id")),
				Integer.parseInt(testData.getProperty("owner.id.org")), testData.getProperty("owner.type.org"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(fetchDuplicateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter non-existing owner id when duplicating brand to Org")
	public void testErrorMsgWhenEnterNonExistingOwnerIdOfOrg() throws IOException {
		logStep("Duplicating brand to non-existing org id");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id")),
				Integer.parseInt(testData.getProperty("non.existing.owner.id")),
				testData.getProperty("owner.type.org"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(
				fetchDuplicateBrandApiResponse, testData.getProperty("non.existing.owner.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter non-existing owner id when duplicating brand to Client")
	public void testErrorMsgWhenEnterNonExistingOwnerIdOfClient() throws IOException {
		logStep("Duplicating brand to non-existing client id");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id")),
				Integer.parseInt(testData.getProperty("non.existing.owner.id")), testData.getProperty("owner.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfClient(
				fetchDuplicateBrandApiResponse, testData.getProperty("non.existing.owner.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter invalid owner type while duplicating brand")
	public void testErrorMsgWhenEnterInvalidownerType() throws IOException {
		logStep("Duplicating brand to invalid owner type neither client nor org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id.one")),
				Integer.parseInt(testData.getProperty("owner.id.org")), testData.getProperty("invalid.owner.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForInvalidOwnerType(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter null owner type while duplicating brand")
	public void testErrorMsgWhenEnterNullownerType() throws IOException {
		logStep("Duplicating brand to owner type value as null");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id.one")),
				Integer.parseInt(testData.getProperty("owner.id.org")), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNullOwnerType(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter null owner Id while duplicating brand")
	public void testErrorMsgWhenEnterNullownerId() throws IOException {
		logStep("Duplicating brand to owner type value as either client or org but owner id as null");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id.one")), 0, testData.getProperty("owner.type.org"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNullOwnerId(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when duplicating brand with invalid url")
	public void testErrorMsgForInvalidUrl() throws IOException {
		logStep("Duplicating brand to org with invalid url");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandInvalidUrl(
				Integer.parseInt(testData.getProperty("brand.id.one")), 0, testData.getProperty("owner.type.org"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when duplicating brand with incorrect HTTP method")
	public void testErrorMsgForInvalidMethodCall() throws IOException {
		logStep("Duplicating brand to org with invalid HTTP method call as PUT");
		fetchDuplicateBrandApiResponse = brandInfo.duplicateBrandInvalidMethodCall(
				Integer.parseInt(testData.getProperty("brand.id.one")),
				Integer.parseInt(testData.getProperty("owner.id.org")), testData.getProperty("owner.type.org"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethodCall(fetchDuplicateBrandApiResponse);
	}
}
