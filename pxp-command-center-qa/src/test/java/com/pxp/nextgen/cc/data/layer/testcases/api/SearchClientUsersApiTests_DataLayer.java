// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SearchClientUsersApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response searchClientUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@DataProvider(name = "userdatavalidusertype")
	public Object[] searchUserDetailsWithoutFilter() {
		Object[][] obj = new Object[][] { { testData.getProperty("user.type.client").trim(), null, null, null, null,
				null, null, null, null, (testData.getProperty("api.page.no").trim()),
				(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidusertype", description = "Search Client user without filter  by keeping page 0 and page size 10")
	public void testSearchClientUserDetailsWithoutFilter(String[] getSearchUserDetails) throws NullPointerException {
		searchClientUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientUserApiResponse);
		CCUtils.assertEquals(searchClientUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchClientUserApiResponse, getSearchUserDetails[0]);
	}

	@DataProvider(name = "userdatavalidinput")
	public Object[] searchUserDetailsWithValidInput() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.client").trim(),
						testData.getProperty("api.full.client.firstname").trim(),
						testData.getProperty("api.full.client.lastname").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.client").trim(),
						testData.getProperty("api.partial.client.firstname").trim(),
						testData.getProperty("api.partial.client.lastname").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidinput", description = "Search Client user with full/partial  firstname & lastname by keeping page 0 and page size 10")
	public void testSearchClientUserWithValidInput(String[] getSearchUserDetails) throws NullPointerException {
		searchClientUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientUserApiResponse);
		CCUtils.assertEquals(searchClientUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchClientUserApiResponse, getSearchUserDetails[1]);
	}

	@DataProvider(name = "userdatavalidemailinput")
	public Object[] searchUserDetailsWithValidEmailInput() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.client").trim(), null, null,
						testData.getProperty("api.full.client.emailId").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.client").trim(), null, null,
						testData.getProperty("api.partial.client.emailId").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidemailinput", description = "Search Client user with full/partial  emailId by keeping page 0 and page size 10")
	public void testSearchClientUserWithValidEmailInput(String[] getSearchUserDetails) throws NullPointerException {
		searchClientUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientUserApiResponse);
		CCUtils.assertEquals(searchClientUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchClientUserApiResponse, getSearchUserDetails[3]);
	}

	@DataProvider(name = "userdatavalidmobileinput")
	public Object[] searchUserDetailsWithValidMobileNumberInput() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.client").trim(), null, null, null, null,
						testData.getProperty("api.full.client.mobilenumber").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.client").trim(), null, null, null, null,
						testData.getProperty("api.partial.client.mobilenumber").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidmobileinput", description = "Search Internal user with full/partial  mobile number by keeping page 0 and page size 10")
	public void testSearchUserDetailsWithValidMobileNumberInput(String[] getSearchUserDetails)
			throws NullPointerException {
		searchClientUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientUserApiResponse);
		CCUtils.assertEquals(searchClientUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchClientUserApiResponse, getSearchUserDetails[5]);
	}

	@DataProvider(name = "userdatainvalidusertype")
	public Object[] searchUserDetailsWithInvalidUserType() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.invalid").trim(), null, null, null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(),
						testData.getProperty("api.full.client.firstname").trim(),
						testData.getProperty("api.full.client.lastname").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(),
						testData.getProperty("api.partial.client.firstname").trim(),
						testData.getProperty("api.partial.client.lastname").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null,
						testData.getProperty("api.full.client.emailId").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null,
						testData.getProperty("api.partial.client.emailId").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null, null, null,
						testData.getProperty("api.full.client.mobilenumber").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null, null, null,
						testData.getProperty("api.partial.client.mobilenumber").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatainvalidusertype", description = "Search  user using invalid usertype by keeping page 0 and page size 10")
	public void testSearchUserDetailsWithInvalidUserType(String[] getSearchUserDetails)
			throws NullPointerException, IOException {
		Response searchInternalUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserTypeField(searchInternalUserApiResponse);
	}

	@DataProvider(name = "userdatainvalidinput")
	public Object[] searchUserDetailsWithInvalidInput() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.client").trim(), testData.getProperty("api.invalid.firstName").trim(),
						testData.getProperty("api.invalid.lastName").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.client").trim(), null, null,
						testData.getProperty("api.invalid.email.id").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.client").trim(), null, null, null, null,
						testData.getProperty("api.invalid.mobile").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) }, };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatainvalidinput", description = "Search Client user  with invalid input  by keeping page 0 and page size 10")
	public void testSearchClientUserDetailsWithInvalidInput(String[] getSearchUserDetails)
			throws NullPointerException, IOException {
		Response searchInternalUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Var/reseller user instead of Client user & verify status code ")
	public void testSearchUserDetailsWithAnotherUserType() throws IOException {
		Response searchInternalUserApiResponse = userInfo.getUsers_DL(testData.getProperty("user.type.varreseller"),
				testData.getProperty("api.full.client.firstname"), testData.getProperty("api.full.client.lastname"),
				testData.getProperty("api.full.client.emailId"), null,
				null, null, null, null, (testData.getProperty("api.page.no")),
				(testData.getProperty("api.page.size")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidusertype", description = "Search Client user using invalid url")
	public void testSearchClientUserUsingInvalidUrl(String[] getSearchUserDetails) throws IOException {
		searchClientUserApiResponse = userInfo.getUsersInvalidUrl_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientUserApiResponse);
		CCUtils.assertEquals(searchClientUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(searchClientUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidusertype", description = "Search Client user using invalid method call")
	public void testSearchClientUserUsingInvalidMethodCall(String[] getSearchUserDetails) throws IOException {
		searchClientUserApiResponse = userInfo.invalidMethodCallGetUsers_DL(getSearchUserDetails[0],
				getSearchUserDetails[1], getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4],
				getSearchUserDetails[5], getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8],
				getSearchUserDetails[9], getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchClientUserApiResponse);
		CCUtils.assertEquals(searchClientUserApiResponse.getStatusCode(), 405);
		validation.verifyErrorInResponseForInvalidMethod(searchClientUserApiResponse);
	}
}