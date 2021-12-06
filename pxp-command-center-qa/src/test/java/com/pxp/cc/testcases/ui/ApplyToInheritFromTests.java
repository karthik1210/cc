package com.pxp.cc.testcases.ui;

import java.awt.AWTException;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ApplyInheritPracticeSettingsPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;

public class ApplyToInheritFromTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public AddOrgNodePage addOrgNodePage;
	public ApplyInheritPracticeSettingsPage applyInheritPracticePage;
	public String practiceNameOne;
	public String practiceNameTwo;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Search Client " + testData.getProperty("apply.inherit.client")
				+ " in client directory and navigate to client's general settings page.");
		clientDirectoryPage.searchNode(testData.getProperty("apply.inherit.client"));
		clientDirectoryPage.clickSearchButton();
		generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
	}

	public void createAgnosticPracticeWithinClient() throws InterruptedException, AWTException {
		logStep("Test to create two agnostic practices within client.");
		String clientName = generalSettingsPage.getClientName();
		List<String> listOfSolutionsAdded = Arrays.asList(testData.getProperty("solution.chat"),
				testData.getProperty("solution.engage"));
		List<String> listOfSolutions = Arrays.asList(testData.getProperty("solution.pss"),
				testData.getProperty("solution.portal"));
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(clientName);
		practiceNameOne = ccuser.getPracticeName() + CCUtils.generateAlphaNumericString(4);
		logStep("Creating Practice with name : "+practiceNameOne+" with solutions "+listOfSolutionsAdded.toString());
		addOrgNodePage.createNewOrgNode(practiceNameOne, ccuser.getPracticeSapId(), ccuser.getPracticeTimezone(),
				ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.selectFewSolutions(listOfSolutionsAdded);
		addOrgNodePage.clickOnSaveAndAddButton();
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameOne);
		generalSettingsPage.verifySolutionActiveWithinThisContainer();
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutions);
		practiceNameTwo = ccuser.getPracticeName() + CCUtils.generateAlphaNumericString(4);
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(clientName);
		logStep("Creating Practice with name : "+practiceNameTwo+" with solutions "+listOfSolutions.toString());
		addOrgNodePage.createNewOrgNode(practiceNameTwo, ccuser.getPracticeSapId(), ccuser.getPracticeTimezone(),
				ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.selectFewSolutions(listOfSolutions);
		generalSettingsPage = addOrgNodePage.clickOnSaveAndAddButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.verifySolutionActiveWithinThisContainer();
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameTwo);
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutions);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Apply to' option is working correctly when applied solution from one practice to another.")
	public void testVerifyApplyToFunctionalityToSinglePracticestVerifyApplyToFunctionalityToSinglePractice() throws InterruptedException, AWTException {
		createAgnosticPracticeWithinClient();
		logStep("Applyinng solutions of practice "+practiceNameTwo+" to practice "+practiceNameOne);
		generalSettingsPage.clickOnShortCutButton();
		applyInheritPracticePage = generalSettingsPage.clickOnApplyToButton();
		applyInheritPracticePage.verifyApplyPracticeLabel();
		applyInheritPracticePage.clickPracticeDropdown();
		applyInheritPracticePage.selectDesiredPractice(practiceNameOne);
		applyInheritPracticePage.clickToApplyButton();
		generalSettingsPage = applyInheritPracticePage.clickToSaveButton();
	}

}
