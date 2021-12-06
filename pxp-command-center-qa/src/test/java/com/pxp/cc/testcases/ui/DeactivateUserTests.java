// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.DeactivateUserPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class DeactivateUserTests extends CommandCenterGenericTest {

	ClientDirectoryPage cchomepage;
	DeactivateUserPage deactivateUserPage;
	public CommonMethods commonMethods;

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check deactive popup")
	public void testDeactivePopUp() throws Exception {
		logStep("Login to Command Center");
		cchomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("navigate to Commandcenter Internal users tab");
		deactivateUserPage = new DeactivateUserPage(driver);
		deactivateUserPage.clickCCUserIcon();
		logStep("Verifying deactivate internal user popup");
		deactivateUserPage.selectUser();
		deactivateUserPage.clickDeactivate();
		String actualPopupMessage = deactivateUserPage.verifyPopUpMessage();
		if (actualPopupMessage.equalsIgnoreCase("Are you sure you want to deactivate")) {
			log("Popup message is displayed");
			CCUtils.assertTrue(true, "Test Pass");
		} else {
			log("popup message is incorrect : " + actualPopupMessage);
			CCUtils.assertTrue(false, "Test Failed");
		}
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check user status changed to deactivate")
	public void testInternalUserIsDeactive() throws Exception {
		logStep("Login to Command Center");
		cchomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("navigate to Commandcenter Internal users tab");
		deactivateUserPage = new DeactivateUserPage(driver);
		deactivateUserPage.clickCCUserIcon();
		logStep("Deactivating internal user popup");
		deactivateUserPage.selectUser();
		deactivateUserPage.clickDeactivate();
		deactivateUserPage.clickDeactivateButton();
		commonMethods = new CommonMethods(driver);
		String actualToasterMessage = commonMethods.getToasterMessage();
		if (actualToasterMessage.equalsIgnoreCase("Are you sure you want to deactivate")) {
			log("Popup message is displayed");
			CCUtils.assertTrue(true, "Test Pass");
		} else {
			log("popup message is incorrect : " + actualToasterMessage);
			CCUtils.assertTrue(false, "Test Failed");
		}
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check status changed to deactivate for multiple user")
	public void testBulkUser() throws Exception {
		logStep("Login to Command Center");
		cchomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("navigate to Commandcenter Internal users tab");
		deactivateUserPage = new DeactivateUserPage(driver);
		deactivateUserPage.clickCCUserIcon();
		logStep("Deactivating multiple internal user popup");
		deactivateUserPage.selectUser();
		deactivateUserPage.selectUserTwo();
		deactivateUserPage.clickDeactivate();
		deactivateUserPage.clickDeactivateButton();
		commonMethods = new CommonMethods(driver);
		String actualToasterMessage = commonMethods.getToasterMessage();
		if (actualToasterMessage.equalsIgnoreCase("Are you sure you want to deactivate")) {
			log("Popup message is displayed");
			CCUtils.assertTrue(true, "Test Pass");
		} else {
			log("popup message is incorrect : " + actualToasterMessage);
			CCUtils.assertTrue(false, "Test Failed");
		}
	}

}
