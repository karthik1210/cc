// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class PracticeInterfaces {
    public int externalSystemId;
    public HashMap<String, Object> params;
    public boolean active;
}