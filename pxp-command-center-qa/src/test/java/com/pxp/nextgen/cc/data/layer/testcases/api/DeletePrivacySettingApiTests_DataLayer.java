//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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

public class DeletePrivacySettingApiTests_DataLayer extends BaseRest {

	private OrgInfo orgInfo;
	private Validations validations;
	Response deleteTouIdApiResponse, addTouApiResponse, getTousApiResponse, getTouApiResponse,
			fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse, createClientApiResponse,
			createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
	int practiceId, touId;
	List<Integer> solutionList = null;
	private ClientInfo clientInfo;
	PracticeTouDetails practiceTouDetailsObj;
	TouSolutions touSolutionsObj;
	DisplayText addTouContentObj;
	String touType, touName;
	Map<String, Object> deleteTouStatus = new HashMap<String, Object>();

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
	}

	@DataProvider(name = "invalidOrValidPracticeIdAndTouId")
	public Object[][] invalidOrValidPracticeIdAndTouId() throws Exception {
		return new Object[][] {
				{ testData.getProperty("api.valid.practice.id.tou").trim(),
						testData.getProperty("api.Non.existing.tou.id").trim() },
				{ testData.getProperty("api.Non.existing.practice.id").trim(),
						testData.getProperty("api.valid.tou.id.active").trim() },
				{ testData.getProperty("api.Non.existing.practice.id").trim(),
						testData.getProperty("api.Non.existing.tou.id").trim() }

		};
	}

	@DataProvider(name = "nullOrValidPracticeIdAndTouId")
	public Object[][] nullOrValidPracticeIdAndTouId() throws Exception {
		return new Object[][] { { testData.getProperty("api.valid.practice.id.tou").trim(), null },
				{ null, testData.getProperty("api.valid.tou.id.active").trim() }, { null, null },
				{ "", testData.getProperty("api.valid.tou.id.active").trim() }, { "", "" } };
	}

	@DataProvider(name = "alphaNumericPracticeIdAndTouId")
	public Object[][] alphaNumericPracticeIdAndTouId() throws Exception {
		return new Object[][] {
				{ testData.getProperty("api.valid.practice.id.tou"),
						testData.getProperty("api.valid.tou.id.active") + "Sample" },
				{ testData.getProperty("api.valid.practice.id.tou") + "Sample",
						testData.getProperty("api.valid.tou.id.active") },
				{ testData.getProperty("api.valid.practice.id.tou") + "Sample",
						testData.getProperty("api.valid.tou.id.active") + "Sample" } };
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24326_Deleting privacy setting with a valid practice id and Draft tou id")
	public void deleteTouWithPracticeIdAndDraftTouId() throws IOException {
		logStep("TC_09_Verify the Response code as 200, when user try to delete the Privacy Settings for Draft Status by using PUT Request [DL/BL]");
		practiceId = createPractice();
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		logStep("Create PayLoad for new tou creation to practice and get the TouId");
		touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType);
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousResponse("Valid", "DL", practiceId, touId, deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21246_Deleting privacy setting with a valid practice id and Inactive tou id")
	public void deleteTouWithPracticeIdAndInactiveTouId() throws IOException {
		logStep("TC_01_Verify the Response code as 200, when user try to delete the Privacy Settings for Inactive Status by using PUT Request [DL]");
		practiceId = createPractice();
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		logStep("Create PayLoad for new tou creation to practice and get the TouId");
		touId = getTouIdByAddingtoPractice(practiceId, touName, "Inactive", touType);
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousResponse("Valid", "DL", practiceId, touId, deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21247_deleting Tou Details with Invalid URI")
	public void deleteTouWithInvalidURIForPracticeIdAndTouId() throws IOException {
		logStep("TC_02_Verify the Response code as 404, when user try to give invalid URI for PUT Call of the privacy settings [DL]");
		practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.tou"));
		touId = Integer.parseInt(testData.getProperty("api.valid.tou.id.active"));
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousResponse("InValid", "DL", practiceId, touId,
				deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(deleteTouIdApiResponse);
	}

	@Test(priority = 4, dataProvider = "alphaNumericPracticeIdAndTouId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21248_deleting Tou Details with alphanumeric practice id and tou id")
	public void deleteTouWithAlphnumericPracticeIdAndTouId(String practiceId, String touId) throws IOException {
		logStep("TC_03_Verify the Response code as 400, when user try to give alphanumeric practiceId and tou Id for PUT Request for privacy settings [DL]");
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousInvaliDatatypeResponse("DL", practiceId, touId,
				deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21249_deleting Tou Details with Invalid Method")
	public void deleteTouWithInvalidMethodForPracticeIdAndTouId() throws IOException {
		logStep("TC_04_verify response code 405 and error message, when user enters valid practice Id and valid tou id Instead of PUT, Patch Request to delete the Privacy settings [DL]");
		practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.tou"));
		touId = Integer.parseInt(testData.getProperty("api.valid.tou.id.active"));
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousResponse("InValidMethod", "DL", practiceId, touId,
				deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidMethodCall(deleteTouIdApiResponse);
	}

	@Test(priority = 6, dataProvider = "nullOrValidPracticeIdAndTouId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21250_deleting Tou Details with Null/Empty practice id and tou id")
	public void deleteTouWithNullEmptyPracticeIdAndTouId(String practiceId, String touId) throws IOException {
		logStep("TC_05_Verify the Response code as 400, when user try to give null values to practiceId and tou Id for PUT Request for privacy settings [DL]");
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousInvaliDatatypeResponse("DL", practiceId, touId,
				deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 7, dataProvider = "invalidOrValidPracticeIdAndTouId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21251_deleting Tou Details with Invalid or Valid practice id and tou id")
	public void deleteTouDetailsWithValidOrInvalidPracticeIdAndTouId(String practiceId, String touId)
			throws IOException {
		logStep("TC_06_Verify the Response code as 400, when user try to give invalid/non existing practice id and valid tou Id for privacy settings by using PUT Request [DL]");
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousInvaliDatatypeResponse("DL", practiceId, touId,
				deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21252_Deleting Tou details for a valid practice id and Active tou id")
	public void deleteTouDetailsWithPracticeIdAndActiveTouId() throws IOException {
		logStep("TC_07_Verify the Response code as 400, when user try to give Active tou Id for PUT Request for privacy settings [DL]");
		practiceId = createPractice();
		touType = testData.getProperty("PXPP.18282.api.tou.type");
		touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
		logStep("Create PayLoad for new tou creation to practice and get the TouId");
		touId = getTouIdByAddingtoPractice(practiceId, touName, "Active", touType);
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "Deleted");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousResponse("Valid", "DL", practiceId, touId, deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 400);
		CCUtils.assertEquals(deleteTouIdApiResponse.jsonPath().get("message").toString(),
				"Active tou cannot be deleted");
	}

	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21253_deleting Tou Details with Invalid Payload")
	public void deleteTouWithInvalidPayloadForPracticeIdAndTouId() throws IOException {
		logStep("TC_08_Verify the Response code as 400, when user try to give Valid practice Id,tou Id and invalid payload of privacy settings by using PUT [DL]");
		practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.tou"));
		touId = Integer.parseInt(testData.getProperty("api.valid.tou.id.active"));
		logStep("Delete tou with touId for the practice");
		deleteTouStatus.clear();
		deleteTouStatus.put("status", null);
		deleteTouIdApiResponse = orgInfo.deletePracticeTousResponse("Valid", "DL", practiceId, touId, deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 400);
		deleteTouStatus.clear();
		deleteTouStatus.put("status", "");
		deleteTouIdApiResponse = orgInfo.deletePracticeTousResponse("Valid", "DL", practiceId, touId, deleteTouStatus);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteTouIdApiResponse);
		CCUtils.assertEquals(deleteTouIdApiResponse.getStatusCode(), 400);
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
                testData.getProperty("api.client.timezoneid"),testData.getProperty("ehr.identifier"),testData.getProperty("pm.identifier"));
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

	@Test(enabled = false)
	public int getTouIdByAddingtoPractice(int practiceId, String touName, String touStatus, String touType)
			throws IOException {
		practiceTouDetailsObj = practiceTouPayload(practiceId, touName, touStatus);
		logStep("Add a new tou to practice");
		addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
		logStep("Get All tous for practice");
		getTousApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
		touId = getTousApiResponse.jsonPath().get("touId[0]");
		return touId;
	}
}
