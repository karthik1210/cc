// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

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

	@FindBy(xpath = "//div[text()='User Status']//following::mat-icon")
	private List<WebElement> listClientUserStatus;

	@FindBy(id = "newButton")
	private WebElement buttonNew;

	@FindBy(id = "IconContainer")
	private WebElement buttonKebabMenu;

	@FindBy(xpath = "//td[@id='no-result']")
	private WebElement errorMessage;

	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement buttonClearAll;

	@FindBy(xpath = "//span[@class='configure']")
	private List<WebElement> labelSelectedSearchOptions;

	@FindBy(id = "firstName")
	private WebElement labelSelectedFirstName;

	@FindBy(id = "lastName")
	private WebElement labelSelectedLastName;

	@FindBy(id = "email")
	private WebElement labelSelectedEmail;

	@FindBy(id = "resellerName")
	private WebElement labelSelectedVarResellerName;

	@FindBy(id = "userName")
	private WebElement labelSelectedUserName;

	@FindBy(id = "mobileNumber")
	private WebElement labelSelectedMobileNumber;

	@FindBy(id = "enrollStatus")
	private List<WebElement> labelSelectedEnrollmentStatus;

	@FindBy(id = "userStatus")
	private List<WebElement> labelSelectedUserStatus;

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
		return listClientUserStatus.get(0).getText().trim();
	}

	public AddNewClientUserPage clickOnNewButton() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		buttonNew.click();
		log("Clicked on new button in client users page");
		return PageFactory.initElements(driver, AddNewClientUserPage.class);
	}

	public void clickOnKebabMenuButton() {
		CCUtils.verifyAndWaitForElement(buttonKebabMenu, driver);
		javascriptClick(buttonKebabMenu);
	}

	public void verifyErrorMessageOnClientUserPage() {
		CCUtils.verifyAndWaitForElement(errorMessage, driver);
		CCUtils.assertTrue(errorMessage.isDisplayed(), "No results found");
		log("No results found error message is displayed");
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

	public void verifySelectedSearchOptions(List<String> enrollmentStatus, List<String> userStatus, String firstName,
			String lastName, String email, String username, String mobileNumber) {

		for (int i = 0; i < labelSelectedSearchOptions.size(); i++) {
			String fieldId = labelSelectedSearchOptions.get(i).getAttribute("id");
			String fieldValue = labelSelectedSearchOptions.get(i).getText().trim();
			if (fieldId.equals("firstName")) {
				CCUtils.assertEquals(labelSelectedFirstName.getText().trim(), firstName);
			} else if (fieldId.equals("lastName")) {
				CCUtils.assertEquals(labelSelectedLastName.getText().trim(), lastName);
			} else if (fieldId.equals("userName")) {
				CCUtils.assertEquals(labelSelectedUserName.getText().trim(), username);
			} else if (fieldId.equals("email")) {
				CCUtils.assertEquals(labelSelectedEmail.getText().trim(), email);
			} else if (fieldId.equals("mobileNumber")) {
				CCUtils.assertEquals(labelSelectedMobileNumber.getText().trim(), mobileNumber);
			} else if (fieldId.equals("enrollStatus")) {
				CCUtils.assertTrue(enrollmentStatus.contains(fieldValue), "Enrollment status is not present");
			} else if (fieldId.equals("userStatus")) {
				CCUtils.assertTrue(userStatus.contains(fieldValue), "User status is not present");
			}
		}
	}
}
