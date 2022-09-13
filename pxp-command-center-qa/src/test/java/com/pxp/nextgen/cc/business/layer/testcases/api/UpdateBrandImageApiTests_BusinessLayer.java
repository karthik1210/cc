// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateBrandImageApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private BrandsInfo brandsInfo;
	private Validations validation;
	Response updateBrandingLogoApiResponse, getBrandingDetailsApiResponse, createBrandApiResponse,
			createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAgnosticPracticeApiResponse;;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId, brandId;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		brandsInfo = new BrandsInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
	}
	

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  brand image in  logo type and verify response code")
	public void testResponseCodeForUpdateBrandImageInFavicon() throws IOException {
	//Dev
	//	int brandId = 5567 ;
		
		
		//Qa
		int brandId = 5289 ;
		
		log("Update brand image for existing brandId in favicon logo type");
		String projectpath = System.getProperty("user.dir");
		File file = new File(projectpath +  File.separator +"src\\test\\resources\\data-driven\\images\\leaf.svg");
      
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);

	}	

	}
