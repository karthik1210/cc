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

	@FindBy(id = "userStatus")
	private List<WebElement> listInternalUserStatus;

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
		return listInternalUserStatus.get(0).getAttribute("ng-reflect-message").toString().trim();
	}

}
