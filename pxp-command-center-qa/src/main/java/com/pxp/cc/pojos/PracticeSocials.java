// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PracticeSocials {
    public int socialId;
    public String href;
    public boolean enabled;
}
