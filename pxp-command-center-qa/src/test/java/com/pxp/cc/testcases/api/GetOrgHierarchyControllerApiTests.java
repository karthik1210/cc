package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class GetOrgHierarchyControllerApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, getOrgHierarchyControllerApiResponse;
	List<Integer> solutionList = null;
//	private OrgInfo orgInfo;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
//		orgInfo = new OrgInfo();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details excluding client data")
	public void getOrgHierarchyWithClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
		
		logStep("Update client status from active to inactive");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(clientId, false);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 200);	
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details including client data")
	public void getOrgHierarchyWithoutClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
		
		logStep("Update client status from active to inactive");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(clientId, true);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 200);	
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details when client id is null")
	public void testErrorMessageIfClientIdIsNull() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
		
		logStep("Update client status from active to inactive");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(null, true);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 400);	
		validation.verifyErrorMessageIfClientIdIsNull(getOrgHierarchyControllerApiResponse);		
	}	
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details including client data")
	public void getOrgHierarchynullClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
		
		logStep("Update client status from active to inactive");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(clientId, null);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 200);	
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details excluding client data")
	public void getOrgHierarchyWithInvalidClientId() throws IOException {
		logStep("Update client status from active to inactive");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(ccUser.getNonExistingClientId(), false);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 200);	
	}

}
