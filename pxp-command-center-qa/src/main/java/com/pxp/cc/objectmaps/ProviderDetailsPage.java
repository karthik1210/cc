// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
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

	@FindBy(xpath = "//div[contains(text(),'Solutions Display Info')]")
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
	
	public ProviderDetailsPage(WebDriver driver) {
		super(driver);
	}

	public String invalidImagePath = "\\src\\test\\resources\\data-driven\\images\\223267.jpg";

	public void verifyImageIsAlreadyUploaded() {
		CCUtils.verifyAndWaitForElement(removeImage, driver);
		log("Image is already uploaded");
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
		uploadProviderImage.sendKeys(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
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
		CCUtils.assertEquals(labelSolutionsSection.getText().trim(), "Solutions Display Info");
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

}


