// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class ClientUserDetailsPage extends CCMainPage {

	@FindBy(xpath = "//span[@class='m-2']")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//mat-icon[@mattooltip='More Options']")
	private WebElement buttonKebabMenu;

	@FindBy(id = "enrollBtn")
	private WebElement buttonSendEnrollmentEmail;

	@FindBy(id = "enrollStatus")
	private WebElement labelEnrollmentStatusValue;

	@FindBy(xpath = "//span[text()='User Status']//following::span/mat-icon[@role='img']")
	private WebElement labelUserStatusValue;

	@FindBy(xpath = "//mat-card-title[@id='userName']")
	private WebElement labelUsernameValue;

	@FindBy(id = "editUser")
	private WebElement buttonEditClientUser;

	@FindBy(id = "cancelUser")
	private WebElement buttonCancel;

	@FindBy(id = "saveUser")
	private WebElement buttonSave;

	@FindBy(id = "passwordInput")
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

	@FindBy(xpath = "//div[@class='editbutton']")
	private WebElement buttonManage;

	@FindBy(xpath = "//div[@class='header px-4 mb-2']")
	private List<WebElement> systemHeaderAssociatedEntities;

	@FindBy(xpath = "//h2[text()='Associated Entities']")
	private WebElement headerAssociatedEntities;

	@FindBy(id = "searchEntities")
	private WebElement textBoxSearch;

	@FindBy(className = "role-name")
	private WebElement textRoleName;

	@FindBy(xpath = "//div[@class='role-container']")
	private List<WebElement> userRoles;

	@FindBy(xpath = "//span[@class='location-name ng-star-inserted']")
	private List<WebElement> listOfLocations;

	@FindBy(xpath = "//mat-card-title[@class='mat-card-title pt-1 text-oneline']")
	private WebElement labelNameTitleSelfView;

	@FindBy(xpath = "//mat-card-subtitle[@class='mat-card-subtitle text-oneline mb-0']")
	private WebElement labelSubtitleNameSelfView;

	@FindBy(xpath = "//div[@class='mat-card-avatar mat-fab mat-button-base mat-accent shadow-none mr-3']")
	private WebElement labelIntialNameSelfView;

	@FindBy(xpath = "//span[contains(text(),'Last Login')]")
	private WebElement labelLastLoginSelfView;

	@FindBy(xpath = "//span[contains(text(),'Enrollment Status')]")
	private WebElement labelEnrollmentStatusSelfView;

	@FindBy(xpath = "//Span[contains(text(),'Connected Resource')]")
	private WebElement labelUserConnectedResourceSelfView;

	@FindBy(xpath = "//span[contains(text(),'User Status')]")
	private WebElement labelUserStatusSelfView;

	@FindBy(xpath = "//div[@class='flex py-3 px-4']")
	private List<WebElement> labelLastLoginDateEnrollmentStatusUserStatusSelfView;

	@FindBy(xpath = "//mat-icon[contains(text(),'arrow_back')]")
	private WebElement buttonBackArrow;

	@FindBy(xpath = "//button[@role='menuitem']")
	private List<WebElement> buttonKebabMenuList;

	@FindBy(xpath = "//button[contains(text(),'Activate User')]")
	private WebElement kebabActiveUserOption;

	@FindBy(xpath = "//button[contains(text(),'Deactivate User')]")
	private WebElement kebabDeactiveUserOption;

	@FindBy(xpath = "//span[@class='unlock']")
	private WebElement labelUserStatusUnlock;

	@FindBy(xpath = "//span[@class='locked']")
	private WebElement labelUserStatusLocked;

	@FindBy(id = "connected-resource")
	private WebElement linkConnectedResource;

	@FindBy(xpath = "//mat-icon[@ng-reflect-message='User exceeded login attempts']")
	private WebElement labelHoverLockedUserStatus;

	@FindBy(xpath = "//mat-icon[@ng-reflect-ng-class='active'][contains(text(),' check_circle')]")
	private WebElement labelHoverActiveUserStatus;

	@FindBy(xpath = "//mat-icon[@ng-reflect-ng-class='inactive']")
	private WebElement labelHoverInactiveUserStatus;

	@FindBy(xpath = "//span[text()='Connected Resource']")
	private WebElement labelConnectedResource;

	@FindBy(id = "non-connected")
	private WebElement buttonNoneConnect;

	@FindBy(xpath = "//mat-card-title[@class='mat-card-title pt-1 text-oneline']")
	private WebElement userFirstLastName;

	@FindBy(xpath = "//mat-card-subtitle[@class='mat-card-subtitle text-oneline mb-0']")
	private WebElement clientNameOfUser;

	@FindBy(id = "non-connected")
	private WebElement textFirstName;

	@FindBy(id = "non-connected")
	private WebElement textLastName;

	@FindBy(id = "non-connected")
	private WebElement textEmailId;

	@FindBy(id = "detailSaveBtn")
	private WebElement buttonUnlock;

	@FindBy(className = "locked-label")
	private WebElement labelLockedUser;

	private String xpathHoverTextMessage = "//div[@id='cdk-describedby-message-container']//following-sibling::div[@id='valueToReplace']";

	private WebElement prepareDynamicXpath(String xpathValue, String substitutionValue) {
		System.out.println(driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue))));
		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

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
		CCUtils.assertTrue(labelUserStatusValue.isDisplayed(),
				"User status value is not present on client user details page");
		CCUtils.assertEquals(labelUserStatusValue.getText().trim(), userStatus);
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
		CCUtils.assertTrue(labelPageTitle.isDisplayed(), "Client user details page title is not displayed on screen");
		String title = labelPageTitle.getText().trim();
		CCUtils.assertEquals(title.trim(), "User Details");
		CCUtils.assertEquals(labelPageTitle.isDisplayed(), true);
		log("Client user details page title is displayed on screen");
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
		javascriptClick(buttonSave);
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		if (buttonCancel.isDisplayed() == true) {
			CCUtils.assertEquals(buttonCancel.getText().trim(), "CANCEL");
		} else {
			logStep("Cancel button is not present on client user details page");
		}
	}

	public void verifySaveButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonSave, driver);
		if (buttonSave.isDisplayed() == true) {
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
		List<String> buttonName = null;
		for (int i = 0; i < buttonKebabMenuList.size(); i++) {
			buttonName = new ArrayList<String>();
			buttonName.add(buttonKebabMenuList.get(i).getText().trim());
		}
		CCUtils.assertTrue(!buttonName.contains("Lock User"), "Lock User button is present on screen");
	}

	public void clickOnLockUserButton() {
		CCUtils.verifyAndWaitForElement(buttonLockUser, driver);
		javascriptClick(buttonLockUser);
	}

	public void verifyUnlockButtonIsDispalyed() {
		CCUtils.verifyAndWaitForElement(buttonUnlockUser, driver);
		CCUtils.assertTrue(buttonUnlockUser.isDisplayed(),
				"Unlock button is not displayed on client user details page");
	}

	public void clickOnUnlockButton() {
		CCUtils.verifyAndWaitForElement(buttonUnlockUser, driver);
		javascriptClick(buttonUnlockUser);
		log("Clicked on unlock user button on the client user details page");
	}

	public ManageAssociatedEntitiesPage clickOnManageButton() {
		CCUtils.verifyAndWaitForElement(buttonManage, driver);
		buttonManage.click();
		log("Clicked on Manage button on the client user details page");
		return PageFactory.initElements(driver, ManageAssociatedEntitiesPage.class);
	}

	public List<String> listOfAssociatedEntities() {
		List<String> list = new ArrayList<String>();
		for (WebElement element : listOfAssociatedEntities) {
			CCUtils.verifyAndWaitForElement(element, driver);
			list.add(element.getText().trim());
		}
		return list;
	}

	public List<String> listOfSystemAssociatedEntities() {
		List<String> list = new ArrayList<String>();
		for (WebElement element : systemHeaderAssociatedEntities) {
			CCUtils.verifyAndWaitForElement(element, driver);
			list.add(element.getText().trim());
		}
		return list;
	}

	public void verifySelectedEntitiesInManagePopupSameAsAssociatedEntities(String managePopupSelectedEntities) {
		List<String> listOfAssociatedEntities = new ArrayList<String>();
		listOfAssociatedEntities.addAll(listOfAssociatedEntities());
		listOfAssociatedEntities.addAll(listOfSystemAssociatedEntities());
		for (int i = 0; i < listOfAssociatedEntities.size(); i++) {
			CCUtils.assertTrue(managePopupSelectedEntities.contains(listOfAssociatedEntities.get(i)),
					"Fetched entities from manage popup are different than associated entities container");
		}
	}

	public void verifyAssociateEntitiesTitle() {
		CCUtils.verifyAndWaitForElement(headerAssociatedEntities, driver);
		if (headerAssociatedEntities.isDisplayed() == true) {
			String title = headerAssociatedEntities.getText().trim();
			CCUtils.assertEquals(title.trim(), "Associated Entities");
		} else {
			log("Associated Entities header is not displayed on client users details page");
		}
	}

	public void verifyManageButtonPresentOnAssociatedEntitiesTab() {
		CCUtils.verifyAndWaitForElement(buttonManage, driver);
		if (buttonManage.isDisplayed() == true) {
			String title = buttonManage.getText().trim();
			CCUtils.assertEquals(title.trim(), "MANAGE");
		} else {
			log("Manage button is not displayed on client users details page");
		}
	}

	public void verifySearchTextbox() {
		CCUtils.verifyAndWaitForElement(textBoxSearch, driver);
		log("verified the presence of search textbox");
		CCUtils.assertTrue(textBoxSearch.isDisplayed(),
				"Search textbox is not displayed in associated entities container on client users details page");
		log("Verifying Search placeholder is displayed in search textbox");
		CCUtils.assertEquals(textBoxSearch.getAttribute("placeholder").trim(), "Search");
		log("Box text for search textbox is: " + textBoxSearch.getAttribute("placeholder").trim());
	}

	public void searchEntity(String enitity) {
		verifySearchTextbox();
		textBoxSearch.sendKeys(enitity);
		textBoxSearch.sendKeys(Keys.ENTER);
		log("Searched text " + enitity + "in search textbox");
	}

	public void verifySearchResultDisplayed(String searchData) {
		for (WebElement element : listOfAssociatedEntities) {
			CCUtils.verifyAndWaitForElement(element, driver);
			if (element.getText().contains("No search results found")) {
				log("entity " + searchData + " does not found.");
			} else if (element.getText().contains(searchData)) {
				log("Entity" + searchData + " found.");
			}
		}
	}

	public void clickDesiredAssociatedEntity(String associatedEntity) {
		for (WebElement ele : listOfAssociatedEntities) {
			CCUtils.verifyAndWaitForElement(ele, driver);
			CCUtils.assertTrue((ele.getText().trim().contains(associatedEntity)),
					"Verifying desired associated entity found on Associated Entities container");
			ele.click();
			log("clicked to desired entity.");
		}
	}

	public void displayAssociatedEntityUserRoleAndLocation() {
		for (WebElement role : userRoles) {
			CCUtils.verifyAndWaitForElement(role, driver);
			log("User role " + role.getText() + " is present");
			if ((role.getText().trim().equals("Client - Super Admin"))
					|| (role.getText().trim().equals("Client - Staff(View Only)"))) {
				for (WebElement location : listOfLocations) {
					CCUtils.verifyAndWaitForElement(location, driver);
				}
				log("corresponding locations are present");
			}
		}
	}

	public void verifyClientUserSelfViewLabels() {
		CCUtils.verifyAndWaitForElement(labelNameTitleSelfView, driver);
		log("Verifying self view client user labels on client user page");
		verifyClientUserFullNameInSelfViewMode();
		verifyClientUserSubtitleInSelfViewMode();
		verifyClientUserNameIntialsInViewMode();
		verifyClientUserLastLoginInSelfViewMode();
		verifyClientUserEnrollmentStatusInSelfViewMode();
		verifyClientUserConnectedResourceLinkInSelfViewMode();
		verifyClientUserStatusInSelfViewMode();
	}

	public void verifyClientUserFullNameInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelNameTitleSelfView, driver);
		CCUtils.assertTrue(labelNameTitleSelfView.isDisplayed(),
				"Client full name label is not displayed on client user details page");
		log("Client User Full name on Client User Details screen: " + labelNameTitleSelfView.getText().trim());
	}

	public void verifyClientUserSubtitleInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelSubtitleNameSelfView, driver);
		CCUtils.assertTrue(labelSubtitleNameSelfView.isDisplayed(),
				"Client subtitle label is not displayed on client user details page");
		log("Client User sub tiltle on Client User Details screen: " + labelSubtitleNameSelfView.getText());
	}

	public void verifyClientUserNameIntialsInViewMode() {
		CCUtils.verifyAndWaitForElement(labelIntialNameSelfView, driver);
		CCUtils.assertTrue(labelIntialNameSelfView.isDisplayed(),
				"Client intials label is not displayed on client user details page");
		log("Client User intials name on Client User Details screen: " + labelIntialNameSelfView.getText());
	}

	public void verifyClientUserLastLoginInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelLastLoginSelfView, driver);
		CCUtils.assertTrue(labelLastLoginSelfView.isDisplayed(),
				"Client last login date label is not displayed on client user details page");
		log("Client User last login date on Client User Details screen: " + labelLastLoginSelfView.getText());
	}

	public void verifyClientUserEnrollmentStatusInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelEnrollmentStatusSelfView, driver);
		CCUtils.assertTrue(labelEnrollmentStatusSelfView.isDisplayed(),
				"Client enrollment status label is not displayed on client user details page");
		log("Client User enrollment status on Client User Details screen: " + labelEnrollmentStatusSelfView.getText());
	}

	public void verifyClientUserConnectedResourceLinkInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelUserConnectedResourceSelfView, driver);
		CCUtils.assertTrue(labelUserConnectedResourceSelfView.isDisplayed(),
				"Client connected resource link label is not displayed on client user details page");
		log("Client User connected resource on Client User Details screen: "
				+ labelUserConnectedResourceSelfView.getText());
	}

	public void verifyClientUserStatusInSelfViewMode() {
		CCUtils.verifyAndWaitForElement(labelUserStatusSelfView, driver);
		CCUtils.assertTrue(labelUserStatusSelfView.isDisplayed(),
				"Client user status label is not displayed on client user details page");
		log("Client User status on Client User Details screen: " + labelUserStatusSelfView.getText());
	}

	public void verifyClientLastLoginDateEnrollmentAndStatusValuesAreNotBlankInSelfViewMode() {
		for (int i = 0; i < labelLastLoginDateEnrollmentStatusUserStatusSelfView.size(); i++) {
			CCUtils.assertEquals(labelLastLoginDateEnrollmentStatusUserStatusSelfView.isEmpty(), false);
		}
	}

	public void clickBackArrowOnClientUserDetailsPage() {
		CCUtils.verifyAndWaitForElement(buttonBackArrow, driver);
		buttonBackArrow.click();
		log("Clicked back button on client user details page");
	}

	public void verifyClientUserKebabMenuOptionsDisplayedForSelfView() {
		for (int i = 0; i < buttonKebabMenuList.size(); i++)
			log("Veify kebab menu options: " + buttonKebabMenuList.get(i).getText());
		CCUtils.assertEquals(buttonKebabMenuList.isEmpty(), false);
	}

	public void verifyActivateOrDeactivateOptionDisabledToSelfUser() {
		if (labelUserStatusValue.getText().trim().equals("Active")) {
			clickOnKebabMenuButton();
			CCUtils.assertEquals(kebabDeactiveUserOption.isEnabled(), false);
		} else if (labelUserStatusValue.getText().trim().equals("Inactive")) {
			clickOnKebabMenuButton();
			CCUtils.assertEquals(kebabActiveUserOption.isEnabled(), false);
		}
	}

	public void verifySendEnrollmentOptionDisabledInKebabMenuToSelfUser() {
		clickOnKebabMenuButton();
		CCUtils.assertEquals(buttonSendEnrollmentEmail.isEnabled(), false);
	}

	public void verifyLockAndUnlockOptionDisabledToSelfUser() {
		if (labelUserStatusValue.getText().trim().equals("Lock")) {
			clickOnKebabMenuButton();
			CCUtils.assertEquals(labelUserStatusUnlock.isEnabled(), false);
		} else if (labelUserStatusValue.getText().trim().equals("Unlock")) {
			clickOnKebabMenuButton();
			CCUtils.assertEquals(labelUserStatusLocked.isEnabled(), false);
		}
	}

	public void verifyConnectedResourceLinkIsDisplayed() {
		log("Verifying connected resource link is displayed on client user details page");
		CCUtils.assertEquals(linkConnectedResource.isDisplayed(), true);
	}

	public void verifyHoverOverMessageOnLockedUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverLockedUserStatus, driver);
		CCUtils.assertEquals(labelHoverLockedUserStatus.getAttribute("ng-reflect-message").toString().trim(),
				"User exceeded login attempts");
	}

	public void verifyHoverOverMessageOnActiveUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverActiveUserStatus, driver);
		String date = CCUtils.dateformatter();
		CCUtils.assertEquals(labelHoverActiveUserStatus.getAttribute("ng-reflect-message").toString().trim(), date);
	}

	public void verifyHoverOverMessageOnInactiveUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverInactiveUserStatus, driver);
		CCUtils.assertEquals(labelHoverInactiveUserStatus.getAttribute("ng-reflect-message").toString().trim(),
				labelNameTitleSelfView.getText().concat(labelLastLoginSelfView.getText()));
	}

	public void verifyHoverOverMessageOnNoRoleAssignedUserStatus() {
		CCUtils.verifyAndWaitForElement(labelHoverLockedUserStatus, driver);
		CCUtils.assertEquals(labelHoverLockedUserStatus.getAttribute("ng-reflect-message").toString().trim(),
				"No roles assigned");
	}

	public void verifyConnectedResourceValueIsNoneConnect() {
		CCUtils.verifyAndWaitForElement(labelConnectedResource, driver);
		CCUtils.verifyAndWaitForElement(buttonNoneConnect, driver);
	}

	public ClientUserNoneConnectPage clickNoneConnectButton() {
		CCUtils.verifyAndWaitForElement(buttonNoneConnect, driver);
		buttonNoneConnect.click();
		log("clicked on None-connect button");
		return PageFactory.initElements(driver, ClientUserNoneConnectPage.class);
	}

	public String getClientName() {
		CCUtils.verifyAndWaitForElement(clientNameOfUser, driver);
		return clientNameOfUser.getText().toString().trim();
	}

	public void verifyClientFirstNameOverridden(String searchData) {
		CCUtils.verifyAndWaitForElement(textFirstName, driver);
		if (searchData.contains(textFirstName.getText().toString().trim()) == true) {
			CCUtils.assertTrue(true, "Verifying the connected resource first name is overridden");
			log("connected resource's first name is overridden in client user's first name field");
		}
	}

	public void verifyClientLastNameOverridden(String searchData) {
		CCUtils.verifyAndWaitForElement(textLastName, driver);
		if (searchData.contains(textLastName.getText().toString().trim()) == true) {
			CCUtils.assertTrue(true, "Verifying the connected resource last name is overridden");
			log("connected resource's last name is overridden in client user's last name field");
		}
	}

	public void verifyClientEmailIdOverridden(String searchData) {
		CCUtils.verifyAndWaitForElement(textEmailId, driver);
		if (searchData.contains(textEmailId.getText().toString().trim()) == true) {
			CCUtils.assertTrue(true, "Verifying the connected resource email Id is overridden");
			log("connected resource's email id is overridden in client user's email id field");
		}
	}

	public void verifyClientFirstNameNotOverridden(String searchData) {
		CCUtils.verifyAndWaitForElement(textFirstName, driver);
		if (searchData.contains(textFirstName.getText().toString().trim()) == false) {
			CCUtils.assertTrue(true, "Verifying the connected resource first name is not overridden");
			log("connected resource's first name is not overridden in client user's first name field");
		}
	}

	public void verifyClientLastNameNotOverridden(String searchData) {
		CCUtils.verifyAndWaitForElement(textLastName, driver);
		if (searchData.contains(textLastName.getText().toString().trim()) == false) {
			CCUtils.assertTrue(true, "Verifying the connected resource last name is not overridden");
			log("connected resource's last name is not overridden in client user's last name field");
		}
	}

	public void verifyClientEmailIdNotOverridden(String searchData) {
		CCUtils.verifyAndWaitForElement(textEmailId, driver);
		if (searchData.contains(textEmailId.getText().toString().trim()) == false) {
			CCUtils.assertTrue(true, "Verifying the connected resource email Id is not overridden");
			log("connected resource's email id is not overridden in client user's email id field");
		}
	}

	public void verifyUnlockButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonUnlock, driver);
		CCUtils.assertTrue(buttonUnlock.isDisplayed(), "Unlock button is not present on client user details page");
		CCUtils.assertEquals(buttonUnlock.getText().trim(), "UNLOCK");
		CCUtils.assertTrue(buttonUnlock.isEnabled(), "Unlock button is disabled on client user details page");
	}

	public void verifyLockedLabelAndIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(labelLockedUser, driver);
		CCUtils.assertTrue(labelLockedUser.isDisplayed(),
				"locked label status is not present on client user details page");
		CCUtils.assertEquals(labelLockedUser.getText().trim(), "Locked");
		CCUtils.assertEquals(labelUserStatusValue.getText().trim(), "lock");
		CCUtils.mouseHoverOverTheElement(driver, labelUserStatusValue);
		String id = labelUserStatusValue.getAttribute("aria-describedby").trim();
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

}
