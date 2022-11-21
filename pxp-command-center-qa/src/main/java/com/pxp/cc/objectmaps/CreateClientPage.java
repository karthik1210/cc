// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class CreateClientPage extends CCMainPage {

	public CreateClientPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[contains(text(),'+ NEW')]")
	private WebElement buttonNew;

	@FindBy(xpath = "//button[normalize-space()='Create Client']")
	private WebElement buttonCreateClient;

	@FindBy(xpath = "//span[@id='clientCancelBtn']")
	private WebElement buttonCancelClient;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement buttonConfirm;

	@FindBy(xpath = "//input[@name='name']")
	private WebElement textboxClientName;

	@FindBy(xpath = "//input[@name='sapNumber']")
	private WebElement textboxSapId;

	@FindBy(id = "clientTypeSelect")
	private WebElement dropdownClientType;

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//input[@value='true']")
	private WebElement radioBtnActive;

	@FindBy(id = "clientInactiveInput")
	private WebElement radioBtnInactive;

	@FindBy(xpath = "//input[@value='test']")
	private WebElement checkboxTest;

	@FindBy(id = "clientCheckName")
	private WebElement errorUniqueClientName;

	@FindBy(xpath = "//div[text()='Client name is required']")
	private WebElement errorRequiredClientName;

	@FindBy(xpath = "//div[text()='SAP ID is required']")
	private WebElement errorRequiredSapId;

	@FindBy(xpath = "//mat-dialog-container[@role='dialog']")
	private WebElement dialogCreateClient;

	@FindBy(xpath = "//div[normalize-space()='SAP ID already in use']")
	private WebElement errorUniqueSap;

	@FindBy(id = "clientCheckNameEmpty")
	private WebElement errorEmptyClientName;

	@FindBy(id = "clientCheckSapEmpty")
	private WebElement errorEmptyClientSapId;

	@FindBy(id = "clientModelLabel")
	private WebElement labelPageTitle;

	@FindBy(id = "clientTopTitle")
	private WebElement labelPageSubTitle;

	@FindBy(id = "clienNameLabel")
	private WebElement labelClientName;

	@FindBy(id = "clientSapLabel")
	private WebElement labelClientSapId;

	@FindBy(id = "clientTypeLabel")
	private WebElement labelClientType;

	@FindBy(id = "timezone-label")
	private WebElement labelTimezone;

	@FindBy(id = "clientStatusLable")
	private WebElement labelStatus;

	@FindBy(id = "clientTestLabel")
	private WebElement labelTest;

	@FindBy(xpath = "//label[text()='Active']")
	private WebElement labelActive;

	@FindBy(xpath = "//label[text()='Inactive']")
	private WebElement labelInactive;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement buttonCloseWindow;

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTextboxTimezone;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement searchTextbox;

	public void clickCreateClient() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		buttonNew.click();
		log("Clicked on new button");
		CCUtils.verifyAndWaitForElement(buttonCreateClient, driver);
		buttonCreateClient.click();
		log("Clicked on Create client button");
	}

	public String verifyClientPopupText() {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		CCUtils.verifyAndWaitForElement(dropdownClientType, driver);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		CCUtils.verifyAndWaitForElement(radioBtnActive, driver);
		CCUtils.verifyAndWaitForElement(radioBtnInactive, driver);
		CCUtils.verifyAndWaitForElement(checkboxTest, driver);
		String popuptext = dialogCreateClient.getText();
		buttonCancelClient.click();
		log("Clicked on Cancel Button");
		return popuptext;
	}

	private String xpathRequiredClientType = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private String xpathRequiredNewClientTimezone = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForClientType(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectClientType(String clientTypeName) {
		WebElement clientType = prepareDynamicXpathForClientType(xpathRequiredClientType, clientTypeName);
		CCUtils.verifyAndWaitForElement(clientType, driver);
		clientType.click();
		log("Clicked on : " + clientTypeName);
	}

	public void selectTimeZone(String newTimezone) {
		WebElement newClientTimezone = prepareDynamicXpathForClientType(xpathRequiredNewClientTimezone, newTimezone);
		newClientTimezone.click();
		log("Clicked on : " + newTimezone);
	}

	public void createNewClient(String newClientName, String newSAPID, String clientType, String newTimezone)
			throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.sendKeys(newClientName);
		log("Entered text in client name field : " + newClientName);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(newSAPID);
		log("Entered text in SAP ID field : " + newSAPID);
		CCUtils.verifyForElementToBeClickable(dropdownClientType, driver);
		CCUtils.verifyForElementToBeClickable(dropdownClientType, driver);
		dropdownClientType.click();
		log("clicked to dropdown of client type");
		selectClientType(clientType);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		log("click to dropdown of Timezone");
		dropdownTimezone.click();
		selectTimeZone(newTimezone);
	}

	public void clickConfirmPopup() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		javascriptClick(buttonConfirm);
		log("Clicked on create button");
	}

	public void selectInactiveRadiobutton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(radioBtnInactive, driver);
		radioBtnInactive.click();
		log("Clicked on inactive radio button");
	}

	public void selectTestClient() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkboxTest, driver);
		javascriptClick(checkboxTest);
		log("Clicked on Test checkbox");
	}

	public void verifyClientNameAndSapUnique(String duplicateClientName, String duplicateSAP) {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.sendKeys(duplicateClientName);
		log("Entered text in client name field is " + duplicateClientName);
		CCUtils.verifyAndWaitForElement(errorUniqueClientName, driver);
		CCUtils.assertEquals(errorUniqueClientName.getText().trim(), "Client name already in use");
		log("Error Message displayed for duplicate client name is :" + errorUniqueClientName.getText().trim());
		textboxClientName.clear();
		textboxClientName.sendKeys("new " + duplicateClientName);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(duplicateSAP);
		log("Entered text in SAP ID field : " + duplicateSAP);
		CCUtils.verifyAndWaitForElement(errorUniqueSap, driver);
		CCUtils.assertEquals(errorUniqueSap.getText().trim(), "SAP ID already in use");
		log("Error Message displayed for duplicate client sap id is :" + errorUniqueSap.getText().trim());
		textboxSapId.clear();
	}

	public void verifyActiveRadioButtonIsByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioBtnActive, driver);
		log("Verifying active radio button is by default selected on the create client popup");
		CCUtils.assertTrue(radioBtnActive.isSelected(), "Active radio button is not by default selected");
	}

	public void verifyRequiredErrorMessageForClientNameAndSapIdField(String clientName, String sapId) {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.sendKeys(clientName);
		log("Entered text in client name field is " + clientName);
		log("Remove all characters from client name field");
		CCUtils.clearTextboxField(textboxClientName);
		CCUtils.verifyAndWaitForElement(errorRequiredClientName, driver);
		CCUtils.assertEquals(errorRequiredClientName.getText().trim(), "Client name is required");
		log("Error Message displayed for client name field is :" + errorRequiredClientName.getText().trim());
		textboxClientName.sendKeys("new " + clientName);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(sapId);
		log("Entered text in SAP ID field : " + sapId);
		log("Remove all characters from sap id field");
		CCUtils.clearTextboxField(textboxSapId);
		CCUtils.verifyAndWaitForElement(errorRequiredSapId, driver);
		CCUtils.assertEquals(errorRequiredSapId.getText().trim(), "SAP ID is required");
		log("Error Message displayed for sap id field is :" + errorRequiredSapId.getText().trim());
	}

	public void verifyErrorMessageForClientNameAndSapIdFieldWhenUserEnterBlankSpace(String clientName, String sapId) {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.clear();
		textboxClientName.sendKeys(clientName);
		log("Entered text in client name field is " + clientName);
		CCUtils.verifyAndWaitForElement(errorEmptyClientName, driver);
		CCUtils.assertEquals(errorEmptyClientName.getText().trim(), "Client name cannot be empty");
		log("Error Message displayed for client name field is :" + errorEmptyClientName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(sapId);
		log("Entered text in SAP ID field : " + sapId);
		CCUtils.verifyAndWaitForElement(errorEmptyClientSapId, driver);
		CCUtils.assertEquals(errorEmptyClientSapId.getText().trim(), "SAP ID cannot be empty");
		log("Error Message displayed for sap id field is :" + errorEmptyClientSapId.getText().trim());
	}

	public void verifyCreateClientPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		log("Verifying create client page title");
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Create New Client");
	}

	public void verifyCreateClientPageSubTitle() {
		CCUtils.verifyAndWaitForElement(labelPageSubTitle, driver);
		log("Verifying create client page sub title");
		CCUtils.assertEquals(labelPageSubTitle.getText().trim(),
				"Fill out the following fields to create a new client.");
	}

	public void verifyClientNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelClientName, driver);
		log("Verifying client name label text");
		CCUtils.assertTrue(labelClientName.isDisplayed(), "Client name label is not displayed on create client popup");
		CCUtils.assertEquals(labelClientName.getText().trim(), "Client Name *");
		log("Client name label text is " + labelClientName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		log("Verifying Input text here placeholder is displayed for client name textbox field");
		CCUtils.assertEquals(textboxClientName.getAttribute("placeholder").trim(), "Input text here");
		log("Box text for client name textbox field is " + textboxClientName.getAttribute("placeholder").trim());
	}

	public void verifySapIdLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelClientSapId, driver);
		log("Verifying sap id label text");
		CCUtils.assertTrue(labelClientSapId.isDisplayed(), "Sap id label is not displayed on create client popup");
		CCUtils.assertEquals(labelClientSapId.getText().trim(), "SAP ID *");
		log("Sap id label text is " + labelClientSapId.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		log("Verifying Input salesforce SAP ID placeholder is displayed for sap id textbox field");
		CCUtils.assertEquals(textboxSapId.getAttribute("placeholder").trim(), "Input salesforce SAP ID");
		log("Box text for sap id textbox field is " + textboxSapId.getAttribute("placeholder").trim());
	}

	public void verifyClientTypeLabelTextAndDropDownDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelClientType, driver);
		log("Verifying client type label text");
		CCUtils.assertTrue(labelClientType.isDisplayed(), "client type label is not displayed on create client popup");
		CCUtils.assertEquals(labelClientType.getText().trim(), "Client Type *");
		log("Client type label text is " + labelClientType.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownClientType, driver);
		log("Verifying select placeholder is displayed for client type dropdown field");
		CCUtils.assertEquals(dropdownClientType.getAttribute("placeholder").trim(), "Select");
		log("Box text for client type dropdown field is " + dropdownClientType.getAttribute("placeholder").trim());
	}

	public void verifyTimezoneLabelTextAndDropDownDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelTimezone, driver);
		log("Verifying timezone label text");
		CCUtils.assertTrue(labelTimezone.isDisplayed(), "Timezone label is not displayed on create client popup");
		CCUtils.assertEquals(labelTimezone.getText().trim(), "Timezone*");
		log("Timezone label text is " + labelTimezone.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownTextboxTimezone, driver);
		log("Verifying select placeholder is displayed for client timezone dropdown field");
		CCUtils.assertEquals(dropdownTextboxTimezone.getAttribute("placeholder").trim(), "Select");
		log("Box text for client timezone dropdown field is "
				+ dropdownTextboxTimezone.getAttribute("placeholder").trim());
	}

	public void verifyStatusLabelTextAndRadioButtons() {
		CCUtils.verifyAndWaitForElement(labelStatus, driver);
		log("Verifying status label text");
		CCUtils.assertTrue(labelStatus.isDisplayed(), "Status label is not displayed on create client popup");
		CCUtils.assertEquals(labelStatus.getText().trim(), "Status *");
		log("Status label text is " + labelStatus.getText().trim());
		log("Verifying active label and radio button are displayed on create client popup");
		CCUtils.assertEquals(labelActive.getText().trim(), "Active");
		CCUtils.assertTrue(radioBtnActive.isDisplayed(), "Active radiobutton is not present on create client popup");
		log("Verifying inactive label and radio button are displayed on create client popup");
		CCUtils.assertEquals(labelInactive.getText().trim(), "Inactive");
		CCUtils.assertTrue(radioBtnInactive.isDisplayed(),
				"Inactive radiobutton is not present on create client popup");
	}

	public void verifyTestLabelTextAndCheckboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelTest, driver);
		log("Verifying test label text");
		CCUtils.assertTrue(labelTest.isDisplayed(), "Test label is not displayed on create client popup");
		CCUtils.assertEquals(labelTest.getText().trim(), "Test (Cannot be undone once created)");
		log("Test label text is " + labelTest.getText().trim());
		CCUtils.verifyAndWaitForElement(checkboxTest, driver);
		log("Verifying test checkbox is displayed on screen and is not selected");
		CCUtils.assertTrue(checkboxTest.isDisplayed(), "Test checkbox is not displayed on screen");
		CCUtils.assertEquals(checkboxTest.isSelected(), false);
	}

	public void verifyCloseWindowButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCloseWindow, driver);
		log("Verifying close window button is displayed on create client popup");
		CCUtils.assertTrue(buttonCloseWindow.isDisplayed(),
				"Close window button is not displayed on create client popup");
		CCUtils.assertEquals(buttonCloseWindow.getText().trim(), "clear");
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancelClient, driver);
		log("Verifying cancel button is displayed on create client popup");
		CCUtils.assertTrue(buttonCancelClient.isDisplayed(), "Cancel button is not displayed on create client popup");
		log("Verifying cancel button is enabled");
		CCUtils.assertTrue(buttonCancelClient.isEnabled(), "Cancel button is disabled");
		log("Verifying cancel button text");
		CCUtils.assertEquals(buttonCancelClient.getText().trim(), "Cancel");
	}

	public void verifyCreateButtonIsDisplayedAndIsDisabled() {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		log("Verifying create button is displayed on create client popup");
		CCUtils.assertTrue(buttonConfirm.isDisplayed(), "Create button is not displayed on create client popup");
		log("Verifying create button is disabled");
		CCUtils.assertEquals(buttonConfirm.isEnabled(), false);
		log("Verifying create button text");
		CCUtils.assertEquals(buttonConfirm.getText().trim(), "Create");
	}

	public void verifyNoItemsFoundMsgForClientTypeAndTimezoneField(String nonExistingClientType,
			String nonExistingClientTimezone) {
		CCUtils.verifyAndWaitForElement(dropdownClientType, driver);
		dropdownClientType.click();
		searchTextbox.sendKeys(nonExistingClientType);
		log("Entered text in client type field : " + nonExistingClientType);
		verifyNoItemsFound();
		searchTextbox.clear();
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		searchTextbox.sendKeys(nonExistingClientTimezone);
		log("Entered text in timezone field : " + nonExistingClientTimezone);
		verifyNoItemsFound();
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}

}
