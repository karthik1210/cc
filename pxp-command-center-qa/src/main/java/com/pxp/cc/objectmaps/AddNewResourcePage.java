// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

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

	@FindBy(id = "addNewProviderSave")
	private WebElement saveButton;

	@FindBy(id = "ExternalIdInput")
	private WebElement textboxExternalId;

	@FindBy(xpath = "(//input[@aria-autocomplete='list'])[5]")
	private WebElement dropdownResourceType;

	@FindBy(id = "searchValues")
	private WebElement selectFirstOption;

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
	}

	public void clickFirstInAutoSuggestion() {
		CCUtils.verifyAndWaitForElement(selectFirstOption, driver);
		selectFirstOption.click();
	}
}
