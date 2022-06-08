// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrgHierarchyDetails {
	private Integer orgId;
	private String name;
	private String type;
	private boolean active;
	private Integer parentId;
}
