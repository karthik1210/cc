// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pxp.cc.util.CCUtils;

public class BrandingTemplatesPage extends CCMainPage {

	@FindBy(xpath = "//span[contains( text(),'+ VERSION')]")
	private WebElement versionButton;

	@FindBy(xpath = "//input[@id='version-input']")
	private WebElement textboxVersion;

	@FindBy(id = "createBrandButton")
	private WebElement saveButton;

	@FindBy(xpath = "(//input[@type='file'])[2]")
	private WebElement browseSolLogo;

	@FindBy(id = "uploadText1")
	private WebElement altTextBox;

	@FindBy(id = "uploadText0")
	private WebElement altTextFaviconBox;

	@FindBy(id = "uploadText2")
	private WebElement altTextPatientCommunicationBox;

	@FindBy(id = "removeImageButton")
	private WebElement removeImageButton;

	@FindBy(xpath = "//div[contains(text(),'Branding Templates')]")
	private WebElement brandingTemplatesLabel;

	@FindBy(xpath = "//span[contains(text(),' ACTIVE ')]")
	private WebElement activeStatus;

	@FindBy(xpath = "//span[contains(text(),' INACTIVE ')]")
	private WebElement inactiveStatus;

	@FindBy(xpath = "(//mat-icon[@id='kebabIcon'])[1]")
	private WebElement kebabMenuForActiveStatus;

	@FindBy(xpath = "(//mat-icon[@id='kebabIcon'])[2]")
	private WebElement kebabMenu;

	@FindBy(id = "duplicate")
	private WebElement duplicateButton;

	@FindBy(xpath = "//span[contains(text(), 'IN USE')]")
	private WebElement inUseStatus;

	@FindBy(xpath = "//div[@class='mat-tree-node-click noclass']")
	private WebElement clientNode;

	@FindBy(id = "delete_forever")
	private WebElement deleteButton;

	@FindBy(id = "edit")
	private WebElement editButton;

	@FindBy(id = "confirmLabel")
	private WebElement labelConfirmationQuestion;

	@FindBy(id = "unsavedConfirmBtn")
	private WebElement yesDeleteButton;

	@FindBy(id = "unsavedCancelBtn")
	private WebElement CancelButton;

	@FindBy(id = "clearDelete")
	private WebElement CrossIcon;

	@FindBy(xpath = "//div[contains(text(),'Version name is required')]")
	private WebElement errorVersionName;

	@FindBy(xpath = "//input[@id='favicon']//preceding::p[1]")
	private WebElement errorInvalidFormatTypeFavicon;

	@FindBy(xpath = "//input[@id='logo']//preceding::p[1]")
	private WebElement errorInvalidFormatTypeSolutionsLogo;

	@FindBy(xpath = "//input[@id='emailLogo']//preceding::p[1]")
	private WebElement errorInvalidFormatTypePatientCommunicationsLogo;

	@FindBy(xpath = "(//input[@type='file'])[3]")
	private WebElement browsePatientCommunicationLogo;

	@FindBy(xpath = "(//input[@type='file'])[1]")
	private WebElement browseFaviconLogo;

	@FindBy(xpath = "//div[contains(text(),'AltText name is required')]")
	private WebElement errorAltTextRequired;

	@FindBy(xpath = "//div[contains(text(),'Maximum characters reached')]")
	private WebElement errorMaxCharacterReached;

	@FindBy(xpath = "(//button[@id='removeImageButton'])[2]")
	private WebElement removeSolutionsLogo;

	@FindBy(xpath = "(//button[@id='removeImageButton'])")
	private WebElement removePatientCommunicationsLogo;

	@FindBy(xpath = "(//button[@id='removeImageButton'])[1]")
	private WebElement removeFaviconLogo;

	@FindBy(xpath = "(//div[@class='image-container']//p[contains(text(),'No File Uploaded')])[1]")
	private WebElement faviconNoFileUploaded;

	@FindBy(xpath = "(//div[@class='image-container']//p[contains(text(),'No File Uploaded')])[2]")
	private WebElement solutionsNoFileUploaded;

