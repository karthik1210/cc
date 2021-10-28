package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductStatus {
private String productId;
private String status;
private String statusDate;
private String statusNotes;

}
