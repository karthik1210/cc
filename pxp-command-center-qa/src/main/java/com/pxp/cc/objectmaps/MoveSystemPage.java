// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class MoveSystemPage extends CCMainPage {

	@FindBy(xpath = "//h4[text()='Move System']")
	private WebElement labelTitleMoveSystem;

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

	@FindBy(xpath = "//button[text()=' Confirm ']")
	private WebElement buttonConfirm;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement buttonClear;

	@FindBy(id = "snackIconBtn")
	private WebElement iconError;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	public MoveSystemPage(WebDriver driver) {
		super(driver);
	}

	public void verifyMoveSystemPopup(String systemName) {
		verifyMoveSystemPageTitle();
		verifyMoveSystemNameIsPresent(systemName);
		verifyClientTexboxDefaultValueAndClientLabel();
		verifyTextIsNotPresentInClientNameTextbox();
		verifyInheritAndDoNotInheritRadioButtonsAndLabels();
		verifyDoNotInheritRadioButtonByDefaultSelected();
		verifyCancelButtonIsDisplayed();
		verifyConfirmButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
	}

	public void verifyMoveSystemPageTitle() {
		log("Verifying move system title is displayed or not");
		CCUtils.verifyAndWaitForElement(labelTitleMoveSystem, driver);
		if (labelTitleMoveSystem.isDisplayed() == true)
			CCUtils.assertEquals(labelTitleMoveSystem.getText().trim(), "Move System");
		else
			log("Move system title is nod displayed on move system popup");
	}

	public void verifyMoveSystemNameIsPresent(String systemName) {
		log("Verifying move system name is displayed on move system popup");
		CCUtils.verifyAndWaitForElement(labelTopTitle, driver);
		CCUtils.assertEquals(labelTopTitle.getText().trim(), "Move " + systemName + " to a different Client");
	}

	public void verifyClientTexboxDefaultValueAndClientLabel() {
		log("Verifying client label and client textbox default value");
		CCUtils.verifyAndWaitForElement(labelClient, driver);
		CCUtils.assertEquals(labelClient.getText().trim(), "Client *");
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		CCUtils.assertEquals(textboxClient.getAttribute("placeholder").trim(), "Search/Select");
	}

	public void verifyTextIsNotPresentInClientNameTextbox() {
		log("Verifying client name is not present on move system pop up");
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		CCUtils.assertTrue(textboxClient.getText().length() == 0, "Search text is present in client textbox");
	}

	public void verifyInheritAndDoNotInheritRadioButtonsAndLabels() {
		log("Verifying question text present before inherit and do not inherit radio buttons");
		CCUtils.verifyAndWaitForElement(labelQuestion, driver);
		CCUtils.assertEquals(labelQuestion.getText().trim(),
				"Would you like to inherit settings from the Client selected above?");
		CCUtils.verifyAndWaitForElement(labelDoNotInherit, driver);
		log("Verifying do not inherit label and radio button are displayed on add move system popup");
		CCUtils.assertEquals(labelDoNotInherit.getText().trim(), "Do not inherit");
		CCUtils.assertTrue(radioButtonDoNotInherit.isDisplayed(),
				"Do not inherit radiobutton is not present on move system popup");
		log("Verifying inactive label and radio button are displayed on move system popup");
		CCUtils.assertEquals(labelInherit.getText().trim(), "Inherit");
		CCUtils.assertTrue(radioButtonInherit.isDisplayed(), "Inherit radiobutton is not present on move system popup");
	}

	public void verifyDoNotInheritRadioButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonDoNotInherit, driver);
		log("Verifying do not inherit button is by default selected");
		if (radioButtonDoNotInherit.isDisplayed() == true && radioButtonDoNotInherit.isSelected() == true) {
			log("Do not inherit button is displayed on add new internal user page and selected");
		} else {
			log("Do not inherit button is not displayed on add new internal user page and selected");
		}
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		log("Verifying cross icon is displayed on move system popup");
		if (buttonClear.isDisplayed() == true) {
			log("Verifying cross icon is enabled");
			CCUtils.assertTrue(buttonClear.isEnabled(), "Cross icon is disabled");
		} else {
			log("cross icon is not displayed on move system popup");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on move system popup");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button is enabled");
			CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on move system popup");
		}
	}

	public void verifyConfirmButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		log("Verifying confirm button is displayed on move system popup");
		if (buttonConfirm.isDisplayed() == true) {
			log("Verifying confirm button is enabled");
			CCUtils.assertTrue(buttonConfirm.isEnabled(), "Confirm button is disabled");
			log("Verifying confirm button text");
			CCUtils.assertEquals(buttonConfirm.getText().trim(), "Confirm");
		} else {
			log("Confirm button is not displayed on move system popup");
		}
	}

	public GeneralSettingsPage clickOnConfirmButton() {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		log("Clicked on confirm button");
		buttonConfirm.click();
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		log("Clicked on X icon");
		javascriptClick(buttonClear);
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Clicked on cancel button");
		buttonCancel.click();
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
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

	public void verifyNoClientsFoundErrorMessage(String searchText) {
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		textboxClient.sendKeys(searchText);
		log("Entered searched text is " + searchText.trim());
		CCUtils.verifyAndWaitForElement(errorNoClientsFound, driver);
		CCUtils.assertEquals(errorNoClientsFound.getText(), "No Clients found");
		log("No clients found for the entered text");
	}

	public void enterSearchTextClientTextbox(String searchText) {
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		textboxClient.sendKeys(searchText);
		log("Entered searched text is " + searchText.trim());
	}

	public void verifyAutoSuggestionIsNotPresent() {
		if (selectFirstInAutoSuggestion.isDisplayed() == false) {
			log("Auto suggestion is not displayed for search text");
		} else {
			log("Auto suggestion is displayed for search text");
		}
	}

	public void selectInheritRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonInherit, driver);
		javascriptClick(radioButtonInherit);
		log("Clicked on inherit radiobutton in popup");
	}

	public void moveSystemNodeToClientWithDoNotInherit(String searchText) {
		enterSearchTextClientTextbox(searchText);
		clickFirstInAutoSuggestion();
		clickOnConfirmButton();
	}

	public void moveSystemNodeToClientWithInherit(String searchText) {
		enterSearchTextClientTextbox(searchText);
		clickFirstInAutoSuggestion();
		selectInheritRadiobutton();
		clickOnConfirmButton();
	}
}
