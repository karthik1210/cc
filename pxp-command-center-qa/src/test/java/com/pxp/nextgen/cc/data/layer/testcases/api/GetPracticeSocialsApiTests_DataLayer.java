// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.PracticeSocials;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class GetPracticeSocialsApiTests_DataLayer extends BaseRest {
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private Validations validations;
	Response addUpdatePracticeSocialsApiResponse, getPracticeSocialApiResponse, createClientApiResponse,
			createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
	PracticeSocials practiceSocialsObj;
	ArrayList<PracticeSocials> practiceSocialsReqList = new ArrayList<PracticeSocials>();

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
	}

	@DataProvider(name = "invalidPracticeId")
	public Object[][] invalidPracticeId() throws Exception {
		return new Object[][] { { testData.getProperty("Non.existing.practice.id").trim() },
				{ testData.getProperty("api.client.id").trim() }, { testData.getProperty("api.system.id").trim() },
				{ testData.getProperty("api.enterprise.id").trim() } };
	}

	@DataProvider(name = "nullandStringPracticeId")
	public Object[][] nullandStringPracticeId() throws Exception {
		return new Object[][] { { testData.getProperty("PXPP.18282.api.practice.id") + "Sample" }, { null }, { "" } };
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19339_Add and get multiple socials to given practiceId")
	public void getMultiplePracticeSocialsApiSuccess() throws IOException {
		logStep("TC 001_verify response code 200 and response body when user enters valid practice Id to fetch practice socials which are linked by using GET Method for [DL]");
		int orgId = createPractice();
		logStep("Add a SocialId for a given orgId");
		LinkedHashSet<String> socialHref = new LinkedHashSet<String>();
		socialHref.add(testData.getProperty("api.practice.socials.twitter") + orgId);
		socialHref.add(testData.getProperty("api.practice.socials.facebook") + orgId);
		socialHref.add(testData.getProperty("api.practice.socials.linkedIn") + orgId);
		socialHref.add(testData.getProperty("api.practice.socials.youTube") + orgId);
		ArrayList<String> listSocialHref = new ArrayList<String>(socialHref);
		practiceSocialsReqList.clear();
		for (int i = 0; i < listSocialHref.size(); i++) {
			practiceSocialsObj = new PracticeSocials(i + 1, listSocialHref.get(i), true);
			practiceSocialsReqList.add(practiceSocialsObj);
		}
		addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("Valid", "DL", orgId,
				practiceSocialsReqList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
		CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 200);
		logStep("Getting SocialId information for a given orgId");
		getPracticeSocialApiResponse = orgInfo.getPracticeSocialsResponse("Valid", "DL", orgId);
		for (int i = 0; i < listSocialHref.size(); i++) {
			int socialId = getPracticeSocialApiResponse.jsonPath().get("socialId[" + i + "]");
			String href = getPracticeSocialApiResponse.jsonPath().get("href[" + i + "]");
			if (socialHref.contains(href)) {
				logStep(href + " Socials fetched successfully for " + orgId);
			}
		}
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19359_No content error for given practiceId without socials")
	public void getPracticeSocialsWithOutSocialsApi() throws IOException {
		logStep("TC 002_verify response code 204 and error message when user enters valid practice Id which doesn't link any socials by using GET Method [DL]");
		int orgId = createPractice();
		logStep("Getting SocialId information for a given orgId");
		getPracticeSocialApiResponse = orgInfo.getPracticeSocialsResponse("Valid", "DL", orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeSocialApiResponse);
		CCUtils.assertEquals(getPracticeSocialApiResponse.getStatusCode(), 204);
	}

	@Test(priority = 3, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19378_Get Practice social for Invalid PracticeId")
	public void getPracticeSocialsWithInvalidPracticeApi(String practiceId) throws IOException {
		logStep("TC 003_verify response code 204 and error message when user enters Invalid practice Id by using GET Method [DL]");
		logStep("Getting SocialId information for a given orgId");
		getPracticeSocialApiResponse = orgInfo.getPracticeSocialsInvaliDatatypeResponse("DL", practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeSocialApiResponse);
		CCUtils.assertEquals(getPracticeSocialApiResponse.getStatusCode(), 204);
	}

	@Test(priority = 4, dataProvider = "nullandStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19414_PXPP-19415_Get Practice social for null and Invalid Datatype PracticeId")
	public void getPracticeSocialsWithInvalidDatatypePracticeApi(String practiceId) throws IOException {
		logStep("TC 004_TC 005_verify response code 400 and error message when user enters null and Invalid Datatype value on practice Id by using GET Method [DL]");
		logStep("Getting SocialId information for a given orgId");
		getPracticeSocialApiResponse = orgInfo.getPracticeSocialsInvaliDatatypeResponse("DL", practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeSocialApiResponse);
		CCUtils.assertEquals(getPracticeSocialApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19417Get social to given practiceId with Invalid URI Type")
	public void getPracticeSocialsWithInvalidUriApi() throws IOException {
		logStep("TC 007_verify response code 404 and error message when user enters valid practice Id with Invalid URI by using GET Method  [BL]");
		int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
		logStep("Getting SocialId information for a given orgId");
		getPracticeSocialApiResponse = orgInfo.getPracticeSocialsResponse("InValid", "DL", orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeSocialApiResponse);
		CCUtils.assertEquals(getPracticeSocialApiResponse.getStatusCode(), 404);
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19418_Get social to given practiceId with Invalid Mathod Type")
	public void getPracticeSocialsWithInvalidMethodApi() throws IOException {
		logStep("TC 008_verify response code 405 and error message when user enters valid practice Id with Invalid DELETE method [DL]");
		int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
		logStep("Getting SocialId information for a given orgId");
		getPracticeSocialApiResponse = orgInfo.getPracticeSocialsResponse("InValidMethodType", "DL", orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeSocialApiResponse);
		CCUtils.assertEquals(getPracticeSocialApiResponse.getStatusCode(), 405);
	}

	@Test(enabled = false)
	public int createPractice() throws IOException {
		logStep("Create nge practice below the client with valid details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validations.verifyCreatePracticeResponseBody(createPracticeApiResponse,
				testData.getProperty("api.practice.name"));
		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		return orgId;
	}

}
