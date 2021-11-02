package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.UpdateProductStatus;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.UpdateOrgProductStatusValidations;

import io.restassured.response.Response;

public class UpdateOrgProductStatusApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createClientApiResponse;
	private UpdateOrgProductStatusValidations updateOrgProductStatusValidations;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
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

	@DataProvider(name = "typesofstatus")
	public Object[] searchClientDetailsWithoutFilter() {
		Object[] obj = new Object[] { "Active Implementation", "Go-live", "On Hold", "Terminated" };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "typesofstatus", description = "Update product status of practice within client.")
	public void testUpdateExistingProductStatusOfPractices(String getTypesOfProductStatus) throws IOException {
		logStep("Creating a client.");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWitinClient(clientId, pracOneSolutionList);
		logStep("Created a practice with PracId : " + PracticeOneId + " within client with client ID : " + clientId);
		Response getOrgIdInfo = orgInfo.getOrgId(Integer.parseInt(PracticeOneId));
		logStep("Print response of Target Practice Org" + getOrgIdInfo);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
		String activeValue = updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrgIdInfo);
		logStep("Fetch and store active value : " + activeValue);
		String sapNumber = updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store  sapNumber value : " + sapNumber);
		int orgId = Integer
				.parseInt(updateOrgProductStatusValidations.fetchOrgIdKeyValueFromResponseBody(getOrgIdInfo));
		log("Fetch and store orgId value : " + orgId);
		String timezoneId = updateOrgProductStatusValidations.fetchTimezoneIdKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store timezone value : " + timezoneId);
		String displayName = updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store Display name value : " + displayName);
		ArrayList<HashMap<String, Object>> solutionDetails = updateOrgProductStatusValidations
				.fetchSolutionsListInGetOrgResponseBody(getOrgIdInfo);
		List<String> listOfProductId = updateOrgProductStatusValidations.fetchProductsfromGetOrgId(solutionDetails);
		List<UpdateProductStatus> listOfUpdatedSolutions = updateOrgProductStatusValidations
				.fetchUpdatedSolutionsList(listOfProductId, getTypesOfProductStatus);
		log("Fetch and store Display solutionStatusList value : " + listOfUpdatedSolutions);
		Response updateProductStatusResponse = orgInfo.updatePracticeWithSolutions(activeValue, displayName, orgId,
				sapNumber, listOfUpdatedSolutions, timezoneId);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateProductStatusResponse.getStatusCode(), 200);
		logStep("Validating Response Body that status is updated to " + getTypesOfProductStatus);
		Response getOrgIdInfoAfterUpdate = orgInfo.getOrgId(Integer.parseInt(PracticeOneId));
		updateOrgProductStatusValidations.validateSolutionsListInGetOrgResponseBody(getOrgIdInfoAfterUpdate,
				getTypesOfProductStatus);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "typesofstatus", description = "Org Id can not be zero error message.")
	public void testErrorOrgIdOfPracticeIsZero(String getTypesOfProductStatus) throws IOException {
		logStep("Creating a client.");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1103, 1104);
		String PracticeOneId = createPracticeWitinClient(clientId, pracOneSolutionList);
		logStep("Created a practice with PracId : " + PracticeOneId + " within client with client ID : " + clientId);
		Response getOrgIdInfo = orgInfo.getOrgId(Integer.parseInt(PracticeOneId));
		logStep("Print response of Target Practice Org" + getOrgIdInfo);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
		String activeValue = updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrgIdInfo);
		logStep("Fetch and store active value : " + activeValue);
		String sapNumber = updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store  sapNumber value : " + sapNumber);
		String timezoneId = updateOrgProductStatusValidations.fetchTimezoneIdKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store timezone value : " + timezoneId);
		String displayName = updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store Display name value : " + displayName);
		ArrayList<HashMap<String, Object>> solutionDetails = updateOrgProductStatusValidations
				.fetchSolutionsListInGetOrgResponseBody(getOrgIdInfo);
		List<String> listOfProductId = updateOrgProductStatusValidations.fetchProductsfromGetOrgId(solutionDetails);
		List<UpdateProductStatus> listOfUpdatedSolutions = updateOrgProductStatusValidations
				.fetchUpdatedSolutionsList(listOfProductId, getTypesOfProductStatus);
		log("Fetch and store Display solutionStatusList value : " + listOfUpdatedSolutions);
		Response updateProductStatusResponse = orgInfo.updatePracticeWithSolutions(activeValue, displayName, 0,
				sapNumber, listOfUpdatedSolutions, timezoneId);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateProductStatusResponse.getStatusCode(), 400);
		logStep("Validating Error Message for Org Id = 0 in response body");
		updateOrgProductStatusValidations.validateOrgIdZeroErrorMessage(updateProductStatusResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "typesofstatus", description = "Test Timezone Id does not exist error message")
	public void testErrorTimezoneIdNotExisting(String getTypesOfProductStatus) throws IOException {
		logStep("Creating a client.");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1103, 1104);
		String PracticeOneId = createPracticeWitinClient(clientId, pracOneSolutionList);
		logStep("Created a practice with PracId : " + PracticeOneId + " within client with client ID : " + clientId);
		Response getOrgIdInfo = orgInfo.getOrgId(Integer.parseInt(PracticeOneId));
		logStep("Print response of Target Practice Org" + getOrgIdInfo);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
		String activeValue = updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrgIdInfo);
		logStep("Fetch and store active value : " + activeValue);
		String sapNumber = updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store  sapNumber value : " + sapNumber);
		int orgId = Integer
				.parseInt(updateOrgProductStatusValidations.fetchOrgIdKeyValueFromResponseBody(getOrgIdInfo));
		log("Fetch and store orgId value : " + orgId);
		String displayName = updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrgIdInfo);
		log("Fetch and store Display name value : " + displayName);
		ArrayList<HashMap<String, Object>> solutionDetails = updateOrgProductStatusValidations
				.fetchSolutionsListInGetOrgResponseBody(getOrgIdInfo);
		List<String> listOfProductId = updateOrgProductStatusValidations.fetchProductsfromGetOrgId(solutionDetails);
		List<UpdateProductStatus> listOfUpdatedSolutions = updateOrgProductStatusValidations
				.fetchUpdatedSolutionsList(listOfProductId, getTypesOfProductStatus);
		log("Fetch and store Display solutionStatusList value : " + listOfUpdatedSolutions);
		Response updateProductStatusResponse = orgInfo.updatePracticeWithSolutions(activeValue, displayName, orgId,
				sapNumber, listOfUpdatedSolutions, "Non-Existing");
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateProductStatusResponse.getStatusCode(), 400);
		logStep("Validating Error Message for TimezoneId doesnot exist in response body");
		updateOrgProductStatusValidations.validateTimeZoneIdDoesnotExistErrorMessage(updateProductStatusResponse,
				"Non-Existing");
	}
}
