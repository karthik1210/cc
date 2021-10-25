// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import com.medfusion.common.utils.Mailinator;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class PasswordPage extends BaseRest {

	public Mailinator mailinator = new Mailinator();

	public Response password(String userName) {

		String url = ApiPath.POST_FORGOT_PASSWORD;
		return given().spec(requestSpec).body(userName).when().post(url).then().extract().response();
	}

	public String urlInEmail(String resetMailinatorEmail, String autogeneratePwd) throws InterruptedException {
		String url = mailinator.getLinkFromEmailWithToken(resetMailinatorEmail, "Password reset request",
				"pwd-change?key", 1);
		log("Fetching the reset password url received in email:" + url);
		return url;
	}

}
