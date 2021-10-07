// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.pxp.cc.util.CCUtils;

public class AddOrgNodePage extends CCMainPage {

	@FindBy(id = "addNodeTitle")
	private WebElement lablePageTitle;

	@FindBy(id = "cancel-text")
	private WebElement crossIcon;

	@FindBy(id = "addNodeCancel")
	private WebElement buttonCancelInAddNewNode;

	@FindBy(id = "addNodeSave")
	private WebElement buttonSaveAndAdd;

	@FindBy(id = "parent-label")
	private WebElement parentLabel;

	@FindBy(id = "addNodeClientDisp")
	private WebElement textboxParentEntityNameAtSystem;

	@FindBy(xpath = "//ng-select[@id='addNodeEnterpriseSelect'] | //ng-select[@id='addNodePracticeLabel']")
	private WebElement dropdownParentEntityName;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textboxParentEntityName;

	@FindBy(xpath = "//span[text()='Practice Name*']")
	private WebElement labelPracticeName;

	@FindBy(xpath = "//span[text()='System Name*']")
	private WebElement labelSystemName;

	@FindBy(xpath = "//span[text()='Enterprise Name*']")
	private WebElement labelEnterpriseName;

	@FindBy(id = "addNodeNameInput")
	private WebElement textboxName;

	@FindBy(xpath = "//span[text()='Solutions Display Name']")
	private WebElement solutionDisplayNameLabel;

	@FindBy(id = "addNodeSolutionInput")
	private WebElement textboxSolutionDisplayName;

	@FindBy(xpath = "//span[text()='SAP ID']")
	private WebElement sapIdLabel;

	@FindBy(id = "addNodeSapInput")
	private WebElement textboxSapId;

	@FindBy(id = "timezone-label")
	private WebElement timezoneLabel;

	@FindBy(id = "timezoneSelect")
	private WebElement dropdownTimezone;

	@FindBy(xpath = "//div[@aria-expanded='true']//input[@type='text']")
	private WebElement textboxTimezone;

	@FindBy(xpath = "//span[text()='Status * ']")
	private WebElement statusLabel;

	@FindBy(xpath = "//input[@id='addNodeActiveRadio-input']")
	private WebElement radioButtonActive;

	@FindBy(xpath = "//input[@id='addNodeInactiveRadio-input']")
	private WebElement radioButtonInactive;

	@FindBy(xpath = "//ng-select[@placeholder='Search/Select']")
	private WebElement placeholderParentEntityDropdown;

	@FindBy(xpath = "//ng-select[@placeholder='Select']")
	private WebElement placeholderTimezoneDropdown;

	@FindBy(xpath = "//span[text()='Which solutions will this practice use?']")
	private WebElement labelSolution;

	@FindBy(xpath = "//input[@type='checkbox']")
	private List<WebElement> checkboxSolution;

	@FindBy(xpath = "//div[text()=' Maximum characters reached ']")
	private WebElement errorMaximumCharacter;

	@FindBy(xpath = "//div[@class='text-danger ng-star-inserted']//div[@class='ng-star-inserted']")
	private WebElement errorNameRequired;

	@FindBy(xpath = "(//div[@class='text-danger ng-star-inserted'])[3]")
	private WebElement errorInvalidSap;

	@FindBy(xpath = "//div[contains(text(),'Parent Entity is required')]")
	private WebElement errorParentNameRequired;

	@FindBy(xpath = "//div[@class='flex']/span")
	private WebElement toasterMessage;

	@FindBy(xpath = "//mat-icon[text()='close']")
	private WebElement closeToasterMessageIcon;

	@FindBy(xpath = "//div[@aria-selected='true']/span")
	private WebElement selectedTimezoneValue;

	@FindBy(xpath = "//div[text()='No items found']")
	private WebElement textNoItemsFound;

	@FindBy(xpath = "//div[@role='option']")
	private List<WebElement> timezoneOptionList;

	@FindBy(xpath = "//span[@class='mat-checkbox-label']")
	private List<WebElement> labelSolutionList;

