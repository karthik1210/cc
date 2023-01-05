// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.UpdateOrgProductStatusValidations;
import io.restassured.response.Response;

public class GetOrgPracticesProductsCount extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private UpdateOrgProductStatusValidations updateOrgProductStatusValidations;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAgnosticPracticeApiResponse;
	List<Integer> solutionList = null;
	public int clientId, systemOneId, entpriseOneId, entpriseTwoId, practiceOneId, practiceTwoId, practiceThreeId,
			inactivePracticeFourId, agnosticPracticeId = 0;
	List<Integer> pracSolutionList = Arrays.asList(1101);
	List<Integer> practiceOneSolutionList = Arrays.asList(1102);
	List<Integer> practiceTwoSolutionList = Arrays.asList(1103, 1102);
	List<Integer> practiceThreeSolutionList = Arrays.asList(1104, 1102);
	List<Integer> practiceFourSolutionList = Arrays.asList(1105, 1102);

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		logStep("creating Agnostic practice node within client");
		clientId = createClient();
		updateOrgProductStatusValidations = new UpdateOrgProductStatusValidations();
		systemOneId = createSystemwithinClient(clientId);
		entpriseOneId = createEnterprisewithinSystem(systemOneId);
		practiceOneId = createPracticeWithinEnterprise(entpriseOneId, practiceOneSolutionList, "true");
		practiceTwoId = createPracticeWithinEnterprise(entpriseOneId, practiceTwoSolutionList, "true");
		log("In enterpriseOne with Id " + entpriseOneId + " two practices added with prac one id : " + practiceOneId
				+ " and prac two id :" + practiceTwoId);
		entpriseTwoId = createEnterprisewithinSystem(systemOneId);
		practiceThreeId = createPracticeWithinEnterprise(entpriseTwoId, practiceThreeSolutionList, "true");
		inactivePracticeFourId = createPracticeWithinEnterprise(entpriseTwoId, practiceFourSolutionList, "false");
		log("In enterpriseTwo with Id " + entpriseTwoId + " two practices added with prac one id : " + practiceThreeId
				+ " and inactive prac two id :" + inactivePracticeFourId);
		agnosticPracticeId = createAgnosticPracticeWitinClient(clientId, pracSolutionList);
	}

	public int createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		logStep("Create a client of name : " + clientName);
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createClientApiResponse.jsonPath().getInt("id");
	}

	public int createSystemwithinClient(int parentId) throws IOException {
		logStep("Create a new active system below active client of id : " + clientId);
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(),
				ccUser.getSystemDisplayNameApi().concat(CCUtils.generateRandomString()),
				ccUser.getSystemNameApi().concat(CCUtils.generateRandomString()), ccUser.getSystemOrgTypeApi(),
				parentId, ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		return createSystemApiResponse.jsonPath().getInt("id");
	}

	public int createEnterprisewithinSystem(int systemId) throws IOException {
		logStep("Create a new active enterprise below active system with id : " + systemId);
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseName, ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber, ccUser.getEnterpriseTimezoneIdApi());
		return createEnterpriseApiResponse.jsonPath().getInt("id");
	}

	public int createPracticeWithinEnterprise(int enterpriseId, List<Integer> solutionsList, String isActive)
			throws IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		solutionList = new ArrayList<Integer>();
		for (int solution : solutionsList) {
			solutionList.add(solution);
			logStep("Adding solutions ID : " + solution + " within practice node : " + practiceName);
		}
		logStep("Create a practice within enterprise of Id : " + enterpriseId);
		createPracticeApiResponse = orgInfo.createPracticeWithSolutions(isActive, ccUser.getPracticeDisplayNameApi(),
				practiceName, ccUser.getPracticeOrgTypeApi(), enterpriseId, ccUser.getEnterpriseOrgTypeApi(),
				practiceSapNumber, ccUser.getPracticeTimezoneIdApi(), solutionList);
		return createPracticeApiResponse.jsonPath().getInt("id");
	}

	public int createAgnosticPracticeWitinClient(int clientId, List<Integer> solutionsList) throws IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		solutionList = new ArrayList<Integer>();
		for (int solution : solutionsList) {
			solutionList.add(solution);
			logStep("Adding solutions ID : " + solution + " within practice node : " + practiceName);
		}
		logStep("Create a practice within client of Id : " + clientId);
		createAgnosticPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(), clientId,
				ccUser.getParentTypeApi(), practiceSapNumber, ccUser.getPracticeTimezoneIdApi(), solutionList);
		return createAgnosticPracticeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test the Enterprise One level number of Practice count and number of Products count")
	public void testEnterpriseOneProductsAndActiveCount() throws IOException {
		List<Integer> solutionList = new ArrayList<Integer>();
		solutionList.addAll(practiceOneSolutionList);
		solutionList.addAll(practiceTwoSolutionList);
		Response getEnterpriseOneIdInfo = orgInfo.getOrgId(entpriseOneId);
		updateOrgProductStatusValidations.validateEnterpriseOrgTypeInResponseBody(getEnterpriseOneIdInfo);
		updateOrgProductStatusValidations.validatePracticeCount(getEnterpriseOneIdInfo, 2);
		logStep("Validating for Product ID 1102,1103 present within enterpriseOne and its active count validation");
		updateOrgProductStatusValidations.validateProductActiveCountInGetOrgResponseBody(getEnterpriseOneIdInfo,
				solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test the Enterprise Two level number of Practice count and number of Products count")
	public void testEnterpriseTwoProductsAndActiveCount() throws IOException {
		List<Integer> solutionList = new ArrayList<Integer>();
		solutionList.addAll(practiceThreeSolutionList);
		solutionList.addAll(practiceFourSolutionList);
		Response getEnterpriseTwoIdInfo = orgInfo.getOrgId(entpriseTwoId);
		updateOrgProductStatusValidations.validateEnterpriseOrgTypeInResponseBody(getEnterpriseTwoIdInfo);
		updateOrgProductStatusValidations.validatePracticeCount(getEnterpriseTwoIdInfo, 1);
		logStep("Validating for Product ID 1102,1104 present within enterpriseTwo and its active count validation.");
		updateOrgProductStatusValidations.validateProductActiveCountInGetOrgResponseBody(getEnterpriseTwoIdInfo,
				solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test the System level number of Practice count and number of Products count")
	public void testSystemProductsAndTheirActiveCount() throws IOException {
		List<Integer> solutionList = new ArrayList<Integer>();
		solutionList.addAll(practiceOneSolutionList);
		solutionList.addAll(practiceTwoSolutionList);
		solutionList.addAll(practiceThreeSolutionList);
		Response getSystemIdInfo = orgInfo.getOrgId(systemOneId);
		updateOrgProductStatusValidations.validateSystemOrgTypeInResponseBody(getSystemIdInfo);
		updateOrgProductStatusValidations.validatePracticeCount(getSystemIdInfo, 3);
		logStep("Validating for Product ID 1102,1103,1104 present within system and its active count validation.");
		updateOrgProductStatusValidations.validateProductActiveCountInGetOrgResponseBody(getSystemIdInfo, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test the Client level number of Practice count and number of Products count")
	public void testClientProductsAndTheirActiveCount() throws IOException {
		List<Integer> solutionList = new ArrayList<Integer>();
		solutionList.addAll(practiceOneSolutionList);
		solutionList.addAll(practiceTwoSolutionList);
		solutionList.addAll(practiceThreeSolutionList);
		solutionList.addAll(pracSolutionList);
		Response getClientIdInfo = clientInfo.getClientDetails(String.valueOf(clientId));
		updateOrgProductStatusValidations.validatePracticeCount(getClientIdInfo, 5);
		logStep("Validating for Product ID 1102,1103,1104 present within system and its active count validation.");
		updateOrgProductStatusValidations.validateProductActiveCountInGetOrgResponseBody(getClientIdInfo, solutionList);
	}
}
