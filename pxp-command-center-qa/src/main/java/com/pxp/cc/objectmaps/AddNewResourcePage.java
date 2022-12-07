// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class AddNewResourcePage extends CCMainPage {

	public AddNewResourcePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "(//div[@role='tab'])[2]")
	private WebElement tabResources;

	@FindBy(id = "openDialog")
	private WebElement addButton;

	@FindBy(xpath = "//input[@id='NameInput']")
	private WebElement textboxName;

	@FindBy(xpath = "//div[@role='option']/span")
	private List<WebElement> listOfResourceType;

	@FindBy(id = "selectStatus")
	private WebElement textboxConnectedProvider;

	@FindBy(xpath="//button[@type='submit']")
	private WebElement saveButton;

	@FindBy(id = "ExternalIdInput")
	private WebElement textboxExternalId;

	@FindBy(xpath = "//div[text()='Select Resource Type']//following::div[@role='combobox']/input")
	private WebElement dropdownResourceType;

	@FindBy(xpath = "//mat-option[@id='searchValues'][1]")
    private WebElement selectFirstOption;

	@FindBy(id = "NameInput")
	private WebElement textboxtName;
	
	@FindBy(id = "addNewTitle")
	private WebElement labelAddNewResourcePageTitle;
	
	@FindBy(xpath = "//span[text()='Name * ']")
	private WebElement labelName;
	
	@FindBy(xpath = "//span[text()='External ID * ']")
	private WebElement labeltextboxExternalId;
	
	@FindBy(xpath = "//span[text()='Resource Type * ']")
	private WebElement labeltextboxResourceType;
	
	@FindBy(xpath = "//div[@id='maxfirstNameError']")
	private WebElement errorMaximumCharacterName;
	
	@FindBy(xpath = "//div[@id='maxExternalIdError']")
	private WebElement errorMaximumCharacterExternalId;
	
	@FindBy(xpath = "//div[text()='External Id already in use']")
	private WebElement errorUniqueExternalId;
	
	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;
	
	@FindBy(id = "addNewCancel")
	private WebElement buttonCancel;
	
	@FindBy(id = "cancel-text")
	private WebElement crossIcon;
	
	public void clickOnResourcesTab() {
		CCUtils.verifyAndWaitForElement(tabResources, driver);
		tabResources.click();
		log("Clicked on ResourcesTab");
	}

	public void verifyAddButtonIsEnabled() {
		logStep("Verifying add button is enabled or not");
		CCUtils.verifyAndWaitForElement(addButton, driver);
		CCUtils.assertTrue(addButton.isEnabled(), "Add button is disabled on provider page");
	}

	public void clickOnAddResourceButton() {
		log("Click on add new Resource Button");
		CCUtils.verifyAndWaitForElement(addButton, driver);
		addButton.click();	
	}

	public void enterResourceName(String input) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(input);
		log("Entered input for Name : " + input);
	}

	public void enterConnectedProvider(String input) {
		CCUtils.verifyAndWaitForElement(textboxConnectedProvider, driver);
		textboxConnectedProvider.sendKeys(input);
		log("Entered input for Connected Provider : " + input);
	}

	public void enterExternalId(String input) {
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.clear();
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.sendKeys(input);
		log("Entered input for ExternalId : " + input);
	}

	public void selectResourceType(String value) {
		clickOnResourceType();
		if (listOfResourceType.size() > 0) {
			if (value.equals("Person")) {
				javascriptClick(listOfResourceType.get(0));
				log("Clicked on Person Resource Type option");
			} else if (value.equals("Place")) {
				javascriptClick(listOfResourceType.get(1));
				log("Clicked on Place Resource Type option");
			} else if (value.equals("Thing")) {
				javascriptClick(listOfResourceType.get(2));
				log("Clicked on Thing Resource Type option");
			} else {
				log("Resource Type option not displayed on add a resource screen");
			}
		}
	};

	public void clickOnResourceType() {
		CCUtils.verifyAndWaitForElement(dropdownResourceType, driver);
		dropdownResourceType.click();
	}

	public void verifySaveAndAddButtonIsEnabled() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		logStep("Verifying Save and add button is enabled");
		CCUtils.assertEquals(saveButton.isEnabled(), true);
	}

	public void clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
		logStep("Clicked on Save and add button");
	}

	public void clickFirstInAutoSuggestion() {
		CCUtils.verifyAndWaitForElementPresence(selectFirstOption, driver);
		javascriptClick(selectFirstOption);
	}
	
	public void verifyAddNewResourcePageUi() throws Exception {
		verifyAddNewResourcesPageTitle();
		verifyNameLabelAndTextboxDefaultValue();
		verifyExternalIDLabelTextAndTextboxDefaultValue();
		verifyResourcesTypeLabelTextAndTextboxDefaultValue();
		verifyCancelButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
		verifySaveAndAddButtonIsDisabled();	
	}
	
	public void verifyAddNewResourcesPageTitle() {
		CCUtils.verifyAndWaitForElement(labelAddNewResourcePageTitle, driver);
		log("Verifying add new resource page title");
		CCUtils.assertEquals(labelAddNewResourcePageTitle.getText().trim(), "Add a Resource");
	}
	
	public void verifyNameLabelAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelName, driver);
		log("Verifying name label text");
		CCUtils.assertEquals(labelName.getText().trim(), "Name *");
		log("Name label text is " + labelName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxtName, driver);
		log("Verifying input text placeholder is displayed for name textbox field");
		CCUtils.assertEquals(textboxtName.getAttribute("placeholder").trim(), "Input Name");
		log("Box text for name textbox field is " + textboxName.getAttribute("placeholder").trim());		
	}
	
	public void verifyExternalIDLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labeltextboxExternalId, driver);
		log("Verifying ExternalId label text");
		CCUtils.assertEquals(labeltextboxExternalId.getText().trim(), "External ID *");
		log("ExternalId label text is " + labeltextboxExternalId.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		log("Verifying input text placeholder is displayed for External Id textbox field");
		CCUtils.assertEquals(textboxExternalId.getAttribute("placeholder").trim(), "Input External Id");
		log("Box text for ExternalId textbox field is " + textboxExternalId.getAttribute("placeholder").trim());
	}	
	
	public void verifyResourcesTypeLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labeltextboxResourceType, driver);
		log("Verifying Resourec Type label text");
		CCUtils.assertEquals(labeltextboxResourceType.getText().trim(), "Resource Type *");
		log("Resource Type label text is " + labeltextboxResourceType.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownResourceType, driver);
	}	
	
	public void verifyHundredMaximumCharactersReachedErrorMessage(String resourceName) {
			log("Enter more than 100 characters in Resource Name textbox field and verify error message");
			CCUtils.verifyAndWaitForElement(textboxName, driver);
			textboxName.sendKeys(resourceName);
			log("Entered text in Resource Name field is " + resourceName);
			CCUtils.verifyAndWaitForElement(errorMaximumCharacterName, driver);
			CCUtils.assertEquals(errorMaximumCharacterName.getText().trim(), "Maximum characters reached");
			log("Error message displayed for Resource Name field is " + errorMaximumCharacterName.getText().trim());		
	 }
	
	public void verifyFiftyMaximumCharactersReachedErrorMessage(String ExternalId) {
		log("Enter more than 50 characters in ExternalId textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.sendKeys(ExternalId);
		log("Entered text in ExternalId field is " + ExternalId);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterExternalId, driver);
		CCUtils.assertEquals(errorMaximumCharacterExternalId.getText().trim(), "Maximum characters reached");
		log("Error message displayed for ExternalId field is " + errorMaximumCharacterExternalId.getText().trim());
	}
	
	public void enterResourceType(String input) {
		CCUtils.verifyAndWaitForElement(dropdownResourceType, driver);
		dropdownResourceType.sendKeys(input);
		log("Entered input for Resource : " + input);
	}
	
	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}
	
	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel button");
	}
	
	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on X icon");
	}
	
	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on add a Resource page");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button is enabled");
			CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on add a Resource page");
		}
	}
	
	public void verifySaveAndAddButtonIsDisabled() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		logStep("Verifying Save and add button is disabled");
		CCUtils.assertEquals(saveButton.isEnabled(), false);
	}
	
	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		log("Verifying cross icon is displayed on add a Resource page");
		if (crossIcon.isDisplayed() == true) {
			log("Verifying cross icon is enabled");
			CCUtils.assertTrue(crossIcon.isEnabled(), "Cross icon is disabled");
		} else {
			log("cross icon is not displayed on add a Resource page");
		}
	}
	public void verifyAddResourcePopupTitle() {
		CCUtils.verifyAndWaitForElement(labelAddNewResourcePageTitle, driver);
		log("Verifying create Resource page title");
		CCUtils.assertEquals(labelAddNewResourcePageTitle.getText().trim(), "Add a Resource");
	}
}
