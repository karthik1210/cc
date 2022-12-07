//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import java.util.ArrayList;
import java.util.List;
import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class AddResourceImageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String resourceName, firstName;

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

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify Resource image is successfully uploaded")
	public void testAddResourceImage() throws Exception {
		ngHierarchyClientCreate();
		createProvider();
		createResource();
		ccui.resourceDetailsPage().clickToSearchButton();
		List<String> listOfResource = new ArrayList<String>();
		listOfResource.add(resourceName);
		ccui.resourceDetailsPage().verifySearchResourceFound(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().uploadResourceImage();
		runOnce = false;
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify Resource image is successfully removed")
	public void testAddResourceImageAndRemoveImage() throws Exception {
		ngHierarchyClientCreate();
		createProvider();
		createResource();
		ccui.resourceDetailsPage().clickToSearchButton();
		List<String> listOfResource = new ArrayList<String>();
		listOfResource.add(resourceName);
		ccui.resourceDetailsPage().verifySearchResourceFound(resourceName);
		ccui.resourceDetailsPage().uploadResourceImage();
		ccui.resourceDetailsPage().removeResourceImage();
		ccui.resourceDetailsPage().verifyToasterMessage("Success! Successfully removed image");
		runOnce = false;
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Verify invalid file type error is displayed when uploading invalid format image file")
	public void testErrorInvalidFileType() throws Exception {
		ngHierarchyClientCreate();
		createProvider();
		createResource();
		ccui.resourceDetailsPage().clickToSearchButton();
		List<String> listOfResource = new ArrayList<String>();
		listOfResource.add(resourceName);
		ccui.resourceDetailsPage().verifySearchResourceFound(resourceName);
		ccui.resourceDetailsPage().uploadResourceInvalidImage();
		ccui.resourceDetailsPage().verifyInvalidFileTypeErrorMessage();
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
			ccui.getPrivacyPolicySettingsPage()
					.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		}
	}

	public void createProvider() throws Exception {
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.addNewProviderPage = new AddNewProviderPage(driver);
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Create new Provider using valid test data");
		firstName = "Provi" + CCUtils.generateAlphaNumericString(10);
		String lastName = CCUtils.generateAlphaNumericString(5);
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		ccui.addNewProviderPage().verifyAddButtonIsEnabled();
		ccui.addNewProviderPage().clickOnAddProviderButton();
		ccui.addNewProviderPage().enterFirstName(firstName);
		ccui.addNewProviderPage().enterLastName(lastName);
		ccui.addNewProviderPage().enterCredentials(credentials);
		ccui.addNewProviderPage().enterExternalId(externalId);
		ccui.addNewProviderPage().selectGenderValue("Male");
		ccui.addNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.addNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
		logStep("Verify Providers And Resources tab is displayed");
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
	}

	public void createResource() throws Exception {
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
		ccui.addNewResourcePage().clickOnResourcesTab();
		ccui.addNewResourcePage().verifyAddButtonIsEnabled();
		ccui.addNewResourcePage().clickOnAddResourceButton();
		logStep("Create new Resources using valid test data");
		resourceName = "Resourr" + CCUtils.generateAlphaNumericString(10);
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.addNewResourcePage().enterResourceName(resourceName);
		ccui.addNewResourcePage().enterExternalId(externalIds);
		ccui.addNewResourcePage().selectResourceType("Person");
		ccui.addNewResourcePage().enterConnectedProvider(firstName);
		ccui.addNewResourcePage().clickFirstInAutoSuggestion();
		ccui.addNewResourcePage().verifySaveAndAddButtonIsEnabled();
		ccui.addNewResourcePage().clickOnSaveAndAddButton();
	}
}
