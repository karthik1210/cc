// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddNewProviderPage extends CCMainPage {
	public CommonMethods commonMethods;

	public AddNewProviderPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "openDialog")
	private WebElement addButton;

	@FindBy(id = "FirstNameInput")
	private WebElement textboxFirstName;

	@FindBy(id = "LastNameInput")
	private WebElement textboxLastName;

	@FindBy(id = "ExternalIdInput")
	private WebElement textboxExternalId;

	@FindBy(id = "CredentialsInput")
	private WebElement textboxCredentials;

	@FindBy(xpath = "(//div[@role='combobox'])[5]")
	private WebElement dropdownGender;

	@FindBy(id = "addNewProviderSave")
	private WebElement saveButton;

	@FindBy(id = "searchOrgInput")
	private WebElement Searchicon;

	@FindBy(id = "searchOrgInput")
	private WebElement textboxSearch;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectProvider;

	@FindBy(id = "editDetailsBtn")
	private WebElement clickOnEdit;

	@FindBy(id = "providerEmailInput")
	private WebElement providerEmail;

	@FindBy(id = "saveDetailsBtn")
	private WebElement saveBtn;

	@FindBy(id = "selectStatus")
	private WebElement textboxSearchClientName;

	@FindBy(xpath = "//mat-icon[contains(text(),'arrow_back')]")
	private WebElement backArrow;

	@FindBy(xpath = "(//input[@id='searchFilterInput'])[1]")
	private WebElement textboxSearchProviderName;

	@FindBy(xpath = "//span[contains(text(),' Providers, Resources & Staff')]")
	private WebElement lableProvidersAndResourcesPageTitle;

	@FindBy(xpath = "(//mat-option[@id='searchList'])[1]")
	private WebElement selectFirstOption;

	public void verifyProvidersAndResourcesPageTitle() {
		CCUtils.verifyAndWaitForElement(lableProvidersAndResourcesPageTitle, driver);
		CCUtils.assertEquals(lableProvidersAndResourcesPageTitle.getText().trim(), "Providers, Resources & Staff");
		log("lableProvidersAndResourcesPageTitle page title is displayed");
	}

	public void verifyAddProvidersAndResourcesButton() {
		CCUtils.verifyAndWaitForElement(addButton, driver);
		log(" Add Button is displayed");
	}

	public void clickOnAddLocationButton() {
		CCUtils.verifyAndWaitForElement(addButton, driver);
		addButton.click();
		log("Verifying add new location Button");
	}

	public void enterFirstName(String input) {
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.clear();
		CCUtils.verifyAndWaitForElement(textboxFirstName, driver);
		textboxFirstName.sendKeys(input);
		log("Entered input for First Name : " + input);
	}

	public void enterLastName(String input) {
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.clear();
		CCUtils.verifyAndWaitForElement(textboxLastName, driver);
		textboxLastName.sendKeys(input);
		log("Entered input for last Name : " + input);
	}

	public void enterCredentials(String input) {
		CCUtils.verifyAndWaitForElement(textboxCredentials, driver);
		textboxCredentials.clear();
		CCUtils.verifyAndWaitForElement(textboxCredentials, driver);
		textboxCredentials.sendKeys(input);
		log("Entered input for textboxCredentials : " + input);
	}

	public void enterExternalId(String input) {
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.clear();
		CCUtils.verifyAndWaitForElement(textboxExternalId, driver);
		textboxExternalId.sendKeys(input);
		log("Entered input for ExternalId : " + input);
	}

	private String xpathRequiredGender = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForGender(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectGender(String gender) {
		WebElement state = prepareDynamicXpathForGender(xpathRequiredGender, gender);
		CCUtils.verifyAndWaitForElement(state, driver);
		state.click();
		log("Clicked on : " + gender);
	}

	public void addGender(String gender) throws InterruptedException {
		log("click to dropdown of client type");
		dropdownGender.click();
		selectGender(gender);
	}

	public void clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		saveButton.click();
	}

	public void addProvider(String firstName) throws InterruptedException {
		verifyProvidersAndResourcesPageTitle();
		verifyAddProvidersAndResourcesButton();
		clickOnAddLocationButton();
		enterFirstName(firstName);
		enterLastName(CCUtils.generateAlphaNumericString(18));
		enterExternalId(CCUtils.generateAlphaNumericString(8));
		enterCredentials(CCUtils.generateAlphaNumericString(8));
		addGender("Female");
		clickOnSaveAndAddButton();
	}

	public void clickSearchIcon() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(Searchicon, driver);
		Searchicon.click();
	}

	public void enterSearchText(String firstName) {
		CCUtils.verifyAndWaitForElement(textboxSearch, driver);
		textboxSearch.sendKeys(firstName);
		log("Entered text in search :" + firstName);
	}

	public void selectProvider() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(selectProvider, driver);
		selectProvider.click();
		log("Clicked on first Provider");
	}

	public void clickOnEdit() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(clickOnEdit, driver);
		clickOnEdit.click();
	}

	public void enterProviderEmail(String email) {
		CCUtils.verifyAndWaitForElement(providerEmail, driver);
		providerEmail.sendKeys(email);
	}

	public void clickOnSave() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(saveBtn, driver);
		saveBtn.click();
	}

	public void clickOnBackArrow() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(backArrow, driver);
		backArrow.click();
	}

	public void editProvider() throws InterruptedException {
		clickOnEdit();
		enterProviderEmail(CCUtils.generateNewEmail(12));
		clickOnSave();
		clickOnBackArrow();
	}

	public void verifyProvidersAndResourcesTitle() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(lableProvidersAndResourcesPageTitle, driver);
		CCUtils.assertEquals(lableProvidersAndResourcesPageTitle.isDisplayed(), true);
	}

	public void clickFirstOption() {
		CCUtils.verifyAndWaitForElement(selectFirstOption, driver);
		if (selectFirstOption.isDisplayed()) {
			log("Clicked on " + selectFirstOption.getText() + " in Auto Suggestion ");
			javascriptClick(selectFirstOption);
		} else {
			log(selectFirstOption.getText() + " is not present in Auto Suggestion");
			CCUtils.assertTrue(false, "Searched text not present in client");
		}
	}

	public void enterProviderName(String providerName) {
		CCUtils.verifyAndWaitForElement(textboxSearchProviderName, driver);
		textboxSearchProviderName.click();
		textboxSearchProviderName.sendKeys(providerName);
		log("Entered input for search provider field : " + providerName);
	}

	public void searchProvider(String providerName) throws NullPointerException, InterruptedException {
		enterProviderName(providerName);
		clickFirstOption();
		CCUtils.pressEnterKey();
	}
}
