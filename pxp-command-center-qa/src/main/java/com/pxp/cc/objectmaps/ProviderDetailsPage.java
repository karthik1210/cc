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

public class ProviderDetailsPage extends CCMainPage {

	@FindBy(xpath = "//input[@type='file']")
	private WebElement uploadProviderImage;

	@FindBy(xpath = "//div//span[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImage;

	@FindBy(xpath = "//div[contains(text(),'REMOVE IMAGE')]")
	private WebElement removeImageInactive;

	@FindBy(xpath = "//p[contains(text(),'Invalid file type')]")
	private WebElement invalidFileType;

	@FindBy(xpath = "//span[text()='Provider Details']")
	private WebElement providerDetailsTitle;

	@FindBy(xpath = "//img[@alt='prs-image']")
	private WebElement blankImage;

	@FindBy(id = "searchOrgInput")
	private WebElement Searchicon;

	@FindBy(id = "searchOrgInput")
	private WebElement textboxSearch;

	@FindBy(xpath = "(//tr[@role='row'])[2]")
	private WebElement selectProvider;

	@FindBy(xpath = "//span[text()='Provider Details']")
	private WebElement titleProviderDetails;

	@FindBy(xpath = "//div[contains(text(),'Provider Display Info')]")
	private WebElement labelSolutionsSection;

	@FindBy(id = "editDetailsBtn")
	private WebElement btnEdit;

	@FindBy(id = "copySettingsBtn")
	private WebElement labelCopySettings;

	@FindBy(xpath = "//span[contains(text(),'Provider Display Name (EN)')]")
	private WebElement labelProviderDisplayNameEn;

	@FindBy(xpath = "//span[contains(text(),'Provider Display Name (ES)')]")
	private WebElement labelProviderDisplayNameEs;

	@FindBy(xpath = "//span[contains(text(),'Email Address')]")
	private WebElement labelEmail;

	@FindBy(xpath = "//span[contains(text(),'Phone Number')]")
	private WebElement labelPhoneNumber;

	@FindBy(id = "saveDetailsBtn")
	private WebElement saveSolutionInfo;

	@FindBy(id = "providerNameENInput")
	private WebElement textboxProviderDisplayNameEn;

	@FindBy(id = "providernameESInput")
	private WebElement textboxProviderDisplayNameEs;

	@FindBy(id = "providerEmailInput")
	private WebElement textboxEmailAddress;

	@FindBy(id = "providerMobileInput")
	private WebElement textboxPhoneNumber;

	@FindBy(id = "providerNameENMaxLength")
	private WebElement errorMessageDisplayNameEn;

	@FindBy(id = "providerNameESMaxLength")
	private WebElement errorMessageDisplayNameEs;

	@FindBy(id = "validEmailError")
	private WebElement errorMessageEmail;

	@FindBy(xpath = "//div[contains(text(),'valid Phone Number')]")
	private WebElement errorMessagePhoneNumber;

	@FindBy(id = "cancelBtn")
	private WebElement btnCancel;

	@FindBy(xpath = "//div[@role='tab']//div")
	private List<WebElement> listOfSystemTabs;

	@FindBy(id = "treeNodeName")
	private List<WebElement> listOfPracTabs;

	@FindBy(xpath = "//div[text()='Connected Scheduling Resources']")
	private WebElement labelConnectedResources;

	@FindBy(xpath = "//div//input[@id='inputCheckbox']//following::label[@id='resourceName']")
	private WebElement resourceCheckBox;

	@FindBy(id = "addResourceType")
	private WebElement connectedResourcesAdd;

	@FindBy(xpath = "//div[@class='title']")
	private WebElement addResourceTitle;

	@FindBy(id = "Resource")
	private WebElement inputTextResource;

	@FindBy(id = "save-icon")
	private WebElement addButtonResource;

	@FindBy(xpath = "//label[text()='Resource']")
	private WebElement popupLabelResource;

	@FindBy(id = "cancel-icon")
	private WebElement cancelPopupButton;

	@FindBy(xpath = "//span[text()=' clear ']")
	private WebElement crossPopupIcon;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement noItemsFound;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button/following-sibling::p")
	private List<WebElement> listOfEntTabs;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button//span//mat-icon")
	private WebElement arrowEntPractice;

	@FindBy(xpath = "//mat-icon[@mattooltip='Back']")
	private WebElement backIcon;

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;
	
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

	@FindBy(id = "removeResourceBtn")
	private WebElement removeConnection;

	@FindBy(xpath = "(//td[@role='gridcell'])[2]")
	private WebElement connectedResource;

	@FindBy(xpath = "//span[normalize-space(text())='Success! Successfully removed image']")
	private WebElement successToasterMessage;

	@FindBy(id = "resourceName")
	private WebElement columnResourceName;

	@FindBy(xpath = "(//div[normalize-space(text())='No data to display'])[1]")
	private WebElement noDataToDisplayMessage;

	@FindBy(xpath = "(//mat-list-item[@role='listitem'])[1]")
	private WebElement connectedLocation;

	@FindBy(xpath = "//mat-icon[normalize-space(text())='cancel']")
	private WebElement removeConnectionBtn;

	@FindBy(xpath = "//div[normalize-space(text())='Resource ID']")
	private WebElement columnResourceId;

	@FindBy(xpath = "//label[@class='switch']")
	private WebElement toggleButton;

	@FindBy(xpath = "//mat-tree-node[@role='treeitem']//button//span//mat-icon")
	private WebElement entepriseArrow;

	@FindBy(xpath = "//span[contains(text(),'Provider Details updated successfully!')]")
	private WebElement updateToasterMessage;

	@FindBy(xpath = "//div[(normalize-space(text())='Provider Info')]")
	private WebElement providerInfoLabel;

	@FindBy(xpath = "//div[normalize-space(text())='Provider ID']")
	private WebElement labelProviderID;

	@FindBy(xpath = "//div[normalize-space(text())='Command Center ID']")
	private WebElement labelCommandCenterID;

	@FindBy(xpath = "//div[normalize-space(text())='Staff ID']")
	private WebElement labelStaffID;

	@FindBy(xpath = "//p[@class='connected-solution-header']")
	private WebElement connectedSolutionsTitle;
	
	@FindBy(id = "removeLocationBtn0")
	private WebElement removeConnectionLocation;

	public ProviderDetailsPage(WebDriver driver) {
		super(driver);
	}

	public String removeParticularLocation(String data) {
		return "//div[text()=' " + data + " ']//following::div//button[@mattooltip='Remove Connection']";
	}

	public String finalResourceTableXpath(String data) {
		return "//td[@role='gridcell']//div[contains(text(),'" + data + "')]";
	}

	public String removeParticularResource(String data) {
		return "(//td[@role='gridcell']//div[contains(text(),'" + data
				+ "')]//following::td//div//button[@id='removeResourceBtn'])[1]";
	}

	public String invalidImagePath = "/src/test/resources/data-driven/images/223267.jpg";

	public void verifyImageIsAlreadyUploaded() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Image is already uploaded");
	}