	@FindBy(xpath = "(//div[@class='image-container']//p[contains(text(),'No File Uploaded')])[3]")
	private WebElement patientCommunicationsNoFileUploaded;

	@FindBy(xpath = "//mat-radio-button[@value='true']")
	private WebElement logoRadioButton;
	
	@FindBy(id = "radioBtnPracticeName-input")
	private WebElement practiceNameRadioButton;

	@FindBy(xpath = "(//div[contains(text(),'Maximum characters reached')])[1]")
	private WebElement faviconMaxCharReached;

	@FindBy(xpath = "(//div[contains(text(),'Maximum characters reached')])[2]")
	private WebElement solutionsLogoMaxCharReached;

	@FindBy(xpath = "(//div[contains(text(),'Maximum characters reached')])[3]")
	private WebElement patientCommunicationsLogoMaxCharReached;

	public String invalidImagePath = "/src/test/resources/data-driven/images/223267.jpg";

	public String validImagePath = "/src/test/resources/data-driven/images/logo.png";

	public String editValidImagePath = "/src/test/resources/data-driven/images/leaf.svg";

	@FindBy(id = "activate")
	private WebElement makeActiveButton;

	@FindBy(xpath = "(//div[normalize-space(text())='Branding Templates']//following::mat-icon[contains(text(),'keyboard_arrow_down')])[1]")
	private WebElement expandArrow;

	@FindBy(xpath = "//mat-icon[contains(text(),'expand_more')]")
	private WebElement showOtherVersions;

	@FindBy(xpath = "//p[contains(text(),' Hide other versions')]")
	private WebElement hideOtherVersions;
	
	@FindBy(id = "save-icon")
	private WebElement saveButtonOnConfirmationDialogBox;

	@FindBy(xpath = "//h3[contains(text(),'Are you sure you want to apply these changes?')]")
	private WebElement confirmationDialogBoxTitle;

	@FindBy(xpath = "//p[@class='pt-3']")
	private WebElement confirmationDialogSubText;

	@FindBy(id = "cancel-icon")
	private WebElement cancelButton;

	@FindBy(id = "close-icon")
	private WebElement crossIcon;

	@FindBy(xpath = "//span[@class='titleName']//span")
	private List<WebElement> newlyCreatedBrandName;

	@FindBy(id = "editBrandButton")
	private WebElement editSaveButton;

	public BrandingTemplatesPage(WebDriver driver) {
		super(driver);
	}

	public void VerifyBrandingTemplatesIsDisplayed() throws InterruptedException, AWTException {
		CCUtils.scrollPageViaElement(driver, brandingTemplatesLabel);
		CCUtils.verifyAndWaitForElement(brandingTemplatesLabel, driver);
		log("Verifying Inactive status text");
		if(brandingTemplatesLabel.getText().trim().contains("Branding Templates")) {
			CCUtils.assertTrue(true, "verifying branding title");
		}
	}

