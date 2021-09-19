// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.pxp.cc.util.CCUtils;

public class ClientDirectoryPage extends CCMainPage {

	@FindBy(how = How.XPATH, using = "//button[@class='mat-focus-indicator mat-menu-trigger mat-mini-fab mat-button-base mat-accent']//span[@class='mat-button-wrapper']")
	private WebElement userFullName;

	@FindBy(how = How.XPATH, using = "//mat-icon[normalize-space()='link']")
	private WebElement ssoLinks;

	@FindBy(how = How.XPATH, using = "//button[@class='mat-focus-indicator mat-menu-trigger mat-mini-fab mat-button-base mat-accent']")
	private WebElement userIcon;

	@FindBy(how = How.XPATH, using = "//h1[@class='.page-title']")
	private WebElement pxpCommandCenterLabel;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Profile Settings']")
	private WebElement profileSetting;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Help']")
	private WebElement userHelp;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Log out']")
	private WebElement userLogout;

	@FindBy(how = How.XPATH, using = "//div[@class='cdk-overlay-container']//a")
	private List<WebElement> ssoLinkList;

	@FindBy(id = "directorySearchInput")
	private WebElement textboxSearchClientDirectory;

	@FindBy(id = "directoryToggleIcon")
	private WebElement buttonSearchClientDirectory;

	@FindBy(xpath = "//input[@type='text']")
	private WebElement dropdownClientFilter;

	@FindBy(id = "directoryClearButton")
	private WebElement buttonClearClientFilter;

	@FindBy(id = "directoryApplyButton")
	private WebElement buttonApplyClientFilter;

	@FindBy(xpath = "//div[@class='tableborder']")
	private List<WebElement> tableSearchResult;

	@FindBy(xpath = "//td[@id='name-row']")
	private List<WebElement> tableClientNameInSearchResult;

	@FindBy(xpath = "//td[@id='no-result']")
	private WebElement noResultFoundInClientDirectory;

	@FindBy(xpath = "//td[@type='directoryTypeRow']")
	private List<WebElement> tableClientTypeInSearchResult;

	@FindBy(id = "treeNodeName")
	private List<WebElement> nodeNameInGeneralSetting;

	@FindBy(id = "name-row")
	private WebElement clientTableRow;

	@FindBy(xpath = "//span[@class='ng-tns-c112-6 ng-star-inserted']")
	private WebElement defaultItemsPerPage;

	@FindBy(xpath = "//div[@class='mat-paginator-range-label']")
	private WebElement displayPagination;

	@FindBy(xpath = "//div[@class='mat-select-arrow ng-tns-c112-20']")
	private WebElement itemsPerPageClick;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private List<WebElement> listOfItemsPerPage;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']")
	private List<WebElement> listOfRowsClientTable;

	List<String> actualSsoLinks = new ArrayList<String>();
	List<String> nodeUnderClientInGeneralSetting = new ArrayList<String>();

	public final List<String> expectedInternalSsoLinks = Arrays.asList("Appointments Dashboard",
			"DCF Admin Tool - Super User", "Form Builder (Forms)", "Practice Portal", "PSS Admin",
			"Virtual Visits Admin Tool");
	public final List<String> expectedExternalSsoLinks = Arrays.asList("Appointments Dashboard", "Form Builder (Forms)",
			"Pay Reports", "Practice Portal", "PSS Admin");

	public ClientDirectoryPage(WebDriver driver) {
		super(driver);
	}

	public void clickSSO() {
		CCUtils.verifyAndWaitForElement(ssoLinks, driver);
		javascriptClick(ssoLinks);
		log("SSOlinks clicked.....");
	}

	public void clickUserIcon() {
		CCUtils.verifyAndWaitForElement(userIcon, driver);
		javascriptClick(userIcon);
		log("User icon clicked.");
	}

	public void clickLogout() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(userLogout, driver);
		javascriptClick(userLogout);
		log("Clicked on logout");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void verifyTopPanelElements() {
		log("Verifying Command Center Top Panel Elements");
		Assert.assertTrue(pxpCommandCenterLabel.getText().equals("PxP Command Center"), "PXP Home icon is displayed");
		Assert.assertTrue(ssoLinks.isDisplayed(), "SSO icon is displayed");
		Assert.assertTrue(userFullName.isDisplayed(), "User icon is displayed");

	}

