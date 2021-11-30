// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.pxp.cc.util.CCUtils;

public class MoveEnterprisePage extends CCMainPage {

	public MoveEnterprisePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@id='orgTitle']")
	private WebElement nodeName;

	@FindBy(xpath = "//div[@class='movebutton ng-star-inserted']")
	private WebElement buttonMoveEnt;

	@FindBy(id = "moveClientInput")
	private WebElement inputClientName;

	@FindBy(css = "ng-select[id='moveOrgSelect'] input[type='text']")
	private WebElement dropdownSystem;

	@FindBy(className = "ng-option ng-option-marked ng-star-inserted")
	private List<WebElement> listOfSystem;

	@FindBy(id = "moveSubmitBtn")
	private WebElement buttonMoveEntConfirm;

	@FindBy(xpath = "//span[@class='mat-option-text']//span")
	private WebElement selectClientFromAutoSuggestion;

	@FindBy(xpath = "//div[@role='option']")
	private WebElement selectSystemFromAutoSuggestion;

	@FindBy(xpath = "//h4[text()='Move Enterprise']")
	private WebElement labelTitleMoveEnterprise;

	@FindBy(xpath = "//h6[@class='toptitle my-3']")
	private WebElement labelTopTitle;

	@FindBy(xpath = "//label[text()='Client *']")
	private WebElement labelClient;

	@FindBy(id = "moveClientInput")
	private WebElement textboxClient;

	@FindBy(xpath = "//label[text()='Would you like to inherit settings from the Client selected above?']")
	private WebElement labelQuestion;

	@FindBy(xpath = "//input[@id='moveUncheckedRadio-input']")
	private WebElement radioButtonDoNotInherit;

	@FindBy(xpath = "//input[@id='moveCheckedRadio-input']")
	private WebElement radioButtonInherit;

	@FindBy(xpath = "//div[text()='No Clients found']")
	private WebElement errorNoClientsFound;

	@FindBy(xpath = "//input[@id='moveUncheckedRadio-input']//following::label[text()='Do not inherit']")
	private WebElement labelDoNotInherit;

	@FindBy(xpath = "//input[@id='moveCheckedRadio-input']//following::label[text()='Inherit']")
	private WebElement labelInherit;

	@FindBy(xpath = "//span[text()='Cancel']")
	private WebElement buttonCancel;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement buttonClear;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	public String getNodeName() {
		CCUtils.verifyAndWaitForElement(nodeName, driver);
		return nodeName.getText();
	}

	public void verifyMoveEnterpriseNameIsPresent(String systemName) {
		log("Verifying enterprise name is displayed in popup");
		CCUtils.verifyAndWaitForElement(labelTopTitle, driver);
		CCUtils.assertEquals(labelTopTitle.getText().trim(), "Move " + systemName + " to a different System");
	}

	public MoveSystemPage moveSystemPage;

	public void verifyEnterprisePopup(String nodeName) {
		verifyMoveEnterprisePageTitle();
		verifyMoveEnterpriseNameIsPresent(nodeName);
		moveSystemPage = new MoveSystemPage(driver);
		moveSystemPage.verifyClientTexboxDefaultValueAndClientLabel();
		moveSystemPage.verifyTextIsNotPresentInClientNameTextbox();
		moveSystemPage.verifyInheritAndDoNotInheritRadioButtonsAndLabels();
		moveSystemPage.verifyDoNotInheritRadioButtonByDefaultSelected();
		moveSystemPage.verifyCancelButtonIsDisplayed();
		moveSystemPage.verifyConfirmButtonIsDisplayed();
		moveSystemPage.verifyCrossIconIsDisplayed();
	}

	public void verifyMoveEnterprisePageTitle() {
		log("Verifying move enterprise title is displayed or not");
		CCUtils.verifyAndWaitForElement(labelTitleMoveEnterprise, driver);
		if (labelTitleMoveEnterprise.isDisplayed() == true)
			CCUtils.assertEquals(labelTitleMoveEnterprise.getText().trim(), "Move Enterprise");
		else
			log(" Move enterprise title is not in popup");
	}

	public void clickMoveEnterprise() {
		CCUtils.verifyAndWaitForElement(buttonMoveEnt, driver);
		log("Move Enterprise button clicked");
		buttonMoveEnt.click();
	}

	public void enterClientName(String input) {
		CCUtils.verifyAndWaitForElement(inputClientName, driver);
		inputClientName.sendKeys(input);
		log("Entered Client is : " + input);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void enterSystemName(String input) {
		CCUtils.verifyAndWaitForElement(dropdownSystem, driver);
		dropdownSystem.sendKeys(input);
		log("Entered system is : " + input);
	}

	public void selectClientFromAutoSugesstion() {
		CCUtils.verifyAndWaitForElement(selectClientFromAutoSuggestion, driver);
		log("Clicked on " + selectClientFromAutoSuggestion.getText() + " from autosuggestion");
		selectClientFromAutoSuggestion.click();
	}

	public void selectSystemFromAutoSugesstion() {
		CCUtils.verifyAndWaitForElement(selectSystemFromAutoSuggestion, driver);
		log("Clicked on " + selectSystemFromAutoSuggestion.getText() + " from autosuggestion");
		selectSystemFromAutoSuggestion.click();
	}

	public void clickInheritRadioButton() {
		CCUtils.verifyAndWaitForElement(radioButtonInherit, driver);
		javascriptClick(radioButtonInherit);
		log("Inherit radiobutton is clicked");
	}

	public void clickConfirmInMoveEnterprise() {
		CCUtils.verifyAndWaitForElement(buttonMoveEntConfirm, driver);
		buttonMoveEntConfirm.click();
		log("Confirm button in Move Enterprise popup is clicked");
	}

}
