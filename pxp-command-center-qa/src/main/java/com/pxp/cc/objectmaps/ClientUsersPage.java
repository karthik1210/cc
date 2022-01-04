// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class ClientUsersPage extends CCMainPage {

	@FindBy(className = "tab-name")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement buttonSearch;

	@FindBy(id = "firstName")
	private List<WebElement> selectUser;

	@FindBy(id = "clientProductIcon")
	private List<WebElement> listClientUserStatus;

	@FindBy(id = "newButton")
	private WebElement buttonNew;

	public ClientUsersPage(WebDriver driver) {
		super(driver);
	}

	public void verifyClientUsersPageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Client Users");
		log("Client users page title is displayed");
	}

	public SearchOptionsPage clickOnSearchOptionPage() {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		buttonSearch.click();
		log("Clicked on search button in client users page");
		return PageFactory.initElements(driver, SearchOptionsPage.class);
	}

	public ClientUserDetailsPage selectUser() {
		CCUtils.verifyAndWaitForElement(selectUser.get(0), driver);
		javascriptClick(selectUser.get(0));
		log("Clicked on first client user");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}
	
	public String getClientUserStatus() {
		CCUtils.verifyAndWaitForElement(listClientUserStatus.get(0), driver);
		return listClientUserStatus.get(0).getAttribute("ng-reflect-message").toString().trim();
	}

	public AddNewClientUserPage clickOnNewButton() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		buttonNew.click();
		log("Clicked on new button in client users page");
		return PageFactory.initElements(driver, AddNewClientUserPage.class);
	}

}