	List<String> actualtimezoneValues = new ArrayList<String>();
	List<String> actualSolutionValues = new ArrayList<String>();

	public final List<String> expectedTimezoneValues = Arrays.asList("Select", "Eastern Standard Time",
			"Atlantic Standard Time", "Central Standard Time", "Mountain Standard Time", "Pacific Standard Time",
			"Alaska Standard Time", "Hawaii Standard Time", "Hong Kong Standard Time", "Indiana Standard Time",
			"Arizona Standard Time", "Midway Island, Samoa", "International Date Line West", "Nuku'alofa",
			"Tijuana, Baja California", "Chihuahua, La Paz, Mazatlan", "Central America",
			"Guadalajara, Mexico City, Monterrey", "Saskatchewan", "Bagota, Lima, Quito, Rio Branco", "Caracas",
			"La Paz", "Manaus", "Santiago", "Newfoundland", "Buenos Aires, Georgetown", "Greenland", "Montevideo",
			"Azores", "Cape Verde Is.", "Casablanca,Monrovia,Reykjavik", "Greenwich Mean Time",
			"Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna", "Belgrade, Bratislava, Budapest, Ljubljana, Prague",
			"Brussels, Copenhagen, Madrid, Paris", "Sarajevo, Skopje, Warsaw, Zagreb", "West Central Africa", "Amman",
			"Athens, Bucharest, Istanbul", "Beirut", "Cairo", "Harare, Pretoria",
			"Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius", "Jerusalem", "Minsk", "Mid-Atlantic", "Baghdad",
			"Kuwait, Riyadh", "Moscow, St. Petersburg, Volgograd", "Nairobi", "Tehran", "Abu Dhabi, Muscat", "Baku",
			"Yerevan", "Kabul", "Ekaterinburg", "Islamabad, Karachi, Tashkent", "Chennai, Kolkata, Mumbai, New Delhi",
			"Kathmandu", "Almaty, Novosibirsk", "Astana, Dhaka", "Yangon (Rangoon)", "Bangkok, Hanoi, Jakarta",
			"Krasnoyarsk", "Irkutsk, Ulaan Bataar", "Kuala Lumpur, Singapore", "Perth", "Taipei",
			"Osaka, Sapporo, Tokyo", "Seoul", "Yakutsk", "Adelaide", "Darwin", "Brisbane",
			"Canberra, Melbourne, Sydney", "Guam, Port Moresby", "Hobart", "Vladivostok",
			"Magadan, Solomon Is., New Caledonia", "Auckland, Wellington");

	public final List<String> expectedSolutionValues = Arrays.asList("Chat", "Engage", "Forms", "PSS", "Pay", "Portal",
			"Virtual Visits");

	public AddOrgNodePage(WebDriver driver) {
		super(driver);
	}

