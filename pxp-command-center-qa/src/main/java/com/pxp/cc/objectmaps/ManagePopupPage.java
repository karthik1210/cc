// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pxp.cc.util.CCUtils;

public class ManagePopupPage extends CCMainPage {

	@FindBy(id = "addNewTitle")
	private WebElement headerManageAssociatedEntities;

	@FindBy(id = "searchFilterInput")
	private WebElement textBoxSearch;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(id = "addEntityCancelBtn")
	private WebElement buttonCancel;

	@FindBy(id = "addEntitySaveBtn")
	private WebElement buttonSaveApply;

	@FindBy(id = "mat-checkbox-1-input")
	private WebElement checkBoxSelectAllEntities;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@class='mat-row cdk-row ng-star-inserted']//mat-checkbox")
	private WebElement checkBoxSingleEntity;

	@FindBy(xpath = "//th[contains(text(),'System')]//mat-icon")
	private WebElement filterSystem;

	@FindBy(xpath = "//th[contains(text(),'Enterprise')]//mat-icon")
	private WebElement filterEnterprise;

	@FindBy(xpath = "//th[contains(text(),'Practice')]//mat-icon")
	private WebElement filterPractice;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']")
	private List<WebElement> listOfEntities;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@class='mat-row cdk-row ng-star-inserted selected']")
	private List<WebElement> listOfSelectedAssociateEntities;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@class='mat-row cdk-row ng-star-inserted']")
	private List<WebElement> listOfNonSelectedEntities;

	@FindBy(xpath = "//td[@class='mat-cell cdk-cell cdk-column-systemName mat-column-systemName ng-star-inserted']")
	private WebElement entitySystem;

	@FindBy(xpath = "//td[@class='mat-cell cdk-cell cdk-column-enterpriseName mat-column-enterpriseName ng-star-inserted']")
	private WebElement entityEnterprise;

	@FindBy(xpath = "//td[@class='mat-cell cdk-cell cdk-column-practiceName mat-column-practiceName ng-star-inserted']")
	private WebElement entityPractice;

	@FindBy(xpath = "//td[@role='gridcell']")
	private List<WebElement> tableColumnData;

	public ManagePopupPage(WebDriver driver) {
		super(driver);
	}

	public void verifyManageAssociatedEntitiesPopupTitle() {
		CCUtils.verifyAndWaitForElement(headerManageAssociatedEntities, driver);
		CCUtils.assertEquals(headerManageAssociatedEntities.getText().toString().trim(), "Manage Associated Entities");
		log("Title : " + headerManageAssociatedEntities.getText().toString().trim() + " is verified.");
	}

	public void verifySearchTextbox() {
		CCUtils.verifyAndWaitForElement(textBoxSearch, driver);
		log("verified the presence of search textbox.");
		log("Verifying Search placeholder is displayed in search textbox");
		CCUtils.assertEquals(textBoxSearch.getAttribute("placeholder").trim(), "Search");
		log("Box text for search textbox is: " + textBoxSearch.getAttribute("placeholder").trim());
	}

	public void verifyCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		CCUtils.assertTrue((buttonCancel.isDisplayed() && buttonCancel.isEnabled()),
				"Verifying cancel button is displayed and enabled");
	}

	public void verifyCrossButton() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		CCUtils.assertTrue((crossIcon.isDisplayed() && crossIcon.isEnabled()),
				"Verifying cross icon button is displayed and enabled");
	}

	public void verifySaveApplyButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveApply, driver);
		CCUtils.assertEquals(buttonSaveApply.getText().trim(), "Save & Apply");
		CCUtils.assertTrue(buttonSaveApply.isDisplayed(), "Verifying cross icon button is displayed");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.attributeToBe(buttonSaveApply, "ng-reflect-ng-class", "disabled"));
	}

	public void verifySystemFilter() {
		CCUtils.verifyAndWaitForElement(filterSystem, driver);
		CCUtils.assertEquals(filterSystem.getText().trim(), "arrow_upward");
	}

	public void verifyEnterpriseFilter() {
		CCUtils.verifyAndWaitForElement(filterEnterprise, driver);
		CCUtils.assertEquals(filterEnterprise.getText().trim(), "arrow_upward");
	}

	public void verifyPracticeFilter() {
		CCUtils.verifyAndWaitForElement(filterPractice, driver);
		CCUtils.assertEquals(filterPractice.getText().trim(), "arrow_upward");
	}

	public void fetchListOfNonSelectedEntities(String addNewEntity) {
		List<String> listOfEntities = new ArrayList<String>();
		for (WebElement element : listOfNonSelectedEntities) {
			CCUtils.verifyAndWaitForElement(element, driver);
			listOfEntities.add(element.getText().trim());
			if (element.getText().trim().contains(addNewEntity)) {
				checkBoxSingleEntity.click();
				log("select the entity : " + addNewEntity);
			}
		}
	}

	public void fetchListOfSelectedEntities(String removeEntity) {
		List<String> listOfEntities = new ArrayList<String>();
		for (WebElement element : listOfSelectedAssociateEntities) {
			listOfEntities.add(element.getText().trim());
			if (element.getText().trim().contains(removeEntity)) {
				checkBoxSingleEntity.click();
				log("select the entity : " + removeEntity);
			}
		}
	}

	public String fetchListOfSelectedEntities() {
		String list = null;
		List<String> listdata = new ArrayList<String>();
		for (WebElement element : listOfSelectedAssociateEntities) {
			CCUtils.verifyAndWaitForElement(element, driver);
			listdata.add(element.getText().trim());
		}
		list = listdata.toString();
		return list;
	}

	public ClientUserDetailsPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Cancel button Clicked");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public ClientUserDetailsPage clickOnCloseWindow() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Cross icon button Clicked");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public ClientUserDetailsPage clickOnSaveApplyButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveApply, driver);
		buttonSaveApply.click();
		log("Save/Apply button Clicked");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public void searchEntity(String enitity) {
		verifySearchTextbox();
		textBoxSearch.sendKeys(enitity);
		textBoxSearch.sendKeys(Keys.ENTER);
		log("Searched text " + enitity + "in search textbox");
	}

	public void verifySearchResultDisplayed(String searchData) {
		for (WebElement element : listOfNonSelectedEntities) {
			CCUtils.verifyAndWaitForElement(element, driver);
			if (element.getText().contains("No search results found")) {
				log("entity " + searchData + " does not found.");
			} else if (element.getText().contains(searchData)) {
				log("Entity" + searchData + " found.");
			}
		}
	}

	public void clickToSelectAllCheckBox() {
		CCUtils.verifyAndWaitForElement(checkBoxSelectAllEntities, driver);
		javascriptClick(checkBoxSelectAllEntities);
		log("Select All CheckBox clicked");
	}

	public void verifySelectAllCheckBoxInManagePopup(String value) {
		CCUtils.verifyAndWaitForElement(checkBoxSelectAllEntities, driver);
		CCUtils.assertEquals(checkBoxSelectAllEntities.getAttribute("aria-checked"), value);
	}

	public void verifySaveApplyButtonStatusInManagePopup(String value) {
		CCUtils.verifyAndWaitForElement(buttonSaveApply, driver);
		CCUtils.assertEquals(buttonSaveApply.getAttribute("class"), value);
	}

}
