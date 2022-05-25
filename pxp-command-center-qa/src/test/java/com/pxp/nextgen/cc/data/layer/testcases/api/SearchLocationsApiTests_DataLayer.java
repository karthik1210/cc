// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import com.pxp.cc.model.BaseRest;

public class SearchLocationsApiTests_DataLayer extends BaseRest{
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
	}

	@DataProvider(name = "userTypeValue")
	public Object[][] getUserTypes() throws Exception {
		return new Object[][] { { testData.getProperty("user.type.internal").trim() },
				{ testData.getProperty("user.type.client").trim() },
				{ testData.getProperty("user.type.varreseller").trim() } };
	}
	
	

}
