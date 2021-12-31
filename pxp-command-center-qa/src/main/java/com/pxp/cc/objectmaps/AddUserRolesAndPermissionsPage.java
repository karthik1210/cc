// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class AddUserRolesAndPermissionsPage extends CCMainPage {

	public AddUserRolesAndPermissionsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[text()=' Add User Roles & Permissons']")
	private WebElement labelPageTitle;

	@FindBy(className = "roleInput")
	private WebElement labelUserRole;

	@FindBy(id = "selectRole")
	private WebElement dropdownUserRole;

	@FindBy(xpath = "//label[text()='Locations']")
	private WebElement labelLocations;

	@FindBy(id = "selectLocation")
	private WebElement dropdownLocations;

	@FindBy(id = "cancelDialog")
	private WebElement buttonCancel;

	@FindBy(id = "addRole")
	private WebElement buttonAdd;

	@FindBy(id = "closeDialog")
	private WebElement buttonCrossIcon;

	@FindBy(xpath = "//div[text()=' User Role is required ']")
	private WebElement errorUserRoleRequired;

	@FindBy(xpath = "//div[@role='option']")
	private List<WebElement> listUserRoleOption;

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
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		log("Verifying add user roles and permissions page title");
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Add User Roles & Permissons");
		log("Add user roles and permissions page title is: " + labelPageTitle.getText().trim());
	}

	public void verifyUserRoleLabelTextAndDropDownDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUserRole, driver);
		log("Verifying user role label text");
		CCUtils.assertEquals(labelUserRole.getText().trim(), "User Role *");
		log("User role label text is: " + labelUserRole.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownUserRole, driver);
		log("Verifying Search/Select placeholder is displayed for user role dropdown");
		CCUtils.assertEquals(dropdownUserRole.getAttribute("placeholder").trim(), "Search/Select");
		log("Box text for user role dropdown is: " + dropdownUserRole.getAttribute("placeholder").trim());
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
		log("Verifying cross icon is displayed on add user roles & permissions page");
		if (buttonCrossIcon.isDisplayed() == true) {
			CCUtils.assertEquals(buttonCrossIcon.getText().trim(), "clear");
			log("Cross icon text is : " + buttonCrossIcon.getText().trim());
		} else {
			log("cross icon is not displayed on add user roles & permissions page");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on add user roles & permissions page");
		if (buttonCancel.isDisplayed() == true) {
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
			log("Cancel button text is : " + buttonCancel.getText().trim());
		} else {
			log("Cancel button is not displayed on add user roles & permissions page");
		}
	}

	public void verifyAddButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonAdd, driver);
		log("Verifying add button is displayed on add user roles & permissions page");
		if (buttonAdd.isDisplayed() == true) {
			CCUtils.assertEquals(buttonAdd.getText().trim(), "Add");
			log("Add button text is : " + buttonAdd.getText().trim());
		} else {
			log("Add button is not displayed on add user roles & permissions page");
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

	public ClientUserDetailsPage clickOnAddButton() {
		CCUtils.verifyAndWaitForElement(buttonAdd, driver);
		buttonAdd.click();
		log("Clicked on the add button");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public String getRequiredFieldErrorMessage() {
		CCUtils.verifyAndWaitForElement(errorUserRoleRequired, driver);
		return errorUserRoleRequired.getText().trim();
	}

	public void clickOnUserRoleDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownUserRole, driver);
		dropdownUserRole.click();
		log("Clicked on the user role dropdown");
	}

	public void clickOnLocationsDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownLocations, driver);
		dropdownLocations.click();
		log("Clicked on the locations dropdown");
	}

	public void selectUserRole(String userRole) {
		if (userRole.equals("Client - Super Admin")) {
			listUserRoleOption.get(0).click();
			log("Clicked on the client super admin user role");
		} else if (userRole.equals("Client - Staff (View Only)")) {
			listUserRoleOption.get(1).click();
			log("Clicked on the client staff(view only) user role");
		}
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

	public void getUserRole() {
		for (int i = 0; i < listUserRoleOption.size(); i++) {
			actualUserRoleList.add(listUserRoleOption.get(i).getText().trim());
		}
		log("User role dropdown options are :" + actualUserRoleList);
	}

	public void verifyUserRoleOptions() throws InterruptedException {
		log("Verifying user role dropdown options");
		CCUtils.assertEquals(actualUserRoleList, expectedUserRoleList);
	}
}
