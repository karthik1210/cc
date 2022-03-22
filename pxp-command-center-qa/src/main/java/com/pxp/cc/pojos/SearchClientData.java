// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchClientData {
	private int clientId;
	private String name;
	private String type;
	private boolean favorite;

}
