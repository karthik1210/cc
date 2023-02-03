// Copyright 2021-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class InternalUserDetailsPage extends CCMainPage {

	@FindBy(xpath = "//button[@class='mat-menu-trigger menu-button']")
	private WebElement buttonKebabMenu;

	@FindBy(id = "enrollBtn")
	private WebElement buttonSendEnrollmentEmail;

	@FindBy(id = "enrollStatus")
	private WebElement labelEnrollmentStatusValue;

	@FindBy(id = "userStatus")
	private WebElement labelUserStatusValue;

	@FindBy(xpath = "//mat-card-title[@class='mat-card-title pt-1 text-oneline']")
	private WebElement labelUsernameValue;

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

	@FindBy(id = "passwordInput")
	private WebElement labelPassword;

	@FindBy(id = "cancelUser")
	private WebElement buttonCancel;

	@FindBy(id = "saveUser")
	private WebElement buttonSave;

	@FindBy(xpath = "//mat-icon[contains(text(),'arrow_back')]")
	private WebElement buttonBackArrow;

	@FindBy(xpath = "//mat-icon[@ng-reflect-message='User exceeded login attempts']")
	private WebElement labelHoverLockedUserStatus;

	@FindBy(xpath = "//mat-icon[@ng-reflect-ng-class='inactive']")
	private WebElement labelHoverInactiveUserStatus;

	@FindBy(xpath = "//mat-icon[@ng-reflect-ng-class='active'][contains(text(),' check_circle')]")
	private WebElement labelHoverActiveUserStatus;

	@FindBy(xpath = "//h2[contains(text(),'User Roles')]")
	private WebElement labelUserRoleTitle;

	@FindBy(xpath = "//td[contains(text(),'Super Admin')]")
	private WebElement labelSuperAdminRole;

	@FindBy(xpath = "//td[contains(text(),'Support T2')]")
	private WebElement labelSupportT2Role;

	@FindBy(xpath = "//td[contains(text(),'Implementation')]")
	private WebElement labelImplementationRole;

	@FindBy(xpath = "//td[contains(text(),'Support T3')]")
	private WebElement labelSupportT3Role;

	@FindBy(xpath = "//td[contains(text(),'Demo/Sales')]")
	private WebElement labelDemoSalesRole;

	@FindBy(xpath = "//td[contains(text(),'Support T1')]")
	private WebElement labelSupportT1Role;

	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']")
	private List<WebElement> buttonAddAllUserRole;

	@FindBy(xpath = "//button[@class='save-button']")
	private WebElement buttonSaveRole;

	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']")
	private List<WebElement> labelAfterAddAllUserRoles;

	@FindBy(xpath = "//button[@mattooltip='Add Role']")
	private WebElement buttonAddSingleUserRole;

	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']")
	private WebElement labelAfterAddSingleUserRole;

	@FindBy(xpath = " //button[@mattooltip='Delete']")
	private List<WebElement> buttonDeleteAllUserRole;

	@FindBy(xpath = "//button[@mattooltip='Delete']")
	private WebElement buttonDeleteSingleUserRole;

	@FindBy(xpath = "//span[contains(text(),'CANCEL')]")
	private WebElement buttonCancelRole;

	@FindBy(xpath = "//span[@class='m-2']")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[text()='User Status']//following::span/mat-icon[@role='img']")
	private WebElement labelInternalUserStatusValue;

	@FindBy(xpath = "//button[contains(text(),'Lock User')]")
	private WebElement buttonLockUser;

	@FindBy(xpath = "//button[contains(text(),'Deactivate User')]")
	private WebElement buttonDeactivateUser;

	@FindBy(xpath = "//span[contains(text(),'Inactive')]")
	private WebElement inactiveStatus;

	@FindBy(id = "detailSaveBtn")
	private WebElement buttonUnlock;

	@FindBy(className = "locked-label")
	private WebElement labelLockedUser;

	@FindBy(id = "editUser")
	private WebElement edit;

	@FindBy(className = "applyBtn")
	private WebElement deactivateButton;
	
	@FindBy(xpath = "//mat-card-title[contains(text(),'User Roles')]")
	private WebElement UserRolesTitle;

	@FindBy(xpath = "//button[@id='editUserRole']")
	private WebElement UserRolesEdit;

	@FindBy(xpath = "//span[contains(text(),'SAVE')]")
	private WebElement UserRolessave;

	@FindBy(xpath = "//span[contains(text(),'CANCEL')]")
	private WebElement UserRolescancel;

	@FindBy(xpath = "//div[contains(text(),'Solution Admin')]")
	private WebElement UserRolessolutionadmin;

	@FindBy(xpath = "(//button[@mattooltip='Remove Role'])[1]")
	private WebElement UserRolesRemove;

	@FindBy(xpath = "(//button[@mattooltip='Add Role'])[1]")
	private WebElement UserRolesAdd;

	@FindBy(xpath = "//span[contains(text(),'Role has been edited successfully')]")
	private WebElement UserRolesedittostermeassage;

	private String xpathHoverTextMessage = "//div[@id='cdk-describedby-message-container']//following-sibling::div[@id='valueToReplace']";

	private WebElement prepareDynamicXpath(String xpathValue, String substitutionValue) {
		System.out.println(driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue))));
		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public InternalUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	List<String> actualRolesValues = new ArrayList<String>();

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		javascriptClick(buttonKebabMenu);
	}

	public void checkInactiveStatus() {
		CCUtils.assertEquals(inactiveStatus.isDisplayed(), true);
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

	public void verifyInternalUserNameTitleInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelNameTitleSelfView, driver);
		log("Verify Internal User Full name on Internal User Details screen: " + labelNameTitleSelfView.getText());
		CCUtils.assertEquals(labelNameTitleSelfView.isDisplayed(), true);
	}

	public void verifyInternalUserSubtitleInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelSubtitleNameSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelSubtitleNameSelfView.getText());
		CCUtils.assertEquals(labelSubtitleNameSelfView.isDisplayed(), true);
	}

	public void verifyInternalUserNameIntialsInViewMode() {
		CCUtils.verifyAndWaitForElement(labelIntialNameSelfView, driver);
		log("Internal User intials name on Internal User Details screen: " + labelIntialNameSelfView.getText());
		CCUtils.assertEquals(labelIntialNameSelfView.isDisplayed(), true);
	}

	public void verifyInternalUserLastLoginInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelLastLoginSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelLastLoginSelfView.getText());
		CCUtils.assertEquals(labelLastLoginSelfView.isDisplayed(), true);
	}

	public void verifyInternalUserEnrollmentStatusInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelEnrollmentStatusSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelEnrollmentStatusSelfView.getText());
		CCUtils.assertEquals(labelEnrollmentStatusSelfView.isDisplayed(), true);
	}

	public void verifyInternalUserUserStatusInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelUserStatusSelfView, driver);
		log("Internal User sub tiltle on Internal User Details screen: " + labelUserStatusSelfView.getText());
		CCUtils.assertEquals(labelUserStatusSelfView.isDisplayed(), true);
	}

	public void verifyInternalUserLastLoginDateEnrollmentAndUserStatusInSelfViewMode() {
		for (int i = 0; i < labelLastLoginDateEnrollmentStatusUserStatusSelfView.size(); i++) {
			CCUtils.assertEquals(labelLastLoginDateEnrollmentStatusUserStatusSelfView.isEmpty(), false);
		}
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
		CCUtils.assertEquals(labelPassword.getAttribute("disabled"), "true");
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
		labelHoverInactiveUserStatus.getAttribute("ng-reflect-message").toString();
		CCUtils.assertEquals(labelHoverInactiveUserStatus.getText(),
				labelNameTitleSelfView.getText().concat(labelLastLoginSelfView.getText()));
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
		CCUtils.assertEquals(labelHoverLockedUserStatus.getText(), "No roles assigned");
	}

	public void verifyInternalUsersRoleTitle() {
		CCUtils.verifyAndWaitForElement(labelUserRoleTitle, driver);
		CCUtils.assertEquals(labelUserRoleTitle.getText().trim(), "User Roles");
		log("Internal Users role title is displayed");
	}

	public void verifyEditButtonForInternalUsersRole() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditInternalUserRole, driver);
		scrollAndWait(0, 150, 10);
		CCUtils.assertEquals(buttonEditInternalUserRole.getText().trim(), "EDIT");
		log("User role edit button is displayed");
	}

	public void verifyLabelsOfInternalUserEditButton() throws InterruptedException {
		log("Verifying internal user edit labels on internal user details page");
		verifyInternalUserTitleInViewMode();
		verifyInternalUsersRoleTitle();
		verifyEditButtonForInternalUsersRole();
	}

	public void clickUserRoleEditButtonInInternalUserDetailsPage() {
		CCUtils.verifyAndWaitForElement(buttonEditInternalUserRole, driver);
		buttonEditInternalUserRole.click();
		log("User role button is editable");
	}

	public void verifyLabelsOfInternalUserRoles() throws InterruptedException {
		log("Verifying internal user roles labels on internal user details page");
		verifyLabelOfSuperAdminRole();
		verifyLabelOfSupportT2Role();
		verifyLabelOfImplementationRole();
		verifySupportT3RoleLabel();
		verifyLabelDemoSalesRole();
		verifylabelSupportT1RoleRole();
	}

	public void verifyLabelOfSuperAdminRole() {
		CCUtils.verifyAndWaitForElement(labelSuperAdminRole, driver);
		CCUtils.assertEquals(labelSuperAdminRole.getText().trim(), "Super Admin");
		log("Internal Users super admin role label is displayed");
	}

	public void verifyLabelOfSupportT2Role() {
		CCUtils.verifyAndWaitForElement(labelSupportT2Role, driver);
		CCUtils.assertEquals(labelSupportT2Role.getText().trim(), "Support T2");
		log("Internal Users super admin role label is displayed");
	}

	public void verifyLabelOfImplementationRole() {
		CCUtils.verifyAndWaitForElement(labelImplementationRole, driver);
		CCUtils.assertEquals(labelImplementationRole.getText().trim(), "Implementation");
		log("Internal Users super admin role label is displayed");
	}

	public void verifySupportT3RoleLabel() {
		CCUtils.verifyAndWaitForElement(labelSupportT3Role, driver);
		CCUtils.assertEquals(labelSupportT3Role.getText().trim(), "Support T3");
		log("Internal Users super admin role label is displayed");
	}

	public void verifyLabelDemoSalesRole() {
		CCUtils.verifyAndWaitForElement(labelDemoSalesRole, driver);
		CCUtils.assertEquals(labelDemoSalesRole.getText().trim(), "Demo/Sales");
		log("Internal Users super admin role label is displayed");
	}

	public void verifylabelSupportT1RoleRole() {
		CCUtils.verifyAndWaitForElement(labelSupportT1Role, driver);
		CCUtils.assertEquals(labelSupportT1Role.getText().trim(), "Support T1");
		log("Internal Users super admin role label is displayed");
	}

	public void selectAllUsersRolesAddAndSaveFunctionality() throws AWTException {
		CCUtils.verifyAndWaitForListOfElement(buttonAddAllUserRole, driver);
		for (int i = 0; i < buttonAddAllUserRole.size(); i++) {
			CCUtils.scrollPageToEnd(driver);
			buttonAddAllUserRole.get(i).click();
			log("All Internal User roles add buttons are selected" + buttonAddAllUserRole);
		}
	}

	public void clickSaveUserRoleButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveRole, driver);
		buttonSaveRole.click();
		log("Saved button clicked ");
	}

	public void verifySelectedAllUserRoleNamesDisplayed() throws AWTException {
		CCUtils.scrollPageToEnd(driver);
		for (int i = 0; i < labelAfterAddAllUserRoles.size(); i++) {
			actualRolesValues.add(labelAfterAddAllUserRoles.get(i).getText());
		}
		List<String> expectedRoleValues = Arrays.asList("Super Admin", "Support T2", "Implementation", "Support T3",
				"Demo/Sales", "Support T1");
		CCUtils.assertEquals(actualRolesValues, expectedRoleValues);
	}

	public void selectSingleUsersRoleForAddAndSaveFunctionality() throws AWTException {
		CCUtils.scrollPageToEnd(driver);
		CCUtils.verifyAndWaitForElement(buttonAddSingleUserRole, driver);
		buttonAddSingleUserRole.click();
		log("Single Internal User roles add button is selected");
	}

	public void verifyAfterAddAndSaveSingleUserRole() {
		CCUtils.verifyAndWaitForElement(labelAfterAddSingleUserRole, driver);
		log("Verifying added user role name: " + labelAfterAddSingleUserRole.getText());
		CCUtils.assertEquals(labelAfterAddSingleUserRole.isDisplayed(), true);
	}

	public void selectAllUsersRolesDeleteAndSaveFunctionality() throws AWTException {
		CCUtils.verifyAndWaitForListOfElement(buttonDeleteAllUserRole, driver);
		for (int i = 0; i < buttonDeleteAllUserRole.size(); i++) {
			CCUtils.scrollPageToEnd(driver);
			buttonDeleteAllUserRole.get(i).click();
			log("All Internal User roles add buttons are selected" + buttonDeleteAllUserRole);
		}
	}

	public void selectSingleUsersRoleForDeleteAndSaveFunctionality() throws AWTException {
		CCUtils.scrollPageToEnd(driver);
		CCUtils.verifyAndWaitForElement(buttonDeleteSingleUserRole, driver);
		buttonDeleteSingleUserRole.click();
		log("Single Internal User roles delete button is selected");
	}

	public void clickCancelUserRoleButton() {
		CCUtils.verifyAndWaitForElement(buttonCancelRole, driver);
		buttonCancelRole.click();
		log("Saved button clicked ");
	}

	public void verifyNoUserRolesNameDisplayed() {
		for (int i = 0; i < labelAfterAddAllUserRoles.size(); i++)
			log("Veify user roles name: " + labelAfterAddAllUserRoles.get(i).getText());
		CCUtils.assertEquals(labelAfterAddAllUserRoles.isEmpty(), true);
	}

	public void verifyInternalUserDetailsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.isDisplayed(), true);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "User Details");
		log("Internal user details page title is displayed on screen");
	}

	public void verifySelectedUserStatusIsSameOnInternalUserDetailsPage() {
		CCUtils.verifyAndWaitForElement(labelInternalUserStatusValue, driver);
		CCUtils.assertEquals(labelInternalUserStatusValue.isDisplayed(), true);
		log("User status value is present on internal user details page");
	}

	public void verifyLockUserButtonIsDisplayed() {
		log("Verifying lock user button is displayed in kebab menu");
		CCUtils.assertEquals(buttonLockUser.isDisplayed(), true);
	}

	public void verifyLockUsertButtonIsNotDisplayed() {
		log("Verifying lock user button is not displayed in kebab menu");
		List<String> buttonName = null;
		for (int i = 0; i < buttonKebabMenuOptionsAdminView.size(); i++) {
			buttonName = new ArrayList<String>();
			buttonName.add(buttonKebabMenuOptionsAdminView.get(i).getText().trim());
		}
		CCUtils.assertTrue(!buttonName.contains("Lock User"), "Lock User button is present on screen");
	}

	public void clickOnLockUserButton() {
		CCUtils.verifyAndWaitForElement(buttonLockUser, driver);
		log("Click on lock user button");
		javascriptClick(buttonLockUser);
	}

	public void verifyDeactivateUserButtonIsDisplayed() {
		log("Verifying deactivate user button is displayed in kebab menu");
		CCUtils.assertEquals(buttonDeactivateUser.isDisplayed(), true);
	}

	public void verifyActivateUserButtonIsDisplayed() {
		log("Verifying activate user button is displayed in kebab menu");
		CCUtils.assertEquals(kebabActiveUserOption.isDisplayed(), true);
	}

	public void verifyDeactivateUsertButtonIsNotDisplayed() {
		log("Verifying deactivate user button is displayed in kebab menu");
		CCUtils.assertEquals(buttonDeactivateUser.isDisplayed(), true);
	}

	public void verifyDeactivateUsertButtonIsNotEnabled() {
		log("Verifying deactivate user button is enabled in kebab menu");
		CCUtils.assertEquals(!buttonDeactivateUser.isEnabled(), false);
	}

	public DeactivateUserConfirmationPage clickOnDeactivateUserButton() {
		CCUtils.verifyAndWaitForElement(buttonDeactivateUser, driver);
		log("Click on deactivate user button");
		javascriptClick(buttonDeactivateUser);
		return PageFactory.initElements(driver, DeactivateUserConfirmationPage.class);
	}

	public void verifySelectedUserStatusIsSameOnInternalUserDetailsPage(String userStatus) {
		CCUtils.verifyAndWaitForElement(labelInternalUserStatusValue, driver);
		CCUtils.assertTrue(labelInternalUserStatusValue.isDisplayed(),
				"User status value is not present on internal user details page");
		CCUtils.assertEquals(labelInternalUserStatusValue.getText().trim(), userStatus);
	}

	public void verifyUnlockButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonUnlock, driver);
		CCUtils.assertTrue(buttonUnlock.isDisplayed(), "Unlock button is not present on internal user details page");
		CCUtils.assertEquals(buttonUnlock.getText().trim(), "UNLOCK");
		CCUtils.assertTrue(buttonUnlock.isEnabled(), "Unlock button is disabled on internal user details page");
	}

	public void verifyLockedLabelAndIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(labelLockedUser, driver);
		CCUtils.assertTrue(labelLockedUser.isDisplayed(),
				"locked label status is not present on internal user details page");
		CCUtils.assertEquals(labelLockedUser.getText().trim(), "Locked");
		CCUtils.assertEquals(labelInternalUserStatusValue.getText().trim(), "lock");
		CCUtils.mouseHoverOverTheElement(driver, labelInternalUserStatusValue);
		String id = labelInternalUserStatusValue.getAttribute("aria-describedby").trim();
		CCUtils.assertEquals(prepareDynamicXpath(xpathHoverTextMessage, id).getAttribute("innerHTML").trim(),
				"Account manually locked");
	}

	public void verifyUserStatusAfterClickingOnLockUserButton() {
		verifyUnlockButtonIsDisplayed();
		verifyLockedLabelAndIconIsDisplayed();
	}

	public void clickOnUnlockUserButton() {
		CCUtils.verifyAndWaitForElement(buttonUnlock, driver);
		log("Click on unlock user button");
		javascriptClick(buttonUnlock);
	}

	public void verifyEditButtonVisible() {
		CCUtils.verifyAndWaitForElement(buttonEditInternalUser, driver);
		log("verifying if edit button is present on internal users details page");
	}

	public AddNewInternalUsersPage clickoneditbutton() {
		CCUtils.verifyAndWaitForElement(edit, driver);
		edit.click();
		log("Clicked on edit in command center users page");
		return PageFactory.initElements(driver, AddNewInternalUsersPage.class);
	}

	public void clickYesDeactivate() {
		CCUtils.verifyAndWaitForElement(deactivateButton, driver);
		deactivateButton.click();
		log("Clicked on Yes, Deactivate");
	}

	public void verifyUserRolesTitle() {
		CCUtils.verifyAndWaitForElement(UserRolesTitle, driver);
		CCUtils.assertEquals(UserRolesTitle.getText().trim(), "");
		log("User Roles Title is display");
	}

	public void verifyUserRolesEditbutton() {
		CCUtils.verifyAndWaitForElement(UserRolesEdit, driver);
		CCUtils.assertEquals(UserRolesEdit.getText().trim(), "EDIT");
		log("User Roles Edit is display");
	}

	public void clickUserRolesEditbutton() {
		CCUtils.verifyAndWaitForElement(UserRolesEdit, driver);
		log("Click on Edit  button");
		javascriptClick(UserRolesEdit);
	}

	public void verifyUserRoleSaveButton() {
		CCUtils.verifyAndWaitForElement(UserRolessave, driver);
		CCUtils.assertEquals(UserRolessave.getText().trim(), "SAVE");
		log("User Roles save button is display");
	}

	public void verifyUserRoleCancelButton() {
		CCUtils.verifyAndWaitForElement(UserRolescancel, driver);
		CCUtils.assertEquals(UserRolescancel.getText().trim(), "CANCEL");
		log("User Roles Cancel is display");
	}

	public void verifySolutionAdmin() {
		CCUtils.verifyAndWaitForElement(UserRolessolutionadmin, driver);
		CCUtils.assertEquals(UserRolessolutionadmin.getText().trim(), "Solution Admin");
		log("User Roles Cancel is display");
	}

	public void clickToRemoveUserRoles() {
		CCUtils.verifyAndWaitForElement(UserRolesRemove, driver);
		for(int i=0;i<=5; i++) {
		javascriptClick(UserRolesRemove);
		CCUtils.verifyAndWaitForElement(UserRolesRemove, driver);
		}
		log("User is remove the  role");
	}

	public void clickToAddUserRoles() {
		CCUtils.verifyAndWaitForElement(UserRolesAdd, driver);
		for(int i=0;i<=4; i++) {
			javascriptClick(UserRolesAdd);
			CCUtils.verifyAndWaitForElement(UserRolesAdd, driver);
			}
		log("User is add the  role");
	}

	public void clickOnSaveButton() {
		CCUtils.verifyAndWaitForElement(UserRolessave, driver);
		log("User is save the solution admin role");
		javascriptClick(UserRolessave);
	}

	public void clickOnCancel() {
		CCUtils.verifyAndWaitForElement(UserRolescancel, driver);
		log("User is cancel");
		javascriptClick(UserRolescancel);
	}

	public void verifyEditToasterMessage() {
		CCUtils.verifyAndWaitForElement(UserRolesedittostermeassage, driver);
		CCUtils.assertEquals(UserRolesedittostermeassage.getText().trim(), "Role has been edited successfully");
		log("Role has been edited successfully meassage is display");
	}

}
