// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class InternalUserDetailsPage extends CCMainPage {

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

	@FindBy(xpath = "//button[@role='menuitem']")
	private List<WebElement> buttonKebabMenuOptionsAdminView;

	@FindBy(xpath = "//button[contains(text(),'Deactivate User')]")
	private WebElement kebabDeactiveUserOption;

	@FindBy(xpath = "//button[contains(text(),'Activate User')]")
	private WebElement kebabActiveUserOption;

	@FindBy(xpath = "//span[@class='locked']")
	private WebElement labelUserStatusLocked;

	@FindBy(xpath = "//span[@class='unlock']")
	private WebElement labelUserStatusUnlock;

	@FindBy(xpath = "//mat-card-title[@class='mat-card-title pt-1 text-oneline']")
	private WebElement labelNameTitleSelfView;

	@FindBy(xpath = "//mat-card-subtitle[@class='mat-card-subtitle text-oneline mb-0 ng-star-inserted']")
	private WebElement labelSubtitleNameSelfView;

	@FindBy(xpath = "//div[@class='mat-card-avatar mat-fab mat-button-base mat-accent shadow-none mr-3']")
	private WebElement labelIntialNameSelfView;

	@FindBy(xpath = "//div[@class='flex py-3 px-4']")
	private List<WebElement> labelLastLoginDateEnrollmentStatusUserStatusSelfView;

	@FindBy(xpath = "//button[@role='menuitem']")
	private List<WebElement> buttonKebabMenuOptionsSelfView;

	@FindBy(xpath = "//span[contains(text(),'Last Login')]")
	private WebElement labelLastLoginSelfView;

	@FindBy(xpath = "//span[contains(text(),'Enrollment Status')]")
	private WebElement labelEnrollmentStatusSelfView;

	@FindBy(xpath = "//span[contains(text(),'User Status')]")
	private WebElement labelUserStatusSelfView;

	@FindBy(id = "editUser")
	private WebElement buttonEditInternalUser;

	@FindBy(xpath = "//h2[contains(text(),'User Roles')]//following::div[contains(text(),'EDIT')]")
	private WebElement buttonEditInternalUserRole;

	@FindBy(xpath = "//h2[contains(text(),'User Roles')]//preceding::div[contains(text(),'EDIT')]")
	private WebElement buttonEditInternalUserDemographicDetails;

	@FindBy(id = "addFirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "addLastNameInput")
	private WebElement textboxLasttName;

	@FindBy(id = "addEmailInput")
	private WebElement textboxEmail;

	@FindBy(id = "addUserNameInput")
	private WebElement textboxUserName;

	@FindBy(id = "mobileInput")
	private WebElement textboxMobileNumber;

	@FindBy(xpath = "//span[contains(text(),'Password')]")
	private WebElement labelPassword;

	@FindBy(xpath = "//span[contains(text(),'CANCEL')]")
	private WebElement buttonCancel;

	@FindBy(xpath = "//button[contains(text(),'SAVE')]")
	private WebElement buttonSave;

	@FindBy(xpath = "//mat-icon[contains(text(),'arrow_back')]")
	private WebElement buttonBackArrow;

	@FindBy(xpath = "//mat-icon[@ng-reflect-message='User exceeded login attempts']")
	private WebElement labelHoverLockedUserStatus;

	@FindBy(xpath = "//mat-icon[@ng-reflect-ng-class='inactive']")
	private WebElement labelHoverInactiveUserStatus;

	@FindBy(xpath = "//mat-icon[@ng-reflect-ng-class='active'][contains(text(),' check_circle')]")
	private WebElement labelHoverActiveUserStatus;

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

	public String verifyInternalUserNameTitleInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelNameTitleSelfView, driver);
		log("Verify Internal User Full name on Internal User Details screen: " + labelNameTitleSelfView.getText());
		CCUtils.assertEquals(labelNameTitleSelfView.isDisplayed(), true);
		return labelNameTitleSelfView.getText();
	}

	public String verifyInternalUserSubtitleInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelSubtitleNameSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelSubtitleNameSelfView.getText());
		CCUtils.assertEquals(labelSubtitleNameSelfView.isDisplayed(), true);
		return labelSubtitleNameSelfView.getText();
	}

	public String verifyInternalUserNameIntialsInViewMode() {
		CCUtils.verifyAndWaitForElement(labelIntialNameSelfView, driver);
		log("Internal User intials name on Internal User Details screen: " + labelIntialNameSelfView.getText());
		CCUtils.assertEquals(labelIntialNameSelfView.isDisplayed(), true);
		return labelIntialNameSelfView.getText();
	}

	public String verifyInternalUserLastLoginInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelLastLoginSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelLastLoginSelfView.getText());
		CCUtils.assertEquals(labelLastLoginSelfView.isDisplayed(), true);
		return labelLastLoginSelfView.getText();
	}

	public String verifyInternalUserEnrollmentStatusInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelEnrollmentStatusSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelEnrollmentStatusSelfView.getText());
		CCUtils.assertEquals(labelEnrollmentStatusSelfView.isDisplayed(), true);
		return labelEnrollmentStatusSelfView.getText();
	}

	public String verifyInternalUserUserStatusInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelUserStatusSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelUserStatusSelfView.getText());
		CCUtils.assertEquals(labelUserStatusSelfView.isDisplayed(), true);
		return labelUserStatusSelfView.getText();
	}

	public List<WebElement> verifyInternalUserLastLoginDateEnrollmentAndUserStatusInSelfViewMode() {
		for (int i = 0; i < labelLastLoginDateEnrollmentStatusUserStatusSelfView.size(); i++) {
			log("Admin User details on Internal User Details screen: "
					+ labelLastLoginDateEnrollmentStatusUserStatusSelfView.get(i).getText());
			CCUtils.assertEquals(labelLastLoginDateEnrollmentStatusUserStatusSelfView.isEmpty(), false);
		}
		return labelLastLoginDateEnrollmentStatusUserStatusSelfView;
	}

	public void verifyInternalUserSelfViewLabels() {
		CCUtils.verifyAndWaitForElement(labelNameTitleSelfView, driver);
		log("Verifying self view internal user labels on internal user page");
		verifyInternalUserNameTitleInSelfViewMode();
		verifyInternalUserSubtitleInSelfViewMode();
		verifyInternalUserNameIntialsInViewMode();
		verifyInternalUserLastLoginInSelfViewMode();
		verifyInternalUserEnrollmentStatusInSelfViewMode();
		verifyInternalUserUserStatusInSelfViewMode();
	}

	public void verifyInternalUserKebabMenuOptionsDisplayedForSelfView() {
		for (int i = 0; i < buttonKebabMenuOptionsSelfView.size(); i++)
			log("Veify kebab menu options: " + buttonKebabMenuOptionsSelfView.get(i).getText());
		CCUtils.assertEquals(buttonKebabMenuOptionsSelfView.isEmpty(), false);
	}

	public void verifyActivateOrDeactivateOptionDisabledToSelfUser() {
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

	public void verifySendEnrollmentOptionInKebabMenuToSelfUser() {
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

	public void verifyLockAndUnlockOptionDisplayedToSelfUser() {
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

	public void clickEditInInternalUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditInternalUser, driver);
		javascriptClick(buttonEditInternalUser);
		log("Clicked on Edit button");
	}

	public void verifyUserRoleNonEditableInInternalUserDetailsPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditInternalUserRole, driver);
		buttonEditInternalUserRole.getAttribute("readonly").equals(true);
		log("User role is not editable");
	}

	public void verifykEditDemographicDetailsInInternalUserDetailsPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditInternalUserDemographicDetails, driver);
		javascriptClick(buttonEditInternalUserDemographicDetails);
		log("Clicked on Edit button");
	}

	public void enterFirstName(String input) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.clear();
		textboxFirstName.sendKeys(input);
		log("Entered input for Firstname : " + input);
	}

	public void enterLastName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLasttName, driver);
		textboxLasttName.clear();
		textboxLasttName.sendKeys(input);
		log("Entered input for Last name : " + input);
	}

	public void enterUserName(String input) {
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		textboxUserName.clear();
		textboxUserName.sendKeys(input);
		log("Entered input for username : " + input);
	}

	public void enterEmail(String input) {
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		textboxEmail.clear();
		textboxEmail.sendKeys(input);
		log("Entered input for email : " + input);
	}

	public void enterMobileNumber(String input) {
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.clear();
		textboxMobileNumber.sendKeys(input);
		log("Entered input for mobile number : " + input);
	}

	public void verifyPasswordUneditable() {
		CCUtils.verifyAndWaitForElement(labelPassword, driver);
		labelPassword.getAttribute("readonly").equals(true);
		log("Password is not editable");
	}

	public void clickCancel() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel");
	}

	public void clickSave() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		buttonSave.click();
		log("Clicked on Save");
	}

	public void clickBackArrowOnInternalUserDetailsPage() {
		CCUtils.verifyAndWaitForElement(buttonBackArrow, driver);
		buttonBackArrow.click();
		log("Click back button on internal user details page");
	}

	public void verifyHoverOverMessageOnLockedUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverLockedUserStatus, driver);
		labelHoverLockedUserStatus.getAttribute("ng-reflect-message");
		CCUtils.assertEquals(labelHoverLockedUserStatus.getText(), "User exceeded login attempts");
	}

	public void verifyHoverOverMessageOnInactiveUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverInactiveUserStatus, driver);
		String date = "Name(" + CCUtils.dateformatter() + ")";
		CCUtils.assertEquals(labelHoverInactiveUserStatus, date);
		labelHoverInactiveUserStatus.getAttribute("ng-reflect-message");
	}

	public void verifyHoverOverMessageOnActiveUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverActiveUserStatus, driver);
		labelHoverActiveUserStatus.getAttribute("ng-reflect-message");
		String date = CCUtils.dateformatter();
		CCUtils.assertEquals(labelHoverActiveUserStatus.getText(), date);
	}

	public void verifyHoverOverMessageOnNoRoleAssignedUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverLockedUserStatus, driver);
		labelHoverLockedUserStatus.getAttribute("ng-reflect-message");
	}
}
