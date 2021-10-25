// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ApplyToInheritFrom;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.ApplyToInheritFromValidations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class ApplyToInheritFromApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private ApplyToInheritFrom applytoInheritFrom;
	Response createClientApiResponse, fetchClientDetailsApiResponse;
	private ApplyToInheritFromValidations applyToInheritFromValidations;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		applytoInheritFrom = new ApplyToInheritFrom();
		applyToInheritFromValidations = new ApplyToInheritFromValidations();
	}

	public String createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		logStep("Create a client with name: " + clientName);
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response for create client");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		log("Fetching Client Id from Response");
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		log("Client :" + clientName + " is created of client Id : " + clientId);
		return clientId;
	}

	public String createPracticeWithinClient(String clientId, List<Integer> solutionsList) throws IOException {
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
		logStep("Validating Response Code for practice created");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		String practiceId = createPracticeApiResponse.jsonPath().get("id").toString();
		log("Created Practice Id is : " + practiceId);
		return practiceId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new client within that create two practices with solutions use Apply to/Inherit from functionality from Prac1 to Prac2.")
	public void testApplyToInheritFromSolutionsOfCurrentPracticeToAnotherPractice() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String practiceOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		log("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ practiceOneId);
		List<Integer> pracTwoSolutionList = Arrays.asList(1103, 1104);
		String PracticeTwoId = createPracticeWithinClient(clientId, pracTwoSolutionList);
		log("Print the Id of created 2nd Practice within client :" + clientId + " and Practice Id is "
				+ PracticeTwoId);
		List<Integer> sourcePrac = new ArrayList<Integer>();
		sourcePrac.add(Integer.parseInt(practiceOneId));
		List<Integer> targetPrac = new ArrayList<Integer>();
		targetPrac.add(Integer.parseInt(PracticeTwoId));
		logStep("Applying solutions of source parctice to target practice");
		Response applySolutions = applytoInheritFrom.applyInheritSolutions(sourcePrac, targetPrac);
		log("Print the Response : " + applySolutions);
		log("Validating Response Code");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 200);
		Response getOrdIdInfo = orgInfo.getOrgId(Integer.parseInt(PracticeTwoId));
		log("Print response of Target Practice Org" + getOrdIdInfo);
		CCUtils.assertEquals(getOrdIdInfo, practiceOneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate Apply to functionality from Prac1 to multiple Practices i.e., Prac2 & Prac3")
	public void testApplyToSolutionsOfCurrentPracticeToAnotherPractices() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String practiceOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		log("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ practiceOneId);
		List<Integer> pracTwoSolutionList = Arrays.asList(1103, 1104);
		String PracticeTwoId = createPracticeWithinClient(clientId, pracTwoSolutionList);
		log("Print the Id of created 2nd Practice within client :" + clientId + " and Practice Id is "
				+ PracticeTwoId);
		List<Integer> pracThreeSolutionList = Arrays.asList(1105, 1401);
		String PracticeThreeId = createPracticeWithinClient(clientId, pracThreeSolutionList);
		log("Print the Id of created 3rd Practice within client :" + clientId + " and Practice Id is "
				+ PracticeThreeId);
		List<Integer> sourcePrac = new ArrayList<Integer>();
		sourcePrac.add(Integer.parseInt(practiceOneId));
		List<Integer> targetPrac = new ArrayList<Integer>();
		targetPrac.add(Integer.parseInt(PracticeTwoId));
		targetPrac.add(Integer.parseInt(PracticeThreeId));
		logStep("Inheriting solution from source practice");
		Response applySolutions = applytoInheritFrom.applyInheritSolutions(sourcePrac, targetPrac);
		log("Print the Response : " + applySolutions);
		logStep("Validating Response Code and checking target practice");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 200);
		Response getOrdIdInfo = orgInfo.getOrgId(Integer.parseInt(PracticeTwoId));
		applyToInheritFromValidations.fetchResponseOfGetOrg(getOrdIdInfo);
		log("Print response of Target Practice 1 Org : " + getOrdIdInfo);
		Response getOrdId_Info = orgInfo.getOrgId(Integer.parseInt(PracticeThreeId));
		log("Print response of Target Practice 2 Org" + getOrdId_Info);
		applyToInheritFromValidations.fetchResponseOfGetOrg(getOrdId_Info);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate Inherit From  practices functionality from Prac1 & Prac3 to Prac2.")
	public void testInheritFromSolutionsOfCurrentPracticeToAnotherPractices() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		logStep("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ PracticeOneId);
		List<Integer> pracTwoSolutionList = Arrays.asList(1103, 1104);
		String PracticeTwoId = createPracticeWithinClient(clientId, pracTwoSolutionList);
		logStep("Print the Id of created 2nd Practice within client :" + clientId + " and Practice Id is "
				+ PracticeTwoId);
		List<Integer> pracThreeSolutionList = Arrays.asList(1105, 1401);
		String PracticeThreeId = createPracticeWithinClient(clientId, pracThreeSolutionList);
		logStep("Print the Id of created 3rd Practice within client :" + clientId + " and Practice Id is "
				+ PracticeThreeId);
		List<Integer> sourcePrac = new ArrayList<Integer>();
		sourcePrac.add(Integer.parseInt(PracticeOneId));
		sourcePrac.add(Integer.parseInt(PracticeThreeId));
		List<Integer> targetPrac = new ArrayList<Integer>();
		targetPrac.add(Integer.parseInt(PracticeTwoId));
		Response applySolutions = applytoInheritFrom.applyInheritSolutions(sourcePrac, targetPrac);
		logStep("Print the Response : " + applySolutions);
		logStep("Validating Response Code");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 200);
		Response getOrdIdInfo = orgInfo.getOrgId(Integer.parseInt(PracticeTwoId));
		CCUtils.assertEquals(getOrdIdInfo, PracticeTwoId);
		logStep("Print response of Target Practice 1 Org : " + getOrdIdInfo);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating while Applying solutions of Non-Existing Practice to Existing Practice throws error.")
	public void testErrorApplySolutionsOfNonExistingPracticeToAnotherPractice() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		String nonExistingPractice = "8080";
		logStep("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ PracticeOneId);
		List<Integer> sourcePrac = new ArrayList<Integer>();
		sourcePrac.add(Integer.parseInt(nonExistingPractice));
		List<Integer> targetPrac = new ArrayList<Integer>();
		targetPrac.add(Integer.parseInt(PracticeOneId));
		log("Now will Apply solutions of Non-Existing Practice Id : 8080 to Existing Practice Id : " + PracticeOneId);
		Response applySolutions = applytoInheritFrom.applyInheritSolutions(sourcePrac, targetPrac);
		logStep("Print the Response : " + applySolutions);
		logStep("Validating Response Code");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 400);
		applyToInheritFromValidations.validateNonExistingSourcePractice(applySolutions, nonExistingPractice);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating while Applying solutions both Source Practice Id and Target Practice Id are same throws error.")
	public void testErrorApplySolutionsToSamePracticesSourceIdAndTargetIdIsSame() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		log("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ PracticeOneId);
		List<Integer> sourcePrac = new ArrayList<Integer>();
		sourcePrac.add(Integer.parseInt(PracticeOneId));
		List<Integer> targetPrac = new ArrayList<Integer>();
		targetPrac.add(Integer.parseInt(PracticeOneId));
		log("Now will Apply solutions of Practice Id : " + PracticeOneId + " to Practice Id : " + PracticeOneId);
		Response applySolutions = applytoInheritFrom.applyInheritSolutions(sourcePrac, targetPrac);
		logStep("Print the Response : " + applySolutions);
		logStep("Validating Response Code");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 400);
		applyToInheritFromValidations.validateSourcePracIdTargetPracIdSameErrorMsg(applySolutions);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating while Applying solutions both Source Practice Id and Target Practice Id are same throws error.")
	public void testErrorApplySolutionsWhereSourceIdIsNull() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		log("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ PracticeOneId);
		List<Integer> targetPrac = new ArrayList<Integer>();
		targetPrac.add(Integer.parseInt(PracticeOneId));
		log("Now will Apply solutions of empty/null Practice Id : '' to Existing Practice Id : " + PracticeOneId);
		Response applySolutions = applytoInheritFrom.applySolutionsWhereSourceIDNull(targetPrac);
		logStep("Print the Response : " + applySolutions);
		logStep("Validating Response Code");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 400);
		applyToInheritFromValidations.validateSourcePracIdNullSameErrorMsg(applySolutions);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating while Applying solutions both Source Practice Id and Target Practice Id are same throws error.")
	public void testErrorApplySolutionsWhereTargetIdIsNull() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		log("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ PracticeOneId);
		List<Integer> sourcePrac = new ArrayList<Integer>();
		sourcePrac.add(Integer.parseInt(PracticeOneId));
		log("Now will Apply solutions of empty/null Practice Id : '' to Existing Practice Id : " + PracticeOneId);
		Response applySolutions = applytoInheritFrom.applySolutionsWhereTargetIDNull(sourcePrac);
		log("Print the Response : " + applySolutions);
		logStep("Validating Response Code");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 400);
		applyToInheritFromValidations.validateTargetPracIdNullSameErrorMsg(applySolutions);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating while Applying solutions both Source Practice Id and Target Practice Id are same throws error.")
	public void testErrorApplyInheritSolutionsWhereTargetIdIsNonExisting() throws IOException {
		logStep("Creating a new client");
		String clientId = createClient();
		List<Integer> pracOneSolutionList = Arrays.asList(1101, 1102);
		String PracticeOneId = createPracticeWithinClient(clientId, pracOneSolutionList);
		String nonExistingPractice = "8080";
		log("Print the Id of created 1st Practice within client :" + clientId + " and Practice Id is "
				+ PracticeOneId);
		List<Integer> sourcePrac = new ArrayList<Integer>();
		sourcePrac.add(Integer.parseInt(PracticeOneId));
		List<Integer> targetPrac = new ArrayList<Integer>();
		targetPrac.add(Integer.parseInt(nonExistingPractice));
		logStep("Applying solutions of Existing Practice Id : " + PracticeOneId
				+ "to Non-Existing Practice Id : 8080");
		Response applySolutions = applytoInheritFrom.applyInheritSolutions(sourcePrac, targetPrac);
		log("Print the Response : " + applySolutions);
		logStep("Validating Response Code");
		CCUtils.assertEquals(applySolutions.getStatusCode(), 400);
		applyToInheritFromValidations.validateNonExistingTargetPractice(applySolutions, nonExistingPractice);
	}
}