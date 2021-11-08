// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.pojos.UpdateProductStatus;
import io.restassured.response.Response;

public class UpdateOrgProductStatusValidations {

	public ArrayList<HashMap<String, Object>> solutionList;

	public void validatePracticeOrgTypeInResponseBody(Response response) {
		CCUtils.assertEquals(response.jsonPath().get("orgType"), "PRACTICE");
	}

	public Map<String, Object> fetchKeyValuesFromResponseBodyOfGetOrg(Response response) {
		Map<String, Object> responseFetch = new HashMap<String, Object>();
		responseFetch.put("active", response.jsonPath().get("active").toString());
		responseFetch.put("displayName", response.jsonPath().get("displayName").toString());
		responseFetch.put("id", response.jsonPath().get("orgId").toString());
		responseFetch.put("sapNumber", response.jsonPath().get("sapNumber").toString());
		responseFetch.put("solutionStatusList", response.jsonPath().get("sapNumber").toString());
		responseFetch.put("timezoneId", response.jsonPath().get("timezone.id").toString());
		return responseFetch;
	}

	public String fetchActiveKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("active").toString();
	}

	public String fetchDisplayNameKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("displayName").toString();
	}

	public String fetchSapNumberKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("sapNumber").toString();
	}

	public String fetchOrgIdKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("orgId").toString();
	}

	public String fetchTimezoneIdKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("timezone.id").toString();
	}

	public List<UpdateProductStatus> fetchAndUpdateSolutionsListInGetOrgResponseBody(Response response, String status,
			String statusDate, String statusNotes) {
		solutionList = new ArrayList<HashMap<String, Object>>();
		solutionList = response.jsonPath().get("solutions");
		ArrayList<HashMap<String, Object>> storeValue = new ArrayList<HashMap<String, Object>>();
		Log4jUtil.log("Print the value of solution List present within Get Org Response Body : " + solutionList);
		for (HashMap<String, Object> solutionDetails : solutionList) {
			Iterator<Entry<String, Object>> it = solutionDetails.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				Log4jUtil.log("Key = " + entry.getKey() + " and Value = " + entry.getValue());
				if (entry.getKey().equals("status") && (entry.getValue() != null)) {
					storeValue.add(solutionDetails);
				}
			}
		}
		Log4jUtil.log("Print the value of solution List with status not equal to null : " + storeValue);
		List<String> listOfProductId = fetchProductsfromGetOrgId(storeValue);
		List<UpdateProductStatus> listOfUpdatedProducts = fetchUpdatedSolutionsList(listOfProductId, status, statusDate,
				statusNotes);
		return listOfUpdatedProducts;
	}

	public void validateSolutionsListInGetOrgResponseBody(Response response, String status) {
		solutionList = new ArrayList<HashMap<String, Object>>();
		solutionList = response.jsonPath().get("solutions");
		for (HashMap<String, Object> solutionDetails : solutionList) {
			Iterator<Entry<String, Object>> it = solutionDetails.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				if (entry.getKey().equals("status") && (entry.getValue() != null)) {
					CCUtils.assertEquals(entry.getValue(), status);
				}
			}
		}
	}

	public List<String> fetchProductsfromGetOrgId(ArrayList<HashMap<String, Object>> solutionDetails) {
		List<String> keyValueStore = new ArrayList<String>();
		for (HashMap<String, Object> solutionDetail : solutionDetails) {
			Iterator<Entry<String, Object>> itr = solutionDetail.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, Object> entrySolution = itr.next();
				if (entrySolution.getKey().equals("productId")) {
					keyValueStore.add(entrySolution.getValue().toString());
				}
			}
		}
		Log4jUtil.log("Print List " + keyValueStore);
		return keyValueStore;
	}

	public List<UpdateProductStatus> fetchUpdatedSolutionsList(List<String> productIdList, String status,
			String stausDate, String statusNotes) {
		List<UpdateProductStatus> listofUpdatedProducts = new ArrayList<UpdateProductStatus>();
		if (stausDate == null) {
			for (int i = 0; i < productIdList.size(); i++) {
				listofUpdatedProducts.add(new UpdateProductStatus(productIdList.get(i), status, stausDate, null));
			}
		} else if (stausDate == "") {
			for (int i = 0; i < productIdList.size(); i++) {
				listofUpdatedProducts.add(new UpdateProductStatus(productIdList.get(i), status, stausDate, null));
			}
		} else {
			for (int i = 0; i < productIdList.size(); i++) {
				listofUpdatedProducts.add(new UpdateProductStatus(productIdList.get(i), status, stausDate, null));
			}
		}
		return listofUpdatedProducts;
	}

	public void validateOrgIdZeroErrorMessage(Response response) {
		CCUtils.assertEquals(response.jsonPath().get("message"), "Org not found for org id=" + 0);
	}

	public void validateTimeZoneIdDoesnotExistErrorMessage(Response response, String nonExistingTimezone) {
		CCUtils.assertEquals(response.jsonPath().get("message"),
				"Timezone id=" + nonExistingTimezone + " doesn't exist");
	}

	public void validateInvalidDateAndFormatErrorMessage(Response response) {
		CCUtils.assertEquals(response.jsonPath().get("message"),
				"Invalid date format must be=MM/dd/yyyy");
	}

	public void validateDateAndFormatNotFoundErrorMessage(Response response) {
		CCUtils.assertEquals(response.jsonPath().get("message"), "Input parameters can not be null or empty");
	}

}
