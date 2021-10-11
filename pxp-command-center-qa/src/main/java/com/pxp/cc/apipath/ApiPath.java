// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.apipath;

public class ApiPath {

	public static final String BEARER_TYPE = "Bearer ";
	public static final String AUTHORIZATION = "Authorization";
	public static final String CONTENT_TYPE = "application/json";
	public static final String POST_SIGNIN = "/service/identity/v1/api/auth/signin";
	public static final String SERVICE_CC = "/service/cc";
	public static final String V1_CLIENTS = "/v1/clients";
	public static final String GET_CLIENT = SERVICE_CC + V1_CLIENTS + "/";
	public static final String POST_CLIENT = SERVICE_CC + V1_CLIENTS;
	public static final String GET_CLIENT_TYPES = SERVICE_CC + V1_CLIENTS + "/types";
	public static final String GET_CLIENT_DIRECTORY = SERVICE_CC + V1_CLIENTS + "/directory";
	public static final String POST_SYSTEM_PRACTICE = "/service/cc/v1/client-orgs";
	public static final String GET_ORG_DETAILS = "/service/cc/v1/orgs/";
	public static final String POST_ENTERPRISE_PRACTICE = "/service/cc/v1/orgs";
	public static final String GET_CLIENTSAPNUMBER = SERVICE_CC + V1_CLIENTS + "/sapnumber/";
	public static final String GET_PARENT_DROPDOWN = "/TYPES​​/";
	public static final String PUT_ORG_CONTROLLER = "/service/cc/v1/orgs";
	public static final String GET_CLIENT_STATUS = "/status/";

	public static final String ADD_FAVORITES(int clientId) {
		return SERVICE_CC + V1_CLIENTS + "/client/" + clientId + "/favorites";
	}

	public static final String DELETE_FAVORITE(int clientId) {
		return SERVICE_CC + V1_CLIENTS + "/client/" + clientId + "/favorite";
	}

	public static final String MENU_CLIENTS(int clientId) {
		return GET_CLIENT + clientId + "/menu";
	}

	public static final String GET_CLIENTNAME = SERVICE_CC + V1_CLIENTS + "/name/";
	public static final String GET_ORGID = "/service/cc/v1/orgs/";
	public static final String GET_ORG_HIERARCHY = "/service/cc/v1/org-hierarchy/";
	public static final String PATCH_ORG_STATUS = "/status/";
	public static final String POST_MOVE_ORG_NODE = "/service/cc/v1/business-units";
	public static final String GET_TIMEZONES = "/service/cc/v1/timezones";
<<<<<<< HEAD
	public static final String PUT_APPLYTO_INHERITFROM = "/service/cc/v1/client-orgs/products";
=======
	public static final String GET_CLIENT_ORG_ID = "/service/cc/v1/client-orgs/";
>>>>>>> 5d396a731ce0e4bd636716e38cd9ab0c50f8704d
}
