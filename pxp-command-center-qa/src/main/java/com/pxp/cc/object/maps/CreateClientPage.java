package com.pxp.cc.object.maps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class CreateClientPage extends CCMainPage {

	public CreateClientPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//span[contains(text(),'+ NEW')]")
	private WebElement buttonNew;

	@FindBy(xpath = "//button[normalize-space()='Create Client']")
	private WebElement buttonCreateClient;

	@FindBy(id = "CC-y0uN")
	private WebElement buttonCancelClient;

	@FindBy(xpath = "//button[normalize-space()='Create']")
	private WebElement buttonConfirm;

	@FindBy(xpath = "//input[@name='name']")
	private WebElement textboxClientName;

	@FindBy(xpath = "//input[@name='sapNumber']")
	private WebElement textboxSapId;

	@FindBy(xpath = "//ng-select[@id='CC-vly8']")
	private WebElement dropdownClientType;

	@FindBy(xpath = "//div[@role='option']//span[@ng-reflect-ng-item-label='Direct Client'][normalize-space()='Direct Client']")
	private WebElement selectDirectClient;

	@FindBy(id = "CC-w6xf")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//input[@value='true']")
	private WebElement radioBtnActive;

	@FindBy(xpath = "//input[@value='false']]")
	private WebElement radioBtnInactive;

	@FindBy(xpath = "//input[@name='test']")
	private WebElement checkboxTest;

	@FindBy(id = "CC-tcwj")
	private WebElement errorUniqueClientName;

	@FindBy(xpath = "//mat-dialog-container[@role='dialog']")
	private WebElement dialogCreateClient;

	@FindBy(xpath = "//div[normalize-space()='SAP ID already in use']")
	private WebElement errorUniqueSap;

	@FindBy(xpath = "//div[@class='cdk-overlay-container']")
	private WebElement toasterMessage;

	public void clickCreateClient() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		log(" New button clicked");
		buttonNew.click();
		CCUtils.verifyAndWaitForElement(buttonCreateClient, driver);
		buttonCreateClient.click();
		log("Clicked on Create client button");
	}

	public String verifyClientPopupText() {

		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		CCUtils.verifyAndWaitForElement(dropdownClientType, driver);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		CCUtils.verifyAndWaitForElement(radioBtnActive, driver);
		CCUtils.verifyAndWaitForElement(radioBtnInactive, driver);
		CCUtils.verifyAndWaitForElement(checkboxTest, driver);
		String popuptext = dialogCreateClient.getText();
		buttonCancelClient.click();
		log("Clicked on Cancel Button");
		return popuptext;
	}

	public void createNewClient(String newClientName, String newSAPID, String newTimezone) throws InterruptedException {
		textboxClientName.sendKeys(newClientName);
		log("Entered text in client name field : " + newClientName);
		textboxSapId.sendKeys(newSAPID);
		log("Entered text in SAP ID field : " + newSAPID);
		selectDirectClient.click();
		CCUtils.selectFromDropDown(dropdownTimezone, newTimezone);
	}

	public String clickConfirmPopup() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		buttonConfirm.click();
		log("Clicked on Create button in popup");
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		String actualMessage = toasterMessage.getText();
		return actualMessage;
	}

	public void selectInactiveRadiobutton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(radioBtnInactive, driver);
		radioBtnInactive.click();
		log("Clicked on Inactive radiobutton in popup");
	}

	public void selectTestClient() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkboxTest, driver);
		checkboxTest.click();
		log("Clicked on Test checkbox");
	}

	public void verifyClientNameAndSapUnique(String duplicateClientName, String duplicateSAP) throws Exception {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.sendKeys(duplicateClientName);
		log("Entered text in client name field is " + duplicateClientName);
		CCUtils.verifyAndWaitForElement(errorUniqueClientName, driver);
		CCUtils.assertEquals(errorUniqueClientName.getText(), "Client name already in use");
		log("Error Message displayed for duplicate client name is :" + errorUniqueClientName.getText());
		textboxClientName.clear();
		textboxClientName.sendKeys("new " + duplicateClientName);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(duplicateSAP);
		log("Entered text in SAP ID field : " + duplicateSAP);
		CCUtils.verifyAndWaitForElement(errorUniqueSap, driver);
		CCUtils.assertEquals(errorUniqueSap.getText(), "SAP ID already in use");
		log("Error Message displayed for duplicate client name is :" + errorUniqueSap.getText());
		textboxSapId.clear();

	}

	public void selectTestCheckbox() {
		CCUtils.verifyAndWaitForElement(checkboxTest, driver);
		checkboxTest.click();
	}
}
