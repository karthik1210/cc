// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import com.pxp.cc.objectmaps.EditBasicInfoGeneralSettingsPage;

public class EditBasicInfoGeneralSettingsTests extends CommandCenterGenericTest {
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	String clientName, clientSapNumber, externalId, providerFirstName, resourceName, firstName;
	public AddOrgNodePage addOrgNodePage;
	public NGHierarchyClientCreation ngHierarchyClientCreation;
	public EditBasicInfoGeneralSettingsPage editBasicInfoGeneralSettingsPage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to command center portal");
		cchomepage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
		createClientPage = new CreateClientPage(driver);
		commonMethods = new CommonMethods(driver);
		ngHierarchyClientCreation = new NGHierarchyClientCreation(driver);
		String clientNam = testData.getProperty("new.client.name2")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSap = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientType = testData.getProperty("new.client.type");
		String timeZone = testData.getProperty("new.client.timezone");
		String systemOrEnterprise = testData.getProperty("new.system");
		String timeZoneDisplayName = testData.getProperty("new.timezone.display.name");
		String solution = testData.getProperty("solution.forms");
		ngHierarchyClientCreation.createNewActiveClient(clientNam, clientSap, clientType, timeZone);
		ngHierarchyClientCreation.createNewActiveSystem(systemOrEnterprise, systemOrEnterprise);
		ngHierarchyClientCreation.createNewActiveEnterprise(systemOrEnterprise, systemOrEnterprise,
				timeZoneDisplayName);
		ngHierarchyClientCreation.createNewActivePractice(systemOrEnterprise, systemOrEnterprise, timeZoneDisplayName,
				solution);
		editBasicInfoGeneralSettingsPage = new EditBasicInfoGeneralSettingsPage(driver);
	}
	
	@Test(priority=2, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at Client level")
	public void testBasicInfoSectionAtClientLevel() throws Exception {
		logStep("Verify Basic Info section At Client Level");
		editBasicInfoGeneralSettingsPage.clickClientOrg();
		editBasicInfoGeneralSettingsPage.verifyEditBasicInfoAtClientLevel();
	}

	@Test(priority=3, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at Client level")
	public void editAndSaveBasicInfoSectionAtClientLevel() throws Exception {
		logStep("Verify edit Basic Info functionality at Client level");
		editBasicInfoGeneralSettingsPage.clickClientOrg();
		editBasicInfoGeneralSettingsPage.verifyEditButtonIsDisplayed();
		editBasicInfoGeneralSettingsPage.clickEditButton();
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientType = testData.getProperty("edit.client.type");
		editBasicInfoGeneralSettingsPage.editSapIdClientType(clientSapNumber, clientType);
		editBasicInfoGeneralSettingsPage.editTimezone(testData.getProperty("edit.timezone.display.name"));
		editBasicInfoGeneralSettingsPage.clickSaveButton();
		editBasicInfoGeneralSettingsPage.verifySuccessToasterMessage();
	}
	
	@Test(priority=4, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at System level")
	public void testBasicInfoSectionAtSystemLevel() throws Exception {
		logStep("Verify Basic Info section At System Level");
		editBasicInfoGeneralSettingsPage.clickSystemOrg();
		editBasicInfoGeneralSettingsPage.verifyEditBasicInfoAtSystemLevel();
	}

	@Test(priority=5, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at System level")
	public void editAndSaveBasicInfoSectionAtSystemLevel() throws Exception {
		logStep("Verify edit Basic Info functionality at System level");
		editBasicInfoGeneralSettingsPage.clickSystemOrg();
		editBasicInfoGeneralSettingsPage.verifyEditButtonIsDisplayed();
		editBasicInfoGeneralSettingsPage.clickEditButton();
		editBasicInfoGeneralSettingsPage.clickSaveButton();
		editBasicInfoGeneralSettingsPage.verifySuccessToasterMessage();
	}

	@Test(priority=6, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at Enterprise level")
	public void testBasicInfoSectionAtEnterpriseLevel() throws Exception {
		logStep("Verify Basic Info section At Enterprise Level");
		editBasicInfoGeneralSettingsPage.clickEnterpriseOrg();
		editBasicInfoGeneralSettingsPage.verifyEditBasicInfoAtEnterpriseLevel();
	}

	@Test(priority=7, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at Enterprise level")
	public void editAndSaveBasicInfoSectionAtEnterpriseLevel() throws Exception {
		logStep("Verify edit Basic Info functionality at Enterprise level");
		editBasicInfoGeneralSettingsPage.clickEnterpriseOrg();
		editBasicInfoGeneralSettingsPage.verifyEditButtonIsDisplayed();
		editBasicInfoGeneralSettingsPage.clickEditButton();
		editBasicInfoGeneralSettingsPage.editTimezone(testData.getProperty("edit.timezone.display.name"));
		editBasicInfoGeneralSettingsPage.clickSaveButton();
		editBasicInfoGeneralSettingsPage.verifySuccessToasterMessage();
	}
	
	@Test(priority=0, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at Practice level")
	public void testBasicInfoSectionAtPracticeLevel() throws Exception {
		logStep("Verify Basic Info section At Practice Level");
		editBasicInfoGeneralSettingsPage.verifyEditBasicInfoAtPracticeLevel();
	}

	@Test(priority=1, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at Practice level")
	public void editAndSaveBasicInfoSectionAtPracticeLevel() throws Exception {
		logStep("Verify edit Basic Info functionality at Practice level");
		editBasicInfoGeneralSettingsPage.verifyEditButtonIsDisplayed();
		editBasicInfoGeneralSettingsPage.clickEditButton();
		editBasicInfoGeneralSettingsPage.enterSolutionsDisplayName("Engage");
		editBasicInfoGeneralSettingsPage.editTimezone(testData.getProperty("edit.timezone.display.name"));
		editBasicInfoGeneralSettingsPage.clickSaveButton();
		editBasicInfoGeneralSettingsPage.verifySuccessToasterMessage();
	}
	
	@Test(priority=8, retryAnalyzer = RetryAnalyzer.class, description = "Verify Basic Info Section Collapse Arrow functionality")
	public void testBasicInfoSectionCollapseArrow() throws Exception {
		logStep("Verify Basic Info Section Collapse Arrow functionality");
		editBasicInfoGeneralSettingsPage.clickOnBasicInfoCollapseButton();
	}
	
	@Test(priority=9, retryAnalyzer = RetryAnalyzer.class, description = "Verify Basic Info Section Cancel Button Functionality")
	public void testCancelButtonFunctionality() throws Exception {
		logStep("Verify Basic Info Section Cancel Button Functionality");
		editBasicInfoGeneralSettingsPage.verifyEditButtonIsDisplayed();
		editBasicInfoGeneralSettingsPage.clickEditButton();		
		editBasicInfoGeneralSettingsPage.enterSolutionsDisplayName("Engage");
		editBasicInfoGeneralSettingsPage.editTimezone(testData.getProperty("edit.timezone.display.name"));
		editBasicInfoGeneralSettingsPage.clickOnCancelButton();
	}
}
