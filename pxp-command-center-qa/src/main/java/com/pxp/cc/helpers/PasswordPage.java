// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.medfusion.common.utils.Mailinator;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class PasswordPage extends BaseRest {

	public Mailinator mailinator = new Mailinator();

	public Response forgotPassword(String userName) {

		String url = ApiPath.POST_FORGOT_PASSWORD;
		return given().spec(requestSpec).body(userName).when().post(url).then().extract().response();
	}
	
	public Response resetPasswordFunctionality(String password, String token) throws IOException {
		Map<String, Object> resetPasswordPayload = postResetPassword(password, token);
		String url = ApiPath.POST_RESET_PASSWORD;
		return given().spec(requestSpec).body(resetPasswordPayload).when().post(url).then().extract()
				.response();
	}
	
	private Map<String, Object> postResetPassword(String password, String token) {
		Map<String, Object> resetPasswordMap = new HashMap<String, Object>();
		resetPasswordMap.put("password", password);
		resetPasswordMap.put("token", token);
		return resetPasswordMap;
	}
	
	public String urlInEmail(String resetMailinatorEmail, String autogeneratePwd) throws InterruptedException {
		String url = mailinator.getLinkFromEmailWithText(resetMailinatorEmail, "Password reset request", "pwd-change?key", 1);
		log("Fetching the reset password url received in email:" + url);
		return url;
	}
	
	public String getTokenValueFromEmailLink(String url)
	{
	String[] list = url.split("=");
	String token = list[1];
	String[] splitToken = token.split(" ");
	return splitToken[0];
	}

}
