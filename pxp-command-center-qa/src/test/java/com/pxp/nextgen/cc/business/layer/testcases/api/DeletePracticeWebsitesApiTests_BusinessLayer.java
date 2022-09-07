package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.DisplayText;
import com.pxp.cc.pojos.SavePracticeWebsite;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class DeletePracticeWebsitesApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    Response deletePracticeWebsiteApiResponse,getPracticeWebsitesApiResponse;
    List<SavePracticeWebsite> savePracticeWebsite=new ArrayList<SavePracticeWebsite>();
    SavePracticeWebsite savePracticeWebsiteObj;
    DisplayText displayTextObj;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and inactive tou id")
    public void gdeletePracticeWebsiteApiSuccess() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites_BL("BL",12855);
        JsonPath js = new JsonPath(getPracticeWebsitesApiResponse);
        System.out.println("siteId"+js.getInt("siteId[0]"));
        int siteId= js.getInt("siteId[0]");
        String url= js.getString("url[0]");
        String englishDisplay="SampleTest234";
        String spanishDisplay="SampletestSpanish";
        displayTextObj=new DisplayText(englishDisplay,spanishDisplay);
        savePracticeWebsiteObj=new SavePracticeWebsite(siteId,url,"EXTERNAL_LINKS",2,displayTextObj,false);
        savePracticeWebsite.add(savePracticeWebsiteObj);
        deletePracticeWebsiteApiResponse = orgInfo.deletePracticeWebsitesWithValidPracticeIdHasWebsites("BL", 12855,savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePracticeWebsiteApiResponse);
        CCUtils.assertEquals(deletePracticeWebsiteApiResponse.getStatusCode(), 200);
    }
}
