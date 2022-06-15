// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class UpdateInternalUsersApiTests_DataLayer extends BaseRest {

	private UserInfo userInfo;
	private Validations validation;
	Response createUserApiResponse, fetchUserDetailsApiResponse, updateInternalUserDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update newly created internal user details using user id")
	public void testCreateNewInternalUserAndModifyAllDetails() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify all the existing details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null, "Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUpdateInternalResponseBody(updateInternalUserDetailsApiResponse, id);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update null value in mandatory fields then validate error message")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter null value in mandatory fields and validate error message");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null, "Internal", null, null, null,
				null, null, testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null,
				null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyNullUserErrorMessageResponseBodyForInternalUser(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user & update the existing username then validate error message")
	public void testErrorMessagesForDuplicateInternalUsername() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter existing username details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null, "Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.username"), email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateInternalUsernameInDataLayer(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update existing email then validate error message")
	public void testErrorMessagesForDuplicateInternalUserEmailId() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter existing email id details of Internal user while updating email id");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null, "Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				testData.getProperty("valid.user.email"), mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateInternalEmailIdInDataLayer(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update existing mobile number then validate error message")
	public void testErrorMessagesForDuplicateInternalUserMobileNumber() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify existing mobile nuber details of Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null, "Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, Long.parseLong(testData.getProperty("valid.user.mobile.number")),
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null, null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateInternalMobileNumberInDataLayer(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update more than 100 character with combination of special characters in firstName,lastName & userName field also validate error message")
	public void testErrorMessagesForInvalidInternalUserNameAndMaximumLength() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter more than 100 character in firstName lastName & UserName fields and validate error message");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null, "Internal",
				testData.getProperty("api.invalid.internalUser.firstName"),
				testData.getProperty("api.invalid.internalUser.lastName"), testData.getProperty("api.invalid.userName"),
				email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInternalUserMaximumLength(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update invalid usertype then validate error message")
	public void testErrorMessagesForInternalInvalidUserType() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify invalid user type for Internal user");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null,
				testData.getProperty("api.nonexisting.user.type"), testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), userName, email, mobileNumber,
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null, null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidInternalUserType(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update invalid url and validate error status code")
	public void testErrorMessagesForUpdateInternalUserWithInvalidUrl() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter invalid url and validate error status code");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayerUsingInvalidUrl(null, "Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update the details with invalid method call then validate error message")
	public void testErrorForInternalUserInvalidMethodCall() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Call api with invalid method type which is POST");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayerUsingInvalidMethodCall(null,
				"Internal", testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				userName, email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInternalUserInvalidMethod(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update existing created VAReseller user type with Client type using user id and validate error status code")
	public void testModifyExistingUserTypeWithDifferentUserType() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify existing user type from Internal to VAReseller user");
		updateInternalUserDetailsApiResponse = userInfo.updateInternalUser_DataLayer(null, "Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, Long.parseLong(testData.getProperty("valid.user.mobile.number")),
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null, null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateInternalMobileNumberInDataLayer(updateInternalUserDetailsApiResponse);
	}

}
