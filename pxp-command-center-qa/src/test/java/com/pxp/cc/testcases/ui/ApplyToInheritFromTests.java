// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.testcases.ui;

import java.awt.AWTException;
import java.util.ArrayList;
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
import com.pxp.cc.util.CommonMethods;

public class ApplyToInheritFromTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public AddOrgNodePage addOrgNodePage;
	public ApplyInheritPracticeSettingsPage applyInheritPracticePage;
	public CommonMethods commonMethods;
	public String practiceNameOne;
	List<String> listOfSolutionsPracOne;
	public String practiceNameTwo;
	List<String> listOfSolutionsPracTwo;
	public String practiceNameThree;
	List<String> appliedPractices = new ArrayList<String>();

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Search Client " + testData.getProperty("apply.inherit.client")
				+ " in client directory and navigate to client's general settings page.");
		clientDirectoryPage.searchNode(testData.getProperty("apply.inherit.client"));
		clientDirectoryPage.clickSearchButton();
		generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
	}

	public void createAgnosticPractices() throws InterruptedException, AWTException {
		logStep("Test to create two agnostic practices within client.");
		String clientName = generalSettingsPage.getClientName();
		listOfSolutionsPracOne = Arrays.asList(testData.getProperty("solution.chat"),
				testData.getProperty("solution.engage"));
		practiceNameOne = ccuser.getPracticeName() + CCUtils.generateAlphaNumericString(4);
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		generalSettingsPage = addOrgNodePage.createPracticeWithSolutions(practiceNameOne, clientName,
				listOfSolutionsPracOne, ccuser.getPracticeSapId(), ccuser.getPracticeTimezone(),
				ccuser.getPracticeSolutionDisplayName());
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.verifySolutionActiveWithinThisContainer();
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameOne);
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutionsPracOne);
		practiceNameThree = ccuser.getPracticeName() + CCUtils.generateAlphaNumericString(4);
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		generalSettingsPage = addOrgNodePage.createPracticeWithSolutions(practiceNameThree, clientName,
				listOfSolutionsPracOne, ccuser.getPracticeSapId(), ccuser.getPracticeTimezone(),
				ccuser.getPracticeSolutionDisplayName());
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.verifySolutionActiveWithinThisContainer();
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameThree);
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutionsPracOne);
		practiceNameTwo = ccuser.getPracticeName() + CCUtils.generateAlphaNumericString(4);
		listOfSolutionsPracTwo = Arrays.asList(testData.getProperty("solution.pss"),
				testData.getProperty("solution.portal"));
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		generalSettingsPage = addOrgNodePage.createPracticeWithSolutions(practiceNameTwo, clientName,
				listOfSolutionsPracTwo, ccuser.getPracticeSapId(), ccuser.getPracticeTimezone(),
				ccuser.getPracticeSolutionDisplayName());
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.verifySolutionActiveWithinThisContainer();
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutionsPracTwo);
		appliedPractices.addAll(listOfSolutionsPracTwo);
		appliedPractices.addAll(listOfSolutionsPracOne);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Apply to' functionality whether solutions is applied from one practice to another.")
	public void testVerifyApplyToFunctionalityToSinglePracticestVerifyApplyToFunctionalityToSinglePractice()
			throws InterruptedException, AWTException {
		createAgnosticPractices();
		logStep("Applying solutions of practice " + practiceNameTwo + " to practice " + practiceNameOne);
		generalSettingsPage.searchNode(practiceNameTwo);
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameTwo);
		generalSettingsPage.clickOnShortCutButton();
		applyInheritPracticePage = generalSettingsPage.clickOnApplyToButton();
		applyInheritPracticePage.verifyApplyPracticeLabel();
		applyInheritPracticePage.clickPracticeDropdown();
		applyInheritPracticePage.selectDesiredPractice(practiceNameOne);
		applyInheritPracticePage.clickToApplyButton();
		generalSettingsPage = applyInheritPracticePage.clickToSaveButton();
		commonMethods = new CommonMethods(driver);
		logStep("Verifying toaster message while we applying solutions to practice.");
		commonMethods
				.verifyToasterMessage("Solutions settings applied from " + practiceNameTwo + " to " + practiceNameOne);
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(practiceNameOne);
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameOne);
		logStep("Verifying all solutions which are applied.");
		generalSettingsPage.verifyListOfActiveSolutions(appliedPractices);
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutionsPracTwo);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Inherit to' functionality whether solutions is inherited from selected practice to another.")
	public void testVerifyInheritFromFunctionalityToSinglePracticestVerifyApplyToFunctionalityToSinglePractice()
			throws InterruptedException, AWTException {
		createAgnosticPractices();
		logStep("Inheriting solutions from practice " + practiceNameOne + " to practice " + practiceNameTwo);
		generalSettingsPage.clickOnShortCutButton();
		applyInheritPracticePage = generalSettingsPage.clickOnInheritFromButton();
		applyInheritPracticePage.verifyInheritPracticeLabel();
		applyInheritPracticePage.clickPracticeDropdown();
		applyInheritPracticePage.selectDesiredPractice(practiceNameOne);
		applyInheritPracticePage.clickToApplyButton();
		generalSettingsPage = applyInheritPracticePage.clickToSaveButton();
		commonMethods = new CommonMethods(driver);
		logStep("Verifying toaster message while we inheriting solutions to practice.");
		commonMethods.verifyToasterMessage(
				"Solutions settings inherited from " + practiceNameOne + " to " + practiceNameTwo);
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(practiceNameTwo);
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameTwo);
		logStep("Verifying all solutions which are inherited.");
		generalSettingsPage.verifyListOfActiveSolutions(appliedPractices);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Inherit to' functionality when same solutions inherited from one practice to another.")
	public void testVerifyInheritFromFunctionalityOfPracticesWithSameSolutions()
			throws InterruptedException, AWTException {
		createAgnosticPractices();
		logStep("Inheriting solutions from practice " + practiceNameOne + " to practice " + practiceNameThree);
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(practiceNameThree);
		generalSettingsPage.clickOnShortCutButton();
		applyInheritPracticePage = generalSettingsPage.clickOnInheritFromButton();
		applyInheritPracticePage.verifyInheritPracticeLabel();
		applyInheritPracticePage.clickPracticeDropdown();
		applyInheritPracticePage.selectDesiredPractice(practiceNameOne);
		applyInheritPracticePage.clickToApplyButton();
		generalSettingsPage = applyInheritPracticePage.clickToSaveButton();
		commonMethods = new CommonMethods(driver);
		logStep("Verifying toaster message while we inheriting solutions to practice.");
		commonMethods.verifyToasterMessage(
				"Solutions settings inherited from " + practiceNameOne + " to " + practiceNameThree);
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(practiceNameThree);
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameThree);
		logStep("Verifying all solutions which are inherited.");
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutionsPracOne);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test 'Apply to' functionality when same solutions applied from one practice to another.")
	public void testVerifyApplyFromFunctionalityOfPracticesWithSameSolutions()
			throws InterruptedException, AWTException {
		createAgnosticPractices();
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(practiceNameThree);
		logStep("Applying solutions from practice " + practiceNameThree + " to practice " + practiceNameOne);
		generalSettingsPage.clickOnShortCutButton();
		applyInheritPracticePage = generalSettingsPage.clickOnApplyToButton();
		applyInheritPracticePage.verifyApplyPracticeLabel();
		applyInheritPracticePage.clickPracticeDropdown();
		applyInheritPracticePage.selectDesiredPractice(practiceNameOne);
		applyInheritPracticePage.clickToApplyButton();
		generalSettingsPage = applyInheritPracticePage.clickToSaveButton();
		commonMethods = new CommonMethods(driver);
		logStep("Verifying toaster message while we applying solutions to practice.");
		commonMethods.verifyToasterMessage(
				"Solutions settings applied from " + practiceNameThree + " to " + practiceNameOne);
		generalSettingsPage.clearSearchText();
		generalSettingsPage.searchNode(practiceNameOne);
		CCUtils.assertEquals(generalSettingsPage.getOrgName(), practiceNameOne);
		logStep("Verifying all solutions which are applied.");
		generalSettingsPage.verifyListOfActiveSolutions(listOfSolutionsPracOne);
	}
}
