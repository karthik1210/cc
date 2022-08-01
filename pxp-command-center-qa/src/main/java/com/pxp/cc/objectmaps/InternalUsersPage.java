// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class InternalUsersPage extends CCMainPage {

	@FindBy(id = "newButton")
	private WebElement buttonNew;

	@FindBy(xpath = "//span[text()=' Internal Users ']")
	private WebElement labelPageTitle;

	@FindBy(id = "users")
	private List<WebElement> selectUser;

	@FindBy(id = "editUser")
	private WebElement buttonEditInternalUser;

	@FindBy(id = "Cancel")
	private WebElement buttonCancel;

	@FindBy(id = "Save")
	private WebElement buttonSave;

	@FindBy(id = "First")
	private WebElement textboxFirstName;

	@FindBy(id = "Last")
	private WebElement textboxLasttName;

	@FindBy(id = "Email")
	private WebElement textboxEmail;

	@FindBy(id = "USername")
	private WebElement textboxUserName;

	@FindBy(id = "Mobile")
	private WebElement textboxMobileNumber;

	@FindBy(id = "Pwd")
	private WebElement labelPassword;

	@FindBy(id = "fullname")
	private WebElement rightPanelInternalFullName;

	@FindBy(id = "err-first")
	private WebElement errorFirstName;

	@FindBy(id = "err-last")
	private WebElement errorLastName;

	@FindBy(id = "err-user")
	private WebElement errorUserName;

	@FindBy(id = "err-email")
	private WebElement errorEmail;

	@FindBy(id = "errmobile")
	private WebElement errorMobileNumber;

	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement buttonSearch;

	@FindBy(xpath = "//button[text()=' SEND ENROLLMENT ']")
	private WebElement buttonSendEnrollment;

	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']")
	private List<WebElement> tableRowsData;

	@FindBy(id = "userStatus")
	private List<WebElement> listInternalUserStatus;

	@FindBy(xpath = "//span[contains(text(),'Active')]")
	private WebElement activeCheckbox;

	@FindBy(id = "filterSearchBtn")
	private WebElement searchButton;

	@FindBy(xpath = "//span[contains(text(),'No roles assigned')]")
	private WebElement noRolesAssignedCheckbox;

	@FindBy(xpath = "//span[contains(text(),'Inactive')]")
	private WebElement inactiveCheckbox;

	@FindBy(xpath = "//span[contains(text(),'Locked out')]")
	private WebElement LockedCheckbox;

	@FindBy(xpath = "//td[@class='mat-cell cdk-cell cdk-column-firstName mat-column-firstName ng-star-inserted'][1]")
	private WebElement selectInternalUser;

	public InternalUsersPage(WebDriver driver) {
		super(driver);
	}

	public AddNewInternalUsersPage clickOnNewButton() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		buttonNew.click();
		log("Clicked on new button in command center users page");
		return PageFactory.initElements(driver, AddNewInternalUsersPage.class);
	}

	public void verifyInternalUsersPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Internal Users");
		log("Internal Users page title is displayed");
	}

	public void clickOnActiveCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		Thread.sleep(10000);
		activeCheckbox.click();
		log("Clicked on active User Status checkbox on Search Options popup");
	}

	public void clickOnNoRolesAssignedCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		Thread.sleep(10000);
		noRolesAssignedCheckbox.click();
		log("Clicked on no roles assigned User Status checkbox on Search Options popup");
	}

	public void clickOnInactiveCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		Thread.sleep(10000);
		inactiveCheckbox.click();
		log("Clicked on inactive User Status checkbox on Search Options popup");
	}

	public void clickOnLockedCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		Thread.sleep(10000);
		LockedCheckbox.click();
		log("Clicked on inactive User Status checkbox on Search Options popup");
	}

	public void selectInternalUser() throws InterruptedException {
		selectInternalUser.click();
		log("Clicked on first internal user");
	}

	public void selectUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectUser.get(0), driver);
		javascriptClick(selectUser.get(0));
		log("Clicked on first internal user");
	}

	public void clickEditInInternalUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonEditInternalUser, driver);
		javascriptClick(buttonEditInternalUser);
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

	public String getInternalUsernameInViewMode() {
		CCUtils.verifyAndWaitForElement(rightPanelInternalFullName, driver);
		log("Internal User Full name : " + rightPanelInternalFullName.getText());
		return rightPanelInternalFullName.getText();
	}

	public String getFirstNameError() {
		CCUtils.verifyAndWaitForElement(errorFirstName, driver);
		log("Error in first name field is : " + errorFirstName.getText());
		return errorFirstName.getText();
	}

	public String getLastNameError() {
		CCUtils.verifyAndWaitForElement(errorLastName, driver);
		log("Error in last name field is : " + errorLastName.getText());
		return errorLastName.getText();
	}

	public String getUserNameError() {
		CCUtils.verifyAndWaitForElement(errorUserName, driver);
		log("Error in username field is : " + errorUserName.getText());
		return errorUserName.getText();
	}

	public String getEmailError() {
		CCUtils.verifyAndWaitForElement(errorEmail, driver);
		log("Error in email field is : " + errorEmail.getText());
		return errorEmail.getText();
	}

	public String getMobileNumberError() {
		CCUtils.verifyAndWaitForElement(errorMobileNumber, driver);
		log("Error in Mobile number field is : " + errorMobileNumber.getText());
		return errorMobileNumber.getText();
	}

	public SearchOptionsPage clickOnSearchOptionPage() {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		buttonSearch.click();
		log("Clicked on search button in command center users page");
		return PageFactory.initElements(driver, SearchOptionsPage.class);
	}

	public SendBulkEnrollmentPage clickOnSendEnrollmentButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollment, driver);
		log("Click on send enrollment button");
		buttonSendEnrollment.click();
		return PageFactory.initElements(driver, SendBulkEnrollmentPage.class);
	}

	public InternalUserDetailsPage clickOneOfTheUserNameFromTable() {
		for (int i = 0; i < tableRowsData.size(); i++)
			tableRowsData.get(i).click();
		log("Clicked on any of the user name in internal users page");
		return PageFactory.initElements(driver, InternalUserDetailsPage.class);
	}

	public String getInternalUserStatus() {
		CCUtils.verifyAndWaitForElement(listInternalUserStatus.get(0), driver);
		return listInternalUserStatus.get(0).getAttribute("ng-reflect-message").toString().trim();
	}
}
