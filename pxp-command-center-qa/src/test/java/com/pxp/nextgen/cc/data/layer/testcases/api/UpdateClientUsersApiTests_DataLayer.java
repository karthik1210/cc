// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateClientUsersApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	private SharedInfo sharedInfo;
	Response createUserApiResponse, fetchUserDetailsApiResponse, updateClientUserDetailsApiResponse, createProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();
		sharedInfo = new SharedInfo();
	}

	public int createProvider(int practiceId) throws Exception {
		logStep("Create provider for providerId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		return createProviderApiResponse.jsonPath().get("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update client user details using user id")
	public void testFetchExistingClientUserAndModifyAllDetails() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		String emailone = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userNameone = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumberone = CCUtils.generateMobileNumber();
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify all the existing details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", firstName, lastName, userNameone,
				emailone, mobileNumberone, testData.getProperty("user.status.active"), clientId, null, providerId, null,
				roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUpdateClientResponseBody(updateClientUserDetailsApiResponse, userId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update null value in mandatory fields then validate error message")
	public void testErrorMessagesForMandatoryClientFields() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter null value in mandatory fields and validate error message");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", null, null, null, null, null,
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), null,
				testData.getProperty("api.provider.id"), null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyNullUserErrorMessageResponseBodyForClientUser(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update existing username then validate error message")
	public void testErrorMessagesForDuplicateClientUsername() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter existing username details of client user");
		String userNameone = createUserApiResponse.jsonPath().get("userName");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", firstName, lastName, userNameone,
				email, mobileNumber, testData.getProperty("user.status.active"), clientId, null, providerId, null,
				roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update existing email then validate error message")
	public void testErrorMessagesForDuplicateClientUserEmailId() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter existing email id details of Client user while updating email id");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", firstName, lastName, userName,
				testData.getProperty("valid.user.email"), mobileNumber, testData.getProperty("user.status.active"),
				clientId, null, providerId, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 409);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update existing mobile number then validate error message")
	public void testErrorMessagesForDuplicateClientUserMobileNumber() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		String userNameone = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify existing mobile nuber details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", firstName, lastName, userNameone,
				email, Long.parseLong(testData.getProperty("valid.user.mobile.number")),
				testData.getProperty("user.status.active"), clientId, null, providerId, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateClientMobileNumber(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and enter more than 100 character with combination of special characters in firstName,lastName & userName field also validate error message")
	public void testErrorMessagesForInvalidClientUserNameAndMaximumLength() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Enter more than 100 character in firstName lastName & UserName fields and validate error message");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client",
				testData.getProperty("api.invalid.clientUser.firstName"),
				testData.getProperty("api.invalid.clientUser.lastName"), testData.getProperty("api.invalid.userName"),
				email, mobileNumber, testData.getProperty("user.status.active"), clientId, null, providerId, null,
				roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForClientUserMaximumLength(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update invalid user type and validate error message")
	public void testErrorMessagesForClientInvalidUserType() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");

		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify invalid user type for Client user");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer(testData.getProperty("api.nonexisting.user.type"), firstName, lastName, userName, email, mobileNumber,
				testData.getProperty("user.status.active"), testData.getProperty("api.clientId.id"), clientId, null,
				null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and  update existing VAReseller user type with Client user type then validate error status code")
	public void testModifyExistingUserTypeWithDifferentUserType() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Update existing VAReseller user type with Client user type");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("VAReseller", firstName, lastName, userName,
				email, mobileNumber, testData.getProperty("user.status.active"),
				testData.getProperty("api.clientId.id"), null, testData.getProperty("api.provider.id"), null, roleIds,
				userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing userId & upadte Client user using valid data then validate error status code")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		logStep("Fetch existing client user details with existing client userid");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		int userId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Update existing VAReseller user type with Client user type");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", firstName, lastName, userName,
				email, mobileNumber, testData.getProperty("user.status.active"), clientId, null, providerId, null,
				roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientUserDetailsApiResponse);
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(updateClientUserDetailsApiResponse,
				(testData.getProperty("api.nonexisting.client.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update invalid mobile number then validate error message")
	public void testErrorMessageForInvalidClientUserMobileNumber() throws Exception {
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify existing mobile nuber details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", firstName, lastName, userName,
				email, Long.parseLong(testData.getProperty("invalid.mobile.number")),
				testData.getProperty("user.status.active"), clientId, null, providerId, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidMobile(updateClientUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch existing client user details and update invalid email then validate error message")
	public void testErrorMessageForinvalidClientUserEmail() throws Exception {
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int userId = createUserApiResponse.jsonPath().getInt("id");
		String emailOne = testData.getProperty("invalid.clientuser.email.id");
		String firstName = testData.getProperty("valid.user.first.name").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("valid.user.last.name").concat(CCUtils.generateAlphaNumericString(5));
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(userId);
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.clientUser.roleId")));
		logStep("Modify existing mobile nuber details of Client user");
		updateClientUserDetailsApiResponse = userInfo.updateUser_DataLayer("Client", firstName, lastName, userName,
				emailOne, mobileNumber, testData.getProperty("user.status.active"), clientId, null, providerId, null,
				roleIds, userId);
		logStep("Validating response information");
		CCUtils.assertEquals(updateClientUserDetailsApiResponse.getStatusCode(), 400);
	}
}
