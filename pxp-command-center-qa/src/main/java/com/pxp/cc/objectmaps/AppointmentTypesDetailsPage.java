// Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class AppointmentTypesDetailsPage extends CCMainPage {

	public AppointmentTypesDetailsPage(WebDriver driver) {
		super(driver);

	}

	@FindBy(id = "headerNavbar")
	private WebElement appointmentTypeTitle;

	@FindBy(xpath = "//span[normalize-space(text())='All Appointment Types']")
	private WebElement appointmentTypeDetailsTitle;

	@FindBy(xpath = "//div[normalize-space(text())='Appointment Type Display Info']")
	private WebElement appTypeDisplayInfoLabel;

	@FindBy(id = "displayNameSettingsBtn")
	private WebElement labelCopySettings;

	@FindBy(id = "displayNameSaveBtn")
	private WebElement saveAppTypeInfo;

	@FindBy(xpath = "//mat-icon[normalize-space(text())='edit']")
	private WebElement labelEditButton;

	@FindBy(xpath = "//span[contains(text(),'Appointment Type Display Name (EN)')]")
	private WebElement labelAppTypeDisplayNameEn;

	@FindBy(xpath = "//span[contains(text(),'Appointment Type Display Name (ES)')]")
	private WebElement labelAppTypeDisplayNameEs;

	@FindBy(xpath = "//input[@formcontrolname='en']")
	private WebElement textboxDisplayNameEn;

	@FindBy(xpath = "//input[@formcontrolname='es']")
	private WebElement textboxDisplayNameEs;

	@FindBy(xpath = "(//div[normalize-space(text())='Maximum characters reached'])[1]")
	private WebElement errorMessageDisplayNameEn;

	@FindBy(xpath = "(//div[normalize-space(text())='Maximum characters reached'])[2]")
	private WebElement errorMessageDisplayNameEs;

	@FindBy(id = "displayNameCancelBtn")
	private WebElement btnCancel;

	@FindBy(xpath = "//div[text()='Connected Scheduling Resources']")
	private WebElement labelConnectedResources;

	@FindBy(id = "resourceName")
	private WebElement columnResourceName;

	@FindBy(xpath = "(//div[normalize-space(text())='No data to display'])[1]")
	private WebElement noDataToDisplayMessage;

	@FindBy(xpath = "(//mat-list-item[@role='listitem'])[1]")
	private WebElement connectedLocation;

	@FindBy(xpath = "//div[normalize-space(text())='Resource ID']")
	private WebElement columnResourceId;

	@FindBy(id = "addResourceType")
	private WebElement connectedResourcesAdd;

	@FindBy(xpath = "//div[@class='title']")
	private WebElement addResourceTitle;

	@FindBy(id = "Resource")
	private WebElement inputTextResource;

	@FindBy(id = "save-icon")
	private WebElement addButtonResource;

	@FindBy(xpath = "//label[text()='Resource']")
	private WebElement popupLabelResource;

	@FindBy(id = "cancel-icon")
	private WebElement cancelPopupButton;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement crossPopupIcon;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement noItemsFound;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//mat-icon[text()=' chat ']//..//following::div[@id='toggleButton']//child::input[@type='checkbox'])[1]")
	private WebElement connectedSolChat;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Pay ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolPay;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Virtual Visits ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolVirtualVisits;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' PSS ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolPss;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Patient Portal ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolPortal;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Engage ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolEngage;

	@FindBy(xpath = "//p[@class='connected-solution-header']")
	private WebElement connectedSolutionsTitle;

	@FindBy(xpath = "//mat-icon[normalize-space(text())='arrow_back']")
	private WebElement backIcon;

	@FindBy(id = "manageAccountsIcon")
	private WebElement tabProvidersAndResources;
	@FindBy(xpath = "//div[(normalize-space(text())='Appointment Type Info')]")
	private WebElement appTypeInfoLabel;

	@FindBy(xpath = "//div[normalize-space(text())='Appointment Type ID']")
	private WebElement labelAppTypeID;

	@FindBy(xpath = "//div[normalize-space(text())='Command Center ID']")
	private WebElement labelCommandCenterID;

	@FindBy(xpath = "//div[normalize-space(text())='Visit Duration']")
	private WebElement labelVisitDuration;

	@FindBy(xpath = "//div[(normalize-space(text())='Appointment Categories')]")
	private WebElement appTypeAppCategoriesLabel;

	@FindBy(id = "removeResourceBtn")
	private WebElement removeConnection;

	@FindBy(xpath = "//div//input[@id='inputCheckbox']//following::label[@id='resourceName']")
	private WebElement resourceCheckBox;

	public void enterDisplayNameEn(String input) {
		CCUtils.verifyAndWaitForElement(textboxDisplayNameEn, driver);
		textboxDisplayNameEn.clear();
		CCUtils.verifyAndWaitForElement(textboxDisplayNameEn, driver);
		textboxDisplayNameEn.sendKeys(input);
		log("Entered input for Appointment Type Display Name En : " + input);
	}

	public void enterDisplayNameEs(String input) {
		CCUtils.verifyAndWaitForElement(textboxDisplayNameEs, driver);
		textboxDisplayNameEs.clear();
		CCUtils.verifyAndWaitForElement(textboxDisplayNameEs, driver);
		textboxDisplayNameEs.sendKeys(input);
		log("Entered input for Appointment Type Display Name Es : " + input);
	}

	public void clickOnSaveButton() {
		CCUtils.verifyAndWaitForElement(saveAppTypeInfo, driver);
		saveAppTypeInfo.click();
		log("Clicked on Save Button");
	}

	public void clickOnCancel() {
		CCUtils.verifyAndWaitForElement(btnCancel, driver);
		btnCancel.click();
		log("Clicked on Cancel Button");
	}

	public void verifyLabelConnectedSchedulingResources() {
		CCUtils.verifyAndWaitForElement(labelConnectedResources, driver);
		log("label Connected scheduling resources is present");
	}

	public void verifyMaximumCharactersReachedErrorMessage(String AppTypeDisplayNameEn, String AppTypeDisplayNameEs) {
		log("Enter more than 100 characters in Appointment Type DisplayNameEn textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxDisplayNameEn, driver);
		textboxDisplayNameEn.sendKeys(AppTypeDisplayNameEn);
		log("Entered text in Appointment Type DisplayNameEn field is " + AppTypeDisplayNameEn);
		CCUtils.verifyAndWaitForElement(errorMessageDisplayNameEn, driver);
		CCUtils.assertEquals(errorMessageDisplayNameEn.getText().trim(), "Maximum characters reached");
		log("Error message displayed for Appointment Type DisplayNameEn field is "
				+ errorMessageDisplayNameEn.getText().trim());
		log("Enter more than 100 characters in LocationDisplayNameEs textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxDisplayNameEs, driver);
		textboxDisplayNameEs.sendKeys(AppTypeDisplayNameEs);
		log("Entered text in Appointment Type DisplayNameEs field is " + AppTypeDisplayNameEs);
	}

	public void verifyConnectedSchedulingResourcesSection() throws InterruptedException, AWTException {
		verifyConnectedSchedulingResourcesSectionLabel();
		verifyResourceNameColumn();
		verifyResourceID();
		verifyConnectedSchedulingResourcesAddButtonIsDisplayed();
		verifyMessageWithNoConnectedResource();
		log("Verified Connected Scheduling Resources Section");
	}

	public void verifyConnectedSchedulingResourcesSectionLabel() {
		CCUtils.scrollPageViaElement(driver, labelConnectedResources);
		CCUtils.verifyAndWaitForElement(labelConnectedResources, driver);
		CCUtils.assertEquals(labelConnectedResources.getText().trim(), "Connected Scheduling Resources");
		log("Verified Connected Scheduling Resources Section Label");
	}

	public void verifyResourceNameColumn() {
		CCUtils.verifyAndWaitForElement(columnResourceName, driver);
		CCUtils.assertEquals(columnResourceName.getText().trim(), "Resource Name");
		log("Verified Resource Name Column");
	}

	public void verifyResourceID() {
		CCUtils.verifyAndWaitForElement(columnResourceId, driver);
		CCUtils.assertEquals(columnResourceId.getText().trim(), "Resource ID");
		log("Verified Resource ID Column");
	}

	public void verifyConnectedSchedulingResourcesAddButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(connectedResourcesAdd, driver);
		log("Verified Add Button Is Displayed");
	}

	public void verifyMessageWithNoConnectedResource() {
		CCUtils.verifyAndWaitForElement(noDataToDisplayMessage, driver);
		CCUtils.assertEquals(noDataToDisplayMessage.getText().trim(), "No data to display");
		log("Verified 'No Data To Display Message' With No Connected Resource");
	}

	public void clickToResourceAddButton() {
		CCUtils.verifyAndWaitForElement(connectedResourcesAdd, driver);
		connectedResourcesAdd.click();
		log("clicked to + ADD button");
	}

	public void verifyAddResourcesPopupDetails() {
		verifyAddResourceTitle();
		verifyResourceLabel();
		verifyCancelButton();
		verifyAddButton();
		verifyCrossIcon();
		log("Verified Add Resources Popup Details");
	}

	public void verifyAddResourceTitle() {
		CCUtils.verifyAndWaitForElement(addResourceTitle, driver);
		CCUtils.assertEquals(addResourceTitle.getText().trim(), "Add Resource(s)");
		log("verify add resource title is displayed");
	}

	public void verifyAllAppointmentTypesDetailsTitle() {
		CCUtils.verifyAndWaitForElement(appointmentTypeTitle, driver);
		CCUtils.assertEquals(appointmentTypeTitle.isDisplayed(), true);
		log("AppointmentTypes Details Title is displayed");
	}

	public void clickOnEditButton() {
		CCUtils.verifyAndWaitForElement(labelEditButton, driver);
		labelEditButton.click();
		log("Clicked on Edit button");
	}

	public void verifyAppointmentTypeDisplayInfoSection() {
		verifyAppointmentTypeDisplayInfoLabel();
		verifyEditButtonLabel();
		verifyCopySettingsButtonLabel();
		verifyAppTypeDisplayNameEnLabel();
		verifyAppTypeDisplayNameEsLabel();
		log("Verified AppointmentType Display Info Section");
	}

	public void verifyAppointmentTypeDisplayInfoLabel() {
		CCUtils.verifyAndWaitForElement(appTypeDisplayInfoLabel, driver);
		CCUtils.assertEquals(appTypeDisplayInfoLabel.getText().trim(), "Appointment Type Display Info");
		log("Verified Appointment Type Display Info");
	}

	public void verifyCopySettingsButtonLabel() {
		CCUtils.verifyAndWaitForElement(labelCopySettings, driver);
		CCUtils.assertEquals(labelCopySettings.getText().trim(), "COPY SETTINGS");
		log("Verified Copy Settings Button Label");
	}

	public void verifyEditButtonLabel() {
		CCUtils.verifyAndWaitForElement(labelEditButton, driver);
		CCUtils.assertEquals(labelEditButton.getText().trim(), "edit");
		log("Verified Edit Button Label");
	}

	public void verifyAppTypeDisplayNameEnLabel() {
		CCUtils.verifyAndWaitForElement(labelAppTypeDisplayNameEn, driver);
		CCUtils.assertEquals(labelAppTypeDisplayNameEn.getText().trim(), "Appointment Type Display Name (EN)");
		log("Verified Appointment Type Display Name (EN) Label");
	}

	public void verifyAppTypeDisplayNameEsLabel() {
		CCUtils.verifyAndWaitForElement(labelAppTypeDisplayNameEs, driver);
		CCUtils.assertEquals(labelAppTypeDisplayNameEs.getText().trim(), "Appointment Type Display Name (ES)");
		log("Verified Appointment Type Display Name (ES) Label");
	}

	public void verifyResourceLabel() {
		CCUtils.verifyAndWaitForElement(popupLabelResource, driver);
		CCUtils.assertEquals(popupLabelResource.getText().trim(), "Resource");
		log("label resource is displayed in pop up");
	}

	public void verifyCancelButton() {
		CCUtils.verifyAndWaitForElement(cancelPopupButton, driver);
		CCUtils.assertEquals(cancelPopupButton.getText().trim(), "Cancel");
		log("cancel button is displayed in pop up");
	}

	public void verifyAddButton() {
		CCUtils.verifyAndWaitForElement(addButtonResource, driver);
		CCUtils.assertEquals(addButtonResource.getAttribute("disabled"), "true");
		log("cancel button is displayed in pop up");
	}

	public void verifyCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossPopupIcon, driver);
		log("cross icon is displayed in pop up");
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossPopupIcon, driver);
		log("Clicked on cross icon on pop up");
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(noItemsFound, driver);
		log("no items found message found");
	}

	public void clickToRemoveConnectionButton() {
		CCUtils.verifyAndWaitForElement(removeConnection, driver);
		removeConnection.click();
		log("clicked to remove connection");
	}

	public void enterResourceNameinInputText(String resourceName) {
		CCUtils.verifyAndWaitForElement(inputTextResource, driver);
		inputTextResource.sendKeys(resourceName);
		log("entered resource name in input text");
	}

	public void clickToResourceCheckBox() {
		CCUtils.verifyElementToBeClickable(resourceCheckBox, driver);
		CCUtils.verifyAndWaitForElement(resourceCheckBox, driver);
		resourceCheckBox.click();
		log("desired resource selected");
	}

	public void clickToAddButtonInPopup() {
		CCUtils.verifyAndWaitForElement(addButtonResource, driver);
		CCUtils.verifyAndWaitForElement(addResourceTitle, driver);
		addResourceTitle.click();
		addButtonResource.click();
		log("clicked to Add button of pop up");
	}

	public void verifyAppointmentTypeInfoSection() throws InterruptedException, AWTException {
		verifyAppTypeInfoSectionLabel();
		verifyAppointmentTypeId();
		verifyVisitDuration();
		verifyCommandCenterID();
		verifyAppCategoriesLabel();
		log("Verified AppointmentType Info Section");
	}

	public void verifyAppTypeInfoSectionLabel() {
		CCUtils.scrollPageViaElement(driver, appTypeInfoLabel);
		CCUtils.verifyAndWaitForElement(appTypeInfoLabel, driver);
		CCUtils.assertEquals(appTypeInfoLabel.getText().trim(), "Appointment Type Info");
		log("Verified Appointment Type Info Label");
	}

	public void verifyCommandCenterID() {
		CCUtils.verifyAndWaitForElement(labelCommandCenterID, driver);
		CCUtils.assertEquals(labelCommandCenterID.getText().trim(), "Command Center ID");
		log("Verified Command Center ID");
	}

	public void verifyAppointmentTypeId() {
		CCUtils.verifyAndWaitForElement(labelAppTypeID, driver);
		CCUtils.assertEquals(labelAppTypeID.getText().trim(), "Appointment Type ID");
		log("Verified Appointment Type ID");
	}

	public void verifyVisitDuration() {
		CCUtils.verifyAndWaitForElement(labelVisitDuration, driver);
		CCUtils.assertEquals(labelVisitDuration.getText().trim(), "Visit Duration");
		log("Verified label Visit Duration");
	}

	public void verifyAppCategoriesLabel() {
		CCUtils.verifyAndWaitForElement(appTypeAppCategoriesLabel, driver);
		CCUtils.assertEquals(appTypeAppCategoriesLabel.getText().trim(), "Appointment Categories");
		log("Verified label Appointment Categories");
	}

	public void clickOnChatToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolChat);
		CCUtils.verifyAndWaitForElement(connectedSolChat, driver);
		CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "false");
		log("The toggle is not checked yet as aria-checked attribute value is "
				+ connectedSolChat.getAttribute("aria-checked").toString().trim());
		log("click On Chat Toggle");
		javascriptClick(connectedSolChat);
		CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "true");
		log("The toggle is checked as aria-checked attribute value is "
				+ connectedSolChat.getAttribute("aria-checked").toString().trim());
	}

	public void clickOffChatToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolChat);
		CCUtils.verifyAndWaitForElement(connectedSolChat, driver);
		CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "true");
		log("The toggle is checked as aria-checked attribute value is "
				+ connectedSolChat.getAttribute("aria-checked").toString().trim());
		log("click to chat toggle");
		javascriptClick(connectedSolChat);
		CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "false");
		log("The toggle is not checked yet as aria-checked attribute value is "
				+ connectedSolChat.getAttribute("aria-checked").toString().trim());
	}

	public void clickOnPortalToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPortal);
		CCUtils.verifyAndWaitForElement(connectedSolPortal, driver);
		javascriptClick(connectedSolPortal);
		log("clicked On Portal Toggle");
	}

	public void clickOnEngageToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolEngage);
		CCUtils.verifyAndWaitForElement(connectedSolEngage, driver);
		javascriptClick(connectedSolEngage);
		log("clicked On Engage Toggle");
	}

	public void clickOffEngageToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolEngage, driver);
		javascriptClick(connectedSolEngage);
		log("clicked Off Engage Toggle");
	}

	public void clickOnPayToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPay);
		CCUtils.verifyAndWaitForElement(connectedSolPay, driver);
		javascriptClick(connectedSolPay);
		log("clicked On Pay Toggle");
	}

	public void clickOffPayToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPay, driver);
		javascriptClick(connectedSolPay);
		log("clicked off pay toggle");
	}

	public void clickOnVirtualVisitToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolVirtualVisits);
		javascriptClick(connectedSolVirtualVisits);
		log("clicked on virtual visit toggle");
	}

	public void clickOffVirtualVisitToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolVirtualVisits, driver);
		javascriptClick(connectedSolVirtualVisits);
		log("clicked of virtual visit toggle");
	}

	public void clickOnPSSToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPss);
		CCUtils.verifyAndWaitForElement(connectedSolPss, driver);
		javascriptClick(connectedSolPss);
		log("clicked on Pss Toggle");
	}

	public void clickOffPSSToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPss, driver);
		javascriptClick(connectedSolPss);
		log("clicked Off PSS Toggle");
	}

	public void clickOffPortalToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPortal, driver);
		javascriptClick(connectedSolPortal);
		log("clicked Off Portal Toggle");
	}

	public void verifyConnectedSolutionsTitle() {
		CCUtils.scrollPageViaElement(driver, connectedSolutionsTitle);
		CCUtils.verifyAndWaitForElement(connectedSolutionsTitle, driver);
		CCUtils.assertTrue(connectedSolutionsTitle.isDisplayed(), "verifying connected solutions title");
		log("Verified connected solutions Title");
	}

	public void clickToBackButton() throws AWTException {
		CCUtils.scrollPageToStart();
		CCUtils.verifyElementToBeClickable(backIcon, driver);
		CCUtils.verifyAndWaitForElement(backIcon, driver);
		backIcon.click();
		log("clicked to back button in appointment types details page");
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

}
