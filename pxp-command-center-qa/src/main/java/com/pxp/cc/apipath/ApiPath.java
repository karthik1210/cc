// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.apipath;

public class ApiPath {

	public static final String BEARER_TYPE = "Bearer ";
	public static final String AUTHORIZATION = "Authorization";
	public static final String CONTENT_TYPE = "application/json";
	public static final String POST_SIGNIN = "/service/identity/v1/api/auth/signin";
	public static final String SERVICE_CC = "/service/cc";
	public static final String V1_CLIENTS = "/v1/clients";
	public static final String GET_CLIENT = SERVICE_CC + V1_CLIENTS + "/";
	public static final String GET_CLIENT_DIRECTORY = SERVICE_CC + V1_CLIENTS + "/directory";
	public static final String POST_SYSTEM_PRACTICE = "/service/cc/v1/client-orgs";
	public static final String GET_ORG_DETAILS = "/service/cc/v1/orgs/";
	public static final String POST_ENTERPRISE_PRACTICE = "/service/cc/v1/orgs";
	public static final String GET_PARENT_DROPDOWN = "/TYPES​​/";
	public static final String PUT_ORG_CONTROLLER = "/service/cc/v1/orgs";
	public static final String GET_CLIENT_STATUS = "/status/";
	public static final String POST_FORGOT_PASSWORD = "/service/cc/v1/user/forgot-password";
	public static final String POST_RESET_PASSWORD = "/service/cc/v1/user/reset-password";

	public static final String ADD_FAVORITES(int clientId) {
		return SERVICE_CC + V1_CLIENTS + "/client/" + clientId + "/favorites";
	}

	public static final String DELETE_FAVORITE(int clientId) {
		return SERVICE_CC + V1_CLIENTS + "/client/" + clientId + "/favorite";
	}

	public static final String MENU_CLIENTS(int clientId) {
		return GET_CLIENT + clientId + "/menu";
	}

	public static final String GET_ORGID = "/service/cc/v1/orgs/";
	public static final String GET_ORG_HIERARCHY = "/service/cc/v1/org-hierarchy/";
	public static final String PATCH_ORG_STATUS = "/status/";
	public static final String POST_MOVE_ORG_NODE = "/service/cc/v1/business-units";
	public static final String GET_CLIENT_ORG_ID = "/service/cc/v1/client-orgs/";
	public static final String POST_AUDIT_LOG = "/service/cc/v1/audit/view";
	public static final String PUT_APPLYTO_INHERITFROM = "/service/cc/v1/client-orgs/products";
	public static final String GET_PRODUCTS = "/service/cc/v1/products";

	public static final String SERVICE_CLIENT_BL = "/service/client-service";
	public static final String SERVICE_ORG_BL = "/service/org-service";
	public static final String CLIENT_BL = SERVICE_CLIENT_BL + "/v1/clients";
	public static final String INVALID_PATH_CLIENT_BL = SERVICE_CLIENT_BL + "/v1/clientsabc";

	public static final String CLIENT_DETAILS_BL(String clientId) {
		return CLIENT_BL + "/" + "{clientId}";
	}

	public static final String CLIENTNAME_BL(String name) {
		return SERVICE_CLIENT_BL + "/v1/clients/name/" + "{name}";
	}

	public static final String INVALID_PATH_CLIENTNAME_BL(String name) {
		return SERVICE_CLIENT_BL + "/v1/clients/nameabc##$122/" + "{name}";
	}

	public static final String ORG_DETAILS_BL(int orgId) {
		return SERVICE_ORG_BL + "/v1/orgs/" + orgId;
	}

	public static final String ORG_BL = SERVICE_ORG_BL + "/v1/orgs";
	public static final String INVALID_PATH_ORG_BL = SERVICE_ORG_BL + "/v1/orgsssss";
	public static final String CLIENT_TYPES = SERVICE_CLIENT_BL + "/v1/clients/types";
	public static final String INVALID_PATH_CLIENTTYPE_BL = SERVICE_CLIENT_BL + "/typesadsds";

	public static final String CLIENTSAPNUMBER_BL(String sapNumber) {
		return SERVICE_CLIENT_BL + "/v1/clients/sapnumber/" + "{sapNumber}";
	}

