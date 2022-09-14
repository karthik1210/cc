//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableOrDisableSolutionsForResourceApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response updateSolutionForResources, getSolutionForResources;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing solution id for existing resourceId  and practiceId and verify response body")
	public void testToEnableExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Disable existing solution id for existing resourceId  and practiceId and verify response body")
	public void testToDisableExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting solution id for existing resourceId and practiceId  and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Enter nonexisting solution id for existing resourceId and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing solution id for non-existing resourceId and existing practice id and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForNonExistingResourceId() throws IOException {
		logStep("Enter nonexisting solution id for non-existing resourceId and existing practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("nonexisting.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting provider and practiceId and verify error message")
	public void testValidLocationIdForNonExistingProviderIdAndPracticeId() throws IOException {
		logStep("Enter nonexisting solution id for existing resourceId and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceForNullPayload_DL(resourceId,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a POST method call instead of PUT method call and verify error message")
	public void testErrorMessageForPostMethodCall() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingInvalidMethod_DL(resourceId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url to Enable or disable solutions for resources and verify error message")
	public void testErrorMessageEnableOrDisableSolutionsForResourceWithInvalidUrl() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingInvalidUrl_DL(resourceId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for resourceId and practiceId to add Locations and verify error message")
	public void testAddToLocationForNullValues() throws IOException {
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingNullValue_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
	}
}