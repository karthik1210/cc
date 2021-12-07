// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class ApplyInheritPracticeSettingsPage extends CCMainPage {

	@FindBy(id = "applySetting")
	private WebElement labelApplyPracticeSettings;

	@FindBy(id = "inheritSetting")
	private WebElement labelInheritPracticeSettings;

	@FindBy(id = "applyDropdown")
	private WebElement practiceDropdown;

	@FindBy(xpath = "//div[@role='option']")
	private List<WebElement> listOfPractices;

	@FindBy(id = "shortcutApplyButton")
	private WebElement applyButton;

	@FindBy(id = "shortcutClearButton")
	private WebElement clearButton;

	@FindBy(id = "applyBtn")
	private WebElement saveButton;

	@FindBy(id = "applyCancel")
	private WebElement cancelButton;

	public ApplyInheritPracticeSettingsPage(WebDriver driver) {
		super(driver);
	}

	public void clickPracticeDropdown() {
		CCUtils.verifyAndWaitForElement(practiceDropdown, driver);
		log("click to practice dropdown.");
		practiceDropdown.click();
	}

	public void verifyApplyPracticeLabel() {
		CCUtils.verifyAndWaitForElement(labelApplyPracticeSettings, driver);
		log("Verifying label Apply Practice Settings is present or not.");
		CCUtils.assertEquals(labelApplyPracticeSettings.getText(), "Apply Practice Settings");
	}

	public void verifyInheritPracticeLabel() {
		CCUtils.verifyAndWaitForElement(labelInheritPracticeSettings, driver);
		log("Verifying label Inherit Practice Settings is present or not.");
		CCUtils.assertEquals(labelInheritPracticeSettings.getText(), "Inherit Practice Settings");
	}

	public void selectDesiredPractice(String practice) {
		for (WebElement option : listOfPractices) {
			if ((option.getText()).contains(practice)) {
				CCUtils.verifyAndWaitForElement(option, driver);
				option.click();
			}
		}
	}

	public void clickToApplyButton() {
		CCUtils.verifyAndWaitForElement(applyButton, driver);
		javascriptClick(applyButton);
		log("apply button clicked");
	}

	public void clickToClearButton() {
		CCUtils.verifyAndWaitForElement(clearButton, driver);
		javascriptClick(clearButton);
		log("clear button clicked");
	}

	public GeneralSettingsPage clickToSaveButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
		log("save button clicked");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickToCancelButton() {
		CCUtils.verifyAndWaitForElement(cancelButton, driver);
		cancelButton.click();
		log("cancel button clicked");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}
}
