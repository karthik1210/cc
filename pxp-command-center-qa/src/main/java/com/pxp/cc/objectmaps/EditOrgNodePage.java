// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.pxp.cc.util.CCUtils;

public class EditOrgNodePage extends CCMainPage {

	public EditOrgNodePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "treeExpandBtn")
	private WebElement clientCarretIcon;

	@FindBy(xpath = "//mat-icon[contains(text(),'keyboard_arrow_up')]")
	private WebElement systemCarretIcon;

	@FindBy(xpath = "//mat-icon[contains(text(),'keyboard_arrow_up')]")
	private WebElement enterpriseCarretIcon;

	@FindBy(xpath = "//mat-tree-node[@aria-level='2']")
	private WebElement clickSystemNode;

	@FindBy(xpath = "//mat-tree-node[@aria-level='3']")
	private WebElement clickEnterpriseNode;

	@FindBy(xpath = "//mat-tree-node[@aria-level='4']")
	private WebElement clickPracticeNode;

	@FindBy(id = "orgEditBtn")
	private WebElement editOrgButton;

	@FindBy(id = "orgSapInput")
	private WebElement textBoxOrgEditSapId;

	@FindBy(id = "orgSolutionInput")
	private WebElement textBoxSolutionDisplayName;

	@FindBy(id = "timezoneSelect")
	private WebElement dropDownTimezone;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textBoxTimezone;

	@FindBy(id = "orgInactiveRadio")
	private WebElement orgInactiveRadioButton;

	@FindBy(id = "orgActiveRadio")
	private WebElement orgActiveRadioButton;

	@FindBy(id = "orgSaveBtn")
	private WebElement orgSaveBtn;

	@FindBy(id = "orgCancelBtn")
	private WebElement orgCancelBtn;

	@FindBy(id = "a2ff2005c281-0")
	private WebElement orgTimezoneSelectOption;

	@FindBy(id = "OrgSapError")
	private WebElement errorMessageSapIdMaxLength;

	@FindBy(xpath = "//*[@id=\"cdk-overlay-0\"]/snack-bar-container/app-snack-icon/div/span")
	private WebElement errorMessageInactiveOrgNode;

	@FindBy(id = "orgSolutionError")
	private WebElement errorMessageSolutionDisplayNameMaxLength;

	public void clickOnClientCarretIcon() {
		CCUtils.verifyAndWaitForElement(clientCarretIcon, driver);
		clientCarretIcon.click();
	}

	public void clickOnSystemCarretIcon() {
		wait.until(ExpectedConditions.visibilityOf(systemCarretIcon));
		CCUtils.verifyAndWaitForElement(systemCarretIcon, driver);
		systemCarretIcon.click();
	}

	public void clickOnEnterpriseCarretIcon() {
		CCUtils.verifyAndWaitForElement(enterpriseCarretIcon, driver);
		enterpriseCarretIcon.click();
	}

	public void clickOnEnterpriseNode() {
		CCUtils.verifyAndWaitForElement(clickEnterpriseNode, driver);
		clickEnterpriseNode.click();
	}

	public void clickOnPracticeNode() {
		CCUtils.verifyAndWaitForElement(clickPracticeNode, driver);
		clickPracticeNode.click();
	}

	public void clickEditButtonOfOrg() {
		CCUtils.verifyAndWaitForElement(editOrgButton, driver);
		log("Clicked on edit button");
		editOrgButton.click();
	}

	public void clickActiveRadioButtonOfOrgNode() {
		CCUtils.verifyAndWaitForElement(orgActiveRadioButton, driver);
		log("Clicked on active radio button");
		orgActiveRadioButton.click();
	}

	public void clickInactiveRadioButtonOfOrgNode() {
		CCUtils.verifyAndWaitForElement(orgInactiveRadioButton, driver);
		log("Clicked on Inactive radio button");
		orgInactiveRadioButton.click();
	}

	public void clickCancelButtonOfOrg() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(orgCancelBtn, driver);
		log("Clicked on cancel button");
		scrollAndWait(0, 500, 200);
		orgCancelBtn.click();
	}

	public void clickSaveButtonOfOrg() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(orgSaveBtn, driver);
		log("Clicked on save button");
		scrollAndWait(0, 500, 200);
		orgSaveBtn.click();
	}

	public void editOrgOnGeneralSettingPage(String sapId, String solutionsDisplayName, String timezone)
			throws InterruptedException {
		textBoxOrgEditSapId.clear();
		textBoxOrgEditSapId.sendKeys(sapId);
		textBoxSolutionDisplayName.clear();
		textBoxSolutionDisplayName.sendKeys(solutionsDisplayName);
		scrollAndWait(0, 500, 200);
		dropDownTimezone.click();
		textBoxTimezone.sendKeys(timezone);
		textBoxTimezone.sendKeys(Keys.ENTER);
	}

	public void overrideEditOrgOnGeneralSettingPage(String sapId, String solutionsDisplayName, String timezone)
			throws InterruptedException {
		textBoxOrgEditSapId.sendKeys(sapId);
		textBoxSolutionDisplayName.sendKeys(solutionsDisplayName);
		dropDownTimezone.click();
		textBoxTimezone.sendKeys(timezone);
		textBoxTimezone.sendKeys(Keys.ENTER);
	}

	public void nullEditOnGeneralSettingPage(String orgTimezoneSelectOption) throws InterruptedException {
		textBoxOrgEditSapId.clear();
		textBoxOrgEditSapId.sendKeys(" ");
		textBoxSolutionDisplayName.clear();
		textBoxSolutionDisplayName.sendKeys(" ");
		dropDownTimezone.click();
		textBoxTimezone.sendKeys(orgTimezoneSelectOption);
		textBoxTimezone.sendKeys(Keys.ENTER);
	}

	public void maximumLengthEditOrgOnGeneralSettingPage(String timezone) throws InterruptedException {
		textBoxOrgEditSapId.clear();
		textBoxOrgEditSapId.sendKeys("VC848684425643610242937645@$$q$&(**&*^bcgdBN77BJGS2");
		textBoxSolutionDisplayName.clear();
		textBoxSolutionDisplayName.sendKeys(
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS2");
		dropDownTimezone.click();
		textBoxTimezone.sendKeys(timezone);
		textBoxTimezone.sendKeys(Keys.ENTER);
	}

	public String sapIdMaxLengthValidationMessageOnEditOrg() {
		String errorSapIdLength = errorMessageSapIdMaxLength.getText();
		return errorSapIdLength;
	}

	public String solutionDisplayNameMaxLengthValidationMessageOnEditOrg() {
		String errorSolutionDisplayNameLength = errorMessageSolutionDisplayNameMaxLength.getText();
		return errorSolutionDisplayNameLength;
	}

	public String errorMessageOnEditOrgForInactiveClient() {
		String InactiveClient = errorMessageInactiveOrgNode.getText();
		return InactiveClient;
	}

}
