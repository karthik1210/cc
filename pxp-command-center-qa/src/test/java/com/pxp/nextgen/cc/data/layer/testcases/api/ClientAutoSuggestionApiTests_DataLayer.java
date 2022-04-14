//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class ClientAutoSuggestionApiTests_DataLayer extends BaseRest {

	private ClientInfo clientData;
	private Validations validation;
	Response getSearchedClientDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientData = new ClientInfo();
		validation = new Validations();
	}

	@DataProvider(name = "typesofsearchdata")
	public Object[] typesOfSearchData() {
		Object[] obj = new Object[] { "Client", "4051", "client1", "Client@12" };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "typesofsearchdata", description = "will filter client data according to searched input provided in query parameter.")
	public void testGetSearchSuggestedClientsList(String typesOfSearchData) throws IOException {
		getSearchedClientDetailsApiResponse = clientData.getAutoSuggestionClient_DL(typesOfSearchData);
		logStep("Validating response information");
		CCUtils.assertEquals(getSearchedClientDetailsApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(getSearchedClientDetailsApiResponse);
		validation.verifyGetSearchedAutosuggestionsClientResponses(getSearchedClientDetailsApiResponse,
				typesOfSearchData);
	}

	@DataProvider(name = "minthreecharacterdata")
	public Object[] minThreeCharacterData() {
		Object[] obj = new Object[] { "cl", "01", "a", "7", "  " };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "minthreecharacterdata", description = "while searching client of minimum three characters throws error")
	public void testErrorMinimumThreeCharacter(String minThreeCharacterData) throws IOException {
		getSearchedClientDetailsApiResponse = clientData.getAutoSuggestionClient_DL(minThreeCharacterData);
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
		getSearchedClientDetailsApiResponse = clientData.getAutoSuggestionClient_DL(nonExistingClientData);
		logStep("Validating response information");
		CCUtils.assertEquals(getSearchedClientDetailsApiResponse.getStatusCode(), 204);
		CCUtils.responseTimeValidation(getSearchedClientDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getSearchedClientDetailsApiResponse = clientData
				.getAutoSuggestionClientInvalidUrl_DL(testData.getProperty("api.existing.client.data"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getSearchedClientDetailsApiResponse);
		CCUtils.assertEquals(getSearchedClientDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getSearchedClientDetailsApiResponse);
	}

}
