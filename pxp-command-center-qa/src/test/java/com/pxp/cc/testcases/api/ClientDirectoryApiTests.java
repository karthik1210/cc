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
				{ ccUser.getQueryFullClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryPartialClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryFullCommandCenterId().trim(), ccUser.getPage().trim(),
						ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryPartialCommandCenterId().trim(), ccUser.getPage().trim(),
						ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryFullSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryPartialSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryFullEnterpriseName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryPartialEnterpriseName().trim(), ccUser.getPage().trim(),
						ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryFullPracticeName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getQueryPartialPracticeName().trim(), ccUser.getPage().trim(),
						ccUser.getPageSizeOne().trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithoutfilter", description = "search client nodes in client directory by keeping Items per page as 10 and current page as 1-10")
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
			int clientPerData = jsonPath.getInt("data.size()");
			log("Get the size of clients in response body : " + clientPerData);
			log("Page Number : " + jsonPath.getInt("pageNo"));
			log("Items(Clients) per Page : " + jsonPath.getInt("pageSize"));
			log("Comparing Searched Items names/ids with client data received in Response body.");
			for (int dataArrayIteration = 0; dataArrayIteration < jsonPath
					.getInt("data.size()"); dataArrayIteration++) {
				String clientIteratePerData = jsonPath.get("data[" + dataArrayIteration + "].name");
				log("Print Value : " + clientIteratePerData);
				if (clientIteratePerData.contains(getSearchClientDetails[0])) {
					log("searched value :" + getSearchClientDetails[0] + "present within response body's value"
							+ clientIteratePerData);
					CCUtils.assertTrue(true, "Found data " + clientIteratePerData + " is present in expected data"
							+ getSearchClientDetails[0]);
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
				{ ccUser.getQueryFullClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType },
				{ ccUser.getQueryPartialClientName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType },
				{ ccUser.getQueryFullCommandCenterId().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType },
				{ ccUser.getQueryPartialCommandCenterId().trim(), ccUser.getPage().trim(),
						ccUser.getPageSizeOne().trim(), filterType },
				{ ccUser.getQueryFullSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType },
				{ ccUser.getQueryPartialSystemName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType },
				{ ccUser.getQueryFullEnterpriseName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType },
				{ ccUser.getQueryPartialEnterpriseName().trim(), ccUser.getPage().trim(),
						ccUser.getPageSizeOne().trim(), filterType },
				{ ccUser.getQueryFullPracticeName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType },
				{ ccUser.getQueryPartialPracticeName().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim(),
						filterType } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithfilter", description = "search client nodes by applying multiselect Client Type Filter in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchClientOutputWithFilter(Object[] clientDataWithFilter) throws IOException {
		try {
			System.out.println("Type :" + clientDataWithFilter[3]);
			Response searchClientDirResultWithFilter = clientDirectory.getClientFromClientDirectoryWithFilter(
					clientDataWithFilter[0].toString(), clientDataWithFilter[1].toString(),
					clientDataWithFilter[2].toString(), clientDataWithFilter[3]);
			log("Validating Response Time : " + searchClientDirResultWithFilter.toString());
			CCUtils.responseTimeValidation(searchClientDirResultWithFilter);
			log("Validating Response Code");
			CCUtils.assertEquals(searchClientDirResultWithFilter.getStatusCode(), 200);
			jsonPath = new JsonPath(searchClientDirResultWithFilter.asString());
			log("Print data[] in response body : " + jsonPath.get("data"));
			int clientdataSize = jsonPath.getInt("data.size()");
			List<String> filterType = (List<String>) clientDataWithFilter[3];
			log("Comparing Searched Items names/ids with client data received in Response body.");
			for (int dataArrayIteration = 0; dataArrayIteration < clientdataSize; dataArrayIteration++) {
				String clientIteratePerData = jsonPath.get("data[" + dataArrayIteration + "].name");
				if (clientIteratePerData.contains(clientDataWithFilter[0].toString())) {
					log("Searched value :" + clientDataWithFilter[0] + " is present within response body's value : "
							+ clientIteratePerData);
					CCUtils.assertTrue(true, "Found data " + clientIteratePerData + " is present in expected data : "
							+ clientDataWithFilter[0].toString());
				}
			}
			log("Comparing Searched Items applied  Multifilter Type with client type data received in Response body.");
			for (int dataArrayIteration = 0; dataArrayIteration < clientdataSize; dataArrayIteration++) {
				String clientTypePerData = jsonPath.get("data[" + dataArrayIteration + "].type");
				for (int multiFilterIteration = 0; multiFilterIteration < filterType.size(); multiFilterIteration++) {
					if (clientTypePerData.contains(filterType.get(multiFilterIteration))) {
						CCUtils.assertTrue(true,
								"Found data : " + clientTypePerData + " is matches with expected data : "
										+ filterType.get(multiFilterIteration).toString());
					} else {
						log("Found data : " + clientTypePerData + " does not matches with expected data : "
								+ filterType.get(multiFilterIteration).toString());
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
				{ ccUser.getNonexistingclient().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getNonexistingccid().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getNonexistingsystem().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getNonexistingenterprise().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() },
				{ ccUser.getNonexistingpractice().trim(), ccUser.getPage().trim(), ccUser.getPageSizeOne().trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatanonexisting", description = "search non existing client/client nodes in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchNonExistingClient(Object[] clientDataNonExisting) {
		try {
			Response nonexistingClient = clientDirectory.getClientFromClientDirectoryWithoutFilter(
					clientDataNonExisting[0].toString(), clientDataNonExisting[1].toString(),
					clientDataNonExisting[2].toString());
			log("Validating Response Time");
			CCUtils.responseTimeValidation(nonexistingClient);
			log("Validating Response Code");
			CCUtils.assertEquals(nonexistingClient.getStatusCode(), 200);
			jsonPath = new JsonPath(nonexistingClient.asString());
			log("Print data[] in response body : " + jsonPath.get("data"));
			int clientDataSize = jsonPath.getInt("data.size()");
			log("Get the size of data[] in response body : " + clientDataSize);
			if (clientDataSize == 0) {
				log("Client Data array in Response body is empty,so expected searched data : "
						+ clientDataNonExisting[0].toString() + " does not found in response body.");
			}

		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}
	}
}
