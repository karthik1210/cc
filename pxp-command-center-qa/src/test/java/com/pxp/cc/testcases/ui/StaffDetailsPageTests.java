//Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.Test;
import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class StaffDetailsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
	String staffName;
	String firstName = "New"
			+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
	String lastName = CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6))
			.concat("'s");

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
	
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enable and disable practice for created staff")
	public void testToEnableAndDisableStaffForPractice() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		createStaff();
		logStep("PXPP-14383 Verify User is able to search staff on the Providers, Resources & Staff page");
		ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
		logStep("Navigated to staff details page");
		ccui.getStaffPage().selectStaff();
		ccui.getStaffPage().verifyStaffDetailsTitle();
		logStep("PXPP-14390 Verify User is able to enable or disable the Staff at the multiple Practices.");
		ccui.getStaffDetailsPage().clickToEntepriseArrow();
		ccui.getStaffDetailsPage().clickOnToggleButton();
		ccui.getStaffPage().clickToBackButton();
		ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
		ccui.getStaffPage().selectStaff();
		ccui.getStaffPage().verifyStaffDetailsTitle();
		ccui.getStaffDetailsPage().clickOnToggleButton();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Staff label And Staff Info Section")
	public void verifyStaffLabelAndStaffInfoSection() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		createStaff();
		ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
		logStep("Navigated to staff details page");
		ccui.getStaffPage().selectStaff();
		ccui.getStaffPage().verifyStaffDetailsTitle();
		logStep("PXPP-14501 ,PXPP-14506 Verify System Tabs section is displayed on Staff Details page");
		ccui.getStaffDetailsPage().verifyStaffInfoSection();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify staff image is successfully added and removed and Verify invalid file type error is displayed when uploading invalid format image file")
	 public void testAddRemoveStaffImageFunctionalityAndInvalidFileTypeMessage() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		createStaff();
	    ccui.getStaffPage().enterStaffInSearchTextBox(firstName);
	    logStep("PXPP-26559 Verify details of Staff Name and Staff Image section");
	    ccui.getStaffPage().selectStaff();
	    ccui.getStaffPage().verifyStaffDetailsTitle();
	    logStep("PXPP-26567 Verify Upload staff image functionality");
	    ccui.getStaffDetailsPage().uploadStaffImage();
	    logStep("PXPP-26568 Verify remove uploaded image functionality");
	    ccui.getStaffDetailsPage().removeStaffImage();
	    ccui.getStaffPage().verifyStaffDetailsTitle();
	    ccui.getStaffDetailsPage().uploadStaffInvalidImage();
	    logStep("PXPP-26572 Verify error message is displayed when invalid format image is uploaded");
	    ccui.getStaffDetailsPage().verifyInvalidFileTypeErrorMessage();
	     runOnce = false;
	 }

	public void createStaff() throws Exception {
		ccui.getStaffPage().clickOnStaffTab();
		logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
		ccui.getStaffPage().verifyAddButtonIsEnabled();
		logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
		ccui.getStaffPage().clickOnAddStaffButton();
		logStep("Verify add new Resource popup");
		ccui.getStaffPage().verifyAddStaffPopupTitle();
		logStep("PXPP-26546 Verify ADD button is displayed on Providers, Resources, & Staff tab Staff page for the client ");
		logStep("Enter data in mandatory fields");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		logStep("PXPP-26550 Verify Add Staff dialog box contains First Name, Last Name, External ID, Gender fields");
		ccui.getStaffPage().enterFirstName(firstName);
		ccui.getStaffPage().enterLastName(lastName);
		ccui.getStaffPage().enterExternalId(externalId);
		ccui.getStaffPage().selectGenderValue("Male");
		ccui.getStaffPage().verifySaveAndAddButtonIsEnabled();
		logStep("PXPP-26551 Verify Add Staff dialog box contains Save & Add button ");
		ccui.getStaffPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Staff has been successfully created");

	}

	@Test(enabled = false)
	public void navigatetoStaff() throws Exception {
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		ccui.getStaffPage().clickOnStaffTab();
		logStep("PXPP-26548 Verify dialog box opens after clicking on +ADD button");
		ccui.getStaffPage().verifyAddButtonIsEnabled();
		logStep("PXPP-26549 Verify dialog box title as Add Staff after dialog box gets displayed");
		ccui.getStaffPage().clickOnAddStaffButton();
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
			ccui.getNgHierarchyClientCreationPage()
					.clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
			ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		}
	}
}