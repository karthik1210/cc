// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

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

import io.restassured.response.Response;

public class GetOrgPracticesProductsCount extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAgnosticPracticeApiResponse;
	List<Integer> solutionList = null;
	public int clientId = 0;
	public int systemOneId = 0;
	public int systemTwoId = 0;
	public int entpriseOneId = 0;
	public int entpriseTwoId = 0;
	public int practiceOneId = 0;
	public int practiceTwoId = 0;
	public int practiceThreeId = 0;
	public int inactivePracticeFourId = 0;
	public int agnosticPracticeId = 0;
	List<Integer> pracSolutionList = Arrays.asList(1101);

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		logStep("creating Agnostic practice node within client");
		clientId = createClient();
		systemOneId = createSystemwithinClient(clientId);
		entpriseOneId = createEnterprisewithinSystem(systemOneId);
		practiceOneId = createPracticeWitinEnterprise(entpriseOneId,Arrays.asList(1102),true);
		practiceTwoId = createPracticeWitinEnterprise(entpriseOneId,Arrays.asList(1103),true);
		entpriseTwoId = createEnterprisewithinSystem(systemTwoId);
		practiceThreeId = createPracticeWitinEnterprise(entpriseOneId,Arrays.asList(1104,1102),true);
		inactivePracticeFourId = createPracticeWitinEnterprise(entpriseOneId,Arrays.asList(1105,1102),false);
		agnosticPracticeId = createAgnosticPracticeWitinClient(clientId, pracSolutionList);
	}

	public int createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		logStep("Create a client of name : " + clientName);
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		return createClientApiResponse.jsonPath().getInt("id");
	}

	public int createSystemwithinClient(int clientID) throws IOException {
		int clientId = clientID;
		logStep("Create a new active system below active client of id : " + clientId);
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(),
				ccUser.getSystemDisplayNameApi().concat(CCUtils.generateRandomString()),
				ccUser.getSystemNameApi().concat(CCUtils.generateRandomString()), ccUser.getSystemOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		return createSystemApiResponse.jsonPath().getInt("id");
	}

	public int createEnterprisewithinSystem(int systemID) throws IOException {
		int systemId = systemID;
		logStep("Create a new active enterprise below active system with id : " + systemId);
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseName, ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber, ccUser.getEnterpriseTimezoneIdApi());
		return createEnterpriseApiResponse.jsonPath().getInt("id");
	}

	public int createPracticeWitinEnterprise(int enterpriseId, List<Integer> solutionsList, boolean isActive)
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
				practiceName, ccUser.getPracticeOrgTypeApi(), enterpriseId, ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);
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
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "")
	public void test() throws IOException {
		Response getEnterpriseOneIdInfo = orgInfo.getOrgId(entpriseOneId);
		//updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
	}
}
