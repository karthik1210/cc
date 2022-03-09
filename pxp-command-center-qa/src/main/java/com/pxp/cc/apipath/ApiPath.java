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
	public static final String GET_TIMEZONES = "/service/cc/v1/timezones";
	public static final String GET_CLIENT_ORG_ID = "/service/cc/v1/client-orgs/";
	public static final String POST_AUDIT_LOG = "/service/cc/v1/audit/view";
	public static final String PUT_APPLYTO_INHERITFROM = "/service/cc/v1/client-orgs/products";
	public static final String GET_PRODUCTS = "/service/cc/v1/products";
	public static final String GET_AUTOSUGGESTION_CLIENT = SERVICE_CC + V1_CLIENTS + "/auto-suggestion-client";
	public static final String SERVICE_CLIENT_BL = "/service/client";
	public static final String SERVICE_ORG_BL = "/service/org";
	public static final String POST_CLIENT_BL = SERVICE_CLIENT_BL + "/clients";
	public static final String GET_CLIENT_BL = SERVICE_CLIENT_BL + "/clients/";
	public static final String GET_CLIENTNAME_BL = SERVICE_CLIENT_BL + "/clients/name/";
	public static final String INVALID_PATH_GET_CLIENTNAME_BL = SERVICE_CLIENT_BL + "/clients/nameabc##$122/";

	public static final String GET_ORG_DETAILS_BL = SERVICE_ORG_BL + "/orgs/";
	public static final String POST_ORG_BL = SERVICE_ORG_BL + "/orgs/";
	public static final String GET_CLIENT_TYPES = SERVICE_CLIENT_BL + "/clients/types";
	public static final String INVALID_PATH_GET_CLIENTTYPE_BL = SERVICE_CLIENT_BL + "/typesadsds";
	public static final String GET_CLIENTSAPNUMBER_BL = SERVICE_CLIENT_BL + "/clients/sapnumber/";
	public static final String INVALID_PATH_GET_CLIENTSAPNUMBER_BL = SERVICE_CLIENT_BL + "/clients/sapnumber#1234sdF/";
	public static final String SERVICE_CLIENT_DL = "/service/client-data";
	public static final String GET_CLIENTNAME_DL = SERVICE_CLIENT_DL + "/clients/name/";
	public static final String INVALID_PATH_GET_CLIENTNAME_DL = SERVICE_CLIENT_DL + "/clients/nameabc##$122/";

	public static final String SERVICE_ORG_DL = "/service/org-data";
	public static final String GET_ORG_DETAILS_DL = SERVICE_ORG_DL + "/orgs/";
	public static final String POST_ORG_DL = SERVICE_ORG_DL + "/orgs";

	public static final String GET_CLIENT_TYPES_DL = SERVICE_CLIENT_DL + "/clients/types";
	public static final String INVALID_PATH_GET_CLIENTTYPE_DL = SERVICE_CLIENT_DL + "/typesadsds";
	public static final String GET_CLIENTSAPNUMBER_DL = SERVICE_CLIENT_DL + "/clients/sapnumber/";
	public static final String INVALID_PATH_GET_CLIENTSAPNUMBER_DL = SERVICE_CLIENT_DL + "/clients/sapnumber#1234sdF/";
	public static final String INVALID_PATH_GET_ORGID_DL = SERVICE_ORG_DL + "/orgsnameabc##$122/";
	public static final String PXP_CC_USER = "pxp-cc-user";
	public static final String POST_CLIENT_DL = SERVICE_CLIENT_DL + "/clients";
	public static final String GET_CLIENT_DL = SERVICE_CLIENT_DL + "/clients/";

	public static final String INVALID_PATH_GET_CLIENT_DL = SERVICE_CLIENT_DL + "/clientsss/";
	public static final String INVALID_PATH_PUT_UPDATECLIENT_BL = SERVICE_CLIENT_BL + "/clients/fhg/";
	public static final String PUT_CLIENT_DL = SERVICE_CLIENT_DL + "/clients/";
	public static final String INVALID_PATH_PUT_CLIENT_DL = SERVICE_CLIENT_DL + "/clientssssss/";
	public static final String GET_ORGANIZATION_LIST_DL = SERVICE_ORG_DL + "/clients/";
	public static final String GET_CLIENT_SOLUTION_DL = SERVICE_CLIENT_DL + "/clients/";
	public static final String PUT_ORGS_SOLUTION_DL = SERVICE_ORG_DL + "/orgs/";
	public static final String SERVICE_USER_BL = "/service/user";
	public static final String USER_ROLES_BL = SERVICE_USER_BL + "/usertypes/";
	public static final String INVALID_PATH_USER_ROLES_BL(String userType) {
		return USER_ROLES_BL + userType + "/rolesss";
	}
	public static final String SERVICE_USER_DL = "/service/user-data";
	public static final String USER_ROLES_DL = SERVICE_USER_DL + "/usertypes/";
	public static final String INVALID_PATH_USER_ROLES_DL(String userType) {
		return USER_ROLES_DL + userType + "/rolesss";
	}
}
