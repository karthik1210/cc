// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.util.CCUtils;

public class ResourceDetailsPage extends CCMainPage {

	public ResourceDetailsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "searchOrgInput")
	private WebElement searchButton;

	@FindBy(id = "treeNodeName")
	private List<WebElement> listOfPracTabs;

	@FindBy(xpath = "//img[@alt='prs-image']")
	private WebElement blankImage;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement uploadResourceImage;

	@FindBy(xpath = "//div/span[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImage;

	@FindBy(xpath = "//div[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImageInactive;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']//td[1]")
	private WebElement listOfResourceFirstName;

	@FindBy(xpath = "//span[text()='Resource Details']")
	private WebElement resourceDetailsTitle;

	public String invalidImagePath = "/src/test/resources/data-driven/images/223267.jpg";

	@FindBy(xpath = "//p[contains(text(),'Invalid file type')]")
	private WebElement invalidFileType;

	@FindBy(xpath = "//span[normalize-space(text())='Success! Successfully removed image']")
	private WebElement toasterMessage;

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMess;

	@FindBy(id = "searchButton")
	private WebElement searchBtn;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectResource;

	@FindBy(xpath = "//span[@class='slider round']")
	private WebElement toggleButton;

	@FindBy(xpath = "//span[normalize-space(text())='Success! New practice has been successfully unassigned']")
	private WebElement toasterMsg;

	@FindBy(xpath = "//span[normalize-space(text())='Success! New practice has been successfully assigned']")
	private WebElement toasterMsgOne;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button//span//mat-icon")
	private WebElement entepriseArrow;

	@FindBy(xpath = "//p[normalize-space(text())='Connected Solutions']")
	private WebElement connectedSolutionsLabel;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//mat-icon[text()=' chat ']//..//following::div[@id='toggleButton']//child::input[@type='checkbox'])[1]")
	private WebElement connectedSolChat;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Pay ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolPay;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Virtual Visits ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolVirtualVisits;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' PSS ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolPss;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Patient Portal ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolPortal;

	@FindBy(xpath = "(//div[@class='connected-solution-container']//div[@id='solutionGroup']//div//p[text()=' Engage ']//..//following::div[@id='toggleButton'])[1]")
	private WebElement connectedSolEngage;

	@FindBy(xpath = "//div[@aria-label='Connected Locations']")
	private WebElement connectedLocationSectionTitle;

	@FindBy(id = "addLocation")
	private WebElement addLocationButton;

	@FindBy(xpath = "//div[@role='combobox']//input[@id='Location']")
	private WebElement locationInput;

	@FindBy(xpath = "//div[@role='combobox']")
	private WebElement listBox;

	@FindBy(xpath = "//div//input[@id='inputCheckbox']//following::label[@id='locationName']")
	private WebElement locationCheckBox;

	@FindBy(xpath = "(//div[@aria-label='Connected Locations']//following::td[@id='no-result'])[1]")
	private WebElement noDataDisplayConnectedLocations;

	@FindBy(xpath = "//span[@id='cancel-text']")
	private WebElement crossIcon;

	@FindBy(xpath = "//label[@class='input']")
	private WebElement labelPopup;

	@FindBy(xpath = "//div[@class='title']")
	private WebElement addResourceTitle;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement noItemsFound;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement crossPopupIcon;

	@FindBy(id = "cancel-icon")
	private WebElement cancelPopupButton;

	@FindBy(id = "save-icon")
	private WebElement addButtonResource;

	@FindBy(id = "removeResourceBtn")
	private WebElement removeConnection;

	@FindBy(xpath = "(//td[@role='gridcell'])[2]")
	private WebElement connectedResource;

	@FindBy(xpath = "//mat-icon[normalize-space(text())='cancel']")
	private WebElement inactiveStatus;

	@FindBy(xpath = "//mat-icon[normalize-space(text())='check_circle']")
	private WebElement activeStatus;

	public String validImagePath = "/src/test/resources/data-driven/images/logo.png";

	public String removeParticularLocation(String data) {
		return "//div[text()=' " + data + " ']//following::div//button[@mattooltip='Remove Connection']";
	}

	@FindBy(xpath = "//mat-icon[text()='arrow_back ']")
	private WebElement backIcon;

	@FindBy(xpath = "//div[contains(text(),'Resource Display Info')]")
	private WebElement labelSolutionsSection;

	@FindBy(id = "editDetailsBtn")
	private WebElement btnEdit;

	@FindBy(id = "copySettingsBtn")
	private WebElement labelCopySettings;

	@FindBy(xpath = "//span[contains(text(),'Resource Display Name (EN)')]")
	private WebElement labelResourceDisplayNameEn;

	@FindBy(xpath = "//span[contains(text(),'Resource Display Name (ES)')]")
	private WebElement labelResourceDisplayNameEs;

	@FindBy(id = "saveDetailsBtn")
	private WebElement saveSolutionInfo;

	@FindBy(id = "resourceNameENInput")
	private WebElement textboxResourceDisplayNameEn;

	@FindBy(id = "resourceNameESInput")
	private WebElement textboxResourceDisplayNameEs;

	@FindBy(id = "resourceNameENMaxLength")
	private WebElement errorMessageDisplayNameEn;

	@FindBy(id = "resourceNameESMaxLength")
	private WebElement errorMessageDisplayNameEs;

	@FindBy(id = "cancelBtn")
	private WebElement btnCancel;

	@FindBy(xpath = "//div[(normalize-space(text())='Scheduling Resource Info')]")
	private WebElement SchedulingResourceInfoLabel;

	@FindBy(xpath = "//div[normalize-space(text())='Command Center ID']")
	private WebElement labelCommandCenterID;

	@FindBy(xpath = "//div[text()=' Resource ID ']")
	private WebElement labelResourceID;

	@FindBy(xpath = "//div[text()=' Resource Type ']")
	private WebElement labelResourceType;
	
	@FindBy(xpath = "(//tbody[@role='rowgroup']//tr[@role='row']//td[1])")
	private WebElement resourceName;

	public void clickToSearchButton() {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.click();
		log("clicked to search button");
	}

	public void verifyResourceDetailsTitle() {
		CCUtils.verifyAndWaitForElement(resourceDetailsTitle, driver);
		log("verified Resource details page title is present");
	}

	public void verifySearchResourceFound(String listOfResource) {
		log("Entered in a search resource loop");
		CCUtils.scrollPageViaElement(driver, listOfResourceFirstName);
		log("scroll page");
		log("get the text of list : " + listOfResourceFirstName.getText().toString());
		log("verified presence of list resource");
		CCUtils.verifyAndWaitForElement(listOfResourceFirstName, driver);
		if ((listOfResourceFirstName.getText().toString().trim()).equalsIgnoreCase(listOfResource.trim())) {
			listOfResourceFirstName.click();
		}
	}

	public void uploadResourceImage() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElementPresence(blankImage, driver);
		String imageSrcValue = blankImage.getAttribute("src");
		log("print the value of src attribute of image tag : " + imageSrcValue);
		javascriptClick(uploadResourceImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadResourceImage.sendKeys(projectpath + "/src/test/resources/data-driven/images/logo.png");
		Assert.assertFalse(
				blankImage.getAttribute("src")
						.equals("https://qa-command-center.dev.pxp-mf-cc.nextgenaws.net/assets/img/Default_pfp.jpg"),
				"verifying src image value");
		log("verifying image src attribute value is changed");
		log("print the value of src attribute value : " + blankImage.getAttribute("src"));
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Verified that remove image button is visible after image is uploaded");
		log("Verified that Image is uploaded");
	}

	public void removeResourceImage() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Verified that remove image button is visible if image is already uploaded");
		removeImage.click();
		log("clicked to remove image button");
		CCUtils.verifyAndWaitForElement(removeImageInactive, driver);
		log("verified that remove image button got disabled");
	}

	public void verifyInvalidFileTypeErrorMessage() {
		CCUtils.verifyAndWaitForElement(invalidFileType, driver);
		log("Verified invalid file type error message is displayed");
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMess, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMess.isDisplayed() == true)
			CCUtils.assertEquals(toasterMess.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void clickToResource(String resourceName) throws InterruptedException {
		enterResourceInSearchTextBox(resourceName);
		clickToSearchBtn();
		selectResource();
	}

	public void enterResourceInSearchTextBox(String resourceName) {
		CCUtils.verifyAndWaitForElement(searchButton, driver);
		searchButton.clear();
		searchButton.sendKeys(resourceName);
		log("resource name : " + resourceName + " entered in serach textbox");
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

	public void clickOnToggleButton() {
		CCUtils.verifyAndWaitForElement(toggleButton, driver);
		toggleButton.click();
		log("clicked to toggle button");
	}

	public void verifyToasterMsgForEnable(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMsgOne, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMsgOne.isDisplayed() == true)
			CCUtils.assertEquals(toasterMsgOne.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void verifyToasterMsgForDisable(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMsg, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMsg.isDisplayed() == true)
			CCUtils.assertEquals(toasterMsg.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void clickToEntepriseArrow() {
		CCUtils.verifyAndWaitForElement(entepriseArrow, driver);
		entepriseArrow.click();
		log("clicked to enteprise Arrow");
	}

	public void verifyConnectedSolutionsLabel() {
		log("Verify connected Solutions Label");
		CCUtils.scrollPageViaElement(driver, connectedSolutionsLabel);
		CCUtils.verifyAndWaitForElement(connectedSolutionsLabel, driver);
		CCUtils.assertEquals(connectedSolutionsLabel.isDisplayed(), true);
	}

	public void clickOnEngageToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolEngage);
		CCUtils.verifyAndWaitForElement(connectedSolEngage, driver);
		log("click On Engage Toggle");
		javascriptClick(connectedSolEngage);
	}

	public void clickOffEngageToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolEngage, driver);
		log("click Off Engage Toggle");
		javascriptClick(connectedSolEngage);
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
		log("click Off Chat Toggle");
		javascriptClick(connectedSolChat);
		CCUtils.assertEquals(connectedSolChat.getAttribute("aria-checked").toString().trim(), "false");
		log("The toggle is not checked yet as aria-checked attribute value is "
				+ connectedSolChat.getAttribute("aria-checked").toString().trim());
	}

	public void clickOnPayToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPay);
		CCUtils.verifyAndWaitForElement(connectedSolPay, driver);
		log("click On Pay Toggle");
		javascriptClick(connectedSolPay);
	}

	public void clickOffPayToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPay, driver);
		log("click off pay toggle");
		javascriptClick(connectedSolPay);
	}

	public void clickOnVirtualVisitToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolVirtualVisits);
		log("click on virtual visit toggle");
		javascriptClick(connectedSolVirtualVisits);
	}

	public void clickOffVirtualVisitToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolVirtualVisits, driver);
		log("click of virtual visit toggle");
		javascriptClick(connectedSolVirtualVisits);
	}

	public void clickOnPSSToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPss);
		CCUtils.verifyAndWaitForElement(connectedSolPss, driver);
		log("click on Pss Toggle");
		javascriptClick(connectedSolPss);
	}

	public void clickOffPSSToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPss, driver);
		log("click Off PSS Toggle");
		javascriptClick(connectedSolPss);
	}

	public void clickOnPortalToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPortal);
		CCUtils.verifyAndWaitForElement(connectedSolPortal, driver);
		log("click On Portal Toggle");
		javascriptClick(connectedSolPortal);
	}

	public void clickOffPortalToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPortal, driver);
		log("click Off Portal Toggle");
		javascriptClick(connectedSolPortal);
	}

	public void selectPractice(String practiceName) {
		for (WebElement ele : listOfPracTabs) {
			CCUtils.verifyAndWaitForElement(ele, driver);
			CCUtils.assertEquals(ele.getText().toString().trim(), practiceName.trim());
			ele.click();
			log("clicked to practice tab named as : " + ele.getText().toString().trim());
		}
	}

	public void verifyConnectedLocationSection() {
		CCUtils.scrollPageViaElement(driver, connectedLocationSectionTitle);
		CCUtils.verifyAndWaitForElement(connectedLocationSectionTitle, driver);
		CCUtils.assertEquals(connectedLocationSectionTitle.getText().toString().trim(), "Connected Locations");
		log("verifying the presence of connected locations title");
	}

	public void clickToAddLocationButton() {
		CCUtils.verifyAndWaitForElement(addLocationButton, driver);
		addLocationButton.click();
		log("clicked to add location button");
	}

	public void enterTextInLocationTextBox(String location) {
		CCUtils.verifyAndWaitForElement(listBox, driver);
		CCUtils.assertEquals(listBox.getAttribute("aria-expanded").toString().trim(), "false");
		listBox.click();
		CCUtils.verifyElementToBeClickable(locationInput, driver);
		CCUtils.assertEquals(listBox.getAttribute("aria-expanded").toString().trim(), "true");
		verifyNoItemsFound();
		log("send values to listbox div");
		locationInput.sendKeys(location);
		log("entered location in input textbox");
	}

	public void clickToLocationCheckBox(String location) {
		CCUtils.verifyElementToBeClickable(locationCheckBox, driver);
		CCUtils.verifyAndWaitForElement(locationCheckBox, driver);
		CCUtils.assertEquals(locationCheckBox.getText().trim(), location);
		locationCheckBox.click();
		log("desired location " + location + " is selected");
	}

	public void verifyPopupTitle(String title) {
		CCUtils.verifyAndWaitForElement(addResourceTitle, driver);
		CCUtils.assertEquals(addResourceTitle.getText().trim(), title);
		log("verifying title of pop up");
	}

	public void clickToRemoveParticularLocation(String locationName) {
		WebElement locationData = driver.findElement(By.xpath(removeParticularLocation(locationName)));
		CCUtils.verifyAndWaitForElement(locationData, driver);
		locationData.click();
		log("clicked to remove resource icon for resource : " + locationName);
	}

	public void verifyNoDataDisplayConnectedLocationsMessage() {
		CCUtils.verifyAndWaitForElement(noDataDisplayConnectedLocations, driver);
		log("No data to display message dispalyed in Connected locations");
	}

	public void verifyPopupLabel() {
		CCUtils.verifyAndWaitForElement(labelPopup, driver);
		log("pop up is displayed in add popup");
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(noItemsFound, driver);
		log("no items found message found");
	}

	public void clickToAddButtonInPopup() {
		CCUtils.verifyAndWaitForElement(addButtonResource, driver);
		CCUtils.verifyAndWaitForElement(addResourceTitle, driver);
		addResourceTitle.click();
		addButtonResource.click();
		log("clicked to Add button of pop up");
	}

	public void verifyCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossPopupIcon, driver);
		log("cross icon is displayed in pop up");
	}

	public void verifyCancelButton() {
		CCUtils.verifyAndWaitForElement(cancelPopupButton, driver);
		log("cancel button is displayed in pop up");
	}

	public void verifyAddButton() {
		CCUtils.verifyAndWaitForElement(addButtonResource, driver);
		CCUtils.assertEquals(addButtonResource.getAttribute("disabled"), "true");
		log("cancel button is displayed in pop up");
	}

	public void verifyConnectedResourceInWebTable() {
		CCUtils.verifyAndWaitForElement(connectedResource, driver);
		CCUtils.assertEquals(connectedResource.isDisplayed(), true);
		log("Connected Resource is present in connected scheduling resource table");
	}

	public void clickToRemoveConnectionButton() {
		CCUtils.verifyAndWaitForElement(removeConnection, driver);
		removeConnection.click();
		log("clicked to remove connection");
	}

	public void verifyAddLocationsPopupLabel() {
		CCUtils.verifyAndWaitForElement(labelPopup, driver);
		log("pop up is displayed in add popup");
	}

	public void verifyConnectedLocationsPopupDetails() throws InterruptedException, AWTException {
		verifyPopupTitle("Add Location(s)");
		verifyAddLocationsPopupLabel();
		verifyCrossIcon();
		verifyCancelButton();
		verifyAddButton();
		log("Verified Connected Locations Popup Details");
	}

	public void verifyConnectedLocationsSection() throws InterruptedException, AWTException {
		verifyConnectedLocationsSectionLabel();
		verifyConnectedLocationsAddButtonIsDisplayed();
		log("Verified Connected Locations Section");
	}

	public void verifyConnectedLocationsSectionLabel() {
		CCUtils.scrollPageViaElement(driver, connectedLocationSectionTitle);
		CCUtils.verifyAndWaitForElement(connectedLocationSectionTitle, driver);
		CCUtils.assertEquals(connectedLocationSectionTitle.getText().toString().trim(), "Connected Locations");
		log("verified the presence of connected locations title");
	}

	public void verifyConnectedLocationsAddButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(addLocationButton, driver);
		log("Verified Add Button Is Displayed");
	}

	public void clickToBackButton() throws AWTException {
		CCUtils.scrollPageToStart();
		CCUtils.verifyElementToBeClickable(backIcon, driver);
		CCUtils.verifyAndWaitForElement(backIcon, driver);
		backIcon.click();
		log("clicked to back button in resource details page");
	}

	public void verifyInactiveStatus() {
		CCUtils.verifyAndWaitForElement(inactiveStatus, driver);
		CCUtils.assertEquals(inactiveStatus.isDisplayed(), true);
		log("Verified inactive status");
	}

	public void verifyActiveStatus() {
		CCUtils.verifyAndWaitForElement(activeStatus, driver);
		CCUtils.assertEquals(activeStatus.isDisplayed(), true);
		log("Verified active status");
	}

	public void verifySearchResourcesFound(String searchedResource) {
		CCUtils.verifyAndWaitForElement(resourceName, driver);
		CCUtils.assertEquals(resourceName.isDisplayed(), true);
	}

	public void uploadResourcesImage() throws InterruptedException, AWTException {
		javascriptClick(uploadResourceImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadResourceImage.sendKeys(projectpath + validImagePath);
		log("Verifying Image uploaded");
	}
	
	public void uploadResourceInvalidImage() throws InterruptedException, AWTException {
		javascriptClick(uploadResourceImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadResourceImage.sendKeys(projectpath + invalidImagePath);
		log("Verifying resource image uploaded");
	}

	public void verifyResourcesInfoSection() throws InterruptedException, AWTException {
		verifySchedulingResourceInfoLabel();
		verifyResourceID();
		verifyResourceType();
		verifyCommandCenterID();
		log("Verified Resource Info Section");
	}

	public void verifySchedulingResourceInfoLabel() {
		CCUtils.scrollPageViaElement(driver, SchedulingResourceInfoLabel);
		CCUtils.verifyAndWaitForElement(SchedulingResourceInfoLabel, driver);
		log("Verifying Provider Info Label");
		CCUtils.assertEquals(SchedulingResourceInfoLabel.getText().trim(), "Scheduling Resource Info");
	}

	public void verifyCommandCenterID() {
		CCUtils.verifyAndWaitForElement(labelCommandCenterID, driver);
		CCUtils.assertEquals(labelCommandCenterID.getText().trim(), "Command Center ID");
		log("Verified Command Center ID");
	}

	public void verifyResourceID() {
		CCUtils.verifyAndWaitForElement(labelResourceID, driver);
		CCUtils.assertEquals(labelResourceID.isDisplayed(), true);
		log("Verified Resource ID");
	}

	public void verifyResourceType() {
		CCUtils.verifyAndWaitForElement(labelResourceType, driver);
		CCUtils.assertEquals(labelResourceType.isDisplayed(), true);
		log("Verified Resource Type");
	}

	public void verifyResourceDisplayInfoSection() throws InterruptedException, AWTException {
		log("Verifying Resources Display Info Section");
		verifySolutionsDisplayInfoSectionLabel();
		verifyEditButtonLabel();
		verifyCopySettingsButtonLabel();
		verifyResourceDisplayNameEnLabel();
		verifyResourceDisplayNameEsLabel();
	}

	public void verifySolutionsDisplayInfoSectionLabel() {
		CCUtils.scrollPageViaElement(driver, labelSolutionsSection);
		CCUtils.verifyAndWaitForElement(labelSolutionsSection, driver);
		log("Verifying Solutions Display Info Label");
		CCUtils.assertEquals(labelSolutionsSection.getText().trim(), "Resource Display Info");
	}

	public void verifyEditButtonLabel() {
		CCUtils.verifyAndWaitForElement(btnEdit, driver);
		log("Verifying Edit Button Label");
		CCUtils.assertEquals(btnEdit.isDisplayed(), true);
	}

	public void verifyCopySettingsButtonLabel() {
		CCUtils.verifyAndWaitForElement(labelCopySettings, driver);
		log("Verifying Copy Settings Button Label");
		CCUtils.assertEquals(labelCopySettings.getText().trim(), "COPY SETTINGS");
	}

	public void verifyResourceDisplayNameEnLabel() {
		CCUtils.verifyAndWaitForElement(labelResourceDisplayNameEn, driver);
		log("Verifying Provider Display Name (EN) Label");
		CCUtils.assertEquals(labelResourceDisplayNameEn.getText().trim(), "Resource Display Name (EN)");
	}

	public void verifyResourceDisplayNameEsLabel() {
		CCUtils.verifyAndWaitForElement(labelResourceDisplayNameEs, driver);
		log("Verifying Resource Display Name (ES) Label");
		CCUtils.assertEquals(labelResourceDisplayNameEs.getText().trim(), "Resource Display Name (ES)");
	}

	public void clickOnEditButton() {
		CCUtils.verifyAndWaitForElement(btnEdit, driver);
		btnEdit.click();
		log("Clicked on Edit Button");
	}

	public void clickOnSaveButton() {
		CCUtils.verifyAndWaitForElement(saveSolutionInfo, driver);
		saveSolutionInfo.click();
		log("Clicked on Save Button");
	}

	public void enterResourceDisplayNameEn(String input) {
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEn, driver);
		textboxResourceDisplayNameEn.clear();
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEn, driver);
		textboxResourceDisplayNameEn.sendKeys(input);
		log("Entered input for Provider Display Name En : " + input);
	}

	public void enterResourceDisplayNameEs(String input) {
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEs, driver);
		textboxResourceDisplayNameEs.clear();
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEs, driver);
		textboxResourceDisplayNameEs.sendKeys(input);
		log("Entered input for Provider Display Name Es : " + input);
	}

	public void verifyMaximumCharactersReachedErrorMessage(String resourceDisplayNameEn, String resourceDisplayNameEs) {
		log("Enter more than 100 characters in resourceDisplayNameEn textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEn, driver);
		textboxResourceDisplayNameEn.clear();
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEn, driver);
		textboxResourceDisplayNameEn.sendKeys(resourceDisplayNameEn);
		log("Entered text in resourceDisplayNameEn field is " + resourceDisplayNameEn);
		CCUtils.verifyAndWaitForElement(errorMessageDisplayNameEn, driver);
		CCUtils.assertEquals(errorMessageDisplayNameEn.getText().trim(), "Maximum characters reached");
		log("Error message displayed for resourceDisplayNameEn field is " + errorMessageDisplayNameEn.getText().trim());
		log("Enter more than 100 characters in resourceDisplayNameEs textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEs, driver);
		textboxResourceDisplayNameEs.clear();
		CCUtils.verifyAndWaitForElement(textboxResourceDisplayNameEs, driver);
		textboxResourceDisplayNameEs.sendKeys(resourceDisplayNameEs);
		log("Entered text in ProviderDisplayNameEs field is " + resourceDisplayNameEs);
		CCUtils.verifyAndWaitForElement(errorMessageDisplayNameEs, driver);
		CCUtils.assertEquals(errorMessageDisplayNameEs.getText().trim(), "Maximum characters reached");
		log("Error message displayed for resourceDisplayNameEs field is " + errorMessageDisplayNameEs.getText().trim());
	}

	public void clickOnCancel() {
		CCUtils.verifyAndWaitForElement(btnCancel, driver);
		btnCancel.click();
		log("Clicked on Cancel Button");
	}

	public void verifyUpdateToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMess, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMess.isDisplayed() == true)
			CCUtils.assertEquals(toasterMess.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

}
