// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.Product;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetProductListTests extends BaseRest {
	private Validations validation;
	private Product product;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		validation = new Validations();
		product = new Product();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check productid & name ")
	public void testProductValues() throws IOException {
		Response res = product.getProducts();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 200);
		validation.verifyGetProductsApiResponseBody(res);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instaead of get ")
	public void testErrorMessageForInvalidProduct() throws IOException {
		Response res = product.getInvalidProduct();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 405);
	}
}
