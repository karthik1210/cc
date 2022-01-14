// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class VARResellerUsersPage extends CCMainPage {
	
	@FindBy(xpath = "//span[text()=' VAR/Reseller Users ']")
	private WebElement labelPageTitle;
    
	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement buttonSearchOptions;
	
	@FindBy(xpath = "//button[text()=' SEND ENROLLMENT ']")
	private WebElement buttonSendEnrollment;
	
	@FindBy(id = "newButton")
	private WebElement buttonNew;
	
	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']")
	private List<WebElement> tableRowsData;

	public VARResellerUsersPage(WebDriver driver) {
		super(driver);
	}

	public void verifyVARResellerUsersPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Internal Users");
		log("VAR/Reseller Users page title is displayed");
	}

	public SearchOptionsVARResellerUsersPage clickOnSearchOptionVARResellerUsersPage() {
		CCUtils.verifyAndWaitForElement(buttonSearchOptions, driver);
		buttonSearchOptions.click();
		log("Clicked on Search Option button in command center users page");
		return PageFactory.initElements(driver,SearchOptionsVARResellerUsersPage.class);
	}
	
	public SendBulkEnrollmentPage clickOnSendEnrollmentButton() {
		CCUtils.verifyAndWaitForElement(buttonSendEnrollment, driver);
		log("Click on send enrollment button");
		buttonSendEnrollment.click();
		return PageFactory.initElements(driver, SendBulkEnrollmentPage.class);
	}

	public AddNewInternalUsersPage clickOnNewButton() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		buttonNew.click();
		log("Clicked on new button in command center users page");
		return PageFactory.initElements(driver, AddNewInternalUsersPage.class);
	}
}

