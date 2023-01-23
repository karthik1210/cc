// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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

	public static final String CLIENT_DETAILS_NULL_VALUES_BL() {
		return CLIENT_BL + "/null";
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

	public static final String GET_ORGANIZATION_LIST_DL(int clientId) {
		return SERVICE_ORG_DL + "/v1/clients/" + clientId + "/orgs";
	}

	public static final String INVALID_PATH_GET_ORGANIZATION_LIST_DL(int clientId) {
		return SERVICE_ORG_DL + "/v1/clients/" + clientId + "/orgsabcdfef";
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

	public static final String CLIENT_DIRECTORY_BL(String searchData, String page, String pageSize, Object type) {
		return SERVICE_CLIENT_BL + "/v1/clients";
	}

	public static final String CLIENT_DIRECTORY_DL = SERVICE_CLIENT_DL + "/v1/clients";

	public static final String CLIENT_DIRECTORY_DL(String searchData, String page, String pageSize, Object type) {
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

	public static final String AUTOSUGGESTION_CLIENT_DL = SERVICE_CLIENT_DL + "/v1/clients/auto-suggestions";
	public static final String INVALID_PATH_AUTOSUGGESTION_CLIENT_DL = SERVICE_CLIENT_DL + "/auto-suggestionsadsds";

	public static final String UPDATE_ORG(int orgId) {
		return ORG_DL + "/" + "{orgId}";
	}

	public static final String INVALID_PATH_UPDATE_ORG(int orgId) {
		return ORG_DL + "sssssssssssssssssss/" + "{orgId}";
	}

	public static final String GET_ORGANIZATION_LIST_BL(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + "{clientId}" + "/orgs";
	}

	public static final String INVALID_PATH_GET_ORGANIZATION_LIST_BL(int clientId) {
		return SERVICE_CLIENT_BL + "/v1/clients/" + "{clientId}" + "/orgsssssabc";
	}

	public static final String UPDATE_ORG_BL(int orgId) {
		return ORG_BL + "/" + "{orgId}";
	}

	public static final String INVALID_PATH_UPDATE_ORG_BL(int orgId) {
		return ORG_BL + "sssssssssssssssssss/" + "{orgId}";
	}

	public static String MOVE_ORG_NODE(int clientId) {

		return SERVICE_CLIENT_BL + "/v1/clients/" + clientId + "/orgs";
	}

	public static String INVALID_PATH_MOVE_ORG_NODE(int clientId) {

		return SERVICE_CLIENT_BL + "/v1/clients/" + clientId + "/orgssssss";
	}

	public static String VALIDATE_USER_BL() {
		return SERVICE_USER_BL + "/v1/users/validate-user";
	}

	public static String INVALID_PATH_VALIDATE_USER_BL() {
		return SERVICE_USER_BL + "/v1/userssss/validate-usersssss";
	}

	public static final String USER_DETAILS_BL = SERVICE_USER_BL + "/v1/users/";
	public static final String INVALID_PATH_USER_DETAILS_BL = SERVICE_USER_BL + "/v1/usersss/";

	public static String MOVE_ORGANIZATION_NODE(int orgId) {
		return SERVICE_ORG_BL + "/v1/orgs/" + orgId + "/client-orgs";
	}

	public static String INVALID_PATH_MOVE_ORGANIZATION_NODE(int orgId) {
		return SERVICE_ORG_BL + "/v1/orgs/" + orgId + "/client-orgssssss";
	}

	public static String VALIDATE_USER_DL() {
		return SERVICE_USER_DL + "/v1/users/validate-user";
	}

	public static String INVALID_PATH_VALIDATE_USER_DL() {
		return SERVICE_USER_DL + "/v1/Usersss/validateUser";
	}

	public static final String SERVICE_ROLE_BL = "/service/role-service";

	public static final String USER_ROLES_BY_USERTYPE_BL(String userType) {
		return SERVICE_ROLE_BL + "/v1/usertypes/" + "{userType}" + "/roles";
	}

	public static final String INVALID_PATH_USER_ROLES_BY_USERTYPE_BL(String userType) {
		return SERVICE_ROLE_BL + "/v1/usertypes/" + "{userType}" + "/rolesss";
	}

	public static final String SERVICE_PRODUCT_BL = "/service/product-service";
	public static final String PRODUCTS_BL = SERVICE_PRODUCT_BL + "/v1/products";
	public static final String INVALID_PATH_PRODUCTS_BL = SERVICE_PRODUCT_BL + "/v1/productsssss";

	public static final String USER_DL = SERVICE_USER_DL + "/v1/users";
	public static final String INVALID_PATH_USER_DL = SERVICE_USER_DL + "/v1/usersabc";

	public static final String USER_BL = SERVICE_USER_BL + "/v1/users";
	public static final String INVALID_PATH_USER_BL = SERVICE_USER_BL + "/v1/usersabc";

	public static final String USER_DETAILS_DL = SERVICE_USER_DL + "/v1/users/";

	public static final String ADD_ASSOCIATE_ENTITIES_BL(int userId) {
		return SERVICE_USER_BL + "/v1/users/" + "{userId}" + "/entities";
	}

	public static final String ADD_ASSOCIATE_ENTITIES_INVALID_URL_BL(int userId) {
		return SERVICE_USER_BL + "/v1/usersss/" + "{userId}" + "/entitiesyz123";
	}

	public static final String ASSOCIATE_ENTITIES_BL = SERVICE_USER_BL + "/v1/users/" + "{userId}" + "/entities";
	public static final String ASSOCIATE_ENTITIES_INVALID_URL_BL = SERVICE_USER_BL + "/v1/users/" + "{userId}"
			+ "/entitiesssss";

	public static final String INVALID_INTERNAL_USER_DL = SERVICE_USER_DL + "/v1/usersssdsdss/";
	public static final String INVALID_PATH_INTERNALUSER_BL = SERVICE_USER_BL + "/v1/usersabc123/";
	public static final String SERVICE_PRODUCT_DL = "/service/product-data";
	public static final String PRODUCTS_DL = SERVICE_PRODUCT_DL + "/v1/products";
	public static final String INVALID_PATH_PRODUCTS_DL = SERVICE_PRODUCT_DL + "/v1/productsssss";

	public static final String ORG_HIERARCHY_DL(int orgId) {
		return SERVICE_ORG_DL + "/v1/orgs/" + orgId + "/org-hierarchy";
	}

	public static final String INVALID_PATH_ORG_HIERARCHY_DL(int orgId) {
		return SERVICE_ORG_DL + "/v1/orgs/" + orgId + "/org-hierarchysssss";
	}

	public static String ADD_USER_BL(int userId) {
		return SERVICE_USER_BL + "/v1/users/" + userId;
	}

	public static String INVALID_PATH_ADD_USER_BL(int id) {
		return SERVICE_USER_BL + "/v1/usersssssss/" + id;
	}

	public static String INVALID_PATH_VAReseller_USER_SERVICE(int id) {
		return INVALID_PATH_INTERNALUSER_BL + id;
	}

	public static String INVALID_PATH_INTERNAL_USER_SERVICE(int id) {
		return INVALID_PATH_INTERNALUSER_BL + id;
	}

	public static String UPDATE_USER_DL(int userId) {
		return SERVICE_USER_DL + "/v1/users/" + userId;
	}

	public static String INVALID_PATH_UPDATE_USER_DL(int userId) {
		return SERVICE_USER_DL + "/v1/userssssss/" + userId;
	}

	public static final String ASSOCIATE_ENTITIES_DL = SERVICE_USER_DL + "/v1/users/" + "{userId}" + "/entities";
	public static final String ASSOCIATE_ENTITIES_INVALID_URL_DL = SERVICE_USER_DL + "/v1/users/" + "{userId}"
			+ "/entitiesssss";

	public static final String INVALID_INTERNAL_USER_DATA(int id) {
		return INVALID_INTERNAL_USER_DL + id;
	}

	public static final String ADD_ASSOCIATE_ENTITIES_DL(int userId) {
		return SERVICE_USER_DL + "/v1/users/" + "{userId}" + "/entities";
	}

	public static final String ADD_ASSOCIATE_ENTITIES_INVALID_URL_DL(int userId) {
		return SERVICE_USER_DL + "/v1/usersss/" + "{userId}" + "/entitiesyz123";
	}

	public static final String ACTIVATE_DEACTIVATE_USER_DL = SERVICE_USER_DL + "/v1/users/statuses";
	public static final String ACTIVATE_DEACTIVATE_USER_INVALID_URL_DL = SERVICE_USER_DL + "/v1/userss/statusessss";

	public static final String LOGIN_DETAIL_BL(int orgId) {
		return SERVICE_USER_BL + "/v1/users/" + orgId + "/audit/login";
	}

	public static final String LOGIN_DETAIL_INVALID_URL_BL(int orgId) {
		return SERVICE_USER_BL + "/v1/users/" + orgId + "/audit/loginabc";
	}

	public static final String SERVICE_SHARED_BL = "/service/shared-service";
	public static final String SHARED_DL = "/service/shared-data";
	public static final String DUPLICATE_BRANDS_DL = SHARED_DL + "/v1/brands/";
	public static final String INVALID_DUPLICATE_BRANDS_DL = SHARED_DL + "/v1/brandsss/";

	public static final String PATCH_INTERNAL_USER_ROLES_BL(int userId, int orgId) {
		return SERVICE_USER_BL + "/v1/users/" + userId + "/entities/" + orgId + "/roles";
	}

	public static final String PATCH_INTERNAL_USER_ROLES_INVALID_URL_BL(int userId, int orgId) {
		return SERVICE_USER_BL + "/v1/users/" + userId + "/entities/" + orgId + "/rolesssss";
	}

	public static final String PATCH_USER_ROLES_DL(int userId, int orgId) {
		return SERVICE_USER_DL + "/v1/users/" + userId + "/entities/" + orgId + "/roles";
	}

	public static final String PATCH_USER_ROLES_INVALID_URL_DL(int userId, int orgId) {
		return SERVICE_USER_DL + "/v1/users/" + userId + "/entities/" + orgId + "/rolesssss";
	}

	public static final String UPDATE_USER_ROLE_BL(int userId) {
		return SERVICE_USER_BL + "/v1/users/" + userId + "/roles";
	}

	public static final String INVALID_PATH_UPDATE_USER_ROLE_BL(int userId) {
		return SERVICE_USER_BL + "/v1/users/" + userId + "/rolesssss";
	}

	public static final String ACTIVATE_DEACTIVATE_USER_BL = SERVICE_USER_BL + "/v1/users/statuses";
	public static final String ACTIVATE_DEACTIVATE_USER_INVALID_URL_BL = SERVICE_USER_BL + "/v1/userss/statusessss";

	public static final String LOGIN_DETAIL_DL(int orgId) {
		return SERVICE_USER_DL + "/v1/users/" + orgId + "/audit/login";
	}

	public static final String LOGIN_DETAIL_INVALID_URL_DL(int orgId) {
		return SERVICE_USER_DL + "/v1/users/" + orgId + "/audit/loginabc";
	}

	public static final String LOGIN_DETAIL_NULL_VALUES_BL() {
		return SERVICE_USER_BL + "/v1/users/" + null + "/audit/login";
	}

	public static final String DUPLICATE_BRANDS_BL = SERVICE_SHARED_BL + "/v1/brands/";
	public static final String INVALID_DUPLICATE_BRANDS_BL = SERVICE_SHARED_BL + "/v1/brandsss/";

	public static final String BRAND_DETAILS_BL(int brandId) {
		return SERVICE_SHARED_BL + "/v1/brands/" + brandId;
	}

	public static final String INVALID_PATH_BRAND_DETAILS_BL(int brandId) {
		return SERVICE_SHARED_BL + "/v1/brandsssss/" + brandId;
	}

	public static final String ADD_OR_REMOVE_RESOURCES_FOR_APPOINTMENT_TYPE(int practiceId, int appointmentTypeId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId
				+ "/resources";
	}

	public static final String INVALID_PATH_ADD_OR_REMOVE_RESOURCES_FOR_APPOINTMENT_TYPE(int practiceId,
																						 int appointmentTypeId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId
				+ "/resourcessssss";
	}

	public static final String PROVIDER_DETAILS_BL(int providerId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId;
	}

	public static final String GET_PROVIDER_DETAILS_BL(int clientId, int providerId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/providers/" + providerId;
	}

	public static final String PROVIDER_DETAILS_INVALID_URL_BL(int providerId) {
		return SERVICE_SHARED_BL + "/v1/providerssss/" + providerId;
	}

	public static final String CREATE_PROVIDER_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/providers";
	}

	public static final String INVALID_PATH_CREATE_PROVIDER_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/providersssss";
	}

	public static final String PROVIDER_DETAILS_DL(int clientId, int providerId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/providers/" + providerId;
	}

	public static final String PROVIDER_DETAILS_INVALID_URL_DL(int providerId) {
		return SHARED_DL + "/v1/providerssss/" + providerId;
	}

	public static final String UPDATE_ORG_SOLUTIONS_DL(int orgId) {
		return SERVICE_ORG_DL + "/v1/orgs/" + orgId + "/solutions";
	}

	public static final String INVALID_PATH_UPDATE_ORG_SOLUTIONS_DL(int orgId) {
		return SERVICE_ORG_DL + "/v1/orgs/" + orgId + "/solutionsssss";
	}

	public static final String FETCH_RESOURCES_FOR_APPOINTMENTTYPE_BL(int practiceId, int appointmentTypeId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId
				+ "/resources";
	}

	public static final String FETCH_RESOURCES_FOR_APPOINTMENTTYPE_BL_INVALID_URL(int practiceId,
																				  int appointmentTypeId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId
				+ "/resourcesblabla";
	}

	public static final String FETCH_RESOURCES_FOR_APPOINTMENTTYPE_BL_NULL_VALUES = SERVICE_SHARED_BL
			+ "/v1/practices/null/appointment-types/null/resources";

	public static final String FETCH_RESOURCES_FOR_APPOINTMENTTYPE_DL(int practiceId, int appointmentTypeId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId + "/resources";
	}

	public static final String FETCH_RESOURCES_FOR_APPOINTMENTTYPE_DL_NULL_VALUES = SHARED_DL
			+ "/v1/practices/null/appointment-types/null/resources";

	public static final String FETCH_RESOURCES_FOR_APPOINTMENTTYPE_DL_INVALID_URL(int practiceId,
																				  int appointmentTypeId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId
				+ "/resourcesblabla";
	}

	public static final String PRACTICES_ASSOCIATED_TO_PROVIDER_NULL_VALUES_BL = SERVICE_SHARED_BL
			+ "/v1/providers/null/practices";

	public static final String PRACTICES_ASSOCIATED_TO_PROVIDER_BL(int providerId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices";
	}

	public static final String PRACTICES_ASSOCIATED_TO_PROVIDER_INVALID_URL_BL(int providerId) {
		return SERVICE_SHARED_BL + "/v1/providerssss/" + providerId + "/practices";
	}

	public static final String FETCH_SOLUTIONS_FOR_APPOINTMENTTYPE_DL(int practiceId, int appointmentTypeId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId + "/solutions";
	}

	public static final String FETCH_SOLUTIONS_FOR_APPOINTMENTTYPE_DL_NULL_VALUES = SHARED_DL
			+ "/v1/practices/null/appointment-types/null/solutions";

	public static final String FETCH_SOLUTIONS_FOR_APPOINTMENTTYPE_DL_INVALID_URL(int practiceId,
																				  int appointmentTypeId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId
				+ "/solutionsblabla";
	}

	public static final String SEARCH_LOCATIONS_DL = SHARED_DL + "/v1/locations";

	public static final String INVALIDURL_SEARCH_LOCATIONS_DL = SHARED_DL + "/v1/locationsdd";

	public static final String FETCH_SOLUTIONS_FOR_RESOURCES_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/solutions";
	}

	public static final String FETCH_SOLUTIONS_FOR_RESOURCES_DL_NULL_VALUES = SHARED_DL
			+ "/v1/resources/null/practices/null/solutions";

	public static final String FETCH_SOLUTIONS_FOR_RESOURCES_DL_INVALID_URL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/solutionsqw";
	}

	public static final String PRACTICES_ASSOCIATED_TO_PROVIDER_DL(int providerId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices";
	}

	public static final String PRACTICES_ASSOCIATED_TO_PROVIDER_INVALID_URL_DL(int providerId) {
		return SHARED_DL + "/v1/providerssss/" + providerId + "/practices";
	}

	public static final String USER_INFO_DL = SERVICE_USER_DL + "/v1/users/userinfo";
	public static final String INVALID_PATH_USER_INFO_DL = SERVICE_USER_DL + "/v1/users/userinfo/1234aseds";

	public static final String USER_INFO_BL = SERVICE_USER_BL + "/v1/users/userinfo";
	public static final String INVALID_PATH_USER_INFO_BL = SERVICE_USER_BL + "/v1/users/userinfo/1234as";

	public static String GET_USERS_DL() {
		return SERVICE_USER_DL + "/v1/users";
	}

	public static String GET_USERS_INVALID_PATH_DL() {
		return SERVICE_USER_DL + "/v1/Usersss";
	}

	public static final String BRAND_DETAILS_DL(int brandId) {
		return SHARED_DL + "/v1/brands/" + brandId;
	}

	public static final String INVALID_PATH_BRAND_DETAILS_DL(int brandId) {
		return SHARED_DL + "/v1/brandsssss/" + brandId;
	}

	public static final String UPDATE_PROVIDER_DETAILS(int providerId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices";
	}

	public static final String GET_PROVIDER_FOR_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers";
	}

	public static final String INVALID_PATH_GET_PROVIDER_FOR_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers12abc";
	}

	public static final String GET_PROVIDER_FOR_LOCATION_BL_NULL_VALUES = SERVICE_SHARED_BL
			+ "/v1/locations/null/practices/null/providers";

	public static final String GET_PROVIDERS_FOR_LOCATION_DL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers";
	}

	public static final String GET_PROVIDERS_FOR_LOCATION_INVALID_PATH_DL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers12abc";
	}

	public static final String GET_PROVIDERS_FOR_LOCATION_DL_NULL_VALUES = SHARED_DL
			+ "/v1/locations/null/practices/null/providers";

	public static final String FETCH_SOLUTIONS_FOR_RESOURCES_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/solutions";
	}

	public static final String FETCH_SOLUTIONS_FOR_RESOURCES_BL_NULL_VALUES = SERVICE_SHARED_BL
			+ "/v1/resources/null/practices/null/solutions";

	public static final String FETCH_SOLUTIONS_FOR_RESOURCES_BL_INVALID_URL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/solutionsqw";
	}

	public static final String DELETE_BRAND_DETAILS_DL(int brandId) {
		return SHARED_DL + "/v1/brands/" + brandId;
	}

	public static final String INVALID_PATH_DELETE_BRAND_DETAILS_DL(int brandId) {
		return SHARED_DL + "/v1/brandsssss/" + brandId;
	}

	public static final String DELETE_BRAND_DETAILS_BL(int brandId) {
		return SERVICE_SHARED_BL + "/v1/brands/" + brandId;
	}

	public static final String INVALID_PATH_DELETE_BRAND_DETAILS_BL(int brandId) {
		return SERVICE_SHARED_BL + "/v1/brandsssss/" + brandId;
	}

	public static final String UPDATE_ORG_SOLUTIONS_BL(int orgId) {
		return SERVICE_ORG_BL + "/v1/orgs/" + orgId + "/solutions";
	}

	public static final String UPDATE_ORG_SOLUTIONS_BL_INVALID_URL(int orgId) {
		return SERVICE_ORG_BL + "/v1/orgs/" + orgId + "/solutionsinvalidurl";
	}

	public static final String UPDATE_ORG_SOLUTIONS_DL_INVALID_URL(int orgId) {
		return SERVICE_ORG_DL + "/v1/orgs/" + orgId + "/solutionsinvalidurl";
	}

	public static final String UPDATE_ORG_SOLUTIONS_BL_ORG_NULL = SERVICE_ORG_BL + "/v1/orgs/null/solutions";

	public static final String UPDATE_ORG_SOLUTIONS_DL_ORG_NULL = SERVICE_ORG_DL + "/v1/orgs/null/solutions";

	public static final String CREATEBRANDS_DL = SHARED_DL + "/v1/brands/";
	public static final String INVALID_CREATE_BRANDS_DL = SHARED_DL + "/v1/brandsss/";

	public static final String UPDATE_BRAND_DL(int BrandId) {
		return SHARED_DL + "/v1/brands/" + BrandId;
	}

	public static final String Invalid_UPDATE_BRAND_DL(int BrandId) {
		return SHARED_DL + "/v1/brandsssss/" + BrandId;
	}

	public static final String GET_APPOINTMENT_TYPE_DL(int appointmentTypeId) {
		return SHARED_DL + "/v1/appointment-types/" + appointmentTypeId;
	}

	public static final String INVALID_PATH_FOR_GET_APPOINTMENT_TYPE_DL(int appointmentTypeId) {
		return SHARED_DL + "/v1/appointment-typesss/" + appointmentTypeId;
	}

	public static final String GET_LOCATIONLIST_ROLE_BL(int UserId, int OrgId) {
		return SERVICE_USER_BL + "/v1/users/" + UserId + "/orgs/" + OrgId;
	}

	public static final String INVALID_GET_LOCATIONLIST_ROLE_BL = SERVICE_USER_BL + "/v1/users/4003807/orgsss/4677";
	public static final String GET_LOCATIONLIST_ROLE_BL_NULL_VALUES = SERVICE_USER_BL + "/v1/users/null/orgs/null";
	public static final String GET_LOCATIONLIST_ROLE_BL_ALPHANUM_VALUES = SERVICE_USER_BL
			+ "/v1/users/4003807@#ASD/orgs/4677";

	public static final String FETCH_RESOURCES_FOR_PROVIDERS_VALID_URL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String FETCH_RESOURCES_FOR_PROVIDERS_INVALID_URL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providersssss/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String FETCH_RESOURCES_FOR_PROVIDERS_WITH_INVALID_DATATYPE(String providerId,
																				   String practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String FETCH_RESOURCE_FOR_PROVIDER_VALID_URL_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String FETCH_RESOURCE_FOR_PROVIDER_INVALID_URL_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providersssss" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String FETCH_RESOURCE_FOR_PROVIDER_VALID_URL_DL_INVALID_DATATYPE(String providerId,
																						 String practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String FETCH_LIST_OF_BRANDS_DL(int orgId) {
		return SHARED_DL + "/v1/orgs/" + orgId + "/brands";
	}

	public static final String FETCH_LIST_OF_BRANDS_DL_INVALID_ORG(int orgId) {
		return SHARED_DL + "/v1/orgs/" + orgId + "!3w/brands";
	}

	public static final String FETCH_LIST_OF_BRANDS_INVALID_URL_DL(int orgId) {
		return SHARED_DL + "/v1/orgs/" + orgId + "/brandsinvalidurl";
	}

	public static final String BRAND_LIST_DL(int orgId) {
		return SHARED_DL + "/v1/orgs/" + orgId + "/brands";
	}

	public static final String INVALID_PATH_BRAND_LIST_DL(int orgId) {
		return SHARED_DL + "/v1/orgs/" + orgId + "/brandsssss";
	}

	public static final String UPDATE_BRAND_DETAILS_BL(String BrandId) {
		return SERVICE_SHARED_BL + "/v1/brands/" + BrandId;
	}

	public static final String CREATE_BRAND_BL = SERVICE_SHARED_BL + "/v1/brands/";

	public static final String CREATE_BRAND_DL = SHARED_DL + "/v1/brands/";

	public static final String GET_APPOINTMENT_TYPES_BL(int appointmentTypeId) {
		return SERVICE_SHARED_BL + "/v1/appointment-types/" + appointmentTypeId;
	}

	public static final String GET_APPOINTMENT_TYPES_INVALID_PATH_BL(int appointmentTypeId) {
		return SERVICE_SHARED_BL + "/v1/appointment-typesss/" + appointmentTypeId;
	}

	public static final String GET_APPOINTMENT_TYPES_BL_NULL_VALUES = SERVICE_SHARED_BL + "/v1/appointment-types/null";

	public static final String GET_RESOURCE_DETAILS_DL(int resourceId) {
		return SHARED_DL + "/v1/resources/" + resourceId;
	}

	public static final String RESOURCE_IMAGE_DL(int resourceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/images";
	}

	public static final String INVALID_PATH_RESOURCE_IMAGE_DL(int resourceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/imagessss";
	}

	public static final String FETCH_LIST_OF_BRANDS_BL(int orgId) {
		return SERVICE_SHARED_BL + "/v1/orgs/" + orgId + "/brands";
	}

	public static final String FETCH_LIST_OF_BRANDS_BL_INVALID_ORG(int orgId) {
		return SERVICE_SHARED_BL + "/v1/orgs/" + orgId + "!3w/brands";
	}

	public static final String FETCH_LIST_OF_BRANDS_INVALID_URL_BL(int orgId) {
		return SERVICE_SHARED_BL + "/v1/orgs/" + orgId + "/brandsinvalidurl";
	}

	public static final String UPDATE_PRACTICE_AND_RESOURCES_DL_INVALID_URL(int practiceId, int resourceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resourcess/" + resourceId;
	}

	public static final String UPDATE_PRACTICE_AND_RESOURCES_DL_VALID_URL(int practiceId, int resourceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String UPDATE_PRACTICE_AND_RESOURCES_DL_INVALID_DATATYPE(String practiceId, String resourceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String BRAND_LIST_BL(int orgId) {
		return SERVICE_SHARED_BL + "/v1/orgs/" + orgId + "/brands";
	}

	public static final String INVALID_PATH_BRAND_LIST_BL(int orgId) {
		return SERVICE_SHARED_BL + "/v1/orgs/" + orgId + "/brandsssss";
	}

	public static final String FETCH_PRACTICE_FOR_LOCATIONS_DL_VALID_URL(int locationId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices";
	}

	public static final String FETCH_PRACTICE_FOR_LOCATIONS_DL_INVALID_URL(int locationId) {
		return SHARED_DL + "/v1/locationsss/" + locationId + "/practices";
	}

	public static final String UPDATE_BRAND_IMAGE_DL(int brandId) {
		return SHARED_DL + "/v1/brands/" + brandId + "/logo";
	}

	public static final String DETETE_BRAND_IMAGE_DL(int brandId, String id) {
		return SHARED_DL + "/v1/brands/" + brandId + "/images/" + id;
	}

	public static final String DETETE_BRAND_IMAGE_INVALID_URL_DL(int brandId, String id) {
		return SHARED_DL + "/v1/brands/" + brandId + "/imagesssss/" + id;
	}

	public static final String DETETE_BRAND_IMAGE_NullValue_DL() {
		return SHARED_DL + "/v1/brands/brandId/images/id";
	}

	public static final String CREATE_PROVIDER_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/providers";
	}

	public static final String GET_RESOURCE_DETAILS_BL(int resourceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId;
	}

	public static final String GET_RESOURCE_DETAILS_BL_INVALID_URL(int resourceId) {
		return SERVICE_SHARED_BL + "/v1/resources23/" + resourceId;
	}

	public static final String GET_RESOURCE_DETAILS_BL_NULL = SERVICE_SHARED_BL + "/v1/resources/" + "null";

	public static final String CREATE_RESOURCE_DETAILS_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources";
	}

	public static final String GET_LOCATION_FOR_RESOURCE_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/locations";
	}

	public static final String GET_LOCATION_FOR_RESOURCE_INVALID_URL_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/locationssss";
	}

	public static final String GET_LOCATION_FOR_RESOURCE_DL_NULL_VALUES() {
		return SHARED_DL + "/v1/resources/null/practices/null/locations";
	}

	public static final String UPDATE_BRAND_DETAILS_DL(String BrandId) {
		return SHARED_DL + "/v1/brands/" + BrandId;
	}

	public static final String UPDATE_BRAND_DETAILS_INVALID_URL_DL(String BrandId) {
		return SHARED_DL + "/v1/brandssssss/" + BrandId;
	}

	public static final String CREATE_BRANDS_INVALID_URL_DL = SHARED_DL + "/v1/brandsss/";

	public static final String ADD_RESOURCE_IMAGE(int resourceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/images";
	}

	public static final String ADD_RESOURCE_IMAGE_INVALID_URL(int resourceId) {
		return SERVICE_SHARED_BL + "/v1/resources123/" + resourceId + "/imagesss";
	}

	public static final String SAVE_PRACTICE_PROVIDER_BL(int practiceId, int providerId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/providers/" + providerId;
	}

	public static final String INVALID_PATH_SAVE_PRACTICE_PROVIDER_BL(int practiceId, int providerId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/providerssss/" + providerId;
	}

	public static final String GET_PRACTICE_PROVIDER_BL(int practiceId, int providerId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/providers/" + providerId;
	}

	public static final String PRACTICE_WEBSITES(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-website/practices/" + practiceId + "/sites";
		else
			return SERVICE_ORG_DL + "/v1/practice-website/practices/" + practiceId + "/sites";
	}

	public static final String PRACTICE_WEBSITES_WITHOUT_PRACTICEID(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-website/practices/sites";
		else
			return SERVICE_ORG_DL + "/v1/practice-website/practices/sites";
	}

	public static final String PRACTICE_WEBSITES_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-website/practices/" + practiceId + "/sites";
		else
			return SERVICE_ORG_DL + "/v1/practice-website/practices/" + practiceId + "/sites";
	}

	public static final String PRACTICE_WEBSITES_WITH_INVLAID_URI(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-website/practicesssss/" + practiceId + "/sites";
		else
			return SERVICE_ORG_DL + "/v1/practice-website/practicesssss/" + practiceId + "/sites";
	}

	public static final String NULL_PRACTICE_WEBSITES(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-website/practices/null/sites";
		else
			return SERVICE_ORG_DL + "/v1/practice-website/practices/null/sites";
	}

	public static final String UPDATE_LOCATION_FOR_PROVIDER_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/locations";
	}

	public static final String UPDATE_LOCATION_FOR_PROVIDER_INVALID_URL_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/locationsssss";
	}

	public static final String UPDATE_LOCATION_FOR_PROVIDER__NULL_VALUE_DL() {
		return SHARED_DL + "/v1/providers/null/practices//locations";
	}

	public static final String UPDATE_SOLUTIONS_FOR_RESOURCES_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_RESOURCES_INVALID_URL_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/solutionsss";
	}

	public static final String UPDATE_SOLUTION_FOR_LOCATION_DL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_RESOURCES__NULL_VALUE_DL() {
		return SHARED_DL + "/v1/resources/null/practices/null/solutions";
	}

	public static final String CREATE_LOCATION_BL(int clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/locations";
	}

	public static final String INVALID_PATH_CREATE_LOCATION_BL(int clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/locationssss";
	}

	public static final String LOCATION_DETAILS_BL(int clientId, int locationId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/locations/" + locationId;
	}

	public static final String UPDATE_ORG_WEBSITES(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgs/" + practiceId;
		else
			return SERVICE_ORG_DL + "/v1/orgs/" + practiceId;
	}

	public static final String UPDATE_ORG_WEBSITES_WITHOUT_PRACTICEID(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgs/";
		else
			return SERVICE_ORG_DL + "/v1/orgs/";
	}

	public static final String UPDATE_ORG_WEBSITES_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgs/" + practiceId;
		else
			return SERVICE_ORG_DL + "/v1/orgs/" + practiceId;
	}

	public static final String UPDATE_ORG_WEBSITES_WITH_INVLAID_URI(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgsss/" + practiceId;
		else
			return SERVICE_ORG_DL + "/v1/orgsss/" + practiceId;
	}

	public static final String NULL_UPDATE_ORG_WEBSITES(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgs/null";
		else
			return SERVICE_ORG_DL + "/v1/orgs/null";
	}

	public static final String UPDATE_BRAND_LOGO_BL(int brandId) {
		return SERVICE_SHARED_BL + "/v1/brands/" + brandId + "/upload";
	}

	public static final String UPDATE_BRAND_LOGO_INVALID_URL_BL(int brandId) {
		return SERVICE_SHARED_BL + "/v1/brands/" + brandId + "/uploadsss";
	}

	public static final String UPDATE_BRAND_LOGO_NULL_BRANDID_BL() {
		return SERVICE_SHARED_BL + "/v1/brands/" + null + "/upload";
	}

	public static final String UPDATE_LOCATION_FOR_PRACTICE_BL(int locationId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices";
	}

	public static final String UPDATE_BRAND_IMAGE_DL_INVALID_URL(int brandId) {
		return SHARED_DL + "/v1/brands/" + brandId + "/logoinvalidurl";
	}

	public static final String CREATE_LOCATION_DL(int clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/locations";
	}

	public static final String INVALID_PATH_CREATE_LOCATION_DL(int clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/locationssss";
	}

	public static final String LOCATION_DETAILS_DL(int clientId, int locationId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/locations/" + locationId;
	}

	public static final String UPDATE_LOCATION_FOR_PRACTICE_DL(int locationId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices";
	}

	public static final String UPDATE_SOLUTION_FOR_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/solutions";
	}

	public static final String LOCATION_FOR_CLIENT_BL(String clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/locations";
	}

	public static final String INVALID_URL_LOCATION_FOR_CLIENT_BL(String clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/locationssss";
	}

	public static final String LOCATION_FOR_CLIENT_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/clients/null/locations";
	}

	public static final String LOCATION_FOR_CLIENT_DL(String clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/locations";
	}

	public static final String INVALID_URL_LOCATION_FOR_CLIENT_DL(String clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/locationssss";
	}

	public static final String LOCATION_FOR_CLIENT_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/clients/null/locations";
	}

	public static final String ASSOCIATEDISASSOCIATE_BRAND_FOR_CLIENTORORG__NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/orgs/null/brands";

	}

	public static final String DETETE_BRAND_IMAGE_BL(int brandId, String id) {
		return SERVICE_SHARED_BL + "/v1/brands/" + brandId + "/images/" + id;
	}

	public static final String DETETE_BRAND_IMAGE_INVALID_URL_BL(int brandId, String id) {
		return SERVICE_SHARED_BL + "/v1/brands/" + brandId + "/imagesssss/" + id;
	}

	public static final String DETETE_BRAND_IMAGE_NullValue_BL() {
		return SERVICE_SHARED_BL + "/v1/brands/brandId/images/id";
	}

	public static final String INVALID_PATH_LOCATION_DETAILS_BL(int locationId) {
		return SERVICE_SHARED_BL + "/v1/locationsssss/" + locationId;
	}

	public static final String CREATE_RESOURCE_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources";
	}

	public static final String CREATE_RESOURCE_NULL_VALUE_BL = SERVICE_SHARED_BL + "/v1/practices/null/resources";

	public static final String CREATE_RESOURCE_INVALID_URL_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources12@";
	}

	public static final String PRACTICE_SOCIALS(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-social/practices/" + practiceId + "/socials";
		else
			return SERVICE_ORG_DL + "/v1/practice-social/practices/" + practiceId + "/socials";
	}

	public static final String PRACTICE_SOCIALS_WITHOUT_PRACTICEID(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-social/practices//socials";
		else
			return SERVICE_ORG_DL + "/v1/practice-social/practices//socials";
	}

	public static final String PRACTICE_SOCIALS_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-social/practices/" + practiceId + "/socials";
		else
			return SERVICE_ORG_DL + "/v1/practice-social/practices/" + practiceId + "/socials";
	}

	public static final String PRACTICE_SOCIALS_WITH_INVLAID_URI(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-social/practicesss/" + practiceId + "/socials";
		else
			return SERVICE_ORG_DL + "/v1/practice-social/practicesss/" + practiceId + "/socials";
	}

	public static final String INVALID_PATH_LOCATION_DETAILS_DL(int locationId) {
		return SHARED_DL + "/v1/locationsssss/" + locationId;
	}

	public static final String CREATE_RESOURCE_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resources";

	}

	public static final String GET_SOCIALS(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_COMMONS_BL + "/v1/socials";
		else
			return SERVICE_COMMONS_DL + "/v1/socials";
	}

	public static final String GET_SOCIALS_INVALID_URI(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_COMMONS_BL + "/v1/socialsss";
		else
			return SERVICE_COMMONS_DL + "/v1/socialsss";
	}

	public static final String INVALID_PATH_UPDATE_LOCATION_FOR_PRACTICE_BL(int locationId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practicessss";
	}

	public static final String INVALID_PATH_UPDATE_LOCATION_FOR_PRACTICE_DL(int locationId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practicessss";
	}

	public static final String CREATE_RESOURCE_DL(String practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resources";

	}

	public static final String CREATE_RESOURCE_INVALID_URL_DL(int practiceId) {
		return SHARED_DL + "/v1/practicesSS/" + practiceId + "/resources123";
	}

	public static final String CREATE_RESOURCE_NULL_VALUE_DL = SHARED_DL + "/v1/practices/null/resources";

	public static final String ADD_RESOURCE_FOR_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/resources";
	}

	public static final String DELETE_RESOURCE_FOR_LOCATION_BL(int locationId, int practiceId, int resourceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/resources/"
				+ resourceId;
	}

	public static final String ADD_RESOURCE_FOR_LOCATION_DL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/resources";
	}

	public static final String DELETE_RESOURCE_FOR_LOCATION_DL(int locationId, int practiceId, int resourceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String CREATE_APPOINTMENT_TYPE_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/appointment-types";
	}

	public static final String PRACTICE_INTERFACE(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/interfaces";
		else
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/interfaces";
	}

	public static final String PRACTICE_INTERFACE_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/interfaces";
		else
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/interfaces";
	}

	public static final String PRACTICE_INTERFACE_WITH_INVLAID_URI(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/interfacesss";
		else
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/interfacesss";
	}

	public static final String ASSOCIATE_DEASSOCIATE_PRACTICE_TO_LOCATION_BL(int locationId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices";
	}

	public static final String FETCH_LOCATION_BY_PRACTICE_BL_VALID_DETAILS(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId;
	}

	public static final String FETCH_LOCATION_BY_PRACTICE_USING_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/locations/" + null + "/practices/" + null;
	}

	public static final String INVALID_PATH_TO_FETCH_LOCATION_BY_PRACTICE_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practicesERROR/" + practiceId;
	}

	public static final String PRACTICE_INTERFACE_WITH_INTERFACEID(String serviceLayer, int practiceId,
																   int interfaceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/interfaces/" + interfaceId;
		else
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/interfaces/" + interfaceId;
	}

	public static final String PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_DATATYPE(String serviceLayer,
																						 String practiceId, String interfaceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/interfaces/" + interfaceId;
		else
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/interfaces/" + interfaceId;
	}

	public static final String PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_URI(String serviceLayer, int practiceId,
																					int interfaceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/interfacesss/" + interfaceId;
		else
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/interfacesss/" + interfaceId;
	}

	public static final String GET_EXTERNAL_SYSTEMID(String serviceLayer, int externalSystemId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_PRODUCT_BL + "/v1/externalsystems/" + externalSystemId;
		else
			return SERVICE_PRODUCT_DL + "/v1/externalsystems/" + externalSystemId;
	}

	public static final String GET_EXTERNAL_SYSTEMID_WITH_INVLAID_DATATYPE(String serviceLayer,
																		   String externalSystemId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_PRODUCT_BL + "/v1/externalsystems/" + externalSystemId;
		else
			return SERVICE_PRODUCT_DL + "/v1/externalsystems/" + externalSystemId;
	}

	public static final String GET_EXTERNAL_SYSTEMID_WITH_INVLAID_URI(String serviceLayer, int externalSystemId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_PRODUCT_BL + "/v1/externalsystemsss/" + externalSystemId;
		else
			return SERVICE_PRODUCT_DL + "/v1/externalsystemsss/" + externalSystemId;
	}

	public static final String ADD_OR_REMOVE_APPOINTMENT_TYPES_FOR_RESOURCE_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/appointment-types";
	}

	public static final String ADD_RESOURCES_FOR_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/resources";
	}

	public static final String ADD_RESOURCES_FOR_LOCATION_INVALID_URL_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/resourcessss";
	}

	public static final String ADD_RESOURCES_FOR_LOCATION_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/locations/null/practices/null/resources";
	}

	public static final String ORGS_SOLUTION_BL = SERVICE_ORG_BL + "/v1/orgs/";

	public static final String UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_USING_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/locations/null/practices/null/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_INVALID_URL_BL(int locationId,
																						int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practicesError/" + practiceId + "/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_RESOURCES_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_RESOURCES_USING_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/resources/null/practices/null/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_RESOURCES_INVALID_URL_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practicesError/" + practiceId + "/solutions";
	}

	public static final String GET_LOCATION_FOR_RESOURCE_INVALID_URL_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practicesError/" + practiceId + "/locations";
	}

	public static final String GET_LOCATION_FOR_RESOURCE_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/locations";
	}

	public static final String GET_LOCATION_FOR_RESOURCE_BL_NULL_VALUES() {
		return SERVICE_SHARED_BL + "/v1/resources/null/practices/null/locations";
	}

	public static final String ADD_RESOURCES_FOR_LOCATION_DL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/resources";
	}

	public static final String ADD_RESOURCES_FOR_LOCATION_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/locations/null/practices/null/resources";
	}

	public static final String SAVE_LOCATION_INFO_FOR_PRACTICE_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId;
	}

	public static final String SAVE_LOCATION_INFO_FOR_PRACTICE_USING_INVALID_URL_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practicesWRONG/" + practiceId;
	}

	public static final String SAVE_LOCATION_INFO_FOR_PRACTICE_USING_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/locations/" + null + "/practices/" + null;
	}

	public static final String ASSOCIATE_PROVIDERS_TO_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers";
	}

	public static final String ASSOCIATE_PROVIDERS_TO_LOCATION_INVALID_URL_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providersssss";
	}

	public static final String ASSOCIATE_PROVIDERS_TO_LOCATION_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/locations/null/practices/null/providers";
	}

	public static final String ASSOCIATE_DEASSOCIATE_PRACTICE_TO_LOCATION_DL(int locationId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices";
	}

	public static final String FETCH_LOCATION_BY_PRACTICE_DL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId;
	}

	public static final String FETCH_LOCATION_BY_PRACTICE_USING_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/locations/null/practices/null";
	}

	public static final String DELETE_PROVIDER_FOR_LOCATION_BL(int locationId, int practiceId, int providerId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers/"
				+ providerId;
	}

	public static final String GET_UPDATE_DELETE_TOUID(String serviceLayer, int practiceId, int touId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-tou/practices/" + practiceId + "/tous/" + touId;
		else
			return SERVICE_ORG_DL + "/v1/practice-tou/practices/" + practiceId + "/tous/" + touId;
	}

	public static final String GET_UPDATE_DELETE_TOUID_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId,
																			 String touId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-tou/practices/" + practiceId + "/tous/" + touId;
		else
			return SERVICE_ORG_DL + "/v1/practice-tou/practices/" + practiceId + "/tous/" + touId;
	}

	public static final String GET_UPDATE_DELETE_TOUID_WITH_INVLAID_URI(String serviceLayer, int practiceId,
																		int touId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-tou/practices/" + practiceId + "/toussss/" + touId;
		else
			return SERVICE_ORG_DL + "/v1/practice-tou/practices/" + practiceId + "/toussss/" + touId;
	}

	public static final String GET_ADD_TOUS(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-tou/practices/" + practiceId + "/tous";
		else
			return SERVICE_ORG_DL + "/v1/practice-tou/practices/" + practiceId + "/tous";
	}

	public static final String GET_ADD_TOUS_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-tou/practices/" + practiceId + "/tous";
		else
			return SERVICE_ORG_DL + "/v1/practice-tou/practices/" + practiceId + "/tous";
	}

	public static final String GET_ADD_TOUS_WITH_INVLAID_URI(String serviceLayer, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practice-tou/practices/" + practiceId + "/toussss";
		else
			return SERVICE_ORG_DL + "/v1/practice-tou/practices/" + practiceId + "/toussss";
	}

	public static final String DELETE_PROVIDER_FOR_LOCATION_DL(int locationId, int practiceId, int providerId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers/" + providerId;
	}

	public static final String UPDATE_SOLUTION_FOR_LOCATION_DL_INVALID_URL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/solutionsinvalidurl";
	}

	public static final String SAVE_LOCATION_INFO_FOR_PRACTICE_USING_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/locations/" + null + "/practices/" + null;
	}

	public static final String UPLOAD_PROVIDER_IMAGE(int staffId) {
		return SERVICE_SHARED_BL + "/v1/staff/" + staffId + "/images";
	}

	public static final String GET_STATES(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_COMMONS_BL + "/v1/states";
		else
			return SERVICE_COMMONS_DL + "/v1/states";
	}

	public static final String GET_STATES_WITH_INVLAID_URI(String serviceLayer) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_COMMONS_BL + "/v1/statesss";
		else
			return SERVICE_COMMONS_DL + "/v1/statesss";
	}

	public static final String GET_PATIENT_ACCESS_SETTINGS_DL(String uriType, int practiceId) {
		if (uriType.equalsIgnoreCase("Valid"))
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-access-settings";
		else
			return SERVICE_ORG_DL + "/v1/practicesssss/" + practiceId + "/patient-access-settings";
	}

	public static final String GET_PATIENT_ACCESS_SETTINGS_DL(String practiceId) {
		return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-access-settings";
	}

	public static final String SAVE_PRACTICE_PROVIDER_DL(int practiceId, int providerId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/providers/" + providerId;
	}

	public static final String SAVE_PRACTICE_PROVIDER_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/practices/null/providers/null";
	}

	public static final String GET_PATIENT_GUARDIAN_ACCESS_DL(String uriType, int practiceId) {
		if (uriType.equalsIgnoreCase("Valid"))
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-guardian-access";
		else
			return SERVICE_ORG_DL + "/v1/practicesssss/" + practiceId + "/patient-guardian-access";
	}

	public static final String GET_PATIENT_GUARDIAN_ACCESS_DL(String practiceId) {
		return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-guardian-access";
	}

	public static final String GET_PROVIDER_IMAGE(String imageId) {
		return SERVICE_SHARED_BL + "/v1/staff/image/" + imageId;
	}

	public static final String GET_PATIENT_AGEOUT_DL(String uriType, int practiceId) {
		if (uriType.equalsIgnoreCase("Valid"))
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-ageout";
		else
			return SERVICE_ORG_DL + "/v1/practicesssss/" + practiceId + "/patient-ageout";
	}

	public static final String GET_PATIENT_AGEOUT_DL(String practiceId) {
		return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-ageout";
	}

	public static final String UPDATE_SOLUTIONS_FOR_PROVIDER_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/solutions";
	}

	public static final String GET_LOCATION_BY_PRACTICE_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/locations";
	}

	public static final String GET_LOCATION_BY_PRACTICE_DL() {
		return SHARED_DL + "/v1/practices/null/locations";
	}

	public static final String PROVIDER_DETAILS_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/providers/null";
	}

	public static final String UPDATE_SOLUTIONS_FOR_PROVIDER_BL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/solutions";
	}

	public static final String UPDATE_LOCATION_FOR_PROVIDER_BL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/locations";
	}

	public static final String GET_LOCATIONS_BY_PRACTICE_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/locations";
	}

	public static final String GET_LOCATIONS_BY_PRACTICE_BL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/locations";
	}

	public static final String GET_PATIENT_ACCESS_BL(String uriType, int practiceId) {
		if (uriType.equalsIgnoreCase("Valid"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/patient-access";
		else
			return SERVICE_ORG_BL + "/v1/practicesssss/" + practiceId + "/patient-access";
	}

	public static final String GET_PATIENT_ACCESS_BL(String practiceId) {
		return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/patient-access";
	}

	public static final String CREATE_UPDATE_PATIENT_ACCESS(String serviceLayer, String uriType, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL")) {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/patient-access";
			else
				return SERVICE_ORG_BL + "/v1/practicesss/" + practiceId + "/patient-access";
		} else {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-access";
			else
				return SERVICE_ORG_DL + "/v1/practicesss/" + practiceId + "/patient-access";
		}
	}

	public static final String CREATE_UPDATE_PATIENT_ACCESS_WITH_INVLAID_DATATYPE(String serviceLayer,
																				  String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/practices/" + practiceId + "/patient-access";
		else
			return SERVICE_ORG_DL + "/v1/practices/" + practiceId + "/patient-access";
	}

	public static final String ASSOCIATE_PROVIDERS_TO_LOCATION_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/locations/null/practices/null/providers";
	}

	public static final String PROVIDERS_FOR_LOCATION_DL(int locationId, int practiceId) {
		return SHARED_DL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers";
	}

	public static final String UPDATE_LOCATION_FOR_PROVIDER__NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/providers/null/practices/null/locations";
	}

	public static final String FETCH_SOLUTIONS_FOR_PROVIDERS_BL_NULL_VALUES = SERVICE_SHARED_BL
			+ "/v1/resources/null/practices/null/solutions";

	public static final String GET_SOLUTIONS_FOR_PROVIDER_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/solutions";
	}

	public static final String GET_SOLUTIONS_FOR_PROVIDERS_DL_NULL_VALUES = SHARED_DL
			+ "/v1/providers/null/practices/null/solutions";

	public static final String GET_PRACTICE_LOCATION_BL(int practiceId, int locationId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/locations/" + locationId;
	}

	public static String GET_PRACTICE_LOCATION_BL_NULL_VALUES() {
		return SERVICE_SHARED_BL + "/v1/practices/null/locations/null";
	}

	public static final String UPDATE_SOLUTIONS_FOR_Location_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/solutions";
	}

	public static final String GET_EXTERNAL_INTERNAL_MAP_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/map";
	}

	public static final String GET_EXTERNAL_INTERNAL_MAP_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/practices/null/map";
	}

	public static final String UPDATE_SOLUTIONS_FOR_PROVIDER_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/providers/null/practices/null/solutions";
	}

	public static final String GET_PRACTICE_LOCATION_DL(int practiceId, int locationId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/locations/" + locationId;
	}

	public static String GET_PRACTICE_LOCATION_DL_NULL_VALUES() {
		return SHARED_DL + "/v1/practices/null/locations/null";
	}

	public static final String GET_ORG_SOLUTIONS_NULL_VALUES_DL() {
		return SERVICE_ORG_DL + "/v1/orgs/null/solutions";
	}

	public static final String CREATE_APPOINTMENT_TYPE_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/appointment-types";
	}

	public static final String GET_APPOINTMENT_TYPES_FOR_RESOURCE_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/resources/null/practices/null/appointment-types";
	}

	public static final String GET_PRACTICES_PROVIDERS(int practiceId, int providerId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/providers/" + providerId;
	}

	public static final String GET_PRACTICES_PROVIDERS_NULL_VALUES = SHARED_DL + "/v1/practices/null/providers/null";

	public static String GET_PROVIDER_BY_CLIENTID_DL() {
		return SHARED_DL + "/v1/providers/";
	}

	public static String GET_CONNECTED_PRACTICE_PROVIDER_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/providers";
	}

	public static final String GET_CONNECTED_PRACTICES_PROVIDERS__BL_NULL_VALUES = SERVICE_SHARED_BL
			+ "/v1/practices/null/providers?name=null";

	public static final String PROVIDER_FOR_CLIENT_BL(String clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/providers";
	}

	public static final String UPDATE_PROVIDER_FOR_PRACTICE_BL(int providerId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices";
	}

	public static final String UPDATE_SOLUTION_FOR_PROVIDER_BL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/solutions";
	}

	public static final String UPDATE_RESOURCES_FOR_PROVIDER_BL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String UPDATE_RESOURCES_FOR_PROVIDER_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/providers/null/practices/null/resources";
	}

	public static final String PROVIDER_FOR_CLIENT_DL(String clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/providers";
	}

	public static final String GET_LOCATION_FOR_PROVIDER_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/locations";
	}

	public static final String GET_LOCATION_FOR_PROVIDER_DL_NULL_VALUES() {
		return SHARED_DL + "/v1/providers/null/practices/null/locations";
	}

	public static final String UPDATE_RESOURCES_FOR_PRACTICE_DL(int resourceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices";
	}

	public static final String UPDATE_RESOURCES_FOR_PRACTICE_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/resources/null/practices";
	}

	public static final String SEND_USER_ENROLLMENT_CONSUMER(String serviceLayer, String uriType, int useId) {
		if (serviceLayer.equalsIgnoreCase("BL")) {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_USER_BL + "/v1/users/enrollments/" + useId;
			else
				return SERVICE_USER_BL + "/v1/users/enrollmentsss/" + useId;
		} else {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_USER_DL + "/v1/users/enrollments/" + useId;
			else
				return SERVICE_USER_DL + "/v1/users/enrollmentsss/" + useId;
		}
	}

	public static final String SEND_USER_ENROLLMENT_CONSUMER_WITH_INVLAID_DATATYPE(String serviceLayer, String useId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_USER_BL + "/v1/users/enrollments/" + useId;
		else
			return SERVICE_USER_DL + "/v1/users/enrollments/" + useId;
	}

	public static final String GET_ENROLLMENT_USER(String uriType, String enrollmentId) {
		if (uriType.equalsIgnoreCase("Valid"))
			return SERVICE_USER_DL + "/v1/users/enrollments/" + enrollmentId;
		else
			return SERVICE_USER_DL + "/v1/users/enrollmentsss/" + enrollmentId;

	}

	public static final String UPDATE_SOLUTIONS_FOR_PROVIDERS_USING_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/providers/null/practices/null/solutions";
	}

	public static final String GET_PRACTICES_PROVIDERS_BL(int practiceId, int providerId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/providers/" + providerId;
	}

	public static final String GET_PRACTICES_PROVIDERS_NULL_VALUES_BL = SERVICE_SHARED_BL
			+ "/v1/practices/null/providers/null";

	public static final String GET_LOCATION_FOR_PROVIDER_BL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/locations";
	}

	public static final String PROVIDERS_FOR_LOCATION_BL(int locationId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/locations/" + locationId + "/practices/" + practiceId + "/providers";
	}

	public static final String GET_LOCATION_FOR_PROVIDER_BL_NULL_VALUES() {
		return SERVICE_SHARED_BL + "/v1/providers/null/practices/null/locations";
	}

	public static final String UPDATE_EXPIRED_STATUS(String serviceLayer, String uriType) {
		if (serviceLayer.equalsIgnoreCase("BL")) {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_USER_BL + "/v1/users/enrollments/status";
			else
				return SERVICE_USER_BL + "/v1/users/enrollmentsss/status";
		} else {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_USER_DL + "/v1/users/enrollments/status";
			else
				return SERVICE_USER_DL + "/v1/users/enrollmentsss/status";
		}
	}

	public static final String SEARCH_RESOURCE_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources-location";
	}

	public static final String SEARCH_RESOURCE_NULL_BL = SERVICE_SHARED_BL + "/v1/practices/null/resources-location";

	public static final String UPDATE_RESOURCE_DETAILS_DL(int resourceId) {
		return SHARED_DL + "/v1/resources/" + resourceId;
	}

	public static final String UPDATE_RESOURCE_DETAILS_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/resources/null";
	}

	public static String SEARCH_LOCATION_FOR_PROVIDER_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/locations-provider";
	}

	public static String SEARCH_LOCATION_FOR_PROVIDER_DL() {
		return SHARED_DL + "/v1/providers/null/practices/null/locations-provider";
	}

	public static String GET_PROVIDER_BY_CLIENT_ID_BL() {
		return SERVICE_SHARED_BL + "/v1/providers/";
	}

	public static String SEARCH_LOCATION_FOR_PROVIDER_BL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/locations-provider";
	}

	public static String SEARCH_LOCATION_FOR_PROVIDER_BL() {
		return SERVICE_SHARED_BL + "/v1/providers/null/practices/null/locations-provider";
	}

	public static final String UPDATE_RESOURCES_FOR_PROVIDER_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static String SEARCH_RESOURCES_FOR_PROVIDER_DL(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources-provider";
	}

	public static String SEARCH_RESOURCES_FOR_PROVIDER_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/providers/null/practices/null/resources-provider";
	}

	public static final String UPDATE_LOCATION_FOR_RESOURCE_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/locations";
	}

	public static final String UPDATE_LOCATION_FOR_RESOURCES_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/resources/null/practices/null/locations";
	}

	public static final String PRACTICES_ASSOCIATED_TO_RESOURCE_DL(int resourceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices";
	}

	public static final String PRACTICES_ASSOCIATED_TO_RESOURCE_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/resources/null/practices";
	}

	public static final String UPDATE_PASSWORD_DURING_ENROLLMENT(String uriType, String enrollmentId) {
		if (uriType.equalsIgnoreCase("valid"))
			return SERVICE_USER_BL + "/v1/users/enrollments/credentials/" + enrollmentId;
		else
			return SERVICE_USER_BL + "/v1/users/enrollments/credentialsssss/" + enrollmentId;
	}

	public static final String SEARCH_RESOURCES_DL(int clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/resources";
	}

	public static final String SEARCH_RESOURCES_NULL_DL = SHARED_DL + "/v1/clients/null/resources";

	public static final String ADD_REMOVE_RESOURCE_FOR_PROVIDER(int providerId, int practiceId) {
		return SHARED_DL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources";
	}

	public static final String ADD_REMOVE_RESOURCE_FOR_PROVIDER_NULL = SHARED_DL
			+ "/v1/providers/null/practices/null/resources";

	public static String SEARCH_RESOURCES_FOR_PROVIDER_BL(int providerId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/providers/" + providerId + "/practices/" + practiceId + "/resources-provider";
	}

	public static String SEARCH_RESOURCES_FOR_PROVIDER_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/providers/null/practices/null/resources-provider";
	}

	public static final String UPDATE_RESOURCES_FOR_PRACTICE_BL(int resourceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practices";
	}

	public static final String UPDATE_RESOURCES_FOR_PRACTICE_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/resources/null/practices";

	}

	public static final String GET_ORG_PROVIDER_FOR_LOCATION_BL(int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/providers-location";
	}

	public static final String GET_ORG_PROVIDER_FOR_LOCATION_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/providers-location";
	}

	public static final String GET_USER_INFO(String serviceLayer, int userId) {
		if (serviceLayer.equalsIgnoreCase("DL"))
			return SERVICE_USER_DL + "/v1/users/" + userId;
		else
			return SERVICE_USER_BL + "/v1/users/" + userId;
	}

	public static final String SAVE_PRACTICE_RESOURCES_BL(int practiceId, int resourceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String GET_PRACTICE_RESOURCES_BL(int practiceId, int resourceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String SAVE_PRACTICE_RESOURCES_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/resources/null";
	}

	public static final String UPDATE_LOCATION_FOR_RESOURCE_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/locations";
	}

	public static final String UPDATE_LOCATION_FOR_RESOURCES_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/resources/null/practices/null/locations";
	}

	public static final String GET_DISPLAY_DETAILS_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String GET_DISPLAY_DETAILS_BL_NULL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/resources/null";
	}

	public static final String GET_RESOURCE_DETAILS_DL_NULL = SHARED_DL + "/v1/resources/null";

	public static final String DELETE_RESOURCE_IMAGE_BL(int resourceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId + "/images";
	}

	public static final String UPDATE_RESOURCE_DETAILS_BL(int resourceId) {
		return SERVICE_SHARED_BL + "/v1/resources/" + resourceId;
	}

	public static final String UPDATE_RESOURCE_DETAILS_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/resources/null";
	}

	public static final String GET_RESOURCE_IMAGE_BL(String imageUrl) {
		return SERVICE_SHARED_BL + "/v1/resources/image/" + imageUrl;
	}

	public static final String GET_RESOURCE_IMAGE_BL_NULL_VALUES() {
		return SERVICE_SHARED_BL + "/v1/resources/image/null";
	}

	public static final String RESOURCE_FOR_CLIENT_BL(String clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/resources";
	}

	public static final String SEARCH_LOCATION_FOR_RESOURCE_BL(int resourceId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/resources/" + resourceId + "/locations-resource";
	}

	public static final String SEARCH_LOCATION_FOR_RESOURCE_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/resources/null/locations-resource";
	}

	public static final String SEARCH_PROVIDERS_LOCATION_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/providers-location";
	}

	public static final String SEARCH_PROVIDERS_LOCATION_NULL_DL = SHARED_DL + "/v1/practices/null/providers-location";

	public static final String SEARCH_PROVIDERS_DL(int clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/providers";
	}

	public static final String SEARCH_PROVIDERS_NULL_DL = SHARED_DL + "/v1/clients/null/providers";

	public static final String GET_RESOURCE_DISPLAY_DETAILS_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String GET_RESOURCE_DISPLAY_DETAILS_DL_NULL() {
		return SHARED_DL + "/v1/practices/null/resources/null";
	}

	public static final String SAVE_PRACTICE_RESOURCES_DL(int practiceId, int resourceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resources/" + resourceId;
	}

	public static final String SEARCH_RESOURCE_DL(int practiceId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/resources-location";
	}

	public static final String SEARCH_RESOURCE_NULL_DL = SHARED_DL + "/v1/practices/null/resources-location";

	public static final String CREATE_STAFF_DL(int clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/staff";
	}

	public static final String STAFF_FOR_CLIENT_DL(String clientId) {
		return SHARED_DL + "/v1/clients/" + clientId + "/staff";
	}

	public static final String PRACTICES_ASSOCIATED_TO_STAFF_DL(int staffId) {
		return SHARED_DL + "/v1/providers/" + staffId + "/practices";
	}

	public static final String SEND_ENROLLMENT_BL(String uriType) {
		if (uriType.equalsIgnoreCase("Valid"))
			return SERVICE_USER_BL + "/v1/users/enrollments";
		else
			return SERVICE_USER_BL + "/v1/usersssss/enrollments";
	}

	public static final String CREATE_STAFF_BL(int clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/staff";
	}

	public static final String PRACTICES_ASSOCIATED_TO_STAFF_BL(int staffId) {
		return SERVICE_SHARED_BL + "/v1/staff/" + staffId + "/practices";
	}

	public static final String PRACTICES_ASSOCIATED_TO_STAFF_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/staff/null/practices";
	}

	public static final String GET_STAFF_DL(int staffId) {
		return SHARED_DL + "/v1/staff/" + staffId;
	}

	public static final String GET_STAFF_DL_NULL_VALUE() {
		return SHARED_DL + "/v1/staff/null";
	}

	public static final String GET_STAFF_DETAILS_BL(int clientId, int staffId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/staff/" + staffId;
	}

	public static final String GET_STAFF_DETAILS_BL_NULL_VALUE() {
		return SERVICE_SHARED_BL + "/v1/clients/null/staff/null";
	}

	public static final String PRACTICES_ASSOCIATED_TO_STAFF_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/staff/null/practices";
	}

	public static final String GET_COMMON_TEMPLATE_LIST_DL(String validURI) {
		if (validURI.equalsIgnoreCase("Valid"))
			return SERVICE_COMMONS_DL + "/v1/comm-templates";
		else
			return SERVICE_COMMONS_DL + "/v1/comm-templatesss";
	}

	public static final String GET_COMMON_TEMPLATE_LIST_BL(String validURI) {
		if (validURI.equalsIgnoreCase("Valid"))
			return SERVICE_COMMONS_BL + "/v1/comm-templates";
		else
			return SERVICE_COMMONS_BL + "/v1/comm-templatesss";
	}

	public static final String UPDATE_RESOURCES_FOR_PROVIDER_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/providers/null/practices/null/resources";
	}

	public static final String GET_BRAND_IMAGE_URL_BL(Object imageId) {
		return SERVICE_SHARED_BL + "/v1/brands/image/" + imageId;
	}

	public static final String GET_BRAND_IMAGE_URL_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/brands/image/null";
	}

	public static final String STAFF_FOR_CLIENT_BL(String clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/staff";
	}

	public static final String CREATE_TEMPLATE_VERSION(String serviceLayer, String uriType, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL")) {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_BL + "/v1/orgs/" + practiceId + "/template-versions";
			else
				return SERVICE_ORG_BL + "/v1/orgsss/" + practiceId + "/template-versions";
		} else {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_DL + "/v1/orgs/" + practiceId + "/template-versions";
			else
				return SERVICE_ORG_DL + "/v1/orgsss/" + practiceId + "/template-versions";
		}
	}

	public static final String CREATE_TEMPLATE_VERSION_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgs/" + practiceId + "/template-versions";
		else
			return SERVICE_ORG_DL + "/v1/orgs/" + practiceId + "/template-versions";
	}

	public static final String ADD_ROLE_LOCATION_BL(int userId, int orgId) {
		return SERVICE_USER_BL + "/v1/users/" + userId + "/entities/" + orgId;
	}

	public static final String ADD_ROLE_LOCATION_DL(int userId, int orgId) {
		return SERVICE_USER_DL + "/v1/users/" + userId + "/entities/" + orgId;
	}

	public static final String APPOINTMENT_TYPES_ASSOCIATED_TO_RESOURCE_DL(int resourceId, int practiceId) {
		return SHARED_DL + "/v1/resources/" + resourceId + "/practices/" + practiceId + "/appointment-types";
	}

	public static final String APPOINTMENT_TYPES_ASSOCIATED_TO_RESOURCE_NULLVALUES_DL() {
		return SHARED_DL + "/v1/resources/null/practices/null/appointment-types";
	}

	public static final String UPDATE_TEMPLATE_VERSION(String serviceLayer, String uriType, int practiceId,
													   Long templateVersionId) {
		if (serviceLayer.equalsIgnoreCase("BL")) {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_BL + "/v1/orgs/" + practiceId + "/template-versions/" + templateVersionId;
			else
				return SERVICE_ORG_BL + "/v1/orgsss/" + practiceId + "/template-versions/" + templateVersionId;
		} else {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_DL + "/v1/orgs/" + practiceId + "/template-versions/" + templateVersionId;
			else
				return SERVICE_ORG_DL + "/v1/orgsss/" + practiceId + "/template-versions/" + templateVersionId;
		}
	}

	public static final String UPDATE_TEMPLATE_VERSION_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId,
																			 String templateVersionId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgs/" + practiceId + "/template-versions/" + templateVersionId;
		else
			return SERVICE_ORG_DL + "/v1/orgs/" + practiceId + "/template-versions/" + templateVersionId;
	}

	public static final String SAVE_APP_TYPES_INFO_FOR_PRACTICE_BL(int appointmentTypeId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/appointment-types/" + appointmentTypeId + "/practices/" + practiceId;
	}

	public static final String SAVE_APP_TYPES_INFO_FOR_PRACTICE_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/appointment-types/null/practices/null";
	}

	public static final String APP_TYPES_FOR_CLIENT_BL(String clientId) {
		return SERVICE_SHARED_BL + "/v1/clients/" + clientId + "/appointment-types";
	}

	public static final String SOLUTIONS_FOR_APPOINTMENT_TYPE_BL(int appointmentTypeId, int practiceId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId
				+ "/solutions";
	}

	public static final String UPDATE_SOLUTIONS_FOR_APPOINTMENT_TYPE_USING_NULL_VALUE_BL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/appointment-types/null/solutions";
	}

	public static final String GET_SOLUTIONS_FOR_APPOINTMENT_TYPE_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/appointment-types/null/solutions";
	}

	public static final String GET_TEMPLATE_ATTRIBUTE(String serviceLayer, String uriType, int practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL")) {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_BL + "/v1/orgs/" + practiceId + "/template-attributes";
			else
				return SERVICE_ORG_BL + "/v1/orgsss/" + practiceId + "/template-attributes";
		} else {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_ORG_DL + "/v1/orgs/" + practiceId + "/template-attributes";
			else
				return SERVICE_ORG_DL + "/v1/orgsss/" + practiceId + "/template-attributes";
		}
	}

	public static final String GET_TEMPLATE_ATTRIBUTE_WITH_INVLAID_DATATYPE(String serviceLayer, String practiceId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_ORG_BL + "/v1/orgs/" + practiceId + "/template-attributes";
		else
			return SERVICE_ORG_DL + "/v1/orgs/" + practiceId + "/template-attributes";
	}

	public static final String ENABLE_DISABLE_APP_TYPES_BL(int practiceId, int appointmentTypeId) {
		return SERVICE_SHARED_BL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId;
	}

	public static final String ENABLE_DISABLE_APP_TYPES_NULL_VALUES_BL() {
		return SERVICE_SHARED_BL + "/v1/practices/null/appointment-types/null";
	}

	public static final String UPDATE_USER_STATUS_DL = SERVICE_USER_DL + "/v1/users/status";
	
	public static final String SOLUTIONS_FOR_APPOINTMENT_TYPE_DL(int appointmentTypeId, int practiceId){ 
		return  SHARED_DL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId + "/solutions";
     }
	
	public static final String UPDATE_SOLUTIONS_FOR_APPOINTMENT_TYPE_USING_NULL_VALUE_DL() {
		return SHARED_DL + "/v1/practices/null/appointment-types/null/solutions";
	}

	public static final String GET_TEMPLATE_VARIABLE_LISTS(String serviceLayer, String uriType, int templateId) {
		if (serviceLayer.equalsIgnoreCase("BL")) {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_COMMONS_BL + "/v1/comm-templates/" + templateId + "/variables";
			else
				return SERVICE_COMMONS_BL + "/v1/comm-templatesssss/" + templateId + "/variables";
		} else {
			if (uriType.equalsIgnoreCase("Valid"))
				return SERVICE_COMMONS_DL + "/v1/comm-templates/" + templateId + "/variables";
			else
				return SERVICE_COMMONS_DL + "/v1/comm-templatesssss/" + templateId + "/variables";
		}
	}

	public static final String GET_TEMPLATE_VARIABLE_LISTS_WITH_INVLAID_DATATYPE(String serviceLayer, String templateId) {
		if (serviceLayer.equalsIgnoreCase("BL"))
			return SERVICE_COMMONS_BL + "/v1/comm-templates/" + templateId + "/variables";
		else
			return SERVICE_COMMONS_DL + "/v1/comm-templates/" + templateId + "/variables";
	}
	
	public static final String ENABLE_DISABLE_APP_TYPES_DL(int practiceId, int appointmentTypeId) {
		return SHARED_DL + "/v1/practices/" + practiceId + "/appointment-types/" + appointmentTypeId;
	}

	public static final String ENABLE_DISABLE_APP_TYPES_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/practices/null/appointment-types/null";
	}
	
	public static final String SAVE_APP_TYPES_INFO_FOR_PRACTICE_DL(int appointmentTypeId, int practiceId) {
		return SHARED_DL + "/v1/appointment-types/" + appointmentTypeId + "/practices/" + practiceId;
	}
	
	public static final String SAVE_APP_TYPES_INFO_FOR_PRACTICE_NULL_VALUES_DL() {
		return SHARED_DL + "/v1/appointment-types/null/practices/null";
	}
}
