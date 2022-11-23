// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pxp.cc.util.CCUtils;

public class ProviderResourceAndStaffSearchPage extends CCMainPage {

	@FindBy(id = "generalLabel")
	private WebElement lableProvidersAndResourcesPageTitle;

	@FindBy(xpath = "//div[@role='tab']//div[text()='Providers']")
	private WebElement tabProvider;

	@FindBy(xpath = "//div[@role='tab']//div[text()='Resources']")
	private WebElement tabResources;

	@FindBy(xpath = "//div[@role='tab']//div[text()='Staff']")
	private WebElement tabStaff;

	@FindBy(id = "searchOrgInput")
	private WebElement searchTextbox;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']//td[1]")
	private WebElement listOfProviderFirstName;

	@FindBy(id = "searchButton")
	private WebElement searchButton;

	public ProviderResourceAndStaffSearchPage(WebDriver driver) {
		super(driver);
	}

	public void verifyProvidersAndResourcesPageTitle() {
		CCUtils.verifyAndWaitForElement(lableProvidersAndResourcesPageTitle, driver);
		CCUtils.assertEquals(lableProvidersAndResourcesPageTitle.getText().trim(), "Providers, Resources & Staff");
		log("Providers,Resources & Staff Title page is displayed");
	}

	public void verifyProviderByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(tabProvider, driver);
		CCUtils.assertEquals(tabProvider.getAttribute("aria-selected").toString(), "true");
		log("verified that provider tab by default selected");
	}

	public void enterProviderInSearchTextbox(String providerName) {
		CCUtils.verifyAndWaitForElement(searchTextbox, driver);
		searchTextbox.sendKeys(providerName);
		log("provider name entered in search textbox");
	}

	public void clickToSearchButton() {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.click();
		log("clicked to search button");
	}

	public void clickToResourcesTab() {
		CCUtils.verifyAndWaitForElement(tabResources, driver);
		tabResources.click();
		log("clicked to resource tab");
	}

	public void clickToProvidersTab() {
		CCUtils.verifyAndWaitForElement(tabProvider, driver);
		tabProvider.click();
		log("clicked to provider tab");
	}

	public void verifySearchProviderFound(String listOfProvider) {
		log("Entered in a search provider loop");
		CCUtils.scrollPageViaElement(driver, listOfProviderFirstName);
		log("scroll page");
		log("get the text of list : " + listOfProviderFirstName.getText().toString());
		log("verified presence of list provider");
		CCUtils.verifyAndWaitForElement(listOfProviderFirstName, driver);
		if ((listOfProviderFirstName.getText().toString().trim()).equalsIgnoreCase(listOfProvider.trim())) {
			listOfProviderFirstName.click();
		}
	}
}
