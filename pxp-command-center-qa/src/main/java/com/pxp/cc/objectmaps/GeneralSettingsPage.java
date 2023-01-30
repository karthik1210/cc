// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class GeneralSettingsPage extends CCMainPage {

	@FindBy(className = "info")
	private WebElement clientName;

	@FindBy(xpath = "(//span[@class='value'])[2]")
	private WebElement labelClientSapValue;

	@FindBy(xpath = "(//span[@class='value'])[4]")
	private WebElement labelClientTimezoneValue;

	@FindBy(xpath = "//span[@class='link value']")
	private WebElement labelClientStatusValue;

	@FindBy(id = "orgActiveLabel")
	private WebElement labelNodeStatusValue;

	@FindBy(id = "detailEditLabel")
	private WebElement buttonClientInfoEdit;
	
	@FindBy(id = "detailCancelBtn")
	private WebElement buttonClientInfoEditCancel;

	@FindBy(id = "addNodeActiveRadio-input")
	private WebElement radioButtonActive;

	@FindBy(id = "addNodeInactiveRadio-input")
	private WebElement radioButtonInactive;

	@FindBy(id = "generalToggleIcon-button")
	private WebElement buttonNew;

	@FindBy(id = "generalAddLabel")
	private WebElement addLabel;

	@FindBy(id = "generalSystemLabel")
	private WebElement buttonAddNewSystem;

	@FindBy(id = "generalAddEnterprise")
	private WebElement buttonAddNewEnterprise;

	@FindBy(id = "generaladdPractice")
	private WebElement buttonAddNewPractice;

	@FindBy(id = "generalLabel")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[@id='orgSapValue'] | //span[@id='orgSapNull']")
	private WebElement labelOrgSapValue;

	@FindBy(id = "orgSolutionValue")
	private WebElement labelOrgSolutionDisplayNameValue;

	@FindBy(id = "orgTimezoneValue")
	private WebElement labelOrgTimezoneValue;

	@FindBy(xpath = "(//mat-icon[@id='treeActiveIcon'])[2]")
	private WebElement labelOrgStatusValue;

	@FindBy(id = "orgTitle")
	private WebElement labelOrgName;

	@FindBy(xpath = "//div[@class='mat-tree-node-click bordernode']/p | //div[@class='mat-tree-node-click treenodespace']/p | //div[@class='mat-tree-node-click practicenodespace']/p")
	private WebElement selectedOrgNodeName;

	@FindBy(xpath = "//div[@class='mat-tree-node-click bordernode']/div/mat-icon | //div[@class='mat-tree-node-click treenodespace']/div/mat-icon | //div[@class='mat-tree-node-click practicenodespace']/div/mat-icon")
	private WebElement selectedOrgNodeStatusIcon;

	@FindBy(xpath = "//mat-tree-node[@aria-level='2']")
	private WebElement clickSystemNode;

	public String clientNameValue;
	public String flagTest = "flagTest";

	@FindBy(id = "displayNameEditBtn")
	private WebElement editClientButton;

	@FindBy(id = "detailSapInput")
	private WebElement textBoxClientEditSapId;

	@FindBy(id = "timezoneSelect")
	private WebElement dropDownTimezoneClient;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement clientTextBoxTimezone;

	@FindBy(id = "detailInactiveRadio")
	private WebElement clientInactiveRadioButton;

	@FindBy(id = "detailSaveBtn")
	private WebElement clientSaveBtn;

	@FindBy(xpath = "//div[@class='ng-input']//following::span[1]")
	private WebElement expandClientDropdown;

	@FindBy(xpath = "//span[@class='ng-value-label ng-star-inserted']")
	private WebElement clientDropdownHeader;

	@FindBy(xpath = "//div[@class='ng-option ng-option-child ng-star-inserted' or @role='option' or @class='ng-option ng-option-selected ng-option-child ng-star-inserted']")
	private WebElement listofClientsInClientDropdown;

	@FindBy(xpath = "//div[@role='group']//p[contains(text(),'RECENTLY VISITED')]")
	private WebElement recentlyVisitedTitleInClientDropdown;

	@FindBy(xpath = "//div[@role='group']//p[contains(text(),'FAVORITES')]")
	private WebElement favoritesTitleInClientDropdown;

	@FindBy(xpath = "//div[@role='group']//p[contains(text(),'RECENTLY VISITED')]//following::div[@role='option']")
	private List<WebElement> recentlyVisitedClients;

	@FindBy(xpath = "//p[contains(text(),'RECENTLY VISITED')]//following::p[1]")
	private WebElement recentlyVisitedFirstClient;

	@FindBy(xpath = "//div[@role='group']//p[contains(text(),'RECENTLY VISITED')]//following::div[@role='option'][2]")
	private WebElement recentlyVisitedSecondClient;

	@FindBy(xpath = "//div[@role='group']//p[contains(text(),'FAVORITES')]//following::div[@role='option'][1]")
	private WebElement favoriteFirstClient;

	@FindBy(id = "headerBookIcon")
	private WebElement backToClientDirectory;

	@FindBy(xpath = "//div[@class='info']")
	private WebElement clientGeneralSettings;

	@FindBy(id = "searchIcon")
	private WebElement SearchiconInGeneralSetting;

	@FindBy(xpath = "//mat-icon[@id='searchIcon']")
	private WebElement searchIcon;

	@FindBy(xpath = "//input[@id='searchInput']")
	private WebElement textboxSearchInGeneralSetting;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(id = "displayClientName")
	private WebElement labelNodeNameRightPanel;

	@FindBy(className = "mat-option-text")
	private List<WebElement> listOfSuggestions;

	@FindBy(id = "treeNodeName")
	private List<WebElement> nodeStatusInGeneralSetting;

	@FindBy(xpath = "(//span[@class='value'])[3]")
	private WebElement labelClientTypeValue;

	@FindBy(id = "orgTypeValue")
	private WebElement labelOrgClientTypeValue;

	@FindBy(xpath = "//div[text()=' MOVE SYSTEM ']")
	private WebElement buttonMoveSystem;

	public CommonMethods commonMethods;

	@FindBy(xpath = "//div[@class='solution-header']")
	private WebElement activeSolutionsLabel;

	@FindBy(xpath = "//span[@id='assignment']")
	private List<WebElement> activeSolutionsListWithinSolutionActiveContainer;

	@FindBy(xpath = "//button[@id='purchaseAddButton-button']")
	private WebElement buttonShortcut;

	@FindBy(xpath = "//button[@id='applyTo']")
	private WebElement buttonApplyTo;

	@FindBy(xpath = "//button[@id='inheritFrom']")
	private WebElement buttonInheritFrom;

	@FindBy(xpath = "//span[@id='solutionStatus']")
	private List<WebElement> solutionStatus;

	@FindBy(xpath = "//a[@id='headerBookIcon']")
	private WebElement iconClientDirectory;

	@FindBy(id = "orgEditBtn")
	private WebElement editOrgButton;

	@FindBy(xpath = "//span[@id='solutionAvailable']//preceding::span[@class='form']")
	private List<WebElement> listActiveSolutions;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textBoxTimezone;

	@FindBy(xpath = "//mat-tree-node[@aria-level='1']")
	private WebElement treeClientNode;

	@FindBy(id = "treeNodeName")
	private List<WebElement> treeNode;

	@FindBy(id = "displayClientName")
	private WebElement clientNodeTitle;

	@FindBy(id = "locationOnIcon")
	private WebElement tabLocations;

	@FindBy(id = "locationTile")
	private WebElement tileLocations;

	@FindBy(id = "headerSettingIcon")
	private WebElement generalSettingsTab;

	@FindBy(id = "manageAccountsIcon")
	private WebElement tabProvidersAndResources;

	@FindBy(xpath = "//span[@class='purchaseTitle']")
	private WebElement availableToPurchaseTitle;

	@FindBy(xpath = "//div[normalize-space(text())='Purchased Solutions']")
	private WebElement purchaseSolutionsTitle;

	@FindBy(id = "unsavedConfirmBtn")
	private WebElement YesActivateButton;

	@FindBy(id = "unsavedCancelBtn")
	private WebElement activatePopupCancelbutton;

	@FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Patient Chat']/../following-sibling::div/span[text()='ACTIVATE']")
	private WebElement chatActivateButton;

	@FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Engage']/../following-sibling::div/span[text()='ACTIVATE']")
	private WebElement engageActivateButton;

	@FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Virtual Visits']/../following-sibling::div/span[text()='ACTIVATE']")
	private WebElement virtualVisitsActivateButton;

	@FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Pay']/../following-sibling::div/span[text()='ACTIVATE']")
	private WebElement payActivateButton;

	@FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Patient Portal']/../following-sibling::div/span[text()='ACTIVATE']")
	private WebElement portalActivateButton;

	@FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='PSS']/../following-sibling::div/span[text()='ACTIVATE']")
	private WebElement pSSActivateButton;

	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::span//button//mat-icon)[1]")
	private WebElement purchasedSolutionExpandButton;
	
	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::span//button//mat-icon)[2]")
	private WebElement purchasedSolutionAddOnsExpandButton;
	
	@FindBy(xpath = "(//div[@class='content'])[2]")
	private WebElement SapIdReadValue;

	private String purchaseActivateButton(String solutionImageIcon) {
		return "//div//div[@id='purchasedSolutionOrg']//following::div[@class='row']//div//span[contains(text(),'"
				+ solutionImageIcon + "')]";
	}

	@FindBy(xpath = "(//span[@class='value'])[2]")
	private WebElement duplicateSapId;

	@FindBy(xpath = "(//span[@class='info ng-star-inserted'])[1]")
	private WebElement duplicateClientName;

	@FindBy(id = "detailNameInput")
	private WebElement textboxClientName;

	@FindBy(id = "detailSapInput")
	private WebElement textboxSapId;

	@FindBy(xpath = "//ng-select[@id='clientTypeSelect']//div[@class='ng-select-container ng-has-value']")
	private WebElement dropdownClientType;

	@FindBy(xpath = "//ng-select[@id='timezoneSelect']//div[@class='ng-select-container ng-has-value']")
	private WebElement dropdownTimezone;

	@FindBy(id = "detailActiveRadio")
	private WebElement radiobuttonActive;

	@FindBy(id = "detailInactiveRadio")
	private WebElement radiobuttonInactive;

	@FindBy(xpath = "//span[normalize-space()='CANCEL']")
	private WebElement buttonCancel;

	@FindBy(xpath = "//span[normalize-space()='SAVE']")
	private WebElement buttonSave;

	@FindBy(xpath = "//div[@id='clientSolutionTitle']")
	private WebElement labelSolutionsActive;

	@FindBy(xpath = "//div[normalize-space(text())='Maximum characters reached']")
	private WebElement textboxError;

	@FindBy(id = "headerBookIcon")
	private WebElement clientTab;

	@FindBy(xpath = "(//mat-select[@role ='combobox']//div)[3]")
	private WebElement statusArrow;

	@FindBy(xpath = "//span[contains(text(),'Inactive')]")
	private WebElement inactiveStatus;

	@FindBy(id = "detailSaveBtn")
	private WebElement saveBtn;

	@FindBy(xpath = "//span[contains(text(),'Changes updated successfully')]")
	private WebElement toasterMessage;

	@FindBy(xpath = "(//p[@id='treeNodeName']//span[@tabindex=0])[1]")
	private WebElement clientNode;

	@FindBy(id = "providerTile")
	private WebElement providerAndResourcesTile;

	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' Virtual Visits ']//following::div[@aria-label='More Options']//span[@id='IconContainer'])[1]")
	private WebElement activeVirtualVisitsMoreOptions;
	
	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' Virtual Visits ']//following::div[@aria-label='More Options']//span[@id='IconContainer'])[1]")
	private WebElement activeVirtualVisitsAddOnsMoreOptions;

	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' Pay ']//following::div[@aria-label='More Options']//span[@id='IconContainer'])[1]")
	private WebElement activePayMoreOptions;

	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' Engage ']//following::div[@aria-label='More Options']//span[@id='IconContainer'])[1]")
	private WebElement activeEngageMoreOptions;

	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' Patient Portal ']//following::div[@aria-label='More Options']//span[@id='IconContainer'])[1]")
	private WebElement activePatientPortalMoreOptions;

	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' Patient Chat ']//following::div[@id='purchaseStatus']//following::div[@class='more'])[1]")
	private WebElement activePatientChatMoreOptions;
	
	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' Patient Chat ']//following::div[@aria-label='More Options']//span[@id='IconContainer'])[1]")
	private WebElement activePatientChatAddOnsMoreOptions;

	@FindBy(xpath = "(//div[@id='purchasedSolutionOrg']//following::div//span[text()=' PSS ']//following::div[@aria-label='More Options']//span[@id='IconContainer'])[1]")
	private WebElement activePSSMoreOptions;

	@FindBy(xpath = "//button[@id='highlight_off']")
	private WebElement terminateButton;

	@FindBy(xpath = "//span[@id='confirmLabel']")
	private WebElement popupTitle;

	@FindBy(xpath = "//span[@id='clearDelete']")
	private WebElement crossIconPopup;

	@FindBy(xpath = "//div[@class='backCaution']//p")
	private WebElement cautionMessage;
	
	@FindBy(id = "detailNameValidation")
	private WebElement clientNameRequiredErrorMessage;
	
	@FindBy(xpath = "//div[@id='purchasedSolutionOrg'][normalize-space(text())='Purchased Solutions Add-Ons']")
	private WebElement purchasedSolutionsAddOnsTitle;
	
	@FindBy(xpath = "//span[@id='locationTile']//following::span[1]")
	private WebElement locationCount;

	private String xpathRequiredClientType = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	List<String> activeSolutionName = new ArrayList<String>();

	public GeneralSettingsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void clickOnNewButton() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		log("Click on new button");
		buttonNew.click();
	}

	public AddOrgNodePage clickOnAddSystem() {
		clickOnNewButton();
		log("New button clicked");
		CCUtils.verifyAndWaitForElement(buttonAddNewSystem, driver);
		log("Clicked on system button");
		buttonAddNewSystem.click();
		return PageFactory.initElements(driver, AddOrgNodePage.class);
	}

	public AddOrgNodePage clickOnAddEnterprise() {
		clickOnNewButton();
		CCUtils.verifyAndWaitForElement(buttonAddNewEnterprise, driver);
		log("Clicked on enterprise button");
		CCUtils.verifyAndWaitForElement(buttonAddNewEnterprise, driver);
		buttonAddNewEnterprise.click();
		return PageFactory.initElements(driver, AddOrgNodePage.class);
	}

	public AddOrgNodePage clickOnAddPractice() {
		clickOnNewButton();
		CCUtils.verifyAndWaitForElement(buttonAddNewPractice, driver);
		log("Clicking on practice button");
		buttonAddNewPractice.click();
		return PageFactory.initElements(driver, AddOrgNodePage.class);
	}

	public String getClientName() {
		CCUtils.verifyAndWaitForElement(clientName, driver);
		if (clientName.getText().trim().contains("flagTest")) {
			clientNameValue = clientName.getText().replace(flagTest, "");
		} else {
			clientNameValue = clientName.getText();
		}
		return clientNameValue.trim();
	}

	public String getClientSapId() {
		CCUtils.verifyAndWaitForElement(labelClientSapValue, driver);
		return labelClientSapValue.getText().trim();
	}

	public String getClientTimezone() {
		CCUtils.verifyAndWaitForElement(labelClientTimezoneValue, driver);
		return labelClientTimezoneValue.getText().trim();
	}

	public String getClientStatus() {
		CCUtils.verifyAndWaitForElement(labelClientStatusValue, driver);
		return labelClientStatusValue.getText().trim();
	}

	public void clickOnClientInfoPageEditButton() {
		CCUtils.verifyAndWaitForElement(buttonClientInfoEdit, driver);
		buttonClientInfoEdit.click();
	}

	public void verifyGeneralSettingsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "General Settings");
		log("General settings page title is displayed");
	}

	public String getOrgName() {
		CCUtils.verifyAndWaitForElement(labelOrgName, driver);
		return labelOrgName.getText().trim();
	}

	public String getOrgSapId() {
		CCUtils.verifyAndWaitForElement(labelOrgSapValue, driver);
		return labelOrgSapValue.getText().trim();
	}

	public String getOrgTimezone() {
		CCUtils.verifyAndWaitForElement(labelOrgTimezoneValue, driver);
		return labelOrgTimezoneValue.getText().trim();
	}

	public String getOrgStatus() {
		CCUtils.verifyAndWaitForElement(labelOrgStatusValue, driver);
		return labelOrgStatusValue.getText().trim();
	}

	public void verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(Boolean isParentInactive) {
		CCUtils.verifyAndWaitForElement(labelOrgStatusValue, driver);
		log("Verifying org node status is inactive for newly added org node when parent is inactive");
		if (isParentInactive == true) {
			CCUtils.assertEquals(labelOrgStatusValue.getText().trim(), "cancel");
			log("Newly added org node status is inactive");
		} else {
			log("Newly added org node status is active");
		}
	}

	public void verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactiveForPractice(Boolean isParentInactive) {
		CCUtils.verifyAndWaitForElement(labelOrgStatusValue, driver);
		log("Verifying org node status is inactive for newly added org node when parent is inactive");
		if (isParentInactive == true) {
			CCUtils.assertEquals(labelOrgStatusValue.getText().trim(), "check_circle");
			log("Newly added org node status is inactive");
		} else {
			log("Newly added org node status is active");
		}
	}

	public void verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(String orgNodeName, String sapId,
			String solutionDisplayName, String timezone) {
		log("Verifying org name value");
		wait.until(ExpectedConditions.textToBePresentInElement(labelOrgName, orgNodeName));
		CCUtils.assertEquals(labelOrgName.getText().trim(), orgNodeName);
		log("Verifying org sap id value");
		wait.until(ExpectedConditions.textToBePresentInElement(labelOrgSapValue, sapId));
		CCUtils.assertEquals(labelOrgSapValue.getText().trim(), sapId);
		log("Verifying org solution display name value");
		CCUtils.verifyAndWaitForElement(labelOrgSolutionDisplayNameValue, driver);
		CCUtils.assertEquals(labelOrgSolutionDisplayNameValue.getText().trim(), solutionDisplayName);
		log("Verifying org timezone value");
		CCUtils.verifyAndWaitForElement(labelOrgTimezoneValue, driver);
		CCUtils.assertEquals(labelOrgTimezoneValue.getText().trim(), timezone);
	}

	public void verifyNewlyAddedOrgNodeDetailsInOrgTree(String orgNodeName, Boolean isOrgNodeStatusActive) {
		log("Verifying newly added org node name is displayed org tree");
		CCUtils.verifyAndWaitForElement(selectedOrgNodeName, driver);
		CCUtils.assertEquals(selectedOrgNodeName.isDisplayed(), true);
	}

	public void verifyNewlyAddedOrgNodeStatusOnGeneralSettings(Boolean isOrgNodeStatusActive) {
		CCUtils.verifyAndWaitForElement(labelOrgStatusValue, driver);
		log("Verifying org node status for newly added org node in general settings page");
		CCUtils.assertEquals(labelOrgStatusValue.isDisplayed(), true);
	}

	public void verifyHyphenSymbolIsDisplayedForSapIdField(String orgNodeName) {
		log("Verifying org name value");
		wait.until(ExpectedConditions.textToBePresentInElement(labelOrgName, orgNodeName));
		CCUtils.assertEquals(labelOrgName.getText().trim(), orgNodeName);
		log("Verifying org sap id value is blank");
		CCUtils.assertEquals(labelOrgSapValue.getText().trim(), "-");
	}

	public void clickOnSystemNode() {
		CCUtils.verifyAndWaitForElement(clickSystemNode, driver);
		log("Clicked on system node");
		clickSystemNode.click();
	}

	public void verifyEditButtonIsVisible() {
		CCUtils.verifyAndWaitForElement(editClientButton, driver);
		log("Edit button is displayed");
	}
	
	public void clickEditButtonOfClient() {
		CCUtils.verifyAndWaitForElement(editClientButton, driver);
		editClientButton.click();
		log("Clicked on edit button");
	}
	
	public void clickCancelButtonOfEditClient() {
		CCUtils.verifyAndWaitForElement(buttonClientInfoEditCancel, driver);
		buttonClientInfoEditCancel.click();
		log("Clicked on cancel button");
	}

	public void editClientNodeOnGeneralSettingPage(String sapId, String solutionsDisplayName, String timezone)
			throws InterruptedException {
		textBoxClientEditSapId.sendKeys(sapId);
		dropDownTimezoneClient.click();
		clientTextBoxTimezone.sendKeys(timezone);
		CCUtils.pressEnterKey();
	}

	public void clickInactiveRadioButtonOfClient() {
		CCUtils.verifyAndWaitForElement(radiobuttonInactive, driver);
		radiobuttonInactive.click();
		log("Clicked on Inactive radio button");
	}

	public void clickActiveRadioButtonOfClient() {
		CCUtils.verifyAndWaitForElement(radiobuttonActive, driver);
		radiobuttonActive.click();
		log("Clicked on Active radio button");
	}

	public void clickSaveButtonClient() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(clientSaveBtn, driver);
		scrollAndWait(0, 500, 200);
		clientSaveBtn.click();
		log("Clicked on save button");
	}

	public String getClientDropdownHeader() {
		CCUtils.verifyAndWaitForElement(clientDropdownHeader, driver);
		return clientDropdownHeader.getText().trim();
	}

	public void clickClientDropdownGenSettings() {
		CCUtils.verifyAndWaitForElement(expandClientDropdown, driver);
		highlightElement(expandClientDropdown);
		expandClientDropdown.click();
		log("clicked to Client Dropdown and expand the dropdown.");
	}

	public String getFirstClientDropdownRecentlyVisits() {
		CCUtils.verifyAndWaitForElement(recentlyVisitedFirstClient, driver);
		return recentlyVisitedFirstClient.getText().trim();
	}

	public List<String> getListOfRecentVisitsInClientDropdown() {
		List<String> recentVisitsLists = new ArrayList<String>();
		for (int i = 0; i < recentlyVisitedClients.size(); i++) {
			recentVisitsLists.add(recentlyVisitedClients.get(i).getText());
		}
		return recentVisitsLists;
	}

	public String clickAnyRecentVisitsInClientDropdown() {
		CCUtils.verifyAndWaitForElement(recentlyVisitedSecondClient, driver);
		highlightElement(recentlyVisitedSecondClient);
		String recentVisitClient = recentlyVisitedSecondClient.getText().trim();
		recentlyVisitedSecondClient.click();
		return recentVisitClient;
	}

	public String clickAnyFavClientInClientDropdown() {
		CCUtils.verifyAndWaitForElement(favoriteFirstClient, driver);
		highlightElement(favoriteFirstClient);
		String favoritesSecondClient = favoriteFirstClient.getText().trim();
		favoriteFirstClient.click();
		return favoritesSecondClient;
	}

	public void clickClientDirectoryIcon() {
		CCUtils.verifyAndWaitForElement(backToClientDirectory, driver);
		javascriptClick(backToClientDirectory);
		log("clicked to client directory icon");
	}

	public String getGeneralSettingsRightSectionClientName() {
		CCUtils.verifyAndWaitForElement(clientGeneralSettings, driver);
		highlightElement(clientGeneralSettings);
		return clientGeneralSettings.getText();
	}

	public boolean checkVisiblityOfFavTitle() {
		try {
			if (favoritesTitleInClientDropdown.isDisplayed()) {
				log("Favorites Section is displayed in Client Dropdown.");
				return false;
			}
		} catch (RuntimeException ee) {
			if (ee.toString().contains("NoSuchElementException")) {
				log("Expected Exception : " + ee.toString() + " as Favorites is not present");
			}
		}
		return true;
	}

	public void enterSearchText(String nodeName) {
		CCUtils.verifyAndWaitForElement(textboxSearchInGeneralSetting, driver);
		textboxSearchInGeneralSetting.sendKeys(nodeName);
		log("Entered text in search :" + nodeName);
	}
	
	public void pressEnterInSearchText() {
		CCUtils.verifyAndWaitForElement(textboxSearchInGeneralSetting, driver);
		textboxSearchInGeneralSetting.sendKeys(Keys.ENTER);
		log("press enter key");
	}

	public void clearSearchText() {
		CCUtils.verifyAndWaitForElement(textboxSearchInGeneralSetting, driver);
		textboxSearchInGeneralSetting.clear();
		log("text in search is cleared");
	}

	public String defaultTextInSearch() {
		CCUtils.verifyAndWaitForElement(textboxSearchInGeneralSetting, driver);
		log("Default text in search :" + textboxSearchInGeneralSetting.getAttribute("placeholder"));
		return textboxSearchInGeneralSetting.getAttribute("placeholder");
	}

	public String returnGeneralSettingTitle() {
		CCUtils.verifyAndWaitForElement(labelNodeNameRightPanel, driver);
		log("Node title on right panel is : " + labelNodeNameRightPanel.getText());
		return labelNodeNameRightPanel.getText();
	}

	public void verifyStatusIcon() {
		CCUtils.verifyAndWaitForElement(nodeStatusInGeneralSetting.get(0), driver);
		CCUtils.assertTrue(nodeStatusInGeneralSetting.get(0).isDisplayed(), "Status is displayed");
	}

	public void clickFirstInAutoSuggestion() {
		CCUtils.verifyAndWaitForElementPresence(selectFirstInAutoSuggestion, driver);
		if (selectFirstInAutoSuggestion.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstInAutoSuggestion);

		} else {
			log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present in client");
		}
	}
	
	public String autosuggestion(String orgName) {
		return "//span[@class='mat-option-text']//span//b[normalize-space(text())='"+orgName+"']";
	}
	
	public void clickFirstInAutoSuggestion(String orgName) {
		WebElement orgAutoSuggestionOption = driver.findElement(By.xpath(autosuggestion(orgName)));
		CCUtils.verifyAndWaitForElementPresence(orgAutoSuggestionOption, driver);
		if (orgAutoSuggestionOption.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			javascriptClick(orgAutoSuggestionOption);

		} else {
			log(orgAutoSuggestionOption.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present in client");
		}
	}

	public void verifyAutoSuggestionList(String searchedNode) {
		CCUtils.verifyAndWaitForElement(listOfSuggestions.get(0), driver);
		for (int count = 0; count <= listOfSuggestions.size(); count++) {
			if (count == listOfSuggestions.size()) {
				break;
			} else {
				log("option " + (count + 1) + " in autosuggestion is '" + listOfSuggestions.get(count).getText()
						+ "' , and Searched text is " + searchedNode);

				CCUtils.assertTrue(listOfSuggestions.get(count).getText().contains(searchedNode),
						"partial search present in list");
				continue;
			}
		}
	}

	public String getClientType() {
		CCUtils.verifyAndWaitForElement(labelClientTypeValue, driver);
		return labelClientTypeValue.getText().trim();
	}

	public String getOrgClientType() {
		CCUtils.verifyAndWaitForElement(labelOrgClientTypeValue, driver);
		return labelOrgClientTypeValue.getText().trim();
	}

	public MoveSystemPage clickOnMoveSystem() {
		CCUtils.verifyAndWaitForElement(buttonMoveSystem, driver);
		log("Clicked on move system button");
		buttonMoveSystem.click();
		return PageFactory.initElements(driver, MoveSystemPage.class);
	}

	public void verifyClientNameValue(String clientNameValue) {
		CCUtils.verifyAndWaitForElement(clientName, driver);
		CCUtils.assertEquals(clientName.getText().trim(), clientNameValue);
	}

	public void verifySystemNodeFieldData(String systemNameValue, String systemSapId, String systemTimezone,
			String ClientType, Boolean isNodeStatusActive) {
		searchNode(systemNameValue);
		CCUtils.assertEquals(getOrgSapId(), systemSapId);
		CCUtils.assertEquals(getOrgTimezone(), systemTimezone);
		CCUtils.assertEquals(getOrgClientType(), ClientType);
		log("Verifying system node status in general settings page");
		if (isNodeStatusActive == true) {
			CCUtils.assertEquals(getOrgStatus(), "active");
			log("System node status is active");
		} else {
			CCUtils.assertEquals(getOrgStatus(), "inactive");
			log("System node status is inactive");
		}
	}

	public void verifyEnterpriseNodeFieldData(String enterpriseNameValue, String enterpriseSapId,
			String enterpriseTimezone, String ClientType, Boolean isNodeStatusActive) {
		clearSearchText();
		searchNode(enterpriseNameValue);
		CCUtils.assertEquals(getOrgSapId(), enterpriseSapId);
		CCUtils.assertEquals(getOrgTimezone(), enterpriseTimezone);
		CCUtils.assertEquals(getOrgClientType(), ClientType);
		log("Verifying enterprise node status in general settings page");
		if (isNodeStatusActive == true) {
			CCUtils.assertEquals(getOrgStatus(), "active");
			log("Enterprise node status is active");
		} else {
			CCUtils.assertEquals(getOrgStatus(), "inactive");
			log("Enterprise node status is inactive");
		}
	}

	public void verifyPracticeNodeFieldData(String practiceNameValue, String practiceSapId, String practiceTimezone,
			String ClientType, Boolean isNodeStatusActive) {
		clearSearchText();
		searchNode(practiceNameValue);
		CCUtils.assertEquals(getOrgSapId(), practiceSapId);
		CCUtils.assertEquals(getOrgTimezone(), practiceTimezone);
		CCUtils.assertEquals(getOrgClientType(), ClientType);
		log("Verifying practice node status in general settings page");
		if (isNodeStatusActive == true) {
			CCUtils.assertEquals(getOrgStatus(), "active");
			log("Practice node status is active");
		} else {
			CCUtils.assertEquals(getOrgStatus(), "inactive");
			log("Practice node status is inactive");
		}
	}

	public void searchNode(String nodeName) {
		enterSearchText(nodeName);
		clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
	}

	public Boolean returnClientNodeStatusValue() {
		if (getClientStatus().equals("active"))
			return true;
		else
			return false;
	}

	public Boolean returnOrgStatus() {
		if (getOrgStatus().equals("active"))
			return true;
		else
			return false;
	}

	public void clickClientDirectory() {
		CCUtils.verifyAndWaitForElement(clientTab, driver);
		javascriptClick(clientTab);
		log("clicked on client directory tab:");
	}

	public String getDuplicateSap() {
		CCUtils.verifyAndWaitForElement(duplicateSapId, driver);
		String clientSap = duplicateSapId.getText();
		return clientSap;
	}

	public String getDuplicateClientName() {
		CCUtils.verifyAndWaitForElement(duplicateClientName, driver);
		String clientName = duplicateClientName.getText();
		return clientName;
	}

	public void enterNewClientName(String newClientName) {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.sendKeys(newClientName);
		log("Entered text in Client name field is :" + newClientName);
	}

	public void enterNewClientSap(String newSapId) {
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(newSapId);
		log("Entered text in Client name field is :" + newSapId);
	}

	public String getEditButtonText() {
		CCUtils.verifyAndWaitForElement(editClientButton, driver);
		return editClientButton.getText();
	}

	public void clickClientType() {
		CCUtils.verifyAndWaitForElement(dropdownClientType, driver);
		dropdownClientType.click();
		log("clicked on Client type dropdown in Edit Client screen");
	}

	public void clickTimeZoneDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		log("clicked on Timezone dropdown in Edit Client screen");
	}

	public void clickCancel() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("clicked on Cancel button in Edit Client screen");
	}

	public void clickSave() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		buttonSave.click();
		log("clicked on Save button in Edit Client screen");
	}

	public void clearClientNameField() {
		CCUtils.clearTextboxField(textboxClientName);
		log("deleted input in Client name field");
	}

	public void clearSapField() {
		CCUtils.clearTextboxField(textboxSapId);
		log("deleted input in Sap id field");
	}

	public String getErrorMessage() {
		CCUtils.verifyAndWaitForElement(textboxError, driver);
		String errorMessage = textboxError.getText();
		return errorMessage;
	}

	public void verifyNoErrorPresent() {
		CCUtils.assertTrue(textboxError.isDisplayed(), "error message displayed");
		log("error message not displayed");
	}

	public void verifySolutionActiveWithinThisContainer() throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(activeSolutionsLabel, driver);
		if (activeSolutionsLabel.getText()
				.contains("Solutions Active within this [Client, System, Enterprise, Practice]")) {
			log("Verifying whether 'Solutions Active within this [Client, System, Enterprise, Practice]' container is present or not.");
			CCUtils.assertTrue(true, "Solutions Active within this [Client, System, Enterprise, Practice] is present.");
			;
		}
	}

	public void verifyListOfActiveSolutions(List<String> listOfActiveSolutionsAdded) throws AWTException {
		for (String selectSolution : listOfActiveSolutionsAdded) {
			for (int i = 0; i < activeSolutionsListWithinSolutionActiveContainer.size(); i++) {
				CCUtils.scrollPageToEnd();
				CCUtils.verifyAndWaitForElement(activeSolutionsListWithinSolutionActiveContainer.get(i), driver);
				if ((activeSolutionsListWithinSolutionActiveContainer.get(i).getText().trim())
						.contains(selectSolution)) {
					CCUtils.assertTrue(true,
							"Verifying added solution :" + selectSolution
									+ " is present within List of Active Solutions."
									+ activeSolutionsListWithinSolutionActiveContainer.toString());
					log("Solution name : " + selectSolution + " is present.");
					CCUtils.assertTrue((solutionStatus.get(i).getText().trim()).contains("Newly purchased"),
							"Verifying added solution's status is newly ppurchased or not.");
				}
			}
		}
	}

	public void clickOnShortCutButton() {
		CCUtils.verifyAndWaitForElement(buttonShortcut, driver);
		buttonShortcut.click();
		log("ShortCut button clicked");
	}

	public ApplyInheritPracticeSettingsPage clickOnApplyToButton() {
		CCUtils.verifyAndWaitForElement(buttonApplyTo, driver);
		buttonApplyTo.click();
		log("Apply To button clicked");
		return PageFactory.initElements(driver, ApplyInheritPracticeSettingsPage.class);
	}

	public ApplyInheritPracticeSettingsPage clickOnInheritFromButton() {
		CCUtils.verifyAndWaitForElement(buttonInheritFrom, driver);
		buttonInheritFrom.click();
		log("Inherit From button clicked");
		return PageFactory.initElements(driver, ApplyInheritPracticeSettingsPage.class);
	}

	public void clickOnClientDirectoryIcon() {
		CCUtils.verifyAndWaitForElement(iconClientDirectory, driver);
		javascriptClick(iconClientDirectory);
		log("Clicked on client directory icon");
	}

	public String getNameInEditMode() {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		return textboxClientName.getAttribute("value").trim();
	}

	public String getSapIdInEditMode() {
		CCUtils.verifyAndWaitForElement(textBoxClientEditSapId, driver);
		return textBoxClientEditSapId.getAttribute("value").trim();
	}

	public void clickOnEditButtonOfOrg() {
		CCUtils.verifyAndWaitForElement(editOrgButton, driver);
		log("Clicked on edit button");
		editOrgButton.click();
	}

	public String getOrgSolutionDisplayName() {
		CCUtils.verifyAndWaitForElement(labelOrgSolutionDisplayNameValue, driver);
		return labelOrgSolutionDisplayNameValue.getText().trim();
	}

	public List<String> getListOfActiveSolutions() throws AWTException {
		for (int i = 0; i < listActiveSolutions.size(); i++) {
			CCUtils.scrollPageToEnd();
			activeSolutionName.add(listActiveSolutions.get(i).getText().trim());
		}
		return activeSolutionName;
	}

	public void selectTimezone(String timezone) {
		clickTimeZoneDropdown();
		textBoxTimezone.sendKeys(timezone);
		CCUtils.pressEnterKey();
	}

	private WebElement prepareDynamicXpathForClientType(String xpathValue, String substitutionValue) {
		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectClientType(String clientTypeName) {
		WebElement clientType = prepareDynamicXpathForClientType(xpathRequiredClientType, clientTypeName);
		CCUtils.verifyAndWaitForElement(clientType, driver);
		clientType.click();
		log("Clicked on : " + clientTypeName);
	}

	public void selectClientNodeStatus(String existingStatusValue) {
		if ((existingStatusValue.trim().contains("active")) == true)
			clickInactiveRadioButtonOfClient();
		else if (existingStatusValue.equals("inactive") == true)
			clickActiveRadioButtonOfClient();
	}

	public String getClientNodeStatus() {
		CCUtils.verifyAndWaitForElement(clientInactiveRadioButton, driver);
		CCUtils.verifyAndWaitForElement(radiobuttonActive, driver);
		if ((clientInactiveRadioButton.getAttribute("class").contains("mat-radio-checked") == true)) {
			return "inactive";
		} else {
			return "active";
		}
	}

	public void selectClientNodeInTheTree() {
		CCUtils.verifyAndWaitForElement(treeClientNode, driver);
		treeClientNode.click();
		log("Clicked on the client node in the tree");
	}

	public void clickSearchIcon() {
		CCUtils.verifyAndWaitForElement(SearchiconInGeneralSetting, driver);
		javascriptClick(SearchiconInGeneralSetting);
		log("Clicked on Search icon");

	}

	public void verifynodesPresentInClientGeneralSettings(String orgNode) {
		for (WebElement ele : treeNode) {
			if (ele.equals(orgNode)) {
				log("tree node :" + ele + " is equal to provided org node " + orgNode);
			}
		}
	}

	public void verifyClientNodeIsSelected() {
		CCUtils.verifyAndWaitForElement(clientNodeTitle, driver);
		CCUtils.assertEquals(clientNodeTitle.isDisplayed(), true);
		log("Client node title is displayed");
	}

	public void verifySearchResult(String actualTitle, String expectedTitle) {
		CCUtils.assertTrue(actualTitle.contains(expectedTitle), "Searched node not selected. Title not found");
	}

	public void verifySearchedResult() {
		CCUtils.verifyAndWaitForElement(clientNodeTitle, driver);
		CCUtils.assertEquals(clientNodeTitle.isDisplayed(), true);
	}

	public void verifySearchResultFunctionality(List<String> nodesInClient, String expectedTitle) {
		if (nodesInClient.contains(expectedTitle)) {
			log("Node present in client's general setting page");
		} else {
			log("Test Case failed, node not present under client");
		}
	}

	public void clickOnLocationsTab() {
		CCUtils.verifyAndWaitForElement(tabLocations, driver);
		javascriptClick(tabLocations);
		log("Clicked on Locations tab");
	}

	public void clickOnLocationsTile() {
		CCUtils.verifyAndWaitForElement(tileLocations, driver);
		javascriptClick(tileLocations);
		log("Clicked on Locations tile");
	}

	public void verifyLocationsTab() {
		log("Verify 'Locations Tab' on General Settings page");
		CCUtils.verifyAndWaitForElement(tabLocations, driver);
		CCUtils.assertEquals(tabLocations.isDisplayed(), true);
	}

	public void clickOnGeneralSettingsTab() {
		CCUtils.verifyAndWaitForElement(generalSettingsTab, driver);
		javascriptClick(generalSettingsTab);
		log("Clicked on General Settings Tab");
	}

	public void verifyProvidersAndResourcesTab() {
		log("Verify 'ProvidersAndResources' on General Settings page");
		CCUtils.verifyAndWaitForElement(tabProvidersAndResources, driver);
		CCUtils.assertEquals(tabProvidersAndResources.isDisplayed(), true);
	}

	public void clickOnProvidersAndResourcesTab() {
		CCUtils.verifyAndWaitForElement(tabProvidersAndResources, driver);
		javascriptClick(tabProvidersAndResources);
		log("Clicked on ProvidersAndResources tab");
	}

	public void verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(String orgNodeName) {
		log("Verifying org name value");
		CCUtils.verifyAndWaitForElement(labelNodeNameRightPanel, driver);
		CCUtils.assertEquals(labelNodeNameRightPanel.isDisplayed(), true);
		log("Verified Newly Added Org Node Details In Org Tree");
	}

	public void clickFirstOption() {
		CCUtils.verifyAndWaitForElement(selectFirstInAutoSuggestion, driver);
		if (selectFirstInAutoSuggestion.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstInAutoSuggestion);

		} else {
			log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present in client");
		}
	}

	public void verifyAvailableToPurchaseSection() {
		log("Verify 'Available To Purchase' section on General Settings page");
		CCUtils.scrollPageViaElement(driver, availableToPurchaseTitle);
		CCUtils.verifyAndWaitForElement(availableToPurchaseTitle, driver);
		CCUtils.assertEquals(availableToPurchaseTitle.isDisplayed(), true);
	}

	public void verifyPurchaseSolutionsSection() {
		log("Verify 'Purchase Solutions' section on General Settings page");
		CCUtils.scrollPageViaElement(driver, purchaseSolutionsTitle);
		CCUtils.verifyAndWaitForElement(purchaseSolutionsTitle, driver);
		CCUtils.assertEquals(purchaseSolutionsTitle.isDisplayed(), true);
	}
	
	public void verifyPurchaseSolutionsAddOnsSection() {
		log("Verify 'Purchase Solutions Add Ons' section on General Settings page");
		CCUtils.scrollPageViaElement(driver, purchasedSolutionsAddOnsTitle);
		CCUtils.verifyAndWaitForElement(purchasedSolutionsAddOnsTitle, driver);
		CCUtils.assertEquals(purchasedSolutionsAddOnsTitle.isDisplayed(), true);
	}

	public void clickToActivateButtonOfVirtualVisitsSolution() {
		CCUtils.scrollPageViaElement(driver, virtualVisitsActivateButton);
		CCUtils.verifyAndWaitForElement(virtualVisitsActivateButton, driver);
		virtualVisitsActivateButton.click();
		log("clicked to activate button of Virtual visits");
	}

	public void clickToActivateButtonOfPaySolution() {
		CCUtils.verifyAndWaitForElement(payActivateButton, driver);
		payActivateButton.click();
		log("clicked to activate button of Pay");
	}

	public void clickToActivateButtonOfEngageSolution() {
		CCUtils.verifyAndWaitForElement(engageActivateButton, driver);
		engageActivateButton.click();
		log("clicked to activate button of Engage");
	}

	public void clickToActivateButtonOfChatSolution() {
		CCUtils.verifyAndWaitForElement(chatActivateButton, driver);
		chatActivateButton.click();
		log("clicked to activate button of Chat");
	}

	public void clickToActivateButtonOfPortalSolution() {
		CCUtils.verifyAndWaitForElement(portalActivateButton, driver);
		portalActivateButton.click();
		log("clicked to activate button of Portal");
	}

	public void clickToActivateButtonOfPSSSolution() {
		CCUtils.verifyAndWaitForElement(pSSActivateButton, driver);
		pSSActivateButton.click();
		log("clicked to activate button of PSS");
	}

	public void verifySolutionIsPresentInPurchasedSolutions(String solutionImageIcon) {
		WebElement element = driver.findElement(By.xpath(purchaseActivateButton(solutionImageIcon)));
		CCUtils.verifyAndWaitForElement(element, driver);
		CCUtils.scrollPageViaElement(driver, element);
		element.click();
		log("Verified that desired solution " + solutionImageIcon + " is within purchased solutions section");
	}

	public void clickToYesActivateButtonInActivateSolutions() {
		CCUtils.verifyAndWaitForElement(YesActivateButton, driver);
		YesActivateButton.click();
		log("click to Yes,Activate Button in Activate solutions popup");
	}

	public void enterClientName(String clientName) {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.clear();
		textboxClientName.sendKeys(clientName);
		log("Entered text in Client name field is :" + clientName);
	}

	public void changeStatusToInactive() {
		CCUtils.verifyAndWaitForElement(statusArrow, driver);
		statusArrow.click();
		log("clicked to arrow button");
		inactiveStatus.click();
		log("Clicked on inactive status");
		saveBtn.click();
		log("Clicked on save");
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void clickToExpandPurchaseSolutions() {
		CCUtils.scrollPageViaElement(driver, purchasedSolutionExpandButton);
		CCUtils.verifyAndWaitForElement(purchasedSolutionExpandButton, driver);
		purchasedSolutionExpandButton.click();
		log("click to purchased solution expand button");
	}
	
	public void clickToExpandPurchaseSolutionsAddOns() {
		CCUtils.scrollPageViaElement(driver, purchasedSolutionAddOnsExpandButton);
		CCUtils.verifyAndWaitForElement(purchasedSolutionAddOnsExpandButton, driver);
		purchasedSolutionAddOnsExpandButton.click();
		log("click to purchased solution add ons expand button");
	}

	public void clickToVirtualVisitsMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activeVirtualVisitsMoreOptions);
		CCUtils.verifyAndWaitForElement(activeVirtualVisitsMoreOptions, driver);
		activeVirtualVisitsMoreOptions.click();
		log("click to virtual visits more options");
	}
	
	public void clickToVirtualVisitsAddOnsMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activeVirtualVisitsAddOnsMoreOptions);
		CCUtils.verifyAndWaitForElement(activeVirtualVisitsAddOnsMoreOptions, driver);
		activeVirtualVisitsAddOnsMoreOptions.click();
		log("click to virtual visits add ons more options");
	}

	public void clickToPayMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activePayMoreOptions);
		CCUtils.verifyAndWaitForElement(activePayMoreOptions, driver);
		activePayMoreOptions.click();
		log("click to pay more options");
	}

	public void clickToEngageMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activeEngageMoreOptions);
		CCUtils.verifyAndWaitForElement(activeEngageMoreOptions, driver);
		activeEngageMoreOptions.click();
		log("click to engage more options");
	}

	public void clickToPatientPortalMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activePatientPortalMoreOptions);
		CCUtils.verifyAndWaitForElement(activePatientPortalMoreOptions, driver);
		activePatientPortalMoreOptions.click();
		log("click to patient portal more options");
	}

	public void clickToPatientChatMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activePatientChatMoreOptions);
		CCUtils.verifyAndWaitForElement(activePatientChatMoreOptions, driver);
		activePatientChatMoreOptions.click();
		log("click to patient chat more options");
	}
	
	public void clickToPatientChatAddOnsMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activePatientChatAddOnsMoreOptions);
		CCUtils.verifyAndWaitForElement(activePatientChatAddOnsMoreOptions, driver);
		activePatientChatAddOnsMoreOptions.click();
		log("click to patient chat add ons more options");
	}

	public void clickToPSSMoreOptions() {
		CCUtils.scrollPageViaElement(driver, activePSSMoreOptions);
		CCUtils.verifyAndWaitForElement(activePSSMoreOptions, driver);
		activePSSMoreOptions.click();
		log("click to PSS more options");
	}

	public void clickToTerminateOption() {
		CCUtils.verifyAndWaitForElement(terminateButton, driver);
		terminateButton.click();
		log("click to terminate option");
	}

	public void verifyTerminatePopupTitle() {
		CCUtils.verifyAndWaitForElement(popupTitle, driver);
		if (popupTitle.getText().contains("Terminate - ")) {
			CCUtils.assertTrue(true, "verifying popup title");
		}
	}

	public void verifyActivatePopupTitle() {
		CCUtils.verifyAndWaitForElement(popupTitle, driver);
		if (popupTitle.getText().contains("Activate - ")) {
			CCUtils.assertTrue(true, "verifying popup title");
		}
	}

	public void verifyActivateTerminatePopupCancelButtonAndCrossIcon() {
		CCUtils.verifyAndWaitForElement(activatePopupCancelbutton, driver);
		log("cancel button is present in pop up");
		CCUtils.verifyAndWaitForElement(crossIconPopup, driver);
		log("cross iocn is present in pop up");
	}

	public void cautionActivateMessage(String practiceName) {
		CCUtils.verifyAndWaitForElement(cautionMessage, driver);
		CCUtils.assertEquals(cautionMessage.getText().toString(),
				"Before continuing, are you sure " + practiceName + " has purchased this solution?");
	}

	public void cautionTerminateMessage(String practiceName) {
		CCUtils.verifyAndWaitForElement(cautionMessage, driver);
		CCUtils.assertEquals(cautionMessage.getText().toString(),
				"Before continuing, are you sure this solution is on no longer used by " + practiceName + "?");
	}

	public void clickToClientNode() {
		CCUtils.verifyAndWaitForElement(clientNode, driver);
		clientNode.click();
		log("click to client node in general settings");
	}

	public void verifyProvidersAndResourcesTile() {
		log("Verify 'ProvidersAndResources' Tile on General Settings page");
		CCUtils.verifyAndWaitForElement(providerAndResourcesTile, driver);
		CCUtils.assertEquals(providerAndResourcesTile.isDisplayed(), true);
	}

	public void clickOnProvidersAndResourcesTile() {
		CCUtils.verifyAndWaitForElement(providerAndResourcesTile, driver);
		javascriptClick(providerAndResourcesTile);
		log("Clicked on ProvidersAndResources tile");
	}

	public void navigatetoClientNode(String clientName) throws Exception {
		WebElement clientNode = driver
				.findElement(By.xpath("//p[@id='treeNodeName']/span[normalize-space(text())='" + clientName + "']"));
		javascriptClick(clientNode);
	}
	
	public String getSAPIdValueForBasicInfo() {
		CCUtils.scrollPageViaElement(driver, SapIdReadValue);
		CCUtils.verifyAndWaitForElement(SapIdReadValue, driver);
		log("fetched the value of sap id : "+SapIdReadValue.getText().trim());
		return SapIdReadValue.getText().trim();
	}
	
	public String getClientNameErrorMessage() {
		CCUtils.verifyAndWaitForElement(clientNameRequiredErrorMessage, driver);
		return clientNameRequiredErrorMessage.getText().trim();
	}
	
	public String getLocationCount() {
		CCUtils.verifyAndWaitForElement(locationCount, driver);
		return locationCount.getText().trim();
	}
}
