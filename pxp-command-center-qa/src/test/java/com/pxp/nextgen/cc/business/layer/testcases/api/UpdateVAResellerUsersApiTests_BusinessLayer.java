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

public class UpdateVAResellerUsersApiTests_BusinessLayer extends BaseRest {

	private UserInfo userInfo;
	private Validations validation;
	Response createUserApiResponse, fetchUserDetailsApiResponse, updateVARResellerUserDetailsApiResponse;
	public int userId;
	List<Integer> roleIds;
	String email, userName;
	Long mobileNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
		userId = createUser();
	}

	public int createUser() throws NullPointerException, IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.varReseller.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("VARReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null,
				testData.getProperty("api.resellerId"), null, roleIds, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Updated newly created Var/Reseller user details using user id")
	public void testCreateNewVarResellerUser() throws IOException {
		logStep("Modify all the existing details of VARReseller user");
		email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		mobileNumber = CCUtils.generateMobileNumber();
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("VARReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.reseller.id"), null, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update null value in mandatory fields then validate error message")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		logStep("Enter null value in mandatory fields and validate error message");
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("VARReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), null, null,
				null, testData.getProperty("user.status.active"), null, null, null, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForNullUsernameAndEmailField(updateVARResellerUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update the existing username then validate error message")
	public void testErrorMessagesForDuplicateVARResellerUsername() throws IOException {
		logStep("Enter existing username details of VARReseller user");
		email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		mobileNumber = CCUtils.generateMobileNumber();
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("VARReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.username"), email, mobileNumber, testData.getProperty("user.status.active"),
				null, testData.getProperty("api.reseller.id"), null, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 409);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update existing email then validate error message")
	public void testErrorMessagesForDuplicateVARResellerUserUserEmailId() throws IOException {
		logStep("Enter existing email id details of VARReseller user while updating email id");
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("VARReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				testData.getProperty("valid.user.email"), mobileNumber, testData.getProperty("user.status.active"),
				null, testData.getProperty("api.reseller.id"), null, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 409);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update existing mobile number then validate error message")
	public void testErrorMessagesForDuplicateVARResellerUserMobileNumber() throws IOException {
		logStep("Modify existing mobile nuber details of VARReseller user");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("VARReseller",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.reseller.id"), null, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update more than 100 character with combination of special characters in firstName,lastName & userName field also validate error message")
	public void testErrorMessagesForInvalidVARResellerUserNameAndMaximumLength() throws IOException {
		logStep("Enter more than 100 character in firstName lastName & UserName fields and validate error message");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(100));
		String firstName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(100));
		String lastName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(100));
		mobileNumber = CCUtils.generateMobileNumber();
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("VARReseller", firstName, lastName,
				userName, email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.reseller.id"), null, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 400);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update invalid usertype then validate error message")
	public void testErrorMessagesForVARResellerInvalidUserType() throws IOException {
		logStep("Modify invalid user type for VARReseller user");
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer(
				testData.getProperty("api.nonexisting.user.type"), testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), userName, email, mobileNumber,
				testData.getProperty("user.status.active"), null, testData.getProperty("api.reseller.id"), null, null,
				roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user type as Internal then validate error status code")
	public void testModifyExistingUserTypeWithDifferentUserType() throws IOException {
		logStep("Modify existing user type from VARReseller to Internal user");
		updateVARResellerUserDetailsApiResponse = userInfo.updateUser_BusinessLayer("Internal",
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"), userName,
				email, mobileNumber, testData.getProperty("user.status.active"), null,
				testData.getProperty("api.reseller.id"), null, null, roleIds, userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateVARResellerUserDetailsApiResponse);
		CCUtils.assertEquals(updateVARResellerUserDetailsApiResponse.getStatusCode(), 400);
	}

}
