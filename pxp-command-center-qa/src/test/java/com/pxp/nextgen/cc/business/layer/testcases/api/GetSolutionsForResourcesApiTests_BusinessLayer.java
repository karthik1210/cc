//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgResourceInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class GetSolutionsForResourcesApiTests_BusinessLayer extends BaseRest{

	private OrgResourceInfo orgResourceInfo;
	private Validations validations;
	Response fetchSolutionsForResources;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validations = new Validations();
		orgResourceInfo = new OrgResourceInfo();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the solutions of a resource of a practice")
	public void testGetSolutionsForResources() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 200);
		practiceAppointmentTypeValidations.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing a resource Id and practice Id but no solution is associated")
	public void testResponseCodeForExistingResourceIdAndPracticeIdOfNoAssociatedSolution() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("no.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("no.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing resource Id and non-existing practice Id")
	public void testErrorMessageForExistingResourceIdAndNonexistingPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of existing practice Id and non-existing resource Id")
	public void testErrorMessageForNonExistingResourceIdAndExistingPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of both non-existing resource Id and practice Id")
	public void testErrorMessageForBothNonExistingResourceIdAndPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with incorrect HTTP method")
	public void testErrorMessageForInvalidMethodCallWhenFetchingSolutionWithHTTPMethodPOST() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL_InvalidMethodCall(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidMethodCall(fetchSolutionsForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with invalid url")
	public void testErrorMessageForInvalidUrlWhenFetchingSolutions() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL_InvalidUrl(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(fetchSolutionsForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with resouce id and null and practice Id as null")
	public void testErrorMessageForNullValuesInPracticeIdAndAppointmentTypeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 400);
		validations.verifyErrorMessageIfClientIdIsNull(fetchSolutionsForResources);
	}
}
