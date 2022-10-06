package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

	@FindBy(xpath = "(//tr[@id='locationList']//td[1])")
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
		searchTextBox.sendKeys(locationName);
		log("location name" + locationName + "entered in serach textbox");
	}

	public void clickToSearchButton() {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.click();
		log("clicked to search button");
	}

	public void verifyListOfLocationMatchedSearchResult(List<String> locationName) throws AWTException {
		log("logged to method");
		List<String> actualListOfLocations = new ArrayList<String>();
		log("print the size of list of locations : "+listOfLocationName.size());
		for(WebElement element : listOfLocationName) {
			CCUtils.verifyAndWaitForElement(element, driver);
			actualListOfLocations.add(element.getText().toString().trim());
		}
		log("print the value of actual list of locations : "+actualListOfLocations);
		for (int i = 0; i < locationName.size(); i++) {
			log("logged to for loop 1");
			CCUtils.scrollPageToEnd();
			for (int j = 0; j < listOfLocationName.size(); j++) {
				log("logged to for loop 2");
				CCUtils.verifyAndWaitForElement(listOfLocationName.get(j), driver);
				CCUtils.assertEquals(listOfLocationName.get(j).getText().toString().trim(), locationName.get(0).trim());
				log(" actual location from web table : " + listOfLocationName.get(j).getText().toString().trim()
						+ " is equal to expected location : " + locationName.get(0).trim());
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
		for (WebElement element : listOfLocationName) {
			for (int i = 0; i < listOfLocations.size(); i++) {
				CCUtils.verifyAndWaitForElement(element, driver);
				CCUtils.assertEquals(element.getText().toString().trim(), listOfLocations.get(0).trim());
			}
		}
	}

}

