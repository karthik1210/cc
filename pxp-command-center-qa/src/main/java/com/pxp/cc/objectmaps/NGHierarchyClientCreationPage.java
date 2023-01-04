// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.util.CCUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NGHierarchyClientCreationPage extends CCMainPage {

	public NGHierarchyClientCreationPage(WebDriver driver) {
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

	@FindBy(xpath = "//input[@id='clientNameInput']")
	private WebElement textboxClientName;

	@FindBy(xpath = "//input[@name='sapNumber']")
	private WebElement textboxSapId;

	@FindBy(id = "clientTypeSelect")
	private WebElement dropdownClientType;

	@FindBy(xpath = "//ng-select[@id='timezoneSelect']//span[@class='ng-arrow-wrapper']")
	private WebElement dropdownTimezone;

	@FindBy(id = "clientActiveInput-input")
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

	@FindBy(xpath = "//div[text()='Active ']")
	private WebElement labelActive;

	@FindBy(xpath = "//div[normalize-space(text())='Inactive']")
	private WebElement labelInactive;

	@FindBy(xpath = "//span[normalize-space(text())='clear']")
	private WebElement buttonCloseWindow;

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTextboxTimezone;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement searchTextbox;

	@FindBy(how = How.XPATH, using = "//button[@class='mat-focus-indicator mat-menu-trigger mat-mini-fab mat-button-base mat-accent']//span[@class='mat-button-wrapper']")
	private WebElement userFullName;

	@FindBy(how = How.XPATH, using = "//mat-icon[normalize-space()='link']")
	private WebElement ssoLinks;

	@FindBy(id = "headerBtn")
	private WebElement userIcon;

	@FindBy(how = How.XPATH, using = "//h1[@class='.page-title']")
	private WebElement pxpCommandCenterLabel;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Profile Settings']")
	private WebElement profileSetting;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Help']")
	private WebElement userHelp;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Log out']")
	private WebElement userLogout;

	@FindBy(how = How.XPATH, using = "//div[@class='cdk-overlay-container']//a")
	private List<WebElement> ssoLinkList;

	@FindBy(id = "directorySearchInput")
	private WebElement textboxSearchClientDirectory;

	@FindBy(id = "directoryToggleIcon")
	private WebElement buttonSearchClientDirectory;

	@FindBy(id = "directoryTypeInput")
	private WebElement dropdownClientFilter;

	@FindBy(id = "directoryClearButton")
	private WebElement buttonClearClientFilter;

	@FindBy(className = "confirmbutton")
	private WebElement buttonApplyClientFilter;

	@FindBy(xpath = "//div[@class='tableborder']")
	private List<WebElement> tableSearchResult;

	@FindBy(xpath = "//td[@id='name-row']")
	private List<WebElement> tableClientNameInSearchResult;

	@FindBy(xpath = "//td[@id='no-result']")
	private WebElement noResultFoundInClientDirectory;

	@FindBy(xpath = "//td[@type='directoryTypeRow']")
	private List<WebElement> tableClientTypeInSearchResult;

	@FindBy(id = "treeNodeName")
	private List<WebElement> nodeNameInGeneralSetting;

	@FindBy(id = "name-row")
	private WebElement clientTableRow;

	@FindBy(xpath = "//mat-icon[@id='directoryGradeIcon']")
	private List<WebElement> listOfFavClients;

	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']//mat-icon[@id='star-icon']")
	private List<WebElement> listOfNonFavClients;

	@FindBy(id = "cmdCircleIcon")
	private WebElement iconCommandCenterUsers;

	@FindBy(id = "mat-select-0")
	private WebElement defaultItemsPerPage;

	@FindBy(xpath = "//div[@class='mat-paginator-range-label']")
	private WebElement displayPagination;

	@FindBy(xpath = "//mat-select[@role='combobox']//following::div[starts-with(@class,'mat-select-arrow')][1]")
	private WebElement itemsPerPageDropdown;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private List<WebElement> listOfItemsPerPage;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']")
	private List<WebElement> listOfRowsClientTable;

	@FindBy(xpath = "//button[@aria-label='Next page']")
	private WebElement buttonNextPage;

	@FindBy(xpath = "//button[@aria-label='Last page']")
	private WebElement buttonLastPage;

	@FindBy(xpath = "//button[@aria-label='Previous page']")
	private WebElement buttonPreviousPage;

	@FindBy(xpath = "//button[@aria-label='First page']")
	private WebElement buttonFirstPage;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'20')]")
	private WebElement itemsPerPageTwenty;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'50')]")
	private WebElement itemsPerPageFifty;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'100')]")
	private WebElement itemsPerPageHundred;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'200')]")
	private WebElement itemsPerPageTwoHundred;

	@FindBy(id = "directoryTitle")
	private WebElement labelPageTitleDirectory;

	@FindBy(xpath = "//mat-icon[@id='star-icon']")
	private WebElement unFavoritedStarIcon;

	@FindBy(xpath = "//mat-icon[@id='star-icon']")
	private WebElement FavoritedStarIcon;

	@FindBy(xpath = "//div[@id='cdk-describedby-message-16']")
	private WebElement tooltipMsg;

	@FindBy(id = "displayClientName")
	private WebElement clientNodeTitle;

	@FindBy(id = "addNodeTitle")
	private WebElement lablePageTitle;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement crossIcon;

	@FindBy(id = "addNodeCancel")
	private WebElement buttonCancelInAddNewNode;

	@FindBy(id = "addNodeSave")
	private WebElement buttonSaveAndAdd;

	@FindBy(id = "parent-label")
	private WebElement labelParentEntity;

	@FindBy(id = "addNodeClientDisp")
	private WebElement textboxParentEntityNameAtSystem;

	@FindBy(xpath = "//ng-select[@id='addNodeEnterpriseSelect'] | //ng-select[@id='addNodePracticeLabel']")
	private WebElement dropdownParentEntityName;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textboxParentEntityName;

	@FindBy(xpath = "//span[text()='Practice Name *']")
	private WebElement labelPracticeName;

	@FindBy(xpath = "//span[text()='System Name *']")
	private WebElement labelSystemName;

	@FindBy(xpath = "//span[text()='Enterprise Name *']")
	private WebElement labelEnterpriseName;

	@FindBy(id = "addNodeNameInput")
	private WebElement textboxName;

	@FindBy(xpath = "//span[text()='Solutions Display Name']")
	private WebElement labelSolutionDisplayName;

	@FindBy(id = "addNodeSolutionInput")
	private WebElement textboxSolutionDisplayName;

	@FindBy(xpath = "//span[text()='SAP ID']")
	private WebElement labelSapId;

	@FindBy(id = "addNodeSapInput")
	private WebElement textboxSapIdAddNode;

	/*
	 * @FindBy(id = "timezone-label") private WebElement labelTimezone;
	 */

	/*
	 * @FindBy(id = "timezoneSelect") private WebElement dropdownTimezone;
	 */

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textboxTimezone;

	@FindBy(xpath = "//label[text()='Status *']")
	private WebElement labelStatusAddNode;

	@FindBy(xpath = "//input[@id='addNodeActiveRadio-input']")
	private WebElement radioButtonActive;

	@FindBy(xpath = "//input[@id='addNodeInactiveRadio-input']")
	private WebElement radioButtonInactive;

	@FindBy(xpath = "//ng-select[@placeholder='Search/Select']")
	private WebElement placeholderParentEntityDropdown;

	@FindBy(xpath = "//ng-select[@placeholder='Select']")
	private WebElement placeholderTimezoneDropdown;

	@FindBy(xpath = "//span[text()='Which solutions will this practice use?']")
	private WebElement labelSolution;

	@FindBy(xpath = "//input[@type='checkbox']")
	private List<WebElement> checkboxSolution;

	@FindBy(xpath = "//div[text()=' Maximum characters reached ']")
	private WebElement errorMaximumCharacter;

	@FindBy(xpath = "//div[@class='text-danger ng-star-inserted']//div[@class='ng-star-inserted']")
	private WebElement errorNameRequired;

	@FindBy(xpath = "(//div[@class='text-danger ng-star-inserted'])[3]")
	private WebElement errorInvalidSap;

	@FindBy(xpath = "//div[contains(text(),'Parent Entity is required')]")
	private WebElement errorParentNameRequired;

	@FindBy(xpath = "//div[@aria-selected='true']/span")
	private WebElement selectedTimezoneValue;

	/*
	 * @FindBy(xpath = "//div[text()='No items found']") private WebElement
	 * textNoItemsFound;
	 */

	@FindBy(xpath = "//div[@role='option']")
	private List<WebElement> timezoneOptionList;

	@FindBy(xpath = "//span[@class='mat-checkbox-label']")
	private List<WebElement> labelSolutionList;

	@FindBy(xpath = "//span[@class='mat-checkbox-label']//preceding::div[@class='mat-checkbox-inner-container']")
	private List<WebElement> particularSolutionCheckbox;

	/*
	 * @FindBy(xpath = "//label[text()='Active']") private WebElement labelActive;
	 */

	/*
	 * @FindBy(xpath = "//label[text()='Inactive']") private WebElement
	 * labelInactive;
	 */

	@FindBy(id = "manageClientLabel")
	private WebElement btnManageClient;

	@FindBy(xpath = "//button[contains(text(),' + ADD SYSTEM')]")
	private WebElement btnAddSystem;

	@FindBy(id = "addNodeNameInput")
	private WebElement enterSystemName;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement enterEhrSystem;

	@FindBy(xpath = "//div[@role='option']")
	private WebElement selectOptionSystem;

	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement enterPmSystem;

	@FindBy(id = "addNodeSave")
	private WebElement btnSave;

	@FindBy(xpath = "(//input[@type='text'])[4]")
	private WebElement enterTimezone;

	@FindBy(xpath = "//button[contains(text(),'+ ADD PRACTICE')]")
	private WebElement btnAddPractice;

	@FindBy(id = "addNodeNameInput")
	private WebElement enterPracticeName;

	@FindBy(xpath = "(//input[@type='text'])[5]")
	private WebElement enterPmPractice;

	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement enterEhrPractice;

	@FindBy(xpath = "//div[@class='mat-tree-node-click bordernode']//mat-icon[@role='img'][normalize-space()='keyboard_arrow_right']")
	private WebElement expandArrow;

	@FindBy(xpath = "//div[@class='mat-tree-node2 bordernode']/button/span/mat-icon")
	private WebElement expandSystemArrow;

	@FindBy(xpath = "//div[@class='mat-tree-node2 treenodespace']/button/span/mat-icon")
	private WebElement expandEnterpriseArrow;

	@FindBy(id = "//span[text()='PM System *']")
	private WebElement labelPm;

	@FindBy(xpath = "//span[text()='EHR System *']")
	private WebElement labelEhr;

	@FindBy(xpath = "//button[contains(text(),'+ ADD ENTERPRISE')]")
	private WebElement btnAddEnterprise;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement enterPmEnterprise;

	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement enterEhrEnterprise;

	@FindBy(xpath = "//span[@id='timezone-label']")
	private WebElement labelTimeZone;

	@FindBy(xpath = "//span[text()='Solutions Display Name']")
	private WebElement labelSolutionsDisplayName;

	@FindBy(id = "addNodeSolutionInput")
	private WebElement textboxSolutionsName;

	/*
	 * @FindBy(xpath = "//input[@value='true']") private WebElement radioBtnActive;
	 */

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(id = "closeManageClientLabel")
	private WebElement btnOrgClose;

	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement enterPracEhrSystem;

	@FindBy(xpath = "(//input[@type='text'])[5]")
	private WebElement enterPracPmSystem;

	@FindBy(xpath = "(//p[@id='treeNodeName'])[2]")
	private WebElement orgName;

	@FindBy(id = "closeLabel")
	private WebElement btnClose;

	@FindBy(xpath = "//input[@id='addNodeInactiveRadio-input']")
	private WebElement radioBtnInactiveAddNode;

	@FindBy(xpath = "//div[contains(text(),'Maximum characters reached')] ")
	private WebElement errorMessage;

	public void clickCreateClient() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		buttonNew.click();
		log("Clicked on new button");
		/*
		 * CCUtils.verifyAndWaitForElement(buttonCreateClient, driver);
		 * buttonCreateClient.click(); log("Clicked on Create client button");
		 */
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
		CCUtils.verifyAndWaitForElement(dropdownClientType, driver);
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
		labelPageTitle.click();
		log("Entered text in client name field is " + duplicateClientName);
		CCUtils.verifyAndWaitForElement(errorUniqueClientName, driver);
		CCUtils.assertEquals(errorUniqueClientName.getText().trim(), "Client name already in use");
		log("Error Message displayed for duplicate client name is :" + errorUniqueClientName.getText().trim());
		textboxClientName.clear();
		// textboxClientName.sendKeys("new " + duplicateClientName);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(duplicateSAP);
		labelPageTitle.click();
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

	/*
	 * public void
	 * verifyErrorMessageForClientNameAndSapIdFieldWhenUserEnterBlankSpace(String
	 * clientName, String sapId) {
	 * CCUtils.verifyAndWaitForElement(textboxClientName, driver);
	 * textboxClientName.clear(); textboxClientName.sendKeys(clientName);
	 * log("Entered text in client name field is " + clientName);
	 * CCUtils.verifyAndWaitForElement(errorEmptyClientName, driver);
	 * CCUtils.assertEquals(errorEmptyClientName.getText().trim(),
	 * "Client name cannot be empty");
	 * log("Error Message displayed for client name field is :" +
	 * errorEmptyClientName.getText().trim());
	 * CCUtils.verifyAndWaitForElement(textboxSapId, driver);
	 * textboxSapId.sendKeys(sapId); log("Entered text in SAP ID field : " + sapId);
	 * CCUtils.verifyAndWaitForElement(errorEmptyClientSapId, driver);
	 * CCUtils.assertEquals(errorEmptyClientSapId.getText().trim(),
	 * "SAP ID cannot be empty");
	 * log("Error Message displayed for sap id field is :" +
	 * errorEmptyClientSapId.getText().trim()); }
	 */

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
		// CCUtils.assertEquals(labelTimezone.getText().trim(), "Timezone*");
		log("Timezone label text is " + labelTimezone.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownTextboxTimezone, driver);
		log("Verifying select placeholder is displayed for client timezone dropdown field");
		CCUtils.assertEquals(dropdownTextboxTimezone.getAttribute("placeholder").trim(), "Select");
		log("Box text for client timezone dropdown field is "
				+ dropdownTextboxTimezone.getAttribute("placeholder").trim());
	}

	public void verifyStatusLabelTextAndRadioButtons() {
		CCUtils.verifyAndWaitForElement(labelStatusAddNode, driver);
		log("Verifying status label text");
		CCUtils.assertTrue(labelStatusAddNode.isDisplayed(), "Status label is not displayed on create client popup");
		CCUtils.assertEquals(labelStatusAddNode.getText().trim(), "Status *");
		log("Status label text is " + labelStatusAddNode.getText().trim());
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

	/*
	 * public void verifyNoItemsFound() {
	 * CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
	 * CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
	 * log("No items found for the entered text"); }
	 */

	List<String> actualSsoLinks = new ArrayList<String>();
	List<String> nodeUnderClientInGeneralSetting = new ArrayList<String>();

	public final List<String> expectedInternalSsoLinks = Arrays.asList("Appointments Dashboard",
			"DCF Admin Tool - Super User", "Form Builder (Forms)", "Practice Portal", "PSS Admin",
			"Virtual Visits Admin Tool");
	public final List<String> expectedExternalSsoLinks = Arrays.asList("Appointments Dashboard", "Form Builder (Forms)",
			"Pay Reports", "Practice Portal", "PSS Admin");

	public void clickSSO() {
		CCUtils.verifyAndWaitForElement(ssoLinks, driver);
		javascriptClick(ssoLinks);
		log("SSOlinks clicked....");
	}

	public void clickUserIcon() {
		CCUtils.verifyAndWaitForElement(userIcon, driver);
		javascriptClick(userIcon);
		log("User icon clicked.");
	}

	public void clickLogout() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(userLogout, driver);
		javascriptClick(userLogout);
		log("Clicked on logout");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void verifyTopPanelElements() {
		log("Verifying Command Center Top Panel Elements");
		Assert.assertTrue(pxpCommandCenterLabel.getText().equals("PxP Command Center"), "PXP Home icon is displayed");
		Assert.assertTrue(ssoLinks.isDisplayed(), "SSO icon is displayed");
		Assert.assertTrue(userFullName.isDisplayed(), "User icon is displayed");

	}

	public WebDriver verifyUserIconContent() {
		CCUtils.verifyAndWaitForElement(profileSetting, driver);
		CCUtils.verifyAndWaitForElement(userHelp, driver);
		CCUtils.verifyAndWaitForElement(userLogout, driver);
		return driver;
	}

	public void getSsoLinks() {
		log("number of sso links present for user is " + ssoLinkList.size());
		for (int i = 0; i < ssoLinkList.size(); i++) {
			actualSsoLinks.add(ssoLinkList.get(i).getText());
		}
		log("SSO links present for user are :" + actualSsoLinks);
	}

	public void verifySSOLinksForInternalUsers() throws InterruptedException {
		CCUtils.assertEquals(actualSsoLinks, expectedExternalSsoLinks);
	}

	public void verifySSOLinksForExternalUsers() throws InterruptedException {
		CCUtils.assertEquals(actualSsoLinks, expectedExternalSsoLinks);
	}

	public void fetchElementColor(WebElement we, String uiName) {
		String weColor = we.getCssValue("color");
		String weBGColor = we.getCssValue("background-color");
		log("Colour for " + uiName + " is " + weColor + " & background color is " + weBGColor);
	}

	public void searchNode(String searchNodeName) {
		CCUtils.verifyAndWaitForElement(textboxSearchClientDirectory, driver);
		textboxSearchClientDirectory.sendKeys(searchNodeName);
		log("Entered text in Client Directory Search is : " + searchNodeName);
	}

	public void selectClientTypeDropDown() {
		CCUtils.verifyAndWaitForElement(dropdownClientFilter, driver);
		dropdownClientFilter.click();
		log("Clicked on Client Type dropdown");
	}

	public void clickSearchButton() {
		CCUtils.verifyAndWaitForElement(buttonSearchClientDirectory, driver);
		javascriptClick(buttonSearchClientDirectory);
		log("Clicked on Search button");
	}

	public void applyClientTypeFilter() {
		CCUtils.verifyAndWaitForElement(buttonApplyClientFilter, driver);
		buttonApplyClientFilter.click();
		log("Clicked on Apply button in Client type Filter");
	}

	public void clearClientTypeFilter() {
		CCUtils.verifyAndWaitForElement(buttonClearClientFilter, driver);
		buttonClearClientFilter.click();
		log("Clicked on Clear button in Client type Filter");
	}

	public void clickSearchResult(String clientName) {
		WebElement clientDisplay = driver.findElement(By.xpath("//td[normalize-space(text())='" + clientName + "']"));
		CCUtils.verifyAndWaitForElement(clientDisplay, driver);
		// String searchResultText =
		// tableClientNameInSearchResult.get(0).getText().trim();
		// tableClientNameInSearchResult.get(0).click();
		new Actions(driver).moveToElement(clientDisplay).click().build().perform();
		log("Clicked on Client :" + clientName);
	}

	public void verfiySearchResult(String searchNodeName) {
		CCUtils.verifyAndWaitForElement(tableClientNameInSearchResult.get(0), driver);
		log("number of matching results for searched text :" + tableClientNameInSearchResult.size());
		for (int listOfClientInResult = 0; listOfClientInResult <= tableSearchResult.size(); listOfClientInResult++) {
			CCUtils.assertTrue(
					tableClientNameInSearchResult.get(listOfClientInResult).getText().contains(searchNodeName),
					"Result matches");

			log("Searched result in row " + (listOfClientInResult + 1) + " is :"
					+ tableClientNameInSearchResult.get(listOfClientInResult).getText());
			if (tableClientNameInSearchResult.get(listOfClientInResult).getText().equalsIgnoreCase(searchNodeName)) {
				log("Matching result found");
				break;
			} else {
				continue;
			}

		}

	}

	public boolean testToCheckNoResultsFound() {
		CCUtils.verifyAndWaitForElement(noResultFoundInClientDirectory, driver);
		if (noResultFoundInClientDirectory.getText().equals("No search results found")) {
			return true;
		}
		log("Search result found " + noResultFoundInClientDirectory.getText());
		return false;
	}

	public void verfiySearchButtonFunctionality(String searchClient) {
		CCUtils.verifyAndWaitForElement(textboxSearchClientDirectory, driver);
		log("Default text in Client directory search is : " + textboxSearchClientDirectory.getAttribute("placeholder"));
		CCUtils.assertTrue(textboxSearchClientDirectory.getAttribute("placeholder").contains("Search"), "");
		CCUtils.assertEquals(buttonSearchClientDirectory.getText(), "SEARCH");
		log("Default text present in Search button in search textbox : " + buttonSearchClientDirectory.getText());
		searchNode(searchClient);
		clickSearchButton();
		CCUtils.assertEquals(buttonSearchClientDirectory.getText(), "CLEAR");
		log("Text present in Client search button after it is clicked is : " + buttonSearchClientDirectory.getText());
	}

	// private String xpathRequiredClientType =
	// "//label[normalize-space()='valueToReplace']";

	private WebElement prepareWebElementWithDynamicXpath(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	/*
	 * public void selectClientType(String clientTypeName) {
	 * 
	 * WebElement clientType =
	 * prepareWebElementWithDynamicXpath(xpathRequiredClientType, clientTypeName);
	 * log("Clicked on : " + clientTypeName); clientType.click(); }
	 */

	public void verfiySearchResultForClientType(String resultClientType) {
		CCUtils.verifyAndWaitForElement(tableClientTypeInSearchResult.get(0), driver);
		log("number of matching results for searched text :" + tableClientTypeInSearchResult.size());
		for (int listOfClient = 0; listOfClient <= tableClientTypeInSearchResult.size(); listOfClient++) {
			CCUtils.assertTrue(tableClientTypeInSearchResult.get(listOfClient).getText().contains(resultClientType),
					"Verifying each node");
			log("Searched result in row " + (listOfClient + 1) + " is :"
					+ tableClientTypeInSearchResult.get(listOfClient).getText());
			if (tableClientTypeInSearchResult.get(listOfClient).getText().equalsIgnoreCase(resultClientType)) {
				break;
			} else {
				continue;
			}
		}
	}

	public void verifySearchNodeInGeneralSetting() {
		CCUtils.verifyAndWaitForElement(tableClientNameInSearchResult.get(0), driver);
		tableClientNameInSearchResult.get(0).click();
		log("Clicked on Client :" + tableClientNameInSearchResult.get(0).getText());
	}

	public List<String> getClientNodeinGeneralSetting() {
		CCUtils.verifyAndWaitForElement(nodeNameInGeneralSetting.get(0), driver);
		log("number of nodes under Client is " + nodeNameInGeneralSetting.size());
		for (int i = 0; i < nodeNameInGeneralSetting.size(); i++) {
			nodeUnderClientInGeneralSetting.add(nodeNameInGeneralSetting.get(i).getText());
			log("Nodes present in Client is :" + nodeNameInGeneralSetting.get(i).getText());
		}
		return nodeUnderClientInGeneralSetting;
	}

	public GeneralSettingsPage selectSearchedClientNameInClientTable() {
		CCUtils.verifyAndWaitForElement(clientTableRow, driver);
		highlightElement(clientTableRow);
		clientTableRow.click();
		log("Clicked on client name in client directory table");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void searchAndSelectClientNameInClientTable(String searchText) {
		log("Enter search text in search textbox");
		searchNode(searchText);
		log("Click on search button");
		clickSearchButton();
		log("Click on client name in client directory table");
		selectSearchedClientNameInClientTable();
	}

	public void listOfFavoritingClients(List<String> list) throws AWTException, InterruptedException {
		for (int i = 0; i < list.size(); i++) {
			if (i == 5) {
				textboxSearchClientDirectory.clear();
				searchNode(list.get(i).trim());
				clickSearchButton();
				CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
				unFavoritedStarIcon.click();
				CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
				log("Clicked to Favorite star icon");
				CCUtils.performMoveCursor(unFavoritedStarIcon, driver);
				CCUtils.hover(unFavoritedStarIcon, driver);
				String color = fetchWebElementColor(unFavoritedStarIcon);
				log("print value of color since it is not favourited it should be : " + color);
				log("comparing color of fav icon to : " + color);
				CCUtils.assertEquals(color, "rgba(0, 0, 0, 0.6)");
			} else {
				textboxSearchClientDirectory.clear();
				searchNode(list.get(i).trim());
				clickSearchButton();
				CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
				unFavoritedStarIcon.click();
				log("Clicked to Favorite star icon");
				textboxSearchClientDirectory.clear();
			}
		}
	}

	public CommandCenterUsersPage clickOnCommandCenterUsersIcon() {
		CCUtils.verifyAndWaitForElement(iconCommandCenterUsers, driver);
		iconCommandCenterUsers.click();
		log("Clicked on command center users icon");
		return PageFactory.initElements(driver, CommandCenterUsersPage.class);
	}

	public String getDefaultItemsPerPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		highlightElement(defaultItemsPerPage);
		return defaultItemsPerPage.getText().toString();
	}

	public String getDefaultPagination() {
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("of", 0);
		String data = null;
		for (String w : words) {
			data = w.trim();
			log("Print the value of default Pagination : " + w.trim());
			break;
		}
		return data.trim();
	}

	public String getTotalNoOfClients() {
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("of", 0);
		String data = null;
		for (String w : words) {
			data = w.trim();
			log("Print the value of default Pagination : " + w.trim());
		}
		return data.trim();
	}

	public String splitingPagination() {
		CCUtils.verifyAndWaitForElement(displayPagination, driver);
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("\\s", 3);
		String data = null;
		for (String w : words) {
			data = w.trim();
		}
		log("Print the value of default Pagination : " + data.trim());
		return data.trim();
	}

	public String getNoOfRowsPagination() {
		String data = splitingPagination();
		String noOfRowsPagination = data.substring(0, 2).trim();
		return noOfRowsPagination.trim();
	}

	public String getLHSPagination() throws AWTException {
		String data = getDefaultPagination();
		String[] words = data.split("–", 2);
		for (String w : words) {
			data = w.trim();
			break;
		}
		log("Print the value of LHS Pagination : " + data.trim());
		return data.trim();
	}

	public String getRHSPagination() throws AWTException {
		String data = getDefaultPagination();
		String[] words = data.split("–", 0);
		for (String w : words) {
			data = w.trim();
		}
		log("Print the value of RHS Pagination : " + data.trim());
		return data.trim();
	}

	public void clickItemsPerPageDropdown() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(itemsPerPageDropdown, driver);
		highlightElement(itemsPerPageDropdown);
		javascriptClick(itemsPerPageDropdown);
	}

	public List<Integer> getListOfItemsPerPage() {
		List<Integer> itemsPerPage = new ArrayList<Integer>();
		for (WebElement item : listOfItemsPerPage) {
			log("Print the value of items per page : " + item.getText().toString());
			CCUtils.verifyAndWaitForElement(item, driver);
			itemsPerPage.add(Integer.parseInt(item.getText().toString()));
		}
		return itemsPerPage;
	}

	public int getNoOfRowsInClientTable() throws AWTException {
		CCUtils.scrollPageToEnd();
		for (int i = 0; i < listOfRowsClientTable.size(); i++) {
			CCUtils.verifyAndWaitForElement(listOfRowsClientTable.get(i), driver);
		}
		return listOfRowsClientTable.size();
	}

	public void clickNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		log("click to Next Page button");
		buttonNextPage.click();
	}

	public boolean isNextPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonNextPage, driver);
		return buttonStatus;
	}

	public void clickLastPageButton() {
		CCUtils.verifyAndWaitForElement(buttonLastPage, driver);
		log("click to Last Page button");
		buttonLastPage.click();
	}

	public boolean isLastPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonLastPage, driver);
		return buttonStatus;
	}

	public void clickPreviousPageButton() {
		CCUtils.verifyAndWaitForElement(buttonPreviousPage, driver);
		log("click to Previous Page button");
		buttonPreviousPage.click();
	}

	public boolean isPreviousPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonPreviousPage, driver);
		return buttonStatus;
	}

	public void clickFirstPageButton() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		log("click to First Page button");
		buttonFirstPage.click();
	}

	public boolean isFirstPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonFirstPage, driver);
		return buttonStatus;
	}

	public String clickTwentyItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageTwenty, driver);
		highlightElement(itemsPerPageTwenty);
		currentItemPerPage = itemsPerPageTwenty.getText().toString();
		itemsPerPageTwenty.click();
		return currentItemPerPage;
	}

	public String clickFiftyItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageFifty, driver);
		highlightElement(itemsPerPageFifty);
		currentItemPerPage = itemsPerPageFifty.getText().toString();
		itemsPerPageFifty.click();
		return currentItemPerPage;
	}

	public String clickHundredItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageHundred, driver);
		highlightElement(itemsPerPageHundred);
		currentItemPerPage = itemsPerPageHundred.getText().toString();
		itemsPerPageHundred.click();
		return currentItemPerPage;
	}

	public String clickTwoHundredItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageTwoHundred, driver);
		highlightElement(itemsPerPageTwoHundred);
		currentItemPerPage = itemsPerPageTwoHundred.getText().toString();
		itemsPerPageTwoHundred.click();
		return currentItemPerPage;
	}

	public void checkLastPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		if (noOfClients >= itemsPerPageValue) {
			clickLastPageButton();
			log("verifying RHS Pagination value : " + Integer.parseInt(getRHSPagination())
					+ "should be equal to Number of Clients : " + noOfClients);
			CCUtils.assertEquals(Integer.parseInt(getRHSPagination()), noOfClients);
			log("verifying after clicking to Last Page Button, Last Page and Next Page button should get disabled.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
		} else {
			log("Veriyfing all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
			CCUtils.assertTrue(isFirstPageButtonDisabled() == false, "First Page Button should get disabled");
			CCUtils.assertTrue(isPreviousPageButtonDisabled() == false, "Previous Page Button should get disabled");
		}
	}

	public void checkNextPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		log("Total number of clients is : " + noOfClients);
		if (noOfClients >= itemsPerPageValue) {
			int LHSPaginationValue = 1;
			while (isNextPageButtonDisabled()) {
				clickNextPageButton();
				LHSPaginationValue += itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : " + getLHSPagination() + " with expected value : "
						+ LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(getLHSPagination()), LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkPreviousPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		log("Total number of clients is : " + noOfClients);
		if (noOfClients >= itemsPerPageValue) {
			checkNextPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
			int LHSPaginationValue = Integer.parseInt(getLHSPagination());
			while (isPreviousPageButtonDisabled()) {
				clickPreviousPageButton();
				LHSPaginationValue -= itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : " + Integer.parseInt(getLHSPagination())
						+ " with expected value : " + LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(getLHSPagination()), LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkFirstPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		log("Total number of clients is : " + noOfClients);
		checkLastPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
		if (isFirstPageButtonDisabled() == true) {
			clickFirstPageButton();
			String expectedPagination = "1 – " + itemsPerPageValue;
			CCUtils.assertEquals(getDefaultPagination(), expectedPagination);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void verifyClientDirectioryPageTitle() {
		log("Verifying client directiory page title");
		CCUtils.verifyAndWaitForElement(labelPageTitleDirectory, driver);
		CCUtils.assertTrue(labelPageTitleDirectory.isDisplayed(), "Client directory page title is not displayed");
		CCUtils.assertEquals(labelPageTitleDirectory.getText().trim(), "Client Directory");
	}

	public void deselectFavClientInClientDirectory() {
		for (int i = listOfFavClients.size() - 1; i >= 0; i--) {
			CCUtils.verifyAndWaitForElement(listOfFavClients.get(i), driver);
			highlightElement(listOfFavClients.get(i));
			listOfFavClients.get(i).click();
			CCUtils.verifyAndWaitForElement(listOfNonFavClients.get(i), driver);
		}
		log("Un-Favorited all clients in client directory table");
	}

	public void selectFavClientInClientDirectory() {
		for (int i = listOfFavClients.size(); i <= 5; i++) {
			CCUtils.verifyAndWaitForElement(listOfNonFavClients.get(i), driver);
			highlightElement(listOfNonFavClients.get(i));
			listOfNonFavClients.get(i).click();
		}
		log("Favorited all clients in client directory table");
	}

	public int getSizeOfListOfFavClients() {
		log("print size of client :" + listOfFavClients.size());
		return listOfFavClients.size();
	}

	public void unFavoriteAllFavoritedClients() {
		for (WebElement ele : listOfFavClients) {
			// CCUtils.verifyAndWaitForElement(ele, driver);
			log("unfavoriting fav clients" + ele.getText());
			javascriptClick(ele);
		}
	}

	public void FavoriteAClient() {
		if (FavoritedStarIcon.isSelected()) {
			log("client is already favorited");
		} else {
			CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
			unFavoritedStarIcon.click();
			log("Clicked to Favorite star icon");
		}
	}

	public boolean getNoOfFavoriteClients() {
		if (getSizeOfListOfFavClients() == 5) {
			log("Total no of favorited clients is five");
			return false;
		} else
			log("Total no of favorited clients is less than five");
		return true;

	}

	public boolean verifyAClientIsNonFav() {
		CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
		if (unFavoritedStarIcon.isDisplayed()) {
			log("It is un - favorited");
			return true;
		} else
			return false;
	}

	public int getSizeOfListOfNonFavClients() {
		return listOfNonFavClients.size();
	}

	public int getSizeListOfFavClients() {
		return listOfFavClients.size();
	}

	public void verifyNoOfFavoriteClient(boolean value) {
		if (value == false) {
			log("since favorited clients are 5 so unfavoriting all favorited client.");
			unFavoriteAllFavoritedClients();
		} else
			log("favorited clients are less tham 5");
	}

	public void validateFavClientsAndUnFavThem(int value) {
		if ((value <= 5) && (value != 0)) {
			log("since favorited clients are 5 or less than 5 so unfavoriting all favorited client.");
			unFavoriteAllFavoritedClients();
		} else
			log("No need to un favorite as no favorite clients present");
	}

	public void howManyFavClients(int value) {
		if ((value <= 5) && (value != 0)) {
			log("favorited clients are 5 or less than 5");
		} else
			log("no favorite clients present");
	}

	public boolean checkNoOfFavClientsAndUnFavoriteThem(int value) {
		if (value <= 5 && value != 0) {
			log("Favorite clients are : " + value);
			deselectFavClientInClientDirectory();
			return true;
		} else {
			log("No Favorite clients so selecting first five client as favorite and unfavorite them");
			return false;
		}
	}

	public void FavoriteClientThenUnfavorite(boolean value, String ClientName) {
		if (value == false) {
			searchNode(ClientName);
			clickSearchButton();
			FavoriteAClient();
			deselectFavClientInClientDirectory();
		} else
			log("Already deselected all clients");
	}

	public void verifyClientNodeIsSelected() {
		CCUtils.verifyAndWaitForElement(clientNodeTitle, driver);
		CCUtils.assertEquals(clientNodeTitle.isDisplayed(), true);
		log("Client node title is displayed");
	}

	List<String> actualSolutionValues = new ArrayList<String>();

	public final List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "PSS", "Pay", "Portal",
			"Virtual Visits");

	public void verifyActiveButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		log("Verifying active button is by default selected");
		if (radioButtonActive.isDisplayed() == true && radioButtonActive.isSelected() == true) {
			log("Active button is displayed on screen and selected");
		} else {
			log("Active button is not displayed on screen and selected");
		}
	}

	public Boolean verifyValueIsNotSelectedInParentEntity() {
		CCUtils.verifyAndWaitForElement(textboxParentEntityName, driver);
		String parentEntityName = textboxParentEntityName.getText().trim();
		if (parentEntityName.length() == 0) {
			log("Serach text is not present in parent entity textbox");
			return true;
		} else {
			log("Search text is present in parent entity textbox");
		}
		return false;
	}

	public void verifyDataIsNotFetchedIfParentEntityNotSelected(Boolean isParentEntityNameNotSelected) {
		if (isParentEntityNameNotSelected == true) {
			log("Verifying text is not present in sap id field");
			CCUtils.verifyAndWaitForElement(textboxSapIdAddNode, driver);
			String sapId = textboxSapIdAddNode.getText().trim();
			CCUtils.assertTrue(sapId.length() == 0, "Text is present in sap id field");
			log("Verifying text is not present in timezone field");
			CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
			dropdownTimezone.click();
			CCUtils.verifyAndWaitForElement(textboxTimezone, driver);
			String timezone = textboxTimezone.getText().trim();
			CCUtils.assertTrue(timezone.length() == 0, "Text is present in timezone field");
		}
	}

	public GeneralSettingsPage clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		buttonSaveAndAdd.click();
		log("Clicked on Save&Add button");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancelInAddNewNode, driver);
		javascriptClick(buttonCancelInAddNewNode);
		log("Clicked on Cancel button");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		javascriptClick(crossIcon);
		log("Clicked on X icon");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void verifyErrorMessageForOrgNodeNameField(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered text in org name field is " + orgName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacter, driver);
		CCUtils.assertEquals(errorMaximumCharacter.getText().trim(), "Maximum characters reached");
		log("Error Message displayed for name field is " + errorMaximumCharacter.getText().trim());
	}

	public void verifyErrorMessageForSolutionDisplayNameField(String solutionDisplayName) {
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.sendKeys(solutionDisplayName);
		log("Entered text in solution display name field is " + solutionDisplayName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacter, driver);
		CCUtils.assertEquals(errorMaximumCharacter.getText().trim(), "Maximum characters reached");
		log("Error Message displayed for solution display name field is " + errorMaximumCharacter.getText().trim());
	}

	public void verifyErrorMessageForSapIdField(String sapId) {
		CCUtils.verifyAndWaitForElement(textboxSapIdAddNode, driver);
		textboxSapIdAddNode.sendKeys(sapId);
		log("Entered text in sap id field is " + sapId);
		CCUtils.verifyAndWaitForElement(errorInvalidSap, driver);
		CCUtils.assertEquals(errorInvalidSap.getText().trim(), "SAP ID cannot contain more than 50 characters");
		log("Error Message displayed for sap id field is " + errorInvalidSap.getText().trim());
	}

	public void verifyOrgNameIsRequiredField(String labelText) {
		CCUtils.verifyAndWaitForElement(errorNameRequired, driver);
		if (labelText.trim().equals("System Name *")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "System name is required");
			log("System name is required error message displayed on add new system popup");
		} else if (labelText.trim().equals("Enterprise Name *")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "Enterprise name is required");
			log("Enterprise name is required error message displayed on add new enterprise popup");
		} else if (labelText.trim().equals("Practice Name *")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "Practice name is required");
			log("Practice name is required error message displayed on add new practice popup");
		}
	}

	public void enterSpaceInRequiredField() {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Enter space in org name textbox");
		textboxName.sendKeys("  ");
		clickOnSaveAndAddButton();
	}

	public void verifyParentEntityNameIsRequiredField() {
		CCUtils.verifyAndWaitForElement(errorParentNameRequired, driver);
		CCUtils.assertEquals(errorParentNameRequired.getText().trim(), "Parent Entity is required");
		log("Parent Entity required error message displayed in add new enterprise popup");
	}

	public void verifyParentEntityDropdownisReadonlyField() {
		CCUtils.verifyAndWaitForElement(textboxParentEntityNameAtSystem, driver);
		log("Verify parent entity dropdown is disabled on add system page");
		String state = textboxParentEntityNameAtSystem.getAttribute("readonly");
		CCUtils.assertNotNull(state, "The object return null value");
	}

	public void verifyTestDataFetchedFromSelectedParent(String sapId, String timezone, String Status) {
		wait.until(ExpectedConditions.textToBePresentInElementValue(textboxSapIdAddNode, sapId));
		log("Verifying sap id is fetched from selected parent or not");
		CCUtils.assertEquals(textboxSapIdAddNode.getAttribute("value").trim(), sapId.trim());
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		log("Click on timezone dropdown");
		dropdownTimezone.click();
		log("Verifying timezone is fetched from selected parent or not");
		CCUtils.assertEquals(selectedTimezoneValue.getText().trim(), timezone.trim());
		log("Verifying status is fetched from selected parent or not");
		if (Status.toString().trim().equals("active")) {
			CCUtils.assertTrue(radioButtonActive.isSelected(), "Active radio button is not selected");
		} else {
			CCUtils.assertTrue(radioButtonInactive.isSelected(), "Inactive radio button is not selected");
		}
	}

	public void verifyClientNameIsDisplayedOnParentEntityDropdown(String clientName) {
		log("Verifying client name is displayed on parent entity drop down");
		wait.until(ExpectedConditions.textToBePresentInElementValue(textboxParentEntityNameAtSystem, clientName));
		CCUtils.assertEquals(textboxParentEntityNameAtSystem.getAttribute("value").trim(), clientName);
	}

	public void createNewOrgNode(String orgName, String sapId, String timezone, String solutionDisplayName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered org name is " + orgName);
		CCUtils.verifyAndWaitForElement(textboxSapIdAddNode, driver);
		textboxSapIdAddNode.clear();
		CCUtils.verifyAndWaitForElement(textboxSapIdAddNode, driver);
		textboxSapIdAddNode.sendKeys(sapId);
		log("Entered org sap id is " + sapId);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		CCUtils.verifyAndWaitForElement(textboxTimezone, driver);
		textboxTimezone.sendKeys(timezone, Keys.ENTER);
		log("Entered org timezone is " + timezone);
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.sendKeys(solutionDisplayName);
		log("Entered org solution display name is " + solutionDisplayName);
	}

	public void verifySolutionLabelIsDisplayed() {
		CCUtils.verifyAndWaitForElement(labelSolution, driver);
		CCUtils.assertEquals(labelSolution.getText().trim(), "Which solutions will this practice use?");
	}

	public void addSolutions() throws InterruptedException {
		log("Select all solutions");
		scrollAndWait(0, 500, 3000);
		for (int i = 0; i < checkboxSolution.size(); i++) {
			javascriptClick(checkboxSolution.get(i));
		}
	}

	public void verifyBoxTextForParentEntityField() {
		log("Verifying search/select placeholder is displayed for parent entity field");
		CCUtils.verifyAndWaitForElement(dropdownParentEntityName, driver);
		CCUtils.assertEquals(dropdownParentEntityName.getAttribute("placeholder").toString(), "Search/Select");
	}

	public void verifyBoxTextForSapIdField() {
		log("Verifying input text placeholder is displayed for sap id field textbox");
		CCUtils.verifyAndWaitForElement(textboxSapIdAddNode, driver);
		textboxSapIdAddNode.clear();
		CCUtils.assertEquals(textboxSapIdAddNode.getAttribute("placeholder").toString(), "Input Text");
	}

	public void verifyBoxTextForTimezoneField() {
		log("Verifying select placeholder is displayed for timezone field");
		CCUtils.verifyAndWaitForElement(placeholderTimezoneDropdown, driver);
		CCUtils.assertEquals(placeholderTimezoneDropdown.getAttribute("placeholder").toString(), "Select");
	}

	public void verifyBoxTextForSolutionDisplayNameField() {
		log("Verifying inputtText placeholder is displayed for solution display name field textbox");
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.clear();
		CCUtils.assertEquals(textboxSolutionDisplayName.getAttribute("placeholder").toString(), "Input Text");
	}

	public void verifyBoxTextForNameField() {
		log("Verifying input text placeholder is displayed for name field textbox");
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.clear();
		CCUtils.assertEquals(textboxName.getAttribute("placeholder").toString(), "Input Text");
	}

	/*
	 * public void selectInactiveRadiobutton() {
	 * CCUtils.verifyAndWaitForElement(radioButtonInactive, driver);
	 * javascriptClick(radioButtonInactive);
	 * log("Clicked on inactive radiobutton in popup"); }
	 */

	public void selectActiveRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		javascriptClick(radioButtonActive);
		log("Clicked on active radiobutton in popup");
	}

	public void clickOnParentEntityNameDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownParentEntityName, driver);
		dropdownParentEntityName.click();
		log("Clicked on parent entity name dropdown in add new enterprise popup");
	}

	public void searchTextForParentEntityName(String searchText) {
		CCUtils.verifyAndWaitForElement(textboxParentEntityName, driver);
		log("Enter text in parent entity textbox");
		textboxParentEntityName.sendKeys(searchText.trim());
		textboxParentEntityName.sendKeys(Keys.ENTER);
		log("Entered searched text is " + searchText.trim());
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}

	public String getSystemNameLabelText() {
		return labelSystemName.getText().trim();
	}

	public String getEnterpriseNameLabelText() {
		return labelEnterpriseName.getText().trim();
	}

	public String getPracticeNameLabelText() {
		return labelPracticeName.getText().trim();
	}

	public void verifySapIdFieldAcceptBlankSpace(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Enter text in org name textbox");
		textboxName.sendKeys(orgName);
		log("Entered org name text is " + orgName.trim());
		CCUtils.verifyAndWaitForElement(textboxSapIdAddNode, driver);
		textboxSapIdAddNode.clear();
		log("Enter space in org sap id textbox");
		textboxSapIdAddNode.sendKeys(" ");
	}

	public void getSolutionValues() throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		for (int i = 0; i < labelSolutionList.size(); i++) {
			actualSolutionValues.add(labelSolutionList.get(i).getText().trim());
		}
		log("Solution available for user are :" + actualSolutionValues);
	}

	public void verifySolutionValues() throws InterruptedException {
		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

	public void addNewSystemNode(String orgName, String sapId, String timezone, String solutionDisplayName,
			Boolean isActive) throws InterruptedException {
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		clickOnSaveAndAddButton();
	}

	public void addNewNode(String parentEntityName, Boolean isActive, String orgName, String sapId, String timezone,
			String solutionDisplayName) throws InterruptedException {
		clickOnParentEntityNameDropdown();
		searchTextForParentEntityName(parentEntityName);
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		clickOnSaveAndAddButton();
	}

	public void addNewPracticeNodeWithSolutions(String parentEntityName, Boolean isActive, String orgName, String sapId,
			String timezone, String solutionDisplayName) throws InterruptedException {
		clickOnParentEntityNameDropdown();
		searchTextForParentEntityName(parentEntityName);
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		addSolutions();
		clickOnSaveAndAddButton();
	}

	public GeneralSettingsPage createPracticeWithSolutions(String practiceName, String clientName,
			List<String> solutions, String sapId, String timezone, String solutionDisplayName)
			throws InterruptedException, AWTException {
		clickOnParentEntityNameDropdown();
		searchTextForParentEntityName(clientName);
		logStep("Creating Practice with name : " + practiceName + " with solutions " + solutions.toString());
		createNewOrgNode(practiceName, sapId, timezone, solutionDisplayName);
		selectFewSolutions(solutions);
		clickOnSaveAndAddButton();
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void selectFewSolutions(List<String> providedSolutions) throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		for (String selectSolution : providedSolutions) {
			for (int i = 0; i < labelSolutionList.size(); i++) {
				CCUtils.verifyAndWaitForElement(labelSolutionList.get(i), driver);
				if ((labelSolutionList.get(i).getText().trim()).contains(selectSolution)) {
					CCUtils.verifyAndWaitForElement(particularSolutionCheckbox.get(i), driver);
					particularSolutionCheckbox.get(i).click();
					log("Solution name : " + selectSolution + " is selected.");
				}
			}
		}
	}

	public void verifyAddOrgNodePageTitle(String nodeName) {
		log("Verifying add org node page title");
		if (nodeName.equals("SYSTEM")) {
			CCUtils.verifyAndWaitForElement(lablePageTitle, driver);
			CCUtils.assertTrue(lablePageTitle.isDisplayed(),
					"Add new system page title is not displayed on add new system popup");
			CCUtils.assertEquals(lablePageTitle.getText().trim(), "Add New System");
		} else if (nodeName.equals("ENTERPRISE")) {
			CCUtils.verifyAndWaitForElement(lablePageTitle, driver);
			CCUtils.assertTrue(lablePageTitle.isDisplayed(),
					"Add new enterprise page title is not displayed on add new enterprise popup");
			CCUtils.assertEquals(lablePageTitle.getText().trim(), "Add New Enterprise");
		} else if (nodeName.equals("PRACTICE")) {
			CCUtils.verifyAndWaitForElement(lablePageTitle, driver);
			CCUtils.assertTrue(lablePageTitle.isDisplayed(),
					"Add new practice page title is not displayed on add new practice popup");
			CCUtils.assertEquals(lablePageTitle.getText().trim(), "Add New Practice");
		}
	}

	public void verifyParentEntityLableText() {
		CCUtils.verifyAndWaitForElement(labelParentEntity, driver);
		log("Verifying parent entity label text");
		CCUtils.assertTrue(labelParentEntity.isDisplayed(),
				"Parent entity label is not displayed on add new node popup");
		CCUtils.assertEquals(labelParentEntity.getText().trim(), "Parent Entity *");
	}

	public void verifyNodeNameLableText(String node) {
		log("Verifying org node name label text");
		if (node.equals("SYSTEM")) {
			CCUtils.verifyAndWaitForElement(labelSystemName, driver);
			CCUtils.assertTrue(labelSystemName.isDisplayed(),
					"System name label is not displayed on add new system popup");
			CCUtils.assertEquals(labelSystemName.getText().trim(), "System Name *");
		} else if (node.equals("ENTERPRISE")) {
			CCUtils.verifyAndWaitForElement(labelEnterpriseName, driver);
			CCUtils.assertTrue(labelEnterpriseName.isDisplayed(),
					"Enterprise name label is not displayed on add new enterprise popup");
			CCUtils.assertEquals(labelEnterpriseName.getText().trim(), "Enterprise Name *");
		} else if (node.equals("PRACTICE")) {
			CCUtils.verifyAndWaitForElement(labelPracticeName, driver);
			CCUtils.assertTrue(labelPracticeName.isDisplayed(),
					"Practice name label is not displayed on add new practice popup");
			CCUtils.assertEquals(labelPracticeName.getText().trim(), "Practice Name *");
		}
	}

	public void verifySolutionDisplayNameLableText() {
		CCUtils.verifyAndWaitForElement(labelSolutionDisplayName, driver);
		log("Verifying solution display name label text");
		CCUtils.assertTrue(labelSolutionDisplayName.isDisplayed(),
				"Solution display name label is not displayed on add new node popup");
		CCUtils.assertEquals(labelSolutionDisplayName.getText().trim(), "Solutions Display Name");
	}

	public void verifySapIdLableText() {
		CCUtils.verifyAndWaitForElement(labelSapId, driver);
		log("Verifying sap id label text");
		CCUtils.assertTrue(labelSapId.isDisplayed(), "Sap id label is not displayed on add new node popup");
		CCUtils.assertEquals(labelSapId.getText().trim(), "SAP ID");
	}

	public void verifyTimezoneLableText() {
		CCUtils.verifyAndWaitForElement(labelTimezone, driver);
		log("Verifying timezone label text");
		CCUtils.assertTrue(labelTimezone.isDisplayed(), "Timezone label is not displayed on add new node popup");
		CCUtils.assertEquals(labelTimezone.getText().trim(), "Timezone");
	}

	/*
	 * public void verifyStatusLabelTextAndRadioButtons() {
	 * CCUtils.verifyAndWaitForElement(labelStatus, driver);
	 * CCUtils.assertTrue(labelStatus.isDisplayed(),
	 * "Status label is not displayed on add new node popup");
	 * log("Verifying status label text");
	 * CCUtils.assertEquals(labelStatus.getText().trim(), "Status *");
	 * log("Status label text is " + labelStatus.getText().trim());
	 * log("Verifying active label and radio button are displayed on add new node popup"
	 * ); CCUtils.assertEquals(labelActive.getText().trim(), "Active");
	 * CCUtils.assertTrue(radioButtonActive.isDisplayed(),
	 * "Active radiobutton is not present on add new node popup");
	 * log("Verifying inactive label and radio button are displayed on add new node popup"
	 * ); CCUtils.assertEquals(labelInactive.getText().trim(), "Inactive");
	 * CCUtils.assertTrue(radioButtonInactive.isDisplayed(),
	 * "Inactive radiobutton is not present on add new node popup"); }
	 */

	public void verifyLabelPresentOnAddNewNodePopup(String nodeName) {
		verifyAddOrgNodePageTitle(nodeName);
		verifyParentEntityLableText();
		verifyNodeNameLableText(nodeName);
		verifySolutionDisplayNameLableText();
		verifySapIdLableText();
		verifyTimezoneLableText();
		verifyStatusLabelTextAndRadioButtons();
	}

	public void enterOrgName(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered org name is " + orgName);
	}

	public void clickOnManageClient() {
		CCUtils.verifyAndWaitForElement(btnManageClient, driver);
		javascriptClick(btnManageClient);
		log("Clicked on Manage Client button");
	}

	public void clickOnExpandArrow(String systemName) {
		WebElement element = driver
				.findElement(By.xpath("//button[@aria-label='toggle " + systemName + "']/span/mat-icon"));
		javascriptClick(element);
		log("Clicked on expand Arrow button");
	}

	public void clickOnSystemExpandArrow() {
		CCUtils.verifyAndWaitForElement(expandSystemArrow, driver);
		javascriptClick(expandSystemArrow);
		log("Clicked on expand Arrow button for System");
	}

	public void clickOnEnterpriseExpandArrow(String enterpriseName) {
		WebElement element = driver
				.findElement(By.xpath("//button[@aria-label='toggle " + enterpriseName + "']/span/mat-icon"));
		javascriptClick(element);
		log("Clicked on expand Arrow button for Enterprise");
	}

	public void clickOnAddSystem() {
		CCUtils.verifyAndWaitForElement(btnAddSystem, driver);
		javascriptClick(btnAddSystem);
		log("Clicked on Add System  button");
	}

	public void enterNewSystemDetails(String systemName) {
		CCUtils.verifyAndWaitForElement(enterSystemName, driver);
		enterSystemName.sendKeys(systemName);
		enterEhrSystem.click();
		selectOptionSystem.click();
		enterPmSystem.click();
		selectOptionSystem.click();
		btnSave.click();
	}

	public void systemName(String systemName) {
		clickOnAddSystem();
		enterNewSystemDetails(systemName);
	}

	private String xpathRequiredNewTimezone = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForTimezone(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void addTimezone(String newTimezone) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		log("click to dropdown of Timezone");
		dropdownTimezone.click();
		selectTimeZone(newTimezone);
	}

	/*
	 * public void selectTimeZone(String newTimezone) { WebElement newClientTimezone
	 * = prepareDynamicXpathForTimezone(xpathRequiredNewTimezone, newTimezone);
	 * newClientTimezone.click(); log("Clicked on : " + newTimezone); }
	 */

	public void clickOnAddPractice() {
		CCUtils.verifyAndWaitForElement(btnAddPractice, driver);
		javascriptClick(btnAddPractice);
		log("Clicked on Add Practice  button");
	}

	public void enterNewPracticeDetails(String practiceName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(enterPracticeName, driver);
		enterPracticeName.sendKeys(practiceName);
		enterPmPractice.click();
		selectOptionSystem.click();
		enterEhrPractice.click();
		selectOptionSystem.click();
		enterTimezone.click();
		addTimezone("Nairobi");
		btnSave.click();
	}

	public void addNewPractice(String practiceName) throws InterruptedException {
		clickOnAddPractice();
		enterNewPracticeDetails(practiceName);
	}

	public void addNewSystem(String systemName) {
		clickOnAddSystem();
		enterNewSystemDetails(systemName);
	}

	public void verifyAddSystemButton() {
		CCUtils.verifyAndWaitForElement(btnAddSystem, driver);
		log("Add System Button is displayed");
	}

	public void verifyAddNewSystemPage() throws InterruptedException, AWTException {
		log("Verifying add New System Page");
		verifySystemNameLabelTextAndTextboxDefaultValue();
		verifyPmSystemLabelText();
		verifyEhrSystemLabelText();
		verifyActiveInactiveRadioButtonStatusLabel();
		verifyActiveRadioButtonIsByDefaultSelected();
	}

	public void verifySystemNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelSystemName, driver);
		log("Verifying System name label text");
		CCUtils.assertEquals(labelSystemName.getText().trim(), "System Name *");
		log("System name label text is " + labelSystemName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Verifying Input text here placeholder is displayed for System name textbox field");
		CCUtils.assertEquals(textboxName.getAttribute("placeholder").trim(), "Input System Name");
		log("Box text for System name textbox field is " + textboxName.getAttribute("placeholder").trim());
	}

	public void verifyEhrSystemLabelText() {
		CCUtils.verifyAndWaitForElement(labelEhr, driver);
		log("Verifying State label text");
		CCUtils.assertEquals(labelEhr.getText().trim(), "EHR System *");
		log("State label text is " + labelEhr.getText().trim());
	}

	public void verifyPmSystemLabelText() {
		CCUtils.verifyAndWaitForElement(labelPm, driver);
		log("Verifying Timezone label text");
		CCUtils.assertEquals(labelPm.getText().trim(), "PM System *");
		log("Timezone label text is " + labelPm.getText().trim());
	}

	public void enterSystemName(String input) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.clear();
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(input);
		log("Entered input for System Name : " + input);
	}

	private String xpathRequiredPmSystem = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private String xpathRequiredNewEhrSystem = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForState(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void addPmSystemAndEhrSystem(String pmName, String ehrName) throws InterruptedException {
		log("click to dropdown of PM system");
		enterPmSystem.click();
		selectPmSystem(pmName);
		log("click to dropdown of EhrSystem");
		enterEhrSystem.click();
		selectEhrSystem(ehrName);
	}

	public void selectPmSystem(String pmName) {
		WebElement pm = prepareDynamicXpathForState(xpathRequiredPmSystem, pmName);
		CCUtils.verifyAndWaitForElement(pm, driver);
		pm.click();
		log("Clicked on : " + pmName);
	}

	public void selectEhrSystem(String ehrName) {
		WebElement ehr = prepareDynamicXpathForState(xpathRequiredNewEhrSystem, ehrName);
		ehr.click();
		log("Clicked on : " + ehrName);
	}

	public void verifyAddEnterpriseButton() {
		CCUtils.verifyAndWaitForElement(btnAddEnterprise, driver);
		log("Add Enterprise Button is displayed");
	}

	public void verifyAddNewEnterprisePage() throws InterruptedException, AWTException {
		log("Verifying add New Enterprise Page");
		verifyEnterpriseNameLabelTextAndTextboxDefaultValue();
		verifyPmSystemLabelText();
		verifyEhrSystemLabelText();
		verifyTimezoneLabelText();
		verifyActiveInactiveRadioButtonStatusLabel();
		verifyActiveRadioButtonIsByDefaultSelected();
	}

	public void verifyEnterpriseNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEnterpriseName, driver);
		log("Verifying Enterprise name label text");
		CCUtils.assertEquals(labelEnterpriseName.getText().trim(), "Enterprise Name *");
		log("Enterprise name label text is " + labelEnterpriseName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Verifying Input text here placeholder is displayed for Enterprise name textbox field");
		CCUtils.assertEquals(textboxName.getAttribute("placeholder").trim(), "Input Enterprise Name");
		log("Box text for Enterprise name textbox field is " + textboxName.getAttribute("placeholder").trim());
	}

	public void addNewEnterprise(String enterpriseName) throws InterruptedException {
		clickOnAddEnterprise();
		enterNewEnterpriseDetails(enterpriseName);
	}

	public void clickOnAddEnterprise() {
		CCUtils.verifyAndWaitForElement(btnAddEnterprise, driver);
		javascriptClick(btnAddEnterprise);
		log("Clicked on Add Enterprise  button");
	}

	public void enterNewEnterpriseDetails(String enterpriseName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(enterpriseName);
		enterPmEnterprise.click();
		selectOptionSystem.click();
		enterEhrEnterprise.click();
		selectOptionSystem.click();
		enterTimezone.click();
		addTimezone("Nairobi");
		btnSave.click();
	}

	public void verifyTimezoneLabelText() {
		CCUtils.verifyAndWaitForElement(labelTimeZone, driver);
		log("Verifying Timezone label text");
		CCUtils.assertEquals(labelTimezone.getText().trim(), "Timezone *");
		log("Timezone label text is " + labelTimeZone.getText().trim());
	}

	public void clickOnAddPracticeButton() {
		CCUtils.verifyAndWaitForElement(btnAddPractice, driver);
		javascriptClick(btnAddPractice);
		log("Clicked on Add Practice  button");
	}

	public void addNewPractice() throws InterruptedException {
		clickOnAddPracticeButton();
		enterNewPracticeDetails("PracticeForDemo");
	}

	public void verifyManageClientButton() throws Exception {
		CCUtils.waitForElementPresence(btnManageClient, driver);
		CCUtils.assertEquals(btnManageClient.getText().trim(), "MANAGE CLIENT");
		log(" MANAGE CLIENT  Button is displayed");
	}

	public void verifyAddPracticeButton() {
		CCUtils.verifyAndWaitForElement(btnAddPractice, driver);
		log("Add Practice Button is displayed");
	}

	public void verifyAddNewPracticePage() throws InterruptedException, AWTException {
		log("Verifying add New Practice Page");
		verifyPracticeNameLabelTextAndTextboxDefaultValue();
		verifyPmSystemLabelText();
		verifyEhrSystemLabelText();
		verifyTimezoneLabelText();
		verifySolutionsDisplayNameLabelTextAndTextboxDefaultValue();
		verifyActiveInactiveRadioButtonStatusLabel();
		verifyActiveRadioButtonIsByDefaultSelected();
	}

	public void verifyPracticeNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelPracticeName, driver);
		log("Verifying Practice name label text");
		CCUtils.assertEquals(labelPracticeName.getText().trim(), "Practice Name *");
		log("Practice name label text is " + labelPracticeName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Verifying Input text here placeholder is displayed for Practice name textbox field");
		CCUtils.assertEquals(textboxName.getAttribute("placeholder").trim(), "Input Practice Name");
		log("Box text for Practice name textbox field is " + textboxName.getAttribute("placeholder").trim());
	}

	public void verifySolutionsDisplayNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelSolutionsDisplayName, driver);
		log("Verifying Solutions Display Name label text");
		CCUtils.assertEquals(labelSolutionsDisplayName.getText().trim(), "Practice Name *");
		log("Solutions Display Name label text is " + labelSolutionsDisplayName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxSolutionsName, driver);
		log("Verifying Input text here placeholder is displayed for Practice name textbox field");
		CCUtils.assertEquals(textboxSolutionsName.getAttribute("placeholder").trim(), "Input Solutions Display Name");
		log("Box text for Solutions Display Name textbox field is "
				+ textboxSolutionsName.getAttribute("placeholder").trim());
	}

	/*
	 * public void verifyActiveRadioButtonIsByDefaultSelected() {
	 * CCUtils.verifyAndWaitForElement(radioBtnActive, driver);
	 * log("Verifying active radio button is by default selected");
	 * CCUtils.assertTrue(radioBtnActive.isSelected(),
	 * "Active radio button is by default selected"); }
	 */

	public void verifyActiveInactiveRadioButtonStatusLabel() {
		CCUtils.verifyAndWaitForElement(labelActive, driver);
		log("Verifying Active label text");
		CCUtils.assertEquals(labelActive.getText().trim(), " Active ");
		log("Active label text is " + labelActive.getText().trim());

		CCUtils.verifyAndWaitForElement(labelInactive, driver);
		log("Verifying Inactive label text");
		CCUtils.assertEquals(labelInactive.getText().trim(), " Inactive ");
		log("Inactive label text is " + labelInactive.getText().trim());
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void verifyInactiveRadioButton() {
		CCUtils.verifyAndWaitForElement(radioBtnInactiveAddNode, driver);
		log("Inactive Button is displayed");
	}

	public void enterEnterpriseName(String input) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.clear();
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(input);
		log("Entered input for Enterprise Name : " + input);
	}

	public void enterSolutionsDisplayName(String input) {
		CCUtils.verifyAndWaitForElement(textboxSolutionsName, driver);
		textboxSolutionsName.clear();
		CCUtils.verifyAndWaitForElement(textboxSolutionsName, driver);
		textboxSolutionsName.sendKeys(input);
		log("Entered input for Solutions Display Name : " + input);
	}

	public void verifyMaximumCharactersReachedErrorMessage(String orgName) {
		log("Enter more than 100 characters in system Name field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered text in system Name field is " + orgName);
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertEquals(errorMessage.getText().trim(), "Maximum characters reached");
		log("Error message displayed for system Name field is " + errorMessage.getText().trim());
	}

	public void clickOnCloseButton() {
		CCUtils.verifyAndWaitForElement(btnClose, driver);
		javascriptClick(btnClose);
		log("Clicked on close button");
	}

	public void clickOnOrgCloseButton() {
		CCUtils.verifyAndWaitForElement(btnOrgClose, driver);
		javascriptClick(btnOrgClose);
		log("Clicked on close button");
	}

	public void enterPracticeName(String input) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.clear();
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(input);
		log("Entered input for Practice Name : " + input);
	}

	public void addPracticePmSystemAndEhrSystem(String pmName, String ehrName) throws InterruptedException {
		log("click to dropdown of PM system");
		enterPracPmSystem.click();
		selectPmSystem(pmName);
		log("click to dropdown of EhrSystem");
		enterPracEhrSystem.click();
		selectEhrSystem(ehrName);
	}

	public void verifyOrgName() {
		CCUtils.verifyAndWaitForElement(orgName, driver);
		CCUtils.assertEquals(orgName.isDisplayed(), true);
		log("Org node is displayed");
	}
}