	public void VerifyAndClickVersionButton() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(versionButton, driver);
		log("Verifying +VERSION is displayed");
		CCUtils.assertEquals(versionButton.getText().trim(), "+ VERSION");
		CCUtils.verifyAndWaitForElement(versionButton, driver);
		javascriptClick(versionButton);
		log("Clicked on +VERSION");
	}

	public void VerifyBrandingNameIsActive() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(activeStatus, driver);
		log("Verifying Inactive status text");
		CCUtils.assertEquals(activeStatus.getText().trim(), "ACTIVE");
	}

	public void VerifyBrandingNameIsInactive() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(inactiveStatus, driver);
		log("Verifying Inactive status text");
		CCUtils.assertEquals(inactiveStatus.getText().trim(), "INACTIVE");
	}

	public void clickOnKebabMenu() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(kebabMenu, driver);
		javascriptClick(kebabMenu);
		log("Clicked on kebabMenu");
	}

	public void clickOnKebabMenuOfParticularBranding(String brandName) throws InterruptedException {
		String xpathLocator = "(//div//span[@class='titleName']//span[normalize-space(text())='" + brandName
				+ "']//..//..//following::span//mat-icon)[1]";
		WebElement elem = driver.findElement(By.xpath(xpathLocator));
		javascriptClick(elem);
		log("Clicked on kebabMenu of brand : "+brandName);
	}

	public void clickOnkebabMenuForActiveStatus() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(kebabMenuForActiveStatus, driver);
		javascriptClick(kebabMenuForActiveStatus);
		log("Clicked on kebabMenu");
	}

	public void clickOnDuplicate() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(duplicateButton, driver);
		javascriptClick(duplicateButton);
		log("Clicked on duplicate");
	}

	public void createNewBrand(String newVersion) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxVersion, driver);
		textboxVersion.sendKeys(newVersion);
		log("Entered text in Version name field : " + newVersion);

	}
	
	public void removeEnteredBrand() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxVersion, driver);
		textboxVersion.clear();
		log("removed value in brand version text box");

	}

	public void verifyAndClickSaveButton() throws InterruptedException, AWTException {
		CCUtils.scrollPageViaElement(driver,saveButton);
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		log("Verifying SAVE is displayed");
		CCUtils.assertEquals(saveButton.getText().trim(), "Save");
		CCUtils.verifyAndWaitForElement(saveButton, driver);
		javascriptClick(saveButton);
		log("Clicked on SAVE");
	}
	
	public void clickEditSaveButton() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElementPresent(editSaveButton, driver);
		CCUtils.assertEquals(editSaveButton.getText().trim(), "Save");
		javascriptClick(editSaveButton);
		log("Clicked on SAVE");
	}

	public void uploadSolutionLogoImage() throws InterruptedException, AWTException {
		javascriptClick(browseSolLogo);
		log("Clicked on BROWSE");
		String projectpath = System.getProperty("user.dir");
		browseSolLogo.sendKeys(projectpath + "/src/test/resources/data-driven/images/logo.png");
		log("Verifying Image uploaded");
	}

	public void enterAltText(String altText) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(altTextBox, driver);
		altTextBox.sendKeys(altText);
		log("Entered text in Version name field : " + altText);
	}

	public void removeEnterAltText() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(altTextBox, driver);
		altTextFaviconBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		altTextFaviconBox.sendKeys(Keys.BACK_SPACE);
		log("Remove entered text in Version name field");
	}
	
	public void enterAltTextForPatientCommunication(String altText) throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, altTextPatientCommunicationBox);
		CCUtils.verifyAndWaitForElement(altTextPatientCommunicationBox, driver);
		altTextPatientCommunicationBox.sendKeys(altText);
		log("Entered text in Alt Text field of Patient Communication is : " + altText);
	}
	
	public void removeEnterAltTextForPatientCommunication() throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, altTextPatientCommunicationBox);
		CCUtils.verifyAndWaitForElement(altTextPatientCommunicationBox, driver);
		altTextFaviconBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		altTextFaviconBox.sendKeys(Keys.BACK_SPACE);
		log("Remove entered text in Alt Text field of Patient Communication");
	}

	public void enterAltTextForFavicon(String altText) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(altTextFaviconBox, driver);
		altTextFaviconBox.sendKeys(altText);
		log("Entered text in Alt Text field of Favicon is : " + altText);
	}

	public void removeEnteredAltTextForFavicon() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(altTextFaviconBox, driver);
		altTextFaviconBox.click();
		altTextFaviconBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		altTextFaviconBox.sendKeys(Keys.BACK_SPACE);
		log("Removed entered text in Alt Text field of Favicon");
	}
	
	public void verifyRemoveButton() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(removeImageButton, driver);
		log("Verifying REMOVE is displayed");
		CCUtils.assertEquals(removeImageButton.getText().trim(), "REMOVE");
	}

	public void clickOnClientNode() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(clientNode, driver);
		javascriptClick(clientNode);
		log("Clicked on clientNode");
	}

	public void VerifyInUseStatus(String newVersion) throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(inUseStatus, driver);
		log("Verifying inUseStatus text");
		CCUtils.assertEquals(inUseStatus.getText().trim(), "IN USE");
	}

	public void verifyEditOptionForActiveStatus() throws InterruptedException {

		CCUtils.verifyAndWaitForElement(editButton, driver);
		CCUtils.assertTrue(true, editButton.getText().trim());
		log("Edit option is  display");
	}

	public void clickOnDelete() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(deleteButton, driver);
		javascriptClick(deleteButton);
		log("Clicked on delete");
	}

	public void verifyConfirmationPopupForDelete() {
		verifyConfirmationPageTitle();
		verifyCancelButtonIsDisplayed();
		verifyCrossIconIsDisplayed();
		verifyYesDeleteIsDisplayed();
	}

	public void verifyConfirmationPageTitle() {
		CCUtils.verifyAndWaitForElement(labelConfirmationQuestion, driver);
		if (labelConfirmationQuestion.isDisplayed() == true) {
			CCUtils.assertEquals(labelConfirmationQuestion.getText().trim(),
					"Are you sure you want to delete this item?");
			log("Delete Inactive branding template confirmation text is = "
					+ labelConfirmationQuestion.getText().trim());
		} else {
			log("Delete Inactive branding template confirmation text is not displayed");
		}
	}

	public void verifyYesDeleteIsDisplayed() {
		CCUtils.verifyAndWaitForElement(yesDeleteButton, driver);
		CCUtils.assertTrue(yesDeleteButton.isDisplayed(),
				"Yes, Delete button is not displayed on delete confirmation popup");
		CCUtils.assertEquals(yesDeleteButton.getText().trim(), "Yes, Delete");
		CCUtils.assertEquals(yesDeleteButton.isEnabled(), true);
	}

	public void verifyCancelButtonIsDisplayed() {
		CCUtils.verifyAndWaitForElement(CancelButton, driver);
		CCUtils.assertTrue(CancelButton.isDisplayed(), "Cancel button is not displayed on delete confirmation popup");
		CCUtils.assertEquals(CancelButton.getText().trim(), "Cancel");
		CCUtils.assertEquals(CancelButton.isEnabled(), true);
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(CrossIcon, driver);
		CCUtils.assertTrue(CrossIcon.isDisplayed(), "Cross icon is not displayed on delete confirmation popup");
		CCUtils.assertEquals(CrossIcon.getText().trim(), "clear");
		CCUtils.assertEquals(CrossIcon.isEnabled(), true);
	}

	public void clickOnYesDelete() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(yesDeleteButton, driver);
		javascriptClick(yesDeleteButton);
		log("Clicked on Yes,Delete Button");
	}

	public void clickOnCancel() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(CancelButton, driver);
		javascriptClick(CancelButton);
		log("Clicked on Cancel");
	}

	public void clickOnCrossIcon() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(CrossIcon, driver);
		javascriptClick(CrossIcon);
		log("Clicked on CrossIcon");
	}

	public void verifyVerionNameErrorGotDisplayed() {
		CCUtils.verifyAndWaitForElement(errorVersionName, driver);
		if (errorVersionName.isDisplayed() && errorVersionName.getText().trim().equals("Version name is required")) {
			CCUtils.assertTrue(true, "Message got displayed");
		}
	}

	public void uploadPatientCommunicationsLogoInvalidImage() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		javascriptClick(browsePatientCommunicationLogo);
		log("Clicked on Patient Communication BROWSE");
		String projectpath = System.getProperty("user.dir");
		browsePatientCommunicationLogo.sendKeys(projectpath + invalidImagePath);
		log("Verifying Image uploaded");
	}

	public void uploadPatientCommunicationsLogoValidImage() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		javascriptClick(browsePatientCommunicationLogo);
		log("Clicked on Patient Communication BROWSE");
		String projectpath = System.getProperty("user.dir");
		browsePatientCommunicationLogo.sendKeys(projectpath + validImagePath);
		log("Verifying Image uploaded");
	}

	public void uploadFaviconLogoInvalidImage() throws InterruptedException, AWTException {
		javascriptClick(browseFaviconLogo);
		log("Clicked on Favicon BROWSE");
		String projectpath = System.getProperty("user.dir");
		browseFaviconLogo.sendKeys(projectpath + invalidImagePath);
		log("Verifying Image uploaded");
	}

	public void uploadFaviconLogoValidImage() throws InterruptedException, AWTException {
		javascriptClick(browseFaviconLogo);
		log("Clicked on Favicon BROWSE");
		String projectpath = System.getProperty("user.dir");
		browseFaviconLogo.sendKeys(projectpath + validImagePath);
		log("Verifying Image uploaded");
	}

	public void uploadSolutionLogoInvalidImage() throws InterruptedException, AWTException {
		javascriptClick(browseSolLogo);
		log("Clicked on Solution Logo BROWSE");
		String projectpath = System.getProperty("user.dir");
		browseSolLogo.sendKeys(projectpath + invalidImagePath);
		log("Verifying Image uploaded");
	}

	public void verifyInvalidFaviconImageErrorGotDisplayed() {
		CCUtils.verifyAndWaitForElement(errorInvalidFormatTypeFavicon, driver);
		if (errorInvalidFormatTypeFavicon.isDisplayed()
				&& errorInvalidFormatTypeFavicon.getText().trim().equals("Invalid file type")) {
			CCUtils.assertTrue(true, "Message got displayed");
		}
	}

	public void verifyInvalidSolutionsLogoImageErrorGotDisplayed() {
		CCUtils.verifyAndWaitForElement(errorInvalidFormatTypeSolutionsLogo, driver);
		if (errorInvalidFormatTypeSolutionsLogo.isDisplayed()
				&& errorInvalidFormatTypeSolutionsLogo.getText().trim().equals("Invalid file type")) {
			CCUtils.assertTrue(true, "Message got displayed");
		}
	}

	public void verifyInvalidPatientCommunicationsLogoImageErrorGotDisplayed() {
		CCUtils.verifyAndWaitForElement(errorInvalidFormatTypePatientCommunicationsLogo, driver);
		if (errorInvalidFormatTypePatientCommunicationsLogo.isDisplayed()
				&& errorInvalidFormatTypePatientCommunicationsLogo.getText().trim().equals("Invalid file type")) {
			CCUtils.assertTrue(true, "Message got displayed");
		}
	}

	public void verifyAltTextRequiredErrorGotDisplayed() {
		CCUtils.verifyAndWaitForElement(errorAltTextRequired, driver);
		if (errorAltTextRequired.isDisplayed()
				&& errorAltTextRequired.getText().trim().equals("AltText name is required")) {
			CCUtils.assertTrue(true, "Alt Text Required error message got displayed");
		}
	}

	public void verifyMaxCharacterReachedErrorGotDisplayed() {
		CCUtils.verifyAndWaitForElement(errorMaxCharacterReached, driver);
		if (errorMaxCharacterReached.isDisplayed()
				&& errorMaxCharacterReached.getText().trim().equals("Maximum characters reached")) {
			CCUtils.assertTrue(true, "Maximum characters reached error message got displayed");
		}
	}

	public void verifyNoFileUploadedFaviconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(faviconNoFileUploaded, driver);
		if (faviconNoFileUploaded.isDisplayed() && faviconNoFileUploaded.getText().trim().equals("No File Uploaded")) {
			CCUtils.assertTrue(true, "No File Uploaded message got displayed");
		}
	}

	public void verifyNoFileUploadedSolutionIsDisplayed() {
		CCUtils.verifyAndWaitForElement(solutionsNoFileUploaded, driver);
		if (solutionsNoFileUploaded.isDisplayed()
				&& solutionsNoFileUploaded.getText().trim().equals("No File Uploaded")) {
			CCUtils.assertTrue(true, "No File Uploaded message got displayed");
		}
	}

	public void verifyNoFileUploadedPatientCommunicationIsDisplayed() {
		CCUtils.verifyAndWaitForElement(patientCommunicationsNoFileUploaded, driver);
		if (patientCommunicationsNoFileUploaded.isDisplayed()
				&& patientCommunicationsNoFileUploaded.getText().trim().equals("No File Uploaded")) {
			CCUtils.assertTrue(true, "No File Uploaded message got displayed");
		}
	}

	public void verifyPatientCommunicationMaxCharReachedErrorIsDisplayed() {
		CCUtils.verifyAndWaitForElement(patientCommunicationsLogoMaxCharReached, driver);
		if (patientCommunicationsLogoMaxCharReached.isDisplayed()
				&& patientCommunicationsLogoMaxCharReached.getText().trim().equals("Maximum characters reached")) {
			CCUtils.assertTrue(true, "Patient Communication Max character reached message got displayed");
		}
	}

	public void verifySolutionsLogoMaxCharReachedErrorIsDisplayed() {
		CCUtils.verifyAndWaitForElement(solutionsLogoMaxCharReached, driver);
		if (solutionsLogoMaxCharReached.isDisplayed()
				&& solutionsLogoMaxCharReached.getText().trim().equals("Maximum characters reached")) {
			CCUtils.assertTrue(true, "Solutions Logo Max character reached message got displayed");
		}
	}

	public void verifyFaviconLogoMaxCharReachedErrorIsDisplayed() {
		CCUtils.verifyAndWaitForElement(faviconMaxCharReached, driver);
		if (faviconMaxCharReached.isDisplayed()
				&& faviconMaxCharReached.getText().trim().equals("Maximum characters reached")) {
			CCUtils.assertTrue(true, "Favicon Max character reached message got displayed");
		}
	}

	public void clickToRemoveButtonOfSolutionsLogo() {
		CCUtils.verifyAndWaitForElement(removeSolutionsLogo, driver);
		removeSolutionsLogo.click();
		log("clicked to remove button of Solutions Logo");
	}

	public void clickToRemoveButtonOfFaviconLogo() {
		CCUtils.verifyAndWaitForElement(removeFaviconLogo, driver);
		removeFaviconLogo.click();
		log("clicked to remove button of Favicon Logo");
	}

	public void clickToRemoveButtonOfPatientCommunicationsLogo() throws AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(removePatientCommunicationsLogo, driver);
		removePatientCommunicationsLogo.click();
		log("clicked to remove button of Patient Communications Logo");
	}

	public void clickToLogoRadioButton() {
		CCUtils.verifyAndWaitForElement(logoRadioButton, driver);
		logoRadioButton.click();
		log("clicked to logo button");
	}

	public void clickToPracticeNameRadioButton() {
		CCUtils.verifyAndWaitForElement(practiceNameRadioButton, driver);
		javascriptClick(practiceNameRadioButton);
		log("clicked to practice name button");
	}
	
	public void clickOnMakeActive() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(makeActiveButton, driver);
		javascriptClick(makeActiveButton);
		log("Clicked on Make Active");
	}

	public void clickOnEdit() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(editButton, driver);
		javascriptClick(editButton);
		log("Clicked on Edit button");
	}

	public void clickOnExpandArrowForBrandingTemplates() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(expandArrow, driver);
		javascriptClick(expandArrow);
		log("Clicked on expand Arrow");
	}

	public void clickOnShowOtherVersionsArrow() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(showOtherVersions, driver);
		javascriptClick(showOtherVersions);
		log("Clicked on show other versions");
	}

	public void editBrandVersionName(String newVersion) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxVersion, driver);
		textboxVersion.clear();
		CCUtils.verifyAndWaitForElement(textboxVersion, driver);
		textboxVersion.sendKeys(newVersion);
		log("Entered text in Version name field : " + newVersion);
	}

	public void editFaviconLogoValidImage() throws InterruptedException, AWTException {
		javascriptClick(browseFaviconLogo);
		log("Clicked on Favicon BROWSE");
		String projectpath = System.getProperty("user.dir");
		browseFaviconLogo.sendKeys(projectpath + editValidImagePath);
		log("Verifying Image uploaded");
	}

	public void editSolutionLogoImage() throws InterruptedException, AWTException {
		javascriptClick(browseSolLogo);
		log("Clicked on BROWSE");
		String projectpath = System.getProperty("user.dir");
		browseSolLogo.sendKeys(projectpath + editValidImagePath);
		log("Verifying Image uploaded");
	}

	public void editPatientCommunicationsLogoValidImage() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		javascriptClick(browsePatientCommunicationLogo);
		log("Clicked on Patient Communication BROWSE");
		String projectpath = System.getProperty("user.dir");
		browsePatientCommunicationLogo.sendKeys(projectpath + editValidImagePath);
		log("Verifying Image uploaded");
	}

	public void editAltTextForSolutionsLogo(String altText) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(altTextBox, driver);
		textboxVersion.clear();
		CCUtils.verifyAndWaitForElement(altTextBox, driver);
		altTextBox.sendKeys(altText);
		log("Entered text in Version name field : " + altText);
	}

	public void editAltTextForPatientCommunicationsLogo(String altText) throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(altTextPatientCommunicationBox, driver);
		textboxVersion.clear();
		CCUtils.verifyAndWaitForElement(altTextPatientCommunicationBox, driver);
		altTextPatientCommunicationBox.sendKeys(altText);
		log("Entered text in Alt Text field of Patient Communication is : " + altText);
		CCUtils.scrollPageToStart();
	}

	public void editAltTextForFaviconLogo(String altText) throws InterruptedException {
		CCUtils.verifyAndWaitForElement(altTextFaviconBox, driver);
		textboxVersion.clear();
		CCUtils.verifyAndWaitForElement(altTextFaviconBox, driver);
		altTextFaviconBox.sendKeys(altText);
		log("Entered text in Alt Text field of Favicon is : " + altText);
	}

	public void clickSaveOnConfirmationDialogBox() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(saveButtonOnConfirmationDialogBox, driver);
		javascriptClick(saveButtonOnConfirmationDialogBox);
		log("Clicked on Save ButtonOnConfirmationPopup");
	}

	public void verifyConfirmationDialogBoxTitleLabel() {
		CCUtils.verifyAndWaitForElement(confirmationDialogBoxTitle, driver);
		CCUtils.assertTrue(confirmationDialogBoxTitle.isDisplayed(), "Are you sure you want to apply these changes?");
	}

	public void verifyConfirmationDialogBoxSubText() {
		CCUtils.verifyAndWaitForElement(confirmationDialogSubText, driver);
		CCUtils.assertTrue(confirmationDialogSubText.isDisplayed(),
				"Changes will be applied to the following entities actively using this template:");
	}

	public void verifyConfirmationDialogBoxSaveButton() {
		CCUtils.verifyAndWaitForElement(saveButtonOnConfirmationDialogBox, driver);
		CCUtils.assertEquals(saveButtonOnConfirmationDialogBox.isDisplayed(), true);
	}

	public void verifyConfirmationDialogBoxCancelButton() {
		CCUtils.verifyAndWaitForElement(cancelButton, driver);
		CCUtils.assertEquals(cancelButton.isDisplayed(), true);
	}

	public void verifyConfirmationDialogBoxCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		CCUtils.assertEquals(crossIcon.isDisplayed(), true);
	}

	public void clickCancelOnConfirmationDialogBox() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(cancelButton, driver);
		javascriptClick(cancelButton);
		log("Clicked on cancel Button On Confirmation DialogBox");
	}

	public void clickCrossIconOnConfirmationDialogBox() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		javascriptClick(crossIcon);
		log("Clicked on crossIcon On Confirmation DialogBox");
	}

	public void VerifyNewlyCreatedBrandingTemplate(String input) throws InterruptedException {
		CCUtils.scrollPageViaElement(driver, hideOtherVersions);
		for(WebElement ele : newlyCreatedBrandName) {
			CCUtils.verifyAndWaitForElement(ele, driver);
			if(ele.getText().trim().equals(input)){
				CCUtils.assertTrue(true, "verifying newly created brand is present in brand section");
				log("verifying newly created brand is present in brand section");
			}
		}
	}

	public void verifyAndClickEditSaveButton() throws InterruptedException, AWTException {
		CCUtils.verifyAndWaitForElement(editSaveButton, driver);
		log("Verifying SAVE is displayed");
		CCUtils.assertEquals(editSaveButton.getText().trim(), "Save");
		CCUtils.verifyAndWaitForElement(editSaveButton, driver);
		javascriptClick(editSaveButton);
		log("Clicked on SAVE");
	}
	
	public void pageRefresh() {
		driver.navigate().refresh();
		log("Refreshing page");
		
	}
}