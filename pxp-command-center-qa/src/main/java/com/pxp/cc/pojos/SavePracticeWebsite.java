package com.pxp.cc.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavePracticeWebsite {
    public int siteId;
    public String url;
    public String type;
    public int sortOrder;
    public DisplayText displayText;
    public boolean deleted;
}
