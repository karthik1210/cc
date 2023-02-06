// Copyright 2021-2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;
import com.sun.xml.bind.api.AccessorException;

public class DeactivateUserPage extends CCMainPage {
	public DeactivateUserPage(WebDriver driver) {
		super(driver);
	}

	private CCUtils ccUtils = new CCUtils();

	@FindBy(xpath = "//span[text()='Select Users to Deactivate']")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//span[contains(text(),'SEARCH OPTIONS')]")
	private WebElement searchOption;

	@FindBy(xpath = "//div[contains(text(),'First Name')]")
	private WebElement labelFirstName;

	@FindBy(xpath = "//div[contains(text(),'Last Name')]")
	private WebElement labelLastName;

	@FindBy(xpath = "//div[contains(text(),'Client')]")
	private WebElement labelClient;

	@FindBy(xpath = "//div[contains(text(),'Email')]")
	private WebElement labelEmail;

	@FindBy(xpath = "//span[contains(text(),'Username')]")
	private WebElement labelUsername;

	@FindBy(xpath = "//div[contains(text(),' Mobile Number ')]")
	private WebElement labelMobileNumber;

	@FindBy(id = "filterClearBtn")
	private WebElement buttonClear;

	@FindBy(id = "searchFirstNameInput")

	private WebElement textboxFirstName;

