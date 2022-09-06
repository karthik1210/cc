package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgWebsites;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPracticeWebsitesApiTests_BusinessLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response addPracticeWebsitesApiResponse;
    List<OrgWebsites> orgWebsites = new ArrayList<OrgWebsites>();
    Map<String, Object> displayText1 = new HashMap<String, Object>();
    Map<String, Object> displayText2 = new HashMap<String, Object>();
    OrgWebsites orgWebsites1;
    OrgWebsites orgWebsites2;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidPracticeId")
    public Object[][] invalidPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("Non.existing.practice.id").trim()},
                {testData.getProperty("api.client.id").trim()},
                {testData.getProperty("api.system.id").trim()},
                {testData.getProperty("api.enterprise.id").trim()},
                {null},
                {testData.getProperty("invalid.practice.id.tou").trim()}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "add multiple links for a practice which has external links earlier")
    public void addPracticeWebsitesWithPracticeIdHasExternalLinksWithMultipleExternalLinks() throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText2.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText2.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites2 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        orgWebsites.add(orgWebsites2);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks_BL(Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links")), orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "add a external link for a practice which has external links earlier")
    public void addPracticeWebsitesWithPracticeIdHasExternalLinks() throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks_BL(Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links")), orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 3, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "add a external link with invalid or null practice id")
    public void addPracticeWebsitesWithInvalidOrNullPracticeId(String practiceId) throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2)), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks_BL(practiceId, orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(addPracticeWebsitesApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "add a external link with valid practice id and  invalid URI")
    public void addPracticeWebsitesWithValidPracticeIdAndInvalidURI() throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2)), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsitesWithInvalidURI_BL(Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links")), orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(addPracticeWebsitesApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "add a external link without Practice Id")
    public void addPracticeWebsitesWithoutPracticeId() throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2)), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsitesWithoutPracticeId_BL(orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(addPracticeWebsitesApiResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "add a external link with valid practice id and  invalid request")
    public void addPracticeWebsitesWithValidPracticeIdAndInvalidRequest() throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2)), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsitesWithInvalidRequest_BL(Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links")), orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(addPracticeWebsitesApiResponse);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "add a external link for a practice which has external links earlier with null value on payload fields")
    public void addPracticeWebsitesWithPracticeIdHasExternalLinksWithNullPayload() throws IOException {
        orgWebsites1 = new OrgWebsites(null,null,null,null);
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks_BL(Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links")), orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(addPracticeWebsitesApiResponse);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "add a external link for a practice which has not external links earlier")
    public void addPracticeWebsitesWithPracticeIdHasNotExternalLinks() throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks_BL(Integer.parseInt(testData.getProperty("api.practice.website.practice.id.does.not.have.links")), orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "add multiple links for a practice which has not external links earlier")
    public void addPracticeWebsitesWithPracticeIdHasNotExternalLinksWithMultipleExternalLinks() throws IOException {
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText2.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText2.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites2 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.add(orgWebsites1);
        orgWebsites.add(orgWebsites2);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks_BL(Integer.parseInt(testData.getProperty("api.practice.website.practice.id.not.have.links")), orgWebsites);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeWebsitesApiResponse);
        CCUtils.assertEquals(addPracticeWebsitesApiResponse.getStatusCode(), 200);
    }
}
