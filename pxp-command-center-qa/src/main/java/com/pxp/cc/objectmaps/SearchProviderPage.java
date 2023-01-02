// Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class SearchProviderPage extends CCMainPage {

	public SearchProviderPage(WebDriver driver) {
		super(driver);
	}

	public CommonMethods commonMethods;

	@FindBy(id = "searchOrgInput")
	private WebElement searchTextBox;

	@FindBy(id = "searchButton")
	private WebElement searchButton;

	@FindBy(id = "toggleFilters")
	private WebElement hideFiltersButton;

	@FindBy(xpath = "(//tbody[@role='rowgroup']//tr[@role='row']//td[1])")
	private WebElement providerName;

	@FindBy(xpath = "//div[normalize-space(text())='No data to display']")
	private WebElement noDataDisplayMessage;

	@FindBy(id = "applyBtn")
	private WebElement filterApplyButton;

	@FindBy(id = "clearBtn")
	private WebElement filterClearButton;

	@FindBy(id = "systemSelect")
	private WebElement systemDropdownFilter;

	@FindBy(id = "enterpriseSelect")
	private WebElement enterpriseDropdownFilter;

	@FindBy(id = "practiceSelect")
	private WebElement practiceDropdownFilter;

	@FindBy(id = "solutionSelect")
	private WebElement solutionDropdownFilter;

	@FindBy(xpath = "//label[@for='systemSelect']")
	private WebElement labelSystem;

	@FindBy(xpath = "//label[@for='enterpriseSelect']")
	private WebElement labelEnterprise;

	@FindBy(xpath = "//label[@for='practiceSelect']")
	private WebElement labelPractice;

	@FindBy(xpath = "//label[@for='solutionSelect']")
	private WebElement labelSolution;

	@FindBy(xpath = "//div[contains(text(),'FILTERS')]")
	private WebElement labelFilter;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectProvider;

	@FindBy(xpath = "(//div[@role='combobox'])[3]")
	private WebElement selectPracticeFilter;

	@FindBy(xpath = "(//div[@role='option'])[1]")
	private WebElement selectPractice;

	@FindBy(xpath = "//div[normalize-space(text())='Status']")
	private WebElement providersStatusLabel;

	@FindBy(xpath = "//mat-select[@role='combobox']//following::div[starts-with(@class,'mat-select-arrow')][1]")
	private WebElement itemsPerPageDropdown;

	@FindBy(xpath = "//button[@aria-label='Next page']")
	private WebElement buttonNextPage;

	@FindBy(xpath = "//button[@aria-label='Last page']")
	private WebElement buttonLastPage;

	@FindBy(xpath = "//button[@aria-label='Previous page']")
	private WebElement buttonPreviousPage;

	@FindBy(xpath = "//button[@aria-label='First page']")
	private WebElement buttonFirstPage;

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

	@FindBy(xpath = "//div//span//following::span[contains(text(),'10')]")
	private WebElement defaultItemsPerPage;

	@FindBy(xpath = "(//tbody[@role='rowgroup']//tr[@role='row']//td[1])")
	private List<WebElement> listOfProviderName;

	@FindBy(xpath = "//div[@role='tab']//div[text()='Providers']")
	private WebElement tabProvider;

	@FindBy(xpath = "//div[normalize-space(text())='Last Name']")
	private WebElement providersLastNameLabel;

	@FindBy(xpath = "//td[@aria-sort='ascending']")
	private WebElement providersAscSort;

	@FindBy(xpath = "//td[@aria-sort='descending']")
	private WebElement providersDscSort;
	
	@FindBy(xpath = "//mat-icon[normalize-space(text())='check_circle']")
	private WebElement activeStatus;
	
	@FindBy(xpath = "//mat-icon[normalize-space(text())='cancel']")
	private WebElement inactiveStatus;

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

	public void clickToSystemFilter() {
		CCUtils.verifyAndWaitForElement(systemDropdownFilter, driver);
		systemDropdownFilter.click();
	}

	public void clickToEnterpriseFilter() {
		CCUtils.verifyAndWaitForElement(enterpriseDropdownFilter, driver);
		enterpriseDropdownFilter.click();
	}

	public void clickToPracticeFilter() {
		CCUtils.verifyAndWaitForElement(practiceDropdownFilter, driver);
		practiceDropdownFilter.click();
	}

	public void clickToSolutionFilter() {
		CCUtils.verifyAndWaitForElement(solutionDropdownFilter, driver);
		solutionDropdownFilter.click();
	}

	public void clickToSearchButton() {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.click();
		log("clicked to search button");
	}

	public void clickToHideFiltersButton() {
		CCUtils.verifyAndWaitForElement(hideFiltersButton, driver);
		hideFiltersButton.click();
		log("clicked to Hide Button Filter");
	}

	public void searchProviderAndVerifyResult(String searchProviderName) throws AWTException {
		enterProviderInSearchTextBox(searchProviderName);
		clickToSearchButton();
		verifySearchProviderFound(searchProviderName);
	}

	public void verifySearchProviderFound(String searchedProvider) {
		CCUtils.verifyAndWaitForElement(providerName, driver);
		CCUtils.assertEquals(providerName.isDisplayed(), true);
	}

	public void searchNonExistingProviderAndVerifyResult(String providerName) {
		searchTextBox.clear();
		enterProviderInSearchTextBox(providerName);
		clickToSearchButton();
		verifyNoDataDisplayErrorMessage();
	}

	public void enterProviderInSearchTextBox(String providerName) {
		CCUtils.verifyAndWaitForElement(searchTextBox, driver);
		searchTextBox.clear();
		searchTextBox.sendKeys(providerName);
		log("provider name : " + providerName + " entered in serach textbox");
	}

	public void verifyNoDataDisplayErrorMessage() {
		CCUtils.verifyAndWaitForElement(noDataDisplayMessage, driver);
		CCUtils.assertTrue(noDataDisplayMessage.isDisplayed(), "No Data to Display");
		log("No Data to Display error message is displayed");
	}

	public void clickToApplyFilterButton() {
		CCUtils.verifyAndWaitForElement(filterApplyButton, driver);
		filterApplyButton.click();
		log("clicked to apply filter button");
	}

	public void clickToClearFilterButton() {
		CCUtils.verifyAndWaitForElement(filterClearButton, driver);
		filterClearButton.click();
		log("clicked to clear filter button");
	}

	public void verifySearchButtonIsDisplayed() {
		log("Verifying search button is displayed on providers search page");
		CCUtils.verifyAndWaitForElement(searchTextBox, driver);
		searchTextBox.sendKeys("abcccccc");
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		if (searchButton.isDisplayed() == true) {
			log("Verifying search button is enabled");
			CCUtils.assertTrue(searchButton.isEnabled(), "search button is disabled");
			log("Verifying search button text");
			CCUtils.assertEquals(searchButton.getText().trim(), "SEARCH");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public void verifySearchTextBoxAndPlaceholderIsDisplayed() {
		CCUtils.verifyAndWaitForElement(searchTextBox, driver);
		log("Verifying search textbox is present on providers search page");
		if (searchTextBox.isDisplayed() == true) {
			log("Verifying search textbox is enabled");
			CCUtils.assertTrue(searchTextBox.isEnabled(), "search button is disabled");
			CCUtils.assertEquals(searchTextBox.getAttribute("placeholder"), "Search");
			log("Verified search text and it's placeholder");
		} else {
			log("Search button is not displayed on search options popup");
		}
	}

	public void verifySystemFilterLabelTextAndPlaceholder() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(labelSystem, driver);
		if (labelSystem.isDisplayed()) {
			log("Verifying System filter label text");
			CCUtils.assertEquals(labelSystem.getText().trim(), "System");
			log("Sytem filter label text is " + labelSystem.getText().trim());
		} else {
			log("Sytem filter label is not displayed  on providers search page");
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
			log("Enterprise filter label is not displayed on providers search page");
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
			log("Practice filter label is not displayed on providers search page");
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
			log("Solution filter label is not displayed on providers search page");
		}
		CCUtils.assertTrue(solutionDropdownFilter.isDisplayed(), "verifying solution dropdown is present or not");
		CCUtils.assertEquals(solutionDropdownFilter.getAttribute("placeholder"), "Search/Select");
	}

	public void verifyFilterLabel() {
		CCUtils.verifyAndWaitForElement(labelFilter, driver);
		if (labelPractice.isDisplayed()) {
			log("Verifying filter label text");
			CCUtils.assertEquals(labelFilter.getText(), "FILTERS");
			log("FILTERS text is successfully verified");
		}
	}

	public void clearButtonDisplayed() {
		CCUtils.verifyAndWaitForElement(filterClearButton, driver);
		CCUtils.assertTrue(filterClearButton.isDisplayed(), "verifying clear button is dispalyed or not");
		log("Verified clear button is present on provider search page");
	}

	public void clearApplyDisplayed() {
		CCUtils.verifyAndWaitForElement(filterApplyButton, driver);
		CCUtils.assertTrue(filterApplyButton.isDisplayed(), "verifying clear button is dispalyed or not");
		log("Verified apply button is present on provider search page");
	}

	public void verifyAllElementsOfFilterNotVisible() {
		CCUtils.verifyAndWaitForElement(hideFiltersButton, driver);
		if (hideFiltersButton.getText().contains("Show Filters")) {
			log("Show filters is displayed instead of hide filters");
		}
	}

	public ProviderDetailsPage clickToProvider(String providerName) throws InterruptedException {
		enterProviderInSearchTextBox(providerName);
		clickToSearchButton();
		selectProvider();
		return PageFactory.initElements(super.driver, ProviderDetailsPage.class);
	}

	public void selectProvider() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectProvider, driver);
		selectProvider.click();
		log("Clicked on first Provider");
	}

	public void selectPracticeFilter(String filterName) {
		CCUtils.verifyAndWaitForElement(selectPracticeFilter, driver);
		selectPracticeFilter.click();
		log("clicked to select Practice Filter");
	}

	public void clickToFirstPracticeOption() {
		CCUtils.verifyAndWaitForElement(selectPractice, driver);
		selectPractice.click();
	}

	public void clickAscProvidersFirstNameSortToDsc() {
		CCUtils.verifyAndWaitForElement(providersAscSort, driver);
		providersAscSort.click();
		log("clicked to ascending Provider first name sort");
	}

	public void verifyProvidersFirstNameDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(providersDscSort, driver);
		log("verified after clicking to acsendig sort Provider first name sort arrow goes downwards to descending sort");
	}

	public void clickDscProvidersFirstNameSortToAsc() {
		CCUtils.verifyAndWaitForElement(providersDscSort, driver);
		providersDscSort.click();
		log("clicked to descending Provider first name sort");
	}

	public void verifyProvidersFirstNameAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(providersAscSort, driver);
		log("verified after clicking to descendig sort Provider first name sort arrow goes upwards to ascending sort");
	}

	public void clickAscProvidersLastNameSortToDsc() {
		CCUtils.verifyAndWaitForElement(providersLastNameLabel, driver);
		javascriptClick(providersLastNameLabel);
		log("clicked on Provider LastName column label");
		CCUtils.verifyAndWaitForElement(providersAscSort, driver);
		providersAscSort.click();
		log("clicked to ascending Provider last name sort");
	}

	public void verifyProvidersLastNameDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(providersDscSort, driver);
		log("verified after clicking to acsendig sort Provider last name sort arrow goes downwards to descending sort");
	}

	public void clickDscProvidersLastNameSortToAsc() {
		CCUtils.verifyAndWaitForElement(providersDscSort, driver);
		providersDscSort.click();
		log("clicked to descending Provider last name sort");
	}

	public void verifyProvidersLastNameAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(providersAscSort, driver);
		log("verified after clicking to descendig sort Provider last name sort arrow goes upwards to ascending sort");
	}

	public void clickAscProvidersStatusSortToDsc() {
		CCUtils.verifyAndWaitForElement(providersStatusLabel, driver);
		javascriptClick(providersStatusLabel);
		log("clicked on Provider status column label");
		CCUtils.verifyAndWaitForElement(providersAscSort, driver);
		providersAscSort.click();
		log("clicked to ascending Provider status sort");
	}

	public void verifyProvidersStatusDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(providersDscSort, driver);
		log("verified after clicking to acsendig sort Provider status sort arrow goes downwards to descending sort");
	}

	public void clickDscProvidersStatusSortToAsc() {
		CCUtils.verifyAndWaitForElement(providersDscSort, driver);
		providersDscSort.click();
		log("clicked to descending Provider status sort");
	}

	public void verifyProvidersStatusAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(providersAscSort, driver);
		log("verified after clicking to descendig sort Provider status sort arrow goes upwards to ascending sort");
	}

	public String getDefaultItemsPerPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		highlightElement(defaultItemsPerPage);
		return defaultItemsPerPage.getText().toString();
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

	public String getTotalNoOfProviders() {
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

	public void clickItemsPerPageDropdown() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(itemsPerPageDropdown, driver);
		highlightElement(itemsPerPageDropdown);
		javascriptClick(itemsPerPageDropdown);
	}

	public void clickNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		buttonNextPage.click();
		log("clicked to Next Page button");
	}

	public boolean isNextPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonNextPage, driver);
		return buttonStatus;
	}

	public void clickLastPageButton() {
		CCUtils.verifyAndWaitForElement(buttonLastPage, driver);
		log("click to Last Page button");
		buttonLastPage.click();
	}

	public boolean isLastPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonLastPage, driver);
		return buttonStatus;
	}

	public void clickPreviousPageButton() {
		CCUtils.verifyAndWaitForElement(buttonPreviousPage, driver);
		log("click to Previous Page button");
		buttonPreviousPage.click();
	}

	public boolean isPreviousPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonPreviousPage, driver);
		return buttonStatus;
	}

	public void clickFirstPageButton() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		log("click to First Page button");
		buttonFirstPage.click();
	}

	public boolean isFirstPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonFirstPage, driver);
		return buttonStatus;
	}

	public String getNoOfRowsPagination() {
		String data = splitingPagination();
		String noOfRowsPagination = data.substring(0, 2).trim();
		return noOfRowsPagination.trim();
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

	public void checkLastPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfProviders = Integer.valueOf(getTotalNoOfProviders());
		if (noOfProviders >= itemsPerPageValue) {
			clickLastPageButton();
			log("verifying RHS Pagination value : " + Integer.parseInt(getRHSPagination())
					+ "should be equal to Number of Providers : " + noOfProviders);
			CCUtils.assertEquals(Integer.parseInt(getRHSPagination()), noOfProviders);
			log("verifying after clicking to Last Page Button, Last Page and Next Page button should get disabled.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
		} else {
			log("Veriyfing all the Paginations should be disabled Toal number of Providers is less that Items per page so all Providers displayed in 1st slot.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
			CCUtils.assertTrue(isFirstPageButtonDisabled() == false, "First Page Button should get disabled");
			CCUtils.assertTrue(isPreviousPageButtonDisabled() == false, "Previous Page Button should get disabled");
		}
	}

	public void checkPreviousPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfProviders = Integer.valueOf(getTotalNoOfProviders());
		log("Total number of Providers is : " + noOfProviders);
		if (noOfProviders >= itemsPerPageValue) {
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
			log("Verifying all the Paginations should be disabled Toal number of Providers is less that Items per page so all Providers displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkFirstPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfProviders = Integer.valueOf(getTotalNoOfProviders());
		log("Total number of Providers is : " + noOfProviders);
		checkLastPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
		if (isFirstPageButtonDisabled() == true) {
			clickFirstPageButton();
			String expectedPagination = "1 – " + itemsPerPageValue;
			CCUtils.assertEquals(getDefaultPagination(), expectedPagination);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
		} else {
			log("Verifying all the Paginations should be disabled Toal number of Providers is less that Items per page so all Providers displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkNextPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		CCUtils.scrollPageToEnd();
		int noOfProviders = Integer.valueOf(getTotalNoOfProviders());
		log("Total number of Providers is : " + noOfProviders);
		if (noOfProviders >= itemsPerPageValue) {
			int LHSPaginationValue = 1;
			while (isNextPageButtonDisabled()) {
				clickNextPageButton();
				LHSPaginationValue += itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : " + getLHSPagination() + " with expected value : "
						+ LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(getLHSPagination()), LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of Providers is less that Items per page so all Providers displayed in 1st slot.");
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

	public void verifyListOfProviderMatchedSearchResult(List<String> ProviderName) throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForPresenceOfListOfElement(listOfProviderName, driver);
		log("print the size of list of Providers : " + listOfProviderName.size());
		for (int i = 0; i < ProviderName.size(); i++) {
			for (int j = 0; j < listOfProviderName.size(); j++) {
				CCUtils.verifyAndWaitForElement(listOfProviderName.get(j), driver);
				if (listOfProviderName.get(j).getText().toString().trim().contains(ProviderName.get(0).trim())) {
					log(" actual Provider from web table : " + listOfProviderName.get(j).getText().toString().trim()
							+ " is EQUAL to expected Provider : " + ProviderName.get(0).trim());
				} else
					log(" actual Provider from web table : " + listOfProviderName.get(j).getText().toString().trim()
							+ " is NOT EQUAL to expected Provider : " + ProviderName.get(0).trim());
			}
		}
	}

	public void clickToProvidersTab() {
		CCUtils.verifyAndWaitForElement(tabProvider, driver);
		tabProvider.click();
		log("clicked to provider tab");
	}

	public void verifyActiveStatus() {
		CCUtils.verifyAndWaitForElement(activeStatus, driver);
		CCUtils.assertEquals(activeStatus.isDisplayed(), true);
		log("Verified active status");
	}

	public void verifyInactiveStatus() {
		CCUtils.verifyAndWaitForElement(inactiveStatus, driver);
		CCUtils.assertEquals(inactiveStatus.isDisplayed(), true);
		log("Verified inactive status");
	}

}
