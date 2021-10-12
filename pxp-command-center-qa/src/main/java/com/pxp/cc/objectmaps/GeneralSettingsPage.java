// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.pxp.cc.util.CCUtils;

public class GeneralSettingsPage extends CCMainPage {

	@FindBy(className = "info")
	private WebElement clientName;

	@FindBy(xpath = "(//span[@class='value'])[2]")
	private WebElement labelClientSapValue;

	@FindBy(xpath = "(//span[@class='value'])[4]")
	private WebElement labelClientTimezoneValue;

	@FindBy(xpath = "//span[@class='link value']")
	private WebElement labelClientStatusValue;

	@FindBy(id = "detailEditLabel")
	private WebElement buttonClientInfoEdit;

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

	@FindBy(xpath = "//span[@class='link inputvalue']")
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

	@FindBy(id = "detailEditLabel")
	private WebElement editClientButton;

	@FindBy(id = "detailSapInput")
	private WebElement textBoxClientEditSapId;

	@FindBy(id = "timezoneSelect")
	private WebElement dropDownTimezoneClient;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement clientTextBoxTimezone;

	@FindBy(id = "detailInactiveRadio")
	private WebElement ClientInactiveRadioButton;

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

	@FindBy(id = "searchInput")
	private WebElement textboxSearchInGeneralSetting;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(className = "info")
	private WebElement labelNodeNameRightPanel;

	@FindBy(className = "mat-option-text")
	private List<WebElement> listOfSuggestions;

	@FindBy(id = "treeActiveIcon")
	private List<WebElement> nodeStatusInGeneralSetting;

	@FindBy(xpath = "(//span[@class='value'])[3]")
	private WebElement labelClientTypeValue;

	@FindBy(id = "orgTypeValue")
	private WebElement labelOrgClientTypeValue;

	@FindBy(xpath = "//div[text()=' MOVE SYSTEM ']")
	private WebElement buttonMoveSystem;

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(xpath = "//div[@class='flex']/span//following::a")
	private WebElement linkClientName;

	@FindBy(id = "snackCloseBtn")
	private WebElement buttonCloseToaster;

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
		log("Clicked on practice button");
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
			CCUtils.assertEquals(labelOrgStatusValue.getText().trim(), "inactive");
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
		CCUtils.assertEquals(selectedOrgNodeName.getText().trim(), orgNodeName);
		CCUtils.verifyAndWaitForElement(selectedOrgNodeStatusIcon, driver);
		log("Verifying org node status for newly added org node in org tree");
		if (isOrgNodeStatusActive == true) {
			CCUtils.assertEquals(selectedOrgNodeStatusIcon.getText().trim(), "check_circle");
			log("Newly added org node status is active");
		} else {
			CCUtils.assertEquals(selectedOrgNodeStatusIcon.getText().trim(), "cancel");
			log("Newly added org node status is inactive");
		}
	}

	public void verifyNewlyAddedOrgNodeStatusOnGeneralSettings(Boolean isOrgNodeStatusActive) {
		CCUtils.verifyAndWaitForElement(labelOrgStatusValue, driver);
		log("Verifying org node status for newly added org node in general settings page");
		if (isOrgNodeStatusActive == true) {
			CCUtils.assertEquals(labelOrgStatusValue.getText().trim(), "active");
			log("Newly added org node status is active");
		} else {
			CCUtils.assertEquals(labelOrgStatusValue.getText().trim(), "inactive");
			log("Newly added org node status is inactive");
		}
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

	public void clickEditButtonOfClient() {
		CCUtils.verifyAndWaitForElement(editClientButton, driver);
		log("Clicked on edit button");
		editClientButton.click();
	}

	public void editClientNodeOnGeneralSettingPage(String sapId, String solutionsDisplayName, String timezone)
			throws InterruptedException {
		textBoxClientEditSapId.sendKeys(sapId);
		dropDownTimezoneClient.click();
		clientTextBoxTimezone.sendKeys(timezone);
		CCUtils.pressEnterKey();
	}

	public void clickInactiveRadioButtonOfClient() {
		CCUtils.verifyAndWaitForElement(ClientInactiveRadioButton, driver);
		log("Clicked on Inactive radio button");
		ClientInactiveRadioButton.click();
	}

	public void clickSaveButtonClient() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(clientSaveBtn, driver);
		log("Clicked on save button");
		scrollAndWait(0, 500, 200);
		clientSaveBtn.click();
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

	public ClientDirectoryPage clickClientDirectoryIcon() {
		CCUtils.verifyAndWaitForElement(backToClientDirectory, driver);
		backToClientDirectory.click();
		return PageFactory.initElements(driver, ClientDirectoryPage.class);
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
		CCUtils.verifyAndWaitForElement(selectFirstInAutoSuggestion, driver);
		if (selectFirstInAutoSuggestion.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			selectFirstInAutoSuggestion.click();

		} else {
			log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
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

	public void verifyErrorMessageInactiveTargetClient() {
		log("Verifying toaster message for inactive target client");
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		CCUtils.assertEquals(toasterMessage.getText(), "Client selected is inactive. Activate client and try again.");
	}

	public void verifySuccessToasterMessage(String systemName, String clientNameValue) {
		log("Verifying success toaster message");
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		CCUtils.assertEquals(toasterMessage.getText(),
				"SUCCESS " + systemName + " has been successfully moved to client " + clientNameValue);
		linkClientName.click();
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

	public void verifyEnterpriseNodeFieldData(String enterpriseNameValue, String enterpriseSapId, String enterpriseTimezone,
			String ClientType, Boolean isNodeStatusActive) {
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

	public void closeToasterMessage() {
		CCUtils.verifyAndWaitForElement(buttonCloseToaster, driver);
		log("Clicked on cross icon");
		buttonCloseToaster.click();
	}
	
	public void verifyPracticeSuccessToasterMessage(String practiceName, String clientNameValue) {
		log("Verifying success toaster message");
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		CCUtils.assertEquals(toasterMessage.getText(),
				"SUCCESS " + practiceName + " has been successfully moved to client " + clientNameValue);
		linkClientName.click();
		CCUtils.verifyAndWaitForElement(clientName, driver);
		CCUtils.assertEquals(clientName.getText().trim(), clientNameValue);
	}

	
	
}
