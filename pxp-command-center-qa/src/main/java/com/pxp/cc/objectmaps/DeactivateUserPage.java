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

	@FindBy(className = "mat-icon notranslate mat-list-icon material-icons mat-icon-no-color")
	private WebElement tabCCUser;

	@FindBy(id = "deactivate")
	private WebElement buttonDeactivate;

	@FindBy(id = "confirm")
	private WebElement buttonConfirmInDeactivatePopup;

	@FindBy(id = "cancel")
	private WebElement buttonCancelinDeactivatePopup;

	@FindBy(id = "generalToggleIcon-button")
	private WebElement labelDeactivatePopup;

	@FindBy(id = "userchk")
	private WebElement selectUser;

	@FindBy(xpath = "userchk/span/div[1]]")
	private WebElement selectUserTwo;

	@FindBy(id = "generalToggleIcon-button")
	private WebElement deactivatePopupMessage;

	public void clickCCUserIcon() {
		CCUtils.verifyAndWaitForElement(tabCCUser, driver);
		tabCCUser.click();
	}

	public void clickDeactivateButton() {
		CCUtils.verifyAndWaitForElement(buttonDeactivate, driver);
		buttonDeactivate.click();
	}

	public void getUserStatus() {
		CCUtils.verifyAndWaitForElement(buttonDeactivate, driver);
		buttonDeactivate.click();
	}

	public void selectUser() {
		CCUtils.verifyAndWaitForElement(selectUser, driver);
		selectUser.click();
	}

	public void selectUserTwo() {
		CCUtils.verifyAndWaitForElement(selectUser, driver);
		selectUserTwo.click();
	}

	public void clickDeactivate() {
		CCUtils.verifyAndWaitForElement(buttonConfirmInDeactivatePopup, driver);
		buttonConfirmInDeactivatePopup.click();
	}

	public String verifyPopUpMessage() {
		CCUtils.verifyAndWaitForElement(labelDeactivatePopup, driver);
		return labelDeactivatePopup.getText();
	}

}