	public static final String INVALID_PATH_CLIENTSAPNUMBER_BL(String sapNumber) {
		return SERVICE_CLIENT_BL + "/v1/clients/sapnumber#1234sdF/" + "{sapNumber}";
	}

	public static final String POST_CLIENT_BL = SERVICE_CLIENT_BL + "/v1/clients";
	public static final String GET_CLIENT_BL = SERVICE_CLIENT_BL + "/clients/";
	public static final String GET_CLIENTNAME_BL = SERVICE_CLIENT_BL + "/clients/name/";
	public static final String INVALID_PATH_GET_CLIENTNAME_BL = SERVICE_CLIENT_BL + "/clients/nameabc##$122/";
	public static final String GET_AUTOSUGGESTION_CLIENT = SERVICE_CLIENT_BL + "/auto-suggestion-client";
	public static final String GET_ORG_DETAILS_BL = SERVICE_ORG_BL + "/orgs/";
	public static final String POST_ORG_BL = SERVICE_ORG_BL + "v1/orgs";
	public static final String GET_CLIENT_TYPES = SERVICE_CLIENT_BL + "/clients/types";
	public static final String INVALID_PATH_GET_CLIENTTYPE_BL = SERVICE_CLIENT_BL + "/typesadsds";
	public static final String GET_CLIENTSAPNUMBER_BL = SERVICE_CLIENT_BL + "/clients/sapnumber/";
	public static final String INVALID_PATH_GET_CLIENTSAPNUMBER_BL = SERVICE_CLIENT_BL + "/clients/sapnumber#1234sdF/";
	public static final String SERVICE_CLIENT_DL = "/service/client-data";

	public static final String CLIENTNAME_DL(String name) {
		return SERVICE_CLIENT_DL + "/v1/clients/name/" + "{name}";
	}

	public static final String INVALID_PATH_CLIENTNAME_DL(String name) {
		return SERVICE_CLIENT_DL + "/v1/clients/nameabc##$122/" + "{name}";
	}

	public static final String SERVICE_ORG_DL = "/service/org-data";
	public static final String GET_ORG_DETAILS_DL = SERVICE_ORG_DL + "/v1/orgs/";
	public static final String ORG_DL = SERVICE_ORG_DL + "/v1/orgs";
	public static final String CLIENT_TYPES_DL = SERVICE_CLIENT_DL + "/v1/clients/types";
	public static final String INVALID_PATH_CLIENTTYPE_DL = SERVICE_CLIENT_DL + "/typesadsds";

	public static final String CLIENTSAPNUMBER_DL(String sapNumber) {
		return SERVICE_CLIENT_DL + "/v1/clients/sapnumber/" + "{sapNumber}";
	}

	public static final String INVALID_PATH_CLIENTSAPNUMBER_DL(String sapNumber) {
		return SERVICE_CLIENT_DL + "/v1/clients/sapnumber#1234sdF/" + "{sapNumber}";
	}

	public static final String INVALID_PATH_GET_ORGID_DL = SERVICE_ORG_DL + "/v1/orgsnameabc##$122/";
	public static final String PXP_CC_USER = "pxp-cc-user";
	public static final String CLIENT_DL = SERVICE_CLIENT_DL + "/v1/clients";
	public static final String INVALID_PATH_CLIENT_DL = SERVICE_CLIENT_DL + "/v1/clientsabc";

	public static final String CLIENT_DETAILS_DL(int clientId) {
		return CLIENT_DL + "/" + "{clientId}";
	}

	public static final String INVALID_PATH_CLIENT_DETAILS_BL(String clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/fhg/" + "{clientId}";
	}

	public static final String INVALID_PATH_CLIENT_DETAILS_DL(int clientId) {
		return SERVICE_CLIENT_DL + "/v1/clientssssss/" + "{clientId}";
	}

	public static final String GET_ORGANIZATION_LIST_DL = SERVICE_ORG_DL + "/v1/clients/";

	public static final String CLIENT_SOLUTION_DL(int clientId) {
		return SERVICE_CLIENT_DL + "/v1/clients/" + "{clientId}" + "/solutions";
	}

	public static final String SERVICE_USER_BL = "/service/user-service";
	public static final String USER_ROLES_BL = SERVICE_USER_BL + "/usertypes/";

	public static final String USER_ROLES_BL(String userType) {
		return SERVICE_USER_BL + "/v1/usertypes/" + "{userType}" + "/roles";
	}

