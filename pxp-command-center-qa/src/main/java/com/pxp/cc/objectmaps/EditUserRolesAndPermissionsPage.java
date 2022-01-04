// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class EditUserRolesAndPermissionsPage extends CCMainPage{

	public EditUserRolesAndPermissionsPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//span[text()=' Edit User Roles & Permissons']")
	private WebElement labelEditPageTitle;

	@FindBy(className = "roleInput")
	private WebElement labelUserRole;

	@FindBy(id = "selectRole")
	private WebElement textBoxUserRole;
	
	@FindBy(id = "selectRoletext")
	private WebElement selectedUserRoleBoxText;

	@FindBy(xpath = "//label[text()='Locations']")
	private WebElement labelLocations;

	@FindBy(id = "selectLocation")
	private WebElement dropdownLocations;

	@FindBy(id = "cancelDialog")
	private WebElement buttonCancel;

	@FindBy(id = "saveButton")
	private WebElement buttonSave;

	@FindBy(id = "closeDialog")
	private WebElement buttonCrossIcon;

	@FindBy(id = "locationClearButton")
	private WebElement buttonClear;

	@FindBy(id = "locationApplyButton")
	private WebElement buttonApply;

	@FindBy(id = "applyLabel")
	private WebElement checkboxSelectAll;

	@FindBy(id = "selectedValue")
	private List<WebElement> listOfLocationsCheckbox;

	@FindBy(xpath = "//label[text()='Locations']//following::div[@class='ng-value ng-star-inserted']/span[2]")
	private List<WebElement> listOfSelectedLocations;

	@FindBy(xpath = "//div[@role='option']")
	private List<WebElement> listOfLocation;

	@FindBy(xpath = "//label[text()='Locations']//following::div[@class='ng-value ng-star-inserted']/span[1]")
	private List<WebElement> listOfSelectedLocationsCrossIcon;

	public final List<String> expectedUserRoleList = Arrays.asList("Client - Super Admin",
			"Client - Staff (View Only)");
	List<String> actualUserRoleList = new ArrayList<String>();

	public void verifyPageTitle() {
		CCUtils.verifyAndWaitForElement(labelEditPageTitle, driver);
		log("Verifying edit user roles and permissions page title");
		CCUtils.assertEquals(labelEditPageTitle.getText().trim(), "Edit User Roles & Permissons");
		log("Edit user roles and permissions page title is: " + labelEditPageTitle.getText().trim());
	}
	
	public void verifyUserRoleLabelTextAndTextDefaultValue(String selectedUserRole) {
		CCUtils.verifyAndWaitForElement(labelUserRole, driver);
		log("Verifying user role label text");
		CCUtils.assertEquals(labelUserRole.getText().trim(), "User Role *");
		log("User role label text is: " + labelUserRole.getText().trim());
		CCUtils.verifyAndWaitForElement(selectedUserRoleBoxText, driver);
		log("Verifying "+selectedUserRole+" placeholder is displayed for user role dropdown");
		CCUtils.assertEquals(selectedUserRoleBoxText.getAttribute("placeholder").trim(), selectedUserRole);
		log("Box text for user role dropdown is: " + selectedUserRoleBoxText.getAttribute("placeholder").trim());
	}

	public void verifyLocationsLabelTextAndDropDownDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLocations, driver);
		log("Verifying locations label text");
		CCUtils.assertEquals(labelLocations.getText().trim(), "Locations");
		log("Loations label text is: " + labelLocations.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownLocations, driver);
		log("Verifying Search/Select placeholder is displayed for locations dropdown");
		CCUtils.assertEquals(dropdownLocations.getAttribute("placeholder").trim(), "Search/Select");
		log("Default value for locations dropdown is: " + dropdownLocations.getAttribute("placeholder").trim());
	}
	
	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		log("Verifying cross icon is displayed on edit user roles & permissions page");
		if (buttonCrossIcon.isDisplayed() == true) {
			CCUtils.assertEquals(buttonCrossIcon.getText().trim(), "clear");
			log("Cross icon text is : " + buttonCrossIcon.getText().trim());
		} else {
			log("cross icon is not displayed on edit user roles & permissions page");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on edit user roles & permissions page");
		if (buttonCancel.isDisplayed() == true) {
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
			log("Cancel button text is : " + buttonCancel.getText().trim());
		} else {
			log("Cancel button is not displayed on edit user roles & permissions page");
		}
	}
	
	public void verifySaveButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		log("Verifying save button is displayed on edit user roles & permissions page");
		if (buttonSave.isDisplayed() == true) {
			CCUtils.assertEquals(buttonSave.getText().trim(), "Save");
			log("Save button text is : " + buttonSave.getText().trim());
		} else {
			log("Save button is not displayed on edit user roles & permissions page");
		}
	}

	public ClientUserDetailsPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on the cancel button");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public ClientUserDetailsPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(buttonCrossIcon, driver);
		buttonCrossIcon.click();
		log("Clicked on the cross icon");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public ClientUserDetailsPage clickOnSaveButton() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		buttonSave.click();
		log("Clicked on the save button");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}
	
	public void clickOnLocationsDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownLocations, driver);
		dropdownLocations.click();
		log("Clicked on the locations dropdown");
	}

	public void clickOnSelectAllCheckbox() {
		CCUtils.verifyAndWaitForElement(checkboxSelectAll, driver);
		checkboxSelectAll.click();
		log("Clicked on the select all checkbox");
	}

	public void verifyAllCheckboxesAreSelected() {
		log("Verifying all location checkboxes are selected");
		for (int i = 0; i < listOfLocationsCheckbox.size(); i++) {
			CCUtils.assertTrue(listOfLocationsCheckbox.get(i).isSelected(), "Checkox is not selected");
		}
	}

	public void selectLocations(List<String> locations) {
		for (int i = 0; i < locations.size(); i++) {
			if (listOfLocation.get(i).getText().trim().equals(locations.get(i))) {
				listOfLocation.get(i).click();
			} else {
				log("Location is not displayed in locations dropdown");
			}
		}
	}
	
	public void clearSearchedLocationsUsingCrossIcon(List<String> locations) {
		for (int i = 0; i < locations.size(); i++) {
			if (listOfSelectedLocations.get(i).getText().trim().equals(locations.get(i))) {
				listOfSelectedLocationsCrossIcon.get(i).click();
			} else {
				log("Location is not displayed in locations dropdown");
			}
		}
	}

	public void verifyAllCheckboxesAreNotSelected() {
		log("Verifying all checkboxes are not selected");
		for (int i = 0; i < listOfLocationsCheckbox.size(); i++) {
			CCUtils.assertEquals(listOfLocationsCheckbox.get(i).isSelected(), false);
		}
	}

	public void clickOnApplyButton() {
		CCUtils.verifyAndWaitForElement(buttonApply, driver);
		buttonApply.click();
		log("Clicked on the apply button");
	}

	public void clickOnClearButton() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		buttonClear.click();
		log("Clicked on the clear button");
	}

	public void verifySelectedLocationsAreDisplayedInLocationsDropdown(List<String> selectedLocations) {
		for (int i = 0; i < selectedLocations.size(); i++) {
			CCUtils.assertEquals(listOfSelectedLocations.get(i).getText().trim(), selectedLocations.get(i));
			log("Selected " + listOfSelectedLocations.get(i).getText().trim() + " is displayed in locations dropdown");
		}
	}

	public void verifySelectedLocationsAreClearedInLocationsDropdown() {
		CCUtils.assertTrue(listOfSelectedLocations.isEmpty(),
				"Selected locations are still displayed in locations dropdown");
	}
}
