// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddAndUpdateProductSolutionsPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;

public class AddAndUpdateProductSolutionsTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public AddOrgNodePage addOrgNodePage;
	public AddAndUpdateProductSolutionsPage addAndUpdateProductSolutionsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice to terminate the newly purchased solutions")
	public void testTerminatedSolutionAvailableForPurchase() throws Exception {
		String parentName = generalSettingsPage.getClientName();
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		String practiceName = testData.getProperty("active.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewPracticeNodeWithSolutions(parentName, true, practiceName,
				testData.getProperty("new.practice.sapid"), testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		String actualSolutionName = addAndUpdateProductSolutionsPage.selectTerminateCheckboxForSingleProduct();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		String expectedSolutionName = addAndUpdateProductSolutionsPage
				.verifyTerminatedSolutionsIsAvailableForPurchase();
		CCUtils.assertEquals(actualSolutionName, expectedSolutionName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice to activate already terminated solutions")
	public void testAgnosticPracticeToActivateAlreadyTerminatedSolutions() throws Exception {
		String parentName = generalSettingsPage.getClientName();
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		String practiceName = testData.getProperty("active.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewPracticeNodeWithSolutions(parentName, true, practiceName,
				testData.getProperty("new.practice.sapid"), testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		addAndUpdateProductSolutionsPage.selectTerminateCheckboxForSingleProduct();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		String actualSolutionName = addAndUpdateProductSolutionsPage.selectActivateCheckboxForSingleProduct();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		String expectedSolutionName = addAndUpdateProductSolutionsPage
				.verifyActivatedSolutionsIsAvailableForTerminate();
		CCUtils.assertEquals(actualSolutionName, expectedSolutionName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new Nge practice to terminate the all newly purchased solutions")
	public void testAddNewNgePracticeToTerminateAllNewlyPurchasedSolutions() throws Exception {
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemName = ccuser.getSystemName().concat(CCUtils.generateRandomString());
		addOrgNodePage.createNewOrgNode(systemName, ccuser.getSystemSapId(), ccuser.getSystemTimezone(),
				ccuser.getSystemSolutionDisplayName());
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();

		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new enterprise pop up");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getSystemName());
		addOrgNodePage.createNewOrgNode(ccuser.getEnterpriseName(), ccuser.getEnterpriseSapId(),
				ccuser.getEnterpriseTimezone(), ccuser.getEnterpriseSolutionDisplayName());
		addOrgNodePage.clickOnSaveAndAddButton();

		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getEnterpriseName());
		logStep("Add new nge practice node below enterprise node");
		String practiceName = testData.getProperty("active.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.createNewOrgNode(practiceName, testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addOrgNodePage.addSolutions();
		addOrgNodePage.clickOnSaveAndAddButton();
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		addAndUpdateProductSolutionsPage.selectAllTerminateCheckboxOfProducts();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new Nge practice to activate all terminated solutions")
	public void testAddNewNgePracticeToActivateAllTerminatedSolutions() throws Exception {

		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemName = ccuser.getSystemName().concat(CCUtils.generateRandomString());
		addOrgNodePage.createNewOrgNode(systemName, ccuser.getSystemSapId(), ccuser.getSystemTimezone(),
				ccuser.getSystemSolutionDisplayName());
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();

		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new enterprise pop up");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getSystemName());
		addOrgNodePage.createNewOrgNode(ccuser.getEnterpriseName(), ccuser.getEnterpriseSapId(),
				ccuser.getEnterpriseTimezone(), ccuser.getEnterpriseSolutionDisplayName());
		addOrgNodePage.clickOnSaveAndAddButton();

		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getEnterpriseName());
		logStep("Add new nge practice node below enterprise node");
		String practiceName = testData.getProperty("active.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.createNewOrgNode(practiceName, testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addOrgNodePage.addSolutions();
		addOrgNodePage.clickOnSaveAndAddButton();
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		addAndUpdateProductSolutionsPage.selectAllTerminateCheckboxOfProducts();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		addAndUpdateProductSolutionsPage.selectAllActivateCheckboxOfProducts();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		addAndUpdateProductSolutionsPage.verifyActiveSolutionsNames();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice to terminate the all purchased solutions")
	public void testAgnosticPracticeToTerminateAllTheSolutions() throws Exception {
		String parentName = generalSettingsPage.getClientName();
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		String practiceName = testData.getProperty("active.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewPracticeNodeWithSolutions(parentName, true, practiceName,
				testData.getProperty("new.practice.sapid"), testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		addAndUpdateProductSolutionsPage.selectAllTerminateCheckboxOfProducts();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice to activate all the terminated solutions")
	public void testAgnosticPracticeToActivateAllTheTerminatedSolutions() throws Exception {
		String parentName = generalSettingsPage.getClientName();
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		String practiceName = testData.getProperty("active.practice.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.addNewPracticeNodeWithSolutions(parentName, true, practiceName,
				testData.getProperty("new.practice.sapid"), testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		addAndUpdateProductSolutionsPage.selectAllTerminateCheckboxOfProducts();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
		addAndUpdateProductSolutionsPage.clickOnOrgPageEditButton();
		addAndUpdateProductSolutionsPage.selectAllActivateCheckboxOfProducts();
		addAndUpdateProductSolutionsPage.clickOnOrgPageSaveButton();
		addAndUpdateProductSolutionsPage.verifyActiveSolutionsNames();
	}

}
