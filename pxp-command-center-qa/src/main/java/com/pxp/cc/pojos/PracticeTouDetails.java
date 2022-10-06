// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class PracticeTouDetails {
    public String name;
    public String status;
    public ArrayList<TouSolutions> solutions;
    public String touType;
    public DisplayText content;
}
