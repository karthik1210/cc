package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.UpdateOrgProductStatusValidations;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class UpdateOrgProductStatusApiTests extends BaseRest{

	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	Response createClientApiResponse, fetchClientDetailsApiResponse, updateClientApiResponse;
	private UpdateOrgProductStatusValidations updateOrgProductStatusValidations;
	List<Integer> solutionList = null;
	
	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		updateOrgProductStatusValidations = new UpdateOrgProductStatusValidations();
	}
	
	public String createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		logStep("Create a client of name : " + clientName);
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time and Response Code");
		//CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Fetching Client Id from Response");
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		logStep("Client :" + clientName + " is created of client Id : " + clientId);
		return clientId;
	}
	

	public String createPracticeWitinClient(String clientId, List<Integer> solutionsList) throws IOException {
		String clientID = clientId;
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		solutionList = new ArrayList<Integer>();
		for (int solution : solutionsList) {
			solutionList.add(solution);
			logStep("Adding solutions ID : " + solution + " within practice node : " + practiceName);
		}
		Response createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientID), ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		String practiceId = createPracticeApiResponse.jsonPath().get("id").toString();
		logStep("Created Practice Id is : " + practiceId);
		return practiceId;
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update product status of practices within client.")
	public void testUpdateExistingProductStatusOfPractices() throws IOException {
		logStep("Creating a client.");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWitinClient(clientId, pracOneSolutionList);
		logStep("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ PracticeOneId);
		Response getOrdIdInfo = orgInfo.getOrgId(Integer.parseInt(PracticeOneId));
		logStep("Print response of Target Practice Org" + getOrdIdInfo);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrdIdInfo);
		String activeValue = updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrdIdInfo);
		log("Print active value : "+activeValue); 
		String sapNumber = updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrdIdInfo);
		log("Print sapNumber value : "+sapNumber); 
		String orgId = updateOrgProductStatusValidations.fetchOrgIdKeyValueFromResponseBody(getOrdIdInfo);
		log("Print orgId value : "+orgId);
		String timezoneId = updateOrgProductStatusValidations.fetchTimezoneIdKeyValueFromResponseBody(getOrdIdInfo);
		log("Print timezone value : "+timezoneId);
		String displayName = updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrdIdInfo);
		log("Print Display name value : "+displayName);
		updateOrgProductStatusValidations.fetchSolutionsListInGetOrgResponseBody(getOrdIdInfo);
	}
}
