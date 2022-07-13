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

public class UpdateClientUsersApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchUserDetailsApiResponse, updateClientUserDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update client user details using user id")
	public void testFetchExistingClientUserAndModifyAllDetails() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify all the existing details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", firstName, lastName,
				userName, email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUpdateClientResponseBody(updateClientUserDetailsApiResponse, userId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update null value in mandatory fields then validate error message")
	public void testErrorMessagesForMandatoryClientFields() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter null value in mandatory fields and validate error message");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", null, null, null, null,
				null, testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null,
				testData.getProperty("api.provider.id"), null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyNullUserErrorMessageResponseBodyForClientUser(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update existing username then validate error message")
	public void testErrorMessagesForDuplicateClientUsername() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter existing username details of client user");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", firstName, lastName,
				testData.getProperty("valid.username"), email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateClientUsername(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update existing email then validate error message")
	public void testErrorMessagesForDuplicateClientUserEmailId() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter existing email id details of Client user while updating email id");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", firstName, lastName,
				userName, testData.getProperty("valid.user.email"), mobileNumber,
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null,
				testData.getProperty("api.provider.id"), null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateClientEmailId(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update existing mobile number then validate error message")
	public void testErrorMessagesForDuplicateClientUserMobileNumber() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify existing mobile nuber details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", firstName, lastName,
				userName, email, Long.parseLong(testData.getProperty("valid.user.mobile.number")),
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null,
				testData.getProperty("api.provider.id"), null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateClientMobileNumber(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and enter more than 100 character with combination of special characters in firstName,lastName & userName field also validate error message")
	public void testErrorMessagesForInvalidClientUserNameAndMaximumLength() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter more than 100 character in firstName lastName & UserName fields and validate error message");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client",
				testData.getProperty("api.invalid.clientUser.firstName"),
				testData.getProperty("api.invalid.clientUser.lastName"), testData.getProperty("api.invalid.userName"),
				email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForClientUserMaximumLength(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update invalid user type and validate error message")
	public void testErrorMessagesForClientInvalidUserType() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify invalid user type for Client user");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null,
				testData.getProperty("api.nonexisting.user.type"), firstName, lastName, userName, email, mobileNumber,
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null,
				testData.getProperty("api.provider.id"), null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidClientUserType(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and enter invalid url and validate error status code")
	public void testErrorMessagesForUpdateClientUserWithInvalidUrl() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter invalid url and validate error status code");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayerUsingInvalidUrl(null, "Client",
				firstName, lastName, userName, email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update client user details with POST method then validate error status code")
	public void testErrorForClientUserInvalidMethodCall() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Call api with invalid method type which is POST");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayerUsingInvalidMethodCall(null, "Client",
				firstName, lastName, userName, email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and  update existing VAReseller user type with Client user type then validate error status code")
	public void testModifyExistingUserTypeWithDifferentUserType() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Update existing VAReseller user type with Client user type");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "VAReseller", firstName,
				lastName, userName, email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(updateClientUserDetailsApiResponse,
				(testData.getProperty("existing.client.userid")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and  enter non existing userId & upadte Client user using valid data then validate error status code")
	public void testErrorMessagesForNonExistingUserId() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Update existing VAReseller user type with Client user type");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", firstName, lastName,
				userName, email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(updateClientUserDetailsApiResponse,
				(testData.getProperty("api.nonexisting.client.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update invalid mobile number then validate error message")
	public void testErrorMessagesForinvalidClientUserMobileNumber() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify existing mobile nuber details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", firstName, lastName,
				userName, email, Long.parseLong(testData.getProperty("invalid.mobile.number")),
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null,
				testData.getProperty("api.provider.id"), null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidMobile(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update invalid email then validate error message")
	public void testErrorMessagesForinvalidClientUserEmail() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("invalid.clientuser.emailid");
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("existing.client.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify existing mobile nuber details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateClientUser_DataLayer(null, "Client", firstName, lastName,
				userName, email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInvalidClientEmailId(updateClientUserDetailsApiResponse);

	}
}
