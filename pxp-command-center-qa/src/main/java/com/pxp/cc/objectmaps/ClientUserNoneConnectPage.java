// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class ClientUserNoneConnectPage extends CCMainPage {

	@FindBy(id = "mat-dialog-title-4")
	private WebElement textTitle;

	@FindBy(xpath = "//p[@class='content']")
	private WebElement textInfoMsg;

	@FindBy(id = "searchFilterInput")
	private WebElement textBoxSearch;

	@FindBy(id = "cancelDialog")
	private WebElement buttonCancel;

	@FindBy(id = "connectSaveBtn")
	private WebElement buttonConnect;

	@FindBy(id = "closeDialog")
	private WebElement buttonCrossWindow;

	@FindBy(xpath = "//div[@class='overrride-content pb-2']")
	private WebElement textOverrideInfoMsg;

	@FindBy(id = "providerCheckedRadio")
	private WebElement radioButtonYes;

	@FindBy(id = "providerUncheckedRadio-input")
	private WebElement radioButtonNo;

	@FindBy(id = "searchList")
	private List<WebElement> searchList;

	@FindBy(id = "searchInputOverlay")
	private WebElement selectedSearchData;

	@FindBy(id = "searchToggleIcon")
	private WebElement buttonClear;

	public ClientUserNoneConnectPage(WebDriver driver) {
		super(driver);
	}

	public void verifyClientUserNoneConnectPopupTitle() {
		CCUtils.verifyAndWaitForElement(textTitle, driver);
		log("verified the presence of connect existing resource popup title");
		if (textTitle.isDisplayed() == true) {
			String title = textTitle.getText().trim();
			CCUtils.assertEquals(title, "Connect Client User to Existing Resource");
			log("Verified title should be 'Connect Client User to Existing Resource' in connect existing resource popup");
		} else {
			log("Client user connect existing resourceconnect existing resource popup title is not displayed");
		}
	}

	public void verifyClientUserNoneConnectInfoMessage(String clientName) {
		CCUtils.verifyAndWaitForElement(textInfoMsg, driver);
		log("verified the presence of info text in connect existing resource popup");
		if (textInfoMsg.isDisplayed() == true) {
			String title = textInfoMsg.getText().trim();
			CCUtils.assertEquals(title, "Search for an existing provider or staff member within " + clientName);
			log("Verified info message should be : Search for an existing provider or staff member within " + clientName
					+ " in connect existing resource popup");
		} else {
			log("connect existing resource popup Info message is not displayed");
		}
	}

	public void verifySearchTextbox() {
		CCUtils.verifyAndWaitForElement(textBoxSearch, driver);
		log("verified the presence of search textbox in connect existing resource popup");
		CCUtils.assertTrue(textBoxSearch.isDisplayed(),
				"Search textbox is not displayed in connect existing resource popup");
		log("Verifying Search placeholder is displayed in search textbox");
		CCUtils.assertEquals(textBoxSearch.getAttribute("placeholder").trim(), "Search");
		log("Box text for search textbox is: " + textBoxSearch.getAttribute("placeholder").trim());
	}

	public void verifyCancelButtonPresentOnNoneConnectPopup() {
		CCUtils.verifyAndWaitForElement(buttonCancel, driver);
		if (buttonCancel.isDisplayed() == true) {
			String title = buttonCancel.getText().trim();
			CCUtils.assertEquals(title.trim(), "Cancel");
		} else {
			log("Cancel is not displayed on connect existing resource popup");
		}
	}

	public void verifyConnectButtonPresentOnNoneConnectPopup() {
		CCUtils.verifyAndWaitForElement(buttonConnect, driver);
		if (buttonConnect.isDisplayed() == true) {
			String title = buttonConnect.getText().trim();
			CCUtils.assertEquals(title.trim(), "Connect");
		} else {
			log("Connect is not displayed on connect existing resource popup");
		}
	}

	public void verifyCrossIconIsDisplayed() {
		CCUtils.verifyAndWaitForElement(buttonCrossWindow, driver);
		log("Verifying cross icon is displayed on connect existing resource popup");
		if (buttonCrossWindow.isDisplayed() == true) {
			CCUtils.assertEquals(buttonCrossWindow.getText().trim(), "clear");
			log("Cross icon text is : " + buttonCrossWindow.getText().trim());
		} else {
			log("cross icon is not displayed on on connect existing resource popup");
		}
	}

	public String searchProvider(String providerAndStaff) {
		verifySearchTextbox();
		String selectedProvider = null;
		textBoxSearch.sendKeys(providerAndStaff);
		log("Searched text " + providerAndStaff + "in search textbox");
		for (WebElement element : searchList) {
			CCUtils.verifyAndWaitForElement(element, driver);
			if (element.getText().contains(providerAndStaff)) {
				log("Provider/Staff " + providerAndStaff + " found.");
				selectedProvider = element.getText().trim();
				element.click();
			}
		}
		return selectedProvider;
	}

	public void verifySearchTextboxdataAfterSelectingProvider(String searchData) {
		verifySearchTextbox();
		CCUtils.verifyAndWaitForElement(selectedSearchData, driver);
		if (selectedSearchData.getText().trim().contains(searchData)) {
			CCUtils.assertTrue(true, "Search data is same as selected from search list");
		}
	}

	public void verifyClearButtonPresentInSearchTextbox() {
		CCUtils.verifyAndWaitForElement(buttonClear, driver);
		log("verified the presence of clear button in search textbox");
	}

	public void verifyOverrideDemographicDetailsMessage() {
		CCUtils.verifyAndWaitForElement(textOverrideInfoMsg, driver);
		CCUtils.assertEquals(textOverrideInfoMsg.getText().trim(),
				"Would you like to override this user’s first name, last name and email to match the resource details?*");
		log("Verfied the override info message on connect existing resource popup");
	}

	public void verifyYesOverrideRadioButton() {
		CCUtils.verifyAndWaitForElement(radioButtonYes, driver);
		CCUtils.assertTrue(radioButtonYes.isSelected(), "Verifying Yes,Override radio button is by default selected");
		log("verified the presence of Yes,Override radio button");
	}

	public void verifyNoDoNotOverrideRadioButton() {
		CCUtils.verifyAndWaitForElement(radioButtonNo, driver);
		log("verified the presence of No,do not Override radio button");
	}

	public void verifyConnectButtonIsEnabled() {
		verifyConnectButtonPresentOnNoneConnectPopup();
		CCUtils.assertTrue((buttonConnect.isEnabled() == true),
				"Verifying connect button is enabled after selecting data from search list");
	}

	public ClientUserDetailsPage clickOnConnectButton() {
		verifyConnectButtonIsEnabled();
		buttonConnect.click();
		log("Connect button clicked");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public ClientUserDetailsPage clickOnCancelButton() {
		verifyCancelButtonPresentOnNoneConnectPopup();
		buttonCancel.click();
		log("Cancel button clicked");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}

	public ClientUserDetailsPage clickOnCloseWindow() {
		verifyCrossIconIsDisplayed();
		buttonCrossWindow.click();
		log("Cross icon button clicked");
		return PageFactory.initElements(driver, ClientUserDetailsPage.class);
	}
}