	@FindBy(id = "searchLastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "searchUserNameInput")
	private WebElement textboxUsername;

	@FindBy(id = "searchclientNameInput")
	private WebElement clientName;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private WebElement selectFirstInAutoSuggestion;

	@FindBy(id = "searchEmailInput")
	private WebElement textboxEmail;

	@FindBy(id = "searchMobileInput")
	private WebElement textboxMobileNumber;

	@FindBy(xpath = "//div[contains(@class, 'userStatus')]")
	private WebElement labelUserStatus;

	@FindBy(id = "searchUserStatus")
	private WebElement labelUserStatusList;

	@FindBy(xpath = "//div[contains(@class, 'userStatus')]//following::input[@type='checkbox']")
	private List<WebElement> checkboxUserStatus;

	@FindBy(xpath = "//div[contains(text(),'User Status')]//following::div[@class='mat-checkbox-inner-container']")
	private WebElement checkboxUserStatusSingle;

	@FindBy(xpath = "//span[contains(text(),'Active')]")
	private WebElement userStatusActive;

	@FindBy(xpath = "//span[contains(text(),'Inactive')]")
	private WebElement userStatusInactive;

	@FindBy(xpath = "//span[contains(text(),'No roles assigned')]")
	private WebElement userStatusNoRolesAssigned;

	@FindBy(xpath = "//div[contains(@class, 'enrStatus')]")
	private WebElement labelEnrollmentStatus;

	@FindBy(id = "searchEnrollmentStatus")
	private List<WebElement> labelEnrollmentStatusList;

	@FindBy(xpath = "//div[@class='mat-checkbox-inner-container']")
	private List<WebElement> checkboxEnrollmentAndUserStatusList;

	@FindBy(xpath = "//div[contains(text(),'Enrollment Status')]//following::input[@type='checkbox']")
	private List<WebElement> checkboxEnrollmentStatus;

	@FindBy(xpath = "//div[contains(text(),'EnrollmentStatus')]//following::div[@class='mat-checkbox-inner-container']")
	private WebElement checkboxEnrollmentStatusSingle;

	@FindBy(xpath = "//span[contains(text(),'Confirmed')]")
	private WebElement enrollmentStatusConfirmed;

	@FindBy(xpath = "//span[contains(text(),'Pending')]")
	private WebElement enrollmentStatusPending;

	@FindBy(xpath = "//span[contains(text(),'Expired')]")
	private WebElement enrollmentStatusExpired;

	@FindBy(xpath = "//span[contains(text(),'Not Sent')]")
	private WebElement enrollmentStatusNotSent;

	@FindBy(xpath = "//div[text()=' Enrollment Status ']//following::input[@type='checkbox']")
	private List<WebElement> enrollmentStatusCheckboxList;

	@FindBy(xpath = "//div[text()=' Enrollment Status ']//following::span[@class='mat-checkbox-label']")
	private List<WebElement> listEnrollmentStatusLabel;

	@FindBy(xpath = "//span[text()='VAR/Reseller']")
	private WebElement labelVarReseller;

	@FindBy(xpath = "//input[@formcontrolname='resellerName']")
	private WebElement dropdownSearchVarReseller;

	@FindBy(xpath = "//span[contains(text(),'No roles assigned')]")
	private WebElement noRolesAssignedCheckbox;

	@FindBy(xpath = "//span[normalize-space(text())='Confirmed']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement confirmedStatusCheckBox;

	@FindBy(xpath = "//span[normalize-space(text())='Pending']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement pendingStatusCheckBox;

	@FindBy(xpath = "//span[normalize-space(text())='Expired']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement expiredStatusCheckBox;

	@FindBy(xpath = "//span[@class='mat-checkbox-label']/span[normalize-space(text())='Not Sent']/../preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
	private WebElement notSentStatusCheckBox;

	@FindBy(id = "filterSearchBtn")
	private WebElement buttonSearch;

	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement clearAllBtn;

	@FindBy(xpath = "//span[contains(text(),'Active')]")
	private WebElement activeCheckbox;

	@FindBy(id = "deactivateBtn")
	private WebElement deactivateBtn;

	@FindBy(id = "applyBtn")
	private WebElement applyBtn;

	@FindBy(id = "applyCancel")
	private WebElement applyCancel;

	@FindBy(xpath = "//td[@id='select_check']")
	private WebElement checkBox;

	@FindBy(xpath = "//label[contains(text(),'Please enter or select to search')]")
	private WebElement errorSearchOptionsPage;

	@FindBy(xpath = "//mat-icon[contains(text(),'clear')]")
	private List<WebElement> buttonFilterSearchCrossIcon;

	@FindBy(xpath = "//span[contains(text(),'CLEAR ALL')]")
	private WebElement buttonClearAll;

	@FindBy(id = "no-result")
	private WebElement noResultsFound;

	@FindBy(xpath = "//span[contains(text(),'number of results found, please refine search further')]")
	private WebElement errorToasterMessage;

	@FindBy(id = "applyDeactivate")
	private WebElement popupTitle;

	@FindBy(xpath = "//span[contains(text(),'Are you sure you would like to deactivate 10 user(s)? ')]")
	private WebElement questionMessage;

	@FindBy(xpath = "//div//span[@class='cancel']")
	private WebElement popupApplyCancel;

	@FindBy(id = "applyClearIcon")
	private WebElement crossIcon;

	@FindBy(id = "applyBtn")
	private WebElement popupApplyBtn;

	@FindBy(xpath = "//*[@id=\"headerNavbar\"]/div/button/span[1]/mat-icon")
	private WebElement arrow_back;

	@FindBy(xpath = "//mat-select[@role ='combobox']")
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

	public void verifyDecativatePopuplableDisplayed() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(popupTitle, driver);
		CCUtils.assertEquals(popupTitle.getText().trim(), "Deactivate?");
	}

	public void verifyDecativateQuestionDisplayed() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(questionMessage, driver);
		CCUtils.assertEquals(questionMessage.getText().trim(), "Are you sure you would like to deactivate 10 user(s)?");
	}

	public void verifyCancelButtonDisplayed() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(popupApplyCancel, driver);
		CCUtils.assertEquals(popupApplyCancel.getText().trim(), "Cancel");
	}

	public void clickOnCancelbuttonConfirmationPopup() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(popupApplyCancel, driver);
		popupApplyCancel.click();
	}

	public void clickOnBackArrow() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(arrow_back, driver);
		arrow_back.click();
	}

