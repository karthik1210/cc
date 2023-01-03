// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.DisplayText;
import com.pxp.cc.pojos.OrgWebsites;
import com.pxp.cc.pojos.SavePracticeWebsite;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeletePracticeWebsitesApiTests_DataLayer extends BaseRest {
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private Validations validations;
	Response deletePracticeWebsiteApiResponse, getPracticeWebsitesApiResponse, addPracticeWebsitesApiResponse,
			createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
	List<SavePracticeWebsite> savePracticeWebsite = new ArrayList<SavePracticeWebsite>();
	SavePracticeWebsite savePracticeWebsiteObj, savePracticeWebsiteObj1;
	DisplayText displayTextObj, displayTextObj1;
	List<OrgWebsites> orgWebsites = new ArrayList<OrgWebsites>();
	Map<String, Object> displayText1 = new HashMap<String, Object>();
	Map<String, Object> displayText2 = new HashMap<String, Object>();
	OrgWebsites orgWebsites1, orgWebsites2;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Single Practice Website with delete as true")
	public void deletePracticeWebsiteApiSuccess() throws IOException {
		int orgId = createPractice();
		logStep("Add a SiteId for a given orgId");
		displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en"))
				.concat(CCUtils.generateAlphaNumericString(2)));
		displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es"))
				.concat(CCUtils.generateAlphaNumericString(2)));
		orgWebsites1 = new OrgWebsites(
				testData.getProperty("api.practice.website.url").replace("1",
						CCUtils.generateAlphaNumericString(2).trim()),
				testData.getProperty("api.practice.website.type"),
				Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
		orgWebsites.add(orgWebsites1);
		addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks("DL", orgId,
				orgWebsites);
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = displayText1.get("en").toString();
		String spanishDisplay = displayText1.get("es").toString();
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
		savePracticeWebsite.clear();
		savePracticeWebsite.add(savePracticeWebsiteObj);
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", orgId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Multiple Practice Website with delete as true")
	public void deleteMultiPracticeWebsiteApiSuccess() throws IOException {
		int orgId = createPractice();
		logStep("Add multiple SiteIds for a given orgId");
		displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en"))
				.concat(CCUtils.generateAlphaNumericString(2)));
		displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es"))
				.concat(CCUtils.generateAlphaNumericString(2)));
		displayText2.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en"))
				.concat(CCUtils.generateAlphaNumericString(2)));
		displayText2.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es"))
				.concat(CCUtils.generateAlphaNumericString(2)));
		orgWebsites1 = new OrgWebsites(
				testData.getProperty("api.practice.website.url").replace("1",
						CCUtils.generateAlphaNumericString(2).trim()),
				testData.getProperty("api.practice.website.type"),
				Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
		orgWebsites2 = new OrgWebsites(
				testData.getProperty("api.practice.website.url").replace("1",
						CCUtils.generateAlphaNumericString(2).trim()),
				testData.getProperty("api.practice.website.type"),
				Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText2);
		orgWebsites.add(orgWebsites1);
		orgWebsites.add(orgWebsites2);
		addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks("DL", orgId,
				orgWebsites);
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = displayText1.get("en").toString();
		String spanishDisplay = displayText1.get("es").toString();
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		int siteId1 = getPracticeWebsitesApiResponse.jsonPath().get("siteId[1]");
		String url1 = getPracticeWebsitesApiResponse.jsonPath().get("url[1]");
		String englishDisplay1 = displayText2.get("en").toString();
		String spanishDisplay1 = displayText2.get("es").toString();
		String type1 = getPracticeWebsitesApiResponse.jsonPath().get("type[1]");
		int sortOrder1 = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[1]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		displayTextObj1 = new DisplayText(englishDisplay1, spanishDisplay1);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
		savePracticeWebsiteObj1 = new SavePracticeWebsite(siteId1, url1, type1, sortOrder1, displayTextObj1, true);
		savePracticeWebsite.clear();
		savePracticeWebsite.add(savePracticeWebsiteObj);
		savePracticeWebsite.add(savePracticeWebsiteObj1);
		logStep("Delete multiple SiteIds for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", orgId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Single Practice Website for Invalid datatype of Practice Id")
	public void deletePracticeWebsiteInvaliDataTypePracticeApi() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
		String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
		savePracticeWebsite.clear();
		savePracticeWebsite.add(savePracticeWebsiteObj);
		String orgIdString = orgId + "Sample";
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesInvaliDatatypeResponse("DL", orgIdString,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Single Practice Website for Invalid Method Call")
	public void deletePracticeWebsiteInvalidMethodCallApi() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
		String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
		savePracticeWebsite.add(savePracticeWebsiteObj);
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("InValidMethodType", "DL",
				orgId, savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 405);
	}

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Single Practice Website for Null PracticeId")
	public void deletePracticeWebsiteNullValueApi() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
		String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
		savePracticeWebsite.add(savePracticeWebsiteObj);
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("NullValue", "DL", orgId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 400);
	}

	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Practice Website for invalid PracticeId")
	public void deletePracticeWebsiteInvalidPracticeApi() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
		String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
		savePracticeWebsite.add(savePracticeWebsiteObj);
		int clientId = Integer.parseInt(testData.getProperty("api.client.id"));
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", clientId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(deletePracticeWebsiteApiResponse, clientId);
		int systemId = Integer.parseInt(testData.getProperty("api.system.id"));
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", systemId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(deletePracticeWebsiteApiResponse, systemId);
		int enterpriseId = Integer.parseInt(testData.getProperty("api.enterprise.id"));
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", enterpriseId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(deletePracticeWebsiteApiResponse, enterpriseId);
	}

	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Single Practice Website for Invalid URI")
	public void deletePracticeWebsiteInvaliUriApi() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
		String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
		savePracticeWebsite.add(savePracticeWebsiteObj);
		logStep("Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("InValid", "DL", orgId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 404);
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Delete the Single Practice Website with delete attribute as false")
	public void deleteSinglePracticeWebsiteWithDeleteFalseApi() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
		String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, false);
		savePracticeWebsite.add(savePracticeWebsiteObj);
		logStep("Try to Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", orgId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 200);
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int dSiteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		if (siteId == dSiteId)
			logStep("SiteId is " + dSiteId + " not deleted");
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Delete the multiple Practice Website with delete attribute as false")
	public void deleteMultiplePracticeWebsiteWithDeleteFalseApi() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
		logStep("Getting SiteId information for a given orgId");
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
		String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
		String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
		String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
		int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
		displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
		int siteId1 = getPracticeWebsitesApiResponse.jsonPath().get("siteId[1]");
		String url1 = getPracticeWebsitesApiResponse.jsonPath().get("url[1]");
		String englishDisplay1 = getPracticeWebsitesApiResponse.jsonPath().get("displayText[1].en");
		String spanishDisplay1 = getPracticeWebsitesApiResponse.jsonPath().get("displayText[1].es");
		String type1 = getPracticeWebsitesApiResponse.jsonPath().get("type[1]");
		int sortOrder1 = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[1]");
		displayTextObj1 = new DisplayText(englishDisplay1, spanishDisplay1);
		savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, false);
		savePracticeWebsiteObj1 = new SavePracticeWebsite(siteId1, url1, type1, sortOrder1, displayTextObj1, false);
		savePracticeWebsite.clear();
		savePracticeWebsite.add(savePracticeWebsiteObj);
		savePracticeWebsite.add(savePracticeWebsiteObj1);
		logStep("Try to Delete a SiteId for a given orgId");
		deletePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", orgId,
				savePracticeWebsite);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
		CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 200);
		getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
		int dSiteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
		int dSiteId1 = getPracticeWebsitesApiResponse.jsonPath().get("siteId[1]");
		if (siteId == dSiteId && siteId1 == dSiteId1)
			logStep("SiteId is " + dSiteId + " ," + dSiteId1 + " not deleted");
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
