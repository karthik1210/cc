// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class LocationDetailsPage extends CCMainPage {

	public LocationDetailsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[text()='Location Details']")
	private WebElement titleLocationDetails;

	@FindBy(xpath = "//div[text()='Resource']")
	private WebElement labelResource;

	@FindBy(id = "resourceInput")
	private WebElement textboxResource;

	@FindBy(xpath = "(//div[@role='option'])[1]")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(id = "cancel-icon")
	private WebElement buttonCancel;

	@FindBy(xpath = "//span[contains(text(),'clear')]")
	private WebElement crossIcon;

	@FindBy(xpath = "(//td[@role='gridcell'])[1]")
	private WebElement newResource;

	@FindBy(id = "removeResourceBtn")
	private WebElement removeConnection;

	@FindBy(xpath = "//div[contains(text(),'Connected Scheduling Resources')]")
	private WebElement labelSection;

	@FindBy(xpath = "(//button[contains(text(),'+ ADD')])[2]")
	private WebElement btnAdd;

	@FindBy(xpath = "//div[contains(text(),'Resource Name')]")
	private WebElement labelResourceNameColumn;

	@FindBy(xpath = "//div[contains(text(),'Type')]")
	private WebElement labelTypeColumn;

	@FindBy(id = "save-icon")
	private WebElement clickAdd;

	@FindBy(xpath = "//div[@class='title']")
	private WebElement popupTitle;

	public void verifyLocationDetailsTitle() {
		CCUtils.verifyAndWaitForElement(titleLocationDetails, driver);
		log("Verifying Location Details Title");
		CCUtils.assertEquals(titleLocationDetails.getText().trim(), "Location Details");
	}

	public void verifyConnectedSchedulingResourcesSection() throws InterruptedException, AWTException {
		log("Verifying Connected Scheduling Resources Section");
		verifyConnectedSchedulingResourcesLabel();
		verifyResourceNameColumnLabel();
		verifyTypeColumnLabel();
		verifyAddButtonLabel();
	}

	public void verifyConnectedSchedulingResourcesLabel() {
		CCUtils.verifyAndWaitForElement(labelSection, driver);
		log("Verifying Connected Scheduling Resources Label");
		CCUtils.assertEquals(labelSection.getText().trim(), "Connected Scheduling Resources");
	}

	public void verifyResourceNameColumnLabel() {
		CCUtils.verifyAndWaitForElement(labelResourceNameColumn, driver);
		log("Verifying Resource Name Column Label");
		CCUtils.assertEquals(labelResourceNameColumn.getText().trim(), "Resource Name");
	}

	public void verifyTypeColumnLabel() {
		CCUtils.verifyAndWaitForElement(labelTypeColumn, driver);
		log("Verifying Type Column Label");
		CCUtils.assertEquals(labelTypeColumn.getText().trim(), "Type");
	}

	public void verifyAddButtonLabel() {
		CCUtils.verifyAndWaitForElement(btnAdd, driver);
		log("Verifying Add Button Label");
		CCUtils.assertEquals(btnAdd.getText().trim(), "+ ADD");
	}

	public void clickOnAddButton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(btnAdd, driver);
		btnAdd.click();
	}

	public void clickOnAddToSave() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(clickAdd, driver);
		clickAdd.click();
	}

	public void verifyAddResourcePopupTitle() {
		CCUtils.verifyAndWaitForElement(popupTitle, driver);
		log("Verifying Add Resource Popup title");
		CCUtils.assertEquals(popupTitle.getText().trim(), "Add Resource(s)");
	}

	public void verifyResourceNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelResource, driver);
		log("Verifying Resource name label text");
		CCUtils.assertEquals(labelResource.getText().trim(), "Resource");
		log("Resource name label text is " + labelResource.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxResource, driver);
		log("Verifying Input text here placeholder is displayed for Resource name textbox field");
		CCUtils.assertEquals(textboxResource.getAttribute("placeholder").trim(), "Input Text");
		log("Box text for Resource name textbox field is " + textboxResource.getAttribute("placeholder").trim());
	}

	public void verifyAddButtonIsDisabled() {
		CCUtils.verifyAndWaitForElement(clickAdd, driver);
		CCUtils.assertEquals(clickAdd.isEnabled(), false);
	}

	public void enterResourceName(String input) {
		CCUtils.verifyAndWaitForElement(textboxResource, driver);
		textboxResource.sendKeys(input);
		log("Entered input for Resource Name : " + input);
	}

	public void clickFirstInAutoSuggestion() {
		CCUtils.verifyAndWaitForElement(selectFirstInAutoSuggestion, driver);
		if (selectFirstInAutoSuggestion.isDisplayed()) {
			log("Clicked on " + selectFirstInAutoSuggestion.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstInAutoSuggestion);

		} else {
			log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present");
		}
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel Button");
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on cross Icon");
	}

	public void verifyNewlyAddedConnectedSchedulingResource() {
		CCUtils.verifyAndWaitForElement(newResource, driver);
		log("Verifying newly added Connected Scheduling Resource in list");
		CCUtils.assertEquals(newResource.isDisplayed(), true);
	}

	public void clickOnRemoveConnectionButton() {
		CCUtils.verifyAndWaitForElement(removeConnection, driver);
		removeConnection.click();
		log("Clicked on Remove Connection Button");
	}
}
