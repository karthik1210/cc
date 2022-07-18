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

public class SearchInternalUsersApiTest_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response searchInternalUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@DataProvider(name = "userdatavalidusertype")
	public Object[] searchUserDetailsWithoutFilter() {

		Object[][] obj = new Object[][] { { testData.getProperty("user.type.internal").trim(), null, null, null, null,
				null, null, null, null, (testData.getProperty("api.page.no").trim()),
				(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidusertype", description = "Search Internal user without filter  by keeping page 0 and page size 10")
	public void testSearchInternalUserDetailsWithoutFilter(String[] getSearchUserDetails) throws NullPointerException {
		searchInternalUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchInternalUserApiResponse, getSearchUserDetails[0]);
	}

	@DataProvider(name = "userdatavalidinput")
	public Object[] searchUserDetailsWithValidInput() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.internal").trim(), testData.getProperty("api.full.first.name").trim(),
						testData.getProperty("api.full.last.name").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.internal").trim(),
						testData.getProperty("api.partial.first.name").trim(),
						testData.getProperty("api.partial.last.name").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidinput", description = "Search Internal user with full/partial  firstname & lastname by keeping page 0 and page size 10")
	public void testSearchInternalUserWithValidInput(String[] getSearchUserDetails) throws NullPointerException {
		searchInternalUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchInternalUserApiResponse, getSearchUserDetails[1]);
	}

	@DataProvider(name = "userdatavalidemailinput")
	public Object[] searchUserDetailsWithValidEmailInput() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.internal").trim(), null, null,
						testData.getProperty("api.full.email.id").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.internal").trim(), null, null,
						testData.getProperty("api.partial.email.id").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) }

		};
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidemailinput", description = "Search Internal user with full/partial  emailId by keeping page 0 and page size 10")
	public void testSearchInternalUserWithValidEmailInput(String[] getSearchUserDetails) throws NullPointerException {
		searchInternalUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchInternalUserApiResponse, getSearchUserDetails[3]);
	}

	@DataProvider(name = "userdatavalidemobileinput")
	public Object[] searchUserDetailsWithValidMobileNumberInput() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.internal").trim(), null, null, null, null,
						testData.getProperty("api.full.mobile.number").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.internal").trim(), null, null, null, null,
						testData.getProperty("api.partial.mobile.number").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidemobileinput", description = "Search Internal user with full/partial  mobile number by keeping page 0 and page size 10")
	public void testSearchUserDetailsWithValidMobileNumberInput(String[] getSearchUserDetails)
			throws NullPointerException {
		searchInternalUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 200);
		validation.verifyInternalUserOutputWithValidInput(searchInternalUserApiResponse, getSearchUserDetails[5]);
	}

	@DataProvider(name = "userdatainvalidusertype")
	public Object[] searchUserDetailsWithInvalidUserType() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("user.type.invalid").trim(), null, null, null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), testData.getProperty("api.full.first.name").trim(),
						testData.getProperty("user.type.invalid").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(),
						testData.getProperty("api.partial.first.name").trim(),
						testData.getProperty("api.partial.last.name").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null,
						testData.getProperty("api.full.email.id").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null,
						testData.getProperty("api.partial.email.id").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null, null, null,
						testData.getProperty("api.full.mobile.number").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.invalid").trim(), null, null, null, null,
						testData.getProperty("api.partial.mobile.number").trim(), null, null, null,
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
				{ testData.getProperty("user.type.internal").trim(),
						testData.getProperty("api.invalid.firstName").trim(),
						testData.getProperty("api.invalid.lastName").trim(), null, null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.internal").trim(), null, null,
						testData.getProperty("api.invalid.email.id").trim(), null, null, null, null, null,
						(testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
				{ testData.getProperty("user.type.internal").trim(), null, null, null, null,
						testData.getProperty("api.invalid.mobile").trim(), null, null, null,
						(testData.getProperty("api.page.no").trim()),
						(testData.getProperty("api.page.size").trim()) }, };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatainvalidinput", description = "Search Internal user  with invalid input  by keeping page 0 and page size 10")
	public void testSearchUserDetailsWithInvalidInput(String[] getSearchUserDetails)
			throws NullPointerException, IOException {
		Response searchInternalUserApiResponse = userInfo.getUsers_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Var/reseller user instead of Internal user & verify status code ")
	public void testSearchUserDetailsWithAnotherUserType() throws IOException {
		Response searchInternalUserApiResponse = userInfo.getUsers_DL(testData.getProperty("user.type.varreseller"),
				testData.getProperty("api.internal.firstName"), testData.getProperty("api.internal.lastName"),
				testData.getProperty("api.internal.email"), testData.getProperty("api.internal.userName"),
				testData.getProperty("api.internal.mobile"), null, null, null, (testData.getProperty("api.page.no")),
				(testData.getProperty("api.page.size")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidusertype", description = "Search Internal user using invalid url")
	public void testSearchInternalUserUsingInvalidUrl(String[] getSearchUserDetails) throws IOException {
		searchInternalUserApiResponse = userInfo.getUsersInvalidUrl_DL(getSearchUserDetails[0], getSearchUserDetails[1],
				getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4], getSearchUserDetails[5],
				getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8], getSearchUserDetails[9],
				getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(searchInternalUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidusertype", description = "Search Internal user using invalid method call")
	public void testSearchInternalUserUsingInvalidMethodCall(String[] getSearchUserDetails) throws IOException {
		searchInternalUserApiResponse = userInfo.invalidMethodCallGetUsers_DL(getSearchUserDetails[0],
				getSearchUserDetails[1], getSearchUserDetails[2], getSearchUserDetails[3], getSearchUserDetails[4],
				getSearchUserDetails[5], getSearchUserDetails[6], getSearchUserDetails[7], getSearchUserDetails[8],
				getSearchUserDetails[9], getSearchUserDetails[10]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchInternalUserApiResponse);
		CCUtils.assertEquals(searchInternalUserApiResponse.getStatusCode(), 405);
		validation.verifyErrorInResponseForInvalidMethod(searchInternalUserApiResponse);
	}
}