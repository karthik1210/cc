// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class CreateClientTests extends CommandCenterGenericTest {
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;

	@BeforeMethod
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to command center portal");
		cchomepage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
	}

	@Test(enabled = true, groups = 
		{"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Create a new active client using valid client details and verify success toaster message")
	public void testNewActiveClientCreation()  throws Exception {
		logStep("Enter valid details in mandatory fields");
		String clientName = testData.getProperty("new.client.name")
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
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages on create client popup")
	public void testErrorMessagesOnCreateClientPopUp() throws Exception {
		logStep("Enter already existing client name and sap id in client name and sap id field and verify error message");
		createClientPage.verifyClientNameAndSapUnique(testData.getProperty("duplicate.client.name"),
				testData.getProperty("duplicate.client.sap"));
		logStep("Remove all characters from client name and sap id field and verify required error message is displayed for mandatory fields");
		String clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		createClientPage.verifyRequiredErrorMessageForClientNameAndSapIdField(clientName, clientSapNumber);
		logStep("Verify error message when a user enters blank space in client name and the sap id field");
		createClientPage.verifyErrorMessageForClientNameAndSapIdFieldWhenUserEnterBlankSpace(" ", " ");
		logStep("Verify no items found message is displayed for client type and timezone field when user enter non existing client type and timezone");
		createClientPage.verifyNoItemsFoundMsgForClientTypeAndTimezoneField(
				testData.getProperty("new.client.type").concat(CCUtils.generateAlphaNumericString(4)),
				testData.getProperty("new.client.timezone").concat(CCUtils.generateAlphaNumericString(4)));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Create a new inactive test client and verify success toaster message")
	public void testNewInactiveClientCreation() throws Exception {
		logStep("Enter valid details in mandatory fields");
		String clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				"Pacific Standard Time");
		logStep("Select inactive radio button and click on test checkbox on the create client pop-up");
		createClientPage.selectInactiveRadiobutton();
		createClientPage.selectTestClient();
		logStep("Click on create button on the create client pop-up");
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify create client popup")
	public void testCreateClientPopUp() throws Exception {
		logStep("Verify create client popup");
		createClientPage.verifyCreateClientPageTitle();
		createClientPage.verifyCreateClientPageSubTitle();
		createClientPage.verifyClientNameLabelTextAndTextboxDefaultValue();
		createClientPage.verifySapIdLabelTextAndTextboxDefaultValue();
		createClientPage.verifyClientTypeLabelTextAndDropDownDefaultValue();
		commonMethods.getClientTypeDropdownValue();
		commonMethods.verifyClientTypeDropdownValues(testData.getProperty("qa.data.layer.service.base.url"));
		createClientPage.verifyTimezoneLabelTextAndDropDownDefaultValue();
		commonMethods.getTimezoneDropdownValue();
		commonMethods.verifyTimezoneDropdownValues();
		createClientPage.verifyStatusLabelTextAndRadioButtons();
		createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
		createClientPage.verifyTestLabelTextAndCheckboxDefaultValue();
		createClientPage.verifyCancelButtonIsDisplayed();
		createClientPage.verifyCloseWindowButtonIsDisplayed();
		createClientPage.verifyCreateButtonIsDisplayedAndIsDisabled();
	}
}
