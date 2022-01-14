// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class CommandCenterUsersPage extends CCMainPage {

	@FindBy(xpath = "//mat-icon[@class='mat-icon notranslate pageTopIcon material-icons mat-icon-no-color']")
	private WebElement iconCommandCenterUsers;

	@FindBy(xpath = "//span[contains(text(),' Command Center Users ')]")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//div[@tabindex='0']")
	private WebElement tabInternalUsers;

	@FindBy(xpath = "//div[@tabindex='0']/div")
	private WebElement tabTitleInternalUsers;

	@FindBy(xpath = "//div[text()='CLIENT USERS']")
	private WebElement tabClientUsers;
	
	@FindBy(xpath = "//div[text()='VAR/RESELLER USERS']")
	private WebElement tabVARResellerUsers;

	public CommandCenterUsersPage(WebDriver driver) {
		super(driver);
	}

	public void verifyUserRedirectedToCommandCenterPage() {
		CCUtils.verifyAndWaitForElement(iconCommandCenterUsers, driver);
		log("Verifying command center users icon is displayed on screen");
		CCUtils.assertTrue(iconCommandCenterUsers.isDisplayed(),
				"Command center users icon is not displayed on screen.");
		log("Verifying command center users page title");
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		if (labelPageTitle.isDisplayed())
			CCUtils.assertEquals(labelPageTitle.getText().trim(), "Command Center Users");
		else
			log("Command center users page title is not displayed on screen");
	}

	public void verifyInternalUsersTabByDefaultSelected() {
		log("Verifying internal users tab is by default selected in command center user page");
		CCUtils.verifyAndWaitForElement(tabTitleInternalUsers, driver);
		if (tabInternalUsers.isDisplayed() == true && tabTitleInternalUsers.getText().trim().equals("INTERNAL USERS"))
			CCUtils.assertEquals(tabInternalUsers.getAttribute("aria-selected"), "true");
		else
			log("Internal users tab is not displayed on command center users page");
	}

	public ClientUsersPage clickOnClientUsersTab() {
		CCUtils.verifyAndWaitForElement(tabClientUsers, driver);
		tabClientUsers.click();
		log("User clicked Client Users tab.");
		return PageFactory.initElements(driver, ClientUsersPage.class);

	}

	public VARResellerUsersPage clickOnVARResellerUsersTab() {
		CCUtils.verifyAndWaitForElement(tabClientUsers, driver);
		tabVARResellerUsers.click();
		log("User clicked VARReseller Users tab.");
		return PageFactory.initElements(driver, VARResellerUsersPage.class);

    }
}
