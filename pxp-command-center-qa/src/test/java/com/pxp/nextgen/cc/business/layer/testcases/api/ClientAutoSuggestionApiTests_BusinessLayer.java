// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ClientAutoSuggestionApiTests_BusinessLayer extends BaseRest {

	private ClientInfo clientData;
	private Validations validation;
	Response getSearchedClientDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientData = new ClientInfo();
		validation = new Validations();
	}

	@DataProvider(name = "searchData")
	public Object[] searchData() {
		Object[] obj = new Object[] { "Client", "client1", testData.getProperty("favoriting.client.one"), "Client@12" };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "searchData", description = "will filter client data according to searched input provided in query parameter.")
	public void testGetSearchSuggestedClientsList(String searchData) throws IOException {
		getSearchedClientDetailsApiResponse = clientData.getAutoSuggestionClient_BL(searchData);
		logStep("Validating response information");
		CCUtils.assertEquals(getSearchedClientDetailsApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(getSearchedClientDetailsApiResponse);
		validation.verifyGetSearchedAutosuggestionsClientResponses(getSearchedClientDetailsApiResponse, searchData);
	}

	@DataProvider(name = "minthreecharacterdata")
	public Object[] minThreeCharacterData() {
		Object[] obj = new Object[] { "cl", "01", "a", "7", "  " };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "minthreecharacterdata", description = "while searching client of minimum three characters throws error")
	public void testErrorMinimumThreeCharacter(String minThreeCharacterData) throws IOException {
		getSearchedClientDetailsApiResponse = clientData.getAutoSuggestionClient_BL(minThreeCharacterData);
		logStep("Validating response information");
		CCUtils.assertEquals(getSearchedClientDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(getSearchedClientDetailsApiResponse);
		validation.validateAutosuggestionsClientMaxThreeCharactersErrorMessage(getSearchedClientDetailsApiResponse,
				minThreeCharacterData);
	}

	@DataProvider(name = "nonexistingclientdata")
	public Object[] nonExistingClientData() {
		Object[] obj = new Object[] { "on1!", "01@1", "8*8" };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "nonexistingclientdata", description = "Create a new client and fetch newly created client details using client id")
	public void testNoContentOfClientStatusCode(String nonExistingClientData) throws IOException {
		getSearchedClientDetailsApiResponse = clientData.getAutoSuggestionClient_BL(nonExistingClientData);
		logStep("Validating response information");
		CCUtils.assertEquals(getSearchedClientDetailsApiResponse.getStatusCode(), 204);
		CCUtils.responseTimeValidation(getSearchedClientDetailsApiResponse);
	}
}
