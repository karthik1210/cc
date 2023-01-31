// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.model.CCUser;
import com.pxp.cc.pojos.UserSendEnrollmentConsumerDataLayer;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class UserBulkEnrollmentTests extends CommandCenterGenericTest {

    public CCUI ccui;
    boolean runOnce = true;
    boolean enabled;
    HashMap<String, Boolean> verification = new HashMap<String, Boolean>();
    static String internalFirstName, varResellerFirstName, firstName;
    private UserInfo userInfo;
    Response createUserApiResponse,
            sendUserEnrollmentConsumerResponse, updatePasswordDuringEnrollmentApiResponse, updateExpiredStatusApiResponse;
    HashMap<String, Object> userMap = new HashMap<String, Object>();
    static int userId;
    String userType1;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    Date date1 = new Date(System.currentTimeMillis() - 2 * 60 * 60 * 1000);
    Date date2 = new Date(System.currentTimeMillis() - 26 * 60 * 60 * 1000);
    String currentTs = dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)));
    String expiryTs = dateFormat.format(new Date(date1.getTime() + (1000 * 60 * 60 * 24)));
    String initiatedTs = dateFormat.format(new Date(date2.getTime() + (1000 * 60 * 60 * 24)));
    static List<String> enrollmentStatus = new ArrayList<String>();
    static BaseRest baseRest;
    public CCUser ccUser;

    @BeforeClass
    public void setBaseUri() throws Exception {
        testData = new PropertyFileLoader();
        log("Setting up the Test Data.");
        baseRest = new BaseRest();
        baseRest.setupServiceRequestSpecificationBuilderForDataLayerForUI();
        userInfo = new UserInfo();
        enrollmentStatus.add(testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
        enrollmentStatus.add(testData.getProperty("pxpp.26460.enrollment.status.pending"));
        enrollmentStatus.add(testData.getProperty("pxpp.26460.enrollment.status.expired"));
        enrollmentStatus.add(testData.getProperty("pxpp.26460.enrollment.status.confirmed"));
    }

    @BeforeMethod(alwaysRun = true)
    public void loginToCommandCenterPortal() throws Exception {
        if (runOnce) {
            ccui = new CCUI(testData, driver);
            logStep("Login to command center portal");
            ccui.getClientDirectoryPage();
            ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCommandCenterUsersIcon(), "Command Center User Icon");
        }
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verifying elements of Internal Users for Bulk Enrollment")
    public void internalUsersVerificationOnBulkEnrollment() throws Exception {
        initialPreRequisite();
        logStep("PXPP-26544 TC 001_Verify \"BULK ENROLLMENT\" button is displayed for Internal Users Tab In Command Center Users Screen");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getInternalUserTab(), "Internal Users Tab");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getInternalUsersLabel(), "Internal Users Label");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        logStep("PXPP-26634 TC 013_Verify the SEARCH OPTIONS filter for Internal Users in Bulk Enrollment Screen");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        verification = ccui.getUserBulkEnrollmentPage().verifyElementsInSearchOptions();
        logStep("PXPP-26576 TC 004_Verify that \"Bulk Enrollment \" page got triggered when user click on \"BULK ENROLLMENT\" button for Internal User");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentTitle(), "Bulk Enrollment Title");
        logStep("PXPP-26627 TC 010_Verify that User can enable the \"Send Enrollment\" button in Bulk Enrollment Screen for Internal Users");
        enabled = ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        log("is " + ccui.getUserBulkEnrollmentPage().getEnrollButton().getText() + " enabled = " + enabled);
        ccui.getUserBulkEnrollmentPage().selectFirstUserOnUserTable();
        enabled = ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        log("is " + ccui.getUserBulkEnrollmentPage().getEnrollButton().getText() + " enabled = " + enabled);
        logStep("PXPP-26624 TC 007_Verify that User back to Internal Users Tab when user click on  Cancel button in Bulk Enrollment Page for Internal User");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getInternalUsersLabel(), "Internal Users Label");
        runOnce = false;
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verifying elements of VarReseller Users for Bulk Enrollment")
    public void varResellerUsersVerificationAndValidationsForBulkEnrollment() throws Exception {
        initialPreRequisite();
        logStep("PXPP-26569 TC 003_Verify \"BULK ENROLLMENT\" button is displayed for VAR/Reseller Users Tab In Command Center Users Screen");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "VarReseller Users Tab");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getVarResellerUsersLabel(), "VarReseller Users Label");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        logStep("PXPP-26641 TC 015_Verify the SEARCH OPTIONS filter for VAR/Reseller Users in Bulk Enrollment Screen");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        verification = ccui.getUserBulkEnrollmentPage().verifyElementsInSearchOptions();
        logStep("PXPP-26623 TC 006_Verify that \"Bulk Enrollment \" page got triggered when user click on  \"BULK ENROLLMENT\" button for VAR/Reseller User");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentTitle(), "Bulk Enrollment Title");
        logStep("PXPP-26633 TC 012_Verify that User can enable the \"Send Enrollment\" button in Bulk Enrollment Screen for VAR/Reseller Users");
        enabled = ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        log("is " + ccui.getUserBulkEnrollmentPage().getEnrollButton().getText() + " enabled = " + enabled);
        ccui.getUserBulkEnrollmentPage().selectFirstUserOnUserTable();
        enabled = ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        log("is " + ccui.getUserBulkEnrollmentPage().getEnrollButton().getText() + " enabled = " + enabled);
        logStep("PXPP-26626 TC 009_Verify that User back to VAR/Reseller Users Tab when user click on  Cancel button in Bulk Enrollment Page for VAR/Reseller Users");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getVarResellerUsersLabel(), "VarReseller Users Label");
        runOnce = false;
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Search Inactive Users on Bulk Enrollment")
    public void inactiveUserSearchOnBulkEnrollment() throws Exception {
        logStep("PXPP-26642 TC 016_Verify that Inactive Internal Users are not fetched to User search table in Bulk Enrollment Screen");
        internalFirstName = createUsers("Internal", "NOT_SENT", "Inactive", "Inactive", "Active").get("firstName").toString();
        varResellerFirstName = createUsers("VarReseller", "NOT_SENT", "Inactive", "Inactive", "Active").get("firstName").toString();
        //clientFirstName = createUsers("Client", "NOT_SENT", "Inactive", "Inactive", "Active").get("firstName").toString();
        initialPreRequisite();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), "inactive");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        logStep("PXPP-26644 TC 018_Verify that Inactive VAR/Reseller Users are not fetched to User search table in Bulk Enrollment Screen");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "VarReseller User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), "inactive");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        runOnce = false;
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Search for No Roles Users on Bulk Enrollment")
    public void noRolesUserSearchOnBulkEnrollment() throws Exception {
        internalFirstName = createUsers("Internal", "NOT_SENT", "NoRoles", "Active", "inactive").get("firstName").toString();
        varResellerFirstName = createUsers("VarReseller", "NOT_SENT", "NoRoles", "Active", "inactive").get("firstName").toString();
        //clientFirstName = createUsers("Client", "NOT_SENT", "NoRoles", "Active", "inactive").get("firstName").toString();
        initialPreRequisite();
        logStep("PXPP-26645 TC 019_Verify that Internal Users are not fetched to User search table in Bulk Enrollment Screen when Internal user doesn't assign roles");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        logStep("PXPP-26647 TC 021_Verify that VAR/Reseller Users are not fetched to User search table in Bulk Enrollment Screen when VAR/Reseller  user doesn't assign any roles");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "VarReseller User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        runOnce = false;
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Search Locked Users on Bulk Enrollment")
    public void lockedUserSearchOnBulkEnrollment() throws InterruptedException, IOException {
        internalFirstName = createUsers("Internal", "NOT_SENT", "Locked", "Active", "active").get("firstName").toString();
        varResellerFirstName = createUsers("VarReseller", "NOT_SENT", "Locked", "Active", "active").get("firstName").toString();
        //clientFirstName = createUsers("Client", "NOT_SENT", "Locked", "Active", "active").get("firstName").toString();
        initialPreRequisite();
        logStep("PXPP-26648 TC 022_Verify that Internal Users are not fetched to User search table in Bulk Enrollment Screen when Internal user got locked");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickSearchUserOnUserTable(internalFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getKebabMenuOnUserDetails(), "Kebab menu");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getLockUserButton(), "Lock user option");
        ccui.getCommonMethods().verifyToasterMessage("Success! User status has been successfully locked");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBackArrowOnUserDetail(), "Back Arrow");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        logStep("PXPP-26650 TC 024_Verify that VAR/Reseller  Users are not fetched to User search table in Bulk Enrollment Screen when VAR/Reseller  user got locked");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "VarReseller User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickSearchUserOnUserTable(varResellerFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getKebabMenuOnUserDetails(), "Kebab menu");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getLockUserButton(), "Lock user option");
        ccui.getCommonMethods().verifyToasterMessage("Success! User status has been successfully locked");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBackArrowOnUserDetail(), "Back Arrow");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        runOnce = false;
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Search for Confirmed Users on Bulk Enrollment")
    public void confirmedUserSearchOnBulkEnrollment() throws Exception {
        internalFirstName = createUsers("Internal", "CONFIRMED", "Confirmed", "Active", "active").get("firstName").toString();
        createUserWithStatus("Confirmed");
        varResellerFirstName = createUsers("VarReseller", "CONFIRMED", "Confirmed", "Active", "active").get("firstName").toString();
        createUserWithStatus("Confirmed");
        initialPreRequisite();
        logStep("PXPP-26661 TC 025_Verify that Internal Users are not fetched to User search table in Bulk Enrollment Screen when Internal user Enrollment Status is Confirmed");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        logStep("PXPP-26663 TC 027_Verify that VAR/Reseller Users are not fetched to User search table in Bulk Enrollment Screen when VAR/Reseller user Enrollment Status is Confirmed");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "VarReseller User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstName);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel On Bulk Enrollment Page");
        runOnce = false;
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Search for NOT_SENT, PENDING and EXPIRED  on Internal Users on Bulk Enrollment")
    public void internalUserSearchOnBulkEnrollment() throws Exception {
        String internalFirstNameNotSent = createUsers("Internal", "NOT_SENT", "", "Active", "active").get("firstName").toString();
        createUserWithStatus("NOT_SENT");
        String internalFirstNamePending = createUsers("Internal", "PENDING", "", "Active", "active").get("firstName").toString();
        createUserWithStatus("PENDING");
        String internalFirstNameExpired = createUsers("Internal", "EXPIRED", "", "Active", "active").get("firstName").toString();
        createUserWithStatus("EXPIRED");
        initialPreRequisite();
        logStep("PXPP-26664 TC 028_Verify that Internal Users are fetched to User search table in Bulk Enrollment Screen when Internal User Enrollment Status as Not-Sent, Pending and Expired");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getInternalUserTab(), "Internal User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("NOT SENT");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstNameNotSent);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("NOT SENT");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        logStep("PXPP-26669 TC 032_Verify the Cancel or (X) Send Enrollment Confirmation POP-UP Functionality in Bulk Enrollment Screen");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCancelButtonOnSendEnrollmentConfirmation(), "Cancel Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearButtonOnSendEnrollmentConfirmation(), "Clear Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        logStep("PXPP-26667 TC 031_Verify the Send Enrollment Functionality in Bulk Enrollment Screen, When Internal User Enrollment Status as Not-Sent, Pending and Expired");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getYesSendButton(), "Yes, Send Button");
        ccui.getCommonMethods().verifyToasterMessage("Success! Enrollment email has been sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstNameNotSent);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("NOT SENT");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("PENDING");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Pending");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstNamePending);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("PENDING");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCancelButtonOnSendEnrollmentConfirmation(), "Cancel Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearButtonOnSendEnrollmentConfirmation(), "Clear Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getYesSendButton(), "Yes, Send Button");
        ccui.getCommonMethods().verifyToasterMessage("Success! Enrollment email has been sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstNamePending);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("PENDING");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Pending");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("EXPIRED");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Expired");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstNameExpired);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("EXPIRED");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCancelButtonOnSendEnrollmentConfirmation(), "Cancel Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearButtonOnSendEnrollmentConfirmation(), "Clear Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getYesSendButton(), "Yes, Send Button");
        ccui.getCommonMethods().verifyToasterMessage("Success! Enrollment email has been sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), internalFirstNameExpired);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("EXPIRED");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        runOnce = false;
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Search for NOT_SENT, PENDING and EXPIRED on VAR/Reseller Users on Bulk Enrollment")
    public void varResellerUserSearchOnBulkEnrollment() throws Exception {
        String varResellerFirstNameNotSent = createUsers("VarReseller", "NOT_SENT", "", "Active", "active").get("firstName").toString();
        createUserWithStatus("NOT_SENT");
        String varResellerFirstNamePending = createUsers("VarReseller", "PENDING", "", "Active", "active").get("firstName").toString();
        createUserWithStatus("PENDING");
        String varResellerFirstNameExpired = createUsers("VarReseller", "EXPIRED", "", "Active", "active").get("firstName").toString();
        createUserWithStatus("EXPIRED");
        initialPreRequisite();
        logStep("PXPP-26666 TC 030_Verify that VAR/Reseller Users are fetched to User search table in Bulk Enrollment Screen when VAR/Reseller User Enrollment Status as Not-Sent, Pending and Expired");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "VarReseller User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("NOT SENT");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstNameNotSent);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("NOT SENT");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCancelButtonOnSendEnrollmentConfirmation(), "Cancel Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearButtonOnSendEnrollmentConfirmation(), "Clear Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        logStep("PXPP-26693 TC 034_Verify the Send Enrollment Functionality in Bulk Enrollment Screen, When VAR/Reseller User Enrollment Status as Not-Sent, Pending and Expired");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getYesSendButton(), "Yes, Send Button");
        ccui.getCommonMethods().verifyToasterMessage("Success! Enrollment email has been sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstNameNotSent);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("NOT SENT");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("PENDING");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Pending");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstNamePending);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("PENDING");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCancelButtonOnSendEnrollmentConfirmation(), "Cancel Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearButtonOnSendEnrollmentConfirmation(), "Clear Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getYesSendButton(), "Yes, Send Button");
        ccui.getCommonMethods().verifyToasterMessage("Success! Enrollment email has been sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstNamePending);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("PENDING");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Pending");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("EXPIRED");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Expired");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstNameExpired);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("EXPIRED");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCancelButtonOnSendEnrollmentConfirmation(), "Cancel Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearButtonOnSendEnrollmentConfirmation(), "Clear Button");
        ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getYesSendButton(), "Yes, Send Button");
        ccui.getCommonMethods().verifyToasterMessage("Success! Enrollment email has been sent");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), varResellerFirstNameExpired);
        ccui.getUserBulkEnrollmentPage().selectEnrollmentStatus("EXPIRED");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getNoResultsFoundMessage(), "No Result Found Message");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        runOnce = false;
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Search for VAR/Reseller Users and verify Display message")
    public void varResellerUserSearchToVerifyDisplayMessage() throws Exception {
        logStep("PXPP-26700 TC 041_Verify the Display message after Filter Search in Bulk Enrollment Screen For VAR/Reseller Users");
        HashMap<String, Object> payload = createUsers("VarReseller", "NOT_SENT", "", "Active", "active");
        createUserWithStatus("NOT_SENT");
        initialPreRequisite();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "VarReseller User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload.get("username").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        runOnce = false;
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "Search for Internal Users and verify Display message")
    public void internalUserSearchToVerifyDisplayMessage() throws Exception {
        logStep("PXPP-26698 TC 039_Verify the Display message after Filter Search in Bulk Enrollment Screen For Internal Users");
        HashMap<String, Object> payload = createUsers("internal", "NOT_SENT", "", "Active", "active");
        createUserWithStatus("NOT_SENT");
        initialPreRequisite();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getInternalUserTab(), "internal User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload.get("username").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        runOnce = false;
    }


    @Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "Verify Popup Message of Send Enrollment")
    public void verifyPopupMessageOfSendEnrollment() throws Exception {
        logStep("PXPP-26704 TC 045_Verify Send Enrollment Confirmation popup in Bulk Enrollment Screen");
        initialPreRequisite();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getInternalUserTab(), "Internal Users Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        enabled = ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        log("is " + ccui.getUserBulkEnrollmentPage().getEnrollButton().getText() + " enabled = " + enabled);
        ccui.getUserBulkEnrollmentPage().selectFirstUserOnUserTable();
        enabled = ccui.getUserBulkEnrollmentPage().verifyElementIsEnabled(ccui.getUserBulkEnrollmentPage().getEnrollButton());
        log("is " + ccui.getUserBulkEnrollmentPage().getEnrollButton().getText() + " enabled = " + enabled);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getEnrollButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getConfirmationMessageForSendEnrollment(), "Confirmation message for send enrollment");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getYesSendButton(), "Yes, Send button");
        ccui.getUserBulkEnrollmentPage().verifyElementPresence(ccui.getUserBulkEnrollmentPage().getCancelButtonOnSendEnrollmentConfirmation(), "Cancel button");
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalyzer.class, description = "Select/de-select of users in Bulk Enrollment")
    public void selectAndDeselectInBulkEnrollment() throws Exception {
        initialPreRequisite();
        logStep("PXPP-26701 TC 042_Verify the select/de-select the list of users for User Search Table in Bulk Enrollment Screen of Internal Users");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getInternalUserTab(), "Internal Users Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(true);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(false);
        ccui.getUserBulkEnrollmentPage().selectFirstUserOnUserTable();
        ccui.getUserBulkEnrollmentPage().isChecked(true);
        ccui.getUserBulkEnrollmentPage().selectFirstUserOnUserTable();
        ccui.getUserBulkEnrollmentPage().isChecked(false);
        ccui.getUserBulkEnrollmentPage().doubleClickOnFirstUserOnUserTable();
        ccui.getUserBulkEnrollmentPage().isChecked(false);
        ccui.getUserBulkEnrollmentPage().countUsersOnUserSearchTable();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getChangeItemsDisplayPerPage(), "Change Items Display Per Page");
        ccui.getUserBulkEnrollmentPage().changeItemsDisplayPerPage(50);
        ccui.getUserBulkEnrollmentPage().countUsersOnUserSearchTable();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(true);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(false);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getBulkEnrollmentCancelButton(), "Cancel button");
        logStep("PXPP-26703 TC 044_Verify the select/de-select the list of users for User Search Table in Bulk Enrollment Screen of VAR/Reseller Users");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "Var/Reseller Users Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(true);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(false);
        ccui.getUserBulkEnrollmentPage().selectFirstUserOnUserTable();
        ccui.getUserBulkEnrollmentPage().isChecked(true);
        ccui.getUserBulkEnrollmentPage().selectFirstUserOnUserTable();
        ccui.getUserBulkEnrollmentPage().isChecked(false);
        ccui.getUserBulkEnrollmentPage().doubleClickOnFirstUserOnUserTable();
        ccui.getUserBulkEnrollmentPage().isChecked(false);
        ccui.getUserBulkEnrollmentPage().countUsersOnUserSearchTable();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getChangeItemsDisplayPerPage(), "Change Items Display Per Page");
        ccui.getUserBulkEnrollmentPage().changeItemsDisplayPerPage(200);
        ccui.getUserBulkEnrollmentPage().countUsersOnUserSearchTable();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(true);
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectAllUserOnPage(), "Selecting All users on current page");
        ccui.getUserBulkEnrollmentPage().isChecked(false);
    }

    @Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "Verify the Clear and without Search button Functionality for Search Filter in Bulk Enrollment Screen")
    public void internalUserSearchToVerifyClearWithoutSearchOnSearchFilterInBulkEnrollmentScreen() throws Exception {
        logStep("PXPP-26697 TC 038_Verify the Clear and without Search button Functionality for Search Filter in Bulk Enrollment Screen");
        HashMap<String, Object> payload = createUsers("internal", "NOT_SENT", "", "Active", "active");
        createUserWithStatus("NOT_SENT");
        initialPreRequisite();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getInternalUserTab(), "internal User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload.get("username").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearButtonOnSearchFilterOnUserPage(), "clear button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload.get("username").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectUsersToSendEnrollmentLabel(), "Select users to send enrollment Label");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().verifyTextBoxIsEmpty("firstName");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSelectUsersToSendEnrollmentLabel(), "Select users to send enrollment label");
        driver.navigate().refresh();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().verifyTextBoxIsEmpty("firstName");
    }

    @Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "Verify the Search Filter Functionality with all attributes in Bulk Enrollment Screen for Internal Users")
    public void verifyOnInternalUserSearchFunctionalityWithAllAttributes() throws Exception {
        logStep("PXPP-26694 TC 035_Verify the Search Filter Functionality with all attributes in Bulk Enrollment Screen for Internal Users");
        HashMap<String, Object> payload1 = createUsers("internal", "NOT_SENT", "", "Active", "active");
        initialPreRequisite();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getInternalUserTab(), "internal User Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload1.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload1.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload1.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload1.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload1.get("username").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload1.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload1.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload1.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload1.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload1.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload1.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload1.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload1.get("username").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload1.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload1.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload1.get("firstName").toString());
    }

    @Test(priority = 15, retryAnalyzer = RetryAnalyzer.class, description = "Verify the Search Filter Functionality with all attributes in Bulk Enrollment Screen for Internal Users")
    public void verifyOnVarResellerUserSearchFunctionalityWithAllAttributes() throws Exception {
        logStep("PXPP-26696 TC 037_Verify the Search Filter Functionality with all attributes in Bulk Enrollment Screen for VAR/Reseller Users");
        HashMap<String, Object> payload2 = createUsers("varReseller", "NOT_SENT", "", "Active", "active");
        initialPreRequisite();
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getVarResellerUserTab(), "Var/Reseller Users Tab");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSendEnrollmentButton(), "Send Enrollment Button");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload2.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload2.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload2.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload2.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload2.get("username").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload2.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload2.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload2.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload2.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload2.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getClearAll(), "Clear All");
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchOptionsButton(), "Search Options Button");
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getFirstNameTextBoxOnSearchFilter(), payload2.get("firstName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getLastNameTextBoxOnSearchFilter(), payload2.get("lastName").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getUsernameTextBoxOnSearchFilter(), payload2.get("username").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getMobileNumberTextBoxOnSearchFilter(), payload2.get("mobile").toString());
        ccui.getUserBulkEnrollmentPage().enterValue(ccui.getUserBulkEnrollmentPage().getEmailTextBoxOnSearchFilter(), payload2.get("email").toString());
        ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getSearchButtonOnSearchFilterOnUserPage(), "Search Button on Search Filter");
        ccui.getUserBulkEnrollmentPage().checkEnrollmentStatus("Not Sent");
        ccui.getUserBulkEnrollmentPage().checkFirstNameOfUserOnUserSearchTable(payload2.get("firstName").toString());
    }

    @Test(enabled = false)
    public void initialPreRequisite() throws InterruptedException {
        if (!runOnce) {
            ccui = new CCUI(testData, driver);
            logStep("Login to command center portal");
            ccui.getClientDirectoryPage();
            ccui.getUserBulkEnrollmentPage().clickCommandCenterUserIcon();
        }
    }

    @Test(enabled = false)
    public HashMap<String, Object> createNewInternalUser(String enrollmentStatus, String firstNameValue, String userStatus, String roleStatus) throws IOException {
        logStep("Create Internal user using valid details ");
        String firstName;
        List<Integer> roleIds = new ArrayList<Integer>();
        if(firstNameValue.equalsIgnoreCase("Inactive"))
            firstName = "Inactive"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else if(firstNameValue.equalsIgnoreCase("NoRoles"))
            firstName = "NoRoles"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else if(firstNameValue.equalsIgnoreCase("Locked"))
            firstName = "Locked"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else if(firstNameValue.equalsIgnoreCase("Confirmed"))
            firstName = "Confirmed"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else
            firstName = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        String lastName = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.lastName").concat(CCUtils.generateAlphaNumericString(5)));
        if(roleStatus.equalsIgnoreCase("active"))
            roleIds.add(Integer.parseInt(testData.getProperty("api.internal.user.roleId")));
        else
            roleIds = null;
        String email = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5)));
        String userName = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.userName").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5)));
        Long mobileNumber = CCUtils.generateMobileNumber();
        String userType = "Internal";
        baseRest.setupServiceRequestSpecificationBuilderForUI();
        createUserApiResponse = userInfo.createNewUser_BusinessLayer(userType,
                firstName, lastName, userName, email,
                mobileNumber, userStatus, enrollmentStatus, null, null, null, roleIds,
                false);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        userMap.put("userType", userType);
        userMap.put("firstName", firstName);
        userMap.put("lastName", lastName);
        userMap.put("mobile", mobileNumber);
        userMap.put("email", email);
        return userMap;
    }

    @Test(enabled = false)
    public HashMap<String, Object> createNewVarResellerUser(String enrollmentStatus, String firstNameValue, String userStatus, String userRoles) throws IOException {
        logStep("Create Var/Reseller user using valid details ");
        List<Integer> roleIds = new ArrayList<Integer>();
        if(userRoles.equalsIgnoreCase("active"))
            roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
        else
            roleIds = null;
        String email = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5)));
        String userName = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.userName")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5)));
        Long mobileNumber = CCUtils.generateMobileNumber();
        baseRest.setupServiceRequestSpecificationBuilderForUI();
        String userType = "VARReseller";
        String lastName = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.lastName"));
        String firstName;
        if(firstNameValue.equalsIgnoreCase("Inactive"))
            firstName = "Inactive"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else if(firstNameValue.equalsIgnoreCase("NoRoles"))
            firstName = "NoRoles"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else if(firstNameValue.equalsIgnoreCase("Locked"))
            firstName = "Locked"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else if(firstNameValue.equalsIgnoreCase("Confirmed"))
            firstName = "Confirmed"+(CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        else
            firstName = (CCUtils.generateRandomStringWithoutSymbolsOrNumbers(5)).concat(testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5)));
        createUserApiResponse = userInfo.createNewUser_BusinessLayer(userType,
                firstName, lastName, userName, email,
                mobileNumber, userStatus, enrollmentStatus, null, testData.getProperty("api.resellerId"), null, roleIds,
                false);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        userMap.put("userType", userType);
        userMap.put("firstName", firstName);
        userMap.put("lastName", lastName);
        userMap.put("mobile", mobileNumber);
        userMap.put("email", email);
        return userMap;
    }

    @Test(enabled = false)
    public HashMap<String, Object> createUsers(String userType, String status, String firstNameValue, String userStatus, String userRoles) throws IOException {
        if(userType.equalsIgnoreCase("Internal")) {
            userMap.clear();
            userMap = createNewInternalUser(status, firstNameValue, userStatus, userRoles);
            userType1 = userMap.get("userType").toString();
            userId = Integer.parseInt(userMap.get("userId").toString());
            firstName = userMap.get("firstName").toString();
        }
        else if(userType.equalsIgnoreCase("VarReseller")) {
            userMap.clear();
            userMap = createNewVarResellerUser(status, firstNameValue, userStatus, userRoles);
            userId = Integer.parseInt(userMap.get("userId").toString());
            firstName = userMap.get("firstName").toString();
        }
        return userMap;
    }

    @Test(enabled = false)
    public void createUserWithStatus(String status) throws IOException {
        baseRest.setupServiceRequestSpecificationBuilderForDataLayerForUI();
        String internalEnrollmentId;
        UserSendEnrollmentConsumerDataLayer userSendEnrollmentConsumerPayloadObj;
        if (status.equalsIgnoreCase(testData.getProperty("pxpp.26460.enrollment.status.pending"))) {
            Date date1 = new Date(System.currentTimeMillis() + 4 * 60 * 60 * 1000);
            Date date2 = new Date(System.currentTimeMillis() - 20 * 60 * 60 * 1000);
            String expiryTs = dateFormat.format(new Date(date1.getTime() + (1000 * 60 * 60 * 24)));
            String initiatedTs = dateFormat.format(new Date(date2.getTime() + (1000 * 60 * 60 * 24)));
            internalEnrollmentId = CCUtils.generateAuthId();
            userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(internalEnrollmentId, initiatedTs, expiryTs, status);
            sendUserEnrollmentConsumerResponse = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
            logStep("Validating response Code");
            CCUtils.responseTimeValidation(sendUserEnrollmentConsumerResponse);
            CCUtils.assertEquals(sendUserEnrollmentConsumerResponse.getStatusCode(), 200);
        } else if (status.equalsIgnoreCase(testData.getProperty("pxpp.26403.valid.expired.status"))) {
            internalEnrollmentId = CCUtils.generateAuthId();
            userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(internalEnrollmentId, initiatedTs, expiryTs, status);
            sendUserEnrollmentConsumerResponse = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
            logStep("Validating response Code");
            CCUtils.responseTimeValidation(sendUserEnrollmentConsumerResponse);
            CCUtils.assertEquals(sendUserEnrollmentConsumerResponse.getStatusCode(), 200);
            updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_DL("DL", "Valid", status, currentTs);
            logStep("Validating response information");
            CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
            CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 200);
        } else if (status.equalsIgnoreCase(testData.getProperty("pxpp.26460.enrollment.status.confirmed"))) {
            Date date1 = new Date(System.currentTimeMillis() + 4 * 60 * 60 * 1000);
            Date date2 = new Date(System.currentTimeMillis() - 20 * 60 * 60 * 1000);
            String expiryTs = dateFormat.format(new Date(date1.getTime() + (1000 * 60 * 60 * 24)));
            String initiatedTs = dateFormat.format(new Date(date2.getTime() + (1000 * 60 * 60 * 24)));
            internalEnrollmentId = CCUtils.generateAuthId();
            userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(internalEnrollmentId, initiatedTs, expiryTs, testData.getProperty("pxpp.26460.enrollment.status.pending"));
            sendUserEnrollmentConsumerResponse = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
            logStep("Validating response Code");
            CCUtils.responseTimeValidation(sendUserEnrollmentConsumerResponse);
            CCUtils.assertEquals(sendUserEnrollmentConsumerResponse.getStatusCode(), 200);
            baseRest.setupServiceRequestSpecificationBuilderForUI();
            String password = CCUtils.generateAlphaNumericStringIncludingSpecialChar(25) + "&9";
            updatePasswordDuringEnrollmentApiResponse = userInfo.updatePassword_BL("valid", internalEnrollmentId, password);
            logStep("Validating response information");
            CCUtils.responseTimeValidation(updatePasswordDuringEnrollmentApiResponse);
            CCUtils.assertEquals(updatePasswordDuringEnrollmentApiResponse.getStatusCode(), 200);
        }
    }
}
