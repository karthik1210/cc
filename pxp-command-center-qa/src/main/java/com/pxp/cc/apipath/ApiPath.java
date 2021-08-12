// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.apipath;

public class ApiPath {

	public static final String BEARER_TYPE = "Bearer ";
	public static final String AUTHORIZATION = "Authorization";
	public static final String CONTENT_TYPE = "application/json";
	public static final String POST_SIGNIN = "/service/identity/v1/api/auth/signin";
	public static final String SERVICE_CC = "/service/cc";
	public static final String V1_CLIENTS = "/v1/clients";
	public static final String GET_CLIENT = SERVICE_CC + V1_CLIENTS;
	public static final String POST_CLIENT = SERVICE_CC + V1_CLIENTS;
	public static final String GET_CLIENT_TYPE = SERVICE_CC + V1_CLIENTS + "/types";
	public static final String GET_CLIENT_DIRECTORY = SERVICE_CC + V1_CLIENTS + "/directory";

}
