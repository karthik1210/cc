// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.EditOrgNodePage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;

public class EditOrgNodeTest extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public EditOrgNodePage editOrgNodePage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in edit system field and verify edit system functionality")
	public void testOrgSystemSaveEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system node");
		generalSettingsPage.clickOnSystemNode();
		editOrgNodePage = new EditOrgNodePage(driver);
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in system node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickInactiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter details in edit system field and verify edit cancel system functionality")
	public void testOrgSystemCancelEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system node");
		generalSettingsPage.clickOnSystemNode();
		editOrgNodePage = new EditOrgNodePage(driver);
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in system node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickInactiveRadioButtonOfOrgNode();
		logStep("Click on cancel button");
		editOrgNodePage.clickCancelButtonOfOrg();
	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit system override functionality")
	public void testOrgSystemOverrideExistingDataEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system node");
		generalSettingsPage.clickOnSystemNode();
		editOrgNodePage = new EditOrgNodePage(driver);
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in system node fields");
		editOrgNodePage.overrideEditOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 12, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit system functionality without entering data in system field")
	public void testEditOrgSystemWithNullData() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system node");
		generalSettingsPage.clickOnSystemNode();
		editOrgNodePage = new EditOrgNodePage(driver);
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in system node fields");
		editOrgNodePage.nullEditOnGeneralSettingPage(prop.getProperty("org.timezone.select.option"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 15, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit system functionality with Maximum data")
	public void testEditOrgSystemMaximumLengthValidation() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system node");
		generalSettingsPage.clickOnSystemNode();
		editOrgNodePage = new EditOrgNodePage(driver);
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in system node fields");
		editOrgNodePage.maximumLengthEditOrgOnGeneralSettingPage(prop.getProperty("existing.timezone"));
		CCUtils.assertEquals(editOrgNodePage.sapIdMaxLengthValidationMessageOnEditOrg(), "Maximum characters reached");
		CCUtils.assertEquals(editOrgNodePage.solutionDisplayNameMaxLengthValidationMessageOnEditOrg(),
				"Maximum characters reached");
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on cancel button");
		editOrgNodePage.clickCancelButtonOfOrg();
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in edit enterprise field and verify edit enterprise functionality")
	public void testOrgEnterpriseSaveEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in enterprise node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickInactiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 10, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter details in edit enterprise field and verify edit cancel enterprise functionality")
	public void testOrgEnterpriseCancelEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in enterprise node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickInactiveRadioButtonOfOrgNode();
		logStep("Click on cancel button");
		editOrgNodePage.clickCancelButtonOfOrg();
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit enterprise override functionality")
	public void testOrgEnterpriseOverrideExistingDataEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in enterprise node fields");
		editOrgNodePage.overrideEditOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 13, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit enterprise functionality without entering data in enterprise field")
	public void testEditOrgEnterpriseWithNullData() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in enterprise node fields");
		editOrgNodePage.nullEditOnGeneralSettingPage(prop.getProperty("org.timezone.select.option"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 16, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit enterprise functionality with Maximum data")
	public void testEditOrgEnterpriseMaximumLengthValidation() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in enterprise node fields");
		editOrgNodePage.maximumLengthEditOrgOnGeneralSettingPage(prop.getProperty("existing.timezone"));
		CCUtils.assertEquals(editOrgNodePage.sapIdMaxLengthValidationMessageOnEditOrg(), "Maximum characters reached");
		CCUtils.assertEquals(editOrgNodePage.solutionDisplayNameMaxLengthValidationMessageOnEditOrg(),
				"Maximum characters reached");
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on cancel button");
		editOrgNodePage.clickCancelButtonOfOrg();
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in edit practice field and verify edit practice functionality")
	public void testOrgPracticeSaveEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickOnPracticeNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in practice node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickInactiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 11, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter details in edit practice field and verify edit cancel practice functionality" )
	public void testOrgPracticeCancelEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickOnPracticeNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in practice node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickInactiveRadioButtonOfOrgNode();
		logStep("Click on cancel button");
		editOrgNodePage.clickCancelButtonOfOrg();
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit practice override functionality")
	public void testOrgPracticeOverrideExistingDataEditFunctionality() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickOnPracticeNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in practice node fields");
		editOrgNodePage.overrideEditOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 14, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit practice functionality without entering data in practice field")
	public void testEditOrgPracticeWithNullData() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickOnPracticeNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in practice node fields");
		editOrgNodePage.nullEditOnGeneralSettingPage(prop.getProperty("org.timezone.select.option"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
	}

	@Test(priority = 17, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit practice functionality with Maximum data")
	public void testEditOrgPracticeMaximumLengthValidation() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickOnPracticeNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in practice node fields");
		editOrgNodePage.maximumLengthEditOrgOnGeneralSettingPage(prop.getProperty("existing.timezone"));
		CCUtils.assertEquals(editOrgNodePage.sapIdMaxLengthValidationMessageOnEditOrg(), "Maximum characters reached");
		CCUtils.assertEquals(editOrgNodePage.solutionDisplayNameMaxLengthValidationMessageOnEditOrg(),
				"Maximum characters reached");
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on cancel button");
		editOrgNodePage.clickCancelButtonOfOrg();
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit system functionality for inactive parent")
	public void testEditSystemErrorMessagesForInactiveParentClient() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on edit button of client");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("Enter details in client node fields");
		generalSettingsPage.editClientNodeOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		generalSettingsPage.clickInactiveRadioButtonOfClient();
		generalSettingsPage.clickSaveButtonClient();
		generalSettingsPage.clickOnSystemNode();
		editOrgNodePage = new EditOrgNodePage(driver);
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in system node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
		CCUtils.assertEquals(editOrgNodePage.errorMessageOnEditOrgForInactiveClient(),
				"System cannot be Activated as Client is Inactive");
	}
	
	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit enterprise functionality for inactive parent")
	public void testEditEnterpriseErrorMessagesForInactiveParentSystem() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in enterprise node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
		CCUtils.assertEquals(editOrgNodePage.errorMessageOnEditOrgForInactiveClient(),
				"Enterprise cannot be Activated as System is Inactive");
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit practice functionality for inactive parent")
	public void testEditPracticeErrorMessagesForInactiveParentEnterprise() throws Exception {
		PropertyFileLoader prop = new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("existing.client.name"));
		editOrgNodePage = new EditOrgNodePage(driver);
		logStep("Click on system node carret icon");
		editOrgNodePage.clickOnSystemCarretIcon();
		editOrgNodePage.clickOnEnterpriseCarretIcon();
		editOrgNodePage.clickOnEnterpriseNode();
		editOrgNodePage.clickOnPracticeNode();
		editOrgNodePage.clickEditButtonOfOrg();
		logStep("Enter details in practice node fields");
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("existing.sapid"),
				prop.getProperty("existing.solutiondisplayname"), prop.getProperty("existing.timezone"));
		editOrgNodePage.clickActiveRadioButtonOfOrgNode();
		logStep("Click on save button");
		editOrgNodePage.clickSaveButtonOfOrg();
		CCUtils.assertEquals(editOrgNodePage.errorMessageOnEditOrgForInactiveClient(),
				"Practice cannot be Activated as Enterprise is Inactive");
	}
	
}
