// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class PatientAccessAgeoutBusinessLayer {
    public boolean autoAgeout;
    public int defaultAgeout;
    public ArrayList<PatientAccessAgeOutRulesBusinessLayer> ageoutRules;
}