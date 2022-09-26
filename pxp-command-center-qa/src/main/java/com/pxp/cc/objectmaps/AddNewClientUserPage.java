// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class AddNewClientUserPage extends CCMainPage {
	@FindBy(id = "addNewTitle")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[text()='Client *']")
	private WebElement labelClient;

	@FindBy(id = "selectStatus")
	private WebElement textboxSearchClientName;

	@FindBy(xpath = "//p[contains(text(),' Use the search below to')]")
	private WebElement labelSearchText;

	@FindBy(id = "searchFilterInput")
	private WebElement textboxSearchProviderOrStaffMember;

	@FindBy(id = "addFirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "addLastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "addUserNameInput")
	private WebElement textboxUserName;

	@FindBy(id = "addEmailInput")
	private WebElement textboxEmail;

	@FindBy(id = "addMobileInput")
	private WebElement textboxMobileNumber;

	@FindBy(className = "cancel")
	private WebElement buttonCancel;

	@FindBy(id = "addClientNextBtn")
	private WebElement buttonNext;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(id = "searchOrgFilterInput")
	private WebElement textboxSearchOrg;

	@FindBy(id = "addEntityCancelBtn")
	private WebElement buttonEntityCancel;

	@FindBy(id = "addEntityBackBtn")
	private WebElement buttonBack;

	@FindBy(id = "addEntitySaveBtn")
	private WebElement buttonSaveAndAdd;

	@FindBy(xpath = "//th[@role='columnheader'][1]/mat-checkbox")
	private WebElement indeterminateCheckbox;

	@FindBy(className = "page1")
	private WebElement imageStepOne;

	@FindBy(id = "step2Img")
	private WebElement imageStepTwo;

	@FindBy(className = "user-label")
	private WebElement labelUser;

	@FindBy(className = "user-detail")
	private WebElement labelUserDetails;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(xpath = "//span[contains(text(),'Which entities')]//preceding::span[@class='user-label'][1]")
	private WebElement labelUserAtStepTwo;

	@FindBy(xpath = "//span[contains(text(),'Which entities should')]")
	private WebElement labelUserDetailsAtStepTwo;

	@FindBy(id = "ClientNameError")
	private WebElement errorClientNameRequired;

	@FindBy(xpath = "//span[text()='Client *']//following::span[text()=' clear '][1]")
	private WebElement iconClearSearchText;

	@FindBy(xpath = "//span[text()='No entities selected']")
	private WebElement labelNoEntitiesSelected;

	@FindBy(xpath = "//td[@role='gridcell']/mat-checkbox/label/div/input")
	private List<WebElement> checkboxList;

	@FindBy(xpath = "//th[@role='columnheader' and text()=' System ']")
	private WebElement labelSystem;

	@FindBy(xpath = "//th[@role='columnheader' and text()=' Enterprise ']")
	private WebElement labelEnterprise;

	@FindBy(xpath = "//th[@role='columnheader' and text()=' Practice ']")
	private WebElement labelPractice;

	@FindBy(xpath = "//th[@role='columnheader' and text()=' System ']/span/mat-icon")
	private WebElement iconSystemSort;

	@FindBy(xpath = "//th[@role='columnheader' and text()=' Enterprise ']/span/mat-icon")
	private WebElement iconEnterpriseSort;

	@FindBy(xpath = "//th[@role='columnheader' and text()=' Practice ']/span/mat-icon")
	private WebElement iconPraciceSort;

	@FindBy(xpath = "//div[text()='No Clients found']")
	private WebElement errorNoClientsFound;
	
	@FindBy(xpath = "//input[@id='createUserInactiveRadio-input']")
	private WebElement inactiveStatus;

	public String columnNameValue;

	public AddNewClientUserPage(WebDriver driver) {
		super(driver);
	}

	public void verifyAddNewClientUserPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed()) {
			CCUtils.assertEquals(labelPageTitle.getText().trim(), "Add New Client User");
			log("Add new client user page title is " + labelPageTitle.getText().trim());
		} else {
			log("Add new client user page title is not displayed");
		}
	}

	public void verifyStepOneImageIsDisplayed() {
		CCUtils.verifyAndWaitForElement(imageStepOne, driver);
		CCUtils.assertTrue(imageStepOne.isDisplayed(),
				"Step one image icon is not displayed on add new client user page");
	}

	public void VerifyHeaderTextForStepOne() {
		log("Verifying header text on add new client user step one popup");
		CCUtils.verifyAndWaitForElement(labelUser, driver);
		CCUtils.assertEquals(labelUser.getText().trim(), "USER DETAILS");
		CCUtils.verifyAndWaitForElement(labelUserDetails, driver);
		CCUtils.assertEquals(labelUserDetails.getText().trim(), "Complete the following details.");
	}

	public void verifyClientLabelTextAndSearchTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelClient, driver);
		log("Verifying client label text");
		CCUtils.assertEquals(labelClient.getText().trim(), "Client *");
		log("Client label text is " + labelClient.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxSearchClientName, driver);
		log("Verifying search/select text placeholder is displayed for Client search textbox field");
		CCUtils.assertEquals(textboxSearchClientName.getAttribute("placeholder").trim(), "Search/Select");
		log("Box text for client search textbox field is "
				+ textboxSearchClientName.getAttribute("placeholder").trim());
	}

	public void verifyProviderLabelTextAndSearchTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelSearchText, driver);
		log("Verifying provider or staff member label text");
		CCUtils.assertEquals(labelSearchText.getText().trim(),
				"Use the search below to speed up your workflow and connect to an existing provider or staff member.");
		log("Provider or staff member label text is " + labelSearchText.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxSearchProviderOrStaffMember, driver);
		log("Verifying Search text placeholder is displayed for provider or staff member search textbox field");
		CCUtils.assertEquals(textboxSearchProviderOrStaffMember.getAttribute("placeholder").trim(), "Search");
		log("Box text for provider or staff member search textbox field is "
				+ textboxSearchProviderOrStaffMember.getAttribute("placeholder").trim());
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		log("Verifying cross icon is displayed on add new client user page");
		if (crossIcon.isDisplayed() == true)
			CCUtils.assertEquals(crossIcon.getText().trim(), "clear");
		else
			log("cross icon is not displayed on add new client user page");
	}

	public void verifyClientCancelButtonIsDisplayed() throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on add new client user page");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on add new client user page");
		}
	}

	public void verifyBackButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonBack, driver);
		log("Verifying back button is displayed on add new client user page");
		if (buttonBack.isDisplayed() == true)
			CCUtils.assertEquals(buttonBack.getText().trim(), "Back");
		else
			log("Back button is not displayed on add new client user page");
	}

	public void verifyNextButtonIsDisabled() {
		log("Verifying next button is disabled");
		CCUtils.assertEquals(buttonNext.getAttribute("disabled"), "true");
	}

	public void verifyNextButtonIsEnabled() throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(buttonNext, driver);
		log("Verifying next button is enabled");
		CCUtils.assertTrue(buttonNext.isEnabled(), "Next button is disabled");
	}

	public void enterClientName(String clientName) {
		CCUtils.verifyAndWaitForElement(textboxSearchClientName, driver);
		textboxSearchClientName.sendKeys(clientName);
		log("Entered input for client field : " + clientName);
	}

	public void clickFirstInAutoSuggestion() {
		CCUtils.verifyAndWaitForElement(selectFirstInAutoSuggestion, driver);
		if (selectFirstInAutoSuggestion.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstInAutoSuggestion);

		} else {
			log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present in client");
		}
	}

	public void searchClient(String clientName) {
		enterClientName(clientName);
		clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
	}

	public void clickOnNextButton() {
		CCUtils.verifyAndWaitForElement(buttonNext, driver);
		buttonNext.click();
		log("Clicked on Next button");
	}

	public void clickOnBackButton() {
		CCUtils.verifyAndWaitForElement(buttonBack, driver);
		buttonBack.click();
		log("Clicked on back button");
	}

	public ClientUsersPage clickOnEntityCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonEntityCancel, driver);
		buttonEntityCancel.click();
		log("Clicked on entity cancel button");
		return PageFactory.initElements(driver, ClientUsersPage.class);
	}

	public void verifyHeaderTextForStepTwo(String firstName, String lastName) {
		log("Verifying header text on add new client user step two popup");
		CCUtils.verifyAndWaitForElement(labelUserAtStepTwo, driver);
		CCUtils.assertEquals(labelUserAtStepTwo.getText().trim(), "USER DETAILS");
		CCUtils.verifyAndWaitForElement(labelUserDetailsAtStepTwo, driver);
		CCUtils.assertEquals(labelUserDetailsAtStepTwo.getText().trim(),
				"Which entities should " + firstName + " " + lastName + " be able to access?");
	}

	public void verifyStepTwoImageIsDisplayed() {
		log("Verifying image is present on add new client user step two popup");
		CCUtils.verifyAndWaitForElement(imageStepTwo, driver);
		CCUtils.assertTrue(imageStepTwo.isDisplayed(),
				"Step two image icon is not displayed on add new client user page");
	}

	public void verifyEntitySearchTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(textboxSearchOrg, driver);
		if (textboxSearchOrg.isDisplayed() == true) {
			log("Verifying search text placeholder is displayed for entity search textbox field");
			CCUtils.assertEquals(textboxSearchOrg.getAttribute("placeholder").trim(), "Search");
			log("Box text for entity search textbox field is " + textboxSearchOrg.getAttribute("placeholder").trim());
		} else {
			log("Entity search textbox is not present");
		}
	}

	public void verifyEntityCancelButtonIsDisplayed() throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(buttonEntityCancel, driver);
		log("Verifying cancel button is displayed on add new client user page");
		if (buttonEntityCancel.isDisplayed() == true)
			CCUtils.assertEquals(buttonEntityCancel.getText().trim(), "Cancel");
		else
			log("Cancel button is not displayed on add new client user page");
	}

	public void verifySaveAndAddButtonIsDisabled() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		log("Verifying save and add button is disabled");
		CCUtils.assertEquals(buttonSaveAndAdd.getAttribute("disabled"), "true");
	}

	public void verifyAllCheckboxesAreNotSelected() {
		log("Verifying all checkboxes are not selected");
		for (int i = 0; i < checkboxList.size(); i++) {
			CCUtils.assertTrue(checkboxList.get(i).getAttribute("aria-checked").equals("false"), "Checkox is selected");
		}
	}

	public void verifyNoEntitiesSelectedMessageIsDisplayed() {
		log("Verifying no entites selected label is displayed");
		CCUtils.verifyAndWaitForElement(labelNoEntitiesSelected, driver);
		if (labelNoEntitiesSelected.isDisplayed() == true) {
			CCUtils.assertEquals(labelNoEntitiesSelected.getText().trim(), "No entities selected");
		} else {
			log("No entities selected label is nor present");
		}
	}

	public void clickOnIndeterminateCheckbox() {
		CCUtils.verifyAndWaitForElement(indeterminateCheckbox, driver);
		indeterminateCheckbox.click();
		log("Clicked on indeterminate checkbox");
	}

	public void verifyAllCheckboxesAreSelected() {
		log("Verifying all checkboxes are selected");
		for (int i = 0; i < checkboxList.size(); i++) {
			CCUtils.assertTrue(checkboxList.get(i).isSelected(), "Checkox is not selected");
		}
	}

	public void verifyNoEntitiesSelectedMessageIsNotPresent() {
		log("Verifying no entites selected label is not displayed");
		CCUtils.assertEquals(labelNoEntitiesSelected.isDisplayed(), false);
	}

	public void verifySaveAndAddButtonIsEnabled() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		CCUtils.assertEquals(buttonSaveAndAdd.isEnabled(), true);
	}

	public void verifyEntitiesTableHeader() {
		log("Verifying indeterminate checkbox is present in manage entities table header");
		CCUtils.verifyAndWaitForElement(indeterminateCheckbox, driver);
		CCUtils.assertTrue(indeterminateCheckbox.isDisplayed(),
				"Indeterminate Checkbox is not present in entities table header");
		log("Verifying system column is displayed");
		CCUtils.verifyAndWaitForElement(labelSystem, driver);
		if (labelSystem.isDisplayed() == true) {
			columnNameValue = labelSystem.getText().replace("arrow_upward", "");
			CCUtils.assertEquals(columnNameValue.trim(), "System");
		} else {
			log("System column is not present in manage entities table");
		}
		log("Verifying sort icon is displayed for the system column and it's pointing upward");
		CCUtils.assertEquals(iconSystemSort.getText().trim(), "arrow_upward");
		log("Verifying enterprise column is displayed");
		CCUtils.verifyAndWaitForElement(labelEnterprise, driver);
		if (labelEnterprise.isDisplayed() == true) {
			columnNameValue = labelEnterprise.getText().replace("arrow_upward", "");
			CCUtils.assertEquals(columnNameValue.trim(), "Enterprise");
		} else {
			log("Enterprise column is not present in manage entities table");
		}
		log("Verifying sort icon is displayed for the enterprise column and it's pointing upward");
		CCUtils.assertEquals(iconEnterpriseSort.getText().trim(), "arrow_upward");
		log("Verifying practice column is displayed");
		CCUtils.verifyAndWaitForElement(labelPractice, driver);
		if (labelPractice.isDisplayed() == true) {
			columnNameValue = labelPractice.getText().replace("arrow_upward", "");
			CCUtils.assertEquals(columnNameValue.trim(), "Practice");
		} else {
			log("Practice column is not present in manage entities table");
		}
		log("Verifying sort icon is displayed for the practice column and it's pointing upward");
		CCUtils.assertEquals(iconPraciceSort.getText().trim(), "arrow_upward");
	}

	public String getClientNameValue() {
		CCUtils.verifyAndWaitForElement(textboxSearchClientName, driver);
		return textboxSearchClientName.getAttribute("value").trim();
	}

	public void verifyNoClientsFoundErrorMessage() {
		CCUtils.verifyAndWaitForElement(errorNoClientsFound, driver);
		CCUtils.assertEquals(errorNoClientsFound.getText(), "No Clients found");
		log("No clients found for the entered text");
	}

	public ClientUsersPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on cancel button");
		return PageFactory.initElements(driver, ClientUsersPage.class);
	}

	public ClientUsersPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on cross icon");
		return PageFactory.initElements(driver, ClientUsersPage.class);
	}

	public ClientUsersPage clickOnSaveAndAdd() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		buttonSaveAndAdd.click();
		log("Clicked on save and add button");
		return PageFactory.initElements(driver, ClientUsersPage.class);
	}
	
	public void clickOnInactiveStatus() {
		CCUtils.verifyAndWaitForElement(inactiveStatus, driver);
		inactiveStatus.click();
		log("Clicked on Inactive Status");
	}
}
