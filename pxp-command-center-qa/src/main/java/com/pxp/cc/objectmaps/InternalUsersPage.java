// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

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

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

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

	public void verifySuccessToaster() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), "Success! New User has been successfully added");
		else
			log("Success toaster is not displayed on internal users page");
	}
}
