// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTemplateAttributes {
    public String email_subject;
    public String email_body;
}
