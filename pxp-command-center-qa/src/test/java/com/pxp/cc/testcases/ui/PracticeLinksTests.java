package com.pxp.cc.testcases.ui;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PracticeLinksTests extends CommandCenterGenericTest {
    private ClientDirectoryPage clientDirectoryPage;
    public ClientDirectoryPage cchomepage;
    private GeneralSettingsPage generalSettingsPage;
    public CreateClientPage createClientPage;
    public CommonMethods commonMethods;
    public AddOrgNodePage addOrgNodePage;
    String systemName, enterpriseName, clientName, practiceName;
    PracticeLinksPage practiceLinksPage;

    @BeforeMethod(alwaysRun = true)
    public void loginToCommandCenterPortal() throws Exception {
        logStep("Login to command center portal");
        cchomepage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
                testData.getProperty("user.password"));
        createClientPage = new CreateClientPage(driver);
        logStep("Select create client button from new dropdown");
        commonMethods = new CommonMethods(driver);
        createNewActiveClient();
        createNewActiveSystem();
        createNewActiveEnterprise();
        createNewActivePractice();
    }

    @Test(priority = 1, groups = {"smoke"},retryAnalyzer = RetryAnalyzer.class, description = "Verifying Respective label and other elements are present in UI or not")
    public void verifyPracticeLinkSectionAvailability() throws InterruptedException, AWTException {
        logStep("TC001 Verify that user can navigate to Practice Links section in Command Center Application");
        practiceLinksPage = new PracticeLinksPage(driver);
        logStep("Verify Practice Links section is displayed at practice level");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        logStep("TC002 Verify that user can collapse and expand Practice Links section in Command Center Application");
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyPracticeWebsiteCollapseArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteCollapseButton();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        logStep("TC007 Verify that whether user is able to see the EXTERNAL URLS in Practice Links section at practice level");
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlIsDisplayed();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteCollapseButton();
        logStep("TC008 Verify the External URLS by default 1st time in Practice Links section");
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlIsDisplayed();
        practiceLinksPage.verifyNoExternalUrlConfigureLabelIsDisplayed();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20288 Verify that user can add practice website in Practice Links section with Cancel and Save by using Edit button")
    public void verifyAddPracticeWebsiteWithCancelAndSave() throws InterruptedException, AWTException {
        logStep("TC003 Verify that user can add practice website in Practice Links section with Cancel and Save by using Edit button");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteLinkIconIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyPracticeWebsiteEditIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteEditButton();
        practiceLinksPage.enterPracticeWebsite("https://www."+practiceName+".com");
        practiceLinksPage.clickOnPracticeWebsiteCancelButton();
        practiceLinksPage.verifyPracticeWebsiteDisplayArea("https://www."+practiceName+".com");
        practiceLinksPage.clickOnPracticeWebsiteEditButton();
        practiceLinksPage.enterPracticeWebsite("https://www."+practiceName+".com");
        practiceLinksPage.clickOnPracticeWebsiteSaveButton();
        practiceLinksPage.verifyPracticeWebsiteDisplayArea("https://www."+practiceName+".com");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20291 Verify that whether user is able to edit and save the Practice website in Practice Links section at practice level")
    public void updateAndSavePracticeWebsite() throws InterruptedException, AWTException {
        logStep("TC005 Verify that whether user is able to edit and save the Practice website in Practice Links section at practice level");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.clickOnPracticeWebsiteEditButton();
        practiceLinksPage.enterPracticeWebsite("https://www."+practiceName+".com");
        practiceLinksPage.clickOnPracticeWebsiteSaveButton();
        practiceLinksPage.clickOnPracticeWebsiteEditButton();
        practiceLinksPage.updatePracticeWebsite("https://www."+practiceName+"update.com");
        practiceLinksPage.clickOnPracticeWebsiteSaveButton();
        practiceLinksPage.verifyPracticeWebsiteDisplayArea("https://www."+practiceName+"update.com");
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20330 Verify that whether user is able to edit and cancel the Practice website in Practice Links section at practice level")
    public void updateAndCancelPracticeWebsite() throws InterruptedException, AWTException {
        logStep("TC006 Verify that whether user is able to edit and cancel the Practice website in Practice Links section at practice level");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.clickOnPracticeWebsiteEditButton();
        practiceLinksPage.enterPracticeWebsite("https://www."+practiceName+".com");
        practiceLinksPage.clickOnPracticeWebsiteSaveButton();
        practiceLinksPage.clickOnPracticeWebsiteEditButton();
        practiceLinksPage.updatePracticeWebsite("https://www."+practiceName+"update.com");
        practiceLinksPage.clickOnPracticeWebsiteCancelButton();
        practiceLinksPage.verifyPracticeWebsiteDisplayArea("https://www."+practiceName+"update.com");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20290 Verify that user can't add more than one practice website in Practice Links section in Command Center Application")
    public void verifyMoreThanOnePracticeWebsiteCannotAdded() throws InterruptedException, AWTException {
        logStep("TC004 Verify that user can't add more than one practice website in Practice Links section in Command Center Application");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.checkPracticeWebsiteIsEmptyOrNot();
        practiceLinksPage.clickOnPracticeWebsiteEditButton();
        practiceLinksPage.enterPracticeWebsite("https://www."+practiceName+".com");
        practiceLinksPage.clickOnPracticeWebsiteSaveButton();
        practiceLinksPage.checkPracticeWebsiteIsEmptyOrNot();
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20463 Verify the Edit and Cancel functionality for External URLS in Practice Links section for the 1st time")
    public void verifyEditAndCancelForExternalUrl() throws InterruptedException, AWTException {
        logStep("TC009 Verify the Edit and Cancel functionality for External URLS in Practice Links section for the 1st time");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        List<String> externalWebsite = new ArrayList<String>();
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlCancelButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20347 Verify the Edit and Save functionality for External URLs in Practice Links section for the 1st time")
    public void verifyEditAndSaveForExternalUrl() throws InterruptedException, AWTException {
        logStep("TC010 Verify the Edit and Save functionality for External URLs in Practice Links section for the 1st time");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        List<String> externalWebsite = new ArrayList<String>();
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20465 Verify the Edit and Save functionality for multiple External URLs in Practice Links section")
    public void verifyEditAndSaveWithMultipleExternalUrl() throws InterruptedException, AWTException {
        logStep("TC011 Verify the Edit and Save functionality for multiple External URLs in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20464 Verify that user able to add more than 10 links for EXTERNAL URLS in Practice Links section")
    public void verifyEditAndSaveWith10PlusExternalUrl() throws InterruptedException, AWTException {
        logStep("TC012 Verify that user able to add more than 10 links for EXTERNAL URLS in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN4"),testData.getProperty("PXPP.17867.external.url.ES4"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN4").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(3));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN5"),testData.getProperty("PXPP.17867.external.url.ES5"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN5").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(4));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN6"),testData.getProperty("PXPP.17867.external.url.ES6"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN6").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(5));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN7"),testData.getProperty("PXPP.17867.external.url.ES7"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN7").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(6));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN8"),testData.getProperty("PXPP.17867.external.url.ES8"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN8").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(7));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN9"),testData.getProperty("PXPP.17867.external.url.ES9"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN9").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(8));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN10"),testData.getProperty("PXPP.17867.external.url.ES10"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN10").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(9));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN11"),testData.getProperty("PXPP.17867.external.url.ES11"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN11").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(10));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN12"),testData.getProperty("PXPP.17867.external.url.ES12"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN12").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(11));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20475 Verify that user able to drag and drop the EXTERNAL URLs by using Rearrange functionality in Practice Links section")
    public void verifyDragAndDropInExternalUrl() throws InterruptedException, AWTException {
        logStep("TC014 Verify that user able to drag and drop the EXTERNAL URLs by using Rearrange functionality in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        commonMethods = new CommonMethods(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN4"),testData.getProperty("PXPP.17867.external.url.ES4"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN4").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(3));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN5"),testData.getProperty("PXPP.17867.external.url.ES5"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN5").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(4));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN6"),testData.getProperty("PXPP.17867.external.url.ES6"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN6").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(5));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN7"),testData.getProperty("PXPP.17867.external.url.ES7"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN7").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(6));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN8"),testData.getProperty("PXPP.17867.external.url.ES8"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN8").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(7));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN9"),testData.getProperty("PXPP.17867.external.url.ES9"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN9").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(8));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN10"),testData.getProperty("PXPP.17867.external.url.ES10"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN10").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(9));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN11"),testData.getProperty("PXPP.17867.external.url.ES11"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN11").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(10));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN12"),testData.getProperty("PXPP.17867.external.url.ES12"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN12").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(11));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        commonMethods.verifyToasterMessage("Success! External URLs saved successfully");
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
        practiceLinksPage.printListOfExternalUrls();
        practiceLinksPage.clickOnExternalUrlRearrangeButton();
        practiceLinksPage.dragAndDropExternalUrl(1,2);
        practiceLinksPage.clickOnExternalUrlSaveButton();
        commonMethods.verifyToasterMessage("Success! Sorted External URLs saved successfully");
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
        practiceLinksPage.printListOfExternalUrls();
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20476 Verify the delete functionality the EXTERNAL URLs with Cancel functionality")
    public void deleteAndCancelExternalUrl() throws InterruptedException, AWTException {
        logStep("TC015 Verify the delete functionality the EXTERNAL URLs with Cancel functionality");
        practiceLinksPage = new PracticeLinksPage(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
        practiceLinksPage.deleteExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlCancelButton();
        practiceLinksPage.printListOfExternalUrls();
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20477 Verify the delete functionality the EXTERNAL URLS with Save")
    public void deleteAndSaveExternalUrl() throws InterruptedException, AWTException {
        logStep("TC016 Verify the delete functionality the EXTERNAL URLS with Save");
        practiceLinksPage = new PracticeLinksPage(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
        practiceLinksPage.deleteExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        commonMethods.verifyToasterMessage("Success! External URLs saved successfully");
        practiceLinksPage.printListOfExternalUrls();
    }

    @Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20478 Verify the Length of the Display Text is allowed only 100 characters for the EXTERNAL URLs ")
    public void verify100LengthForExternalUrl() throws InterruptedException, AWTException {
        logStep("TC017 Verify the Length of the Display Text is allowed only 100 characters for the EXTERNAL URLs ");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.verifyExternalUrlDeleteIconIsDisplayed();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.100.length.text"),testData.getProperty("PXPP.17867.external.url.100.length.text"));
        practiceLinksPage.enterExternalUrl(testData.getProperty("PXPP.17867.external.url.100.length.url"));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        commonMethods.verifyToasterMessage("Success! External URLs saved successfully");
        practiceLinksPage.printListOfExternalUrls();
    }

    @Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20479 Verify that Application should thrown error when user entered 101 characters for the Display Text in EXTERNAL URLs at Practice Links section")
    public void verify101LengthOnExternalUrl() throws InterruptedException, AWTException {
        logStep("TC018 Verify that Application should thrown error when user entered 101 characters for the Display Text in EXTERNAL URLs at Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.verifyExternalUrlDeleteIconIsDisplayed();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.101.length.text"),testData.getProperty("PXPP.17867.external.url.101.length.text"));
        practiceLinksPage.verifyMaximumCharacterError();
    }

    @Test(priority = 16, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20481 Verify the Edit and Save functionality for existing External URLs in Practice Links section")
    public void verifyEditAndSaveExistingExternalUrl() throws InterruptedException, AWTException {
        logStep("TC020 Verify the Edit and Save functionality for existing External URLs in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
        practiceLinksPage.printListOfExternalUrls();
        practiceLinksPage.printListOfDisplayText();
        practiceLinksPage.enterValueOnExistingValueOnDisplayText(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.enterValueOnExistingValueOnExternalUrl(testData.getProperty("PXPP.17867.external.url.EN1"));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        commonMethods.verifyToasterMessage("Success! External URLs saved successfully");
        practiceLinksPage.printListOfExternalUrls();
        practiceLinksPage.printListOfDisplayText();
    }

    @Test(priority = 15, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20480 Verify the Edit and Cancel functionality for existing External URLs in Practice Links section")
    public void verifyEditAndCancelExistingExternalUrl() throws InterruptedException, AWTException {
        logStep("TC019 Verify the Edit and Cancel functionality for existing External URLs in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"),testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN1").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"),testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN2").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN3"),testData.getProperty("PXPP.17867.external.url.ES3"));
        externalWebsite.add("https://www."+testData.getProperty("PXPP.17867.external.url.EN3").substring(0,4).concat(CCUtils.generateAlphaNumericString(3))+".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        practiceLinksPage.verifyExternalUrlDisplay(externalWebsite);
        practiceLinksPage.printListOfExternalUrls();
        practiceLinksPage.printListOfDisplayText();
        practiceLinksPage.enterValueOnExistingValueOnDisplayText(externalWebsite.get(2));
        practiceLinksPage.clickOnExternalUrlCancelButton();
        practiceLinksPage.enterValueOnExistingValueOnExternalUrl(testData.getProperty("PXPP.17867.external.url.EN1"));
        practiceLinksPage.clickOnExternalUrlCancelButton();
        practiceLinksPage.printListOfExternalUrls();
        practiceLinksPage.printListOfDisplayText();
    }

    @Test(priority = 17, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20482 Verify the Social Media by default 1st time in Practice Links section")
    public void verifyElementsOfSocialMediaForFirstTime() throws InterruptedException, AWTException {
        logStep("TC021 Verify the Social Media by default 1st time in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.verifyTwitterLabel();
        practiceLinksPage.verifyFacebookLabel();
        practiceLinksPage.verifyLinkedinLabel();
        practiceLinksPage.verifyYoutubeLabel();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
    }

    @Test(priority = 18, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20484 Verify the Edit and Cancel functionality for Social Media in Practice Links section for the 1st time")
    public void verifyEditAndCancelOnSocialMedia() throws InterruptedException, AWTException {
        logStep("TC022 Verify the Edit and Cancel functionality for Social Media in Practice Links section for the 1st time");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.verifyTwitterLabel();
        practiceLinksPage.verifyFacebookLabel();
        practiceLinksPage.verifyLinkedinLabel();
        practiceLinksPage.verifyYoutubeLabel();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        practiceLinksPage.clickOnSocialMediaCancel();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
    }

    @Test(priority = 19, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20483 Verify the Edit and Save functionality for social media in Practice Links section for the 1st time")
    public void verifyEditAndSaveOnSocialMedia() throws InterruptedException, AWTException {
        logStep("TC023 Verify the Edit and Save functionality for social media in Practice Links section for the 1st time");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.verifyTwitterLabel();
        practiceLinksPage.verifyFacebookLabel();
        practiceLinksPage.verifyLinkedinLabel();
        practiceLinksPage.verifyYoutubeLabel();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        practiceLinksPage.clickOnSocialMediaSave();
        practiceLinksPage.printSocialMediaUrl();
    }

    @Test(priority = 20, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20485 Verify the Toggle buttons for social media in Practice Links section")
    public void verifyToggleSectionOnSocialMedia() throws InterruptedException, AWTException {
        logStep("TC024 Verify the Toggle buttons for social media in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
        practiceLinksPage.clickOnSocialMediaCancel();
        practiceLinksPage.verifySocialMediaUrlEmpty();
    }

    @Test(priority = 21, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20486 Verify the Edit and Cancel functionality for multiple social media with Toggle on/off in Practice Links section")
    public void verifyMultipleToggleSectionWithCancelOnSocialMedia() throws InterruptedException, AWTException {
        logStep("TC025 Verify the Edit and Cancel functionality for multiple social media with Toggle on/off in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        practiceLinksPage.toggleSwitchClick("Twitter");
        practiceLinksPage.enterValueOnSocialMedia("Twitter", "https://www.twitter.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        practiceLinksPage.toggleSwitchClick("Linkedin");
        practiceLinksPage.enterValueOnSocialMedia("Linkedin", "https://www.linkedin.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        practiceLinksPage.toggleSwitchClick("Youtube");
        practiceLinksPage.enterValueOnSocialMedia("Youtube", "https://www.youtube.com/"+testData.getProperty("PXPP.17867.external.url.EN3"));
        practiceLinksPage.clickOnSocialMediaCancel();
        Thread.sleep(3000);
        practiceLinksPage.verifySocialMediaUrlEmpty();
    }

    @Test(priority = 22, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20487 Verify the Edit and Save functionality for multiple social media with Toggle on/off in Practice Links section")
    public void verifyMultipleToggleSectionWithSaveOnSocialMedia() throws InterruptedException, AWTException {
        logStep("TC026 Verify the Edit and Save functionality for multiple social media with Toggle on/off in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Twitter");
        practiceLinksPage.enterValueOnSocialMedia("Twitter", "https://www.twitter.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Linkedin");
        practiceLinksPage.enterValueOnSocialMedia("Linkedin", "https://www.linkedin.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Youtube");
        practiceLinksPage.enterValueOnSocialMedia("Youtube", "https://www.youtube.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.clickOnSocialMediaSave();
        commonMethods.verifyToasterMessage("Successfully Updated Social Media.");
        Thread.sleep(3000);
        practiceLinksPage.printSocialMediaUrl();
    }

    @Test(priority = 23, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20488 Verify the Edit and Cancel functionality for updating existing social media with Toggle on/off in Practice Links section")
    public void verifyUpdateMultipleToggleSectionWithCancelOnSocialMedia() throws InterruptedException, AWTException {
        logStep("TC027 Verify the Edit and Cancel functionality for updating existing social media with Toggle on/off in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Twitter");
        practiceLinksPage.enterValueOnSocialMedia("Twitter", "https://www.twitter.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Linkedin");
        practiceLinksPage.enterValueOnSocialMedia("Linkedin", "https://www.linkedin.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Youtube");
        practiceLinksPage.enterValueOnSocialMedia("Youtube", "https://www.youtube.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.clickOnSocialMediaSave();
        commonMethods.verifyToasterMessage("Successfully Updated Social Media.");
        Thread.sleep(3000);
        practiceLinksPage.printSocialMediaUrl();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.updateValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.clickOnSocialMediaCancel();
        Thread.sleep(3000);
        practiceLinksPage.printSocialMediaUrl();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.clickOnSocialMediaCancel();
        practiceLinksPage.printSocialMediaUrl();
    }

    @Test(priority = 24, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20489 Verify the Edit and Save functionality for updating existing social media with Toggle on/off in Practice Links section")
    public void verifyUpdateMultipleToggleSectionWithSaveOnSocialMedia() throws InterruptedException, AWTException {
        logStep("TC028 Verify the Edit and Save functionality for updating existing social media with Toggle on/off in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.verifyToggleOff();
        practiceLinksPage.verifyEditTextBoxUrlEmpty();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.enterValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Twitter");
        practiceLinksPage.enterValueOnSocialMedia("Twitter", "https://www.twitter.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Linkedin");
        practiceLinksPage.enterValueOnSocialMedia("Linkedin", "https://www.linkedin.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.toggleSwitchClick("Youtube");
        practiceLinksPage.enterValueOnSocialMedia("Youtube", "https://www.youtube.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.clickOnSocialMediaSave();
        commonMethods.verifyToasterMessage("Successfully Updated Social Media.");
        Thread.sleep(3000);
        practiceLinksPage.printSocialMediaUrl();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.updateValueOnSocialMedia("Facebook", "https://www.facebook.com/"+testData.getProperty("PXPP.17867.external.url.EN3").replaceAll("\\s", ""));
        practiceLinksPage.clickOnSocialMediaSave();
        commonMethods.verifyToasterMessage("Successfully Updated Social Media.");
        Thread.sleep(3000);
        practiceLinksPage.printSocialMediaUrl();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.toggleSwitchClick("Facebook");
        practiceLinksPage.clickOnSocialMediaSave();
        commonMethods.verifyToasterMessage("Successfully Updated Social Media.");
        practiceLinksPage.printSocialMediaUrl();
    }

    @Test(priority = 25, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20490 Verify the Error message for social media URL text box, when its empty")
    public void verifySocialMediaErrorMessage() throws InterruptedException, AWTException {
        logStep("TC028 Verify the Edit and Save functionality for updating existing social media with Toggle on/off in Practice Links section");
        practiceLinksPage = new PracticeLinksPage(driver);
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifySocialMediaLabel();
        practiceLinksPage.verifySocialMediaEditButton();
        practiceLinksPage.clickOnSocialMediaEdit();
        practiceLinksPage.toggleSwitchClick("Facebook");
        Thread.sleep(3000);
        practiceLinksPage.verifySocialMediaError();
    }

    @Test(priority = 26, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20492 TC030 Verify the Rearrange button Functionality for EXTERNAL URLS")
    public void verifyRearrangeButtonInExternalUrl() throws InterruptedException, AWTException {
        logStep("TC030 Verify the Rearrange button Functionality for EXTERNAL URLS");
        practiceLinksPage = new PracticeLinksPage(driver);
        commonMethods = new CommonMethods(driver);
        List<String> externalWebsite = new ArrayList<String>();
        practiceLinksPage.verifyPracticeLinksLabelIsDisplayed();
        practiceLinksPage.verifyRearrangeButtonOnExternalUrl();
        practiceLinksPage.verifyPracticeWebsiteExpandArrowIsDisplayed();
        practiceLinksPage.clickOnPracticeWebsiteExpandButton();
        practiceLinksPage.verifyExternalUrlEditIsDisplayed();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.verifyRearrangeButtonOnExternalUrl();
        practiceLinksPage.verifyExternalUrlAddIsDisplayed();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN1"), testData.getProperty("PXPP.17867.external.url.ES1"));
        externalWebsite.add("https://www." + testData.getProperty("PXPP.17867.external.url.EN1").substring(0, 4).concat(CCUtils.generateAlphaNumericString(3)) + ".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(0));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        commonMethods.verifyToasterMessage("Success! External URLs saved successfully");
        practiceLinksPage.verifyRearrangeButtonOnExternalUrl();
        practiceLinksPage.clickOnExternalUrlEditButton();
        practiceLinksPage.clickOnExternalUrlAddButton();
        practiceLinksPage.enterExternalUrlHyperLinkText(testData.getProperty("PXPP.17867.external.url.EN2"), testData.getProperty("PXPP.17867.external.url.ES2"));
        externalWebsite.add("https://www." + testData.getProperty("PXPP.17867.external.url.EN2").substring(0, 4).concat(CCUtils.generateAlphaNumericString(3)) + ".org/us");
        practiceLinksPage.enterExternalUrl(externalWebsite.get(1));
        practiceLinksPage.clickOnExternalUrlSaveButton();
        commonMethods.verifyToasterMessage("Success! External URLs saved successfully");
        practiceLinksPage.verifyRearrangeButtonOnExternalUrl();
    }

    public void createNewActiveClient() throws Exception {
        createClientPage = new CreateClientPage(driver);
        logStep("Select create client button from new dropdown");
        createClientPage.clickCreateClient();
        commonMethods = new CommonMethods(driver);
        logStep("Enter valid details in mandatory fields");
        clientName = testData.getProperty("new.client.name2")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
        String clientSapNumber = testData.getProperty("new.client.sap")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
        createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
                testData.getProperty("new.client.timezone"));
        logStep("Verify active radio button is by default selected on the create client popup");
        createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on create button on the create client popup");
        createClientPage.clickConfirmPopup();
        String actualMessage = commonMethods.getToasterMessage();
        logStep("Verify success toaster message");
        CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        logStep("Searching a client in client directory");
        clientDirectoryPage.searchNode(clientName);
        clientDirectoryPage.clickSearchButton();
        clientDirectoryPage.clickSearchResult();
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        generalSettingsPage.verifyClientNodeIsSelected();
    }

    public void createNewActiveSystem() throws NullPointerException, InterruptedException {
        addOrgNodePage = new AddOrgNodePage(driver);
        logStep("Verify manage client button is displayed");
        addOrgNodePage.verifyManageClientButton();
        logStep("Click on manage client button");
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.verifyAddSystemButton();
        addOrgNodePage.clickOnAddSystem();
        logStep("Enter data in all input fields");
        systemName = CCUtils.generateAlphaNumericString(18);
        addOrgNodePage.enterSystemName(systemName);
        addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
        addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }

    public void createNewActiveEnterprise() throws NullPointerException, InterruptedException {
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        logStep("Verify general settings page title");
        generalSettingsPage.verifyGeneralSettingsPageTitle();
        addOrgNodePage = new AddOrgNodePage(driver);
        logStep("Verify manage client button is displayed");
        addOrgNodePage.verifyManageClientButton();
        logStep("Click on manage client button");
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnExpandArrow();
        logStep("Verify add new Enterprise page");
        driver.navigate().refresh();
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnExpandArrow();
        addOrgNodePage.verifyAddEnterpriseButton();
        addOrgNodePage.clickOnAddEnterprise();
        logStep("Enter data in all input fields");
        enterpriseName = CCUtils.generateAlphaNumericString(18);
        addOrgNodePage.enterEnterpriseName(enterpriseName);
        addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
        addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }

    public void createNewActivePractice() throws NullPointerException, InterruptedException {
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        addOrgNodePage = new AddOrgNodePage(driver);
        logStep("Verify manage client button is displayed");
        addOrgNodePage.verifyManageClientButton();
        logStep("Click on manage client button");
        addOrgNodePage.clickOnManageClient();
        logStep("Click on arrow");
        addOrgNodePage.clickOnSystemExpandArrow();
        driver.navigate().refresh();
        logStep("Click on manage client button");
        generalSettingsPage = new GeneralSettingsPage(driver);
        generalSettingsPage.clickSearchIcon();
        logStep("Searching a node under client in general settings page");
        generalSettingsPage.enterSearchText(enterpriseName);
        generalSettingsPage.clickFirstInAutoSuggestion();
        CCUtils.pressEnterKey();
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnSystemExpandArrow();
        addOrgNodePage.clickOnEnterpriseExpandArrow();
        logStep("Verify add new practice page");
        addOrgNodePage.verifyAddPracticeButton();
        addOrgNodePage.clickOnAddPracticeButton();
        logStep("Enter data in all input fields");
        practiceName = CCUtils.generateAlphaNumericString(18);
        addOrgNodePage.enterPracticeName(practiceName);
        addOrgNodePage.addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
                testData.getProperty("new.system"));
        addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
        addOrgNodePage.enterSolutionsDisplayName(testData.getProperty("solution.forms"));
        addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }
}
