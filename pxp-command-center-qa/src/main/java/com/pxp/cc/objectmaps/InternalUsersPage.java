// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
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

	@FindBy(xpath = "//table[@role='grid']/tbody[@role='rowgroup']/tr")
	private List<WebElement> selectUser;

	@FindBy(id = "fullname")
	private WebElement rightPanelInternalFullName;

	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement buttonSearch;

	@FindBy(xpath = "//button[text()=' SEND ENROLLMENT ']")
	private WebElement buttonSendEnrollment;

	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']")
	private List<WebElement> tableRowsData;

	@FindBy(xpath = "//div[text()='User Status']//following::mat-icon")
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
	private WebElement lockedCheckbox;

	@FindBy(xpath = "//td[@class='mat-cell cdk-cell cdk-column-firstName mat-column-firstName ng-star-inserted'][1]")
	private WebElement selectInternalUser;
	
	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement buttonClearAll;
	
	@FindBy(xpath = "//span[contains(text(),'number of results found, please refine search further')]")
	private WebElement errorToasterMessage;
	
	@FindBy(id = "searchFirstNameInput")
	private WebElement textboxFirstName;
	
	@FindBy(id = "no-result")
	private WebElement noResultsFound;

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
		CCUtils.verifyAndWaitForElement(activeCheckbox, driver);
		activeCheckbox.click();
		log("Clicked on active User Status checkbox on Search Options popup");
	}

	public void clickOnNoRolesAssignedCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		CCUtils.verifyAndWaitForElement(noRolesAssignedCheckbox, driver);
		noRolesAssignedCheckbox.click();
		log("Clicked on no roles assigned User Status checkbox on Search Options popup");
	}

	public void clickOnInactiveCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		CCUtils.verifyAndWaitForElement(inactiveCheckbox, driver);
		inactiveCheckbox.click();
		log("Clicked on inactive User Status checkbox on Search Options popup");
	}

	public void clickOnLockedCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, searchButton);
		CCUtils.verifyAndWaitForElement(lockedCheckbox, driver);
		lockedCheckbox.click();
		log("Clicked on inactive User Status checkbox on Search Options popup");
	}

	public void selectInternalUser() throws InterruptedException {
		selectInternalUser.click();
		log("Clicked on first internal user");
	}

	public void selectUser() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectUser.get(0), driver);
		selectUser.get(0).click();
		log("Clicked on first internal user");
	}

	public String getInternalUsernameInViewMode() {
		CCUtils.verifyAndWaitForElement(rightPanelInternalFullName, driver);
		log("Internal User Full name : " + rightPanelInternalFullName.getText());
		return rightPanelInternalFullName.getText();
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
		return listInternalUserStatus.get(0).getText().toString().trim();
	}
	
	public void verifyClearAllButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonClearAll, driver);
		CCUtils.assertTrue(buttonClearAll.isDisplayed(),
				"Clear All button is not displayed on var/reseller users page");
		CCUtils.assertEquals(buttonClearAll.getText().trim(), "CLEAR ALL");
		CCUtils.assertTrue(buttonClearAll.isEnabled(), "Clear All button is not enabled on var/reseller users page");
	}
			
	public void clickOnClearAllButton() {
		CCUtils.verifyAndWaitForElement(buttonClearAll, driver);
		javascriptClick(buttonClearAll);
	}
		
	public void verifyErrorMessageWhenNonExistingDataIsEntered(String firstName) throws AWTException, InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);
	}
		
	public void verifyNoResultsFoundErrorMessageIsDisplayed() {
		CCUtils.verifyAndWaitForElement(noResultsFound, driver);
		CCUtils.assertEquals(noResultsFound.getText().trim(), "No results found");
		log("No results found error message is displayed");
	}
		
	public void verifyToasterMessageIsDisplayedIfMoreThanFiftyMatchesReturn() {
		CCUtils.verifyAndWaitForElement(errorToasterMessage, driver);
		CCUtils.assertEquals(errorToasterMessage.isDisplayed(), true);
		log("number of results found, please refine search further error message is displayed");
	}
}
