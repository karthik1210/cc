package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class SearchLocationPage extends CCMainPage {

	public SearchLocationPage(WebDriver driver) {
		super(driver);
	}

	public CommonMethods commonMethods;

	@FindBy(id = "searchOrgInput")
	private WebElement searchTextBox;

	@FindBy(id = "searchButton")
	private WebElement searchButton;

	@FindBy(id = "toggleFilters")
	private WebElement hideFiltersButton;

	@FindBy(xpath = "(//tbody[@role='rowgroup']//tr[@id='locationList']//td[1])")
	private List<WebElement> listOfLocationName;

	@FindBy(id = "no-result")
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

	public void enterLocationInSearchTextBox(String locationName) {
		CCUtils.verifyAndWaitForElement(searchTextBox, driver);
		searchTextBox.clear();
		searchTextBox.sendKeys(locationName);
		log("location name" + locationName + "entered in serach textbox");
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

	public void verifyListOfLocationMatchedSearchResult(List<String> locationName) throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForPresenceOfListOfElement(listOfLocationName, driver);
		log("print the size of list of locations : " + listOfLocationName.size());
		for (int i = 0; i < locationName.size(); i++) {
			for (int j = 0; j < listOfLocationName.size(); j++) {
				CCUtils.verifyAndWaitForElement(listOfLocationName.get(j), driver);
				if (listOfLocationName.get(j).getText().toString().trim().contains(locationName.get(0).trim())) {
					log(" actual location from web table : " + listOfLocationName.get(j).getText().toString().trim()
							+ " is EQUAL to expected location : " + locationName.get(0).trim());
				} else
					log(" actual location from web table : " + listOfLocationName.get(j).getText().toString().trim()
							+ " is NOT EQUAL to expected location : " + locationName.get(0).trim());
			}
		}
	}

	public void searchNonExistingLocationAndVerifyResult(String locationName) {
		enterLocationInSearchTextBox(locationName);
		clickToSearchButton();
		verifyNoDataDisplayErrorMessage();
	}

	public void verifyNoDataDisplayErrorMessage() {
		CCUtils.verifyAndWaitForElement(noDataDisplayMessage, driver);
		CCUtils.assertTrue(noDataDisplayMessage.isDisplayed(), "No Data to Display Error message got displayed");
		log("No Data to Display error message is displayed");
	}

	public void searchLocationAndVerifyResult(String locationName) throws AWTException {
		
		enterLocationInSearchTextBox(locationName);
		clickToSearchButton();
		List<String> listOfLocName = new ArrayList<String>();
		listOfLocName.add(locationName);
		verifySearchLocationFound(listOfLocName);
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

	public void verifySearchLocationFound(List<String> listOfLocations) {
		CCUtils.verifyAndWaitForPresenceOfListOfElement(listOfLocationName, driver);
		for (WebElement element : listOfLocationName) {
			for (int i = 0; i < listOfLocations.size(); i++) {
				CCUtils.verifyAndWaitForElement(element, driver);
				CCUtils.assertEquals(element.getText().toString().trim(), listOfLocations.get(0).trim());
			}
		}
	}

	public void verifySearchButtonIsDisplayed() {
		log("Verifying search button is displayed on locations search page");
		CCUtils.verifyAndWaitForElement(searchTextBox, driver);
		searchTextBox.sendKeys("blabla");
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
		log("Verifying search textbox is present on locations search page");
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
			log("Sytem filter label is not displayed  on locations search page");
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
			log("Enterprise filter label is not displayed on locations search page");
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
			log("Practice filter label is not displayed on locations search page");
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
			log("Solution filter label is not displayed on locations search page");
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
		log("Verified clear button is present on location search page");
	}
	
	public void clearApplyDisplayed() {
		CCUtils.verifyAndWaitForElement(filterApplyButton, driver);
		CCUtils.assertTrue(filterApplyButton.isDisplayed(), "verifying clear button is dispalyed or not");
		log("Verified apply button is present on location search page");
	}
	
	public void verifyAllElementsOfFilterNotVisible() {
		CCUtils.verifyAndWaitForElement(hideFiltersButton, driver);
		if(hideFiltersButton.getText().contains("Show Filters")) {
			log("Show filters is displayed instead of hide filters");
		}
	}
}
