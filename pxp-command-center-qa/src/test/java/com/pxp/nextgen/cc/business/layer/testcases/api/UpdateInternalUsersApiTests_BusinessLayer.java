// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateInternalUsersApiTests_BusinessLayer extends BaseRest {

	private UserInfo userInfo;
	private Validations validation;
	Response createUserApiResponse, fetchUserDetailsApiResponse, updateInternalUserDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update newly created internal user details using user id")
	public void testCreateNewInternalUserAndModifyAllDetails() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify all the existing details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.clientId.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and while updating Enter null value in mandatory fields then validate error message")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter null value in mandatory fields and validate error message");
		updateInternalUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("Internal", null, null, null, null,
				null, testData.getProperty("user.status.active"), null, testData.getProperty("api.clientId.id"), null,
				null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyNullUserErrorMessageResponseBodyForInternalUser(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and while updating enter existing username then validate error message")
	public void testErrorMessagesForDuplicateInternalUsername() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify existing username details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.username"), email, mobileNumber, testData.getProperty("user.status.active"),
				null, testData.getProperty("api.clientId.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateInternalUsername(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and while updating enter existing email then validate error message")
	public void testErrorMessagesForDuplicateInternalUserEmailId() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify existing email id details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				testData.getProperty("valid.user.email"), mobileNumber, testData.getProperty("user.status.active"),
				null, testData.getProperty("api.clientId.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateInternalUserEmailId(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and while updating enter existing mobile number then validate error message")
	public void testErrorMessagesForDuplicateInternalUserMobileNumber() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify existing mobile nuber details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, Long.parseLong(testData.getProperty("valid.user.mobile.number")),
				testData.getProperty("user.status.active"), null, testData.getProperty("api.clientId.id"), null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateInternalUserMobileNumber(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and while updating enter Enter more than 100 character with combination of special characters in firstName,lastName & userName field also validate error message")
	public void testErrorMessagesForInvalidInternalUserNameAndMaximumLength() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter more than 100 character in firstName lastName & UserName fields and validate error message");
		updateInternalUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("Internal",
				testData.getProperty("api.invalid.internalUser.firstName"),
				testData.getProperty("api.invalid.internalUser.lastName"), testData.getProperty("api.invalid.userName"),
				email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.clientId.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInternalUserMaximumLength(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and while updating enter invalid user type and validate error message")
	public void testErrorMessagesForInternalInvalidUserType() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify existing username details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateUser_BusinessLayer(
				testData.getProperty("api.nonexisting.user.type"), testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.username"), email,
				mobileNumber, testData.getProperty("user.status.active"), null, testData.getProperty("api.clientId.id"),
				null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
	}

}