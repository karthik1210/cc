// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class PatientAccessDetailsBusinessLayer {
    public PatientAccessSettingsBusinessLayer settings;
    public PatientAccessAgeoutBusinessLayer ageout;
    public PatientGuardianAccessBusinessLayer guardianAccess;
}
