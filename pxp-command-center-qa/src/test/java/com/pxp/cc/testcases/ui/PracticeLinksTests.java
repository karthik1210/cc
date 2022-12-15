// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class PracticeLinksTests extends CommandCenterGenericTest {

    public boolean runOnce = true;
    public CCUI ccui;

    @BeforeMethod(alwaysRun = true)
    public void loginToCommandCenterPortal() throws Exception {
        if (runOnce) {
            ccui = new CCUI(testData, driver);
            logStep("Login to command center portal");
            ccui.getClientDirectoryPage();
            ccui.initializeTestData();
            getClientSystementErpriseandPractice();
        }
    }

    @Test(priority = 5, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verifying Smoke functionality of Practice Links")
    public void verifyBasicPracticeLinks() throws Exception {
        ngHierarchyClientCreate();
        logStep("Smoke Validation for Practice Links");
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteExpandButton();
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteEditButton();
        ccui.getPracticeLinksPage().enterPracticeWebsite("https://www."+clientSystementErpriseandPractice.get("practiceName")+".com");
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteSaveButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        List<String> externalWebsite = new ArrayList<String>();
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(0));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().toggleSwitchClick("facebook");
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        ccui.getPracticeLinksPage().clickOnSocialMediaSave();
        ccui.getCommonMethods().verifyToasterMessage("Successfully Updated Social Media.");
        runOnce = false;
    }

    @Test(priority = 1,retryAnalyzer = RetryAnalyzer.class, description = "Verifying Respective label and other elements are present in UI or not with Cancel functionality and basic functionality check")
    public void verifyPracticeLinkSectionAvailability() throws Exception {
        ngHierarchyClientCreate();
        HashMap<String, Boolean> verification = new HashMap<String, Boolean>();
        verification = ccui.getPracticeLinksPage().verifyPracticeLinksElements();
        runOnce = false;
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verifying Basic functionality of Practice Links")
    public void verifyBasicFunctionalityOfPracticeLinks() throws Exception {
        ngHierarchyClientCreate();
        logStep("PXPP-20288 TC003 Verify that user can add practice website in Practice Links section with Cancel and Save by using Edit button");
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteExpandButton();
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteEditButton();
        ccui.getPracticeLinksPage().enterPracticeWebsite("https://www."+clientSystementErpriseandPractice.get("practiceName")+".com");
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteCancelButton();
        ccui.getPracticeLinksPage().verifyPracticeWebsiteDisplayArea("https://www."+clientSystementErpriseandPractice.get("practiceName")+".com");
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteEditButton();
        ccui.getPracticeLinksPage().enterPracticeWebsite("https://www."+clientSystementErpriseandPractice.get("practiceName")+".com");
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteSaveButton();
        ccui.getPracticeLinksPage().verifyPracticeWebsiteDisplayArea("https://www."+clientSystementErpriseandPractice.get("practiceName")+".com");
        logStep("PXPP-20330 TC006 Verify that whether user is able to update and cancel the Practice website in Practice Links section at practice level");
        ccui.getPracticeLinksPage().updateWithActionForPracticeWebsite("https://www."+clientSystementErpriseandPractice.get("practiceName")+"update.com", "Cancel");
        ccui.getPracticeLinksPage().verifyPracticeWebsiteDisplayArea("https://www."+clientSystementErpriseandPractice.get("practiceName")+"update.com");
        logStep("PXPP-20291 TC005 Verify that whether user is able to update and save the Practice website in Practice Links section at practice level");
        ccui.getPracticeLinksPage().updateWithActionForPracticeWebsite("https://www."+clientSystementErpriseandPractice.get("practiceName")+"update.com", "Save");
        ccui.getPracticeLinksPage().verifyPracticeWebsiteDisplayArea("https://www."+clientSystementErpriseandPractice.get("practiceName")+"update.com");
        logStep("PXPP-20290 TC004 Verify that user can't add more than one practice website in Practice Links section in Command Center Application");
        ccui.getPracticeLinksPage().checkPracticeWebsiteIsEmptyOrNot();
        logStep("PXPP-20463 TC009 Verify the Edit and Cancel functionality for External URLS in Practice Links section for the 1st time");
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        List<String> externalWebsite = new ArrayList<String>();
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(0));
        ccui.getPracticeLinksPage().clickOnExternalUrlCancelButton();
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        logStep("PXPP-20480 TC019 Verify the Edit and Cancel functionality for existing External URLs in Practice Links section");
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(0));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(1));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(2));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        logStep("PXPP-20484 TC022 Verify the Edit and Cancel functionality for Social Media in Practice Links section for the 1st time");
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().verifyToggleOff();
        ccui.getPracticeLinksPage().verifyEditTextBoxUrlEmpty();
        ccui.getPracticeLinksPage().toggleSwitchClick("Facebook");
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        ccui.getPracticeLinksPage().clickOnSocialMediaCancel();
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().verifyEditTextBoxUrlEmpty();
        logStep("20486 PXPP-TC025 Verify the Edit and Cancel functionality for multiple social media with Toggle on/off in Practice Links section");
        ccui.getPracticeLinksPage().toggleAllSocial();
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Twitter", "https://www.twitter.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Linkedin", "https://www.linkedin.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Youtube", "https://www.youtube.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        ccui.getPracticeLinksPage().clickOnSocialMediaCancel();
        ccui.getPracticeLinksPage().verifySocialMediaUrlEmpty();
        runOnce = false;
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Verify the Edit and Save functionality for External URLs in Practice Links section")
    public void verifyEditAndSaveForExternalUrl() throws Exception {
        ngHierarchyClientCreate();
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteExpandButton();
        logStep("PXPP-20478 TC017 Verify the Length of the Display Text is allowed only 100 characters for the EXTERNAL URLs ");
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.100.length.text"),testData.getProperty("PXPP.17867.external.url.100.length.text"));
        ccui.getPracticeLinksPage().enterExternalUrl(testData.getProperty("PXPP.17867.external.url.100.length.url"));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        logStep("20479 TC018 Verify that Application should thrown error when user entered 101 characters for the Display Text in EXTERNAL URLs at Practice Links section");
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.101.length.text"),testData.getProperty("PXPP.17867.external.url.101.length.text"));
        ccui.getPracticeLinksPage().verifyMaximumCharacterError();
        ccui.getPracticeLinksPage().clickOnExternalUrlCancelButton();
        logStep("PXPP-20492 TC030 Verify the Rearrange button Functionality for EXTERNAL URLS");
        logStep("PXPP-20347 TC010 Verify the Edit and Save functionality for External URLs in Practice Links section for the 1st time");
        List<String> externalWebsite = new ArrayList<String>();
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().verifyRearrangeButtonOnExternalUrl();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"), testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www." + testData.getProperty("PXPP.17867.external.url.EN1").substring(0, 4).concat(CCUtils.generateAlphaNumericString(3)) + ".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(0));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().verifyRearrangeButtonOnExternalUrl();
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"), testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www." + testData.getProperty("PXPP.17867.external.url.EN2").substring(0, 4).concat(CCUtils.generateAlphaNumericString(3)) + ".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(1));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().verifyRearrangeButtonOnExternalUrl();
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(2));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN4"),testData.getProperty("PXPP.17867.external.url.ES4"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN4").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(3));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        logStep("PXPP-20464 TC012 Verify that user able to add more than 10 links for EXTERNAL URLS in Practice Links section");
        ccui.getPracticeLinksPage().clickOnExternalUrlEditButton();
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN5"),testData.getProperty("PXPP.17867.external.url.ES5"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN5").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(4));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN6"),testData.getProperty("PXPP.17867.external.url.ES6"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN6").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(5));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN7"),testData.getProperty("PXPP.17867.external.url.ES7"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN7").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(6));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN8"),testData.getProperty("PXPP.17867.external.url.ES8"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN8").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(7));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN9"),testData.getProperty("PXPP.17867.external.url.ES9"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN9").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(8));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN10"),testData.getProperty("PXPP.17867.external.url.ES10"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN10").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(9));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN11"),testData.getProperty("PXPP.17867.external.url.ES11"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN11").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(10));
        ccui.getPracticeLinksPage().clickOnExternalUrlAddButton();
        ccui.getPracticeLinksPage().enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN12"),testData.getProperty("PXPP.17867.external.url.ES12"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN12").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        ccui.getPracticeLinksPage().enterExternalUrl(externalWebsite.get(11));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        logStep("20475 TC014 Verify that user able to drag and drop the EXTERNAL URLs by using Rearrange functionality in Practice Links section");
        ccui.getPracticeLinksPage().printListOfExternalUrls();
        ccui.getPracticeLinksPage().clickOnExternalUrlRearrangeButton();
        ccui.getPracticeLinksPage().dragAndDropExternalUrl(1,2);
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! Sorted External URLs saved successfully");
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        ccui.getPracticeLinksPage().printListOfExternalUrls();
        logStep("PXPP-20476 TC015 Verify the delete functionality the EXTERNAL URLs with Cancel functionality");
        ccui.getPracticeLinksPage().deleteExternalUrl(externalWebsite.get(1));
        ccui.getPracticeLinksPage().clickOnExternalUrlCancelButton();
        ccui.getPracticeLinksPage().printListOfExternalUrls();
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        logStep("PXPP-20477 TC016 Verify the delete functionality the EXTERNAL URLS with Save");
        ccui.getPracticeLinksPage().deleteExternalUrl(externalWebsite.get(1));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().printListOfExternalUrls();
        ccui.getPracticeLinksPage().verifyExternalUrlDisplay(externalWebsite);
        logStep("PXPP-20481 TC020 Verify the Edit and Save functionality for existing External URLs in Practice Links section");
        ccui.getPracticeLinksPage().printListOfExternalUrls();
        ccui.getPracticeLinksPage().printListOfDisplayText();
        ccui.getPracticeLinksPage().enterValueOnExistingValueOnDisplayText(externalWebsite.get(2));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().printListOfExternalUrls();
        ccui.getPracticeLinksPage().printListOfDisplayText();
        ccui.getPracticeLinksPage().enterValueOnExistingValueOnExternalUrl(testData.getProperty("PXPP.17867.external.url.EN1"));
        ccui.getPracticeLinksPage().clickOnExternalUrlSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! External URLs saved successfully");
        ccui.getPracticeLinksPage().printListOfExternalUrls();
        ccui.getPracticeLinksPage().printListOfDisplayText();
        runOnce = false;
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify the Edit and Save functionality for social media in Practice Links section")
    public void verifyEditAndSaveOnSocialMedia() throws Exception {
        ngHierarchyClientCreate();
        logStep("PXPP-20483 TC023 Verify the Edit and Save functionality for social media in Practice Links section for the 1st time");
        ccui.getPracticeLinksPage().clickOnPracticeWebsiteExpandButton();
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().toggleSwitchClick("Facebook");
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s",""));
        ccui.getPracticeLinksPage().clickOnSocialMediaSave();
        ccui.getPracticeLinksPage().printSocialMediaUrl();
        logStep("PXPP-20487 TC026 Verify the Edit and Save functionality for multiple social media with Toggle on/off in Practice Links section");
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().toggleSwitchClick("Twitter");
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Twitter", "https://www.twitter.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        ccui.getPracticeLinksPage().toggleSwitchClick("Linkedin");
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Linkedin", "https://www.linkedin.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        ccui.getPracticeLinksPage().toggleSwitchClick("Youtube");
        ccui.getPracticeLinksPage().enterValueOnSocialMedia("Youtube", "https://www.youtube.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        ccui.getPracticeLinksPage().clickOnSocialMediaSave();
        ccui.getCommonMethods().verifyToasterMessage("Successfully Updated Social Media.");
        ccui.getPracticeLinksPage().printSocialMediaUrl();
        logStep("PXPP-20488 TC027 Verify the Edit and Cancel functionality for updating existing social media with Toggle on/off in Practice Links section");
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().updateValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        ccui.getPracticeLinksPage().clickOnSocialMediaCancel();
        ccui.getPracticeLinksPage().printSocialMediaUrl();
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().toggleSwitchClick("Facebook");
        ccui.getPracticeLinksPage().clickOnSocialMediaCancel();
        ccui.getPracticeLinksPage().printSocialMediaUrl();
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().updateValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        ccui.getPracticeLinksPage().clickOnSocialMediaSave();
        ccui.getCommonMethods().verifyToasterMessage("Successfully Updated Social Media.");
        ccui.getPracticeLinksPage().printSocialMediaUrl();
        ccui.getPracticeLinksPage().clickOnSocialMediaEdit();
        ccui.getPracticeLinksPage().toggleSwitchClick("Facebook");
        ccui.getPracticeLinksPage().clickOnSocialMediaSave();
        ccui.getCommonMethods().verifyToasterMessage("Successfully Updated Social Media.");
        ccui.getPracticeLinksPage().printSocialMediaUrl();
        runOnce = false;
    }

    @Test(enabled = false)
    public void ngHierarchyClientCreate() throws Exception {
        if (!runOnce) {
            ccui = new CCUI(testData, driver);
            logStep("Login to command center portal");
            LoginPage loginPage = new LoginPage(driver, testData.getProperty("base.url"));
            loginPage.login(testData.getProperty("user.name"), testData.getProperty("user.password"));
            logStep("Select create client button from new dropdown");
            logStep("Searching a client in client directory");
            ccui.getNgHierarchyClientCreationPage().searchNode(clientSystementErpriseandPractice.get("clientNam"));
            ccui.getNgHierarchyClientCreationPage().clickSearchButton();
            ccui.getNgHierarchyClientCreationPage().clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
            logStep("Navigating To General Settings page");
            ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
            ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
            ccui.getPrivacyPolicySettingsPage().clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
            ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
        }
    }
}