	public static final String INVALID_PATH_USER_ROLES_BL(String userType) {
		return SERVICE_USER_BL + "/v1/usertypes/" + userType + "/rolesss";
	}

	public static final String SERVICE_USER_DL = "/service/user-data";

	public static final String USER_ROLES_DL(String userType) {
		return SERVICE_USER_DL + "/v1/usertypes/" + "{userType}" + "/roles";
	}

	public static final String INVALID_PATH_USER_ROLES_DL(String userType) {
		return SERVICE_USER_DL + "/v1/usertypes/" + userType + "/rolesss";
	}

	public static final String ORGS_SOLUTION_DL = SERVICE_ORG_DL + "/v1/orgs/";

	public static final String INVALID_PATH_ORGS_SOLUTION_DL(int orgId) {
		return ORGS_SOLUTION_DL + orgId + "/Solutionsaaaa";
	}

	public static final String USER_ROLES_BY_USER_BL(int userId) {
		return SERVICE_USER_BL + "/v1/users/" + "{userId}" + "/roles";
	}

	public static final String INVALID_PATH_USER_ROLES_BY_USER_BL(int userId) {
		return SERVICE_USER_BL + "/v1/users/" + "{userId}" + "/rolessss";
	}

	public static final String CLIENT_DIRECTORY_BL = SERVICE_CLIENT_BL + "/v1/clients";

	public static final String CLIENT_DIRECTORY_BL(String searchData, String page, String pageSize, Object type)

	{
		return SERVICE_CLIENT_BL + "/v1/clients";
	}

	public static final String CLIENT_DIRECTORY_DL = SERVICE_CLIENT_DL + "/v1/clients";

	public static final String CLIENT_DIRECTORY_DL(String searchData, String page, String pageSize, Object type)

	{
		return SERVICE_CLIENT_DL + "/v1/clients";
	}

	public static final String AUTOSUGGESTION_CLIENT_BL = SERVICE_CLIENT_BL + "/v1/clients/auto-suggestions";

	public static final String AUTOSUGGESTION_CLIENT_BL(String searchData) {
		return SERVICE_CLIENT_BL + "/v1/clients/auto-suggestions";
	}

	public static final String FAVORITE(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + clientId + "/favorites";
	}

	public static final String INVALID_PATH_FAVORITE(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + clientId + "/favoritesss";
	}

	public static final String CLIENT_ORG_HIERARCHY_BL(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + clientId + "/org-hierarchy";
	}

	public static final String INVALID_PATH_CLIENT_ORGS_HIERARCHY_BL(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + clientId + "/org-hierarchyyrteytr";
	}

	public static final String SERVICE_COMMONS_DL = "service/commons-data";
	public static final String TIMEZONES_DL = SERVICE_COMMONS_DL + "/v1/timezones";
	public static final String INVALID_PATH_TIMEZONES_DL = SERVICE_COMMONS_DL + "/v1/timezonesssss";

	public static final String SERVICE_COMMONS_BL = "service/commons-service";
	public static final String TIMEZONES_BL = SERVICE_COMMONS_BL + "/v1/timezones";
	public static final String INVALID_PATH_TIMEZONES_BL = SERVICE_COMMONS_BL + "/v1/timezonesssss";

	public static final String USER_ROLES_BY_USER_DL(int userId) {
		return SERVICE_USER_DL + "/v1/users/" + "{userId}" + "/roles";
	}

	public static final String INVALID_PATH_USER_ROLES_BY_USER_DL(int userId) {
		return SERVICE_USER_DL + "/v1/users/" + "{userId}" + "/rolessss";
	}

	public static final String CLIENT_ORG_HIERARCHY_DL(int clientId) {
		return SERVICE_CLIENT_DL + "/v1/clients/" + clientId + "/org-hierarchy";
	}

	public static final String INVALID_PATH_CLIENT_ORG_HIERARCHY_DL(int clientId) {
		return SERVICE_CLIENT_DL + "/v1/clients/" + clientId + "/org-hierarcchyyrteytr";
	}

	public static final String GET_ORGANIZATION_LIST_BL(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + "{clientId}" + "/orgs";
	}

	public static final String INVALID_PATH_GET_ORGANIZATION_LIST_BL(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + "{clientId}" + "/orgsssssabc";
	}

}
