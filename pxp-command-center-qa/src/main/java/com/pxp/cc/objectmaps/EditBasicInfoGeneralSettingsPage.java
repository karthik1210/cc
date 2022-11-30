// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class EditBasicInfoGeneralSettingsPage extends CCMainPage {

	public EditBasicInfoGeneralSettingsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[normalize-space(text())='Timezone']")
	private WebElement labelTimeZone;

	@FindBy(xpath = "//div[normalize-space(text())='Basic Info']")
	private WebElement labelBasicInfoTitle;

	@FindBy(xpath = "//span[normalize-space(text())='Changes updated successfully']")
	private WebElement toasterMessage;

	@FindBy(xpath = "(//mat-icon[contains(text(),'edit')])[2]")
	private WebElement basicInfoEditButton;

	@FindBy(xpath = "(//p[@id='treeNodeName'])[3]")
	private WebElement enterpriseOrg;

	@FindBy(id = "manageClientLabel")
	private WebElement manageClientButton;

	@FindBy(xpath = "(//p[@id='treeNodeName'])[2]")
	private WebElement systemOrg;

	@FindBy(xpath = "(//p[@id='treeNodeName'])[1]")
	private WebElement clientOrg;

	@FindBy(id = "saveDetailsBtn")
	private WebElement saveButton;

	@FindBy(xpath = "//label[normalize-space(text())='PM System']")
	private WebElement labelPm;

	@FindBy(xpath = "//label[normalize-space(text())='EHR System']")
	private WebElement labelEhr;

	private WebElement prepareDynamicXpathForTimezone(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	@FindBy(xpath = "(//mat-icon[normalize-space(text())='keyboard_arrow_down'])[1]")
	private WebElement basicInfoCollapseArrow;

	@FindBy(id = "cancelBtn")
	private WebElement cancelBtn;

	private String xpathRequiredNewTimezone = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//div[normalize-space(text())='API Site ID']")
	private WebElement labelAPISiteID;

	@FindBy(xpath = "//div[normalize-space(text())='Integration ID']")
	private WebElement labelIntegrationID;

	@FindBy(xpath = "//input[@formcontrolname='solutionDisplayName']")
	private WebElement textboxSolutionsName;
	
	@FindBy(xpath = "//div[normalize-space(text())='Solutions Display Name']")
	private WebElement labelSolutionsDisplayName;

	@FindBy(xpath = "(//input[@type='text'])[1]")
	private WebElement textboxSapId;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement dropdownClientType;

	private String xpathRequiredClientType = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForClientType(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	@FindBy(xpath = "//div[normalize-space(text())='Command Center ID']")
	private WebElement labelCommandCenterID;

	@FindBy(xpath = "//div[normalize-space(text())='Site Generator ID']")
	private WebElement labelSiteGeneratorID;
	
	@FindBy(xpath = "//div[contains(text(),'Maximum characters reached')] ")
	private WebElement errorMessage;

	List<String> actualSolutionValues = new ArrayList<String>();

	public final List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "PSS", "Pay", "Portal",
			"Virtual Visits");

	public void verifyEditButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(basicInfoEditButton, driver);
		log("Verifying search button is displayed on search options popup");
		if (basicInfoEditButton.isDisplayed() == true) {
			log("Verifying Edit button is enabled");
			CCUtils.assertTrue(basicInfoEditButton.isEnabled(), "search button is enabled");
			log("Verifying Edit button text");
			CCUtils.assertEquals(basicInfoEditButton.getText().trim(), "edit");
			log("Edit button is displayed");
		} else {
			log("Edit button is not displayed");
		}
	}

	public void clickEditButton() {
		CCUtils.verifyAndWaitForElement(basicInfoEditButton, driver);
		basicInfoEditButton.click();
		log("Clicked on edit button");
	}

	public void clickEnterpriseOrg() {
		CCUtils.scrollPageViaElement(driver, manageClientButton);
		CCUtils.verifyAndWaitForElement(enterpriseOrg, driver);
		enterpriseOrg.click();
		log("Clicked on enterprise org");
	}

	public void clickSystemOrg() {
		CCUtils.verifyAndWaitForElement(systemOrg, driver);
		systemOrg.click();
		log("Clicked on system org");
	}

	public void clickClientOrg() {
		CCUtils.scrollPageViaElement(driver, manageClientButton);
		CCUtils.verifyAndWaitForElement(clientOrg, driver);
		clientOrg.click();
		log("Clicked on client org");
	}

	public void clickSaveButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
		log("Clicked on save button");
	}

	public void verifySuccessToasterMessage() {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		CCUtils.assertEquals(toasterMessage.getText().trim(), "Changes updated successfully");
		log("Verified 'Changes updated successfully' success toaster message");
	}

	public void verifyEHRSystemLabel() {
		CCUtils.verifyAndWaitForElement(labelEhr, driver);
		CCUtils.assertEquals(labelEhr.getText().trim(), "EHR System");
		log("Verified EHR System Label");
	}

	public void verifyPMSystemLabel() {
		CCUtils.verifyAndWaitForElement(labelPm, driver);
		CCUtils.assertEquals(labelPm.getText().trim(), "PM System");
		log("Verified PM System Label");
	}

	public void verifyTimezoneLabel() {
		CCUtils.verifyAndWaitForElement(labelTimeZone, driver);
		CCUtils.assertEquals(labelTimeZone.getText().trim(), "Timezone");
		log("Verified TimeZone Label");
	}

	public void verifySolutionsDisplayName() {
		CCUtils.verifyAndWaitForElement(labelSolutionsDisplayName, driver);
		CCUtils.assertEquals(labelSolutionsDisplayName.getText().trim(), "Solutions Display Name");
		log("Verified Solutions Display Name Label");
	}

	public void verifyBasicInfoTitle() {
		CCUtils.verifyAndWaitForElement(labelBasicInfoTitle, driver);
		CCUtils.assertEquals(labelBasicInfoTitle.getText().trim(), "Basic Info");
		log("Verified Basic Info title At Practice Level");
	}

	public void verifyEditBasicInfoAtPracticeLevel() throws InterruptedException, AWTException {
		log("Verifying Basic Info Section at Practice level");
		verifyBasicInfoTitle();
		verifyEHRSystemLabel();
		verifyPMSystemLabel();
		verifyTimezoneLabel();
		verifySolutionsDisplayName();
		verifyCommandCenterID();
		verifySiteGeneratorID();
		verifyIntegrationID();
		verifyAPISiteID();
	}

	public void verifyEditBasicInfoAtEnterpriseLevel() throws InterruptedException, AWTException {
		log("Verifying Basic Info Section at Enterprise level");
		verifyBasicInfoTitle();
		verifyEHRSystemLabel();
		verifyPMSystemLabel();
		verifyTimezoneLabel();
		verifyCommandCenterID();
		verifySiteGeneratorID();
		verifyAPISiteID();
	}

	public void verifyEditBasicInfoAtSystemLevel() throws InterruptedException, AWTException {
		log("Verifying Basic Info Section at System level");
		verifyBasicInfoTitle();
		verifyEHRSystemLabel();
		verifyPMSystemLabel();
		verifyCommandCenterID();
		verifyAPISiteID();
	}

	public void verifyEditBasicInfoAtClientLevel() throws InterruptedException, AWTException {
		log("Verifying Basic Info Section at Client level");
		verifyBasicInfoTitle();
		verifyEHRSystemLabel();
		verifyPMSystemLabel();
		verifyTimezoneLabel();
		verifyCommandCenterID();
	}

	public void editSapIdClientType(String newSAPID, String clientType) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.clear();
		textboxSapId.sendKeys(newSAPID);
		log("Entered text in SAP ID field : " + newSAPID);
		CCUtils.verifyForElementToBeClickable(dropdownClientType, driver);
		dropdownClientType.click();
		log("clicked to dropdown of client type");
		selectClientType(clientType);
		log("Edited SAP ID and ClientType");
	}

	public void selectClientType(String clientTypeName) {
		WebElement clientType = prepareDynamicXpathForClientType(xpathRequiredClientType, clientTypeName);
		CCUtils.verifyAndWaitForElement(clientType, driver);
		clientType.click();
		log("Clicked on : " + clientTypeName);
	}

	public void verifyCommandCenterID() {
		CCUtils.verifyAndWaitForElement(labelCommandCenterID, driver);
		CCUtils.assertEquals(labelCommandCenterID.getText().trim(), "Command Center ID info");
		log("Verified Command Center ID");
	}

	public void verifySiteGeneratorID() {
		CCUtils.verifyAndWaitForElement(labelSiteGeneratorID, driver);
		CCUtils.assertEquals(labelSiteGeneratorID.getText().trim(), "Site Generator ID info");
		log("Verified Site Generator ID");
	}

	public void verifyIntegrationID() {
		CCUtils.verifyAndWaitForElement(labelIntegrationID, driver);
		CCUtils.assertEquals(labelIntegrationID.getText().trim(), "Integration ID info");
		log("Verified Integration ID");
	}

	public void verifyAPISiteID() {
		CCUtils.assertEquals(labelAPISiteID.getText().trim(), "API Site ID info");
		log("Verified API Site ID");
	}

	public void enterSolutionsDisplayName(String input) {
		CCUtils.scrollPageViaElement(driver, labelBasicInfoTitle);
		CCUtils.verifyAndWaitForElement(textboxSolutionsName, driver);
		textboxSolutionsName.clear();
		CCUtils.verifyAndWaitForElement(textboxSolutionsName, driver);
		textboxSolutionsName.sendKeys(input);
		log("Edited input for Solutions Display Name : " + input);
	}

	public void editTimezone(String newTimezone) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		log("click to dropdown of Timezone");
		dropdownTimezone.click();
		selectTimeZone(newTimezone);
		log("Edited input for Timezone : " + newTimezone);
	}

	public void selectTimeZone(String newTimezone) {
		WebElement newClientTimezone = prepareDynamicXpathForTimezone(xpathRequiredNewTimezone, newTimezone);
		newClientTimezone.click();
		log("Clicked on : " + newTimezone);
	}

	public void clickOnBasicInfoCollapseButton() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(basicInfoCollapseArrow, driver);
		log("clicking on basic info collapse arrow");
		javascriptClick(basicInfoCollapseArrow);
		log("Clicked on Basic Info collapse arrow");
	}

	public void clickOnCancelButton() throws InterruptedException, AWTException {
		CCUtils.scrollPageViaElement(driver, basicInfoCollapseArrow);
		CCUtils.verifyAndWaitForElement(cancelBtn, driver);
		log("clicking on cancel button");
		javascriptClick(cancelBtn);
		log("Clicked on cancel button");
	}
}
