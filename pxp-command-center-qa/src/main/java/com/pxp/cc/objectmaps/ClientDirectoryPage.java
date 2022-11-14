// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.pxp.cc.util.CCUtils;

public class ClientDirectoryPage extends CCMainPage {

	@FindBy(how = How.XPATH, using = "//button[@class='mat-focus-indicator mat-menu-trigger mat-mini-fab mat-button-base mat-accent']//span[@class='mat-button-wrapper']")
	private WebElement userFullName;

	@FindBy(how = How.XPATH, using = "//mat-icon[normalize-space()='link']")
	private WebElement ssoLinks;

	@FindBy(id = "headerBtn")
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

	@FindBy(id = "directoryTypeInput")
	private WebElement dropdownClientFilter;

	@FindBy(id = "directoryClearButton")
	private WebElement buttonClearClientFilter;

	@FindBy(xpath = "//button[@class = 'confirmbutton']/span[text()='APPLY']")
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

	@FindBy(xpath = "//mat-icon[@id='directoryGradeIcon']")
	private List<WebElement> listOfFavClients;

	@FindBy(xpath = "//tr[@class='mat-row cdk-row ng-star-inserted']//mat-icon[@id='star-icon']")
	private List<WebElement> listOfNonFavClients;

	@FindBy(id = "cmdCircleIcon")
	private WebElement iconCommandCenterUsers;

	@FindBy(id = "mat-select-0")
	private WebElement defaultItemsPerPage;

	@FindBy(xpath = "//div[@class='mat-paginator-range-label']")
	private WebElement displayPagination;

	@FindBy(xpath = "//mat-select[@role='combobox']//following::div[starts-with(@class,'mat-select-arrow')][1]")
	private WebElement itemsPerPageDropdown;

	@FindBy(xpath = "//span[@class='mat-option-text']")
	private List<WebElement> listOfItemsPerPage;

	@FindBy(xpath = "//tbody[@role='rowgroup']//tr[@role='row']")
	private List<WebElement> listOfRowsClientTable;

	@FindBy(xpath = "//button[@aria-label='Next page']")
	private WebElement buttonNextPage;

	@FindBy(xpath = "//button[@aria-label='Last page']")
	private WebElement buttonLastPage;

	@FindBy(xpath = "//button[@aria-label='Previous page']")
	private WebElement buttonPreviousPage;

