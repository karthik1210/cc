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

	public Response getListOfTouForAPractice_BL(int practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_BL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPracticeWithInvalidRequest_BL(int practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_BL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().put(url).then().extract().response();
	}

	public Response getListOfTouForAPracticeWithInvalidURI_BL(int practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_WITH_INVALID_URI_BL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPractice_BL(String practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_BL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPractice_BL(int practiceId, int type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_BL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPractice_BL(String practiceId, int type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_BL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForPracticeWithoutPracticeId_BL(String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_WITH_EMPTYVALUE_ON_PRACTICEID_BL();
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPractice_DL(int practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_DL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPracticeWithInvalidRequest_DL(int practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_DL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().put(url).then().extract().response();
	}

	public Response getListOfTouForAPracticeWithInvalidURI_DL(int practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_WITH_INVALID_URI_DL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPractice_DL(String practiceId, String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_DL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPractice_DL(int practiceId, int type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_DL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getListOfTouForAPractice_DL(String practiceId, int type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_DL(practiceId);
		return given().spec(requestSpec).queryParam("type", type).when().get(url.replace("%2F%2F", "")).then().extract()
				.response();
	}

	public Response getListOfTouForPracticeWithoutPracticeId_DL(String type) {
		String url = ApiPath.FETCH_LIST_OF_TOU_FOR_A_PRACTICE_WITH_EMPTYVALUE_ON_PRACTICEID_DL();
		return given().spec(requestSpec).queryParam("type", type).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_BL(int practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_BL(String practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_BL(int practiceId, String touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_BL(String practiceId, String touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouIdWithInvalidRequest_BL(int practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouIdWithInvalidURI_BL(int practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_WITH_INVALID_URI_BL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingWithoutPracticeIdAndValidTouId_BL(int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_WITHOUT_PRACTICEID_AND_VALID_TOUID_BL(touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingWithoutPracticeIdAndTouId_BL() {
		String url = ApiPath.FETCH_PRIVACYSETTING_WITHOUT_PRACTICEID_AND_TOUID_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_DL(int practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_DL(String practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_DL(int practiceId, String touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouId_DL(String practiceId, String touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouIdWithInvalidRequest_DL(int practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}

	public Response getPrivacySettingByPracticeIdAndTouIdWithInvalidURI_DL(int practiceId, int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_WITH_INVALID_URI_DL(practiceId, touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingWithoutPracticeIdAndValidTouId_DL(int touId) {
		String url = ApiPath.FETCH_PRIVACYSETTING_WITHOUT_PRACTICEID_AND_VALID_TOUID_DL(touId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPrivacySettingWithoutPracticeIdAndTouId_DL() {
		String url = ApiPath.FETCH_PRIVACYSETTING_WITHOUT_PRACTICEID_AND_TOUID_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public static Map<String, Object> deletePrivacySetting(String deleteStatus) {
		Map<String, Object> deletePrivacySetting = new HashMap<String, Object>();
		deletePrivacySetting.put("deleted", deleteStatus);
		return deletePrivacySetting;
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_BL(int practiceId, int touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_BL(String practiceId, int touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_BL(int practiceId, String touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_BL(String practiceId, String touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouIdWithInvalidRequest_BL(int practiceId, int touId,
			String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_BL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().patch(url).then().extract()
				.response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouIdWithInvalidURI_BL(int practiceId, int touId,
			String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_WITH_INVALID_URI_BL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_DL(int practiceId, int touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_DL(String practiceId, int touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_DL(int practiceId, String touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouId_DL(String practiceId, String touId, String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouIdWithInvalidRequest_DL(int practiceId, int touId,
			String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_DL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().patch(url).then().extract()
				.response();
	}

	public Response deletePrivacySettingByPracticeIdAndTouIdWithInvalidURI_DL(int practiceId, int touId,
			String deleteStatus) {
		String url = ApiPath.DELETE_PRIVACYSETTING_BY_PRACTICEID_AND_TOUID_WITH_INVALID_URI_DL(practiceId, touId);
		Map<String, Object> deletePrivacySettingPayload = deletePrivacySetting(deleteStatus);
		return given().spec(requestSpec).body(deletePrivacySettingPayload).when().put(url).then().extract().response();
	}

	public static Map<String, Object> addTouMap(String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType) {
		Map<String, Object> addTouMap = new HashMap<String, Object>();
		addTouMap.put("name", name);
		addTouMap.put("active", active);
		addTouMap.put("solutions", TouSolutions);
		addTouMap.put("content", addTouContMap);
		addTouMap.put("touType", touType);
		return addTouMap;
	}

	public Response addTou_BL(Integer practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.ADDTOU_BL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
	}

	public Response addTouInvalidURI_BL(Integer practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.INVALIDURI_ADDTOU_BL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
	}

	public Response addTouAlphaNumericPracticeId_BL(String practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.INVALID_DATATYPE_ADDTOU_BL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
	}

	public Response addTouInvalidMethod_BL(Integer practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.ADDTOU_BL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().patch(url).then().extract().response();
	}

	public Response addTouToNullPractice_BL(String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.NULL_PRACTICEID_ADDTOU_BL();
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
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

	public Response addTou_DL(Integer practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.ADDTOU_DL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
	}

	public Response addTouInvalidURI_DL(Integer practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.INVALIDURI_ADDTOU_DL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
	}

	public Response addTouAlphaNumericPracticeId_DL(String practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.INVALID_DATATYPE_ADDTOU_DL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
	}

	public Response addTouInvalidMethod_DL(Integer practiceId, String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.ADDTOU_DL(practiceId);
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().patch(url).then().extract().response();
	}

	public Response addTouToNullPractice_DL(String name, Boolean active,
			ArrayList<HashMap<String, Object>> TouSolutions, Map<String, Object> addTouContMap, String touType)
			throws IOException {
		String url = ApiPath.NULL_PRACTICEID_ADDTOU_DL();
		Map<String, Object> addTouMapPayload = addTouMap(name, active, TouSolutions, addTouContMap, touType);
		return given().spec(requestSpec).body(addTouMapPayload).when().post(url).then().extract().response();
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

	public Response updateTouPracticeResponse(String validURI, String serviceLayer, int practiceId, int touId,
			UpdateTou updateTouDetails) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.UPDATE_TOU(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).body(updateTouDetails).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.UPDATE_TOU_WITH_INVLAID_URI(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).body(updateTouDetails).when().post(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValidMethodType")) {
			String url = ApiPath.UPDATE_TOU(serviceLayer, practiceId, touId);
			return given().spec(requestSpec).body(updateTouDetails).when().patch(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("NullValue")) {
			String url = ApiPath.NULL_UPDATE_TOU(serviceLayer);
			return given().spec(requestSpec).body(updateTouDetails).when().post(url).then().extract().response();
		} else {
			String url = ApiPath.UPDATE_TOU_WITHOUT_PRACTICEID(serviceLayer);
			return given().spec(requestSpec).body(updateTouDetails).when().post(url).then().extract().response();
		}
	}

	public Response updateTouPracticeInvaliDatatypeResponse(String serviceLayer, String practiceId, String touId,
			UpdateTou updateTouDetails) {
		String url = ApiPath.UPDATE_TOU_WITH_INVLAID_DATATYPE(serviceLayer, practiceId, touId);
		return given().spec(requestSpec).body(updateTouDetails).when().post(url).then().extract().response();
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

	public Response updateOrgsSolutions_BusinessLayer(List<OrgSolutions> productList, int orgId)
			throws IOException {
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
	
	public Response updateOrgsSolutions_BusinessLayer_InvalidHTTPMethod(List<OrgSolutions> productList, int orgId)
			throws IOException {
		String url = ApiPath.UPDATE_ORG_SOLUTIONS_BL(orgId);
		return given().spec(requestSpec).body(productList).when().post(url).then().extract().response();
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

}
