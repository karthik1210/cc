//Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class StaffPage extends CCMainPage {

	public CommonMethods commonMethods;

	public StaffPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@role='tab'][3]")
	private WebElement tabStaff;

	@FindBy(id = "openDialog")
	private WebElement addButton;

	@FindBy(xpath = "//input[@id='FirstNameInput']")
	private WebElement textboxFirstName;

	@FindBy(xpath = "//input[@id='LastNameInput']")
	private WebElement textboxLastName;

	@FindBy(id = "ExternalIdInput")
	private WebElement textboxExternalId;

	@FindBy(xpath = "//ng-select[@formcontrolname='gender']")
	private WebElement dropdownGender;

	@FindBy(xpath = "//div[@role='option']/span")
	private List<WebElement> listOfGenderValue;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement saveButton;

	@FindBy(id = "addNewTitle")
	private WebElement labelAddNewStaffPageTitle;

	@FindBy(id = "addNewCancel")
	private WebElement buttonCancel;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(xpath = "//span[contains(text(),' Providers, Resources & Staff')]")
	private WebElement labelProvidersAndResourcesPageTitle;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;

	@FindBy(xpath = "//div[@id='maxfirstNameError']")
	private WebElement errorMaximumCharacterFirstName;

	@FindBy(xpath = "//div[@id='maxlastNameError']")
	private WebElement errorMaximumCharacterLastName;

	@FindBy(xpath = "//div[@id='maxExternalIdError']")
	private WebElement errorMaximumCharacterExternalId;

	@FindBy(xpath = "//div[@id='maxCredentialsError']")
	private WebElement errorMaximumCharacterCredential;

	@FindBy(id = "Gender")
	private WebElement textBoxGender;

	@FindBy(id = "validFirstNameError")
	private WebElement errorValidFirstName;

	@FindBy(id = "validLastNameError")
	private WebElement errorValidLastName;

	@FindBy(xpath = "//div[contains(text(),'FILTERS')]")
	private WebElement labelFilter;

	@FindBy(xpath = "//label[@for='practiceSelect']")
	private WebElement labelPractice;

	@FindBy(xpath = "//label[@for='systemSelect']")
	private WebElement labelSystem;

	@FindBy(id = "systemSelect")
	private WebElement systemDropdownFilter;

	@FindBy(xpath = "//label[@for='enterpriseSelect']")
	private WebElement labelEnterprise;

	@FindBy(id = "enterpriseSelect")
	private WebElement enterpriseDropdownFilter;

	@FindBy(id = "practiceSelect")
	private WebElement practiceDropdownFilter;

	@FindBy(xpath = "//label[@for='solutionSelect']")
	private WebElement labelSolution;

	@FindBy(id = "solutionSelect")
	private WebElement solutionDropdownFilter;

	@FindBy(id = "clearBtn")
	private WebElement filterClearButton;

	@FindBy(id = "applyBtn")
	private WebElement filterApplyButton;

	@FindBy(id = "toggleFilters")
	private WebElement hideFiltersButton;

	@FindBy(xpath = "//div//span//following::span[contains(text(),'10')]")
	private WebElement defaultItemsPerPage;

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

	@FindBy(xpath = "//div[@class='mat-paginator-range-label']")
	private WebElement displayPagination;

	@FindBy(id = "searchOrgInput")
	private WebElement searchButton;

	@FindBy(id = "toggleFilters")
	private WebElement showFiltersButton;

	@FindBy(id = "searchButton")
	private WebElement searchbttn;

	@FindBy(id = "no-result")
	private WebElement noDataDisplayMessage;

	@FindBy(xpath = "//span[text()='Staff Details']")
	private WebElement resourceDetailsTitle;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectStaff;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button//span//mat-icon")
	private WebElement entepriseArrow;

	@FindBy(xpath = "//mat-icon[text()='arrow_back ']")
	private WebElement backIcon;

	@FindBy(xpath = "//td[@aria-sort='ascending']")
	private WebElement staffAscSort;

	@FindBy(xpath = "//td[@aria-sort='descending']")
	private WebElement staffDscSort;

	@FindBy(xpath = "//button[@aria-label='Next page']")
	private WebElement buttonNextPage;

	@FindBy(xpath = "//button[@aria-label='Previous page']")
	private WebElement buttonPreviousPage;

	@FindBy(xpath = "//button[@aria-label='First page']")
	private WebElement buttonFirstPage;

	@FindBy(xpath = "//ng-dropdown-panel/div/div[2]/div[1]")
	private WebElement selectFirstOption;

	@FindBy(xpath = "//div[text()=' Last Name ']")
	private WebElement staffLastNameLabel;

	@FindBy(xpath = "//div[normalize-space(text())='Status']")
	private WebElement staffStatusLabel;

	public void clickOnStaffTab() {
		CCUtils.verifyAndWaitForElement(tabStaff, driver);
		tabStaff.click();
		log("Clicked on StaffTab");
	}

	public void verifyAddButtonIsEnabled() {
		logStep("Verifying add button is enabled or not");
		CCUtils.verifyAndWaitForElement(addButton, driver);
		CCUtils.assertTrue(addButton.isEnabled(), "Add button is disabled on staff page");
	}

	public void clickOnAddStaffButton() {
		CCUtils.verifyAndWaitForElement(addButton, driver);
		addButton.click();
		log("Verifying add new satff Button");
	}

	public void enterFirstName(String input) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(input);
		log("Entered input for Name : " + input);
	}

	public void enterLastName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(input);
		log("Entered input for Name : " + input);
	}

	public void enterExternalId(String input) {
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.clear();
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.sendKeys(input);
		log("Entered input for ExternalId : " + input);
	}

	public void clickOnGenderDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownGender, driver);
		dropdownGender.click();
	}

	public void selectGenderValue(String value) {
		clickOnGenderDropdown();
		if (listOfGenderValue.size() > 0) {
			if (value.equals("Male")) {
				javascriptClick(listOfGenderValue.get(0));
				log("Clicked on 'Male' option");
			} else if (value.equals("Female")) {
				javascriptClick(listOfGenderValue.get(1));
				log("Clicked on 'Female' option");

			} else {
				log("Gender value not displayed on add a staff screen");
			}
		}
	}

	public void verifySaveAndAddButtonIsEnabled() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		logStep("Verifying Save and add button is enabled");
		CCUtils.assertEquals(saveButton.isEnabled(), true);
	}

	public void clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
	}

	public void verifyAddStaffPopupTitle() {
		CCUtils.verifyAndWaitForElement(labelAddNewStaffPageTitle, driver);
		log("Verifying create Resource page title");
		CCUtils.assertEquals(labelAddNewStaffPageTitle.isDisplayed(), true);
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel button");
	}

	public void verifyProvidersAndResourcesAndStaffTitle() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(labelProvidersAndResourcesPageTitle, driver);
		CCUtils.assertEquals(labelProvidersAndResourcesPageTitle.isDisplayed(), true);
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on X icon");
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}

	public String getMaximumCharacterErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterFirstName, driver);
		return errorMaximumCharacterFirstName.getText().trim();
	}

	public String getMaximumCharacterErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterLastName, driver);
		return errorMaximumCharacterLastName.getText().trim();
	}

	public String getMaximumCharacterErrorForExternalIdField() {
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterExternalId, driver);
		return errorMaximumCharacterExternalId.getText().trim();
	}

	public void enterGender(String input) {
		CCUtils.verifyAndWaitForElement(textBoxGender, driver);
		textBoxGender.sendKeys(input);
		log("Entered input for Gender : " + input);
	}

	public String getValidErrorForFirstNameField() {
		CCUtils.verifyAndWaitForElement(errorValidFirstName, driver);
		return errorValidFirstName.getText().trim();
	}

	public String getValidErrorForLastNameField() {
		CCUtils.verifyAndWaitForElement(errorValidLastName, driver);
		return errorValidLastName.getText().trim();
	}

	public void verifyFilterLabel() {
		CCUtils.verifyAndWaitForElement(labelFilter, driver);
		if (labelPractice.isDisplayed()) {
			log("Verifying filter label text");
			CCUtils.assertEquals(labelFilter.getText(), "FILTERS");
			log("FILTERS text is successfully verified");
		}
	}

	public void verifySystemFilterLabelTextAndPlaceholder() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelSystem, driver);
		if (labelSystem.isDisplayed()) {
			log("Verifying System filter label text");
			CCUtils.assertEquals(labelSystem.getText().trim(), "System");
			log("Sytem filter label text is " + labelSystem.getText().trim());
		} else {
			log("Sytem filter label is not displayed  on Staff search page");
		}
		CCUtils.assertTrue(systemDropdownFilter.isDisplayed(), "verifying system dropdown is present or not");
		CCUtils.assertEquals(systemDropdownFilter.getAttribute("placeholder"), "Search/Select");
	}

	public void verifyEnterpriseFilterLabelTextAndPlaceholder() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelEnterprise, driver);
		if (labelEnterprise.isDisplayed()) {
			log("Verifying Enterprise filter label text");
			CCUtils.assertEquals(labelEnterprise.getText().trim(), "Enterprise");
			log("Enterprise filter label text is " + labelEnterprise.getText().trim());
		} else {
			log("Enterprise filter label is not displayed on staff search page");
		}
		CCUtils.assertTrue(enterpriseDropdownFilter.isDisplayed(), "verifying enterprise dropdown is present or not");
		CCUtils.assertEquals(enterpriseDropdownFilter.getAttribute("placeholder"), "Search/Select");
	}

	public void verifyPracticeFilterLabelTextAndPlaceholder() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelPractice, driver);
		if (labelPractice.isDisplayed()) {
			log("Verifying Practice filter label text");
			CCUtils.assertEquals(labelPractice.getText().trim(), "Practice");
			log("Practice filter label text is " + labelPractice.getText().trim());
		} else {
			log("Practice filter label is not displayed on staff search page");
		}
		CCUtils.assertTrue(practiceDropdownFilter.isDisplayed(), "verifying practice dropdown is present or not");
		CCUtils.assertEquals(practiceDropdownFilter.getAttribute("placeholder"), "Search/Select");
	}

	public void verifySolutionFilterLabelTextAndPlaceholder() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelSolution, driver);
		if (labelSolution.isDisplayed()) {
			log("Verifying Solution filter label text");
			CCUtils.assertEquals(labelSolution.getText().trim(), "Solution");
			log("Solution filter label text is " + labelSolution.getText().trim());
		} else {
			log("Solution filter label is not displayed on staff search page");
		}
		CCUtils.assertTrue(solutionDropdownFilter.isDisplayed(), "verifying solution dropdown is present or not");
		CCUtils.assertEquals(solutionDropdownFilter.getAttribute("placeholder"), "Search/Select");
	}

	public void clearButtonDisplayed() {
		CCUtils.verifyAndWaitForElement(filterClearButton, driver);
		CCUtils.assertTrue(filterClearButton.isDisplayed(), "verifying clear button is dispalyed or not");
		log("Verified clear button is present on Staff search page");
	}

	public void clearApplyDisplayed() {
		CCUtils.verifyAndWaitForElement(filterApplyButton, driver);
		CCUtils.assertTrue(filterApplyButton.isDisplayed(), "verifying clear button is dispalyed or not");
		log("Verified apply button is present on Staff search page");
	}

	public void clickToHideFiltersButton() {
		CCUtils.verifyAndWaitForElement(hideFiltersButton, driver);
		hideFiltersButton.click();
		log("clicked to Hide Button Filter");
	}

	public void clickToShowFiltersButton() {
		CCUtils.verifyAndWaitForElement(showFiltersButton, driver);
		showFiltersButton.click();
		log("clicked on Show Button Filetr");
	}

	public void verifyAllElementsOfFilterNotVisible() {
		CCUtils.verifyAndWaitForElement(hideFiltersButton, driver);
		if (hideFiltersButton.getText().contains("Show Filters")) {
			log("Show filters is displayed instead of hide filters");
		}
	}

	public String getDefaultItemsPerPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		highlightElement(defaultItemsPerPage);
		return defaultItemsPerPage.getText().toString();
	}

	public void clickNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		buttonNextPage.click();
		log("clicked to Next Page button");
	}

	public void checkNextPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		CCUtils.scrollPageToEnd(driver);
		int noOfStaff = Integer.valueOf(getTotalNoOfStaff());
		log("Total number of Staff is : " + noOfStaff);
		if (noOfStaff >= itemsPerPageValue) {
			int LHSPaginationValue = 1;
			while (isNextPageButtonDisabled()) {
				clickNextPageButton();
				LHSPaginationValue += itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : " + getLHSPagination() + " with expected value : "
						+ LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(getLHSPagination()), LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of Staff is less that Items per page so all Providers displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public String getTotalNoOfStaff() {
		CCUtils.verifyAndWaitForElement(displayPagination, driver);
		CCUtils.scrollPageViaElement(driver, displayPagination);
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("of", 0);
		String data = null;
		for (String w : words) {
			data = w.trim();
			log("Print the value of default Pagination : " + w.trim());
		}
		return data.trim();
	}

	public boolean isNextPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonNextPage, driver);
		return buttonStatus;
	}

	public String getLHSPagination() throws AWTException {
		String data = getDefaultPagination();
		String[] words = data.split("–", 2);
		String value = null;
		for (String w : words) {
			value = w.trim();
			log("print the value of data : " + value);
			break;
		}
		log("Print the value of LHS Pagination : " + value.trim());
		return value.trim();
	}

	public String getRHSPagination() throws AWTException {
		String data = getDefaultPagination();
		String[] words = data.split("–", 0);
		for (String w : words) {
			data = w.trim();
		}
		log("Print the value of RHS Pagination : " + data.trim());
		return data.trim();
	}

	public String getDefaultPagination() {
		CCUtils.verifyAndWaitForElement(displayPagination, driver);
		CCUtils.scrollPageViaElement(driver, displayPagination);
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("of", 0);
		String data = null;
		for (String w : words) {
			data = w.trim();
			log("Print the value of default Pagination : " + w.trim());
			break;
		}
		return data.trim();
	}

	@FindBy(xpath = "//button[@aria-label='Last page']")
	private WebElement buttonLastPage;

	public boolean isLastPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonLastPage, driver);
		return buttonStatus;
	}

	public boolean isFirstPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonFirstPage, driver);
		return buttonStatus;
	}

	public boolean isPreviousPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonPreviousPage, driver);
		return buttonStatus;
	}

	public void clickItemsPerPageDropdown() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd(driver);
		CCUtils.verifyAndWaitForElement(itemsPerPageDropdown, driver);
		highlightElement(itemsPerPageDropdown);
		javascriptClick(itemsPerPageDropdown);
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

	public void enterNonExistingStaffAndVerifyResult(String staffFirstName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.clear();
		searchButton.sendKeys(staffFirstName);
		log("resource name : " + staffFirstName + " entered in search textbox");
		clickToSearchBtn();
	}

	public void enterStaffInSearchTextBox(String staffFirstName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.clear();
		searchButton.sendKeys(staffFirstName);
		log("resource name : " + staffFirstName + " entered in search textbox");
		clickToSearchBtn();
	}

	public void clickToSearchBtn() {
		CCUtils.verifyAndWaitForElement(searchbttn, driver);
		searchbttn.click();
		log("clicked to search button");
	}

	public void verifyNoDataDisplayErrorMessage() {
		CCUtils.verifyAndWaitForElement(noDataDisplayMessage, driver);
		CCUtils.assertTrue(noDataDisplayMessage.isDisplayed(), "No Data to Display");
		log("No Data to Display error message is displayed");
	}

	public void verifyStaffDetailsTitle() {
		CCUtils.verifyAndWaitForElement(resourceDetailsTitle, driver);
		log("verified Staff details page title is present");
	}

	public void selectStaff() {
		CCUtils.verifyAndWaitForElement(selectStaff, driver);
		selectStaff.click();
		log("Clicked on first Staff");
	}

	public void clickToEntepriseArrow() {
		CCUtils.verifyAndWaitForElement(entepriseArrow, driver);
		entepriseArrow.click();
		log("clicked to enteprise Arrow");
	}

	public void clickToBackButton() throws AWTException {
		CCUtils.scrollPageToStart();
		CCUtils.verifyElementToBeClickable(backIcon, driver);
		CCUtils.verifyAndWaitForElement(backIcon, driver);
		backIcon.click();
		log("clicked to back button in staff details page");
	}

	public void clickToSystemFilter() {
		CCUtils.verifyAndWaitForElement(systemDropdownFilter, driver);
		systemDropdownFilter.click();
	}

	private String xpathRequiredClientType = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpath(String xpathValue, String substitutionValue) {
		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectFilter(String filterName) {
		WebElement clientType = prepareDynamicXpath(xpathRequiredClientType, filterName);
		CCUtils.verifyAndWaitForElement(clientType, driver);
		clientType.click();
		log("Clicked on : " + filterName);
	}

	public void clickToApplyFilterButton() {
		CCUtils.verifyAndWaitForElement(filterApplyButton, driver);
		filterApplyButton.click();
		log("clicked to apply filter button");
	}

	public void clickToEnterpriseFilter() {
		CCUtils.verifyAndWaitForElement(enterpriseDropdownFilter, driver);
		enterpriseDropdownFilter.click();
	}

	public void clickToPracticeFilter() {
		CCUtils.verifyAndWaitForElement(practiceDropdownFilter, driver);
		practiceDropdownFilter.click();
	}

	public void clickToClearFilterButton() {
		CCUtils.verifyAndWaitForElement(filterClearButton, driver);
		filterClearButton.click();
		log("clicked to clear filter button");
	}

	public void clickFirstInAutoSuggestion() throws InterruptedException {
		CCUtils.waitForElementPresence(selectFirstOption, driver);
		javascriptClick(selectFirstOption);
	}

	public String getNoOfRowsPagination() {
		String data = splitingPagination();
		String noOfRowsPagination = data.substring(0, 2).trim();
		return noOfRowsPagination.trim();
	}

	public String splitingPagination() {
		CCUtils.verifyAndWaitForElement(displayPagination, driver);
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("\\s", 3);
		String data = null;
		for (String w : words) {
			data = w.trim();
		}
		log("Print the value of default Pagination : " + data.trim());
		return data.trim();
	}

	public void checkLastPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfStaff = Integer.valueOf(getTotalNoOfStaff());
		if (noOfStaff >= itemsPerPageValue) {
			clickLastPageButton();
			log("verifying RHS Pagination value : " + Integer.parseInt(getRHSPagination())
					+ "should be equal to Number of Providers : " + noOfStaff);
			CCUtils.assertEquals(Integer.parseInt(getRHSPagination()), noOfStaff);
			log("verifying after clicking to Last Page Button, Last Page and Next Page button should get disabled.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
		} else {
			log("Veriyfing all the Paginations should be disabled Toal number of resource is less that Items per page ");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
			CCUtils.assertTrue(isFirstPageButtonDisabled() == false, "First Page Button should get disabled");
			CCUtils.assertTrue(isPreviousPageButtonDisabled() == false, "Previous Page Button should get disabled");
		}
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

	public void checkPreviousPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfStaff = Integer.valueOf(getTotalNoOfStaff());
		log("Total number of Resources is : " + noOfStaff);
		if (noOfStaff >= itemsPerPageValue) {
			checkNextPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
			int LHSPaginationValue = Integer.parseInt(getLHSPagination());
			while (isPreviousPageButtonDisabled()) {
				clickPreviousPageButton();
				LHSPaginationValue -= itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : " + Integer.parseInt(getLHSPagination())
						+ " with expected value : " + LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(getLHSPagination()), LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of Staff is less that Items per page .");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void clickAscStaffTypeSortToDsc() {
		log("clicked on Resource type  column label");
		CCUtils.verifyAndWaitForElement(staffAscSort, driver);
		staffAscSort.click();
		log("clicked to ascending staff type firstname sort");
	}

	public void verifyStaffTypeDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(staffDscSort, driver);
		log("verified after clicking to ascending sort staff type first  name sort arrow goes downwards to descending sort");
	}

	public void clickDscStaffTypeSortToAsc() {
		CCUtils.verifyAndWaitForElement(staffDscSort, driver);
		staffDscSort.click();
		log("clicked to descending Staff first  name sort");
	}

	public void verifyStaffTypeAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(staffAscSort, driver);
		log("verified after clicking to descending sort staff first name sort arrow goes upwards to ascending sort");
	}

	public void clickAscStaffLastNameSortToDsc() {
		CCUtils.verifyAndWaitForElement(staffLastNameLabel, driver);
		javascriptClick(staffLastNameLabel);
		CCUtils.verifyAndWaitForElement(staffAscSort, driver);
		staffAscSort.click();
		log("clicked to ascending Staff last name sort");
	}

	public void verifyStaffLastNameDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(staffDscSort, driver);
		log("verified after clicking to ascending sort staff last name sort arrow goes downwards to descending sort");
	}

	public void clickDscStaffLastNameSortToAsc() {
		CCUtils.verifyAndWaitForElement(staffDscSort, driver);
		staffDscSort.click();
		log("clicked to descending Staff last name sort");
	}

	public void verifyStaffLastNameAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(staffAscSort, driver);
		log("verified after clicking to descending sort staff last name sort arrow goes upwards to ascending sort");
	}

	public void clickStaffStatusSortToDsc() {
		CCUtils.verifyAndWaitForElement(staffStatusLabel, driver);
		javascriptClick(staffStatusLabel);
		log("clicked on staff status column label");
		CCUtils.verifyAndWaitForElement(staffAscSort, driver);
		staffAscSort.click();
		log("clicked to ascending arrow to get descending order");
	}

	public void verifyStaffStatusDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(staffDscSort, driver);
		log("verified after clicking to acsendig sort Staff status sort arrow goes downwards to descending sort");
	}

	public void clickDscStaffStatusSortToAsc() {
		CCUtils.verifyAndWaitForElement(staffDscSort, driver);
		staffDscSort.click();
		log("clicked to descending arrow to get ascending order");
	}

	public void verifyStaffStatusAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(staffAscSort, driver);
		log("verified after clicking to descendig sort Staff status sort arrow goes upwards to ascending sort");
	}

}
