// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.pxp.cc.util.CCUtils;

public class GeneralSettingsPage extends CCMainPage {

	@FindBy(id = "detailInfo")
	private WebElement clientName;

	@FindBy(id = "detailSapValue")
	private WebElement labelClientSapValue;

	@FindBy(id = "detailTimezoneInput")
	private WebElement labelClientTimezoneValue;

	@FindBy(xpath = "//span[@class='link value ng-star-inserted']")
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

	@FindBy(id = "orgSolutionNull")
	private WebElement labelOrgSolutionDisplayNameValue;

	@FindBy(id = "orgTimezoneValue")
	private WebElement labelOrgTimezoneValue;

	@FindBy(xpath = "//span[@class='link inputvalue ng-star-inserted']")
	private WebElement labelOrgStatusValue;

	@FindBy(id = "orgTitle")
	private WebElement labelOrgName;

	@FindBy(xpath = "//div[@class='mat-tree-node-click bordernode']/p | //div[@class='mat-tree-node-click treenodespace']/p | //div[@class='mat-tree-node-click practicenodespace']/p")
	private WebElement selectedOrgNodeName;

	@FindBy(xpath = "//div[@class='mat-tree-node-click bordernode']/div/mat-icon | //div[@class='mat-tree-node-click treenodespace']/div/mat-icon | //div[@class='mat-tree-node-click practicenodespace']/div/mat-icon")
	private WebElement selectedOrgNodeStatusIcon;

	public String clientNameValue;
	public String flagTest = "flagTest";

	public GeneralSettingsPage(WebDriver driver) {
		super(driver);
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

}
