// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.DisplayText;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.pojos.PracticeTouDetails;
import com.pxp.cc.pojos.TouSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class AddTouApiTests_DataLayer extends BaseRest {
	private OrgInfo orgInfo;
	private Validations validations;
	Response addTouApiResponse, getTouApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse,
			createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
	int practiceId;
	List<Integer> solutionList = null;
	private ClientInfo clientInfo;
	PracticeTouDetails practiceTouDetailsObj;
	TouSolutions touSolutionsObj;
	DisplayText addTouContentObj;
	String touType, touName;

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

	@DataProvider(name = "nullAndStringPracticeId")
	public Object[][] nullAndStringPracticeId() throws Exception {
		return new Object[][] { { testData.getProperty("api.practice.interface.practiceid") + "Sample",
				"PXPP-21243_TC_03_Verify the Response code as 400, when user try to give alphanumeric practice Id for Practice of privacy settings by using POST Request [BL]" },
				{ null, "PXPP-21244_TC_05_Verify the Response code as 400, when user try to give null practice Id for add the privacy settings by using POST Request [BL/DL]" },
				{ "", "Verify the Response Code 400, when user enter Empty practice Id" } };
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21242_Add Draft tou for practice")
	public void addDraftTouToPracticeSuccess() throws IOException {
		logStep("TC_01_Verify the Response code as 200, when user try to add Privacy settings by using POST Request [BL/DL]");
		practiceId = createPractice();
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		logStep("Create PayLoad for new tou creation to practice");
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 200);
		getTouApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
		validations.getTouIdValidation(getTouApiResponse, touName, practiceId);
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24199_Add Active tou for practice")
	public void addActiveTouToPracticeSuccess() throws IOException {
		logStep("TC_08_Verify the Response code as 200, when user try to add Privacy settings for Active Status by using POST Request [BL/DL]");
		practiceId = createPractice();
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		logStep("Create PayLoad for new tou creation to practice");
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Active");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 200);
		getTouApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
		validations.getTouIdValidation(getTouApiResponse, touName, practiceId);
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24218_Add Inactive tou for practice")
	public void addInactiveTouToPracticeSuccess() throws IOException {
		logStep("TC_09_Verify the Response code as 200, when user try to add Privacy settings for Inactive Status by using POST Request [BL/DL]");
		practiceId = createPractice();
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		logStep("Create PayLoad for new tou creation to practice");
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Inactive");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 200);
		getTouApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
		validations.getTouIdValidation(getTouApiResponse, touName, practiceId);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24219_Add multiple tous to given practice")
	public void addMultipleTousToPracticeSuccess() throws IOException {
		logStep("TC_10_Verify the Response code as 200, when user try to add multiple tous to same practiceId by using POST Request [BL/DL]");
		practiceId = createPractice();
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		logStep("Create PayLoad for new tou creation to practice");
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Create PayLoad for new tou creation to practice");
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName.concat("ActiveTou"), "Active");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Create PayLoad for new tou creation to practice");
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName.concat("InactiveTou"), "Inactive");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 200);
		getTouApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
		validations.getTouIdValidation(getTouApiResponse, touName, practiceId);
		validations.getTouIdValidation(getTouApiResponse, touName.concat("ActiveTou"), practiceId);
		validations.getTouIdValidation(getTouApiResponse, touName.concat("InactiveTou"), practiceId);

	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21241_Add tou for practice with Invalid URI")
	public void addTouToPracticeWithInvalidURI() throws IOException {
		logStep("TC_02_Verify the Response code as 404, when user try to give invalid URI for Practice of privacy settings by using POST Request[BL/DL]");
		practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = testData.getProperty("PXPP.18282.api.tou.name") + "InvalidUri";
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("InValid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 404);
	}

	@Test(priority = 5, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20046_PXPP-20056_Add tou for Alphanumeric/Null/Empty practice")
	public void addTouForAlphaNumericNullEmptyPractice(String practiceId, String testCaseName) throws IOException {
		logStep(testCaseName);
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		int payloadOrgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
		touName = testData.getProperty("PXPP.18282.api.tou.name") + "NullEmptyName";
		practiceTouDetailsObj = practiceTouPayload(payloadOrgId, touName, "Draft");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousInvaliDatatypeResponse("DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21240_Add tou for Invalid Method Type")
	public void addTouToPracticeInvalidMethodType() throws IOException {
		logStep("TC_04_Verify the Response code as 405, when user try to call PUT method instead of POST Request for practice of privacy settings [BL/DL]");
		practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = testData.getProperty("PXPP.18282.api.tou.name") + "InvalidMethod";
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("InValidMethod", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 405);
	}

	@Test(priority = 7, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21239_Add tou for Invalid Practice Id")
	public void addTouToInvalidPractice(String practiceId) throws IOException {
		logStep("TC_06_Verify the Response code as 400, when user try to give invalid practice id of privacy settings POST Request [BL/DL]");
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		int payloadOrgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
		touName = testData.getProperty("PXPP.18282.api.tou.name") + "InvalidPracticeId";
		practiceTouDetailsObj = practiceTouPayload(payloadOrgId, touName, "Draft");
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousInvaliDatatypeResponse("DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(addTouApiResponse, Integer.parseInt(practiceId));
	}

	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21245_Add tou for Invalid payload")
	public void addTouToInvalidPayload() throws IOException {
		logStep("TC_07_Verify the Response code as 400, when user try to give Valid practice Id and invalid payload of privacy settings by using POST Request [BL/DL]");
		practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
		String contentEng = testData.getProperty("PXPP.18282.api.content.eng");
		String contentSpan = testData.getProperty("PXPP.18282.api.content.span");
		addTouContentObj = new DisplayText(contentEng, contentSpan);
		ArrayList<TouSolutions> touSolutionList = new ArrayList<TouSolutions>();
		touSolutionList.clear();
		touSolutionsObj = new TouSolutions(null, testData.getProperty("PXPP.18282.api.sol.name"), true);
		touSolutionList.add(touSolutionsObj);
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		practiceTouDetailsObj = new PracticeTouDetails("touName123", "Draft", touSolutionList, touType,
				addTouContentObj);
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(addTouApiResponse);
		validations.verifyErrorResponseBodyForInvalidSolTou(addTouApiResponse);
		practiceTouDetailsObj = new PracticeTouDetails("", "Draft", touSolutionList, touType, addTouContentObj);
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 400);
		CCUtils.assertEquals(addTouApiResponse.jsonPath().get("message").toString(), "Name is required");
		practiceTouDetailsObj = new PracticeTouDetails("TestTou", "", touSolutionList, touType, addTouContentObj);
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Validating response Code");
		CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 400);
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
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
				new OrgSolutions(testData.getProperty("forms.id"),
						testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, orgId);
		return orgId;
	}

	@Test(enabled = false)
	public PracticeTouDetails practiceTouPayload(int practiceId, String touName, String touStatus) throws IOException {
		String contentEng = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
		String contentSpan = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
		addTouContentObj = new DisplayText(contentEng, contentSpan);
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId,
				testData.getProperty("get.org.type"));
		List<String> solutionIds = fetchOrgSolutionDetailsApiResponse.jsonPath().get("solutionId");
		ArrayList<TouSolutions> touSolutionList = new ArrayList<TouSolutions>();
		touSolutionList.clear();
		for (int i = 0; i < solutionIds.size(); i++) {
			int solutionId = fetchOrgSolutionDetailsApiResponse.jsonPath().get("solutionId[" + i + "]");
			String name = fetchOrgSolutionDetailsApiResponse.jsonPath().get("name[" + i + "]");
			boolean active = fetchOrgSolutionDetailsApiResponse.jsonPath().get("active[" + i + "]");
			if (active) {
				touSolutionsObj = new TouSolutions(solutionId, name, active);
				touSolutionList.add(touSolutionsObj);
			}
		}
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		practiceTouDetailsObj = new PracticeTouDetails(touName, touStatus, touSolutionList, touType, addTouContentObj);
		return practiceTouDetailsObj;
	}

}