// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceStatus {
	private Integer roleId;
	private String status;
	private String message;
}
