// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.pxp.cc.util.CCUtils;

public class DeactivateUserPage extends CCMainPage {
	public DeactivateUserPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement searchOption;

	@FindBy(id = "searchFirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "filterSearchBtn")
	private WebElement buttonSearch;

	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement clearAllBtn;

	@FindBy(xpath = "//span[contains(text(),'Active')]")
	private WebElement activeCheckbox;

	@FindBy(id = "deactivateBtn")
	private WebElement deactivateBtn;

	@FindBy(id = "applyBtn")
	private WebElement applyBtn;
	
	@FindBy(xpath = "//td[@id='select_check']")
	private WebElement checkBox;

	public void clickOnSearchOption() {
		CCUtils.verifyAndWaitForElement(searchOption, driver);
		searchOption.click();
	}

	public void enterSearchTextInSearchFirstNameFields(String firstName) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);
	}

	public void clickOnSearchButton() throws InterruptedException {
		log("Verifying clear button is displayed on search options popup");

		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		buttonSearch.click();
	}

	public void clickOnActiveCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		CCUtils.verifyAndWaitForElement(activeCheckbox, driver);
		activeCheckbox.click();
		log("Clicked on active User Status checkbox on Search Options popup");
	}

	public void verifyClearAllButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(clearAllBtn, driver);
		CCUtils.assertEquals(clearAllBtn.getText().trim(), "CLEAR ALL");
	}

	public void clickOncheckBox() throws InterruptedException {

		CCUtils.verifyAndWaitForElement(checkBox, driver);
		checkBox.click();
	}

	public void clickOndeactivateBtn() throws InterruptedException {

		CCUtils.verifyAndWaitForElement(deactivateBtn, driver);
		deactivateBtn.click();
	}
	
	public void clickOnApplyBtn() throws InterruptedException {

		CCUtils.verifyAndWaitForElement(applyBtn, driver);
		applyBtn.click();
	}
}
