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
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.pojos.OrgWebsites;
import com.pxp.cc.pojos.SavePracticeWebsite;
import com.pxp.cc.pojos.UpdateTou;
import com.pxp.cc.pojos.UpdateProductStatus;
import com.pxp.cc.pojos.PracticeSocials;
import com.pxp.cc.pojos.*;
import io.restassured.response.Response;

public class OrgInfo extends BaseRest {

	public Response getOrgNodesDetails(String orgId) {

		String url = ApiPath.GET_ORG_DETAILS + orgId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createOrg(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String timezoneId, int ehrIdentifier, int pmIdentifier) throws IOException {

		Map<String, Object> createNewOrgPayload = postCreateOrg(active, displayName, name, orgType, parentId,
				parentType, timezoneId, ehrIdentifier, pmIdentifier);

		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.ORG_BL).then().extract()
				.response();
	}

	public Response createOrg_DataLayer(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String timezoneId, int ehrIdentifier, int pmIdentifier) throws IOException {
		Map<String, Object> createNewOrgPayload = postCreateOrg(active, displayName, name, orgType, parentId,
				parentType, timezoneId, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.ORG_DL).then().extract()
				.response();
	}

	public Response getOrgId_DataLayer(int orgId) throws IOException {
		String url = ApiPath.GET_ORG_DETAILS_DL + orgId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createSystem(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewSystemPayload = postCreateSystem(active, displayName, name, orgType, parentId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewSystemPayload).when().post(ApiPath.POST_SYSTEM_PRACTICE).then()
				.extract().response();
	}

	public Response createAgnosticPractice(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewSystemPayload = postCreateSystem(active, displayName, name, orgType, parentId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewSystemPayload).when().post(ApiPath.POST_SYSTEM_PRACTICE).then()
				.extract().response();
	}

	public Response updateSystem(String active, String displayName, int orgId, String sapNumber, String timezoneId)
			throws IOException {

		Map<String, Object> updateSystemPayload = putUpdateSystem(active, displayName, orgId, sapNumber, timezoneId);

		return given().spec(requestSpec).body(updateSystemPayload).when().put(ApiPath.PUT_ORG_CONTROLLER).then()
				.extract().response();
	}

	public Response getOrgId(int orgId) throws IOException {
		String url = ApiPath.ORG_DETAILS_BL(orgId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createEnterprise(String active, String displayName, String name, String orgType, int orgId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewEnterprisePayload = postCreateEnterprise(active, displayName, name, orgType, orgId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewEnterprisePayload).when().post(ApiPath.POST_ENTERPRISE_PRACTICE)
				.then().extract().response();
	}

	public Response updateEnterprise(String active, String displayName, int orgId, String sapNumber, String timezoneId)
			throws IOException {

		Map<String, Object> updateEnterprisePayload = putUpdateEnterprise(active, displayName, orgId, sapNumber,
				timezoneId);

		return given().spec(requestSpec).body(updateEnterprisePayload).when().put(ApiPath.PUT_ORG_CONTROLLER).then()
				.extract().response();
	}

	public Response createPractice(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewPracticePayload = postCreatePractice(active, displayName, name, orgType, parentId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewPracticePayload).when().post(ApiPath.POST_ENTERPRISE_PRACTICE)
				.then().extract().response();
	}

	public Response createAgnosticPracticeNode(String active, String displayName, String name, String orgType,
			int parentId, String parentType, String sapNumber, String timezoneId, List<Integer> products)
			throws IOException {
		Map<String, Object> createNewPracticeNodePayload = postCreatePracticeNode(active, displayName, name, orgType,
				parentId, parentType, sapNumber, timezoneId, products);
		return given().spec(requestSpec).body(createNewPracticeNodePayload).when().post(ApiPath.POST_SYSTEM_PRACTICE)
				.then().extract().response();
	}

	public Response createPracticeWithSolutions(String active, String displayName, String name, String orgType,
			int parentId, String parentType, String sapNumber, String timezoneId, List<Integer> productList)
			throws IOException {
		Map<String, Object> createNewPracticePayload = postCreatePracticeNode(active, displayName, name, orgType,
				parentId, parentType, sapNumber, timezoneId, productList);
		return given().spec(requestSpec).body(createNewPracticePayload).when().post(ApiPath.POST_ENTERPRISE_PRACTICE)
				.then().extract().response();
	}

	public Response updatePractice(String active, String displayName, int orgId, String sapNumber, String timezoneId)
			throws IOException {

		Map<String, Object> updatePracticePayload = putUpdatePractice(active, displayName, orgId, sapNumber,
				timezoneId);

		return given().spec(requestSpec).body(updatePracticePayload).when().put(ApiPath.PUT_ORG_CONTROLLER).then()
				.extract().response();
	}

	public Response updatePracticeWithSolutions(String active, String displayName, int orgId, String sapNumber,
			List<UpdateProductStatus> solutionList, String timezoneId) throws IOException {
		Map<String, Object> updatePracticePayload = putUpdatePracticeWithSolutions(active, displayName, orgId,
				sapNumber, solutionList, timezoneId);
		return given().spec(requestSpec).body(updatePracticePayload).when().put(ApiPath.PUT_ORG_CONTROLLER).then()
				.extract().response();
	}

	public Response patchOrgStatus(String orgId, String orgStatus) throws IOException {
		String url = ApiPath.GET_ORG_DETAILS + orgId + ApiPath.PATCH_ORG_STATUS + orgStatus;
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}

	public Response createAgnosticPracticeNode(String name, String displayName, String sapNumber, String active,
			String timezoneId, List<Map<String, Object>> products, String orgType, int parentId, String parentType)
			throws IOException {

		Map<String, Object> createNewPracticeNodePayload = postCreatePracticeNode(name, displayName, sapNumber, active,
				timezoneId, products, orgType, parentId, parentType);

		return given().spec(requestSpec).body(createNewPracticeNodePayload).when().post(ApiPath.ORG_BL).then().extract()
				.response();
	}

	public Response createEnterpriseNodeForAgnostic(String active, String displayName, String name, String orgType,
			int orgId, String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewEnterprisePayload = postCreateEnterprise(active, displayName, name, orgType, orgId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewEnterprisePayload).when().post(ApiPath.POST_SYSTEM_PRACTICE)
				.then().extract().response();
	}

	public static Map<String, Object> postCreateSystem(String active, String displayName, String name, String orgType,
			int parentId, String parentType, String sapNumber, String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("name", name);
		systemMap.put("orgType", orgType);
		systemMap.put("parentId", parentId);
		systemMap.put("parentType", parentType);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}

	public static Map<String, Object> postCreateOrg(String active, String displayName, String name, String orgType,
			int parentId, String parentType, String timezoneId, int ehrIdentifier, int pmIdentifier) {
		Map<String, Object> orgMap = new HashMap<String, Object>();
		orgMap.put("name", name);
		orgMap.put("orgType", orgType);
		orgMap.put("displayName", displayName);
		orgMap.put("active", active);
		orgMap.put("timezoneId", timezoneId);
		orgMap.put("parentId", parentId);
		orgMap.put("parentType", parentType);
		orgMap.put("ehrIdentifier", ehrIdentifier);
		orgMap.put("pmIdentifier", pmIdentifier);
		return orgMap;
	}

	public static Map<String, Object> putUpdateSystem(String active, String displayName, int orgId, String sapNumber,
			String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("id", orgId);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}

	public static Map<String, Object> postCreateEnterprise(String active, String displayName, String name,
			String orgType, int parentId, String parentType, String sapNumber, String timezoneId) {
		Map<String, Object> enterpriseMap = new HashMap<String, Object>();
		enterpriseMap.put("active", active);
		enterpriseMap.put("displayName", displayName);
		enterpriseMap.put("name", name);
		enterpriseMap.put("orgType", orgType);
		enterpriseMap.put("parentId", parentId);
		enterpriseMap.put("parentType", parentType);
		enterpriseMap.put("sapNumber", sapNumber);
		enterpriseMap.put("timezoneId", timezoneId);
		return enterpriseMap;
	}

	public static Map<String, Object> putUpdateEnterprise(String active, String displayName, int orgId,
			String sapNumber, String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("id", orgId);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}

	public static Map<String, Object> postCreatePractice(String active, String displayName, String name, String orgType,
			int parentId, String parentType, String sapNumber, String timezoneId) {
		Map<String, Object> practiceMap = new HashMap<String, Object>();
		practiceMap.put("active", active);
		practiceMap.put("displayName", displayName);
		practiceMap.put("name", name);
		practiceMap.put("orgType", orgType);
		practiceMap.put("parentId", parentId);
		practiceMap.put("parentType", parentType);
		practiceMap.put("sapNumber", sapNumber);
		practiceMap.put("timezoneId", timezoneId);
		return practiceMap;
	}

	public static Map<String, Object> putUpdatePractice(String active, String displayName, int orgId, String sapNumber,
			String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("id", orgId);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}

	public static Map<String, Object> putUpdatePracticeWithSolutions(String active, String displayName, int orgId,
			String sapNumber, List<UpdateProductStatus> solutionList, String timezoneId) {
		Map<String, Object> practiceMap = new HashMap<String, Object>();
		practiceMap.put("active", active);
		practiceMap.put("displayName", displayName);
		practiceMap.put("id", orgId);
		practiceMap.put("sapNumber", sapNumber);
		practiceMap.put("solutionStatusList", solutionList);
		practiceMap.put("timezoneId", timezoneId);
		return practiceMap;
	}

	public Map<String, Object> solutionDetails(String prodID, String status, String statusDate, String statusNotes) {
		Map<String, Object> solutionMap = new HashMap<String, Object>();
		solutionMap.put("productId", prodID);
		solutionMap.put("status", status);
		solutionMap.put("statusDate", statusDate);
		solutionMap.put("statusNotes", statusNotes);
		return solutionMap;
	}

	public static Map<String, Object> postCreatePracticeNode(String name, String displayName, String sapNumber,
			String active, String timezoneId, List<Map<String, Object>> products, String orgType, int parentId,
			String parentType) {
		Map<String, Object> practiceMap = new HashMap<String, Object>();
		practiceMap.put("name", name);
		practiceMap.put("displayName", displayName);
		practiceMap.put("sapNumber", sapNumber);
		practiceMap.put("active", active);
		practiceMap.put("timezoneId", timezoneId);
		practiceMap.put("solutions", products);
		practiceMap.put("orgType", orgType);
		practiceMap.put("parentId", parentId);
		practiceMap.put("parentType", parentType);
		return practiceMap;
	}

	public static Map<String, Object> postCreatePracticeNode(String active, String displayName, String name,
			String orgType, int parentId, String parentType, String sapNumber, String timezoneId,
			List<Integer> products) {
		Map<String, Object> practiceMap = new HashMap<String, Object>();
		practiceMap.put("active", active);
		practiceMap.put("displayName", displayName);
		practiceMap.put("name", name);
		practiceMap.put("orgType", orgType);
		practiceMap.put("parentId", parentId);
		practiceMap.put("parentType", parentType);
		practiceMap.put("sapNumber", sapNumber);
		practiceMap.put("timezoneId", timezoneId);
		practiceMap.put("products", products);
		return practiceMap;
	}

	public Response getOrgIdUsingInvalidUrl_DataLayer(int orgId) throws IOException {
		String url = ApiPath.INVALID_PATH_GET_ORGID_DL + orgId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getOrganizationListByClientId_DataLayer(int clientId, String orgType) throws IOException {
		String url = ApiPath.GET_ORGANIZATION_LIST_DL(clientId);
		return given().spec(requestSpec).queryParam("type", orgType).when().get(url).then().extract().response();

	}

	public Response getOrganizationListByClientIdUsingInvalidUrl_DataLayer(int clientId, String orgType)
			throws IOException {
		String url = ApiPath.INVALID_PATH_GET_ORGANIZATION_LIST_DL(clientId);
		return given().spec(requestSpec).queryParam("type", orgType).when().get(url).then().extract().response();

	}

	public static Map<String, Object> postCreatePracticeNodeDataLayer(String name, String displayName, String sapNumber,
			String active, String timezoneId, String orgType, int parentId, String parentType, String ehrIdentifier,
			String pmIdentifier) {
		Map<String, Object> practiceMap = new HashMap<String, Object>();
		practiceMap.put("name", name);
		practiceMap.put("displayName", displayName);
		practiceMap.put("sapNumber", sapNumber);
		practiceMap.put("active", active);
		practiceMap.put("timezoneId", timezoneId);
		practiceMap.put("orgType", orgType);
		practiceMap.put("parentId", parentId);
		practiceMap.put("parentType", parentType);
		practiceMap.put("ehrIdentifier", ehrIdentifier);
		practiceMap.put("pmIdentifier", pmIdentifier);
		return practiceMap;
	}

	public Response updateOrgsSolutionsDetails_DataLayer(int solutionId, String status, int orgId) throws IOException {
		String url = ApiPath.ORGS_SOLUTION_DL + orgId + "/solutions";
		List<Map<String, Object>> updateOrgsSolutionPayload = putUpdateOrgsSolutionsDetails_DataLayer(solutionId,
				status);

		return given().spec(requestSpec).body(updateOrgsSolutionPayload).when().put(url).then().extract().response();
	}

	public static List<Map<String, Object>> putUpdateOrgsSolutionsDetails_DataLayer(int solutionId,
			String NewlyPurchased) {
		List<Map<String, Object>> solutionsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> solutionsMap = new HashMap<String, Object>();
		solutionsMap.put("solutionId", solutionId);
		solutionsMap.put("status", NewlyPurchased);
		solutionsList.add(solutionsMap);
		return solutionsList;
	}

	public Response getOrgSolutionsDetails_DataLayer(int orgId) {
		String url = ApiPath.ORGS_SOLUTION_DL + orgId + "/solutions";
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionOrgIdUsingInvalidUrl_DataLayer(int orgId) throws IOException {
		String url = ApiPath.INVALID_PATH_ORGS_SOLUTION_DL(orgId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public static Map<String, Object> updateOrgNodeMap(String name, String orgType, String displayName, String active,
			String timezoneId, int ehrIdentifier, int pmIdentifier) {
		Map<String, Object> updateOrgMap = new HashMap<String, Object>();
		updateOrgMap.put("name", name);
		updateOrgMap.put("orgType", orgType);
		updateOrgMap.put("displayName", displayName);
		updateOrgMap.put("active", active);
		updateOrgMap.put("timezoneId", timezoneId);
		updateOrgMap.put("ehrIdentifier", ehrIdentifier);
		updateOrgMap.put("pmIdentifier", pmIdentifier);
		return updateOrgMap;
	}

	public Response updateOrgNode_DataLayer(String name, String orgType, String displayName, String active,
			String timezoneId, Integer orgId, int ehrIdentifier, int pmIdentifier) throws IOException {
		String url = ApiPath.UPDATE_ORG(orgId);
		Map<String, Object> updateOrgNodePayload = updateOrgNodeMap(name, orgType, displayName, active, timezoneId,
				ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).pathParam("orgId", orgId).body(updateOrgNodePayload).when().put(url).then()
				.extract().response();
	}

	public Response updateOrgNodeUsingInvalidUrl_DataLayer(String name, String orgType, String displayName,
			String active, String timezoneId, Integer orgId, int ehrIdentifier, int pmIdentifier) throws IOException {
		String url = ApiPath.INVALID_PATH_UPDATE_ORG(orgId);
		Map<String, Object> updateOrgNodePayload = updateOrgNodeMap(name, orgType, displayName, active, timezoneId,
				ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).pathParam("orgId", orgId).body(updateOrgNodePayload).when().put(url).then()
				.extract().response();
	}

	public static Map<String, Object> createOrgNodeMap(String active, String displayName, String name, String orgType,
			Integer parentId, String parentType, String timezoneId, List<Map<String, Object>> solutionList) {
		Map<String, Object> orgMap = new HashMap<String, Object>();
		orgMap.put("active", active);
		orgMap.put("displayName", displayName);
		orgMap.put("name", name);
		orgMap.put("orgType", orgType);
		orgMap.put("parentId", parentId);
		orgMap.put("parentType", parentType);
		orgMap.put("timezoneId", timezoneId);
		orgMap.put("solutions", solutionList);
		return orgMap;
	}

	public Response createOrgNode(String active, String displayName, String name, String orgType, Integer parentId,
			String parentType, String timezoneId, List<Map<String, Object>> solutionList) throws Exception {

		Map<String, Object> createNewOrgPayload = createOrgNodeMap(active, displayName, name, orgType, parentId,
				parentType, timezoneId, solutionList);
		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.ORG_BL).then().extract()
				.response();
	}

	public Response createOrgNodeUsingInvalidUrl(String active, String displayName, String name, String orgType,
			Integer parentId, String parentType, String timezoneId, List<Map<String, Object>> solutionList)
			throws Exception {

		Map<String, Object> createNewOrgPayload = createOrgNodeMap(active, displayName, name, orgType, parentId,
				parentType, timezoneId, solutionList);
		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.INVALID_PATH_ORG_BL).then()
				.extract().response();
	}

	public static Map<String, Object> updateOrgNodeDetailsMap(String name, String orgType, String displayName,
			String sapNumber, String active, String timezoneId, List<Map<String, Object>> solutionList) {
		Map<String, Object> updateOrgNodeValueMap = new HashMap<String, Object>();
		updateOrgNodeValueMap.put("name", name);
		updateOrgNodeValueMap.put("orgType", orgType);
		updateOrgNodeValueMap.put("displayName", displayName);
		updateOrgNodeValueMap.put("sapNumber", sapNumber);
		updateOrgNodeValueMap.put("active", active);
		updateOrgNodeValueMap.put("timezoneId", timezoneId);
		updateOrgNodeValueMap.put("solutions", solutionList);
		return updateOrgNodeValueMap;
	}

	public Response updateOrgNode(String name, String orgType, String displayName, String sapNumber, String active,
			String timezoneId, List<Map<String, Object>> solutionList, Integer orgId) throws IOException {
		String url = ApiPath.UPDATE_ORG_BL(orgId);
		Map<String, Object> updateOrgNodePayload = updateOrgNodeDetailsMap(name, orgType, displayName, sapNumber,
				active, timezoneId, solutionList);
		return given().spec(requestSpec).pathParam("orgId", orgId).body(updateOrgNodePayload).when().put(url).then()
				.extract().response();
	}

	public Response updateOrgNodeUsingInvalidUrl(String name, String orgType, String displayName, String sapNumber,
			String active, String timezoneId, List<Map<String, Object>> solutionList, Integer orgId)
			throws IOException {
		String url = ApiPath.INVALID_PATH_UPDATE_ORG_BL(orgId);
		Map<String, Object> updateOrgNodePayload = updateOrgNodeDetailsMap(name, orgType, displayName, sapNumber,
				active, timezoneId, solutionList);
		return given().spec(requestSpec).pathParam("orgId", orgId).body(updateOrgNodePayload).when().put(url).then()
				.extract().response();
	}

	public Response getOrgHierarchyByOrgId_DataLayer(int orgId) throws IOException {
		String url = ApiPath.ORG_HIERARCHY_DL(orgId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getOrgHierarchyByOrgIdUsingInvalidUrl_DataLayer(int orgId) throws IOException {
		String url = ApiPath.INVALID_PATH_ORG_HIERARCHY_DL(orgId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCall_DataLayer(int orgId) throws IOException {
		String url = ApiPath.ORG_HIERARCHY_DL(orgId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response createOrgNode_DataLayer(String name, String orgType, String displayName, String active,
			String timezoneId, int parentId, String parentType, int ehrIdentifier, int pmIdentifier)
			throws IOException {
		Map<String, Object> createNewOrgPayload = postCreateOrgMap(name, orgType, displayName, active, timezoneId,
				parentId, parentType, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.ORG_DL).then().extract()
				.response();
	}

	public static Map<String, Object> postCreateOrgMap(String name, String orgType, String displayName, String active,
			String timezoneId, int parentId, String parentType, int ehrIdentifier, int pmIdentifier) {
		Map<String, Object> orgMap = new HashMap<String, Object>();
		orgMap.put("name", name);
		orgMap.put("orgType", orgType);
		orgMap.put("displayName", displayName);
		orgMap.put("active", active);
		orgMap.put("timezoneId", timezoneId);
		orgMap.put("parentId", parentId);
		orgMap.put("parentType", parentType);
		orgMap.put("ehrIdentifier", ehrIdentifier);
		orgMap.put("pmIdentifier", pmIdentifier);
		return orgMap;
	}

	public Response updateOrgsSolutions_DataLayer(List<OrgSolutions> productList, int orgId, int userId)
			throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_DL(orgId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).body(productList).when().put(url).then()
				.extract().response();
	}

	public Response updateOrgsSolutionsUsingInvalidUrl_DataLayer(List<OrgSolutions> productList, int orgId, int userId)
			throws IOException {
		String url = ApiPath.INVALID_PATH_UPDATE_ORG_SOLUTIONS_DL(orgId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).body(productList).when().put(url).then()
				.extract().response();
	}

	public Response updateOrgsSolutionsUsingInvalidMethod_DataLayer(List<OrgSolutions> productList, int orgId,
			int userId) throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_DL(orgId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).body(productList).when().post(url).then()
				.extract().response();
	}

	public Response createOrgNode_BL(String name, String orgType, String displayName, String active, String timezoneId,
			int parentId, String parentType, int ehrIdentifier, int pmIdentifier) throws IOException {

		Map<String, Object> createNewOrgPayload = postCreateOrgMap(name, orgType, displayName, active, timezoneId,
				parentId, parentType, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.ORG_BL).then().extract()
				.response();
	}

	public Response createOrgNodeUsingInvalidUrl_BL(String name, String orgType, String displayName, String active,
			String timezoneId, int parentId, String parentType, int ehrIdentifier, int pmIdentifier)
			throws IOException {

		Map<String, Object> createNewOrgPayload = postCreateOrgMap(name, orgType, displayName, active, timezoneId,
				parentId, parentType, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.INVALID_PATH_ORG_BL).then()
				.extract().response();
	}

	public Response updateOrgSolutions(List<Map<String, Object>> productList, int orgId) throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_BL(orgId);
		return given().spec(requestSpec).body(productList).when().put(url).then().extract().response();
	}

	public static Map<String, Object> updateOrgNodeDetailsMap(String name, String orgType, String displayName,
			String active, String timezoneId, int ehrIdentifier, int pmIdentifier) {
		Map<String, Object> updateOrgNodeValueMap = new HashMap<String, Object>();
		updateOrgNodeValueMap.put("name", name);
		updateOrgNodeValueMap.put("orgType", orgType);
		updateOrgNodeValueMap.put("displayName", displayName);
		updateOrgNodeValueMap.put("active", active);
		updateOrgNodeValueMap.put("timezoneId", timezoneId);
		updateOrgNodeValueMap.put("ehrIdentifier", ehrIdentifier);
		updateOrgNodeValueMap.put("pmIdentifier", pmIdentifier);
		return updateOrgNodeValueMap;
	}

	public Response updateOrgNode_BL(String name, String orgType, String displayName, String active, String timezoneId,
			int ehrIdentifier, int pmIdentifier, Integer orgId) throws IOException {
		String url = ApiPath.UPDATE_ORG_BL(orgId);
		Map<String, Object> updateOrgNodePayload = updateOrgNodeDetailsMap(name, orgType, displayName, active,
				timezoneId, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).pathParam("orgId", orgId).body(updateOrgNodePayload).when().put(url).then()
				.extract().response();
	}

	public Response updateOrgNodeUsingInvalidUrl_BL(String name, String orgType, String displayName, String sapNumber,
			String active, String timezoneId, int ehrIdentifier, int pmIdentifier, Integer orgId) throws IOException {
		String url = ApiPath.INVALID_PATH_UPDATE_ORG_BL(orgId);
		Map<String, Object> updateOrgNodePayload = updateOrgNodeDetailsMap(name, orgType, displayName, active,
				timezoneId, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).pathParam("orgId", orgId).body(updateOrgNodePayload).when().put(url).then()
				.extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdHasWebsites(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdHasNotWebsites(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdWithInvalidURI(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_URI(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdWithInvalidRequest(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithNullOrInvalidDataTypeOnPracticeId(String serviceLayer, String practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithEmptyOnPracticeId(String serviceLayer) {
		String url = ApiPath.PRACTICE_WEBSITES_WITHOUT_PRACTICEID(serviceLayer);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithNonExistingPracticeId_BL(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdHasWebsites_DL(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdHasNotWebsites_DL(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdWithInvalidURI_DL(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_URI(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithValidPracticeIdWithInvalidRequest_DL(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithNullOrInvalidDataTypeOnPracticeId_DL(String serviceLayer,
			String practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithEmptyOnPracticeId_DL(String serviceLayer) {
		String url = ApiPath.PRACTICE_WEBSITES_WITHOUT_PRACTICEID(serviceLayer);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeWebsitesWithNonExistingPracticeId(String serviceLayer, int practiceId) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response addPracticeWebsiteWithPracticeIdHasExternalLinks(String serviceLayer, int practiceId,
			List<OrgWebsites> orgWebsites) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).body(orgWebsites).when().post(url).then().extract().response();
	}

	public Response addPracticeWebsiteWithPracticeIdHasExternalLinks(String serviceLayer, String practiceId,
			List<OrgWebsites> orgWebsites) {
		String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).body(orgWebsites).when().post(url).then().extract().response();
	}

	public Response addPracticeWebsitesWithoutPracticeId(String serviceLayer, List<OrgWebsites> orgWebsites) {
		String url = ApiPath.PRACTICE_WEBSITES_WITHOUT_PRACTICEID(serviceLayer);
		return given().spec(requestSpec).body(orgWebsites).when().post(url).then().extract().response();
	}

	public Response addPracticeWebsitesWithInvalidURI(String serviceLayer, int practiceId,
			List<OrgWebsites> orgWebsites) {
		String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_URI(serviceLayer, practiceId);
		return given().spec(requestSpec).body(orgWebsites).when().post(url).then().extract().response();
	}

	public Response addPracticeWebsitesWithInvalidRequest(String serviceLayer, int practiceId,
			List<OrgWebsites> orgWebsites) {
		String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
		return given().spec(requestSpec).body(orgWebsites).when().delete(url).then().extract().response();
	}

	public Response updateDeletePracticeWebsitesResponse(String validURI, String serviceLayer, int practiceId,
			List<SavePracticeWebsite> SavePracticeWebsiteList) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
			return given().spec(requestSpec).body(SavePracticeWebsiteList).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).body(SavePracticeWebsiteList).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValidMethodType")) {
			String url = ApiPath.PRACTICE_WEBSITES(serviceLayer, practiceId);
			return given().spec(requestSpec).body(SavePracticeWebsiteList).when().delete(url).then().extract()
					.response();
		} else if (validURI.equalsIgnoreCase("NullValue")) {
			String url = ApiPath.NULL_PRACTICE_WEBSITES(serviceLayer);
			return given().spec(requestSpec).body(SavePracticeWebsiteList).when().post(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_WEBSITES_WITHOUT_PRACTICEID(serviceLayer);
			return given().spec(requestSpec).body(SavePracticeWebsiteList).when().post(url).then().extract().response();
		}
	}

	public Response updateDeletePracticeWebsitesInvaliDatatypeResponse(String serviceLayer, String practiceId,
			List<SavePracticeWebsite> SavePracticeWebsiteList) {
		String url = ApiPath.PRACTICE_WEBSITES_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).body(SavePracticeWebsiteList).when().post(url).then().extract().response();
	}

	public Response updateGetOrgPracticeWebsitesResponse(String validURI, String serviceLayer, int practiceId,
			Map<String, Object> websiteUrl) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.UPDATE_ORG_WEBSITES(serviceLayer, practiceId);
			return given().spec(requestSpec).body(websiteUrl).when().patch(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.UPDATE_ORG_WEBSITES_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).body(websiteUrl).when().patch(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValidMethodType")) {
			String url = ApiPath.UPDATE_ORG_WEBSITES(serviceLayer, practiceId);
			return given().spec(requestSpec).body(websiteUrl).when().delete(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("NullValue")) {
			String url = ApiPath.NULL_UPDATE_ORG_WEBSITES(serviceLayer);
			return given().spec(requestSpec).body(websiteUrl).when().patch(url).then().extract().response();
		} else {
			String url = ApiPath.UPDATE_ORG_WEBSITES_WITHOUT_PRACTICEID(serviceLayer);
			return given().spec(requestSpec).body(websiteUrl).when().patch(url).then().extract().response();
		}
	}

	public Response updateGetOrgPracticeWebsitesInvaliDatatypeResponse(String serviceLayer, String practiceId,
			Map<String, Object> websiteUrl) {
		String url = ApiPath.UPDATE_ORG_WEBSITES_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).body(websiteUrl).when().patch(url).then().extract().response();
	}

	public Response updateOrgsSolutionsDetails_BL(int solutionId, String status, int orgId) throws IOException {
		String url = ApiPath.ORG_BL + "/" + orgId + "/solutions";
		List<Map<String, Object>> updateOrgsSolutionPayload = putUpdateOrgsSolutionsDetails_DataLayer(solutionId,
				status);

		return given().spec(requestSpec).body(updateOrgsSolutionPayload).when().put(url).then().extract().response();
	}

	public Response addUpdatePracticeSocialsResponse(String validURI, String serviceLayer, int practiceId,
			ArrayList<PracticeSocials> socialsDetails) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_SOCIALS(serviceLayer, practiceId);
			return given().spec(requestSpec).body(socialsDetails).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_SOCIALS_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).body(socialsDetails).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValidMethodType")) {
			String url = ApiPath.PRACTICE_SOCIALS(serviceLayer, practiceId);
			return given().spec(requestSpec).body(socialsDetails).when().patch(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_SOCIALS_WITHOUT_PRACTICEID(serviceLayer);
			return given().spec(requestSpec).body(socialsDetails).when().post(url).then().extract().response();
		}
	}

	public Response addUpdatePracticeSocialsInvaliDatatypeResponse(String serviceLayer, String practiceId,
			ArrayList<PracticeSocials> socialsDetails) {
		String url = ApiPath.PRACTICE_SOCIALS_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).body(socialsDetails).when().post(url).then().extract().response();
	}

	public Response getPracticeSocialsResponse(String validURI, String serviceLayer, int practiceId) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_SOCIALS(serviceLayer, practiceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_SOCIALS_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValidMethodType")) {
			String url = ApiPath.PRACTICE_SOCIALS(serviceLayer, practiceId);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_SOCIALS_WITHOUT_PRACTICEID(serviceLayer);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		}
	}

	public Response getPracticeSocialsInvaliDatatypeResponse(String serviceLayer, String practiceId) {
		String url = ApiPath.PRACTICE_SOCIALS_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response updateOrgsSolutions_BusinessLayer(List<OrgSolutions> productList, int orgId) throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_BL(orgId);
		return given().spec(requestSpec).body(productList).when().put(url).then().extract().response();
	}

	public Response updateOrgSolutions_OrgNULLValue(List<OrgSolutions> productList) {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_BL_ORG_NULL;
		return given().spec(requestSpec).body(productList).when().put(url).then().extract().response();
	}

	public Response updateOrgsSolutions_BusinessLayer_InvalidUrl(List<OrgSolutions> productList, int orgId)
			throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_BL_INVALID_URL(orgId);
		return given().spec(requestSpec).body(productList).when().put(url).then().extract().response();
	}

	public Response updateOrgsSolutions_DataLayer_InvalidUrl(List<OrgSolutions> productList, int orgId)
			throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_DL_INVALID_URL(orgId);
		return given().spec(requestSpec).body(productList).when().put(url).then().extract().response();
	}

	public Response updateOrgsSolutions_BusinessLayer_InvalidHTTPMethod(List<OrgSolutions> productList, int orgId)
			throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_BL(orgId);
		return given().spec(requestSpec).body(productList).when().post(url).then().extract().response();
	}

	public Response updateOrgsSolutions_DataLayer_InvalidHTTPMethod(List<OrgSolutions> productList, int orgId)
			throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_DL(orgId);
		return given().spec(requestSpec).body(productList).when().post(url).then().extract().response();
	}

	public Response updateOrgsSolutions_DataLayer(List<OrgSolutions> productList, int orgId) throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_DL(orgId);
		return given().spec(requestSpec).body(productList).when().put(url).then().extract().response();
	}

	public Response updateOrgSolutions_OrgNULLValue_DL(List<OrgSolutions> productList) {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_DL_ORG_NULL;
		return given().spec(requestSpec).body(productList).when().put(url).then().extract().response();
	}

	public Response getPracticeInterfacesResponse(String validURI, String serviceLayer, int practiceId) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_INTERFACE(serviceLayer, practiceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_INTERFACE(serviceLayer, practiceId);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		}
	}

	public Response getPracticeInterfacesInvaliDatatypeResponse(String serviceLayer, String practiceId) {
		String url = ApiPath.PRACTICE_INTERFACE_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response addPracticeInterfacesResponse(String validURI, String serviceLayer, int practiceId,
			PracticeInterfaces addPracticeInterfaces) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_INTERFACE(serviceLayer, practiceId);
			return given().spec(requestSpec).body(addPracticeInterfaces).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).body(addPracticeInterfaces).when().post(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_INTERFACE(serviceLayer, practiceId);
			return given().spec(requestSpec).body(addPracticeInterfaces).when().patch(url).then().extract().response();
		}
	}

	public Response addPracticeInterfacesInvaliDatatypeResponse(String serviceLayer, String practiceId,
			PracticeInterfaces addPracticeInterfaces) {
		String url = ApiPath.PRACTICE_INTERFACE_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).body(addPracticeInterfaces).when().post(url).then().extract().response();
	}

	public Response updatePracticeInterfacesResponse(String validURI, String serviceLayer, int practiceId,
			int interfaceId, PracticeInterfaces updatePracticeInterfaces) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID(serviceLayer, practiceId, interfaceId);
			return given().spec(requestSpec).body(updatePracticeInterfaces).when().put(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_URI(serviceLayer, practiceId,
					interfaceId);
			return given().spec(requestSpec).body(updatePracticeInterfaces).when().put(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID(serviceLayer, practiceId, interfaceId);
			return given().spec(requestSpec).body(updatePracticeInterfaces).when().patch(url).then().extract()
					.response();
		}
	}

	public Response updatePracticeInterfacesInvaliDatatypeResponse(String serviceLayer, String practiceId,
			String interfaceId, PracticeInterfaces updatePracticeInterfaces) {
		String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_DATATYPE(serviceLayer, practiceId,
				interfaceId);
		return given().spec(requestSpec).body(updatePracticeInterfaces).when().put(url).then().extract().response();
	}

	public Response getByInterfaceResponse(String validURI, String serviceLayer, int practiceId, int interfaceId) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID(serviceLayer, practiceId, interfaceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_URI(serviceLayer, practiceId,
					interfaceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID(serviceLayer, practiceId, interfaceId);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		}
	}

	public Response getByInterfaceInvaliDatatypeResponse(String serviceLayer, String practiceId, String interfaceId) {
		String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_DATATYPE(serviceLayer, practiceId,
				interfaceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response deleteAnInterfaceResponse(String validURI, String serviceLayer, int practiceId, int interfaceId) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID(serviceLayer, practiceId, interfaceId);
			return given().spec(requestSpec).when().delete(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_URI(serviceLayer, practiceId,
					interfaceId);
			return given().spec(requestSpec).when().delete(url).then().extract().response();
		} else {
			String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID(serviceLayer, practiceId, interfaceId);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		}
	}

	public Response deleteAnInterfaceInvaliDatatypeResponse(String serviceLayer, String practiceId,
			String interfaceId) {
		String url = ApiPath.PRACTICE_INTERFACE_WITH_INTERFACEID_WITH_INVLAID_DATATYPE(serviceLayer, practiceId,
				interfaceId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response getOrgSolutionsDetails_DataLayer(int orgId, String type) {
		String url = ApiPath.ORGS_SOLUTION_DL + orgId + "/solutions";
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getSolutionOrgIdUsingInvalidUrl_DataLayer(int orgId, String type) throws IOException {
		String url = ApiPath.INVALID_PATH_ORGS_SOLUTION_DL(orgId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getOrgSolutionsDetails_DataLayer_InvalidHTTPMethod(int orgId, String type) {
		String url = ApiPath.ORGS_SOLUTION_DL + orgId + "/solutions";
		return given().spec(requestSpec).queryParam("type", type).when().post(url).then().extract().response();
	}

	public Response updateOrgSolutionsDetails_BL(int solutionId, String status, int orgId) throws IOException {
		String url = ApiPath.ORGS_SOLUTION_BL + orgId + "/solutions";
		List<Map<String, Object>> updateOrgsSolutionPayload = putUpdateOrgSolutionsDetails_BL(solutionId, status);
		return given().spec(requestSpec).body(updateOrgsSolutionPayload).when().put(url).then().extract().response();
	}

	public static List<Map<String, Object>> putUpdateOrgSolutionsDetails_BL(int solutionId, String NewlyPurchased) {
		List<Map<String, Object>> solutionsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> solutionsMap = new HashMap<String, Object>();
		solutionsMap.put("solutionId", solutionId);
		solutionsMap.put("status", NewlyPurchased);
		solutionsList.add(solutionsMap);
		return solutionsList;
	}

	public Response getPracticeTousResponse(String validURI, String serviceLayer, int practiceId, String type) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_ADD_TOUS(serviceLayer, practiceId);
			return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_ADD_TOUS_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
		} else {
			String url = ApiPath.GET_ADD_TOUS(serviceLayer, practiceId);
			return given().spec(requestSpec).queryParam("type", type).when().put(url).then().extract().response();
		}
	}

	public Response getPracticeTousInvaliDatatypeResponse(String serviceLayer, String practiceId, String type) {
		String url = ApiPath.GET_ADD_TOUS_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getByTouIdResponse(String validURI, String serviceLayer, int practiceId, int touId) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID_WITH_INVLAID_URI(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		}
	}

	public Response getByTouIdInvaliDatatypeResponse(String serviceLayer, String practiceId, String touId) {
		String url = ApiPath.GET_UPDATE_DELETE_TOUID_WITH_INVLAID_DATATYPE(serviceLayer, practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response addPracticeTousResponse(String validURI, String serviceLayer, int practiceId, PracticeTouDetails addPracticeTous) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_ADD_TOUS(serviceLayer, practiceId);
			return given().spec(requestSpec).body(addPracticeTous).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_ADD_TOUS_WITH_INVLAID_URI(serviceLayer, practiceId);
			return given().spec(requestSpec).body(addPracticeTous).when().post(url).then().extract().response();
		} else {
			String url = ApiPath.GET_ADD_TOUS(serviceLayer, practiceId);
			return given().spec(requestSpec).body(addPracticeTous).when().patch(url).then().extract().response();
		}
	}

	public Response addPracticeTousInvaliDatatypeResponse(String serviceLayer, String practiceId, PracticeTouDetails addPracticeTous) {
		String url = ApiPath.GET_ADD_TOUS_WITH_INVLAID_DATATYPE(serviceLayer, practiceId);
		return given().spec(requestSpec).body(addPracticeTous).when().post(url).then().extract().response();
	}

	public Response updatePracticeTousResponse(String validURI, String serviceLayer, int practiceId, int touId, PracticeTouDetails updatePracticeTous) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).body(updatePracticeTous).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID_WITH_INVLAID_URI(serviceLayer, practiceId,
					touId);
			return given().spec(requestSpec).body(updatePracticeTous).when().post(url).then().extract().response();
		} else {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).body(updatePracticeTous).when().patch(url).then().extract()
					.response();
		}
	}

	public Response updatePracticeTousInvaliDatatypeResponse(String serviceLayer, String practiceId, String touId, PracticeTouDetails updatePracticeTous) {
		String url = ApiPath.GET_UPDATE_DELETE_TOUID_WITH_INVLAID_DATATYPE(serviceLayer, practiceId,
				touId);
		return given().spec(requestSpec).body(updatePracticeTous).when().post(url).then().extract().response();
	}

	public Response deletePracticeTousResponse(String validURI, String serviceLayer, int practiceId, int touId, Map<String, Object>  deleteTouMap) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).body(deleteTouMap).when().put(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID_WITH_INVLAID_URI(serviceLayer, practiceId,
					touId);
			return given().spec(requestSpec).body(deleteTouMap).when().put(url).then().extract().response();
		} else {
			String url = ApiPath.GET_UPDATE_DELETE_TOUID(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).body(deleteTouMap).when().patch(url).then().extract()
					.response();
		}
	}

	public Response deletePracticeTousInvaliDatatypeResponse(String serviceLayer, String practiceId, String touId, Map<String, Object> deleteTouMap) {
		String url = ApiPath.GET_UPDATE_DELETE_TOUID_WITH_INVLAID_DATATYPE(serviceLayer, practiceId,
				touId);
		return given().spec(requestSpec).body(deleteTouMap).when().put(url).then().extract().response();
	}

	public Response getOrgSolutionsDetails_BusinessLayer(int orgId, String type) {
		String url = ApiPath.ORGS_SOLUTION_BL + orgId + "/solutions";
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getPatientAccessSettings_DataLayer(String urlType, int practiceId) {
		if(urlType.equalsIgnoreCase("valid")) {
			String url = ApiPath.GET_PATIENT_ACCESS_SETTINGS_DL("Valid", practiceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		}
		else if(urlType.equalsIgnoreCase("Invalid")) {
			String url = ApiPath.GET_PATIENT_ACCESS_SETTINGS_DL("Invalid", practiceId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		}

		else {
			String url = ApiPath.GET_PATIENT_ACCESS_SETTINGS_DL("Valid", practiceId);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		}
	}

	public Response getPatientAccessSettings_DataLayer(String practiceId) {
		String url = ApiPath.GET_PATIENT_ACCESS_SETTINGS_DL(practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
}
