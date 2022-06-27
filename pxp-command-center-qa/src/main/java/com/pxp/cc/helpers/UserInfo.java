// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class UserInfo extends BaseRest {

	public Response getUserRoles(String userType) {
		String url = ApiPath.USER_ROLES_BL(userType);
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

	public Response getUserRolesUsingInvalidUrl(String userType) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_BL(userType);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getUserRoles_DataLayer(String userType) {
		String url = ApiPath.USER_ROLES_DL(userType);
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

	public Response getUserRolesUsingInvalidUrl_DataLayer(String userType) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_DL(userType);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUser(int userId) {
		String url = ApiPath.USER_ROLES_BY_USER_BL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUserUsingInvalidUrl(int userId) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_BY_USER_BL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUser_DataLayer(int userId) {
		String url = ApiPath.USER_ROLES_BY_USER_DL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUserUsingInvalidUrl_DataLayer(int userId) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_BY_USER_DL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response validateUserDetails(String email, String username, String mobileNumber) {
		String url = ApiPath.VALIDATE_USER_BL();
		return given().spec(requestSpec).queryParam("email", email).queryParam("username", username)
				.queryParam("mobile", mobileNumber).when().get(url).then().extract().response();
	}

	public Response validateUserDetailsUsingInvalidUrl(String email, String username, String mobileNumber) {
		String url = ApiPath.INVALID_PATH_VALIDATE_USER_BL();
		return given().spec(requestSpec).queryParam("email", email).queryParam("username", username)
				.queryParam("mobile", mobileNumber).when().get(url).then().extract().response();
	}

	public Response getUserDetails(int userId) {
		String url = ApiPath.USER_DETAILS_BL + userId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getUserDetailsInvalidUrl(String userId) {
		String url = ApiPath.INVALID_PATH_USER_DETAILS_BL + userId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response validateUserDetails_DL(String email, String username, String mobileNumber) {
		String url = ApiPath.VALIDATE_USER_DL();
		return given().spec(requestSpec).queryParam("email", email).queryParam("username", username)
				.queryParam("mobile", mobileNumber).when().get(url).then().extract().response();
	}

	public Response validateUserDetailsUsingInvalidUrl_DL(String email, String username, String mobileNumber) {
		String url = ApiPath.INVALID_PATH_VALIDATE_USER_DL();
		return given().spec(requestSpec).queryParam("email", email).queryParam("username", username)
				.queryParam("mobile", mobileNumber).when().get(url).then().extract().response();
	}

	public Response createNewUser_DataLayer(String authId, String userType, String firstName, String lastName,
			String userName, String email, Long mobile, String userStatus, Object clientId, Object resellerId,
			Object providerId, String expiryDate, List<Integer> roleIds) throws IOException {
		Map<String, Object> createNewUserPayload = postCreateUser(authId, userType, firstName, lastName, userName,
				email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		return given().spec(requestSpec).body(createNewUserPayload).when().post(ApiPath.USER_DL).then().extract()
				.response();

	}

	public Response createNewUser_DataLayerUsingInvalidUrl(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds) throws IOException {
		Map<String, Object> createNewUserPayload = postCreateUser(authId, userType, firstName, lastName, userName,
				email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String invalidUrl = ApiPath.INVALID_PATH_USER_DL;
		return given().spec(requestSpec).body(createNewUserPayload).when().post(invalidUrl).then().extract().response();
	}

	private Map<String, Object> postCreateUser(String authId, String userType, String firstName, String lastName,
			String userName, String email, Long mobile, String userStatus, Object clientId, Object resellerId,
			Object providerId, String expiryDate, List<Integer> roleIds) {
		Map<String, Object> createUsertMap = new HashMap<String, Object>();
		createUsertMap.put("authId", authId);
		createUsertMap.put("userType", userType);
		createUsertMap.put("firstName", firstName);
		createUsertMap.put("lastName", lastName);
		createUsertMap.put("userName", userName);
		createUsertMap.put("email", email);
		createUsertMap.put("mobile", mobile);
		createUsertMap.put("userStatus", userStatus);
		createUsertMap.put("clientId", clientId);
		createUsertMap.put("resellerId", resellerId);
		createUsertMap.put("providerId", providerId);
		createUsertMap.put("expiryDate", expiryDate);
		createUsertMap.put("roleIds", roleIds);

		return createUsertMap;
	}

	public Response createNewUser_BusinessLayer(String ClientId, String PoolId, String authId, String userType,
			String firstName, String lastName, String userName, String email, Long mobile, String userStatus,
			Object clientId, Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds)
			throws IOException {
		Map<String, Object> createNewUserPayload = postCreateUser(authId, userType, firstName, lastName, userName,
				email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.USER_BL;
		return given().spec(requestSpec).header("ClientId", ClientId).header("PoolId", PoolId)
				.body(createNewUserPayload).when().post(url).then().extract().response();
	}

	public Response createNewUser_BusinessLayerUsingInvalidUrl(String ClientId, String PoolId, String authId,
			String userType, String firstName, String lastName, String userName, String email, Long mobile,
			String userStatus, Object clientId, Object resellerId, Object providerId, String expiryDate,
			List<Integer> roleIds) throws IOException {
		Map<String, Object> createNewUserPayload = postCreateUser(authId, userType, firstName, lastName, userName,
				email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String invalidUrl = ApiPath.INVALID_PATH_USER_BL;
		return given().spec(requestSpec).header("ClientId", ClientId).header("PoolId", PoolId)
				.body(createNewUserPayload).when().post(invalidUrl).then().extract().response();
	}

	public static Response getUserDetails_DL(String userId) {
		String url = ApiPath.USER_DETAILS_DL + userId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public static Response getUserDetailsInvalidUrl_DL(String userId) {
		String url = ApiPath.INVALID_PATH_USER_DL + userId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public static Response invalidMethodCall_DL(String userId) {
		String url = ApiPath.USER_DL;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getAssociateEntities(int userId) {
		String url = ApiPath.ASSOCIATE_ENTITIES_BL;
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getAssociateEntitiesInvalidUrl(int userId) {
		String url = ApiPath.ASSOCIATE_ENTITIES_INVALID_URL_BL;
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response invalidMethodCallGetAssociateEntities(int userId) {
		String url = ApiPath.ASSOCIATE_ENTITIES_BL;
		return given().spec(requestSpec).pathParam("userId", userId).when().put(url).then().extract().response();
	}

	public Response addAssociateEntitiesInvalidUrl(int userId, ArrayList<Object> orgIDs) {
		List<Object> associatedEntitiesPayload = postassociatedEntities(orgIDs);
		String invalidurl = ApiPath.ADD_ASSOCIATE_ENTITIES_INVALID_URL_BL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).body(associatedEntitiesPayload).when()
				.post(invalidurl).then().extract().response();
	}

	public Response invalidMethodCall(int userId, ArrayList<Object> orgIDs) {
		List<Object> associatedEntitiesPayload = postassociatedEntities(orgIDs);
		String url = ApiPath.ADD_ASSOCIATE_ENTITIES_BL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).body(associatedEntitiesPayload).when().put(url)
				.then().extract().response();
	}

	public Response addAssociateEntities(int userId, ArrayList<Object> orgIDs) {
		List<Object> associatedEntitiesPayload = postassociatedEntities(orgIDs);
		String url = ApiPath.ADD_ASSOCIATE_ENTITIES_BL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).body(associatedEntitiesPayload).when().post(url)
				.then().extract().response();
	}

	private List<Object> postassociatedEntities(ArrayList<Object> orgID) {
		List<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.addAll(orgID);
		return OrgIDs;

	}

	public Response getUserDetails_DL(int userId) {
		String url = ApiPath.USER_DETAILS_DL + userId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response updateInternalUser_BusinessLayer(String authId, String userType, String firstName, String lastName,
			String userName, String email, Long mobile, String userStatus, Object clientId, Object resellerId,
			Object providerId, String expiryDate, List<Integer> roleIds, Integer id) throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.INTERNAL_USER_BL + id;
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(url).then().extract().response();
	}

	public Response updateInternalUser_BusinessLayerUsingInvalidUrl(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String invalidUrl = ApiPath.INVALID_PATH_INTERNALUSER_BL + id;
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(invalidUrl).then().extract()
				.response();
	}

	public Response updateInternalUser_BusinessLayerUsingInvalidMethodCall(String authId, String userType,
			String firstName, String lastName, String userName, String email, Long mobile, String userStatus,
			Object clientId, Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.INTERNAL_USER_BL + id;
		return given().spec(requestSpec).body(updateInternalUserPayload).when().post(url).then().extract().response();
	}

	private Map<String, Object> putUpdateInternalUser(String authId, String userType, String firstName, String lastName,
			String userName, String email, Long mobile, String userStatus, Object clientId, Object resellerId,
			Object providerId, String expiryDate, List<Integer> roleIds) {
		Map<String, Object> updateInternalUsertMap = new HashMap<String, Object>();
		updateInternalUsertMap.put("authId", authId);
		updateInternalUsertMap.put("userType", userType);
		updateInternalUsertMap.put("firstName", firstName);
		updateInternalUsertMap.put("lastName", lastName);
		updateInternalUsertMap.put("userName", userName);
		updateInternalUsertMap.put("email", email);
		updateInternalUsertMap.put("mobile", mobile);
		updateInternalUsertMap.put("userStatus", userStatus);
		updateInternalUsertMap.put("clientId", clientId);
		updateInternalUsertMap.put("resellerId", resellerId);
		updateInternalUsertMap.put("providerId", providerId);
		updateInternalUsertMap.put("expiryDate", expiryDate);
		updateInternalUsertMap.put("roleIds", roleIds);

		return updateInternalUsertMap;
	}

	private Map<String, Object> putUpdateClientUser(String authId, String userType, String firstName, String lastName,
			String userName, String email, Long mobile, String userStatus, Object clientId, Object resellerId,
			Object providerId, String expiryDate, List<Integer> roleIds) {
		Map<String, Object> updateInternalUsertMap = new HashMap<String, Object>();
		updateInternalUsertMap.put("authId", authId);
		updateInternalUsertMap.put("userType", userType);
		updateInternalUsertMap.put("firstName", firstName);
		updateInternalUsertMap.put("lastName", lastName);
		updateInternalUsertMap.put("userName", userName);
		updateInternalUsertMap.put("email", email);
		updateInternalUsertMap.put("mobile", mobile);
		updateInternalUsertMap.put("userStatus", userStatus);
		updateInternalUsertMap.put("clientId", clientId);
		updateInternalUsertMap.put("resellerId", resellerId);
		updateInternalUsertMap.put("providerId", providerId);
		updateInternalUsertMap.put("expiryDate", expiryDate);
		updateInternalUsertMap.put("roleIds", roleIds);
		return updateInternalUsertMap;
	}

	public Response patchInternalUser_BusinessLayer(String userStatus, Integer id) throws IOException {
		Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
		String url = ApiPath.INTERNAL_USER_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().patch(url).then().extract().response();
	}

	public Response patchInternalUser_BusinessLayerUsingInvalidUrl(String userStatus, Integer id) throws IOException {
		Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
		String url = ApiPath.INVALID_PATH_INTERNAL_USER_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().patch(url).then().extract().response();
	}

	public Response patchInternalUser_BusinessLayerUsingInvalidMethodCall(String userStatus, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
		String url = ApiPath.INTERNAL_USER_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().post(url).then().extract().response();
	}

	private Map<String, Object> patchInternalUserStatus(String userStatus) {
		Map<String, Object> updateInternalUsertMap = new HashMap<String, Object>();
		updateInternalUsertMap.put("userStatus", userStatus);
		return updateInternalUsertMap;
	}

	public Response updateInternalUser_DataLayer(String authId, String userType, String firstName, String lastName,
			String userName, String email, Long mobile, String userStatus, Object clientId, Object resellerId,
			Object providerId, String expiryDate, List<Integer> roleIds, Integer id) throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.INTERNAL_USER_DATA(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(url).then().extract().response();
	}

	public Response updateInternalUser_DataLayerUsingInvalidUrl(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String invalidUrl = ApiPath.INVALID_INTERNAL_USER_DATA(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(invalidUrl).then().extract()
				.response();
	}

	public Response updateInternalUser_DataLayerUsingInvalidMethodCall(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.INTERNAL_USER_DATA(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().post(url).then().extract().response();
	}

	public Response updateClientUser_BusinessLayer(String authId, String userType, String firstName, String lastName,
			String userName, String email, Long mobile, String userStatus, Object clientId, Object resellerId,
			Object providerId, String expiryDate, List<Integer> roleIds, Integer userId) throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateClientUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.CLIENT_USER_SERVICE(userId);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(url).then().extract().response();
	}

	public Response updateClientUser_BusinessLayerUsingInvalidUrl(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer userId)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateClientUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String invalidUrl = ApiPath.INVALID_PATH_CLIENT_BL(userId);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(invalidUrl).then().extract()
				.response();
	}

	public Response updateClientUser_BusinessLayerUsingInvalidMethodCall(String authId, String userType,
			String firstName, String lastName, String userName, String email, Long mobile, String userStatus,
			Object clientId, Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds,
			Integer userId) throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateClientUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.CLIENT_USER_SERVICE(userId);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().post(url).then().extract().response();
	}

	public Response updateVAResellerUser_BusinessLayer(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.VAReseller_USER_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(url).then().extract().response();
	}

	public Response updateVAResellerUser_BusinessLayerUsingInvalidUrl(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String invalidUrl = ApiPath.INVALID_PATH_VAReseller_USER_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(invalidUrl).then().extract()
				.response();
	}

	public Response updateVAResellerUser_InvalidPostMethodCall(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.VAReseller_USER_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().post(url).then().extract().response();
	}
	
	public Response updateVAResellerUser_DataLayer(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.VAReseller_USER_DATA_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(url).then().extract().response();
	}
	
	public Response updateVAResellerUser_DataLayerUsingInvalidUrl(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String invalidUrl = ApiPath.INVALID_PATH_VAReseller_USER_DATA_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().put(invalidUrl).then().extract()
				.response();
	}
	
	public Response updateVAResellerUser_InvalidPostMethodCallDataLayer(String authId, String userType, String firstName,
			String lastName, String userName, String email, Long mobile, String userStatus, Object clientId,
			Object resellerId, Object providerId, String expiryDate, List<Integer> roleIds, Integer id)
			throws IOException {
		Map<String, Object> updateInternalUserPayload = putUpdateInternalUser(authId, userType, firstName, lastName,
				userName, email, mobile, userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
		String url = ApiPath.VAReseller_USER_DATA_SERVICE(id);
		return given().spec(requestSpec).body(updateInternalUserPayload).when().post(url).then().extract().response();
	}

	public Response getAssociateEntitiesDL(int userId) {
		String url = ApiPath.ASSOCIATE_ENTITIES_DL;
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getAssociateEntitiesInvalidUrlDL(int userId) {
		String url = ApiPath.ASSOCIATE_ENTITIES_INVALID_URL_DL;
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response invalidMethodCallGetAssociateEntitiesDL(int userId) {
		String url = ApiPath.ASSOCIATE_ENTITIES_DL;
		return given().spec(requestSpec).pathParam("userId", userId).when().put(url).then().extract().response();
	}

	public Response addAssociateEntities_DL(int userId, ArrayList<Object> orgIDs, int pxp_cc_user) {
		List<Object> associatedEntitiesPayload = associatedEntities_DL(orgIDs);
		String url = ApiPath.ADD_ASSOCIATE_ENTITIES_DL(userId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, pxp_cc_user).pathParam("userId", userId)
				.body(associatedEntitiesPayload).when().post(url).then().extract().response();
	}

	public Response getAssociateEntities_DL(int userId, int pxp_cc_user) {
		String url = ApiPath.ASSOCIATE_ENTITIES_DL;
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, pxp_cc_user).pathParam("userId", userId).when()
				.get(url).then().extract().response();
	}

	private List<Object> associatedEntities_DL(ArrayList<Object> orgID) {
		List<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.addAll(orgID);
		return OrgIDs;

	}

	public Response addAssociateEntitiesInvalidUrl_DL(int userId, ArrayList<Object> orgIDs, int pxp_cc_user) {
		List<Object> associatedEntitiesPayload = associatedEntities_DL(orgIDs);
		String invalidurl = ApiPath.ADD_ASSOCIATE_ENTITIES_INVALID_URL_DL(userId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, pxp_cc_user).pathParam("userId", userId)
				.body(associatedEntitiesPayload).when().post(invalidurl).then().extract().response();
	}

	public Response invalidMethodCall_DL(int userId, ArrayList<Object> orgIDs, int pxp_cc_user) {
		List<Object> associatedEntitiesPayload = associatedEntities_DL(orgIDs);
		String url = ApiPath.ADD_ASSOCIATE_ENTITIES_DL(userId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, pxp_cc_user).pathParam("userId", userId)
				.body(associatedEntitiesPayload).when().put(url).then().extract().response();
	}

	public Response getUserlogin(int userId) {
		String url = ApiPath.LOGIN_DETAIL_BL(userId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getUserloginInvalidUrl(int userId) {
		String url = ApiPath.LOGIN_DETAIL_INVALID_URL_BL(userId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCallGetUserlogin(int userId) {
		String url = ApiPath.LOGIN_DETAIL_BL(userId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response activateDeactivateUsers_DL(String status, ArrayList<Integer> userIds) {
		Map<String, Object> activateDeactivateUsersPayload = activateDeactivateUsers(status, userIds);
		String url = ApiPath.ACTIVATE_DEACTIVATE_USER_DL;
		return given().spec(requestSpec).body(activateDeactivateUsersPayload).when().put(url).then().extract()
				.response();
	}

	public Response activateDeactivateUsersUsingInvalidMethod_DL(String status, ArrayList<Integer> userIds) {
		Map<String, Object> activateDeactivateUsersPayload = activateDeactivateUsers(status, userIds);
		String url = ApiPath.ACTIVATE_DEACTIVATE_USER_DL;
		return given().spec(requestSpec).body(activateDeactivateUsersPayload).when().post(url).then().extract()
				.response();
	}

	public Response activateDeactivateUsersUsingInvalidUrl_DL(String status, ArrayList<Integer> userIds) {
		Map<String, Object> activateDeactivateUsersPayload = activateDeactivateUsers(status, userIds);
		String url = ApiPath.ACTIVATE_DEACTIVATE_USER_INVALID_URL_DL;
		return given().spec(requestSpec).body(activateDeactivateUsersPayload).when().put(url).then().extract()
				.response();
	}

	private Map<String, Object> activateDeactivateUsers(String status, List<Integer> userIds) {
		Map<String, Object> activateDeactivateUsersMap = new HashMap<String, Object>();
		activateDeactivateUsersMap.put("status", status);
		activateDeactivateUsersMap.put("userIds", userIds);
		return activateDeactivateUsersMap;
	}

	public Response patchInternalUserRole_BL(int userId, int orgId, String roleId, String active) throws IOException {
		Map<String, Object> roleAssociationPayload = patchRoleAssociation(roleId, active);
		String url = ApiPath.PATCH_INTERNAL_USER_ROLES_BL(userId, orgId);
		return given().spec(requestSpec).body(roleAssociationPayload).when().patch(url).then().extract().response();
	}

	public Response patchInternalUserRoleInvalidUrl_BL(int userId, int orgId, String roleId, String active)
			throws IOException {
		Map<String, Object> roleAssociationPayload = patchRoleAssociation(roleId, active);
		String url = ApiPath.PATCH_INTERNAL_USER_ROLES_INVALID_URL_BL(userId, orgId);
		return given().spec(requestSpec).body(roleAssociationPayload).when().patch(url).then().extract().response();
	}

	public Response patchInternalUserRoleInvalidMethod_BL(int userId, int orgId, String roleId, String active)
			throws IOException {
		Map<String, Object> roleAssociationPayload = patchRoleAssociation(roleId, active);
		String url = ApiPath.PATCH_INTERNAL_USER_ROLES_BL(userId, orgId);
		return given().spec(requestSpec).body(roleAssociationPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> patchRoleAssociation(String roleId, String active) {
		Map<String, Object> updateroleAssociatioMap = new HashMap<String, Object>();
		updateroleAssociatioMap.put("roleId", roleId);
		updateroleAssociatioMap.put("active", active);
		return updateroleAssociatioMap;
	}

	public Response updateUserRoles(int userId, ArrayList<Integer> roleIds) {
		String url = ApiPath.UPDATE_USER_ROLE_BL(userId);
		return given().spec(requestSpec).body(roleIds).when().put(url).then().extract().response();
	}

	public Response updateUserRolesUsingInvalidMethodCall(int userId, ArrayList<Integer> roleIds) {
		String url = ApiPath.UPDATE_USER_ROLE_BL(userId);
		return given().spec(requestSpec).body(roleIds).when().post(url).then().extract().response();
	}

	public Response updateUserRolesUsingInvalidUrl(int userId, ArrayList<Integer> roleIds) {
		String url = ApiPath.INVALID_PATH_UPDATE_USER_ROLE_BL(userId);
		return given().spec(requestSpec).body(roleIds).when().put(url).then().extract().response();
	}

	public Response activateDeactivateUsers_BL(String status, ArrayList<Integer> userIds) {
		Map<String, Object> activateDeactivateUsersPayload = activateDeactivateUsers(status, userIds);
		String url = ApiPath.ACTIVATE_DEACTIVATE_USER_BL;
		return given().spec(requestSpec).body(activateDeactivateUsersPayload).when().put(url).then().extract()
				.response();
	}

	public Response activateDeactivateUsersUsingInvalidMethod_BL(String status, ArrayList<Integer> userIds) {
		Map<String, Object> activateDeactivateUsersPayload = activateDeactivateUsers(status, userIds);
		String url = ApiPath.ACTIVATE_DEACTIVATE_USER_BL;
		return given().spec(requestSpec).body(activateDeactivateUsersPayload).when().post(url).then().extract()
				.response();
	}

	public Response activateDeactivateUsersUsingInvalidUrl_BL(String status, ArrayList<Integer> userIds) {
		Map<String, Object> activateDeactivateUsersPayload = activateDeactivateUsers(status, userIds);
		String url = ApiPath.ACTIVATE_DEACTIVATE_USER_INVALID_URL_BL;
		return given().spec(requestSpec).body(activateDeactivateUsersPayload).when().put(url).then().extract()
				.response();
	}
	
	public Response getInternalUserlogin(int userId) {
		String url = ApiPath.LOGIN_DETAIL_DL(userId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getInternalUserloginInvalidUrl(int userId) {
		String url = ApiPath.LOGIN_DETAIL_INVALID_URL_DL(userId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCallGetInternalUserlogin(int userId) {
		String url = ApiPath.LOGIN_DETAIL_DL(userId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}
}
