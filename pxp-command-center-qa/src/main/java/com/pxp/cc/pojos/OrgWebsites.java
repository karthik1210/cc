package com.pxp.cc.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class OrgWebsites {
    private String url;
    private String type;
    private Integer sortOrder;
    private Map<String, Object> displayText;
}
