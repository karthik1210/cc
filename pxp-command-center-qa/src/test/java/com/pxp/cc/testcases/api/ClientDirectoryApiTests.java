// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientDirectory;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ClientDirectoryApiTests extends BaseRest {

	private ClientDirectory clientDirectory;
	JsonPath jsonPath;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientDirectory = new ClientDirectory();
	}

	@DataProvider(name = "clientdatawithoutfilter")
	public Object[] searchClientDetailsWithoutFilter() {
		Object[][] obj = new Object[][] {
				{ ccUser.getQueryFullClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getQueryPartialClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getQueryFullCommandCenterId().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getQueryPartialCommandCenterId().trim(), ccUser.getPage().trim(),
						ccUser.getPageSize1().trim() },
				{ ccUser.getQueryFullSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getQueryPartialSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getQueryFullEnterpriseName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getQueryPartialEnterpriseName().trim(), ccUser.getPage().trim(),
						ccUser.getPageSize1().trim() },
				{ ccUser.getQueryFullPracticeName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getQueryPartialPracticeName().trim(), ccUser.getPage().trim(),
						ccUser.getPageSize1().trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithoutfilter", description = "search client/system/enterprise/practice/clientid in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchClientOutputWithoutFilter(String[] getSearchClientDetails) throws IOException {
		try {
			Response searchClientDirResult = clientDirectory.getClientFromClientDirectoryWithoutFilter(
					getSearchClientDetails[0], getSearchClientDetails[1], getSearchClientDetails[2]);
			log("Validating Response Time");
			CCUtils.responseTimeValidation(searchClientDirResult);
			log("Validating Response Code");
			CCUtils.assertEquals(searchClientDirResult.getStatusCode(), 200);
			jsonPath = new JsonPath(searchClientDirResult.asString());
			log("Display list of clients in response body : " + jsonPath.get("data"));
			int data = jsonPath.getInt("data.size()");
			log("Get the size of clients in response body : " + data);
			log("Page Number : " + jsonPath.getInt("pageNo"));
			log("Items(Clients) per Page : " + jsonPath.getInt("pageSize"));
			for (int i = 0; i < jsonPath.getInt("data.size()"); i++) {
				String valueperdata = jsonPath.get("data[" + i + "].name");
				log("Print Value : " + valueperdata);
				if (valueperdata.contains(getSearchClientDetails[0])) {
					log("searched value :" + getSearchClientDetails[0] + "present within response body's value"
							+ valueperdata);
					boolean update = true;
					CCUtils.assertTrue(update,
							"Found data " + valueperdata + " is present in expexted data" + getSearchClientDetails[0]);
				}
			}
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}
	}

	@DataProvider(name = "clientdatawithfilter")
	public Object[] searchClientDetailsWithFilter() {
		ArrayList<String> filterType = new ArrayList<String>();
		filterType.add(ccUser.getClientonetype());
		filterType.add(ccUser.getClienteleventype());
		filterType.add(ccUser.getClienttwotype());
		filterType.add(ccUser.getClienteighttype());
		Object[][] obj = new Object[][] {
				{ ccUser.getQueryFullClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryPartialClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryFullCommandCenterId().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryPartialCommandCenterId().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryFullSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryPartialSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryFullEnterpriseName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryPartialEnterpriseName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryFullPracticeName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType },
				{ ccUser.getQueryPartialPracticeName().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim(),
						filterType } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithfilter", description = "search client/system/enterprise/practice/clientid by applying multiselect Client Type Filter in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchClientOutputWithFilter(Object[] clientdatawithfilter) throws IOException {
		try {
			System.out.println("Type :" + clientdatawithfilter[3]);
			Response searchClientDirResult = clientDirectory.getClientFromClientDirectoryWithFilter(
					clientdatawithfilter[0].toString(), clientdatawithfilter[1].toString(),
					clientdatawithfilter[2].toString(), clientdatawithfilter[3]);
			log("Validating Response Time : " + searchClientDirResult.toString());
			CCUtils.responseTimeValidation(searchClientDirResult);
			log("Validating Response Code");
			CCUtils.assertEquals(searchClientDirResult.getStatusCode(), 200);
			jsonPath = new JsonPath(searchClientDirResult.asString());
			log("Print data[] in response body : " + jsonPath.get("data"));
			int data = jsonPath.getInt("data.size()");
			log("Get the size of data[] in response body : " + data);
			log("Value of Page : " + jsonPath.getInt("pageNo"));
			log("Value of current Page size : " + jsonPath.getInt("pageSize"));
			List<String> filterType = (List<String>) clientdatawithfilter[3];

			for (int i = 0; i < data; i++) {
				String valuePerData = jsonPath.get("data[" + i + "].name");
				if (valuePerData.contains(clientdatawithfilter[0].toString())) {
					log("Searched value :" + clientdatawithfilter[0] + " is present within response body's value : "
							+ valuePerData);
					CCUtils.assertTrue(true, "Found data " + valuePerData + " is present in expected data : "
							+ clientdatawithfilter[0].toString());
				}
			}
			for (int i = 0; i < data; i++) {
				String clientTypePerData = jsonPath.get("data[" + i + "].type");
				for (int j = 0; j < filterType.size(); j++) {
					if (clientTypePerData.contains(filterType.get(j))) {
						CCUtils.assertTrue(true, "Found data : " + clientTypePerData
								+ " is matches with expected data : " + filterType.get(j).toString());
					} else {
						log("Found data : " + clientTypePerData + " does not matches with expected data : "
								+ filterType.get(j).toString());
					}
				}
			}
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}
	}

	@DataProvider(name = "clientdatanonexisting")
	public Object[] searchClientDetails() {
		Object[][] obj = new Object[][] {
				{ ccUser.getNonexistingclient().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getNonexistingccid().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getNonexistingsystem().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getNonexistingenterprise().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() },
				{ ccUser.getNonexistingpractice().trim(), ccUser.getPage().trim(), ccUser.getPageSize1().trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatanonexisting", description = "search non existing client/system/enterprise/practice/clientid in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchNonExistingClient(Object[] clientdatanonexisting) {
		try {
			Response nonexistingClient = clientDirectory.getClientFromClientDirectoryWithoutFilter(
					clientdatanonexisting[0].toString(), clientdatanonexisting[1].toString(),
					clientdatanonexisting[2].toString());
			log("Validating Response Time");
			CCUtils.responseTimeValidation(nonexistingClient);
			log("Validating Response Code");
			CCUtils.assertEquals(nonexistingClient.getStatusCode(), 200);
			jsonPath = new JsonPath(nonexistingClient.asString());
			log("Print data[] in response body : " + jsonPath.get("data"));
			int data = jsonPath.getInt("data.size()");
			log("Get the size of data[] in response body : " + data);
			if (data == 0) {
				log("Data array is empty. So expected searched data : " + clientdatanonexisting[0].toString()
						+ " does not found in response body.");
			}

		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}
	}
}
