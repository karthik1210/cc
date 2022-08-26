// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrgNode {
	private Integer orgId;
	private Integer parentId;
	private String type;
	private String name;
	private boolean active;
}
