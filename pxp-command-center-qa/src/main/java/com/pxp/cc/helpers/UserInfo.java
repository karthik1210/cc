// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.restassured.response.Response;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.*;

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

    public Response validateUserDetails(String email, String username, long mobileNumber) {
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

    public Response validateUserDetails_DL(String email, String username, long mobileNumber) {
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
        createUsertMap.put("enrollmentStatus", "NOT_SENT");
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
        return given().spec(requestSpec).body(createNewUserPayload).when().post(url).then().extract().response();
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

    public Response patchInternalUser_DataLayer(String userStatus, Integer id) throws IOException {
        Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
        String url = ApiPath.UPDATE_USER_DL(id);
        return given().spec(requestSpec).body(updateInternalUserPayload).when().patch(url).then().extract().response();
    }

    public Response patchInternalUser_DataLayerUsingInvalidMethodCall(String userStatus, Integer id)
            throws IOException {
        Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
        String url = ApiPath.UPDATE_USER_DL(id);
        return given().spec(requestSpec).body(updateInternalUserPayload).when().post(url).then().extract().response();
    }

    public Response patchInternalUser_BusinessLayerUsingInvalidUrl(String userStatus, Integer id) throws IOException {
        Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
        String url = ApiPath.INVALID_PATH_INTERNAL_USER_SERVICE(id);
        return given().spec(requestSpec).body(updateInternalUserPayload).when().patch(url).then().extract().response();
    }

    public Response patchInternalUser_DataLayerUsingInvalidUrl(String userStatus, Integer id) throws IOException {
        Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
        String url = ApiPath.INVALID_PATH_UPDATE_USER_DL(id);
        return given().spec(requestSpec).body(updateInternalUserPayload).when().patch(url).then().extract().response();
    }

    private Map<String, Object> patchInternalUserStatus(String userStatus) {
        Map<String, Object> updateInternalUsertMap = new HashMap<String, Object>();
        updateInternalUsertMap.put("userStatus", userStatus);
        return updateInternalUsertMap;
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

    public Response addAssociateEntities_DL(int userId, ArrayList<Object> orgIDs) {
        List<Object> associatedEntitiesPayload = associatedEntities_DL(orgIDs);
        String url = ApiPath.ADD_ASSOCIATE_ENTITIES_DL(userId);
        return given().spec(requestSpec).pathParam("userId", userId)
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

    public Response addAssociateEntitiesInvalidUrl_DL(int userId, ArrayList<Object> orgIDs) {
        List<Object> associatedEntitiesPayload = associatedEntities_DL(orgIDs);
        String invalidurl = ApiPath.ADD_ASSOCIATE_ENTITIES_INVALID_URL_DL(userId);
        return given().spec(requestSpec).pathParam("userId", userId)
                .body(associatedEntitiesPayload).when().post(invalidurl).then().extract().response();
    }

    public Response invalidMethodCall_DL(int userId, ArrayList<Object> orgIDs, int pxp_cc_user) {
        List<Object> associatedEntitiesPayload = associatedEntities_DL(orgIDs);
        String url = ApiPath.ADD_ASSOCIATE_ENTITIES_DL(userId);
        return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, pxp_cc_user).pathParam("userId", userId)
                .body(associatedEntitiesPayload).when().put(url).then().extract().response();
    }

    public Response getUserLogin(int userId) {
        String url = ApiPath.LOGIN_DETAIL_BL(userId);
        return given().spec(requestSpec).when().get(url).then().extract().response();
    }

    public Response getUserLoginInvalidUrl(int userId) {
        String url = ApiPath.LOGIN_DETAIL_INVALID_URL_BL(userId);
        return given().spec(requestSpec).when().get(url).then().extract().response();
    }

    public Response invalidMethodCallGetUserLogin(int userId) {
        String url = ApiPath.LOGIN_DETAIL_BL(userId);
        return given().spec(requestSpec).when().put(url).then().extract().response();
    }

    public Response getUserLogin_NullValues() {
        String url = ApiPath.LOGIN_DETAIL_NULL_VALUES_BL();
        return given().spec(requestSpec).when().get(url).then().extract().response();
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

    public Response patchInternalUserRole_DL(int userId, int orgId, String roleId, String active) throws IOException {
        Map<String, Object> roleAssociationPayload = patchRoleAssociation(roleId, active);
        String url = ApiPath.PATCH_USER_ROLES_DL(userId, orgId);
        return given().spec(requestSpec).body(roleAssociationPayload).when().patch(url).then().extract().response();
    }

    public Response patchInternalUserRoleInvalidUrl_DL(int userId, int orgId, String roleId, String active)
            throws IOException {
        Map<String, Object> roleAssociationPayload = patchRoleAssociation(roleId, active);
        String url = ApiPath.PATCH_USER_ROLES_INVALID_URL_DL(userId, orgId);
        return given().spec(requestSpec).body(roleAssociationPayload).when().patch(url).then().extract().response();
    }

    public Response patchInternalUserRoleInvalidMethod_DL(int userId, int orgId, String roleId, String active)
            throws IOException {
        Map<String, Object> roleAssociationPayload = patchRoleAssociation(roleId, active);
        String url = ApiPath.PATCH_USER_ROLES_DL(userId, orgId);
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

    public Response getUserlogin_DL(int userId) {
        String url = ApiPath.LOGIN_DETAIL_DL(userId);
        return given().spec(requestSpec).when().get(url).then().extract().response();
    }

    public Response getUserloginInvalidUrl_DL(int userId) {
        String url = ApiPath.LOGIN_DETAIL_INVALID_URL_DL(userId);
        return given().spec(requestSpec).when().get(url).then().extract().response();
    }

    public Response invalidMethodCallGetUserlogin_DL(int userId) {
        String url = ApiPath.LOGIN_DETAIL_DL(userId);
        return given().spec(requestSpec).when().put(url).then().extract().response();
    }

    public Response getUserInfo_DataLayer(String authId) {
        String url = ApiPath.USER_INFO_DL;
        return given().spec(requestSpec).headers("X-Auth-ID", authId).when().get(url).then().extract().response();
    }

    public Response getUserInfoUsingInvalidUrl_DataLayer(String authId) {
        String url = ApiPath.INVALID_PATH_USER_INFO_DL;
        return given().spec(requestSpec).headers("X-Auth-ID", authId).when().get(url).then().extract().response();
    }

    public Response getUserInfoUsingInvalidMethodCall_DataLayer(String authId) {
        String url = ApiPath.USER_INFO_DL;
        return given().spec(requestSpec).headers("X-Auth-ID", authId).when().post(url).then().extract().response();
    }

    public Response getUserInfo(String authId) {
        String url = ApiPath.USER_INFO_BL;
        return given().spec(requestSpec).headers("X-Auth-ID", authId).when().get(url).then().extract().response();
    }

    public Response getUserInfoUsingInvalidUrl(String authId) {
        String url = ApiPath.INVALID_PATH_USER_INFO_BL;
        return given().spec(requestSpec).headers("X-Auth-ID", authId).when().get(url).then().extract().response();
    }

    public Response getUserInfoUsingInvalidMethodCall(String authId) {
        String url = ApiPath.USER_INFO_BL;
        return given().spec(requestSpec).headers("X-Auth-ID", authId).when().post(url).then().extract().response();
    }

    public Response getUsers_DL(String type, String firstname, String lastname, String email, String username,
                                String mobileNumber, String clientname, String resellername, String sortdir, String pageno,
                                String pagesize) {
        String url = ApiPath.GET_USERS_DL();
        return given().spec(requestSpec).queryParam("type", type).queryParam("firstname", firstname)
                .queryParam("lastname", lastname).queryParam("email", email).queryParam("username", username)
                .queryParam("mobile", mobileNumber).queryParam("clientname", clientname)
                .queryParam("resellername", resellername).queryParam("sortdir", sortdir).queryParam("pageno", pageno)
                .queryParam("pagesize", pagesize).when().get(url).then().extract().response();
    }

    public Response getUsersInvalidUrl_DL(String type, String firstname, String lastname, String email, String username,
                                          String mobileNumber, String clientname, String resellername, String sortdir, String pageno,
                                          String pagesize) {
        String url = ApiPath.GET_USERS_INVALID_PATH_DL();
        return given().spec(requestSpec).queryParam("type", type).queryParam("firstname", firstname)
                .queryParam("lastname", lastname).queryParam("email", email).queryParam("username", username)
                .queryParam("mobile", mobileNumber).queryParam("clientname", clientname)
                .queryParam("resellername", resellername).queryParam("sortdir", sortdir).queryParam("pageno", pageno)
                .queryParam("pagesize", pagesize).when().get(url).then().extract().response();
    }

    public Response invalidMethodCallGetUsers_DL(String type, String firstname, String lastname, String email,
                                                 String username, String mobileNumber, String clientname, String resellername, String sortdir, String pageno,
                                                 String pagesize) {
        String url = ApiPath.GET_USERS_DL();
        return given().spec(requestSpec).queryParam("type", type).queryParam("firstname", firstname)
                .queryParam("lastname", lastname).queryParam("email", email).queryParam("username", username)
                .queryParam("mobile", mobileNumber).queryParam("clientname", clientname)
                .queryParam("resellername", resellername).queryParam("sortdir", sortdir).queryParam("pageno", pageno)
                .queryParam("pagesize", pagesize).when().put(url).then().extract().response();
    }

    public Response getSearchUser(String type, String urlType) {
        String url = ApiPath.USER_DETAILS_BL;
        String invalidUrl = ApiPath.INVALID_PATH_USER_DETAILS_BL;
        if (urlType.equalsIgnoreCase("Valid"))
            return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
        else
            return given().spec(requestSpec).queryParam("type", type).when().get(invalidUrl).then().extract()
                    .response();
    }

    public Response verifyResponseForValidUserIncorrectData(String type, String firstName, String lastName,
                                                            String email, String clientName) {
        String url = ApiPath.USER_DETAILS_BL;
        return given().spec(requestSpec).queryParam("type", type).queryParam("firstName", firstName)
                .queryParams("lastName", lastName).queryParam("email", email).queryParam("clientName", clientName)
                .when().get(url).then().extract().response();
    }

    public Response getSearchUserDetailsForGivenType(String type, String firstName, String lastName, String email,
                                                     String userName, String mobileNo, String clientName, String resellerName, String sortColumn, String sortDir,
                                                     String pageNo, String pageSize) {
        String url = ApiPath.USER_DETAILS_BL;
        return given().spec(requestSpec).queryParam("type", type).queryParam("firstName", firstName)
                .queryParam("lastName", lastName).queryParam("email", email).queryParam("userName", userName)
                .queryParam("mobileNo", mobileNo).queryParam("clientName", clientName)
                .queryParam("resellerName", resellerName).queryParam("sortColumn", sortColumn)
                .queryParam("sortDir", sortDir).queryParam("pageNo", pageNo).queryParam("pageSize", pageSize).when()
                .get(url).then().extract().response();
    }

    public Response getSearchUserDetailsWithInvalidPut(String type) {
        String url = ApiPath.USER_DETAILS_BL;
        return given().spec(requestSpec).when().put(url).then().extract().response();
    }

    public Response putUserRole_DataLayer(int userId, int pxp_cc_user, ArrayList<Object> roles) {
        List<Object> putRolesPayload = putRoles_DL(roles);
        String url = ApiPath.USER_ROLES_BY_USER_DL(userId);
        return given().spec(requestSpec).pathParam("userId", userId).header(ApiPath.PXP_CC_USER, pxp_cc_user)
                .body(putRolesPayload).when().put(url).then().extract().response();
    }

    private List<Object> putRoles_DL(ArrayList<Object> roles) {
        List<Object> Roles = new ArrayList<Object>();
        Roles.addAll(roles);
        return Roles;
    }

    public Response sendUserEnrollmentConsumerDL(String validURI, int userId,
                                                 UserSendEnrollmentConsumerDataLayer sendUserEnrollmentConsumerPayload) {
        if (validURI.equalsIgnoreCase("Valid")) {
            String url = ApiPath.SEND_USER_ENROLLMENT_CONSUMER("DL", "Valid", userId);
            return given().spec(requestSpec).body(sendUserEnrollmentConsumerPayload).when().patch(url).then().extract()
                    .response();
        } else if (validURI.equalsIgnoreCase("InValid")) {
            String url = ApiPath.SEND_USER_ENROLLMENT_CONSUMER("DL", "InValid", userId);
            return given().spec(requestSpec).body(sendUserEnrollmentConsumerPayload).when().patch(url).then().extract()
                    .response();
        } else {
            String url = ApiPath.SEND_USER_ENROLLMENT_CONSUMER("DL", "Valid", userId);
            return given().spec(requestSpec).body(sendUserEnrollmentConsumerPayload).when().delete(url).then().extract()
                    .response();
        }
    }

    public Response sendUserEnrollmentConsumerInvaliDatatypeResponseDL(String userId,
                                                                       UserSendEnrollmentConsumerDataLayer sendUserEnrollmentConsumerPayload) {
        String url = ApiPath.SEND_USER_ENROLLMENT_CONSUMER_WITH_INVLAID_DATATYPE("DL", userId);
        return given().spec(requestSpec).body(sendUserEnrollmentConsumerPayload).when().patch(url).then().extract()
                .response();
    }

    public Response getUserEnrollmentDL(String validURI, String enrollmentId, String enrollmentStatus) {
        if (validURI.equalsIgnoreCase("Valid")) {
            String url = ApiPath.GET_ENROLLMENT_USER("Valid", enrollmentId);
            return given().spec(requestSpec).queryParam("status", enrollmentStatus).when().get(url).then().extract()
                    .response();
        } else if (validURI.equalsIgnoreCase("InValid")) {
            String url = ApiPath.GET_ENROLLMENT_USER("InValid", enrollmentId);
            return given().spec(requestSpec).queryParam("status", enrollmentStatus).when().get(url).then().extract()
                    .response();
        } else {
            String url = ApiPath.GET_ENROLLMENT_USER("Valid", enrollmentId);
            return given().spec(requestSpec).queryParam("status", enrollmentStatus).when().delete(url).then().extract()
                    .response();
        }
    }

    public Response createNewUser_BusinessLayer(String userType, String firstName, String lastName, String userName,
                                                String email, Long mobile, String userStatus, String enrollmentStatus, Object clientId, Object resellerId,
                                                Object providerId, List<Integer> roleIds, boolean emailEnrollment) throws IOException {
        Map<String, Object> createNewUserPayload = postCreateUser(userType, firstName, lastName, userName, email,
                mobile, userStatus, enrollmentStatus, clientId, resellerId, providerId, roleIds, emailEnrollment);
        String url = ApiPath.USER_BL;
        return given().spec(requestSpec).body(createNewUserPayload).when().post(url).then().extract().response();
    }

    private Map<String, Object> postCreateUser(String userType, String firstName, String lastName, String userName,
                                               String email, Long mobile, String userStatus, String enrollmentStatus, Object clientId, Object resellerId,
                                               Object providerId, List<Integer> roleIds, boolean emailEnrollment) {
        Map<String, Object> createUsertMap = new HashMap<String, Object>();
        createUsertMap.put("userType", userType);
        createUsertMap.put("firstName", firstName);
        createUsertMap.put("lastName", lastName);
        createUsertMap.put("userName", userName);
        createUsertMap.put("email", email);
        createUsertMap.put("mobile", mobile);
        createUsertMap.put("userStatus", userStatus);
        createUsertMap.put("enrollmentStatus", enrollmentStatus);
        createUsertMap.put("clientId", clientId);
        createUsertMap.put("resellerId", resellerId);
        createUsertMap.put("providerId", providerId);
        createUsertMap.put("roleIds", roleIds);
        createUsertMap.put("emailEnrollment", emailEnrollment);
        return createUsertMap;
    }

    public Response createNewUser_BusinessLayer() throws IOException {
        String url = ApiPath.USER_BL;
        return given().spec(requestSpec).when().post(url).then().extract().response();
    }

    public Response createNewUser_DataLayer() throws IOException {
        String url = ApiPath.USER_DL;
        return given().spec(requestSpec).when().post(url).then().extract().response();
    }

    public Response createNewUser_DataLayer(String userType, String firstName, String lastName, String userName,
                                            String email, Long mobile, String userStatus, String enrollmentStatus, Object clientId, Object resellerId,
                                            Object providerId, List<Integer> roleIds, boolean emailEnrollment) throws IOException {
        Map<String, Object> createNewUserPayload = postCreateUser(userType, firstName, lastName, userName, email,
                mobile, userStatus, enrollmentStatus, clientId, resellerId, providerId, roleIds, emailEnrollment);
        String url = ApiPath.USER_DL;
        return given().spec(requestSpec).body(createNewUserPayload).when().post(url).then().extract().response();
    }

    public Response updateUser_BusinessLayer(String userType, String firstName, String lastName, String userName,
                                             String email, Long mobile, String userStatus, Object clientId, Object resellerId, Object providerId,
                                             String expiryDate, List<Integer> roleIds, Integer id) throws IOException {
        Map<String, Object> updateUserPayload = updateUser(userType, firstName, lastName, userName, email, mobile,
                userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
        String url = ApiPath.ADD_USER_BL(id);
        return given().spec(requestSpec).body(updateUserPayload).when().put(url).then().extract().response();
    }

    public Response updateUser_DataLayer(String userType, String firstName, String lastName, String userName,
                                         String email, Long mobile, String userStatus, Object clientId, Object resellerId, Object providerId,
                                         String expiryDate, List<Integer> roleIds, Integer id) throws IOException {
        Map<String, Object> updateUserPayload = updateUser(userType, firstName, lastName, userName, email, mobile,
                userStatus, clientId, resellerId, providerId, expiryDate, roleIds);
        String url = ApiPath.UPDATE_USER_DL(id);
        return given().spec(requestSpec).body(updateUserPayload).when().put(url).then().extract().response();
    }

    private Map<String, Object> updateUser(String userType, String firstName, String lastName, String userName,
                                           String email, Long mobile, String userStatus, Object clientId, Object resellerId, Object providerId,
                                           String expiryDate, List<Integer> roleIds) {
        Map<String, Object> updateUsertMap = new HashMap<String, Object>();
        updateUsertMap.put("userType", userType);
        updateUsertMap.put("firstName", firstName);
        updateUsertMap.put("lastName", lastName);
        updateUsertMap.put("userName", userName);
        updateUsertMap.put("email", email);
        updateUsertMap.put("mobile", mobile);
        updateUsertMap.put("userStatus", userStatus);
        updateUsertMap.put("clientId", clientId);
        updateUsertMap.put("resellerId", resellerId);
        updateUsertMap.put("providerId", providerId);
        updateUsertMap.put("expiryDate", expiryDate);
        updateUsertMap.put("roleIds", roleIds);
        return updateUsertMap;
    }

    public Response patchInternalUser_BusinessLayer(Integer id, String poolId, String userStatus) throws IOException {
        Map<String, Object> updateInternalUserPayload = patchInternalUserStatus(userStatus);
        String url = ApiPath.ADD_USER_BL(id);
        return given().spec(requestSpec).header("poolId", poolId).body(updateInternalUserPayload).when().patch(url).then().extract().response();
    }

    public Map<String, Object> updateExpiredStatus_DL(String status, String currentTs) {
        Map<String, Object> updateExpiredStatusDataFetch = new HashMap<String, Object>();
        updateExpiredStatusDataFetch.put("status", status);
        updateExpiredStatusDataFetch.put("currentTs", currentTs);
        return updateExpiredStatusDataFetch;
    }

    public Map<String, Object> updateExpiredStatus_BL(String status) {
        Map<String, Object> updateExpiredStatusDataFetch = new HashMap<String, Object>();
        updateExpiredStatusDataFetch.put("status", status);
        return updateExpiredStatusDataFetch;
    }

    public Response updateExpiredStatus_DL(String serviceLayer, String typeURI, String status, String currentTs) {
        if (typeURI.equalsIgnoreCase("valid") || typeURI.equalsIgnoreCase("Invalid")) {
            String url = ApiPath.UPDATE_EXPIRED_STATUS(serviceLayer, typeURI);
            return given().spec(requestSpec).body(updateExpiredStatus_DL(status, currentTs)).when().patch(url).then().extract().response();
        } else {
            String url = ApiPath.UPDATE_EXPIRED_STATUS(serviceLayer, "Valid");
            return given().spec(requestSpec).body(updateExpiredStatus_DL(status, currentTs)).when().delete(url).then().extract().response();
        }
    }

    public Response updateExpiredStatus_BL(String serviceLayer, String typeURI, String status) {
		if (typeURI.equalsIgnoreCase("valid") || typeURI.equalsIgnoreCase("Invalid")) {
			String url = ApiPath.UPDATE_EXPIRED_STATUS(serviceLayer, typeURI);
			return given().spec(requestSpec).body(updateExpiredStatus_BL(status)).when().patch(url).then().extract().response();
		} else {
			String url = ApiPath.UPDATE_EXPIRED_STATUS(serviceLayer, "Valid");
			return given().spec(requestSpec).body(updateExpiredStatus_BL(status)).when().delete(url).then().extract().response();
		}
	}

    public Map<String, Object> updatePasswordDuringEnrollment_BL(String password) {
        Map<String, Object> updatePassword = new HashMap<String, Object>();
        updatePassword.put("password", password);
        return updatePassword;
    }

    public Response updatePassword_BL(String typeURI, String enrollmentId, String password) {
        if (typeURI.equalsIgnoreCase("valid") || typeURI.equalsIgnoreCase("Invalid")) {
            String url = ApiPath.UPDATE_PASSWORD_DURING_ENROLLMENT(typeURI, enrollmentId);
            return given().spec(requestSpec).body(updatePasswordDuringEnrollment_BL(password)).when().patch(url).then().extract().response();
        } else {
            String url = ApiPath.UPDATE_PASSWORD_DURING_ENROLLMENT( "Valid", enrollmentId);
            return given().spec(requestSpec).body(updatePasswordDuringEnrollment_BL(password)).when().delete(url).then().extract().response();
        }
    }

    public Response searchUserWithEnrollmentStatus(String serviceLayer, String userType, String uriType, String enrollmentStatus) {
        if (serviceLayer.equalsIgnoreCase("DL")) {
            String url = ApiPath.USER_DL;
            return given().spec(requestSpec).queryParam("type", userType).queryParam("enrollstatus", enrollmentStatus).when().get(url).then().extract().response();
        } else {
            String url = ApiPath.USER_BL;
            return given().spec(requestSpec).queryParam("type", userType).queryParam("enrollstatus", enrollmentStatus).when().get(url).then().extract().response();
        }
    }

    public Response getUserInfo(String serviceLayer, int userId) {
        String url = ApiPath.GET_USER_INFO(serviceLayer, userId);
        return given().spec(requestSpec).when().get(url).then().extract().response();
    }

    public Response getUserRolesByUserType(String userType) {
        String url = ApiPath.USER_ROLES_BL(userType);
        return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
    }

    public Response sendEnrollmentBL(String typeURI, ArrayList<Integer> usersList) {
        if (typeURI.equalsIgnoreCase("Valid")) {
            String url = ApiPath.SEND_ENROLLMENT_BL(typeURI);
            return given().spec(requestSpec).body(usersList).when().post(url).then().extract().response();
        } else if (typeURI.equalsIgnoreCase("InValid")) {
            String url = ApiPath.SEND_ENROLLMENT_BL(typeURI);
            return given().spec(requestSpec).body(usersList).when().post(url).then().extract().response();
        } else {
            String url = ApiPath.SEND_ENROLLMENT_BL("Valid");
            return given().spec(requestSpec).body(usersList).when().delete(url).then().extract().response();
        }
    }

    public Response getUsersByTypeAndEnrollmentStatusBL(String type, String enrollmentStatus, String userStatus) {
          String url = ApiPath.USER_DETAILS_BL;
            return given().spec(requestSpec).queryParam("type", type).queryParam("enrollstatus", enrollmentStatus).queryParam("userstatus", userStatus)
                    .when().get(url).then().extract().response();
        }
    

    public Response addRoleLocation_BL(int userId ,int orgId, int roleId, String name,
			int locationId, String nameone) {
		String url = ApiPath.ADD_ROLE_LOCATION_BL(userId,orgId);
		Map<String, Object> locationIdPayload = locationsIdRequestPayload(locationId, nameone);
		List<Object> locationsPayload = locationRequestPayload(locationIdPayload);
		Map<String, Object> addRoleLocationPayload = addRoleLocationRequestPayload(roleId, name,
				locationsPayload);
		return given().spec(requestSpec).body(addRoleLocationPayload).when().put(url).then().extract()
				.response();
	}

	private List<Object> locationRequestPayload(Map<String, Object> locationIdPayload) {
		List<Object> Locations = new ArrayList<Object>();
		Locations.add(locationIdPayload);
		return Locations;
	}

	private Map<String, Object> locationsIdRequestPayload(int locationId, String nameone) {
		Map<String, Object> locationMap = new HashMap<String, Object>();
		locationMap.put("locationId", locationId);
		locationMap.put("name", nameone);
		return locationMap;
	}

	private Map<String, Object> addRoleLocationRequestPayload(int roleId, String name,
			List<Object> locationsPayload) {
		Map<String, Object> addRoleLocationMap = new HashMap<String, Object>();

		addRoleLocationMap.put("roleId", roleId);
		addRoleLocationMap.put("name", name);
		addRoleLocationMap.put("locations", locationsPayload);
		return addRoleLocationMap;
	}
	
	public Response addRoleLocation_DL(int userId ,int orgId, int roleId, String name,
			int locationId, String nameone) {
		String url = ApiPath.ADD_ROLE_LOCATION_DL(userId,orgId);
		Map<String, Object> locationIdPayload = locationsIdRequestPayload(locationId, nameone);
		List<Object> locationsPayload = locationRequestPayload(locationIdPayload);
		Map<String, Object> addRoleLocationPayload = addRoleLocationRequestPayload(roleId, name,
				locationsPayload);
		return given().spec(requestSpec).body(addRoleLocationPayload).when().put(url).then().extract()
				.response();
	}
	
	public Response updateUsersStatus_DL(int userId, String status, String message, String oldStatus, String username) {
		String url = ApiPath.UPDATE_USER_STATUS_DL;
		Map<String, Object> usersStatus = userStatusRequestPayload(userId, status, message, oldStatus, username);
		List<Object> userStatusPayload = userStatusPayload(usersStatus);
		return given().spec(requestSpec).body(userStatusPayload).when().put(url).then().extract().response();
	}

	private List<Object> userStatusPayload(Map<String, Object> usersStatus) {
		List<Object> userStatus = new ArrayList<Object>();
		userStatus.add(usersStatus);
		return userStatus;
	}

	private Map<String, Object> userStatusRequestPayload(int userId, String status, String message, String oldStatus,
			String username) {
		Map<String, Object> userStatusMap = new HashMap<String, Object>();
		userStatusMap.put("userId", userId);
		userStatusMap.put("status", status);
		userStatusMap.put("message", message);
		userStatusMap.put("oldStatus", oldStatus);
		userStatusMap.put("username", username);
		return userStatusMap;
	}

    public static Response getUserDetailsUserNameEmail_DL(String typeURI, String userNameEmail) {
        if (typeURI.equalsIgnoreCase("Valid")) {
            String url = ApiPath.GET_USER_DETAILS_USERNAME_EMAIL(typeURI,userNameEmail);
            return given().spec(requestSpec).when().get(url).then().extract().response();
        } else if (typeURI.equalsIgnoreCase("InValid")) {
            String url = ApiPath.GET_USER_DETAILS_USERNAME_EMAIL(typeURI,userNameEmail);
            return given().spec(requestSpec).when().get(url).then().extract().response();
        } else {
            String url = ApiPath.GET_USER_DETAILS_USERNAME_EMAIL("Valid",userNameEmail);
            return given().spec(requestSpec).when().delete(url).then().extract().response();
        }
    }
}
