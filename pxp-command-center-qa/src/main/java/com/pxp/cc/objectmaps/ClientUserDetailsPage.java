// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class ClientUserDetailsPage extends CCMainPage {

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

	@FindBy(id = "clientEdit")
	private WebElement buttonEditClientUser;

	@FindBy(id = "editCancel")
	private WebElement buttonCancel;

	@FindBy(id = "saveClient")
	private WebElement buttonSave;

	@FindBy(id = "password")
	private WebElement labelPassword;

	@FindBy(xpath = "//mat-list-item[@role='listitem']")
	private List<WebElement> listOfAssociatedEntities;

	@FindBy(id = "addUserRole")
	private WebElement buttonAdd;

	@FindBy(xpath = "//mat-card-title[@id='listUserRoles']")
	private List<WebElement> listOfUserRoles;

	@FindBy(id = "editicon")
	private WebElement buttonEditIcon;

	@FindBy(id = "DeleteUserRole")
	private WebElement buttonDeleteUserRole;
	
	@FindBy(xpath = "//button[contains(text(),'Lock User')]")
	private WebElement buttonLockUser;
	
	@FindBy(id = "detailSaveBtn")
	private WebElement buttonUnlockUser;

	public ClientUserDetailsPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		log("Click on kebab menu icon on client user details page");
		javascriptClick(buttonKebabMenu);
	}

	public void verifySendEnrollmentButtonIsNotDisplayed() {
		log("Verifying send enrollment email button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), false);
	}

	public void verifySelectedUserStatusIsSameOnClientUserDetailsPage(String userStatus) {
		CCUtils.verifyAndWaitForElement(labelUserStatusValue, driver);
		if (labelUserStatusValue.isDisplayed()) {
			CCUtils.assertEquals(labelUserStatusValue.getText().trim(), userStatus);
		} else {
			log("User status value is not present on client user details page");
		}
	}

	public void verifySendEnrollmentButtonIsDisplayed() {
		log("Verifying send enrollment email button is isplayed in kebab menu");
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isDisplayed(), true);
	}

	public String getUserName() {
		CCUtils.verifyAndWaitForElement(labelUsernameValue, driver);
		return labelUsernameValue.getText().trim();
	}

	public SendEnrollmentConfirmationPage clickOnSendEnrollmentEmailButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollmentEmail, driver);
		log("Click on send enrollment email button");
		javascriptClick(buttonSendEnrollmentEmail);
		return PageFactory.initElements(driver, SendEnrollmentConfirmationPage.class);
	}

	public void verifyClientUserDetailsPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed()) {
			String title = labelPageTitle.getText().trim().replace("arrow_back", "");
			CCUtils.assertEquals(title.trim(), "Client User Details");
		} else {
			log("Client user details page title is not displayed on screen");
		}
	}

	public void clickEditInClientUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditClientUser, driver);
		javascriptClick(buttonEditClientUser);
	}

	public void verifyPasswordUneditable() {
		CCUtils.verifyAndWaitForElement(labelPassword, driver);
		CCUtils.assertEquals(labelPassword.getAttribute("disabled"), "true");
		log("Password is not editable");
	}

	public void clickCancel() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
	}

	public void clickSave() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		buttonSave.click();
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		if (buttonCancel.isDisplayed()) {
			CCUtils.assertEquals(buttonCancel.getText().trim(), "CANCEL");
		} else {
			logStep("Cancel button is not present on client user details page");
		}
	}

	public void verifySaveButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		if (buttonSave.isDisplayed()) {
			CCUtils.assertEquals(buttonSave.getText().trim(), "SAVE");
		} else {
			logStep("Save button is not present on client user details page");
		}
	}

	public void selectEntity() {
		CCUtils.verifyAndWaitForElement(listOfAssociatedEntities.get(0), driver);
		javascriptClick(listOfAssociatedEntities.get(0));
		log("Clicked on the first entity");
	}

	public EditUserRolesAndPermissionsPage selectUserRoleAndClickOnEditButton(String userRoleText) {
		for (WebElement userRole : listOfUserRoles) {
			CCUtils.verifyAndWaitForElement(userRole, driver);
			if (userRole.getText().contains(userRoleText)) {
				buttonEditIcon.click();
			}
			break;
		}
		log("Clicked on the edit button of user role : " + userRoleText);
		return PageFactory.initElements(driver, EditUserRolesAndPermissionsPage.class);
	}

	public AddUserRolesAndPermissionsPage clickOnAddButton() {
		CCUtils.verifyAndWaitForElement(buttonAdd, driver);
		buttonAdd.click();
		log("Clicked on add button on the client user details page");
		return PageFactory.initElements(driver, AddUserRolesAndPermissionsPage.class);
	}

	public ClientUserDeleteUserRolesAndPermissionsPage clickOnDeleteUserRoleButton() {
		CCUtils.verifyAndWaitForElement(buttonDeleteUserRole, driver);
		javascriptClick(buttonDeleteUserRole);
		log("Clicked on delete user role button on the client user details page");
		return PageFactory.initElements(driver, ClientUserDeleteUserRolesAndPermissionsPage.class);
	}
	
	public void verifyLockUserButtonIsDisplayed() {
		log("Verifying lock user button is displayed in kebab menu");
		CCUtils.assertEquals(buttonLockUser.isDisplayed(), true);
	}
	
	public void verifyLockUserButtonIsNotDisplayed() {
		log("Verifying lock user button is not displayed in kebab menu");
		CCUtils.assertEquals(buttonLockUser.isDisplayed(), false);
	}
	
	public LockUserConfirmationPage clickOnLockUserButton() {
		CCUtils.verifyAndWaitForElement(buttonLockUser, driver);
		javascriptClick(buttonLockUser);
		return PageFactory.initElements(driver, LockUserConfirmationPage.class);
	}
	
	public void verifyUnlockButtonIsDispalyed() {
		CCUtils.verifyAndWaitForElement(buttonUnlockUser, driver); 
		CCUtils.assertTrue(buttonUnlockUser.isDisplayed(), "Unlock button is not displayed on client user details page");
	}
	
	public void clickOnUnlockButton() {
		CCUtils.verifyAndWaitForElement(buttonUnlockUser, driver);
		javascriptClick(buttonUnlockUser);
		log("Clicked on unlock user button on the client user details page");
	}

}
