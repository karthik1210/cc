// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgHierarchyDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetOrgHierarchyByOrgIdApiTests_DataLayer extends BaseRest {
	private OrgInfo orgInfo;
	private Validations validation;
	Response getOrgHierarchyApiResponse;
	List<OrgHierarchyDetails> datalist;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NGE practice id in orgId field and verify response body")
	public void testGetOrgHierarchyUsingNgePracticeId() throws IOException {
		getOrgHierarchyApiResponse = orgInfo
				.getOrgHierarchyByOrgId_DataLayer(Integer.parseInt(testData.getProperty("org.nge.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 200);
		datalist = new ArrayList<OrgHierarchyDetails>();
		datalist.add(new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.nge.practice.id")),
				"practice_name_1", testData.getProperty("api.practice.orgtype"), Boolean.parseBoolean("false"),
				Integer.parseInt(testData.getProperty("org.enterprise.id.one"))));
		logStep("Verify only NGE practice node is displayed in response");
		validation.verifyOrgHierarchyResponseBody(getOrgHierarchyApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Agnostic practice id in orgId field and verify response body")
	public void testGetOrgHierarchyUsingAgnosticPracticeId() throws IOException {
		getOrgHierarchyApiResponse = orgInfo
				.getOrgHierarchyByOrgId_DataLayer(Integer.parseInt(testData.getProperty("org.agnostic.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 200);
		datalist = new ArrayList<OrgHierarchyDetails>();
		datalist.add(new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.agnostic.practice.id")),
				"agnostic practice", testData.getProperty("api.practice.orgtype"), Boolean.parseBoolean("false"), 0));
		logStep("Verify only agnostic practice node is displayed in response");
		validation.verifyOrgHierarchyResponseBody(getOrgHierarchyApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Enterprise id(with child nodes) in orgId field and verify response body")
	public void testGetOrgHierarchyUsingEnterpriseId() throws IOException {
		getOrgHierarchyApiResponse = orgInfo
				.getOrgHierarchyByOrgId_DataLayer(Integer.parseInt(testData.getProperty("org.enterprise.id.one")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 200);
		List<OrgHierarchyDetails> entepriseOrgHierarchyList = Arrays.asList(
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.enterprise.id.one")),
						"enterprise_name_1", testData.getProperty("api.enterprise.orgtype"),
						Boolean.parseBoolean(testData.getProperty("api.enterprise.active")),
						Integer.parseInt(testData.getProperty("org.system.id"))),
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.nge.practice.id.two")),
						"practice_name_2", testData.getProperty("api.practice.orgtype"),
						Boolean.parseBoolean(testData.getProperty("api.practice.active")),
						Integer.parseInt(testData.getProperty("org.enterprise.id.one"))),
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.nge.practice.id")),
						"practice_name_1", testData.getProperty("api.practice.orgtype"), Boolean.parseBoolean("false"),
						Integer.parseInt(testData.getProperty("org.enterprise.id.one"))));
		logStep("Verify enterprise node is displayed with all child nodes in response");
		validation.verifyOrgHierarchyResponseBody(getOrgHierarchyApiResponse, entepriseOrgHierarchyList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter System id(with child nodes) in orgId field and verify response body")
	public void testGetOrgHierarchyUsingSystemId() throws IOException {
		getOrgHierarchyApiResponse = orgInfo
				.getOrgHierarchyByOrgId_DataLayer(Integer.parseInt(testData.getProperty("org.system.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 200);
		List<OrgHierarchyDetails> systemOrgHierarchyList = Arrays.asList(
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.system.id")), "system_name1",
						testData.getProperty("api.system.orgtype"),
						Boolean.parseBoolean(testData.getProperty("api.system.active")), 0),
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.enterprise.id.two")),
						"enterprise_name_2", testData.getProperty("api.enterprise.orgtype"),
						Boolean.parseBoolean("false"), Integer.parseInt(testData.getProperty("org.system.id"))),
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.enterprise.id.one")),
						"enterprise_name_1", testData.getProperty("api.enterprise.orgtype"),
						Boolean.parseBoolean(testData.getProperty("api.enterprise.active")),
						Integer.parseInt(testData.getProperty("org.system.id"))),
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.nge.practice.id.two")),
						"practice_name_2", testData.getProperty("api.practice.orgtype"),
						Boolean.parseBoolean(testData.getProperty("api.practice.active")),
						Integer.parseInt(testData.getProperty("org.enterprise.id.one"))),
				new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.nge.practice.id")),
						"practice_name_1", testData.getProperty("api.practice.orgtype"), Boolean.parseBoolean("false"),
						Integer.parseInt(testData.getProperty("org.enterprise.id.one"))));
		logStep("Verify system node is displayed with all child nodes in response");
		validation.verifyOrgHierarchyResponseBody(getOrgHierarchyApiResponse, systemOrgHierarchyList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Enterprise id(without child nodes) in orgId field and verify response body")
	public void testGetOrgHierarchyUsingEnterpriseIdWithoutChildNodes() throws IOException {
		getOrgHierarchyApiResponse = orgInfo
				.getOrgHierarchyByOrgId_DataLayer(Integer.parseInt(testData.getProperty("org.enterprise.id.two")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 200);
		datalist = new ArrayList<OrgHierarchyDetails>();
		datalist.add(new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.enterprise.id.two")),
				"enterprise_name_2", testData.getProperty("api.enterprise.orgtype"), Boolean.parseBoolean("false"),
				Integer.parseInt(testData.getProperty("org.system.id"))));
		logStep("Verify only enterprise node is displayed in response");
		validation.verifyOrgHierarchyResponseBody(getOrgHierarchyApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter System id(without child nodes) in orgId field and verify response body")
	public void testGetOrgHierarchyUsingSystemIdWithoutChildNodes() throws IOException {
		getOrgHierarchyApiResponse = orgInfo
				.getOrgHierarchyByOrgId_DataLayer(Integer.parseInt(testData.getProperty("org.system.id.two")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 200);
		datalist = new ArrayList<OrgHierarchyDetails>();
		datalist.add(new OrgHierarchyDetails(Integer.parseInt(testData.getProperty("org.system.id.two")),
				"System_name_2", testData.getProperty("api.system.orgtype"),
				Boolean.parseBoolean(testData.getProperty("api.system.active")), 0));
		logStep("Verify only system node is displayed in response");
		validation.verifyOrgHierarchyResponseBody(getOrgHierarchyApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getOrgHierarchyApiResponse = orgInfo.getOrgHierarchyByOrgIdUsingInvalidUrl_DataLayer(
				Integer.parseInt(testData.getProperty("api.existing.orgid")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getOrgHierarchyApiResponse);
	}

	@DataProvider(name = "invalidOrgId")
	public Object[][] getOrgId() throws Exception {
		return new Object[][] { { testData.getProperty("api.org.invalid.id") },
				{ testData.getProperty("api.existing.client.id") } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidOrgId", description = "Enter invalid org id in orgid field and validate response statuscode")
	public void testResponseForInvalidOrgId(String invalidOrgId) throws IOException {
		int orgId = Integer.parseInt(invalidOrgId);
		getOrgHierarchyApiResponse = orgInfo.getOrgHierarchyByOrgId_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		getOrgHierarchyApiResponse = orgInfo
				.invalidMethodCall_DataLayer(Integer.parseInt(testData.getProperty("api.existing.orgid")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgHierarchyApiResponse);
		CCUtils.assertEquals(getOrgHierarchyApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(getOrgHierarchyApiResponse);
	}
}