	public void verifyActiveButtonByDefaultSelected() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		log("Verifying active button is by default selected");
		if (radioButtonActive.isDisplayed() == true && radioButtonActive.isSelected() == true) {
			log("Active button is displayed on screen and selected");
		} else {
			log("Active button is not displayed on screen and selected");
		}
	}

	public Boolean verifyValueIsNotSelectedInParentEntity() {
		CCUtils.verifyAndWaitForElement(textboxParentEntityName, driver);
		String parentEntityName = textboxParentEntityName.getText().trim();
		if (parentEntityName.length() == 0) {
			log("Serach text is not present in parent entity textbox");
			return true;
		} else {
			log("Search text is present in parent entity textbox");
		}
		return false;

	}

	public void verifyDataIsNotFetchedIfParentEntityNotSelected(Boolean isParentEntityNameNotSelected) {
		if (isParentEntityNameNotSelected == true) {
			log("Verifying text is not present in sap id field");
			CCUtils.verifyAndWaitForElement(textboxSapId, driver);
			String sapId = textboxSapId.getText().trim();
			CCUtils.assertTrue(sapId.length() == 0, "Text is present in sap id field");
			log("Verifying text is not present in timezone field");
			CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
			dropdownTimezone.click();
			CCUtils.verifyAndWaitForElement(textboxTimezone, driver);
			String timezone = textboxTimezone.getText().trim();
			CCUtils.assertTrue(timezone.length() == 0, "Text is present in timezone field");
		}
	}

	public GeneralSettingsPage clickOnSaveAndAddButton() {
		CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
		log("Clicked on Save&Add button");
		buttonSaveAndAdd.click();
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCancelButton() {
		CCUtils.verifyAndWaitForElement(buttonCancelInAddNewNode, driver);
		log("Clicked on Cancel button");
		buttonCancelInAddNewNode.click();
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public GeneralSettingsPage clickOnCrossIcon() {
		CCUtils.verifyAndWaitForElement(crossIcon, driver);
		log("Clicked on X icon");
		crossIcon.click();
		return PageFactory.initElements(driver, GeneralSettingsPage.class);
	}

	public void verifyErrorMessageForOrgNodeNameField(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered text in org name field is " + orgName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacter, driver);
		CCUtils.assertEquals(errorMaximumCharacter.getText().trim(), "Maximum characters reached");
		log("Error Message displayed for name field is " + errorMaximumCharacter.getText().trim());
	}

	public void verifyErrorMessageForSolutionDisplayNameField(String solutionDisplayName) {
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.sendKeys(solutionDisplayName);
		log("Entered text in solution display name field is " + solutionDisplayName);
		CCUtils.verifyAndWaitForElement(errorMaximumCharacter, driver);
		CCUtils.assertEquals(errorMaximumCharacter.getText().trim(), "Maximum characters reached");
		log("Error Message displayed for solution display name field is " + errorMaximumCharacter.getText().trim());
	}

	public void verifyErrorMessageForSapIdField(String sapId) {
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(sapId);
		log("Entered text in sap id field is " + sapId);
		CCUtils.verifyAndWaitForElement(errorInvalidSap, driver);
		CCUtils.assertEquals(errorInvalidSap.getText().trim(), "SAP ID cannot contain more than 50 characters");
		log("Error Message displayed for sap id field is " + errorInvalidSap.getText().trim());
	}

	public void verifyOrgNameIsRequiredField(String labelText) {
		CCUtils.verifyAndWaitForElement(errorNameRequired, driver);
		if (labelText.trim().equals("System Name*")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "System name is required");
			log("System name is required error message displayed on add new system popup");
		} else if (labelText.trim().equals("Enterprise Name*")) {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "Enterprise name is required");
			log("Enterprise name is required error message displayed on add new enterprise popup");
		} else {
			CCUtils.assertEquals(errorNameRequired.getText().trim(), "Practice name is required");
			log("Practice name is required error message displayed on add new practice popup");
		}
	}

	public void enterSpaceInRequiredField() {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Enter space in org name textbox");
		textboxName.sendKeys("  ");
		clickOnSaveAndAddButton();
	}

	public void verifyToasterMessage(String labelText) {
		CCUtils.verifyAndWaitForElement(toasterMessage, driver);
		if (labelText.trim().equals("System Name*")) {
			CCUtils.assertEquals(toasterMessage.getText().trim(), "System name cannot be empty");
			log("Error Message displayed for system name field is " + toasterMessage.getText().trim());
		} else if (labelText.trim().equals("Enterprise Name*")) {
			CCUtils.assertEquals(toasterMessage.getText().trim(), "Enterprise name cannot be empty");
			log("Error Message displayed for enterprise name field is " + toasterMessage.getText().trim());
		} else {
			CCUtils.assertEquals(toasterMessage.getText().trim(), "Practice name cannot be empty");
			log("Error Message displayed for practice name field is " + toasterMessage.getText().trim());
		}
		CCUtils.verifyAndWaitForElement(closeToasterMessageIcon, driver);
		closeToasterMessageIcon.click();
	}

	public void verifyParentEntityNameIsRequiredField() {
		CCUtils.verifyAndWaitForElement(errorParentNameRequired, driver);
		CCUtils.assertEquals(errorParentNameRequired.getText().trim(), "Parent Entity is required");
		log("Parent Entity required error message displayed in add new enterprise popup");
	}

	public void verifyParentEntityDropdownisReadonlyField() {
		CCUtils.verifyAndWaitForElement(textboxParentEntityNameAtSystem, driver);
		log("Verify parent entity dropdown is disabled on add system page");
		String state = textboxParentEntityNameAtSystem.getAttribute("readonly");
		CCUtils.assertNotNull(state, "The object return null value");
	}

	public void verifyTestDataFetchedFromSelectedParent(String sapId, String timezone, String Status) {
		wait.until(ExpectedConditions.textToBePresentInElementValue(textboxSapId, sapId));
		log("Verifying sap id is fetched from selected parent or not");
		CCUtils.assertEquals(textboxSapId.getAttribute("value").trim(), sapId.trim());
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		log("Click on timezone dropdown");
		dropdownTimezone.click();
		log("Verifying timezone is fetched from selected parent or not");
		CCUtils.assertEquals(selectedTimezoneValue.getText().trim(), timezone.trim());
		log("Verifying status is fetched from selected parent or not");
		if (Status.toString().trim().equals("active")) {
			CCUtils.assertTrue(radioButtonActive.isSelected(), "Active radio button is not selected");
		} else {
			CCUtils.assertTrue(radioButtonInactive.isSelected(), "Inactive radio button is not selected");
		}
	}

	public void verifyClientNameIsDisplayedOnParentEntityDropdown(String clientName) {
		log("Verifying client name is displayed on parent entity drop down");
		wait.until(ExpectedConditions.textToBePresentInElementValue(textboxParentEntityNameAtSystem, clientName));
		CCUtils.assertEquals(textboxParentEntityNameAtSystem.getAttribute("value").trim(), clientName);
	}

	public void createNewOrgNode(String orgName, String sapId, String timezone, String solutionDisplayName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.sendKeys(orgName);
		log("Entered org name is " + orgName);
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.clear();
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.sendKeys(sapId);
		log("Entered org sap id is " + sapId);
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		CCUtils.verifyAndWaitForElement(textboxTimezone, driver);
		textboxTimezone.sendKeys(timezone, Keys.ENTER);
		log("Entered org timezone is " + timezone);
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.sendKeys(solutionDisplayName);
		log("Entered org solution display name is " + solutionDisplayName);
	}

	public void verifySolutionLabelIsDisplayed() {
		CCUtils.verifyAndWaitForElement(labelSolution, driver);
		CCUtils.assertEquals(labelSolution.getText().trim(), "Which solutions will this practice use?");
	}

	public void addSolutions() throws InterruptedException {
		log("Select all solutions");
		scrollAndWait(0, 500, 3000);
		for (int i = 0; i < checkboxSolution.size(); i++) {
			javascriptClick(checkboxSolution.get(i));
		}
	}

	public void verifyBoxTextForParentEntityField() {
		log("Verifying search/select placeholder is displayed for parent entity field");
		CCUtils.verifyAndWaitForElement(dropdownParentEntityName, driver);
		CCUtils.assertEquals(dropdownParentEntityName.getAttribute("placeholder").toString(), "Search/Select");
	}

	public void verifyBoxTextForSapIdField() {
		log("Verifying input text placeholder is displayed for sap id field textbox");
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.clear();
		CCUtils.assertEquals(textboxSapId.getAttribute("placeholder").toString(), "Input Text");
	}

	public void verifyBoxTextForTimezoneField() {
		log("Verifying select placeholder is displayed for timezone field");
		CCUtils.verifyAndWaitForElement(placeholderTimezoneDropdown, driver);
		CCUtils.assertEquals(placeholderTimezoneDropdown.getAttribute("placeholder").toString(), "Select");
	}

	public void verifyBoxTextForSolutionDisplayNameField() {
		log("Verifying inputtText placeholder is displayed for solution display name field textbox");
		CCUtils.verifyAndWaitForElement(textboxSolutionDisplayName, driver);
		textboxSolutionDisplayName.clear();
		CCUtils.assertEquals(textboxSolutionDisplayName.getAttribute("placeholder").toString(), "Input Text");
	}

	public void verifyBoxTextForNameField() {
		log("Verifying input text placeholder is displayed for name field textbox");
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		textboxName.clear();
		CCUtils.assertEquals(textboxName.getAttribute("placeholder").toString(), "Input Text");
	}

	public void selectInactiveRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonInactive, driver);
		javascriptClick(radioButtonInactive);
		log("Clicked on inactive radiobutton in popup");
	}

	public void selectActiveRadiobutton() {
		CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
		javascriptClick(radioButtonActive);
		log("Clicked on active radiobutton in popup");
	}

	public void clickOnParentEntityNameDropdown() {
		CCUtils.verifyAndWaitForElement(dropdownParentEntityName, driver);
		dropdownParentEntityName.click();
		log("Clicked on parent entity name dropdown in add new enterprise popup");
	}

	public void searchTextForParentEntityName(String searchText) {
		CCUtils.verifyAndWaitForElement(textboxParentEntityName, driver);
		log("Enter text in parent entity textbox");
		textboxParentEntityName.sendKeys(searchText.trim());
		textboxParentEntityName.sendKeys(Keys.ENTER);
		log("Entered searched text is " + searchText.trim());
	}

	public void verifyNoItemsFound() {
		CCUtils.verifyAndWaitForElement(textNoItemsFound, driver);
		CCUtils.assertEquals(textNoItemsFound.getText().trim(), "No items found");
		log("No items found for the entered text");
	}

	public String getSystemNameLabelText() {
		return labelSystemName.getText().trim();
	}

	public String getEnterpriseNameLabelText() {
		return labelEnterpriseName.getText().trim();
	}

	public String getPracticeNameLabelText() {
		return labelPracticeName.getText().trim();
	}

	public void verifySapIdFieldAcceptBlankSpace(String orgName) {
		CCUtils.verifyAndWaitForElement(textboxName, driver);
		log("Enter text in org name textbox");
		textboxName.sendKeys(orgName);
		log("Entered org name text is " + orgName.trim());
		CCUtils.verifyAndWaitForElement(textboxSapId, driver);
		textboxSapId.clear();
		log("Enter space in org sap id textbox");
		textboxSapId.sendKeys(" ");
	}

	public void getTimezoneDropdownValue() {
		CCUtils.verifyAndWaitForElement(dropdownTimezone, driver);
		dropdownTimezone.click();
		for (int i = 0; i < timezoneOptionList.size(); i++) {
			actualtimezoneValues.add(timezoneOptionList.get(i).getText().trim());
		}
		log("Timezone values present for user are :" + actualtimezoneValues);

	}

	public void verifyTimezoneDropdownValues() throws InterruptedException {
		CCUtils.assertEquals(actualtimezoneValues, expectedTimezoneValues);
	}

	public void getSolutionValues() throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		for (int i = 0; i < labelSolutionList.size(); i++) {
			actualSolutionValues.add(labelSolutionList.get(i).getText().trim());
		}
		log("Solution available for user are :" + actualSolutionValues);
	}

	public void verifySolutionValues() throws InterruptedException {
		CCUtils.assertEquals(actualSolutionValues, expectedSolutionValues);
	}

	public void addNewSystemNode(String orgName, String sapId, String timezone, String solutionDisplayName,
			Boolean isActive) {
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		clickOnSaveAndAddButton();
	}

	public void addNewNode(String parentEntityName, Boolean isActive, String orgName, String sapId, String timezone,
			String solutionDisplayName) {
		clickOnParentEntityNameDropdown();
		searchTextForParentEntityName(parentEntityName);
		createNewOrgNode(orgName, sapId, timezone, solutionDisplayName);
		if (isActive == true)
			selectActiveRadiobutton();
		else
			selectInactiveRadiobutton();
		clickOnSaveAndAddButton();
	}
}
