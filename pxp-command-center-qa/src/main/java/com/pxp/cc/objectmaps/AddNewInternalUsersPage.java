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

public class AddNewInternalUsersPage extends CCMainPage {

	@FindBy(id = "addNewTitle")
	private WebElement labelTitleAddNewInternalUsersPage;

	@FindBy(xpath = "//label[text()='User Roles']")
	private WebElement labelUserRoles;

	@FindBy(id = "addNewCancel")
	private WebElement buttonCancel;

	@FindBy(id = "addNewInternalSave")
	private WebElement buttonSaveAndAdd;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(xpath = "//input[@id='createUserActiveRadio-input']")
	private WebElement radioButtonActive;

	@FindBy(xpath = "//input[@id='createUserInactiveRadio-input']")
	private WebElement radioButtonInactive;

	@FindBy(xpath = "//label[text()='Active']")
	private WebElement labelActive;

	@FindBy(xpath = "//label[text()='Inactive']")
	private WebElement labelInactive;

	@FindBy(xpath = "//label[@id='rolesLabel']/span")
	private List<WebElement> labelUserRolesList;

	@FindBy(xpath = "//label[@id='rolesLabel']/mat-checkbox/label/div/input")
	private List<WebElement> checkboxUserRoles;

	List<String> actualUserRoleValues = new ArrayList<String>();

	public final List<String> expectedUserRoleValues = Arrays.asList("Super Admin", "Support T2", "Implementation",
			"Support T3", "Demo/Sales", "Support T1");

	public AddNewInternalUsersPage(WebDriver driver) {
		super(driver);
	}

	public void verifyAddNewInternalUserPageTitle() {
		CCUtils.verifyAndWaitForElement(labelTitleAddNewInternalUsersPage, driver);
		if (labelTitleAddNewInternalUsersPage.isDisplayed()) {
			CCUtils.assertEquals(labelTitleAddNewInternalUsersPage.getText().trim(), "Add New Internal User");
			log("Add new internal user page title is " + labelTitleAddNewInternalUsersPage.getText().trim());
		} else {
			log("Add new internal user page title is not displayed");
		}
	}

	public void verifyUserRoles() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(labelUserRoles, driver);
		if (labelUserRoles.isDisplayed()) {
			log("Verifying user roles label text");
			CCUtils.assertEquals(labelUserRoles.getText().trim(), "User Roles");
			log("User roles label text is " + labelUserRoles.getText().trim());
			log("Verifying user roles available for internal user");
			verifyUserRoleValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserRoleCheckboxesAreNotByDefaultSelected();
		} else {
			log("User roles label is not displayed on add new internal user page");
		}
	}

	public void getUserRoleValues() throws InterruptedException {
		for (int i = 0; i < labelUserRolesList.size(); i++) {
			actualUserRoleValues.add(labelUserRolesList.get(i).getText().trim());
		}
		log("User roles available for internal user are :" + actualUserRoleValues);
	}

	public void verifyUserRoleValues() throws InterruptedException {
		getUserRoleValues();
		CCUtils.assertEquals(actualUserRoleValues, expectedUserRoleValues);
	}

	public void verifyUserRoleCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxUserRoles.size(); i++) {
			CCUtils.assertTrue(!checkboxUserRoles.get(i).isSelected(), "User role checkboxes are already selected");
		}
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		log("Verifying cross icon is displayed on add new internal user page");
		if (crossIcon.isDisplayed() == true) {
			log("Verifying cross icon is enabled");
			CCUtils.assertTrue(crossIcon.isEnabled(), "Cross icon is disabled");
		} else {
			log("cross icon is not displayed on add new internal user page");
		}
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on add new internal user page");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button is enabled");
			CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on add new internal user page");
		}
	}

	public void verifySaveAndAddButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		log("Verifying save & add button is displayed on add new internal user page");
		if (buttonSaveAndAdd.isDisplayed() == true) {
			log("Verifying save & add button is enabled");
			CCUtils.assertTrue(buttonSaveAndAdd.isEnabled(), "Save & Add button is disabled");
			log("Verifying save & add button text");
			CCUtils.assertEquals(buttonSaveAndAdd.getText().trim(), "Save & Add");
		} else {
			log("Save & Add button is not displayed on add new internal user page");
		}
	}

	public InternalUsersPage clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		buttonSaveAndAdd.click();
		log("Clicked on Save&Add button");
		return PageFactory.initElements(driver, InternalUsersPage.class);
	}

	public InternalUsersPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel button");
		return PageFactory.initElements(driver, InternalUsersPage.class);
	}

	public InternalUsersPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on X icon");
		return PageFactory.initElements(driver, InternalUsersPage.class);
	}

	public void addUserRoles() throws InterruptedException {
		log("Select all user role");
		for (int i = 0; i < checkboxUserRoles.size(); i++) {
			javascriptClick(checkboxUserRoles.get(i));
		}
	}

}
