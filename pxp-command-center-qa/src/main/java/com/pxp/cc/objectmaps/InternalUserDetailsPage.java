// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class InternalUserDetailsPage extends CCMainPage {

	@FindBy(xpath = "//div[@class='arrowDiv']")
	private WebElement labelPageTitle;

	@FindBy(id = "kebabMenu")
	private WebElement buttonKebabMenu;

	@FindBy(id = "enrollBtn")
	private WebElement buttonSendEnrollmentEmail;

	@FindBy(id = "enrollStatus")
	private WebElement labelEnrollmentStatusValue;

	@FindBy(id = "userStatus")
	private WebElement labelUserStatusValue;

	@FindBy(xpath = "//mat-card-title[@id='userName']")
	private WebElement labelUsernameValue;

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(xpath = "//mat-card-title[@class='mat-card-title pt-1 text-oneline']")
	private WebElement labelTitleAdminView;

	@FindBy(xpath = "//mat-card-subtitle[@class='mat-card-subtitle text-oneline mb-0 ng-star-inserted']")
	private WebElement labelSubtitleAdminView;

	@FindBy(xpath = "//div[@class='mat-card-avatar mat-fab mat-button-base mat-accent shadow-none mr-3']")
	private WebElement labelIntialNameAdminView;

	@FindBy(xpath = "//div[@class='flex py-3 px-4']")
	private List<WebElement> labelLastLoginDateEnrollmentStatusUserStatusAdminView;

	@FindBy(xpath = "//mat-icon[contains(text(),'more_horiz')]")
	private WebElement buttonKebabMenuAdminView;

	@FindBy(xpath = "//button[@role='menuitem']")
	private List<WebElement> buttonKebabMenuOptionsAdminView;

	@FindBy(xpath = "//span[@class='locked-label']")
	private WebElement labelUserStatus;

	@FindBy(xpath = "//td[@role='gridcell']/span/mat-icon")
	private WebElement activeUserStatus;

	@FindBy(xpath = "//button[contains(text(),'Deactivate User')]")
	private WebElement kebabDeactiveUserOption;

	@FindBy(xpath = "//button[contains(text(),'Activate User')]")
	private WebElement kebabActiveUserOption;

	@FindBy(xpath = "//span[@class='locked']")
	private WebElement labelUserStatusLocked;

	@FindBy(xpath = "//span[@class='unlock']")
	private WebElement labelUserStatusUnlock;

	public InternalUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		javascriptClick(buttonKebabMenu);
	}

	public void verifySuccessToaster() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), "Enrollment sent to users successfully");
		else
			log("Success toaster is not displayed");
	}

	public void verifySendEnrollmentButtonIsNotDisplayed() {
		log("Verifying send enrollment email button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), false);
	}

	public SendEnrollmentConfirmationPage clickOnSendEnrollmentEmailButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollmentEmail, driver);
		javascriptClick(buttonSendEnrollmentEmail);
		return PageFactory.initElements(driver, SendEnrollmentConfirmationPage.class);
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(labelUsernameValue, driver);
		return labelUsernameValue.getText().trim();
	}

	public void verifyInternalUserAdminViewLabels() {
		log("Verifying admin internal user labels on internal user page");
		verifyInternalUserTitleInViewMode();
		verifyInternalUserSubtitleInViewMode();
		verifyInternalUserInitialNameInViewMode();
		verifyInternalUserStatusAndDateInViewMode();
	}

	public String verifyInternalUserTitleInViewMode() {
		CCUtils.verifyAndWaitForElement(labelTitleAdminView, driver);
		log("Verify Internal User Full name on Internal User Details screen: " + labelTitleAdminView.getText());
		CCUtils.assertEquals(labelTitleAdminView.isDisplayed(), true);
		return labelTitleAdminView.getText();
	}

	public String verifyInternalUserSubtitleInViewMode() {
		CCUtils.verifyAndWaitForElement(labelSubtitleAdminView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelSubtitleAdminView.getText());
		CCUtils.assertEquals(labelSubtitleAdminView.isDisplayed(), true);
		return labelSubtitleAdminView.getText();
	}

	public String verifyInternalUserInitialNameInViewMode() {
		CCUtils.verifyAndWaitForElement(labelIntialNameAdminView, driver);
		log("Internal User intials name on Internal User Details screen: " + labelIntialNameAdminView.getText());
		CCUtils.assertEquals(labelIntialNameAdminView.isDisplayed(), true);
		return labelIntialNameAdminView.getText();
	}

	public List<WebElement> verifyInternalUserStatusAndDateInViewMode() {
		for (int i = 0; i < labelLastLoginDateEnrollmentStatusUserStatusAdminView.size(); i++) {
			log("Admin User details on Internal User Details screen: "
					+ labelLastLoginDateEnrollmentStatusUserStatusAdminView.get(i).getText());
			CCUtils.assertEquals(labelLastLoginDateEnrollmentStatusUserStatusAdminView.isEmpty(), false);
		}
		return labelLastLoginDateEnrollmentStatusUserStatusAdminView;
	}

	public void verifyKebabMenuOptionsForAdminView() {
		for (int i = 0; i < buttonKebabMenuOptionsAdminView.size(); i++)
			log("Veify kebab menu options: " + buttonKebabMenuOptionsAdminView.get(i).getText());
		CCUtils.assertEquals(buttonKebabMenuOptionsAdminView.isEmpty(), false);
	}

	public void verifyActivateOrDeactivateOptionDisplayedToAdminUser() {
		if (labelUserStatusValue.getText().trim().equals("Active")) {
			clickOnKebabMenuButton();
			log("Verify kebab menu display option of Deactivate user");
			CCUtils.assertEquals(kebabDeactiveUserOption.getText().trim(), "Deactivate User");
		} else if (labelUserStatusValue.getText().trim().equals("Inactive")) {
			clickOnKebabMenuButton();
			log("Verify kebab menu display option of activate user");
			CCUtils.assertEquals(kebabActiveUserOption.getText().trim(), "Activate User");
		}
	}

	public void verifyLockOrUnlockOptionDisplayedToAdminUser() {
		if (labelUserStatusValue.getText().trim().equals("Lock")) {
			clickOnKebabMenuButton();
			log("Verify kebab menu display option of Unlock User");
			CCUtils.assertEquals(labelUserStatusUnlock.getText().trim(), "Unlock User");
		} else if (labelUserStatusValue.getText().trim().equals("Unlock")) {
			clickOnKebabMenuButton();
			log("Verify kebab menu display option of lock User");
			CCUtils.assertEquals(labelUserStatusLocked.getText().trim(), "Lock User");
		}
	}

	public void verifySendEnrollmentOptionInKebabMenuToAdminUser() {
		if (labelUserStatusValue.getText().trim().equals("Active")) {
			clickOnKebabMenuButton();
			log("Verify kebab menu display option of Send Enrollment Email");
			CCUtils.assertEquals(buttonSendEnrollmentEmail.getText().trim(), "Send Enrollment Email");
		} else if (labelUserStatusValue.getText().trim().equals("Inactive")
				|| labelUserStatusValue.getText().trim().equals("No roles assigned")
				|| labelUserStatusValue.getText().trim().equals("Locked")) {
			clickOnKebabMenuButton();
			log("Verifying send enrollment email button is not displayed in kebab menu");
			CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), false);
		}

	}
}
