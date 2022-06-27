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

public class UpdateVAResellerUsersApiTests_DataLayer extends BaseRest {

	private UserInfo userInfo;
	private Validations validation;
	Response createUserApiResponse, updateVAResellerUserDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and updated newly created Var/Reseller user details using user id")
	public void testCreateNewVarResellerUser() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify all the existing details of VAReseller user");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null, "VAReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.reseller.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUpdateVAResellerResponseBody(updateVAResellerUserDetailsApiResponse, id);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and update null value in mandatory fields then validate error message")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter null value in mandatory fields and validate error message");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null, "VAReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), null, null,
				null, testData.getProperty("user.status.active"), null, null, null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyNullUserErrorMessageResponseBodyForVAResellerUserDataLayer(
				updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user & update the existing username then validate error message")
	public void testErrorMessagesForDuplicateVAResellerUsername() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter existing username details of VAReseller user");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null, "VAReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.username"), email, mobileNumber, testData.getProperty("user.status.active"),
				null, testData.getProperty("api.reseller.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateVAResellerUsername(updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and update existing email then validate error message")
	public void testErrorMessagesForDuplicateVAResellerUserUserEmailId() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter existing email id details of VAReseller user while updating email id");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null, "VAReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				testData.getProperty("valid.user.email"), mobileNumber, testData.getProperty("user.status.active"),
				null, testData.getProperty("api.reseller.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateVAResellerEmailId(updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and update existing mobile number then validate error message")
	public void testErrorMessagesForDuplicateVAResellerUserMobileNumber() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify existing mobile nuber details of VAReseller user");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null, "VAReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, Long.parseLong(testData.getProperty("valid.user.mobile.number")),
				testData.getProperty("user.status.active"), null, testData.getProperty("api.reseller.id"), null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateVAResellerMobileNumber(updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and update more than 100 character with combination of special characters in firstName,lastName & userName field also validate error message")
	public void testErrorMessagesForInvalidVAResellerUserNameAndMaximumLength() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter more than 100 character in firstName lastName & UserName fields and validate error message");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.invalid.varesellerUser.firstName"),
				testData.getProperty("api.invalid.varesellerUser.lastName"),
				testData.getProperty("api.invalid.userName"), email, mobileNumber,
				testData.getProperty("user.status.active"), null, testData.getProperty("api.reseller.id"), null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForVAResellerUserMaximumLength(updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and update invalid usertype then validate error message")
	public void testErrorMessagesForVAResellerInvalidUserType() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify invalid user type for VAReseller user");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null,
				testData.getProperty("api.nonexisting.user.type"), testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), userName, email, mobileNumber,
				testData.getProperty("user.status.active"), null, testData.getProperty("api.reseller.id"), null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidVAResellerUserType(updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and update invalid url and validate error status code")
	public void testErrorMessagesForUpdateVAResellerUserWithInvalidUrl() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter invalid url and validate error status code");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayerUsingInvalidUrl(null,
				"VAReseller", testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), userName, email, mobileNumber,
				testData.getProperty("user.status.active"), null, testData.getProperty("api.reseller.id"), null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user and update the details with invalid method call then validate error message")
	public void testErrorForVAResellerUserInvalidMethodCall() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Call api with invalid method type which is POST");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_InvalidPostMethodCallDataLayer(null,
				"VAReseller", testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), userName, email, mobileNumber,
				testData.getProperty("user.status.active"), null, testData.getProperty("api.reseller.id"), null, null,
				roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateVAResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Created VAReseller user & update user type as Internal then validate error status code")
	public void testModifyExistingUserTypeWithDifferentUserType() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		int id = Integer.parseInt(testData.getProperty("existing.user.id"));
		logStep("Modify existing user type from VAReseller to Internal user");
		updateVAResellerUserDetailsApiResponse = userInfo.updateVAResellerUser_DataLayer(null, "Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.reseller.id"), null, null, roleIds, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVAResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVAResellerUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForVAResellerUserInvalidUserType(updateVAResellerUserDetailsApiResponse);
	}

}
