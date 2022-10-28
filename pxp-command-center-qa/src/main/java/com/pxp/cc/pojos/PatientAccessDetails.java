// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class PatientAccessDetails {
    public PatientAccessSettings settings;
    public PatientAccessAgeout ageout;
    public ArrayList<PatientAccessPermission> permissions;
}
