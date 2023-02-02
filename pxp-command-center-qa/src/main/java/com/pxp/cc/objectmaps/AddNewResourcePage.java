// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class AddNewResourcePage extends CCMainPage {

	public AddNewResourcePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "(//div[@role='tab'])[2]")
	private WebElement tabResources;

	@FindBy(id = "openDialog")
	private WebElement addButton;

	@FindBy(xpath = "//input[@id='NameInput']")
	private WebElement textboxName;

	@FindBy(xpath = "//div[@role='option']/span")
	private List<WebElement> listOfResourceType;

	@FindBy(id = "selectStatus")
	private WebElement textboxConnectedProvider;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement saveButton;

	@FindBy(id = "ExternalIdInput")
	private WebElement textboxExternalId;

	@FindBy(xpath = "//div[text()='Select Resource Type']//following::div[@role='combobox']/input")
	private WebElement dropdownResourceType;

	@FindBy(xpath = "//mat-option[@id='searchValues'][1]")
	private WebElement selectFirstOption;

	@FindBy(id = "NameInput")
	private WebElement textboxtName;

	@FindBy(id = "addNewTitle")
	private WebElement labelAddNewResourcePageTitle;

	@FindBy(xpath = "//span[text()='Name * ']")
	private WebElement labelName;

	@FindBy(xpath = "//span[text()='External ID * ']")
	private WebElement labeltextboxExternalId;

	@FindBy(xpath = "//span[text()='Resource Type * ']")
	private WebElement labeltextboxResourceType;

	@FindBy(xpath = "//div[@id='maxfirstNameError']")
	private WebElement errorMaximumCharacterName;

	@FindBy(xpath = "//div[@id='maxExternalIdError']")
	private WebElement errorMaximumCharacterExternalId;

	@FindBy(xpath = "//div[text()='External Id already in use']")
	private WebElement errorUniqueExternalId;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;

	@FindBy(id = "addNewCancel")
	private WebElement buttonCancel;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

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

	@FindBy(id = "applyBtn")
	private WebElement filterApplyButton;

	@FindBy(id = "clearBtn")
	private WebElement filterClearButton;

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

	@FindBy(xpath = "//table//thead//td//following::td[@aria-sort='ascending'][1]")
	private WebElement resourceNameAscSort;

	@FindBy(xpath = "//td[@aria-sort='descending']")
	private WebElement resourceNameDscSort;

	@FindBy(id = "searchOrgInput")
	private WebElement searchButton;

	@FindBy(id = "searchButton")
	private WebElement searchBtn;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectResource;

	@FindBy(xpath = "//span[text()='Resource Details']")
	private WebElement resourceDetailsTitle;

	@FindBy(id = "no-result")
	private WebElement noDataDisplayMessage;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button//span//mat-icon")
	private WebElement entepriseArrow;

	@FindBy(xpath = "//mat-icon[text()='arrow_back ']")
	private WebElement backIcon;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']//td[1]")
	private List<WebElement> listOfResourceName;

	private String xpathRequiredClientType = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpath(String xpathValue, String substitutionValue) {
		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	@FindBy(xpath = "//td[@aria-sort='ascending']")
	private WebElement resourcesAscSort;

	@FindBy(xpath = "//td[@aria-sort='descending']")
	private WebElement resourcesDscSort;

	@FindBy(xpath = "//div[normalize-space(text())='Status']")
	private WebElement resourcesStatusLabel;

	@FindBy(xpath = "//div[text()=' Scheduling Resource Name ']")
	private WebElement resourcesNameLabel;

	@FindBy(xpath = "//div[text()=' Type ']")
	private WebElement resourcesTypeLabel;

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
	
	@FindBy(xpath = "//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//mat-icon[text()=' chat ']//..//following::div[@id='toggleButton']//child::input[@type='checkbox'][1]")
	private WebElement connectedSolChat;
	
	@FindBy(xpath="//input[@id='mat-slide-toggle-15-input']")
	private WebElement connectedsolutions;
	
	public void clickToSolutionFilter() {
		CCUtils.verifyAndWaitForElement(solutionDropdownFilter, driver);
		solutionDropdownFilter.click();
	}


	public void clickToSystemFilter() {
		CCUtils.verifyAndWaitForElement(systemDropdownFilter, driver);
		systemDropdownFilter.click();
	}

	public void enterNonExistingResourceAndVerifyResult(String resourceName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.clear();
		searchButton.sendKeys(resourceName);
		log("resource name : " + resourceName + " entered in search textbox");
		clickToSearchBtn();
	}

	public void enterResourceInSearchTextBox(String resourceName) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.clear();
		searchButton.sendKeys(resourceName);
		log("resource name : " + resourceName + " entered in search textbox");
		clickToSearchBtn();
	}

	public void verifyNoDataDisplayErrorMessage() {
		CCUtils.verifyAndWaitForElement(noDataDisplayMessage, driver);
		CCUtils.assertTrue(noDataDisplayMessage.isDisplayed(), "No Data to Display");
		log("No Data to Display error message is displayed");
	}

	public void verifyResourceDetailsTitle() {
		CCUtils.verifyAndWaitForElement(resourceDetailsTitle, driver);
		log("verified Resource details page title is present");
	}

	public void clickToSearchBtn() {
		CCUtils.verifyAndWaitForElement(searchBtn, driver);
		searchBtn.click();
		log("clicked to search button");
	}

	public void selectResource() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectResource, driver);
		selectResource.click();
		log("Clicked on first Resource");
	}

	public void clickAscResourceNameSortToDsc() {
		CCUtils.verifyAndWaitForElement(resourceNameAscSort, driver);
		resourceNameAscSort.click();
		log("clicked to ascending resource name sort");
	}

	public void verifyDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourceNameDscSort, driver);
		log("verified after clicking to acsendig sort resource name sort arrow goes downwards to descending sort");
	}

	public void clickDscResourceNameSortToAsc() {
		CCUtils.verifyAndWaitForElement(resourceNameDscSort, driver);
		resourceNameDscSort.click();
		log("clicked to descending resource name sort");
	}

	public void verifyAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourceNameAscSort, driver);
		log("verified after clicking to descendig sort resource name sort arrow goes upwards to ascending sort");
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

	public String getDefaultItemsPerPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		highlightElement(defaultItemsPerPage);
		return defaultItemsPerPage.getText().toString();
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

	public void clearButtonDisplayed() {
		CCUtils.verifyAndWaitForElement(filterClearButton, driver);
		CCUtils.assertTrue(filterClearButton.isDisplayed(), "verifying clear button is dispalyed or not");
		log("Verified clear button is present on resource search page");
	}

	public void clearApplyDisplayed() {
		CCUtils.verifyAndWaitForElement(filterApplyButton, driver);
		CCUtils.assertTrue(filterApplyButton.isDisplayed(), "verifying clear button is dispalyed or not");
		log("Verified apply button is present on provider search page");
	}

	public void clickToHideFiltersButton() {
		CCUtils.verifyAndWaitForElement(hideFiltersButton, driver);
		hideFiltersButton.click();
		log("clicked to Hide Button Filter");
	}

	public void verifyAllElementsOfFilterNotVisible() {
		CCUtils.verifyAndWaitForElement(hideFiltersButton, driver);
		if (hideFiltersButton.getText().contains("Show Filters")) {
			log("Show filters is displayed instead of hide filters");
		}
	}

	public void clickOnResourcesTab() {
		CCUtils.verifyAndWaitForElement(tabResources, driver);
		tabResources.click();
		log("Clicked on ResourcesTab");
	}

	public void verifyAddButtonIsEnabled() {
		logStep("Verifying add button is enabled or not");
		CCUtils.verifyAndWaitForElement(addButton, driver);
		CCUtils.assertTrue(addButton.isEnabled(), "Add button is disabled on provider page");
	}

	public void clickOnAddResourceButton() {
		log("Click on add new Resource Button");
		CCUtils.verifyAndWaitForElement(addButton, driver);
		addButton.click();
	}

	public void enterResourceName(String input) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(input);
		log("Entered input for Name : " + input);
	}

	public void enterConnectedProvider(String input) {
		CCUtils.verifyAndWaitForElement(textboxConnectedProvider, driver);
		textboxConnectedProvider.sendKeys(input);
		log("Entered input for Connected Provider : " + input);
		textboxConnectedProvider.sendKeys(Keys.BACK_SPACE);
		textboxConnectedProvider.sendKeys(String.valueOf(input.charAt(input.length() - 1)));
	}

	public void enterExternalId(String input) {
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.clear();
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.sendKeys(input);
		log("Entered input for ExternalId : " + input);
	}

	public void selectResourceType(String value) {
		clickOnResourceType();
		if (listOfResourceType.size() > 0) {
			if (value.equals("Person")) {
				javascriptClick(listOfResourceType.get(0));
				log("Clicked on Person Resource Type option");
			} else if (value.equals("Place")) {
				javascriptClick(listOfResourceType.get(1));
				log("Clicked on Place Resource Type option");
			} else if (value.equals("Thing")) {
				javascriptClick(listOfResourceType.get(2));
				log("Clicked on Thing Resource Type option");
			} else {
				log("Resource Type option not displayed on add a resource screen");
			}
		}
	};

	public void clickOnResourceType() {
		CCUtils.verifyAndWaitForElement(dropdownResourceType, driver);
		dropdownResourceType.click();
	}

	public void verifySaveAndAddButtonIsEnabled() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		logStep("Verifying Save and add button is enabled");
		CCUtils.assertEquals(saveButton.isEnabled(), true);
	}

	public void clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
		logStep("Clicked on Save and add button");
	}

	public void clickFirstInAutoSuggestion() throws InterruptedException {
		CCUtils.waitForElementPresence(selectFirstOption, driver);
		javascriptClick(selectFirstOption);
	}

	public void verifyAddNewResourcePageUi() throws Exception {
		verifyAddNewResourcesPageTitle();
		verifyNameLabelAndTextboxDefaultValue();
		verifyExternalIDLabelTextAndTextboxDefaultValue();
		verifyResourcesTypeLabelTextAndTextboxDefaultValue();
		verifyCancelButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
		verifySaveAndAddButtonIsDisabled();
	}

	public void verifyAddNewResourcesPageTitle() {
		CCUtils.verifyAndWaitForElement(labelAddNewResourcePageTitle, driver);
		log("Verifying add new resource page title");
		CCUtils.assertEquals(labelAddNewResourcePageTitle.getText().trim(), "Add a Resource");
	}

	public void verifyNameLabelAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labelName, driver);
		log("Verifying name label text");
		CCUtils.assertEquals(labelName.getText().trim(), "Name *");
		log("Name label text is " + labelName.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxtName, driver);
		log("Verifying input text placeholder is displayed for name textbox field");
		CCUtils.assertEquals(textboxtName.getAttribute("placeholder").trim(), "Input Name");
		log("Box text for name textbox field is " + textboxName.getAttribute("placeholder").trim());
	}

	public void verifyExternalIDLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labeltextboxExternalId, driver);
		log("Verifying ExternalId label text");
		CCUtils.assertEquals(labeltextboxExternalId.getText().trim(), "External ID *");
		log("ExternalId label text is " + labeltextboxExternalId.getText().trim());
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		log("Verifying input text placeholder is displayed for External Id textbox field");
		CCUtils.assertEquals(textboxExternalId.getAttribute("placeholder").trim(), "Input External Id");
		log("Box text for ExternalId textbox field is " + textboxExternalId.getAttribute("placeholder").trim());
	}

	public void verifyResourcesTypeLabelTextAndTextboxDefaultValue() {
		CCUtils.verifyAndWaitForElement(labeltextboxResourceType, driver);
		log("Verifying Resourec Type label text");
		CCUtils.assertEquals(labeltextboxResourceType.getText().trim(), "Resource Type *");
		log("Resource Type label text is " + labeltextboxResourceType.getText().trim());
		CCUtils.verifyAndWaitForElement(dropdownResourceType, driver);
	}

	public void verifyHundredMaximumCharactersReachedErrorMessage(String resourceName) {
		log("Enter more than 100 characters in Resource Name textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(resourceName);
		log("Entered text in Resource Name field is " + resourceName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterName, driver);
		CCUtils.assertEquals(errorMaximumCharacterName.getText().trim(), "Maximum characters reached");
		log("Error message displayed for Resource Name field is " + errorMaximumCharacterName.getText().trim());
	}

	public void verifyFiftyMaximumCharactersReachedErrorMessage(String ExternalId) {
		log("Enter more than 50 characters in ExternalId textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.sendKeys(ExternalId);
		log("Entered text in ExternalId field is " + ExternalId);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacterExternalId, driver);
		CCUtils.assertEquals(errorMaximumCharacterExternalId.getText().trim(), "Maximum characters reached");
		log("Error message displayed for ExternalId field is " + errorMaximumCharacterExternalId.getText().trim());
	}

	public void enterResourceType(String input) {
		CCUtils.verifyAndWaitForElement(dropdownResourceType, driver);
		dropdownResourceType.sendKeys(input);
		log("Entered input for Resource : " + input);
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}

	public void clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		buttonCancel.click();
		log("Clicked on Cancel button");
	}

	public void clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		crossIcon.click();
		log("Clicked on X icon");
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		log("Verifying cancel button is displayed on add a Resource page");
		if (buttonCancel.isDisplayed() == true) {
			log("Verifying cancel button is enabled");
			CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
			log("Verifying cancel button text");
			CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
		} else {
			log("Cancel button is not displayed on add a Resource page");
		}
	}

	public void verifySaveAndAddButtonIsDisabled() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		logStep("Verifying Save and add button is disabled");
		CCUtils.assertEquals(saveButton.isEnabled(), false);
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		log("Verifying cross icon is displayed on add a Resource page");
		if (crossIcon.isDisplayed() == true) {
			log("Verifying cross icon is enabled");
			CCUtils.assertTrue(crossIcon.isEnabled(), "Cross icon is disabled");
		} else {
			log("cross icon is not displayed on add a Resource page");
		}
	}

	public void verifyAddResourcePopupTitle() {
		CCUtils.verifyAndWaitForElement(labelAddNewResourcePageTitle, driver);
		log("Verifying create Resource page title");
		CCUtils.assertEquals(labelAddNewResourcePageTitle.getText().trim(), "Add a Resource");
	}

	public void verifyListOfResourceMatchedSearchResult(List<String> resourceName) throws AWTException {
		CCUtils.scrollPageToEnd(driver);
		CCUtils.verifyAndWaitForPresenceOfListOfElement(listOfResourceName, driver);
		log("print the size of list of Resources : " + listOfResourceName.size());
		for (int i = 0; i < resourceName.size(); i++) {
			for (int j = 0; j < listOfResourceName.size(); j++) {
				CCUtils.verifyAndWaitForElement(listOfResourceName.get(j), driver);
				if (listOfResourceName.get(j).getText().toString().trim().contains(resourceName.get(0).trim())) {
					log(" actual rsources from web table : " + listOfResourceName.get(j).getText().toString().trim()
							+ " is EQUAL to expected resources : " + resourceName.get(0).trim());
				} else
					log(" actual location from web table : " + listOfResourceName.get(j).getText().toString().trim()
							+ " is NOT EQUAL to expected resource : " + resourceName.get(0).trim());
			}
		}
	}

	public void clickToBackButton() throws AWTException {
		CCUtils.scrollPageToStart();
		CCUtils.verifyElementToBeClickable(backIcon, driver);
		CCUtils.verifyAndWaitForElement(backIcon, driver);
		backIcon.click();
		log("clicked to back button in resource details page");
	}

	public void clickToEntepriseArrow() {
		CCUtils.verifyAndWaitForElement(entepriseArrow, driver);
		entepriseArrow.click();
		log("clicked to enteprise Arrow");
	}

	public void clickToClearFilterButton() {
		CCUtils.verifyAndWaitForElement(filterClearButton, driver);
		filterClearButton.click();
		log("clicked to clear filter button");
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

	public void clickAscResourcesStatusSortToDsc() {
		CCUtils.verifyAndWaitForElement(resourcesStatusLabel, driver);
		javascriptClick(resourcesStatusLabel);
		log("clicked on resources status column label");
		CCUtils.verifyAndWaitForElement(resourcesAscSort, driver);
		resourcesAscSort.click();
		log("clicked to ascending Resources status sort");
	}

	public void verifyResourcesStatusDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourcesDscSort, driver);
		log("verified after clicking to acsendig sort Resources status sort arrow goes downwards to descending sort");
	}

	public void clickDscResourcesStatusSortToAsc() {
		CCUtils.verifyAndWaitForElement(resourcesDscSort, driver);
		resourcesDscSort.click();
		log("clicked to descending Resources status sort");
	}

	public void verifyResourcesStatusAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourcesAscSort, driver);
		log("verified after clicking to descendig sort Resources status sort arrow goes upwards to ascending sort");
	}

	public void clickAscResourcesFirstNameSortToDsc() {
		CCUtils.verifyAndWaitForElement(resourcesNameLabel, driver);
		javascriptClick(resourcesNameLabel);
		CCUtils.verifyAndWaitForElement(resourcesAscSort, driver);
		resourcesAscSort.click();
		log("clicked to ascending Resource first name sort");
	}

	public void verifyResourcesFirstNameDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourcesDscSort, driver);
		log("verified after clicking to acsendig sort resource first name sort arrow goes downwards to descending sort");
	}

	public void clickDscResourcesFirstNameSortToAsc() {
		CCUtils.verifyAndWaitForElement(resourcesDscSort, driver);
		resourcesDscSort.click();
		log("clicked to descending Resource first name sort");
	}

	public void verifyResourcesFirstNameAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourcesAscSort, driver);
		log("verified after clicking to descendig sort resource first name sort arrow goes upwards to ascending sort");
	}

	public void clickAscResourceTypeSortToDsc() {
		log("clicked on Resource type  column label");
		CCUtils.verifyAndWaitForElement(resourcesAscSort, driver);
		resourcesAscSort.click();
		log("clicked to ascending Resource type name sort");
	}

	public void verifyResourceTypeDescendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourcesDscSort, driver);
		log("verified after clicking to acsendig sort Resource type name sort arrow goes downwards to descending sort");
	}

	public void clickDscResourceTypeSortToAsc() {
		CCUtils.verifyAndWaitForElement(resourcesDscSort, driver);
		resourcesDscSort.click();
		log("clicked to descending Resource type name sort");
	}

	public void verifyResourceTypeAscendingSortIsVisible() {
		CCUtils.verifyAndWaitForElement(resourcesAscSort, driver);
		log("verified after clicking to descendig sort Resource type name sort arrow goes upwards to ascending sort");
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

	public void clickFirstPageButton() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		log("click to First Page button");
		buttonFirstPage.click();
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

	public String getTotalNoOfResources() {
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

	public void clickNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		buttonNextPage.click();
		log("clicked to Next Page button");
	}

	public boolean isFirstPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonFirstPage, driver);
		return buttonStatus;
	}

	public boolean isNextPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonNextPage, driver);
		return buttonStatus;
	}

	public boolean isPreviousPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonPreviousPage, driver);
		return buttonStatus;
	}

	public void checkNextPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		CCUtils.scrollPageToEnd(driver);
		int noOfProviders = Integer.valueOf(getTotalNoOfResources());
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
		int noOfProviders = Integer.valueOf(getTotalNoOfResources());
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
		int noOfProviders = Integer.valueOf(getTotalNoOfResources());
		log("Total number of Resources is : " + noOfProviders);
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
			log("Verifying all the Paginations should be disabled Toal number of Resources is less that Items per page so all Providers displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkFirstPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfResources = Integer.valueOf(getTotalNoOfResources());
		log("Total number of Resources is : " + noOfResources);
		checkLastPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
		if (isFirstPageButtonDisabled() == true) {
			clickFirstPageButton();
			String expectedPagination = "1 – " + itemsPerPageValue;
			CCUtils.assertEquals(getDefaultPagination(), expectedPagination);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
		} else {
			log("Verifying all the Paginations should be disabled Toal number of Resources is less that Items per page so all Providers displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}
	
	public void clickOnChatToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolChat);
        CCUtils.verifyAndWaitForElement(connectedSolChat, driver);
        CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "false");
        log("The toggle is not checked yet as aria-checked attribute value is "
                + connectedSolChat.getAttribute("aria-checked").toString().trim());
        log("click On Chat Toggle");
        javascriptClick(connectedSolChat);
        CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "true");
        log("The toggle is checked as aria-checked attribute value is "
                + connectedSolChat.getAttribute("aria-checked").toString().trim());
	}

	public void clickOffChatToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolChat);
		CCUtils.verifyAndWaitForElement(connectedSolChat, driver);
		CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "true");
		log("The toggle is checked as aria-checked attribute value is "
				+ connectedSolChat.getAttribute("aria-checked").toString().trim());
		log("click to chat toggle");
		javascriptClick(connectedSolChat);
		CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "false");
		log("The toggle is not checked yet as aria-checked attribute value is "
				+ connectedSolChat.getAttribute("aria-checked").toString().trim());
	}
	
	public void connectedSolutions() {
			CCUtils.verifyAndWaitForElement(connectedsolutions, driver);
			connectedsolutions.click();
			log("clicked to enteprise Arrow");
		}

	
	public void verifyListOfResourcesMatchedSearchResult(List<String> ResourceName) throws AWTException {
		CCUtils.scrollPageToEnd(driver);
		CCUtils.verifyAndWaitForPresenceOfListOfElement(listOfResourceName, driver);
		log("print the size of list of resources : " + listOfResourceName.size());
		for (int i = 0; i < ResourceName.size(); i++) {
			for (int j = 0; j < listOfResourceName.size(); j++) {
				CCUtils.verifyAndWaitForElement(listOfResourceName.get(j), driver);
				if (listOfResourceName.get(j).getText().toString().trim().contains(ResourceName.get(0).trim())) {
					log(" actual resources from web table : " + listOfResourceName.get(j).getText().toString().trim()
							+ " is EQUAL to expected resources : " + ResourceName.get(0).trim());
				} else
					log(" actual resources from web table : " + listOfResourceName.get(j).getText().toString().trim()
							+ " is NOT EQUAL to expected resources : " + ResourceName.get(0).trim());
			}
		}
	}		
}