	public String xpathRequiredClientType(String practiceName) {
		return "//mat-tree-node//p[contains(text(),'" + practiceName
				+ "')]//following::div[1]//label[@class='switch']//span";
	}

	public void verifyProviderDetailsTitle() {
		CCUtils.verifyAndWaitForElement(providerDetailsTitle, driver);
		log("verified Provider details page title is present");
	}

	public void uploadProviderImage() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(blankImage, driver);
		String imageSrcValue = blankImage.getAttribute("src");
		log("print the value of src attribute of image tag : " + imageSrcValue);
		javascriptClick(uploadProviderImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadProviderImage.sendKeys(projectpath + "/src/test/resources/data-driven/images/logo.png");
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

	public void removeProviderImage() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Verified that remove image button is visible if image is already uploaded");
		removeImage.click();
		log("clicked to remove image button");
		CCUtils.verifyAndWaitForElement(removeImageInactive, driver);
		log("verified that remove image button got disabled");
	}

	public void verifyInvalidFileTypeErrorMessage() {
		CCUtils.verifyAndWaitForElement(invalidFileType, driver);
		log("Verified invalid file type error message is dispalyed");
	}

	public void uploadProviderInvalidImage() throws InterruptedException, AWTException {
		javascriptClick(uploadProviderImage);
		log("Clicked on edit image");
		String projectpath = System.getProperty("user.dir");
		uploadProviderImage.sendKeys(projectpath + invalidImagePath);
		log("Verifying provider image uploaded");
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

	public void verifySolutionsDisplayInfoSection() throws InterruptedException, AWTException {
		log("Verifying Solutions Display Info Section");
		verifySolutionsDisplayInfoSectionLabel();
		verifyEditButtonLabel();
		verifyCopySettingsButtonLabel();
		verifyProviderDisplayNameEnLabel();
		verifyProviderDisplayNameEsLabel();
		verifyEmailAddressLabel();
		verifyPhoneNumberLabel();
	}

	public void verifySolutionsDisplayInfoSectionLabel() {
		CCUtils.scrollPageViaElement(driver, labelSolutionsSection);
		CCUtils.verifyAndWaitForElement(labelSolutionsSection, driver);
		log("Verifying Solutions Display Info Label");
		CCUtils.assertEquals(labelSolutionsSection.getText().trim(), "Provider Display Info");
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

	public void verifyProviderDisplayNameEnLabel() {
		CCUtils.verifyAndWaitForElement(labelProviderDisplayNameEn, driver);
		log("Verifying Provider Display Name (EN) Label");
		CCUtils.assertEquals(labelProviderDisplayNameEn.getText().trim(), "Provider Display Name (EN)");
	}

	public void verifyProviderDisplayNameEsLabel() {
		CCUtils.verifyAndWaitForElement(labelProviderDisplayNameEs, driver);
		log("Verifying Provider Display Name (ES) Label");
		CCUtils.assertEquals(labelProviderDisplayNameEs.getText().trim(), "Provider Display Name (ES)");
	}

	public void verifyEmailAddressLabel() {
		CCUtils.verifyAndWaitForElement(labelEmail, driver);
		log("Verifying Provider Email Address Label");
		CCUtils.assertEquals(labelEmail.getText().trim(), "Email Address");
	}

	public void verifyPhoneNumberLabel() {
		CCUtils.verifyAndWaitForElement(labelPhoneNumber, driver);
		log("Verifying Provider Phone Number Label");
		CCUtils.assertEquals(labelPhoneNumber.getText().trim(), "Phone Number");
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

	public void enterProviderDisplayNameEn(String input) {
		CCUtils.verifyAndWaitForElement(textboxProviderDisplayNameEn, driver);
		textboxProviderDisplayNameEn.clear();
		CCUtils.verifyAndWaitForElement(textboxProviderDisplayNameEn, driver);
		textboxProviderDisplayNameEn.sendKeys(input);
		log("Entered input for Provider Display Name En : " + input);
	}

	public void enterProviderDisplayNameEs(String input) {
		CCUtils.verifyAndWaitForElement(textboxProviderDisplayNameEs, driver);
		textboxProviderDisplayNameEs.clear();
		CCUtils.verifyAndWaitForElement(textboxProviderDisplayNameEs, driver);
		textboxProviderDisplayNameEs.sendKeys(input);
		log("Entered input for Provider Display Name Es : " + input);
	}

	public void enterEmailAddress(String input) {
		CCUtils.verifyAndWaitForElement(textboxEmailAddress, driver);
		textboxEmailAddress.clear();
		textboxEmailAddress.sendKeys(input);
		log("Entered input for Email Address : " + input);
	}

	public void enterPhoneNumber(String input) {
		CCUtils.verifyAndWaitForElement(textboxPhoneNumber, driver);
		textboxPhoneNumber.clear();
		textboxPhoneNumber.sendKeys(input);
		log("Entered input for Phone number : " + input);
	}

	public void verifyMaximumCharactersReachedErrorMessage(String providerDisplayNameEn, String ProviderDisplayNameEs,
			String invalidEmail, String phoneNumber) {
		log("Enter more than 100 characters in ProviderDisplayNameEn textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxProviderDisplayNameEn, driver);
		textboxProviderDisplayNameEn.sendKeys(providerDisplayNameEn);
		log("Entered text in ProviderDisplayNameEn field is " + providerDisplayNameEn);
		CCUtils.verifyAndWaitForElement(errorMessageDisplayNameEn, driver);
		CCUtils.assertEquals(errorMessageDisplayNameEn.getText().trim(), "Maximum characters reached");
		log("Error message displayed for ProviderDisplayNameEn field is " + errorMessageDisplayNameEn.getText().trim());
		log("Enter more than 100 characters in ProviderDisplayNameEs textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxProviderDisplayNameEs, driver);
		textboxProviderDisplayNameEs.sendKeys(ProviderDisplayNameEs);
		log("Entered text in ProviderDisplayNameEs field is " + ProviderDisplayNameEs);
		CCUtils.verifyAndWaitForElement(errorMessageDisplayNameEs, driver);
		CCUtils.assertEquals(errorMessageDisplayNameEs.getText().trim(), "Maximum characters reached");
		log("Error message displayed for ProviderDisplayNameEs field is " + errorMessageDisplayNameEs.getText().trim());
		log("Enter invalid Email Address textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxEmailAddress, driver);
		textboxEmailAddress.clear();
		textboxEmailAddress.sendKeys(invalidEmail);
		log("Entered input for Email Address : " + invalidEmail);
		CCUtils.verifyAndWaitForElement(errorMessageEmail, driver);
		CCUtils.assertEquals(errorMessageEmail.getText().trim(), "Please enter valid Email");
		log("Error message displayed for Email Address field is " + errorMessageEmail.getText().trim());
		log("Enter invalid phone number in textbox field and verify error message");
		CCUtils.verifyAndWaitForElement(textboxPhoneNumber, driver);
		textboxPhoneNumber.clear();
		textboxPhoneNumber.sendKeys(phoneNumber);
		log("Entered text in phone number field is " + phoneNumber);
		CCUtils.verifyAndWaitForElement(errorMessagePhoneNumber, driver);
		CCUtils.assertEquals(errorMessagePhoneNumber.getText().trim(), "Please enter valid Phone Number");
		log("Error message displayed for phone number field is " + errorMessagePhoneNumber.getText().trim());
	}

	public void clickOnCancel() {
		CCUtils.verifyAndWaitForElement(btnCancel, driver);
		btnCancel.click();
		log("Clicked on Cancel Button");
	}

	public void clickToSytemTab(String systemName) {
		for (WebElement element : listOfSystemTabs) {
			if (element.getText().toString().contains(systemName)) {
				CCUtils.verifyAndWaitForElement(element, driver);
				javascriptClick(element);

				log("clicked to system : " + systemName + " tab in location details page");
			}
		}
	}

	public void clickToPracticeTabAndToggleOn(String practiceName) throws AWTException {
		CCUtils.scrollPageToEnd(driver);
		CCUtils.verifyAndWaitForListOfElement(listOfPracTabs, driver);
		for (WebElement ele : listOfPracTabs) {
			log(ele.getText().toString());
			if (ele.getText().toString().trim().equals(practiceName)) {
				log("actual practice : " + ele.getText().toString() + " is equal to " + practiceName);
				if (ele.getAttribute("class").toString().trim().equals("treenode no-event")) {
					WebElement toggleButtonSpecific = driver
							.findElement(By.xpath(xpathRequiredClientType(practiceName)));
					CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
					toggleButtonSpecific.click();
					log("clicked on toggle button");
					log("Toggle ON button of practice : " + ele.getText() + " tab");
					CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
				}
			} else
				log("either practice " + ele.getText() + " does not match with " + practiceName
						+ " or toggle button for " + ele.getText() + " is already ON");
		}
	}

	public void clickToPracticeTabAndToggleOff(String practiceName) throws AWTException {
		CCUtils.scrollPageToEnd(driver);
		CCUtils.verifyAndWaitForListOfElement(listOfPracTabs, driver);
		for (WebElement ele : listOfPracTabs) {
			if ((ele.getText().toString().equals(practiceName))
					&& (ele.getAttribute("class").toString().equals("treenode cursor"))) {
				WebElement toggleButtonSpecific = driver.findElement(By.xpath(xpathRequiredClientType(practiceName)));
				CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
				toggleButtonSpecific.click();
				log("clicked on toggle button");
				log("clicked to toggle OFF button of practice : " + ele.getText() + " tab");
				CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
			} else
				log("toggle button is already OFF for practice " + ele.getText());
		}
	}

	public void verifyLabelConnectedSchedulingResources() {
		CCUtils.verifyAndWaitForElement(labelConnectedResources, driver);
		log("label Connected scheduling resources is present");
	}

	public void verifyDesiredResourceInWebTable(String resourceName) {
		WebElement resourceData = driver.findElement(By.xpath(finalResourceTableXpath(resourceName)));
		CCUtils.verifyAndWaitForElement(resourceData, driver);
		log("Resource data : " + resourceName + " is present in connected scheduling resource table");
	}

	public void clickToRemoveParticularResource(String resourceName) {
		WebElement resourceData = driver.findElement(By.xpath(removeParticularResource(resourceName)));
		CCUtils.verifyAndWaitForElement(resourceData, driver);
		resourceData.click();
		log("clicked to remove resource icon for resource : " + resourceName);
	}

	public void clickToResourceAddButton() {
		CCUtils.verifyAndWaitForElement(connectedResourcesAdd, driver);
		connectedResourcesAdd.click();
		log("clicked to + ADD button");
	}

	public void verifyAddResourceTitle() {
		CCUtils.verifyAndWaitForElement(addResourceTitle, driver);
		CCUtils.assertEquals(addResourceTitle.getText().trim(), "Add Resource(s)");
		log("verify add resource title is displayed");
	}

	public void enterResourceNameinInputText(String resourceName) {
		CCUtils.verifyAndWaitForElement(inputTextResource, driver);
		inputTextResource.sendKeys(resourceName);
		log("entered resource name in input text");
	}

	public void clickToResourceCheckBox() {
		CCUtils.verifyElementToBeClickable(resourceCheckBox, driver);
		CCUtils.verifyAndWaitForElement(resourceCheckBox, driver);
		resourceCheckBox.click();
		log("desired resource selected");
	}

	public void clickToAddButtonInPopup() {
		CCUtils.verifyAndWaitForElement(addButtonResource, driver);
		CCUtils.verifyAndWaitForElement(addResourceTitle, driver);
		addResourceTitle.click();
		addButtonResource.click();
		log("clicked to Add button of pop up");
	}

	public void verifyResourceLabel() {
		CCUtils.verifyAndWaitForElement(popupLabelResource, driver);
		CCUtils.assertEquals(popupLabelResource.getText().trim(), "Resource");
		log("label resource is displayed in pop up");
	}

	public void verifyCancelButton() {
		CCUtils.verifyAndWaitForElement(cancelPopupButton, driver);
		CCUtils.assertEquals(cancelPopupButton.getText().trim(), "Cancel");
		log("cancel button is displayed in pop up");
	}

	public void verifyAddButton() {
		CCUtils.verifyAndWaitForElement(addButtonResource, driver);
		CCUtils.assertEquals(addButtonResource.getAttribute("disabled"), "true");
		log("cancel button is displayed in pop up");
	}

	public void verifyCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossPopupIcon, driver);
		log("cross icon is displayed in pop up");
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(noItemsFound, driver);
		log("no items found message found");
	}

	public void clickToEnterpriseTabPracticeAndToggleOn(String enterpriseName, String practiceName)
			throws AWTException {
		for (WebElement element : listOfEntTabs) {
			CCUtils.verifyAndWaitForElement(element, driver);
			CCUtils.scrollPageToEnd(driver);
			if (element.getText().toString().trim().equals(enterpriseName.trim())) {
				if (arrowEntPractice.getText().contains("chevron_right")) {
					arrowEntPractice.click();
					log("clicked to arrow practice : " + arrowEntPractice.getText() + " tab");
				} else
					log("enterprise " + element.getText() + " is already expanded");
				for (WebElement ele : listOfPracTabs) {
					log("get the class of ele: " + ele.getAttribute("class").toString());
					if (ele.getText().toString().trim().equals(practiceName.trim())
							&& ele.getAttribute("class").toString().trim().equals("treenode no-event")) {
						WebElement toggleButtonSpecific = driver
								.findElement(By.xpath(xpathRequiredClientType(practiceName)));
						CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
						toggleButtonSpecific.click();
						log("clicked to toggle ON button of practice : " + ele.getText() + " tab");
					} else if (ele.getText().toString().contains(practiceName)
							&& ele.getAttribute("class").toString().contains("treenode cursor")) {
						log("toggle for this practice :" + ele.getText() + "is already ON");
					}
				}
			}
		}
	}

	public void clickToBackButton() {
		CCUtils.verifyAndWaitForElement(backIcon, driver);
		backIcon.click();
		log("clicked on Back Arrow");
	}

	public void clickToEnterpriseTabPracticeAndToggleOff(String enterpriseName, String practiceName)
			throws AWTException {
		log("entered to ent prac tab");
		for (WebElement element : listOfEntTabs) {
			log("entered to ent tab");
			CCUtils.verifyAndWaitForElement(element, driver);
			CCUtils.scrollPageToEnd(driver);
			if (element.getText().toString().contains(enterpriseName)) {
				log("if ent tab contains desired ent");
				log("get the text of arrow : " + arrowEntPractice.getText());
				if (arrowEntPractice.getText().equals("chevron_right")) {
					arrowEntPractice.click();
					log("clicked to arrow practice : " + arrowEntPractice.getText() + " tab");
				} else
					log("enterprise " + element.getText() + " is already expanded");
				for (WebElement ele : listOfPracTabs) {
					log("entered to prac tab");
					log("get the class of ele: " + ele.getAttribute("class").toString());
					if (ele.getText().toString().contains(practiceName)
							&& ele.getAttribute("class").toString().contains("treenode cursor")) {
						WebElement toggleButtonSpecific = driver
								.findElement(By.xpath(xpathRequiredClientType(practiceName)));
						CCUtils.verifyAndWaitForElement(toggleButtonSpecific, driver);
						toggleButtonSpecific.click();
						log("clicked to toggle OFF button of practice : " + ele.getText() + " tab");
					} else if (ele.getText().toString().contains(practiceName)
							&& ele.getAttribute("class").toString().contains("treenode no-event")) {
						log("toggle for this practice :" + ele.getText() + "is already OFF");
					}
				}
			}
		}
	}

	public void verifyToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
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

	public void clickOnPortalToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPortal);
		CCUtils.verifyAndWaitForElement(connectedSolPortal, driver);
		javascriptClick(connectedSolPortal);
		log("clicked On Portal Toggle");
	}

	public void selectPractice(String practiceName) {
		for (WebElement ele : listOfPracTabs) {
			CCUtils.verifyAndWaitForElement(ele, driver);
			CCUtils.assertEquals(ele.getText().toString().trim(), practiceName.trim());
			ele.click();
			log("clicked to practice tab named as : " + ele.getText().toString().trim());
		}
	}

	public void verifyConnectedLocationsSectionLabel() {
		CCUtils.scrollPageViaElement(driver, connectedLocationSectionTitle);
		CCUtils.verifyAndWaitForElement(connectedLocationSectionTitle, driver);
		CCUtils.assertEquals(connectedLocationSectionTitle.getText().toString().trim(), "Connected Locations");
		log("verified the presence of connected locations title");
	}

	public void verifyConnectedLocationsSection() throws InterruptedException, AWTException {
		verifyConnectedLocationsSectionLabel();
		verifyConnectedLocationsAddButtonIsDisplayed();
		log("Verified Connected Locations Section");
	}

	public void verifyConnectedLocationsAddButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(addLocationButton, driver);
		log("Verified Add Button Is Displayed");
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
		log("verified title of pop up");
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

	public void verifyAddLocationsPopupLabel() {
		CCUtils.verifyAndWaitForElement(labelPopup, driver);
		log("pop up is displayed in add popup");
	}

	public void clickToEntepriseArrow() {
		CCUtils.verifyAndWaitForElement(entepriseArrow, driver);
		entepriseArrow.click();
		log("clicked to enteprise Arrow");
	}

	public void clickOnToggleButton() {
		CCUtils.verifyAndWaitForElement(toggleButton, driver);
		toggleButton.click();
		log("clicked to toggle button");
	}

	public String getToasterMessage() {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		return toasterMessage.getText().trim();

	}

	public void verifyUpdateToasterMessage(String expectedToasterMessage) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		Log4jUtil.log("Verifying success toaster message.");
		if (toasterMessage.isDisplayed() == true)
			CCUtils.assertEquals(toasterMessage.getText().trim(), expectedToasterMessage);
		else
			Log4jUtil.log("Success toaster is not displayed");
	}

	public void verifyProviderInfoSection() throws InterruptedException, AWTException {
		verifyProviderInfoSectionLabel();
		verifyProviderID();
		verifyStaffID();
		verifyCommandCenterID();
		log("Verified Provider Info Section");
	}

	public void verifyProviderInfoSectionLabel() {
		CCUtils.scrollPageViaElement(driver, providerInfoLabel);
		CCUtils.verifyAndWaitForElement(providerInfoLabel, driver);
		log("Verifying Provider Info Label");
		CCUtils.assertEquals(providerInfoLabel.getText().trim(), "Provider Info");
	}

	public void verifyCommandCenterID() {
		CCUtils.verifyAndWaitForElement(labelCommandCenterID, driver);
		CCUtils.assertEquals(labelCommandCenterID.getText().trim(), "Command Center ID");
		log("Verified Command Center ID");
	}

	public void verifyProviderID() {
		CCUtils.verifyAndWaitForElement(labelProviderID, driver);
		CCUtils.assertEquals(labelProviderID.getText().trim(), "Provider ID");
		log("Verified Provider ID");
	}

	public void verifyStaffID() {
		CCUtils.verifyAndWaitForElement(labelStaffID, driver);
		CCUtils.assertEquals(labelStaffID.isDisplayed(), true);
		log("Verified Staff ID");
	}

	public void verifyConnectedSolutionsTitle() {
		CCUtils.scrollPageViaElement(driver, connectedSolutionsTitle);
		CCUtils.verifyAndWaitForElement(connectedSolutionsTitle, driver);
		CCUtils.assertTrue(connectedSolutionsTitle.isDisplayed(), "verifying connected solutions title");
		log("Verified connected solutions Title");
	}

	public void clickOnEngageToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolEngage);
		CCUtils.verifyAndWaitForElement(connectedSolEngage, driver);
		javascriptClick(connectedSolEngage);
		log("clicked On Engage Toggle");
	}

	public void clickOffEngageToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolEngage, driver);
		javascriptClick(connectedSolEngage);
		log("clicked Off Engage Toggle");
	}

	public void clickOnPayToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPay);
		CCUtils.verifyAndWaitForElement(connectedSolPay, driver);
		javascriptClick(connectedSolPay);
		log("clicked On Pay Toggle");
	}

	public void clickOffPayToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPay, driver);
		javascriptClick(connectedSolPay);
		log("clicked off pay toggle");
	}

	public void clickOnVirtualVisitToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolVirtualVisits);
		javascriptClick(connectedSolVirtualVisits);
		log("clicked on virtual visit toggle");
	}

	public void clickOffVirtualVisitToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolVirtualVisits, driver);
		javascriptClick(connectedSolVirtualVisits);
		log("clicked of virtual visit toggle");
	}

	public void clickOnPSSToggle() {
		CCUtils.scrollPageViaElement(driver, connectedSolPss);
		CCUtils.verifyAndWaitForElement(connectedSolPss, driver);
		javascriptClick(connectedSolPss);
		log("clicked on Pss Toggle");
	}

	public void clickOffPSSToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPss, driver);
		javascriptClick(connectedSolPss);
		log("clicked Off PSS Toggle");
	}

	public void clickOffPortalToggle() {
		CCUtils.verifyAndWaitForElement(connectedSolPortal, driver);
		javascriptClick(connectedSolPortal);
		log("clicked Off Portal Toggle");
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

	public String getSuccessToasterMessage() {
		CCUtils.verifyAndWaitForElement(successToasterMessage, driver);
		return successToasterMessage.getText().trim();
	}

	public void verifyConnectedSchedulingResourcesSection() throws InterruptedException, AWTException {
		verifyConnectedSchedulingResourcesSectionLabel();
		verifyResourceNameColumn();
		verifyResourceID();
		verifyConnectedSchedulingResourcesAddButtonIsDisplayed();
		verifyMessageWithNoConnectedResource();
		log("Verified Connected Scheduling Resources Section");
	}

	public void verifyConnectedSchedulingResourcesSectionLabel() {
		CCUtils.scrollPageViaElement(driver, labelConnectedResources);
		CCUtils.verifyAndWaitForElement(labelConnectedResources, driver);
		CCUtils.assertEquals(labelConnectedResources.getText().trim(), "Connected Scheduling Resources");
		log("Verified Connected Scheduling Resources Section Label");
	}

	public void verifyResourceNameColumn() {
		CCUtils.verifyAndWaitForElement(columnResourceName, driver);
		CCUtils.assertEquals(columnResourceName.getText().trim(), "Resource Name");
		log("Verified Resource Name Column");
	}

	public void verifyResourceID() {
		CCUtils.verifyAndWaitForElement(columnResourceId, driver);
		CCUtils.assertEquals(columnResourceId.getText().trim(), "Resource ID");
		log("Verified Resource ID Column");
	}

	public void verifyConnectedSchedulingResourcesAddButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(connectedResourcesAdd, driver);
		log("Verified Add Button Is Displayed");
	}

	public void verifyMessageWithNoConnectedResource() {
		CCUtils.verifyAndWaitForElement(noDataToDisplayMessage, driver);
		CCUtils.assertEquals(noDataToDisplayMessage.getText().trim(), "No data to display");
		log("Verified 'No Data To Display Message' With No Connected Resource");
	}

	public void verifyAddResourcesPopupDetails() throws InterruptedException, AWTException {
		verifyAddResourceTitle();
		verifyResourceLabel();
		verifyCancelButton();
		verifyAddButton();
		verifyCrossIcon();
		log("Verified Add Resources Popup Details");
	}

	public void verifyConnectedLocationsPopupDetails() throws InterruptedException, AWTException {
		verifyPopupTitle("Add Location(s)");
		verifyAddLocationsPopupLabel();
		verifyCrossIcon();
		verifyCancelButton();
		verifyAddButton();
		log("Verified Connected Locations Popup Details");
	}

	public void verifyConnectedLocationInWebTable() {
		CCUtils.verifyAndWaitForElement(connectedLocation, driver);
		CCUtils.assertEquals(connectedLocation.isDisplayed(), true);
		log("Connected Resource is present in connected Location table");
	}

	public void clickToRemoveConnectionBtn() {
		CCUtils.verifyAndWaitForElement(removeConnectionLocation, driver);
		removeConnectionLocation.click();
		log("clicked to remove connection");
	}

}