	public WebDriver verifyUserIconContent() {
		CCUtils.verifyAndWaitForElement(profileSetting, driver);
		CCUtils.verifyAndWaitForElement(userHelp, driver);
		CCUtils.verifyAndWaitForElement(userLogout, driver);
		return driver;
	}

	public void getSsoLinks() {
		log("number of sso links present for user is " + ssoLinkList.size());

		for (int i = 0; i < ssoLinkList.size(); i++) {
			actualSsoLinks.add(ssoLinkList.get(i).getText());
		}
		log("SSO links present for user are :" + actualSsoLinks);
	}

	public void verifySSOLinksForInternalUsers() throws InterruptedException {
		CCUtils.assertEquals(actualSsoLinks, expectedExternalSsoLinks);
	}

	public void verifySSOLinksForExternalUsers() throws InterruptedException {
		CCUtils.assertEquals(actualSsoLinks, expectedExternalSsoLinks);
	}

	public void fetchElementColor(WebElement we, String uiName) {
		String weColor = we.getCssValue("color");
		String weBGColor = we.getCssValue("background-color");
		log("Colour for " + uiName + " is " + weColor + " & background color is " + weBGColor);
	}

	public void searchNode(String searchNodeName) {
		CCUtils.verifyAndWaitForElement(textboxSearchClientDirectory, driver);
		textboxSearchClientDirectory.sendKeys(searchNodeName);
		log("Entered text in Client Directory Search is : " + searchNodeName);
	}

	public void selectClientTypeDropDown() {
		CCUtils.verifyAndWaitForElement(dropdownClientFilter, driver);
		dropdownClientFilter.click();
		log("Clicked on Client Type dropdown");
	}

	public void clickSearchButton() {

		CCUtils.verifyAndWaitForElement(buttonSearchClientDirectory, driver);
		buttonSearchClientDirectory.click();
		log("Clicked on Search button");

	}

	public void applyClientTypeFilter() {
		CCUtils.verifyAndWaitForElement(buttonApplyClientFilter, driver);
		buttonApplyClientFilter.click();
		log("Clicked on Apply button in Client type Filter");
	}

	public void clearClientTypeFilter() {
		CCUtils.verifyAndWaitForElement(buttonClearClientFilter, driver);
		buttonClearClientFilter.click();
		log("Clicked on Clear button in Client type Filter");
	}

	public void clickSearchResult() {
		CCUtils.verifyAndWaitForElement(tableClientNameInSearchResult.get(0), driver);
		tableClientNameInSearchResult.get(0).click();
		log("Clicked on Client :" + tableClientNameInSearchResult.get(0).getText());
	}

	public void verfiySearchResult(String searchNodeName) {
		CCUtils.verifyAndWaitForElement(tableClientNameInSearchResult.get(0), driver);
		log("number of matching results for searched text :" + tableClientNameInSearchResult.size());
		for (int listOfClientInResult = 0; listOfClientInResult <= tableSearchResult.size(); listOfClientInResult++) {
			CCUtils.assertTrue(
					tableClientNameInSearchResult.get(listOfClientInResult).getText().contains(searchNodeName),
					"Result matches");

			log("Searched result in row " + (listOfClientInResult + 1) + " is :"
					+ tableClientNameInSearchResult.get(listOfClientInResult).getText());
			if (tableClientNameInSearchResult.get(listOfClientInResult).getText().equalsIgnoreCase(searchNodeName)) {
				log("Matching result found");
				break;
			} else {
				continue;
			}

		}

	}

	public boolean testToCheckNoResultsFound() {
		CCUtils.verifyAndWaitForElement(noResultFoundInClientDirectory, driver);
		if (noResultFoundInClientDirectory.getText().equals("No search results found")) {
			return true;
		}
		log("Search result found " + noResultFoundInClientDirectory.getText());
		return false;
	}

