// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.util.CCUtils;

public class EditOrgDetailsGeneralSettingsPage extends CCMainPage {

	public EditOrgDetailsGeneralSettingsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//p[@id='treeNodeName'])[1]")
	private WebElement clientOrg;

	@FindBy(id = "displayNameEditBtn")
	private WebElement editButton;

	@FindBy(id = "detailSaveBtn")
	private WebElement saveButton;

	@FindBy(id = "detailCancelBtn")
	private WebElement cancelButton;

	@FindBy(id = "detailNameInput")
	private WebElement textBox;

	@FindBy(xpath = "//mat-select[@role ='combobox']")
	private WebElement statusArrow;

	@FindBy(xpath = "//span[normalize-space(text())='Changes updated successfully']")
	private WebElement toasterMessage;

	@FindBy(xpath = "//div[normalize-space(text())='Maximum characters reached']")
	private WebElement errorMessage;

	@FindBy(xpath = "//span[normalize-space(text())='Inactive']")
	private WebElement inactiveStatus;
 
	@FindBy(xpath = "//p[@id='treeNodeName'])[3]")
	private WebElement enterpriseArrow;
	
	@FindBy(xpath = "//p[@id='treeNodeName'])[2]")
	private WebElement systemArrow;

	public void clickClientOrg() {
		CCUtils.verifyAndWaitForElement(clientOrg, driver);
		clientOrg.click();
		log("Clicked on client org");
	}

	public void verifyEditOrgDetailsAtClientLevel() throws InterruptedException, AWTException {
		log("Verifying Edit Org Details Section at Client level");
		verifyEditButtonIsDisplayed();
		clickEditButton();
		verifySaveButtonIsDisplayed();
		verifyCancelButtonIsDisplayed();
	}

	public void clickEditButton() {
		CCUtils.verifyAndWaitForElement(editButton, driver);
		editButton.click();
		log("Clicked on edit button");
	}

	public void clickSaveButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
		log("Clicked on Save button");
	}

	public void clickCancelButton() {
		CCUtils.verifyAndWaitForElement(cancelButton, driver);
		cancelButton.click();
		log("Clicked on cancel button");
	}

	public void verifyEditButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(editButton, driver);
		log("Verifying edit button is displayed");
		if (editButton.isDisplayed() == true) {
			log("Verifying Edit button is enabled");
			CCUtils.assertTrue(editButton.isEnabled(), "edit button is enabled");
			log("Verifying Edit button text");
			CCUtils.assertEquals(editButton.getText().trim(), "edit EDIT");
			log("Edit button is displayed");
		} else {
			log("Edit button is not displayed");
		}
	}

	public void verifySaveButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		log("Verifying Save button is displayed");
		if (saveButton.isDisplayed() == true) {
			log("Verifying Save button is enabled");
			CCUtils.assertTrue(saveButton.isEnabled(), "Save button is enabled");
			log("Verifying Save button text");
			CCUtils.assertEquals(saveButton.getText().trim(), "SAVE");
			log("Save button is displayed");
		} else {
			log("Save button is not displayed");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(cancelButton, driver);
		log("Verifying Cancel button is displayed");
		if (cancelButton.isDisplayed() == true) {
			log("Verifying Cancel button is enabled");
			CCUtils.assertTrue(cancelButton.isEnabled(), "Cancel button is enabled");
			log("Verifying Cancel button text");
			CCUtils.assertEquals(cancelButton.getText().trim(), "CANCEL");
			log("Cancel button is displayed");
		} else {
			log("Cancel button is not displayed");
		}
	}

	public void EditAndSaveNameAndStatus(String orgName) throws InterruptedException, AWTException {
		log("Edit and save system name and status at system level");
		editOrgName(orgName);
		changeStatus();
		clickSaveButton();

	}

	public void editOrgName(String orgName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textBox, driver);
		textBox.clear();
		textBox.sendKeys(orgName);
		log("Entered text : " + orgName);
	}

	public void changeStatus() {
		CCUtils.verifyAndWaitForElement(statusArrow, driver);
		statusArrow.click();
		inactiveStatus.click();
		log("Clicked on inactive status");
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void verifyErrorMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (errorMessage.isDisplayed() == true)
			CCUtils.assertEquals(errorMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("error message is not displayed");
	}

	public void clickOnSystemArrow() {
		CCUtils.verifyAndWaitForElement(systemArrow, driver);
		log("clicking on system level");
		new Actions(driver).moveToElement(systemArrow).click().build().perform();
		log("Successfully system has been viewed");
	}

	public void clickOnEnterpriseArrow() {
		CCUtils.verifyAndWaitForElement(enterpriseArrow, driver);
		log("clicking on Enterprise level");
		new Actions(driver).moveToElement(enterpriseArrow).click().build().perform();
		log("Successfully Enterprise has been viewed");
	}
}
