// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Client {

	public static Map<String, Object> postCreateClientUpdateClient(String active, String clientId, String clientName,
			String clientSapNumber, String testClient, String type, String timezoneId) {
		Map<String, Object> createClientMap = new HashMap<String, Object>();
		createClientMap.put("active", active);
		createClientMap.put("clientId", clientId);
		createClientMap.put("name", clientName);
		createClientMap.put("sapNumber", clientSapNumber);
		createClientMap.put("testClient", testClient);
		createClientMap.put("type", type);
		createClientMap.put("timezoneId", timezoneId);
		return createClientMap;

	}
}
