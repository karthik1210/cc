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

	@FindBy(id = "addClientUser")
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

	@FindBy(id = "mat-select-2")
	private WebElement defaultItemsPerPage;

	@FindBy(xpath = "//div[@class='mat-paginator-range-label']")
	private WebElement displayPagination;

	@FindBy(xpath = "//button[@aria-label='Next page']")
	private WebElement buttonNextPage;

	@FindBy(xpath = "//button[@aria-label='Last page']")
	private WebElement buttonLastPage;

	@FindBy(xpath = "//button[@aria-label='Previous page']")
	private WebElement buttonPreviousPage;

	@FindBy(xpath = "//button[@aria-label='First page']")
	private WebElement buttonFirstPage;

	@FindBy(xpath = "//mat-select[@role='combobox']//following::div[starts-with(@class,'mat-select-arrow')][1]")
	private WebElement itemsPerPageDropdown;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'20')]")
	private WebElement itemsPerPageTwenty;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'50')]")
	private WebElement itemsPerPageFifty;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'100')]")
	private WebElement itemsPerPageHundred;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'200')]")
	private WebElement itemsPerPageTwoHundred;
	
	@FindBy(id = "IconContainer")
	private WebElement moreOptionsButton;

	public InternalUsersPage(WebDriver driver) {
		super(driver);
	}

	private CCUtils ccUtils = new CCUtils();

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

	public String getDefaultItemsPerPage() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		return defaultItemsPerPage.getText().toString();
	}

	public String getDefaultPagination() {
		String defaultPagination = CCUtils.getDefaultPaginationOfWebPage(displayPagination, driver);
		return defaultPagination;
	}

	public String getTotalNoOfInternalUsers() {
		String totalUsers = CCUtils.getTotalNoOfClientsFromPaginationSection(displayPagination, driver);
		return totalUsers;
	}

	public String getNoOfRowsPagination() {
		return ccUtils.getNoOfRowsPagination(displayPagination, driver);
	}

	public boolean isFirstPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonFirstPage, driver);
		return buttonStatus;
	}

	public boolean isPreviousPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonPreviousPage, driver);
		return buttonStatus;
	}

	public boolean isLastPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonLastPage, driver);
		return buttonStatus;
	}

	public boolean isNextPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonNextPage, driver);
		return buttonStatus;
	}

	public void clickItemsPerPageDropdown() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(itemsPerPageDropdown, driver);
		highlightElement(itemsPerPageDropdown);
		javascriptClick(itemsPerPageDropdown);
	}

	public void clickNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		log("click to Next Page button");
		buttonNextPage.click();
	}

	public void clickLastPageButton() {
		CCUtils.verifyAndWaitForElement(buttonLastPage, driver);
		log("click to Last Page button");
		buttonLastPage.click();
	}

	public void clickPreviousPageButton() {
		CCUtils.verifyAndWaitForElement(buttonPreviousPage, driver);
		log("click to Previous Page button");
		buttonPreviousPage.click();
	}

	public void clickFirstPageButton() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		log("click to First Page button");
		buttonFirstPage.click();
	}

	public void checkNextPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfInternalUsers());
		log("Total number of clients is : " + noOfClients);
		if (noOfClients >= itemsPerPageValue) {
			int LHSPaginationValue = 1;
			while (isNextPageButtonDisabled()) {
				clickNextPageButton();
				LHSPaginationValue += itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : "
						+ ccUtils.getLHSPaginationOfWebPage(displayPagination, driver) + " with expected value : "
						+ LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(ccUtils.getLHSPaginationOfWebPage(displayPagination, driver)),
						LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public String clickTwentyItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageTwenty, driver);
		highlightElement(itemsPerPageTwenty);
		currentItemPerPage = itemsPerPageTwenty.getText().toString();
		itemsPerPageTwenty.click();
		return currentItemPerPage;
	}

	public String clickFiftyItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageFifty, driver);
		highlightElement(itemsPerPageFifty);
		currentItemPerPage = itemsPerPageFifty.getText().toString();
		itemsPerPageFifty.click();
		return currentItemPerPage;
	}

	public String clickHundredItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageHundred, driver);
		highlightElement(itemsPerPageHundred);
		currentItemPerPage = itemsPerPageHundred.getText().toString();
		itemsPerPageHundred.click();
		return currentItemPerPage;
	}

	public String clickTwoHundredItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageTwoHundred, driver);
		highlightElement(itemsPerPageTwoHundred);
		currentItemPerPage = itemsPerPageTwoHundred.getText().toString();
		itemsPerPageTwoHundred.click();
		return currentItemPerPage;
	}

	public void checkFirstPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfInternalUsers());
		log("Total number of clients is : " + noOfClients);
		checkLastPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
		if (isFirstPageButtonDisabled() == true) {
			clickFirstPageButton();
			String expectedPagination = "1 – " + itemsPerPageValue;
			CCUtils.assertEquals(getDefaultPagination(), expectedPagination);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkLastPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfInternalUsers());
		if (noOfClients >= itemsPerPageValue) {
			clickLastPageButton();
			log("verifying RHS Pagination value : "
					+ Integer.parseInt(CCUtils.getRHSPaginationOfWebPage(displayPagination, driver))
					+ "should be equal to Number of Clients : " + noOfClients);
			CCUtils.assertEquals(Integer.parseInt(CCUtils.getRHSPaginationOfWebPage(displayPagination, driver)),
					noOfClients);
			log("verifying after clicking to Last Page Button, Last Page and Next Page button should get disabled.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
		} else {
			log("Veriyfing all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
			CCUtils.assertTrue(isFirstPageButtonDisabled() == false, "First Page Button should get disabled");
			CCUtils.assertTrue(isPreviousPageButtonDisabled() == false, "Previous Page Button should get disabled");
		}
	}

	public void checkPreviousPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfInternalUsers());
		log("Total number of clients is : " + noOfClients);
		if (noOfClients >= itemsPerPageValue) {
			checkNextPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
			int LHSPaginationValue = Integer.parseInt(ccUtils.getLHSPaginationOfWebPage(displayPagination, driver));
			while (isPreviousPageButtonDisabled()) {
				clickPreviousPageButton();
				LHSPaginationValue -= itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : "
						+ Integer.parseInt(ccUtils.getLHSPaginationOfWebPage(displayPagination, driver))
						+ " with expected value : " + LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(ccUtils.getLHSPaginationOfWebPage(displayPagination, driver)),
						LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
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

	public void verifyErrorMessageWhenNonExistingDataIsEntered(String firstName)
			throws AWTException, InterruptedException {
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
	
	public void clickToMoreOptions() {
		CCUtils.verifyAndWaitForElement(moreOptionsButton, driver);
		moreOptionsButton.click();
		log("clicked to more options button");
	}
}