	public void verfiySearchButtonFunctionality(String searchClient) {
		CCUtils.verifyAndWaitForElement(textboxSearchClientDirectory, driver);
		log("Default text in Client directory search is : " + textboxSearchClientDirectory.getAttribute("placeholder"));
		CCUtils.assertTrue(textboxSearchClientDirectory.getAttribute("placeholder").contains("Search"), "");
		CCUtils.assertEquals(buttonSearchClientDirectory.getText(), "SEARCH");
		log("Default text present in Search button in search textbox : " + buttonSearchClientDirectory.getText());
		searchNode(searchClient);
		clickSearchButton();
		CCUtils.assertEquals(buttonSearchClientDirectory.getText(), "CLEAR");
		log("Text present in Client search button after it is clicked is : " + buttonSearchClientDirectory.getText());
	}

	private String xpathRequiredClientType = "//label[normalize-space()='valueToReplace']";

	private WebElement prepareWebElementWithDynamicXpath(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("valueToReplace", substitutionValue)));
	}

	public void selectClientType(String clientTypeName) {

		WebElement clientType = prepareWebElementWithDynamicXpath(xpathRequiredClientType, clientTypeName);
		log("Clicked on : " + clientTypeName);
		clientType.click();
	}

	public void verfiySearchResultForClientType(String resultClientType) {
		CCUtils.verifyAndWaitForElement(tableClientTypeInSearchResult.get(0), driver);
		log("number of matching results for searched text :" + tableClientTypeInSearchResult.size());
		for (int listOfClient = 0; listOfClient <= tableClientTypeInSearchResult.size(); listOfClient++) {
			CCUtils.assertTrue(tableClientTypeInSearchResult.get(listOfClient).getText().contains(resultClientType),
					"Verifying each node");
			log("Searched result in row " + (listOfClient + 1) + " is :"
					+ tableClientTypeInSearchResult.get(listOfClient).getText());
			if (tableClientTypeInSearchResult.get(listOfClient).getText().equalsIgnoreCase(resultClientType)) {
				break;
			} else {
				continue;
			}
		}
	}

	public void verifySearchNodeInGeneralSetting() {
		CCUtils.verifyAndWaitForElement(tableClientNameInSearchResult.get(0), driver);
		tableClientNameInSearchResult.get(0).click();
		log("Clicked on Client :" + tableClientNameInSearchResult.get(0).getText());
	}

	public List<String> getClientNodeinGeneralSetting() {
		CCUtils.verifyAndWaitForElement(nodeNameInGeneralSetting.get(0), driver);
		log("number of nodes under Client is " + nodeNameInGeneralSetting.size());

		for (int i = 0; i < nodeNameInGeneralSetting.size(); i++) {
			nodeUnderClientInGeneralSetting.add(nodeNameInGeneralSetting.get(i).getText());
			log("Nodes present in Client is :" + nodeNameInGeneralSetting.get(i).getText());
		}
		return nodeUnderClientInGeneralSetting;
	}

	public GeneralSettingsPage selectSearchedClientNameInClientTable() {
		CCUtils.verifyAndWaitForElement(clientTableRow, driver);
		clientTableRow.click();
		log("Clicked on client name in client directory table");
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void searchAndSelectClientNameInClientTable(String searchText) {
		log("Enter search text in search textbox");
		searchNode(searchText);
		log("Click on search button");
		clickSearchButton();
		log("Click on client name in client directory table");
		selectSearchedClientNameInClientTable();
	}

	public String getDefaultItemsPerPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		highlightElement(defaultItemsPerPage);
		return defaultItemsPerPage.getText().toString();
	}

	public String getDefaultPagination() {
		String s1 = displayPagination.getText().toString();
		String[] words=s1.split("of",0);
		String data = null;
		for(String w :words){  
			data = w.trim();
		log("Print the value of default Pagination : "+w.trim());  
		break;
		}  
		return data.trim();
	}

	public String getTotalNoOfClients() {
		String s1 = displayPagination.getText().toString();
		String[] words=s1.split("of",0);
		String data = null;
		for(String w :words){  
			data = w.trim();
		log("Print the value of default Pagination : "+w.trim());  
		}  
		return data.trim();
	}

}