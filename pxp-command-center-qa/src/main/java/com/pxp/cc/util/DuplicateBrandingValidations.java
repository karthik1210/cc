package com.pxp.cc.util;

import java.io.IOException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DuplicateBrandingValidations {

	public void verifyErrorMessageInResponseForNonExistingBrandId(Response response, String nonExistingBrandId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Brand not found by id=" + nonExistingBrandId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(Response response, String nonExistingOwnerId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org not found for OrgId=" + nonExistingOwnerId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNonExistingOrgIdOfClient(Response response, String nonExistingOwnerId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found by id=" + nonExistingOwnerId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidOwnerType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid ownerType");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNonExistingOwnerIdForClient(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid ownerType");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNullOwnerType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "ownerType cannot be empty or null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNullOwnerId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "ownerId cannot be null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

}
