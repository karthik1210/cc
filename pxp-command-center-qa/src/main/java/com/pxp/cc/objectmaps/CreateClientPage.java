// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import org.openqa.selenium.By;
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

	@FindBy(xpath = "//span[@id='clientCancelBtn']")
	private WebElement buttonCancelClient;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement buttonConfirm;

	@FindBy(xpath = "//input[@name='name']")
	private WebElement textboxClientName;

	@FindBy(xpath = "//input[@name='sapNumber']")
	private WebElement textboxSapId;

	@FindBy(id = "clientTypeSelect")
	private WebElement dropdownClientType;

	@FindBy(xpath = "//div[@role='option']//span[contains(text(),'Direct Client')]")
	private WebElement selectDirectClient;

	@FindBy(xpath = "//div[@role='option']//span[contains(text(),'Pacific Standard Time')]")
	private WebElement selectESTTimezone;

	@FindBy(xpath = "//ng-select[@id='timezoneSelect']//span[@class='ng-arrow-wrapper']")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//input[@value='true']")
	private WebElement radioBtnActive;

	@FindBy(id = "clientInactiveInput")
	private WebElement radioBtnInactive;

	@FindBy(xpath = "//input[@value='test']")
	private WebElement checkboxTest;

	@FindBy(id = "clientCheckName")
	private WebElement errorUniqueClientName;

	@FindBy(xpath = "//mat-dialog-container[@role='dialog']")
	private WebElement dialogCreateClient;

	@FindBy(xpath = "//div[normalize-space()='SAP ID already in use']")
	private WebElement errorUniqueSap;

	public void clickCreateClient() {
		CCUtils.verifyAndWaitForElement(buttonNew, driver);
		log("New button clicked");
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

	private String xpathRequiredClientType = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private String xpathRequiredNewClientTimezone = "//div[@role='option']//span[contains(text(),'valueToReplace')]";

	private WebElement prepareDynamicXpathForClientType(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectClientType(String clientTypeName) {
		WebElement clientType = prepareDynamicXpathForClientType(xpathRequiredClientType, clientTypeName);
		CCUtils.verifyAndWaitForElement(clientType, driver);
		clientType.click();
		log("Clicked on : " + clientTypeName);
	}

	public void selectTimeZone(String newTimezone) {
		WebElement newClientTimezone = prepareDynamicXpathForClientType(xpathRequiredNewClientTimezone, newTimezone);
		newClientTimezone.click();
		log("Clicked on : " + newTimezone);
	}

	public void createNewClient(String newClientName, String newSAPID, String clientType, String newTimezone)
			throws InterruptedException {
		CCUtils.verifyAndWaitForElement(textboxClientName, driver);
		textboxClientName.sendKeys(newClientName);
		log("Entered text in client name field : " + newClientName);
		textboxSapId.sendKeys(newSAPID);
		log("Entered text in SAP ID field : " + newSAPID);
		CCUtils.verifyAndWaitForElement(dropdownClientType, driver);
		dropdownClientType.click();
		selectClientType(clientType);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		selectTimeZone(newTimezone);
	}

	public void clickConfirmPopup() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(buttonConfirm, driver);
		javascriptClick(buttonConfirm);
	}

	public void selectInactiveRadiobutton() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(radioBtnInactive, driver);
		radioBtnInactive.click();
	}

	public void selectTestClient() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(checkboxTest, driver);
		Thread.sleep(3000);
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

}