	@FindBy(xpath = "//button[@aria-label='First page']")
	private WebElement buttonFirstPage;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'20')]")
	private WebElement itemsPerPageTwenty;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'50')]")
	private WebElement itemsPerPageFifty;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'100')]")
	private WebElement itemsPerPageHundred;

	@FindBy(xpath = "//span[@class='mat-option-text'][contains(text(),'200')]")
	private WebElement itemsPerPageTwoHundred;

	@FindBy(id = "directoryTitle")
	private WebElement labelPageTitle;

	@FindBy(xpath = "//mat-icon[@id='star-icon']")
	private WebElement unFavoritedStarIcon;

	@FindBy(xpath = "//mat-icon[@id='star-icon']")
	private WebElement FavoritedStarIcon;

	@FindBy(xpath = "//div[@id='cdk-describedby-message-16']")
	private WebElement tooltipMsg;

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
		log("SSOlinks clicked....");
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
		javascriptClick(buttonSearchClientDirectory);
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
		String searchResultText = tableClientNameInSearchResult.get(0).getText().trim();
		tableClientNameInSearchResult.get(0).click();
		log("Clicked on Client :" + searchResultText);
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
		log("webdriverwait initiated");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='table']"))));
		log("webdriverwait Completed and tableClientTypeInSearchResult validation");
		CCUtils.verifyAndWaitForListOfElement(tableClientTypeInSearchResult, driver);
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
		highlightElement(clientTableRow);
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

	public void listOfFavoritingClients(List<String> list) throws AWTException, InterruptedException {
		for (int i = 0; i < list.size(); i++) {
			if (i == 5) {
				textboxSearchClientDirectory.clear();
				searchNode(list.get(i).trim());
				clickSearchButton();
				CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
				unFavoritedStarIcon.click();
				CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
				log("Clicked to Favorite star icon");
				CCUtils.performMoveCursor(unFavoritedStarIcon, driver);
				CCUtils.hover(unFavoritedStarIcon, driver);
				String color = fetchWebElementColor(unFavoritedStarIcon);
				log("print value of color since it is not favourited it should be : " + color);
				log("comparing color of fav icon to : " + color);
				CCUtils.assertEquals(color, "rgba(0, 0, 0, 0.6)");
			} else {
				textboxSearchClientDirectory.clear();
				searchNode(list.get(i).trim());
				clickSearchButton();
				CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
				unFavoritedStarIcon.click();
				log("Clicked to Favorite star icon");
				textboxSearchClientDirectory.clear();
			}
		}
	}

	public CommandCenterUsersPage clickOnCommandCenterUsersIcon() {
		CCUtils.verifyAndWaitForElement(iconCommandCenterUsers, driver);
		iconCommandCenterUsers.click();
		log("Clicked on command center users icon");
		return PageFactory.initElements(driver, CommandCenterUsersPage.class);
	}

	public String getDefaultItemsPerPage() throws InterruptedException {
		CCUtils.verifyAndWaitForElement(defaultItemsPerPage, driver);
		highlightElement(defaultItemsPerPage);
		return defaultItemsPerPage.getText().toString();
	}

	public String getDefaultPagination() {
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("of", 0);
		String data = null;
		for (String w : words) {
			data = w.trim();
			log("Print the value of default Pagination : " + w.trim());
			break;
		}
		return data.trim();
	}

	public String getTotalNoOfClients() {
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("of", 0);
		String data = null;
		for (String w : words) {
			data = w.trim();
			log("Print the value of default Pagination : " + w.trim());
		}
		return data.trim();
	}

	public String splitingPagination() {
		CCUtils.verifyAndWaitForElement(displayPagination, driver);
		String s1 = displayPagination.getText().toString();
		String[] words = s1.split("\\s", 3);
		String data = null;
		for (String w : words) {
			data = w.trim();
		}
		log("Print the value of default Pagination : " + data.trim());
		return data.trim();
	}

	public String getNoOfRowsPagination() {
		String data = splitingPagination();
		String noOfRowsPagination = data.substring(0, 2).trim();
		return noOfRowsPagination.trim();
	}

	public String getLHSPagination() throws AWTException {
		String data = getDefaultPagination();
		String[] words = data.split("–", 2);
		for (String w : words) {
			data = w.trim();
			break;
		}
		log("Print the value of LHS Pagination : " + data.trim());
		return data.trim();
	}

	public String getRHSPagination() throws AWTException {
		String data = getDefaultPagination();
		String[] words = data.split("–", 0);
		for (String w : words) {
			data = w.trim();
		}
		log("Print the value of RHS Pagination : " + data.trim());
		return data.trim();
	}

	public void clickItemsPerPageDropdown() throws InterruptedException, AWTException {
		CCUtils.scrollPageToEnd();
		CCUtils.verifyAndWaitForElement(itemsPerPageDropdown, driver);
		highlightElement(itemsPerPageDropdown);
		javascriptClick(itemsPerPageDropdown);
	}

	public List<Integer> getListOfItemsPerPage() {
		List<Integer> itemsPerPage = new ArrayList<Integer>();
		for (WebElement item : listOfItemsPerPage) {
			log("Print the value of items per page : " + item.getText().toString());
			CCUtils.verifyAndWaitForElement(item, driver);
			itemsPerPage.add(Integer.parseInt(item.getText().toString()));
		}
		return itemsPerPage;
	}

	public int getNoOfRowsInClientTable() throws AWTException {
		CCUtils.scrollPageToEnd();
		for (int i = 0; i < listOfRowsClientTable.size(); i++) {
			CCUtils.verifyAndWaitForElement(listOfRowsClientTable.get(i), driver);
		}
		return listOfRowsClientTable.size();
	}

	public void clickNextPageButton() {
		CCUtils.verifyAndWaitForElement(buttonNextPage, driver);
		log("click to Next Page button");
		buttonNextPage.click();
	}

	public boolean isNextPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonNextPage, driver);
		return buttonStatus;
	}

	public void clickLastPageButton() {
		CCUtils.verifyAndWaitForElement(buttonLastPage, driver);
		log("click to Last Page button");
		buttonLastPage.click();
	}

	public boolean isLastPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonLastPage, driver);
		return buttonStatus;
	}

	public void clickPreviousPageButton() {
		CCUtils.verifyAndWaitForElement(buttonPreviousPage, driver);
		log("click to Previous Page button");
		buttonPreviousPage.click();
	}

	public boolean isPreviousPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonPreviousPage, driver);
		return buttonStatus;
	}

	public void clickFirstPageButton() {
		CCUtils.verifyAndWaitForElement(buttonFirstPage, driver);
		log("click to First Page button");
		buttonFirstPage.click();
	}

	public boolean isFirstPageButtonDisabled() {
		boolean buttonStatus = CCUtils.isPaginationButtonDisabled(buttonFirstPage, driver);
		return buttonStatus;
	}

	public String clickTwentyItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageTwenty, driver);
		highlightElement(itemsPerPageTwenty);
		currentItemPerPage = itemsPerPageTwenty.getText().toString();
		itemsPerPageTwenty.click();
		return currentItemPerPage;
	}

	public String clickFiftyItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageFifty, driver);
		highlightElement(itemsPerPageFifty);
		currentItemPerPage = itemsPerPageFifty.getText().toString();
		itemsPerPageFifty.click();
		return currentItemPerPage;
	}

	public String clickHundredItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageHundred, driver);
		highlightElement(itemsPerPageHundred);
		currentItemPerPage = itemsPerPageHundred.getText().toString();
		itemsPerPageHundred.click();
		return currentItemPerPage;
	}

	public String clickTwoHundredItemsPerPage() {
		String currentItemPerPage = null;
		CCUtils.verifyAndWaitForElement(itemsPerPageTwoHundred, driver);
		highlightElement(itemsPerPageTwoHundred);
		currentItemPerPage = itemsPerPageTwoHundred.getText().toString();
		itemsPerPageTwoHundred.click();
		return currentItemPerPage;
	}

	public void checkLastPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		if (noOfClients >= itemsPerPageValue) {
			clickLastPageButton();
			log("verifying RHS Pagination value : " + Integer.parseInt(getRHSPagination())
					+ "should be equal to Number of Clients : " + noOfClients);
			CCUtils.assertEquals(Integer.parseInt(getRHSPagination()), noOfClients);
			log("verifying after clicking to Last Page Button, Last Page and Next Page button should get disabled.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
		} else {
			log("Veriyfing all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertTrue((isLastPageButtonDisabled() == false), "Last Page Button should get disabled");
			CCUtils.assertTrue(isNextPageButtonDisabled() == false, "Next Page Button should get disabled");
			CCUtils.assertTrue(isFirstPageButtonDisabled() == false, "First Page Button should get disabled");
			CCUtils.assertTrue(isPreviousPageButtonDisabled() == false, "Previous Page Button should get disabled");
		}
	}

	public void checkNextPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		log("Total number of clients is : " + noOfClients);
		if (noOfClients >= itemsPerPageValue) {
			int LHSPaginationValue = 1;
			while (isNextPageButtonDisabled()) {
				clickNextPageButton();
				LHSPaginationValue += itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : " + getLHSPagination() + " with expected value : "
						+ LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(getLHSPagination()), LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkPreviousPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		log("Total number of clients is : " + noOfClients);
		if (noOfClients >= itemsPerPageValue) {
			checkNextPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
			int LHSPaginationValue = Integer.parseInt(getLHSPagination());
			while (isPreviousPageButtonDisabled()) {
				clickPreviousPageButton();
				LHSPaginationValue -= itemsPerPageValue;
				log("Verifying the actual LHS Pagination which is : " + Integer.parseInt(getLHSPagination())
						+ " with expected value : " + LHSPaginationValue);
				CCUtils.assertEquals(Integer.parseInt(getLHSPagination()), LHSPaginationValue);
			}
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void checkFirstPageButtonFunctionalityTillGetDisabled(int itemsPerPageValue)
			throws NumberFormatException, AWTException {
		int noOfClients = Integer.valueOf(getTotalNoOfClients());
		log("Total number of clients is : " + noOfClients);
		checkLastPageButtonFunctionalityTillGetDisabled(itemsPerPageValue);
		if (isFirstPageButtonDisabled() == true) {
			clickFirstPageButton();
			String expectedPagination = "1 – " + itemsPerPageValue;
			CCUtils.assertEquals(getDefaultPagination(), expectedPagination);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
		} else {
			log("Verifying all the Paginations should be disabled Toal number of clients is less that Items per page so all clients displayed in 1st slot.");
			CCUtils.assertEquals(isLastPageButtonDisabled(), false);
			CCUtils.assertEquals(isFirstPageButtonDisabled(), false);
			CCUtils.assertEquals(isNextPageButtonDisabled(), false);
			CCUtils.assertEquals(isPreviousPageButtonDisabled(), false);
		}
	}

	public void verifyClientDirectioryPageTitle() {
		log("Verifying client directiory page title");
		CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
		CCUtils.assertTrue(labelPageTitle.isDisplayed(), "Client directory page title is not displayed");
		CCUtils.assertEquals(labelPageTitle.getText().trim(), "Client Directory");
	}

	public void deselectFavClientInClientDirectory() {
		for (int i = listOfFavClients.size() - 1; i >= 0; i--) {
			CCUtils.verifyAndWaitForElement(listOfFavClients.get(i), driver);
			highlightElement(listOfFavClients.get(i));
			listOfFavClients.get(i).click();
			CCUtils.verifyAndWaitForElement(listOfNonFavClients.get(i), driver);
		}
		log("Un-Favorited all clients in client directory table");
	}

	public void selectFavClientInClientDirectory() {
		for (int i = listOfFavClients.size(); i <= 5; i++) {
			CCUtils.verifyAndWaitForElement(listOfNonFavClients.get(i), driver);
			highlightElement(listOfNonFavClients.get(i));
			listOfNonFavClients.get(i).click();
		}
		log("Favorited all clients in client directory table");
	}

	public int getSizeOfListOfFavClients() {
		log("print size of client :" + listOfFavClients.size());
		return listOfFavClients.size();
	}

	public void unFavoriteAllFavoritedClients() {
		for (WebElement ele : listOfFavClients) {
			// CCUtils.verifyAndWaitForElement(ele, driver);
			log("unfavoriting fav clients" + ele.getText());
			javascriptClick(ele);
		}
	}

	public void FavoriteAClient() {
		if (FavoritedStarIcon.isSelected()) {
			log("client is already favorited");
		} else {
			CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
			unFavoritedStarIcon.click();
			log("Clicked to Favorite star icon");
		}
	}

	public boolean getNoOfFavoriteClients() {
		if (getSizeOfListOfFavClients() == 5) {
			log("Total no of favorited clients is five");
			return false;
		} else
			log("Total no of favorited clients is less than five");
		return true;

	}

	public boolean verifyAClientIsNonFav() {
		CCUtils.verifyAndWaitForElement(unFavoritedStarIcon, driver);
		if (unFavoritedStarIcon.isDisplayed()) {
			log("It is un - favorited");
			return true;
		} else
			return false;
	}

	public int getSizeOfListOfNonFavClients() {
		return listOfNonFavClients.size();
	}

	public int getSizeListOfFavClients() {
		return listOfFavClients.size();
	}

	public void verifyNoOfFavoriteClient(boolean value) {
		if (value == false) {
			log("since favorited clients are 5 so unfavoriting all favorited client.");
			unFavoriteAllFavoritedClients();
		} else
			log("favorited clients are less tham 5");
	}

	public void validateFavClientsAndUnFavThem(int value) {
		if ((value <= 5) && (value != 0)) {
			log("since favorited clients are 5 or less than 5 so unfavoriting all favorited client.");
			unFavoriteAllFavoritedClients();
		} else
			log("No need to un favorite as no favorite clients present");
	}

	public void howManyFavClients(int value) {
		if ((value <= 5) && (value != 0)) {
			log("favorited clients are 5 or less than 5");
		} else
			log("no favorite clients present");
	}

	public boolean checkNoOfFavClientsAndUnFavoriteThem(int value) {
		if (value <= 5 && value != 0) {
			log("Favorite clients are : " + value);
			deselectFavClientInClientDirectory();
			return true;
		} else {
			log("No Favorite clients so selecting first five client as favorite and unfavorite them");
			return false;
		}
	}

	public void FavoriteClientThenUnfavorite(boolean value, String ClientName) {
		if (value == false) {
			searchNode(ClientName);
			clickSearchButton();
			FavoriteAClient();
			deselectFavClientInClientDirectory();
		} else
			log("Already deselected all clients");
	}

	public void gettoolTipMessage() {
	}
}
