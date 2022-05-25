
// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class CreateUsersApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response createUserApiResponse, fetchUserDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		logStep("Enter invalid url and validate error status code");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayerUsingInvalidUrl(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		AssertJUnit.assertEquals(createUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in mandatory fields and validate error message")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		logStep("Enter null value in mandatory fields and validate error message");
		String authId = CCUtils.generateAuthId();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal", null, null, null, null, null, null,
				null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		AssertJUnit.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyNullUserErrorMessageResponseBody(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing username and validate error message ")
	public void testErrorMessagesForExistingUsername() throws IOException {
		logStep("Create user using existing username");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("existing.internalUser.userName");
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForDuplicateUsername(createUserApiResponse,
				testData.getProperty("existing.internalUser.userName"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing email and validate error message ")
	public void testErrorMessagesForExistingEmail() throws IOException {
		logStep("Create user using existing email");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.existing.email");
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForDuplicateEmail(createUserApiResponse,
				testData.getProperty("api.existing.email"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 character in username field and validate error message ")
	public void testErrorMessagesForInvalidUsername() throws IOException {
		logStep("Create user using more than 100 character in username field");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.invalid.userName");
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), 
				userName, email, mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null,
				roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUsername(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 character in firstName field and validate error message ")
	public void testErrorMessagesForInvalidFirstName() throws IOException {
		logStep("Create user using more than 100 character in firstName field");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName");
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.invalid.internalUser.firstName"), testData.getProperty("api.user.lastName"),
				userName, email, mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null,
				roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidFirstName(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid user type and validate error message ")
	public void testErrorMessagesForInvalidUserType() throws IOException {
		logStep("Create user using invalid user type");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName");
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId,
				testData.getProperty("api.nonexisting.user.type"), testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserType(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid mobile number and validate error message ")
	public void testErrorMessagesForInvalidMobile() throws IOException {
		logStep("Create user using invalid mobile number");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName");
		Long mobileNumber = CCUtils.generateinvalidMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidMobile(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter only speatial character firstName & lastName and validate error message ")
	public void testErrorMessagesForOnlySpeatialCharacterInFirstAndLastName() throws IOException {
		logStep("Create user using invalid firstName & lastName");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.invalid.firstName"), testData.getProperty("api.invalid.lastName"), userName,
				email, mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForSpetialCharacterInFirstName(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create client user using invalid  clientId and validate error message ")
	public void testErrorMessagesForInvalidClientId() throws IOException {
		logStep("Create client user using invalid  clientId ");
		String authId = CCUtils.generateAuthId();
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Client",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, "4080", null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidClientId(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create client user invalid providerId and validate error message ")
	public void testErrorMessagesForInvalidProviderId() throws IOException {
		logStep("Create client user using invalid providerId ");
		String authId = CCUtils.generateAuthId();
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Client",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "4058", null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidProviderId(createUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user  and fetch newly created internal user details using user id")
	public void testCreateNewInternalUser() throws IOException {
		logStep("Create internal user using valid details ");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		String expiryDate = testData.getProperty("internalUser.status.msg").concat(CCUtils.dateformatter());
		String id = createUserApiResponse.jsonPath().getString("id");
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse, authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber.toString(), testData.getProperty("api.user.userStatus"), expiryDate);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new client user  and fetch newly created client user details using user id")
	public void testCreateNewClientUser() throws IOException {
		logStep("Create client user using valid details ");
		String authId = CCUtils.generateAuthId();
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Client",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), testData.getProperty("api.clientId.id"),
				null, testData.getProperty("api.provider.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		String id = createUserApiResponse.jsonPath().getString("id");
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyClientUserDetailsResponseBody(fetchUserDetailsApiResponse, authId, "Client",
				testData.getProperty("api.client.firstName"), testData.getProperty("api.client.lastName"), userName,
				email, mobileNumber.toString(), testData.getProperty("api.client.userStatus"),
				testData.getProperty("api.client.id"), testData.getProperty("api.client.name"),
				testData.getProperty("api.provider.id"), testData.getProperty("api.provider.name"),
				testData.getProperty("api.status.msg"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Var/Reseller user  and fetch newly created Var/Reseller user details using user id")
	public void testCreateNewVarResellerUser() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		String authId = CCUtils.generateAuthId();
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("varReseller.user.userStatus"), null,
				testData.getProperty("api.reseller.id"), null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		String id = createUserApiResponse.jsonPath().getString("id");
		fetchUserDetailsApiResponse = userInfo.getUserDetails_DL(id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse, authId, "VAReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber.toString(), testData.getProperty("varReseller.user.userStatus"),
				testData.getProperty("api.status.msg"));
	}
}
