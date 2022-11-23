// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class ClientUsersPage extends CCMainPage {

	@FindBy(xpath = "//span[contains(text(),' Client Users ')]") 
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement buttonSearch;

	@FindBy(id = "firstName")
	private List<WebElement> selectUser;

	@FindBy(id = "clientProductIcon")
	private List<WebElement> listClientUserStatus;

	@FindBy(id = "addClientUser")
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

	@FindBy(xpath = "//mat-icon[contains(text(),'more_vert')] ")
	private WebElement kebabMenu;

	@FindBy(xpath = "(//td[@id='firstName'])[1]")
	private WebElement selectClientUser;

	@FindBy(id = "FirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "LastNameInput")
	private WebElement textboxLastName;

	@FindBy(xpath = "//input[@id='EmailInput']")
	private WebElement textboxEmail;

	@FindBy(id = "loginUsernameInput")
	private WebElement textboxUserName;

	@FindBy(xpath = "//input[@id='addMobileInput'] | //input[@id='mobileInput'] | //input[@id='MobileNumberInput']")
	private WebElement textboxMobileNumber;

	@FindBy(xpath = "//span[text()='First Name *']")
	private WebElement labelFirstName;

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
		log("Clicked on search button in command center users page");
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
		log("Clicked on locked User Status checkbox on Search Options popup");
	}

	public void selectClientUser() throws InterruptedException {
		selectClientUser.click();
		log("Clicked on first Client user");
	}

	public void clickOnKebabMenu() throws InterruptedException {
		kebabMenu.click();
		log("Clicked on kebabMenu on Client Users page");
	}

	public void enterFirstName(String input) {
		CCUtils.verifyAndWaitForElement(labelFirstName, driver);
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.clear();
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(input);
		log("Entered input for Firstname : " + input);
	}

	public void enterLastName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.clear();
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(input);
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
		CCUtils.verifyAndWaitForElement(textboxUserName, driver);
		textboxUserName.click();
		log("Entered input for email : " + input);
	}

	public void enterMobileNumber(String input) {
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		textboxMobileNumber.clear();
		textboxMobileNumber.sendKeys(input);
		log("Entered input for mobile number : " + input);
	}
}
