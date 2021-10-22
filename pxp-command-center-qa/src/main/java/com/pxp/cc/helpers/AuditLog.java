// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class AuditLog extends BaseRest {
	private static final String X_TIMEZONE_OFFSET = "X-Timezone-Offset";
	private static final String X_TIMEZONE_OFFSET_VALUE = "-330";

	public Response viewAuditLog(List<String> eventcds, int limit) throws IOException {
		Map<String, Object> auditLogViewPayload = postAuditLog(eventcds, limit);
		return given().spec(requestSpec).header(X_TIMEZONE_OFFSET, X_TIMEZONE_OFFSET_VALUE).body(auditLogViewPayload)
				.when().post(ApiPath.POST_AUDIT_LOG).then().extract().response();
	}

	private Map<String, Object> postAuditLog(List<String> eventcds, int limit) {
		Map<String, Object> auditLogViewMap = new HashMap<String, Object>();
		auditLogViewMap.put("eventcds", eventcds);
		auditLogViewMap.put("limit", limit);
		return auditLogViewMap;
	}
}
