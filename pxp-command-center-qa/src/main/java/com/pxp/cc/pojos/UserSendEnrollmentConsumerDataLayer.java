// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserSendEnrollmentConsumerDataLayer {
    public String enrollmentId;
    public String initiatedTs;
    public String expiryTs;
    public String status;
}