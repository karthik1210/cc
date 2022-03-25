// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeMethod;
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
	public int clientId = 0;
	public int practiceId = 0;
	List<Integer> pracSolutionList = Arrays.asList(1101, 1102, 1103);

	@BeforeMethod()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		updateOrgProductStatusValidations = new UpdateOrgProductStatusValidations();
		logStep("creating Agnostic practice node within client");
		clientId = createClient();
		practiceId = createPracticeWitinClient(clientId, pracSolutionList);
	}

	public int createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		logStep("Create a client of name : " + clientName);
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		return createClientApiResponse.jsonPath().getInt("id");
	}

	public int createPracticeWitinClient(int clientId, List<Integer> solutionsList) throws IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		solutionList = new ArrayList<Integer>();
		for (int solution : solutionsList) {
			solutionList.add(solution);
			logStep("Adding solutions ID : " + solution + " within practice node : " + practiceName);
		}
		logStep("Create a practice within client of Id : " + clientId);
		Response createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(), clientId,
				ccUser.getParentTypeApi(), practiceSapNumber, ccUser.getPracticeTimezoneIdApi(), solutionList);
		return createPracticeApiResponse.jsonPath().getInt("id");
	}

	@DataProvider(name = "typesofstatus")
	public Object[] typesOfOrgProductStatus() {
		Object[] obj = new Object[] { "Active Implementation", "Go-live", "On Hold", "Terminated" };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "typesofstatus", description = "Update product status of practice within client and validate whether updated or not")
	public void testUpdateExistingProductStatusOfPractices(String getTypesOfProductStatus) throws IOException {
		Response getOrgIdInfo = orgInfo.getOrgId(practiceId);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
		List<UpdateProductStatus> listOfUpdatedSolutions = updateOrgProductStatusValidations
				.fetchAndUpdateSolutionsListInGetOrgResponseBody(getOrgIdInfo, getTypesOfProductStatus,
						CCUtils.dateformatter(), null);
		logStep("Updating status of Products within Practices");
		Response updateProductStatusResponse = orgInfo.updatePracticeWithSolutions(
				updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrgIdInfo),
				updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrgIdInfo),
				Integer.parseInt(updateOrgProductStatusValidations.fetchOrgIdKeyValueFromResponseBody(getOrgIdInfo)),
				updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrgIdInfo),
				listOfUpdatedSolutions,
				updateOrgProductStatusValidations.fetchTimezoneIdKeyValueFromResponseBody(getOrgIdInfo));
		logStep("Validating response information");
		CCUtils.assertEquals(updateProductStatusResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(updateProductStatusResponse);
		Response getOrgIdInfoAfterUpdate = orgInfo.getOrgId(practiceId);
		logStep("Validating pdated product status is reflected within practice by fetching Get Org response body");
		updateOrgProductStatusValidations.validateSolutionsListInGetOrgResponseBody(getOrgIdInfoAfterUpdate,
				getTypesOfProductStatus);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "typesofstatus", description = "Validate Org Id can not be zero error message.")
	public void testErrorOrgIdOfPracticeIsZero(String getTypesOfProductStatus) throws IOException {
		Response getOrgIdInfo = orgInfo.getOrgId(practiceId);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
		List<UpdateProductStatus> listOfUpdatedSolutions = updateOrgProductStatusValidations
				.fetchAndUpdateSolutionsListInGetOrgResponseBody(getOrgIdInfo, getTypesOfProductStatus,
						CCUtils.dateformatter(), null);
		logStep("Updating status of Products within Practices");
		Response updateProductStatusResponse = orgInfo.updatePracticeWithSolutions(
				updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrgIdInfo),
				updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrgIdInfo), 0,
				updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrgIdInfo),
				listOfUpdatedSolutions,
				updateOrgProductStatusValidations.fetchTimezoneIdKeyValueFromResponseBody(getOrgIdInfo));
		logStep("Validating response information");
		CCUtils.assertEquals(updateProductStatusResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(updateProductStatusResponse);
		logStep("Validating Error Message for Org Id = 0 in response body");
		updateOrgProductStatusValidations.validateOrgIdZeroErrorMessage(updateProductStatusResponse);
	}

	@DataProvider(name = "typesofinvaliddateformats")
	public Object[] typesOfInvalidDateFormats() {
		Object[] obj = new Object[] { "Non-existing", "22-sep-2021", "Dec-44-2022", "Jan-9-23" };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "typesofinvaliddateformats", description = "Test Invalid date and format=MM/dd/yyyy error message when updating status for go live and statusDate string value not in date format.")
	public void testErrorInvalidDateAndFormat(String typesOfInvalidDateFormat) throws IOException {
		Response getOrgIdInfo = orgInfo.getOrgId(practiceId);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
		List<UpdateProductStatus> listOfUpdatedSolutions = updateOrgProductStatusValidations
				.fetchAndUpdateSolutionsListInGetOrgResponseBody(getOrgIdInfo, "Go-live", typesOfInvalidDateFormat,
						null);
		logStep("Updating status of Products within Practices");
		Response updateProductStatusResponse = orgInfo.updatePracticeWithSolutions(
				updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrgIdInfo),
				updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrgIdInfo),
				Integer.parseInt(updateOrgProductStatusValidations.fetchOrgIdKeyValueFromResponseBody(getOrgIdInfo)),
				updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrgIdInfo),
				listOfUpdatedSolutions,
				updateOrgProductStatusValidations.fetchTimezoneIdKeyValueFromResponseBody(getOrgIdInfo));
		logStep("Validating response information");
		CCUtils.assertEquals(updateProductStatusResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(updateProductStatusResponse);
		logStep("Validating Error Message for Invalid date=" + typesOfInvalidDateFormat
				+ " and format=MM/dd/yyyy in response body");
		updateOrgProductStatusValidations.validateInvalidDateAndFormatErrorMessage(updateProductStatusResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test Input parameters can not be null or empty error message when updating status for Go-live and statusDate is null or blank data.")
	public void testErrorDateAndFormatNotFound() throws IOException {
		Response getOrgIdInfo = orgInfo.getOrgId(practiceId);
		updateOrgProductStatusValidations.validatePracticeOrgTypeInResponseBody(getOrgIdInfo);
		List<UpdateProductStatus> listOfUpdatedSolutions = updateOrgProductStatusValidations
				.fetchAndUpdateSolutionsListInGetOrgResponseBody(getOrgIdInfo, "Go-live", null, null);
		logStep("Updating status of Products within Practices");
		Response updateProductStatusResponse = orgInfo.updatePracticeWithSolutions(
				updateOrgProductStatusValidations.fetchActiveKeyValueFromResponseBody(getOrgIdInfo),
				updateOrgProductStatusValidations.fetchDisplayNameKeyValueFromResponseBody(getOrgIdInfo),
				Integer.parseInt(updateOrgProductStatusValidations.fetchOrgIdKeyValueFromResponseBody(getOrgIdInfo)),
				updateOrgProductStatusValidations.fetchSapNumberKeyValueFromResponseBody(getOrgIdInfo),
				listOfUpdatedSolutions,
				updateOrgProductStatusValidations.fetchTimezoneIdKeyValueFromResponseBody(getOrgIdInfo));
		logStep("Validating response information");
		CCUtils.assertEquals(updateProductStatusResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(updateProductStatusResponse);
		logStep("Validating Error Message for Date and format not found in response body");
		updateOrgProductStatusValidations.validateDateAndFormatNotFoundErrorMessage(updateProductStatusResponse);
	}
}