	public void verifyYesDeactivateButtonDisplayed() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(popupApplyBtn, driver);
		CCUtils.assertEquals(popupApplyBtn.getText().trim(), "Yes, deactivate");
	}

	public void verifyCrossIconDisplayed() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		CCUtils.assertEquals(crossIcon.getText().trim(), "clear");
	}

	public void clickOnCrossIcon() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
	}

	public void verifySelectedMultipleUsersDeactivatePopup() throws InterruptedException {
		log("Verifying selected multile users deactivate popup ");
		verifyDecativatePopuplableDisplayed();
		verifyDecativateQuestionDisplayed();
		verifyCancelButtonDisplayed();
		verifyYesDeactivateButtonDisplayed();
		verifyCrossIconDisplayed();
	}

	public void verifySearchInternalUserPopup() throws InterruptedException, AccessorException {
		log("Verifying search internal user pop-up");
		verifyFirstNameLabelTextAndTextboxDefaultValue();
		verifyLastNameLabelTextAndTextboxDefaultValue();
		verifyUsernameLabelTextAndTextboxDefaultValue();
		verifyEmailLabelTextAndTextboxDefaultValue();
		verifyMobileNumberLabelTextAndTextboxDefaultValue();
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		verifyClearButtonIsDisplayed();
		verifySearchButtonIsDisplayed();
	}

	public void clickOnSearchOption() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(searchOption, driver);
		searchOption.click();
	}

	public void enterSearchTextInSearchFirstNameFields(String firstName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(firstName);
		log("Entered text in first name field is " + firstName);
	}

	public void clickOnSearchButton() throws InterruptedException {
		log("Verifying clear button is displayed on search options popup");
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		buttonSearch.click();
	}

	public void clickOnActiveCheckbox() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		CCUtils.verifyAndWaitForElement(activeCheckbox, driver);
		activeCheckbox.click();
		log("Clicked on active User Status checkbox on Search Options popup");
	}

	public void verifyClearAllButtonIsDisplayed() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(clearAllBtn, driver);
		CCUtils.assertEquals(clearAllBtn.getText().trim(), "CLEAR ALL");
	}

	public void clickOncheckBox() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkBox, driver);
		checkBox.click();
	}

	public void clickOndeactivateBtn() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(deactivateBtn, driver);
		deactivateBtn.click();
	}

	public void clickOnApplyBtn() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(applyBtn, driver);
		applyBtn.click();
	}

	public void clickOnApplyCancel() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(applyCancel, driver);
		applyCancel.click();
	}

	public void verifyFirstNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelFirstName, driver);
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		if (labelFirstName.isDisplayed() == true && textboxFirstName.isDisplayed() == true) {
			log("Verifying first name label text");
			CCUtils.assertEquals(labelFirstName.getText().trim(), "First Name");
			log("First name label text is " + labelFirstName.getText().trim());
			log("Verifying input text placeholder is displayed for first name textbox field");
			CCUtils.assertEquals(textboxFirstName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for first name textbox field is " + textboxFirstName.getAttribute("placeholder").trim());
		} else {
			log("First name label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyLastNameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelLastName, driver);
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		if (labelLastName.isDisplayed() == true && textboxLastName.isDisplayed() == true) {
			log("Verifying last name label text");
			CCUtils.assertEquals(labelLastName.getText().trim(), "Last Name");
			log("Last name label text is " + labelLastName.getText().trim());
			log("Verifying input text placeholder is displayed for last name textbox field");
			CCUtils.assertEquals(textboxLastName.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for last name textbox field is " + textboxLastName.getAttribute("placeholder").trim());
		} else {
			log("Last name label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyUsernameLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelUsername, driver);
		CCUtils.verifyAndWaitForElement(textboxUsername, driver);
		if (labelUsername.isDisplayed() == true && textboxUsername.isDisplayed() == true) {
			log("Verifying username label text");
			CCUtils.assertEquals(labelUsername.getText().trim(), "Username");
			log("Username label text is " + labelUsername.getText().trim());
			log("Verifying input text placeholder is displayed for username textbox field ");
			CCUtils.assertEquals(textboxUsername.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for username textbox field is " + textboxUsername.getAttribute("placeholder").trim());
		} else {
			log("Username label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyEmailLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		CCUtils.verifyAndWaitForElement(textboxEmail, driver);
		if (labelEmail.isDisplayed() == true && textboxEmail.isDisplayed() == true) {
			log("Verifying email label text");
			CCUtils.assertEquals(labelEmail.getText().trim(), "Email");
			log("Email label text is " + labelEmail.getText().trim());
			log("Verifying input text placeholder is displayed for email textbox field ");
			CCUtils.assertEquals(textboxEmail.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for email textbox field is " + textboxEmail.getAttribute("placeholder").trim());
		} else {
			log("Email label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyMobileNumberLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelMobileNumber, driver);
		CCUtils.verifyAndWaitForElement(textboxMobileNumber, driver);
		if (labelMobileNumber.isDisplayed() == true && textboxMobileNumber.isDisplayed() == true) {
			log("Verifying mobile number label text");
			CCUtils.assertEquals(labelMobileNumber.getText().trim(), "Mobile Number");
			log("Mobile number label text is " + labelMobileNumber.getText().trim());
			log("Verifying input text placeholder is displayed for mobile number textbox field ");
			CCUtils.assertEquals(textboxMobileNumber.getAttribute("placeholder").trim(), "Input Text");
			log("Box text for mobile number textbox field is "
					+ textboxMobileNumber.getAttribute("placeholder").trim());
		} else {
			log("Mobile number label and input textbox are not displayed on search options popup");
		}
	}

	public void verifyUserStatusLabelTextAndCheckboxes() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelUserStatus, driver);
		if (labelUserStatus.isDisplayed() == true) {
			log("Verifying status label text");
			CCUtils.assertEquals(labelUserStatus.getText().trim(), "User Status");
			log("User Status label text is " + labelUserStatus.getText().trim());
			log("Verifying User status label are displayed on search options popup");
			verifyUserStatusValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserStatusCheckboxesAreNotByDefaultSelected();
		} else {
			log("User status label is not displayed on search options popup");
		}
	}

	public void verifyUserStatusValues() throws InterruptedException {
		log("Verifying userStatus Active");
		CCUtils.verifyAndWaitForElement(userStatusActive, driver);
		CCUtils.assertEquals(userStatusActive.getText().trim(), "Active");
		log("Active user status label text is " + userStatusActive.getText().trim());
		log("Verifying userStatus Inactive");
		CCUtils.verifyAndWaitForElement(userStatusInactive, driver);
		CCUtils.assertEquals(userStatusInactive.getText().trim(), "Inactive");
		log("Active user status label text is " + userStatusInactive.getText().trim());
		log("Verifying userStatus No roles assigned");
		CCUtils.verifyAndWaitForElement(userStatusNoRolesAssigned, driver);
		CCUtils.assertEquals(userStatusNoRolesAssigned.getText().trim(), "No roles assigned");
		log("Active user status label text is " + userStatusNoRolesAssigned.getText().trim());
	}

	public void verifyUserStatusCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxUserStatus.size(); i++) {
			CCUtils.assertTrue(!checkboxUserStatus.get(i).isSelected(), "User role checkboxes are not selected");
		}
	}

	List<String> actualUserStatusValues = new ArrayList<String>();
	List<String> actualListOfEnrollmentStatusValues = new ArrayList<String>();

	public final List<String> expectedUserStatusValues = Arrays.asList("Active", "Inactive", "No roles assigned");

	List<String> actualEnrollmentStatusValues = new ArrayList<String>();

	public final List<String> expectedEnrollmentStatusValues = Arrays.asList("Confirmed", "Pending", "Expired",
			"Not Sent");

	public final List<String> expectedListOfEnrollmentStatusValues = Arrays.asList("Pending", "Expired", "Not Sent");

	public void selectEnrollmentStatus(String status) {
		if (status.equalsIgnoreCase("Confirmed"))
			confirmedStatusCheckBox.click();
		else if (status.equalsIgnoreCase("Pending"))
			pendingStatusCheckBox.click();
		else if (status.equalsIgnoreCase("Expired"))
			expiredStatusCheckBox.click();
		else if (status.equalsIgnoreCase("Not Sent"))
			notSentStatusCheckBox.click();
	}

	public void selectEnrollment(List<String> enrollmentStatus) {
		for (String each : enrollmentStatus) {
			if (each.equalsIgnoreCase("Confirmed"))
				confirmedStatusCheckBox.click();
			else if (each.equalsIgnoreCase("Pending"))
				pendingStatusCheckBox.click();
			else if (each.equalsIgnoreCase("Expired"))
				expiredStatusCheckBox.click();
			else if (each.equalsIgnoreCase("Not Sent"))
				notSentStatusCheckBox.click();
		}
	}

	public void verifyEnrollmentStatusLabelTextAndCheckboxes() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelEnrollmentStatus, driver);
		if (labelEnrollmentStatus.isDisplayed()) {
			log("Verifying status label text");
			CCUtils.assertEquals(labelEnrollmentStatus.getText().trim(), "Enrollment Email Status");
			log("Enrollment Status label text is " + labelEnrollmentStatus.getText().trim());
			log("Verifying Enrollment status label are displayed on search  user page");
			verifyEnrollmentStatusValues();
			log("Verifying user role checkboxes are not selected");
			verifyUserStatusCheckboxesAreNotByDefaultSelected();
		} else {
			log("Enrollment status label is not displayed on search options popup");
		}
	}

	public void getEnrollmentStatusValues() throws InterruptedException {
		for (int i = 0; i < labelEnrollmentStatusList.size(); i++) {
			actualEnrollmentStatusValues.add(labelEnrollmentStatusList.get(i).getText());
		}
		log("Enrollment status available for internal user are :" + actualEnrollmentStatusValues);
	}

	public void clickEnrollmentStatusCheckboxes() throws InterruptedException {
		for (int i = 0; i < checkboxEnrollmentAndUserStatusList.size(); i++) {
			CCUtils.assertTrue(!checkboxEnrollmentAndUserStatusList.get(i).isSelected(),
					"User role checkboxes are already selected");
			checkboxEnrollmentAndUserStatusList.get(i).click();
		}
	}

	public void verifyEnrollmentStatusValues() throws InterruptedException {
		log("Verifying enrollmentStatus Confirmed");
		CCUtils.verifyAndWaitForElement(enrollmentStatusConfirmed, driver);
		CCUtils.assertEquals(enrollmentStatusConfirmed.getText().trim(), "Confirmed");
		log("Confirmed  enrollment status label text is " + enrollmentStatusConfirmed.getText().trim());
		log("Verifying enrollmentStatus Pending");
		CCUtils.verifyAndWaitForElement(enrollmentStatusPending, driver);
		CCUtils.assertEquals(enrollmentStatusPending.getText().trim(), "Pending");
		log("Pending  enrollment status label text is " + enrollmentStatusPending.getText().trim());
		log("Verifying enrollmentStatus Expired");
		CCUtils.verifyAndWaitForElement(enrollmentStatusExpired, driver);
		CCUtils.assertEquals(enrollmentStatusExpired.getText().trim(), "Expired");
		log("Expired enrollment status label text is " + enrollmentStatusExpired.getText().trim());
		log("Verifying enrollmentStatus Not Sent");
		CCUtils.verifyAndWaitForElement(enrollmentStatusNotSent, driver);
		CCUtils.assertEquals(enrollmentStatusNotSent.getText().trim(), "Not Sent");
		log("Not Sent  enrollment status label text is " + enrollmentStatusNotSent.getText().trim());
	}

	public void verifyEnrollmentStatusCheckboxesAreNotByDefaultSelected() throws InterruptedException {
		for (int i = 0; i < checkboxEnrollmentStatus.size(); i++) {
			CCUtils.assertTrue(!checkboxEnrollmentStatus.get(i).isSelected(),
					"User role checkboxes are already selected");
		}
	}

	public void selectUserStatus(List<String> userStatusList) {
		for (int i = 0; i < userStatusList.size(); i++) {
			if (userStatusList.get(i).equals("Active")) {
				javascriptClick(checkboxUserStatus.get(0));
			} else if (userStatusList.get(i).equals("Inactive")) {
				javascriptClick(checkboxUserStatus.get(1));
			} else if (userStatusList.get(i).equals("No roles assigned")) {
				javascriptClick(checkboxUserStatus.get(2));
			}
		}
	}

	public void verifyClearButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		CCUtils.assertEquals(buttonClear.getText().trim(), "CLEAR");
	}

	public void clickOnClearButton() throws InterruptedException {
		log("Verifying clear button is displayed on search options popup");
		verifyClearButtonIsDisplayed();
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		buttonClear.click();
	}

	public void verifyTextboxIsEmptyWhenClickOnClearButton() {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		String firstName = textboxFirstName.getText().trim();
		String lastName = textboxLastName.getText().trim();
		String userName = textboxUsername.getText().trim();
		String emailName = textboxEmail.getText().trim();
		String mobileNumber = textboxMobileNumber.getText().trim();
		log("Verifying text is not present in firstname field");
		CCUtils.assertTrue(firstName.length() == 0, "Text is present in first field");
		log("Verifying text is not present in lastname field");
		CCUtils.assertTrue(lastName.length() == 0, "Text is present in last field");
		log("Verifying text is not present in username field");
		CCUtils.assertTrue(userName.length() == 0, "Text is present in username field");
		log("Verifying text is not present in email field");
		CCUtils.assertTrue(emailName.length() == 0, "Text is present in email field");
		log("Verifying text is not present in mobilenumber field");
		CCUtils.assertTrue(mobileNumber.length() == 0, "Text is present in mobilenumber field");
	}

	public void verifySearchButtonIsDisplayed() {
		CCUtils.scrollPageViaElement(driver, buttonSearch);
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		log("Verifying search button is displayed on search options popup");
		if (buttonSearch.isDisplayed() == true) {
			log("Verifying search button is enabled");
			CCUtils.assertTrue(buttonSearch.isEnabled(), "search button is disabled");
			log("Verifying search button text");
			CCUtils.assertEquals(buttonSearch.getText().trim(), "SEARCH");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public void clickToSearchButton() {
		CCUtils.verifyAndWaitForElement(buttonSearch, driver);
		buttonSearch.click();
		log("search button clicked");
	}

	public void verifyErrorMessageOnSearchOptionsPage() throws InterruptedException, AWTException {
		verifySearchButtonIsDisplayed();
		clickToSearchButton();
		CCUtils.assertEquals(errorSearchOptionsPage.getText().trim(), "Please enter or select to search");
		log("Error message displayed for empty search field " + errorSearchOptionsPage.getText().trim());

	}

	public void verifySearchFilterCrossIcon() throws InterruptedException {
		for (int i = 0; i < buttonFilterSearchCrossIcon.size(); i++) {
			buttonFilterSearchCrossIcon.get(i).click();
		}
	}

	public void clickOnClearAllButton() {
		CCUtils.verifyAndWaitForElement(buttonClearAll, driver);
		javascriptClick(buttonClearAll);
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

	public void verifyInternalUsersSelectUsersToDectivatePageTitle() {
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Select Users to Deactivate");
		log("Select Users to Deactivate page title is displayed");
	}

	public String getDefaultItemsPerPage() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd(driver);
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
		CCUtils.scrollPageToEnd(driver);
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

}
