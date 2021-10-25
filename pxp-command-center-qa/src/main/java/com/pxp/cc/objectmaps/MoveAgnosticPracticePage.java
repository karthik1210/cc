// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class MoveAgnosticPracticePage extends CCMainPage {

	public MoveAgnosticPracticePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h4[text()='Move Practice']")
	private WebElement labelTitleMovePractice;

	@FindBy(xpath = "//h6[@class='toptitle my-3']")
	private WebElement labelTopTitle;

	@FindBy(id = "moveClientInput")
	private WebElement textboxClient;

	@FindBy(xpath = "//label[text()='Client *']")
	private WebElement labelClient;

	@FindBy(xpath = "//label[text()='Would you like to inherit settings from the Client selected above?']")
	private WebElement labelQuestion;

	@FindBy(xpath = "//input[@id='moveUncheckedRadio-input']//following::label[text()='Do not inherit']")
	private WebElement labelDoNotInherit;

	@FindBy(xpath = "//input[@id='moveUncheckedRadio-input']")
	private WebElement radioButtonDoNotInherit;

	@FindBy(xpath = "//input[@id='moveCheckedRadio-input']//following::label[text()='Inherit']")
	private WebElement labelInherit;

	@FindBy(xpath = "//input[@id='moveCheckedRadio-input']")
	private WebElement radioButtonInherit;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement buttonClear;

	@FindBy(id = "movePracticeBtn-button")
	private WebElement movePracticeButton;

	@FindBy(id = "moveAgnosticBtn")
	private WebElement clickNewClient;

	@FindBy(id = "moveNgeBtn")
	private WebElement clickNewEnterprise;

	@FindBy(xpath = "//mat-tree[1]/mat-tree-node[3]/li[1]//p[1]/span[1]")
	private WebElement clickPracticeNode;

	@FindBy(id = "moveClientInput")
	private WebElement clickSearchClientField;

	@FindBy(xpath = "//span[contains(text(),'TestClientV03 (CC ID: 4004)')]")
	private WebElement selectClientNameFromSearchField;

	@FindBy(id = "moveSubmitBtn")
	private WebElement moveConfirmButton;

	@FindBy(xpath = "//span[text()='Cancel']")
	private WebElement buttonCancel;

	@FindBy(xpath = "//span[text()='Confirm']")
	private WebElement buttonConfirm;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(xpath = "//div[text()='No Clients found']")
	private WebElement errorNoClientsFound;

	@FindBy(css = "ng-select[id='moveOrgSelect'] input[type='text']")
	private WebElement dropdownEnterprise;

	@FindBy(xpath = "//div[@role='option']")
	private WebElement selectEnterpriseFromAutoSuggestion;

	@FindBy(xpath = "//div[@class='flex']/span//following::a")
	private WebElement toasterMessageClientLink;

	@FindBy(xpath = "//div[@class='ng-option ng-option-disabled ng-star-inserted']")
	private WebElement noOptionFound;

	public void verifyMovePracticePopup(String practiceName) {
		verifyMovePracticePageTitle();
		verifyMovePracticeNameIsPresent(practiceName);
		verifyClientTexboxDefaultValueAndClientLabel();
		verifyTextIsNotPresentInClientNameTextbox();
		verifyInheritAndDoNotInheritRadioButtonsAndLabels();
		verifyDoNotInheritRadioButtonByDefaultSelected();
		verifyCancelButtonIsDisplayed();
		verifyConfirmButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
	}

	public void verifyMovePracticePageTitle() {
		CCUtils.verifyAndWaitForElement(labelTitleMovePractice, driver);
		if (labelTitleMovePractice.isDisplayed() == true)
			CCUtils.assertEquals(labelTitleMovePractice.getText().trim(), "Move Practice");
		else
			log("Move practice title is not displayed on move practice popup");
	}

	public void verifyMovePracticeNameIsPresent(String practiceName) {
		log("Verifying move practice name is displayed on move practice popup");
		CCUtils.verifyAndWaitForElement(labelTopTitle, driver);
		CCUtils.assertEquals(labelTopTitle.getText().trim(), "Move " + practiceName + " to a different Client");
	}

	public void verifyClientTexboxDefaultValueAndClientLabel() {
		log("Verifying client label and client textbox default value");
		CCUtils.verifyAndWaitForElement(labelClient, driver);
		CCUtils.assertEquals(labelClient.getText().trim(), "Client *");
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		CCUtils.assertEquals(textboxClient.getAttribute("placeholder").trim(), "Search/Select");
	}

	public void verifyTextIsNotPresentInClientNameTextbox() {
		log("Verifying client name is not present on move practice pop up");
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		CCUtils.assertTrue(textboxClient.getText().length() == 0, "Search text is present in client textbox");
	}

	public void verifyInheritAndDoNotInheritRadioButtonsAndLabels() {
		log("Verifying question text present before inherit and do not inherit radio buttons");
		CCUtils.verifyAndWaitForElement(labelQuestion, driver);
		CCUtils.assertEquals(labelQuestion.getText().trim(),
				"Would you like to inherit settings from the Client selected above?");
		CCUtils.verifyAndWaitForElement(labelDoNotInherit, driver);
		log("Verifying do not inherit label and radio button are displayed on add move practice popup");
		CCUtils.assertEquals(labelDoNotInherit.getText().trim(), "Do not inherit");
		CCUtils.assertTrue(radioButtonDoNotInherit.isDisplayed(),
				"Do not inherit radiobutton is not present on move practice popup");
		log("Verifying inactive label and radio button are displayed on move practice popup");
		CCUtils.assertEquals(labelInherit.getText().trim(), "Inherit");
		CCUtils.assertTrue(radioButtonInherit.isDisplayed(),
				"Inherit radiobutton is not present on move practice popup");
	}

	public void verifyDoNotInheritRadioButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonDoNotInherit, driver);
		log("Verifying do not inherit button is by default selected");
		if (radioButtonDoNotInherit.isDisplayed() == true && radioButtonDoNotInherit.isSelected() == true) {
			CCUtils.assertTrue(true, "default button is do not inherit radiobutton");
			log("Do not inherit button is displayed on add new internal user page and selected");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on move practice popup");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button is enabled");
			CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on move practice popup");
		}
	}

	public void verifyConfirmButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		log("Verifying confirm button is displayed on move practice popup");
		if (buttonConfirm.isDisplayed() == true) {
			log("Verifying confirm button is enabled");
			CCUtils.assertTrue(buttonConfirm.isEnabled(), "Confirm button is disabled");
			log("Verifying confirm button text");
			CCUtils.assertEquals(buttonConfirm.getText().trim(), "Confirm");
		} else {
			log("Confirm button is not displayed on move practice popup");
		}
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		log("Verifying cross icon is displayed on move practice popup");
		if (buttonClear.isDisplayed() == true) {
			log("Verifying cross icon is enabled");
			CCUtils.assertTrue(buttonClear.isEnabled(), "Cross icon is disabled");
		} else {
			log("cross icon is not displayed on move practice popup");
		}
	}

	public void clickMoveButtonOfPractice() {
		CCUtils.verifyAndWaitForElement(movePracticeButton, driver);
		movePracticeButton.click();
	}

	public void clickOnPracticeNode() {
		CCUtils.verifyAndWaitForElement(clickPracticeNode, driver);
		clickPracticeNode.click();
	}

	public void clickOnNewClientOption() {
		CCUtils.verifyAndWaitForElement(clickNewClient, driver);
		clickNewClient.click();
		log("Clicked on New Client button");
	}

	public void clickOnSearchClientfield(String clientName) {
		CCUtils.verifyAndWaitForElement(clickSearchClientField, driver);		
		clickSearchClientField.sendKeys(clientName);
		log("Click on search button");
	}

	public void searchAndSelectClientNameInClientfield() {
		CCUtils.verifyAndWaitForElement(selectClientNameFromSearchField, driver);
		selectClientNameFromSearchField.click();
		log("Enter search text in search textbox");
	}

	public void clickOnMoveConfirmButton() {
		CCUtils.verifyAndWaitForElement(moveConfirmButton, driver);
		moveConfirmButton.click();
	}

	public void enterSearchTextClientTextbox(String searchText) {
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		textboxClient.sendKeys(searchText);
		log("Entered searched text is " + searchText.trim());
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

	public GeneralSettingsPage clickOnConfirmButton() {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		buttonConfirm.click();
		log("Clicked on confirm button");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void movePracticeNodeToClientWithDoNotInherit(String searchText) {
		enterSearchTextClientTextbox(searchText);
		clickFirstInAutoSuggestion();
		clickOnConfirmButton();
	}

	public void verifyNoClientsFoundErrorMessage(String searchText) {
		CCUtils.verifyAndWaitForElement(textboxClient, driver);
		textboxClient.sendKeys(searchText);
		log("Entered searched text is " + searchText.trim());
		CCUtils.verifyAndWaitForElement(errorNoClientsFound, driver);
		CCUtils.assertEquals(errorNoClientsFound.getText(), "No Clients found");
		log("No clients found for the entered text");
	}

	public void movePracticeNodeToClientWithInherit(String searchText) {
		enterSearchTextClientTextbox(searchText);
		clickFirstInAutoSuggestion();
		selectInheritRadiobutton();
		clickOnConfirmButton();
	}

	public void selectInheritRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonInherit, driver);
		javascriptClick(radioButtonInherit);
	}

	public GeneralSettingsPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		javascriptClick(buttonClear);
		log("Clicked on X icon");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on cancel button");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void clickOnNewEnterpriseOption() {
		CCUtils.verifyAndWaitForElement(clickNewEnterprise, driver);
		javascriptClick(clickNewEnterprise);
		log("Clicked on new enterprise button");
	}

	public void searchEnterpriseName(String input) {
		CCUtils.verifyAndWaitForElement(dropdownEnterprise, driver);
		dropdownEnterprise.sendKeys(input);
		log("Entered system is : " + input);
	}

	public void selecEnterpriseFromAutoSugesstion() {
		CCUtils.verifyAndWaitForElement(selectEnterpriseFromAutoSuggestion, driver);
		selectEnterpriseFromAutoSuggestion.click();
	}

	public void movePracticeNodeToEnterpriseWithDoNotInherit(String clientName, String enterpriseName) {
		enterSearchTextClientTextbox(clientName);
		clickFirstInAutoSuggestion();
		searchEnterpriseName(enterpriseName);
		selecEnterpriseFromAutoSugesstion();
		clickOnConfirmButton();
	}

	public void movePracticeNodeToEnterpriseWithInherit(String searchText, String enterpriseName) {
		enterSearchTextClientTextbox(searchText);
		clickFirstInAutoSuggestion();
		searchEnterpriseName(enterpriseName);
		selecEnterpriseFromAutoSugesstion();
		selectInheritRadiobutton();
		clickOnConfirmButton();
	}

	public void clickToasterlink() {
		toasterMessageClientLink.click();
	}

	public void verifyNoEnterpriseFoundErrorMessage(String searchText) {
		CCUtils.verifyAndWaitForElement(dropdownEnterprise, driver);
		dropdownEnterprise.sendKeys(searchText);
		log("Entered searched text is " + searchText.trim());
		CCUtils.verifyAndWaitForElement(noOptionFound, driver);
		CCUtils.assertEquals(noOptionFound.getText(), "No items found");
	}
	
	public void verifyMovePracticePopupForEnterprise(String practiceName) {
		verifyMovePracticePageTitle();
		verifyNGEPracticeName(practiceName);
		verifyClientTexboxDefaultValueAndClientLabel();
		verifyTextIsNotPresentInClientNameTextbox();
		verifyInheritAndDoNotInheritRadioButtonsAndLabels();
		verifyDoNotInheritRadioButtonByDefaultSelected();
		verifyCancelButtonIsDisplayed();
		verifyConfirmButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
	}
	
	public void verifyNGEPracticeName(String practiceName) {
		CCUtils.verifyAndWaitForElement(labelTopTitle, driver);
		CCUtils.assertEquals(labelTopTitle.getText().trim(), "Move " + practiceName + " to a different Enterprise");
	}
}
